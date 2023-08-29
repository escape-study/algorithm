package week02;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ9251_LCS {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str1 = br.readLine();
        String str2 = br.readLine();

        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for(int i = 1; i <= str2.length(); i++){

            char chr2 = str2.charAt(i - 1);
            for(int j = 1; j <= str1.length(); j++){
                char chr1 = str1.charAt(j - 1);

                //두값이 같으면 증가
                if(chr1 == chr2){
                    dp[j][i] = dp[j - 1][i - 1] + 1;
                }
                else{
                    dp[j][i] = Math.max(dp[j][i - 1], dp[j - 1][i]);
                }
            }
        }

        System.out.println(dp[str1.length()][str2.length()]);
    }
}
