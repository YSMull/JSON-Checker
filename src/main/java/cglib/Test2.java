package cglib;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import net.sf.cglib.beans.BeanGenerator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class Test2 {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<String> clazz = String.class;
        Constructor<String> con = clazz.getConstructor(String.class);
        CharSequence str = con.newInstance("abc");
        System.out.println(str);

        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("value",String.class);

        Class a = (Class) beanGenerator.createClass();
        System.out.println(a.getName());
        Object myBean = beanGenerator.create();
        Method setter = myBean.getClass().getMethod("setValue",String.class);
        setter.invoke(myBean,"Hello cglib");

        Method getter = myBean.getClass().getMethod("getValue");
        System.out.println(getter.invoke(myBean));

        CompilationUnit compilationUnit = JavaParser.parse("class A { public Striang a; }");


        Optional<ClassOrInterfaceDeclaration> classA = compilationUnit.getClassByName("A");
        System.out.println();

        com.sun.tools.javac.Main javac = new com.sun.tools.javac.Main();
    }
}
