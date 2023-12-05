package com.pedro.util;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnviaEmail {

	public static void send(File pathArqZip, String destino) {

		// Configurações do servidor de email
		String username = "";
		String password = "";

		// Configurações da sessão
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", ""); 
		props.put("mail.smtp.port", "");

		// Configurações da mensagem
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Criação da mensagem
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(destino));
			message.setSubject("Comprovantes");

			// Parte do texto do email
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Conteúdo do email com anexo");

			// Parte do anexo
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(pathArqZip);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(pathArqZip.getName());
			multipart.addBodyPart(messageBodyPart);

			// Adiciona as partes ao corpo da mensagem
			message.setContent(multipart);

			// Envia a mensagem
			Transport.send(message);

			System.out.println("Email enviado com sucesso!");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
}
