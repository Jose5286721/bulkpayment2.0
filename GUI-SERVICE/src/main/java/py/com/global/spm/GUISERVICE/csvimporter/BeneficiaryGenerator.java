package py.com.global.spm.GUISERVICE.csvimporter;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BeneficiaryGenerator {

    public static void main(String args[]){
        String path = "/home/global/Documents/testBeneficiaries/beneficiary10000.csv";
        Random rnd = new Random();
        int number;
        int i=0;
        List<String> b = new ArrayList<>();
        HashMap<Integer, Object> oima = new HashMap<Integer, Object>();
      while(i<10000){
         number = 10000000 + rnd.nextInt(90000000);
         if(oima.get(number)==null) {
             b.add("09" + number + ",1500, PYG"+number+"+,3, 0982123569,123,jose"+number+"@gmail.com, Jose, Gomez, field2");
             oima.put(number,"asd");
             i++;
         }
      }
      try {
          Files.write(Paths.get(path),b);
      }catch (Exception e){

      }
    }
}
