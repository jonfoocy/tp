package seedu.medmoriser.testutil;

import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Medmoriser medmoriser;

    public AddressBookBuilder() {
        medmoriser = new Medmoriser();
    }

    public AddressBookBuilder(Medmoriser medmoriser) {
        this.medmoriser = medmoriser;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        medmoriser.addPerson(person);
        return this;
    }

    public Medmoriser build() {
        return medmoriser;
    }
}
