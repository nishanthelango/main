package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.log.Log;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Undo feature.
 */
public class UndoCommand extends Command {
    private final Logger logger;
    private int undoCounter;
    private static final String UNDO_USAGE_ERROR_MESSAGE = "Usage: undo [number]";
    private static final String NEGATIVE_NUMBER_ERROR_MESSAGE
            = "[number] must be a positive integer, i.e. 1, 2, 3, ...";
    private static final String INVALID_NUMBER_ERROR_MESSAGE
            = "You have entered an invalid value.";

    /**
     * Checks if undo command contains additional parameters.
     *
     * @param words additional parameters for undo
     * @throws DuchessException throws exceptions if invalid command
     */
    public UndoCommand(List<String> words) throws DuchessException {
        this.logger = Log.getLogger();
        if (words.size() != 1 && words.size() != 0) {
            throw new DuchessException(UNDO_USAGE_ERROR_MESSAGE);
        } else if (words.size() == 1) {
            try {
                undoCounter = Integer.parseInt(words.get(0));

                if (undoCounter <= 0) {
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException e) {
                throw new DuchessException(INVALID_NUMBER_ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                throw new DuchessException(NEGATIVE_NUMBER_ERROR_MESSAGE);
            }

        } else if (words.size() == 0) {
            undoCounter = 1;
        }
    }

    /**
     * Restores previous available state.
     *
     * @param store store object
     * @param ui user interaction object
     * @param storage storage object
     * @throws DuchessException if undo is unsuccessful
     */
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        logger.log(Level.INFO, "Undo is executed.");

        if (storage.getUndoStack().size() == 1) {
            undoCounter = 0;
        } else if (storage.getUndoStack().size() > 1 && undoCounter > 1) {
            storage.addToRedoStack();
            int tempCounter = undoCounter;
            while (tempCounter > 0 && storage.getUndoStack().size() > 1) {
                setToPreviousStore(store, storage);
                tempCounter--;
            }
        } else if (storage.getUndoStack().size() > 1 && undoCounter == 1) {
            storage.addToRedoStack();
            setToPreviousStore(store, storage);
        }
        ui.showUndo(undoCounter);
    }

    /**
     * Updates data to previous Store values.
     *
     * @param store store object
     * @param storage storage object
     * @throws DuchessException if updating store is unsuccessful
     */
    private void setToPreviousStore(Store store, Storage storage) throws DuchessException {
        storage.getLastSnapshot();
        storage.save(storage.peekUndoStackAsStore());

        Store newStore = storage.load();
        assert (store.equals(newStore));
        store.setTaskList(newStore.getTaskList());
        store.setModuleList(newStore.getModuleList());
        store.setDuchessCalendar(newStore.getDuchessCalendar());
    }
}