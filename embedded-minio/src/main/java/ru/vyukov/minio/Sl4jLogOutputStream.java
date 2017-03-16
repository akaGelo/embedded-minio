package ru.vyukov.minio;

import org.apache.commons.exec.LogOutputStream;
import org.slf4j.event.Level;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sl4jLogOutputStream extends LogOutputStream {

	private Level level;

	public Sl4jLogOutputStream(Level level) {
		this.level = level;
	}

	@Override
	protected void processLine(String line, int logLevel) {
		switch (level) {
		case DEBUG:
			log.debug(line);
			break;
		case ERROR:
			log.error(line);
			break;
		case INFO:
			log.info(line);
			break;
		case TRACE:
			log.trace(line);
			break;
		case WARN:
			log.warn(line);
			break;

		}
	}

}
