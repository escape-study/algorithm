package week40;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 아이디어
 * 전형적인 dp문제
 */
public class BOJ9252_LCS2 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String firstStr = br.readLine();
		String secondStr = br.readLine();


		int[][] dp = new int[secondStr.length() + 1][firstStr.length() + 1];
		for(int i = 1; i <= firstStr.length(); i++){
			for(int j = 1; j <= secondStr.length(); j++){

				if(firstStr.charAt(i - 1) == secondStr.charAt(j - 1)){
					dp[j][i] = dp[j - 1][i - 1] + 1;
				}
				else{
					dp[j][i] = Math.max(dp[j - 1][i], dp[j][i - 1]);
				}
			}
		}

		for(int i = 0; i <= secondStr.length(); i++){
			System.out.println(Arrays.toString(dp[i]));
		}

		int i = secondStr.length();
		int j = firstStr.length();
		StringBuilder result = new StringBuilder();
		while(i > 0 && j > 0){

			if(dp[i][j] == dp[i - 1][j]){
				i--;
			}
			else if(dp[i][j] == dp[i][j - 1]){
				j--;
			}
			else{
				result.append(firstStr.charAt(j - 1));
				i--;
				j--;

			}

		}

		int resultLen = dp[secondStr.length()][firstStr.length()];

		System.out.println(resultLen);
		if(resultLen != 0) System.out.println(result.reverse());

	}
}
