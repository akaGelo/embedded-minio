package ru.vyukov.minio.sample;

import static org.junit.Assert.assertNotNull;
import static ru.vyukov.minio.sample.TestSuite.getMinio;

import org.junit.Test;

/**
 * See {@link ru.vyukov.minio.sample.TestSuite}
 * @author gelo
 *
 */
public class RestartAfterAllTestsTest2 {

	@Test
	public void exampleTest1() {
		String accessKey = getMinio().getAccessKey();
		assertNotNull(accessKey);
		
		String bucket = getMinio().getDefaultBucket();
		assertNotNull(bucket);

	}

	@Test
	public void exampleTest2() {
		String secretKey = getMinio().getSecretKey();
		assertNotNull(secretKey);
		
		String bucket = getMinio().getDefaultBucket();
		assertNotNull(bucket);
	}

}