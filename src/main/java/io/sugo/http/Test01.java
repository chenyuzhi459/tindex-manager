package io.sugo.http;

import org.joda.time.Interval;

import java.util.*;

public class Test01 {


    public static void main(String[] args) {
        Interval interval =new Interval(System.currentTimeMillis()-200,System.currentTimeMillis());
        System.out.println("\"end\"");
        System.out.println(interval.toString());

    }
}
