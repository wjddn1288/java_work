package com.edu.shop.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.shop.domain.TopCategory;
import com.edu.shop.util.DBManager;

//이 클래스는 오직 TopCategory 테이블에 대해서
//CRUD만을 수행하는 목적으로 정의된 것임
//select문을 AdminMain에서 작성해도 프로그램은 수행될 수 있으나
//개발 방법적인 측명에서 본다면, 유지보수성에 좋지 않다
//DAO로 별도로 정의해 놓으면, admin뿐 아니라 고객측 앱에서도 이용이 가능하다 (재사용==시간save==돈)
public class TopCategoryDAO {
	DBManager dbManager=DBManager.getInstance(); //Singleton 패턴
	
	//모두 가져오기
	public List selectAll() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<TopCategory> list=new ArrayList<TopCategory>();
		
		con=dbManager.getConnection();
		
		String sql="select * from topcategory order by topcategory_idx asc";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();	
			
			while(rs.next()) { //레코드가 있는 만큼
				TopCategory topCategory=new TopCategory();
				topCategory.setTopcategory_idx(rs.getInt("topcategory_idx"));
				topCategory.setTopcategory_name(rs.getString("topcategory_name"));
				list.add(topCategory); //DTO 추가!!	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		return list; //rs를 대신할 list를 반환한다
	} 
	
	//카테고리 이름으로 pk 반환하기
	public int getTopCategoryIdx(String topcategory_name) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int topcategory_idx=0; //pk값
		
		String sql="select topcategory_idx from topcategory";
		sql+=" where topcategory_name=?"; //상의, 하의
		
		con=dbManager.getConnection();
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, topcategory_name); 
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) { //레코드가 존재한다면
				topcategory_idx= rs.getInt("topcategory_idx");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		
		return topcategory_idx;
	}
}
