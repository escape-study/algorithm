class Solution_연속된부분수열의합 {
    public int[] solution(int[] sequence, int k) {
        int[] answer = new int[2];
        int left = 0, right = 0;
        int minLength = sequence.length + 1;    // 가장 짧은 수열의 길이
        int sum = sequence[0];    // 현재 수열의 합
        while (left < sequence.length) {
            if (sum < k) {
                right++;
                if (right == sequence.length) {
                    break;
                }
                sum += sequence[right];
            } else if (sum == k) {
                // 앞에서부터 탐색하기 때문에 가장 짧았던 수열의 길이보다 짧은것만 가능함
                if (right - left + 1 < minLength) {
                    answer[0] = left;
                    answer[1] = right;
                    minLength = right - left + 1;
                }

                right++;
                if (right == sequence.length) {
                    break;
                }
                sum += sequence[right];
                sum -= sequence[left++];
            } else {
                sum -= sequence[left++];
            }
        }

        return answer;
    }
}