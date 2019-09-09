package com.firstjms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.jms.Queue;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.jms.*;
/**
 * Servlet implementation class GlobalServlet
 */
public class GlobalServlet extends HttpServlet {
	Connection connection;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<html><body>");
		//String option=request.getParameter("ac");

		/*   if(option.equals("register"))
      {*/
		String name=request.getParameter("name");
		try {
			Context initctx = new InitialContext();
			Queue que=(Queue)initctx.lookup("java:/zensarqueue");
			Destination dest=(Destination) que;//we are creating a queue


			QueueConnectionFactory qcf=(QueueConnectionFactory) initctx.lookup("java:/ConnectionFactory");//step 1:creating factory
			connection =qcf.createConnection();//step 2:creating connection from factory
			Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);//step 3:you want to create session,based on session we create session
			//false-synchronous and true means asynchronous

			MessageProducer producer=session.createProducer(dest);//step 4:i want to creating mssg producer based on the session,where the mssg is going we pass in messageproducer.
			//the dest is the queue where message will go.
			TextMessage message=session.createTextMessage(name);//step 5:ready to create mssg(images,text,audio),for every mssg there should be one class
			System.out.println("sending message: "+message.getText());
			producer.send(message);//step 6:ready to send the mssg using producer

			out.println("message "+message.getText()+" sent successfully");  
			out.println("to receive mssg please<a href=ReceiveServlet>click here </a>");
			out.println("</body></html>");
		}
		catch (NamingException | JMSException e) {
			System.err.println("Exception occured: "+e.toString());
		}
		finally {
			try {
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}


