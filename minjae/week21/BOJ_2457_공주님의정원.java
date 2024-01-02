package week21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.util.*;

public class BOJ_2457_공주님의정원 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , Min, Max, M , K;

    static int delta[][] = {{0,1},{0,-1},{1,0},{-1,0},{0,0}};

    static int Map[][];
    static class flower implements Comparable<flower>{
        int start;
        int end;


        public flower(int start_month, int start_day, int end_month, int end_day) {
            this.start = start_month * 100 + start_day;
            this.end = end_month * 100 + end_day;
        }

        @Override
        public int compareTo(flower o) {
            return this.start == o.start ? o.end - this.end : this.start - o.start;
        }

    }
    public static void main(String[] args) throws IOException {

        N = Integer.parseInt(br.readLine());

        List<flower> list = new ArrayList<>();

        for(int i = 0 ; i < N ; i++){
            st = new StringTokenizer(br.readLine());
            int start_m = Integer.parseInt(st.nextToken());
            int start_d = Integer.parseInt(st.nextToken());
            int end_m = Integer.parseInt(st.nextToken());
            int end_d = Integer.parseInt(st.nextToken());
            list.add(new flower(start_m , start_d , end_m , end_d));

        }

        Collections.sort(list);

        int start = 301;
        int end = 1201;
        int max = -1;
        int index = 0;
        int cnt = 0;

        while(start < end){

            boolean isFinded = false;

            for(int i = index ; i < list.size() ; i++){
                flower now = list.get(i);
                if(start < now.start){
                    break;
                }

                if(max < now.end){
                    isFinded = true;
                    max = now.end;
                    index = i+1;
                }


            }



            if(isFinded){
                start = max;
                cnt++;
            }else{
                break;
            }




        }

        if(max < end){
            System.out.println(0);
        }else{
            System.out.println(cnt);
        }



    }

}