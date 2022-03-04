package shell.command.definition;

public interface Command {
    String getHelp();
    String getName();
    String execute(String... args);
}
