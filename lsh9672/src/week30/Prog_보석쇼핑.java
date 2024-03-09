package week30;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 아이디어
 * 투포인트
 */
public class Prog_보석쇼핑 {

	//보석수를 저장할 맵, 중복이 있을수도 있기 때문에.
	private static Map<String, Integer> gemMap;
	//최소길이
	private static int minLen;

	//보석 종류
	private static int maxGemCount;


	//초기 보석종류를 맵에 담기
	private static void init(String[] gems){
		gemMap = new HashMap<>();
		for(String gem : gems){
			if(!gemMap.containsKey(gem)){
				gemMap.put(gem, 0);
			}
		}
	}

	public int[] solution(String[] gems) {
		init(gems);
		minLen = gems.length + 1;
		maxGemCount = gemMap.size();
		int[] answer = new int[2];

		int startIndex = 0;
		int endIndex = 0;
		int count = 0;

		String selectGem = gems[0];
		gemMap.put(selectGem, gemMap.get(selectGem) + 1);
		count++;
		while((startIndex <= endIndex) && (endIndex < gems.length)){

			//뽑은 종류가 보석의 최대종류보다 적다면, 종류 늘려보기(endIndex++).
			if(count < maxGemCount){
				endIndex++;
				if(endIndex >= gems.length) break;
				selectGem = gems[endIndex];

				//뽑으려는 보석수가 0이면 종류(count) 증가.
				if(gemMap.get(selectGem) == 0){
					count++;
				}

				//보석수 저장.
				gemMap.put(selectGem, gemMap.get(selectGem) + 1);


			}

			//뽑은 종류수가 최대 종류와 같거나 크다면 종류 줄이기(startIndex++)
			else{
				// System.out.println(startIndex + ", " + endIndex + "=>" + count);

				int len = endIndex - startIndex + 1;
				if(minLen > len){
					minLen = len;
					answer[0] = startIndex + 1;
					answer[1] = endIndex + 1;


				}

				selectGem = gems[startIndex];
				gemMap.put(selectGem, gemMap.get(selectGem) - 1);

				//0이 되면 종류감소
				if(gemMap.get(selectGem) == 0) count--;
				startIndex++;

			}
		}

		return answer;
	}

	public static void main(String[] args) {
		Prog_보석쇼핑 s = new Prog_보석쇼핑();

		String[] gems1 = {"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"};
		System.out.println(Arrays.toString(s.solution(gems1)));

		String[] gems2 = {"AA", "AB", "AC", "AA", "AC"};
		System.out.println(Arrays.toString(s.solution(gems2)));

		String[] gems3 = {"XYZ", "XYZ", "XYZ"};
		System.out.println(Arrays.toString(s.solution(gems3)));

		String[] gems4 = {"ZZZ", "YYY", "NNNN", "YYY", "BBB"};
		System.out.println(Arrays.toString(s.solution(gems4)));
	}
}
