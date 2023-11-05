import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_19942 {
    static class Food {
        int protein, fat, carbo, vitamin;
        int cost;

        public Food(int protein, int fat, int carbo, int vitamin, int cost) {
            this.protein = protein;
            this.fat = fat;
            this.carbo = carbo;
            this.vitamin = vitamin;
            this.cost = cost;
        }

        public void addFood(Food added) {
            this.protein += added.protein;
            this.fat += added.fat;
            this.carbo += added.carbo;
            this.vitamin += added.vitamin;
            this.cost += added.cost;
        }

    }

    static class Selection implements Comparable<Selection> {
        List<Integer> indexList;
        int cost;

        public Selection(List<Integer> indexList, int cost) {
            this.indexList = indexList;
            this.cost = cost;
        }

        @Override
        public int compareTo(Selection o) {
            if (this.cost == o.cost) {
                String a = "";
                for (int i = 0; i < this.indexList.size(); i++) {
                    a += this.indexList.get(i);
                }

                String b = "";
                for (int i = 0; i < o.indexList.size(); i++) {
                    b += o.indexList.get(i);
                }

                return a.compareTo(b);
            }
            return this.cost - o.cost;
        }
    }

    static int N;
    static Food minNutrients;
    static Food[] foods;
    static List<Selection> answers;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        minNutrients = new Food(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 0);

        foods = new Food[N + 1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            foods[i] = new Food(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        answers = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            boolean[] visited = new boolean[N + 1];
            comb(0, i, 1, visited);
        }

        if (answers.size() == 0) {
            System.out.println(-1);
            System.exit(0);
        }

        Collections.sort(answers);

        StringBuilder sb = new StringBuilder();
        Selection answer = answers.get(0);
        sb.append(answer.cost + "\n");
        int size = answer.indexList.size();
        for (int i = 0; i < size; i++) {
            sb.append(answer.indexList.get(i) + " ");
        }

        System.out.println(sb);

    }   // end of main

    /**
     * 조합(nCr) 수행
     */
    private static void comb(int r, int n, int start, boolean[] visited) {
        if (r == n) {
            check(visited);
            return;
        }

        for (int i = start; i <= N; i++) {
            visited[i] = true;
            comb(r + 1, n, i + 1, visited);
            visited[i] = false;
        }

    }   // end of comb

    /**
     * 조합의 결과가 최소 영양분을 만족하는지 확인
     */
    private static void check(boolean[] visited) {
        Food temp = new Food(0, 0, 0, 0, 0);
        List<Integer> index = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            if (visited[i]) {
                index.add(i);
                temp.addFood(foods[i]);
            }
        }

        if (compare(temp)) {
            answers.add(new Selection(index, temp.cost));
        }
    }   // end of check


    /**
     * 현재 고른 식재료들(temp)이 최소 영양분을 넘었으면 true
     */
    private static boolean compare(Food temp) {
        return temp.protein >= minNutrients.protein && temp.fat >= minNutrients.fat && temp.carbo >= minNutrients.carbo && temp.vitamin >= minNutrients.vitamin;
    }   // end of compare

}