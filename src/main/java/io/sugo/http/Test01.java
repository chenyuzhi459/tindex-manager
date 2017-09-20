package io.sugo.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

public class Test01 {
    private static Properties properties = new Properties();


    public static void main(String[] args) throws IOException {
        String searchValue = "admin-test-001";
        String subString1 = MessageFormat.format("SELECT id, created_date, payload, status_payload FROM {0} WHERE active = FALSE AND created_date >= :start AND {1} LIKE","druid-task","id");
        String subString2 = MessageFormat.format(" ORDER BY {0} {1} limit 1000","created-date","desc");
        String query2 = subString1 + "'%" + searchValue + "%'" + subString2;
        String query  = MessageFormat.format("SELECT id, created_date, payload, status_payload FROM {0} WHERE active = FALSE AND created_date >= :start AND {1} LIKE '%" +searchValue +"%' ORDER BY {2} {3} limit 1000",
                "druid-task","id","created-date","desc");

        System.out.println(query2);
    }
}
