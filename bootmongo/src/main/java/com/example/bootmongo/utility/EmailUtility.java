package com.example.bootmongo.utility;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.bootmongo.exception.BootMongoException;


@Component
public class EmailUtility {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.from}")
	private String emailFrom;

	@Value("${sring.mail.title}")
	private String emailTitle;
	
	public void sendEmail(String email, String token) throws BootMongoException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		String mailUrl = "http://localhost:8080/resetPassword/";

		MimeMessageHelper helper;

		try {
			helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			helper.setTo(email);
			helper.setFrom(emailFrom);
			helper.setSubject(emailTitle + " Forgot Password" );
			String link = mailUrl + token + "/" + email;
			String emailBody = "<p>Please click on the link below to reset your password </p> " + "<a href='"
					+ link + "'>Forgot Password</a>";

			mimeMessage.setContent(emailBody, "text/html");
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new BootMongoException(201,"Failed to send email");
			//e.printStackTrace();
		}
	}
}
