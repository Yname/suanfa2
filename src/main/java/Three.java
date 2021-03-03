import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Three {
    /*
        长度：5(<=4) 10(<8) 25(>=8)
        数字：0（0） 1（10） >=1 (20)
        字母： 只有小写或只有大写 10，没有大小写 0 ，混合 20
        符号： 只有0（0） ， 1（10） >=1 (20)
        奖励：数字和字母 +2, 字母+数字+符号 + 3，数字+大小字母+符号 +5
     */
    public static void main(String[] args) {
        String pwd = "abcd@@n/A123bc";
        int score1 = countPwdSize(pwd);
        int[] intCount = countPwdLetter(pwd);
        int i = countPwdScore(intCount);
        String count = countResult(i + score1);
        System.out.println(count);
    }

    public static String countResult(int score){
        if (score >= 90){
            return "VERY_SECURE";
        }else if (score >= 80){
            return "SECURE";
        }else if (score >= 70){
            return "VERY_STRONG";
        }else if (score >= 60){
            return "STRONG";
        }else if (score >= 50){
            return "AVERAGE";
        }else if (score >= 25){
            return "WEAK";
        }else {
            return "VERY_WEAK";
        }
    }
    public static int countPwdScore(int[] count){
        int score = 0;
        int mark = 0;
        if ( (count[1] >= 1 && count[2] == 0) || (count[2] >= 1 && count[1] == 0)){
            score += 10;
            mark += 1;
        }else if (count[1] >= 1 && count[2] >= 1){
            score += 20;
            mark += 2;
        }
        int[] ints = countPubSome(count[0], score, mark);
        int[] ints2 = countPubSome(count[3], ints[0], ints[1]);
        if (ints2[1] == 2){
            ints2[0] += 2;
        }else if (ints2[1] == 3){
            ints2[0] += 3;
        }else if (ints2[1] == 4){
            ints2[0] += 5;
        }
        return ints2[0];
    }
    public static int[] countPubSome(int count,int score,int mark){
        if (count == 1){
            score += 10;
            mark += 1;
        }else if (count > 1){
            score += 20;
            mark += 1;
        }
        return new int[]{score,mark};
    }
    public static int[] countPwdLetter(String pwd){
        //数字，小写字母，大写字母，符号
        int[] count = {0,0,0,0};

        byte[] bytes = pwd.getBytes();
        for (byte aByte : bytes) {
            if (48 <= aByte && aByte <= 57) {
                count[0] = count[0] + 1;
            } else if (65 <= aByte && aByte <= 90) {
                count[2] = count[2] + 1;
            } else if (97 <= aByte && aByte <= 122) {
                count[1] = count[1] + 1;
            } else {
                count[3] = count[3] + 1;
            }
        }
        System.out.println(Arrays.toString(count));
        return count;
    }
    public static int countPwdSize(String pwd){
        int length = pwd.length();
        if (length <= 4){
            return 5;
        }else if (length < 8){
            return 10;
        }else{
            return 25;
        }
    }
}
