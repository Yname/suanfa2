import java.text.ParseException;
import java.util.Calendar;

public class Two
{
    public static void main(String[] args) {
        String dateStr = "2020-03-21 14:23:31";
//        int year = Integer.parseInt(dateStr.substring(0,4));
//        int month = Integer.parseInt(dateStr.substring(5,7));
//        int day = Integer.parseInt(dateStr.substring(8,10));
//        int i = caculateWeekOfYear(year, month, day);
//        System.out.println(i);


    }

    public static int caculateWeekOfYear(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DATE, day);
        return c.get(Calendar.WEEK_OF_YEAR);
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
        int minute = Integer.parseInt(dateStr.substring(14,16));
        int second = Integer.parseInt(dateStr.substring(17));
        String second2 = dateStr.substring(13);
        //quantum 为小于4
//        String s = countHourSecond(month, day, hour, second2, week + 1,3);
        String s = null;
        return century+""+year+"-"+s;
    }

}
