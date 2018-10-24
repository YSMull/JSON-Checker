## quick start
prepare data

```Java
Object data = Map.of(
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
```

Define your Checker by compose the **primitive checker**

```java
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
```

and run the check:

```java
Object after = checker.check(data);
System.out.println(JSON.toJSONString(after, true));
```

If the `data` dosen't match your `checker` , exception messages will show the mistake **precisely**~

![](https://ws4.sinaimg.cn/large/006tNbRwly1fwj4jl9trtj30w60s4q5g.jpg)

## primitive checker

| ChkrName       | Description                                                  |
| :------------- | ------------------------------------------------------------ |
| **Null**       | target field shoud be null                                   |
| **Any**        | target field shoud not be null                               |
| **Num**        | target field shoud **like** Number, for example: 123, "123"  |
| **StrictNum**  | target field shoud **be** Number                             |
| **Str**        | target field shoud be String                                 |
| **Bool**       | target field shoud **like** Boolean, for example: true , flase , "True", "true", "fAlsE" |
| **StrictBool** | target field shoud **be** Boolean                            |
| **Mixin**      |                                                              |
| **Or**         |                                                              |
| **Obj**        |                                                              |
| **Arr**        |                                                              |
| **Optional**   |                                                              |
| **OrVal**      |                                                              |

