import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , Min, Max, M , K;

    static int delta[][] = {{0,1},{0,-1},{1,0},{-1,0},{0,0}};

    static int Map[][];
    public static void main(String[] args) throws IOException {

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

//        PriorityQueue<Integer> one = new PriorityQueue<>();
//        PriorityQueue<Integer> two = new PriorityQueue<>();
//
//        for(int i = 0 ; i < N ;i++){
//            int k = Integer.parseInt(br.readLine());
//            if(i%2 == 0){ // 짝수
//                two.offer(k);
//            }else{
//                one.offer(k);
//            }
//        }


        List<Integer> one = new ArrayList<>();
        List<Integer> two = new ArrayList<>();

        for(int i = 0 ; i < N ;i++){
            int k = Integer.parseInt(br.readLine());
            if(i%2 == 0){ // 짝수
                two.add(k);
            }else{
                one.add(k);
            }
        }

        Collections.sort(one);
        Collections.sort(two);


        int h[] = new int[M+1];

        int cnt = 0;
        for(int i = 0 ; i < two.size() ;i++){
            int now = two.get(i);
            if(cnt < now){
                for(int j = cnt+1 ; j <= now ; j++){
                    h[j] += two.size() - i;
                }
                cnt = now;
            }
        }

        cnt = 0;
        for(int i = 0 ; i < one.size() ;i++){
            int now = one.get(i);
            if(cnt < now){
                for(int j = cnt+1 ; j <= now ; j++){
                    h[M+1 - j] += one.size() - i;
                }
                cnt = now;
            }
        }


//        int cnt = 0;
//        while (!two.isEmpty()){
//            int now = two.peek();
//
//            if(cnt < now){
//                for(int i = cnt+1 ; i <= now ; i++){
//                    h[i] += two.size();
//                }
//                cnt = now;
//            }
//
//            two.poll();
//
//        }
//
//        cnt = 0;
//
//        while (!one.isEmpty()){
//
//            int now = one.peek();
//
//            if(cnt < now){
//                for(int i = cnt+1 ; i <= now; i++){
//                    h[M+1 - i] += one.size();
//                }
//                cnt = now;
//            }
//            one.poll();
//        }

        Arrays.sort(h);

        int result = 1;

        for(int i = 2 ;i < h.length ;i++){
            if(h[i] == h[1]){
                result++;
            }else{
                break;
            }
        }

//        System.out.println(Arrays.toString(h));
        System.out.println(h[1] + " " + result);
    }

}