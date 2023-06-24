package Root.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Code {

    public static String function() {
        String k = "4985";
        int d = 0;
        for (int i = 0; i < 12; i = i + 1) {
            d = (int) (Math.random() * 10);
            k = k + d;

        }
        String s=k.replaceAll("\\d{4}", "$0 ");
        String n=s.substring(0,s.length()-1);
        return n;
    }

    public static void main(String[] args) {
      String s=  function();
        System.out.println(s);
        System.out.println(date());
    }

    public static String date() {
       Date date= datas();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        String d = sdf.format(date);

        return d;
    }

    public static String generationCVV() {
        String result = "";
        for (int i = 0; i < 3; i = i + 1) {
            int CVV = (int) (Math.random() * 9);
            result = result + CVV;

        }
        return result;
    }
public static Date datas(){
    Date date = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.YEAR, 3);
    date = c.getTime();
return date;

    }
}
