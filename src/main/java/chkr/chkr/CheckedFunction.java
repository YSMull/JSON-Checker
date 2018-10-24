package chkr.chkr;

@FunctionalInterface
interface CheckedFunction<T> {
    T apply(T value) throws Exception;
}