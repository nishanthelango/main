package logic.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.ReminderCommand;
import duchess.model.task.DuchessLog;
import duchess.parser.Parser;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReminderCommandTest {

    @Test
    public void testExecuteWithoutDeadlines() throws DuchessException {
        String filename = "text_file";
        Store store = new Store();
        Ui ui = new Ui();
        Storage storage = new Storage(filename);

        assertTrue(store.getTaskList().size() == 0);
        ReminderCommand reminderCommand = new ReminderCommand();
        reminderCommand.execute(store, ui, storage);

        assertTrue(store.getTaskList().size() == 0);
    }

    @Test
    public void testExecuteWithDeadlines() throws DuchessException {
        String firstDeadline = "deadline homework assignment /by 23/12/2019 1800";
        String secondDeadline = "deadline school project tasks /by 01/11/2020 0900";

        String filename = "text_file";
        Store store = new Store();
        Ui ui = new Ui();
        Storage storage = new Storage(filename);
        DuchessLog duchessLog = new DuchessLog();

        // Adding the firstDeadline
        Parser.parse(firstDeadline).execute(store, ui, storage);

        // Adding the secondDeadline
        Parser.parse(secondDeadline).execute(store, ui, storage);

        assertTrue(store.getTaskList().size() == 2);
        ReminderCommand reminderCommand = new ReminderCommand();
    }
}