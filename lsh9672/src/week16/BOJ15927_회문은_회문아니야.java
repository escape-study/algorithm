package week16;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 아이디어.
 * 슬라이딩 윈도우.
 * 데이터 크기가 작기 떄문에 슬라이딩 윈도우로 완전탐색을 할수 있다.
 * 최대 길이 1~N 까지 반복하고, 각 경우에 대해서 N ~ 1번 반복하며, 각 반복마다 회문검사를 위해서 주어진 문자열의 절반 정도만큼 사용해야 한다.
 * ㄷ대략적으로 계산하면, N * (N - N) / 2 + (N - 1) * (N - (N - 1))/2 .... 1 * (N - 1)/2
 * 각 경우 N N-1...이 뒤에 곱해지는 수는 N을 넘을수가 없다, 즉 잘해봐야 N/2가 된다.
 * 결국 대략적으로 계산했을때 (N + N - 1 + ....1) * (N/2) 로 계산할수 있고 이는 (N(N+1)/2) * (N/2) => 대략 O(N^3)이라고 생각할 수 있다.
 * N의 최대값은 50이므로 50^3 = 125000정도로 충분히 계산이 가능한 범위가 된다.
 *
 * (추가)
 * 데이터가 50이 아니라 50만이하 이므로 N^2만 나와도 터진다.
 * 즉 NlogN 이하로 떨어뜨려야 한다.
 * 너무 어렵게 생각했다.
 * 그냥 처음에 주어진 문자열이 펠린드롬인지 판단하고 아니면 그대로 출력, 맞다면, 전체 길이 -1을 하면 된다.
 * 펠린드롬이라면 문자열 하나를 빼면 펠린드롬이 깨지기 때문이다.
 *
 * 단 모든 문자열이 같은 경우는 제외 해야 한다.
 *
 */

public class BOJ15927_회문은_회문아니야 {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputStr = br.readLine();


        int startIndex = 0;
        int endIndex = inputStr.length() - 1;

        boolean flag = false;
        while(startIndex < endIndex){

            //두값이 다르면 팰린드롬이 아님.
            if(inputStr.charAt(startIndex) != inputStr.charAt(endIndex)){
                System.out.println(inputStr.length());
                return;
            }
            //두값이 같을때는 모든 값이 같을 수도 있기 때문에 그 다른 값을 확인해봐야 함.
            else if(inputStr.charAt(startIndex + 1) != inputStr.charAt(startIndex)){
                flag = true;
            }

            startIndex++;
            endIndex--;

        }

        if(flag){
            System.out.println(inputStr.length() - 1);
        }
        else{
            System.out.println(-1);
        }
    }
}
