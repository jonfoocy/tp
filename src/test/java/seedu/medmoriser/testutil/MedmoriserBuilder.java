package seedu.medmoriser.testutil;

import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.person.Person;

/**
 * A utility class to help with building Medmoriser objects.
 * Example usage: <br>
 *     {@code Medmoriser md = new MedmoriserBuilder().withPerson("John", "Doe").build();}
 */
public class MedmoriserBuilder {

    private Medmoriser medmoriser;

    public MedmoriserBuilder() {
        medmoriser = new Medmoriser();
    }

    public MedmoriserBuilder(Medmoriser medmoriser) {
        this.medmoriser = medmoriser;
    }

    /**
     * Adds a new {@code Person} to the {@code Medmoriser} that we are building.
     */
    public MedmoriserBuilder withPerson(Person person) {
        medmoriser.addPerson(person);
        return this;
    }

    public Medmoriser build() {
        return medmoriser;
    }
}
