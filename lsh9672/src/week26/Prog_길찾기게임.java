package week26;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 아이디어
 * 먼저 트리를 구성해야 한다.
 * Y값을 기준으로 정렬을 하면 루트부터 내려가게 된다.
 * 각 값을 반복문을 돌면서 확인하는데, x값이 부모값보다 작다면,왼쪽 크면 오른쪽에 둔다.
 * 트리를 구성하면 단순하게 반복돌면서 전위순회, 후위순회 하면 된다.
 */

public class Prog_길찾기게임 {

    //노드
    private static class Node{
        int num, x, y;
        Node left, right;

        public Node(int num, int x, int y){
            this.num = num;
            this.x = x;
            this.y = y;
            this.left = null;
            this.right = null;
        }

        public void addLeft(Node node){
            this.left = node;
        }
        public void addRight(Node node){
            this.right = node;
        }

    }

    //루트 노드
    private static Node root;

    //전위순회배열인덱스
    private static int preOrderIndex;
    //후위순회 배열 인덱스
    private static int postOrderIndex;

    //전위순회 배열
    private static int[] preOrderArray;
    //후위순회 배열
    private static int[] postOrderArray;

    //트리 구성하는 메서드
    private static void makeTree(int[][] nodeinfo){
        List<Node> sortList = new ArrayList<>();
        for(int i = 0; i < nodeinfo.length; i++){
            sortList.add(new Node(i + 1, nodeinfo[i][0], nodeinfo[i][1]));
        }
        
        Collections.sort(sortList, (node1, node2) -> node2.y - node1.y);

        root = sortList.get(0);
        Node subRoot;
        for(int i = 1; i < sortList.size(); i++){
            subRoot = root;
            Node currentNode = sortList.get(i);

            while(true){
                //해당 노드가 현재 루트노드보다 x좌표가 작을떄
                if(subRoot.x > currentNode.x) {
                    if(subRoot.left == null){
                        subRoot.left = currentNode;
                        break;
                    }
                    subRoot = subRoot.left;
                }
                //해당 노드가 현재 루트노드보다 x좌표가 클때
                else{
                    if(subRoot.right == null){
                        subRoot.right = currentNode;
                        break;
                    }
                    subRoot = subRoot.right;
                }
            }
        }
    }

    //전위순회
    private static void preOrder(Node currentNode){

        if(currentNode == null) return;

        preOrderArray[preOrderIndex++] = currentNode.num;
        preOrder(currentNode.left);
        preOrder(currentNode.right);

    }

    //후위 순회.
    private static void postOrder(Node currentNode){

        if(currentNode == null) return;

        postOrder(currentNode.left);
        postOrder(currentNode.right);
        postOrderArray[postOrderIndex++] = currentNode.num;

    }



    public int[][] solution(int[][] nodeinfo) {

        int[][] result = new int[2][nodeinfo.length];
        preOrderIndex = 0;
        postOrderIndex = 0;
        preOrderArray = new int[nodeinfo.length];
        postOrderArray = new int[nodeinfo.length];

        makeTree(nodeinfo);

        preOrder(root);
        postOrder(root);


        result[0] = preOrderArray;
        result[1] = postOrderArray;
        return result;
    }

    public static void main(String[] args) {
        Prog_길찾기게임 s = new Prog_길찾기게임();

    }
}
