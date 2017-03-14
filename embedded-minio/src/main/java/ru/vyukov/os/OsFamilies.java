package ru.vyukov.os;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

public enum OsFamilies {
	WINDOWS, LINUX, BSD, MACOS, OTHER;

	private static OsFamilies current;

	static {
		Map<String, OsFamilies> keys = new LinkedHashMap<>();
		keys.put("win", WINDOWS);
		keys.put("linux", LINUX);
		keys.put("mac", MACOS);
		keys.put("bsd", OsFamilies.BSD);

		final String name = System.getProperty("os.name").toLowerCase();
		Predicate<? super Entry<String, OsFamilies>> predicate = entry -> name.contains((entry.getKey()));
		current = keys.entrySet().stream().filter(predicate).map(Entry::getValue).findFirst().orElse(OTHER);

	}

	public static OsFamilies getCurrent() {
		return current;
	}
}
