package com.common.design;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujingxing  on 16/8/9.
 */
class Utils {
    static List<Integer> getArray(int[] array) {
        if (array == null || array.length == 0) return null;
        List<Integer> list = new ArrayList<>();
        for (int i : array) {
            list.add(i);
        }
        return list;
    }
}
