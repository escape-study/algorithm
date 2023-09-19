import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * K개의 집중국으로 수신 가능영역 거리를 최소로 만들어야함.
 * 센서 사이의 구간 중 거리가 짧은 구간에 집중국을 설치하여 센서간 거리가 먼 구간을 제외시켜야하므로
 * 1. 센서 오름 차순 정렬 (sensors)
 * 2. 센서 간 간격 오름 차순 정렬 (dist)
 * 3. K개의 집중국 설치로 N-1 개의 구간 중 거리가 가장 먼 K-1 개의 구간 제외 가능
 * -> 앞에서부터 N-K개의 구간을 더한게 수신 가능 영역의 최솟값
 */
public class Main_2212 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());

        if (K >= N) {   // 집중국이 센서 개수 이상이면
            System.out.println(0);
            System.exit(0);
        }

        int[] sensors = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < sensors.length; i++) {
            sensors[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(sensors);

        int[] dist = new int[N - 1];  // 센서 사이의 거리
        for (int i = 0; i < dist.length; i++) {
            dist[i] = sensors[i + 1] - sensors[i];
        }

        Arrays.sort(dist);

        int answer = 0;
        for (int i = 0; i < N - K; i++) {
            answer += dist[i];
        }

        System.out.println(answer);
    }
}