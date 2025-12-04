import org.junit.*;

public class DefaultTest {
    @Test
    public void messageTest()
    {
        Default def = new Default();
        Assert.assertEquals("Hi!", def.message());
    }
}