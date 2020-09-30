package seedu.medmoriser.model.questionset;

import static java.util.Objects.requireNonNull;
import static seedu.medmoriser.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.medmoriser.model.questionset.exceptions.DuplicateQuestionSetException;
import seedu.medmoriser.model.questionset.exceptions.QuestionSetNotFoundException;

/**
 * A list of questionSets that enforces uniqueness between its elements and does not allow nulls.
 * A questionSet is considered unique by comparing using {@code QuestionSet#isSameQuestionSet(QuestionSet)}.
 * As such, adding and updating of questionSets uses QuestionSet#isSameQuestionSet(QuestionSet)
 * for equality so as to ensure that the questionSet being added or updated is
 * unique in terms of identity in the UniqueQuestionSetList. However, the removal
 * of a questionSet uses QuestionSet#equals(Object) so as to ensure that the questionSet
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see QuestionSet#isSameQuestionSet(QuestionSet)
 */
public class UniqueQuestionSetList implements Iterable<QuestionSet> {

    private final ObservableList<QuestionSet> internalList = FXCollections.observableArrayList();
    private final ObservableList<QuestionSet> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent questionSet as the given argument.
     */
    public boolean contains(QuestionSet toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameQuestionSet);
    }

    /**
     * Adds a questionSet to the list.
     * The questionSet must not already exist in the list.
     */
    public void add(QuestionSet toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateQuestionSetException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the questionSet {@code target} in the list with {@code editedQuestionSet}.
     * {@code target} must exist in the list.
     * The questionSet identity of {@code editedQuestionSet} must not be the same as another existing
     * questionSet in the list.
     */
    public void setQuestionSet(QuestionSet target, QuestionSet editedQuestionSet) {
        requireAllNonNull(target, editedQuestionSet);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new QuestionSetNotFoundException();
        }

        if (!target.isSameQuestionSet(editedQuestionSet) && contains(editedQuestionSet)) {
            throw new DuplicateQuestionSetException();
        }

        internalList.set(index, editedQuestionSet);
    }

    /**
     * Removes the equivalent questionSet from the list.
     * The questionSet must exist in the list.
     */
    public void remove(QuestionSet toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new QuestionSetNotFoundException();
        }
    }

    public void setQuestionSets(UniqueQuestionSetList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code questionSets}.
     * {@code questionSets} must not contain duplicate questionSets.
     */
    public void setQuestionSets(List<QuestionSet> questionSets) {
        requireAllNonNull(questionSets);
        if (!questionSetsAreUnique(questionSets)) {
            throw new DuplicateQuestionSetException();
        }

        internalList.setAll(questionSets);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<QuestionSet> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<QuestionSet> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueQuestionSetList // instanceof handles nulls
                        && internalList.equals(((UniqueQuestionSetList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code questionSets} contains only unique questionSet.
     */
    private boolean questionSetsAreUnique(List<QuestionSet> questionSets) {
        for (int i = 0; i < questionSets.size() - 1; i++) {
            for (int j = i + 1; j < questionSets.size(); j++) {
                if (questionSets.get(i).isSameQuestionSet(questionSets.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}