package duke.command;

import duke.dukeexception.DukeException;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;

import java.util.List;

public class AddEventCommand extends Command {
    private List<String> words;

    public AddEventCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        Task task = new Event(words.subList(0, words.size()));
        try {
            Event.checkClash((Event) task, taskList);
            taskList.add(task);
            ui.showTaskAdded(taskList.getTasks(), task);
            storage.save(taskList);
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }
    }
}