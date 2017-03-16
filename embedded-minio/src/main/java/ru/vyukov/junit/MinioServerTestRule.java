package ru.vyukov.junit;

import java.io.File;

import org.junit.rules.ExternalResource;

import lombok.extern.slf4j.Slf4j;
import ru.vyukov.minio.Minio;

/**
 * TestRule for using in junit test. See embedded-minio-sample project
 * @author gelo
 *
 */
@Slf4j
public class MinioServerTestRule extends ExternalResource {

	private Minio minioInstance;
	
	private File storageFold;

	public MinioServerTestRule(File storageFolder) {
		this.storageFold = storageFolder;
	}

	@Override
	protected void before() throws Throwable {
		if (null != minioInstance)
			throw new IllegalStateException("Minio server already running");
		minioInstance = Minio.newInstance(storageFold);
		minioInstance.start();
		log.info("Minio started");

	}

	@Override
	protected void after() {
		if (null == minioInstance)
			throw new IllegalStateException("Minio server not running");
		minioInstance.destroy();
		log.info("Minio stopped");
	}

	public Minio getInstance() {
		if (null == minioInstance) {
			minioInstance = Minio.newInstance(storageFold);
		}
		return minioInstance;
	}
}
