package io.sugo.http;

import java.util.*;

public class Test01 {


    public static void main(String[] args) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        arrayList.add("ab");
        arrayList.add("ac");

        Iterator<String> iterator = arrayList.iterator();

        while (iterator.hasNext()) {
            if(!iterator.next().contains("a")) {
                iterator.remove();
            }
        }

        System.out.println(arrayList);

    }
}
