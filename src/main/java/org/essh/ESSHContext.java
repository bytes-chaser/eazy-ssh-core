package org.essh;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public interface ESSHContext {

    ESSHContext addClient(Class<? extends ESSHClient> client);

    <T> ESSHContext addParser(Class<T> outputType, ESSHParser<T> parser);

    ESSHContext create(SSH ssh);

    <T extends ESSHClient> T client(Class<T> esshClass);


    void setUser(String user);

    void setHost(String host);

    void setPass(String pass);

    void setPort(int port);


    default ESSHContext create(Supplier<SSH> sshImplSupplier) {
        return create(sshImplSupplier.get());
    }


    default ESSHContext addClient(Collection<Class<? extends ESSHClient>> clients) {
        clients.forEach(this::addClient);
        return this;
    }

    default <T> ESSHContext addParser(Map<Class<T>, ESSHParser<T>> parsersMap) {
        parsersMap.forEach(this::addParser);
        return this;
    }
}
