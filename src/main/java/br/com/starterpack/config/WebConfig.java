package br.com.starterpack.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private SecurityVerification securityVerification;

	@Bean
	public RequestContextListener requestContextListener(){
		return new RequestContextListener();
	} 

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
	}

	@Bean
	public FilterRegistrationBean<SecurityFilter> jwtFilter() {

		final FilterRegistrationBean<SecurityFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new SecurityFilter(this.securityVerification));
		registrationBean.addUrlPatterns("/v10/*", "/v2/*", "/v3/*");
		registrationBean.setAsyncSupported(true);
		registrationBean.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);

		return registrationBean;
	}

	public MappingJackson2HttpMessageConverter jacksonMessageConverter(){

		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		SimpleModule module = new SimpleModule();
		module.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
		module.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
		mapper.registerModule(module);

		messageConverter.setObjectMapper(mapper);

		return messageConverter;

	}

	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		
        converters.add(this.jacksonMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new FormHttpMessageConverter());
    }

}
