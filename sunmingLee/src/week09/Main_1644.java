import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_1644 {
    static int n;
    static List<Integer> primeList = new ArrayList<>();    // n 이하의 소수 리스트

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        if (n == 1) {
            System.out.println(0);
            System.exit(0);
        }

        findPrimes();

        System.out.println(findCases());
    }   // end of main

    /**
     * 가능한 경우의 수 탐색
     */
    private static int findCases() {
        int count = 0;
        int left = 0, right = 0;
        int tempSum = primeList.get(left);
        boolean moveRight = false;

        int size = primeList.size();
        while (right < size) {
            if (moveRight) {
                tempSum += primeList.get(right);
            }

            if (tempSum == n) {
                count++;
                right++;
                moveRight = true;
            } else if (tempSum < n) {
                right++;
                moveRight = true;
            } else {
                tempSum -= primeList.get(left);
                left++;
                moveRight = false;
            }
        }

        return count;
    }   // end of findCases

    /**
     * n 이하의 소수 탐색
     */
    private static void findPrimes() {
        boolean[] primeCheck = new boolean[n + 1];

        for(int i = 2; i <= Math.sqrt(n); i++){

            if(primeCheck[i]) continue;

            for(int j = i*2; j <= n; j += i){
                primeCheck[j] = true;
            }
        }


        for(int i = 2; i <= n; i++){
            if(primeCheck[i]) continue;

            primeList.add(i);
        }
    }   // end of findPrimes
}
