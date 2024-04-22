public class BOJ_3151_합이0 {
  
}
import java.util.*;
import java.io.*;

public class Main {
    static StringTokenizer st;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long ans = 0;
        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] arr =  new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);

        for (int i = 0; i < N; i++) {
            // 값 한개 픽스하고 이분탐색으로 왼쪽 값 오른쪽 값 할당.
            if(arr[i] > 0) break;
            int start = i + 1;
            int end = N - 1;

            while(start < end) {
                long sCnt = 1;
                long eCnt = 1;

                int curSum = arr[i] + arr[start] + arr[end];

                if(curSum == 0) {
                    // start 값과 end 값이 같은 경우...
                    if(arr[start] == arr[end]) {
                        // n개 중에서 2개 고르는 경우의 수 (n개 = end - start) 개수
                        // nC2 = n * (n - 1) / 2
                        long n = end - start + 1;
                        ans += (n) * (n - 1) / 2;
                        break;
                    }
                    // start값과 start + 1위치 값이 같은 경우
                    while(start + 1 < end && arr[start] == arr[start + 1]) {
                        sCnt++;
                        start++;
                    }

                    // end 값과 end - 1 위치 값이 같은 경우
                    while(end - 1 > start && arr[end] == arr[end - 1]) {
                        eCnt++;
                        end--;
                    }

                    // 중복 경우의 수 계산.
                    ans += (sCnt * eCnt);

                }

                if (curSum > 0) {
                    end--;
                } else {
                    start++;
                }
            }
        }

        System.out.println(ans);
    }
}