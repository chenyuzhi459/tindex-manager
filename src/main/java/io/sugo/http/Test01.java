package io.sugo.http;

import org.joda.time.Interval;

import java.util.*;

public class Test01 {


    public static void main(String[] args) {
        Interval interval =new Interval(System.currentTimeMillis()-200,System.currentTimeMillis());
        System.out.println(interval.toString());
        System.out.println(interval.getEnd().toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

    }
}
