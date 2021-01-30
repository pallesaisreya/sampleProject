package com.sample.app.demo.file.watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;

public class FileWatcher implements Runnable {

	private final Kind<Path> ENTRY_CREATE = StandardWatchEventKinds.ENTRY_CREATE;
	protected List<FileListener> listeners = new ArrayList<>();
	protected static final List<WatchService> watchServices = new ArrayList<>();
	protected final File folder;

	public FileWatcher(File folder) {
		this.folder = folder;
	}

	public void watch() {
		if (folder.exists()) {
			Thread thread = new Thread(this);
			thread.setDaemon(true);
			thread.start();
		}

	}

	@Override
	public void run() {
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			Path path = Paths.get(folder.getAbsolutePath());
			path.register(watchService, ENTRY_CREATE);
			System.out.println();
			watchServices.add(watchService);
			boolean poll = true;
			while (poll) {
				poll = pollEvents(watchService);
			}

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean pollEvents(WatchService watchService) throws InterruptedException {
		WatchKey key = watchService.take();
		Path path = (Path) key.watchable();
		for (WatchEvent<?> event : key.pollEvents()) {
			notifyListners(event.kind(), path.resolve((Path) event.context()).toFile());

		}

		return key.reset();
	}

	private void notifyListners(Kind<?> kind, File file) {
		FileEvent event = new FileEvent(file);
		if (kind == ENTRY_CREATE) {
			for (FileListener fileListener : listeners) {
				fileListener.onCreated(event);
			}
		}

	}

	public void addListner(FileListener fileListener) {
		this.listeners.add(fileListener);
		
	}

}
