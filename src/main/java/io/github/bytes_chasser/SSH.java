package io.github.bytes_chasser;

/**
 * SSH Client
 */
@FunctionalInterface
public interface SSH {

    /**
     * Executes commands over SSH channel
     *
     * @param execData - dto with SSH credentials, settings and commands list
     * @return Output String representation
     * @throws Exception if error occurred during execution
     */
    String execute(ExecData execData) throws Exception;
}
