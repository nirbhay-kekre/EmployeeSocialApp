package edu.sjsu.cmpe275;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Component
public class CustomMapperConfig {
	@Autowired
	public CustomMapperConfig(ObjectMapper mapper) {
		mapper.registerModule(new Hibernate5Module());
	}
}
