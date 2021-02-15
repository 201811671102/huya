package pre.cg.websocket.SaveFile;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaveFileImpl implements SaveFileI {
    @Override
    public Map<String, Object> docPath(String fileName) {
        HashMap<String, Object> map = new HashMap<>();
        //根据时间生成文件夹路径
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String docUrl = simpleDateFormat.format(date);
        //文件保存地址
        String path = "static/file/" + docUrl;
        //创建文件
        File dest = new File(path+"/" + fileName);
        //如果文件已经存在就先删除掉
        if (dest.getParentFile().exists()) {
            dest.delete();
        }
        map.put("dest", dest);
        map.put("path", path+"/" + fileName);
        map.put("nginxPath","/"+docUrl+"/"+fileName);
        return map;
    }

    @Override
    public boolean saveFileFromBytes(byte[] b, Map<String, Object> map) {
        //创建文件流对象
        FileOutputStream fstream = null;
        //从map中获取file对象
        File file = (File) map.get("dest");
        //判断路径是否存在，不存在就创建
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            fstream = new FileOutputStream(file, true);
            fstream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fstream != null) {
                try {
                    fstream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return true;
    }
}
