package ru.vyukov.os;

public enum OsArch {

	x86, x64, OTHER;

	private static OsArch current;
	static {
		// http://lopica.sourceforge.net/os.html
		String arch = System.getProperty("os.arch");
		switch (arch) {
		case "x86":
		case "i686":
		case "i386":
			current = x86;
			break;

		case "x86_64":
		case "amd64":
			current = x64;
			break;
		default:
			current = OTHER;
			break;
		}
	}

	public static OsArch getCurrent() {
		return current;
	}
}
