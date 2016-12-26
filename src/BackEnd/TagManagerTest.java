package BackEnd;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.AfterClass;

public class TagManagerTest {
	// Keep track of all the elements added during the testing
	private static List<String> extraTags = new ArrayList<String>();


	@AfterClass
	public static void tearDownAfterClass(){
		// Remove the tags added for testing
		// purposes, after the testing is done
		for(String element : extraTags){
			TagManager.getInstance().deleteTag(element);
		}
		TagManager.getInstance().writeToFile();
	}


	/**
	 * Test the TagManager.addTag method by adding one tag
	 */
	@Test
	public void testAddTagSingleTag(){
		TagManager.getInstance().addTag("@TestCase1Tag");
		extraTags.add("@TestCase1Tag");

		// assert if added tag is contained in the image after adding it
		assertTrue(TagManager.getInstance().getAllTags().contains("@TestCase1Tag"));
	}


	/**
	 * Test the TagManager.addTag method by adding multiple
	 * tags and asserting if those tags are available
	 */
	@Test
	public void testAddTagMultipleTags(){
		// add 20 tags and assert if they are added to the list of tags
		for(int i = 2; i < 22; i++){
			TagManager.getInstance().addTag("@TestCase"+ i +"Tag");
			extraTags.add("@TestCase"+ i +"Tag");

			// assert if added tag is contained in the image after adding it
			assertTrue(TagManager.getInstance().getAllTags().contains("@TestCase" + i +"Tag"));
		}
	}


	/**
	 * Test the TagManager.addTag method by adding multiple
	 * tags that are same and asserting if all those tags
	 * are added or single tag is added
	 */
	@Test
	public void testAddTagSameMulTipleTags(){
		// add same tag 5 times and assert if it is added
		// 5 times or it's added single time
		extraTags.add("@TestCase_x_Tag");
		for(int i = 0; i < 5; i++){
			TagManager.getInstance().addTag("@TestCase_x_Tag");
		}
		// Assert if "@TestCasexTag" is contained in the list of tags
		assertTrue(TagManager.getInstance().getAllTags().contains("@TestCase_x_Tag"));
		// Assert if "@TestCasexTag" is added single time
		assertEquals(TagManager.getInstance().getAllTags().lastIndexOf("@TestCase_x_Tag"),
				         TagManager.getInstance().getAllTags().indexOf("@TestCase_x_Tag"));
	}


	/**
	 * Test the TagManager.DeleteTag method by deleting
	 *  one tag after is is already added
	 */
	@Test
	public void testDeleteTagSingleTag(){
		// remove the tag and and assert if the tag is removed
		TagManager.getInstance().deleteTag("@TestCase1Tag");
		extraTags.remove("@TestCase1Tag");
		assertFalse(TagManager.getInstance().getAllTags().contains("@TestCase1Tag"));
	}


	/**
	 * Test the TagManager.deleteTag method by deleting multiple
	 * tags already added during the testing of addTag and asserting
	 * if those tags are available
	 */
	@Test
	public void testDeleteTagMultipleTags(){
		// remove 20 tags added during the testing of add tag
		// and assert if they are added to the list of tags
		for(int i=2; i < 22; i++){
			TagManager.getInstance().deleteTag("@TestCase"+ i +"Tag");
			extraTags.remove("@TestCase"+ i +"Tag");

			// assert if added tag is contained in the image after adding it
			assertFalse(TagManager.getInstance().getAllTags().contains("@TestCase" + i +"Tag"));
		}
	}


	/**
	 *  Test the TagManager.deleteTag method by deleting
	 *  tag one that is added multiple times during
	 *  TagManager.deleteTag method test
	 */
	@Test
	public void testDeleteTagAddedMultipleTimes(){
		// delete the tag added multiple times
		TagManager.getInstance().deleteTag("@TestCase_x_Tag");
		extraTags.remove("@TestCase_x_Tag");

		// check if only one tag was added by asserting that
		// no tags with name "@TestCasexTag" are contained
		assertFalse(TagManager.getInstance().getAllTags().contains("@TestCase_x_Tag"));
	}


	/**
	 * read from the .ser file TagManager reads and writes
	 * from independently from TagManager and check assert
	 * if data read by TagManager class is equal
	 *
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testWriteAndReadFromFileOneElement() throws FileNotFoundException,
	ClassNotFoundException, IOException{

		// Add single tag and write it to the file and read all the tags
		TagManager.getInstance().addTag("@TestCase_y_Test");
		TagManagerTest.extraTags.add("@TestCase_y_Test");
		TagManager.getInstance().writeToFile();
		TagManager.getInstance().readFromFile();

		// get index of the last item in the list of all tags
		int lastIndex = TagManager.getInstance().getAllTags().size() - 1;
		assertEquals("@TestCase_y_Test",
					 TagManager.getInstance().getAllTags().get(lastIndex));
	}


	/**
	 * Initially adds 20 additional tags to list of all the
	 * tags in tagManager and writes, and read from the .ser file.
	 * Asserts if last 20 tags added are written and read properly
	 * such that 20 tags equal the expected values.
	 *
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testWriteAndReadFromFileMultipleElements() throws FileNotFoundException,
	ClassNotFoundException, IOException{
		// add 20 tags and then write and again read the tags
		for(int i = 0; i < 20; i++){
			TagManager.getInstance().addTag("@TestCase_z"+ i +"_Test");
			TagManagerTest.extraTags.add("@TestCase_z"+ i +"_Test");
		}
		TagManager.getInstance().writeToFile();
		TagManager.getInstance().readFromFile();

		// check the last 20 tags after writing and reading
		for(int i = 0; i < 20; i++){
			int testTagIndex = TagManager.getInstance().getAllTags().size() - 20 + i;
		assertEquals("@TestCase_z"+ i +"_Test",
					 TagManager.getInstance().getAllTags().get(testTagIndex));

		}
	}
}
