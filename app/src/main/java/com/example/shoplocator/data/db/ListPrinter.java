package com.example.shoplocator.data.db;

import java.util.List;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class ListPrinter {

    private final List items;

    public ListPrinter(List items) {
        this.items = items;
    }

    public String toString() {
        String resultStr = "[";
        for (int i=0; i<items.size(); i++) {
            Object item = items.get(i);
            if (i > 0) resultStr += ",";
            resultStr += item.toString();
        }
        resultStr += "]";
        return resultStr;
    }
}
