package week43;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1082_방번호 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static class Number{

        int number;
        int price;

        Number(int number, int price){
            this.number = number;
            this.price = price;
        }

    }
    public static void main(String[] args) throws IOException {

        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(br.readLine());

        Number[] numbers = new Number[N];
        List<Number> list = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            numbers[i] = new Number(i , Integer.parseInt(st.nextToken()));
        }

        Arrays.sort(numbers, Comparator.comparingInt(number -> number.price));

        if(numbers[0].number == 0){
            if(numbers.length == 1 || M < numbers[1].price){
                System.out.println(0);
                return;
            }
            list.add(new Number(numbers[1].number , numbers[1].price));
            M -= numbers[1].price;
        }

        while (true){
            if(M - numbers[0].price < 0) break;
            M -= numbers[0].price;
            list.add(new Number(numbers[0].number , numbers[0].price));
        }

        for (int i = 0; i < list.size(); i++) {

            Number cur = list.get(i);

            int maxNum = cur.number;
            int maxPrice = cur.price;
            for (int j = numbers.length-1; j >= 0 ; j--) {
                if(maxNum < numbers[j].number && numbers[j].price - cur.price <= M){
                    maxNum = numbers[j].number;
                    maxPrice = numbers[j].price;
                }
            }
            M -= maxPrice - cur.price;
            list.set(i, new Number(maxNum, maxPrice));
        }
        list.stream().mapToInt(number -> number.number).forEach(System.out::print);
    }
}