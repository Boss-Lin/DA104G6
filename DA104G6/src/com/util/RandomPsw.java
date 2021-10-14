package com.util;

public class RandomPsw {

    public String genAuthCode() {

        String [] pswPool = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8",
                "9", "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o" ,"p", "q",
                "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z" };

        StringBuilder container = new StringBuilder();

        for (int i = 0 ; i < 8 ; i++) {
            container.append(pswPool[(int)(Math.random() * pswPool.length)]);
        }

        return container.toString();
    }

}
