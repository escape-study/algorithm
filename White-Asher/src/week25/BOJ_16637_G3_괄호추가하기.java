import java.io.*;
import java.util.*;

public class BOJ_16637_G3_괄호추가하기 {
	static int N;
	static int max = Integer.MIN_VALUE;
	static List<Integer> num = new ArrayList<>();
	static List<Character> operator = new ArrayList<>();

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		String input = br.readLine();
		for (int i = 0; i < N; i++) {
			if(i % 2 == 0) num.add(Integer.parseInt(String.valueOf(input.charAt(i))));
			else operator.add(input.charAt(i));
		} 
		DFS(0, num.get(0));
		System.out.println(max);
	}
	
	public static void DFS(int idx, int sum) {
		if(idx >= operator.size()) {
			max = Math.max(max, sum);
			return;
		}
		
		int nonBracket = operation(operator.get(idx), sum, num.get(idx+1));
		DFS(idx+1, nonBracket);
		
		if(idx+2 <= operator.size()) {
			int Bracket = operation(operator.get(idx+1), num.get(idx+1), num.get(idx+2));
			int res = operation(operator.get(idx), sum, Bracket);
			DFS(idx+2, res);
		}
	}
	
	public static int operation(char op, int x, int y) {
		switch (op) {
		case '+':
			return x+y;
		case '-':
			return x-y;
		case '*':
			return x*y;
		}
		return 0;
	}
}