package com.jeffreyricker.osgi.builder.impl;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class TestFileName {

	@Test
	public void testDirName() {
		File f = new File("com.nomura.test");
		assertTrue(f.isDirectory());
		assertEquals("com.nomura.test", f.getName());
	}

}
