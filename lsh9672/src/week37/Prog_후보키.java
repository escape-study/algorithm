package week37;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 아이디어
 * 완탐
 * 단순하게 반복문을 이용해서 해결할 수도 있고,
 * 부분 집합을 이용해도 된다.
 * 주의할 점은 중복체크이다.
 * 여러가지 방법이 있겠지만, 여러 후보키를 조합 한 후에 중복체크를 할때,
 * 각 값들을 문자열 이어붙이기를 통해서 하나의 문자열로 만들고, 중복체크를 하면 편하다
 * 이떄 문자열에는 알파벳소문자와 숫자만 존재하기 때문에 쓰이지 않는 -와 같은 특수문자로 이어붙이면 구분하기 좋다
 *
 * 추가
 * 최소성을 부분집합을 만드는 순서로 하면 안된다
 * 가령 01, 012 이 두개를 비교하는데에는 문제가 되지 않지만,
 * 02, 012 이러한 케이스의 경우에는 하나씩 비교하지 않으면 확인이 불가능하다.
 */

public class Prog_후보키 {


	private static int result;

	private static Set<String> minSet;

	//컬럼 인덱스 합치기
	private static String concatColumn(List<Integer> columnIndex){

		StringBuilder newKey = new StringBuilder();
		for(int index : columnIndex){
			newKey.append(index); //인덱스가 최대 7이라서 그냥 합쳐도 됨(두자리 이상이면 구분자를 두어야 함.)
		}

		return newKey.toString();
	}


	//최소성을 만족하는지 확인 - true면 최소성 만족
	private static boolean minCheck(String newKey){

		for(String candidateKey : minSet){

			boolean flag = true;

			if(candidateKey.length() > newKey.length()) continue;

			//이전에 저장된 후보키와 새로운 키와 비교
			for(int i = 0; i < candidateKey.length(); i++){


				//하나라도 안맞는게 있으면 패스.
				if(!newKey.contains(String.valueOf(candidateKey.charAt(i)))){
					flag = false;
					break;
				}
			}

			if(flag) return false;
		}

		return true;

	}

	//유일성을 만족하는지 확인
	private static boolean checkUnique(String[][] relation, List<Integer> columnIndex){

		//이전의 값 비교를 위한 집합
		Set<String> columnSet = new HashSet<>();

		for(String[] row : relation){

			//컬럼 조합 하나의 문자열로 만들기.
			StringBuilder sb = new StringBuilder();
			for(int index : columnIndex){
				 sb.append(row[index]).append("-");
			}

			//값이 이미 있으면 유일성 만족X
			if(columnSet.contains(sb.toString())) return false;

			columnSet.add(sb.toString());

		}

		return true;
	}

	//조합
	//currentKey : 뽑은 컬럼 인덱스
	private static void recursive(int index, List<Integer> currentKey, String[][] relation){

		//한개이상 뽑앗고, 유일성을 만족하면 최소성 확인.
		if(!currentKey.isEmpty() && checkUnique(relation, currentKey)){

			String newKey = concatColumn(currentKey);

			//최소성을 만족하면 추가.
			if(minSet.isEmpty() || minCheck(newKey)){
				result++;
				minSet.add(newKey);
				return;
			}
		}

		if(index >= relation[0].length) return;

		recursive(index + 1, currentKey, relation);

		currentKey.add(index);
		recursive(index + 1, currentKey, relation);
		currentKey.remove(currentKey.size() - 1);
	}

	public int solution(String[][] relation) {
		result = 0;
		minSet = new HashSet<>();

		recursive(0, new ArrayList<>(), relation);


		return result;
	}

	public static void main(String[] args) {
		Prog_후보키 s = new Prog_후보키();

		String[][] relation = {{"100","ryan","music","2"},{"200","apeach","math","2"},{"300","tube","computer","3"},{"400","con","computer","4"},{"500","muzi","music","3"},{"600","apeach","music","2"}};

		// System.out.println(s.solution(relation));

		String[][] relation2 = { {"a","1","aaa","c","ng"},
			{"a","1","bbb","e","g"},
			{"c","1","aaa","d","ng"},
			{"d","2","bbb","d","ng"}};
		System.out.println(s.solution(relation2));

	}
}
