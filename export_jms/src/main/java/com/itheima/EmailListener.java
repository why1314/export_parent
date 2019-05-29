package com.itheima;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-24-23:21
 */
@Component
public class EmailListener implements MessageListener {

    @Autowired
    private SimpleMailMessage mailMessage;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Override
    public void onMessage(Message message) {

        try {

            MapMessage mapMessage = (MapMessage) message;
            String email = mapMessage.getString("email");
            String userName = mapMessage.getString("userName");
            String companyName = mapMessage.getString("companyName");
            String password = mapMessage.getString("password");

            String context=userName+"先生/女士,欢迎您加入"+companyName+",您的平台账号是:"+email+",密码是:"+password;
            mailMessage.setTo(email);
            mailMessage.setSubject("入职欢迎邮件");
            mailMessage.setText(context);
            mailSender.send(mailMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
