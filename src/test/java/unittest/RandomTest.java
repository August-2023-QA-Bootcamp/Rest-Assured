package unittest;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class RandomTest {

	@Test
	public void randomMathTest() {
		
		System.err.println(new Random().nextInt(3));
	}
}
