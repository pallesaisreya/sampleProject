package com.sample.app.demo.file.watcher;

import java.io.File;
import java.util.EventObject;

public class FileEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileEvent(File file) {
		super(file);
	}
	
	public File getFile() {
		return (File)getSource();
	}
	
	
	
}
