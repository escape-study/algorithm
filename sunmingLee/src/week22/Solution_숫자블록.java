class Solution_숫자블록 {
    public int[] solution(long begin, long end) {
        int start = (int) begin;
        int finish = (int) end;
        int index = 0;
        int[] answer = new int[finish - start + 1];
        for (int i = start; i <= finish; i++) {
            int largest = 1;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    largest = Math.max(largest, j);

                    if (i / j <= 10_000_000) {
                        largest = i / j;
                        break;
                    }
                }
            }

            answer[index] = largest;
            index++;
        }

        if (start == 1) {
            answer[0] = 0;
        }

        return answer;
    }   // end of solution
}