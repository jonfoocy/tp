package seedu.medmoriser.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.medmoriser.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalPersons.ALICE;
import static seedu.medmoriser.testutil.TypicalPersons.getTypicalMedmoriser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.medmoriser.model.person.Person;
import seedu.medmoriser.model.person.exceptions.DuplicatePersonException;
import seedu.medmoriser.testutil.PersonBuilder;

public class MedmoriserTest {

    private final Medmoriser medmoriser = new Medmoriser();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), medmoriser.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> medmoriser.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMedmoriser_replacesData() {
        Medmoriser newData = getTypicalMedmoriser();
        medmoriser.resetData(newData);
        assertEquals(newData, medmoriser);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        MedmoriserStub newData = new MedmoriserStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> medmoriser.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> medmoriser.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInMedmoriser_returnsFalse() {
        assertFalse(medmoriser.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInMedmoriser_returnsTrue() {
        medmoriser.addPerson(ALICE);
        assertTrue(medmoriser.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInMedmoriser_returnsTrue() {
        medmoriser.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(medmoriser.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> medmoriser.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class MedmoriserStub implements ReadOnlyMedmoriser {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        MedmoriserStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
