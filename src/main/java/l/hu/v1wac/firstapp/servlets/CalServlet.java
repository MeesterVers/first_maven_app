package l.hu.v1wac.firstapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/DynamicServlet.do")
public class CalServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String getal_1 = req.getParameter("getal_1");
		String getal_2 = req.getParameter("getal_2");
		int number_1 = Integer.parseInt(getal_1);
		int number_2 = Integer.parseInt(getal_2);
	
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println(" <title>Simple Cal</title>");
		out.println(" <body>");
		out.println(" <h2>Antwoord:</h2>");
		
		if(req.getParameter("min") != null) {
			int min = number_1 - number_2;
			out.println(" <h2>"+ min +"</h2>");
		}
		
		if(req.getParameter("plus") != null) {
			int plus = number_1 + number_2;
			out.println(" <h2>"+ plus +"</h2>");
		}
		
		if(req.getParameter("maal") != null) {
			int maal = number_1 * number_2;
			out.println(" <h2>"+ maal +"</h2>");
		}
		
		if(req.getParameter("delen") != null) {
			int delen = number_1 / number_2;
			out.println(" <h2>"+ delen +"</h2>");
		}
		
		out.println(" </body>");
		out.println("</html>");
	}
}