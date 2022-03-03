package shell.command.implementation;

import shell.command.definition.Command;

public class EchoCommand implements Command {
    @Override
    public String getHelp() {
        return "Command return same input on the output as it was given";
    }

    @Override
    public String getName() {
        return "echo";
    }

    @Override
    public String execute(String... args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if (i < args.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
