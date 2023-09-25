package chansik.src.week05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_16987_계란으로계란치기 {
    static int ans;
    static class Egg {
        private int weight;
        private int gauge;

        public Egg(int weight, int gauge) {
            this.weight = weight;
            this.gauge = gauge;
        }

        public void setGauge(int gauge) {
            this.gauge = gauge;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        Egg[] eggs = new Egg[n];
        for(int i=0;i<n;i++) {
            st = new StringTokenizer(bf.readLine());
            int gauge = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            eggs[i] = new Egg(weight, gauge);
        }
        ans = 0;
        hit(eggs, 0, 0);
        System.out.println(ans);
    }

    public static void hit(Egg[] eggs, int index, int count) {
        // 가장 오른쪽 계란에 도달한 경우
        if (index == eggs.length) {
            ans = Math.max(ans, count);
            return;
        }
        Egg curEgg = eggs[index];
        boolean check = false;
        // 손에든 게란이 깨진 경우
        if (curEgg.gauge <= 0) {
            hit(eggs, index+1, count);
            return;
        }

        for(int i=0;i<eggs.length;i++) {
            Egg nextEgg = eggs[i];
            if (i == index || nextEgg.gauge <= 0) continue;
            int curEggGauge = curEgg.gauge;
            int nextEggGauge = nextEgg.gauge;
            int weight = 0;

            curEgg.setGauge(curEggGauge - nextEgg.weight);
            nextEgg.setGauge(nextEggGauge - curEgg.weight);
            if (curEgg.gauge <= 0 && nextEgg.gauge <= 0) weight = 2;
            else if (curEgg.gauge > 0 && nextEgg.gauge > 0) weight = 0;
            else weight = 1;
            hit(eggs, index+1, count+weight);
            curEgg.setGauge(curEggGauge);
            nextEgg.setGauge(nextEggGauge);
            check = true;
        }

        // 칠 수 있는 계란이 없는 경우
        if (!check) hit(eggs, index+1, count);

    }
}