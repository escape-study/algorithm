package week26;

import java.util.*;

/**
 * 아이디어
 * 비트마스킹 + 완탐
 * 손님이 주문한 단품메뉴 조합을 반복문으로 돌면서 알파벳을 뽑아낸다.
 * 해당 알파벳으로 코스 종류에 맞는 조합을 만들고 기존의 메뉴들과 비교해서 몇번 등장했는지 확인한다.
 * 이때, 비트마스킹으로 해결할 수 있다.
 * A~Z를 각 비트로 나타내면 손님이 주문한 단품메뉴에 포함이 되어있는지 and연산을 이용해서 쉽게 찾아 낼 수 있다.
 * A면 1에서 0비트를 밀고, z면  (Z - A)비트만큼 옆으로 밀어서 표시할 수 있다.
 *
 * (추가)
 * 모든 order를 돌면서, 존재하는 알파벳 리스트를 만드슨 식으로 했는데,
 * 굳이 그럴 필요 없이, 각 order에서 course 길이의 조합을 뽑아내고 이를 맵에 저장하는 식으로 처리 할 수 있다.
 * 그러면 초기 작업에 시간을 많이 들이지 않아도 된다.
 * 또한 굳이 조합을 구하고 order의 값들과 비교 할 필요가 없어 훨씬 효율적이다.
 *
 */

public class Prog_메뉴리뉴얼 {

    //메뉴별 등장횟수 저장할 맵
    private static Map<String, Integer> countMap;

    //등장 알파벳 리스트
    private static List<Character> alphaList;
    //주문한 단품 메뉴를 비트로 표현한 배열
    private static int[] ordersBit;

    //orders를 읽어서 등장한 알파벳 리스트 뽑는 메서드
    private static void makeAlphaList(String[] orders){

        Set<Character> alphaSet = new HashSet<>();
        for(String order : orders){

            for(int i = 0; i < order.length(); i++){
                char chr = order.charAt(i);

                if(alphaSet.contains(chr)) continue;
                alphaSet.add(chr);
            }
        }

        alphaList.addAll(alphaSet);
        Collections.sort(alphaList); //앞선 알파벳부터 뽑기 위해 정렬한번 해줌.
    }

    //orders를 비트로 표현하는 메서드
    private static void makeOrdersBit(String[] orders) {

        for (int i = 0; i < orders.length; i++) {
            int bit = 0;
            for (int j = 0; j < orders[i].length(); j++) {
                bit |= (1<<(orders[i].charAt(j) - 'A'));
            }

            ordersBit[i] = bit;
        }
    }

    //주문 조합들과 비교해서 몇개 맞는지 확인.
    private static int getOrderCount(int currentBit){
        int returnCount = 0;

        for(int bit : ordersBit){

            if(currentBit != (bit&currentBit)) continue;

            returnCount++;
        }

        return returnCount;
    }


    //재귀호출하면서 조합만들기.
    private static void recursive(int index, int currentBit, StringBuilder currentStr, int targetValue){


        //타켓개수만큼 뽑았으면 확인
        if(currentStr.length() == targetValue){
            //몇명의 손님으로 부터 주문되었는지 확인
            int orderCount = getOrderCount(currentBit);
            //최소 2명이상의 손님이 시킨 메뉴만 저장.
            if(orderCount >= 2){
                countMap.put(currentStr.toString(), orderCount);
            }
            return;
        }

        for(int i = index; i < alphaList.size(); i++){
            char tempAlpha = alphaList.get(i);

            currentStr.append(tempAlpha);
            recursive(i + 1, currentBit | (1<<(tempAlpha - 'A')), currentStr, targetValue);
            currentStr.setLength(currentStr.length() - 1);
        }

    }

    public String[] solution(String[] orders, int[] course) {

        List<String> answerList = new ArrayList<>();

        countMap = new HashMap<>();
        alphaList = new ArrayList<>();
        ordersBit = new int[orders.length];

        makeAlphaList(orders);
        makeOrdersBit(orders);

        for(int i = 0; i < course.length; i++){

            recursive(0, 0, new StringBuilder(), course[i]);
            //맵에 저장된 값들중에 최대값 넣기.
            int maxValue = -1;
            for(String key : countMap.keySet()){

                if(key.length() != course[i]) continue;

                maxValue = Math.max(maxValue, countMap.get(key));
            }

            //최대값에 해당하는 키값을 리스트에 저장.
            for(String key : countMap.keySet()){

                if(key.length() != course[i] || countMap.get(key) != maxValue) continue;

                answerList.add(key);
            }

        }

        String[] answer = new String[answerList.size()];
        for(int i = 0; i < answerList.size(); i++){
            answer[i] = answerList.get(i);
        }
        Arrays.sort(answer);

        return answer;
    }

    public static void main(String[] args) {
        Prog_메뉴리뉴얼 s = new Prog_메뉴리뉴얼();
        String[] orders1 = {"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"};
        int[] course1 = {2,3,4};
        System.out.println(Arrays.toString(s.solution(orders1,course1)));

        String[] orders2 = {"ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD"};
        int[] course2 = {2,3,5};
        System.out.println(Arrays.toString(s.solution(orders2,course2)));

        String[] orders3 = {"XYZ", "XWY", "WXA"};
        int[] course3 = {2,3,4};
        System.out.println(Arrays.toString(s.solution(orders3,course3)));
    }
}
