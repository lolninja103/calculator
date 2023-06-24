package Root.Controller;

public class CVV {
    public static void main(String[] args) {
sva();
    }
    public static String sva(){
        String result="";
        for (int i=0;i<3;i=i+1) {
            int CVV = (int) (Math.random() * 9);
           result=result+CVV;
        }

        //card.setCVV(CV);
return result;    }
}
