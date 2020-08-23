package pre.cg.huya.base.websocket;


public enum Protocol {
    //心跳包               uri:1000 body:空
    HeartbeatReq(1000),
    //提交参与者信息        uri:2000 body:名字
    Play_Info(2000),
    //主播尚未开房/关闭房间  uri:3000 body:空
    Room_Close(3000),
    //游戏开始             uri:4000 body:空
    Game_On(4000),
    //游戏结束             uri:5000 body:分数
    Game_Over(5000),
    //开房                uri:6000 body:空
    Room_Open(6000),
    //solo 开房
    Solo_Open(7000),
    //solo 加入房间
    Solo_Join(8000),
    //solo 信息
    Solo_Score(9000),
    //solo 对象已经下线
    Solo_Over(10000),
    //solo 邀请
    Profile_Talk(6666);


    private int value;
    Protocol(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Protocol forNumber(int value){
        switch (value){
            case 1000:return HeartbeatReq;
            case 2000:return Play_Info;
            case 3000:return Room_Close;
            case 4000:return Game_On;
            case 5000:return Game_Over;
            case 6000:return Room_Open;
            case 7000:return Solo_Open;
            case 8000:return Solo_Join;
            case 9000:return Solo_Score;
            case 10000:return Solo_Over;
            case 6666:return Profile_Talk;
            default:return null;
        }
    }
}
