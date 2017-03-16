package ru.vyukov.minio;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import ru.vyukov.os.OsFamilies;

public class MinioLinuxTest {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	@Before
	public void before(){
		Assume.assumeTrue(OsFamilies.isLinux());
	}

	@Test
	public void testStartAndStop() throws Exception {
		File minioWorkFolder = folder.newFolder("minio");
		Minio minioLinux = new MinioLinux(minioWorkFolder);
		minioLinux.start();
		
		SECONDS.sleep(3);
		assertTrue(minioLinux.isAlive());

		minioLinux.destroy();
		SECONDS.sleep(3);
		assertFalse(minioLinux.isAlive());
	}

	@Test(expected = IllegalStateException.class)
	public void testStartAlreadyRunning() throws Exception {
		File minioWorkFolder = folder.newFolder("minio");
		Minio minioLinux = new MinioLinux(minioWorkFolder);
		minioLinux.start();

		try {
			minioLinux.start();
		}
		finally {
			minioLinux.destroy();
		}
	}
}
