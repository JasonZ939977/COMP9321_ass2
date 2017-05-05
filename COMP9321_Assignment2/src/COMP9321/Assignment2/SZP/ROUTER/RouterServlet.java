package COMP9321.Assignment2.SZP.ROUTER;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mysql.jdbc.Statement;

import COMP9321.Assignment2.SZP.BEEN.User;
import COMP9321.Assignment2.SZP.DAO.UserDAO;
import COMP9321.Assignment2.SZP.TOOL.DBUtils;

/**
 * @author Jason
 * Servlet implementation class RouterServlet
 */

@MultipartConfig
public class RouterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public RouterServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession();
		System.out.println("Hello from GET method");
//		build_index_page(request, response, session);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		
		if (action.equals("login")) {
			login(request, response, session);
		} 
	}
	
	private String getRequestParameter(HttpServletRequest request,
			String parameter) {
		String value = request.getParameter(parameter);

		if (value != null) {
			return value.trim();
		} else {
			return new String();
		}
	}
	
	private void login(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		String uname = getRequestParameter(request, "user");
		String password = getRequestParameter(request, "password");
		UserDAO userDao = new UserDAO();
		User user = userDao.userLogin(uname, password);
		if(!user.getId().isEmpty()){
			response.setContentType("text/html;charset=UTF-8");
		    response.getWriter().write("True");
		    System.out.println("User Found");
		    session.setAttribute("user_name",uname);
		    session.setAttribute("user_id",user.getId());
		    session.setAttribute("full_name",user.getFname() + user.getLname());
		    session.setAttribute("email",user.getEmail());
		    session.setAttribute("admin_status",user.getAcc_status());
		    session.setAttribute("type",user.getType());
		    session.setAttribute("yob",user.getYob());
		    session.setAttribute("fname",user.getFname());
		    session.setAttribute("full_address",user.getFull_address());
		    session.setAttribute("cc",user.getCc_no());
		    //session.setAttribute("cart", CartLogger.loadSavedCart(Integer.parseInt(user_id)));
		}else{
			System.out.println("not found");
		}
	}
}
