package duke.task;

import duke.dukeexception.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Event extends Task implements Snoozeable {
    private String description;
    private Date end;
    private Date start;
    private SimpleDateFormat formatter;

    /**
     * Create an event task from user input.
     *
     * @param input tokenized user input
     * @throws DukeException error if user input is invalid
     */
    public Event(List<String> input) throws DukeException {
        int separatorIndex = input.indexOf("/at");
        if (input.size() == 0 || separatorIndex <= 0) {
            throw new DukeException("Format for event: event <event> /at <start datetime> to <end> datetime");
        }

        formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        formatter.setLenient(false);

        try {
            this.description = String.join(" ", input.subList(0, separatorIndex));
            String strStart = String.join(" ", input.subList(separatorIndex + 1, separatorIndex + 3));
            String strEnd = String.join(" ", input.subList(separatorIndex + 4, separatorIndex + 6));
            this.start = formatter.parse(strStart);
            this.end = formatter.parse(strEnd);

        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Format for event: event <event> /at <start datetime> to <end datetime>");
        } catch (ParseException e) {
            throw new DukeException("Invalid datetime. Correct format: dd/mm/yyyy hhmm");
        }
    }

    /**
     * Checks if the event being added clashes with any existing events.
     *
     * @param eventToAdd the event task to be added
     * @param taskList List of tasks
     * @throws DukeException error if there is a clash
     */
    public static void checkClash(Event eventToAdd, TaskList taskList) throws DukeException {
        for (Task task : taskList.getTasks()) {
            if (task instanceof Event) {
                if (startClashes(eventToAdd, task) || endClashes(eventToAdd, task)
                        || entireEventClashes(eventToAdd, task) || isStartOrEndEqual(eventToAdd, task)) {
                    throw new DukeException("Clashes with following event:\n" + "\t" + task.toString());
                }
            }
        }
    }

    private static boolean startClashes(Event eventToAdd, Task task) {
        return eventToAdd.start.after(((Event) task).start) && eventToAdd.start.before(((Event) task).end);
    }

    private static boolean endClashes(Event eventToAdd, Task task) {
        return eventToAdd.end.after(((Event) task).start) && eventToAdd.end.before(((Event) task).end);
    }

    private static boolean entireEventClashes(Event eventToAdd, Task task) {
        return eventToAdd.start.before(((Event) task).start) && eventToAdd.end.after(((Event) task).start);
    }

    private static boolean isStartOrEndEqual(Event eventToAdd, Task task) {
        return eventToAdd.start.equals(((Event) task).start) || eventToAdd.end.equals(((Event) task).end);
    }

    @Override
    public boolean containsKeyword(String keyword) {
        return this.description.contains(keyword);
    }

    @Override
    public void snooze() {
        Calendar date = Calendar.getInstance();

        date.setTime(start);
        date.add(Calendar.DAY_OF_MONTH, 7);
        start.setTime(date.getTimeInMillis());

        date.setTime(end);
        date.add(Calendar.DAY_OF_MONTH, 7);
        end.setTime(date.getTimeInMillis());
    }

    @Override
    public String toString() {
        return String.format("[E]%s %s (at: %s to %s)", super.toString(), this.description,
                formatter.format(this.start), formatter.format(this.end));
    }
}
