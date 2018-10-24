package chkr.chkr;

import com.alibaba.fastjson.JSON;

import java.util.function.Function;

public class Chkr {

    private String chkrName;

    private Integer currentIndex;

    private Chkr prevChkr;

    /**
     * 先让父亲检查
     * 然后自己检查
     * 通过后返回这个值
     */
    private CheckedFunction checkFn;

    private String parentChkrName;

    public Chkr() {
    }

    private Chkr(CheckedFunction checkFn, Chkr chkr) {
        this.checkFn = checkFn;
        this.prevChkr = chkr;
    }

    /**
     *
     * @param checkTypeFn 如果value满足要求就返回它，否则抛出异常
     * @return Chkr
     */
    @SuppressWarnings("unchecked")
    public Chkr match(CheckedFunction checkTypeFn) {
        return new Chkr(value -> {
            if (prevChkr != null) {
                prevChkr.check(value);
            }
            return checkTypeFn.apply(value);
        }, this);
    }

    /**
     *
     * @param isType 对类型进行校验，返回true或false
     * @param message 如果类型检查不通过的报错信息
     * @return Chkr
     */
    @SuppressWarnings("unchecked")
    public Chkr judge(Function<Object, Boolean> isType, String message) {
        return match(value -> {
            if (isType.apply(value)) {
                return value;
            } else {
                throw new Exception("{{ " + JSON.toJSONString(value) + " }} " + message);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public Object check(Object value) throws Exception {
        if (checkFn != null) {
            try {
                return checkFn.apply(value);
            } catch (Exception e) {
                String info = (parentChkrName != null ? parentChkrName : "");
                if (parentChkrName != null && parentChkrName.equals("Arr")) {
                    info = "[" + currentIndex + "]";
                }
                if (!e.getMessage().startsWith("{{") && !e.getMessage().startsWith("[") && info.length() > 0) {
                    info += ".";
                }
                if (e.getMessage().startsWith("{{")) {
                    info += " = ";
                }
//                System.out.println("//"+ e.getMessage());
                throw new Exception(info + e.getMessage());
            }
        } else {
            // empty chkr
            return value;
        }
    }

    public Chkr called(String chkrName) {
        this.chkrName = chkrName;
        return this;
    }

    public Chkr currentIndex(Integer index) {
        this.currentIndex = index;
        return this;
    }

    public Chkr parentChkrName(String parentChkrName) {
        this.parentChkrName = parentChkrName;
        return this;
    }
}
