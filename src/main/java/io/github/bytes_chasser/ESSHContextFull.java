package io.github.bytes_chasser;

/**
 * Extension for {@link ESSHContext} used for accessing context data by {@link ESSHClientProxy} in the runtime
 * and for new implementation creating of {@link ESSHContext}
 */
public interface ESSHContextFull extends ESSHContext {

    /**
     * Return {@link ESSHParser} for DTO class
     *
     * @param esshClass DTO class associated with a {@link ESSHParser}
     * @return {@link ESSHParser} for <b>esshClass</b>
     */
    <T> ESSHParser<T> parser(Class<T> esshClass);

    /**
     * Return context default SSH username
     *
     * @return username
     */
    String getUser();

    /**
     * Return context default SSH host address
     *
     * @return FQDN/IP
     */
    String getHost();

    /**
     * Return context default SSH password
     *
     * @return password
     */
    String getPass();

    /**
     * Return context default SSH port
     *
     * @return SSH port number
     */
    int getPort();
}
