package com.example.demo.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {
	
	//SOAP request huleej avah servlet
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet>messageDispatcherServlet(ApplicationContext applicationContext){
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		
		return new ServletRegistrationBean<>(servlet, "/ws/*");
	}
	
	@Bean(name = "auth")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema authSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AuthPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://example.com/usersoapservice/auth");
        wsdl11Definition.setSchema(authSchema);
        return wsdl11Definition;
    } 
	
	@Bean
    public XsdSchema authSchema() {
        return new SimpleXsdSchema(new ClassPathResource("auth.xsd"));
    }
}
