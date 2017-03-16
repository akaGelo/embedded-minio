package ru.vyukov.minio;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import ru.vyukov.os.OsFamilies;

public class MinioFactoryTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void testNewInstance() throws Exception {
		Minio minio = Minio.newInstance(folder.getRoot());
		assertNotNull(minio);
	}
}
