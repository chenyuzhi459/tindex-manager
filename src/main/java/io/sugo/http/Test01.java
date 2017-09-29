package io.sugo.http;

import java.util.*;

public class Test01 {


    public static void main(String[] args) {
        String str = "_s";
        str = str.replace("[","[[]").replace("_","[_]");

        System.out.println("ss:" + str);

    }
}
