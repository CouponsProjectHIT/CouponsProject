package il.ac.hit.couponsproject.model.dto;


/** 
 * Class that describe a coupon
 */
public class Coupon
{
	
    private int id, discount;
    private String name, description, expiredate, image, location, category;
    private double latitude, longitude, price, newprice;

    /** 
     * Default constructor
     */
	public Coupon()
	{
		super();
	}
    /** 
     * Constructor that create a new coupon
     * @param id, name, description, latitude, longitude, expiredate, price, image, discount, newPruce,location, category.
     */
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
	
	/** 
     * Constructor that create a new coupon without a id
     * @param name, description, latitude, longitude, expiredate, price, image, discount, newPruce,location, category.
     */
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
	
	// Getters and setters functions
	public String getCategory()
	{
		return category;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location = location;
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
	
	
	@Override
	public String toString()
	{
		return "Coupon [id=" + id + ", name=" + name + ", description=" + description + ", latitude=" + latitude + ", longitude=" + longitude + ", expiredate=" + expiredate + ", price=" + price
				+ ", image=" + image + ", discount=" + discount + ", newprice=" + newprice + ", location=" + location + ", category=" + category + "]";
	}
}