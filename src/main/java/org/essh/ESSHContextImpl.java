package org.essh;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ESSHContextImpl implements ESSHContextFull {

    private String user;
    private String host;
    private String pass;
    private int port = -1;
    private SSH ssh;
    private final Supplier<InvocationHandler> invHandler = () -> new ESSHClientProxy(this, ssh);

    private final Set<Class<? extends ESSHClient>> clients = new HashSet<>();
    private final Map<Class<? extends ESSHClient>, Object> proxies = new HashMap<>();
    private final Map<String, ESSHParser<?>> parser = new HashMap<>();


    public ESSHContextImpl register(Class<? extends ESSHClient> client) {
        clients.add(client);
        return this;
    }


    public <T> ESSHContextImpl parser(Class<T> outputType, ESSHParser<T> parser) {
        this.parser.put(outputType.getName(), parser);
        return this;
    }


    public ESSHContext create(SSH ssh) {
        this.ssh = ssh;
        clients.forEach(this::createClientInstance);
        return this;
    }


    protected void createClientInstance(Class<? extends ESSHClient> client) {
        Object proxyInstance = Proxy.newProxyInstance(ESSHContextImpl.class.getClassLoader(), new Class[]{client}, invHandler.get());
        proxies.put(client, proxyInstance);
    }


    @SuppressWarnings("unchecked")
    public <T extends ESSHClient> T client(Class<T> esshClass) {
        if (!proxies.containsKey(esshClass))
            throw new IllegalArgumentException("Unknown SSH client of class" + esshClass.getName());
        return (T) proxies.get(esshClass);
    }


    @SuppressWarnings("unchecked")
    public <T> ESSHParser<T> parser(Class<T> esshClass) {
        return (ESSHParser<T>) parser.get(esshClass.getName());
    }


    public String getUser() {
        return user;
    }


    public void setUser(String user) {
        this.user = user;
    }


    public String getHost() {
        return host;
    }


    public void setHost(String host) {
        this.host = host;
    }


    public String getPass() {
        return pass;
    }


    public void setPass(String pass) {
        this.pass = pass;
    }


    public int getPort() {
        return port;
    }


    public void setPort(int port) {
        this.port = port;
    }

}
