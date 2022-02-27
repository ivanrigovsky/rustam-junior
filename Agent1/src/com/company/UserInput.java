package com.company;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInput {
    char[] ski;
    int z;

    private static String[] english = {"A","B","C","E","H","K","M","O","P","T","X","Y"};

    private static String[] russian = {"А","В","С","Е","Н","К","М","О","Р","Т","Х","У"};

    public void skipass() {
        UserInput user = new UserInput();
        boolean symbol;
        do {
            if (z>0) {
                System.out.println("Некорректный номер ski-pass");
            }
            System.out.println("Введите номер ski-pass");
            Scanner str = new Scanner(System.in);
            String result = str.nextLine();
            result = result.toUpperCase();
            result = result.trim();
            Pattern p = Pattern.compile("[A-Z0-9АВСЕНКМОРТХУ]+");
            Matcher m = p.matcher(result);
            symbol = m.matches();

            this.ski= result.toCharArray();
            z++;

        } while (ski.length!=16 || ski[0]!='E' || symbol!=true);

        StringBuffer result = new StringBuffer();
        int x = 14;

        while (x > 7) {
            result.append(ski[x]);
            x++;
            result.append(ski[x]);
            x = x - 3;
        }

        user.fromRusToEng(result);

        System.out.println(result);
    }

    public void fromRusToEng (StringBuffer result) {
        for(int i=0;i<result.length();i++) {
            for(int j=0; j<english.length;j++) {
                if(Character.toString(result.charAt(i)).equals(russian[j])) {
                    result=result.replace(i, i+1,english[j]);
                }
            }
        }
    }
}
