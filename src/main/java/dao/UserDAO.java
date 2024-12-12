package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.ConnectionPool;

	public class UserDAO {
		public boolean insert(String uid, String jsonstr) throws NamingException, SQLException {
			Connection conn = ConnectionPool.get();
			PreparedStatement stmt = null;
			try {
				String sql = "INSERT INTO user(id, jsonstr) VALUES(?, ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, uid);
				stmt.setString(2, jsonstr);
				
				int count = stmt.executeUpdate();
				return (count == 1) ? true : false;
			} 
			
			finally {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			}
		}
			
		public boolean exists(String uid) throws NamingException, SQLException {
			Connection conn = ConnectionPool.get();
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				String sql = "SELECT id FROM user WHERE id = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, uid);
				rs = stmt.executeQuery();
				return rs.next();
			} 
			finally {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			}
		}
		
		public boolean delete(String uid) throws NamingException, SQLException {
			Connection conn = null;
			PreparedStatement stmt = null;
			try {
				String sql = "DELETE FROM user WHERE id = ?";
				
				conn = ConnectionPool.get();
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, uid);
				
				int count = stmt.executeUpdate();
				return (count == 1) ? true : false;
			}
			
			finally {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			}
		}
				
				
		public int login(String uid, String upass) throws NamingException, SQLException, org.json.simple.parser.ParseException {
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				String sql = "SELECT jsonstr FROM user WHERE id = ?";
				
				conn = ConnectionPool.get();
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, uid);
				
				rs = stmt.executeQuery();
				if (!rs.next()) return 1;
				
				String jsonstr = rs.getString("jsonstr");
				JSONObject obj = (JSONObject) (new JSONParser()).parse(jsonstr);
				String pass = obj.get("password").toString();
				if (!upass.equals(pass)) return 2;

				return 0;
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
				String sql = "SELECT * FROM user";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				
//				ArrayList<UserOBJ> users = new ArrayList<UserOBJ>();
//				while(rs.next()) { 
//					users.add(new UserOBJ(rs.getString("id"), rs.getString("name"), rs.getString("ts")));
//				}
				
				
				
//				JSONArray users = new JSONArray();
//				while (rs.next()) {
//					JSONObject userobj = new JSONObject();
//					userobj.put("id", rs.getString("id"));
//					userobj.put("name", rs.getString("name"));
//					userobj.put("ts", rs.getString("ts"));
//					
//					users.add(userobj);
//				}
//				return users.toJSONString();
			String str = "[";
			int cnt = 0;
			while(rs.next()) {
			    if (cnt++ > 0) str += ", ";
			    str += rs.getString("jsonstr");
			}
			return str + "]";
			
		}
		finally {
			if (rs != null ) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
}