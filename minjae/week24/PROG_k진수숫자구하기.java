package week24;


import java.util.*;

    public class PROG_k진수숫자구하기 {

        public boolean init(long n){
            if(n == 1) return false;
            long a = (long)Math.sqrt(n) + 1;

            for (int i = 2; i < a ;i++){
                if (n % i == 0) return false;
            }
            return true;
        }
        public int solution(int n , int k) {
            int answer = 0;
            StringBuilder sb = new StringBuilder();
            while ( n != 0){
                sb.append(n%k);
                n = n/k;
            }
            String s = sb.reverse().toString();

            String sp[] = s.split("0");

            for (String now : sp){

                if (now.length() == 0) continue;
                long num = Long.parseLong(now);

                if(init(num)){

                    answer++;
                }

            }


            return answer;
        }
}