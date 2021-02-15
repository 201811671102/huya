package pre.cg.test;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.util.NamedList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.NameList;
import pre.cg.App;

import pre.cg.base.mail.MailBase;
import pre.cg.base.phone.PhoneBase;

import pre.cg.ftp.FtpOperation;
import pre.cg.mail.SendMail;
import pre.cg.mapper.ProdMapper;
import pre.cg.mongodb.MongodbUtils;
import pre.cg.phone.PhoneCode;
import pre.cg.pojo.Prod;
import pre.cg.pojo.User;
import pre.cg.redis.RedisUtil;
import pre.cg.rsa.RSAutil;
import pre.cg.service.UserService;

import javax.sound.sampled.Port;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Springboot 测试类
 *
 */



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={App.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    @Autowired
    private  UserService userService;
    @Test
    public void  UUID(){
        System.out.println(UUID.randomUUID().toString());
    }

    @Test
    public void testUserService(){
        System.out.println(userService.SelectAll().toString());
    }


 //   @Autowired
//    private UsertwoMapper usertwoMapper;
//
//    @Test
//    public void TestMybatis_generator(){
//        UsertwoExample usertwoExample = new UsertwoExample();
//        usertwoExample.createCriteria().andIdGreaterThan(1);
//        usertwoExample.setOrderByClause("id = 1");
////        Usertwo usertwo = new Usertwo();
////        usertwo.setId(1);
////        usertwo.setName("CCCCC");
////        usertwoMapper.insert(usertwo);
//        System.out.println(usertwoMapper.selectByPrimaryKey(1).getName());
//    }
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void redis(){
        System.out.println("--------------"+redisUtil.get("CG"));
        System.out.println("______"+"333336666");
        System.out.println("___________"+redisUtil.get("CG"));
    }


    @Test
    public void TestMongoDB(){
        List<User> usertwoList = (List<User>) MongodbUtils.findAll(new User());
        for (User usertwo : usertwoList){
            System.out.println(usertwo.toString());
        }
    }

    @Autowired
    private SendMail sendMail;
    @Test
    public void SendBMail(){
        MailBase mailbase = new MailBase();
       mailbase.setRecipient("519706545@qq.com");
       // mailbase.setRecipient("519706545@qq.com");
        mailbase.setSubject("sb");
        mailbase.setContent("sb陈超");
        System.out.println(sendMail.sendAttachmentMail(mailbase,"C:\\Users\\QQ163\\Pictures\\Saved Pictures\\01.jpg"));
    }

    @Test
    public void phonecode(){
        PhoneCode phoneCode = new PhoneCode();
        try {
            PhoneBase phoneBase = phoneCode.getPhonemsg("13534885959");
            if (phoneBase == null){
                //错误
            }
            //成功
        } catch (Exception e) {
            e.printStackTrace();
            //错误
        }
    }

    @Autowired
    private SolrClient solrClient;

    @Test
    public void SolrDelete() throws IOException, SolrServerException {
        /*删除*/
        //根据id删除信息
        UpdateResponse updateResponse = solrClient.deleteById("1");
        //执行的时间
        long elapsedTime = updateResponse.getElapsedTime();

        int qTime = updateResponse.getQTime();
        //请求地址
        String requestUrl = updateResponse.getRequestUrl();
        //请求的结果{responseHeader={status=0,QTime=2}}
        NamedList<Object> response = updateResponse.getResponse();
        //请求结果的头{status=0,QTime=2}
        NamedList responseHeader = updateResponse.getResponseHeader();
        //请求的状态 0
        int status = updateResponse.getStatus();

        System.out.println("elapsedTime==========="+elapsedTime);
        System.out.println("qTime==========="+qTime);
        System.out.println("requestUrl==========="+requestUrl);
        System.out.println("response==========="+response);
        System.out.println("responseHeader==========="+responseHeader);
        System.out.println("status==========="+status);
    }

    @Test
    public void SolrSelect01() throws IOException, SolrServerException {
        //根据id查询内容
        SolrDocument solrDocument = solrClient.getById("5");
        System.out.println("-------------------"+solrDocument);
        if (!solrDocument.isEmpty()){
            //获取filedName
            Collection<String> fieldNames  = solrDocument.getFieldNames();
            //获取file名和内容
            Map<String,Object> fieldValueMap  = solrDocument.getFieldValueMap();
            for (String name : fieldNames){
                System.out.println(name+"----"+fieldValueMap.get(name));
            }
            System.out.println("byId=================="+solrDocument);
            System.out.println("fieldNames=================="+fieldNames);
            System.out.println("fieldValueMap=================="+fieldValueMap);
        }
    }

    @Test
    public void Redis(){
       // redisUtil.set("aa","bb",3605600);
      //  redisUtil.set("a","b",1000);
      //  redisUtil.set("ab","b",1000);
       // redisUtil.set("aaa","b",1000);
        System.out.println("sfagaf------------->");
        long timee = redisUtil.getExpire("ab");
        System.out.println("----------->"+timee);
        Date date = new Date(new Date().getTime()+timee*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        String time = simpleDateFormat.format(date);
        System.out.println(time);
    }

    @Autowired
    private ProdMapper prodMapper;

    @Test
    public void solr() throws IOException, SolrServerException {
        List<Prod> portList = new ArrayList<>();
     /*   for (int i=1;i<10;i++){
            Prod port = new Prod();
            port.setProdId(i);
            port.setProdDescription("text"+i);
            port.setProdPname("name"+i);
            port.setProdPrice(100.0+i);
            port.setUpdateTime(new Date());
            portList.add(port);
            solrClient.deleteById(i+"");
        }*/

        UpdateResponse updateResponse = solrClient.addBean(portList.get(0));
        System.out.println("getElapsedTime--"+updateResponse.getElapsedTime());
        System.out.println("getRequestUrl--"+updateResponse.getRequestUrl());
        System.out.println("getQTime--"+updateResponse.getQTime());
        NamedList<Object> nameList = updateResponse.getResponse();
        for (Object object : nameList){
            System.out.println("getResponse--"+object.toString());
        }
        System.out.println("getStatus--"+updateResponse.getStatus());
        System.out.println("------------------------");
        UpdateResponse updateResponse1 = solrClient.addBeans(portList);
        System.out.println("portList getStatus--"+updateResponse1.getStatus());
        System.out.println("portList getElapsedTime--"+updateResponse1.getElapsedTime());
        System.out.println("portList getRequestUrl--"+updateResponse1.getRequestUrl());
        System.out.println("portList getQTime--"+updateResponse1.getQTime());
        NamedList<Object> nameList1 = updateResponse1.getResponse();
        for (Object object : nameList1){
            System.out.println("portList getResponse--"+object.toString());
        }
        solrClient.commit(true,true,true);
     //   System.out.println("------------->"+prodMapper.selectByPrimaryKey(1).getProdPname());
    }
    @Test
    public void solrselect() throws IOException, SolrServerException {
        SolrDocument solrDocument = solrClient.getById("1");
        Collection<String> namelist = solrDocument.getFieldNames();
        namelist.remove("_version_");
        for (String str : namelist){
            System.out.println(str+" --------------"+solrDocument.getFieldValue(str));
        }
    }

    @Test
    public void rsa() throws InvalidKeySpecException, NoSuchAlgorithmException {
        Map<String, String> keyMap = RSAutil.createKeys(1024);
        String  publicKey = keyMap.get("publicKey");
        String  privateKey = keyMap.get("privateKey");
        System.out.println("公钥: \n\r" + publicKey);
        System.out.println("私钥： \n\r" + privateKey);

        System.out.println("公钥加密——私钥解密");
        String str = "站在大明门前守卫的禁卫军，事先没有接到\n" +
                "有关的命令，但看到大批盛装的官员来临，也就\n" +
                "以为确系举行大典，因而未加询问。进大明门即\n" +
                "为皇城。文武百官看到端门午门之前气氛平静，\n" +
                "城楼上下也无朝会的迹象，既无几案，站队点名\n" +
                "的御史和御前侍卫“大汉将军”也不见踪影，不免\n" +
                "心中揣测，互相询问：所谓午朝是否讹传？";
        System.out.println("公钥"+RSAutil.getPublicKey(publicKey));
        System.out.println("私钥"+RSAutil.getPrivateKey(privateKey));

        System.out.println("\r明文：\r\n" + str);
        System.out.println("\r明文大小：\r\n" + str.getBytes().length);
        String encodedData = RSAutil.publicEncrypt(str, RSAutil.getPublicKey(publicKey));
        System.out.println("密文：\r\n" + encodedData);
        String decodedData = RSAutil.privateDecrypt(encodedData, RSAutil.getPrivateKey(privateKey));
       System.out.println("解密后文字: \r\n" + decodedData);
    }

    @Test
    public void rsa2() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        String str = "陈干13534885959@#$%#$%";
        System.out.println("原文-》"+str);
        String privatekey = null;
        File file = new File("http://192.168.11.134:8080/publicKey/privateKey");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        int temp = 0;
        while ((temp = bufferedInputStream.read()) != -1){
            privatekey = ""+temp;
        }
        String encodedData = RSAutil.privateEncrypt(str,RSAutil.getPrivateKey(privatekey));
        System.out.println("加密--------》"+encodedData);

        String publickey = null;
        File file2 = new File("http://192.168.11.134:8080/publicKey/publicKey");
        BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file2));
        int temp2 = 0;
        while ((temp2 = bufferedInputStream2.read()) != -1){
            publickey = ""+temp2;
        }
        String decodedDate = RSAutil.publicDecrypt(encodedData,RSAutil.getPublicKey(publickey));
        System.out.println("解密----》"+decodedDate);
    }

    @Test
    public void ftp() throws Exception {
        FtpOperation ftpOperation = new FtpOperation();
        ftpOperation.connectToServer();
       // ftpOperation.uploadToFtp(new FileInputStream(new File("E:\\auto.jpg")),"text.jpg","/usr/local/CGproject/photo");
        ftpOperation.uploadToFtp(new FileInputStream(new File("E:\\激活码.txt")),"text.txt","/var/ftp/ftpuser");
    //    System.out.println("-----------------"+ftpOperation.createDirectory("/usr/local/CGproject"));
        ftpOperation.closeConnect();
    }
}
