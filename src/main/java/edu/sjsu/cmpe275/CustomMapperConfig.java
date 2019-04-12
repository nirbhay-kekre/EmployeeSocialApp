package edu.sjsu.cmpe275;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Component
public class CustomMapperConfig {
	@Autowired
	public CustomMapperConfig(ObjectMapper mapper, 
			 MappingJackson2XmlHttpMessageConverter xmlConverter) {
		mapper.registerModule(new Hibernate5Module());
		xmlConverter.getObjectMapper().registerModule(new Hibernate5Module());
		xmlConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
}
