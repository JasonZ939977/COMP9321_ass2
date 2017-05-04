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

import COMP9321.Assignment2.SZP.DAO.UserDAO;
import COMP9321.Assignment2.SZP.TOOL.DBUtils;


/**
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
		String user_id = null, full_name = null, email = null,fname= null,yob= null, full_address=null, cc=null;
		Integer admin_status = null, type = null;
		System.out.println("Username: " + uname + ". Password: " + password);
		Connection conn = null;
		Statement stmt = null;
		Integer i= 0;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			conn = DBUtils.getConnection();

			// STEP 4: Execute a query
			stmt = (Statement) conn.createStatement();

			String sql = "SELECT * FROM users WHERE username = '" + uname + "' and "
					+ "password='"+UserDAO.getMD5(password)+"' and"
					+ " acc_status = 1;";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			// STEP 5: Extract data from result set
			while(rs.next()){
				user_id = rs.getString("id");
				fname = rs.getString("fname");
				full_name = rs.getString("fname") + " " + rs.getString("lname");
				email = rs.getString("email");
				admin_status = rs.getInt("admin");
				type = rs.getInt("type");
				yob = rs.getString("yob");
				full_address = rs.getString("full_address");
				cc = rs.getString("cc_no");
			}
			
				//System.out.println("User Found");
				response.setContentType("text/html;charset=UTF-8");
	            response.getWriter().write("True");
	            session.setAttribute("user_name",uname);
	            session.setAttribute("user_id",user_id);
	            session.setAttribute("full_name",full_name);
	            session.setAttribute("email",email);
	            session.setAttribute("admin_status",admin_status.toString());
	            session.setAttribute("type",type.toString());
	            session.setAttribute("yob",yob);
	            session.setAttribute("fname",fname);
	            session.setAttribute("full_address",full_address);
	            session.setAttribute("cc",cc);
	            //session.setAttribute("cart", CartLogger.loadSavedCart(Integer.parseInt(user_id)));
			
			rs.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		}catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			//System.out.println("MD5 Exception");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
