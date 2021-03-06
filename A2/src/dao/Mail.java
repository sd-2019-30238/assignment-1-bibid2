package dao;

import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mysql.cj.PreparedQuery;
import com.sun.istack.internal.logging.Logger;

public class Mail implements Observer {

	@Override
	public void sendMail(String recepient)
	{
	  Properties properties = System.getProperties();
      
	  properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable", "true");
      properties.put("mail.smtp.host", "smtp.gmail.com");
      properties.put("mail.smtp.port", "587"); 
      
      //String to = "biancadondas21@gmail.com";
      String from = "biancadondas21@gmail.com";
      //String host = "127.0.0.1";            
      String password = "Parola";
      
      Session sesion = Session.getInstance(properties, new Authenticator() {
    	  @Override
    	  protected PasswordAuthentication getPasswordAuthentication()
    	  {
    		  return new PasswordAuthentication(from, password);
    	  }
	});
      
      Message message = prepareMessage(sesion, from, recepient);
      try {
		Transport.send(message);
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	public static Message prepareMessage(Session session, String from, String recepient) {
		
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Book Available");
			message.setText("Hello! Your book is now available and it has been asignated to you in our app!");
			return message;
		} catch (Exception e) {
			Logger.getLogger(Mail.class.getName(), null).log(Level.SEVERE,null,e);
		}
		return null;
		
	}
	

}
