package com.goMovie.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.goMovie.beans.MailBean;
import com.goMovie.beans.Movie;
import com.goMovie.beans.ServiceBeans;
import com.goMovie.service.Authentication;
import com.goMovie.service.Mail;
import com.goMovie.service.Reservation;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private Authentication auth;
	@Autowired
	private Reservation reservation;
	@Autowired
	private JavaMailSenderImpl springMail;
	@Autowired
	private Mail mailService;

	ModelAndView mav = null;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(@ModelAttribute Movie movie) {
		return reservation.entrance(movie);
	}

	@RequestMapping(value = "/LogInForm", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView logInForm() {
		mav = new ModelAndView();
		mav.setViewName("logInForm");
		return mav;
	}
	
	@RequestMapping(value = "/MailForm", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView MailForm() {
		mav = new ModelAndView();
		mav.setViewName("mail");
		return mav;
	}
	 
	@RequestMapping(value = "/SendMail", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView SendMail(@ModelAttribute MailBean mail) {
		mav = new ModelAndView();
//		System.out.println(mail.getMailReceiver());
//		System.out.println(mail.getMailSubject());
//		System.out.println(mail.getMailContent());
		return mailService.entrance(mail);
	}
	// ???????????? ?????? ????????????
	/*
	 * @RequestMapping(value = "/LogIn", method = {RequestMethod.GET,
	 * RequestMethod.POST}) public ModelAndView logIn(@RequestParam(name = "mId" ,
	 * required = false , defaultValue = "???????????? ????????????") String
	 * id, @RequestParam("mPwd") String pwd) { ModelAndView mav = new
	 * ModelAndView(); mav.addObject("mId",id); mav.addObject("mPwd",pwd);
	 * mav.setViewName("logInForm"); return mav; }
	 */

	// ????????? ???????????? ?????????????????????(?????????????????? ???????????? ?????? ????????? ?????? ????????? ????????? ??????????????? : mId -> mId
	@RequestMapping(value = "/LogIn", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView logIn(@ModelAttribute ServiceBeans sb) {
		return auth.entrance(sb);
	}

	@RequestMapping(value = "/toDate", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView toDate(@ModelAttribute Movie mv) {
		return reservation.entrance(mv);
		
	}

	@RequestMapping(value = "/toTime", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String toTime(@ModelAttribute Movie mv) throws UnsupportedEncodingException {
		mav = reservation.entrance(mv);
		return URLEncoder.encode(mav.getModel().get("SI").toString(), "UTF-8");
	}
	
	@RequestMapping(value = "/toSeat", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView toSeat(@ModelAttribute Movie mv) throws UnsupportedEncodingException {
		return reservation.entrance(mv);
	}

}
