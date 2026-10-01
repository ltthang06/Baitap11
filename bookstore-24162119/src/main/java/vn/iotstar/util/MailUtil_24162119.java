package vn.iotstar.util;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MailUtil_24162119 {

    public static void sendOtp(
            String receiver,
            String otp) {

        String username =
                System.getenv("MAIL_USERNAME");

        String password =
                System.getenv("MAIL_PASSWORD");

        if (username == null
                || password == null) {

            throw new IllegalStateException(
                    "Chua cau hinh MAIL_USERNAME "
                    + "hoac MAIL_PASSWORD"
            );
        }

        Properties properties =
                new Properties();

        properties.put(
                "mail.smtp.auth",
                "true"
        );

        properties.put(
                "mail.smtp.starttls.enable",
                "true"
        );

        properties.put(
                "mail.smtp.host",
                "smtp.gmail.com"
        );

        properties.put(
                "mail.smtp.port",
                "587"
        );

        Session session =
                Session.getInstance(
                        properties,
                        new Authenticator() {

                            @Override
                            protected
                            PasswordAuthentication
                            getPasswordAuthentication() {

                                return new
                                        PasswordAuthentication(
                                                username,
                                                password
                                        );
                            }
                        }
                );

        try {

            Message message =
                    new MimeMessage(session);

            message.setFrom(
                    new InternetAddress(
                            username
                    )
            );

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(
                            receiver
                    )
            );

            message.setSubject(
                    "BookStore OTP"
            );

            message.setText(
                    "Ma OTP cua ban la: "
                    + otp
                    + "\nOTP co hieu luc 5 phut."
            );

            Transport.send(message);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}