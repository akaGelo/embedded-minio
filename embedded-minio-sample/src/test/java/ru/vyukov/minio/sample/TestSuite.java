package ru.vyukov.minio.sample;

import org.junit.ClassRule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ru.vyukov.junit.MinioServerTestRule;
import ru.vyukov.minio.Minio;

/**
 * Stop Minio server after all tests 
 * @author gelo
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ RestartAfterAllTestsTest.class, RestartAfterAllTestsTest2.class, })
public class TestSuite {

	@ClassRule
	public static TemporaryFolder folder = new TemporaryFolder();

	@ClassRule
	public static MinioServerTestRule minioServerTestRule = new MinioServerTestRule(
			folder.getRoot());

	/**
	 * 
	 * @return general minio instance
	 */
	public static Minio getMinio() {
		return minioServerTestRule.getInstance();
	}

}
