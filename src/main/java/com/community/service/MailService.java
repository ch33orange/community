package com.community.service;

import com.community.dataobject.*;
import com.community.exception.*;
import lombok.extern.slf4j.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.mail.*;
import org.springframework.core.io.*;
import org.springframework.mail.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.*;
import org.thymeleaf.*;
import org.thymeleaf.context.*;

import javax.mail.internet.*;
import java.io.*;
import java.util.*;

/**
 * @email chivseg-hao@qq.com
 * @author:blue
 * @date: 2021/4/12
 * @time: 19:33
 */
@Service
@Slf4j
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String setFrom;
    @Value("${spring.mail.password}")
    private String setPassword;

    /**
     * 发送简单邮件
     *
     * @param form
     */
    public void sendSimpleMail(MailForm form) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(mailProperties.getUsername());
            List<String> to = form.getTo();
            String[] toUsers = form.getTo().toArray(new String[to.size()]);
            mailMessage.setTo(toUsers);
            mailMessage.setSubject(form.getSubject());
            mailMessage.setText(form.getText());

            mailSender.send(mailMessage);
        } catch (Exception e) {
            log.error("邮件发送失败", e.getMessage());
            throw new CustomizeException(CustomizeErrorCode.MESSAGE_QUEUE_SEND_ERROR);
        }
    }

    /**
     * 发送html邮件
     *
     * @param form
     */
    public void sendHtmlMail(MailForm form) {
        try {
            String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(setFrom);
            ArrayList reveivers = new ArrayList();
            for (String email : form.getTo()) {
                if (!email.matches(regex) || StringUtils.isBlank(email)) {
                    continue;//邮箱格式不正确
                }
                reveivers.add(email);
            }
            String[] toUsers = (String[]) reveivers.toArray(new String[reveivers.size()]);
            messageHelper.setTo(toUsers);
            messageHelper.setSubject(form.getSubject());
            messageHelper.setText(form.getText(), true);

            // 本地附件
            if (form.getAttachmentPath() != null) {
                List<String> pathList = form.getAttachmentPath();
                for (String attachmentPath : pathList) {
                    File file = new File(attachmentPath);
                    if (file.exists()) {
                        String fileName = file.getName();
                        FileSystemResource fsr = new FileSystemResource(file);
                        messageHelper.addAttachment(fileName, fsr);
                    }
                }
            }
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("邮件发送失败", e.getMessage());
            throw new CustomizeException(CustomizeErrorCode.MESSAGE_QUEUE_SEND_ERROR);
        }
    }

    /**
     * 发送模板邮件
     *
     * @param form
     */
    public void sendTemplateMail(MailForm form) {
        try {
            Context context = new Context();
            context.setLocale(Locale.CHINA);
            context.setVariables(form.getContextVar());
            String templateMail = templateEngine.process(form.getTemplateName(), context);
            form.setText(templateMail);
            sendHtmlMail(form);
        } catch (Exception e) {
            log.error("邮件发送失败", e.getMessage());
            throw new CustomizeException(CustomizeErrorCode.MESSAGE_QUEUE_SEND_ERROR);
        }
    }
}
