package week27;

import java.util.Arrays;

/**
 * 아이디어
 * 중복조합
 * 이모티콘에 할인률을 얼마를 지정해야 가입자를 가장 많이 뽑을수 있는지 알수 없기 때문에 완전탐색으로 다 뽑아본다.
 * 모든 이모티콘에 할인률을 적용헀으면, 고객들이 가지고 있는 기준정보를 가지고 임티플과 임티판매금액을 계산해본다.
 * 임티플을 최소치로 산다라고 했으면, 이모티콘 하나에 할인률 적용할때마다 계산해서, 임티플 개수가 이전에 저장된,
 * 최소치를 넘어가면 가지치기를 할수 있겠지만, 이경우에는 최대로 구매하는 경우라서, 가지치기가 불가능하다.
 * 남은 임티 할인률을 적용했을때, 임티플이 늘어날수도 있기 때문이다.
 * 할인률은 10,20,30,40로 네가지이고, 나올수 있는 이모티콘은 7개이다.
 * 각 임티가 고를수 있는 할인률은 4가지이므로 4^7가지의 경우의 수만 따져주면 된다.
 * 각 경우의 수마다, 계산해야 하는 유저 정보는 100이다.
 * 각 경우의 수마다 7개의 이모티콘을 전부 확인하는데, 확인하는 유저가 100이므로,
 * 뽑는데 필요한 수 * 뽑은 후 유저정보 확인 => 4^7 * (7 * 100) => 16384 * 700 => 11,468,800으로 충분히 완탐을 돌려도 되는 문제이다.
 * (1천만은 1초이내로 처리가능.)
 *
 * 소요시간 : 1시간
 */

public class Prog_이모티콘_할인행사 {

    //할인률
    private final static int[] stdDiscountRate = {10,20,30,40};

    private static int maxEmoticonPlusCount;//최대 이모티콘 개수

    private static int maxEmoticonSalePrice;//이모티콘 총 판매액

    //뽑아낸 할인률로 임티플과 판매량 계산하는 메서드
    private static void calculateEmoticon(int[] discountRate, int[] emoticons, int[][] users){

        int totalPrice = 0;
        int totalEmoticonPlus = 0;
        for(int[] user : users){

            int stdDiscount = user[0];
            int stdPrice = user[1];

            int userPrice = 0; //유저가 구매한 금액.

            for(int i = 0; i < emoticons.length; i++){

                //할인률이 구매 기준 햘인률보다 낮으면 패스
                if(discountRate[i] < stdDiscount) continue;

                userPrice += (emoticons[i] / 100) * (100 - discountRate[i]); //기준 할인률보다 높으면 구매
            }

            //총 구매금액이 기준 금액을 넘으면 임티플로 전환.
            if(userPrice >= stdPrice) totalEmoticonPlus++;

            else totalPrice += userPrice;



        }



        //기존의 임티플과 비교 - 기존 임티플 개수보다 새로구한게 많으면 업데이트.
        if(maxEmoticonPlusCount < totalEmoticonPlus){

            maxEmoticonPlusCount = totalEmoticonPlus;
            maxEmoticonSalePrice = totalPrice;
        }
        //임티플 개수가 같고, 가격이 더 크면 업데이트
        else if(maxEmoticonPlusCount == totalEmoticonPlus && maxEmoticonSalePrice < totalPrice){
            maxEmoticonSalePrice = totalPrice;
        }


    }

    //재귀호출하면서 이모티콘 할인률 조합을 뽑아낼 메서드
    private static void recursive(int index, int[] discountRate, int[] emoticons, int[][] users){

        //이모티콘 개수만큼 뽑았으면 계산.
        if(index == emoticons.length){
            calculateEmoticon(discountRate, emoticons, users);
            return;
        }

        for(int i = 0; i < 4; i++){
            discountRate[index] = stdDiscountRate[i]; //할인률 설정.
            recursive(index + 1, discountRate, emoticons, users);
        }

    }


    public int[] solution(int[][] users, int[] emoticons) {
        maxEmoticonPlusCount = 0;
        maxEmoticonSalePrice = 0;

        recursive(0, new int[emoticons.length],emoticons, users);

        return new int[]{maxEmoticonPlusCount, maxEmoticonSalePrice};
    }

    public static void main(String[] args) {
        Prog_이모티콘_할인행사 s = new Prog_이모티콘_할인행사();

        int[][] users1 = {{40, 10000}, {25, 10000}};
        int[] emoticons1 = {7000, 9000};
        System.out.println(Arrays.toString(s.solution(users1, emoticons1)));

        int[][] users2 = {{40, 2900}, {23, 10000}, {11, 5200}, {5, 5900}, {40, 3100}, {27, 9200}, {32, 6900}};
        int[] emoticons2 = {1300, 1500, 1600, 4900};
        System.out.println(Arrays.toString(s.solution(users2, emoticons2)));

    }
}
