package seedu.medmoriser.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.medmoriser.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.medmoriser.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.medmoriser.commons.core.index.Index;
import seedu.medmoriser.logic.commands.EditCommand;
import seedu.medmoriser.logic.parser.exceptions.ParseException;
import seedu.medmoriser.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUESTION, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ANSWER, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditQuestionSetDescriptor editQuestionSetDescriptor = new EditCommand.EditQuestionSetDescriptor();
        if (argMultimap.getValue(PREFIX_QUESTION).isPresent()) {
            editQuestionSetDescriptor.setQuestion(ParserUtil.parseQuestion(argMultimap
                    .getValue(PREFIX_QUESTION).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editQuestionSetDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editQuestionSetDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ANSWER).isPresent()) {
            editQuestionSetDescriptor.setAnswer(ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editQuestionSetDescriptor::setTags);

        if (!editQuestionSetDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editQuestionSetDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
