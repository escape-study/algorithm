import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_1306 {
    static class Light implements Comparable<Light> {
        int index;
        int brightness;

        public Light(int index, int brightness) {
            this.index = index;
            this.brightness = brightness;
        }


        @Override
        public int compareTo(Light o) {
            return o.brightness - this.brightness;
        }
    }


    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken()) - 1;
        st = new StringTokenizer(br.readLine());
        int[] lights = new int[N];  // 광고판
        for (int i = 0; i < N; i++) {
            lights[i] = Integer.parseInt(st.nextToken());
        }

        PriorityQueue<Light> q = new PriorityQueue<>();
        for (int i = 0; i < 2 * M + 1; i++) {
            q.add(new Light(i, lights[i]));
        }
        sb.append(q.peek().brightness + " ");

        for (int right = 2 * M + 1; right < N; right++) {
            q.add(new Light(right, lights[right]));

            while (q.peek().index < right - 2 * M) {
                q.poll();
            }

            sb.append(q.peek().brightness + " ");
        }

        System.out.println(sb);
    }
}
