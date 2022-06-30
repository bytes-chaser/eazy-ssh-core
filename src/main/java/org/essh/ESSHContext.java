package org.essh;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * SSH Context for {@link ESSHClient} instances.
 * It works as registry of {@link ESSHClient} clients.
 * Optionally possible to configure clients default settings (credentials, port)
 * and client {@link ESSHParser} parsers,
 * that allows use own DTO's as method return types in {@link ESSHClient} methods declarations
 */
public interface ESSHContext {

    /**
     * Registering {@link ESSHClient} in the context
     *
     * @param client {@link ESSHClient}
     * @return this {@link ESSHContext}
     */
    ESSHContext register(Class<? extends ESSHClient> client);

    /**
     * Adds {@link ESSHParser} in the context
     *
     * @param outputType DTO class associated with a {@link ESSHParser},
     *                   that will be used in methods declaration return types
     * @param parser     {@link ESSHParser} for <b>outputType</b>
     * @return this {@link ESSHContext}
     */
    <T> ESSHContext parser(Class<T> outputType, ESSHParser<T> parser);

    /**
     * Commits {@link ESSHClient} instances
     * to use following {@link SSH} implementation
     *
     * @param ssh {@link SSH} implementation
     * @return this {@link ESSHContext}
     */
    ESSHContext create(SSH ssh);

    /**
     * Provides {@link ESSHClient} from the context
     *
     * @param esshClass - {@link ESSHClient} class
     * @return {@link ESSHClient} class instance
     * @throws IllegalArgumentException if instance of <b>esshClass</b> doesn't exists in the context
     */
    <T extends ESSHClient> T client(Class<T> esshClass) throws IllegalArgumentException;

    /**
     * Sets SSH username that will be used by all registered {@link ESSHClient} instances by default
     *
     * @param user - SSH username
     */
    void setUser(String user);

    /**
     * Sets SSH host FQDN/IP that will be used by all registered {@link ESSHClient} instances by default
     *
     * @param host - SSH host FQDN/IP
     */
    void setHost(String host);

    /**
     * Sets SSH password that will be used by all registered {@link ESSHClient} instances by default
     *
     * @param pass - SSH password
     */
    void setPass(String pass);

    /**
     * Sets SSH port that will be used by all registered {@link ESSHClient} instances by default
     *
     * @param port - SSH port
     */
    void setPort(int port);


    /**
     * Commits {@link ESSHClient} instances
     * to use following {@link SSH} implementation
     *
     * @param sshImplSupplier supplier of {@link SSH} implementation
     * @return this {@link ESSHContext}
     */
    default ESSHContext create(Supplier<SSH> sshImplSupplier) {
        return create(sshImplSupplier.get());
    }


    /**
     * Registering collection of {@link ESSHClient} in the context
     *
     * @param clients {@link ESSHClient} collection
     * @return this {@link ESSHContext}
     */
    default ESSHContext register(Collection<Class<? extends ESSHClient>> clients) {
        clients.forEach(this::register);
        return this;
    }

    /**
     * Adds {@link ESSHParser} in the context
     *
     * @param parsersMap {@link ESSHParser} map. Where:<br><br>
     *                   key - DTO class associated with a {@link ESSHParser},
     *                   that will be used in methods declaration return types<br><br>
     *                   value - {@link ESSHParser} for <b>key</b>
     * @return this {@link ESSHContext}
     */
    default <T> ESSHContext parsers(Map<Class<T>, ESSHParser<T>> parsersMap) {
        parsersMap.forEach(this::parser);
        return this;
    }
}
