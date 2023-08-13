import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/**
 * BOJ 3020 : 개똥벌레
 *
 * 개똥벌레가 일직선으로 지나가기 위해 석순(down)과 종유석(up)을 파괴해야함
 * 개똥벌레가 파괴해야 하는 장애물의 최솟값과 그러한 구간의 수
 */
public class BOJ_3020 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());

        int[] down = new int[H + 2];    //  석순
        int[] up = new int[H + 2];      // 종유석
        for(int i = 0; i < N / 2; i++){
            int x = Integer.parseInt(br.readLine());
            int y = Integer.parseInt(br.readLine());

            // index = height
            down[x]++;
            up[y]++;
        }

        // 누적합
        for(int i = 1; i <= H; i++){
            down[i] += down[i - 1];
            up[i] += up[i - 1];
        }

        int min = N;
        int count = 0;
        for(int i = 1; i <= H; i++){
            // 파괴해야 할 장애물의 개수
            int diff = (down[H] - down[i - 1]) + (up[H] - up[H - i]);

            if(diff < min){
                min = diff;
                count = 1;  // 초기화
            } else if(diff == min){
                count++;
            }
        }
        System.out.println(min + " " + count);
    }
}
