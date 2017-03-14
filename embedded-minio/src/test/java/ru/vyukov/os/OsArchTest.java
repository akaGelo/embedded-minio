package ru.vyukov.os;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class OsArchTest {

	@Test
	public void testGetCurrent() throws Exception {
		assertNotEquals("unknown os arch",OsArch.OTHER, OsArch.getCurrent());
	}

}
