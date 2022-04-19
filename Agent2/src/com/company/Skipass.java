package com.company;

import java.io.*;

public class Skipass {
    char[] ski;
    StringBuffer passes = new StringBuffer();

    public void readFile() {
        Skipass user = new Skipass();
        try {
            File file = new File("/Users/79243/Downloads/passes.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {

                this.ski = line.toCharArray();
                StringBuffer result = new StringBuffer();
                user.newFormat(result, ski);

                System.out.println(result);
                passes.append(result);
                passes.append(System.getProperty("line.separator"));

                user.fileWriter(passes);

                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileWriter(StringBuffer passes) {
        try(FileWriter writer = new FileWriter("newSkipass.txt", false))
        {
            writer.write(String.valueOf(passes));
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public void newFormat(StringBuffer result, char ski[]) {
        int x = 14;
        while (x > 7) {
            result.append(ski[x]);
            x++;
            result.append(ski[x]);
            x = x - 3;
        }
    }
}
