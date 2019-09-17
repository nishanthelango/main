import duke.dukeexception.DukeException;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class DetectAnomaliesTest {

    List<String> getList(String input) {
        return List.of(input.split(" "));
    }

    @Test
    void startClashes_clash_exceptionThrown() throws DukeException {
        TaskList taskList = new TaskList();
        Task task = new Event(getList("Event /at 12/12/2020 1800 to 12/12/2020 1900"));
        taskList.add(task);
        Event eventToAdd = new Event(getList("Event /at 12/12/2020 1830 to 12/12/2020 1930"));
        assertThrows(DukeException.class, () -> {
            Event.checkClash(eventToAdd,taskList);
        });
    }

    @Test
    void endClashes_clash_exceptionThrown() throws DukeException {
        TaskList taskList = new TaskList();
        Task task = new Event(getList("Event /at 12/12/2020 1800 to 12/12/2020 1900"));
        taskList.add(task);
        Event eventToAdd = new Event(getList("Event /at 12/12/2020 1730 to 12/12/2020 1830"));
        assertThrows(DukeException.class, () -> {
            Event.checkClash(eventToAdd,taskList);
        });
    }

    @Test
    void entireEventClashes_clash_exceptionThrown() throws DukeException {
        TaskList taskList = new TaskList();
        Task task = new Event(getList("Event /at 12/12/2020 1800 to 12/12/2020 1900"));
        taskList.add(task);
        Event eventToAdd = new Event(getList("Event /at 12/12/2020 1730 to 12/12/2020 1930"));
        assertThrows(DukeException.class, () -> {
            Event.checkClash(eventToAdd,taskList);
        });
    }

    @Test
    void isStartOrEndEqual_clash_exceptionThrown() throws DukeException {
        TaskList taskList = new TaskList();
        Task task = new Event(getList("Event /at 12/12/2020 1800 to 12/12/2020 1900"));
        taskList.add(task);
        Event eventToAdd = new Event(getList("Event /at 12/12/2020 1800 to 12/12/2020 1830"));
        assertThrows(DukeException.class, () -> {
            Event.checkClash(eventToAdd,taskList);
        });
    }
}