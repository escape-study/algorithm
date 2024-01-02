package week22;

public class PROG_행렬테두리 {
    public int[] solution(int rows, int columns, int[][] queries) {
        
        int[] answer = new int[queries.length];
        
        int Map[][] = new int[rows+1][columns+1];
        for(int i = 1 ; i <= rows ; i++){
            for(int j = 1 ; j <= columns; j++){
                Map[i][j] = columns*(i-1) + j;
            }
        }
        
        
        for(int q = 0 ; q < queries.length ; q++){
            int x1 = queries[q][0];
            int y1 = queries[q][1];
            int x2 = queries[q][2];
            int y2 = queries[q][3];
            
            int tmp = Map[x1][y1];
            int min = tmp;
            
            for(int i = x1+1 ; i <= x2 ; i++ ){  // 왼쪽 밑에서 위로
                Map[i-1][y1] = Map[i][y1];
                min = Math.min(Map[i-1][y1] , min);
            }
            
            for(int i = y1 + 1 ; i  <= y2 ; i++){ // 아래쪽 오른쪽에서 왼쪽
                Map[x2][i - 1] = Map[x2][i];
                min = Math.min(Map[x2][i - 1] , min);
            }
            
            for(int i = x2-1 ; i >= x1 ; i--){ // 오른쪽 
                Map[i+1][y2] = Map[i][y2];
                min = Math.min(Map[i+1][y2] , min);
            }
            
            for(int i = y2-1 ; i >= y1 ; i--){ // 위쪽
                Map[x1][i+1] = Map[x1][i];
                min = Math.min(Map[x1][i+1] , min);
            }
            
            Map[x1][y1+1] = tmp;
            
            answer[q] = min;
        }
        
        
        
        return answer;
    }
}