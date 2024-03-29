package com.example.algamoney.api.mail;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Mailer {

	private final JavaMailSender mailSender;
	private final TemplateEngine thymeleaf;

//	@EventListener
//	private void teste(ApplicationReadyEvent event) {
//		this.enviarEmail("jorgeeduardojr96@gmail.com", Arrays.asList("jorgeeduardo-@live.com"), "Testando", "Olá! </br> Teste ok.");
//		System.out.println("Terminado o envio.");
//	}

//	@EventListener
//	private void teste(ApplicationReadyEvent event) {
//		String template = "mail/aviso-lancamentos-vencidos";
//		
//		List<Lancamento> lista = this.repo.findAll();
//		
//		Map<String, Object> variaveis = new HashMap<>();
//		variaveis.put("lancamentos", lista);
//		
//		this.enviarEmail("jorgeeduardojr96@gmail.com", Arrays.asList("jorgeeduardo-@live.com"), "Testando", template, variaveis);
//		System.out.println("Terminado o envio.");
//	}

	public void avisarSobreLancamentosVencidos(List<Lancamento> vencidos, List<Usuario> destinatarios) {
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("lancamentos", vencidos);

		List<String> emails = destinatarios.stream().map(Usuario::getEmail).collect(Collectors.toList());

		this.enviarEmail("jorgeeduardojr96@gmail.com", emails, "Lançamentos Vencidos",
				"mail/aviso-lancamentos-vencidos", variaveis);
		System.out.println("Terminado o envio.");
	}

	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String template,
			Map<String, Object> variaveis) {
		Context context = new Context(new Locale("pt", "BR"));

		variaveis.forEach(context::setVariable);

		String mensagem = thymeleaf.process(template, context);
		this.enviarEmail(remetente, destinatarios, assunto, mensagem);
	}

	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mensagem) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

			helper.setFrom(remetente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(assunto);
			helper.setText(mensagem, true);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new RuntimeException("Problemas com o envio de e-mail.");
		}

	}

}
