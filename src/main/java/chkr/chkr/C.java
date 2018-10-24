package chkr.chkr;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.stream.Collectors;

import static chkr.chkr.Util.orErrMsgBuilder;

public class C {

    public static Chkr Mixin(Chkr... chkrs) {
        return T.Any.match(objMap -> {
            Map objectMap = (Map) objMap;
            Map<String, Object> filterMap = new HashMap<>();
            for (Chkr chkr : chkrs) {
                filterMap.putAll((Map<String, Object>) chkr.check(objectMap));
            }
            return filterMap;
        }).called("Mixin");
    }

    public static Chkr Or(Chkr... chkrs) {
        return T.Any.match(value -> {
            boolean matchOne = false;
            Object result = null;
            List<Exception> exceptionList = new ArrayList<>();
            for (Chkr chkr : chkrs) {
                try {
                    // filter
                    result = chkr.check(value);
                    matchOne = true;
                    break;
                } catch (Exception e) {
                    exceptionList.add(e);
                }
            }
            if (matchOne) {
                return result;
            } else {
                throw new Exception("\n" + orErrMsgBuilder(exceptionList) + " \ndose not match Or()" + "\n" + JSON.toJSONString(value, true));
            }
        }).called("Or");
    }

    public static Chkr Obj(Object... args) throws Exception {
        Map<String, Chkr> chkrMap = Util.parseChkrMap(args);
        if (chkrMap == null || chkrMap.size() == 0) return null;
        return T.Any.match(objMap -> {
            if (!(objMap instanceof Map)) {
                throw new Exception("{{ " + JSON.toJSONString(objMap) + " }} is not an kv");
            }
            Map castMap = (Map) objMap;
            Map<String, Object> filterMap = new HashMap<>();
            Set<String> keys = chkrMap.keySet();
            for (String key : keys) {
                Chkr keyChkr = chkrMap.get(key);
                filterMap.put(key, keyChkr.parentChkrName(key).check(castMap.get(key)));
            }
            return filterMap;
        }).called("kv");
    }

    public static Chkr Arr(Chkr typeChkr) {
        String chkrName = "Arr";
        return T.Any.match(objList -> {
            if (!(objList instanceof List)) {
                throw new Exception("{{ " + JSON.toJSONString(objList) + " }} is not an Array");
            }
            List objectList = (List) objList;
            for (int i = 0; i < objectList.size(); i++) {
                typeChkr.parentChkrName(chkrName).currentIndex(i).check(objectList.get(i));
            }
            return objList;
        }).called(chkrName);
    }

    public static Chkr Optional(Chkr typeChkr) {
        return new Chkr().match(object -> {
            if (object == null) return null;
            return typeChkr.parentChkrName("Optional").check(object);
        });
    }

    public static Chkr OrVal(Object... args) {
        return new Chkr().match(value -> {
            boolean exist = Arrays.asList(args).contains(value);
            String argStr = Arrays.stream(args).map(JSON::toJSONString).collect(Collectors.joining(","));
            if (!exist) throw new Exception("{{ " + JSON.toJSONString(value) + " }} dose not match OrVal(" + argStr + ")");
            return value;
        });
    }
}
