package ru.vyukov.minio;

public class MinioRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -7512183165376849190L;

	public MinioRuntimeException(String string, Exception e) {
		super(string, e);
	}

}
