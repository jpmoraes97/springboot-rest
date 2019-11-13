package com.rest.springboot.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.rest.springboot.models.Lancamento;
import com.rest.springboot.models.Usuario;

@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;
	
	//@Autowired
	//private LancamentoRepository repo;

	/*
	@EventListener
	public void teste(ApplicationEvent event) {
		String template = "mail/aviso-lancamentos-vencidos";
		
		List<Lancamento> lancamentos = repo.findAll();
		
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("lancamentos", lancamentos);
		
		this.enviarEmail("moraesdeveloperweb@gmail.com",
				Arrays.asList("joaopedro_moraes97@outlook.com"), "Teste", template, variaveis);
		System.out.println("Envio realizado com sucesso!");
	}
	*/
	
	public void avisarSobreLancamentosVencidos(List<Lancamento> vencidos, List<Usuario> destinatarios) {
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("lancamentos", vencidos);
		
		List<String> emails = destinatarios.stream().map(u -> u.getEmail())
				.collect(Collectors.toList());
		
		this.enviarEmail("moraesdeveloperweb@gmail.com", emails, "Assunto", "mail/aviso-lancamentos-vencidos", variaveis);
	}
	
	public void enviarEmail(String remetente, List<String> destinatarios, 
			String assunto, String template, Map<String, Object> variaveis) {
		
		Context context = new Context(new Locale("pt", "BR"));
		
		variaveis.entrySet().forEach(e -> context.setVariable(e.getKey(), e.getValue()));
		
		String mensagem = thymeleaf.process(template, context);
		
		this.enviarEmail(remetente, destinatarios, assunto, mensagem);
	}
	
	
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
