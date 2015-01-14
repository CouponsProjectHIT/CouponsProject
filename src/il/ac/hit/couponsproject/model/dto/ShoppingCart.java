package il.ac.hit.couponsproject.model.dto;

import il.ac.hit.couponsproject.exception.CouponException;
import il.ac.hit.couponsproject.model.dao.ICouponsDAO;
import il.ac.hit.couponsproject.model.dao.impl.HibernateCouponsDAO;

import java.util.*;

public class ShoppingCart
{
	Map lines = new Hashtable();
	int size = 0;
	
	public void addCoupon(Coupon coupon)
	{
		if(lines.get(coupon)==null)
		{
			lines.put(coupon.getId(), new ShoppingCartLine(coupon));
			size++;
		}
		else
		{
			ShoppingCartLine line = (ShoppingCartLine)(lines.get(coupon));
		}
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void removeCoupon(Coupon coupon)
	{
		lines.remove(coupon.getId());
		size--;
	}
	
	public double getTotal()
	{
		double total = 0;
		Iterator iterator = lines.values().iterator();
		while(iterator.hasNext())
		{
			ShoppingCartLine line = (ShoppingCartLine)iterator.next();
		}
		return total;
	}
	
	public String getXMLTable()
	{
		StringBuffer sb = new StringBuffer();
		Coupon currentCoupon = null;
		sb.append("<table class='table table-bordered table-striped' border='fixed'><thead><tr><th>תמונה</th><th>קוד קופון</th><th>שם קופון</th><th>תיאור</th><th>מחיר לפני הנחה</th><th>אחוז הנחה</th><th>מחיר חדש</th><th>תאריך תפוגה</th><th>מיקום</th><th>קטגוריה</th><th>הסר מהעגלה</th></tr></thead><tbody>");
		Iterator iterator = lines.values().iterator();
		while(iterator.hasNext())
		{
			ShoppingCartLine line = (ShoppingCartLine)iterator.next();
			currentCoupon = line.getCoupon();
			sb.append("<td><img src="+currentCoupon.getImage()+" height=50 width=50 /></td>");
			sb.append("<td>"+currentCoupon.getId()+"</td>");
			sb.append("<td>"+currentCoupon.getName()+"</td>");
			sb.append("<td>"+currentCoupon.getDescription()+"</td>");
			sb.append("<td>"+currentCoupon.getPrice()+"</td>");
			sb.append("<td>"+currentCoupon.getDiscount()+"</td>");
			sb.append("<td>"+currentCoupon.getNewprice()+"</td>");
/*			sb.append("<td>"+currentCoupon.getLongitude()+"</td>");
			sb.append("<td>"+currentCoupon.getLatitude()+"</td>");*/
			sb.append("<td>"+currentCoupon.getExpiredate()+"</td>");
			sb.append("<td>"+currentCoupon.getLocation()+"</td>");
			sb.append("<td>"+currentCoupon.getCategory()+"</td>");
			sb.append("<td><a href=removeFromCart?id=" + currentCoupon.getId() +" class='btn btn-primary' href='delete-coupons' role='button'>הסר</a></td></tr>");
		}
		sb.append("</tbody></table>");
		return sb.toString();
	}
}