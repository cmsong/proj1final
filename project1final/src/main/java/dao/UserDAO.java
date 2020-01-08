package dao;

import java.util.List;

import model.User;

public interface UserDAO {
	public boolean updateAvail(int u_id, double price);
	public boolean updateAvailDeny(int u_id, double price);
	public List<User> getAllUsers(User u);
    public User getUser(int u_id);
    public User getUser(String u);
    public double getRemaining(User u);
    public double getRemaining(int u_id);
    public List<User> getUsersInDept(User u);
    public List<User> getSubUsers(User u);
    public int checkLogin(String user, String pword); //returns u_id or 0 
}
