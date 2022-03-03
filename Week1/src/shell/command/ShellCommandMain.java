package shell.command;

import shell.command.definition.Command;
import shell.command.implementation.HelpCommand;
import utils.PluginUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShellCommandMain {

    public static void main(String[] args) {
        //Command plugin are stored in Hash map for the performance
        Map<String, Command> commandsMap = new HashMap<>();
        if (args.length > 0) {
            try {
                List<String> lines = Files.readAllLines(Path.of(args[0]));
                List<Command> commands = PluginUtils.LoadPlugins(Command.class, lines.toArray(new String[0]));
                commands.forEach(command -> commandsMap.put(command.getName(), command));

                //Adding Help command on the top of commands
                Command helpCmd = new HelpCommand(commands.toArray(new Command[0]));
                commandsMap.put(helpCmd.getName(), helpCmd);
            } catch (IOException e) {
                System.out.println("Cannot read from file with given name -> Exit!");
                System.exit(1);
            }
        } else {
            System.out.println("WARNING: No plugins available!");
        }

        String lineInput;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                lineInput = br.readLine();
                String cmdString = lineInput.trim();

                if ("exit".equals(cmdString)) {
                    System.exit(0);
                }

                String[] splitCmd = cmdString.split(" ");
                Command command = commandsMap.get(splitCmd[0]);
                if (command == null) {
                    System.out.println("No such command!");
                } else {
                    String[] params = Arrays.copyOfRange(splitCmd, 1, splitCmd.length);
                    System.out.println(command.execute(params));
                }
            } catch (IOException e) {
                System.out.println("WARNING: Cannot read from the input -> exiting");
                System.exit(1);
            }

        }
    }
}
