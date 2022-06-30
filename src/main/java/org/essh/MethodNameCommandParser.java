package org.essh;

/**
 * Parsing command based on method declaration syntax
 */
final class MethodNameCommandParser implements CommandParser {

    final String name;


    public MethodNameCommandParser(String name) {
        this.name = name;
    }


    @Override
    public String[] parse() {
        String[] commands = name.split("And");

        for (int i = 0; i < commands.length; i++) {
            commands[i] = commands[i]
                    .replaceAll("_", "")
                    .replaceAll("-", " -")
                    + "\n"
            ;
        }

        return commands;
    }
}
