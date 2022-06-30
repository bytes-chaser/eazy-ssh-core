package org.essh;

final class ExecCommandParser implements CommandParser {

    final Exec exec;


    ExecCommandParser(Exec exec) {
        this.exec = exec;
    }


    @Override
    public String[] parse() {
        String[] commands = exec.value();
        for (int i = 0; i < commands.length; i++) {
            commands[i] = commands[i] + "\n";
        }
        return commands;
    }
}
