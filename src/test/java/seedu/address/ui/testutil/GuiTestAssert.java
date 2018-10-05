package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.person.Person;
import seedu.address.ui.PersonCard;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    private static final String LABEL_DEFAULT_STYLE = "label";
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAttendance(), actualCard.getAttendance());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());

        expectedCard.getTags().forEach(tag ->
                assertEquals(expectedCard.getTagStyleClasses(tag), actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAttendance().attendanceValue, actualCard.getAttendance());
        assertTagsEqual(expectedPerson, actualCard);
    }

    //@@author aaryamNUS
    /**
     * Returns the color style for {@code tagName}'s label. The tag's color is determined by looking up the color
     * in {@code PersonCard#TAG_COLORS}, using an index generated by the hash code of the tag's content.
     *
     * @see PersonCard#getTagColor(String)
     *
     * Note: This code was adapted from the example implementation provide by @yamgent from SE-EDU
     */

    private static String getTagColor(String tagName) {
        switch (tagName) {
        case "vegetarian":
            return "green";
        case "absent":
            return "orange";
        case "present":
            return "yellow";
        case "vip":
            return "lightblue";
        case "bride":
        case "groom":
            return "red";
        case "Guest Speaker":
            return "white";
        case "not paid":
            return "black";
        case "paid":
            return "purple";
        default:
            throw new AssertionError(tagName + "does not have a color assigned.");
        }
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedPerson} with the
     * correct color.
     *
     * Note: This code was adapted from the example implementation provide by @yamgent from SE-EDU
     */

    private static void assertTagsEqual(Person expectedPerson, PersonCardHandle actualCard) {
        List<String> expectedTags = expectedPerson.getTags().stream().map(tag ->
                tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getTagColor(tag)), actualCard.getTagStyleClasses(tag)));
    }
    //@@author


    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
