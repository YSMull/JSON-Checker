package chkr.chkr;

import com.alibaba.fastjson.JSON;

import java.util.Objects;

public class T {

    public static Chkr Null = new Chkr().judge(Objects::isNull, "is not Null").called("Null");

    public static Chkr Any = new Chkr().judge(Objects::nonNull, "is Null").called("nonNull");

    public static Chkr Num = Any.match(v -> {
        try {
            return Long.parseLong(v.toString());
        } catch (Exception ignore) {
            throw new Exception("{{ "+ JSON.toJSONString(v) +" }} is not Num");
        }
    }).called("Num");

    public static Chkr StrictNum = Any.judge(v -> v instanceof Number, "is not a StrictNum").called("StrictNum");

    public static Chkr Str = Any.judge(v -> v instanceof String, "is not a Str").called("Str");

    public static Chkr StrictBool = Any.judge(v -> v instanceof Boolean, "is not a StrictBool").called("StrictBool");

    public static Chkr Bool = Any.match(v -> {
        String t = v.toString().toLowerCase();
        if (t.equals("false") || t.equals("true")) {
            return Boolean.parseBoolean(v.toString());
        } else {
            throw new Exception("{{ "+ JSON.toJSONString(v) +" }} is not Bool");
        }
    }).called("Bool");
}
