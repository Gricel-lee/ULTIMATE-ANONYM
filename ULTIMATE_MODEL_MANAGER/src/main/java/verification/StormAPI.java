package verification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Model;
import project.Project;
import sharedContext.SharedContext;

public class StormAPI {
	
    private SharedContext sharedContext = SharedContext.getInstance();
    Project project;
    
	private static final Logger logger = LoggerFactory.getLogger(PMCVerification.class);
	
	public double run(Model model, String propFile) {
		project = sharedContext.getProject();
	    String si = project.getStormInstall();
		String command = si + " --prism " + "'" + model.getFilePath() + "'" + " --prop \"" + propFile + "\"" + " -pc";
		String output = OSCommandExecutor.executeCommand(command);
		logger.info(output);
		Double result = StormOutputParser.getDResult(output);
		return result;
	}
	
	public String runPars(Model model, String propFile) {
		project = sharedContext.getProject();
	    String spi = project.getStormParsInstall();
		String command = spi + " --mode solutionfunction --prism " + "'" + model.getFilePath() + "'" + " --prop \"" + propFile + "\"" + " -pc";
		String output = OSCommandExecutor.executeCommand(command);
		logger.info(output);
		String result = StormOutputParser.getSResult(output);
		return result;
	}

}