package COMP9321.Assignment2.SZP.ROUTER;
import java.io.IOException;
import java.math.BigInteger;
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
		// git try!
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
	
	private void signup(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		// TODO Auto-generated method stub	
		String username = null, nickName = null,fname = null, lname = null,email = null,full_address = null, password = null, CC = null;
		Integer yob = null,type = null;
		username = getRequestParameter(request,"username");
		nickName = getRequestParameter(request,"nickName");
		fname = getRequestParameter(request,"fname");
		lname = getRequestParameter(request,"lname");
		email = getRequestParameter(request,"email");
		full_address = getRequestParameter(request,"full_address");
		yob = Integer.valueOf(getRequestParameter(request,"yob"));
		
		UserDao user = new UserDao();
		try {
			password = UserDao.getMD5(getRequestParameter(request,"password"));
			type = Integer.valueOf(getRequestParameter(request,"type"));
			CC = getRequestParameter(request,"CC");
			System.out.println(CC);
			if(user.insertUser(username,nickName,fname,  lname,  email,  yob,  full_address,  CC,  password,  type)){
				String body = "Hi "+ nickName + ",<br><br>Please click on the following link to complete your dblpStore Registration<br><br>";
				body += "<a href='"+"http://localhost:8080/COMP9321_Ass2/emailConfirm.jsp?accId="+username +"'> Complete Registration</a><br><br>regards,<br>dblpAdmin" ;
				String subject = " Complete your registration";
				UserDao.sendMail(email, subject  , body);
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
