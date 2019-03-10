package com.keepcount.model.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmtpMailSender {

	@Autowired
	private JavaMailSender javaMailSender;

	public void send(String to, String subject, String body) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = null;
		try {

			helper = new MimeMessageHelper(mimeMessage, true);
			helper.setSubject(subject);
			helper.setTo(to);
			helper.setText(body, true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		javaMailSender.send(mimeMessage);

	}

}
