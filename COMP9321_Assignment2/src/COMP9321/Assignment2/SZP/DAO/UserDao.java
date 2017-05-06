package COMP9321.Assignment2.SZP.DAO;
/**
 * 
 */


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mysql.jdbc.Statement;

import COMP9321.Assignment2.SZP.TOOL.DBUtils;
import COMP9321.Assignment2.SZP.BEEN.User;

import java.util.*;

/**
 * @author Jasonzhuang
 *
 */
public class UserDao {
	
	public static String getMD5(String data) throws NoSuchAlgorithmException
    { 
		MessageDigest messageDigest=MessageDigest.getInstance("MD5");

        messageDigest.update(data.getBytes());
        byte[] digest=messageDigest.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(Integer.toHexString((int) (b & 0xff)));
        }
        return sb.toString();
    }
	
	public static void sendMail(String email, String userName,String nickName){
		String host = "outlook.office365.com";
		String user = "z5043424@ad.unsw.edu.au";
		String pass = "Zy425323";
		String to = email;
		String from = "z5043424@ad.unsw.edu.au";
		String messageText = "Hi "+ nickName + ",<br><br>Please click on the following link to complete your dblpStore Registration<br><br>"
							+ "<a href='"+"http://localhost:8080/COMP9321_Assignment2/emailConfirm.jsp?accId="
							+ userName+"'> Complete Registration</a><br><br>regards,<br>dblpAdmin";
		String subject = "Registration Confirmation";
		boolean sessionDebug = false;

		Properties props = System.getProperties();
		props.put("mail.host", host);
		/* props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true"); */
		/* props.put("mail.smtp.port", 26); */
		// Uncomment 5 SMTPS-related lines below and comment out 2 SMTP-related lines above and 1 below if you prefer to use SSL
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smts.auth", "true");
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.starttls.enable","true");
		/* props.put("mail.smtps.ssl.trust", host); */
		Session mailSession = Session.getDefaultInstance(props, null);
		mailSession.setDebug(sessionDebug);
		Message msg = new MimeMessage(mailSession);
		try{
			msg.setContent(messageText, "text/html; charset=utf-8");
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = {new InternetAddress(to)};
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			Transport transport = mailSession.getTransport("smtp");
			// Transport transport = mailSession.getTransport("smtps");
			transport.connect(host, user, pass);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static String searchUser(String name){
		String output = "";
		try{  
		    Connection con=DBUtils.getConnection();  
		    PreparedStatement ps=con.prepareStatement("select * from users where username like '"+name+"%' || email like '"+name+"%' || fname like '"+name+"%' || lname like '"+name+"%'");  
		    ResultSet rs=ps.executeQuery();  
		      
		    if(!rs.isBeforeFirst()) {      
		    output= null;   
		    }else{  
		    output += "<center><table width='100%' class='table table-bordered table-striped table-text-center'>";  
		    output += "<tr><th>User ID</th><th>Username</th><th>Email</th><th>Account Status</th></tr>";  
		    String button= null;
		    while(rs.next()){
		    	if(Integer.valueOf(rs.getString("acc_status")) == Integer.valueOf(1)){
		    		button = "<a href=\"#\" onclick=\"toggleUsrStatus("+rs.getString("id")+", 0);return false;\" class=\"btn btn-danger\" id='toggle_user' role=\"button\">Disable User</a>";
		    	}
		    	else{
		    		button = "<a href=\"#\" onclick=\"toggleUsrStatus("+rs.getString("id")+", 1);return false;\" class=\"btn btn-success\" id='toggle_user' role=\"button\">Enable User</a>";
		    	}
		    	output += "<tr><td>"+rs.getString("id")+"</td><td>"+rs.getString("username")+"</td><td>"+rs.getString("email")+"</td> <td>"+button+"</td></tr>";  
		    }  
		    output += "</table><center>";
		    
		    }//end of else for rs.isBeforeFirst		    
		    con.close(); 		    
		    }catch(Exception e){e.printStackTrace();}
		return output;
	}
	
	
	public static String searchUserAct(String name){
		String output = "";
		try{  
		    Connection con=DBUtils.getConnection();  
		    PreparedStatement ps=con.prepareStatement("select * from users where username like '"+name+"%' || email like '"+name+"%' || fname like '"+name+"%' || lname like '"+name+"%'");  
		    ResultSet rs=ps.executeQuery();  
		      
		    if(!rs.isBeforeFirst()) {      
		    output= null;   
		    }else{  
		    output += "<center><table width='100%' class='table table-bordered table-striped table-text-center'>";  
		    output += "<tr><th>User ID</th><th>Username</th><th>Email</th><th>Show Activity</th></tr>";  
		    String button= "";
		    while(rs.next()){
		    	button = "<a href=\"#\" onclick=\"showActivity("+rs.getString("id")+");return false;\" class=\"btn btn-danger\" id='toggle_user' role=\"button\">Fetch User Activity</a>";
		    	output += "<tr><td>"+rs.getString("id")+"</td><td>"+rs.getString("username")+"</td><td>"+rs.getString("email")+"</td> <td>"+button+"</td></tr>";  
		    }  
		    output += "</table><center>";
		    
		    }//end of else for rs.isBeforeFirst		    
		    con.close(); 		    
		    }catch(Exception e){e.printStackTrace();}
		return output;
	}
	
	public User userLogin(String uname, String password){
		Connection conn = null;
		User user = new User();
		try{
			String sql = "SELECT * FROM users WHERE username = '" + uname + "' and "
					+ "password='"+getMD5(password)+"' and"
					+ " acc_status = 1;";
		    conn=DBUtils.getConnection();  
		    PreparedStatement ps=conn.prepareStatement(sql);  
		    ResultSet rs=ps.executeQuery();  
		    System.out.println(uname);
		    while(rs.next()){
		    	user.setId(rs.getString("id"));
		    	System.out.println(user.getId());
				user.setFname(rs.getString("fname"));
				user.setLname(rs.getString("lname"));
				user.setEmail(rs.getString("email"));
				user.setAdmin(rs.getInt("admin"));
				user.setType(rs.getInt("type"));
				user.setYob(rs.getInt("yob"));
				user.setFull_address(rs.getString("full_address"));
				user.setCc_no(rs.getString("cc_no"));
				}	    
		    conn.close(); 	
		    
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		return user;
	}
	
	public boolean insertUser(User user){
		
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBUtils.getConnection();
			stmt = (Statement) conn.createStatement();

			String query = "INSERT INTO `users` (`id`, `username`, `nickname`, `fname`, "
					+ "`lname`, `email`, `yob`, `full_address`, `cc_no`, `password`, `type`, "
					+ "`acc_status`, `admin`) VALUES (NULL, '"
					+ user.getUsername() +"', '"+user.getNickname()+"', '"+user.getFname()+"', '"
					+ user.getLname()+"', '"+user.getEmail()+"', '"+user.getYob()+"', '"
					+ user.getFull_address()+"', '"+user.getCc_no()+"', '"+user.getPassword()+"', '"
					+ user.getType()+"', '1', '0');";
			String sql = query ;
			stmt.executeUpdate(sql);
			
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			return false;
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			return false;
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}// do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		
		return true;
	}

}
