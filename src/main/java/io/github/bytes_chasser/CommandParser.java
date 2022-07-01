package io.github.bytes_chasser;

/**
 * SSH commands parsers.
 */
interface CommandParser {

    /**
     * Parses commands for SHH execution
     *
     * @return commands array
     */
    String[] parse();
}
