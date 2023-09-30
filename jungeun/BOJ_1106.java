import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * BOJ 1106 : 호텔
 *
 * 호텔의 고객을 적어도 C명 늘이기 위해 투자해야 하는 최소 비용
 */

public class BOJ_1106 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int C = Integer.parseInt(st.nextToken());   // 늘어나야 할 고객의 최소 명수
        int N = Integer.parseInt(st.nextToken());   // 홍보할 수 있는 도시의 개수

        // C ~ C+100 까지 늘릴 수 있음 (최대 100명)
        int[] cities = new int[C + 101];
        Arrays.fill(cities, Integer.MAX_VALUE);
        cities[0] = 0;

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            int cost = Integer.parseInt(st.nextToken());        // 홍보 비용
            int customer = Integer.parseInt(st.nextToken());    // 얻을 수 있는 고객의 수

            for(int j = customer; j < C + 101; j++){
                if(cities[j - customer] != Integer.MAX_VALUE)
                    cities[j] = Math.min(cities[j], cost + cities[j - customer]);
            }
        }

        int min = Integer.MAX_VALUE;
        for(int i = C; i < C + 101; i++){
            min = Math.min(min, cities[i]);
        }

        System.out.println(min);
    }
}
