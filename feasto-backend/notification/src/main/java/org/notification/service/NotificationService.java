package org.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.notification.config.RabbitMQConfig;
import org.notification.model.MessageNotification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveMessage(MessageNotification messageNotification){
        sendEmailWithPdf(messageNotification);
    }
    @SneakyThrows
    private void sendEmailWithPdf(MessageNotification messageNotification)  {
           try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromMail);
            helper.setTo("kubasokol3001@gmail.com");
            helper.setSubject("Your bill");
            helper.setText("Find attached your invoice.");


            ByteArrayDataSource dataSource = new ByteArrayDataSource(Base64.getDecoder().decode(messageNotification.pdfContent()), "application/pdf");


            helper.addAttachment("bill.pdf", (InputStreamSource) dataSource);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}


