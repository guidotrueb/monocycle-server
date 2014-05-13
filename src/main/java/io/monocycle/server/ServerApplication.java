package io.monocycle.server;

import io.monocycle.support.SimpleCorsFilter;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@ComponentScan
@Configuration
@EnableAutoConfiguration
public class ServerApplication {

	@Value("${mail.server.host}")
	private String host;

	@Value("${mail.server.port}")
	private String port;

	@Value("${mail.server.username}")
	private String username;

	@Value("${mail.server.password}")
	private String password;

	@Value("${mail.server.protocol}")
	private String protocol;

	@Value("${mail.smtp.auth}")
	private String auth;

	@Value("${mail.smtp.starttls.enable}")
	private String enableStarttls;

	@Value("${mail.debug}")
	private String debug;

	public static void main(String[] args) {

		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost(host);
		javaMailSender.setPort(Integer.valueOf(port));
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);

		javaMailSender.setJavaMailProperties(getMailProperties());

		return javaMailSender;
	}

	private Properties getMailProperties() {

		Properties properties = new Properties();

		properties.setProperty("mail.transport.protocol", protocol);
		properties.setProperty("mail.smtp.auth", auth);
		properties.setProperty("mail.smtp.starttls.enable", enableStarttls);
		properties.setProperty("mail.debug", debug);

		return properties;
	}

	@Bean
	public SimpleCorsFilter getCorsFilter() {
		return new SimpleCorsFilter();
	}

}