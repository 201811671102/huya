package pre.cg.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import pre.cg.base.ResultUtil;
import pre.cg.base.dto.ResultDTO;
import pre.cg.base.mail.MailBase;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class SendMail {
    @Autowired
    private JavaMailSender javaMailSender;
    /*发送简单邮件*/
    public ResultDTO sendSimpleMail(MailBase mailbase){
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(MailBase.SENDER);
            //邮件介接收人
            simpleMailMessage.setTo(mailbase.getRecipient());
            //邮件主题
            simpleMailMessage.setSubject(mailbase.getSubject());
            //邮件内容
            simpleMailMessage.setText(mailbase.getContent());

            javaMailSender.send(simpleMailMessage);
            return new ResultUtil().Success("成功发送邮件到"+mailbase.getRecipient());
        }catch (Exception e){
            return new ResultUtil().Error("400",e.toString());
        }
    }
    /*发送html格式的邮件*/
    public ResultDTO sendHTMLMail(MailBase mailbase){
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(MailBase.SENDER);
            mimeMessageHelper.setTo(mailbase.getRecipient());
            mimeMessageHelper.setSubject(mailbase.getSubject());
            mimeMessageHelper.setText(mailbase.getContent(), true);
            javaMailSender.send(mimeMailMessage);
            return new ResultUtil().Success("成功发送邮件到"+mailbase.getRecipient());
        } catch (Exception e) {
            return new ResultUtil().Error("400",e.toString());
        }
    }
    /* 发送带附件格式的邮件*/
    public ResultDTO sendAttachmentMail(MailBase mailbase,String filePath){
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(MailBase.SENDER);
            mimeMessageHelper.setTo(mailbase.getRecipient());
            mimeMessageHelper.setSubject(mailbase.getSubject());
            mimeMessageHelper.setText(mailbase.getContent());
            //文件路径
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = null;
            if (filePath.lastIndexOf("/") != -1){
                fileName = filePath.substring(filePath.lastIndexOf("/"));
            }else{
                fileName = filePath.substring(filePath.lastIndexOf("\\"));
            }
            mimeMessageHelper.addAttachment(fileName, file);
            javaMailSender.send(mimeMailMessage);
            return new ResultUtil().Success("成功发送邮件到"+mailbase.getRecipient());
        } catch (Exception e) {
            return new ResultUtil().Error("400",e.toString());
        }
    }
    /*发邮件发送之内嵌图片*/
    public ResultDTO sendInlineMail(MailBase mailbase,String filePath){
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(MailBase.SENDER);
            mimeMessageHelper.setTo(mailbase.getRecipient());
            mimeMessageHelper.setSubject(mailbase.getSubject());
            mimeMessageHelper.setText("<html><body><img src='cid:picture' /></body></html>", true);
            //文件路径
            FileSystemResource file = new FileSystemResource(new File(filePath));
            mimeMessageHelper.addInline("picture", file);
            javaMailSender.send(mimeMailMessage);

            return new ResultUtil().Success("成功发送邮件到"+mailbase.getRecipient());
        } catch (Exception e) {
            return new ResultUtil().Error("400",e.toString());
        }
    }
}
