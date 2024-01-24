package unittest;

import org.junit.jupiter.api.Test;

public class StringFormatTest {

	@Test
	public void stringFormat() {
		String khabar = "Vaat";
		int times = 3;
		System.err.println("Ami " + khabar + " Khai " + times + " bela");
		System.err.println(String.format("Ami %s khai %d bela", khabar, times));
		//khabar.concat(String.valueOf(times)).concat(khabar);
	}
}
