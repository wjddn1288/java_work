package com.edu.shop.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.shop.domain.SubCategory;
import com.edu.shop.domain.TopCategory;
import com.edu.shop.util.DBManager;

public class SubCategoryDAO {
	DBManager dbManager = DBManager.getInstanse();
	
	
	public List selectAll(int topcategory_idx) {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		con = dbManager.getConnection();
		String sql = "select * from subcategory where topcategory_idx=? order by subcategory_idx asc";
		
		ArrayList<SubCategory> list = new ArrayList();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, topcategory_idx);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				SubCategory subCategory = new SubCategory();
				TopCategory topCategory = new TopCategory();
				subCategory.setTopcategory(topCategory);
				subCategory.setSubcategory_idx(rs.getInt("subcategory_idx"));
				//subCategory.setTopcategory_idx(rs.getInt("Topcategory_idx"));
				topCategory.setTopcategory_idx(rs.getInt("Topcategory_idx"));
				subCategory.setSubcategory_name(rs.getString("subCategory_name"));
				list.add(subCategory);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		
		return list;
	}
}
