package chansik.src.week11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_5214_환승 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        HashMap<Integer, List<Integer>> hyperTubes = new HashMap<>();
        HashMap<Integer, List<Integer>> stations = new HashMap<>();
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int answer = 0;
        for(int i=1;i<=n;i++) stations.put(i, new ArrayList<>());
        for(int i=1;i<=m;i++) hyperTubes.put(i, new ArrayList<>());
        for(int i=1;i<=m;i++) {
            st = new StringTokenizer(bf.readLine());
            for(int j=0;j<k;j++) {
                int station = Integer.parseInt(st.nextToken());
                stations.get(station).add(i);
                hyperTubes.get(i).add(station);
            }
        }
        if (n == 1) {
            answer = 1;
        } else {
            Queue<int[]> q = new LinkedList<>();
            q.add(new int[]{1, 1});
            boolean[] hyperVis = new boolean[m + 1];
            boolean[] stationVis = new boolean[n + 1];
            stationVis[1] = true;
            while (!q.isEmpty()) {
                int[] info = q.poll();
                int cur = info[0];
                int dist = info[1];
                if (answer != 0) break;

                for (int hyperTube : stations.get(cur)) {
                    if (!hyperVis[hyperTube]) {
                        hyperVis[hyperTube] = true;
                        for (int station : hyperTubes.get(hyperTube)) {
                            if (!stationVis[station]) {
                                stationVis[station] = true;
                                if (station == n) {
                                    answer = dist + 1;
                                    break;
                                }
                                q.add(new int[]{station, dist + 1});
                            }
                        }
                    }
                }
            }
        }
        System.out.println(answer == 0 ? - 1 : answer);
    }
}