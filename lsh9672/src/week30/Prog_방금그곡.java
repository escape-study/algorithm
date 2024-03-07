package week30;

/**
 * 아이디어
 * 슬라이딩 윈도우
 * 해당 분 만큼 멜로디를 반복해서 만들고, 단순하게 문자열을 비교한다
 * 이때 주의할 점은 #붙은 문자의 경우 길이가 2 이므로, 1글자로 바꿔준다.
 */
public class Prog_방금그곡 {

	//#붙은 문자 변경.
	private static String convert(String melody){

		StringBuilder sb = new StringBuilder();

		int index = melody.length() - 1;
		while(index >= 0){
			char c = melody.charAt(index);

			if(c == '#'){
				index--;
				sb.insert(0, (char) (melody.charAt(index) + 32));
			}
			else{
				sb.insert(0, c);
			}
			index--;
		}

		return sb.toString();
	}

	//분으로 변환.
	private static int convertMin(String time){

		String[] temp = time.split(":");

		return Integer.parseInt(temp[0]) * 60  + Integer.parseInt(temp[1]);
	}
	public String solution(String m, String[] musicinfos) {
		String answer = "(None)";
		int maxMinute = -1;

		m = convert(m);

		for(String musicinfo : musicinfos){
			//0 : 시작, 1: 끝, 2 : 이름, 3 : 멜로디
			String[] info = musicinfo.split(",");
			int minute = convertMin(info[1]) - convertMin(info[0]);
			String melody = convert(info[3]);

			//주어진 멜로디보다, 기억한 멜로디가 더 길면 볼 필요 없으미
			if(m.length() > minute) continue;


			for(int startIndex = 0; startIndex <= minute - m.length(); startIndex++){
				boolean flag = true;
				for(int i = 0; i < m.length(); i++){

					if(melody.charAt((startIndex + i) % (melody.length())) != m.charAt(i)){
						flag = false;
						break;
					}
				}

				if(flag){

					if(maxMinute < minute){
						answer = info[2];
						maxMinute = minute;
					}
					break;
				}
			}
		}


		return answer;
	}

	public static void main(String[] args) {
		Prog_방금그곡 s = new Prog_방금그곡();

		String m1 = "ABCDEFG";
		String[] musicinfos1 = {"12:00,12:14,HELLO,CDEFGAB", "13:00,13:05,WORLD,ABCDEF"};
		System.out.println(s.solution(m1, musicinfos1));

		String m2 = "CC#BCC#BCC#BCC#B";
		String[] musicinfos2 = {"03:00,03:30,FOO,CC#B", "04:00,04:08,BAR,CC#BCC#BCC#B"};
		System.out.println(s.solution(m2, musicinfos2));

		String m3 = "ABC";
		String[] musicinfos3 = {"12:00,12:14,HELLO,C#DEFGAB", "13:00,13:05,WORLD,ABCDEF"};
		System.out.println(s.solution(m3, musicinfos3));

	}
}
