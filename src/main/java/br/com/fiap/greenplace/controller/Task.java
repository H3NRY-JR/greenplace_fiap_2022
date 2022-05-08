package br.com.fiap.greenplace.controller;

import java.util.Arrays;

public class Task {
    public static boolean validate(final String eanCode) {
        int amount = 0;

        for(int i = 0; i < eanCode.length()-2; i++){
            int value;
            if (i%2 == 0){
                value = eanCode.charAt(i);
            }else{
                value = eanCode.charAt(i)*2;
            }
            amount += value;
        }

        return amount % 10 == eanCode.charAt(eanCode.length()-1);
    }

    static void main(final String[] args) {

    }
}


