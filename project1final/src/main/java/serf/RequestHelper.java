package serf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestHelper {
	public static void process(HttpServletRequest request, HttpServletResponse response) {
		//This method will delegate other methods on a different layer of our application
		//to process the request.
		String uri = request.getRequestURI();
		System.out.println(uri); 
		//WebService.checkLogin(request, response);
		if(uri.equals("/project1final/login.do")) {
			WebService.checkLogin(request, response);
		} else if (uri.equals("/project1final/getuser.do")) {
			WebService.getUsername(request, response);
		} else if (uri.equals("/project1final/submitrequest.do")) {
			WebService.submitRequest(request, response);
		} else if (uri.equals("/project1final/personalreq.do")){
			WebService.seePersonalReq(request, response);
		} else if (uri.equals("/project1final/subreqs.do")) {
			WebService.seeSubReq(request, response);
		} else if (uri.equals("/project1final/acceptreim.do")) {
			WebService.acceptReq(request, response);
		} else if (uri.equals("/project1final/denyreim.do")) {
			WebService.denyReq(request, response);
		} else if (uri.equals("/project1final/logout.do")) {
			WebService.logout(request, response);
		}
	}
}