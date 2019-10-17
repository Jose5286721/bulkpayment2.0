package py.com.global.spm.GUISERVICE.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BeneficiaryWriter {
    public static void main(String args[]){
        Random rnd = new Random();
        File archivo = new File("/home/global/Desktop/beneficiary.txt");
        BufferedWriter bw = null;
        try {
            bw= new BufferedWriter(new FileWriter(archivo));
            PrintWriter pw = new PrintWriter(bw);
            int number;
            int i =0;
            Map<Integer,Object> list = new HashMap<>();
            while(i<100000) {
                number = 10000000 + rnd.nextInt(90000000);
                if(list.get(number)==null){
                    pw.println("09"+number+",1500,PYG,3,2,jose@gmail.com,Jose,Gomez,123,field2");
                    i++;
                    list.put(number,"asd");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                bw.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
