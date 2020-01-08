package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;
import service.UserService;

public class UserDAOImpl implements UserDAO {
    public static Connection conn = util.JDBCConnection.getConnection();
    
    public boolean updateAvailDeny(int u_id, double price) 
    {
    	double current = (UserService.getUser(u_id)).getRemaining();
    	price += current;
    	System.out.println("Current avail: " + price);
    	String sql = "update users set (avail) = ? where u_id = ?";
    	try {
			PreparedStatement ps = conn.prepareStatement(sql);
			System.out.println(price + " set avail to user");
			ps.setDouble(1, price);
			ps.setInt(2, u_id);
			if(ps.execute())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }
    public User getUser(int u_id) {
        try {
			String sql = "SELECT * FROM users WHERE u_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u_id);
			ResultSet rs = ps.executeQuery();		
			while(rs.next())
			{
				return new User(
						rs.getInt("u_id"), 
                        rs.getInt("depart_id"),
                        rs.getInt("depart_lvl"),
                        rs.getDouble("avail"),
                        rs.getDouble("pending"),
                        rs.getString("uname"),
                        rs.getString("pword"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
    public User getUser(String u) {
        try {
			String sql = "SELECT * FROM users WHERE uname = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u);
			ResultSet rs = ps.executeQuery();		
			while(rs.next())
			{
				return new User(
						rs.getInt("u_id"), 
                        rs.getInt("depart_id"),
                        rs.getInt("depart_lvl"),
                        rs.getDouble("avail"),
                        rs.getDouble("pending"),
                        rs.getString("uname"),
                        rs.getString("pword"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
    public boolean updateAvail(int u_id, double price) {
    	double current = (UserService.getUser(u_id)).getRemaining();
    	price = current - price;
    	String sql = "update users set (avail) = ? where u_id = ?";
    	try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, price);
			ps.setInt(2, u_id);
			if(ps.execute())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }
    public double getRemaining(User u) {
    	String sql = "SELECT * from users where u_id = ?";
    	try {
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setInt(1, u.getU_id());
    		ResultSet rs = ps.executeQuery();
    		while (rs.next())
    		{
    			return rs.getDouble("avail");
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return 0;
    }
    public double getRemaining(int u_id) {
    	String sql = "SELECT * from users where u_id = ?";
    	try {
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setInt(1, u_id);
    		ResultSet rs = ps.executeQuery();
    		while (rs.next())
    		{
    			return rs.getDouble("avail");
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return 0;
    }
    public List<User> getUsersInDept(User u) {
        String sql = "SELECT * FROM users WHERE depart_id = ?";
        List<User> userList = new ArrayList<User>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, u.getD_id());
            ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				userList.add(new User(
                    rs.getInt("u_id"), 
                    rs.getInt("depart_id"),
                    rs.getInt("depart_lvl"),
                    rs.getDouble("avail"),
                    rs.getDouble("pending"),
                    rs.getString("uname"),
                    rs.getString("pword")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
        }
        return userList;
    }
    public List<User> getAllUsers(User u) {
        String sql = "SELECT * FROM users";
        List<User> userList = new ArrayList<User>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				userList.add(new User(
                    rs.getInt("u_id"), 
                    rs.getInt("depart_id"),
                    rs.getInt("depart_lvl"),
                    rs.getDouble("avail"),
                    rs.getDouble("pending"),
                    rs.getString("uname"),
                    rs.getString("pword")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
        }
        return userList;
    }
    public List<User> getSubUsers(User u) {
        List<User> userList = getUsersInDept(u);
        List<User> subList = new ArrayList<User>();
            for(int i = 0; i < userList.size(); i++)
            {
                User x = userList.get(i);
                if (u.getD_lvl() > x.getD_lvl())
                {
                    subList.add(userList.get(i));
                }
            }
        return subList;
    }

    public int checkLogin(String user, String pword){
		String sql = "SELECT * FROM users WHERE uname = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
			{
                String pass = rs.getString("pword");
                int uid = rs.getInt("u_id");
				if (pword.equals(pass))
					return uid;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}