package week20;

import java.util.Arrays;

/**
 * 아이디어
 * 포화이진트리 만들고, 재귀로 루트에서부터 탐색하다가, 서브 트리의 루트가 0인데 자식이 1인 경우가 있으면 트리 불가능.
 * 문제 없으면 트리 가능이다.
 * 포화이진트리의 경우, 2의 제곱수에 루트를 포함하여 - 1한 값이다.(루트는 1개다.)
 * 주어진 수를 이진수로 변환하고 포화이진트리로 만든다.
 * 포화이진트리로 만들떄는 0을 앞에 붙여야 하는데, 뒤에 0을 붙이면 해당 수는 다른 수가 되기 떄문이다.
 */
public class Prog_표현가능한_이진트리 {

    //수를 이진수로 만드는 메서드.
    private static String longToBinary(long number){
        return Long.toBinaryString(number);
    }

    //포화이진트리로 만드는 메서드
    private static String setFullBinaryTree(String binaryNum){

        //트리 높이 계산.
        int height = 1;

        //반복문 돌면서 높이를 하나씩 올려보고 처리.
        while((int)Math.pow(2, height) - 1 < binaryNum.length()){
            height++;
        }

        StringBuilder result = new StringBuilder();
        result.append(binaryNum);

        for(int i = 0; i < (Math.pow(2, height) - 1) - binaryNum.length(); i++){
            result.insert(0, "0");
        }

        return result.toString();
    }

    //재귀돌리면서 트리가 되는지 확인하는 메서드.
    private static boolean dfs(String subBinaryTree, int rootIndex){


        char root = subBinaryTree.charAt(rootIndex); //루트 노드

        String leftSubTree = subBinaryTree.substring(0, rootIndex);//왼쪽 서브 트리
        String rightSubTree = subBinaryTree.substring(rootIndex + 1, subBinaryTree.length());//오른쪽 서브 트리.

        //현재 루트가 0인데, 왼쪽or오른쪽서브트리 루트가 1이면 잘못된 것 - false 리턴
        if(root == '0' &&
                (leftSubTree.charAt(leftSubTree.length()/2) == '1' || rightSubTree.charAt(rightSubTree.length()/2) == '1') ){

            return false;
        }

        //위의 경우에 걸리지 않았고, 길이가 3이상이면 서브트리 존재하기 때문에 재귀호출
        //포화 완전이진트리라 한쪽만 확인하면 됨.
        if(leftSubTree.length() >= 3){

            //재귀 호출
            if(!dfs(leftSubTree, leftSubTree.length()/2)) return false;

            if(!dfs(rightSubTree, rightSubTree.length()/2)) return false;
        }

        return true;
    }

    private static boolean logic(long number){

        //이진수 변환
        String binaryNum = longToBinary(number);

        //포화 완전이진트리로 변환
        String fullBinaryTree = setFullBinaryTree(binaryNum);

        //재귀 호출.
        return dfs(fullBinaryTree, fullBinaryTree.length()/2);
    }

    public int[] solution(long[] numbers) {

        int[] answer = new int[numbers.length];

        for(int i = 0; i < numbers.length; i++){
            if(logic(numbers[i])) answer[i] = 1;
            else answer[i] = 0;
        }

        return answer;
    }
    public static void main(String[] args) {
        Prog_표현가능한_이진트리 s = new Prog_표현가능한_이진트리();
        long[] number1 = {7,42,5};
        System.out.println(Arrays.toString(s.solution(number1)));

        long[] number2 = {63,111,95};
        System.out.println(Arrays.toString(s.solution(number2)));


    }
}
