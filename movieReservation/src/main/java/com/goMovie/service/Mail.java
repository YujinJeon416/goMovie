package com.goMovie.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.goMovie.beans.MailBean;

@Service
public class Mail {
	@Autowired
	private JavaMailSenderImpl sendMail;

	ModelAndView mav;

	public Mail() {
	}

	public ModelAndView entrance(MailBean sendMail) {
		mav = this.sendHTMLMail(sendMail);
		return mav;
	}

	private ModelAndView sendHTMLMail(MailBean sendMail) {
		mav = new ModelAndView();
		try {
			MimeMessage mailMessage = this.sendMail.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
			helper.setTo(sendMail.getMailReceiver());
			helper.setSubject(sendMail.getMailSubject());
			helper.setText(sendMail.getMailContent());
			helper.setFrom("dhgksscjf@naver.com");
			
			this.sendMail.send(mailMessage);
			
			mav.addObject("receiver", sendMail.getMailReceiver());
			mav.setViewName("sendResult");
		} catch (MessagingException e) {
			e.printStackTrace();
			mav.setViewName("mail");
		}
		return mav;
	}

}