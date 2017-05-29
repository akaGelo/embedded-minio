package ru.vyukov.minio;

import java.io.File;

/**
 * Minio Server wrapper
 * 
 * @author gelo
 *
 */
public interface Minio {

	/**
	 * Start Minio server
	 * 
	 * @throws MinioStartException
	 *             if start problem
	 * @throws IllegalStateException
	 *             if server already started
	 */
	void start() throws MinioStartException;

	/**
	 * Test Minio process is alive
	 * 
	 * @return true if minio alive, false if destroyed or not started
	 */
	boolean isAlive();

	/**
	 * Kill Minio subprocess
	 */
	void destroy();

	/**
	 * 
	 * @return current secretKey
	 */
	public String getSecretKey();

	/**
	 * 
	 * @return current accessKey
	 */
	public String getAccessKey();

	/**
	 * 
	 * @return
	 */
	public String getEndpoint();
	

	/**
	 * Create and return bucket
	 * @return
	 * @throws MinioRuntimeException
	 */
	public String getDefaultBucket() throws MinioRuntimeException;
	
	public static Minio newInstance(File storageFolder) {
		return MinioFactory.newInstance(storageFolder);
	}

	

}