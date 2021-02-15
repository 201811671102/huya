package com.cs.heart_release_sock.manager;

import io.netty.channel.Channel;


import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName UserMagger
 * @Description TODO
 * @Author QQ163
 * @Date 2021/1/16 16:08
 **/
public class ConnManager {
    private static final ConnManager connManager = new ConnManager();
    public static ConnManager getInstance() {
        return connManager;
    }
    private ConnManager(){}

    private ConcurrentHashMap<Integer, Channel> ChannelUid = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Channel, Integer> UidChannel = new ConcurrentHashMap<>();
    //建立链接，注册映射关系
    public void onLogin(Integer uid,Channel channel){
        if (this.ChannelUid.containsKey(uid)){
            this.ChannelUid.get(uid).close();
            this.onLogout(channel);
        }
        this.ChannelUid.put(uid,channel);
        this.UidChannel.put(channel,uid);
    }
    //断开链接，注销映射关系
    public void onLogout(Channel channel){
        Integer uid = this.UidChannel.getOrDefault(channel,null);
        if (uid != null){
            this.UidChannel.remove(channel);
            this.ChannelUid.remove(uid);
        }
    }
    //获取uid
    public Integer getUid(Channel channel){
        if (channel != null){
            return this.UidChannel.getOrDefault(channel,null);
        }
        return null;
    }
    //获取channel
    public Channel getChannel(Integer uid){
        if (uid != null){
            return this.ChannelUid.getOrDefault(uid,null);
        }
        return null;
    }
    //获取所有人
    public List<Integer> getAllUid(){
        if (!this.ChannelUid.isEmpty()){
            return (List<Integer>) this.ChannelUid.keys();
        }
        return null;
    }
    //是否在线
    public boolean isAlive(Integer uid){
        if (uid != null){
            return this.ChannelUid.containsKey(uid);
        }
        return false;
    }
}
