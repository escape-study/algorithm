package week14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * (초기 구상)
 * 누적합을 구하고, 부분합을 이용하려고 했다.
 * 구하고자 하는 수를 1부터 증가시키면서 해당 수를 부분합에서 구할 수 있는지를 보려고 했다
 * 이 방법은 직관이고,구하고자 하는 수가 포함되는 최소의 누적합 위치안에서 부분합을 반복적으로 수행해야 한다.
 * 가령, 누적합으로 구한 배열이 1,2,4,7 이고 구할수 있는지 확인하고 싶은 수가 3이라고 한다면, 3을 포함할 수 있는 누적합 4의 위치안에서
 * 부분합을 수행해야 한다. 최대로 나올 수 있는 수가 10^3 * 10^6 이므로 시간초과가 발생하게 된다.
 * (참고)
 * 누적합을 이용하는 것은 맞는데 신박한 방법을 사용한다.
 * 기본적으로 누적합이 7이라고 한다면, 7을 만들기 위해 사용된 원소들을 이용해서 7이하의 자연수를 모두 만들수 있다.(직접 해보면 그러하다)
 * 또한 다음에 더해야 할 원소가 현재까지의 누적합 + 1보다 크다면, 누적합+1은 구할수 없는 수이다.
 * 잘 생각해보면, 누적합 7이라는 것은, 해당 인덱스까지의 수를 선택했을때 만들수 있는 최대수가 7이라는 것이다.
 * 그다음에 오는 수가 +1한 8보다 큰 수가 나오면 8은 만들수 없다는 뜻이 된다.
 * 이러한 방법으로 바텀업 방식으로 인덱스 0부터 누적합을 구하면서 다음에 나오는 수가, 구한 누적합 + 1 초과라면 해당 수를 출력해주면 된다.
 */
public class BOJ2437_저울 {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[] numArray = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            numArray[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(numArray);

        int sum = 0;
        for(int i = 0; i < N; i++){

            //해당원소가 이전원소까지의 누적합+1 보다 크다면 누적합+1이 조건을 만족하는 최소 수임.
            if(sum + 1 < numArray[i]) break;

            sum += numArray[i];
        }

        System.out.println(sum + 1);
    }
}
