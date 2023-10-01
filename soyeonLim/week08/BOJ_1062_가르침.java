package week08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1062_가르침 {
    /*
        예제 :
        9 8
        antabtica  >> data[0] = b
        antaxtica  >> data[1] = x
        antadtica  >> data[2] = d
        antaetica  >> data[3] = e
        antaftica  >> data[4] = f
        antagtica  >> data[5] = g
        antahtica  >> data[6] = h
        antajtica  >> data[7] = j
        antaktica  >> data[8] = k
     */
    public static int N,K;
    public static String[] data;
    public static int answer;

    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        int mask =0;
        // a 1, c 3, i 9, n 14, t 20
        mask |= 1 << (int)'a'-96;//(2^1)
        mask |= 1 << (int)'c'-96;//(2^1+2^3)
        mask |= 1 << (int)'i'-96;//(2^1+2^3+2^9)
        mask |= 1 << (int)'n'-96;//(2^1+2^3+2^9+2^14)
        mask |= 1 << (int)'t'-96;//(2^1+2^3+2^9+2^14+2^20)

        data = new String[N];
        for(int i=0;i<N;i++)
        {
            String s = br.readLine();
            s = s.replaceAll("[a,c,i,n,t]","");
            data[i]=s;
        }

        dfs(1,5,mask);
        System.out.println(answer);
    }

    public static void dfs(int start, int depth,int mask)
    {
        if(K<5)
        {
            answer =0;
            return;
        }
        if(K>=26)
        {
            answer=N;
            return;
        }
        if(depth==K){
            int num =0;
            for(int i=0;i<N;i++)
            {
                boolean check =true;
                //data와 비교해서 있는지 확인
                for(int j=0;j<data[i].length();j++)
                {
                    if((mask &(1<<data[i].charAt(j)-96))==0)
                    {
                        check=false;
                        break;
                    }
                }
                if(check) num++;
            }
            answer = Math.max(answer, num);
            return;
        }
        /*
        a,c,i,n,t가 있으므로 mask : 100000100001000001010
        i==2 : 100000100001000001110 > a,b,c,i,n,t
        i==4 : 100000100001000011110 > a,b,c,d,i,n,t
        i==5 : 100000100001000111110 > a,b,c,d,e,i,n,t >> 8개 이므로 갯수 구하기 b,d,e 있으므로 answer =3

        i==6 : 100000100001001011110 > a,b,c,d,f,i,n,t >> b,d,f 있으므로 answer =3
         */
        for(int i=start;i<27;i++)
        {
            //mask 안에 안 들어있을 경우
            if((mask & (1<<i))==0)
            {
                dfs(i+1,depth+1,mask|(1<<i));
            }
        }
    }
}
