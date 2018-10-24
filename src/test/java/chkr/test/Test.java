package chkr.test;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

import static chkr.chkr.C.*;
import static chkr.chkr.T.Num;
import static chkr.chkr.T.Str;

public class Test {
    public static void main(String[] args) {
        try {
            Object before = Map.of(
                "userId", 123,
                "userName", "haha",
                "phone", "aa",
                "IdCard", "abaa",
                "Address", Map.of(
                    "id", 123,
                    "person", Map.of(
                        "id", "aeee",
                        "age", 123
                    )
                ),
                "numList", List.of(123, 456),
                "aaa", "123",
                "bbb", "222",
                "Or3", "a"
            );
            Object after = Mixin(
                Obj(
                    "userId", Num,
                    "userName", Str,
                    "IdCard", Or(Num, Str),
                    "Address", Obj(
                        "id", Num,
                        "person", Obj(
                            "id", Str,
                            "age", Optional(Num)
                        )
                    ),
                    "numList", Arr(Num)
                ),
                Or(
                    Obj(
                        "Or1", Str
                    ),
                    Obj(
                        "Or2", Str
                    )
                )
            ).check(before);
            System.out.println(JSON.toJSONString(after, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
