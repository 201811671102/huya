package pre.cg.ws.base;


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
    Room_Open(6000);


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
            default:return null;
        }
    }
}
