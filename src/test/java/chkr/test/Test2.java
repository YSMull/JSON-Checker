package chkr.test;

import com.alibaba.fastjson.JSON;

import static chkr.chkr.C.*;
import static chkr.chkr.T.*;
import static chkr.chkr.alias.Alias.*;

public class Test2 {
    public static void main(String[] args) throws Exception {
        Object testData = kv(
            "reports", list(
                kv(
                    "id", 1,
                    "title", "1",
                    "relatedDataModelIds", list(1, 2, 3)
                ),
                kv(
                    "id", 1,
                    "title", "1",
                    "relatedDataModelIds", list(1, 2, 3)
                )
            ),
            "a", "123",
            "b", kv(
                "b1", "123",
                "b2", "false"
            )
        );

        Object after = Mixin(
            Obj(
                "reports", Arr(Obj(
                    "id", Num,
                    "title", Str,
                    "relatedDataModelIds", Arr(Num)
                ))
            ),
            Or(
                Obj(
                    "a", Num,
                    "b", Num
                ),
                Obj(
                    "a", Str,
                    "b", Obj(
                        "b1", StrictNum,
                        "b2", Str
                    )
                ),
                Obj(
                    "a", Str,
                    "b", Obj(
                        "b1", Num,
                        "b2", StrictBool
                    )
                )
            )
        ).check(testData);

        System.out.println(JSON.toJSONString(after, true));
    }
}
