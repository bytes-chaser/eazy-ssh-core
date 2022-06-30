package org.essh;

@FunctionalInterface
public interface ESSHParser<T> {

    T parse(String output);
}
