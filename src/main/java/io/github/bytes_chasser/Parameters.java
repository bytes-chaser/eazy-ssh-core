package io.github.bytes_chasser;

/**
 * Utils for translating method declaration arguments into SSH execution settings
 */
class Parameters {

    /**
     * SSH resulting settings from {@link ESSHContext} and method call arguments
     *
     * @param context {@link ESSHContext}
     * @param args    method call arguments
     * @return @{@link ConnectionData} details
     */
    public static ConnectionData connectionData(ESSHContextFull context, Object[] args) {

        int length = args == null ? -1 : args.length;

        String host = length < 1 ? context.getHost() : (String) args[0];
        String user = length < 2 ? context.getUser() : (String) args[1];
        String pass = length < 3 ? context.getPass() : (String) args[2];

        if (invalid(host))
            throw new IllegalArgumentException("Host argument not found. First parameter is null or empty string");
        else if (invalid(user))
            throw new IllegalArgumentException("Username argument not found. Second parameter is null or empty string");
        else if (invalid(pass))
            throw new IllegalArgumentException("Password argument not found. Third parameter is null or empty string");

        int contextPort = context.getPort();
        int port = contextPort < 0 ? 22 : contextPort;

        if (length > 3) {
            int portArg = (int) args[3];
            if (portArg > 0) port = portArg;
        }

        return new ConnectionData(host, user, pass, port);
    }


    private static boolean invalid(String str) {
        return str == null || str.isEmpty();
    }

}
