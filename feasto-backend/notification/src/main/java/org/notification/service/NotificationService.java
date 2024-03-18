package org.notification.service;

import lombok.RequiredArgsConstructor;
import org.notification.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveMessage(final String pdfContentBase64){
        sendEmailWithPdf("kuba@wp.pl", Base64.getDecoder().decode(pdfContentBase64));
    }
    private void sendEmailWithPdf(String email, byte[] pdfContent) {

    }
}


