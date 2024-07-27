package week50;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 아이디어
 * 구현
 * 수학공식도 주었기 때문에 2중 반복문을 돌면서 정수인 좌표를 구해준다.
 * 해당 좌표들을 이용해서 배열에 표시해준다.
 */
public class Prog_교점에_별_만들기 {
	static long maxX;
	static long maxY;
	static long minX;
	static long minY;

	private static class Node{
		long x, y;
		public Node(long x, long y){
			this.x = x;
			this.y = y;
		}
	}


	//좌표 구해서 반환.
	//A: aLoc[0] ,B: aLoc[1] ,E: aLoc[2], C:bLoc[0], D:bLoc[1], F: bLoc[2]
	//x = (BF-ED)/(AD-BC), y = (EC-AF)/(AD-BC)
	private static Node getLoc(int[] aLoc, int[] bLoc){

		long A = aLoc[0];
		long B = aLoc[1];
		long E = aLoc[2];
		long C = bLoc[0];
		long D = bLoc[1];
		long F = bLoc[2];

		if(((A * D) - (B * C)) == 0) return null; //이 경우 두 직선이 일치 또는 평행하다.

		if(((B * F) - (E * D)) % ((A * D) - (B * C)) != 0) return null;// 정수가 아님.
		if(((E * C) - (A * F)) % ((A * D) - (B * C)) != 0) return null;// 정수가 아님.

		//정수 좌표이면 반환
		return new Node(
			((B * F) - (E * D)) / ((A * D) - (B * C)),
			((E * C) - (A * F)) / ((A * D) - (B * C))
		);
	}

	//x,y 최대 값 찾아서 좌표 평면 만들고 좌표 그리기.
	private static String[] getArray(List<Node> locationList){


		for(Node node : locationList){
			//최대값
			if(node.x > maxX) maxX = node.x;
			if(node.y > maxY) maxY = node.y;

			//최소값
			if(node.x < minX) minX = node.x;
			if(node.y < minY) minY = node.y;
		}

		int height = (int)( maxY - minY + 1);
		int width = (int)(maxX - minX + 1);

		String[] returnArray = new String[height];
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < width; i++){
			sb.append(".");
		}

		for(int i = 0; i < height; i++){
			returnArray[i] = sb.toString();
		}

		for(Node node : locationList){
			int tempY = (int)(maxY - node.y);
			int tempX = (int)(node.x - minX);

			returnArray[tempY] = returnArray[tempY].substring(0,tempX) + "*" + returnArray[tempY].substring(tempX + 1);
		}


		return returnArray;

	}

	public String[] solution(int[][] line) {

		maxX = Long.MIN_VALUE;
		maxY = Long.MIN_VALUE;
		minX = Long.MAX_VALUE;
		minY = Long.MAX_VALUE;


		//반복문을 이용해서 좌표리스트 구하기.
		List<Node> locationList = new ArrayList<>();
		for(int i = 0; i < line.length - 1; i++){
			for(int j = i + 1; j < line.length; j++){

				if(i == j) continue;

				Node tempLoc = getLoc(line[i], line[j]);

				if(tempLoc == null) continue;

				locationList.add(tempLoc); //정수 좌표면 저장.

			}
		}

		return getArray(locationList);
	}

	public static void main(String[] args) {

		Prog_교점에_별_만들기 s = new Prog_교점에_별_만들기();

		int[][] line1 = {{2, -1, 4}, {-2, -1, 4}, {0, -1, 1}, {5, -8, -12}, {5, 8, 12}};
		System.out.println(Arrays.toString(s.solution(line1)));

		int[][] line2 = {{0, 1, -1}, {1, 0, -1}, {1, 0, 1}};
		System.out.println(Arrays.toString(s.solution(line2)));

		int[][] line3 = {{1, -1, 0}, {2, -1, 0}};
		System.out.println(Arrays.toString(s.solution(line3)));

		int[][] line4 = {{1, -1, 0}, {2, -1, 0}, {4, -1, 0}};
		System.out.println(Arrays.toString(s.solution(line4)));

	}
}
