package ru.vyukov.minio;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import ru.vyukov.os.OsFamilies;

final class MinioFactory {

	private MinioFactory() {
		;
	}

	public static Minio newInstance(File storageFolder) {
		@SuppressWarnings("unchecked")
		Class<Minio> clazz = (Class<Minio>) getMinioNativeClass();

		try {
			Constructor<Minio> constructor = clazz.getConstructor(File.class);
			return constructor.newInstance(storageFolder);
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new IllegalStateException("Error on create minio server", e);
		}
	}

	private static Class<? extends Minio> getMinioNativeClass() {
		switch (OsFamilies.getCurrent()) {
		case LINUX:
			return getMinioNativeClass("ru.vyukov.minio.MinioLinux");
		case MACOS:
			return getMinioNativeClass("ru.vyukov.minio.MinioMacOs");
		default:
			throw new IllegalStateException(OsFamilies.getCurrent() + " not supported");
		}
	}

	@SuppressWarnings("unchecked")
	private static Class<? extends Minio> getMinioNativeClass(String className) {
		Class<? extends Minio> nativeMinioClass = null;
		try {
			nativeMinioClass = (Class<? extends Minio>) Class.forName(className);
			return nativeMinioClass;
		}
		catch (ClassNotFoundException e) {
			throw new IllegalStateException(className + " not found");
		}
	}

}
