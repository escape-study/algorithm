package week04;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 아이디어
 *
 * 각 알파벳의 개수를 센다.
 * 알파벳의 수가 K개 이상인 알파벳만 후보로 둔다.
 * 후보로 고른 알파벳의 위치부터 탐색을 시작한다.
 * 반복문을 돌면서 해당 알파벳의 개수를 센다.
 * K개에 도달하면 길이를 저장한다.
 */
public class BOJ20437_문자열게임2 {

    //문자열 정보를 저장할 객체
    private static class Info{
        private int count;//문자 개수
        private List<Integer> location;//문자 위치.

        public Info(int count, int index){
            this.count = count;

            location = new ArrayList<>();
            location.add(index);
        }

        public void addLocation(int index){
            this.count++;
            this.location.add(index);
        }

        public int getCount() {
            return this.count;
        }

        public List<Integer> getLocation() {
            return this.location;
        }
    }

    //포함해야할 문자개수
    private static int K;

    //문자열 저장할 변수
    private static String inputStr;

    //문자의 수를 세서 저장할 맵
    private static Map<Character, Info> charInfo;

    //어떤 문자를 정확히 K개 포함하는 가장짧은 연속 문자열 길이 + //어떤 문자를 정확히 K개 포함하고, 첫번째문자와 마지막문자가 해당 문자로 동일한 가장 긴 연속 문자열.
    private static int[] shortestAndLongestLength(){

        int[] resultArray = {-1,-1}; //0 : 가장짧은 문자열, 1: 가장 긴 문자열.

        int minLength = 10001;
        int maxLength = -1;

        //저장해둔 문자정보 맵에서 문자 개수가 K개 이상인것만 선별
        for(char chr : charInfo.keySet()){

            Info tempInfo = charInfo.get(chr);

            if(tempInfo.getCount() < K) continue;

            //해당 문자열의 길이 재기
            List<Integer> tempList = tempInfo.getLocation();
            for(int i = 0; i <= tempInfo.getCount() - K; i++){

                minLength = Math.min(minLength, tempList.get(i + K - 1) - tempList.get(i) + 1);
                maxLength = Math.max(maxLength, tempList.get(i + K - 1) - tempList.get(i) + 1);

            }
        }

        resultArray[0] = minLength;
        resultArray[1] = maxLength;

        return resultArray;
    }


    //반복문을 돌면서 K개 이상의 알파벳을 찾는 메서드
    private static void init(){

        charInfo = new HashMap<>();

        for(int i = 0; i < inputStr.length(); i++){

            char chr = inputStr.charAt(i);

            if(!charInfo.containsKey(chr)){
                charInfo.put(chr, new Info(1, i));
            }
            else{
                Info temp = charInfo.get(chr);
                temp.addLocation(i);
            }
        }
    }
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder result = new StringBuilder(); //결과 출력을 위한 스트링빌더

        int T = Integer.parseInt(br.readLine());

        for(int testCase = 0; testCase < T; testCase++){
            inputStr = br.readLine();
            K = Integer.parseInt(br.readLine());

            init(); //문자 정보 맵에 담기

            int[] lengthArray = shortestAndLongestLength();

            if(lengthArray[1] == -1){
                result.append(-1);
            }
            else{
                result.append(lengthArray[0]).append(" ").append(lengthArray[1]);
            }

            if(testCase != T - 1){
                result.append("\n"); //케이스 하나끝날때마다 줄바꿈.
            }
        }



        System.out.println(result);
    }
}
