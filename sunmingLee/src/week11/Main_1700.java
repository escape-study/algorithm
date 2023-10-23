import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 페이징 교체 알고리즘 중 OPT 알고리즘 사용
 */
public class Main_1700 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] arr = new int[K];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int answer = 0;
        Set<Integer> multitap = new HashSet<>();
        for (int i = 0; i < K; i++) {
            if (multitap.size() == N && !multitap.contains(arr[i])) {  // 새로운 제품을 꽂아야하는데 멀티탭이 꽉참
                int left = N;
                boolean[] inUse = new boolean[K + 1];
                for (int j = i + 1; j < K; j++) {
                    if (multitap.contains(arr[j]) && !inUse[arr[j]]) {
                        left--;
                        inUse[arr[j]] = true;
                    }

                    if (left == 0) {   // 멀티탭에 꽂힌 제품 중 가장 뒤에 나온 제품
                        multitap.remove(arr[j]);
                        break;
                    }
                }

                // 멀티탭에 꽂힌 제품 중 이후 한번도 사용되지 않은 제품들 중 하나를 뽑음
                if (left > 0) {
                    for (int j = 1; j < inUse.length; j++) {
                        if (multitap.contains(j) && !inUse[j]) {
                            multitap.remove(j);
                            break;
                        }
                    }
                }

                answer++;
            }

            multitap.add(arr[i]);
        }

        System.out.println(answer);
    }
}