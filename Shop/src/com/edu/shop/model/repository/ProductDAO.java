package com.edu.shop.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.shop.domain.Product;
import com.edu.shop.domain.SubCategory;
import com.edu.shop.domain.TopCategory;
import com.edu.shop.util.DBManager;

public class ProductDAO {
	DBManager dbManager = DBManager.getInstanse();
	
	
	public int insert(Product product) {
		Connection con = dbManager.getConnection();
		PreparedStatement pstmt = null;
		//바인드 변수를 쓰는이유 : 튜닝 때문 (추후 추가 학습예정)
		String sql = "insert into product values(seq_product.nextval,?,?,?,?,?)";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,product.getSubcategory().getSubcategory_idx());
			pstmt.setString(2, product.getProduct_name());
			pstmt.setString(3, product.getBrand());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getFilename());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Product> list = new ArrayList();
		//"topcategory","subcategory","productName","brand","price"
		String sql = "select t.topcategory_idx as topcategory_idx, t.topcategory_name as topcategory_name, s.subcategory_idx as subcategory_idx, s.subcategory_name, "
				+ "product_idx, product_name, brand, price from product p inner join SUBCATEGORY s"
				+ " on p.subcategory_idx = s.subcategory_idx  inner join topcategory t on t.topcategory_idx = s.topcategory_idx order by product_idx asc";
		
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Product product = new Product();
				SubCategory subCategory = new SubCategory();
				product.setSubcategory(subCategory);
				TopCategory topCategory = new TopCategory();
				subCategory.setTopcategory(topCategory);
				
				topCategory.setTopcategory_idx(rs.getInt("topcategory_idx"));
				topCategory.setTopcategory_name(rs.getString("topcategory_name"));
				subCategory.setSubcategory_idx(rs.getInt("subcategory_idx"));
				subCategory.setSubcategory_name(rs.getString("subcategory_name"));
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setProduct_name(rs.getString("product_name"));
				product.setBrand(rs.getString("brand"));
				product.setPrice(rs.getInt("price"));
				
				list.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
}
