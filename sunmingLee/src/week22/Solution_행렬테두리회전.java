import java.util.*;

class Solution_행렬테두리회전 {
    int[][] matrix;

    public int[] solution(int rows, int columns, int[][] queries) {
        if(queries.length == 1){
            int i = queries[0][0] - 1;
            int j = queries[0][1];
            return new int[]{i*columns + j};
        }

        int[] answer = new int[queries.length];
        matrix = new int[rows][columns];
        init(rows, columns);    // 초기 행렬 세팅

        for(int i=0; i<queries.length; i++){
            answer[i] = rotate(queries[i]);
        }
        return answer;
    }   // end of solution

    int rotate(int[] query){
        List<Integer> nums = new ArrayList<>();
        int r1 = query[0] - 1;
        int c1 = query[1] - 1;
        int r2 = query[2] - 1;
        int c2 = query[3] - 1;
        int temp = matrix[r1][c1];
        // System.out.println(r1 + " " + c1 + " " + r2 + " " + c2);

        // 왼쪽
        for(int r=r1; r<r2; r++){
            matrix[r][c1] = matrix[r+1][c1];
            nums.add(matrix[r][c1]);
        }

        // 아래쪽
        for(int c=c1; c<c2; c++){
            matrix[r2][c] = matrix[r2][c+1];
            nums.add(matrix[r2][c]);
        }

        // 오른쪽
        for(int r=r2; r>r1; r--){
            matrix[r][c2] = matrix[r-1][c2];
            nums.add(matrix[r][c2]);
        }

        // 위쪽
        for(int c=c2; c>c1+1; c--){
            matrix[r1][c] = matrix[r1][c-1];
            nums.add(matrix[r1][c]);
        }

        matrix[r1][c1+1] = temp;
        nums.add(temp);

        Collections.sort(nums);

        return nums.get(0);
    }   // end of rotate

    void init(int rows, int columns){
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                matrix[i][j] = i*columns + j+1;
            }
        }
    }   // end of init

    void printMatrix(){
        for(int[] i : matrix){
            for(int j : i){
                System.out.print(j + " ");
            }
            System.out.println();
        }
        System.out.println();
    }   // end of printMatrix
}