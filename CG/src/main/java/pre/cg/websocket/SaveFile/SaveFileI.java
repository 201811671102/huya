package pre.cg.websocket.SaveFile;

import java.util.Map;

public interface SaveFileI {
    /**
     * 生成文件路径
     * @param fileName  接收文件名
     * @return  返回文件路径
     */
    Map<String,Object> docPath(String fileName);

    /**
     * 将字节流写入文件
     * @param b 字节流数组
     * @param map  文件路径
     * @return  返回是否成功
     */
    boolean saveFileFromBytes(byte[] b, Map<String, Object> map);
}
