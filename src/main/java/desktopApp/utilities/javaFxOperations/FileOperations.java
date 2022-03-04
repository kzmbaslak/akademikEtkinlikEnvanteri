package desktopApp.utilities.javaFxOperations;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Component;

import akdmEtkinlikEnvanter.core.utilities.result.DataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.ErrorResult;
import akdmEtkinlikEnvanter.core.utilities.result.Result;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessDataResult;
import akdmEtkinlikEnvanter.core.utilities.result.SuccessResult;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

@Component
public class FileOperations {
	public String getChooseFilePath(final String description, final String... extensions) {
		FileChooser fc = new FileChooser();
		if(extensions.length == 0)
			fc.getExtensionFilters().add(new ExtensionFilter(description,"*"));
		else
			fc.getExtensionFilters().add(new ExtensionFilter(description, extensions));
//		fc.getExtensionFilters().add(new ExtensionFilter("PDF File", "*.pdf"));
		File file = fc.showOpenDialog(null);
		
		if(file != null)
			return file.getAbsolutePath();
		return null;
	}
	
	public String getChooseDirectoryPath() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
//		directoryChooser.setInitialDirectory(new File("Desktop"));
		File selectedDirectory = directoryChooser.showDialog(new Stage());
		
		if(selectedDirectory != null)
			return selectedDirectory.getAbsolutePath();
		return null;
	}
	
	public DataResult<String> copyFile(String sourcePath, String destinationPath) {
		File directory = new File(destinationPath);
		if(!directory.exists())
			directory.mkdirs();
		File original = new File(sourcePath);
		if(!original.exists())
			return new ErrorDataResult<String>(null,"Kaynak Dosya Bulunamadı.");
		destinationPath += "/"+original.getName();
		
		File destination = new File(destinationPath);
		
		try {
			if(destination.exists())
				return new ErrorDataResult<String>(null,"Hedef Klasörde Aynı İsimde Dosya Mevcuttur. Dosya İsmi:"+destination.getName() );
//			int counter = 0;
//			while(destination.exists()) {
//        		counter++;
//        		destination = new File(destinationPath+"/"+counter+""+original.getName());
//        	}	
			
			InputStream in = new BufferedInputStream(new FileInputStream(original));
			OutputStream out = new BufferedOutputStream(new FileOutputStream(destination));
			

	        byte[] buffer = new byte[1024];
	        int lengthRead;
	        while ((lengthRead = in.read(buffer)) > 0) {
	            out.write(buffer, 0, lengthRead);
	            out.flush();
	        }
	        in.close();
	        out.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			return new ErrorDataResult(null,e.getMessage());
		}
		
		return new SuccessDataResult<String>(destinationPath,"Dosya Kopylandı.");
		
	}
	
	public Result deleteFile(File file) {
		if(file == null)
			return new ErrorResult("Dosya Mevcut Değildir.");
		else if(file.exists()) {
			boolean status = file.delete();
			return new Result(status);
		}
		return new ErrorResult("Dosya Mevcut değildir.");
	}
}
