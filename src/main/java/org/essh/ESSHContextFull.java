package org.essh;

public interface ESSHContextFull extends ESSHContext {

    <T> ESSHParser<T> parser(Class<T> esshClass);

    String getUser();

    String getHost();

    String getPass();

    int getPort();
}
