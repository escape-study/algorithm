package week33;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 아이디어
 * 표의 각 칸은 객체로 표현
 * 병합의 경우에는 링크드리스트방식으로 표기한다.
 * 병합을 할때는 연결된 맨 마지막 노드에 추가하고, 값을 찾을떄는 맨 첫번째 노드를 찾도록 한다.
 * 더블리 링크드 리스트 방식으로 구현한다.
 * 중요한 점은 매번 링크드 리스트의 첫번쨰 노드를 찾기위해서 탐색하지 않도록 경로압축을 이용해야 한다.
 * 서로소 집합을 이용해서 최대의 부모를 찾고, 최대의 부모에 연결해야 한다.
 *
 * (수정)
 * DSU를 이용해서 해결한다.
 * 여러 노드가 병합되면 최상위 노드를 저장하기 위해서 부모를 저장하는 배열을 만든다
 * 2차원 배열이기 떄문에 1차원으로 만들어서 저장하도록 한다.
 */
public class Prog_표병합 {


	//부모를 저장할 배열
	private static int[] parentArray;

	//값을 저장할  표
	private static String[][] table;

	//부모를 찾는 메서드
	private static int findParent(int node){

		if(node == parentArray[node]) return parentArray[node];


		return parentArray[node] = findParent(parentArray[node]);

	}

	//값으로 업데이트.
	private static void valueUpdate(String value1, String value2){

		//반복문 돌면서 value1에 해당 하는 위치 찾아서 변경.
		for(int i= 0; i < 50; i++){
			for(int j = 0; j < 50; j++){
				int parent = findParent(i * 50 + j);
				if(table[parent / 50][parent % 50] == null || !table[parent / 50][parent % 50].equals(value1)) continue;
				table[i][j] = value2;
			}
		}
	}
	//위치로 업데이트.
	private static void update(int r, int c, String value){

		//해당 위치의 부모 노드 찾기.
		int parent = findParent(r * 50 + c);

		table[parent / 50][parent % 50] = value;
	}

	//병합
	private static void merge(int r1, int c1, int r2, int c2){

		int firstParent = findParent(r1 * 50 + c1);
		int secondParent = findParent(r2 * 50 + c2);

		if(firstParent == secondParent) return;

		String firstValue = table[firstParent / 50][firstParent % 50];
		String secondValue = table[secondParent / 50][secondParent % 50];

		String mergeValue = null;
		if(firstValue != null && secondValue != null){
			mergeValue = firstValue;
		}
		else if(firstValue != null){
			mergeValue = firstValue;
		}
		else mergeValue = secondValue;


		int updateRoot = 0; //업데이트 해야 할 루트
		int oldRoot = 0; // 이전 루트
		//서로 다르면 병합
		if(firstParent < secondParent){
			parentArray[secondParent] = firstParent;
			table[secondParent / 50][secondParent % 50] = null;
			table[firstParent / 50][firstParent % 50] = mergeValue;

			oldRoot = secondParent;
			updateRoot = firstParent;
		}
		else if(firstParent > secondParent){
			parentArray[firstParent] = secondParent;
			table[firstParent / 50][firstParent % 50] = null;
			table[secondParent / 50][secondParent % 50] = mergeValue;
			oldRoot = firstParent;
			updateRoot = secondParent;
		}

		//반복문 돌면서 나머지도 전부 업데이트.
		for(int i = 0; i < 2500; i++){
			if(parentArray[i] != oldRoot) continue;
			parentArray[i] = updateRoot;
		}


	}

	//병합해제
	private static void unMerge(int r, int c){

		//반복문 돌면서 해당 부모 노드를 가진것들을 자기 자신으로 바꿈.
		int parent = findParent(r * 50 + c);
		String value = table[parent / 50][parent % 50];

		for(int i = 0; i < 50; i++){
			for(int j = 0; j < 50; j++){
				int temp = findParent(i * 50 + j);

				if(temp != parent) continue;

				table[i][j] = null;
				parentArray[i * 50 + j] = i * 50 + j;
			}
		}

		table[r][c] = value;

	}

	//프린트
	private static String print(int r, int c){
		int index = r * 50 + c;

		//부모를 찾음.
		int parent = findParent(index);

		String value = table[parent / 50][parent % 50];

		return value == null ? "EMPTY" : value;
	}


