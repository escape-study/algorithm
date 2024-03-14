package week31;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 아이디어
 * 문자열을 자르고 정렬하는 방식으로 구현
 *
 * (추가)
 * 정규표현식도 있지만 문자열 구현으로 해결한다.
 */
public class Prog_파일명_정렬 {

	//파일정보를 저장할 객체
	private static class Node implements Comparable<Node>{
		String head, tail;
		int index, number;

		public Node(String head, String tail, int index,int number){
			this.head = head;
			this.tail = tail;
			this.index = index;
			this.number = number;
		}

		@Override
		public int compareTo(Node node) {


			if(this.head.toUpperCase().compareTo(node.head.toUpperCase()) == 0){

				if(Integer.compare(this.number, node.number) == 0) return Integer.compare(this.index, node.index);

				return Integer.compare(this.number, node.number);
			}
			//head로 정렬
			return this.head.toUpperCase().compareTo(node.head.toUpperCase());
		}
	}

	public String[] solution(String[] files) {
		String[] answer = new String[files.length];

		List<Node> nodeList = new ArrayList<>();
		//반복문돌면서 head, number, tail로 만들기.
		for(int i = 0; i < files.length; i++){

			String fileName = files[i];

			int headIndex = 0;
			int numberIndex = 0;
			while(!Character.isDigit(fileName.charAt(headIndex))){
				headIndex++;
			}
			numberIndex = headIndex;

			while(fileName.length() > numberIndex && Character.isDigit(fileName.charAt(numberIndex))){
				numberIndex++;
			}

			nodeList.add(
				new Node(
					fileName.substring(0, headIndex),
					fileName.substring(numberIndex),
					i,
					Integer.parseInt(fileName.substring(headIndex, numberIndex))
				)
			);
		}
		Collections.sort(nodeList);

		for(int i = 0; i < nodeList.size(); i++){
			answer[i] = files[nodeList.get(i).index];
		}

		return answer;
	}

	public static void main(String[] args) {

		Prog_파일명_정렬 s = new Prog_파일명_정렬();

		String[] files1 = {"img12.png", "img10.png", "img02.png", "img1.png", "IMG01.GIF", "img2.JPG"};
		System.out.println(Arrays.toString(s.solution(files1)));

		// String[] files2 = {"F-5 Freedom Fighter", "B-50 Superfortress", "A-10 Thunderbolt II", "F-14 Tomcat"};
		// System.out.println(Arrays.toString(s.solution(files2)));
		//
		String[] file3 = {"F-15"};
		System.out.println(Arrays.toString(s.solution(file3)));

	}
}
