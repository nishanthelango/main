package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Module;
import duchess.model.task.Deadline;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;
import java.util.Optional;

public class AddDeadlineCommand extends Command {
    /** List containing String objects. */
    private List<String> words;
    private String moduleCode;

    public AddDeadlineCommand(List<String> words) {
        this.words = words;
    }

    public AddDeadlineCommand(List<String> words, String moduleCode) {
        this.words = words;
        this.moduleCode = moduleCode;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Task task;
        if (moduleCode != null) {
            task = new Deadline(words);
            Optional<Module> module = store.findModuleByCode(moduleCode);
            task.setModule(module.orElseThrow(() ->
                    new DuchessException("Unable to find given module.")
            ));
        } else {
            task = new Deadline(words);
        }
        store.getTaskList().add(task);
        ui.showTaskAdded(store.getTaskList(), task);
        storage.save(store);
    }
}
