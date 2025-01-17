package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Command to search for given task.
 */
public class FindCommand extends Command {
    private static final String ENTER_KEYWORD_PROMPT = "Please enter at least a keyword to search.";
    private static final String NO_MATCHING_TASKS_MSG = "There are no matching tasks.";
    private List<String> words;

    /**
     * Returns the length of the longest common subsequence (lcs) of 2 Strings.
     *
     * @param a the first String
     * @param b the second String
     * @return the length of the longest common subsequence
     */
    private int longestCommonSubsequence(String a, String b) {
        int[][] table = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i < table.length - 1; i++) {
            for (int j = 0; j < table[0].length - 1; j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    table[i + 1][j + 1] = table[i][j] + 1;
                } else {
                    table[i + 1][j + 1] = Math.max(table[i][j + 1], table[i + 1][j]);
                }
            }
        }
        return table[a.length()][b.length()];
    }

    public FindCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        if (words.isEmpty()) {
            throw new DuchessException(ENTER_KEYWORD_PROMPT);
        } else {
            String searchTerm = String.join(" ", words.subList(0, words.size())).toLowerCase();
            List<Task> filteredTasks;
            /*
            If search term is enclosed by "", search for exact matches.
             */
            if (!"\"".equals(searchTerm) && searchTerm.charAt(0) == '"'
                    && searchTerm.charAt(searchTerm.length() - 1) == '"') {
                filteredTasks = store
                        .getTaskList()
                        .stream()
                        .filter(task -> task.getDescription()
                                .equals(searchTerm.substring(1, searchTerm.length() - 1)))
                        .collect(Collectors.toList());
            /*
            Search for task descriptions with lcs of length equal to at least 2 less than the length of the search term.
             */
            } else {
                String trimmedSearchTerm = searchTerm.replaceAll(" ", "");
                filteredTasks = store
                        .getTaskList().stream()
                        .filter(task -> longestCommonSubsequence(task.getDescription().toLowerCase()
                                .replaceAll(" ", ""), trimmedSearchTerm)
                                >= trimmedSearchTerm.length() - 2)
                        .collect(Collectors.toList());

                /*
                Checks if the task description contains the exact search term
                if the search term is too short (less than 3 chars).
                 */
                if (trimmedSearchTerm.length() <= 2) {
                    filteredTasks = filteredTasks.stream().filter(task -> task.getDescription().toLowerCase()
                            .contains(trimmedSearchTerm)).collect(Collectors.toList());
                }
                /*
                Sort the filtered tasks based on similarity to searchTerm.
                 */
                filteredTasks.sort(Comparator
                        .comparingInt(task -> -longestCommonSubsequence(task.getDescription().toLowerCase()
                                .replaceAll(" ", ""), trimmedSearchTerm)));
            }

            if (filteredTasks.isEmpty()) {
                throw new DuchessException(NO_MATCHING_TASKS_MSG);
            } else {
                ui.showSearchResult(filteredTasks);
            }
        }
    }
}
