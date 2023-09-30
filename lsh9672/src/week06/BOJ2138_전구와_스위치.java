package week06;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 그리디문제.
 * 여러 글에서 힌트를 얻어서 해결했다
 * 첫번째 전구는 +1번째에 영향을 주지 않는다.
 * 따라서 첫번째 전구를 킬때와 안킬때 두가지 경우로 보면 된다.
 * 각 상태를 볼때는 i번째 전구를 켰을때 i-1번째 값이 변화하게 되는데, 이 값이 답과 맞는지 보면 된다.
 * i가 증가하는 방향으로 보기때문에 i+1이 변화하는 것은 다음 반복에서 처리하도록 한다.
 */

public class BOJ2138_전구와_스위치 {

    //전구 수
    private static int N;

    //초기 전구 상태
    private static String initBulb;

    //만들고자 하는 전구 상태.
    private static String finishedBulb;

    //최종적으로 구할 변횐 회수
    private static int minCount;

    //토글 시키는 메서드
    private static char toggle(char chr){

        return chr == '0' ? '1' : '0';
    }

    //반복문 돌면서 전구 상태를 변화시켜감.
    private static void changeStatus(boolean firstCheck){

        char[] bulbArray = initBulb.toCharArray();

        int count = 0;

        //첫번째 값을 킴
        if(firstCheck){
            bulbArray[0] = toggle(bulbArray[0]);
            bulbArray[1] = toggle(bulbArray[1]);
            count++;
        }

        for(int i = 1; i < N; i++){

            //i-1번째 값이 목표 문자열의 i - 1번째 값과 같으면 패스
            if(bulbArray[i - 1] == finishedBulb.charAt(i - 1)) continue;

            bulbArray[i - 1] = toggle(bulbArray[i - 1]);
            bulbArray[i] = toggle(bulbArray[i]);
            if(i < N - 1){
                bulbArray[i + 1] = toggle(bulbArray[i + 1]);
            }

            count++;

        }

        if(finishedBulb.charAt(N - 1) == bulbArray[N - 1]){
            minCount = Math.min(minCount, count);
        }

    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        initBulb = br.readLine();
        finishedBulb = br.readLine();
        minCount = Integer.MAX_VALUE;

        changeStatus(false);
        changeStatus(true);


        if(minCount == Integer.MAX_VALUE){
            System.out.println(-1);
        }
        else{
            System.out.println(minCount);
        }

    }
}
