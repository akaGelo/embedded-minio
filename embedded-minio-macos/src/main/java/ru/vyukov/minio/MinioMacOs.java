package ru.vyukov.minio;

import java.io.File;
import java.util.Map;

public class MinioMacOs extends AbstractMinio {

	public MinioMacOs(File storageFolder, Map<MinioEnvVariables, String> envelopment) {
		super(storageFolder, envelopment);
	}

	public MinioMacOs(File storageFolder, String accessKey, String secretKey) {
		super(storageFolder, accessKey, secretKey);
	}

	public MinioMacOs(File storageFolder) {
		super(storageFolder);
	}
}