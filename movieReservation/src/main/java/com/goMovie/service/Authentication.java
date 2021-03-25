package com.goMovie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.ModelAndView;

import com.goMovie.beans.ServiceBeans;
import com.goMovie.mapper.MapperInterface;

@Service
public class Authentication {
	@Autowired
	private MapperInterface mapper;
	@Autowired
	private PlatformTransactionManager tran;

	public Authentication() {
	}

	public ModelAndView entrance(ServiceBeans sb) {
		ModelAndView mav = null;
		switch (sb.getSCode()) {
		case "LogIn":
			mav = logInCtl(sb);
			break;

		default:
			break;
		}
		return mav;

	}

	public ModelAndView logInCtl(ServiceBeans sb) {
		ModelAndView mav = new ModelAndView();
		//TransactionStatus status = tran.getTransaction(new DefaultTransactionDefinition());

		try {
			if (this.isMember(sb)) {
				if (this.isAccess(sb)) {
					mav.addObject("member", this.getMemberInfo(sb));
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.setViewName("logInForm");

		return mav;
	}

	private ServiceBeans getMemberInfo(ServiceBeans sb) {
		return mapper.getMemberInfo(sb);
	}

	private boolean convertToBoolean(int value) {
		return value == 1 ? true : false;

	}

	private boolean isMember(ServiceBeans sb) {
		return convertToBoolean(mapper.isMember(sb));
	}

	private boolean isAccess(ServiceBeans sb) {
		return convertToBoolean(mapper.isAccess(sb));
	}
	
}
