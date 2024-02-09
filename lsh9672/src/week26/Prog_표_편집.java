package week26;

import java.io.File;
import java.util.*;

/**
 * 아이디어
 * 단순 구현
 * 링크드 리스트를 만들어서 구현하는 것이 아닌, 인덱스가 있는 자료형을 사용해야 한다.
 * D X 형태로 주어졌을때 X는 30만까지 가능하기 때문에 인덱스로 접근하지 않으면 30만번 반복해야 되는 상황이 발생할 수도 있다.
 *(수정)
 * 인덱스가 존재하는 리스트 형태로 delete연산과 insert연산을 하면, 리스트 크기가 100만이라 시간초과가 나게 된다.
 * 최악의 경우를 생각해보면, 리스트가 100만개이고, 0번째 인덱스를 삭제, 삽입 연산을 반복하면, 나머지 (100만 - 1)개만큼 반복한다.
 * (100만 - 1)개가 인덱스를 한칸씩 밀었다, 당겼다를 반복하게 되기때문에 cmd의 최대개수인 20만번 반복하면
 * (100만 - 1) * 20만이므로 시간초과하게 된다.
 * 문제에서 이동을 하는 U와 D의 경우에는, cmd에 있는 모든 이동칸수(X)를 합쳐도 100만이 최대라고 했으니,
 * 커서 이동을 O(N)으로, 삽입 삭제를 O(1)으로 만들어서 해결하는 것이 좋다.
 * 이를 구현하기 위해서는 링크드 리스트를 만들어서 해결할 수 잇다.
 * */

public class Prog_표_편집 {


    private static class Node{

        int value;
        boolean isDelete;//삭제여부
        boolean isDummy;//시작과 끝에 넣을 더미노드.
        Node left, right;
        public Node(int value){
            this.value = value;
            this.isDelete = false;
            this.isDummy = false;
        }
        public static Node createDummyNode(){

            Node temp = new Node(-1);
            temp.value = -1;
            temp.isDelete = false;
            temp.isDummy = true;

            return temp;
        }

        public void addFirst(Node node){
            node.right = this;
            this.left = node.right;
        }

        public void addLast(Node node){
            this.right = node;
            node.left = this;
        }
    }


    //시작노드
    private static Node root;
    //현재 위치 표시.
    private static Node currentLocation;

    //삭제한 값들을 가지고 있을 스택,
    private static Stack<Node> undoStack;

    //초기화.
    private static void init(int n, int k){

        Node dummyNode = Node.createDummyNode();

        undoStack = new Stack<>();

        Node currentNode = new Node(0);
        root = currentNode;

        root.left = dummyNode;//앞에 더미노드 추가.
        for(int i = 1; i < n; i++){
            Node node = new Node(i);
            currentNode.addLast(node);

            if(i == k) currentLocation = node;

            currentNode = node;
        }

        //마지막에 더미노드 추가
        currentNode.right = dummyNode;
    }

    //명령어 실행.
    private static void execute(char command, int num){

        int index = 0;
        switch(command){
            case 'U': //위로 이동 - 표의 범위를 벗어나는 이동은 안줌.
                index = 0;
                while(index < num){

                    currentLocation = currentLocation.left;
                    index++;
                }

                break;
            case 'D': //아래로 이동
                index = 0;
                while(index < num){
                    currentLocation = currentLocation.right;
                    index++;
                }
                break;
            case 'C': // 삭제 후 아래 행 선택
                undoStack.add(currentLocation);
                currentLocation.isDelete = true;

                //남은 노드들 연결하기
                currentLocation.left.right = currentLocation.right;
                currentLocation.right.left = currentLocation.left;

                //마지막 행이면 위를 선택
                if(currentLocation.right.isDummy){
                    currentLocation = currentLocation.left;
                }
                //마지막 행이 아니면 아래를 선택
                else{
                    currentLocation = currentLocation.right;
                }
                break;
            default: // 되돌리기.
                Node undoNode = undoStack.pop(); //되돌릴 노드 꺼내기
                undoNode.isDelete = false;
                //되돌릴때는 왼쪽,오른쪽노드를 확인해서 삭제되지 않은 노드면 그 노드로 연결해야 함.
                if(!undoNode.left.isDelete){
                    undoNode.right = undoNode.left.right;
                    undoNode.right.left = undoNode;
                    undoNode.left.right = undoNode;
                }
                else{
                    undoNode.left = undoNode.right.left;
                    undoNode.left.right = undoNode;
                    undoNode.right.left = undoNode;
                }
                break;
        }

    }

    //로직 - 명령어 리스트를 파싱해서 실행하기
    private static void logic(String[] cmd){

        char commandChr = ' ';
        int num = 0;

        for(String tempCmd : cmd){
            String[] commandArray = tempCmd.split(" "); //공백을 기준으로 자르기.

            //명령어 길이가 1 => C or Z
            if(commandArray.length == 1){
                commandChr = commandArray[0].charAt(0);
            }

            else{
                commandChr = commandArray[0].charAt(0);
                num = Integer.parseInt(commandArray[1]);
            }

            execute(commandChr, num);//명령어 실행.
        }

    }

    //그래프에 남아있는 값과 삭제된 값 비교.
    private static String getResult(int n){

        StringBuilder returnValue = new StringBuilder();

        Node currentNode = root;
        boolean[] deleteCheck = new boolean[n]; // true이면 삭제된 것.

        //삭제 처리된 노드들을 하나씩 꺼내서 deleteCheck배열에 true로 체크
        while(!undoStack.isEmpty()){
            Node undoNode = undoStack.pop();
            deleteCheck[undoNode.value] = true;
        }

        for(int i = 0; i < n; i++){

            //배열의 값이 true이면 삭제 된 노드
            if(deleteCheck[i]) returnValue.append('X');
            //배열의 값이 false이면 삭제되지 않은 노드.
            else returnValue.append('O');
        }

        return returnValue.toString();
    }

    public String solution(int n, int k, String[] cmd) {

        init(n, k);

        logic(cmd);

        return getResult(n);
    }

    public static void main(String[] args) {
        Prog_표_편집 s = new Prog_표_편집();

//        int n1 = 8;
//        int k1 = 2;
//        String[] cmd1 = {"D 2","C","U 3","C","D 4","C","U 2","Z","Z"};
//        System.out.println(s.solution(n1, k1, cmd1));

        int n2 = 8;
        int k2 = 2;
        String[] cmd2 = {"D 2","C","U 3","C","D 4","C","U 2","Z","Z","U 1","C"};
        System.out.println(s.solution(n2, k2, cmd2));

//        System.out.println(Arrays.toString("Z".split(" ")));

    }
}