	//처리 로직
	private static void logic(String commandStr, List<String> answer){

		String[] commandSplit = commandStr.split(" ");
		int r1 = 0;
		int c1 = 0;
		int r2 = 0;
		int c2 = 0;
		String value = null;

		switch (commandSplit[0]){
			case "UPDATE":

				if(commandSplit.length == 3){
					valueUpdate(commandSplit[1], commandSplit[2]);
				}
				else{
					r1 = Integer.parseInt(commandSplit[1]) - 1;
					c1 = Integer.parseInt(commandSplit[2]) - 1;
					value = commandSplit[3];
					update(r1, c1, value);

				}

				break;
			case "MERGE":
				r1 = Integer.parseInt(commandSplit[1]) - 1;
				c1 = Integer.parseInt(commandSplit[2]) - 1;
				r2 = Integer.parseInt(commandSplit[3]) - 1;
				c2 = Integer.parseInt(commandSplit[4]) - 1;
				merge(r1,c1,r2,c2);
				break;
			case "UNMERGE":
				r1 = Integer.parseInt(commandSplit[1]) - 1;
				c1 = Integer.parseInt(commandSplit[2]) - 1;
				unMerge(r1, c1);
				break;
			case "PRINT":
				r1 = Integer.parseInt(commandSplit[1]) - 1;
				c1 = Integer.parseInt(commandSplit[2]) - 1;

				answer.add(print(r1, c1));
				break;
		}

	}


	public String[] solution(String[] commands) {
		List<String> answer = new ArrayList<>();

		parentArray = new int[2500];
		for(int i = 0; i < 2500; i++){
			parentArray[i] = i;
		}
		table = new String[50][50];


		for(String command : commands){
			logic(command, answer);
		}

		//결과 리스트를 배열로 변환 -> 몇개의 결과가 나올지 모르기 때문에 리스트로 만듦.
		String[] answerArray = new String[answer.size()];
		for(int i = 0; i < answer.size(); i++){
			answerArray[i] = answer.get(i);
		}


		// for(int i = 0; i < 4; i++){
		// 	for(int j = 0; j < 4; j++){
		// 		System.out.print(table[i][j] + " ");
		// 	}
		// 	System.out.print("\n");
		// }


		return answerArray;
	}
	public static void main(String[] args) {

		Prog_표병합 s = new Prog_표병합();
		String[] commands1 = {"UPDATE 1 1 menu", "UPDATE 1 2 category", "UPDATE 2 1 bibimbap", "UPDATE 2 2 korean", "UPDATE 2 3 rice", "UPDATE 3 1 ramyeon", "UPDATE 3 2 korean", "UPDATE 3 3 noodle", "UPDATE 3 4 instant", "UPDATE 4 1 pasta", "UPDATE 4 2 italian", "UPDATE 4 3 noodle", "MERGE 1 2 1 3", "MERGE 1 3 1 4", "UPDATE korean hansik", "UPDATE 1 3 group", "UNMERGE 1 4", "PRINT 1 3", "PRINT 1 4"};
		System.out.println(Arrays.toString(s.solution(commands1)));

		String[] commands2 = {"UPDATE 1 1 a", "UPDATE 1 2 b", "UPDATE 2 1 c", "UPDATE 2 2 d", "MERGE 1 1 1 2", "MERGE 2 2 2 1", "MERGE 2 1 1 1", "PRINT 1 1", "UNMERGE 2 2", "PRINT 1 1"};
		System.out.println(Arrays.toString(s.solution(commands2)));

		String[] commands3 = {"MERGE 1 1 2 2", "UPDATE 1 1 A", "UNMERGE 1 1", "PRINT 1 1", "PRINT 2 2"};
		System.out.println(Arrays.toString(s.solution(commands3)));

		String[] commands4 = {"UPDATE 1 1 A", "UPDATE 2 2 B", "UPDATE 3 3 C", "UPDATE 4 4 D", "MERGE 1 1 2 2", "MERGE 3 3 4 4", "MERGE 1 1 4 4", "UNMERGE 3 3", "PRINT 1 1", "PRINT 2 2", "PRINT 3 3", "PRINT 4 4"};
		System.out.println(Arrays.toString(s.solution(commands4)));




	}
}
