package seedu.medmoriser.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.medmoriser.commons.exceptions.DataConversionException;
import seedu.medmoriser.model.ReadOnlyMedmoriser;
import seedu.medmoriser.model.ReadOnlyUserPrefs;
import seedu.medmoriser.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends MedmoriserStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getMedmoriserFilePath();

    @Override
    Optional<ReadOnlyMedmoriser> readMedmoriser() throws DataConversionException, IOException;

    @Override
    void saveMedmoriser(ReadOnlyMedmoriser medmoriser) throws IOException;

}
