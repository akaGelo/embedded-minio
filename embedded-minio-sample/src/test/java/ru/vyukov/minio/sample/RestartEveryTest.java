package ru.vyukov.minio.sample;

import static org.junit.Assert.assertNotNull;
import static ru.vyukov.minio.sample.TestSuite.getMinio;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import ru.vyukov.junit.MinioServerTestRule;

/**
 * This approach is not optimal. See {@link ru.vyukov.minio.sample.TestSuite}
 * 
 * @author gelo
 *
 */
public class RestartEveryTest {

	@Rule
	public MinioServerTestRule minioServerTestRule = new MinioServerTestRule();

	@Test
	public void exampleTest1() {
		String accessKey = getMinio().getAccessKey();
		assertNotNull(accessKey);

		String bucket = getMinio().getDefaultBucket();
		assertNotNull(bucket);

	}

}