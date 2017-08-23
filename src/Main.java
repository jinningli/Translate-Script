import translate.MapleTranslate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


/**
 * OnlineTranslation
 * lijinning, 2017.08.23, Shanghai.
 */
public class Main {
    final static int aln = 15;
    public static void main(String[] args) throws Exception {
        MapleTranslate obj = new MapleTranslate();
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));

        StringBuffer res = new StringBuffer();

        String err = "";

        String line;
        int l = 0;
        while((line = br.readLine()) != null){
            l ++;
            System.out.println("[Processing]\t(" + l + ") " + line + "");
            String nowline = line;
            while (nowline.length() < aln){
                nowline += " ";
            }
            try {
                nowline += obj.process("EN", "zh_CHS", line);
                res.append(nowline);
            }catch (java.lang.NullPointerException exp){
                res.append(nowline + "  [ERROR: Not Found]");
                err += "(" + l + ") " + line + "\n";
            }
            res.append("\n\n");
        }
        if(err.length() == 0) {
            System.out.println("---------- Word Not Found ---------\nAll Success.");
            System.out.println("---------- Process Success ---------\n");
        }else{
            System.out.println("---------- Word Not Found ---------");
            System.out.print(err);
            System.out.println("---------- Process Success ---------\n");
        }

        bw.write(res.toString());
        br.close();
        bw.close();

        System.out.println("Press Any Key to Show Result...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
        System.out.println("---------- Result ---------\n");
        System.out.println(res);

    }

}
