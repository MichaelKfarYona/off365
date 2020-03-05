import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class TestExecution {
	private static final String DIR_NAME = "c:\\1";
	private static final String EXTENSION = ".png";
	private static class ExtensionFilter implements FilenameFilter { 
		  private final String extension; 
		  public ExtensionFilter(String ext) { 
			  extension = ext;			   
			  } 
		  @Override public boolean accept(File dir, String name){return name.endsWith(extension);}
			  }
	public static void folderCleaning() {
		final FilenameFilter filter = new ExtensionFilter(EXTENSION); 
		final File dir = new File(DIR_NAME); 

		String[] filenames = dir.list(filter); 
		for(String filename: filenames) { 

		 String fullFilename = new StringBuilder().append(DIR_NAME).append(File.separator).append(filename).toString(); 
		 File file = new File(fullFilename);
		 Boolean isDeleted = file.delete(); 
	}
}
	 public static void runTestSuite(int iteration, String XML) {
	      List<String> suites = new ArrayList<String>(); 
	      suites.add(XML); //path of .xml file to be run-provide complete path 
	      TestNG tng = new TestNG(); 
	      tng.setTestSuites(suites); 
	      	for(int i=0;i<iteration;i++) { 
	      		tng.run();  }//run test suite
	}
	 
	 
	 public static void main(String[] args) {
		 // folderCleaning();
		 
		runTestSuite(2, "_newAmdocsMeeting_CRUD.xml");
		//runTestSuite(1, "_allDayMeetingTestEnv.xml");
		
		/* Production */
		//runTestSuite(1, "_allDayMeetingProdEnv.xml");
		
		//runTestSuite(1, "desktopTesting.xml");
		
		
	 }
}
