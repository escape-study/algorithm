package week23;

public class PROG_도넛과막대그래프 {

    public int[] solution(int[][] edges) {
        int Max = 1_000_001;

        int input[] = new int[Max];
        int output[] = new int[Max];

        int maxNode = 0;

        for (int []edge : edges) {
            input[edge[1]]++;
            output[edge[0]]++;

            maxNode = Math.max(maxNode , Math.max(edge[0] , edge[1]));
        }

        int answer[] = new int[4];

        for (int node  = 1; node <= maxNode; node++) {
            if(input[node] == 0 && output[node] >= 2){
                answer[0] = node;
            }else if(output[node] == 0){
                answer[2]++;
            }else if(input[node] >= 2 && output[node] == 2){
                answer[3]++;
            }
        }

        answer[1] = output[answer[0]] - (answer[2] + answer[3]);

        return answer;
    }
}