package service;

import java.util.List;


import dao.UserDAO;
import dao.UserDAOImpl;
import model.User;

public class UserService {
    public static UserDAO ud = new UserDAOImpl();
    public static boolean updateAvail(int u_id, double price) {
    	return ud.updateAvail(u_id, price);
    }
    public static User getUser(int u_id){
        return ud.getUser(u_id);
    }
    public static User getUser(String u){
        return ud.getUser(u);
    }
    public static double getRemaining(User u) {
    	return ud.getRemaining(u);
    }
    public static double getRemaining(int u_id) {
    	return ud.getRemaining(u_id);
    }
    public static List<User> getUsersInDept(User u){
        return ud.getUsersInDept(u);
    }
    public static List<User> getSubUsers(User u){
        return ud.getSubUsers(u);
    }
    public static int checkLogin(String user, String pword) {
    	return ud.checkLogin(user, pword);
    }
    public static boolean updateAvailDeny(int u_id, double price) {
    	return ud.updateAvail(u_id, price);
    }
    public static List<User> getAllUsers(User u) {
    	return ud.getAllUsers(u);
    }
}