package io.github.bytes_chasser;

import java.lang.reflect.Method;

/**
 * Commands parsing, execution related utils
 */
class Command {

    /**
     * Parses commands array from method declaration/annotation
     *
     * @param method - called method
     * @return commands array.
     */
    static String[] get(Method method) {
        boolean isExecMethod = method.isAnnotationPresent(Exec.class);

        CommandParser parser = isExecMethod ?
                new ExecCommandParser(method.getAnnotation(Exec.class))
                :
                new MethodNameCommandParser(method.getName());

        return parser.parse();
    }
}
