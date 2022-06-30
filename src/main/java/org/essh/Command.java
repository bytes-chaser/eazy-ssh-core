package org.essh;

import java.lang.reflect.Method;

class Command {

    static String[] get(Method method) {
        boolean isExecMethod = method.isAnnotationPresent(Exec.class);

        CommandParser parser = isExecMethod ?
                new ExecCommandParser(method.getAnnotation(Exec.class))
                :
                new MethodNameCommandParser(method.getName());

        return parser.parse();
    }
}
