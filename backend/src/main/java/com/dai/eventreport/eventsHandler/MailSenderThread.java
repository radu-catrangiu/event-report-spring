package com.dai.eventreport.eventsHandler;

import javax.mail.internet.MimeMessage;

import com.dai.eventreport.authHandler.User;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailSenderThread implements Runnable {
    private Thread t;
    private Event event;
    private User admin;

    private JavaMailSender javaMailSender;

    MailSenderThread(JavaMailSender javaMailSender, Event event, User admin) {
        this.javaMailSender = javaMailSender;
        this.event = event;
        this.admin = admin;
    }

    private void sendEmail(Event event, User admin) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setSubject("[EventReport] New Event `" + event.getTitle() + "`");
            helper.addTo(admin.getEmail());

            String content = "";
            content += "<b><h1>" + event.getTitle() + "</h1></b>\n";
            content += "<i>Type:</i> <b><h4>" + event.getTag() + "</h4></b>\n";
            content += "<i>Description:</i> <p>" + event.getDescription() + "</p>\n";
            content += "<h5>Check platform for more details!</h5>\n";

            helper.setText(content, true);

            javaMailSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        sendEmail(event, admin);
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, admin.getEmail());
            t.start();
        }
    }

}