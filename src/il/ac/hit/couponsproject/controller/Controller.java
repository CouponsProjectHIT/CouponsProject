package il.ac.hit.couponsproject.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import il.ac.hit.couponsproject.*;
import il.ac.hit.couponsproject.exception.CouponException;
import il.ac.hit.couponsproject.model.dao.ICouponsDAO;
import il.ac.hit.couponsproject.model.dao.impl.HibernateCouponsDAO;
import il.ac.hit.couponsproject.model.dto.Coupon;
import il.ac.hit.couponsproject.model.dto.ShoppingCart;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller/*")
public class Controller extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ICouponsDAO dao = new HibernateCouponsDAO();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void AdminPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminPage.jsp");
		dispatcher.forward(request, response);
	}
	
	public void RemoveFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String couponId = request.getParameter("id");
		Coupon selectedCoupon = null;
		HttpSession session = request.getSession();
		ShoppingCart cart = (ShoppingCart) (session.getAttribute("cart"));
		System.out.println(cart.toString());
		if (couponId != null) 
		{
			try
			{
				selectedCoupon = dao.getCoupon(Integer.parseInt(couponId));
				cart.removeCoupon(selectedCoupon);
			}
			catch (NumberFormatException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (CouponException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/shoppingCart.jsp");
		dispatcher.forward(request, response);
	}
	
	public void UpdateCoupons(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		boolean errorFlag = false;
		Map<String, String> errors = new HashMap<String, String>();
		List<Coupon> coupons = null;
		Coupon selectedCoupon = null;
		request.setAttribute("errors", errors);
		for (Map.Entry<String, String> entry : errors.entrySet())
		{
			entry.setValue("");
		}
		try
		{
			coupons = dao.getCoupons();
			request.setAttribute("CouponsList", coupons);
		}
		catch (CouponException e)
		{
			e.printStackTrace();
		}

		if (request.getParameter("couponSelection") != null)
		{
			try
			{
				selectedCoupon = dao.getCoupon(Integer.parseInt(request.getParameter("couponSelection")));
				if (request.getParameter("coupon-name").isEmpty() == false)
				{
					if (request.getParameter("coupon-name").length() > 1)
					{
						selectedCoupon.setName(request.getParameter("coupon-name"));
					}
					else
					{
						errors.put("couponName", "שם קופון שגוי");
						errorFlag = true;
					}
				}
				if (request.getParameter("coupon-description").isEmpty() == false)
				{
					System.out.println(request.getParameter("coupon-description"));
					if (request.getParameter("coupon-description").length() > 1)
					{
						selectedCoupon.setDescription(request.getParameter("coupon-description"));
					}
					else
					{
						errors.put("couponDescription", "תיאור קופון שגוי");
						errorFlag = true;
					}
				}
				if (request.getParameter("coupon-expiredate").length() != 0)
				{
						selectedCoupon.setExpiredate(request.getParameter("coupon-expiredate"));
				}
				if (request.getParameter("coupon-price").length() != 0)
				{
					try
					{
						Double.parseDouble(request.getParameter("coupon-price"));
						selectedCoupon.setPrice(Double.parseDouble(request.getParameter("coupon-price")));
						if (Double.parseDouble(request.getParameter("coupon-price")) <= 0)
						{
							throw new NumberFormatException("Values error");
						}
					}
					catch (NumberFormatException e)
					{
						errors.put("couponPrice", "מחיר לא תקין !");
						errorFlag = true;
					}
				}
				if (request.getParameter("coupon-discount").length() != 0)
				{
					try
					{
						Integer.parseInt(request.getParameter("coupon-discount"));
						selectedCoupon.setDiscount(Integer.parseInt(request.getParameter("coupon-discount")));
						if (Integer.parseInt(request.getParameter("coupon-discount")) <= 0)
						{
							throw new NumberFormatException("Values error");
						}
					}
					catch (NumberFormatException e)
					{
						errors.put("couponDiscount", "הנחה לא תקינה !");
						errorFlag = true;
					}
				}
				if ((request.getParameter("coupon-discount").length() != 0) && (request.getParameter("coupon-price").length() != 0))
				{
					selectedCoupon.setNewprice(Double.parseDouble(request.getParameter("coupon-price")) - (Double.parseDouble(request.getParameter("coupon-discount")) * Double.parseDouble((request.getParameter("coupon-price"))) / 100));
				}
				else if(request.getParameter("coupon-discount").length() != 0)
				{
					selectedCoupon.setNewprice(selectedCoupon.getPrice() - (selectedCoupon.getPrice() * Double.parseDouble((request.getParameter("coupon-discount"))) / 100));
				}
				else if(request.getParameter("coupon-price").length() != 0)
				{
					selectedCoupon.setNewprice(Double.parseDouble(request.getParameter("coupon-price")) - (selectedCoupon.getDiscount() * Double.parseDouble(request.getParameter("coupon-price")) / 100));
				}
				if (request.getParameter("coupon-image").isEmpty() != true)
				{
					selectedCoupon.setImage(request.getParameter("coupon-image"));
				}
				if (request.getParameter("gmaps-input-address").isEmpty() != true)
				{
					selectedCoupon.setLatitude(Double.parseDouble(request.getParameter("gmaps-output-latitude")));
					selectedCoupon.setLongitude(Double.parseDouble(request.getParameter("gmaps-output-longitude")));
				}
				if (errorFlag == false)
				{
					dao.updateCoupon(selectedCoupon);
					try
					{
						coupons = dao.getCoupons();
						request.setAttribute("CouponsList", coupons);
						request.setAttribute("couponUpdatedFlag", "הקופון עודכן בהצלחה");
						request.setAttribute("textColor", "green");
					}
					catch (CouponException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					request.setAttribute("couponUpdatedFlag", "שגיאה בעדכון הקופון");
					request.setAttribute("textColor", "red");
				}

			}
			catch (NumberFormatException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (CouponException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/updateCoupons.jsp");
		dispatcher.forward(request, response);
	}
	
	public void AddCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		boolean errorFlag = false;
		Map<String, String> errors = new HashMap<String, String>();
		String couponName = request.getParameter("coupon-name");
		if (couponName != null)
		{
			String[] splitedDate = new String[2];
			String couponDescription = request.getParameter("coupon-description");
			String couponExpireDate = request.getParameter("coupon-expiredate");
			String couponPrice = request.getParameter("coupon-price");
			String couponDiscount = request.getParameter("coupon-discount");
			String couponImage = request.getParameter("coupon-image");
			String couponLocation = request.getParameter("gmaps-input-address");
			String latitude = request.getParameter("gmaps-output-latitude");
			String longitude = request.getParameter("gmaps-output-longitude");
			String couponNewPrice = request.getParameter("coupon-newprice");
			String couponCategory = request.getParameter("coupon-category");
			request.setAttribute("couponNameBackup", couponName);
			request.setAttribute("couponDescriptionBackup", couponDescription);
			request.setAttribute("couponExpireDateBackup", couponExpireDate);
			request.setAttribute("couponPriceBackup", couponPrice);
			request.setAttribute("couponDiscountBackup", couponDiscount);
			request.setAttribute("couponImageBackup", couponImage);
			request.setAttribute("couponLocationBackup", couponLocation);
			request.setAttribute("latitudeBackup", latitude);
			request.setAttribute("longitudeBackup", longitude);
			request.setAttribute("couponNewPriceBackup", couponNewPrice);
			request.setAttribute("couponCategoryBackup", couponCategory);
			request.setAttribute("errors", errors);
			for (Map.Entry<String, String> entry : errors.entrySet())
			{
				entry.setValue("");
			}

			if (couponName.isEmpty() == true)
			{

				errors.put("couponName", "שם קופון שגוי !");
				errorFlag = true;
			}
			else
			{
				if (couponName.length() <= 1)
				{
					errors.put("couponName", "שם קופון שגוי !");
					errorFlag = true;
				}
			}
			
			if (couponCategory.isEmpty() == true)
			{

				errors.put("couponCategory", "קטגוריה שגויה !");
				errorFlag = true;
			}
			else
			{
				if (couponName.length() <= 1)
				{
					errors.put("couponCategory", "קטגוריה שגויה !");
					errorFlag = true;
				}
			}

			if (couponDescription.isEmpty() == true)
			{

				errors.put("couponDescription", "תיאור קופון שגוי !");
				errorFlag = true;
			}
			else
			{
				if (couponDescription.length() <= 1)
				{
					errors.put("couponDescription", "תיאור קופון שגוי !");
					errorFlag = true;
				}
			}

			if (couponExpireDate.length() == 0)
			{
				errors.put("couponExpireDate", "תאריך ריק !");
				errorFlag = true;
			}

			if (couponPrice.length() == 0)
			{
				errors.put("couponPrice", "מחיר ריק !");
				errorFlag = true;
			}
			else
			{
				try
				{
					Double.parseDouble(couponPrice);
					if (Double.parseDouble(couponPrice) <= 0)
					{
						throw new NumberFormatException("Values error");
					}
				}
				catch (NumberFormatException e)
				{
					errors.put("couponPrice", "מחיר לא תקין !");
					errorFlag = true;
				}
			}

			if (couponDiscount.length() == 0)
			{
				errors.put("couponDiscount", "הנחה ריקה !");
				errorFlag = true;
			}
			else
			{
				try
				{
					Double.parseDouble(couponDiscount);
					if (Double.parseDouble(couponDiscount) > 100 || Double.parseDouble(couponDiscount) <= 0)
					{
						throw new NumberFormatException("Values error");
					}
				}
				catch (NumberFormatException e)
				{
					errors.put("couponDiscount", "הנחה לא תקינה !");
					errorFlag = true;
				}
			}

			if (couponImage.isEmpty() == true)
			{
				errors.put("couponImage", "אנא הכנס תמונה");
				errorFlag = true;
			}

			if (couponLocation.isEmpty() == true)
			{
				errors.put("couponLocation", "אנא הכנס מיקום");
				errorFlag = true;
			}

			if (errorFlag == false)
			{
				Coupon newCoupon = new Coupon(couponName, couponDescription, Double.parseDouble(latitude), Double.parseDouble(longitude), couponExpireDate, Double.parseDouble(couponPrice),
						couponImage, Integer.parseInt(couponDiscount), Double.parseDouble(couponNewPrice), couponLocation, couponCategory);
				try
				{
					dao.addCoupon(newCoupon);
					request.setAttribute("couponAddedFlag", "הקופון נוסף");
					request.setAttribute("textColor", "green");
				}
				catch (CouponException e)
				{
					e.printStackTrace();
				}
			}
		}
		if(errorFlag == true)
		{
			request.setAttribute("couponAddedFlag", "שגיאה בהוספת הקופון");
			request.setAttribute("textColor", "red");
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/addCoupon.jsp");
		dispatcher.forward(request, response);
	}
	
	public void CouponCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		double latitude = Double.parseDouble(request.getParameter("latitude").toString());
		double longitude = Double.parseDouble(request.getParameter("longitude").toString());
		String category = request.getParameter("category").toString();
		try
		{
			List<Coupon> coupons = dao.getCouponsByCategoryDistance(category, latitude, longitude);
			request.setAttribute("coupons", coupons);
		}
		catch (CouponException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/couponsCat.jsp");
		dispatcher.forward(request, response);
	}
	
	public void Logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/logout.jsp");
		dispatcher.forward(request, response);
	}
	
	public void DeleteCoupons(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		List<Coupon> coupons = null;
		try
		{
			coupons = dao.getCoupons();
		}
		catch (CouponException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String couponId = request.getParameter("couponSelection");
		if (couponId != null)
		{
			try
			{
				dao.deleteCoupon(Integer.parseInt(couponId));
				request.setAttribute("couponDeletedFlag", "הקופון נמחק");
				request.setAttribute("textColor", "green");
				coupons = dao.getCoupons();
			}
			catch (CouponException e)
			{
				e.printStackTrace();
				request.setAttribute("couponDeletedFlag", "שגיאה במחיקה");
				request.setAttribute("textColor", "red");
			}
		}
		request.setAttribute("CouponsList", coupons);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/deleteCoupons.jsp");
		dispatcher.forward(request, response);
	}
	
	public void ShoppingCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/shoppingCart.jsp");
		dispatcher.forward(request, response);
	}
	
	public void AddToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Coupon coupon = null;
		String couponId = request.getParameter("id").toString();
		if (couponId != null) {
			couponId = couponId.trim();
			int id = Integer.parseInt(couponId);

			try
			{
				coupon = dao.getCoupon(id);
			}
			catch (CouponException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpSession session = request.getSession();
			if (session.getAttribute("cart") == null) {
				session.setAttribute("cart", new ShoppingCart());
			}
			ShoppingCart cart = (ShoppingCart) (session.getAttribute("cart"));
			cart.addCoupon(coupon);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/shoppingCart.jsp");
			dispatcher.forward(request, response);
			
		} else {
			// problem.. product id wasnot received
	}}

	public void GetCouponsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		double latitude = Double.parseDouble(request.getParameter("uLatitude").toString());
		double longitude = Double.parseDouble(request.getParameter("uLongitude").toString());
		List<Coupon> coupons = null;
		HashSet<String> categories = dao.getCategories();
		request.setAttribute("categories", categories);
		try
		{
			coupons = dao.getCouponsByDistance(latitude, longitude);
		}
		catch (CouponException e)
		{
			e.printStackTrace();
		}
		request.setAttribute("CouponsList", coupons);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/viewCoupons.jsp");
		dispatcher.forward(request, response);
	}
	
	public void MainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/mainPage.jsp");
		dispatcher.forward(request, response);
	}
	
	public void LoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		List<String> lines = Files.readAllLines(Paths.get(getServletContext().getRealPath("/WEB-INF/login.txt")), Charset.defaultCharset());
		byte[] bytesOfMessage;

		MessageDigest md;
		String md5UserPassword;
		String userName = lines.get(0);
		String password = lines.get(1);
		String incorrect = "";
		Cookie loggedIn;
		if(request.getParameter("submit") != null)
		{
			bytesOfMessage = request.getParameter("password").getBytes("UTF-8");
			try
			{
				
				md = MessageDigest.getInstance("MD5");
				md.update(request.getParameter("password").getBytes(),0,request.getParameter("password").length());
				md5UserPassword = (new BigInteger(1,md.digest()).toString(16));
				if((userName.equals(request.getParameter("username")) == true) && (password.equals(md5UserPassword)) == true)
				{
					loggedIn = new Cookie("loggedIn", "true");
					loggedIn.setMaxAge(300);
					response.addCookie(loggedIn);
					response.sendRedirect("/CouponsProject/Controller/admin-page");
					//RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Controller/admin-page");
					//dispatcher.forward(request, response);
				}
				else
				{
					incorrect = "Incorrect username or password";	
					request.setAttribute("incorrect", incorrect);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
					dispatcher.forward(request, response);
				}
			}
			catch (NoSuchAlgorithmException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		else
		{
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		String path = request.getPathInfo();
		if (path.contains("get-coupons"))
		{
			GetCouponsPage(request, response);
		}
		else if(path.contains("main-page"))
		{
			MainPage(request, response);
		}
		else if(path.contains("coupon-cat"))
		{
			CouponCategory(request, response);
			
		}
		else if(path.contains("logout"))
		{
			Logout(request, response);
		}
		else if(path.contains("admin-page"))
		{
			AdminPage(request, response);
		}
		else if(path.contains("login-page"))
		{
			LoginPage(request, response);
		}
		else if(path.contains("removeFromCart"))
		{
			RemoveFromCart(request, response);
		}
		else if(path.contains("addToCart"))
		{
			AddToCart(request, response);
		}
		else if(path.contains("shopping-cart"))
		{
			ShoppingCart(request, response);
		}
		else if (path.contains("update-coupons"))
		{
			UpdateCoupons(request, response);
		}
		else if (path.contains("delete-coupons"))
		{
			DeleteCoupons(request, response);
		}
		else if (path.contains("add-coupon"))
		{
			AddCoupon(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);

	}

}
