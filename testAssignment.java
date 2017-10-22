import static org.junit.Assert.*;

import org.junit.Test;

public class testAssignment {

	@Test
	public void test() {
		assertTrue(Main.checkAssignment("i=10;"));
		assertFalse(Main.checkAssignment("var i:int"));
	}

}
