package org.essh;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Proxy for execution declarative methods of {@link ESSHClient} in order to create SSH session with call associated settings, data, commands list and parsing.
 * Flow pipe:<br>
 * <ol>
 *     <li>Identifying SSH credentials and connection port from method arguments or context.</li>
 *     <li>Identifying SSH auto-close flag state. (If it's true in the end of execution all the sessions, connections will be closed. If not - connections, sessions will be stored in the pool)</li>
 *     <li>Identifying commands executions list from method signature or context.</li>
 *     <li>Executing commands list using SSH connection data</li>
 *     <li>(Optional) parse the data if method return type associated with existing in the context parser.</li>
 * </ol>
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

        execData.setConnectionData(Parameters.connectionData(context, args));

        String[] commands = Command.get(method);
        execData.setCommands(commands);
        execData.setOutputStartCommandIndex(Parsing.getOutputStartCommandIndex(method, commands));

        String output = ssh.execute(execData);
        ESSHParser parser = Parsing.parser(method, context);
        return parser == null ? output : parser.parse(output);
    }
}
