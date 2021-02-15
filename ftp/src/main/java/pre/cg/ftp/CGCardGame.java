package pre.cg.ftp;

import io.swagger.models.auth.In;

import java.util.*;

/**
 * @ClassName CGCardGame
 * @Description TODO
 * @Author QQ163
 * @Date 2020/11/19 22:32
 **/
public class CGCardGame {
    private List<Integer> cardList = new ArrayList<>();
    private List<Integer> cardsCG = new ArrayList<>();
    private Map<Integer,List<Integer>> cardTypeCG = new HashMap<>();
    private List<Integer> cardsCom = new ArrayList<>();
    private Map<Integer,List<Integer>> cardTypeCom = new HashMap<>();
    private int[] lastSendCard = null;
    //初始化卡牌
    void initCard(){
        for (int j=3;j<=15;j++){
            for (int i=0;i<4;i++){
                cardList.add(j);
            }
        }
        Collections.shuffle(cardList);
    }
    //初始化玩家
    void initPlayer(){
        for (int i=0;i<13*2;i++){
            if (i%2 == 0){
                cardsCG.add(cardList.get(i));
            }else {
                cardsCom.add(cardList.get(i));
            }
        }
        for (int i=1;i<=4;i++){
            cardTypeCG.put(i,new ArrayList<>());
            cardTypeCom.put(i,new ArrayList<>());
        }
        orderType(cardsCG,cardTypeCG);
        orderType(cardsCom,cardTypeCom);
    }
    //排序
    void orderType(List<Integer> cards,Map<Integer,List<Integer>> cardType){
        cards.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        int cardSameNumber=0;
        int lastCard=cards.get(0);
        for (Integer index=0;index<cards.size()+1;index++){
            Integer card;
            if (index == cards.size()){
                card = -1;
            }else{
                card = cards.get(index);
            }
            List<Integer> cardMapList;
            if (card == lastCard){
                cardSameNumber++;
            }else{
                switch (cardSameNumber){
                    case 1:
                        cardMapList = cardType.get(1);
                        cardMapList.add(lastCard);
                        cardType.replace(1,cardMapList);
                        break;
                    case 2:
                        cardMapList = cardType.get(2);
                        cardMapList.add(lastCard);
                        cardType.replace(2,cardMapList);
                        break;
                    case 3:
                        cardMapList = cardType.get(3);
                        cardMapList.add(lastCard);
                        cardType.replace(3,cardMapList);
                        break;
                    case 4:
                        cardMapList = cardType.get(4);
                        cardMapList.add(lastCard);
                        cardType.replace(4,cardMapList);
                        break;
                    default:
                        throw new RuntimeException("cardSameNumber wrong");
                }
                cardSameNumber = 1;
            }
            lastCard = card;
        }
    }
    //显示牌面
    void showCard(String name,List<Integer> cards){
        System.out.println(name+"牌面：");
        for (Integer card : cards){
            System.out.print(changeCard(card)+" ");
        }
        System.out.println("");
    }
    //显示出牌
    void sendCard(String name){
        StringBuilder stringBuilder = new StringBuilder(name+"出牌：");
        switch (lastSendCard[0]){
            case 1:;
            case 2:;
            case 3:;
            case 4:
                for (int i=0;i<lastSendCard[0];i++) {
                    stringBuilder.append(changeCard(lastSendCard[1]));
                    stringBuilder.append("-");
                }
                break;
            case 5:
                for (int i=0;i<3;i++) {
                    stringBuilder.append(changeCard(lastSendCard[1]));
                    stringBuilder.append("-");
                }
                stringBuilder.append(changeCard(lastSendCard[2]));
                break;
            case 6:
                for (int i=0;i<4;i++) {
                    stringBuilder.append(changeCard(lastSendCard[1]));
                    stringBuilder.append("-");
                }
                stringBuilder.append(changeCard(lastSendCard[2]));
                stringBuilder.append("-");
                stringBuilder.append(changeCard(lastSendCard[2]));
                break;
        }
        int index = stringBuilder.lastIndexOf("-");
        stringBuilder.deleteCharAt(index);
        System.out.println(stringBuilder.toString());
    }
    //显示出牌——要不起
    void unsendCard(String name){
        lastSendCard = null;
        System.out.println(name+"出牌：要不起");
    }
    //数值转显示
    String changeCard(Integer card){
        switch (card){
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            case 14:
                return "1";
            case 15:
                return "2";
            default:return card.toString();
        }
    }
    //显示转数值
    Integer changeValue(String value){
        switch (value.toLowerCase()){
            case "j":return 11;
            case "q":return 12;
            case "k":return 13;
            case "1":return 14;
            case "2":return 15;
            default:return Integer.parseInt(value);
        }
    }
    //自动出牌
    void autoSend(String name){
        //出牌的类型
        int[] sendCardType = new int[3];
        //记录要出的牌，待会删掉
        List<Integer> sendCardList = new ArrayList<>();
        //主牌
        List<Integer> mainCardList = new ArrayList<>();
        int mainIndex = -1;
        //副牌
        List<Integer> minorCardList = new ArrayList<>();
        int minorIndex = -1;

        //是否要接牌
        if (lastSendCard == null){
            //从小打到大
            for (int type : cardTypeCom.keySet()){
                mainCardList = cardTypeCom.get(type);
                //就打最小的
                if (mainCardList.size()>0){
                    int sendCard = mainCardList.get(0);
                    //记录出牌类型
                    sendCardType[0] = type;
                    sendCardType[1] = sendCard;
                    for (int i=0;i<type;i++){
                        sendCardList.add(sendCard);
                    }
                    //去掉一个类型
                    mainIndex = 0;
                    break;
                }
            }
        }else{
            Integer type = lastSendCard[0];
            Integer lastCardMain = lastSendCard[1];
            if (type > 4){
                mainCardList = cardTypeCom.get(type-2);
            }else {
                mainCardList = cardTypeCom.get(type);
            }
            //是否有主牌
            if (mainCardList.size() > 0){
                for (Integer i=0;i<mainCardList.size();i++){
                    Integer mainCard = mainCardList.get(i);
                    if (mainCard > lastCardMain){
                        //记录出牌类型
                        sendCardType[0] = type;
                        sendCardType[1] = mainCard;
                        //出主牌
                        for (int j=0;j<type;j++) {
                            sendCardList.add(mainCard);
                        }
                        mainIndex = i;
                        if (type >4 ){
                            minorCardList = cardTypeCom.get(type-4);
                            if (minorCardList.size() > 0 ){
                                Integer minorCard = minorCardList.get(0);
                                minorIndex = 0;
                                for (int j=0;j<type-4;j++) {
                                    sendCardList.add(minorCard);
                                }
                                sendCardType[2] = minorCard;
                            }
                        }
                        break;
                    }else{
                        sendCardList.clear();
                    }
                }
            }else{
                //炸弹
                if (lastSendCard[0]!= 6 && cardTypeCom.get(4).size()>0){
                    mainCardList = cardTypeCom.get(4);
                    Integer mainCard = mainCardList.get(0);
                    mainIndex = 0;
                    sendCardType[0] = 4;
                    sendCardType[1] = mainCard;
                }
            }
        }
        //判断是否能出牌
        if (sendCardList.isEmpty()){
            unsendCard(name);
        }else{
            lastSendCard = sendCardType;
            sendCard(name);
            //去掉副牌类型
            if (minorIndex != -1) {
                //去掉主牌类型
                mainCardList.remove(mainIndex);
                cardTypeCom.replace(sendCardType[0]-2,mainCardList);

                minorCardList.remove(minorIndex);
                cardTypeCom.replace(sendCardType[0] - 4, minorCardList);
            }else {
                //去掉主牌类型
                mainCardList.remove(mainIndex);
                cardTypeCom.replace(sendCardType[0],mainCardList);
            }
            //去掉
            cardsCom.removeAll(sendCardList);
        }
    }
    //玩家出牌
    boolean playerSend(String name,String playerCards){
        if (playerCards.isEmpty()){
            unsendCard(name);
            return true;
        }
        //出牌是否规范
        String[] split = playerCards.split("-");
        if (split.length>6){
            System.out.println("请规范出牌");
            return false;
        }
        List<Integer> playerCardList = new ArrayList<>();
        for (int i=0;i<split.length;i++){
            Integer result = changeValue(split[i]);
            if (result >15 || result<3){
                System.out.println("请规范出牌");
                return false;
            }
            playerCardList.add(result);
        }
        Map<Integer,List<Integer>> playerCardMap = new HashMap<>();
        for (int i=1;i<=4;i++){
            playerCardMap.put(i,new ArrayList<>());
        }
        orderType(playerCardList,playerCardMap);
        if (!cardsCG.containsAll(playerCardList)){
            System.out.println("您没有这样的牌");
            return false;
        }
        int finalType = 0;
        int[] playerSendCard = new int[3];
        for (Integer key : playerCardMap.keySet()){
            if (playerCardMap.get(key) == null || playerCardMap.get(key).size() == 0){
                continue;
            }
            switch (key){
                case 1:
                    finalType = 1;
                    playerSendCard[1] = playerCardMap.get(key).get(0);
                    break;
                case 2:
                    if (finalType == 1){
                        finalType = -1;
                    }else {
                        finalType = 2;
                        playerSendCard[1] = playerCardMap.get(key).get(0);
                    }
                    break;
                case 3:
                    if (finalType == 1){
                        finalType = 5;
                        playerSendCard[2] = playerSendCard[1];
                        playerSendCard[1] = playerCardMap.get(key).get(0);
                    }else if (finalType == 2){
                        finalType = -1;
                    }else{
                        finalType = 3;
                        playerSendCard[1] = playerCardMap.get(key).get(0);
                    }
                    break;
                case 4:
                    if (finalType == 2){
                        finalType = 6;
                        playerSendCard[2] = playerSendCard[1];
                        playerSendCard[1] = playerCardMap.get(key).get(0);
                    }else if (finalType == 1){
                        finalType = -1;
                    }else {
                        finalType = 4;
                        playerSendCard[1] = playerCardMap.get(key).get(0);
                    }
                    break;
                default:break;
            }
        }
        if (finalType == -1){
            System.out.println("请规范出牌");
            return false;
        }
        //是否接得上
        playerSendCard[0] = finalType;
        if (lastSendCard == null){
            lastSendCard = playerSendCard;
            clearPlayerCard(playerCardList);
            return true;
        }else{
            if (finalType == lastSendCard[0]){
                if (playerSendCard[1] > lastSendCard[1]){
                    lastSendCard = playerSendCard;
                    clearPlayerCard(playerCardList);
                    return true;
                }else {
                    System.out.println("请规范出牌");
                    return false;
                }
            }else if (finalType == 4 && lastSendCard[0] != 6){
                lastSendCard = playerSendCard;
                clearPlayerCard(playerCardList);
                return true;
            }else{
                System.out.println("请规范出牌");
                return false;
            }
        }
    }
    //丢牌
    void clearPlayerCard(List<Integer> sendCardList){
        Integer minDelete = sendCardList.get(0);
        Integer maxDelete = sendCardList.get(sendCardList.size() - 1);
        int max = cardsCG.lastIndexOf(maxDelete);
        int min = cardsCG.indexOf(minDelete);
        //外循环现有卡牌
        for (int i = max; i >= min; i--) {
            Integer cgCard = cardsCG.get(i);
            //内循环需要删除的卡牌
            for (int j=sendCardList.size()-1;j>=0;j--) {
                Integer sendCard = sendCardList.get(j);
                if (sendCard.equals(cgCard)){
                    cardsCG.remove(i);
                    sendCardList.remove(j);
                    break;
                }
            }
        }
        cardTypeCG.clear();
        cardTypeCG = new HashMap<Integer, List<Integer>>();
        for (int i=1;i<=4;i++){
            cardTypeCG.put(i,new ArrayList<>());
        }
        if (cardsCG.size() == 0){
            return;
        }
        orderType(cardsCG,cardTypeCG);
    }
    //开始游戏
    void startGame(){
        initCard();
        initPlayer();
        boolean gameOn = true;
        boolean playerOnCharge = true;
        String playerOne = "己方";
        String playerTwo = "电脑";
        Scanner scanner = new Scanner(System.in);
        while (gameOn){
            System.out.println("");
            showCard(playerTwo,cardsCom);
            showCard(playerOne,cardsCG);
            System.out.println("");
            if (playerOnCharge){
                System.out.print(playerOne + "出牌：");
                //获取输入
                boolean onSend = true;
                while (onSend){
                    String answer = scanner.nextLine();
                    onSend = !playerSend(playerOne,answer);
                }
                //结束回合
                playerOnCharge = false;
            }else{
                autoSend(playerTwo);
                //结束回合
                playerOnCharge = true;
            }
            if (cardsCG.size() ==0){
                System.out.println("游戏结束,玩家胜");
                gameOn = false;
            }else if (cardsCom.size() == 0){
                System.out.println("游戏结束,电脑胜");
                gameOn = false;
            }
        }
        scanner.close();
    }
    public static void main(String[] args) {
        new CGCardGame().startGame();
    }
}