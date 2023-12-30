package week21;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 누적합 + 디피.
 * 소형 기관차는 연속된 객실을 끌수 있다, 이부분을 반복문으로 만들면, 매번 5만번을 돌아야 해서 시간초과가 날수도 있다.
 * 따라서 객실의 승객수를 구하기 위해 누적합배열을 만들어서 처리한다.
 * 행은 소형기관차 번호(최대 3까지)이고, 열은 객실번호이다.
 * 첫번째 소형기관차는 끌수 있는 최대 객차수(최대한 많이 손님을 끌라면 객차수를 최대로 채우는 것이 좋다.)부터 끝까지
 * 두번째 소형기관차는 이전 소형기관차가 끌수 있는 최대 객차수 + 1부터 시작
 * 세번째는 +2 부터 시작
 * 이런식으로 모든 경우를 다 구해주면 된다.
 *
 * 끌수 있는 객차수가 주어진 객차보다 크면 배열을 넘어간다 생각할수도 있는데,
 * 문제에서 끌수 있는 최대 객차수는 주어지는 객차수의 1/3보다 작다 했기 때문에,
 * 무조건 끌수 있는 최대 객차수 * 3을 해도 모든 객차보다 작다는 뜻이다. 즉, 최대인원을 구하기 위해 항상 최대 객차수만큼 끈다로 가정해도 된다.
 * 총 소요시간 : 30분
 */

public class BOJ2616_소형기관차 {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        int carriageNum = Integer.parseInt(br.readLine());//객차 수
        int[] carriageArray = new int[carriageNum + 1];

        //누적합 배열 구성
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= carriageNum; i++){
            carriageArray[i] = carriageArray[i - 1] + Integer.parseInt(st.nextToken());
        }

        //소형기관차가 최대로 끌수 있는 객차 수
        int carriageMax = Integer.parseInt(br.readLine());

        //디피 배열 구성
        int[][] dp = new int[4][carriageNum + 1];

        for(int i = 1; i < 4; i++){
            for(int j = i * carriageMax; j <= carriageNum; j++){
                dp[i][j] = Math.max(
                        dp[i - 1][j - carriageMax] + (carriageArray[j] - carriageArray[j - carriageMax]),
                        dp[i][j - 1]);
            }
        }

        System.out.println(dp[3][carriageNum]);

    }
}
