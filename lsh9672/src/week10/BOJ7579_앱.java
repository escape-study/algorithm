package week10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/**
 * 아이디어
 * 배낭문제랑 유사하다고 생각했다.
 * dp를 이용한다.
 * 각 dp 배열 인덱스는 비활성화 하는 메모리라고 생각하면 된다.
 * 가령 인덱스가 50이면, 메모리 50을 비활성화 하는데에 필요한 코스트가 저장되는 것이다.
 *
 * (추가)
 * 참고함.
 * 인덱스를 메모리 사이즈로 하면 1천만이고, 100가지 경우 모두 보면 10억으로 터져버린다.
 * 따라서 해당 인덱스를 비용으로 하고, 값을 해당 비용만큼 썼을때 줄일 수 있는 최대 크기로 한다.
 */
public class BOJ7579_앱{

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] mArray = new int[N];
        int[] cArray = new int[N];

        StringTokenizer mInput = new StringTokenizer(br.readLine());
        StringTokenizer cInput = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            mArray[i] = Integer.parseInt(mInput.nextToken());
            cArray[i] = Integer.parseInt(cInput.nextToken());
        }

        //dp배열 생성, cost로 함. - 최대 cost는 100, 총 앱의 수는 100이므로 10000까지 나올 수 있다.
        int[] dp = new int[10_000 + 1];

        for(int i = 0; i < N; i++) {
            for (int j = 10_000; j >= cArray[i]; j--) {

                if (j - cArray[i] < 0) continue;

                dp[j] = Math.max(dp[j], dp[j - cArray[i]] + mArray[i]);

            }
        }

        System.out.println(Arrays.toString(dp));


        for(int i = 0; i <= 10_000; i++){

            if(dp[i] < M) continue;

            System.out.println(i);
            break;
        }

    }


}
