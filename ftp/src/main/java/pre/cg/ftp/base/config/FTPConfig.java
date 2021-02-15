package pre.cg.ftp.base.config;

import lombok.Data;
import org.apache.commons.net.ftp.FTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName FTPConfig
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/18 23:48
 **/
@Component
@Data
public class FTPConfig {
    @Value("${ftp.userName}")
    private String userName;
    @Value("${ftp.password}")
    private String passWord;
    @Value("${ftp.host}")
    private String host;
    @Value("${ftp.port}")
    private Integer port;
    private Integer passiveMode = FTP.BINARY_FILE_TYPE;
    private String encoding="UTF-8";
    private int clientTimeout=120000;
    private int transferFileType=FTP.BINARY_FILE_TYPE;
    @Value("${ftp.root}")
    private String root;
    @Value("${ftp.MaxTotal}")
    private int MaxTotal;
    @Value("${ftp.MinIdel}")
    private int MinIdel;
    @Value("${ftp.MaxIdle}")
    private int MaxIdle;
    @Value("${ftp.MaxWaitMillis}")
    private int MaxWaitMillis;
}
