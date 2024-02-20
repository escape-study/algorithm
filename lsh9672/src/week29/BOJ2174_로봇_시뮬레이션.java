package week29;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 구현
 * 주어진 좌표를 배열로 변환하도록 하면 된다
 * 좌표계로 주어졌기 떄문에 배열로 만들지 않고, 좌표로만 계산하는게 구현하기 편하지만
 * 최대 100개의 로봇들이 존재하고 충돌 유무를 확인하기 위해서 좌표평면으로 만들어서 연산하는 것이 좋다.
 */
public class BOJ2174_로봇_시뮬레이션 {

	//4방향- 우 상 좌 하 => L이면 +1, R이면 -1
	private final static int[] dx = {0, -1, 0, 1};
	private final static int[] dy = {1, 0, -1, 0};

	//로봇 정보 저장.
	private static class Node{
		int x, y, dir;
		public Node(int x, int y, int dir){
			this.x = x;
			this.y = y;
			this.dir = dir;
		}

		void turnRight(int count){
			this.dir = (this.dir + (3 * count)) % 4;
		}
		void turnLeft(int count){
			this.dir = (this.dir + count) % 4;
		}

		void locationUpdate(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	private static int A;//가로
	private static int B;//세로

	private static int[][] maps;//지도

	//로봇 정보 가지고 있는 배열
	private static Node[] robotInfo;

	//x좌표를 배열의 x로 변환
	private static int convertX(int x){
		return x - 1;
	}
	//y좌표를 배열의 y로 변환.
	private static int convertY(int y){
		return B - y;
	}

	//격자판 벗어나는지 확인
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < B &&
			nextY >= 0 && nextY < A;
	}

	//각각의 명령어 처리. - L,R,F
	private static boolean logic(int robotNum, String command, int count){


		Node robotNode = robotInfo[robotNum];

		//왼쪽
		if(command.equals("L")){
			robotNode.turnLeft(count);
		}
		//오른쪽.
		else if(command.equals("R")){
			robotNode.turnRight(count);
		}
		//전진.
		else{
			int currentX = robotNode.x;
			int currentY = robotNode.y;

			for(int i = 1; i <= count; i++){
				currentX += dx[robotNode.dir];
				currentY += dy[robotNode.dir];

				//벽에 충돌하면 종료.
				if(!check(currentX, currentY)){
					crashStr(1, robotNum, -1);
					return true;
				}

				//로봇에 충돌하면 종료.
				if(maps[currentX][currentY] != 0){
					crashStr(2, robotNum, maps[currentX][currentY]);
					return true;
				}
			}

			//로봇 위치 업데이트.
			maps[robotNode.x][robotNode.y] = 0;
			robotNode.locationUpdate(currentX, currentY);
			maps[currentX][currentY] = robotNum;
		}

		return false;
	}

	//충돌 종류에 따른 문자열 반환. - 벽충돌: 1(y값은 -1), 로봇충돌 2
	private static void crashStr(int type, int x, int y){

		if(type == 1)
			System.out.println("Robot " + x + " crashes into the wall");

		else if(type == 2)
			System.out.println("Robot " + x +  " crashes into robot " + y);
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());


		A = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		maps = new int[B][A];


		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		robotInfo = new Node[N + 1];
		//방향에 따른 인덱스 저장.
		Map<String, Integer> dirMaps = new HashMap<>();
		dirMaps.put("E", 0);
		dirMaps.put("N", 1);
		dirMaps.put("W", 2);
		dirMaps.put("S", 3);

		//로봇 셋팅
		for(int i = 1; i <= N; i++){
			st = new StringTokenizer(br.readLine());
			int x = convertX(Integer.parseInt(st.nextToken()));
			int y = convertY(Integer.parseInt(st.nextToken()));
			maps[y][x] = i;
			robotInfo[i] = new Node(y, x, dirMaps.get(st.nextToken()));
		}

		boolean flag = false;

		//명령어 실행
		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());
			int robotNum = Integer.parseInt(st.nextToken());
			String command = st.nextToken();
			int count = Integer.parseInt(st.nextToken());

			//충돌이 발생하면 종료.
			if(logic(robotNum, command, count)){
				flag = true;
				break;
			}
		}

		if(!flag) System.out.println("OK");


	}

}
