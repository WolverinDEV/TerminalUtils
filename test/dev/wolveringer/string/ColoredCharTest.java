package dev.wolveringer.string;

import org.junit.Assert;
import org.junit.Test;

import dev.wolveringer.terminal.string.ColoredString;

public class ColoredCharTest {
	@Test
	public void testBuilder(){
		String message = "§aH§be§ll§olo §bworld";
		ColoredString string = new ColoredString(message);
		System.out.println("String: "+string);
		Assert.assertTrue(message.equalsIgnoreCase(string.toString()));
	}
}
