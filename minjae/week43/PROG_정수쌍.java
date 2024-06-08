package week43;

public class PROG_정수쌍 {
    public long solution(int r1, int r2) {
        long answer = 0;

        double r1pow = Math.pow(r1, 2);
        double r2pow = Math.pow(r2, 2);

        int onLine = (r2 - r1 + 1)*4;

        for(int i=0; i<= r2; i++){ //1사분면만 계산
            double xpow = Math.pow(i, 2);

            if(i > r1) r1pow = 0;

            double y1 =0;

            if(r1pow != 0){
                y1 = Math.sqrt(r1pow - xpow); //작은원
                if(y1 > Math.floor(y1)){
                    y1 = Math.ceil(y1);
                }
            }

            double y2 = Math.sqrt(r2pow - xpow); //큰원
            if(y2 > Math.floor(y2)){
                y2 = Math.floor(y2);
            }
            answer += (int)y2 - (int)y1 + 1;

        }

        return answer*4 - onLine; //중복 계산된 점 빼주기
    }
}