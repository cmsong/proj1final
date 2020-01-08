package dao;

import java.util.List;

import model.Request;
import model.User;

public interface RequestDAO {
	public boolean setApproval(int r_id, int approval);
	public int getApproval(int r_id);
	public double calcReimbursement(Request r);
	public boolean addRequest(Request r);
	public List<Request> getRequests(User u);
	public List<Request> getRequests(int u_id);
//	public List<Request> getSubRequests(User u);
	public Request getRequest(int r_id);
	public boolean acceptReq(int r_id);
	public List<Request> getSubRequestsDept(User u);
	public List<Request> getSubRequestsSuper(User u);
	public List<Request> getSubRequestsBen(User u);
	public List<Request> getAllRequestsBen(User u);
	public boolean denyRequest(int r_id);
	public boolean setSuccess(int r_id);

}
