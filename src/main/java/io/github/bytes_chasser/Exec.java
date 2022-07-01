package io.github.bytes_chasser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link ESSHClient} method configuration.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Exec {

    /**
     * Commands to be executed
     */
    String[] commands();

    /**
     * Command index which output will be first in SSH payload output
     */
    int outputParseStartIndex() default -1;
}
