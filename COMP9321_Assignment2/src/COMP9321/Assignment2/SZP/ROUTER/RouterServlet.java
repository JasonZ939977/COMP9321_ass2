package COMP9321.Assignment2.SZP.ROUTER;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import COMP9321.Assignment2.SZP.BEEN.User;
import COMP9321.Assignment2.SZP.DAO.UserDao;

/**
 * @author JasonZhuang
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
		} else if (action.equals("signup")) {
			signup(request, response, session);
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
		UserDao userDao = new UserDao();
		User user = userDao.userLogin(uname, password);
		if(!user.getId().isEmpty()){
			response.setContentType("text/html;charset=UTF-8");
		    response.getWriter().write("True");
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
	
	private void signup(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		// TODO Auto-generated method stub	
		User user = new User();
		UserDao userDao = new UserDao();
		user.setUsername(getRequestParameter(request,"reg_username"));
		user.setNickname(getRequestParameter(request,"reg_nickName"));
		user.setFname(getRequestParameter(request,"reg_fname"));
		user.setLname(getRequestParameter(request,"reg_lname"));
		user.setEmail(getRequestParameter(request,"reg_email"));
		user.setFull_address(getRequestParameter(request,"reg_address"));
		user.setYob(Integer.valueOf(getRequestParameter(request,"reg_yob")));
		try {
			user.setPassword(UserDao.getMD5(getRequestParameter(request,"reg_password")));
			user.setType(Integer.valueOf(getRequestParameter(request,"reg_type")));
			user.setCc_no(getRequestParameter(request,"reg_CC"));
			if(userDao.insertUser(user)){
				UserDao.sendMail(user.getEmail(), user.getUsername(), user.getNickname());
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("True");
			}else{
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("False");
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
