package org.notification.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;


public record MessageNotification(
         String email,
         String pdfContent
) {


}
