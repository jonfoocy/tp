package seedu.medmoriser.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_QUESTIONSET_DISPLAYED_INDEX;
import static seedu.medmoriser.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.medmoriser.logic.commands.CommandTestUtil.ANSWER_DESC_AMY;
import static seedu.medmoriser.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.medmoriser.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.medmoriser.logic.commands.CommandTestUtil.QUESTION_DESC_AMY;
import static seedu.medmoriser.testutil.Assert.assertThrows;
import static seedu.medmoriser.testutil.TypicalQuestionSet.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.medmoriser.logic.commands.AddCommand;
import seedu.medmoriser.logic.commands.CommandResult;
import seedu.medmoriser.logic.commands.ListCommand;
import seedu.medmoriser.logic.commands.exceptions.CommandException;
import seedu.medmoriser.logic.parser.exceptions.ParseException;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ModelManager;
import seedu.medmoriser.model.ReadOnlyMedmoriser;
import seedu.medmoriser.model.UserPrefs;
import seedu.medmoriser.model.questionset.QuestionSet;
import seedu.medmoriser.storage.JsonMedmoriserStorage;
import seedu.medmoriser.storage.JsonUserPrefsStorage;
import seedu.medmoriser.storage.StorageManager;
import seedu.medmoriser.testutil.QuestionSetBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonMedmoriserStorage medmoriserStorage =
                new JsonMedmoriserStorage(temporaryFolder.resolve("medmoriser.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(medmoriserStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_QUESTIONSET_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonMedmoriserStorage medmoriserStorage =
                new JsonMedmoriserIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionMedmoriser.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(medmoriserStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + QUESTION_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ANSWER_DESC_AMY;
        QuestionSet expectedQuestionSet = new QuestionSetBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addQuestionSet(expectedQuestionSet);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredQuestionSetList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredQuestionSetList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getMedmoriser(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonMedmoriserIoExceptionThrowingStub extends JsonMedmoriserStorage {
        private JsonMedmoriserIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveMedmoriser(ReadOnlyMedmoriser medmoriser, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
