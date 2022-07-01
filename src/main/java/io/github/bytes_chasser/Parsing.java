package io.github.bytes_chasser;

import java.lang.reflect.Method;

/**
 * Utils for parsing SSH outputs
 */
class Parsing {

    /**
     * Return index of command, which output will be first in resulting text.
     * It will be used cut of pointcut SSH outputs for returning only reasonable payload
     *
     * @param method   - called {@link ESSHClient} method
     * @param commands - commands for execution
     * @return command index which will be first chunk in the output payload text
     */
    public static int getOutputStartCommandIndex(Method method, String[] commands) {
        int length = commands.length;
        int index = -1;

        if (method.isAnnotationPresent(Exec.class)) {
            Exec exec = method.getAnnotation(Exec.class);
            index = exec.outputParseStartIndex();
        }

        if (index < 0) index = length > 1 ? length - 1 : 0;
        return index;
    }


    /**
     * Looking for parser suitable for method return type
     *
     * @param method  - called {@link ESSHClient} method
     * @param context - {@link ESSHContext} of client
     * @return {@link ESSHParser} associated with <b>method</b> return type
     */
    @SuppressWarnings("rawtypes")
    public static ESSHParser parser(Method method, ESSHContextFull context) {
        Class<?> returnType = method.getReturnType();
        return context.parser(returnType);
    }
}
