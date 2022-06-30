package org.essh;

/**
 * Settings for {@link SSH} implementation
 */
public class ExecData {

    ConnectionData connectionData;
    String[] commands;
    int outputStartCommandIndex;


    public ConnectionData getConnectionData() {
        return connectionData;
    }


    public String[] getCommands() {
        return commands;
    }


    public int getOutputStartCommandIndex() {
        return outputStartCommandIndex;
    }


    public void setConnectionData(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }


    public void setCommands(String[] commands) {
        this.commands = commands;
    }


    public void setOutputStartCommandIndex(int outputStartCommandIndex) {
        this.outputStartCommandIndex = outputStartCommandIndex;
    }

}
