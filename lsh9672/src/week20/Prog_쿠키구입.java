package week20;

/**
 * 아이디어
 * 투포인터
 * 투포인터인데, 윈도우가 두개인 특수한 경우이다.
 * m을 기준으로 잡고 첫째아들은 왼쪽범위로 확장, 둘째아들은 오른쪽으로 확장하는 식으로 체크하면 된다.
 * 이렇게 하면 서로 인덱스가 겹치는 일 없이 모든 경우를 다 확인 할 수 있게 된다.
 * 확장할떄마다 수가 커지기 때문에 왼쪽합계와 오른쪽 합계를 비교해서 더 작은 쪽의 인덱스를 확장시켜나가는 식으로 구하면 된다.
 * 두 윈도우의 크기가 동일해지면 그때의 과자수를 저장하고, 다음 m을 찾아서 동일한 방법으로 탐색하면 된다.
 *
 * 시간복잡도는 기준점 잡는 연산이 N-1, 각 기준점마다 투포인터 연산을 하면 최대 N번반복 발생
 * 약 O(N^2)의 시간복잡도를 가지고, N의 최대값은 2000으로 4*10^6이다.
 */
public class Prog_쿠키구입 {

    public int solution(int[] cookie) {

        int answer = 0;

        //기준점을 반복문으로 돌면서 각 기준점마다 투포인터를 돌려 처리.
        for(int m = 0; m < cookie.length - 1; m++){
            int l = m;
            int leftSum = cookie[l];
            int r = m + 1;
            int rightSum = cookie[r];


            //반복문 돌면서 처리.
            while(true) {

//                if(m == 2) System.out.println("leftSum : " + leftSum + ", rightSum : " + rightSum);

                //두 과자 크기가 동일하면 저장.
                if (leftSum == rightSum) {

                    answer = Math.max(answer, leftSum);
//                    System.out.println("l :" + l + ", r : " + r);
//                    System.out.println(leftSum);
                    r++;
                    l--;
                    if(l < 0 || r >= cookie.length) break;
                    rightSum += cookie[r];
                    leftSum += cookie[l];
                }

                //왼쪽 과자 크기가 더 크면 오른쪽 인덱스 증가
                else if (leftSum > rightSum) {
                    r++;
                    if(r >= cookie.length) break;
                    rightSum += cookie[r];
                }

                //오른쪽 과자 크기가 더 크면 왼쪽인덱스 감소,
                else {
                    l--;
                    if(l < 0) break;
                    leftSum += cookie[l];
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int[] cookie1 = {1,1,2,3};
        int result1 = 3;
        Prog_쿠키구입 s1 = new Prog_쿠키구입();
        System.out.println(s1.solution(cookie1));
//
        int[] cookie2 = {1,2,4,5};
        int result3 = 3;
        Prog_쿠키구입 s2 = new Prog_쿠키구입();
        System.out.println(s2.solution(cookie2));

    }
}
