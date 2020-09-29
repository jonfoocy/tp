## User Guide

Medmoriser is a **desktop** app to help medical students memorise and revise their content. It is optimized for
 CLI users so that frequent revisions can be done faster by typing in commands.


## Features

### Adding a person: `add`

Adds a question and answer pair to the database.

Format: `add q/QUESTION a/ANSWER`

Examples:
* `add q/what organ system is the lungs part of? a/respiratory system`
* `add q/what is the function of the heart? a/It's the muscle at the centre of your circulation system, pumping blood around your body as your heart beats. This blood sends oxygen and nutrients to all parts of your body, and carries away unwanted carbon dioxide and waste products.`

### Listing all Questions & Answers : `list`

Shows the entire database of question and answer pairs in the database.

Format: `list`


### Editing a Q&A : `edit` [Coming Soon]

Edits an existing Q&A in the Medmoriser application.

### Locating questions by keywords: `find` [Coming Soon]

Finds Q&A whose questions contain any of the given keywords.

### Deleting a Q&A : `delete`

Deletes the specified Q&A from the question book.

Format: `delete INDEX`

* Deletes the Q&A at the specified `INDEX`.
* The index refers to the index number shown in the displayed question list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:
* `list` followed by `delete 2` deletes the 2nd Q&A in the question book.
* `find disease` followed by `delete 1` deletes the 1st question in the results of the `find` command.

### Viewing help : `help` [Coming soon]

=======

### Viewing help : `help` [Coming soon]

Shows a message explaning how to access the help page.

Format: `help`

### Clearing all entries : `clear`

Clears all entries from the question book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

Medmoriser data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Archiving data files [Coming Soon]

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------


## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Delete** | `delete INDEX`<br> e.g., `delete 3`

**Edit** | Coming Soon
**Find** | Coming Soon

**List** | `list`

