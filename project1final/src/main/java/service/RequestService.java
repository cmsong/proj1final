package service;

import java.util.List;

import dao.RequestDAO;
import dao.RequestDAOImpl;
import model.Request;
import model.User;

public class RequestService {
    public static RequestDAO rd = new RequestDAOImpl();
    public static boolean setApproval(int r_id, int approval) {
    	return rd.setApproval(r_id, approval);
    }
    public static int getApproval(int r_id) {
    	return rd.getApproval(r_id);
    }
    public static boolean addRequest(Request r){
        return rd.addRequest(r);
    }
	public static List<Request> getRequests(int u_id){
        return rd.getRequests(u_id);
    }
	public static List<Request> getRequests(User u){
		return rd.getRequests(u);
	}
//	public static List<Request> getSubRequests(User u){
//        return rd.getSubRequests(u);
//    }
	public static Request getRequest(int r_id){
        return rd.getRequest(r_id);
    }
	public static boolean acceptReq(int r_id) {
		return rd.acceptReq(r_id);
	}
	public static List<Request> getSubRequestsDept(User u){
		return rd.getSubRequestsDept(u);
	}
	public static List<Request> getSubRequestsSuper(User u){
		return rd.getSubRequestsSuper(u);
	}
	public static List<Request> getSubRequestsBen(User u){
		return rd.getSubRequestsBen(u);
	}
	public static List<Request> getAllRequestsBen(User u){
		return rd.getAllRequestsBen(u);
	}
	 public static boolean denyReq(int r_id){
         return rd.denyRequest(r_id);
     }
	 public static boolean setSuccess(int r_id)
	 {
		 return rd.setSuccess(r_id);
	 }
}