package week27;

import java.util.HashMap;
import java.util.Map;

/**
 * 아이디어
 * 구현
 * 이차원 배열을 이용해서 A -> B로 준 선물, B -> A로 준선물을 저장한다.
 * 행은 해당 행번호에 해당하는 친구가 준 선물의 수가 되고, 열은 해당 열번호에 해당하는 친구가 받은 선물이 된다.
 * 선물지수 계산을 위해서 각 행과 열을 한칸씩 더 확장해서 받은선물, 준 선물의 수를 합산해둔다.
 * 배열의 경우 인덱스를 사용해야 하는데, 주어진 친구는 string으로 주어졌기 때문에 map을 이용해서 각 이름마다 인덱스를 매핑해둔다.
 * 선물지수 : (준 선물) - (받은 선물)
 *
 * (주의)
 * 각 사람별 선물수를 계산할때, 2차원 배열로 하게 되면, i == j일수 있는데, 이 경우는 제외 해야 한다.(자기 자신이므로,)
 * 소요 시간 : 아이디어 구상 및 구현 40분
 */
public class Prog_가장_많이_받은_선물 {

    //배열 길이
    private static int n;

    //이름과 인덱스를 매핑할 맵
    private static Map<String, Integer> nameMapping;


    //준선물 받은 선물을 저장할 이차원 배열.
    private static int[][] giftInfo;

    //선물이력을 확인해서 배열채우기.
    private static void setGiftInfo(String[] gifts){

        for(String gift : gifts){
            String[] info = gift.split(" "); //공백을 기준으로 나눔

            //주는 사람과 받는사람 인덱스 뽑기
            int giverIndex = nameMapping.get(info[0]);
            int receiverIndex =  nameMapping.get(info[1]);

            //배열 채우기.
            giftInfo[giverIndex][receiverIndex]++;

            //총 준 개수와 받은개수 저장.
            giftInfo[giverIndex][n]++; //총 준 개수 증가
            giftInfo[n][receiverIndex]++; //총 받은 개수 증가.

        }

    }

    //반복문 돌면서 각 친구별, 다음달에 가장 선물을 많이 받는 사람 구하기.
    private static int getNextMaxGift(){

        int maxGiftCount = 0;
        for(int i = 0; i < n; i++){

            int giftCount = 0;
            for(int j = 0; j < n; j++){

                //같을때는 같은 사람이므로 계산안함.
                if(i == j) continue;

                //선물을 주고 받은 기록이 없거나, 같은 개수의 선물을 주고 받았다면,
                if(giftInfo[i][j] == giftInfo[j][i]){

                    int stdValue = giftInfo[i][n] - giftInfo[n][i];//구하려고 하는 i의 선물지수 계산.
                    int compareValue = giftInfo[j][n] - giftInfo[n][j];//비교하려고 하는 j의 선물지수 계산.
                    //선물지수가 클때만 선물을 받음.
                    if(stdValue <= compareValue) continue;

                    giftCount++;
                }

                //선물을 주고 받은 기록이 있고, 개수가 다르고, i(준 쪽)가 j(받은 쪽)의 값보다 더 크다면 선물을 받음.
                else if(giftInfo[i][j] > giftInfo[j][i]) giftCount++;

            }

            maxGiftCount = Math.max(maxGiftCount, giftCount);

        }

        return maxGiftCount;

    }

    //초기화
    private static void init(String[] friends){

        //배열 길이 셋팅
        n = friends.length;

        nameMapping = new HashMap<>();
        //이름과 인덱스 매핑
        for(int i = 0; i < friends.length; i++){
            nameMapping.put(friends[i], i);
        }

        giftInfo = new int[n + 1][n + 1];//이번달 선물 정보 배열
    }

    public int solution(String[] friends, String[] gifts) {
        int answer = 0;

        //초기 값 설정
        init(friends);
        //선물정보를 받아서 배열 셋팅
        setGiftInfo(gifts);

        return getNextMaxGift();
    }

    public static void main(String[] args) {
        Prog_가장_많이_받은_선물 s = new Prog_가장_많이_받은_선물();

        String[] friends1 = {"muzi", "ryan", "frodo", "neo"};
        String[] gifts1 = {"muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi"};
        System.out.println(s.solution(friends1, gifts1));

        String[] friends2 = {"joy", "brad", "alessandro", "conan", "david"};
        String[] gifts2 = {"alessandro brad", "alessandro joy", "alessandro conan", "david alessandro", "alessandro david"};
        System.out.println(s.solution(friends2, gifts2));

        String[] friends3 = {"a", "b", "c"};
        String[] gifts3 = {"a b", "b a", "c a", "a c", "a c", "c a"};
        System.out.println(s.solution(friends3, gifts3));


    }


}
