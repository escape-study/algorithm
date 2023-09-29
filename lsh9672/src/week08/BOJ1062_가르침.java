package week08;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 완탐을 이용한 문제
 *
 * 앞 뒤 4글자는 고정이라고 한다.
 * 선택할 수 있는 글자는 앞뒤 4글자를 제외한 글자가 된다.
 * 제외한 글자를 모으는 리스트를 만든다.
 * 해당 리스트에서 글자를 뽑고, 뽑을 수 있는 최대 개수만큼 뽑으면 각 문자열들과 비교해서 만들수 있는지 확인한다.
 *
 * (추가)
 * 이와 같은 방식으로, 모든 알파벳중에 K - 5개를 뽑는 경우가 아닌,앞뒤 4글자를 자르고 남은 알바펫중에서 뽑게 하면, K - 5개 미만으로 뽑았을때,
 * 가능한 문자의 경우에는 탐색을 안한다.
 * 그래서 재귀 조건에 K-5와 같거나 작은 경우, 체킹을 하도록 했고, k -5 를 넘어가게 고르면 종료하도록 했다.
 * 이전에 짰을때는 오든 알파벳중에서 5개를 뽑는 경우였는데, 이보다 5배정도 느렸다.
 * K-5개를 골랐을때가 아니라, K -5보다 작을때마다 체크를 해줘야하기 때문이다.
 */
public class BOJ1062_가르침 {
    //단어 개수
    private static int N;

    //가르칠 단어 개수
    private static int K;

    //단어 저장 리스트.
    private static String[] wordArray;

    //알파벳 방문 처리
    private static boolean[] visited;

    //공통 알파벳을 제외한, 뽑을 알파벳 리스트
    private static boolean[] alphaArray;

    //최대 개수 저장
    private static int maxValue;

    //초기 설정 잡는 메서드


    //반복문 돌면서 몇개의 문자열까지 매칭 되는지 확인 후 개수 리턴하는 메서드
    private static int matching(){

        int returnCount = 0;

        for(int i = 0; i < N; i++){
            String word = wordArray[i];

            boolean flag = true;

            for(int j = 0; j < word.length(); j++){

                if(!visited[word.charAt(j) - 'a']){
                    flag = false;
                    break;
                }
            }

            if(flag) returnCount++;
        }

        return returnCount;
    }

    //재귀 돌면서 문자 뽑는 메서드.
    private static void recursive(int index, int currentCount){

        if(currentCount > K - 5) return;

        //뽑아야 되는 최대 문자수 - (K - 5) 개만큼 뽑았으면 문자 매칭
        if(currentCount <= K - 5){
            maxValue = Math.max(maxValue, matching());
        }


        for(int i = index; i < 26; i++){

            //false면 없는 알파벳
            if(!alphaArray[i]) continue;

            if(!visited[i]){
                visited[i] = true;
                recursive(i + 1, currentCount + 1);
                visited[i] = false;
            }
        }

    }

    //초기 문자 넣어두기
    private static void init(){

        String initStr = "antic"; //이 문자들은 고정으로 사용되는 문자.

        for(int i = 0; i < initStr.length(); i++){
            char tempChr = initStr.charAt(i);

            visited[tempChr - 'a'] = true;
        }
    }

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        wordArray = new String[N];
        visited = new boolean[26];
        alphaArray = new boolean[26];

        //가르쳐야 하는 문자 수(K)가 5개 미만이면 그냥 종료
        if(K < 5){
            System.out.println(0);
            return;
        }

        init();

        for(int i = 0; i < N; i++){

            //앞 뒤 4자리를 뺴고 나머지 문자 저장.
            String temp = br.readLine();

            temp = temp.substring(4, temp.length() - 4);

            //배열에 넣고,
            wordArray[i] = temp;

            //해당 문자를 반복문 돌면서 문자 선택을 위한 리스트에 저장 - 조합으로 뽑을 문자 저장.
            for(int j = 0; j < temp.length(); j++){
                char tempChr = temp.charAt(j);

                alphaArray[tempChr - 'a'] = true;
            }
        }

        maxValue = 0;

        recursive(0,0);

        System.out.println(maxValue);

    }
}
