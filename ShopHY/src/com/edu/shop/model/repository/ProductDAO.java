package com.edu.shop.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.shop.domain.Product;
import com.edu.shop.domain.SubCategory;
import com.edu.shop.util.DBManager;

//Product 테이블에 대한 CRUD를 수행할 객체
public class ProductDAO {
	DBManager dbManager = DBManager.getInstance();

	public int insert(Product product) {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = dbManager.getConnection();

		String sql = "insert into product(product_idx,subcategory_idx,product_name,brand,price,filename)"; // 바인드 변수는
																											// 튜닝때문에
																											// 쓰는거다!!
		sql += " valuese(seq_product.nextval,?,?,?,?)";
		// sql+="
		// values(seq_product.nextval,"+subcategory_idx+",'"+product_name+"','"+brand+"',"+price+",'"+filename+"')";
		int result = 0;

		try {
			pstmt = con.prepareStatement(sql);
			// 쿼리 수행전 바인드변수값을 결정짓자!!
			pstmt.setInt(1, product.getSubcategory().getSubcategory_idx());
			pstmt.setString(2, product.getProduct_name());
			pstmt.setString(3, product.getBrand());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getFilename());

			result = pstmt.executeUpdate(); // DML 수행!!
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		System.out.println(sql);

		return result;
	}

	// 하위 카테고리와 조인한 모든 상품 가져오기
	public List selectAll() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<Product> list=new ArrayList<Product>();
		
		con=dbManager.getConnection();
		
		StringBuffer sb=new StringBuffer();
		sb.append("select s.subcategory_idx as subcategory_idx , subcategory_name, product_idx, product_name, brand, price, filename");
		sb.append(" from subcategory s , product p where s.subcategory_idx = p.subcategory_idx; ");
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				Product product= new Product();
				SubCategory subCategory=new SubCategory();
				//subCategory를 product 넣어주자
				product.setSubcategory(subCategory);
				
				subCategory.setSubcategory_idx(rs.getInt(""));
				subCategory.setSubcategory_name(rs.getString("subcategory_name"));
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setProduct_name(rs.getString("product_name"));
				product.setBrand(rs.getString("brand"));
				product.setPrice(rs.getInt("price"));
				product.setBrand(rs.getString("filename"));
				
				//이미 Product가 가진 SubCategory DTO이므로 Product만 담으면 된다!! (has a 관게다!!)
				list.add(product);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt,rs);
		}
		
		return null;
	}
}
