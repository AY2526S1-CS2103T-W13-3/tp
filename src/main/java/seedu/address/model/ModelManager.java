package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook archive;
    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Person> filteredArchivedPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, ReadOnlyAddressBook archive) {
        requireAllNonNull(addressBook, userPrefs, archive);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.archive = new AddressBook(archive);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredArchivedPersons = new FilteredList<>(this.archive.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new AddressBook());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public Path getArchiveFilePath() {
        return userPrefs.getArchiveFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public void setArchiveFilePath(Path archiveFilePath) {
        requireNonNull(archiveFilePath);
        userPrefs.setArchiveFilePath(archiveFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public void setArchive(ReadOnlyAddressBook addressBook) {
        this.archive.resetData(addressBook);
        updateFilteredArchivedPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public ReadOnlyAddressBook getArchive() {
        return archive;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person) || archive.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addArchivedPersons(List<Person> persons) {
        archive.addPersons(persons);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        updateFilteredArchivedPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addPersons(List<Person> persons) {
        addressBook.addPersons(persons);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * the archive
     */
    @Override
    public ObservableList<Person> getFilteredArchivedPersonList() {
        return filteredArchivedPersons;
    }

    @Override
    public void updateFilteredArchivedPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredArchivedPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && archive.equals(otherModelManager.archive)
                && filteredArchivedPersons.equals(otherModelManager.filteredArchivedPersons);
    }

}
