package pre.cg.base.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailBase implements Serializable {
    private String recipient; //邮件接收人
    private String subject; //邮件主题
    private String content; //邮件内容
    public static String SENDER  = "1634337925@qq.com";
}
