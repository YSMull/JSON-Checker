package chkr.test;

import com.alibaba.fastjson.JSON;

import static chkr.chkr.C.*;
import static chkr.chkr.T.*;
import static chkr.chkr.alias.Alias.kv;
import static chkr.chkr.alias.Alias.list;

public class Test3 {
    public static void main(String[] args) throws Exception {

        var object = kv(
                "a", kv(
                        "b", kv(
                                "c", list(kv(
                                        "d", kv(
                                                "e", kv(
                                                        "f", list(1,"2",3,4)
                                                )
                                        )
                                ))
                        )
                )
        );

        var chkr = Obj(
                "a", Obj(
                        "b", Obj(
                                "c", Arr(Obj(
                                        "d", Obj(
                                                "e", Or(Bool, Str, Obj(
                                                        "f", Arr(StrictNum)
                                                ))
                                        )
                                ))
                        )
                )
        );

        var after = chkr.check(object);

        System.out.println(JSON.toJSONString(after, true));
    }
}
