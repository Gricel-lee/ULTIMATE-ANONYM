package headless;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import model.persistent_objects.Model;
import prism.PrismException;
import verification_engine.graph_generator.DependencySolver;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

public class Headless {
	
	private static Options options = new Options();
	private static String projectFile;
	private static String modelID;
	private static String property;
	private static String stormInstall;
	private static String pmc = "prism";
	private static boolean help = false;
	
	private static void setUpCLI() {		
		Option help = new Option("help", "prints usage help inforamtion");
		
		Option projectFile = Option.builder("pf")
							 	   .argName("file")
							 	   .hasArg()
							 	   .desc("The file path to the world model json file")
							 	   .build();
		
		Option modelID = Option.builder("m")
		 					   .argName("modelID")
		 					   .hasArg()
		 					   .desc("The ID of the model as described by the project file")
		 					   .build();
		
		
		Option property = Option.builder("p")
		 					    .argName("definition or file")
		 					    .hasArg()
		 					    .desc("The definition of a property OR a path to a .pctl file")
		 					    .build();
		
		Option stormInstallation = Option.builder("si")
				    .argName("file")
				    .hasArg()
				    .desc("The path to a storm installation")
				    .build();
		
		Option pmc = Option.builder("pmc")
			    .argName("pmc engine")
			    .hasArg()
			    .desc("The pmc to use for verification [prism, storm or storm-pars]")
			    .build();
		
		options.addOption(pmc);
		options.addOption(projectFile);
		options.addOption(modelID);
		options.addOption(property);
		options.addOption(stormInstallation);
		options.addOption(help);
	}
	
	private static void getArgs(String[] args) {
		CommandLineParser parser = new DefaultParser();
		try {
	        CommandLine line = parser.parse(options, args);
	        projectFile = line.getOptionValue("pf");
	        modelID = line.getOptionValue("m");
	        property = line.getOptionValue("p");
	        if (line.hasOption("si")) {
	        	stormInstall = line.getOptionValue("si");
	        }
	        if (line.hasOption("help")) {
	        	help = true;
	        }
	        if (line.hasOption("pmc")) {
	        	if (!line.getOptionValue("pmc").equals("prism") && !line.getOptionValue("pmc").equals("storm") && !line.getOptionValue("pmc").equals("storm-pars")) { 
	        		throw new IllegalArgumentException("A PMC must be one of ['prism' 'storm' 'storm-pars']");
	        	}
	        	pmc = line.getOptionValue("pmc");
	        }
		} catch (ParseException e) {
	        System.err.println("Parsing failed.  Reason: " + e.getMessage());
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, PrismException {
	    // create the parser
		
		setUpCLI();
	    getArgs(args);
	    
	    if (help) {
		    HelpFormatter formatter = new HelpFormatter();
		    formatter.printHelp("headless", options);
	    }
	    
	    // get the models from the project file
	    ArrayList<Model> models = ProjectParser.parse(projectFile);
	    
	    Model pModel = null;
	    for (Model m : models) {
	    	if (m.getModelId().equals(modelID)) {
	    		pModel = m;
	    	}
	    }
	    
	    //Double result = Solver.prism(pModel, property);
	    DependencySolver ds = new DependencySolver();
	    Double result = ds.solve(pModel, property, models);
	    
	    System.out.println("Prism result: " + result.toString());
	}
}
