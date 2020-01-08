package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Request;
import model.User;
import service.RequestService;
import service.UserService;

public class RequestDAOImpl implements RequestDAO {
    public static Connection conn = util.JDBCConnection.getConnection();

    public boolean addRequest(Request r) {
        String sql = "CALL add_request(?,?,?,?,?,?,?,?)";
		r.setPrice(calcReimbursement(r));
		try{
			CallableStatement call = conn.prepareCall(sql);
			
			call.setInt(1, r.getU_id());
			call.setInt(2, r.getEvent_type());
			call.setDouble(3, r.getPrice());
			call.setString(4, r.getLocation());
			call.setString(5, r.getDescription());
			call.setString(6, r.getGrade_format());
			call.setString(7,  r.getGrade_pass());
			call.setInt(8, r.getApproval());
            call.execute();
            UserService.updateAvail(r.getU_id(), r.getPrice());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getApproval(int r_id) {
    	String sql = "select * from requests where r_id = ?";
    	try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, r_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				return rs.getInt("approval");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return -100;
    }
    public boolean setApproval(int r_id, int approval) {
    	String sql = "update requests set (approval) = (?) where r_id = ?";
    	try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, approval);
			ps.setInt(2, r_id);
			if(ps.execute())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }
    public double convertReim(double price, int eType) {
    	if (eType == 1) {
    		return (price * .8);
    	} else if (eType == 2) {
    		return (price * .6);
    	} else if (eType == 3) {
    		return (price * .75);
    	} else if (eType == 4) {
    		return (price);
    	} else if (eType == 5) {
    		return (price * .9);
    	} else if (eType == 6) {
    		return (price * .3);
    	}
    	return 0;
    }
    public double calcReimbursement(Request r) {
    	double avail = UserService.getRemaining(r.getU_id());
    	System.out.println(avail + "avail for " + r.getU_id());
    	double calced = convertReim(r.getPrice(), r.getEvent_type());
    	if (avail - calced > 0)
    		return calced;
    	else
    		return (calced + (avail - calced));
    }
    public List<Request> getRequests(int u_id) {
        String sql = "SELECT * FROM requests WHERE u_idr = ?";

        List<Request> reqList = new ArrayList<Request>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, u_id);
            ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Request newR = new Request(
						rs.getInt("r_id"), 
                        rs.getInt("u_idr"),
                        rs.getDouble("price"),
                        rs.getString("pgrade"),
                        rs.getInt("event_type"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getString("grade_format"),
                        rs.getInt("approval"), rs.getString("g_report"));
				reqList.add(newR);
			}
			return reqList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reqList;
    }
    public List<Request> getRequests(User u) {
    	int u_id = u.getU_id();
        String sql = "SELECT * FROM requests WHERE u_idr = ?";
        System.out.println("select OK");
        List<Request> reqList = new ArrayList<Request>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, u_id);
            ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Request newR = new Request(
						rs.getInt("r_id"), 
                        rs.getInt("u_idr"),
                        rs.getDouble("price"),
                        rs.getString("pgrade"),
                        rs.getInt("event_type"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getString("grade_format"),
                        rs.getInt("approval"), rs.getString("g_report"));
				reqList.add(newR);;
			}
			return reqList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reqList;
    }
    public List<Request> getAllRequestsBen(User u)
    {
    	System.out.println("bencoooo");
        List<Request> reqList = new ArrayList<Request>();
    	String sql = "SELECT * FROM requests WHERE approval = 2";
    	try {
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ResultSet rs = ps.executeQuery();
    		while (rs.next())
            {
            	Request newR = new Request(
						rs.getInt("r_id"), 
                        rs.getInt("u_idr"),
                        rs.getDouble("price"),
                        rs.getString("pgrade"),
                        rs.getInt("event_type"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getString("grade_format"),
                        rs.getInt("approval"), rs.getString("g_report"));
            	System.out.println("adding 1 to benco list");
				reqList.add(newR);;
            }
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
		return reqList;
    }
    public List<Request> getSubRequestsBen(User u) {
    	
        List<User> userList = UserService.getAllUsers(u);
        List<Request> reqList = new ArrayList<Request>();
        try {  
            for(int i = 0; i < userList.size(); i++)
            {
                String sql = "SELECT * FROM requests WHERE u_idr = ? AND approval = 2";
                User x = userList.get(i);
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, x.getU_id());
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                	Request newR = new Request(
    						rs.getInt("r_id"), 
                            rs.getInt("u_idr"),
                            rs.getDouble("price"),
                            rs.getString("pgrade"),
                            rs.getInt("event_type"),
                            rs.getString("location"),
                            rs.getString("description"),
                            rs.getString("grade_format"),
                            rs.getInt("approval"), rs.getString("g_report"));
    				reqList.add(newR);;
                }
            }
		} catch (SQLException e) {
			e.printStackTrace();
        }
        return reqList;
    }
    public List<Request> getSubRequestsDept(User u) {
        List<User> userList = UserService.getSubUsers(u);
        List<Request> reqList = new ArrayList<Request>();
        try {  
            for(int i = 0; i < userList.size(); i++)
            {
                String sql = "SELECT * FROM requests WHERE u_idr = ? AND approval = 1";
                User x = userList.get(i);
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, x.getU_id());
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                	Request newR = new Request(
    						rs.getInt("r_id"), 
                            rs.getInt("u_idr"),
                            rs.getDouble("price"),
                            rs.getString("pgrade"),
                            rs.getInt("event_type"),
                            rs.getString("location"),
                            rs.getString("description"),
                            rs.getString("grade_format"),
                            rs.getInt("approval"), rs.getString("g_report"));
    				reqList.add(newR);;
                }
            }
		} catch (SQLException e) {
			e.printStackTrace();
        }
        return reqList;
    }
    public List<Request> getSubRequestsSuper(User u) {
        List<User> userList = UserService.getSubUsers(u);
        List<Request> reqList = new ArrayList<Request>();
        try {  
            for(int i = 0; i < userList.size(); i++)
            {
                String sql = "SELECT * FROM requests WHERE u_idr = ? AND approval = 0";
                User x = userList.get(i);
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, x.getU_id());
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                	Request newR = new Request(
    						rs.getInt("r_id"), 
                            rs.getInt("u_idr"),
                            rs.getDouble("price"),
                            rs.getString("pgrade"),
                            rs.getInt("event_type"),
                            rs.getString("location"),
                            rs.getString("description"),
                            rs.getString("grade_format"),
                            rs.getInt("approval"), rs.getString("g_report"));
    				reqList.add(newR);;
                }
            }
		} catch (SQLException e) {
			e.printStackTrace();
        }
        
		return reqList;
    }
    public Request getRequest(int r_id) {
        String sql = "SELECT * FROM requests WHERE r_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, r_id);
            ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Request newR = new Request(
						rs.getInt("r_id"), 
                        rs.getInt("u_idr"),
                        rs.getDouble("price"),
                        rs.getString("pgrade"),
                        rs.getInt("event_type"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getString("grade_format"),
                        rs.getInt("approval"), rs.getString("g_report"));
				return(newR);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }
    public boolean acceptReq(int r_id) {
    	Request r = RequestService.getRequest(r_id);
    	int approv = r.getApproval();
    	RequestService.setApproval(r_id, (approv + 1));
    	if(approv == 2)
    	{
    		RequestService.setSuccess(r_id);
    	}
    	return true;
    }
    public boolean setSuccess(int r_id) {
    	Request r = RequestService.getRequest(r_id);
    	String sql = "UPDATE requests set description = '****REIMBURSEMENT ISSUED****' WHERE r_id = ?";
    	try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, r_id);
			return(ps.execute());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    
    public boolean denyRequest(int r_id) {
    	String sql = "UPDATE requests set approval = -1 WHERE r_id = ?";
    	Request r = RequestService.getRequest(r_id);
    	try {
			PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, r_id);
            ps.executeQuery();
            String sql1 = "Update requests set description = '****STATUS: DENIED****' where r_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1,  r_id);
            ps1.execute();
            double price = r.getPrice();
            int u_id = r.getU_id();
            System.out.println("reimbursement added back: " + price + " to U_id : " + u_id);
            UserService.updateAvailDeny(u_id, -1 * price);
            String sql2 = "Update requests set price = 0 where r_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, r_id);
            ps2.execute();
            return true;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
		return false;    
		}
}