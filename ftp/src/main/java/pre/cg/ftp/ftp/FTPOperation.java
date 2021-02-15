package pre.cg.ftp.ftp;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import pre.cg.ftp.base.config.FTPConfig;
import pre.cg.ftp.base.ftp.FTPPool;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName FTPOperation
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/19 0:00
 **/
@Log4j2
@Component
public class FTPOperation {

    //默认文件类型为二进制（图片）
    private static int filetype = FTP.BINARY_FILE_TYPE;

    @Autowired
    FTPConfig config;
    @Autowired
    private FTPPool pool;

    /**
     *
     * 功能：上传文件附件到文件服务器
     * @param buffIn:上传文件流
     * @param fileName：保存文件名称
     * @return
     * @throws IOException
     */
    public  boolean uploadToFtp(InputStream buffIn, String fileName, String filepath){
        FTPClient ftpClient = pool.getFTPClient();
        // 上传文件
        try {
        setFileType(filetype,ftpClient);
        int reply = ftpClient.getReplyCode();
        if(!FTPReply.isPositiveCompletion(reply)){
            ftpClient.disconnect();
            System.out.println("1");
            return false;
        }
        ftpClient.enterLocalPassiveMode();
        if(!ftpClient.changeWorkingDirectory(filepath)){
            String[] dirs = filepath.split("/");
            String tempPath = "";
            for (String dir : dirs){
                if (null == dir || "".equals(dir)) continue;
                tempPath += "/"+dir;
                if (!ftpClient.changeWorkingDirectory(tempPath)){
                    if (!ftpClient.makeDirectory(tempPath)){
                        System.out.println("2");
                        return false;
                    }else{
                        ftpClient.changeWorkingDirectory(tempPath);
                    }
                }
            }
        }
        // 上传文件到ftp
        // 输出操作结果信息
        fileName = new String(fileName.getBytes("utf-8"),"iso-8859-1");
        if (ftpClient.storeFile(fileName, buffIn)) {
            return true;
        } else {
            System.out.println("3");
            return false;
        }
    }catch (Exception e) {
        log.error("ERR : upload file  to ftp : failed! ", e);
    } finally {
        try {
            if (buffIn != null) {
                buffIn.close();
            }
            pool.returnFTPClient(ftpClient);
        } catch (Exception e) {
            log.error("ftp关闭输入流时失败！", e);
        }
    }
        return true;
}


    /**
     *
     * 功能：根据文件名称，下载文件流
     * @param filename
     * @return
     * @throws IOException
     */
    public InputStream downloadFile(String filename, String filepath){
        InputStream in=null;
        FTPClient ftpClient = pool.getFTPClient();
        try {
            ftpClient.enterLocalPassiveMode();
            // 设置传输二进制文件
            setFileType(filetype,ftpClient);
            if(!FTPReply.isPositiveCompletion(ftpClient.getReplyCode()))
            {
                return null;
            }
            if (!ftpClient.changeWorkingDirectory(filepath)){
                return null;
            }
            // ftp文件获取文件
            filename = new String(filename.getBytes("utf-8"),"iso-8859-1");
            in=ftpClient.retrieveFileStream(filename);
            return in;
        }catch (Exception e) {
            log.error("ERR : upload file "+ filename+ " from ftp : failed!", e);
            return null;
        }finally {
            pool.returnFTPClient(ftpClient);
        }
    }


    public  boolean delectFile(String filename,String filepath){
        FTPClient ftpClient = pool.getFTPClient();
        try {
            filename = new String(filename.getBytes("utf-8"),"iso-8859-1");
            ftpClient.changeWorkingDirectory(filepath);
            if (!ftpClient.deleteFile(filename)){
                return false;
            }
            return true;
        }catch (Exception e) {
            log.error("ERR : delete file "+ filename+ " from ftp : failed!", e);
            return false;
        }finally {
            pool.returnFTPClient(ftpClient);
        }
    }
    /**
     * 设置传输文件的类型[文本文件或者二进制文件]
     *
     * @param fileType
     *            --BINARY_FILE_TYPE、ASCII_FILE_TYPE
     */
    public  void setFileType(int fileType,FTPClient ftpClient) {
        try {
            ftpClient.setFileType(fileType);
        } catch (Exception e) {
            log.error("ftp设置传输文件的类型时失败！", e);
        }finally {

        }
    }

}
