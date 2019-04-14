package edu.sjsu.cmpe275;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

/**
 * Jackson ObjectMapper configuration class for json and xml
 * 
 * @author nirbhaykekre
 *
 */
@Component
public class CustomMapperConfig {
	
	/**
	 * registers Hibernate5Module in the object mapper, so that serialization of
	 * entity object won't invoke Lazy loaded entities
	 * 
	 * @param mapper       instance of json ObjectMapper
	 * @param xmlConverter instance of xml MappingJackson2XmlHttpMessageConverter
	 */
	@Autowired
	public CustomMapperConfig(ObjectMapper mapper, MappingJackson2XmlHttpMessageConverter xmlConverter) {
		mapper.registerModule(new Hibernate5Module());
		xmlConverter.getObjectMapper().registerModule(new Hibernate5Module());
		xmlConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
}
