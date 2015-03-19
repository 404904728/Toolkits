package sendemail;


import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * 
 *      
 *     
 * @author btp       
 * @version 1.0     
 * @created 2013-9-17 上午9:49:49
 */
public class EmailUtil  implements Serializable{

	/**  描述  */      
	
	private static final long serialVersionUID = 112087824493020395L;

	public static boolean sendEmail(String email, String title, String msg) throws Exception{
		String emailServer = "smtp.163.com";
		String emailName  = "cesspoolsystem_qiy@163.com";
		String emailPssword = "lantu_yunshidai";
		String emailPort = "25";
		boolean flag = true;
		// 建立邮件会话
		Properties pro = new Properties();
		pro.put("mail.smtp.host",emailServer);// 存储发送邮件的服务器
		pro.put("mail.smtp.port",emailPort);
		pro.put("mail.transport.protocol","smtp");
		pro.put("mail.smtp.auth", "true"); // 通过服务器验证

		Session s = Session.getInstance(pro); // 根据属性新建一个邮件会话
		//s.setDebug(true);

		// 由邮件会话新建一个消息对象
		MimeMessage message = new MimeMessage(s);

		// 设置邮件
		InternetAddress fromAddr = null;
		InternetAddress toAddr = null;

		fromAddr = new InternetAddress(emailName); // 邮件发送人地址
		message.setFrom(fromAddr); // 设置发送地址

		toAddr = new InternetAddress(email); // 邮件接收人地址
		message.setRecipient(Message.RecipientType.TO, toAddr); // 设置接收人地址

		message.setSubject(title); // 设置邮件标题
		message.setContent(msg, "text/html;charset = UTF-8");
		message.setSentDate(new Date()); // 设置邮件日期

		message.saveChanges(); // 保存邮件更改信息

		Transport transport = s.getTransport("smtp");
		transport.connect(emailServer,emailName,emailPssword); // 发件人服务器地址，邮箱账号，邮箱密码
		transport.sendMessage(message, message.getAllRecipients()); // 发送邮件
		System.out.println("邮件发送成功");
		transport.close();// 关闭
			
		return flag;
	}

	public static void main(String[] args) throws Exception {
		sendEmail("404904728@qq.com","报警通知", "baojing");
	}
}
