package ru.vyukov.os;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class OsFamiliesTest {

	@Test
	public void testGetCurrent() throws Exception {
		assertNotEquals("unknown os name", OsFamilies.OTHER, OsFamilies.getCurrent());
	}

}
