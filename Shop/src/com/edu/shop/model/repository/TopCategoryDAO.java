package com.edu.shop.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.shop.domain.TopCategory;
import com.edu.shop.util.DBManager;

public class TopCategoryDAO {
	DBManager dbManager = DBManager.getInstanse();
	
	
	
	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		con = dbManager.getConnection();
		String sql = "select * from topcategory order by topcategory_idx asc";
		
		ArrayList<TopCategory> list = new ArrayList();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				TopCategory topCategory = new TopCategory();
				topCategory.setTopcategory_idx(rs.getInt("topcategory_idx"));
				topCategory.setTopcategory_name(rs.getString("topcategory_name"));
				list.add(topCategory);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		
		return list;
	}
	
	public int getTopCategoryIdx(String topcategory_name) {
		Connection con  = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select topcategory_idx from topcategory where topcategory_name=?";
		int topcategory_idx = 0;
		con = dbManager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, topcategory_name);
			rs = pstmt.executeQuery();
			if(rs.next()) {				
				topcategory_idx = rs.getInt("topcategory_idx");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		
		
		return topcategory_idx;
	}
	
	
}
