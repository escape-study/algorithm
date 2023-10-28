package week12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp이용
 *
 * 2차원 배열을 이용해야 한다.
 * 각 인덱스는 특정번째까지 곱셈한 횟수
 * dp[i][j] => i부터 ~ j번째 행렬까지 곱셈의 연산 최소 수.
 */

public class BOJ11049_행렬_곱셈_순서 {

    private static final int INF = Integer.MAX_VALUE;

    //행렬
    private static class Node{
        int row,col; // 행 렬.

        public Node(int row, int col){
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        Node[] info = new Node[N + 1];
        int[][] dp = new int[N + 1][N + 1];

        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());

            info[i] = new Node(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        //초기값 설정 - 바로 인접한 노드와의 연산 구하기.
        for(int i = 1; i < N; i++){
            dp[i][i + 1] = info[i].row * info[i].col * info[i + 1].col;
        }

        //길이가 2 즉 -> A에서 특정 행렬 하나를 거쳐서 C로 가는 경우, 총 3개의 행렬을 가지고 연산하는 경우를 구한다는 뜻.
        //길이가 1인 두 행열의 곱은 위의 초기화 과정에서 구했음.
        for(int len = 2; len <= N; len++){
            for(int i = 1; i <= N - len; i++){

                //i번째 행에서 j번째 행까지의 곱을 구하기 위해 j를 뽑음
                int j =  i + len;
                //최대 값을 구하기 위해서 최대값 저장해둠.
                dp[i][j] = INF;

                //중간에 어떤 연산을 먼저 할껀지 다 구해봐야 함.
                //앞에서 부터 1과 2번째를 먼저하고 3과 연산할수 도 있지만, 1(23) 이렇게 2,3을 먼저 할수도 있음.
                for(int k = i; k < j; k++){

                    //i에서부터 j까지 곱이 저장되어있는 dp 배열의 값과, 새로구한 값을 비교해서 작은걸로 업데이트한다.
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + (info[i].row * info[k].col * info[j].col));

                }
            }
        }

        System.out.println(dp[1][N]);
    }


}
