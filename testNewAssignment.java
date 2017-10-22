import static org.junit.Assert.*;

import org.junit.Test;

public class testNewAssignment {

	@Test
	public void test() {
		assertTrue(Main.checkNewAssignment("var i:int=10;"));
		assertFalse(Main.checkNewAssignment("var i:int"));
	}

}
