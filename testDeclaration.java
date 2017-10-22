import static org.junit.Assert.*;

import org.junit.Test;

public class testDeclaration {

	@Test
	public void test() {
		assertTrue(Main.checkDeclaration("var i:int"));
		assertFalse(Main.checkDeclaration("var x:int=10;"));
	}
}
