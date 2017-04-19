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
 * Servlet implementation class CreditServlet
 */
@WebServlet("/CreditServlet")
public class CreditServlet extends HttpServlet {
	
	@EJB
	UserFacade uf;
	
	@Context
	HttpServletRequest request;
	
	HttpServletResponse response;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//This is the servlet that responds to the ajax messages from the game and store pages.
		//Runs the update credits query and updates the session
		System.out.println("At the credit update servlet");
		int key = Integer.parseInt(request.getParameter("key1"));
		int credits = Integer.parseInt(request.getParameter("credits1"));
		uf.creditUpdate(key, credits);
		if(credits<=0){
			System.out.println("In no credits statement");
			uf.addCredits(key);
			
			HttpSession session = request.getSession(true);
			session.setAttribute("credits", 10);
		} else{
			HttpSession session = request.getSession(true);
			session.setAttribute("credits", credits);
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
