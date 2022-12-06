package db.diary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/*
 데이터베이스 접속과 해제등은 디자인 영역이 아니기 때문에 만일 JFrame 상속받는 클래스내에 두면
 재사용성도 떨어지고 디자인과 로직이 뒤섞여 유지보수성도 떨어진다..앞으로는 Db관련 연동 코드는 
 무조건 물리적인 파일로 분리시켜 개발하자!!
 
 이 클래스에 역할
 1) 오라클에 접속한 후 Connection을 보유하고 있다가
 	 필요한 者가 있다면 Connection을 가져다 쓸 수 있게 제공해준다.
 	 
 2) Connection을 사용하자가 반납을 할 경우 대신 release해줌 
*/
public class DBManager {
	private static DBManager instance; // 클래스안에 고정 인스턴스 안에 따라가지 않음 /null
	String driver;
	String url;
	String user;
	String pass;
	private Connection connection;

	// 아무도 못쓴다!! 즉 아무도 new 못함
	private DBManager() {
		loadConfig();
		connect();
		//System.out.println(connection);
	}

	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	// Db연결정보 가져오기(유지보수 목적상 자바코드에 두지않고 외부 자원으로 빼놓았음)
	public void loadConfig() {
		FileInputStream fis = null;
		URL path = this.getClass().getClassLoader().getResource("res/db/db.properties");
		File file = null;
		Properties props = null;
		try {
			file = new File(path.toURI());
			fis = new FileInputStream(file);
			props = new Properties();
			props.load(fis);
			driver = props.getProperty("driver");
			url = props.getProperty("url");
			user = props.getProperty("user");
			pass = props.getProperty("pass");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 오라클에 접속한다!!
	public void connect() {
		try {
			// 드라이버 로드
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 다른 패키지이건, 같은 패키지이건, 이 메서드 호출로 외부에서
	// Connection을 가져갈 수 있도록 해주자!!
	public Connection getConnection() {
		return connection;
	}

	public void release(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// DML인 경우 사용
	public void release(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// select 인 경우
	public void release(PreparedStatement pstmt, ResultSet rs) {
	}

	public static void main(String[] args) {
		new DBManager();
	}
}
