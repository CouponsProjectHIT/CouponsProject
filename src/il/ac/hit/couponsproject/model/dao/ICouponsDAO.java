package il.ac.hit.couponsproject.model.dao;

import il.ac.hit.couponsproject.exception.CouponException;
import il.ac.hit.couponsproject.model.dto.Coupon;

import java.util.*;

public interface ICouponsDAO
{
    public void addCoupon(Coupon coupon) throws CouponException;
    public List<Coupon> getCoupons() throws CouponException;
    public Coupon getCoupon(int id) throws CouponException;
    public void deleteCoupon(int id) throws CouponException;
    public void updateCoupon(Coupon coupon) throws CouponException;
    public List<Coupon> getCouponsByDistance(double userLatitude, double userLongitude) throws CouponException;
    public HashSet<String> getCategories();
    public List<Coupon> getCouponsByCategoryDistance(String category, double userLatitude, double userLongitude) throws CouponException;
}