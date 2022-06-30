package org.essh;

import java.lang.reflect.Method;

class Parsing {

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


    @SuppressWarnings("rawtypes")
    public static ESSHParser parser(Method method, ESSHContextFull context) {
        Class<?> returnType = method.getReturnType();
        return context.parser(returnType);
    }
}
