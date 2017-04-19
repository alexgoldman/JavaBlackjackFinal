package edu.pitt.finalProject;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	
	@EJB
	UserFacade uf;
	
	@Context
	HttpServletRequest request;
	
	HttpServletResponse response;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		
		int usernameExists = uf.usernameCheck(username);
		int emailExists = uf.usernameCheck(email);
		//Checks if a username matches and is thus unavaliable
		if(usernameExists==1){
			System.out.println("Username taken");
			request.setAttribute("usernameError", "Username taken");
			//request.getRequestDispatcher("/javaFinalCreateAccount.jsp").forward(request, response);
		}
		//Checks if email is already in use.
		if(emailExists==1){
			System.out.println("Email in use");
			request.setAttribute("emailError", "Email in use");
			
		}
		//Sends error messages
		if(usernameExists==1 || emailExists==1){
			request.getRequestDispatcher("/javaFinalCreateAccount.jsp").forward(request, response);
		} else {
			//Sets the parameters and runs the create account query
			System.out.println("In create account else statement.");
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String password = request.getParameter("password");
			uf.createAccount(fname, lname, email, username, password);
			
			//Creates session variables
			int credits = uf.creditQuery(username);
			int key = uf.keyQuery(username);
			HttpSession session = request.getSession(true);
			session.setAttribute("user", username);
			
			session.setAttribute("key", key);
			
			session.setAttribute("credits", credits);
			
			response.sendRedirect("javaFinalHome.jsp");
			
			session.setAttribute("activeDeck", "cards4.png");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
