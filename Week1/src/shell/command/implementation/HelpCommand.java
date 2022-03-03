package shell.command.implementation;

import shell.command.definition.Command;

public class HelpCommand implements Command {

    private Command[] commands;

    public HelpCommand(Command... commands) {
        this.commands = commands;
    }

    @Override
    public String getHelp() {
        return "Provide user with description of available commands";
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String execute(String... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("*********** Available commands ***********\n");
        for (Command command : commands) {
            sb.append(command.getName());
            sb.append(" -> ");
            sb.append(command.getHelp());
            sb.append("\n");
        }
        sb.append("exit -> Exit the application");
        return sb.toString();
    }
}
