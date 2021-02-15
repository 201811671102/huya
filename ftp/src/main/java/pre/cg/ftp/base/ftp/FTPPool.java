package pre.cg.ftp.base.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pre.cg.ftp.base.config.FTPConfig;

/**
 * @ClassName FTPPool
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/18 23:52
 **/
@Component
public class FTPPool {

    FtpClientFactory factory;
    private final GenericObjectPool<FTPClient> internalPool;
    //初始化连接池
    public FTPPool(@Autowired FtpClientFactory factory){
        this.factory=factory;
        FTPConfig config = factory.getConfig();
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(config.getMaxTotal());
        poolConfig.setMinIdle(config.getMinIdel());
        poolConfig.setMaxIdle(config.getMaxIdle());
        poolConfig.setMaxWaitMillis(config.getMaxWaitMillis());
        this.internalPool = new GenericObjectPool<FTPClient>(factory,poolConfig);
    }
    //从连接池中取连接
    public  FTPClient getFTPClient() {
        try {
            return internalPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //将链接归还到连接池
    public  void returnFTPClient(FTPClient ftpClient) {
        try {
            internalPool.returnObject(ftpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 销毁池子
     */
    public  void destroy() {
        try {
            internalPool.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
