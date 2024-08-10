package week52;

import java.util.Stack;

/**
 * 아이디어
 * 스택
 * 스택을 이용하면 간단하게 풀리는 문제.
 */
public class Prog_짝지어제거하기 {

	public int solution(String s) {
		int answer = -1;

		Stack<Character> charStack = new Stack<>();


		for(int i = 0; i < s.length(); i++){

			char currentChr = s.charAt(i); //문자에서 문자열 하나 뽑기

			//스택이 비어있거나, 최상단 단어가 현재 단어와 동일하다면 추가.
			if(charStack.isEmpty() || charStack.peek() != currentChr){
				charStack.add(currentChr);
			}
			//비어있지 않고, 스택 최상단 단어와 현재 단어가 동일하면 뽑기
			else {
				charStack.pop();
			}
		}

		//스택이 비어있으면 1, 비어있지 않으면 모든 문자를 지울수 없기 떄문에 0을 반환한다.
		return charStack.isEmpty() ? 1 : 0;
	}

	public static void main(String[] args) {

		Prog_짝지어제거하기 s = new Prog_짝지어제거하기();

		String s1 = "baabaa";
		System.out.println(s.solution(s1));

		String s2 = "cdcd";
		System.out.println(s.solution(s2));

	}
}
