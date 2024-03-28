/*
제목 : 뱀
알고리즘 유형 : #implementation , #simulation , #queue
플랫폼 : #BOJ
난이도 : G4
문제번호 : 2589
시간 : 20m
해결 : O
저장 : X
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/3190
특이사항 : #esalgo-week03
*/

import java.io.*;
import java.util.*;

public class BOJ_3190_뱀 {
	static class BAEM{
		int time;
		char operation;
		public BAEM(int time, char operation) {
			super();
			this.time = time;
			this.operation = operation;
		}
	}
	
	static int[][][] map;
	static int N;
	static List<BAEM> baemOperation = new ArrayList<>();
	static int[] dy = {0,0,1,0,-1};
	static int[] dx = {0,1,0,-1,0};
	static int dire = 1;
	static int baemX, baemY; //뱀 머리
	static int time;
	public static void main(String[] args) throws NumberFormatException, IOException {
		input();
		flowTime();
		System.out.println(time);
	}
	
	public static void flowTime() {
		time = 0;
		int rearY = 0;
		int rearX = 0;
		
		while(true) {
			// 방향전환 시간이 아닐때
			//logic
			int headY = baemY + dy[dire];
			int headX = baemX + dx[dire];
			time++;
			// 맵 밖으로 나가면 while문 중지
			if(headY >= N || headY < 0 || headX >=N || headX < 0) {
				break;
			} else { // 맵 안에 있는데 
				// 가려는 곳에 자기 몸통이 있다면 중지
				if(map[headY][headX][0] == 1) {
					break;
				}
				// 가려는 곳에 사과가 있을 때
				else if(map[headY][headX][0] == 2) {
					map[headY][headX][0] = 1;
				} 
				// 가려는 곳에 아무것도 없을 때 
				else {
					map[headY][headX][0] = 1; // 머리 채우기
					map[rearY][rearX][0] = 0; // 꼬리 빈칸으로 만들기
					int tempDir = map[rearY][rearX][1]; // 방향 초기화 (1234 이 아닌 숫자로)
					map[rearY][rearX][1] = 0;
					rearY += dy[tempDir];
					rearX += dx[tempDir];

				}
				baemY = headY;
				baemX = headX;
			}

			// 로직끝나고 방향전환
			if (!baemOperation.isEmpty()) {
				if (baemOperation.get(0).time == time) {
					dire = changeDirection(baemOperation.get(0).operation, dire);
					baemOperation.remove(0);
				}
			}
			
			// 방향 정보를 맵에 저장.
			map[headY][headX][1] = dire;
		}
	}
	
	public static int changeDirection(char op, int dire) {
		switch (op) {
		case 'L':
			if(dire == 1) {
				return 4;
			}
			return dire-1;
		case 'D':
			if(dire == 4) {
				return 1;
			}
			return dire+1;
		}
		return 0;
	}
	
	public static void input() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		map = new int[N][N][2];
		
		int K = Integer.parseInt(br.readLine());
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			map[Integer.parseInt(st.nextToken())-1][Integer.parseInt(st.nextToken())-1][0] = 2; // 사과위치
		}
		map[0][0][0] = 1; // 뱀위치
		map[0][0][1] = 1; // 방향정보
		baemX = 0;
		baemY = 0;
		
		int L = Integer.parseInt(br.readLine());
		for (int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine());
			baemOperation.add(new BAEM(Integer.parseInt(st.nextToken()) , st.nextToken().charAt(0)));
		}
	}
}
