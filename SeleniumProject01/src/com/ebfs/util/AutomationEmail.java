package com.ebfs.util;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class AutomationEmail {
	
	public static void main(String[] args){

		String emailList[] = PropertiesUtil.getProperty("sendToTest").split(",");
		for(String email : emailList){
			System.out.println("Email: " + email);
		}
		System.out.println();
/*		System.out.println("Automation Email ");
		String tmpMsg="<tr><td>Email</td><td>PropertyEntry</td><td>BillMeTypeId</td><td>SourceKey</td><td>CampaignId</td><td>CampaignDescription</td><td>ClientIP</td><td>EntryDate</td><td>ForeignSource</td></tr><tr><td>qaswautomation_qtwxbjq@pchmail.com</td><td>Search & Win</td><td>20846</td><td>AE385</td><td>201324892085844</td><td>S&W_5KAWk_NonImm_Reg</td><td>216.255.103.250</td><td>3/11/2014 3:56:20 PM</td><td>Search & Win</td></tr>";
		System.out.println("Message: " + tmpMsg);
		ArrayList<String> arrList = new ArrayList<String>();
		arrList.add("Prod Bill Me Handler Contenst Entry Validation - Thu_Mar_20 14_04_23_41_PM.xls");
		arrList.add("Prod Bill Me Handler Contenst Entry Validation - Thu_Mar_20 14_04_23_02_PM.xls");
		
		
AutomationEmail.sendEmail("Prod Bill Me Handler Contenst Entry Validation", tmpMsg,arrList );*/

		
	}
	


	public static boolean sendEmail(String argSubject,String msgBody, ArrayList<String> argAttachmentArr ){
		boolean retStatus=false;

		final String username = "joomla.search.win@gmail.com";
		final String password = "search&Win";

		msgBody = createEmailBody(msgBody);
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("Joomla SearchWin QA <joomla.search.win@gmail.com>"));
			String emailList[] = PropertiesUtil.getProperty("sendToTest").split(",");
			for(String tmpEmail : emailList){
				message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(tmpEmail.trim()));
				System.out.println(" Add Email: " + tmpEmail);
			}
			
			message.addRecipients(Message.RecipientType.TO,InternetAddress.parse("gbhuyain@pch.com"));
			message.addRecipients(Message.RecipientType.TO,InternetAddress.parse("dreddy@pch.com"));
			message.addRecipients(Message.RecipientType.TO,InternetAddress.parse("senglezos@pch.com"));
//			message.addRecipients(Message.RecipientType.TO,InternetAddress.parse("rabbani.tanvir@gmail.com"));
			
			message.setSubject(argSubject);

			//message.setText("Details Text mesage. Email should be recieved");
		
			String Path="";// "C:/Rabbani/Studio/workspace/JoomlaSearchAndWin/ResultExcel/";
			
			
			if(!argAttachmentArr.isEmpty()){
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(msgBody);
				messageBodyPart.setContent(msgBody,"text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
			 
			    
			    for(String argAttachment: argAttachmentArr){
			    	messageBodyPart = new MimeBodyPart();
			    	Path=""; //"C:/Rabbani/Studio/workspace/JoomlaSearchAndWin/ResultExcel/";
			    	DataSource source =   new FileDataSource((Path+argAttachment));
			    	messageBodyPart.setDataHandler(new DataHandler(source));
			    	String attachedFileName=argAttachment.replace("./ResultExcel/", "");
			    	System.out.println("Attachd File Name: " + attachedFileName);
			    	messageBodyPart.setFileName(attachedFileName);
			    	multipart.addBodyPart(messageBodyPart);
			    
			    }			    
			   
				message.setContent(multipart,"text/html");
			}else{
				message.setContent(msgBody,"text/html");
			}



			Transport.send(message);
			retStatus=true;
			System.out.println("Done");

		} catch (MessagingException e) {
			System.out.println(e.toString());
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return retStatus;
	}
	
	public static String createEmailBody(String argTrStr){
		String retStr = "";
		final String DATE_FORMAT_NOW = "yyyy-MM-dd hh:mm:ss a";
	   	 Calendar cal = Calendar.getInstance();
	     SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	     String dateStr =sdf.format(cal.getTime());
	     
		String initialStr = "<style type='text/css'>"+
				"body {font: normal 12px auto Tahoma, Geneva, sans-serif;"+
				"	color: #4f6b72;"+
				"	background: #E6EAE9;"+
				"}"+
				"a {color: #c75f3e;}"+
				"table.resultTable {padding: 0;margin: 0; width:750px}"+
				"caption {padding: 0 0 5px 0;font: italic 15px 'Trebuchet MS', Verdana, Arial, Helvetica, sans-serif;text-align: right;}"+
				""+
				"table.resultTable th {"+
				"	padding: 7px 6px;"+
				"	font:bold 13px Tahoma, Geneva, sans-serif;"+
				"	color:#FFF;"+
				"	background:#000;"+
				"	border: 1px solid #999999;"+
				"}"+
				"table.resultTable td {"+
				" 	font: 13px Tahoma, Geneva, sans-serif;"+
				"	border-right: 1px solid #333;"+
				"	border-bottom: 1px solid #333;"+
				"	background: #fff;"+
				"	padding: 6px 6px 6px 12px;"+
				"	color: #000;}"+
				"table.resultTable td.pass {"+
				"font-weight:bold;"+
				"	background: #000;"+
				"	color: #00FF00;}"+
				 "table.resultTable td.fail {"+
				 "font-weight:bold;"+
				 "background: #FDD;"+
				 "color: #F00;}"+
				"</style>"+
				"<html>"+
				"<body> <br/><br/><br/>"+
				"<table class='resultTable'>";


		String endStr =	"</table>"+"</body>"+"</html>";		
		
		retStr = initialStr + 	argTrStr + 	endStr;
		return retStr;
		
		
	}
	
	public static void sendEmailViaGmail(String emailBody) {
		 
		final String username = "joomla.search.win@gmail.com";
		final String password = "search&Win";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("joomla.search.win@gmail.com"));
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse("gbhuyain@pch.com"));
			
			//message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse("rabbani.tanvir@gmail.com"));
			//message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse("gbhuyain@pch.com"));
			message.setSubject("Production Bill Me Entry Validation");
			String msgBody = createEmailBody(emailBody);
			message.setText(msgBody);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}




