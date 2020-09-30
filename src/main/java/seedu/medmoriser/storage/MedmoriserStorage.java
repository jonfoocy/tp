package seedu.medmoriser.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.medmoriser.commons.exceptions.DataConversionException;
import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.ReadOnlyMedmoriser;

/**
 * Represents a storage for {@link Medmoriser}.
 */
public interface MedmoriserStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMedmoriserFilePath();

    /**
     * Returns Medmoriser data as a {@link ReadOnlyMedmoriser}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMedmoriser> readMedmoriser() throws DataConversionException, IOException;

    /**
     * @see #getMedmoriserFilePath()
     */
    Optional<ReadOnlyMedmoriser> readMedmoriser(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMedmoriser} to the storage.
     * @param medmoriser cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMedmoriser(ReadOnlyMedmoriser medmoriser) throws IOException;

    /**
     * @see #saveMedmoriser(ReadOnlyMedmoriser)
     */
    void saveMedmoriser(ReadOnlyMedmoriser addressBook, Path filePath) throws IOException;

}
