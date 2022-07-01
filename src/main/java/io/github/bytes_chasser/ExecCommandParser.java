package io.github.bytes_chasser;

/**
 * Parsing SSH command based on {@link Exec} annotation details
 */
final class ExecCommandParser implements CommandParser {

    final Exec exec;


    ExecCommandParser(Exec exec) {
        this.exec = exec;
    }


    @Override
    public String[] parse() {
        String[] commands = exec.commands();
        for (int i = 0; i < commands.length; i++) {
            commands[i] = commands[i] + "\n";
        }
        return commands;
    }
}
