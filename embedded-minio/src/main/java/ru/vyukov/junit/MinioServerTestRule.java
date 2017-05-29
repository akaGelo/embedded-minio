package ru.vyukov.junit;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.rules.ExternalResource;

import lombok.extern.slf4j.Slf4j;
import ru.vyukov.minio.Minio;

/**
 * TestRule for using in junit test. See embedded-minio-sample project
 * 
 * @author gelo
 *
 */
@Slf4j
public class MinioServerTestRule extends ExternalResource {

	private final static int DEFAULT_START_DELAY = 3_000;

	private Minio minioInstance;

	private File storageFolder;


	@Override
	protected void before() throws Throwable {
		if (null != minioInstance)
			throw new IllegalStateException("Minio server already running");

		File temp = getTmpFolder();
		storageFolder = temp;
		minioInstance = Minio.newInstance(storageFolder);
		minioInstance.start();

		Thread.sleep(DEFAULT_START_DELAY);
		log.info("Minio started");
	}

	@Override
	protected void after() {
		if (null == minioInstance)
			throw new IllegalStateException("Minio server not running");
		minioInstance.destroy();
		log.info("Minio stopped");
		try {
			FileUtils.deleteDirectory(storageFolder);
		} catch (IOException e) {
			log.error("Error on remove test files", e);
		}
	}

	public Minio getInstance() {
		if (null == minioInstance) {
			minioInstance = Minio.newInstance(storageFolder);
		}
		return minioInstance;
	}

	private File getTmpFolder() throws IOException {
		File temp = File.createTempFile("minio-tmp", "");
		temp.delete();
		temp.mkdir();
		return temp;
	}
}
