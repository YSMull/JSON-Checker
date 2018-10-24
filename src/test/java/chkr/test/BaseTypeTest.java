package chkr.test;

import chkr.chkr.Chkr;
import org.junit.rules.ExpectedException;

import static chkr.chkr.C.Or;
import static chkr.chkr.C.OrVal;
import static chkr.chkr.T.Num;
import static chkr.chkr.T.Str;

public class BaseTypeTest {
    public static void main(String[] args) throws Exception {
//        Num.check(null)); // throw "{null} is not Num"
        Num.check(123); // 123
        Num.check("123"); // (Long) 123
//        Num.check("abc")); // throw "{abc} is not Num"
//        Str.check(null)); // throw "{null} is not Str"
        Str.check("abc"); // abc
//        Str.check(123)); // throw "{123} is not Str"
        OrVal(1,2,3,'a').check('b');
        Or(Str, Num).check(123); // 123
        Or(Str, Num).check("abc"); // abc
        Or(Str, Num).check(true); // throw "{true} dose not match Or()"
        new Chkr().check(1); // empty chkr just return the value passed in
    }
}
