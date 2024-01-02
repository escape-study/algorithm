import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_2457 {
    static class Flower implements Comparable<Flower> {
        int birth;  // 개화일시
        int death;  // 낙화일시

        public Flower(int birth, int death) {
            this.birth = birth;
            this.death = death;
        }

        // 피는날 오름차순. 피는날이 같으면 지는날 내림차순.
        @Override
        public int compareTo(Flower o) {
            if (this.birth == o.birth) {
                return o.death - this.death;
            }
            return this.birth - o.birth;
        }
    }

    static int N;
    static Flower[] flowers;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        flowers = new Flower[N];

        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int birthMonth = Integer.parseInt(st.nextToken());
            int birthDay = Integer.parseInt(st.nextToken());
            int deathMonth = Integer.parseInt(st.nextToken());
            int deathDay = Integer.parseInt(st.nextToken());

            // (월 * 100 + 일) 형태로 일자를 int로 저장
            flowers[i] = new Flower(birthMonth * 100 + birthDay, deathMonth * 100 + deathDay);
        }

        Arrays.sort(flowers);

        System.out.println(selectFlowers());

    }   // end of main

    private static int selectFlowers() {
        int start = 301;
        int end = 1201;
        int count = 0;  // 고른 꽃의 개수
        int index = 0;  // 살펴보기 시작할 꽃의 인덱스

        while (start < end) {
            boolean selected = false;
            int latest = start; // 가장 늦은 낙화시기

            for (int i = index; i < N; i++) {
                if (flowers[i].birth > start) {   // 이후 꽃은 모두 start보다 개화시기가 늦음
                    break;
                }

                index = i + 1;
                if (flowers[i].death > latest) {    // 더 늦은 낙화일자로 갱신
                    latest = flowers[i].death;
                    selected = true;
                }

            }

            // 조건을 만족하는 꽃이 없음
            if (!selected) {
                count = 0;
                break;
            }

            start = latest;
            count++;
        }

        return count;
    }   // end of selectFlowers
}