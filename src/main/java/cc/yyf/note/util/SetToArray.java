package cc.yyf.note.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 将Set<String>转化为String[]
 */
public class SetToArray {
    public static String[] convert(Set<String> set) {
        List<String> list = new ArrayList<>(set);
        if (list.size() != 0) {
            String[] strings = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                strings[i] = list.get(i);
            }
            return strings;
        } else {
            return null;
        }
    }
}
