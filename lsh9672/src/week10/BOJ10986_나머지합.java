package week10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 연속된 구간의 합이므로 누적합을 이용했다.
 * 누적합 배열을 SUM이라고 하면
 * M으로 나눠서 나눠 떨어지는 구간은 (SUM[j] - SUM[i - 1]) % M = 0 이다
 * 분배법칙을 이용하면, ((SUM[j] % M - SUM[i - 1] % M) + M) % M = 0 이다
 * 해당 수식이 0이 되려면 (SUM[j] % M - SUM[i - 1] % M) + M 이 부분이 M의 배수가 되어야 한다.
 * 해당 부분이 M의 배수가 되려면 (SUM[j] % M - SUM[i - 1] % M) 이 값이 M의 배수 또는 0이어야 한다.
 * 하지만 해당 부분을 잘 생각해보면 모듈러 연산으로 나올수 있는 값은 0~(M-1) 이다
 * 그렇기 떄문에 해당 값의 최대값은 (M - 1)이다.
 * 즉 해당 값은 0이다.
 * 결론적으로 이와 같은 수식이 나온다 => SUM[j] % M == SUM[i - 1] % M
 *
 *(추가)
 * 단순 이중 반복문은 시간이 무조건 터지는데 개선아이디어가 생각 안나서 참고함.
 * 조합을 이용하는 방법 사용.
 */
public class BOJ10986_나머지합 {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        long[] sumArray = new long[N]; // 누적값을 M으로 나눈 배열.
        long[] countArray = new long[M]; // 나올 수 있는 수의 가지수를 저장(0 ~ (M-1))

        st = new StringTokenizer(br.readLine());
        long tempSum = 0;
        for(int i = 0; i < N; i++){

            int inputValue = Integer.parseInt(st.nextToken());
            tempSum += inputValue;
            sumArray[i] = tempSum % M;

            countArray[(int)sumArray[i]]++;//수를 나눈 나머지들의 개수를 저장
        }


        long result = countArray[0]; //0~ i 까지 인 경우 포함.

        //각 뽑은 수들중에서 2가지를 뽑는 경우의 수 countArray[i]C2
        for(int i = 0; i < M; i++){

            if(countArray[i] <= 1) continue;

            result += countArray[i] * (countArray[i] - 1) / 2;
        }


        System.out.println(result);








    }
}
