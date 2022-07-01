package io.github.bytes_chasser;

/**
 * DTO containing SSH Connection Data
 */
public class ConnectionData {

    final String host;
    final String user;
    final String password;
    final int port;


    /**
     * @param host     - SSH host. It can be FQDN or IP address
     * @param user     - SSH host user.
     * @param password - SSH host user password
     * @param port     - port associated with SSH connection. By default, most of the systems using port 22
     */
    ConnectionData(String host, String user, String password, int port) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.port = port;
    }


    public String getHost() {
        return host;
    }


    public String getUser() {
        return user;
    }


    public String getPassword() {
        return password;
    }


    public int getPort() {
        return port;
    }
}
