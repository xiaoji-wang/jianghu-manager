package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wxji on 2017-01-03.
 */
public class Collection {
    public static Map map(Object... objects) {
        if (objects.length % 2 != 0) {
            throw new RuntimeException("参数数量错误");
        }
        Map map = new HashMap<>();
        for (int i = 0; i < objects.length; i += 2) {
            map.put(objects[i], objects[i + 1]);
        }
        return map;
    }

    public static List list(Object... objects) {
        return Arrays.asList(objects);
    }
}
