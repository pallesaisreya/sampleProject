package com.sample.app.demo.file.watcher;

import java.io.File;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class AppStartUpRunner implements ApplicationRunner{

	@Override
	public void run(ApplicationArguments args) throws Exception {
		File folder = new File("");
		new FileWatcher(folder).addListner(new FileListener() {
			
			@Override
			public void onModified(FileEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDeleted(FileEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCreated(FileEvent event) {
				File file = event.getFile();
				if(file.isFile()) {
					processFile(file);
				}
				
			}		
		})
		
		
	}

	private void processFile(File file) {
		// TODO Auto-generated method stub
		
	}
	
}
