package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import util.ConnectionPool;

public class FeedDAO {
	public boolean insert(String jsonstr) throws NamingException, SQLException, ParseException, org.json.simple.parser.ParseException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
	
		
		try {
			synchronized(this) {
				String sql = "SELECT no FROM feed ORDER BY no DESC LIMIT 1";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				
				int max = (!rs.next()) ? 0 : rs.getInt("no");
				
				JSONObject jsonobj = (JSONObject)(new JSONParser()).parse(jsonstr);
				jsonobj.put("no", max + 1);
				stmt.close();
	
				sql = "INSERT INTO feed(no, id, jsonstr) VALUES(?, ?, ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, max + 1);
				stmt.setString(2, jsonobj.get("id").toString());
				stmt.setString(3, jsonstr); 
				 
				int count = stmt.executeUpdate();
				return ( count == 1 ) ? true : false;
			}
		}
		 
		finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();

		}
	}
	 
	public String getList() throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;  
		
		try {
			String sql = "SELECT * FROM feed ORDER BY no DESC";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			
			String str = "[";
			int cnt = 0;
			while (rs.next()) {
			    if (cnt++ > 0) str += ", ";
			    str += rs.getString("jsonstr");
			}
			return str + "]";
			
		}
		
		finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}	
	
	}
}
