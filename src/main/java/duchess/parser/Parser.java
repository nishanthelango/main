package duchess.parser;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.*;

import java.util.Arrays;
import java.util.List;

public class Parser {
    /**
     * Returns the command to execute after parsing user input.
     *
     * @param input the user input
     * @return the command to execute
     * @throws DuchessException the exception if user input is invalid
     */
    public static Command parse(String input) throws DuchessException {
        List<String> words = Arrays.asList(input.split(" "));
        String keyword = words.get(0);
        List<String> arguments = words.subList(1, words.size());

        // The entire parser is to be refactored
        // when implementing interactive commands.
        switch (keyword) {
        case "list":
            try {
                String secondKeyword = words.get(1);
                switch (secondKeyword) {
                case "tasks":
                    return new ListTasksCommand();
                case "modules":
                    return new ListModulesCommand();
                default:
                    throw new IllegalArgumentException();
                }
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                throw new DuchessException("Usage: list (tasks | modules)");
            }
        case "add":
            try {
                String secondKeyword = words.get(1);
                switch (secondKeyword) {
                case "module":
                    return new AddModuleCommand(words.subList(2, words.size()));
                default:
                    throw new IllegalArgumentException();
                }
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                throw new DuchessException("Usage: add module <module-code> <module-name>");
            }
        case "find":
            return new FindCommand(arguments);
        case "delete":
            try {
                String secondKeyword = words.get(1);
                switch (secondKeyword) {
                case "task":
                    return new DeleteTaskCommand(arguments);
                case "module":
                    return new DeleteModuleCommand(arguments);
                default:
                    throw new IllegalArgumentException();
                }
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                throw new DuchessException("Usage: delete (module|task) <number>");
            }
        case "done":
            try {
                return new DoneCommand(Integer.parseInt(words.get(0)) - 1);
            } catch (NumberFormatException e) {
                throw new DuchessException("Please supply a number. Eg: done 2");
            }
        case "todo":
            if (arguments.get(arguments.size() - 1).charAt(0) == '#') {
                return new AddTodoCommand(arguments.subList(0, arguments.size() - 1),
                        arguments.get(arguments.size() - 1).substring(1));
            } else {
                return new AddTodoCommand(arguments.subList(0, arguments.size()));
            }
        case "deadline":
            if (arguments.get(arguments.size() - 1).charAt(0) == '#') {
                return new AddDeadlineCommand(arguments.subList(0, arguments.size() - 1),
                        arguments.get(arguments.size() - 1).substring(1));
            } else {
                return new AddTodoCommand(arguments.subList(0, arguments.size()));
            }
        case "event":
            return new AddEventCommand(arguments);
        case "reminder":
            return new ReminderCommand();
        case "snooze":
            return new SnoozeCommand(arguments);
        case "schedule":
            try {
                String view = words.get(2);
                boolean isInvalidView = !view.equals("day") && !view.equals("week");
                boolean isIllegalArgument = isInvalidView && (words.size() > 3);
                if (isIllegalArgument) {
                    throw new IllegalArgumentException();
                }
                String date = words.get(1);
                return new ViewScheduleCommand(date, view);
            } catch (IndexOutOfBoundsException e) {
                throw new DuchessException("Usage: schedule <date> (day | week)");
            }
        case "bye":
            return new ByeCommand();
        case "log":
            return new LogCommand();
        case "undo":
            return new UndoCommand(arguments);
        default:
            throw new DuchessException("Please enter a valid command.");
        }
    }
}
