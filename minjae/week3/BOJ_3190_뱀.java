import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_3190_뱀{

    static int N;
    static boolean Map[][][];
    static int change[][];
    static int delta[][] = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

        Map = new boolean[N + 1][N + 1][2];

        int k = Integer.parseInt(br.readLine());
        for (int i = 1; i <= k; i++) {
            st = new StringTokenizer(br.readLine());
            Map[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())][1] = true;
        }


        int l = Integer.parseInt(br.readLine());
        change = new int[l][2];
        int num = 0;

        for (int i = 0; i < l; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            String b = st.nextToken();
            if (b.equals("D")) {
                num = (num + 1) % 4;
            } else if(b.equals("L")) {
                num = num - 1 < 0 ? 3 : num - 1;
            }
            change[i][0] = a;
            change[i][1] = num;


        }


        int cnt = 0;
        int cnt_t = 0;
        int del = 0;
        int del_t = 0;
        int tailX = 1;
        int tailY = 1;
        int nextX = 1;
        int nextY = 1;

        int check = 0;
        int check_t = 0;

        while (true) {
            Map[nextX][nextY][0] = true;

            if(check_t < l && cnt_t == change[check_t][0]  ) {
                del_t = change[check_t][1];
                check_t++;
            }
            if(check < l && cnt == change[check][0] ) {
                del = change[check][1];
                check++;
            }

            nextX = nextX + delta[del][0];
            nextY = nextY + delta[del][1];

            if (nextX < 1 || nextY < 1 || nextX > N || nextY > N || Map[nextX][nextY][0])
                break;
            //System.out.println(nextX + " " + nextY + " : " + change[cnt]);

            if (Map[nextX][nextY][1]) {
                Map[nextX][nextY][1] = false;
            } else {
                Map[tailX][tailY][0] = false;
                tailX = tailX + delta[del_t][0];
                tailY = tailY + delta[del_t][1];
                cnt_t++;
            }
            cnt++;
        }

        System.out.println(cnt+1);
    }

}