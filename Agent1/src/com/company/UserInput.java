package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInput {
    char[] ski;
    int z;

    public void skipass() {
        UserInput u = new UserInput();
        do {
            this.z=z;
            if (z>0) {
                System.out.println("Некорректный номер ski-pass");
            }
            System.out.println("Введите номер ski-pass");
            Scanner str = new Scanner(System.in);
            String result = str.nextLine();

            char[] ski = result.toCharArray();
            this.ski=ski;
            z++;

        } while (ski.length!=16 || ski[0]!='E');

        int x = 14;
        StringBuffer result = new StringBuffer();
        while (x > 7) {
            result.append(ski[x]);
            x++;
            result.append(ski[x]);
            x = x - 3;
        }
        System.out.println(result);
    }
}
