package week26;

import java.util.*;

public class Solution_길찾기게임 {
    class Node {
        int num, x, y;
        Node left, right;

        public Node(int num, int x, int y) {
            this.num = num;
            this.x = x;
            this.y = y;
        }
    }

    private Node root;
    private List<Integer> preorder, postorder;

    public int[][] solution(int[][] nodeinfo) {
        int[][] answer = new int[2][nodeinfo.length];

        List<Node> arrList = new ArrayList<>();
        for (int i = 0; i < nodeinfo.length; i++) {
            arrList.add(new Node(i + 1, nodeinfo[i][0], nodeinfo[i][1]));
        }

        makeTree(arrList);

        preorder = new ArrayList<>();
        preOrder(root);

        postorder = new ArrayList<>();
        postOrder(root);

        answer[0] = preorder.stream().mapToInt(i -> i).toArray();
        answer[1] = postorder.stream().mapToInt(i -> i).toArray();
        return answer;
    }   // end of solution

    private void makeTree(List<Node> arrList) {
        Collections.sort(arrList, (a, b) -> b.y - a.y); // y 내림차순 정렬

        root = arrList.get(0);
        Node subRoot;
        for (int i = 1; i < arrList.size(); i++) {
            subRoot = root;
            Node cur = arrList.get(i);

            while (true) {
                if (subRoot.x > cur.x) {
                    if (subRoot.left == null) {
                        subRoot.left = cur;
                        break;
                    }

                    subRoot = subRoot.left;
                } else {
                    if (subRoot.right == null) {
                        subRoot.right = cur;
                        break;
                    }

                    subRoot = subRoot.right;
                }
            }
        }
    }   // end of makeTree

    private void preOrder(Node cur) {
        if (cur == null) {
            return;
        }

        preorder.add(cur.num);
        preOrder(cur.left);
        preOrder(cur.right);
    }   // end of preOrder

    private void postOrder(Node cur) {
        if (cur == null) {
            return;
        }

        postOrder(cur.left);
        postOrder(cur.right);
        postorder.add(cur.num);
    }   // end of preOrder
}
