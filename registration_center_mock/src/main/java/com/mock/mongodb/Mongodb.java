package com.mock.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class Mongodb {
	
	@Autowired
	private MongoTemplate template;
	
	public boolean checkConnection() {
		
		return template.collectionExists(Orders.class);
	}
	
}

