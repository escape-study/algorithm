package week16;

import java.util.Scanner;

public class Main_15927 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String str = sc.nextLine();

        //case1 str == !isPalindrome
        boolean isPalin = false;
        for (int i = 0; i < str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(str.length() - 1 - i)) {
                System.out.println(str.length());
                return;
        }
        isPalin = true;
        }
        //case2 str == isPalindrome
        if (isPalin) {
            for (int i = 1; i < str.length(); i++) {
                if (str.charAt(0) != str.charAt(i)) {
                    System.out.println(str.length() - 1);
                    return;
                }
            }

        }
        System.out.println(-1);


    }
}
