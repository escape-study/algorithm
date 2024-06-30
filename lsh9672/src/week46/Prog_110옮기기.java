package week46;

import java.util.Arrays;
import java.util.Stack;

/**
 * 아이디어
 * 그리디
 * 사전순으로 앞서기 위해서는 0이 더 앞에 나와야 한다.
 * 110을 뒤에서 찾아서 111로 시작하는 곳 앞에 넣으면 된다.
 * 이 과정을 더 이상 110이 없거나, 111로 시작하는 곳이 없을 때 까지 반복한다.
 *
 * (수정)
 * 위와 같은 방법은 시간초과가 걸릴뿐더러, 굳이 이럴필요가 없다.
 * 문자열을 앞에서 부터 탐색하면 110인것을 찾고, 뒤에서부터 0인 것을 찾는다.
 * 0을 찾으면 그 뒤에 그동안 찾은 110을 이어 붙여준다.
 * 이때 스택을 이용해야 한다.
 * n^2의 시간복잡도가 나오면 시간초과이다.
 */
public class Prog_110옮기기 {


	private static String logic(String numStr){

		int count = 0; //110개수 카운트

		Stack<Character> stack = new Stack<>();

		for(int i = 0; i < numStr.length(); i++){

			char chr = numStr.charAt(i);

			stack.push(chr);

			if(stack.size() >= 3){

				char third = stack.pop();
				char second = stack.pop();
				char first = stack.pop();

				if(first == '1' && second == '1' && third == '0'){
					count++;
				}
				else{
					stack.push(first);
					stack.push(second);
					stack.push(third);
				}
			}

		}


		StringBuilder resultStr = new StringBuilder();
		int index = stack.size();

		int targetIndex = 0;//실제로 110을 삽입할 위치.

		//스택이 비어있을떄 까지 돌면서 0찾기.
		while(!stack.isEmpty()){

			char chr = stack.pop();
			resultStr.insert(0, chr);
			if(targetIndex != 0) continue;

			if(chr == '1'){
				index--;
			}
			else if(chr == '0'){
				targetIndex = index;
			}
		}

		for(int i = 0; i < count; i++){
			resultStr.insert(targetIndex, "110");
		}

		return resultStr.toString();
	}

	public String[] solution(String[] s) {
		String[] answer = new String[s.length];

		for(int i = 0; i < s.length; i++){
			String numStr = s[i];

			answer[i] = logic(numStr);

		}

		return answer;
	}
	public static void main(String[] args) {

		Prog_110옮기기 s = new Prog_110옮기기();
		String[] s1 = {"1110","100111100","0111111010"};
		// String[] s1 = {"1110"};
		System.out.println(Arrays.toString(s.solution(s1)));

	}
}
