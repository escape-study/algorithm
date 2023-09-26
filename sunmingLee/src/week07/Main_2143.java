import java.io.*;
import java.util.*;
public class Main_2143 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int N = Integer.parseInt(br.readLine());
        int[] A = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < A.length; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }
        int M = Integer.parseInt(br.readLine());
        int[] B = new int[M];
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < B.length; i++) {
            B[i] = Integer.parseInt(st.nextToken());
        }
        // 부분합 구하기
        long[] aSum = new long[N * (N + 1) / 2];
        subTotal(aSum, A, N);
        long[] bSum = new long[M * (M + 1) / 2];
        subTotal(bSum, B, M);
        Arrays.sort(aSum);
        Arrays.sort(bSum);
        // 합쳐서 T가 되는 조합 개수 찾기
        long answer = 0;
        int aPoint = 0, bPoint = bSum.length - 1;
        while (aPoint != aSum.length && bPoint != -1) {
            long sum = aSum[aPoint] + bSum[bPoint];
            if (sum == T) {
                long cntA = 1, cntB = 1;
                int tempA = aPoint + 1;
                while (tempA != aSum.length && aSum[aPoint] == aSum[tempA]) {
                    cntA++;
                    tempA++;
                }
                aPoint = tempA;
                int tempB = bPoint - 1;
                while (tempB != -1 && bSum[bPoint] == bSum[tempB]) {
                    cntB++;
                    tempB--;
                }
                bPoint = tempB;
                answer += cntA * cntB;

            } else if (sum < T) {
                aPoint++;
            } else {
                bPoint--;
            }
        }
        System.out.println(answer);
    } // end of main
    /** 부분합을 구해줌 */
    private static void subTotal(long[] sum, int[] arr, int n) {
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    sum[index] = arr[i];
                } else {
                    sum[index] = sum[index - 1] + arr[j];
                }
                index++;
            }
        }
    } // end of subTotal
}