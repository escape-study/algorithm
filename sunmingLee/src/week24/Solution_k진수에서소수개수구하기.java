class Solution_k진수에서소수개수구하기 {
    public static int solution(int n, int k) {
        int answer = 0;

        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            int remain = n % k;
            n /= k;
            if (remain != 0) {
                sb.insert(0, remain);
                continue;
            }

            if (sb.length() == 0) {
                continue;
            }

            if (isPrime(Long.parseLong(sb.toString()))) {
                answer++;
            }

            sb.setLength(0);
        }

        if (sb.length() > 0 && isPrime(Long.parseLong(sb.toString()))) {
            answer++;
        }

        return answer;
    }   // end of solution

    private static boolean isPrime(long num) {
        if (num == 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }   // end of isPrime

    public static void main(String[] args) {
        System.out.println(solution(27, 2));
    }
}