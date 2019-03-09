package HashMapTest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Demo {
    public static void main(String[] args) {
        System.out.println(comparableClassFor(new A()));    // null,A does not implement Comparable.
        System.out.println(comparableClassFor(new B()));    // null,B implements Comparable, compare to Object.
        System.out.println(comparableClassFor(new C()));    // class Demo$C,C implements Comparable, compare to itself.
        System.out.println(comparableClassFor(new D()));    // null,D implements Comparable, compare to its sub type.
        System.out.println(comparableClassFor(new F()));    // null,F is C's sub type.
    }

    static class A{}
    static class B implements Comparable<Object>{
        @Override
        public int compareTo(Object o) {return 0;}
    }
    static class C implements Comparable<C>{
        @Override
        public int compareTo(C o) {return 0;}

    }
    static class D implements Comparable<E>{
        @Override
        public int compareTo(E o) {return 0;}
    }
    static class E{}
    static class F extends C{}

    /**
     * Returns x's Class if it is of the form "class C implements
     * Comparable<C>", else null.
     */
    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c; Type[] ts, as; Type t; ParameterizedType p;
            // bypass checks
            if ((c = x.getClass()) == String.class)
                return c;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                            ((p = (ParameterizedType)t).getRawType() ==
                                    Comparable.class) &&
                            (as = p.getActualTypeArguments()) != null &&
                            as.length == 1 && as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }
}
