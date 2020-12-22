package com.example.algamoney.api.mail;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;
	
//	@EventListener
//	private void teste(ApplicationReadyEvent event) {
//		this.enviarEmail("jorgeeduardojr96@gmail.com", Arrays.asList("jorgeeduardo-@live.com"), "Testando", "Olá! </br> Teste ok.");
//		System.out.println("Terminado o envio.");
//	}

	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mesangem) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

			helper.setFrom(remetente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(assunto);
			helper.setText(mesangem, true);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new RuntimeException("Problemas com o envio de e-mail.");
		}

	}

}
