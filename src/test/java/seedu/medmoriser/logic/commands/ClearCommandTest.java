package seedu.medmoriser.logic.commands;

import static seedu.medmoriser.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.medmoriser.testutil.TypicalPersons.getTypicalMedmoriser;

import org.junit.jupiter.api.Test;

import seedu.medmoriser.model.Medmoriser;
import seedu.medmoriser.model.Model;
import seedu.medmoriser.model.ModelManager;
import seedu.medmoriser.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyMedmoriser_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyMedmoriser_success() {
        Model model = new ModelManager(getTypicalMedmoriser(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalMedmoriser(), new UserPrefs());
        expectedModel.setMedmoriser(new Medmoriser());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
