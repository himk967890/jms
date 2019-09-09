package com.firstjms.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReceiveServlet
 */
public class ReceiveServlet extends HttpServlet {
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	      PrintWriter out=response.getWriter();
	      out.println("<html><body>");
	      Connection connection;
			try {
				Context initctx = new InitialContext();
	    	  Queue que=(Queue)initctx.lookup("java:/zensarqueue");
	    	  Destination dest=(Destination) que;
	    	  
	    	  QueueConnectionFactory qcf=(QueueConnectionFactory) initctx.lookup("java:/ConnectionFactory");//step 1:creating factory
	    	  connection =qcf.createConnection();//step 2:creating connection from factory
	    	  Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);//step 3:you want to create session,based on session we create session
	    	  //false-synchronous and true means asynchronous
	    	  
	    	  MessageConsumer consumer=session.createConsumer(dest);//step 4:i want to creating mssg producer based on the session
	    	  //the dest is the queue where message will go.
	    	 connection.start();
                 while(true)	    	 
                 {
                	 Message m=consumer.receive(1);
                	 if(m!=null)
                	 {
                		 if(m instanceof TextMessage)
                		 {
                			 TextMessage message=(TextMessage) m;
                			 out.println("reading mssg: "+message.getText());
                		 }
                		 else
                			 break;
                	 }
                 }
	    	out.println("to send mssg please <a href=first.html>click here </a>");
	    	out.println("</body></html>");
			}
			catch (Exception e) {
				System.err.println("Exception occured: "+e.toString());
			
	      }
		
		
		}
	
	}


