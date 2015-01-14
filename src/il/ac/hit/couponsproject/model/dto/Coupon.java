package il.ac.hit.couponsproject.model.dto;

public class Coupon
{
    private int id;
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private String expiredate;
    private double price;
    private String image;
    private int discount;
    private double newprice;
    private String location;
    private String category;
    
	public Coupon(int id, String name, String description, double latitude, double longitude, String expiredate, double price, String image, int discount, double newprice, String location,
			String category)
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.expiredate = expiredate;
		this.price = price;
		this.image = image;
		this.discount = discount;
		this.newprice = newprice;
		this.location = location;
		this.category = category;
	}
	public Coupon(String name, String description, double latitude, double longitude, String expiredate, double price, String image, int discount, double newprice, String location,
			String category)
	{
		super();
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.expiredate = expiredate;
		this.price = price;
		this.image = image;
		this.discount = discount;
		this.newprice = newprice;
		this.location = location;
		this.category = category;
	}
	public String getCategory()
	{
		return category;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}
	public Coupon(int id, String name, String description, double latitude, double longitude, String expiredate, double price, String image, int discount, double newprice, String location)
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.expiredate = expiredate;
		this.price = price;
		this.image = image;
		this.discount = discount;
		this.newprice = newprice;
		this.location = location;
	}
	public Coupon(String name, String description, double latitude, double longitude, String expiredate, double price, String image, int discount, double newprice, String location)
	{
		super();
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.expiredate = expiredate;
		this.price = price;
		this.image = image;
		this.discount = discount;
		this.newprice = newprice;
		this.location = location;
	}
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location = location;
	}
	public Coupon()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public Coupon(int id, String name, String description, double latitude,
			double longitude, String expiredate, double price, String image,
			int discount, double newprice)
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.expiredate = expiredate;
		this.price = price;
		this.image = image;
		this.discount = discount;
		this.newprice = newprice;
	}
	public Coupon(String name, String description, double latitude,
			double longitude, String expiredate, double price, String image,
			int discount, double newprice)
	{
		super();
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.expiredate = expiredate;
		this.price = price;
		this.image = image;
		this.discount = discount;
		this.newprice = newprice;
	}
	@Override
	public String toString()
	{
		return "Coupon [id=" + id + ", name=" + name + ", description=" + description + ", latitude=" + latitude + ", longitude=" + longitude + ", expiredate=" + expiredate + ", price=" + price
				+ ", image=" + image + ", discount=" + discount + ", newprice=" + newprice + ", location=" + location + ", category=" + category + "]";
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public double getLatitude()
	{
		return latitude;
	}
	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}
	public double getLongitude()
	{
		return longitude;
	}
	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}
	public String getExpiredate()
	{
		return expiredate;
	}
	public void setExpiredate(String expiredate)
	{
		this.expiredate = expiredate;
	}
	public double getPrice()
	{
		return price;
	}
	public void setPrice(double price)
	{
		this.price = price;
	}
	public String getImage()
	{
		return image;
	}
	public void setImage(String image)
	{
		this.image = image;
	}
	public int getDiscount()
	{
		return discount;
	}
	public void setDiscount(int discount)
	{
		this.discount = discount;
	}
	public double getNewprice()
	{
		return newprice;
	}
	public void setNewprice(double newprice)
	{
		this.newprice = newprice;
	}
}