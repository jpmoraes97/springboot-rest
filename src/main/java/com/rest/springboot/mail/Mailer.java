package com.rest.springboot.mail;

//import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEvent;
//import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender mailSender;
	
//	@EventListener
//	public void teste(ApplicationEvent event) {
//		this.enviarEmail("moraesdeveloperweb@gmail.com",
//				Arrays.asList("joaopedro_moraes97@outlook.com"), "Teste", "Ola!<br  /> Teste OK");
//		System.out.println("Envio realizado com sucesso!");
//	}
	
	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String msg) {
		MimeMessage mime = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mime, "UTF-8");
		try {
			
		helper.setFrom(remetente);
		helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
		helper.setSubject(assunto);
		helper.setText(msg, true);
		mailSender.send(mime);
		
		}catch(MessagingException ex) {
			ex.printStackTrace();
		}
		
	}

}
