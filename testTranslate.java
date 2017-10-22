import static org.junit.Assert.*;

import org.junit.Test;

public class testTranslate {

	@Test
	public void test() {
		assertEquals(Main.writeDeclaration("i","alpha"), "var i:String");
		assertEquals(Main.writeNewAssignment("x", "int", "10"), "var x:Int = 10");
	}

}
