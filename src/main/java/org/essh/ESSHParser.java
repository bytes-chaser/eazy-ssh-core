package org.essh;

@FunctionalInterface
public interface ESSHParser<T> {

    /**
     * Parsing SSH output text to parametrized DTO
     *
     * @param output SSH output text
     * @return parse result object
     */
    T parse(String output);
}
