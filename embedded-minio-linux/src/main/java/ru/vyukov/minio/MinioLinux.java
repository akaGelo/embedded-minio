package ru.vyukov.minio;

import java.io.File;
import java.util.Map;

public class MinioLinux extends AbstractMinio {

	public MinioLinux(File storageFolder, Map<MinioEnvVariables, String> envelopment) {
		super(storageFolder, envelopment);
	}

	public MinioLinux(File storageFolder, String accessKey, String secretKey) {
		super(storageFolder, accessKey, secretKey);
	}

	public MinioLinux(File storageFolder) {
		super(storageFolder);
	}


}
