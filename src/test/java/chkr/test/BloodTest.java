package chkr.test;

import chkr.chkr.Chkr;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

import static chkr.chkr.C.*;
import static chkr.chkr.T.Num;
import static chkr.chkr.T.Str;
import static chkr.chkr.T.StrictNum;
import static chkr.chkr.alias.Alias.kv;
import static chkr.chkr.alias.Alias.list;

public class BloodTest {
    public static void main(String[] args) throws Exception {
        // 准备一个 JSON 数据
        Object before = Map.of(
            "reports", List.of(
                Map.of(
                    "id", 1,
                    "title", "title",
                    "relatedDataModelIds", List.of(1, 2, 3)
                )
            ),
            "dataModels", List.of(
                Map.of(
                    "status", "extract",
                    "relatedDataConnectionIds", List.of(1, 2, "123")
                )
            )
        );
        // 声明一个校验
        Chkr checker = Mixin(
            Obj(
                "reports", Arr(Obj(
                    "id", Num,
                    "title", Str,
                    "relatedDataModelIds", Arr(Num)
                ))
            ),
            Or(
                Obj(
                    "dataModels", Arr(Obj(
                        "status", OrVal("extract", "direct"),
                        "relatedDataConnectionIds", Arr(StrictNum)
                    ))
                ),
                Obj(
                    "dataModels", Arr(Obj(
                        "status", OrVal("extract", "direct"),
                        "relatedTableExtractIds", Arr(Num)
                    ))
                )
            )
        );
        // 执行校验
        Object after = checker.check(before);
        System.out.println(JSON.toJSONString(after, true));
    }
}
