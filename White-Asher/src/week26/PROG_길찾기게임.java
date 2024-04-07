/*
제목 : 길 찾기 게임
알고리즘 유형 : #자료구조
플랫폼 : #Programmers 
난이도 : L3
문제번호 : 42892
시간 : -
해결 : -
저장 : O
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/42892
특이사항 : #esalgo-week26, #2019-KAKAO-BLIND-RECRUITMENT
*/
import java.util.*;

class Node {
    int x, y, value;
    Node left;
    Node right;
    
    public Node(int x, int y, int value, Node left, Node right) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    static int idx;
    static int[][] result  = {};
    public int[][] solution(int[][] nodeinfo) {
        // 노드 입력
        Node[] nodes = new Node[nodeinfo.length];
        
        for(int i = 0; i < nodeinfo.length; i++) {
            nodes[i] = new Node(nodeinfo[i][0], nodeinfo[i][1], i + 1, null, null);
        }
        
        // 좌표 값 순서대로 배열 재정렬
        Arrays.sort(nodes, new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                if(n1.y == n2.y) return n1.x - n2.x;
                else return n2.y - n1.y;
            }
        });
        
        // 트리 생성
        Node root = nodes[0];
        for(int i = 1; i < nodes.length; i++) {
            insertNode(root, nodes[i]);
        }
        
        result = new int[2][nodeinfo.length];
        idx = 0;
        preOrder(root);
        idx = 0;
        postOrder(root);
        
        return result;
    }
    
    public void insertNode(Node parent, Node child) {
        if(parent.x > child.x) {
            if(parent.left == null) parent.left = child;
            else insertNode(parent.left, child);
        } else {
            if(parent.right == null) parent.right = child;
            else insertNode(parent.right, child);
        }
    }
    
    public void preOrder(Node root) {
        if(root != null) {
            result[0][idx++] = root.value;
            preOrder(root.left);
            preOrder(root.right);
        }
    }
    
    public void postOrder(Node root) {
        if(root != null) {
            postOrder(root.left);
            postOrder(root.right);
            result[1][idx++] = root.value;
        }
    }
}