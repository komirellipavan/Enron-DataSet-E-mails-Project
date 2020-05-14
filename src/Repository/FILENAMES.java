package Repository;

  import java.util.*;

  public final class FILENAMES {
  	
    private String id;
    
    private final static String WORKSTATION = "C:\\Users\\Pavan\\HappyToo-Dashboard\\Enron_E-mails_Project";
    private final static String MAC = "/Users/tao";
    private final static String DIR = WORKSTATION;
    
    private FILENAMES(String anID) {
      this.id = anID;
    }
        
    public String toString() {
    	return this.id; 
    }
	
    public static final FILENAMES DEFAULT_STORE_NAME = new FILENAMES("ontology");
    public static final FILENAMES CATALOGUE_MEL = new FILENAMES("unimelb"); 
    public static final FILENAMES LCSH = new FILENAMES("LCSH");
    public static final FILENAMES ROCCHIO = new FILENAMES("rocchio");
    public static final FILENAMES KNN = new FILENAMES("KNN");
    
    public static final FILENAMES TRAININGSET = new FILENAMES(DIR + "/RCV1/training");
    public static final FILENAMES TOPICSET = new FILENAMES(DIR + "/RCV1/topic");
    public static final FILENAMES FEATURESET = new FILENAMES(DIR + "/RCV1/training/features");
    public static final FILENAMES LCSHCONCEPTSET = new FILENAMES(DIR + "/RCV1/training/concepts/lcsh");
    public static final FILENAMES WIKICONCEPTSET = new FILENAMES(DIR + "/RCV1/training/concepts/wiki");
    
    public static final FILENAMES MESSAGES = new FILENAMES(DIR + "/FeatureSelectionExp/");
    
    public static final FILENAMES STOPWORDS = new FILENAMES(DIR + "\\src\\stopword.txt");
    public static final FILENAMES NAMES = new FILENAMES(DIR + "/Sources/name_list_complete.txt");
    public static final FILENAMES RESERVEDWORDS = new FILENAMES(DIR + "\\src\\reservedword.txt");   
    public static final FILENAMES POS_RESOURCE = new FILENAMES(DIR + "/Sources/qtag-eng");	  
    
  }
