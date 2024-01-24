package week24;

/**
 * 구현
 * 소수 구하는 것만 완탐이 아닌 에라토스 테네스의 체 또는, 해당 수의 제곱근까지 나눠보는 식으로 구하면 된다.
 * 주어진 수를 해당 진법에 맞게 변환하고, 0을 기준으로 파싱하면 된다.
 */

public class Prog_k진수에서_소수개수_구하기 {

    //진법 변환
    private static String convert(int n , int k){

        StringBuilder result = new StringBuilder();

        while(n > 0){
            result.append(n % k);
            n /= k;
        }

        return result.reverse().toString();
    }

    //소수판별 메서드
    private static boolean check(String numStr){
        long num = Long.parseLong(numStr);

        if(num == 1) return false;

        //해당수의 제곱근까지만 가면 된다.
        for(int i = 2; i <= (int)Math.sqrt(num); i++){

            //제곱근까지 나눈 나머지가 0인것이 하나라도 있으면 해당 수는 소수가 아님.
            if(num % i == 0) return false;
        }
        return true;
    }

    public int solution(int n, int k) {
        int answer = 0;

        String convertNum = convert(n, k); //k진수로 변환

        //0을 기준으로 파싱.
        int startIndex = 0;
        int endIndex = 1;

        while(endIndex < convertNum.length()){

            //0이 아니면 endIndex를 증가시킴
            if(convertNum.charAt(endIndex) != '0'){
                endIndex++;
                continue;
            }

            //0이면 그 이전구간을 수로 변경후에 소수 체크
            String subStr = convertNum.substring(startIndex, endIndex);
            if(check(subStr)) answer++;

            //start를 다음 0이 아닌 구간으로 이동시켜야 됨.
            while(endIndex < convertNum.length()){

                if(convertNum.charAt(endIndex) == '0'){
                    endIndex++;
                    continue;
                }

                //0이 아닌 값을 찾았으면 해당위치에 start를 가져다 두고 반복문 종료
                startIndex = endIndex;
                endIndex++;
                break;
            }
        }

        //마지막 구간 처리
        if(convertNum.charAt(startIndex) != '0'){
            if(check(convertNum.substring(startIndex, endIndex))) answer++;
        }


        return answer;
    }


    public static void main(String[] args) {
        Prog_k진수에서_소수개수_구하기 s = new Prog_k진수에서_소수개수_구하기();

        int n1 = 437674;
        int k1 = 3;
        System.out.println(s.solution(n1, k1));

        int n2 = 110011;
        int k2 = 10;
        System.out.println(s.solution(n2, k2));

    }
}
