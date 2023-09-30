package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_11559_puyopuyo {
    static int N, M;
    static int delta[][] = {{1,0},{0,1},{-1,0},{0,-1}};
    static char Map[][];
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N =12 ;
        M = 6;
        Map = new char[N][M];

        for(int i = 0 ; i < N ; i++){
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                Map[i][j] = s.charAt(j);
            }
        }

        List<Character>[] list = new List[M];

        for(int i = 0 ; i < M ;i++){
            list[i] = new ArrayList<Character>();
            for (int j = 11 ; j >= 0 ; j--){
                list[i].add(Map[j][i]);
            }
        }

//        for(int i = 0 ; i < M ;i++){
//
//            for(char a : list[i]){
//                System.out.print(a);
//            }
//            System.out.println();
//        }

        int cnt = 0;

        while (true){

            boolean checked[][] = new boolean[6][12];

            PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o2[1] - o1[1];
                }
            });

            for(int i = 0 ; i < 6 ;i++){
                for(int j = 0 ; j < list[i].size() ; j++){
                    char std = list[i].get(j);
                    if(std != '.' && !checked[i][j]){
                        Queue<int[]> queue = new LinkedList<>();
                        Queue<int[]> stock = new LinkedList<>();
                        queue.add(new int[] {i , j});
                        checked[i][j] = true;


                        while(!queue.isEmpty()){
                            int now[] = queue.poll();
                            if(list[now[0]].get(now[1]) == std){
                                stock.add(now);
                            }
                            for(int k = 0 ; k< 4 ; k++){
                                int x = now[0] + delta[k][0];
                                int y = now[1] + delta[k][1];
                                if(x < 0 || x >= 6 || y < 0 || y >= 12 || checked[x][y]) continue;
                                char puyo = list[x].get(y);
                                if(puyo != std) continue;
                                queue.add(new int[]{x , y});
                                checked[x][y] = true;
                            }
                        }

                        if(stock.size() >= 4){
                            while (!stock.isEmpty()){
                                pq.add(stock.poll());
                            }
                        }
                    }
                }
            }

            if(pq.size() == 0){
                break;
            }else{
                cnt++;
                while (!pq.isEmpty()){
                    int funk[]  = pq.poll();
                    list[funk[0]].remove(funk[1]);
                    list[funk[0]].add('.');
                }
            }
        }

        System.out.println(cnt);





    }
}