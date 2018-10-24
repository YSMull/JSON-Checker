package chkr.chkr;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Util {
    public static Map<String, Chkr> parseChkrMap(Object[] args) throws Exception {
        if (args == null || args.length == 0) {
            return null;
        }
        if (args.length % 2 == 1) {
            throw new Exception("params map array size wrong");
        }
        // LinkedHashMap 可以保持插入顺序
        Map<String, Chkr> m = new LinkedHashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            m.put((String) args[i], (Chkr) args[i + 1]);
        }
        return m;
    }

    public static String orErrMsgBuilder(List<Exception> exceptionList) {
        return exceptionList.stream().map(Exception::getMessage).collect(Collectors.joining("\n"));
    }

}
