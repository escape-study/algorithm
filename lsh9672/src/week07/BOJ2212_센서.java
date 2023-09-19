package week07;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그리디 문제.
 * 각 센서별 구간을 구한다.
 * 집중국이 수신가능한 영역은 센서별 구간의 합이다 -> 1개일때
 * 그렇다면 이 수신가능한 영역의 합이 최소가 되려면, 가장 큰값을 빼주면 된다.
 * 집중국이 1개라면 수신가능한 영역의 길이의 합은 센서간 구간의 합이다.
 * 2개가 된다면, 영역을 두개로 나눌 수 있다. 즉, 가장 긴 구간 하나를 스킵할 수 있다는 뜻이된다.
 * 3개가 된다면 영역을 3개로 나눌 수 있다. 즉, 가장 긴 구간 2개를 스킵할 수 있다는 뜻이된다.
 * 예제를 보면 1 3 6 6 7 9 센서가 존재한다.
 * 이 센서간 구간길이를 구하면, 2 3 0 1 2가 된다.
 * 만약 집중국이 하나라면 모든 센서를 수신할 수 있는 영역의 길이의 합은 전체 합이 된다.
 * 하지만 집중국이 두개면 영역을 분리 할 수 있기 때문에 이중 구간하나를 제거할 수 있다
 * 합이 최소가 되야 하기 떄문에 가장 큰 값인 3을 제거하면 된다 -> 답 5
 */
public class BOJ2212_센서 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());

        int result = 0;

        int[] sensorArray = new int[N]; //센서위치 정보 저장할 배열
        int[] sectionArray = new int[N - 1]; //센서 구간 값 저장할 배열

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            sensorArray[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(sensorArray);

        //구간 값 저장.
        int sectionIndex = 0;
        for(int i = 1; i < N; i++){
            sectionArray[sectionIndex++] = Math.abs(sensorArray[i] - sensorArray[i - 1]);
        }

        //구간값 정렬
        Arrays.sort(sectionArray);
        for(int i = 0; i < N - 1 - (K - 1); i++){
            result += sectionArray[i];
        }

        System.out.println(result);

    }
}
