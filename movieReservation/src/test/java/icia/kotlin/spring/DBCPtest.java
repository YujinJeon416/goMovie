package icia.kotlin.spring;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.goMovie.mapper.MapperInterface;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class DBCPtest {
	@Setter(onMethod_ = {@Autowired})//이미메모리에 올라와있는 녀셕중에 데이터 소스타입을 찾는다 
	private DataSource data;//DBCp에 접근할때 사용하는 녀셕
	@Setter(onMethod_ = {@Autowired})//메서드가 호출이 되면 기록을 남긴다 Log4j에 남긴다
	//기록을 남긴다 온메서드  @Autowired 에 메소드가 호출될떄 기록을 남긴다 Log4j에 남긴다
	private SqlSessionFactory sqlSession;
	@Setter(onMethod_ = {@Autowired})
	private MapperInterface mapper;
	@Test
	public void connectTest() {
		try {
			SqlSession session= sqlSession.openSession(); //위에 연결된 녀석을 통해서 오픈세션
			Connection connect = data.getConnection();
			log.info(session);//로그기록 보기
			log.info(connect);//로그기록 보기
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
