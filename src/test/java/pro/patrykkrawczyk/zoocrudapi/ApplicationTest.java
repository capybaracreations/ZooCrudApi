package pro.patrykkrawczyk.zoocrudapi;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class ApplicationTest {

    @Test
    public void exampleTest() {
        Assert.assertEquals(4, 2 + 2);
    }
}
