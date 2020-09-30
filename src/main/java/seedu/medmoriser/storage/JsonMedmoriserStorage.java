package seedu.medmoriser.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.medmoriser.commons.core.LogsCenter;
import seedu.medmoriser.commons.exceptions.DataConversionException;
import seedu.medmoriser.commons.exceptions.IllegalValueException;
import seedu.medmoriser.commons.util.FileUtil;
import seedu.medmoriser.commons.util.JsonUtil;
import seedu.medmoriser.model.ReadOnlyMedmoriser;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonMedmoriserStorage implements MedmoriserStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMedmoriserStorage.class);

    private Path filePath;

    public JsonMedmoriserStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getMedmoriserFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMedmoriser> readMedmoriser() throws DataConversionException {
        return readMedmoriser(filePath);
    }

    /**
     * Similar to {@link #readMedmoriser()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMedmoriser> readMedmoriser(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableMedmoriser> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableMedmoriser.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveMedmoriser(ReadOnlyMedmoriser medmoriser) throws IOException {
        saveMedmoriser(medmoriser, filePath);
    }

    /**
     * Similar to {@link #saveMedmoriser(ReadOnlyMedmoriser)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveMedmoriser(ReadOnlyMedmoriser addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableMedmoriser(addressBook), filePath);
    }

}
