package serf;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.Request;
import model.User;
import service.RequestService;
import service.UserService;

public class WebService {
	public static Connection conn = util.JDBCConnection.getConnection();
	
	public static void checkLogin(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("checking login");
		String u = request.getParameter("username");
		String p = request.getParameter("password");
		System.out.println(u + p);
		if (UserService.checkLogin(u, p) > 0)
		{
			System.out.println("authenticated login");
			HttpSession session = request.getSession();
			session.setAttribute("username", request.getParameter("username"));
			session.setAttribute("department", UserService.getUser(u).getD_name());
			session.setAttribute("dlvl", UserService.getUser(u).getD_lvl());
			session.setAttribute("user", UserService.getUser(u));
			System.out.println("1 " + session.getId());
			try {
				response.sendRedirect(request.getContextPath() + "/home.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("session user: " + session.getAttribute("username"));
		}
	}
	
	public static void getUsername(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String s = (String)session.getAttribute("username");
		User u = UserService.getUser(s);
		String dep = u.getD_name();
		String depLvl = u.getDLvlName(u.getD_lvl());
		String title = s + ", " + depLvl + " of the " + dep + " department!";
		System.out.println(title);
		try {
			response.getWriter().append(title);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void submitRequest(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String s = (String)session.getAttribute("username");
		User u = UserService.getUser(s);
		int approv = u.getD_lvl() - 1;
		int u_id = u.getU_id();
		System.out.println("submitting request for " + s + "at approv lvl " + approv);
		Double price = Double.parseDouble(request.getParameter("price"));
		int event_type = Integer.parseInt(request.getParameter("eventtype"));
		String loc = request.getParameter("loc");
		String desc = request.getParameter("desc");
		String gformat = request.getParameter("gformat");
		String gpass = request.getParameter("pgrade");
		Request r = new Request(1, u_id, price, gpass, event_type, loc, desc, gformat, approv, "");
		RequestService.addRequest(r);
	}
	public static void seePersonalReq(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String s = (String)session.getAttribute("username");
		User u = UserService.getUser(s);
		System.out.println(u.toString());
		List<Request> reqlist = RequestService.getRequests(u);
		Gson g = new Gson();
		String reqstring = g.toJson(reqlist);
		System.out.println(reqstring);
		try {
			response.getWriter().append(reqstring);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    try {
	        response.sendRedirect(request.getContextPath() + "/index.html");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public static void seeSubReq(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String s = (String)session.getAttribute("username");
		User u = UserService.getUser(s);
		List<Request> sr = new ArrayList<Request>();
		if(u.getD_id() == 1)
			sr = RequestService.getAllRequestsBen(u);
		else {
			if(u.getD_lvl() == 2)
				sr = RequestService.getSubRequestsSuper(u);
			else if (u.getD_lvl() == 3)
				sr = RequestService.getSubRequestsDept(u);
		}
		Gson g = new Gson();
		String rstring = g.toJson(sr);
		System.out.println(rstring);
		try {
			response.getWriter().append(rstring);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void acceptReq(HttpServletRequest request, HttpServletResponse response) {
		int r_id = Integer.parseInt(request.getParameter("rid"));
		System.out.println("approving r_id: " + r_id);
		RequestService.acceptReq(r_id);
	}
	public static void denyReq(HttpServletRequest request, HttpServletResponse response) {
		int r_id = Integer.parseInt(request.getParameter("rid"));
		System.out.println("deleting " + r_id);
		RequestService.denyReq(r_id);
	}
	
}
