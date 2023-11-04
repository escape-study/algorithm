package week13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * 아이디어
 * 완탐 - 부분집합
 * 비용이 최소가 되는 선택을 하기 위해 몇개를 골라야 한다.
 * 즉, 3개를 고를 수도 있고, 4개를 고를 수도 있다.
 * 또한 1개를 골라도 조건(영양분 최소)을 만족할 수도 있다.
 * 그렇기 때문에 모든 경우의 수를 부분집합으로 뽑아서 계산하도록 한다.
 *
 */
public class BOJ19942_다이어트 {


    //식재료 정보 객체
    private static class Ingredient{
        int protein, fat, carbohydrate, vitamin, cost;
        public Ingredient(int protein, int fat, int carbohydrate, int vitamin, int cost){
            this.protein = protein;
            this.fat = fat;
            this.carbohydrate = carbohydrate;
            this.vitamin = vitamin;
            this.cost = cost;
        }
        public Ingredient(int protein, int fat, int carbohydrate, int vitamin){
            this.protein = protein;
            this.fat = fat;
            this.carbohydrate = carbohydrate;
            this.vitamin = vitamin;
            this.cost = 0;
        }

        //영양소 비교.
        public boolean check(){
            return this.protein >= minIngredient.protein &&
                    this.fat >= minIngredient.fat &&
                    this.carbohydrate >= minIngredient.carbohydrate &&
                    this.vitamin >= minIngredient.vitamin;
        }

        //영양소 더하기
        public void addNutrient(Ingredient ingredient){
            this.protein += ingredient.protein;
            this.fat += ingredient.fat;
            this.carbohydrate += ingredient.carbohydrate;
            this.vitamin += ingredient.vitamin;
            this.cost += ingredient.cost;
        }

        //객체 복사 - 객체 공유를 막기 위해.
        public Ingredient newIngredient(){
            return new Ingredient(
                    this.protein,
                    this.fat,
                    this.carbohydrate,
                    this.vitamin,
                    this.cost);
        }
    }

    //식재료 수.
    private static int N;

    //최소 비용
    private static int minCost;

    //최소 비용일떄의 식재료 리스트.
    private static List<Integer> minList;

    //최소 영양분
    private static Ingredient minIngredient;

    //식재료 정보
    private static Ingredient[] ingredientInfo;

    //리스트 복사 메서드
    private static List<Integer> copyList(List<Integer> list){
        return list.stream().sorted().collect(Collectors.toList());
    }

    //부분집합을 구하면서 식재료 계산. - 음수 비용이 없기 때문에 최소비용이 되려면 조건을 만족하는 최소의 식재료를 선택하면 된다.
    //즉, 조건을 만족하는 집합이 나오면, 해당 집합에 원소를 더 추가할 필요가 없다는 뜻이다.
    private static void recursive(int index, Ingredient currentInfo, List<Integer> ingredientNum){

        //이전에 저장된 값보다 구하는 비용이 커지면 가지치기
        if(minCost <= currentInfo.cost) return;

        //최소 치를 만족했다면 최대 값 업데이트.
        if(currentInfo.check()){
            if(minCost > currentInfo.cost){
                minCost = currentInfo.cost;
                minList = copyList(ingredientNum);
            }
            return;
        }

        //인덱스가 최대를 넘어서면.
        if(index >= N) return;

        //재료객체 복사.
        Ingredient nextInfo = currentInfo.newIngredient();
        nextInfo.addNutrient(ingredientInfo[index]);

        //리스트에 선택한 값 추가.
        ingredientNum.add(index + 1);

        //현재 재료를 선택하는 경우.
        recursive(index + 1, nextInfo, ingredientNum);

        ingredientNum.remove(ingredientNum.size() - 1);

        //현재 재료를 선택하지 않는 경우.
        recursive(index + 1, currentInfo.newIngredient(), ingredientNum);

    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        minIngredient = new Ingredient(
                Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken())
        );

        ingredientInfo = new Ingredient[N];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            ingredientInfo[i] = new Ingredient(
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken())
            );
        }

        minCost = Integer.MAX_VALUE;
        minList = new ArrayList<>();
        recursive(0, new Ingredient(0,0,0,0), new ArrayList<>());

        if(minList.size() == 0) System.out.println(-1);
        else {
            StringBuilder result = new StringBuilder();
            result.append(minCost).append("\n");

            for(int num : minList){
                result.append(num).append(" ");
            }
            System.out.println(result);
        }


    }
}
