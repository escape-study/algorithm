package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_2011_암호코드 {
    static int Max , Map[], DP[];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        Map = new int [s.length()+1];
        DP = new int [s.length()+1];
        for(int i = 0 ; i< s.length() ; i++){
            Map[i] = s.charAt(i) - '0';
            if(Map[i] < 0 || Map[i] > 9) return;
        }

        if(Map[0] == 0) {
            System.out.println(0);
            return;
        }

        DP[0] = 1;
        DP[1] = 1;
        for(int i=1; i<s.length(); i++) {
            char pri = s.charAt(i - 1);
            if(s.charAt(i) >= '1' && s.charAt(i)<='9'){
                DP[i+1]+=DP[i];
                DP[i+1]%=1000000;
            }
            if (!(pri == '0' || pri > '2' || (pri == '2' && s.charAt(i) > '6'))) {
                DP[i + 1] += DP[i-1];
                DP[i+1]%=1000000;
            }
            DP[i + 1] %= 1000000000;
        }
        System.out.println(DP[s.length()]%1000000000);
    }
}