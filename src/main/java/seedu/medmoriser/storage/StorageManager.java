package seedu.medmoriser.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.medmoriser.commons.core.LogsCenter;
import seedu.medmoriser.commons.exceptions.DataConversionException;
import seedu.medmoriser.model.ReadOnlyMedmoriser;
import seedu.medmoriser.model.ReadOnlyUserPrefs;
import seedu.medmoriser.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private MedmoriserStorage medmoriserStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(MedmoriserStorage medmoriserStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.medmoriserStorage = medmoriserStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getMedmoriserFilePath() {
        return medmoriserStorage.getMedmoriserFilePath();
    }

    @Override
    public Optional<ReadOnlyMedmoriser> readMedmoriser() throws DataConversionException, IOException {
        return readMedmoriser(medmoriserStorage.getMedmoriserFilePath());
    }

    @Override
    public Optional<ReadOnlyMedmoriser> readMedmoriser(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return medmoriserStorage.readMedmoriser(filePath);
    }

    @Override
    public void saveMedmoriser(ReadOnlyMedmoriser medmoriser) throws IOException {
        saveMedmoriser(medmoriser, medmoriserStorage.getMedmoriserFilePath());
    }

    @Override
    public void saveMedmoriser(ReadOnlyMedmoriser addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        medmoriserStorage.saveMedmoriser(addressBook, filePath);
    }

}
