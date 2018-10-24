package chkr.chkr.alias;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Alias {

    public static Map<String, Object> kv(Object ...args) {
        if (args == null || args.length == 0) {
            return null;
        }
        if (args.length % 2 == 1) {
            throw new RuntimeException("params map array size wrong");
        }
        Map<String, Object> m = new LinkedHashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            m.put((String) args[i], args[i + 1]);
        }
        return m;
    }

    public  static List list(Object ...args) {
        return List.of(args);
    }
}
