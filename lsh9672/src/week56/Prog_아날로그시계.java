package week56;

/**
 * 아이디어
 * 구현
 * 시/분의 이동을 초단위로 쪼갠다.
 * 분 : 1 / 3600, 시 : 1 / 43200  => 각 침이 1초에 움직이는 칸수로 변환한다.
 * 초기의 시간을 위의 변환식으로 나타내주고 시작한다.
 * 시간을 전부 초로 변환해서 계산했다면, 시침 분침이 한바퀴 돌때 초침과 몇번 마주치는 지 계산한다.
 * 분침의 경우 59번 마주친다. 분이 12에서 시작해서 다시 12까지 오는데에 분침도 움직이기 때문에 60번이 아닌 59번이 된다.
 * 시침도 마찬가지로 같이 움직이기 때문에 한바퀴를 돌아서 12위치에 오기 전까지 마주치기 때문에,720번이 아니라 719번이 된다.
 *
 * 따라서 시간을 초로 변환한 값 * 720/43200 하면 해당 시간동안 초침과 만나는 수가 나온다.
 *
 * (참고함)
 * https://velog.io/@dsa04156/PCCP-%EA%B8%B0%EC%B6%9C%EB%AC%B8%EC%A0%9C-3%EB%B2%88-%EC%95%84%EB%82%A0%EB%A1%9C%EA%B7%B8-%EC%8B%9C%EA%B3%84-%EC%9E%90%EB%B0%94
 * https://bellog.tistory.com/241
 * https://velog.io/@sour_grape/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%A8%B8%EC%8A%A4-%EC%95%84%EB%82%A0%EB%A1%9C%EA%B7%B8-%EC%8B%9C%EA%B3%84-java
 */
public class Prog_아날로그시계 {

	//초로 변환
	private static int timeToSec(int hour, int minute, int sec){
		return hour * 3600 + minute * 60 + sec;
	}



	public int solution(int h1, int m1, int s1, int h2, int m2, int s2) {

		int startSec = timeToSec(h1, m1, s1);
		int endSec = timeToSec(h2, m2, s2);

		//겹치는 횟수 구하기 => 종료시간까지 겹치는 횟수 - 시작시간까지 겹치는 횟수
		int count = (endSec * 59)/3600 + (endSec * 719) / 43200  - (startSec * 59)/3600  - (startSec * 719) /43200;

		//예외 케이스 처리

		//1) 현재시간에 알림이 울리는지 확인이 필요하다.
		if((startSec * 59) % 3600 == 0 || (startSec * 719) % 43200 == 0) count++;


		//2) 위에서 계산할때,  시침 분침 두번 더했기 때문에 빼줘야 함.
		//43200이상이면 총 두번 나올 수 있음.
		if(startSec >= 43200) count += 1;//start는 두번 뺴줬기 때문에 한번 더해줌.
		if(endSec >= 43200) count -= 1; //endstart라면 두번 더해줬기 때문에 한번 뺴줌




		//시작시간이 00:00:00 인 경우는 1번 예외 케이스에서 구함.



		return count;
	}

	public static void main(String[] args) {
		Prog_아날로그시계 s = new Prog_아날로그시계();

		int h1_1 = 0;
		int m1_1 = 5;
		int s1_1 = 30;
		int h2_1 = 0;
		int m2_1 = 7;
		int s2_1 = 0;
		System.out.println(s.solution(h1_1, m1_1, s1_1, h2_1, m2_1, s2_1));

		int h1_2 = 12;
		int m1_2 = 0;
		int s1_2 = 0;
		int h2_2 = 12;
		int m2_2 = 0;
		int s2_2 = 30;
		System.out.println(s.solution(h1_2, m1_2, s1_2, h2_2, m2_2, s2_2));

		int h1_3 = 0;
		int m1_3 = 6;
		int s1_3 = 1;
		int h2_3 = 0;
		int m2_3 = 6;
		int s2_3 = 6;
		System.out.println(s.solution(h1_3, m1_3, s1_3, h2_3, m2_3, s2_3));

		int h1_4 = 11;
		int m1_4 = 59;
		int s1_4 = 30;
		int h2_4 = 12;
		int m2_4 = 0;
		int s2_4 = 0;
		System.out.println(s.solution(h1_4, m1_4, s1_4, h2_4, m2_4, s2_4));

		int h1_5 = 11;
		int m1_5 = 58;
		int s1_5 = 59;
		int h2_5 = 11;
		int m2_5 = 59;
		int s2_5 = 0;
		System.out.println(s.solution(h1_5, m1_5, s1_5, h2_5, m2_5, s2_5));

		int h1_6 = 1;
		int m1_6 = 5;
		int s1_6 = 5;
		int h2_6 = 1;
		int m2_6 = 5;
		int s2_6 = 6;
		System.out.println(s.solution(h1_6, m1_6, s1_6, h2_6, m2_6, s2_6));

		int h1_7 = 0;
		int m1_7 = 0;
		int s1_7 = 0;
		int h2_7 = 23;
		int m2_7 = 59;
		int s2_7 = 59;
		System.out.println(s.solution(h1_7, m1_7, s1_7, h2_7, m2_7, s2_7));


	}
}
