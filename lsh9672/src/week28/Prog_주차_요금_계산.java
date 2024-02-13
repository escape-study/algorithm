package week28;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 아이디어
 * 구현
 * 각 차량 별 시간을 저장할 Map을 구한다.
 * 1. key는 차량 번호, value는 차량의 총 사용 분을 저장한다.
 * 2. 두번째 Map은 차량의 진입시간을 저장한다.
 * 만약, 진출 시간이 나오면, 해당 차량은 사용시간을 계산해서 첫번째 Map에 저장하고 두번째 맵에서 제거해서, 다음 진입을 저장하도록 한다.
 * 모든 시간을 다 돌고나서도 남아있다면, 23:59분에 출차 된것으로 계산해서 더해준다.
 * 1번의 key값을 오름차순으로 정렬하고, 요금을 출력해준다.
 * 요금의 경우는 integer로 하면된다, 크게 잡아도 최대 이용시간은 2000을 넘지 않고, 기본요금 시간1분, 추가요금이 1분당 최대 금액인 10만원이라해도, 2억을 넘지 않는다.
 * 따라서 integer로 처리가 된다.
 * 또한 차량 정보를 정렬하는데에 nlogn, 차량정보는 1000개 주어지기 떄문에 시간초과는 나지 않는다/
 *
 */
public class Prog_주차_요금_계산 {

	//요금계산
	private static int calculateFee(int[] fees, int minute){
		if(minute <= fees[0]) return fees[1];

		int overTime = (minute - fees[0]) / fees[2]; //초과 시간을 단위시간으로 나누기.
		if((minute - fees[0]) % fees[2] != 0) overTime++;

		return fees[1] + (overTime * fees[3]);
	}

	//시간을 분으로 변경하는 메서드
	private static int timeToMin(String time){
		String[] splitTime = time.split(":");

		return Integer.parseInt(splitTime[0]) * 60 + Integer.parseInt(splitTime[1]);
	}
	public int[] solution(int[] fees, String[] records) {


		//차량 최종 분 저장 메서드
		Map<String, Integer> useTimeMap = new HashMap<>();

		//차량의 마지막 진입시간 저장하는 메서드
		Map<String, Integer> lastInTime = new HashMap<>();

		for(String record : records){
			String[] splitRecord = record.split(" ");
			int minute = timeToMin(splitRecord[0]);
			String carNum = splitRecord[1];

			//진입인지 진출인지 확인.
			//진입일떄는,배열에 그냥 추가
			if(splitRecord[2].equals("IN")){
				lastInTime.put(carNum, minute);
			}
			//진출일떄는 확인 필요.
			else{
				int totalMinute = minute - lastInTime.get(carNum);
				lastInTime.remove(carNum);

				//이전에 저장된 값이 있으면 누적
				if(useTimeMap.containsKey(carNum)){
					useTimeMap.put(carNum, useTimeMap.get(carNum) + totalMinute);
				}
				else{
					useTimeMap.put(carNum, totalMinute);
				}
			}
		}

		//출차 정보가 안들어와서 남아있는 정보 업데이트.
		int lastMinute = timeToMin("23:59");
		for(String carNum : lastInTime.keySet()){

			int minuteGap = lastMinute - lastInTime.get(carNum);
			if(useTimeMap.containsKey(carNum)){
				useTimeMap.put(carNum, useTimeMap.get(carNum) + minuteGap);
			}
			else{
				useTimeMap.put(carNum, minuteGap);
			}
		}

		//차량 번호 오름차순 정렬
		List<String> keyList = new ArrayList<>(useTimeMap.keySet());
		Collections.sort(keyList, Comparator.comparingInt(Integer::parseInt));

		int[] answer = new int[keyList.size()];
		for(int i = 0; i < keyList.size(); i++){
			answer[i] = calculateFee(fees, useTimeMap.get(keyList.get(i)));
		}

		return answer;
	}

	public static void main(String[] args) {

		Prog_주차_요금_계산 s = new Prog_주차_요금_계산();

		int[] frees1 = {180, 5000, 10, 600};
		String[] records1 = {"05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN", "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT"};
		System.out.println(Arrays.toString(s.solution(frees1, records1)));


		int[] frees2 = {120, 0, 60, 591};
		String[] records2 = {"16:00 3961 IN","16:00 0202 IN","18:00 3961 OUT","18:00 0202 OUT","23:58 3961 IN"};
		System.out.println(Arrays.toString(s.solution(frees2, records2)));


		int[] frees3 = {1, 461, 1, 10};
		String[] records3 = {"00:00 1234 IN"};
		System.out.println(Arrays.toString(s.solution(frees3, records3)));
	}
}
