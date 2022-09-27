package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.from}")
    private String from;
    public void sendMailArticleCreated(Article article, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(article.getUser().getEmail());
        message.setSubject(subject);
        message.setText(body);
    }
}
