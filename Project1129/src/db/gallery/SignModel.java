<<<<<<< HEAD
package db.gallery;

import java.sql.PreparedStatement;
import java.sql.SQLException;

//싸인 좌표 테이블은 Dot에 대한 CRUD를 담당할 객체
public class SignModel {
	GalleryMain galleryMain;

	public SignModel(GalleryMain galleryMain) {
		this.galleryMain = galleryMain;
	}

	public void insert(int x, int y, int gallery_id) {
		PreparedStatement pstmt = null;

		String sql = "insert into dot(dot_id,x,y,gallery_id)";
		sql += " values(seq_dot.nextval," + x + "," + y + "," + gallery_id + ")";

		try {
			pstmt = galleryMain.con.prepareStatement(sql);
			int result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			galleryMain.release(pstmt);
		}

	}
}
=======
package db.gallery;

import java.sql.PreparedStatement;
import java.sql.SQLException;

//싸인 좌표 테이블은 Dot에 대한 CRUD를 담당할 객체
public class SignModel {
	GalleryMain galleryMain;

	public SignModel(GalleryMain galleryMain) {
		this.galleryMain = galleryMain;
	}

	public void insert(int x, int y, int gallery_id) {
		PreparedStatement pstmt = null;

		String sql = "insert into dot(dot_id,x,y,gallery_id)";
		sql += " values(seq_dot.nextval," + x + "," + y + "," + gallery_id + ")";

		try {
			pstmt = galleryMain.con.prepareStatement(sql);
			int result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			galleryMain.release(pstmt);
		}

	}
}
>>>>>>> e694098889c5759c58b9353358a8554d64883dbd
