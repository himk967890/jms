package com;

import javax.ejb.ActivationConfigProperty;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: MessageDriven
 */
@javax.ejb.MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "zensarqueue"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "zensarqueue")//here we can connect with the redhat and creating queue also.
public class MessageDriven implements MessageListener {

    /**
     * Default constructor. 
     */
    public MessageDriven() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
              TextMessage tt=(TextMessage)message;
              try {
				System.out.println("reading message: "+tt.getText());
			} catch (Exception e) {
                e.printStackTrace();
			}
    }

}
