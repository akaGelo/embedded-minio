package ru.vyukov.minio;

import static org.slf4j.event.Level.INFO;
import static ru.vyukov.minio.MinioEnvVariables.MINIO_ACCESS_KEY;
import static ru.vyukov.minio.MinioEnvVariables.MINIO_SECRET_KEY;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.event.Level;

import ru.vyukov.os.OsArch;
import ru.vyukov.os.OsFamilies;

/**
 * Abstract Minio wrapper
 * @author gelo
 *
 */
abstract class AbstractMinio implements Minio {

	private final static String MINIO_BINARY_FILE = "/minio/" + OsFamilies.getCurrent()
			+ "/" + OsArch.getCurrent() + "/minio";

	private final File storageFolder;

	private volatile DefaultExecutor executor;

	private volatile DefaultExecuteResultHandler executeResultHandler;

	private volatile ShutdownHookProcessDestroyer shutdownHookProcessDestroyer;

	private volatile File tmpMinioFile;

	private final Map<MinioEnvVariables, String> envelopment;

	@SuppressWarnings("serial")
	public AbstractMinio(File storageFolder) {
		this(storageFolder, new HashMap<MinioEnvVariables, String>() {
			{
				put(MINIO_ACCESS_KEY, generateKey(20)); // copy is default minio key
				put(MINIO_SECRET_KEY, generateKey(40));
			}
		});
	}

	@SuppressWarnings("serial")
	public AbstractMinio(File storageFolder, String accessKey, String secretKey) {
		this(storageFolder, new HashMap<MinioEnvVariables, String>() {
			{
				put(MINIO_ACCESS_KEY, accessKey);
				put(MINIO_SECRET_KEY, secretKey);
			}
		});
	}

	/**
	 * @see ru.vyukov.minio.MinioEnvVariables
	 * @param storageFolder
	 * @param envelopment
	 */
	public AbstractMinio(File storageFolder, Map<MinioEnvVariables, String> envelopment) {
		this.storageFolder = storageFolder;

		if (!envelopment.containsKey(MINIO_ACCESS_KEY))
			throw new IllegalArgumentException(
					"envelopmnent['MINIO_ACCESS_KEY'] can not be null");

		if (!envelopment.containsKey(MINIO_SECRET_KEY))
			throw new IllegalArgumentException(
					"envelopmnent['MINIO_SECRET_KEY'] can not be null");

		this.envelopment = Collections.unmodifiableMap(envelopment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.vyukov.minio.Minio#start()
	 */
	@Override
	public void start() throws MinioStartException {
		if (null != executeResultHandler && !executeResultHandler.hasResult())
			throw new IllegalStateException("Minio server already started");

		tmpMinioFile = initTmpMinitServerFile();

		executeResultHandler = new DefaultExecuteResultHandler();
		shutdownHookProcessDestroyer = new ShutdownHookProcessDestroyer();

		PumpStreamHandler streamHandler = new PumpStreamHandler(
				new Sl4jLogOutputStream(INFO), new Sl4jLogOutputStream(Level.ERROR));

		executor = new DefaultExecutor();
		executor.setStreamHandler(streamHandler);
		executor.setProcessDestroyer(shutdownHookProcessDestroyer);

		try {
			CommandLine command = new CommandLine(tmpMinioFile);
			command.addArgument("server").addArgument(storageFolder.getAbsolutePath());
			executor.execute(command, toStringKeyMap(envelopment), executeResultHandler);
		}
		catch (IOException e) {
			throw new MinioStartException("Error on run minio", e);
		}
	}

	/**
	 * Extract Minio server to FS
	 * @return executable Minio server file
	 * @throws MinioStartException
	 */
	protected File initTmpMinitServerFile() throws MinioStartException {
		File tmpMinioFile = null;
		try (InputStream inputStream = getClass()
				.getResourceAsStream(MINIO_BINARY_FILE)) {
			tmpMinioFile = File.createTempFile("minio", null);
			FileUtils.copyInputStreamToFile(inputStream, tmpMinioFile);
			tmpMinioFile.setExecutable(true);
		}
		catch (IOException | NullPointerException e) {
			throw new MinioStartException("Error on create minio executable file", e);
		}
		return tmpMinioFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.vyukov.minio.Minio#isAlive()
	 */
	@Override
	public boolean isAlive() {
		if (null == executeResultHandler)
			return false;

		return !executeResultHandler.hasResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.vyukov.minio.Minio#destroy()
	 */
	@Override
	public void destroy() {
		shutdownHookProcessDestroyer.run();
		if (null != tmpMinioFile) {
			tmpMinioFile.delete();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.vyukov.minio.Minio#getAccessKey()
	 */
	@Override
	public String getAccessKey() {
		return envelopment.get(MINIO_ACCESS_KEY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.vyukov.minio.Minio#getSecretKey()
	 */
	@Override
	public String getSecretKey() {
		return envelopment.get(MINIO_SECRET_KEY);
	}

	private static String generateKey(int count) {
		return RandomStringUtils.random(count, true, true);
	}

	private Map<String, String> toStringKeyMap(
			Map<MinioEnvVariables, String> envelopment) {
		Map<String, String> strEvn = envelopment.entrySet().stream().collect(Collectors
				.toMap(e -> e.getKey().toString(), e -> e.getValue().toString()));
		return strEvn;
	}
}
