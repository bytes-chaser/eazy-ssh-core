package org.essh;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Proxies methods in order to create SSH session with method call associated settings, data, commands list and parsing.
 * Flow pipe:
 * 1. Identifying SSH credentials and connection port from method arguments or context.
 * 2. Identifying SSH auto-close flag state. (If it's true in the end of execution all the sessions, connections will be closed. If not - connections, sessions will be stored in the pool)
 * 3. Identifying commands executions list from method signature or context.
 * 4. Executing commands list using SSH connection data
 * 5. (Optional) parse the data if method return type associated with existing in the context parser.
 */
final class ESSHClientProxy implements InvocationHandler {

    final ESSHContextFull context;
    final SSH ssh;


    public ESSHClientProxy(ESSHContextImpl context, SSH ssh) {
        this.context = context;
        this.ssh = ssh;
    }


    @SuppressWarnings("rawtypes")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        ExecData execData = new ExecData();

        execData.setConnectionData(Parameters.parse(context, args));

        String[] commands = Command.get(method);
        execData.setCommands(commands);
        execData.setOutputStartCommandIndex(Parsing.getOutputStartCommandIndex(method, commands));

        String output = ssh.execute(execData);
        ESSHParser parser = Parsing.parser(method, context);
        return parser == null ? output : parser.parse(output);
    }
}
