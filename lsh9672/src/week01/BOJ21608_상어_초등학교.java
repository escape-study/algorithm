package week01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class BOJ21608_상어_초등학교 {

    //만족도값을 매핑해두기.
    private static final int[] satisfactionMapping = {0,1,10,100,1000};
    //인접한 값 계산을 위한 4방향
    private static final int[] dx = {-1,1,0,0};
    private static final int[] dy = {0,0,-1,1};

    //자리 크기를 나타낼 변수
    private static int N;
    //자리를 표시할 2차원 배열.
    private static int[][] maps;
    //각 학생이 좋아하는 학생의 번호 표시.
    private static Set<Integer>[] likeStudent;

    //해당 좌표가 배열을 넘어가지 않는지 확인
    private static boolean locationCheck(int x, int y){

        return x >= 0 && x < N && y >= 0 && y < N;
    }

    //주어진 위치의 인접한 좋아하는 학생수
    private static int likeStudentCount(int currentX, int currentY, int currentStudentNum){

        //해당 학생이 좋아하는 사람 목록
        Set<Integer> likeStudentSet = likeStudent[currentStudentNum];

        //칸수
        int count = 0;

        for(int i = 0; i < 4; i++){

            int nextX = currentX + dx[i];
            int nextY = currentY + dy[i];

            //배열을 벗어나이 않았고, 0이 아니고, 좋아하는 학생 목록에 존재한다면
            if(locationCheck(nextX,nextY) && maps[nextX][nextY] != 0 && likeStudentSet.contains(maps[nextX][nextY])){
                count++;
            }
        }

        return count;
    }

    //비어있는 칸 세기
    private static int emptyCount(int currentX, int currentY){
        int count = 0;

        for(int i = 0; i < 4; i++){

            int nextX = currentX + dx[i];
            int nextY = currentY + dy[i];

            if(locationCheck(nextX,nextY) && maps[nextX][nextY] == 0){
                count++;
            }
        }

        return count;
    }

    //학생의 번호를 입력받아서 자리정하기.
    private static void seatSelection(int studentNum){

        int x = 0;
        int y = 0;
        int maxLikeCount = -1;//인접한 좋아하는 학생수
        int maxEmptyCount = -1;//인접한 비어있는 칸수

        //현재 위치기준, 인접한 좋아하는 학생수와 비어있는 칸수
        int currentLikeCount = 0;
        int currentEmptyCount = 0;


        //반복문 돌면서 처리.
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){

                //비어있는 칸이 아니면 패스
                if(maps[i][j] != 0) continue;

                //인접한 좋아하는 학생수와 비어있는 칸수 구하기
                currentLikeCount = likeStudentCount(i,j,studentNum);
                currentEmptyCount = emptyCount(i,j);

                //기존에 저장된 값과 비교
                if((maxLikeCount < currentLikeCount) || ((maxLikeCount == currentLikeCount) && (maxEmptyCount < currentEmptyCount))){
                    x = i;
                    y = j;
                    maxLikeCount = currentLikeCount;
                    maxEmptyCount = currentEmptyCount;
                }
                //빈칸도 같을때는 볼필요 없음, 어차피 행열값이 작은 값에서 큰 값으로 증가하는 식으로 구하고 있기 때문
            }
        }

        //최종적으로 구한 위치에 학생 배치.
        maps[x][y] = studentNum;

    }

    //학생들의 만족도 구하기.
    private static int satisfactionCount(){

        int totalCount = 0;

        Set<Integer> likeStudentSet = null;
        int likeCount = 0; //좋아하는 학생수 카운트

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){

                likeCount = 0;
                likeStudentSet = likeStudent[maps[i][j]];//좋아하는 학생 목록

                for(int dir = 0; dir < 4; dir++){

                    int nextX = i + dx[dir];
                    int nextY = j + dy[dir];

                    //배열을 벗어나지 않았고, 좋아하는 학생 목록에 포함된다면.
                    if(locationCheck(nextX,nextY) && likeStudentSet.contains(maps[nextX][nextY])){
                        likeCount++;
                    }
                }

                //만족도 누적
                totalCount += satisfactionMapping[likeCount];
            }
        }


        return totalCount;
    }
    //변수 등, 기본값 초기화
    private static void init(){

        maps = new int[N][N];

        likeStudent = new Set[N * N + 1];
        for(int i = 1; i <= N * N; i++){
            likeStudent[i] = new HashSet<>();
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        N = Integer.parseInt(br.readLine());

        init(); //멤버변수 초기화

        //좋아하는 학생 목록 만들기.
        for(int i = 0; i < N * N; i++){
            st = new StringTokenizer(br.readLine());

            int studentNum = Integer.parseInt(st.nextToken());

            Set<Integer> tempSet = likeStudent[studentNum];
            tempSet.add(Integer.parseInt(st.nextToken()));
            tempSet.add(Integer.parseInt(st.nextToken()));
            tempSet.add(Integer.parseInt(st.nextToken()));
            tempSet.add(Integer.parseInt(st.nextToken()));

            seatSelection(studentNum);
        }

        //만족도 총 합계 출력
        System.out.println(satisfactionCount());
    }
}
