import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;


public class PosticksTest {

    @Before
    public void setup() {
        //Fixtures.deleteAll();
    }

	@Test
    public void createPostick() {
        // Create a new user and save it
        User fahied = new User("fahied", "Passowrd").save();

        // Create a new Scene
        Scene simmulation1 = new Scene(fahied,"Simmulation1", "Hello guys").save();

        // Post a first Postick
        new Postick(simmulation1, "fahied", "This is my Frist postick", 90.0f, 20.0f).save();
        new Postick(simmulation1, "fahied", "This is my 2nd postick", 90.0f, 20.0f).save();

        // Retrieve all Posticks
        List<Postick> fahiedPosticks = Postick.find("byScene", simmulation1).fetch();

        // Tests
        assertEquals(2, fahiedPosticks.size());

        Postick firstPostick = fahiedPosticks.get(0);
        assertNotNull(firstPostick);
        assertEquals("fahied", firstPostick.author);
        assertEquals("This is my Frist postick", firstPostick.content);
        assertNotNull(firstPostick.postedAt);

        Postick secondPostick = fahiedPosticks.get(0);
        assertNotNull(secondPostick);
        assertEquals("fahied", firstPostick.author);
        assertEquals("This is my Frist postick", secondPostick.content);
        assertNotNull(secondPostick.postedAt);
    }

}
