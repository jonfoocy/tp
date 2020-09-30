package seedu.medmoriser.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.medmoriser.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.commons.exceptions.IllegalValueException;
import seedu.medmoriser.commons.util.JsonUtil;
import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.testutil.TypicalPersons;

public class JsonSerializableMedmoriserTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalQuestionSetMedmoriser.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidQuestionSetMedmoriser.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicateQuestionSetMedmoriser.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableMedmoriser dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableMedmoriser.class).get();
        Medmoriser medmoriserFromFile = dataFromFile.toModelType();
        Medmoriser typicalPersonsMedmoriser = TypicalPersons.getTypicalMedmoriser();
        assertEquals(medmoriserFromFile, typicalPersonsMedmoriser);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMedmoriser dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableMedmoriser.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableMedmoriser dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableMedmoriser.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMedmoriser.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
