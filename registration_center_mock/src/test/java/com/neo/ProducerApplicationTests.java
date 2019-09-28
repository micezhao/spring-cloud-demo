package com.neo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mock.mongodb.Mongodb;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProducerApplicationTests {

	@Autowired
	private Mongodb mongodb;
	
	@Test
	public void contextLoads() {
	
	}
	
	@Test
	public void mongotest() {
		System.out.println(mongodb.checkConnection());
	}

}
