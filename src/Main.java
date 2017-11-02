import translate.MapleTranslate;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * OnlineTranslation
 * lijinning, 2017.08.23, Shanghai.
 */
public class Main {
    final static int aln = 15;
    static Map<String, String> storedData = new HashMap<>();

    public static void loadMap() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("data.maple"));
        String sign;
        String word;
        String cont = "";
        sign = br.readLine();
        while (Objects.equals(sign, "&")){
            sign = br.readLine();
            word = sign;
            while(!Objects.equals(sign, "&")) {
                sign = br.readLine();
                if(sign == null || Objects.equals(sign, "&"))
                    break;
                cont += sign;
                cont += '\n';
            }
            cont = cont.substring(0, cont.length() - 1);
            storedData.put(word, cont);
            cont = "";
            if(sign == null)
                break;
        }
        br.close();
        System.out.println("---------- Load Map Success! ---------");
    }

    public static void saveMap() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("data.maple"));
        for (Map.Entry ent:storedData.entrySet()){
            bw.write("&\n");
            bw.write((String)ent.getKey());
            bw.write('\n');
            bw.write((String)ent.getValue());
            bw.write('\n');
        }
        bw.close();
        System.out.println("---------- Save Map Success! ---------");
    }

    public static void main(String[] args) throws Exception {
        MapleTranslate obj = new MapleTranslate();
        Main.loadMap();
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));

        StringBuffer res = new StringBuffer();

        String err = "";

        String line;
        int l = 0;
        while((line = br.readLine()) != null){
            if(Objects.equals(line, "")||Objects.equals(line, "单词本"))
                continue;
            l ++;
            if (storedData.containsKey(line)){
                System.out.println("[Processing][Offline]\t(" + l + ") " + line + "");
                res.append(storedData.get(line));
                res.append("\n");
                continue;
            }
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
            storedData.put(line, nowline);
            res.append("\n");
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

        Main.saveMap();

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
