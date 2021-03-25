package com.goMovie.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.ModelAndView;

import com.goMovie.beans.Movie;
import com.goMovie.beans.Seat;
import com.goMovie.mapper.ReservationIf;
import com.google.gson.Gson;

@Service
public class Reservation {
	@Autowired
	private ReservationIf mapper;
	@Autowired
	private PlatformTransactionManager tran;
	@Autowired
	private Gson gson;

	public ModelAndView entrance(Movie mv) {

		ModelAndView mav = null;

		if (mv.getMvCode() == null) {
			mav = this.movieListCtl(mv);
		} else {
			switch (mv.getSCode()) {
			case "toDate":
				mav = this.dateCtl(mv);
				break;
			case "toTime":
				mav = this.timeCtl(mv);
				break;
			case "toSeat":
				mav = this.seatCtl(mv);
				break;
			default:
				break;
			}
		}
		return mav;

	}

	private ModelAndView seatCtl(Movie mv) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("seat", gson.toJson(this.getSeat(mv)));
		mav.setViewName("seatChoice");
		return mav;
	}

	private ArrayList<Seat> getSeat(Movie mv) {
		return mapper.getSeat(mv);
	}

	private ModelAndView timeCtl(Movie mv) {
		ModelAndView mav = new ModelAndView();
		
		String jsonData = gson.toJson(this.getScreeningInfo(mv));
		mav.addObject("SI", jsonData);
		
		return mav;
	}

	private ArrayList<Movie> getScreeningInfo(Movie mv) {
		return mapper.getScreeningInfo(mv);
	}

	/* dateChoice_one.jsp로 날짜 선택했던 메서드
	private ModelAndView dateCtl(Movie mv) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("days", this.makeDateList(this.makeDate()));
		mav.addObject("sMovie", this.getMovieInfo(mv));
		mav.addObject("selling", this.ifSelling(mv, this.makeDate()));

		mav.setViewName("dateChoice");
		return mav;
	}
	*/
	
	// dateChoice.jsp -- ajax 적용해서 한 페이지에서 날짜/시간 선택하는 메서드
	private ModelAndView dateCtl(Movie mv) {
		ModelAndView mav = new ModelAndView();
		
		/* Start Date */
		mav.addObject("Access", this.getCurrentDate('d'));
		mav.addObject("days", this.makeDateList(this.makeDate()));
		
		/* 날짜에 따른 상영 여부 확인하는 데이터 추가 */
		String jsonD = gson.toJson(this.ifSelling(mv, this.makeDate()));
		mav.addObject("selling", jsonD);
		
		/* Movie Info & Convert to JSON */
		String jsonData = gson.toJson(this.getMovieList(mv));
		mav.addObject("movieData", jsonData);
		
		/* View */
		mav.setViewName("dateChoice");
		return mav;
	}
 
	// 날짜 출력 자바 서비스단에서 해본 것
	private String makeDateList(ArrayList<String> list) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			sb.append("<button value=" + list.get(i) + " name=\"days\" onclick=\"toNext(\'" + list.get(i));
			sb.append("\')\">" + list.get(i));
			sb.append("</button>");
		}
		return sb.toString();
	}

	private ArrayList<String> ifSelling(Movie mv, ArrayList<String> list) {
		ArrayList<String> dList = new ArrayList<String>();
		for(int i=0;i<mapper.ifSelling(mv).size();i++) {
			if(list.contains(mapper.ifSelling(mv).get(i).getD())){
				dList.add(mapper.ifSelling(mv).get(i).getD());
			}
		}
		return dList;
	}

	private ArrayList<String> makeDate() {
		String timedate = null;
		SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < 7; i++) {
			if (i == 0) {
				cal.add(Calendar.DATE, 0);
			} else {
				cal.add(Calendar.DATE, 1);
			}
			timedate = Format.format(cal.getTime());
			list.add(timedate);
		}
		return list;
	}

	private Movie getMovieInfo(Movie mv) {
		return mapper.getMovieInfo(mv);
	}

	private ModelAndView movieListCtl(Movie mv) {
		ModelAndView mav = new ModelAndView();
		// ArrayList<Movie> movieList = null; --> html을 자바단에서 구현 시 리스트 적용

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E요일");
		mav.addObject("Access", sdf.format(date));

		String jsonData = gson.toJson(this.getMovieList(mv));
		mav.addObject("jsonData", jsonData);

		mav.setViewName("home");
		return mav;
	}

	private ArrayList<Movie> getMovieList(Movie mv) {
		return mapper.getMovieList(mv);
	}

	// 영화 리스트 -- 빈에 담은 데이터를 json으로 넘겨받으면 아래 방법으로 html 만들지 않고 jsp 페이지에서 자바스크립트로 구현
	private String makeMovieList(ArrayList<Movie> mList) {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for (Movie movie : mList) {

			index++;

			// 3으로 나눈 값이 1이면(if 앞에 index++가 있으므로 현 시점에 index는 이미 1)
			// index가 1,4,7,... 등 3n+1일 때마다
			// div를 하나 만들고, 이 div의 style을 flex로 설정
			if (index % 3 == 1) {
				sb.append("<div style=\"display: flex\">");
			}
			sb.append("<div onClick=\"toDate(\'" + movie.getMvCode() + "\')\">");
			sb.append("<div><img src=\"resources/image/" + movie.getMvImage() + "\" /></div>");
			sb.append("<div>" + movie.getMvName() + " / " + movie.getMvGrade() + "등급</div>");
			sb.append("</div>");

			// 3으로 나눈 값이 0이면(index가 3의 배수, 즉 3,6,9,... 등 3n일 때마다 div를 닫음)
			if (index % 3 == 0) {
				sb.append("</div>");
			}

		}

		return sb.toString();
	}
	
	// 선생님 step2.jsp 적용 후  추가: ajax 구현 위한 Current DateTime 메서드
	private String getCurrentDate(char dateType) {
		Date date = new Date();
		
		SimpleDateFormat sdf = (dateType=='f')? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E요일") :
			(dateType=='d')? new SimpleDateFormat("yyyy-MM-dd"):
				(dateType=='t')? new SimpleDateFormat("HH:mm E요일") : null;
				
		return sdf.format(date);
	}
}
