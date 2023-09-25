package week05;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 아이디어
 * dp
 * 현 위치까지 만들수 있는 암호 수를 누적한다.
 * 암호는 현재 위치를 i라고 하면, i위치의 값을 알파벳으로 변환하는것과, i-1과 i를 합쳐서 알파벳으로 변환하는 것 두가지가 필요하다.
 * i위치를 알파벳으로 변환하는 경우에는 i - 1위치까지 누적된 값을 i에 저장해주면 된다.
 * i - 1와 i를 합쳐서 만들때는 i - 2까지 저장된 값을 i에 저장해주면 된다.
 * 이때 중요한 점은, i번째가 0일때와 0이 아닐때 구분을 해줘야 한다.
 * 또한 두 문자를 합쳐서 범위내의 숫자가 되는지도 확인해야 한다.
 */

public class BOJ2011_암호코드 {

    //두 수가 주어졌을때, 변환이 가능한지 확인하는 메서드
    private static boolean check(char chr1, char chr2){

        //해당 문자을 숫자로 변경
        int num1 = Character.getNumericValue(chr1) * 10;
        int num2 = Character.getNumericValue(chr2);

        int totalNum = num1 + num2;

        //앞자리 수가 0이면 합칠수 없음
        if(num1 == 0) return false;

        //합친 수가 10이상, 26이하여야 함.
        if(totalNum < 10 || totalNum > 26) return false;

        return true;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //암호코드 입력 받기
        String code = br.readLine();

        //변환가능 개수를 저장할 배열
        int[] dp = new int[code.length()];

        //첫번째 값이 0이면 잘못된 것.
        if(code.charAt(0) == '0'){
            System.out.println(0);
            return;
        }

        //첫번째가 0이 아닌데 문자 길이가 1이면 1출력
        if(code.length() == 1){
            System.out.println(1);
            return;
        }

        //dp 배열 초기값 넣기.
        dp[0] = 1;


        //1번째 수일때 초기값
        if(code.charAt(1) != '0'){
            dp[1] = 1;
        }

        //앞의 수와 합쳤을때 만들어지면 +1
        if(check(code.charAt(0), code.charAt(1))){
            dp[1]++;
        }


        boolean flag = false;

        //반복문 돌면서 dp배열 채우기
        for(int i = 2; i < code.length(); i++){

            //해당 위치의 문자와 이전문자
            char prevChr = code.charAt(i - 1);
            char currentChr = code.charAt(i);

            //문자가 0일때 - 스스로는 변환이 불가능하고 i - 1일때와 합쳐서 만들 수 있음
            if(currentChr == '0'){

                //i - 1 값과 합쳤을 때 만들 수 없으면 변환 불가
                if(!check(prevChr, currentChr)){
                    flag = true;
                    break;
                }

                //dp[i - 2]에 누적된 값 저장.
                dp[i] = dp[i - 2];
            }

            //문자가 0이 아닐때, 합치는 것과 i번째 하나로 변환하는 것, 둘다 가능.
            //이 케이스에서는 잘못된 암호 케이스를 잡아낼 수 없음.
            //합쳐서 구성이 불가능하더라도 한개씩 변환하면 가능하기 떄문.
            else{

                //두개 합쳐서 변환이 가능하면, 두 경우의 합을 저장.
                if(check(prevChr, currentChr)){

                    dp[i] = (dp[i - 1] + dp[i - 2]) % 1000000;
                }
                //두개 합쳐서 변환이 불가능하면 i - 1번째 까지 누전된 값 저장.
                else{
                    dp[i] = dp[i - 1];
                }

            }
        }

        //출력
        if(flag){
            System.out.println(0);
        }
        else{
            System.out.println(dp[code.length() - 1] % 1000000);
        }

    }
}
