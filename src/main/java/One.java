import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class One {
//    public static void main(String[] args) {
//        int[] time = {1,5,8,11,16,17,17,34,39};
//        int[] line = {3,7,12,17,19,31,40};
////        用于存储 time和line 最小的差值（绝对值）
//        int[] line2 = new int[line.length];
////        用于存储 产生最小差值的 time下标值
//        int[] line3 = new int[line.length];
//
//        for (int i = 0; i < time.length; i++) {
//            for (int j = 0; j < line.length; j++) {
//                int temp = time[i] - line[j];
//                int mark = Math.abs(temp);
//                if (i == 0){
//                    line2[j] = mark;
//                }
//                if (mark <= line2[j]){
//                    line2[j] = mark;
//                    line3[j] = i;
//                }
//            }
//        }
//        for (int i = 0; i < line3.length; i++) {
//            System.out.println("{line:"+line[i]+","+"time:"+time[line3[i]]+"}");
//        }
//    }


    public static void main(String[] args) throws ParseException {
        //日期为 2020-03-21 15:23:31  输出结果为2020-03-22 8:23:31
//        String dateStr = "2020-03-20 18:23:31";
        //日期为2020-03-31 10:23:31   输出结果为2020-03-31 15:23:31
        //String dateStr = "2020-03-31 10:23:31";
        //日期为 2020-04-30 15:23:31  输出结果为2020-05-03 8:23:31
//        String dateStr = "2020-04-30 15:23:31";
        //日期为 2020-05-31 15:23:31  输出结果为2020-06-01 8:23:31
        String dateStr = "2020-03-20 15:23:31";


        String s = calculateWeekAndLy(dateStr);
        System.out.println(s);
    }
    //计算 具体 截取的时间段
    public static String countHourSecond(int monthTemp,int dayTemp,int hourTemp,String second,int week,int quantum){
        //一周天数小于 8
        //j 用于区分星期6-7 分秒截取值（:00:00）  k用于区分星期1-5  休息时间段上 分秒截取值为“:00:00"的情况
        for (int i = week, j = -1,k = -1,n = 0; i < 8; i++) {
            // 判断是否要增加月份
            int[] monthOrDay = addMonthOrDay(monthTemp, dayTemp,hourTemp,i);
            //   计算时间段 < 4
            int[] hourArr = timeQuantum(hourTemp, quantum,k);
            if (monthOrDay[2] == 1) {
                monthTemp = monthOrDay[0];
                dayTemp = monthOrDay[1];
            }
            n = monthOrDay[2];
            if (i < 6) {
                j = 0;
                if (hourArr[1] == 0) {
                    hourTemp = hourArr[0];
                    if (hourArr[2] == 0){
                        second = ":00:00";
                    }
                    break;
                } else if (hourArr[1] == -1) {
                    if (n == 0) {
                        dayTemp += 1;
                    }
                    hourTemp = 8;
                    second = ":00:00";
                }
                else {
                    if (n == 0) {
                        dayTemp += 1;
                    }
                    hourTemp = 8 + hourArr[0];
                    k = 1;
                }
                // 6 和 7
            }else {
                if (n == 0) {
                    dayTemp += 1;
                }
                if (j == -1){
                    hourTemp = 8;
                    second = ":00:00";
                }
                if ( i == 7){
                    i = 1;
                }
            }
        }
        String month = String.valueOf(monthTemp);
        String day = String.valueOf(dayTemp);
        if (month.length() == 1){
            month = "0"+month;
        }
        if (day.length() == 1){
            day = "0"+day;
        }
        return  month+"-"+day+" "+hourTemp+second;
    }
    //计算 具体 截取的时间段
    public static int[] timeQuantum(int hour,int quantum,int sign){
        int rtn = 0;
        int mark = 0;
        //   星期5-7 已经取过值 得情况
        if (sign != -1){
            return new int[]{hour,mark,sign};
        }
        if (hour < 8){
            rtn = 8+quantum;
            sign = 0;
        }else if (hour < 9){
            rtn = hour + quantum;
        }else if (hour < 12){
            rtn = hour + quantum + 2;
        } else if (hour < 14){
        //      rtn = hour + quantum + (14-hour);
            rtn = 14 + quantum;
            sign = 0;
        }else if (hour < 15){
            rtn = hour + quantum;
        }else if (hour < 18){
            //  计算+1天
            mark = -2;
            if (hour != 15){
                rtn = quantum - 18 + hour;
            }
        }else {
            //    晚于18 点 ，计算+1天
            mark = -1;
            sign = 0;
        }
        return new int[]{rtn,mark,sign};
    }
    // 是否 增加 月份
    public static int[] addMonthOrDay(int currentMonth, int day,int hourTemp,int week){
        //   小月
        int[] smlMonth = {4,6,9,11};
        int finalCurrentMonth = currentMonth;
        //   SmallMonthBoolean
        boolean  SMB = Arrays.stream(smlMonth).anyMatch(f -> (f == finalCurrentMonth));
        // 18 - quantum
        if (hourTemp < 15 && (week != 6 || week != 7)){
            return new int[]{0,0,0};
        }

        //是否润月 ，润年
        if ((day == 29 || day == 28) && currentMonth == 2){
            currentMonth += 1;
            day = 1;
            return new int[]{currentMonth,day,1};
        }
        //小月
        if (day == 30){
            if (SMB){
                currentMonth += 1;
                day = 1;
                return  new int[]{currentMonth,day,1};
            }
        }
        //大月
        if (day == 31){
            currentMonth += 1;
            day = 1;
            return new int[]{currentMonth,day,1};
        }
        // 为 null则正常表达
        return new int[]{0,0,0};
    }
    //2020-03-21 14:23:31  计算所在日期 为星期几
    public static String calculateWeekAndLy(String dateStr) throws ParseException {
        int century = Integer.parseInt(dateStr.substring(0,2));
        int year = Integer.parseInt(dateStr.substring(2,4));
        int month = Integer.parseInt(dateStr.substring(5,7));
        int day = Integer.parseInt(dateStr.substring(8,10));
        // 星期几 计算公式 ：(y+[y/4]+[c/4]-2c+[26(m+1）/10]+d-1)%7
        int week =  (year+(year/4)+(century/4)-2*century+(26*(month+1)/10)+day-1)%7;
        // 计算是否 闰年
        //   boolean leapYear = (century%400 == 0) || (century/4 == 0 && century%100 != 0);
        int hour = Integer.parseInt(dateStr.substring(11,13));

        String second = dateStr.substring(13);
        //quantum 为小于4
        String s = countHourSecond(month, day, hour, second, week + 1,3);
        return century+""+year+"-"+s;
    }
}
