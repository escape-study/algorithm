package week29;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 구현
 * 말은 전부 노드로 구성한다.
 * 각 노드는 번호를 키값으로해서 map에 저장하여 위치를 찾기 쉽도록 한다.
 * 말은 링크드리스트로 구현하여 옮기기 쉽게 만들도록 한다.
 * 역으로 뒤집는 것을 대비하여,양방향 링크드리스트로 구성한다.
 */

public class BOj17780_새로운게임 {

	//방향 - 우,좌,상,하
	private final static int[] dx = {0, 0, -1, 1};
	private final static int[] dy = {1, -1, 0, 0};

	private static class Node{
		int num, dir;
		int x, y;

		Node left, right;

		public Node(int x, int y, int num, int dir){
			this.x = x;
			this.y = y;
			this.num = num;
			this.dir = dir;
		}

		//뒤에 말 추가.
		private void addNode(Node node){

			Node currentNode = this;
			while(currentNode.right != null){
				currentNode = currentNode.right;
			}
			currentNode.right = node;
			node.left = currentNode;
		}

		private void updateLocation(int nextX, int nextY){
			this.x = nextX;
			this.y = nextY;
		}

		@Override
		public String toString() {
			return "Node{" +
				"num=" + num +
				", dir=" + dir +
				", x=" + x +
				", y=" + y +
				'}';
		}
	}



	//체스판 크기
	private static int N;
	//말의 개수
	private static int K;
	private static Node[][] nodeMaps; //말 정보를 표시할 배열.
	private static int[][] maps; //색을 표시할 배열
	private static Map<Integer, Node> nodeMapping;

	//방향전환
	private static int reverseDir(int dir){

		return (dir == 0 || dir == 2) ? dir + 1 : dir - 1;
	}

	//시작노드를 받아서, 역순으로 뒤집고, 마지막 노드 반환하는 메서드
	private static Node reversNode(Node firstNode){

		//마지막 노드 찾기
		Node lastNode = firstNode;

		while(lastNode.right != null){
			 lastNode = lastNode.right;
		}

		Node currentNode = lastNode;
		Node nextNode = null;
		while(currentNode != null){

			nextNode = currentNode.left;

			currentNode.left = currentNode.right;
			currentNode.right = nextNode;

			currentNode = nextNode;

		}

		return lastNode;
	}

	//말의 위치 업데이트
	private static void updateNodeLocation(Node currentNode, int nextX, int nextY){

		while(currentNode != null){

			currentNode.updateLocation(nextX, nextY);

			currentNode = currentNode.right;
		}

	}

	//해당 칸에 말이 4개 쌓였는지 확인하는 메서드.
	private static boolean pieceCountCheck(int nextX, int nextY){

		Node currentNode = nodeMaps[nextX][nextY];
		int count = 0;

		while(currentNode != null){

			count++;
			currentNode = currentNode.right;

			if(count >= 4) return true;
		}

		return false;
	}

	//선택한 노드를 기존 노드에서 떼는 메서드
	private static void popNode(Node currentNode){

		//떼려고 하는 노드가 첫번째 노드라면,
		if(currentNode.left == null){
			nodeMaps[currentNode.x][currentNode.y] = null;
		}
		//첫번째 노드가 아니라면,
		else{
			currentNode.left.right = null;
			currentNode.left = null;
		}

	}

	//선택한 노드를 특정위치에 추가하는 메서드
	private static void pushNode(Node currentNode, int nextX, int nextY){

		updateNodeLocation(currentNode, nextX, nextY);
		if(nodeMaps[nextX][nextY] == null){
			nodeMaps[nextX][nextY] = currentNode;
		}
		else{
			nodeMaps[nextX][nextY].addNode(currentNode);
		}
	}


	//색깔에 따라서 이동 처리 하는 메서드
	private static boolean movePiece(int color, Node currentNode, int nextX, int nextY){

		boolean returnState = true;
		//흰색일때
		if(color == 0){
			popNode(currentNode);
			pushNode(currentNode, nextX, nextY);
		}
		//빨간색일때.
		else if(color == 1){
			popNode(currentNode);
			currentNode = reversNode(currentNode);
			pushNode(currentNode, nextX, nextY);
		}
		//파란색일때.
		else{

			//방향전환
			currentNode.dir = reverseDir(currentNode.dir);

			//다음위치 확인.
			nextX = currentNode.x + dx[currentNode.dir];
			nextY = currentNode.y + dy[currentNode.dir];


			//방향전환했는데도 이동 불가능 하거나 파란색이면 종료
			if(!check(nextX, nextY) || maps[nextX][nextY] == 2){

				return true;
			}
			else returnState = movePiece(maps[nextX][nextY], currentNode, nextX, nextY);

		}

		//4개이상이 되면 false리턴.
		if(pieceCountCheck(nextX, nextY) || !returnState) return false;

		return true;
	}

	//방문 체크
	private static boolean check(int nextX, int nextY){

		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < N;

	}

	private static void print(){
		for(int i = 1; i <= K; i++){
			System.out.println(nodeMapping.get(i));
		}

		System.out.println("+++++++++++++++++++++++++");
	}

	private static int logic(){

		int count = 0;
		while(count++ <= 1000){

			for(int i = 1; i <= K; i++){
				Node node = nodeMapping.get(i);

				//해당 말이 가장 아래가 아니면 패스
				if(node.left != null) continue;

				//다음 위치 확인.
				int nextX = node.x + dx[node.dir];
				int nextY = node.y + dy[node.dir];

				boolean state = false; //말을 움직인후 처리 상태(false : 실패, true : 성공)
				//체스판을 벗어나는 경우는 파란색으로 처리.
				if(!check(nextX, nextY)) state = movePiece(2, node, nextX, nextY);
				else state = movePiece(maps[nextX][nextY], node, nextX, nextY);

				//해당 위치에 말이 4개가 되엇는지 확인
				if(!state) return count;

			}


		}

		return count;
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		nodeMaps = new Node[N][N];
		maps = new int[N][N];
		nodeMapping = new HashMap<>();

		//맵 입력
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++){
				maps[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for(int i = 0; i < K; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int dir = Integer.parseInt(st.nextToken()) - 1;

			Node node = new Node(a, b,i + 1, dir);
			nodeMapping.put(i + 1, node);
			nodeMaps[a][b] = node;
		}

		int result = logic();

		if(result > 1000)
			System.out.println(-1);
		else
			System.out.println(result);

	}
}
