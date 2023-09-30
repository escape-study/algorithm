package week2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_9251_LCS {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String N = br.readLine();
		String M = br.readLine();

		char first[] = new char[N.length()];
		char second[] = new char[M.length()];

		for (int i = 0; i < first.length; i++) {
			first[i] = N.charAt(i);
		}
		for (int i = 0; i < second.length; i++) {
			second[i] = M.charAt(i);
		}

		int DP[][] = new int[N.length() + 1][M.length() + 1];

		for (int i = 1; i <= N.length(); i++) {
			for (int j = 1; j <= M.length(); j++) {
				if (first[i - 1] == second[j - 1]) {
					DP[i][j] = DP[i - 1][j-1] + 1;
				} else {
					DP[i][j] = Math.max(DP[i - 1][j], DP[i][j - 1]);
				}

			}
		}

//		for(int i = 0 ; i <= N.length() ;i++) {
//			for(int j = 0 ; j <= M.length(); j++) {
//				
//				System.out.print(DP[i][j] + " ");
//				
//			}
//			System.out.println();
//		}
//		

		System.out.println(DP[N.length()][M.length()]);
	}

}