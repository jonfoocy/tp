package seedu.medmoriser.model;

import javafx.collections.ObservableList;
import seedu.medmoriser.model.person.Person;

/**
 * Unmodifiable view of a question bank
 */
public interface ReadOnlyMedmoriser {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
