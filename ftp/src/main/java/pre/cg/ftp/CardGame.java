package pre.cg.ftp;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.*;

/**
 * @ClassName CardGame
 * @Description TODO
 * @Author QQ163
 * @Date 2020/11/19 10:38
 **/
public class CardGame {
    //一副牌，去鬼
    private static List<Integer> cards;
    //初始化扑克牌
    static{
        cards = new ArrayList<>();
        for (int i=0;i<4*13;i++){
            cards.add(i);
        }
    }
    //上一次打出的牌
    private static int[] lastPlayerCards = null;
    //是否在游戏中
    private static boolean onGame = true;
    //谁

    //初始化游戏
    static void init(Player player1, Player player2) throws Exception {
        Collections.shuffle(cards);
        for (Integer i=0;i<13*2;i++){
            Integer result = cards.get(i);
            if (i%2 == 0){
                player1.getCard(result);
            }else{
                player2.getCard(result);
            }
        }
        player1.orderCard(player1.getCards(),player1.getCardMap());
        player2.orderCard(player2.getCards(),player2.getCardMap());
    }

    //电脑vs电脑 开始游戏
    static void startGame(Player playerOne,Player playerTwo) throws Exception {
       boolean playerOn = true;
       while (onGame){
           //谁出牌
           if (playerOn){
               playerChoose(playerOne);
               playerOn = false;
               Thread.sleep(500);
           }else{
               playerChoose(playerTwo);
               playerOn = true;
               Thread.sleep(500);
           }
       }
    }
    //电脑vs真人开始游戏
    static void startGameTwo(Player playerOne,Player playerTwo) throws Exception {
        boolean playerOn = true;
        while (onGame){
            //谁出牌
            if (playerOn){
                playerOn = realPlayer(playerOne);
            }else{
                playerChoose(playerTwo);
                playerOn = true;
                Thread.sleep(500);
            }
        }
    }
    //真人玩家操作
    static boolean realPlayer(Player player){
        player.showCards();
        Scanner scanner = new Scanner(System.in);
        String[] split = new String[0];
        while (scanner.hasNextLine()) {
            String playerAnswer = scanner.next();
            split = playerAnswer.split("-");
            try {
                player.playerCardType(split,lastPlayerCards);
            } catch (RuntimeException e) {
                continue;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        scanner.close();
        return false;
    }


    //玩家思考
    static void playerChoose(Player player) throws Exception {
        player.showCards();
        //如果上次出的牌为null,就自动出牌，否则接牌
        if (lastPlayerCards == null) {
            lastPlayerCards = player.autoCard();
        }else{
            lastPlayerCards = player.sendCard(lastPlayerCards);
        }
        //如果出牌的结果为null，就是要不去
        if (lastPlayerCards == null){
            System.out.println(player.getName() + ": pass");
        }else{
            //显示出牌结果
            player.showCards(lastPlayerCards);
            //判断是否已经赢了
            if(player.getCards().size() == 0){
                //赢了
                System.out.println(player.getName() + ": 我赢了");
                onGame = false;
            }
        }
    }

    public static void main(String[] args) {
        Player playerCG = new Player("CG");
        Player playerCom = new Player("Com");
        try {
            init(playerCG, playerCom);
            startGameTwo(playerCG,playerCom);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("fail");
        }
    }
}

@Data
class Player{
    //姓名
    private String name;
    //总卡牌
    private List<Integer> cards;
    //卡牌组合
    private Map<Integer,List<Integer>> cardMap;

    public Player(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
        this.cardMap = new HashMap<>();
        for (int i=1;i<=4;i++){
            cardMap.put(i,new ArrayList<>());
        }
    }

    public void getCard(Integer card){
        cards.add(card);
    }
    //排序
    public void orderCard(List<Integer> cards, Map<Integer,List<Integer>> cardMap) throws RuntimeException {
        cards.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        int cardSameNumber=0;
        int lastCard=cards.get(0)/4+3;
        for (Integer index=0;index<cards.size()+1;index++){
            Integer card;
            if (index == cards.size()){
                card = -1;
            }else{
                card = cards.get(index);
            }
            List<Integer> cardMapList;
            if (card/4+3 == lastCard){
                cardSameNumber++;
            }else{
                switch (cardSameNumber){
                    case 1:
                        cardMapList = cardMap.get(1);
                        cardMapList.add(lastCard);
                        cardMap.replace(1,cardMapList);
                        break;
                    case 2:
                        cardMapList = cardMap.get(2);
                        cardMapList.add(lastCard);
                        cardMap.replace(2,cardMapList);
                        break;
                    case 3:
                        cardMapList = cardMap.get(3);
                        cardMapList.add(lastCard);
                        cardMap.replace(3,cardMapList);
                        break;
                    case 4:
                        cardMapList = cardMap.get(4);
                        cardMapList.add(lastCard);
                        cardMap.replace(4,cardMapList);
                        break;
                    default:
                        throw new RuntimeException("cardSameNumber wrong");
                }
                cardSameNumber = 1;
            }
            lastCard = card/4+3;
        }
    }
    /*
    * 自动出牌
    * */
    public int[] autoCard() throws Exception {
        int[] lastPlayerCards = new int[]{0,-1,-1};
        for (int i=1;i<=6;i++){
            lastPlayerCards[0]=i;
            int[] result = sendCard(lastPlayerCards);
            if (result != null){
                return result;
            }
        }
        return null;
    }

    /*
    * 真人操作
    * */
    public void playerCardType(String[] split,int[] lastPlayerCards) throws Exception {
        List<Integer> sendCards = new ArrayList<>();
        for (int i=0;i<split.length;i++){
            sendCards.add( Integer.parseInt(split[i]) );
        }
        //排序
        sendCards.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        //判断出牌类型
        Integer cardsType=0;
        Map<Integer,List<Integer>> sendCardMap = new Hashtable<>();
        Integer typeCard = -1;
        try {
            orderCard(sendCards,sendCardMap);
            int typeNumber=0;
            for (int i : sendCardMap.keySet()){
                List<Integer> integers = sendCardMap.get(i);
                if (integers != null && integers.size()>0){
                    typeNumber++;
                    cardsType = i;
                }
                //连对，不支持
                if (integers.size()>1){
                    throw new RuntimeException("出牌不符合规范2");
                }
            }
            //多于两种类型，不对
            if (typeNumber>2){
                throw new RuntimeException("出牌不符合规范3");
            }
            if (lastPlayerCards != null) {
                //判断是否可以接上
                //是否是同一类型
                if (cardsType != lastPlayerCards[0]) {
                    throw new RuntimeException("出牌不符合规范4");
                }
                //是否接的上
                typeCard = sendCardMap.get(cardsType).get(0);
                if (lastPlayerCards[1] >= typeCard) {
                    throw new RuntimeException("出牌不符合规范5");
                }
            }
        } catch (RuntimeException e) {
            System.out.println(name+": 出牌不符合规范6");
            e.printStackTrace();
            return;
        }
        //出牌
        showCards();
        sendPlayerType(cardsType,typeCard,sendCards);
        showCards(new int[]{cardsType,typeCard});
        //是否赢了
        if (cards.size()==0){
            System.out.println(name+": 我赢了");
        }
    }

    /*
    * 接牌
    * */
    public int[] sendCard(int[] lastPlayerCards) throws Exception {
        Integer sendCard;
        switch (lastPlayerCards[0]){
           //单张
           case 1:
               sendCard = sendSingleType(1, lastPlayerCards[1]);
               if (sendCard != -1){
                   return new int[]{1,sendCard};
               }
               break;
           //一对
           case 2:
               sendCard = sendSingleType(2, lastPlayerCards[1]);
               if (sendCard != -1){
                   return new int[]{2,sendCard};
               }
               break;
           //三张
           case 3:
               sendCard = sendSingleType(3, lastPlayerCards[1]);
               if (sendCard != -1){
                   return new int[]{3,sendCard};
               }
               break;
           //四张
           case 4:
               sendCard = sendSingleType(4, lastPlayerCards[1]);
               if (sendCard != -1){
                   return new int[]{4,sendCard};
               }
               break;
            //三带一
            case 5:
                int[] type4 = sendDoubleType(3,1,lastPlayerCards[1]);
                if (type4 != null) {
                    type4[0] = 4;
                    return type4;
                }
                break;
           //四带二
           case 6:
               int[] type6 = sendDoubleType(4,2,lastPlayerCards[1]);
               if (type6 != null) {
                   type6[0] = 6;
                   return type6;
               }
               break;
           default:
               throw new Exception("wrong lastPlayerCards");
       }
       //没有同类型的牌，且不是四带二，考虑炸弹
       if (lastPlayerCards[0] != 6){
           sendCard = sendSingleType(4, lastPlayerCards[1]);
           if (sendCard != -1){
               return new int[]{4,sendCard};
           }
       }
       return null;
    }

    /*
    * 单类型，单张，一对，三张，四张
    * */
    public int sendSingleType(Integer cardType,Integer lastCard){
        List<Integer> myCards = cardMap.get(cardType);
        if (myCards.size()>0){
            for (int i=0;i<myCards.size();i++){
                Integer card = myCards.get(i);
                if (card > lastCard){
                    //记牌器去掉打出去的牌
                    myCards.remove(i);
                    cardMap.put(cardType,myCards);
                    for (int j=cards.size()-1;j>=0;j--){
                        int result = cards.get(j);
                        for (int k =0;k<4;k++){
                            if (result == (card-3)*4 + k){
                                cards.remove(j);
                            }
                        }
                    }
                    return card;
                }
            }
        }
        return -1;
    }
    /*
    * 组合牌，三带一，四带二
    * */
    public int[] sendDoubleType(Integer cardTypeMain,Integer cardTypeMinor,Integer lastCard){
        List<Integer> cardTypeMains = cardMap.get(cardTypeMain-2);
        //是否有主牌
        if (cardTypeMains.size() == 0){
            return null;
        }else{
            for (int i=0;i<cardTypeMains.size();i++){
                Integer cardMain = cardTypeMains.get(i);
                //主牌是否可以出
                if (cardMain>lastCard){
                    List<Integer> cardTypeMinors = cardMap.get(cardTypeMinor);
                    //是否有副牌
                    if (cardTypeMinors.size() == 0){
                        return null;
                    }else{
                        //记牌器去掉打出去的牌
                        Integer cardMinor = cardTypeMinors.get(0);
                        cardTypeMains.remove((Object)cardMain);
                        cardTypeMinors.remove((Object)cardMinor);
                        cardMap.put(cardTypeMain-2,cardTypeMains);
                        cardMap.put(cardTypeMinor,cardTypeMinors);
                        for (Integer j=0;j<cardTypeMain-2;j++){
                            cards.remove((Object)((cardMain-3)*4+j));
                            cards.remove((Object)((cardTypeMain-3)*4+j));
                        }
                        for (int j=cards.size()-1;j>=0;j--){
                            for (Integer k=0;k<4;k++){
                                int result = cards.get(j);
                                if (result == (cardMain-3)*4+k){
                                    cards.remove(j);
                                }
                                if (result == (cardTypeMain-3)*4+k){
                                    cards.remove(j);
                                }
                            }
                        }
                        return new int[]{0,cardMain,cardMinor};
                    }
                }
                return null;
            }
            return null;
        }
    }

    //真人出牌
    private void  sendPlayerType(Integer cardType,Integer typeCard,List<Integer> sendCards){
        List<Integer> myCards = cardMap.get(cardType);
        myCards.remove(typeCard);
        cardMap.put(cardType,myCards);
        cards.removeAll(sendCards);
    }
    /*
    * 展示牌
    * */
    public void showCards(int[] myCards){
        String cardOne = changeCardShow(myCards[1]);
        String cardTwo = "";
        if (myCards.length == 3){
            cardTwo = changeCardShow(myCards[2]);
        }
        //展示打出的牌
        switch (myCards[0]){
            case 1:
                System.out.print(name+": 一个 "+cardOne);
                System.out.println("");
                break;
            case 2:
                System.out.print(name+": 一对 "+cardOne+cardOne);
                System.out.println("");
                break;
            case 3:
                System.out.print(name+": 三个 "+cardOne+cardOne+cardOne);
                System.out.println("");
                break;
            case 4:
                System.out.print(name+": 炸弹 "+cardOne+cardOne+cardOne+cardOne);
                System.out.println("");
                break;
            case 5:
                System.out.print(name+": 三带一 "+cardOne+cardOne+cardOne+cardTwo);
                System.out.println("");
                break;
            case 6:
                System.out.print(name+": 四带二 "+cardOne+cardOne+cardOne+cardOne+cardTwo+cardTwo);
                System.out.println("");
                break;
        }
    }
    /*
    * 展示现有牌
    * */
    public void showCards(){
        //展示现有的牌
        System.out.print(name+": ");
        for (Integer card : cards){
            System.out.print(changeCardShow(card/4+3));
        }
        System.out.println("");
    }
    /*
    * 牌转换
    * */
    public String changeCardShow(Integer card){
        switch (card){
            case 11:
                return " J ";
            case 12:
                return " Q ";
            case 13:
                return " K ";
            case 14:
                return " A ";
            case 15:
                return " 2 ";
            default:
                return " "+card.toString()+" ";
        }
    }
}

