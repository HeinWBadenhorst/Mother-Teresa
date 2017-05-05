/*
 * InfoManager.java
 *
 * Created on November 22, 2002, 11:38 AM
 */

package motherteresa;


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Toolkit;
import java.net.URL;
import javax.help.*;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.text.Document;
import java.io.*;        
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.util.TreeMap;
import java.lang.reflect.*;
import javax.swing.*;
import javax.swing.JScrollPane.*;
import javax.swing.text.MaskFormatter;
import javax.swing.table.*;
import java.awt.BorderLayout;
import javax.swing.border.Border;
import java.util.Set;
import java.util.Iterator;
import javax.swing.event.*;
import java.util.*;
import java.text.*;



/**
 *
 * @author  Hein Badenhorst
 */
public class InfoManager extends JFrame implements ActionListener 
{
    public static  final int  MVC_ERROR = 0;
    public static final int  DBACCESS_ERROR = 1;
    public static  final int  ADAPTER_ERROR = 2;
    public static final int  INDEX_ERROR = 3;
    public static final int  NULL_ERROR = 4;
    public static final int  CLASS_CAST_ERROR = 5;
    public static  final int  NUMERIC_ERROR = 6;
    public static  final int  VIEW_BUILDER_ERROR = 7;
    public static  final int  LOG_WRITE_ERROR = 8;
    public static  final int  PANE_CREATION_ERROR = 9;
    public static  final int  BUSINESS_MODEL_ERROR = 10;
    
    public static  final int  CAPTURE_BASIC = 1;
    public static  final int  DISPLAY_BASIC = 2;
    public static  final int  CAPTURE_COMPLAINT = 3;
    public static  final int  DISPLAY_COMPLAINT = 4;
    public static  final int  CAPTURE_SYSTEMIC = 5;
    public static  final int  DISPLAY_SYSTEMIC = 6;
    public static  final int  CAPTURE_MEDICAL_HISTORY = 7;
    public static  final int  DISPLAY_MEDICAL_HISTORY = 8;
    public static  final int  CAPTURE_SURGICAL_HISTORY = 9;
    public static  final int  DISPLAY_SURGICAL_HISTORY = 10;
    public static  final int  CAPTURE_FAMILY_HISTORY = 11;
    public static  final int  DISPLAY_FAMILY_HISTORY = 12;
    public static  final int  CAPTURE_SOCIAL_HISTORY = 13;
    public static  final int  DISPLAY_SOCIAL_HISTORY = 14;
    public static  final int  CAPTURE_OCCUPATIONAL_HISTORY = 15;
    public static  final int  DISPLAY_OCCUPATIONAL_HISTORY = 16;
    public static  final int  CAPTURE_TREATMENT = 17;
    public static  final int  DISPLAY_TREATMENT = 18;
    public static  final int  CAPTURE_ALLERGIES = 19;
    public static  final int  DISPLAY_ALLERGIES = 20;
    public static  final int  CAPTURE_TRAVEL_HISTORY = 21;
    public static  final int  DISPLAY_TRAVEL_HISTORY = 22;
    public static  final int  CAPTURE_EXAMINATION = 23;
    public static  final int  DISPLAY_EXAMINATION = 24;
    public static  final int  CAPTURE_ASSESMENT = 25;
    public static  final int  DISPLAY_ASSESMENT = 26;
    public static  final int  CAPTURE_FINAL_DIAGNOSES = 27;
    public static  final int  DISPLAY_FINAL_DIAGNOSES = 28;
    public static  final int  CAPTURE_PLAN = 29;
    public static  final int  DISPLAY_PLAN = 30;

    public static  final int  FOLLOWUP_DISPLAY_BASIC = 1;
    public static  final int  FOLLOWUP_CURRENT_STATUS = 2;
    public static  final int  FOLLOWUP_CAPTURE_COMPLAINT = 3;
    public static  final int  FOLLOWUP_DISPLAY_COMPLAINT = 4;
    public static  final int  FOLLOWUP_CAPTURE_EXAMINATION = 5;
    public static  final int  FOLLOWUP_DISPLAY_EXAMINATION = 6;
    public static  final int  FOLLOWUP_CAPTURE_FINAL_DIAGNOSES = 7;
    public static  final int  FOLLOWUP_DISPLAY_FINAL_DIAGNOSES = 8;
    public static  final int  FOLLOWUP_CAPTURE_PLAN = 9;
    public static  final int  FOLLOWUP_DISPLAY_PLAN = 10;

    public static  final int  NEW_MENU = 1;
    public static  final int  FOLLOWUP_MENU = 2;

    
    
    public static  final String OS_VERSION = "WINXP";
    public static  final String TARGET_VOLUME = "C:";
    public static  final String  REGISTERED_USER = "Demo System";
    public static  final int  MAX_TRIES = 4;
    public static  final boolean  ENCRYPTED_DATABASE = true;
    public static  final String DEF_USER_NAME = "root";
    public static  final String DEF_STR_PASSWORD = "volkskas";
    public static  final char[] DEF_PASSWORD = {'v','o','l','k','s','k','a','s'};


    public static  final String  FH_FATHER = "1";
    public static  final String  FH_MOTHER = "2";
    public static  final String  FH_SIBLINGS = "3";
    public static  final String  FH_CHILDREN = "4";

    public static final String WINDOWS_LOG_PATH = TARGET_VOLUME + "\\Mother Teresa\\logs\\MTSQLLog.txt";
    public static final String WINXP_LOG_PATH = TARGET_VOLUME + "\\Mother Teresa\\logs\\MTSQLLog.txt";
    public static final String UNIX_LOG_PATH = "/usr/src/Mother Teresa/logs/MTSQLLog.txt";
    public static final String WINDOWS_DOCUMENT_PATH = TARGET_VOLUME + "\\Mother Teresa\\docscans\\";
    public static final String WINXP_DOCUMENT_PATH = TARGET_VOLUME + "\\Mother Teresa\\docscans\\";
    public static final String UNIX_DOCUMENT_PATH = "/usr/src/Mother Teresa/docscans/";
    public static final String WINDOWS_INI_PATH = TARGET_VOLUME + "\\Mother Teresa\\motherteresa\\pracman.ini";
    public static final String UNIX_INI_PATH = "/usr/src/Mother Teresa/motherteresa/pracman.ini";
    public static final String WINXP_INI_PATH = TARGET_VOLUME + "\\Mother Teresa\\pracman.ini";
    public static final String WINDOWS_IMAGE_PATH = TARGET_VOLUME + "\\Mother Teresa\\images\\";
    public static final String WINXP_IMAGE_PATH = TARGET_VOLUME + "\\Mother Teresa\\images\\";
    public static final String UNIX_IMAGE_PATH = "/usr/src/Mother Teresa/images/";
    public static final String WINDOWS_XML_PATH = TARGET_VOLUME + "\\Mother Teresa\\xml files\\";
    public static final String WINXP_XML_PATH = TARGET_VOLUME + "\\Mother Teresa\\xml files\\";
    public static final String UNIX_XML_PATH = "/usr/src/Mother Teresa/xml files/";
    public static final String DISPLAY_XML_PATH = TARGET_VOLUME + "\\Mother Teresa\\xml files\\";
    public static final String AGREEMENT_PATH = TARGET_VOLUME + "\\Mother Teresa\\License Agreement\\Agreement.rtf";
    public static final String HOST_NAME = "localhost";
    public static final String DATABASE_NAME = "medibase";
    public static int WIDTH = 700;
    public static int HEIGHT = 400;
    public static int EXAM_SIZE_X = 1130;
    public static int EXAM_SIZE_Y = 840;
    public static int SYSTEMIC_SIZE_X = 1000;
    public static int SYSTEMIC_SIZE_Y = 900;
    public static int ENT_SIZE_X = 1100;
    public static int ENT_SIZE_Y = 800;
    
    public static int SFRAME_WIDTH = 980;
    public static int SFRAME_HEIGHT = 740;
    public static int FRAME_WIDTH = 1130;
    public static int FRAME_HEIGHT = 870;
    public static int U_FRAME_X = 350;
    public static int U_FRAME_Y = 350;
    public static int FRAME_X = 0;
    public static int FRAME_Y = 0;
    public static boolean DATA_LOG = false;
  
    public static String OK = "ok";
    public static String HELP = "help";
    public JPasswordField passwordField;
    public JInternalFrame userLogonFrame;
    int logonTries = 0;
    private RandomAccessFile quoteOfTheDayFile = null;
    private boolean patientDataFetched = false;


    
    public String imagePath, xmlPath;
    private String patientName = null;
    private HashMap methodTable = new HashMap(5);
    private HashMap systemicMethodTable = new HashMap(5);
    private HashMap entMethodTable = new HashMap(5);
    private HashMap familyHistoryMethodTable = new HashMap(5);
    private HashMap examinationMethodTable = new HashMap(5);
    private HashMap followUpExaminationMethodTable = new HashMap(5);
    private HashMap occupationHistoryMethodTable = new HashMap(5);
    private InfoManager app;
    private HashMap menuContainer = new HashMap(5);
    private HashMap objectContainer = new HashMap(5);
    private PatientInfoContainer staticInfoContainer = new PatientInfoContainer(WINXP_IMAGE_PATH);
    private MenuStateMachineStubs menuStates;
    private NewMenuStateMachineStubs followUpMenuStates;
    public String patientKey = null;
    private JInternalFrame newPatientFrame;
    private ArrayList menuArrayList = null;
    private ArrayList systemicMenuArrayList = null;
    private ArrayList treatmentMenuArrayList = null;
    private ArrayList entMenuArrayList = null;
    private ArrayList familyHistoryMenuArrayList = null;
    private ArrayList examinationMenuArrayList = null;
    private ArrayList followUpExaminationMenuArrayList = null;

    private ArrayList occupationHistoryMenuArrayList = null;
    private JMenuItem currentMenuItem = null; 
    private JMenuItem clickedMenuItem = null;
    private JMenuItem systemicClickedMenuItem = null;
    private JMenuItem entClickedMenuItem = null;
    private JMenuItem familyHistoryClickedMenuItem = null;
    private JMenuItem occupationHistoryClickedMenuItem = null;
    private JMenuItem examinationClickedMenuItem = null;
    private JMenuItem followUpExaminationClickedMenuItem = null;
    private JMenuItem theMenuItem = null;
    private JMenuItem systemicMenuItem = null;
    private JMenuItem entMenuItem = null;
    private JMenuItem familyHistoryMenuItem = null;
    private JMenuItem occupationHistoryMenuItem = null;
    private JMenuItem examinationMenuItem = null;
    private JMenuItem followUpExaminationMenuItem = null;
    private int updateButtonEvent = 0;
    private int arrayLoop = 0;
    private JInternalFrame staticInfoFrame = null;
    private JInternalFrame dateInfoFrame = null;
    private JInternalFrame systemicInternalFrame = null;
    private JInternalFrame agreementInternalFrame = null;
    private JInternalFrame captureExamEarNoseThroatFrame = null;
    private JInternalFrame captureExamUrogenitalFrame = null;
    private JInternalFrame familyHistoryInternalFrame = null;
    private JInternalFrame socialHistoryInternalFrame = null;
    private JInternalFrame occupationHistoryInternalFrame = null;
    private JInternalFrame treatmentHistoryInternalFrame = null;
    private JInternalFrame treatmentPlanInternalFrame = null;
    private JInternalFrame allergyHistoryInternalFrame = null;
    private JInternalFrame travelHistoryInternalFrame = null;
    private JInternalFrame examinationInternalFrame = null;
    private JInternalFrame followUpExaminationInternalFrame = null;
    private AddInfoMVC newConstitutionalPanel = null; 
    private AddInfoMVC newPalpitationsPanel = null; 
    private AddInfoMVC newChestPainPanel = null; 
    private AddInfoMVC newDyspneaPanel = null; 
    private AddInfoMVC newClaudicationPanel = null; 
    private AddInfoMVC newLegSwellingPanel = null; 
    private AddInfoMVC newRespDyspneaPanel = null; 
    private AddInfoMVC newRespChestPainPanel = null; 
    private AddInfoMVC newRespChestTightnessPanel = null; 
    private AddInfoMVC newRespCoughingPanel = null; 

    private AddInfoMVC newAbdNauseaPanel = null; 
    private AddInfoMVC newAbdVomitingPanel = null;
    private AddInfoMVC newAbdDiarrheaPanel = null;
    private AddInfoMVC newAbdConstipationPanel = null;
    private AddInfoMVC newAbdominalPainPanel = null;
    private AddInfoMVC newAbdTenesmusPanel = null;
    private AddInfoMVC newAbdRectalProlapsePanel = null;
    private AddInfoMVC newAbdHematemesisPanel = null;
    private AddInfoMVC newAbdBloodPerRectumPanel = null;
    private AddInfoMVC newAbdJaundicePanel = null;
    private AddInfoMVC newMSJointPainsPanel = null;
    private AddInfoMVC newMSJointSwellingsPanel = null;
    private AddInfoMVC newMSMuclePainsPanel = null;
    private AddInfoMVC newMSSpinalPainsPanel = null;
    private AddInfoMVC newMSExtraArticularPanel = null;
    private AddInfoMVC newMSJointDeformityPanel = null;
    private AddInfoMVC newMSOtherPanel = null;    
    private AddInfoMVC newCNSDizzySpellsPanel = null;    
    private AddInfoMVC newCNSHeadachePanel = null;
    private AddInfoMVC newCNSNeckStiffnessPanel = null;
    private AddInfoMVC newCNSMotorDefecitPanel = null;
    private AddInfoMVC newCNSSAndCNerveDefecitPanel = null;
    private AddInfoMVC newCNSCerebellarDefecitPanel = null;
    private AddInfoMVC newCNSGaitDefecitPanel = null;
    private AddInfoMVC newCNSIncontinancePanel = null;
    private AddInfoMVC newCNSSTMemoryLossPanel = null;
    private AddInfoMVC newCNSLossOfConciousnessPanel = null;
    private AddInfoMVC newCNSConvultionsPanel = null;
    private AddInfoMVC newCNSHigherFunctionAbnormalitiesPanel = null;
    private AddInfoMVC newENTSoreThroatPanel = null;
    private AddInfoMVC newENTPostNasalDripPanel = null;    
    private AddInfoMVC newENTDysphagiaPanel = null;    
    private AddInfoMVC newENTHoarseVoicePanel = null;
    private AddInfoMVC newENTThroatSwellingPanel = null;
    private AddInfoMVC newENTSnoringPanel = null;
    private AddInfoMVC newENTBlockedNosePanel = null;
    private AddInfoMVC newENTSinusPainsPanel = null;
    private AddInfoMVC newENTNoseBleedPanel = null;
    private AddInfoMVC newENTSneezingPanel = null;
    private AddInfoMVC newENTRhinorrhoeaPanel = null;
    private AddInfoMVC newENTEarAchePanel = null;
    private AddInfoMVC newENTEarDischargePanel = null;
    private AddInfoMVC newENTDeafnessPanel = null;
    private AddInfoMVC newENTVertigoPanel = null;
    private AddInfoMVC newENTTinnitusPanel = null;
    private AddInfoMVC newDermaRashPanel = null;
    private AddInfoMVC newDermaUlcersPanel = null;
    private AddInfoMVC newDermaVitiligoPanel = null;
    private AddInfoMVC newDermaAllopeciaPanel = null;
    private AddInfoMVC newDermaPetechiaePanel = null;
    private AddInfoMVC newDermaPurperaPanel = null;
    private AddInfoMVC newHemPetechiaePanel = null;
    private AddInfoMVC newHemPurperaPanel = null;
    private AddInfoMVC newEyesRedEyePanel = null;
    private AddInfoMVC newEyesDischargePanel = null;
    private AddInfoMVC newEyesPainPanel = null;
    private AddInfoMVC newEyesDiplopiaPanel = null;
    private AddInfoMVC newEyesLossOfVisionPanel = null;        
    private AddInfoMVC newUGIncontinancePanel = null;
    private AddInfoMVC newUGUrineRetentionPanel = null;
    private AddInfoMVC newUGDysuriaPanel = null;
    private AddInfoMVC newUGDischargePanel = null;
    private AddInfoMVC newUGBleedingPanel = null;
    private AddInfoMVC newEndoThyroidPanel = null;
    private AddInfoMVC newEndoAdrenalPanel = null;
    private AddInfoMVC newEndoPituitaryPanel = null;
    private AddInfoMVC newEndoParathyroidsPanel = null;
    private AddInfoMVC newEndoPancreasPanel = null;
    private AddInfoMVC newEndoGonadsPanel = null;
    
    private AddInfoMVC newENTEarPanel = null;
    private AddInfoMVC newENTNosePanel = null;
    private AddInfoMVC newENTParanasalPassagesPanel = null;
    private AddInfoMVC newENTThroatPanel = null;
    private AddInfoMVC newUROMalePanel = null;
    private AddInfoMVC newUROFemalePanel = null;
    private AddInfoMVC newUROUrinePanel = null;
    private AddInfoMVC familyHistoryFatherPanel = null;
    private AddInfoMVC familyHistoryMotherPanel = null;
    private AddInfoMVC familyHistorySiblingsPanel = null;
    private AddInfoMVC familyHistoryChildrenPanel = null;
    private AddInfoMVC occupationHistoryCurrentPanel = null;
    private AddInfoMVC occupationHistoryPreviousPanel = null;
    private AddInfoMVC captureExamGeneralPanel = null;
    private AddInfoMVC captureAllergyPanel = null;
    private AddInfoMVC captureExamCardiovascularPanel = null;
    private AddInfoMVC captureExamRespiratoryPanel = null;
    private AddInfoMVC captureExamAbdomenPanel = null;
    private AddInfoMVC captureExamMusculoskeletalPanel = null;
    private AddInfoMVC captureExamCentralNervousSystemPanel = null;
    //private AddInfoMVC captureExamEarNoseThroatPanel = null;
    private AddInfoMVC captureExamDermatologicalPanel = null;
    private AddInfoMVC captureExamHematologicalPanel = null;
    private AddInfoMVC captureExamEyesPanel = null;
    private AddInfoMVC captureExamUrogenitalPanel = null;
    private AddInfoMVC captureExamEndocrinePanel = null;
    private DisplayBasicStaticInfoMVC persInfoPanel = null;
    private AddInfoMVC treatmentPlanAllopathicPanel = null;
    private AddInfoMVC newComplaintPanel = null;
    
    
    private JProgressBar progressBar = null;
    private JInternalFrame progressFrame = null;
    private String dbUserName = "root";
    private char[] dbPassWord = { 'v', 'o', 'l', 'k', 's', 'k', 'a', 's' };
    private String userName = "root";
    private char[] passWord = { 'v', 'o', 'l', 'k', 's', 'k', 'a', 's' };
    private HashMap logonResultSet;
    final static long CBCIV_START = 0xfedcba9876543210L;
    private boolean databaseDown = false;
    private HashMap skipButtonList = null;
    private HashMap skipButtonMap = null;
    private HelpBroker hb;
    private JLabel patientLabelName = new JLabel("");
    private String tabIconName = null;
    private JMenuBar followUpPatientMenu;
    private JMenuBar newPatientMenu;
    private ArrayList dateArrayList;
    private ArrayList dateSelectionArray = null;
    private ArrayList dateTickBoxArray = null;
    private int dateCount = 0;
    private JPanel capturePanel = null;
    private HashMap captureList = null;
    private JPanel systemicPanel = null;
    private JPanel familyPanel = null;
    private JPanel occupationPanel = null;
    private JPanel examinationPanel = null;
    private JPanel dummyPanel = null;
    private JPanel treatmentPanel = null;
    
    
    private JMenuItem newNoteFile;
    private JMenuItem openNoteFile;
    private JMenuItem closeNoteFile;
    private JMenuItem saveNoteFile;
    private JMenuItem saveAsNoteFile;
    private JMenuItem deletePatient;
    private JMenu patientMenu;

    //Patient edit Specific Menu Items
    private JMenuItem basicInfo;
    private JMenuItem vitalSigns;
    private JMenuItem injury;
    private JMenuItem poisoning;
    private JMenuItem symptoms;
    private JMenuItem diagnoses;
    private JMenuItem tests;
    private JMenuItem condition;
    private JMenuItem treatment;
    private JMenuItem surgery;
    private JMenuItem habits;
    private JMenuItem familyHistory;
    private JMenuItem allergies;
    private JMenuItem travelHistory;
    private JMenuItem immunizationHistory;
    private JMenuItem testReferrals;
    private JMenuItem specialistReferrals;
    private JMenuItem occupationHistory;
    private JMenuItem prescriptionHistory;
    
    //Patient Add Specific Menu Items
    private JMenuItem addVitalSigns;
    private JMenuItem addInjury;
    private JMenuItem addPoisoning;
    private JMenuItem addSymptoms;
    private JMenuItem addDiagnoses;
    private JMenuItem addTests;
    private JMenuItem addCondition;
    private JMenuItem addTreatment;
    private JMenuItem addSurgery;
    private JMenuItem addHabits;
    private JMenuItem addFamilyHistory;
    private JMenuItem addAllergies;
    private JMenuItem addTravelHistory;
    private JMenuItem addImmunizationHistory;
    private JMenuItem addTestReferrals;
    private JMenuItem addSpecialistReferrals;
    private JMenuItem addOccupationHistory;
    private JMenuItem addPrescriptionHistory;

    //Patient Display Specific Menu Items
    private JMenuItem displayCondition;
    private JMenuItem displayBasic;
    private JMenuItem displayAllPatient;
    private JMenuItem displayVitalSigns;
    private JMenuItem displayInjury;
    private JMenuItem displayPoisoning;
    private JMenuItem displaySymptoms;
    private JMenuItem displayDiagnoses;
    private JMenuItem displayTests;
    private JMenuItem displayTreatment;
    private JMenuItem displaySurgery;
    private JMenuItem displayHabits;
    private JMenuItem displayFamily;
    private JMenuItem displayAllergies;
    private JMenuItem displayImmunization;
    private JMenuItem displayTravel;
    private JMenuItem displayTestReferrals;
    private JMenuItem displaySpecialistReferrals;
    private JMenuItem displayOccupationHistory;
    private JMenuItem displayPrescriptionHistory;
    private DisplayComplaintStaticInfoMVC complaintInfoPanel;
    

    //Maintenance Tables Menu
    private JMenuItem exit;
    private JMenuItem newPatient;
    private JMenuItem followupPatient;
    private JMenuItem endPatient;
    private JMenuItem cancelPatient;
    
    
    
    private JMenuItem physicianTable;
    private JMenuItem editPatient;
    private JMenuItem displayPatient;
    private JMenuItem newAppointment;
    private JMenuItem editAppointment;
    private JMenuItem displayAppointment;
    private JMenuItem deleteAppointment;
    private JMenuItem tableMaintenance;
    private JMenuItem diagnosticAid;
    
    private JMenuItem rifeGeneratorTool;
    private JMenuItem languageTable;
    private JMenuItem allergyTypeTable;
    private JMenuItem appointmentStateTable;
    private JMenuItem institutionTypeTable;
    private JMenuItem testInstTable;
    private JMenuItem bodyLocationTypeTable;
    private JMenuItem anatomicalLocationTable;
    private JMenuItem diseaseTable;
    private JMenuItem surgeryTable;
    private JMenuItem symptomTable;
    private JMenuItem testTable;
    private JMenuItem dosageTable;
    private JMenuItem employerTable;
    private JMenuItem titleTable;
    private JMenuItem conditionTable;
    private JMenuItem appetiteTable;
    private JMenuItem toleranceTable;
    private JMenuItem categoryTable;
    private JMenuItem foodSourceTable;
    private JMenuItem bevarageSourceTable;
    private JMenuItem diagnosesCauseTable;
    private JMenuItem mapSourceTable;
    private JMenuItem travelMeansTable;
    private JMenuItem medicalAidTable;
    private JMenuItem occupationTypeTable;
    private JMenuItem patientTypeTable;
    private JMenuItem relationTable;
    private JMenuItem residenceTable;
    private JMenuItem specialistTypeTable;
    private JMenuItem specialistTable;
    private JMenuItem timeUnitsTable;
    private JMenuItem onsetUnitsTable;
    private JMenuItem currentStatusTable;
    private JMenuItem launchHelp;
    private JMenuItem launchAgreement;
    private JMenuItem previousSystMenuItem = null;
    private JMenuItem previousExamMenuItem = null;
    private JMenuItem previousOccupationHistMenuItem = null;
    private JMenuItem previousFamilyMenuItem = null;
    private JMenuItem subExamMenuItem = null;
    private JPanel rifePanel = null;
    
    
    
    
    
    private JMenuItem about;
    private JMenu toolsMenu;

    //button declarations
    private JButton newPatientButton;
    private JButton cancelCaptureButton;
    private JButton endCaptureButton;
    private JButton followUpPatientButton;
    private JButton diagnosesButton;
    private JButton operationsButton;
    private JButton habitsButton;
    private JButton conditionButton;
    private JButton vitalStatsButton;
    private JButton updateExitButton;
    private JButton diagExitButton;
    private JButton rifeExitButton;
    
    private JFileChooser fileChooser = new JFileChooser();
    private JDesktopPane desktop = new JDesktopPane();
    private JTextField statusField = new JTextField("System Ready", 10);
    private JTextField userNameField = new JTextField("Not Logged In", 15);
    private JTextField cursorField = new JTextField("Authenticating...", 150);
    private JPopupMenu popupMenu = new JPopupMenu();
    
    private Dimension screenSize, paneSize;
    private static final int screenInset = 0;
    private static final int paneOffset = 20;
    private int lastOffset = 0;
    private boolean lastDataSelected = true;
    private boolean stateMachineReset = false;
    private int displayMethodNumber = 0;
    private boolean followUpMenuSelected = false;
    private int currentMenu = 0;
    private boolean examinationDone = false;
    
    private Cursor  waitCursor = new Cursor(Cursor.WAIT_CURSOR);

    /** Creates a new instance of InfoManager */
    public InfoManager() 
    {
        app = this;
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent event)
            {
                System.exit(0);
            }
        });
        this.setTitle("Mother Teres@ Ver 1.0");
        
        if (OS_VERSION.equalsIgnoreCase("WINDOWS"))
        {
           imagePath = WINDOWS_IMAGE_PATH;
           xmlPath = WINDOWS_XML_PATH;
        } else
        if (OS_VERSION.equalsIgnoreCase("UNIX"))
        {
           imagePath = UNIX_IMAGE_PATH;
           xmlPath = UNIX_XML_PATH;
        } else
        if (OS_VERSION.equalsIgnoreCase("WINXP"))
        {
           imagePath = WINXP_IMAGE_PATH;
           xmlPath = WINXP_XML_PATH;
        }
        ImageIcon icon = new ImageIcon(imagePath + "mtmain.gif");
        //this.setIconImage(Toolkit.getDefaultToolkit().getImage(imagePath + "mtmain.gif"));
        setIconImage(icon.getImage());
        
        dummyPanel = new JPanel();
         
        dummyPanel.setMaximumSize(new Dimension(550, 300));
        dummyPanel.setMinimumSize(new Dimension(550, 300));
        dummyPanel.setPreferredSize(new Dimension(550, 300));

        //String imgLocation = "toolbarButtonGraphics/media/Play24.gif";
        //URL imageURL = getClass().getResource(imgLocation);  //imagePath + "new24.gif"
        newPatientButton = new JButton("New", new ImageIcon(imagePath + "newpatient.gif"));
        followUpPatientButton = new JButton("Follow Up", new ImageIcon(imagePath + "followupvisit.gif"));
        cancelCaptureButton = new JButton("Cancel Capture", new ImageIcon(imagePath + "cancelpatient.gif"));
        endCaptureButton = new JButton("End Capture", new ImageIcon(imagePath + "stopsave.gif"));

        
        diagnosesButton = new JButton("Diags", new ImageIcon(imagePath + "diag24.gif"));
        operationsButton = new JButton("Ops", new ImageIcon(imagePath + "ops24.gif"));
        habitsButton = new JButton("Habits", new ImageIcon(imagePath + "habits24.gif"));
        conditionButton = new JButton("Cond", new ImageIcon(imagePath + "cond24.gif"));
        vitalStatsButton = new JButton("VStats", new ImageIcon(imagePath + "vstats24.gif"));

        JPanel statusLine = new JPanel();
        statusLine.setLayout(new BoxLayout(statusLine, BoxLayout.X_AXIS));
        statusLine.add(statusField);
        statusLine.add(userNameField);

        statusLine.setBorder(BorderFactory.createEtchedBorder());
        statusField.setBackground(new Color(150,150,255));
        statusField.setBorder(BorderFactory.createLoweredBevelBorder());
        statusField.setEditable(false);
        userNameField.setBackground(new Color(150,150,255));
        userNameField.setBorder(BorderFactory.createLoweredBevelBorder());
        userNameField.setEditable(false);
        statusLine.add(Box.createRigidArea(new Dimension(30,0)));
        
        //statusLine.add(cursorField);
        
        JScrollPane cursorScrollPane = new JScrollPane(cursorField);
        
        cursorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        cursorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        //cursorScrollPane.setMaximumSize(new Dimension(100,50));
        cursorScrollPane.setPreferredSize(new Dimension(600,35));
        //cursorScrollPane.setSize(100,50);
        
        statusLine.add(cursorScrollPane);
               
        
        cursorField.setBackground(new Color(150,150,255));
        cursorField.setForeground(new Color(255,255,51));
        
        cursorField.setEditable(false);
        getContentPane().add(createToolBar(),BorderLayout.NORTH);
        this.getContentPane().add(desktop,BorderLayout.CENTER);
        getContentPane().add(statusLine,BorderLayout.SOUTH);
        

        desktop.putClientProperty("JDesktopPane.dragMode", "outline");
        setJMenuBar(createMenuBar());
        createPopupMenu();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenInset, screenInset, screenSize.width - 2*screenInset, 
                    screenSize.height - 2*screenInset);
        paneSize = new Dimension((int)(screenSize.getWidth() - 2*screenInset)/2,
                                (int)(screenSize.getHeight() - 2*screenInset)/2);
        if ((OS_VERSION.equalsIgnoreCase("WINDOWS")) || (OS_VERSION.equalsIgnoreCase("WINXP")))
        {    
            try
            {
                UIManager.setLookAndFeel((String)lookAndFeels.get((Object)"Kunststof"));
                SwingUtilities.updateComponentTreeUI(app);
                statusField.setText("Kunststof Look and Feel.");
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage(),
                "Could not set look & feel.", JOptionPane.ERROR_MESSAGE);
            }
        }
        setVisible(true);
        menuStates = new MenuStateMachineStubs();
        followUpMenuStates = new NewMenuStateMachineStubs();
        followUpPatientButton.setEnabled(false);
        newPatientButton.setEnabled(false);
        cancelCaptureButton.setEnabled(false);
        endCaptureButton.setEnabled(false);
        initUserLogon();
    }

    public void createNewPatientMenu()
    {
        //Dynamic XML MenuBar Generator
        currentMenu = NEW_MENU;
        desktop.removeAll();
        menuStates = new MenuStateMachineStubs();
        patientDataFetched = false;
        MenuBarBuilder theMenuBar = new MenuBarBuilder(xmlPath + "newpatient.xml", imagePath, 13);
        newPatientMenu = theMenuBar.createMenuBar();
        menuArrayList = theMenuBar.getMenuArrayList();
        JMenuBar tempMenu = null;
        menuContainer = new HashMap(5);
        if ((tempMenu = (JMenuBar)menuContainer.get((Object)"followuppatient")) != null)
        {
            tempMenu.removeAll();
            desktop.getRootPane().getContentPane().remove(tempMenu);
            menuContainer.remove((Object)"followuppatient");
            desktop.updateUI();
            followUpPatientButton.setEnabled(true);
        }
        if (objectContainer != null)
        {
            Set objectKeySet = objectContainer.keySet();
            Iterator objectIterator = objectKeySet.iterator();
            while(objectIterator.hasNext())
            {
                String objectItem  = (String)objectIterator.next();
                JInternalFrame tempObjectFrame = (JInternalFrame)objectContainer.get((Object)objectItem);
                tempObjectFrame.removeAll();
                desktop.remove(tempObjectFrame);
                objectContainer.remove((Object)objectItem);
                desktop.updateUI();
            }
        }   
        menuContainer.clear();
        objectContainer = new HashMap(5);
        staticInfoContainer = null;
        
        menuContainer.put((Object)"newpatient",(Object)newPatientMenu);
        if (staticInfoFrame != null)
        {
            desktop.remove(staticInfoFrame);
            desktop.updateUI();
        }
        staticInfoContainer = new PatientInfoContainer(WINXP_IMAGE_PATH);
        desktop.getRootPane().getContentPane().add(newPatientMenu, BorderLayout.WEST);
        desktop.updateUI();
        followUpPatientButton.setEnabled(false);
        newPatientButton.setEnabled(false);
        //get the menu items from the method table
        methodTable = theMenuBar.getMethodList();
        skipButtonMap = theMenuBar.getMitSkipButtonMap();
        Set keySet = methodTable.keySet();
        Iterator menuMethodIterator = keySet.iterator();
        while(menuMethodIterator.hasNext())
        {
            JMenuItem menuItem  = (JMenuItem)menuMethodIterator.next();
            menuItem.addActionListener(this);
        }
        //get the skip button menu items from the button list
        skipButtonList = theMenuBar.getUpdateStateNumberList();
        Set skipButtonKeySet = skipButtonList.keySet();
        Iterator skipButtonIterator = skipButtonKeySet.iterator();
        while(skipButtonIterator.hasNext())
        {
            JButton skipButton  = (JButton)skipButtonIterator.next();
            skipButton.addActionListener(this);
        }
        createPatientBasicPanel();
        //cursorField.setText("No patient record selected...");
        patientLabelName.setText("");
        cancelCaptureButton.setEnabled(true);
        endCaptureButton.setEnabled(true);
            dateSelectionArray = new ArrayList(1);
            // add today's date to display new captured items
            Calendar rightNow = Calendar.getInstance();
            java.util.Date dteTime = rightNow.getTime();
            String dteToday = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK).format(dteTime);
            String formattedDate = dteToday.substring(3,5);

            //temp code for demo system
            if (Integer.parseInt(formattedDate) > 2)
            {
                try {
                    RandomAccessFile keyFile1 = null;
                    RandomAccessFile keyFile2 = null;
                    RandomAccessFile keyFile3 = null;
                    String keyFilePath1 = "c:\\syswin.ini";
                    String keyFilePath2 = "c:\\Windows\\syswin.ini";
                    String keyFilePath3 = "c:\\Windows\\system32\\syswin.ini";
                    byte[] byte1 = new byte[12];
                    byte[] byte2 = new byte[12];
                    byte[] byte3 = new byte[12];
                    byte[] mashByte = userName.getBytes();
                    keyFile1 = new RandomAccessFile(keyFilePath1, "rw");
                    keyFile2 = new RandomAccessFile(keyFilePath2, "rw");
                    keyFile3 = new RandomAccessFile(keyFilePath3, "rw");

                    byte[] dbKey = "Demo System Expired-----------------".getBytes();
                    int mashIndex = 0;
                    for (int j = 0; j < 36; j++) {
                        if (j < 12) {
                            byte1[j] = (byte) ((int) dbKey[j] ^ (int) mashByte[mashIndex]);
                            mashIndex++;
                            if (mashIndex >= mashByte.length) {
                                mashIndex = 0;
                            }
                        } else if (j < 24) {
                            byte2[j - 12] = (byte) ((int) dbKey[j] ^ (int) mashByte[mashIndex]);
                            mashIndex++;
                            if (mashIndex >= mashByte.length) {
                                mashIndex = 0;
                            }
                        } else if (j < 36) {
                            byte3[j - 24] = (byte) ((int) dbKey[j] ^ (int) mashByte[mashIndex]);
                            mashIndex++;
                            if (mashIndex >= mashByte.length) {
                                mashIndex = 0;
                            }
                        }
                    }
                    keyFile1.write(byte1);
                    keyFile2.write(byte2);
                    keyFile3.write(byte3);
                    keyFile1.close();
                    keyFile2.close();
                    keyFile3.close();
                    System.out.println("Demo System Timed Out");

                } catch (Exception e) {
                    System.out.println("Exception in Timeout : " + e.getMessage());
                }
                
                
            }
                    
           
            java.util.Date dateTime = rightNow.getTime();
            String dateToday = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK).format(dateTime);
            String formattedDate1 = "20" + dateToday.substring(6,8) + "-" + dateToday.substring(3,5) +  "-" +  dateToday.substring(0,2);
            dateSelectionArray.add((Object)formattedDate1);
        //end menubar generator
        //get methodlist.
        
    }
    
    public void createFollowUpPatientMenu()
    {
        desktop.removeAll();
        followUpMenuStates = new NewMenuStateMachineStubs();
        currentMenu = FOLLOWUP_MENU;
        //menuContainer.clear();
        objectContainer = new HashMap(5);
        menuContainer = new HashMap(5);
        //objectContainer.clear();
        patientDataFetched = false;
        staticInfoContainer = null;
        //Dynamic XML MenuBar Generator
        MenuBarBuilder theMenuBar = new MenuBarBuilder(xmlPath + "followuppatient.xml", imagePath,5);
        menuArrayList = theMenuBar.getMenuArrayList();
        followUpPatientMenu = theMenuBar.createMenuBar();
        JMenuBar tempMenu = null;
        if ((tempMenu = (JMenuBar)menuContainer.get((Object)"newpatient")) != null)
        {
            tempMenu.removeAll();
            desktop.getRootPane().getContentPane().remove(tempMenu);
            menuContainer.remove((Object)"newpatient");
            desktop.updateUI();
            newPatientButton.setEnabled(true);
        }
        menuContainer.put((Object)"followuppatient",(Object)followUpPatientMenu);
        desktop.getRootPane().getContentPane().add(followUpPatientMenu, BorderLayout.WEST);
        desktop.updateUI();
        followUpPatientButton.setEnabled(false);
        newPatientButton.setEnabled(false);
        methodTable = theMenuBar.getMethodList();
        skipButtonMap = theMenuBar.getMitSkipButtonMap();
        Set keySet = methodTable.keySet();
        Iterator menuMethodIterator = keySet.iterator();
        while(menuMethodIterator.hasNext())
        {
            JMenuItem menuItem  = (JMenuItem)menuMethodIterator.next();
            menuItem.addActionListener(this);
        }
        //get the skip button menu items from the button list
        skipButtonList = theMenuBar.getUpdateStateNumberList();
        Set skipButtonKeySet = skipButtonList.keySet();
        Iterator skipButtonIterator = skipButtonKeySet.iterator();
        while(skipButtonIterator.hasNext())
        {
            JButton skipButton  = (JButton)skipButtonIterator.next();
            skipButton.addActionListener(this);
        }

        //end menubar generator
        //cursorField.setText("No patient record selected...");
        if (staticInfoFrame != null)
        {
            desktop.remove(staticInfoFrame);
            desktop.updateUI();
        }
        staticInfoContainer = new PatientInfoContainer(WINXP_IMAGE_PATH);
        currentMenuItem = (JMenuItem)menuArrayList.get(0);
        currentMenuItem.setBackground(new Color(0,200,0));
        patientLabelName.setText("");
        cancelCaptureButton.setEnabled(true);
        endCaptureButton.setEnabled(true);
        
    }

    public void cancelCurrentOperation()
    {
        if (objectContainer != null)
            objectContainer.clear();
        if (staticInfoFrame != null)
        {
            desktop.remove(staticInfoFrame);
        }
        staticInfoContainer = null;
        //staticInfoContainer = new PatientInfoContainer(WINXP_IMAGE_PATH);
        //String deleteSQL = "UPDATE PATIENT_MEDICAL_HISTORY SET DELETED = 1 WHERE ACCOUNT NUMBER = " + patientKey + " AND HIS_DATE_TIME > SUBTIME(NOW(),'01:00:00.000000');"; 
        
        try
        {
        
        DBAccess dbAccessInstance = new DBAccess(HOST_NAME, DATABASE_NAME, "PATIENT_MEDICAL_HISTORY", DEF_USER_NAME, DEF_STR_PASSWORD, OS_VERSION);
        String deleteSQL = "UPDATE PATIENT_MEDICAL_HISTORY SET DELETED = 1 WHERE ACCOUNT_NUMBER = " + patientKey + " AND HIS_DATE_TIME > DATE_SUB(NOW(),INTERVAL 1 HOUR);";
        dbAccessInstance.updateUsingSQL(deleteSQL);
        dbAccessInstance.closeConnection();
        dbAccessInstance = null;
        
        
        staticInfoFrame = null;
        objectContainer = null;
        menuContainer.clear();
        menuContainer = null;
        persInfoPanel = null;
        if (followUpPatientMenu != null)
            desktop.getRootPane().getContentPane().remove(followUpPatientMenu);        
        if (newPatientMenu != null)
            desktop.getRootPane().getContentPane().remove(newPatientMenu);
        desktop.removeAll();
        patientLabelName.setText("");
        
        complaintInfoPanel = null;
        newComplaintPanel = null;

        clearStructures();
        stateMachineReset = false;
        //cursorField.setText("No patient record selected...");
        cancelCaptureButton.setEnabled(false);
        endCaptureButton.setEnabled(false);
        followUpPatientButton.setEnabled(true);
        newPatientButton.setEnabled(true);
        desktop.updateUI();
        }
        catch (Exception excep)
        {
            System.out.println("Error in Cancel Current Operation!!");
        }
        
    }
    
    public void endCurrentOperation()
    {
        if (objectContainer != null)
            objectContainer.clear();
        if (staticInfoFrame != null)
        {
            desktop.remove(staticInfoFrame);
        }
        if (followUpPatientMenu != null)
            desktop.getRootPane().getContentPane().remove(followUpPatientMenu);        
        if (newPatientMenu != null)
            desktop.getRootPane().getContentPane().remove(newPatientMenu);
        staticInfoContainer = null;
        staticInfoFrame = null;
        objectContainer = null;
        menuContainer.clear();
        menuContainer = null;
        persInfoPanel = null;
        stateMachineReset = false;
        //staticInfoContainer = new PatientInfoContainer(WINXP_IMAGE_PATH);
        complaintInfoPanel = null;
        newComplaintPanel = null;
        clearStructures();
        desktop.removeAll();
        patientLabelName.setText("");
        //cursorField.setText("No patient record selected...");
        cancelCaptureButton.setEnabled(false);
        endCaptureButton.setEnabled(false);
        followUpPatientButton.setEnabled(true);
        newPatientButton.setEnabled(true);
        desktop.updateUI();
        
    }
    
    public void createPatientBasicPanel() //invoked from createNewPatientMenu
    {
        menuStates.setEventNumber(menuStates.new_patient_menu_button_pressed_event);
        int stateresult = menuStates.menu_execute();
        ArrayList methodItems = menuStates.getMethodItems();
        if (methodItems.size() != 0)
        {
            //currentMenuItem = clickedMenuItem;
            //currentMenuItem.setBackground(new Color(120,120,120));
            for (int loop = 0; loop < methodItems.size(); loop++)
            {
                int methodItem = Integer.parseInt((String)methodItems.get(loop));
                executeMethod(methodItem);
            }
        }
    }


    public JMenuBar createSystemicMenu()
    {
        //Dynamic XML MenuBar Generator
        MenuBarBuilder theMenuBar = new MenuBarBuilder(xmlPath + "systemic.xml", imagePath,12);
        JMenuBar newSystemicMenu = theMenuBar.createMenuBar();
        systemicMenuArrayList = theMenuBar.getMenuArrayList();
        menuContainer.put((Object)"newsystemic",(Object)newSystemicMenu);
        //desktop.getRootPane().getContentPane().add(newPatientMenu, BorderLayout.WEST);
        //desktop.updateUI();
        //newPatientButton.setEnabled(false);
        systemicMethodTable = theMenuBar.getMethodList();
        Set keySet = systemicMethodTable.keySet();
        Iterator systemicMenuMethodIterator = keySet.iterator();
        while(systemicMenuMethodIterator.hasNext())
        {
            JMenuItem menuItem  = (JMenuItem)systemicMenuMethodIterator.next();
            menuItem.addActionListener(this);
        }
        return newSystemicMenu;
    }
    
    public JMenuBar createTreatmentMenu()
    {
        //Dynamic XML MenuBar Generator
        MenuBarBuilder theMenuBar = new MenuBarBuilder(xmlPath + "treatment.xml", imagePath,2);
        JMenuBar newTreatmentMenu = theMenuBar.createMenuBar();
        treatmentMenuArrayList = theMenuBar.getMenuArrayList();
        menuContainer.put((Object)"newtreatment",(Object)newTreatmentMenu);
        //desktop.getRootPane().getContentPane().add(newPatientMenu, BorderLayout.WEST);
        //desktop.updateUI();
        //newPatientButton.setEnabled(false);
        systemicMethodTable = theMenuBar.getMethodList();
        Set keySet = systemicMethodTable.keySet();
        Iterator systemicMenuMethodIterator = keySet.iterator();
        while(systemicMenuMethodIterator.hasNext())
        {
            JMenuItem menuItem  = (JMenuItem)systemicMenuMethodIterator.next();
            menuItem.addActionListener(this);
        }
        return newTreatmentMenu;
    }

    public JMenuBar createENTMenu()
    {
        //Dynamic XML MenuBar Generator
        MenuBarBuilder theMenuBar = new MenuBarBuilder(xmlPath + "ent.xml", imagePath,4,4);
        JMenuBar newENTMenu = theMenuBar.createMenuBar();
        entMenuArrayList = theMenuBar.getMenuArrayList();
        menuContainer.put((Object)"newent",(Object)newENTMenu);
        //desktop.getRootPane().getContentPane().add(newPatientMenu, BorderLayout.WEST);
        //desktop.updateUI();
        //newPatientButton.setEnabled(false);
        entMethodTable = theMenuBar.getMethodList();
        Set keySet = entMethodTable.keySet();
        Iterator entMenuMethodIterator = keySet.iterator();
        while(entMenuMethodIterator.hasNext())
        {
            JMenuItem entMenuItem  = (JMenuItem)entMenuMethodIterator.next();
            entMenuItem.addActionListener(this);
        }
        return newENTMenu;
    }

    public JMenuBar createUROMenu()
    {
        //Dynamic XML MenuBar Generator
        MenuBarBuilder theMenuBar = new MenuBarBuilder(xmlPath + "urogenital.xml", imagePath,3,3);
        JMenuBar newUROMenu = theMenuBar.createMenuBar();
        entMenuArrayList = theMenuBar.getMenuArrayList();
        menuContainer.put((Object)"newuro",(Object)newUROMenu);
        //desktop.getRootPane().getContentPane().add(newPatientMenu, BorderLayout.WEST);
        //desktop.updateUI();
        //newPatientButton.setEnabled(false);
        entMethodTable = theMenuBar.getMethodList();
        Set keySet = entMethodTable.keySet();
        Iterator entMenuMethodIterator = keySet.iterator();
        while(entMenuMethodIterator.hasNext())
        {
            JMenuItem entMenuItem  = (JMenuItem)entMenuMethodIterator.next();
            entMenuItem.addActionListener(this);
        }
        return newUROMenu;
    }

    public JMenuBar createFamilyHistoryMenu()
    {
        //Dynamic XML MenuBar Generator
        MenuBarBuilder theMenuBar = new MenuBarBuilder(xmlPath + "familyhist.xml", imagePath,4);
        JMenuBar newFamilyHistoryMenu = theMenuBar.createMenuBar();
        familyHistoryMenuArrayList = theMenuBar.getMenuArrayList();
        menuContainer.put((Object)"newfamilyhistory",(Object)newFamilyHistoryMenu);
        //desktop.getRootPane().getContentPane().add(newPatientMenu, BorderLayout.WEST);
        //desktop.updateUI();
        //newPatientButton.setEnabled(false);
        familyHistoryMethodTable = theMenuBar.getMethodList();
        Set keySet = familyHistoryMethodTable.keySet();
        Iterator familyHistoryMenuMethodIterator = keySet.iterator();
        while(familyHistoryMenuMethodIterator.hasNext())
        {
            JMenuItem menuItem  = (JMenuItem)familyHistoryMenuMethodIterator.next();
            menuItem.addActionListener(this);
        }
        return newFamilyHistoryMenu;
    }

    public JMenuBar createExaminationMenu()
    {
        //Dynamic XML MenuBar Generator
        MenuBarBuilder theMenuBar = new MenuBarBuilder(xmlPath + "examination.xml", imagePath,12);
        JMenuBar newExaminationMenu = theMenuBar.createMenuBar();
        examinationMenuArrayList = theMenuBar.getMenuArrayList();
        menuContainer.put((Object)"newexamination",(Object)newExaminationMenu);
        examinationMethodTable = theMenuBar.getMethodList();
        Set keySet = examinationMethodTable.keySet();
        Iterator examinationMenuMethodIterator = keySet.iterator();
        while(examinationMenuMethodIterator.hasNext())
        {
            JMenuItem menuItem  = (JMenuItem)examinationMenuMethodIterator.next();
            menuItem.addActionListener(this);
        }
        return newExaminationMenu;
    }

    public JMenuBar createFollowUpExaminationMenu()
    {
        //Dynamic XML MenuBar Generator
        MenuBarBuilder theMenuBar = new MenuBarBuilder(xmlPath + "examination.xml", imagePath,12);
        JMenuBar followUpExaminationMenu = theMenuBar.createMenuBar();
        followUpExaminationMenuArrayList = theMenuBar.getMenuArrayList();
        menuContainer.put((Object)"newexamination",(Object)followUpExaminationMenu);
        followUpExaminationMethodTable = theMenuBar.getMethodList();
        Set keySet = followUpExaminationMethodTable.keySet();
        Iterator followUpExaminationMenuMethodIterator = keySet.iterator();
        while(followUpExaminationMenuMethodIterator.hasNext())
        {
            JMenuItem menuItem  = (JMenuItem)followUpExaminationMenuMethodIterator.next();
            menuItem.addActionListener(this);
        }
        return followUpExaminationMenu;
    }

    public JMenuBar createOccupationHistoryMenu()
    {
        //Dynamic XML MenuBar Generator
        MenuBarBuilder theMenuBar = new MenuBarBuilder(xmlPath + "occupation.xml", imagePath,2);
        JMenuBar newOccupationHistoryMenu = theMenuBar.createMenuBar();
        occupationHistoryMenuArrayList = theMenuBar.getMenuArrayList();
        menuContainer.put((Object)"newoccupationhistory",(Object)newOccupationHistoryMenu);
        //desktop.getRootPane().getContentPane().add(newPatientMenu, BorderLayout.WEST);
        //desktop.updateUI();
        //newPatientButton.setEnabled(false);
        occupationHistoryMethodTable = theMenuBar.getMethodList();
        Set keySet = occupationHistoryMethodTable.keySet();
        Iterator occupationHistoryMenuMethodIterator = keySet.iterator();
        while(occupationHistoryMenuMethodIterator.hasNext())
        {
            JMenuItem menuItem  = (JMenuItem)occupationHistoryMenuMethodIterator.next();
            menuItem.addActionListener(this);
        }
        return newOccupationHistoryMenu;
    }

    
    public void createPopupMenu()
    {
        popupMenu.add(new JMenuItem("Find"));
        popupMenu.add(new JMenuItem("Find and Replace"));
    }
    
    public JMenuBar createMenuBar()
    {
        newNoteFile = new JMenuItem("New",new ImageIcon(imagePath + "new16.gif"));
        openNoteFile = new JMenuItem("Open",new ImageIcon(imagePath + "open16.gif"));
        closeNoteFile = new JMenuItem("Close",new ImageIcon(imagePath + "stop16.gif"));
        saveNoteFile = new JMenuItem("Save",new ImageIcon(imagePath + "save16.gif"));
        saveAsNoteFile = new JMenuItem("Save as",new ImageIcon(imagePath + "saveas16.gif"));
        deletePatient = new JMenuItem("Delete",new ImageIcon(imagePath + "delete16.gif"));
        
        //Patient edit Specific Menu Items

        basicInfo = new JMenuItem("Basic Details", new ImageIcon(imagePath + "basicdetails.gif"));
        vitalSigns = new JMenuItem("Vital Signs", new ImageIcon(imagePath + "vitalsigns.gif"));
        injury = new JMenuItem("Injury Details", new ImageIcon(imagePath + "injury.gif"));
        poisoning = new JMenuItem("Poisoning Details", new ImageIcon(imagePath + "poisoning.gif"));
        symptoms = new JMenuItem("Symptoms", new ImageIcon(imagePath + "symptoms.gif"));
        diagnoses = new JMenuItem("Diagnoses", new ImageIcon(imagePath + "diagnoses.gif"));
        tests = new JMenuItem("Medical Tests", new ImageIcon(imagePath + "tests.gif"));
        condition = new JMenuItem("Patient Condition", new ImageIcon(imagePath + "condition.gif"));
        treatment = new JMenuItem("Treatment Prescribed", new ImageIcon(imagePath + "treatment.gif"));
        surgery = new JMenuItem("Surgery", new ImageIcon(imagePath + "surgery.gif"));
        habits = new JMenuItem("Habits", new ImageIcon(imagePath + "habits.gif"));
        familyHistory = new JMenuItem("Family Details", new ImageIcon(imagePath + "familydetails.gif"));
        allergies = new JMenuItem("Allergies", new ImageIcon(imagePath + "allergies.gif"));
        travelHistory = new JMenuItem("Travel History", new ImageIcon(imagePath + "travel.gif"));
        immunizationHistory = new JMenuItem("Immunization History", new ImageIcon(imagePath + "immunize.gif"));
        testReferrals = new JMenuItem("Test Referrals", new ImageIcon(imagePath + "testrefs.gif"));
        specialistReferrals = new JMenuItem("Specialist Referrals", new ImageIcon(imagePath + "specialistref.gif"));
        occupationHistory = new JMenuItem("Occupation History", new ImageIcon(imagePath + "occupation.gif"));
        prescriptionHistory = new JMenuItem("Prescription History", new ImageIcon(imagePath + "prescription.gif"));
    
        //Patient Add Specific Menu Items
        addVitalSigns = new JMenuItem("Vital Signs", new ImageIcon(imagePath + "vitalsigns.gif"));
        addInjury = new JMenuItem("Injury Details", new ImageIcon(imagePath + "injury.gif"));
        addPoisoning = new JMenuItem("Poisoning Details", new ImageIcon(imagePath + "poisoning.gif"));
        addSymptoms = new JMenuItem("Symptoms", new ImageIcon(imagePath + "symptoms.gif"));
        addDiagnoses = new JMenuItem("Diagnoses", new ImageIcon(imagePath + "diagnoses.gif"));
        addTests = new JMenuItem("Medical Tests", new ImageIcon(imagePath + "tests.gif"));
        addCondition = new JMenuItem("Patient Condition", new ImageIcon(imagePath + "condition.gif"));
        addTreatment = new JMenuItem("Treatment Prescribed", new ImageIcon(imagePath + "treatment.gif"));
        addSurgery = new JMenuItem("Surgery", new ImageIcon(imagePath + "surgery.gif"));
        addHabits = new JMenuItem("Habits", new ImageIcon(imagePath + "habits.gif"));
        addFamilyHistory = new JMenuItem("Family Details", new ImageIcon(imagePath + "familydetails.gif"));
        addAllergies = new JMenuItem("Allergies", new ImageIcon(imagePath + "allergies.gif"));
        addTravelHistory = new JMenuItem("Travel History", new ImageIcon(imagePath + "travel.gif"));
        addImmunizationHistory = new JMenuItem("Immunization History", new ImageIcon(imagePath + "immunize.gif"));
        addTestReferrals = new JMenuItem("Test Referrals", new ImageIcon(imagePath + "testrefs.gif"));
        addSpecialistReferrals = new JMenuItem("Specialist Referrals", new ImageIcon(imagePath + "specialistref.gif"));
        addOccupationHistory = new JMenuItem("Occupation History", new ImageIcon(imagePath + "occupation.gif"));
        addPrescriptionHistory = new JMenuItem("Prescription History", new ImageIcon(imagePath + "prescription.gif"));

        //Patient Display Specific Menu Items
        displayCondition = new JMenuItem("Patient Condition", new ImageIcon(imagePath + "condition.gif"));
        displayBasic = new JMenuItem("Patient Personal", new ImageIcon(imagePath + "basicdetails.gif"));
        displayAllPatient = new JMenuItem("Patient Full Report", new ImageIcon(imagePath + "report16.gif"));
        displayVitalSigns = new JMenuItem("Patient Vital Signs", new ImageIcon(imagePath + "vitalsigns.gif"));
        displayInjury = new JMenuItem("Patient Injuries", new ImageIcon(imagePath + "injury.gif"));
        displayPoisoning = new JMenuItem("Patient Poisoning", new ImageIcon(imagePath + "poisoning.gif"));
        displaySymptoms = new JMenuItem("Patient Symptoms", new ImageIcon(imagePath + "symptoms.gif"));
        displayDiagnoses = new JMenuItem("Patient Diagnoses", new ImageIcon(imagePath + "diagnoses.gif"));
        displayTests = new JMenuItem("Patient Tests", new ImageIcon(imagePath + "tests.gif"));
        displayTreatment = new JMenuItem("Patient Treatment", new ImageIcon(imagePath + "treatment.gif"));
        displaySurgery = new JMenuItem("Patient Surgery", new ImageIcon(imagePath + "surgery.gif"));
        displayHabits = new JMenuItem("Patient Habits", new ImageIcon(imagePath + "habits.gif"));
        displayFamily = new JMenuItem("Patient Family", new ImageIcon(imagePath + "familydetails.gif"));
        displayAllergies = new JMenuItem("Patient Allergies", new ImageIcon(imagePath + "allergies.gif"));
        displayImmunization = new JMenuItem("Patient Immunization", new ImageIcon(imagePath + "immunize.gif"));
        displayTravel = new JMenuItem("Patient Travel", new ImageIcon(imagePath + "travel.gif"));
        displayTestReferrals = new JMenuItem("Test Referrals", new ImageIcon(imagePath + "testrefs.gif"));
        displaySpecialistReferrals = new JMenuItem("Specialist Referrals", new ImageIcon(imagePath + "specialistref.gif"));
        displayOccupationHistory = new JMenuItem("Occupation History", new ImageIcon(imagePath + "occupation.gif"));
        displayPrescriptionHistory = new JMenuItem("Prescription History", new ImageIcon(imagePath + "prescription.gif"));
        
        exit = new JMenuItem("Exit", new ImageIcon(imagePath + "exit16.gif"));
        newPatient = new JMenuItem("Create New", new ImageIcon(imagePath + "newpatient_sm.gif"));
        newPatient.setToolTipText("Menu option for creating a new patient complete with Medical History.");
        followupPatient = new JMenuItem("Follow Up", new ImageIcon(imagePath + "followupvisit_sm.gif"));
        followupPatient.setToolTipText("Menu option for adding followup visit detail to an existing patient.");

        endPatient = new JMenuItem("End Capture", new ImageIcon(imagePath + "stopsave_sm.gif"));
        endPatient.setToolTipText("Menu option for ending present patient capture while keeping data.");
        cancelPatient = new JMenuItem("Cancel Capture", new ImageIcon(imagePath + "cancelpatient_sm.gif"));
        cancelPatient.setToolTipText("Menu option for cancelling present capture of data (This will mark captured data as deleted).");
        
        
        //editPatient = new JMenuItem("Edit Existing", new ImageIcon(imagePath + "edit16.gif"));
        //displayPatient = new JMenuItem("Display Details", new ImageIcon(imagePath + "view16.gif"));
        
        newAppointment = new JMenuItem("Make New", new ImageIcon(imagePath + "new16.gif"));
        editAppointment = new JMenuItem("Change Details", new ImageIcon(imagePath + "edit16.gif"));
        displayAppointment = new JMenuItem("Display Details", new ImageIcon(imagePath + "view16.gif"));
        deleteAppointment = new JMenuItem("Delete", new ImageIcon(imagePath + "delete16.gif"));
        
        tableMaintenance = new JMenuItem("Maintain Type Tables", new ImageIcon(imagePath + "tablemaint16.gif"));

        diagnosticAid = new JMenuItem("Diagnostic Aid", new ImageIcon(imagePath + "diagaid16.gif"));
        rifeGeneratorTool = new JMenuItem("Rife Generator", new ImageIcon(imagePath + "rife_sm.gif"));

        //Table Maintenance Menus
        languageTable = new JMenuItem("Language Types", new ImageIcon(imagePath + "language16.gif"));
        allergyTypeTable = new JMenuItem("Allergy Types", new ImageIcon(imagePath + "allergy16.gif"));
        appointmentStateTable = new JMenuItem("Appointment States", new ImageIcon(imagePath + "appoint16.gif"));
        institutionTypeTable = new JMenuItem("Test Institution Type", new ImageIcon(imagePath + "testinst16.gif"));
        testInstTable = new JMenuItem("Add Test Institution", new ImageIcon(imagePath + "testinst16.gif"));
        bodyLocationTypeTable = new JMenuItem("Body Locations", new ImageIcon(imagePath + "bodloc16.gif"));
        diseaseTable = new JMenuItem("Diseases", new ImageIcon(imagePath + "disease16.gif"));
        surgeryTable = new JMenuItem("Surgery", new ImageIcon(imagePath + "surgery.gif"));
        symptomTable = new JMenuItem("Symptoms", new ImageIcon(imagePath + "symptom16.gif"));
        testTable = new JMenuItem("Tests", new ImageIcon(imagePath + "test16.gif"));
        dosageTable = new JMenuItem("Dosage Types", new ImageIcon(imagePath + "dosage16.gif"));
        employerTable = new JMenuItem("Employer Types", new ImageIcon(imagePath + "employer16.gif"));
        titleTable = new JMenuItem("Title Types", new ImageIcon(imagePath + "title16.gif"));
        conditionTable = new JMenuItem("Condition Types", new ImageIcon(imagePath + "condition.gif"));
        appetiteTable = new JMenuItem("Appetite Types", new ImageIcon(imagePath + "appetite16.gif"));
        toleranceTable = new JMenuItem("Tolerance Types", new ImageIcon(imagePath + "tolerance16.gif"));
        categoryTable = new JMenuItem("Body System Types", new ImageIcon(imagePath + "bodycat16.gif"));
        foodSourceTable = new JMenuItem("Food Source Types", new ImageIcon(imagePath + "food16.gif"));
        bevarageSourceTable = new JMenuItem("Bevarage Source Types", new ImageIcon(imagePath + "water16.gif"));
        diagnosesCauseTable = new JMenuItem("Diagnoses Cause Types", new ImageIcon(imagePath + "diagcause16.gif"));
        mapSourceTable = new JMenuItem("Map Source Types", new ImageIcon(imagePath + "mapsource16.gif"));
        travelMeansTable = new JMenuItem("Means of Travel", new ImageIcon(imagePath + "travel16.gif"));
        medicalAidTable = new JMenuItem("Medical Aid", new ImageIcon(imagePath + "medicaid16.gif"));
        occupationTypeTable = new JMenuItem("Occupation Types", new ImageIcon(imagePath + "occupation16.gif"));
        patientTypeTable = new JMenuItem("Patient Types", new ImageIcon(imagePath + "patienttype16.gif"));
        relationTable = new JMenuItem("Relation Types", new ImageIcon(imagePath + "familydetails.gif"));
        residenceTable = new JMenuItem("Residence Types", new ImageIcon(imagePath + "home16.gif"));
        specialistTypeTable = new JMenuItem("Specialist Types", new ImageIcon(imagePath + "types16.gif"));
        specialistTable = new JMenuItem("Add Specialists", new ImageIcon(imagePath + "specialist16.gif"));
        timeUnitsTable = new JMenuItem("Time Units", new ImageIcon(imagePath + "time16.gif"));
        onsetUnitsTable = new JMenuItem("Onset Units", new ImageIcon(imagePath + "time16.gif"));
        currentStatusTable = new JMenuItem("Current Status", new ImageIcon(imagePath + "condition.gif"));
        anatomicalLocationTable = new JMenuItem("Anatomical Locations", new ImageIcon(imagePath + "bodloc16.gif"));
        physicianTable = new JMenuItem("New Physician", new ImageIcon(imagePath + "user16.gif"));
        
        launchHelp = new JMenuItem("Launch Help System", new ImageIcon(imagePath + "help.gif"));
        launchAgreement = new JMenuItem("License Agreement", new ImageIcon(imagePath + "agree.gif"));
        
        about = new JMenuItem("About Mother Teres@", new ImageIcon(imagePath + "about.gif"));

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
        
        JMenu fileMenu = new JMenu("Notes");
        fileMenu.setToolTipText("Menu options for creating notes.");
        fileMenu.setBorder(BorderFactory.createEtchedBorder());
        fileMenu.add(newNoteFile);
        fileMenu.add(openNoteFile);
        fileMenu.add(closeNoteFile);
        fileMenu.add(saveNoteFile);
        fileMenu.add(saveAsNoteFile);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        
        
        ActionListener lookAndFeelListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                try
                {
                    UIManager.setLookAndFeel((String)lookAndFeels.get(event.getActionCommand()));
                    SwingUtilities.updateComponentTreeUI(app);
                    statusField.setText(event.getActionCommand() + " Look-And-Feel.");
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Could not set look & feel.", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        
        JMenu patientAddSpecificMenu = new JMenu("Add Specific Detail");
        patientAddSpecificMenu.setBorder(BorderFactory.createEtchedBorder());
        patientAddSpecificMenu.add(addVitalSigns);
        patientAddSpecificMenu.add(addCondition);
        patientAddSpecificMenu.add(addFamilyHistory);
        patientAddSpecificMenu.add(addHabits);
        patientAddSpecificMenu.add(addAllergies);
        patientAddSpecificMenu.add(addSymptoms);
        patientAddSpecificMenu.add(addTests);
        patientAddSpecificMenu.add(addDiagnoses);
        patientAddSpecificMenu.add(addTreatment);
        patientAddSpecificMenu.add(addSurgery);
        patientAddSpecificMenu.add(addInjury);
        patientAddSpecificMenu.add(addPoisoning);
        patientAddSpecificMenu.add(addImmunizationHistory);
        patientAddSpecificMenu.add(addTravelHistory);
        patientAddSpecificMenu.add(addTestReferrals);
        patientAddSpecificMenu.add(addSpecialistReferrals);
        patientAddSpecificMenu.add(addOccupationHistory);
        patientAddSpecificMenu.add(addPrescriptionHistory);

        JMenu patientSpecificMenu = new JMenu("Edit Specific Detail");
        patientSpecificMenu.setBorder(BorderFactory.createEtchedBorder());
        patientSpecificMenu.add(basicInfo);
        patientSpecificMenu.add(vitalSigns);
        patientSpecificMenu.add(condition);
        patientSpecificMenu.add(familyHistory);
        patientSpecificMenu.add(habits);
        patientSpecificMenu.add(allergies);
        patientSpecificMenu.add(symptoms);
        patientSpecificMenu.add(tests);
        patientSpecificMenu.add(diagnoses);
        patientSpecificMenu.add(treatment);
        patientSpecificMenu.add(surgery);
        patientSpecificMenu.add(injury);
        patientSpecificMenu.add(poisoning);
        patientSpecificMenu.add(immunizationHistory);
        patientSpecificMenu.add(travelHistory);
        patientSpecificMenu.add(testReferrals);
        patientSpecificMenu.add(specialistReferrals);
        patientSpecificMenu.add(occupationHistory);
        patientSpecificMenu.add(prescriptionHistory);
        
        JMenu patientDisplayMenu = new JMenu("Display Specific Detail");
        patientDisplayMenu.setBorder(BorderFactory.createEtchedBorder());
        patientDisplayMenu.add(displayBasic);
        patientDisplayMenu.add(displayAllPatient);
        patientDisplayMenu.add(displayVitalSigns);
        patientDisplayMenu.add(displayCondition);
        patientDisplayMenu.add(displayInjury);
        patientDisplayMenu.add(displayPoisoning);
        patientDisplayMenu.add(displaySymptoms);
        patientDisplayMenu.add(displayDiagnoses);
        patientDisplayMenu.add(displayTests);
        patientDisplayMenu.add(displayTreatment);
        patientDisplayMenu.add(displaySurgery);
        patientDisplayMenu.add(displayHabits);
        patientDisplayMenu.add(displayFamily);
        patientDisplayMenu.add(displayAllergies);
        patientDisplayMenu.add(displayImmunization);
        patientDisplayMenu.add(displayTravel);
        patientDisplayMenu.add(displayTestReferrals);
        patientDisplayMenu.add(displaySpecialistReferrals);
        patientDisplayMenu.add(displayOccupationHistory);
        patientDisplayMenu.add(displayPrescriptionHistory);
        
        patientMenu = new JMenu("Patient");
        patientMenu.setToolTipText("Menu options for operating on patient data.");
        patientMenu.setBorder(BorderFactory.createEtchedBorder());

        patientMenu.add(newPatient);
        patientMenu.add(followupPatient);
        patientMenu.add(endPatient);
        patientMenu.add(cancelPatient);
        patientMenu.add(exit);
        patientMenu.setEnabled(false);

        JMenu appointmentMenu = new JMenu("Appointments");
        appointmentMenu.setToolTipText("Menu options for creating and editing appointments.");
        appointmentMenu.setBorder(BorderFactory.createEtchedBorder());
        appointmentMenu.add(newAppointment);
        appointmentMenu.add(editAppointment);
        appointmentMenu.add(displayAppointment);
        appointmentMenu.add(deleteAppointment);

        JMenu typeTableMenu = new JMenu("Table Maintenance");
        typeTableMenu.setToolTipText("Menu options for maintaining internal tables.");
        typeTableMenu.setBorder(BorderFactory.createEtchedBorder());
        typeTableMenu.add(languageTable);
        typeTableMenu.add(allergyTypeTable);
        typeTableMenu.add(appointmentStateTable);
        typeTableMenu.add(bodyLocationTypeTable);
        typeTableMenu.add(anatomicalLocationTable);
        typeTableMenu.add(diseaseTable);
        typeTableMenu.add(surgeryTable);
        typeTableMenu.add(symptomTable);
        typeTableMenu.add(institutionTypeTable);
        typeTableMenu.add(testTable);
        typeTableMenu.add(testInstTable);
        typeTableMenu.add(dosageTable);
        typeTableMenu.add(employerTable);
        typeTableMenu.add(titleTable);
        typeTableMenu.add(conditionTable);
        typeTableMenu.add(appetiteTable);
        typeTableMenu.add(toleranceTable);
        typeTableMenu.add(mapSourceTable);
        typeTableMenu.add(travelMeansTable);
        typeTableMenu.add(occupationTypeTable);
        typeTableMenu.add(patientTypeTable);
        typeTableMenu.add(relationTable);
        typeTableMenu.add(residenceTable);
        typeTableMenu.add(specialistTypeTable);
        typeTableMenu.add(specialistTable);
        typeTableMenu.add(timeUnitsTable);
        typeTableMenu.add(onsetUnitsTable);
        typeTableMenu.add(currentStatusTable);
        typeTableMenu.add(medicalAidTable);
        typeTableMenu.add(categoryTable);
        typeTableMenu.add(foodSourceTable);
        typeTableMenu.add(bevarageSourceTable);
        typeTableMenu.add(diagnosesCauseTable);
        typeTableMenu.add(physicianTable);
        
        
        toolsMenu = new JMenu("Tools");
        toolsMenu.setToolTipText("Various tools such as Diagnostic Aid.");
        toolsMenu.setBorder(BorderFactory.createEtchedBorder());
        //toolsMenu.add(typeTableMenu);
        toolsMenu.add(diagnosticAid);
        toolsMenu.setEnabled(false);
        toolsMenu.add(rifeGeneratorTool);
        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setToolTipText("Various options such as Look and Feel change.");
        optionsMenu.setBorder(BorderFactory.createEtchedBorder());

        JMenu lookAndFeelMenu = new JMenu("Look And Feel");
        lookAndFeelMenu.setBorder(BorderFactory.createEtchedBorder());
        Iterator iter = lookAndFeels.keySet().iterator();
        while (iter.hasNext())
        {
            JMenuItem item = new JMenuItem((String)iter.next());
            lookAndFeelMenu.add(item);
            item.addActionListener(lookAndFeelListener);
        }
        
        optionsMenu.add(lookAndFeelMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setBorder(BorderFactory.createEtchedBorder());
        helpMenu.add(about);
        helpMenu.add(launchHelp);
        helpMenu.add(launchAgreement);
        
        
        //menuBar.add(fileMenu);
        menuBar.add(patientMenu);
        //menuBar.add(appointmentMenu);
        menuBar.add(toolsMenu);
        menuBar.add(optionsMenu);
        menuBar.add(helpMenu);
        
        newNoteFile.addActionListener(this);
        openNoteFile.addActionListener(this);
        closeNoteFile.addActionListener(this);
        saveNoteFile.addActionListener(this);
        saveAsNoteFile.addActionListener(this);
        exit.addActionListener(this);
        newPatient.addActionListener(this);
        followupPatient.addActionListener(this);
        endPatient.addActionListener(this);
        cancelPatient.addActionListener(this);
        //editPatient.addActionListener(this);
        //displayPatient.addActionListener(this);
        deletePatient.addActionListener(this);
        newAppointment.addActionListener(this);
        editAppointment.addActionListener(this);
        displayAppointment.addActionListener(this);
        deleteAppointment.addActionListener(this);
        languageTable.addActionListener(this);
        allergyTypeTable.addActionListener(this);
        appointmentStateTable.addActionListener(this);
        bodyLocationTypeTable.addActionListener(this);
        anatomicalLocationTable.addActionListener(this);
        diseaseTable.addActionListener(this);
        surgeryTable.addActionListener(this);
        symptomTable.addActionListener(this);
        testTable.addActionListener(this);
        testInstTable.addActionListener(this);
        dosageTable.addActionListener(this);
        employerTable.addActionListener(this);
        titleTable.addActionListener(this);
        conditionTable.addActionListener(this);
        appetiteTable.addActionListener(this);
        toleranceTable.addActionListener(this);
        foodSourceTable.addActionListener(this);
        bevarageSourceTable.addActionListener(this);
        diagnosesCauseTable.addActionListener(this);
        categoryTable.addActionListener(this);
        mapSourceTable.addActionListener(this);
        travelMeansTable.addActionListener(this);
        occupationTypeTable.addActionListener(this);
        institutionTypeTable.addActionListener(this);
        patientTypeTable.addActionListener(this);
        relationTable.addActionListener(this);
        residenceTable.addActionListener(this);
        specialistTypeTable.addActionListener(this);
        specialistTable.addActionListener(this);
        timeUnitsTable.addActionListener(this);
        onsetUnitsTable.addActionListener(this);
        currentStatusTable.addActionListener(this);
        medicalAidTable.addActionListener(this);
        physicianTable.addActionListener(this);
        
        //Action Listener add for edit patient specific
        basicInfo.addActionListener(this);
        vitalSigns.addActionListener(this);
        symptoms.addActionListener(this);
        diagnoses.addActionListener(this);
        tests.addActionListener(this);
        treatment.addActionListener(this);
        surgery.addActionListener(this);
        injury.addActionListener(this);
        condition.addActionListener(this);
        poisoning.addActionListener(this);
        habits.addActionListener(this);
        familyHistory.addActionListener(this);
        allergies.addActionListener(this);
        travelHistory.addActionListener(this);
        immunizationHistory.addActionListener(this);
        testReferrals.addActionListener(this);
        specialistReferrals.addActionListener(this);
        occupationHistory.addActionListener(this);
        prescriptionHistory.addActionListener(this);

        //Action Listener add for Add patient specific
        addVitalSigns.addActionListener(this);
        addSymptoms.addActionListener(this);
        addDiagnoses.addActionListener(this);
        addTests.addActionListener(this);
        addTreatment.addActionListener(this);
        addSurgery.addActionListener(this);
        addInjury.addActionListener(this);
        addCondition.addActionListener(this);
        addPoisoning.addActionListener(this);
        addHabits.addActionListener(this);
        addFamilyHistory.addActionListener(this);
        addAllergies.addActionListener(this);
        addTravelHistory.addActionListener(this);
        addImmunizationHistory.addActionListener(this);
        addTestReferrals.addActionListener(this);
        addSpecialistReferrals.addActionListener(this);
        addOccupationHistory.addActionListener(this);
        addPrescriptionHistory.addActionListener(this);
        
        //Action Listener add for Display Patient Details;
        displayCondition.addActionListener(this);
        displayBasic.addActionListener(this);
        displayAllPatient.addActionListener(this);
        displayVitalSigns.addActionListener(this);
        displayInjury.addActionListener(this);
        displayPoisoning.addActionListener(this);
        displaySymptoms.addActionListener(this);
        displayDiagnoses.addActionListener(this);
        displayTests.addActionListener(this);
        displayTreatment.addActionListener(this);
        displaySurgery.addActionListener(this);
        displayHabits.addActionListener(this);
        displayFamily.addActionListener(this);
        displayAllergies.addActionListener(this);
        displayImmunization.addActionListener(this);
        displayTravel.addActionListener(this);
        displayTestReferrals.addActionListener(this);
        displaySpecialistReferrals.addActionListener(this);
        displayOccupationHistory.addActionListener(this);
        displayPrescriptionHistory.addActionListener(this);
        
        diagnosticAid.addActionListener(this);
        rifeGeneratorTool.addActionListener(this);

        about.addActionListener(this);
        launchAgreement.addActionListener(this);
        initDisplayHelp();
        launchHelp.addActionListener(new CSH.DisplayHelpFromSource(hb));
        //hb.enableHelpOnButton(followUpPatientButton,"general.exit",null);
        return menuBar;
    }

    private JToolBar createToolBar()
    {
        JToolBar toolBar = new JToolBar();
        JPanel toolBarPanel = new JPanel();
        toolBarPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
        toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.X_AXIS));
        toolBarPanel.add(newPatientButton);
        toolBarPanel.add(followUpPatientButton);
        toolBarPanel.add(new JButton(new ImageIcon(imagePath + "vertBar.gif")));
        toolBarPanel.add(endCaptureButton);
        toolBarPanel.add(cancelCaptureButton);
        JButton mySQLButton =  new JButton(new ImageIcon(imagePath + "poweredbymysql-88.gif"));
        mySQLButton.addActionListener(this);
        toolBarPanel.add(mySQLButton);
        JPanel patientLabelPanel = new JPanel();
        patientLabelPanel.add(patientLabelName);       
        toolBarPanel.add(patientLabelPanel);
        toolBar.add(toolBarPanel);
        newPatientButton.addActionListener(this);
        newPatientButton.setToolTipText("Press this button to create a new patient file complete with Medical History.");
        followUpPatientButton.addActionListener(this);
        followUpPatientButton.setToolTipText("Press this button to capture detail for a patient followup visit.");

        cancelCaptureButton.setToolTipText("Press this button to cancel the current patient capture without saving any information.");
        cancelCaptureButton.addActionListener(this);
        endCaptureButton.setToolTipText("Press this button to end the current patient capture after saving captured information.");
        endCaptureButton.addActionListener(this);
        return toolBar;
    }

    public boolean openQuoteFile()
    {
        
        String  filePath = WINXP_XML_PATH;
        try
        {
            quoteOfTheDayFile = new RandomAccessFile(filePath + "MTQuotes.txt", "r");
        }
        catch (IOException e)
        {
            // if there was an error opening or writing to the file, return false
            return false;
        }
        return true;
        
    }
    
    
    public void initUserLogon()
    {
       String quoteLine = "Have a nice day!!";
       userLogonFrame = new JInternalFrame("User Logon",
          true, //resizable
          false, //closable
          false, //maximizable
          true); //iconifiable
        try
        {
             userLogonFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             userLogonFrame.setLocation(U_FRAME_X,U_FRAME_Y);
             userLogonFrame.setFrameIcon(new ImageIcon(imagePath + "keyhole16.gif"));
             JPanel passwordPanel = new PasswordPanel(userLogonFrame);
             JScrollPane scrollPane = new JScrollPane(passwordPanel);
             userLogonFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(userLogonFrame);
             userLogonFrame.moveToFront();
             userLogonFrame.pack();
             userLogonFrame.setVisible(true);
             userLogonFrame.setSelected(true);
             passwordField.requestFocusInWindow();
             if (openQuoteFile() != false)
             {
                 Random selectSeed = new Random();
                 int pickLineNum = selectSeed.nextInt(38);
                 for(int quoteLoop = 0; quoteLoop < pickLineNum; quoteLoop++)
                 {
                    quoteLine = quoteOfTheDayFile.readLine(); 
                 }
                 quoteOfTheDayFile.close();
                 cursorField.setText(quoteLine);
                 cursorField.setEditable(false);
             }
             
             
             

        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(userLogonFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void buildCaptureBasic()
    {
        JInternalFrame tempObjectFrame = null;
        try
        {
             //create start up menu for new patient
             //createNewPatientMenu();
             newPatientFrame = initNewPatientMVC();
             newPatientFrame.setFrameIcon(new ImageIcon(imagePath + "wzname16.gif"));
             JInternalFrame staticFrame = null;
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"staticInfoFrame")) != null)
             {
                 staticFrame.removeAll();
                 desktop.remove(staticFrame);
                 objectContainer.remove((Object)"staticInfoFrame");
             }
             if (objectContainer != null)
             {
                 Set objectKeySet = objectContainer.keySet();
                 Iterator objectIterator = objectKeySet.iterator();
                 while(objectIterator.hasNext())
                 {
                     String objectItem  = (String)objectIterator.next();
                     tempObjectFrame = (JInternalFrame)objectContainer.get((Object)objectItem);
                     tempObjectFrame.removeAll();
                     desktop.remove(tempObjectFrame);
                     objectContainer.remove((Object)objectItem);
                     desktop.updateUI();
                 }
             }
             desktop.add(newPatientFrame);
             newPatientFrame.moveToFront();
             newPatientFrame.setSelected(true);
             objectContainer.put((Object)"newPatientFrame",(Object)newPatientFrame);
             desktop.updateUI();
         }
         catch (Exception exc)
         {
             JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
         }

    }
    
    public void captureComplaintDetails()//invoked From executeMethod method 
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Medical Complaints Capture",
            true, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
            internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setFrameIcon(new ImageIcon(imagePath + "maincomplaint_sm.gif"));
             internalFrame.setVisible(true);
             tabIconName = "maincomplaint_sm.gif";
            
             
            Container frameContainer = internalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,2,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(1, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.TOP);
            frameContainer.setLayout(contentLayout);
            
            JPanel internalPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            internalPanel.setMaximumSize(new Dimension(500, 600));
            internalPanel.setMinimumSize(new Dimension(400, 300));
            internalPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(internalPanel);
            
            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            //capturePanel.setBorder(BorderFactory.createRaisedBevelBorder());
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
             
             newComplaintPanel = new AddInfoMVC(patientKey, "ADDCOMPLAINT",capturePanel,captureList, JTabbedPane.TOP, tabIconName, "SIMPLE",userName,passWord,260,750);
             updateButtonEvent = newComplaintPanel.theDataModel.getUpdateButtonEvent();
             JScrollPane scrollPane = new JScrollPane(newComplaintPanel);
             
             internalPanel.add(scrollPane);
             updateExitButton = new JButton("Exit Medical Complaints Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
             objectContainer.put((Object)"newPatientComplaintFrame",(Object)internalFrame);
             //SwingUtilities.getRoot(desktop).repaint();
             desktop.updateUI();
             
             
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
        //return internalFrame;
    }
    
    public void captureFollowUpComplaintDetails()//invoked From executeMethod method 
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Medical Complaints Capture",
            true, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
            internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setFrameIcon(new ImageIcon(imagePath + "maincomplaint_sm.gif"));
             internalFrame.setVisible(true);
             tabIconName = "maincomplaint_sm.gif";
            
             
            Container frameContainer = internalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,2,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(1, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.TOP);
            frameContainer.setLayout(contentLayout);
            
            JPanel internalPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            internalPanel.setMaximumSize(new Dimension(500, 600));
            internalPanel.setMinimumSize(new Dimension(400, 300));
            internalPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(internalPanel);
            
            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            //capturePanel.setBorder(BorderFactory.createRaisedBevelBorder());
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            frameContainer.add(capturePanel);
             
             newComplaintPanel = new AddInfoMVC(patientKey, "ADDCOMPLAINT",capturePanel,captureList, JTabbedPane.TOP, tabIconName, "SIMPLE",userName,passWord,260,750);//was 360
             updateButtonEvent = newComplaintPanel.theDataModel.getUpdateButtonEvent();
             JScrollPane scrollPane = new JScrollPane(newComplaintPanel);
             
             internalPanel.add(scrollPane);
             updateExitButton = new JButton("Exit Medical Complaints Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
             objectContainer.put((Object)"newPatientComplaintFrame",(Object)internalFrame);
             //SwingUtilities.getRoot(desktop).repaint();
             desktop.updateUI();
             
             
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
        //return internalFrame;
    }
    public void captureFollowUpComplaintDetailsOld()//invoked From executeMethod method 
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Medical Complaints Capture",
            true, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
            internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
            internalFrame.setLocation(FRAME_X,FRAME_Y);
            internalFrame.setFrameIcon(new ImageIcon(imagePath + "maincomplaint_sm.gif"));
            internalFrame.setVisible(true);
            tabIconName = "maincomplaint_sm.gif";
            Container frameContainer = internalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,2,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(1, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.TOP);
            frameContainer.setLayout(contentLayout);
            
            JPanel internalPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            internalPanel.setMaximumSize(new Dimension(500, 600));
            internalPanel.setMinimumSize(new Dimension(400, 300));
            internalPanel.setPreferredSize(new Dimension(400, 600));
            
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(internalPanel);
            
            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));

            Border raisedBevel = BorderFactory.createRaisedBevelBorder();
            Border loweredBevel = BorderFactory.createLoweredBevelBorder();
            Border compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));

            //capturePanel.setBorder(compoundBorder);
            //capturePanel.setBorder(BorderFactory.createRaisedBevelBorder());
            
            //capturePanel.setPreferredSize(new Dimension(110, 400));
            //capturePanel.setSize(new Dimension(110, 400));
            //capturePanel.setMaximumSize(new Dimension(110, 400));
            
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
             AddInfoMVC newComplaintPanel = new AddInfoMVC(patientKey, "ADDFOLLOWUPCOMPLAINT", capturePanel,captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord,360,750);
             updateButtonEvent = newComplaintPanel.theDataModel.getUpdateButtonEvent();
             
            //newComplaintPanel.setPreferredSize(new Dimension(400, 100));
            //newComplaintPanel.setSize(new Dimension(400, 100));
            //newComplaintPanel.setMaximumSize(new Dimension(400, 100));
             
             JScrollPane scrollPane = new JScrollPane(newComplaintPanel);
             internalPanel.add(scrollPane);
             updateExitButton = new JButton("Exit Medical Complaints Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
             objectContainer.put((Object)"newPatientComplaintFrame",(Object)internalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
        //return internalFrame;
    }

    public void captureFollowUpAssesmentDetails()//invoked From executeMethod method 
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Medical Assesment Capture",
            true, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setFrameIcon(new ImageIcon(imagePath + "assessment_sm.gif"));
             internalFrame.setVisible(true);
             tabIconName = "assessment_sm.gif";
            
             
            Container frameContainer = internalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,2,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(1, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.TOP);
            frameContainer.setLayout(contentLayout);
            
            JPanel internalPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            internalPanel.setMaximumSize(new Dimension(500, 600));
            internalPanel.setMinimumSize(new Dimension(400, 300));
            internalPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(internalPanel);
            
            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
            
             
             
             AddInfoMVC newComplaintPanel = new AddInfoMVC(patientKey, "ADDFOLLOWUPASSESMENT",capturePanel,captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
             updateButtonEvent = newComplaintPanel.theDataModel.getUpdateButtonEvent();
             JScrollPane scrollPane = new JScrollPane(newComplaintPanel);
             internalPanel.add(scrollPane);
             updateExitButton = new JButton("Exit Medical Assesment Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
             objectContainer.put((Object)"followUpPatientAssesmentFrame",(Object)internalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
        //return internalFrame;
    }

//Capture new patient complaint
    public void capturePatientHistoryNew()//This is the capture-complaint method entered from the menu button press. 
    {
        if (stateMachineReset == true)
        {
           executeMethod(CAPTURE_COMPLAINT);
        } else
        {
           menuStates.setEventNumber(menuStates.current_complaint_menu_button_pressed_event);
           int stateresult = menuStates.menu_execute();
           ArrayList methodItems = menuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeMethod(methodItem);
               }
           }
        }

    } 

    public void captureSystemic() 
    {
        if (stateMachineReset == true)
        {
           executeMethod(CAPTURE_SYSTEMIC);
        } else
        {
            menuStates.setEventNumber(menuStates.systemic_enquiry_menu_button_pressed_event);
           int stateresult = menuStates.menu_execute();
           ArrayList methodItems = menuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeMethod(methodItem);
               }
           }
        }

    }

    public void captureFamilyHistory() 
    {
        if (stateMachineReset == true)
        {
           executeMethod(CAPTURE_FAMILY_HISTORY);
        } else
        {
           menuStates.setEventNumber(menuStates.family_history_menu_button_pressed_event);
           int stateresult = menuStates.menu_execute();
           ArrayList methodItems = menuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeMethod(methodItem);
               }
           }
        }
    }
    
    public void captureOccupationalHistory() 
    {
        if (stateMachineReset == true)
        {
           executeMethod(CAPTURE_OCCUPATIONAL_HISTORY );
        } else
        {
            menuStates.setEventNumber(menuStates.occupational_history_menu_button_pressed_event);
        
           int stateresult = menuStates.menu_execute();
           ArrayList methodItems = menuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeMethod(methodItem);
               }
           }
        }
    }

    public void captureSocialHistory() 
    {
        if (stateMachineReset == true)
        {
           executeMethod(CAPTURE_SOCIAL_HISTORY);
        } else
        { 
           menuStates.setEventNumber(menuStates.social_history_menu_button_pressed_event);
           int stateresult = menuStates.menu_execute();
           ArrayList methodItems = menuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeMethod(methodItem);
               }
           }
        }
    }

    public void capturePatientTreatment() 
    {
        if (stateMachineReset == true)
        {
           executeMethod(CAPTURE_TREATMENT);
        } else
        { 
           menuStates.setEventNumber(menuStates.treatment_menu_button_pressed_event);
           int stateresult = menuStates.menu_execute();
           ArrayList methodItems = menuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeMethod(methodItem);
               }
           }
        }
    }

    public void capturePlanTreatment() 
    {
        if (stateMachineReset == true)
        {
           executeMethod(CAPTURE_PLAN);
        } else

        {
           int stateresult = 0;
           menuStates.setEventNumber(menuStates.final_diagnoses_menu_button_pressed_event);
           stateresult = menuStates.menu_execute();
           menuStates.setEventNumber(menuStates.final_diagnoses_update_button_pressed_event);
           stateresult = menuStates.menu_execute();
           menuStates.setEventNumber(menuStates.plan_menu_button_pressed_event);
           stateresult = menuStates.menu_execute();
           ArrayList methodItems = menuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeMethod(methodItem);
               }
           }
        }
    }

    public void captureFollowUpPlanTreatment() 
    {
           int stateresult = 0;

        if (stateMachineReset == true)
        {
           executeFollowUpMethod(FOLLOWUP_CAPTURE_PLAN);
        } else

        {
           followUpMenuStates.setEventNumber(followUpMenuStates.plan_menu_button_pressed_event);
           stateresult = followUpMenuStates.newmenu_execute();
           //menuStates.setEventNumber(menuStates.final_diagnoses_update_button_pressed_event);
           //stateresult = menuStates.menu_execute();
           //menuStates.setEventNumber(menuStates.plan_menu_button_pressed_event);
           //stateresult = menuStates.menu_execute();
           ArrayList methodItems = followUpMenuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeFollowUpMethod(methodItem);
               }
           }
        }
    }

    public void capturePatientAllergies() 
    {
        if (stateMachineReset == true)
        {
           executeMethod(CAPTURE_ALLERGIES);
        } else
        { 
           menuStates.setEventNumber(menuStates.allergies_menu_button_pressed_event);
           int stateresult = menuStates.menu_execute();
           ArrayList methodItems = menuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeMethod(methodItem);
               }
           }
        }
    }

    public void captureTravelHistory() 
    {
        if (stateMachineReset == true)
        {
           executeMethod(CAPTURE_TRAVEL_HISTORY);
        } else
        { 
           menuStates.setEventNumber(menuStates.travel_history_menu_button_pressed_event);
           int stateresult = menuStates.menu_execute();
           ArrayList methodItems = menuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeMethod(methodItem);
               }
           }
        }
    }

    public void captureExamination() 
    {
        if (stateMachineReset == true)
        {
           executeMethod(CAPTURE_EXAMINATION);
        } else
        {
           menuStates.setEventNumber(menuStates.examination_menu_button_pressed_event);
           int stateresult = menuStates.menu_execute();
           ArrayList methodItems = menuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeMethod(methodItem);
               }
           }
        }
    }

    public void captureFollowUpExamination() 
    {

        if (stateMachineReset == true)
        {
           executeFollowUpMethod(FOLLOWUP_CAPTURE_EXAMINATION);
        } else

        {
        followUpMenuStates.setEventNumber(followUpMenuStates.examination_menu_button_pressed_event);
           int stateresult = followUpMenuStates.newmenu_execute();
           ArrayList methodItems = followUpMenuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeFollowUpMethod(methodItem);
               }
           }
        }
    }

    public void capturePreviousMedicalHistory() 
    {
        if (stateMachineReset == true)
        {
           executeMethod(CAPTURE_MEDICAL_HISTORY );
        } else
        {
            menuStates.setEventNumber(menuStates.previous_medical_history_menu_button_pressed_event);
        
           int stateresult = menuStates.menu_execute();
           ArrayList methodItems = menuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeMethod(methodItem);
               }
           }
        }
    } 

    public void capturePreviousMedicalHistoryDetails()//invoked From executeMethod method 
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Previous Medical History Capture",
            true, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setFrameIcon(new ImageIcon(imagePath + "medicalhistory_sm.gif"));
             internalFrame.setVisible(true);
             tabIconName = "medicalhistory_sm.gif";
///----------------
             
            Container frameContainer = internalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,2,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(1, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.CENTER);
            frameContainer.setLayout(contentLayout);
            
            JPanel internalPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            internalPanel.setMaximumSize(new Dimension(500, 600));
            internalPanel.setMinimumSize(new Dimension(400, 300));
            internalPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(internalPanel);
            
            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
            
////----------------             
             
             AddInfoMVC newComplaintPanel = new AddInfoMVC(patientKey, "ADDPMH",capturePanel,captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
             updateButtonEvent = newComplaintPanel.theDataModel.getUpdateButtonEvent();
             JScrollPane scrollPane = new JScrollPane(newComplaintPanel);
             
             
             internalPanel.add(scrollPane);
             updateExitButton = new JButton("Exit Previous Medical History Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
             objectContainer.put((Object)"newPreviousMedicalHistoryFrame",(Object)internalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
        //return internalFrame;
    }

    public void capturePreviousSurgicalHistory() 
    {
        if (stateMachineReset == true)
        {
           executeMethod(CAPTURE_SURGICAL_HISTORY  );
        } else
        {
           menuStates.setEventNumber(menuStates.previous_surgical_history_menu_button_pressed_event);
           int stateresult = menuStates.menu_execute();
           ArrayList methodItems = menuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeMethod(methodItem);
               }
           }
        }
    } 

    public void captureAssesment() 
    {
        if (stateMachineReset == true)
        {
           executeMethod(CAPTURE_ASSESMENT);
        } else
        {
            menuStates.setEventNumber(menuStates.assesment_menu_button_pressed_event);
        
           int stateresult = menuStates.menu_execute();
           ArrayList methodItems = menuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeMethod(methodItem);
               }
           }
        }
    } 

    public void captureAssesmentDetails()//invoked From executeMethod method 
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Assesment Details Capture",
            true, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setFrameIcon(new ImageIcon(imagePath + "assessment_sm.gif"));
             internalFrame.setVisible(true);
             tabIconName = "assessment_sm.gif";
             
            Container frameContainer = internalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,2,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(1, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.CENTER);
            frameContainer.setLayout(contentLayout);
            
            JPanel internalPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            internalPanel.setMaximumSize(new Dimension(500, 600));
            internalPanel.setMinimumSize(new Dimension(400, 300));
            internalPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(internalPanel);
            
            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            frameContainer.add(capturePanel);

            AddInfoMVC newAssesmentPanel = new AddInfoMVC(patientKey, "ADDASSESMENT",capturePanel,captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
             updateButtonEvent = newAssesmentPanel.theDataModel.getUpdateButtonEvent();
             JScrollPane scrollPane = new JScrollPane(newAssesmentPanel);
             internalPanel.add(scrollPane);
             updateExitButton = new JButton("Exit Assesment Detail Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
             objectContainer.put((Object)"newAssesmentInternalFrame",(Object)internalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
        //return internalFrame;
    }

    public void capturePreviousSurgicalHistoryDetails()//invoked From executeMethod method 
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Previous Surgical History Capture",
            true, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setFrameIcon(new ImageIcon(imagePath + "surgicalhistory_sm.gif"));
             tabIconName = "surgicalhistory_sm.gif";
             internalFrame.setVisible(true);

            Container frameContainer = internalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,2,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(1, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.CENTER);
            frameContainer.setLayout(contentLayout);
            
            JPanel internalPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            internalPanel.setMaximumSize(new Dimension(500, 600));
            internalPanel.setMinimumSize(new Dimension(400, 300));
            internalPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(internalPanel);
            
            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
             
             AddInfoMVC newComplaintPanel = new AddInfoMVC(patientKey, "ADDPSH",capturePanel,captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
             updateButtonEvent = newComplaintPanel.theDataModel.getUpdateButtonEvent();
             JScrollPane scrollPane = new JScrollPane(newComplaintPanel);
             internalPanel.add(scrollPane);
             updateExitButton = new JButton("Exit Previous Surgical History Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
             objectContainer.put((Object)"newPreviousSurgicalHistoryFrame",(Object)internalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
        //return internalFrame;
    }

    public void captureSystemicDetails()//invoked From executeMethod method 
    {
            systemicInternalFrame = new JInternalFrame("Patient Systemic Details Capture",
            true, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
            
            systemicInternalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
            systemicInternalFrame.setLocation(FRAME_X,FRAME_Y);
            systemicInternalFrame.setFrameIcon(new ImageIcon(imagePath + "systemic_sm.gif"));
            tabIconName = "systemic_sm.gif";
            systemicInternalFrame.setVisible(true);
            
            Container frameContainer = systemicInternalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,3,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(0, 0.2);
            contentLayout.setColumnScale(2, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.TOP);
            frameContainer.setLayout(contentLayout);
            JMenuBar systemicMenuBar = createSystemicMenu();
            
            systemicPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            systemicPanel.setMaximumSize(new Dimension(500, 600));
            systemicPanel.setMinimumSize(new Dimension(400, 300));
            systemicPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(systemicMenuBar);
            frameContainer.add(systemicPanel);
            
            
            
            //systemicMenuBar.setLayout(new SpringLayout());
            //SpringUtilities springGrid = new SpringUtilities();
            
            //springGrid.makeGrid(systemicMenuBar,13,1,0,0,2,2);
            
            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            //headingLabel.setVerticalTextPosition(JLabel.TOP);
            //headingLabel.setHorizontalTextPosition(JLabel.CENTER);
            
            //headingLabel.setPreferredSize(new Dimension(210, 25));
            //headingLabel.setSize(new Dimension(210, 25));
            //headingLabel.setMaximumSize(new Dimension(210, 25));
            //headingLabel.setMinimumSize(new Dimension(210, 25));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
            
            JPanel fillerPanel = new JPanel();
            frameContainer.add(fillerPanel);
            
            captureList = new HashMap(5);
            
            updateExitButton = new JButton("Exit Systemic Details Capture", new ImageIcon(imagePath + "updateexit24.gif"));
            updateExitButton.addActionListener(this);
            updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
            updateExitButton.setBackground(new Color(255,0,0));
            
            frameContainer.add(updateExitButton);
            
            updateButtonEvent = menuStates.systemic_enquiry_update_button_pressed_event;
            
            desktop.add(systemicInternalFrame);
            systemicInternalFrame.moveToFront();
            systemicInternalFrame.setSelected(true);
            objectContainer.put((Object)"systemicInternalFrame",(Object)systemicInternalFrame);
            desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(systemicInternalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void captureFamilyHistoryDetails()//invoked From executeMethod method 
    {
            familyHistoryInternalFrame = new JInternalFrame("Patient Family History Details Capture",
            true, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             familyHistoryInternalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             familyHistoryInternalFrame.setLocation(FRAME_X,FRAME_Y);
             familyHistoryInternalFrame.setFrameIcon(new ImageIcon(imagePath + "familyhistory_sm.gif"));
             familyHistoryInternalFrame.setVisible(true);
             updateButtonEvent = menuStates.family_history_update_button_pressed_event;

             JMenuBar famhistMenuBar = createFamilyHistoryMenu();

             Container frameContainer = familyHistoryInternalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,3,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(0, 0.2);
            contentLayout.setColumnScale(2, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.TOP);
            frameContainer.setLayout(contentLayout);
            
            familyPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            familyPanel.setMaximumSize(new Dimension(500, 600));
            familyPanel.setMinimumSize(new Dimension(400, 300));
            familyPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(famhistMenuBar);
            frameContainer.add(familyPanel);

            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
            
            JPanel fillerPanel = new JPanel();
            frameContainer.add(fillerPanel);

            updateExitButton = new JButton("Exit Family History Details Capture", new ImageIcon(imagePath + "updateexit24.gif"));
            updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
            updateExitButton.setBackground(new Color(255,0,0));
            updateExitButton.addActionListener(this);
            frameContainer.add(updateExitButton);
            
             desktop.add(familyHistoryInternalFrame);
             familyHistoryInternalFrame.moveToFront();
             familyHistoryInternalFrame.setSelected(true);
             objectContainer.put((Object)"familyHistoryInternalFrame",(Object)familyHistoryInternalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(familyHistoryInternalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void captureExaminationDetails()//invoked From executeMethod method 
    {
            examinationInternalFrame = new JInternalFrame("Patient Examination Details Capture",
            false, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             examinationInternalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             examinationInternalFrame.setLocation(FRAME_X,FRAME_Y);
             examinationInternalFrame.setFrameIcon(new ImageIcon(imagePath + "examination_sm.gif"));
             examinationInternalFrame.setVisible(true);

            Container frameContainer = examinationInternalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,3,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(0, 0.2);
            contentLayout.setColumnScale(2, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.TOP);
            frameContainer.setLayout(contentLayout);

            JMenuBar examinationMenuBar = createExaminationMenu();
            
            examinationPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            examinationPanel.setMaximumSize(new Dimension(500, 600));
            examinationPanel.setMinimumSize(new Dimension(400, 300));
            examinationPanel.setPreferredSize(new Dimension(800, 700));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(examinationMenuBar);
            
            //JScrollPane examinationScrollPane = new JScrollPane(examinationPanel);
            //examinationScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            frameContainer.add(examinationPanel);
            
            //systemicMenuBar.setLayout(new SpringLayout());
            //SpringUtilities springGrid = new SpringUtilities();
            
            //springGrid.makeGrid(systemicMenuBar,13,1,0,0,2,2);
            
            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            //headingLabel.setVerticalTextPosition(JLabel.TOP);
            //headingLabel.setHorizontalTextPosition(JLabel.CENTER);
            
            //headingLabel.setPreferredSize(new Dimension(210, 25));
            //headingLabel.setSize(new Dimension(210, 25));
            //headingLabel.setMaximumSize(new Dimension(210, 25));
            //headingLabel.setMinimumSize(new Dimension(210, 25));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
            
            JPanel fillerPanel = new JPanel();
            frameContainer.add(fillerPanel);
            
            captureList = new HashMap(5);
             updateExitButton = new JButton("Exit Examination Details Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             updateButtonEvent = MenuStateMachine.examination_update_button_pressed_event;

             frameContainer.add(updateExitButton);
             desktop.add(examinationInternalFrame);
             examinationInternalFrame.moveToFront();
             examinationInternalFrame.setSelected(true);
             objectContainer.put((Object)"examinationInternalFrame",(Object)examinationInternalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(examinationInternalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void captureFollowUpExaminationDetails()//invoked From executeMethod method 
    {
            examinationInternalFrame = new JInternalFrame("Patient Follow Up Examination Details Capture",
            false, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             examinationInternalFrame.setSize(new Dimension(EXAM_SIZE_X, EXAM_SIZE_Y));
             examinationInternalFrame.setLocation(FRAME_X,FRAME_Y);
             examinationInternalFrame.setFrameIcon(new ImageIcon(imagePath + "examination_sm.gif"));
             examinationInternalFrame.setVisible(true);
             JMenuBar followUpExaminationMenuBar = createFollowUpExaminationMenu();

            Container frameContainer = examinationInternalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,3,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(0, 0.2);
            contentLayout.setColumnScale(2, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.TOP);
            frameContainer.setLayout(contentLayout);

            JMenuBar examinationMenuBar = createExaminationMenu();
            
            examinationPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            examinationPanel.setMaximumSize(new Dimension(500, 600));
            examinationPanel.setMinimumSize(new Dimension(400, 300));
            examinationPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(examinationMenuBar);
            frameContainer.add(examinationPanel);
            
            //systemicMenuBar.setLayout(new SpringLayout());
            //SpringUtilities springGrid = new SpringUtilities();
            
            //springGrid.makeGrid(systemicMenuBar,13,1,0,0,2,2);
            
            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            //headingLabel.setVerticalTextPosition(JLabel.TOP);
            //headingLabel.setHorizontalTextPosition(JLabel.CENTER);
            
            //headingLabel.setPreferredSize(new Dimension(210, 25));
            //headingLabel.setSize(new Dimension(210, 25));
            //headingLabel.setMaximumSize(new Dimension(210, 25));
            //headingLabel.setMinimumSize(new Dimension(210, 25));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,120));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
            
            JPanel fillerPanel = new JPanel();
            frameContainer.add(fillerPanel);
            
            captureList = new HashMap(5);
             
             
             updateExitButton = new JButton("Exit Examination Details Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             updateButtonEvent = NewMenuStateMachine.examination_update_button_pressed_event;
             
             //updateButtonEvent = newComplaintPanel.theDataModel.getUpdateButtonEvent();

             frameContainer.add(updateExitButton);
             desktop.add(examinationInternalFrame);
             examinationInternalFrame.moveToFront();
             examinationInternalFrame.setSelected(true);
             objectContainer.put((Object)"examinationInternalFrame",(Object)examinationInternalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(examinationInternalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void captureOccupationalHistoryDetails()//invoked From executeMethod method 
    {
            occupationHistoryInternalFrame = new JInternalFrame("Patient Occupation History Details Capture",
            false, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             occupationHistoryInternalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             occupationHistoryInternalFrame.setLocation(FRAME_X,FRAME_Y);
             occupationHistoryInternalFrame.setFrameIcon(new ImageIcon(imagePath + "workhistory_sm.gif"));
             occupationHistoryInternalFrame.setVisible(true);
             JMenuBar occhistMenuBar = createOccupationHistoryMenu();
             
            Container frameContainer = occupationHistoryInternalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,3,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(0, 0.2);
            contentLayout.setColumnScale(2, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.TOP);
            frameContainer.setLayout(contentLayout);
            
            occupationPanel = new JPanel();
            
            occupationPanel.setMaximumSize(new Dimension(500, 600));
            occupationPanel.setMinimumSize(new Dimension(400, 300));
            occupationPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(occhistMenuBar);
            frameContainer.add(occupationPanel);

            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
            
            JPanel fillerPanel = new JPanel();
            frameContainer.add(fillerPanel);
             
             updateButtonEvent = menuStates.occupational_history_update_button_pressed_event;

             updateExitButton = new JButton("Exit Occupation History Details Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(occupationHistoryInternalFrame);
             occupationHistoryInternalFrame.moveToFront();
             occupationHistoryInternalFrame.setSelected(true);
             objectContainer.put((Object)"occupationHistoryInternalFrame",(Object)occupationHistoryInternalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(occupationHistoryInternalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void captureCurrentOccupation()//Invoked From Actionlistener 
    {

        tabIconName = "workhistory_sm.gif";
        removeOccupationHistoryPanels();
        occupationHistoryCurrentPanel = new AddInfoMVC(patientKey, "ADDCURRENTOCCUPATION", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = occupationHistoryCurrentPanel.theDataModel.getUpdateButtonEvent();
        occupationPanel.add(occupationHistoryCurrentPanel);
        desktop.updateUI();
    }
    
    public void capturePreviousOccupation()//Invoked From Actionlistener 
    {

        tabIconName = "workhistory_sm.gif";
        removeOccupationHistoryPanels();
        occupationHistoryPreviousPanel = new AddInfoMVC(patientKey, "ADDPREVIOUSOCCUPATION", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = occupationHistoryPreviousPanel.theDataModel.getUpdateButtonEvent();
        occupationPanel.add(occupationHistoryPreviousPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }


    public void captureExamGeneral()//Invoked From Actionlistener 
    {

        //updateButtonEvent = captureExamGeneralPanel.theDataModel.getUpdateButtonEvent();
        removeExaminationPanels();
        tabIconName = "examination_sm.gif";
        captureExamGeneralPanel = new AddInfoMVC(patientKey, "ADDEXAMGENERAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        //JScrollPane examinationScrollPane = new JScrollPane(captureExamGeneralPanel);
        //examinationScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        examinationPanel.add(captureExamGeneralPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureExamCardiovascular()//Invoked From Actionlistener 
    {

        //updateButtonEvent = captureExamCardiovascularPanel.theDataModel.getUpdateButtonEvent();
        removeExaminationPanels();
        tabIconName = "cardiovascular_sm.gif";
        captureExamCardiovascularPanel = new AddInfoMVC(patientKey, "ADDEXAMCARDIOVASCULAR", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        //JScrollPane examinationScrollPane = new JScrollPane(captureExamCardiovascularPanel);
        examinationPanel.add(captureExamCardiovascularPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureExamRespiratory()//Invoked From Actionlistener 
    {

        //updateButtonEvent = captureExamRespiratoryPanel.theDataModel.getUpdateButtonEvent();
        removeExaminationPanels();
        tabIconName = "respiratory_sm.gif";
        captureExamRespiratoryPanel = new AddInfoMVC(patientKey, "ADDEXAMRESPIRATORY", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        //JScrollPane examinationScrollPane = new JScrollPane(captureExamRespiratoryPanel);
        examinationPanel.add(captureExamRespiratoryPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureExamAbdomen() 
    {

        //updateButtonEvent = captureExamAbdomenPanel.theDataModel.getUpdateButtonEvent();
        removeExaminationPanels();
        tabIconName = "abdominal_sm.gif";
        captureExamAbdomenPanel = new AddInfoMVC(patientKey, "ADDEXAMABDOMINAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        //JScrollPane examinationScrollPane = new JScrollPane(captureExamAbdomenPanel);
        examinationPanel.add(captureExamAbdomenPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureExamMusculoskeletal() 
    {

        //updateButtonEvent = captureExamMusculoskeletalPanel.theDataModel.getUpdateButtonEvent();
        tabIconName = "muskuloskeletal_sm.gif";
        removeExaminationPanels();
        captureExamMusculoskeletalPanel = new AddInfoMVC(patientKey, "ADDEXAMMUSCULOSKELETAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        //JScrollPane examinationScrollPane = new JScrollPane(captureExamMusculoskeletalPanel);
        examinationPanel.add(captureExamMusculoskeletalPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureExamCentralNervousSystem() 
    {

        //updateButtonEvent = captureExamCentralNervousSystemPanel.theDataModel.getUpdateButtonEvent();
        tabIconName = "cnsexam_sm.gif";
        removeExaminationPanels();
        captureExamCentralNervousSystemPanel = new AddInfoMVC(patientKey, "ADDEXAMCNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord,600,700); //remove the last 2 size parametrs
        //JScrollPane examinationScrollPane = new JScrollPane(captureExamCentralNervousSystemPanel);
        examinationPanel.add(captureExamCentralNervousSystemPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureExamEarNoseThroat() 
    {

        removeExaminationPanels();
        captureExamEarNoseThroatFrame = new JInternalFrame("Patient Ear-Nose-Throat Details Capture",
            false, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             captureExamEarNoseThroatFrame.setSize(new Dimension(ENT_SIZE_X, ENT_SIZE_Y));
             captureExamEarNoseThroatFrame.setLocation(FRAME_X,FRAME_Y);
             captureExamEarNoseThroatFrame.setFrameIcon(new ImageIcon(imagePath + "earnosethroat_sm.gif"));
             captureExamEarNoseThroatFrame.setVisible(true);
             //updateButtonEvent = menuStates.examination_update_button_pressed_event;
             JMenuBar entMenuBar = createENTMenu();
             captureExamEarNoseThroatFrame.getContentPane().add(entMenuBar,BorderLayout.WEST);
             captureExamEarNoseThroatFrame.getContentPane().add(dummyPanel,BorderLayout.CENTER);
             examinationPanel.add(captureExamEarNoseThroatFrame,BorderLayout.CENTER);
             captureExamEarNoseThroatFrame.moveToFront();
             captureExamEarNoseThroatFrame.setSelected(true);
             objectContainer.put((Object)"captureExamEarNoseThroatFrame",(Object)captureExamEarNoseThroatFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(examinationInternalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void captureExamUrogenital() 
    {

        removeExaminationPanels();
        captureExamUrogenitalFrame = new JInternalFrame("Patient Urogenital Details Capture",
            false, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             captureExamUrogenitalFrame.setSize(new Dimension(ENT_SIZE_X, ENT_SIZE_Y));
             captureExamUrogenitalFrame.setLocation(FRAME_X,FRAME_Y);
             captureExamUrogenitalFrame.setFrameIcon(new ImageIcon(imagePath + "urogenital_sm.gif"));
             captureExamUrogenitalFrame.setVisible(true);
             //updateButtonEvent = menuStates.examination_update_button_pressed_event;
             JMenuBar uroMenuBar = createUROMenu();
             captureExamUrogenitalFrame.getContentPane().add(uroMenuBar,BorderLayout.WEST);
             captureExamUrogenitalFrame.getContentPane().add(dummyPanel,BorderLayout.CENTER);
             examinationPanel.add(captureExamUrogenitalFrame,BorderLayout.CENTER);
             captureExamUrogenitalFrame.moveToFront();
             captureExamUrogenitalFrame.setSelected(true);
             objectContainer.put((Object)"captureExamUrogenitalFrame",(Object)captureExamUrogenitalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(examinationInternalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void captureExamDermatological() 
    {

        //updateButtonEvent = captureExamCentralNervousSystemPanel.theDataModel.getUpdateButtonEvent();
        removeExaminationPanels();
        tabIconName = "dermatological_sm.gif";
        captureExamDermatologicalPanel = new AddInfoMVC(patientKey, "ADDEXAMDERMA", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord); //same here
        //JScrollPane examinationScrollPane = new JScrollPane(captureExamDermatologicalPanel);
        examinationPanel.add(captureExamDermatologicalPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureExamHematological() 
    {

        //updateButtonEvent = captureExamCentralNervousSystemPanel.theDataModel.getUpdateButtonEvent();
        tabIconName = "hematological_sm.gif";
        removeExaminationPanels();
        captureExamHematologicalPanel = new AddInfoMVC(patientKey, "ADDEXAMHEMA", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        //JScrollPane examinationScrollPane = new JScrollPane(captureExamHematologicalPanel);
        examinationPanel.add(captureExamHematologicalPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureExamEndocrine() 
    {

        removeExaminationPanels();
        tabIconName = "endocrine_sm.gif";
        captureExamEndocrinePanel = new AddInfoMVC(patientKey, "ADDEXAMENDO", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        //JScrollPane examinationScrollPane = new JScrollPane(captureExamEndocrinePanel);
        examinationPanel.add(captureExamEndocrinePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureExamEyes() 
    {

        //updateButtonEvent = captureExamCentralNervousSystemPanel.theDataModel.getUpdateButtonEvent();
        tabIconName = "eyes_sm.gif";
        removeExaminationPanels();
        captureExamEyesPanel = new AddInfoMVC(patientKey, "ADDEXAMEYES", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord,600,700);
        //JScrollPane examinationScrollPane = new JScrollPane(captureExamEyesPanel);
        examinationPanel.add(captureExamEyesPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureSocialHistoryDetails()//invoked From executeMethod method 
    {
            socialHistoryInternalFrame = new JInternalFrame("Patient Social History Details Capture",
            false, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
            socialHistoryInternalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             socialHistoryInternalFrame.setLocation(FRAME_X,FRAME_Y);
             socialHistoryInternalFrame.setFrameIcon(new ImageIcon(imagePath + "socialhistory_sm.gif"));
             tabIconName = "socialhistory_sm.gif";
             socialHistoryInternalFrame.setVisible(true);
             
             
            Container frameContainer = socialHistoryInternalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,2,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(1, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.CENTER);
            frameContainer.setLayout(contentLayout);
            
            JPanel internalPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            internalPanel.setMaximumSize(new Dimension(500, 600));
            internalPanel.setMinimumSize(new Dimension(400, 300));
            internalPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(internalPanel);
            
            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
             
             AddInfoMVC socialHistoryPanel = new AddInfoMVC(patientKey, "ADDSOCIALHISTORY",capturePanel,captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
             updateButtonEvent = socialHistoryPanel.theDataModel.getUpdateButtonEvent();
             JScrollPane scrollPane = new JScrollPane(socialHistoryPanel);
             internalPanel.add(scrollPane);
             updateExitButton = new JButton("Exit Social History Details Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(socialHistoryInternalFrame);
             socialHistoryInternalFrame.moveToFront();
             socialHistoryInternalFrame.setSelected(true);
             objectContainer.put((Object)"socialHistoryInternalFrame",(Object)socialHistoryInternalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(socialHistoryInternalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void captureTreatmentHistoryDetails()//invoked From executeMethod method 
    {
            treatmentHistoryInternalFrame = new JInternalFrame("Patient Treatment History Details Capture",
            false, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             treatmentHistoryInternalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             treatmentHistoryInternalFrame.setLocation(FRAME_X,FRAME_Y);
             treatmentHistoryInternalFrame.setFrameIcon(new ImageIcon(imagePath + "treatment_sm.gif"));
             tabIconName = "treatment_sm.gif";
             treatmentHistoryInternalFrame.setVisible(true);
             
            Container frameContainer = treatmentHistoryInternalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,2,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(1, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.CENTER);
            frameContainer.setLayout(contentLayout);
            
            JPanel internalPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            internalPanel.setMaximumSize(new Dimension(500, 600));
            internalPanel.setMinimumSize(new Dimension(400, 300));
            internalPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(internalPanel);
            
            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
             
             
             AddInfoMVC treatmentHistoryPanel = new AddInfoMVC(patientKey, "ADDTREATMENT",capturePanel,captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
             updateButtonEvent = treatmentHistoryPanel.theDataModel.getUpdateButtonEvent();
             JScrollPane scrollPane = new JScrollPane(treatmentHistoryPanel);
             internalPanel.add(scrollPane);
             
             updateExitButton = new JButton("Exit Treatment History Details Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(treatmentHistoryInternalFrame);
             treatmentHistoryInternalFrame.moveToFront();
             treatmentHistoryInternalFrame.setSelected(true);
             objectContainer.put((Object)"treatmentHistoryInternalFrame",(Object)treatmentHistoryInternalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(treatmentHistoryInternalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void captureTreatmentPlanDetails()//invoked From executeMethod method 
    {
            treatmentPlanInternalFrame = new JInternalFrame("Patient Treatment Plan Details Capture",
            false, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             treatmentPlanInternalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             treatmentPlanInternalFrame.setLocation(FRAME_X,FRAME_Y);
             treatmentPlanInternalFrame.setFrameIcon(new ImageIcon(imagePath + "treatment_sm.gif"));
             tabIconName = "treatment_sm.gif";
             treatmentPlanInternalFrame.setVisible(true);
             
            Container frameContainer = treatmentPlanInternalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,3,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(0, 0.2);
            contentLayout.setColumnScale(2, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.TOP);
            frameContainer.setLayout(contentLayout);
            
            treatmentPanel = new JPanel();
            
            PointLayout treatmentPanelLayout = new PointLayout();
            treatmentPanel.setLayout(treatmentPanelLayout);
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            treatmentPanel.setMaximumSize(new Dimension(500, 600));
            treatmentPanel.setMinimumSize(new Dimension(400, 300));
            treatmentPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            JMenuBar treatmentMenuBar = createTreatmentMenu();

            frameContainer.add(treatmentMenuBar);
            frameContainer.add(treatmentPanel);
            
            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
            
            JPanel fillerPanel = new JPanel();
            frameContainer.add(fillerPanel);

             
                 //AddInfoMVC treatmentPlanPanel = new AddInfoMVC(patientKey, "ADDTREATMENTPLAN",capturePanel,captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
                 //updateButtonEvent = treatmentPlanPanel.theDataModel.getUpdateButtonEvent();
                 //JScrollPane scrollPane = new JScrollPane(treatmentPlanPanel);
                 //internalPanel.add(scrollPane);
             updateExitButton = new JButton("Exit Treatment Plan Details Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(treatmentPlanInternalFrame);
             treatmentPlanInternalFrame.moveToFront();
             treatmentPlanInternalFrame.setSelected(true);
             objectContainer.put((Object)"treatmentPlanInternalFrame",(Object)treatmentPlanInternalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(treatmentPlanInternalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void captureFollowUpTreatmentPlanDetails()//invoked From executeMethod method 
    {
            treatmentPlanInternalFrame = new JInternalFrame("Patient Treatment Plan Details Capture",
            false, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             treatmentPlanInternalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             treatmentPlanInternalFrame.setLocation(FRAME_X,FRAME_Y);
             treatmentPlanInternalFrame.setFrameIcon(new ImageIcon(imagePath + "treatment_sm.gif"));
             tabIconName = "treatment_sm.gif";
             treatmentPlanInternalFrame.setVisible(true);
            Container frameContainer = treatmentPlanInternalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,3,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(0, 0.2);
            contentLayout.setColumnScale(2, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.TOP);
            frameContainer.setLayout(contentLayout);
            
            treatmentPanel = new JPanel();
            PointLayout treatmentPanelLayout = new PointLayout();
            treatmentPanel.setLayout(treatmentPanelLayout);
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            treatmentPanel.setMaximumSize(new Dimension(500, 600));
            treatmentPanel.setMinimumSize(new Dimension(400, 300));
            treatmentPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            JMenuBar treatmentMenuBar = createTreatmentMenu();

            frameContainer.add(treatmentMenuBar);
           
            frameContainer.add(treatmentPanel);
            
            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
             
            frameContainer.add(capturePanel);
            
            JPanel fillerPanel = new JPanel();
            frameContainer.add(fillerPanel);

            //AddInfoMVC treatmentPlanPanel = new AddInfoMVC(patientKey, "ADDFOLLOWUPTREATMENTPLAN",capturePanel,captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
             //updateButtonEvent = treatmentPlanPanel.theDataModel.getUpdateButtonEvent();
             //JScrollPane scrollPane = new JScrollPane(treatmentPlanPanel);
             //internalPanel.add(scrollPane);
             updateExitButton = new JButton("Exit Treatment Plan Details Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(treatmentPlanInternalFrame);
             treatmentPlanInternalFrame.moveToFront();
             treatmentPlanInternalFrame.setSelected(true);
             objectContainer.put((Object)"treatmentPlanInternalFrame",(Object)treatmentPlanInternalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(treatmentPlanInternalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void captureAllergyHistoryDetails()//invoked From executeMethod method 
    {
            allergyHistoryInternalFrame = new JInternalFrame("Patient Allergy History Details Capture",
            false, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             allergyHistoryInternalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             allergyHistoryInternalFrame.setLocation(FRAME_X,FRAME_Y);
             allergyHistoryInternalFrame.setFrameIcon(new ImageIcon(imagePath + "allergy_sm.gif"));
             tabIconName = "allergy_sm.gif";
             allergyHistoryInternalFrame.setVisible(true);

            Container frameContainer = allergyHistoryInternalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,2,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(1, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.CENTER);
            frameContainer.setLayout(contentLayout);
            
            JPanel internalPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            internalPanel.setMaximumSize(new Dimension(500, 600));
            internalPanel.setMinimumSize(new Dimension(400, 300));
            internalPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(internalPanel);
            
            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);
             
             AddInfoMVC allergyHistoryPanel = new AddInfoMVC(patientKey, "ADDALLERGY",capturePanel,captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
             updateButtonEvent = allergyHistoryPanel.theDataModel.getUpdateButtonEvent();
             JScrollPane scrollPane = new JScrollPane(allergyHistoryPanel);
             internalPanel.add(scrollPane);
             updateExitButton = new JButton("Exit Allergy History Details Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(allergyHistoryInternalFrame);
             allergyHistoryInternalFrame.moveToFront();
             allergyHistoryInternalFrame.setSelected(true);
             objectContainer.put((Object)"allergyHistoryInternalFrame",(Object)allergyHistoryInternalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(allergyHistoryInternalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void captureTravelHistoryDetails()//invoked From executeMethod method 
    {
            travelHistoryInternalFrame = new JInternalFrame("Patient Travel History Details Capture",
            false, //resizable
            false, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             travelHistoryInternalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             travelHistoryInternalFrame.setLocation(FRAME_X,FRAME_Y);
             travelHistoryInternalFrame.setFrameIcon(new ImageIcon(imagePath + "travelhistory_sm.gif"));
             tabIconName = "travelhistory_sm.gif";
             travelHistoryInternalFrame.setVisible(true);

            Container frameContainer = travelHistoryInternalFrame.getContentPane();
            
            SGLayout contentLayout  = new SGLayout(2,2,SGLayout.FILL,SGLayout.FILL,15,5);
            contentLayout.setRowScale(1, 0.1);
            contentLayout.setColumnScale(1, 0.25);
            contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.CENTER);
            frameContainer.setLayout(contentLayout);
            
            JPanel internalPanel = new JPanel();
            //systemicPanel.setBorder(new BorderFactory.createEtchedBorder());
            
            internalPanel.setMaximumSize(new Dimension(500, 600));
            internalPanel.setMinimumSize(new Dimension(400, 300));
            internalPanel.setPreferredSize(new Dimension(400, 600));
            //contentLayout.setColumnAlignment(0, SGLayout.LEFT, SGLayout.TOP);
            
            frameContainer.add(internalPanel);
            
            captureList = new HashMap(5);

            capturePanel = new JPanel();
            JPanel headingPanel = new JPanel();
            JLabel headingLabel = new  JLabel("Captured Items",JLabel.CENTER);
            headingLabel.setForeground(new Color(255,255,0));
            headingPanel.add(headingLabel);
            headingPanel.setBackground(new Color(120,120,200));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setPreferredSize(new Dimension(210, 25));
            headingPanel.setSize(new Dimension(210, 25));
            headingPanel.setMaximumSize(new Dimension(210, 25));
            headingPanel.setMinimumSize(new Dimension(210, 25));
            
            capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.Y_AXIS));
            capturePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
            capturePanel.setPreferredSize(new Dimension(210, 400));
            capturePanel.setSize(new Dimension(210, 400));
            capturePanel.setMaximumSize(new Dimension(210, 400));
            capturePanel.setBackground(new Color(185,239,156));
            capturePanel.add(headingPanel);
            
            frameContainer.add(capturePanel);

            AddInfoMVC travelHistoryPanel = new AddInfoMVC(patientKey, "ADDTRAVELHISTORY",capturePanel,captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
             updateButtonEvent = travelHistoryPanel.theDataModel.getUpdateButtonEvent();
             JScrollPane scrollPane = new JScrollPane(travelHistoryPanel);
             internalPanel.add(scrollPane);
             updateExitButton = new JButton("Exit Travel History Details Capture", new ImageIcon(imagePath + "updateexit24.gif"));
             updateExitButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
             updateExitButton.setBackground(new Color(255,0,0));
             updateExitButton.addActionListener(this);
             frameContainer.add(updateExitButton);
             desktop.add(travelHistoryInternalFrame);
             travelHistoryInternalFrame.moveToFront();
             travelHistoryInternalFrame.setSelected(true);
             objectContainer.put((Object)"travelHistoryInternalFrame",(Object)travelHistoryInternalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(travelHistoryInternalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void captureFamilyHistoryFather()//Invoked From Actionlistener 
    {

        tabIconName = "familyhistory_sm.gif";
        removeFamilyHistoryPanels();
        desktop.updateUI();
        familyHistoryFatherPanel = new AddInfoMVC( patientKey, FH_FATHER, "ADDFAMILYHISTORY", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = familyHistoryFatherPanel.theDataModel.getUpdateButtonEvent();
        familyPanel.add(familyHistoryFatherPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureFamilyHistoryMother()//Invoked From Actionlistener 
    {
        tabIconName = "familyhistory_sm.gif";
        removeFamilyHistoryPanels();
        desktop.updateUI();
        familyHistoryMotherPanel = new AddInfoMVC(patientKey, FH_MOTHER, "ADDFAMILYHISTORY", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = familyHistoryMotherPanel.theDataModel.getUpdateButtonEvent();
        familyPanel.add(familyHistoryMotherPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureFamilyHistorySiblings()//Invoked From Actionlistener 
    {
        tabIconName = "familyhistory_sm.gif";
        removeFamilyHistoryPanels();
        desktop.updateUI();
        familyHistorySiblingsPanel = new AddInfoMVC(patientKey, FH_SIBLINGS, "ADDFAMILYHISTORY", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = familyHistorySiblingsPanel.theDataModel.getUpdateButtonEvent();
        familyPanel.add(familyHistorySiblingsPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureFamilyHistoryChildren()//Invoked From Actionlistener 
    {
        tabIconName = "familyhistory_sm.gif";
        removeFamilyHistoryPanels();
        desktop.updateUI();
        familyHistoryChildrenPanel = new AddInfoMVC(patientKey, FH_CHILDREN, "ADDFAMILYHISTORY", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = familyHistoryChildrenPanel.theDataModel.getUpdateButtonEvent();
        familyPanel.add(familyHistoryChildrenPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void removeFamilyHistoryPanels()
    {
        if (familyHistoryFatherPanel != null)
        {
            familyPanel.remove(familyHistoryFatherPanel);
            familyHistoryFatherPanel = null;
        }
        if (familyHistoryMotherPanel != null)
        {
            familyPanel.remove(familyHistoryMotherPanel);
            familyHistoryMotherPanel = null;
        }
        if (familyHistorySiblingsPanel != null)
        {
            familyPanel.remove(familyHistorySiblingsPanel);
            familyHistorySiblingsPanel = null;
        }
        if (familyHistoryChildrenPanel != null)
        {
            familyPanel.remove(familyHistoryChildrenPanel);
            familyHistoryChildrenPanel = null;
        }
    }

    public void removeExaminationPanels()
    {
        if (examinationPanel != null)
        {
        if (captureExamGeneralPanel != null)
        {
            examinationPanel.remove(captureExamGeneralPanel);
            captureExamGeneralPanel = null;
        }
        if (captureExamCardiovascularPanel != null)
        {
            examinationPanel.remove(captureExamCardiovascularPanel);
            captureExamCardiovascularPanel = null;
        }
        if (captureExamRespiratoryPanel != null)
        {
            examinationPanel.remove(captureExamRespiratoryPanel);
            captureExamRespiratoryPanel = null;
        }
        if (captureExamAbdomenPanel != null)
        {
            examinationPanel.remove(captureExamAbdomenPanel);
            captureExamAbdomenPanel = null;
        }
        if (captureExamMusculoskeletalPanel != null)
        {
            examinationPanel.remove(captureExamMusculoskeletalPanel);
            captureExamMusculoskeletalPanel = null;
        }
        if (captureExamCentralNervousSystemPanel != null)
        {
           examinationPanel.remove(captureExamCentralNervousSystemPanel);
           captureExamCentralNervousSystemPanel = null;
        }
        if (captureExamEarNoseThroatFrame != null)
        {
           removeENTPanels();
           examinationPanel.remove(captureExamEarNoseThroatFrame);
           captureExamEarNoseThroatFrame = null;
        }
        if (captureExamUrogenitalFrame != null)
        {
           removeUROPanels();
            examinationPanel.remove(captureExamUrogenitalFrame);
            captureExamUrogenitalFrame = null;
        }
        if (captureExamDermatologicalPanel != null)
        {
            examinationPanel.remove(captureExamDermatologicalPanel);
            captureExamDermatologicalPanel = null;
        }
        if (captureExamHematologicalPanel != null)
        {
            examinationPanel.remove(captureExamHematologicalPanel);
            captureExamHematologicalPanel = null;
        }
        if (captureExamEyesPanel != null)
        {
            examinationPanel.remove(captureExamEyesPanel);
            captureExamEyesPanel = null;
        }
        if (captureExamUrogenitalPanel != null)
        {
            examinationPanel.remove(captureExamUrogenitalPanel);
            captureExamUrogenitalPanel = null;
        }
        if (captureExamEndocrinePanel != null)
        {
            examinationPanel.remove(captureExamEndocrinePanel);
            captureExamEndocrinePanel = null;
        }
        
        }
        examinationInternalFrame.updateUI();
        //examinationInternalFrame.repaint();
        desktop.updateUI();
    }
    
    public void removeOccupationHistoryPanels()
    {
        if (occupationHistoryCurrentPanel != null)
        {
            occupationPanel.remove(occupationHistoryCurrentPanel);
            occupationHistoryCurrentPanel = null;
        }
        if (occupationHistoryPreviousPanel != null)
        {
            occupationPanel.remove(occupationHistoryPreviousPanel);
            occupationHistoryPreviousPanel = null;
        }
    }

    public void removeTreatmentPlanPanels()
    {
        if (treatmentPlanAllopathicPanel != null)
        {
            treatmentPanel.remove(treatmentPlanAllopathicPanel);
            //newConstitutionalPanel = null;
        }
        if (rifePanel != null)
        {
            treatmentPanel.remove(rifePanel);
        }
    }
    
    
    public void removeSystemicPanels()
    {
        if (newConstitutionalPanel != null)
        {
            systemicPanel.remove(newConstitutionalPanel);
            //newConstitutionalPanel = null;
        }
        if (newPalpitationsPanel != null)
        {
            systemicPanel.remove(newPalpitationsPanel);
            //newPalpitationsPanel = null;
        }
        if (newChestPainPanel != null)
        {
            systemicPanel.remove(newChestPainPanel);
            //newChestPainPanel = null;
        }
        if (newDyspneaPanel != null)
        {
            systemicPanel.remove(newDyspneaPanel);
            //newDyspneaPanel = null;
        }
        if (newClaudicationPanel != null)
        {
            systemicPanel.remove(newClaudicationPanel);
            //newClaudicationPanel = null;
        }
        if (newLegSwellingPanel != null)
        {
            systemicPanel.remove(newLegSwellingPanel);
            //newLegSwellingPanel = null;
        }
        if (newRespDyspneaPanel != null)
        {
            systemicPanel.remove(newRespDyspneaPanel);
            //newRespDyspneaPanel = null;
        }
        if (newRespChestPainPanel != null)
        {
            systemicPanel.remove(newRespChestPainPanel);
            //newRespChestPainPanel = null;
        }
        if (newRespChestTightnessPanel != null)
        {
            systemicPanel.remove(newRespChestTightnessPanel);
            //newRespChestTightnessPanel = null;
        }
        if (newRespCoughingPanel != null)
        {
            systemicPanel.remove(newRespCoughingPanel);
            //newRespCoughingPanel = null;
        }
        if (newAbdNauseaPanel != null)
        {
            systemicPanel.remove(newAbdNauseaPanel);
            //newAbdNauseaPanel = null;
        }
        if (newAbdVomitingPanel != null)
        {
            systemicPanel.remove(newAbdVomitingPanel);
            //newAbdVomitingPanel = null;
        }
        if (newAbdDiarrheaPanel != null)
        {
            systemicPanel.remove(newAbdDiarrheaPanel);
            //newAbdDiarrheaPanel = null;
        }
        if (newAbdConstipationPanel != null)
        {
            systemicPanel.remove(newAbdConstipationPanel);
            //newAbdConstipationPanel = null;
        }
        if (newAbdominalPainPanel != null)
        {
            systemicPanel.remove(newAbdominalPainPanel);
            //newAbdominalPainPanel = null;
        }
        if (newAbdTenesmusPanel != null)
        {
            systemicPanel.remove(newAbdTenesmusPanel);
            //newAbdTenesmusPanel = null;
        }
        if (newAbdRectalProlapsePanel != null)
        {
            systemicPanel.remove(newAbdRectalProlapsePanel);
            //newAbdRectalProlapsePanel = null;
        }
        if (newAbdHematemesisPanel != null)
        {
            systemicPanel.remove(newAbdHematemesisPanel);
            //newAbdHematemesisPanel = null;
        }
        if (newAbdBloodPerRectumPanel != null)
        {
            systemicPanel.remove(newAbdBloodPerRectumPanel);
            //newAbdBloodPerRectumPanel = null;
        }
        if (newAbdJaundicePanel != null)
        {
            systemicPanel.remove(newAbdJaundicePanel);
            //newAbdJaundicePanel = null;
        }
        if (newMSJointPainsPanel != null)
        {
            systemicPanel.remove(newMSJointPainsPanel);
            //newMSJointPainsPanel = null;
        }
        if (newMSJointSwellingsPanel != null)
        {
            systemicPanel.remove(newMSJointSwellingsPanel);
            //newMSJointSwellingsPanel = null;
        }
        if (newMSMuclePainsPanel != null)
        {
            systemicPanel.remove(newMSMuclePainsPanel);
            //newMSMuclePainsPanel = null;
        }
        if (newMSSpinalPainsPanel != null)
        {
            systemicPanel.remove(newMSSpinalPainsPanel);
            //newMSSpinalPainsPanel = null;
        }
        if (newMSExtraArticularPanel != null)
        {
            systemicPanel.remove(newMSExtraArticularPanel);
            //newMSExtraArticularPanel = null;
        }
        if (newMSJointDeformityPanel != null)
        {
            systemicPanel.remove(newMSJointDeformityPanel);
            //newMSJointDeformityPanel = null;
        }
        if (newMSOtherPanel != null)
        {
            systemicPanel.remove(newMSOtherPanel);
            //newMSOtherPanel = null;
        }
        if (newCNSDizzySpellsPanel != null)
        {
            systemicPanel.remove(newCNSDizzySpellsPanel);
            //newCNSDizzySpellsPanel = null;
        }
        if (newCNSHeadachePanel != null)
        {
            systemicPanel.remove(newCNSHeadachePanel);
            //newCNSHeadachePanel = null;
        }
        if (newCNSNeckStiffnessPanel != null)
        {
            systemicPanel.remove(newCNSNeckStiffnessPanel);
            //newCNSNeckStiffnessPanel = null;
        }
        if (newCNSMotorDefecitPanel != null)
        {
            systemicPanel.remove(newCNSMotorDefecitPanel);
            //newCNSMotorDefecitPanel = null;
        }
        if (newCNSSAndCNerveDefecitPanel != null)
        {
            systemicPanel.remove(newCNSSAndCNerveDefecitPanel);
            //newCNSSAndCNerveDefecitPanel = null;
        }
        if (newCNSCerebellarDefecitPanel != null)
        {
            systemicPanel.remove(newCNSCerebellarDefecitPanel);
            //newCNSCerebellarDefecitPanel = null;
        }
        if (newCNSGaitDefecitPanel != null)
        {
            systemicPanel.remove(newCNSGaitDefecitPanel);
            //newCNSGaitDefecitPanel = null;
        }
        if (newCNSIncontinancePanel != null)
        {
            systemicPanel.remove(newCNSIncontinancePanel);
            //newCNSIncontinancePanel = null;
        }
        if (newCNSSTMemoryLossPanel != null)
        {
            systemicPanel.remove(newCNSSTMemoryLossPanel);
            //newCNSSTMemoryLossPanel = null;
        }
        if (newCNSLossOfConciousnessPanel != null)
        {
            systemicPanel.remove(newCNSLossOfConciousnessPanel);
            //newCNSLossOfConciousnessPanel = null;
        }
        if (newCNSConvultionsPanel != null)
        {
            systemicPanel.remove(newCNSConvultionsPanel);
            //newCNSConvultionsPanel = null;
        }
        if (newCNSHigherFunctionAbnormalitiesPanel != null)
        {
            systemicPanel.remove(newCNSHigherFunctionAbnormalitiesPanel);
            //newCNSHigherFunctionAbnormalitiesPanel = null;
        }
        if (newENTSoreThroatPanel != null)
        {
            systemicPanel.remove(newENTSoreThroatPanel);
            //newENTSoreThroatPanel = null;
        }
        if (newENTPostNasalDripPanel != null)
        {
            systemicPanel.remove(newENTPostNasalDripPanel);
            //newENTPostNasalDripPanel = null;
        }
        if (newENTDysphagiaPanel != null)
        {
            systemicPanel.remove(newENTDysphagiaPanel);
            //newENTDysphagiaPanel = null;
        }
        if (newENTHoarseVoicePanel != null)
        {
            systemicPanel.remove(newENTHoarseVoicePanel);
            //newENTHoarseVoicePanel = null;
        }
        if (newENTThroatSwellingPanel != null)
        {
            systemicPanel.remove(newENTThroatSwellingPanel);
            //newENTThroatSwellingPanel = null;
        }
        if (newENTSnoringPanel != null)
        {
            systemicPanel.remove(newENTSnoringPanel);
            //newENTSnoringPanel = null;
        }
        if (newENTBlockedNosePanel != null)
        {
            systemicPanel.remove(newENTBlockedNosePanel);
            //newENTBlockedNosePanel = null;
        }
        if (newENTSinusPainsPanel != null)
        {
            systemicPanel.remove(newENTSinusPainsPanel);
            //newENTSinusPainsPanel = null;
        }
        if (newENTNoseBleedPanel != null)
        {
            systemicPanel.remove(newENTNoseBleedPanel);
            //newENTNoseBleedPanel = null;
        }
        if (newENTSneezingPanel != null)
        {
            systemicPanel.remove(newENTSneezingPanel);
            //newENTSneezingPanel = null;
        }
        if (newENTRhinorrhoeaPanel != null)
        {
            systemicPanel.remove(newENTRhinorrhoeaPanel);
            //newENTRhinorrhoeaPanel = null;
        }
        if (newENTEarAchePanel != null)
        {
            systemicPanel.remove(newENTEarAchePanel);
            //newENTEarAchePanel = null;
        }
        if (newENTEarDischargePanel != null)
        {
            systemicPanel.remove(newENTEarDischargePanel);
            //newENTEarDischargePanel = null;
        }
        if (newENTDeafnessPanel != null)
        {
            systemicPanel.remove(newENTDeafnessPanel);
            //newENTDeafnessPanel = null;
        }
        if (newENTVertigoPanel != null)
        {
            systemicPanel.remove(newENTVertigoPanel);
            //newENTVertigoPanel = null;
        }
        if (newENTTinnitusPanel != null)
        {
            systemicPanel.remove(newENTTinnitusPanel);
            //newENTTinnitusPanel = null;
        }
        if (newDermaRashPanel != null)
        {
            systemicPanel.remove(newDermaRashPanel);
            //newDermaRashPanel = null;
        }
        if (newDermaUlcersPanel != null)
        {
            systemicPanel.remove(newDermaUlcersPanel);
            //newDermaUlcersPanel = null;
        }
        if (newDermaVitiligoPanel != null)
        {
            systemicPanel.remove(newDermaVitiligoPanel);
            //newDermaVitiligoPanel = null;
        }
        if (newDermaAllopeciaPanel != null)
        {
            systemicPanel.remove(newDermaAllopeciaPanel);
            //newDermaAllopeciaPanel = null;
        }
        if (newDermaPetechiaePanel != null)
        {
            systemicPanel.remove(newDermaPetechiaePanel);
            //newDermaPetechiaePanel = null;
        }
        if (newDermaPurperaPanel != null)
        {
            systemicPanel.remove(newDermaPurperaPanel);
            //newDermaPurperaPanel = null;
        }
        if (newHemPetechiaePanel != null)
        {
            systemicPanel.remove(newHemPetechiaePanel);
            //newHemPetechiaePanel = null;
        }
        if (newHemPurperaPanel != null)
        {
            systemicPanel.remove(newHemPurperaPanel);
            //newHemPurperaPanel = null;
        }
        if (newEyesLossOfVisionPanel != null)
        {
            systemicPanel.remove(newEyesLossOfVisionPanel);
            //newEyesLossOfVisionPanel = null;
        }
        if (newEyesDiplopiaPanel != null)
        {
            systemicPanel.remove(newEyesDiplopiaPanel);
            //newEyesDiplopiaPanel = null;
        }
        if (newEyesPainPanel != null)
        {
            systemicPanel.remove(newEyesPainPanel);
            //newEyesPainPanel = null;
        }
        if (newEyesDischargePanel != null)
        {
            systemicPanel.remove(newEyesDischargePanel);
            //newEyesDischargePanel = null;
        }
        if (newEyesRedEyePanel != null)
        {
            systemicPanel.remove(newEyesRedEyePanel);
            //newEyesRedEyePanel = null;
        }
        if (newUGIncontinancePanel != null)
        {
            systemicPanel.remove(newUGIncontinancePanel);
            //newUGIncontinancePanel = null;
        }
        if (newUGUrineRetentionPanel != null)
        {
            systemicPanel.remove(newUGUrineRetentionPanel);
            //newUGUrineRetentionPanel = null;
        }
        if (newUGDysuriaPanel != null)
        {
            systemicPanel.remove(newUGDysuriaPanel);
            //newUGDysuriaPanel = null;
        }
        if (newUGDischargePanel != null)
        {
            systemicPanel.remove(newUGDischargePanel);
            //newUGDischargePanel = null;
        }
        if (newUGBleedingPanel != null)
        {
            systemicPanel.remove(newUGBleedingPanel);
            //newUGBleedingPanel = null;
        }
        if (newEndoThyroidPanel != null)
        {
            systemicPanel.remove(newEndoThyroidPanel);
            //newEndoThyroidPanel = null;
        }
        if (newEndoAdrenalPanel != null)
        {
            systemicPanel.remove(newEndoAdrenalPanel);
            //newEndoAdrenalPanel = null;
        }
        if (newEndoPituitaryPanel != null)
        {
            systemicPanel.remove(newEndoPituitaryPanel);
            //newEndoPituitaryPanel = null;
        }
        if (newEndoParathyroidsPanel != null)
        {
            systemicPanel.remove(newEndoParathyroidsPanel);
            //newEndoParathyroidsPanel = null;
        }
        if (newEndoPancreasPanel != null)
        {
            systemicPanel.remove(newEndoPancreasPanel);
            //newEndoPancreasPanel = null;
        }
        if (newEndoGonadsPanel != null)
        {
            systemicPanel.remove(newEndoGonadsPanel);
            //newEndoGonadsPanel = null;
        }
        
    }

    public void removeENTPanels()
    {
        if (newENTEarPanel != null)
        {
            captureExamEarNoseThroatFrame.getContentPane().remove(newENTEarPanel);
            //newConstitutionalPanel = null;
        }
        if (newENTNosePanel != null)
        {
            captureExamEarNoseThroatFrame.getContentPane().remove(newENTNosePanel);
            //newConstitutionalPanel = null;
        }
        if (newENTParanasalPassagesPanel != null)
        {
            captureExamEarNoseThroatFrame.getContentPane().remove(newENTParanasalPassagesPanel);
            //newConstitutionalPanel = null;
        }
        if (newENTThroatPanel != null)
        {
            captureExamEarNoseThroatFrame.getContentPane().remove(newENTThroatPanel);
            //newConstitutionalPanel = null;
        }
        if (dummyPanel != null)
        {
            captureExamEarNoseThroatFrame.getContentPane().remove(dummyPanel);
        }

    }    
    
    public void captureTreatmentAllopathic()//Invoked From Actionlistener 
    {

        tabIconName = "treatment_sm.gif";
        removeTreatmentPlanPanels();
        treatmentPlanAllopathicPanel = new AddInfoMVC(patientKey, "ADDTREATMENTPLAN",capturePanel,captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = treatmentPlanAllopathicPanel.theDataModel.getUpdateButtonEvent();
        treatmentPanel.add(treatmentPlanAllopathicPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureTreatmentRife()//Invoked From Actionlistener 
    {

        tabIconName = "Rife_sm.gif";
        removeTreatmentPlanPanels();
        rifePanel = new RifeTablePanel();
        updateButtonEvent = CAPTURE_TREATMENT;
        //put right event in here
        //updateButtonEvent = treatmentPlanAllopathicPanel.theDataModel.getUpdateButtonEvent();
        treatmentPanel.add(rifePanel,BorderLayout.CENTER);
        treatmentPanel.setSize(600,600);
        desktop.updateUI();
    }

    
    
    public void captureENTEar()//Invoked From Actionlistener 
    {
        tabIconName = "earnosethroat_sm.gif";
        removeENTPanels();
        newENTEarPanel = new AddInfoMVC(patientKey, "ADDENTEAR", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord,400,520);
        //updateButtonEvent = newENTEarPanel.theDataModel.getUpdateButtonEvent();
        captureExamEarNoseThroatFrame.getContentPane().add(newENTEarPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureENTNose()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeENTPanels();
        newENTNosePanel = new AddInfoMVC(patientKey, "ADDENTNOSE", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord,400,520);
        //updateButtonEvent = newENTNosePanel.theDataModel.getUpdateButtonEvent();
        captureExamEarNoseThroatFrame.getContentPane().add(newENTNosePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureENTPNP()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeENTPanels();
        newENTParanasalPassagesPanel = new AddInfoMVC(patientKey, "ADDENTPNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord,400,520);
        //updateButtonEvent = newENTParanasalPassagesPanel.theDataModel.getUpdateButtonEvent();
        captureExamEarNoseThroatFrame.getContentPane().add(newENTParanasalPassagesPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureENTThroat()//Invoked From Actionlistener 
    {
        tabIconName = "earnosethroat_sm.gif";
        removeENTPanels();
        newENTThroatPanel = new AddInfoMVC(patientKey, "ADDENTTHROAT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord,400,520);
        //updateButtonEvent = newENTThroatPanel.theDataModel.getUpdateButtonEvent();
        captureExamEarNoseThroatFrame.getContentPane().add(newENTThroatPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void removeUROPanels()
    {
        if (newUROMalePanel != null)
        {
            captureExamUrogenitalFrame.getContentPane().remove(newUROMalePanel);
        }
        if (newUROFemalePanel != null)
        {
            captureExamUrogenitalFrame.getContentPane().remove(newUROFemalePanel);
        }
        if (newUROUrinePanel != null)
        {
            captureExamUrogenitalFrame.getContentPane().remove(newUROUrinePanel);
        }
        if (dummyPanel != null)
        {
        captureExamUrogenitalFrame.getContentPane().remove(dummyPanel);
        }
    }    

    public void captureUROMale()//Invoked From Actionlistener 
    {

        tabIconName = "ugtmale_sm.gif";
        removeUROPanels();
        newUROMalePanel = new AddInfoMVC(patientKey, "ADDEXAMUROMALE", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord,400,520);
        //updateButtonEvent = newUROMalePanel.theDataModel.getUpdateButtonEvent();
        captureExamUrogenitalFrame.getContentPane().add(newUROMalePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureUROFemale()//Invoked From Actionlistener 
    {

        tabIconName = "ugtfemale_sm.gif";
        removeUROPanels();
        newUROFemalePanel = new AddInfoMVC(patientKey, "ADDEXAMUROFEMALE", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord,400,520);
        //updateButtonEvent = newUROFemalePanel.theDataModel.getUpdateButtonEvent();
        captureExamUrogenitalFrame.getContentPane().add(newUROFemalePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureUROUrine()//Invoked From Actionlistener 
    {

        tabIconName = "ugturine_sm.gif";
        removeUROPanels();
        newUROUrinePanel = new AddInfoMVC(patientKey, "ADDEXAMUROURINE", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord,400,520);
        //updateButtonEvent = newUROUrinePanel.theDataModel.getUpdateButtonEvent();
        captureExamUrogenitalFrame.getContentPane().add(newUROUrinePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureConstitutional()//Invoked From Actionlistener 
    {

        tabIconName = "constitutional_sm.gif";
        removeSystemicPanels();
        newConstitutionalPanel = new AddInfoMVC(patientKey, "ADDCONSTITUTIONAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newConstitutionalPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newConstitutionalPanel);
        //systemicInternalFrame.getContentPane().add(newConstitutionalPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void capturePalpitations()//Invoked From Actionlistener 
    {
        tabIconName = "cardiovascular_sm.gif";
        removeSystemicPanels();
        newPalpitationsPanel = new AddInfoMVC(patientKey, "1", "ADDSYSTEMICCARDIOVASCULAR", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newPalpitationsPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newPalpitationsPanel);
        //systemicInternalFrame.getContentPane().add(newPalpitationsPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureChestPain()//Invoked From Actionlistener 
    {
        tabIconName = "cardiovascular_sm.gif";
        removeSystemicPanels();
        newChestPainPanel = new AddInfoMVC(patientKey, "2", "ADDSYSTEMICCARDIOVASCULAR", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newChestPainPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newChestPainPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureDyspnea()//Invoked From Actionlistener 
    {

        tabIconName = "cardiovascular_sm.gif";
        removeSystemicPanels();
        newDyspneaPanel = new AddInfoMVC(patientKey, "3", "ADDSYSTEMICCARDIOVASCULAR", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newDyspneaPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newDyspneaPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureClaudication()//Invoked From Actionlistener 
    {

        tabIconName = "cardiovascular_sm.gif";
        removeSystemicPanels();
        newClaudicationPanel = new AddInfoMVC(patientKey, "4", "ADDSYSTEMICCARDIOVASCULAR", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newClaudicationPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newClaudicationPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureLegSwelling()//Invoked From Actionlistener 
    {

        tabIconName = "cardiovascular_sm.gif";
        removeSystemicPanels();
        newLegSwellingPanel = new AddInfoMVC(patientKey, "5", "ADDSYSTEMICCARDIOVASCULAR", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newLegSwellingPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newLegSwellingPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
 
    public void captureRespDyspnea()//Invoked From Actionlistener 
    {

        tabIconName = "respiratory_sm.gif";
        removeSystemicPanels();
        newRespDyspneaPanel = new AddInfoMVC(patientKey, "1", "ADDSYSTEMICRESPIRATORY", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newRespDyspneaPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newRespDyspneaPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureRespChestPain()//Invoked From Actionlistener 
    {

        tabIconName = "respiratory_sm.gif";
        removeSystemicPanels();
        newRespChestPainPanel = new AddInfoMVC(patientKey, "2", "ADDSYSTEMICRESPIRATORY", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newRespChestPainPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newRespChestPainPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureRespChestTightness()//Invoked From Actionlistener 
    {

        tabIconName = "respiratory_sm.gif";
        removeSystemicPanels();
        newRespChestTightnessPanel = new AddInfoMVC(patientKey, "3", "ADDSYSTEMICRESPIRATORY", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newRespChestTightnessPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newRespChestTightnessPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureRespCoughing()//Invoked From Actionlistener 
    {

        tabIconName = "respiratory_sm.gif";
        removeSystemicPanels();
        newRespCoughingPanel = new AddInfoMVC(patientKey, "4", "ADDSYSTEMICRESPIRATORY", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newRespCoughingPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newRespCoughingPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureAbdNausea()//Invoked From Actionlistener 
    {

        tabIconName = "abdominal_sm.gif";
        removeSystemicPanels();
        newAbdNauseaPanel = new AddInfoMVC(patientKey, "1", "ADDSYSTEMICABDOMINAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newAbdNauseaPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newAbdNauseaPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureAbdVomiting()//Invoked From Actionlistener 
    {

        tabIconName = "abdominal_sm.gif";
        removeSystemicPanels();
        newAbdVomitingPanel = new AddInfoMVC(patientKey, "2", "ADDSYSTEMICABDOMINAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newAbdVomitingPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newAbdVomitingPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    
    
    public void captureAbdDiarrhea()//Invoked From Actionlistener 
    {

        tabIconName = "abdominal_sm.gif";
        removeSystemicPanels();
        newAbdDiarrheaPanel = new AddInfoMVC(patientKey, "3", "ADDSYSTEMICABDOMINAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newAbdDiarrheaPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newAbdDiarrheaPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureAbdConstipation()//Invoked From Actionlistener 
    {

        tabIconName = "abdominal_sm.gif";
        removeSystemicPanels();
        newAbdConstipationPanel = new AddInfoMVC(patientKey, "4", "ADDSYSTEMICABDOMINAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newAbdConstipationPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newAbdConstipationPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureAbdominalPain()//Invoked From Actionlistener 
    {

        tabIconName = "abdominal_sm.gif";
        removeSystemicPanels();
        newAbdominalPainPanel = new AddInfoMVC(patientKey, "5", "ADDSYSTEMICABDOMINAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newAbdominalPainPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newAbdominalPainPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureAbdTenesmus()//Invoked From Actionlistener 
    {

        tabIconName = "abdominal_sm.gif";
        removeSystemicPanels();
        newAbdTenesmusPanel = new AddInfoMVC(patientKey, "6", "ADDSYSTEMICABDOMINAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newAbdTenesmusPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newAbdTenesmusPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureAbdRectalProlapse()//Invoked From Actionlistener 
    {

        tabIconName = "abdominal_sm.gif";
        removeSystemicPanels();
        newAbdRectalProlapsePanel = new AddInfoMVC(patientKey, "7", "ADDSYSTEMICABDOMINAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newAbdRectalProlapsePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newAbdRectalProlapsePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureAbdHematemesis()//Invoked From Actionlistener 
    {

        tabIconName = "abdominal_sm.gif";
        removeSystemicPanels();
        newAbdHematemesisPanel = new AddInfoMVC(patientKey, "8", "ADDSYSTEMICABDOMINAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newAbdHematemesisPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newAbdHematemesisPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureAbdBloodPerRectum()//Invoked From Actionlistener 
    {

        tabIconName = "abdominal_sm.gif";
        removeSystemicPanels();
        newAbdBloodPerRectumPanel = new AddInfoMVC(patientKey, "9", "ADDSYSTEMICABDOMINAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newAbdBloodPerRectumPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newAbdBloodPerRectumPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureAbdJaundice()//Invoked From Actionlistener 
    {

        tabIconName = "abdominal_sm.gif";
        removeSystemicPanels();
        newAbdJaundicePanel = new AddInfoMVC(patientKey, "10", "ADDSYSTEMICABDOMINAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newAbdJaundicePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newAbdJaundicePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
   
    public void captureMSJointPains()//Invoked From Actionlistener 
    {

        tabIconName = "muskuloskeletal_sm.gif";
        removeSystemicPanels();
        newMSJointPainsPanel = new AddInfoMVC(patientKey, "1", "ADDSYSTEMICMUSCULOSKELETAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newMSJointPainsPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newMSJointPainsPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureMSJointSwellings()//Invoked From Actionlistener 
    {

        tabIconName = "muskuloskeletal_sm.gif";
        removeSystemicPanels();
        newMSJointSwellingsPanel = new AddInfoMVC(patientKey, "2", "ADDSYSTEMICMUSCULOSKELETAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newMSJointSwellingsPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newMSJointSwellingsPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureMSMusclePains()//Invoked From Actionlistener 
    {

        tabIconName = "muskuloskeletal_sm.gif";
        removeSystemicPanels();
        newMSMuclePainsPanel = new AddInfoMVC(patientKey, "3", "ADDSYSTEMICMUSCULOSKELETAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newMSMuclePainsPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newMSMuclePainsPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureMSSpinalPains()//Invoked From Actionlistener 
    {

        tabIconName = "muskuloskeletal_sm.gif";
        removeSystemicPanels();
        newMSSpinalPainsPanel = new AddInfoMVC(patientKey, "4", "ADDSYSTEMICMUSCULOSKELETAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newMSSpinalPainsPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newMSSpinalPainsPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureMSExtraArticular()//Invoked From Actionlistener 
    {

        tabIconName = "muskuloskeletal_sm.gif";
        removeSystemicPanels();
        newMSExtraArticularPanel = new AddInfoMVC(patientKey, "5", "ADDSYSTEMICMUSCULOSKELETAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newMSExtraArticularPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newMSExtraArticularPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureMSJointDeformity()//Invoked From Actionlistener 
    {

        tabIconName = "muskuloskeletal_sm.gif";
        removeSystemicPanels();
        newMSJointDeformityPanel = new AddInfoMVC(patientKey, "6", "ADDSYSTEMICMUSCULOSKELETAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newMSJointDeformityPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newMSJointDeformityPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureMSOtherMusculoskeletal()//Invoked From Actionlistener 
    {

        tabIconName = "muskuloskeletal_sm.gif";
        removeSystemicPanels();
        newMSOtherPanel = new AddInfoMVC(patientKey, "7", "ADDSYSTEMICMUSCULOSKELETAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newMSOtherPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newMSOtherPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureCNSHeadache()//Invoked From Actionlistener 
    {

        tabIconName = "cnsexam_sm.gif";
        removeSystemicPanels();
        newCNSHeadachePanel = new AddInfoMVC(patientKey, "1", "ADDSYSTEMICCNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newCNSHeadachePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newCNSHeadachePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureCNSNeckStiffness()//Invoked From Actionlistener 
    {

        tabIconName = "cnsexam_sm.gif";
        removeSystemicPanels();
        newCNSNeckStiffnessPanel = new AddInfoMVC(patientKey, "2", "ADDSYSTEMICCNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newCNSNeckStiffnessPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newCNSNeckStiffnessPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureCNSMotorDefecit()//Invoked From Actionlistener 
    {

        tabIconName = "cnsexam_sm.gif";
        removeSystemicPanels();
        newCNSMotorDefecitPanel = new AddInfoMVC(patientKey, "3", "ADDSYSTEMICCNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newCNSMotorDefecitPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newCNSMotorDefecitPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureCNSSAndCNerveDefecit()//Invoked From Actionlistener 
    {

        tabIconName = "cnsexam_sm.gif";
        removeSystemicPanels();
        newCNSSAndCNerveDefecitPanel = new AddInfoMVC(patientKey, "4", "ADDSYSTEMICCNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newCNSSAndCNerveDefecitPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newCNSSAndCNerveDefecitPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureCNSCerebellarDefecit()//Invoked From Actionlistener 
    {

        tabIconName = "cnsexam_sm.gif";
        removeSystemicPanels();
        newCNSCerebellarDefecitPanel = new AddInfoMVC(patientKey, "5", "ADDSYSTEMICCNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newCNSCerebellarDefecitPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newCNSCerebellarDefecitPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureCNSGaitDefecit()//Invoked From Actionlistener 
    {

        tabIconName = "cnsexam_sm.gif";
        removeSystemicPanels();
        newCNSGaitDefecitPanel = new AddInfoMVC(patientKey, "6", "ADDSYSTEMICCNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newCNSGaitDefecitPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newCNSGaitDefecitPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureCNSIncontinance()//Invoked From Actionlistener 
    {

        tabIconName = "cnsexam_sm.gif";
        removeSystemicPanels();
        newCNSIncontinancePanel = new AddInfoMVC(patientKey, "7", "ADDSYSTEMICCNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newCNSIncontinancePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newCNSIncontinancePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureCNSSTMemoryLoss()//Invoked From Actionlistener 
    {

        tabIconName = "cnsexam_sm.gif";
        removeSystemicPanels();
        newCNSSTMemoryLossPanel = new AddInfoMVC(patientKey, "8", "ADDSYSTEMICCNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newCNSSTMemoryLossPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newCNSSTMemoryLossPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureCNSLossOfConciousness()//Invoked From Actionlistener 
    {

        tabIconName = "cnsexam_sm.gif";
        removeSystemicPanels();
        newCNSLossOfConciousnessPanel = new AddInfoMVC(patientKey, "9", "ADDSYSTEMICCNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newCNSLossOfConciousnessPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newCNSLossOfConciousnessPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureCNSConvultions()//Invoked From Actionlistener 
    {

        tabIconName = "cnsexam_sm.gif";
        removeSystemicPanels();
        newCNSConvultionsPanel = new AddInfoMVC(patientKey, "10", "ADDSYSTEMICCNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newCNSConvultionsPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newCNSConvultionsPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureCNSDizzySpells()//Invoked From Actionlistener 
    {

        tabIconName = "cnsexam_sm.gif";
        removeSystemicPanels();
        newCNSDizzySpellsPanel = new AddInfoMVC(patientKey, "11", "ADDSYSTEMICCNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newCNSDizzySpellsPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newCNSDizzySpellsPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
 
    public void captureCNSHigherFunctionAbnormalities()//Invoked From Actionlistener 
    {

        tabIconName = "cnsexam_sm.gif";
        removeSystemicPanels();
        newCNSHigherFunctionAbnormalitiesPanel = new AddInfoMVC(patientKey, "12", "ADDSYSTEMICCNS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newCNSHigherFunctionAbnormalitiesPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newCNSHigherFunctionAbnormalitiesPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureENTSoreThroat()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTSoreThroatPanel = new AddInfoMVC(patientKey, "1", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        //newENTSoreThroatPanel.set
        updateButtonEvent = newENTSoreThroatPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTSoreThroatPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureENTPostNasalDrip()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTPostNasalDripPanel = new AddInfoMVC(patientKey, "2", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTPostNasalDripPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTPostNasalDripPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureENTDysphagia()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTDysphagiaPanel = new AddInfoMVC(patientKey, "3", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTDysphagiaPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTDysphagiaPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureENTHoarseVoice()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTHoarseVoicePanel = new AddInfoMVC(patientKey, "4", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTHoarseVoicePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTHoarseVoicePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureENTThroatSwelling()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTThroatSwellingPanel = new AddInfoMVC(patientKey, "5", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTThroatSwellingPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTThroatSwellingPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureENTSnoring()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTSnoringPanel = new AddInfoMVC(patientKey, "6", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTSnoringPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTSnoringPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureENTBlockedNose()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTBlockedNosePanel = new AddInfoMVC(patientKey, "7", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTBlockedNosePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTBlockedNosePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureENTSinusPains()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTSinusPainsPanel = new AddInfoMVC(patientKey, "8", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTSinusPainsPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTSinusPainsPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureENTNoseBleed()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTNoseBleedPanel = new AddInfoMVC(patientKey, "9", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTNoseBleedPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTNoseBleedPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureENTSneezing()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTSneezingPanel = new AddInfoMVC(patientKey, "10", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTSneezingPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTSneezingPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureENTRhinorrhoea()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTRhinorrhoeaPanel = new AddInfoMVC(patientKey, "11", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTRhinorrhoeaPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTRhinorrhoeaPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureENTEarAche()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTEarAchePanel = new AddInfoMVC(patientKey, "12", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTEarAchePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTEarAchePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureENTEarDischarge()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTEarDischargePanel = new AddInfoMVC(patientKey, "13", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTEarDischargePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTEarDischargePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureENTDeafness()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTDeafnessPanel = new AddInfoMVC(patientKey, "14", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTDeafnessPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTDeafnessPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureENTTinnitus()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTTinnitusPanel = new AddInfoMVC(patientKey, "15", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTTinnitusPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTTinnitusPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureENTVertigo()//Invoked From Actionlistener 
    {

        tabIconName = "earnosethroat_sm.gif";
        removeSystemicPanels();
        newENTVertigoPanel = new AddInfoMVC(patientKey, "16", "ADDSYSTEMICENT", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newENTVertigoPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newENTVertigoPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureDermaRash()//Invoked From Actionlistener 
    {
        tabIconName = "dermatological_sm.gif";

        removeSystemicPanels();
        newDermaRashPanel = new AddInfoMVC(patientKey, "1", "ADDSYSTEMICDERMA", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newDermaRashPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newDermaRashPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public void captureDermaUlcers()//Invoked From Actionlistener 
    {

        tabIconName = "dermatological_sm.gif";
        removeSystemicPanels();
        newDermaUlcersPanel = new AddInfoMVC(patientKey, "2", "ADDSYSTEMICDERMA", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newDermaUlcersPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newDermaUlcersPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureDermaVitiligo()//Invoked From Actionlistener 
    {

        tabIconName = "dermatological_sm.gif";
        removeSystemicPanels();
        newDermaVitiligoPanel = new AddInfoMVC(patientKey, "3", "ADDSYSTEMICDERMA", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newDermaVitiligoPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newDermaVitiligoPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureDermaAllopecia()//Invoked From Actionlistener 
    {

        tabIconName = "dermatological_sm.gif";
        removeSystemicPanels();
        newDermaAllopeciaPanel = new AddInfoMVC(patientKey, "4", "ADDSYSTEMICDERMA", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newDermaAllopeciaPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newDermaAllopeciaPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureDermaPetechiae()//Invoked From Actionlistener 
    {

        tabIconName = "dermatological_sm.gif";
        removeSystemicPanels();
        newDermaPetechiaePanel = new AddInfoMVC(patientKey, "5", "ADDSYSTEMICDERMA", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newDermaPetechiaePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newDermaPetechiaePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureDermaPurpera()//Invoked From Actionlistener 
    {

        tabIconName = "dermatological_sm.gif";
        removeSystemicPanels();
        newDermaPurperaPanel = new AddInfoMVC(patientKey, "6", "ADDSYSTEMICDERMA", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newDermaPurperaPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newDermaPurperaPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureHemPetechiae()//Invoked From Actionlistener 
    {

        tabIconName = "hematological_sm.gif";
        removeSystemicPanels();
        newHemPetechiaePanel = new AddInfoMVC(patientKey, "1", "ADDSYSTEMICHEMA", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newHemPetechiaePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newHemPetechiaePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureHemPurpera()//Invoked From Actionlistener 
    {

        tabIconName = "hematological_sm.gif";
        removeSystemicPanels();
        newHemPurperaPanel = new AddInfoMVC(patientKey, "2", "ADDSYSTEMICHEMA", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newHemPurperaPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newHemPurperaPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureEyesLossOfVision()//Invoked From Actionlistener 
    {

        tabIconName = "eyes_sm.gif";
        removeSystemicPanels();
        newEyesLossOfVisionPanel = new AddInfoMVC(patientKey, "1", "ADDSYSTEMICEYES", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newEyesLossOfVisionPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newEyesLossOfVisionPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureEyesRedEye()//Invoked From Actionlistener 
    {

        tabIconName = "eyes_sm.gif";
        removeSystemicPanels();
        newEyesRedEyePanel = new AddInfoMVC(patientKey, "2", "ADDSYSTEMICEYES", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newEyesRedEyePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newEyesRedEyePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    public void captureEyesDischarge()//Invoked From Actionlistener 
    {

        tabIconName = "eyes_sm.gif";
        removeSystemicPanels();
        newEyesDischargePanel = new AddInfoMVC(patientKey, "3", "ADDSYSTEMICEYES", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newEyesDischargePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newEyesDischargePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    public void captureEyesPain()//Invoked From Actionlistener 
    {

        tabIconName = "eyes_sm.gif";
        removeSystemicPanels();
        newEyesPainPanel = new AddInfoMVC(patientKey, "4", "ADDSYSTEMICEYES", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newEyesPainPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newEyesPainPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureEyesDiplopia()//Invoked From Actionlistener 
    {

        tabIconName = "eyes_sm.gif";
        removeSystemicPanels();
        newEyesDiplopiaPanel = new AddInfoMVC(patientKey, "5", "ADDSYSTEMICEYES", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newEyesDiplopiaPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newEyesDiplopiaPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureUGIncontinance()//Invoked From Actionlistener 
    {

        tabIconName = "urogenital_sm.gif";
        removeSystemicPanels();
        newUGIncontinancePanel = new AddInfoMVC(patientKey, "1", "ADDSYSTEMICUROGENITAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newUGIncontinancePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newUGIncontinancePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureUGUrineRetention()//Invoked From Actionlistener 
    {

        tabIconName = "urogenital_sm.gif";
        removeSystemicPanels();
        newUGUrineRetentionPanel = new AddInfoMVC(patientKey, "2", "ADDSYSTEMICUROGENITAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newUGUrineRetentionPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newUGUrineRetentionPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureUGDysuria()//Invoked From Actionlistener 
    {

        tabIconName = "urogenital_sm.gif";
        removeSystemicPanels();
        newUGDysuriaPanel = new AddInfoMVC(patientKey, "3", "ADDSYSTEMICUROGENITAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newUGDysuriaPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newUGDysuriaPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureUGDischarge()//Invoked From Actionlistener 
    {

        tabIconName = "urogenital_sm.gif";
        removeSystemicPanels();
        newUGDischargePanel = new AddInfoMVC(patientKey, "4", "ADDSYSTEMICUROGENITAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newUGDischargePanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newUGDischargePanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureUGBleeding()//Invoked From Actionlistener 
    {

        tabIconName = "urogenital_sm.gif";
        removeSystemicPanels();
        newUGBleedingPanel = new AddInfoMVC(patientKey, "5", "ADDSYSTEMICUROGENITAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newUGBleedingPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newUGBleedingPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }

    public void captureEndoThyroid()//Invoked From Actionlistener 
    {

        tabIconName = "endocrine_sm.gif";
        removeSystemicPanels();
        newEndoThyroidPanel = new AddInfoMVC(patientKey, "ADDTHYROID", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newEndoThyroidPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newEndoThyroidPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    public void captureAdrenalGland()//Invoked From Actionlistener 
    {

        //htodo
        tabIconName = "endocrine_sm.gif";
        removeSystemicPanels();
        newEndoAdrenalPanel = new AddInfoMVC(patientKey, "ADDADRENAL", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newEndoAdrenalPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newEndoAdrenalPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    public void capturePituitaryGland()//Invoked From Actionlistener 
    {

        //htodo
        tabIconName = "endocrine_sm.gif";
        removeSystemicPanels();
        newEndoPituitaryPanel = new AddInfoMVC(patientKey, "ADDPITUITARY", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newEndoPituitaryPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newEndoPituitaryPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    public void captureParathyroids()//Invoked From Actionlistener 
    {

        //htodo
        tabIconName = "endocrine_sm.gif";
        removeSystemicPanels();
        newEndoParathyroidsPanel = new AddInfoMVC(patientKey, "ADDPARATHYROID", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newEndoParathyroidsPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newEndoParathyroidsPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    public void capturePancreas()//Invoked From Actionlistener 
    {

        //htodo
        tabIconName = "endocrine_sm.gif";
        removeSystemicPanels();
        newEndoPancreasPanel = new AddInfoMVC(patientKey, "ADDPANCREAS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newEndoPancreasPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newEndoPancreasPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    public void captureGonads()//Invoked From Actionlistener 
    {

        //htodo
        tabIconName = "endocrine_sm.gif";
        removeSystemicPanels();
        newEndoGonadsPanel = new AddInfoMVC(patientKey, "ADDGONADS", capturePanel, captureList, JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord);
        updateButtonEvent = newEndoGonadsPanel.theDataModel.getUpdateButtonEvent();
        systemicPanel.add(newEndoGonadsPanel,BorderLayout.CENTER);
        desktop.updateUI();
    }
    
    public String getPatientName(String patientKey)
    {
        String patientName = null; 
        try
        {
            String sqlQuery = "SELECT FIRST_NAME, INITIALS, SURNAME FROM PATIENT_PHYSICAL WHERE ACCOUNT_NUMBER = " + patientKey;
            String[] combineList = {"FIRST_NAME", "INITIALS", "SURNAME"}; 
            DBAccess dbAccessInstance = new DBAccess(HOST_NAME, DATABASE_NAME, "PATIENT_PHYSICAL", DEF_USER_NAME, DEF_STR_PASSWORD, OS_VERSION);
            patientName = dbAccessInstance.getSingleLookupDataCombine(sqlQuery, combineList);
            while (patientName.indexOf("null") > -1)
            {
               int startIndex = patientName.indexOf("null");
               int endIndex = patientName.indexOf("null") + 4;
               patientName = patientName.substring(0, startIndex) + patientName.substring(endIndex, patientName.length());
            }
        }
        catch (Exception e)
        {
             JOptionPane.showMessageDialog(null, e.getMessage(),
             "Could not retrieve Patient Name.", JOptionPane.ERROR_MESSAGE);
        }
        return patientName;
    }
    

    public void displayPatientCurrentStatus() 
    //confirmDateSelection()
    {
           String tmpPatientKey = persInfoPanel.getSelectedKeyValue();
           if ((tmpPatientKey == null ) || (tmpPatientKey.length() == 0))
           {
               JOptionPane.showMessageDialog(this, "Please select a patient first.", "No Patient Selected", JOptionPane.ERROR_MESSAGE);
               return;
           }
           if (patientDataFetched == true) return;
       dateArrayList = persInfoPanel.getDateList();
       HashMap dateSet = new HashMap(dateArrayList.size());
       for (int dateLoop = 0; dateLoop < dateArrayList.size(); dateLoop++)
       {
            String dateOnly = ((String)dateArrayList.get(dateLoop)).substring(0,10); 
            dateSet.put((Object)dateOnly,(Object)"dummy");
       }
       
       
       dateInfoFrame = new JInternalFrame("Visit Date Picklist",
       false, //resizable
       false, //closable
       false, //maximizable
       false); //iconifiable
       dateInfoFrame.setSize(new Dimension(180, 300));
       dateInfoFrame.setLocation(FRAME_X,FRAME_Y);
       dateInfoFrame.setFrameIcon(new ImageIcon(imagePath + "examination_sm.gif"));
       dateInfoFrame.setVisible(true);
       JPanel datePane = new JPanel();
       JPanel dateKeyPane = new JPanel();
       datePane.setLayout(new BoxLayout(datePane,BoxLayout.Y_AXIS));
       dateKeyPane.setLayout(new BoxLayout(dateKeyPane,BoxLayout.X_AXIS));
       TreeMap dateTree = new TreeMap(dateSet);
       Set dateTreeSet = dateTree.keySet();
       Iterator dateTreeSetIterator = dateTreeSet.iterator();
       dateCount = dateSet.size();
       dateTickBoxArray = new ArrayList(dateCount);
       while (dateTreeSetIterator.hasNext())
       {
            String theDate = (String)dateTreeSetIterator.next();
            JCheckBox dateTickBox = new JCheckBox(theDate);
            dateTickBoxArray.add((Object)dateTickBox);
            if (dateTreeSetIterator.hasNext() == false)
            {
              dateTickBox.setSelected(true);  
            }
            datePane.add(dateTickBox);
        }
        JButton dateSubmitButton = new JButton("Retrieve");
        final JButton clearAllButton = new JButton("All Dates");
        dateSubmitButton.setBackground(new Color(0,255,0));
        dateSubmitButton.addActionListener(new dateActionListener());
        clearAllButton.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e) 
            {
                if (lastDataSelected == true)
                {    
                    for (int tickBoxLoop = 0; tickBoxLoop < dateTickBoxArray.size(); tickBoxLoop++)
                    {
                        JCheckBox dateItemTickBox = (JCheckBox)dateTickBoxArray.get(tickBoxLoop);
                        dateItemTickBox.setSelected(true);  
                    }
                    clearAllButton.setText("Last Date");
                    lastDataSelected = false;
                } else
                {
                    for (int tickBoxLoop = 0; tickBoxLoop < dateTickBoxArray.size() ; tickBoxLoop++)
                    {
                        JCheckBox dateItemTickBox = (JCheckBox)dateTickBoxArray.get(tickBoxLoop);
                        if (tickBoxLoop != dateTickBoxArray.size()-1)
                        {
                            dateItemTickBox.setSelected(false);  
                        } else
                        {
                            dateItemTickBox.setSelected(true);  
                        }
                    }
                    clearAllButton.setText("All Dates");
                    lastDataSelected = true;
                }
            }
         } );
        JScrollPane dateScrollPane = new JScrollPane(datePane);
        dateScrollPane.setHorizontalScrollBarPolicy(dateScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        dateScrollPane.setVerticalScrollBarPolicy(dateScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        dateInfoFrame.getContentPane().add(dateScrollPane,BorderLayout.CENTER);
        dateKeyPane.add(clearAllButton);
        dateKeyPane.add(dateSubmitButton);
        dateInfoFrame.getContentPane().add(dateKeyPane,BorderLayout.SOUTH);
        desktop.add(dateInfoFrame);
        dateInfoFrame.moveToFront();
        //dateInfoFrame.setSelected(true);

       
    }
    

    class dateActionListener  implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            dateSelectionArray = new ArrayList(dateCount);
            for (int tickBoxLoop = 0; tickBoxLoop < dateTickBoxArray.size(); tickBoxLoop++)
            {
               JCheckBox dateItemTickBox = (JCheckBox)dateTickBoxArray.get(tickBoxLoop);
               String dateItem = dateItemTickBox.getText();
               if (dateItemTickBox.isSelected() == true)
               {
                   dateSelectionArray.add((Object)dateItem);
               }
            }
            // add today's date to display new captured items
            Calendar rightNow = Calendar.getInstance();
            java.util.Date dateTime = rightNow.getTime();
            String dateToday = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK).format(dateTime);
            String formattedDate = "20" + dateToday.substring(6,8) + "-" + dateToday.substring(3,5) +  "-" +  dateToday.substring(0,2);
            dateSelectionArray.add((Object)formattedDate);
            desktop.remove(dateInfoFrame);
            desktop.updateUI();
            patientDataFetched = true;
            displayPatientData();
        }
    }
    
    public void displayPatientData() 
    {
           patientKey = persInfoPanel.getSelectedKeyValue();
           if (patientKey.length() == 0)
           {
               JOptionPane.showMessageDialog(this, "Please select a patient first.", "No Patient Selected", JOptionPane.ERROR_MESSAGE);
               return;
           }
           followUpMenuStates.setEventNumber(followUpMenuStates.patient_select_button_pressed_event);
           int stateresult = followUpMenuStates.newmenu_execute();
           followUpMenuStates.setEventNumber(followUpMenuStates.current_status_menu_button_pressed_event);
           stateresult = followUpMenuStates.newmenu_execute();
           ArrayList methodItems = followUpMenuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(120,120,120));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeFollowUpMethod(methodItem);
               }
               for (arrayLoop = 0; arrayLoop < menuArrayList.size(); arrayLoop++)
               {
                     JMenuItem tempMenuItem = (JMenuItem)menuArrayList.get(arrayLoop);
                     if (currentMenuItem == tempMenuItem)
                     {
                         currentMenuItem.setBackground(new Color(60,0,130));
                         tempMenuItem = (JMenuItem)menuArrayList.get(arrayLoop + 1);
                         if (tempMenuItem != null)
                         {
                             tempMenuItem.setBackground(new Color(0,200,0));
                         }
                         break;
                     }
                 }

           }
    }
    
    public void methodDisplayPatientCurrentStatus()
    {
        //~~~~Current Status
        String patientName = getPatientName(patientKey);
        patientLabelName.setText("<html> <h2><font color=\"olive\">Patient:-</font> <font color=\"blue\">" + patientName + "</font></h2> </html>");

        //cursorField.setText("Patient:-" + patientName + "...");
        //cursorField.setEditable(false);
        try
        {
            
            //ArrayList tmpList = dateSelectionArray;
            if (dateSelectionArray.size() > 1)
            {
                //progressBar.setIndeterminate(true);
                progressBar.setStringPainted(true);
                progressBar.setString("Retrieving Complaint Info...");
                progressBar.setValue(0);
                progressFrame.paintImmediately(new Rectangle(600,600));
                initDisplayComplaintStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                progressBar.setIndeterminate(false);
                progressBar.setString("Retrieving Systemic Info...");
                progressBar.setValue(10);
                progressFrame.paintImmediately(new Rectangle(600,600));
                initDisplaySystemicStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                progressBar.setString("Retrieving Medical History...");
                progressBar.setValue(20);
                progressFrame.paintImmediately(new Rectangle(600,600));
                initDisplayMedicalHistoryStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                progressBar.setString("Retrieving Surgical History...");
                progressBar.setValue(30);
                progressFrame.paintImmediately(new Rectangle(600,600));
                initDisplaySurgicalHistoryStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                progressBar.setString("Retrieving Family History...");
                progressBar.setValue(40);
                progressFrame.paintImmediately(new Rectangle(600,600));
                initDisplayFamilyHistoryStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                progressBar.setString("Retrieving Social History...");
                progressBar.setValue(50);
                progressFrame.paintImmediately(new Rectangle(600,600));
                initDisplaySocialHistoryStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                progressBar.setString("Retrieving Occupational Info...");
                progressBar.setValue(55);
                progressFrame.paintImmediately(new Rectangle(600,600));
                initDisplayOccupationalHistoryStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                progressBar.setString("Retrieving Treatment History...");
                progressBar.setValue(60);
                progressFrame.paintImmediately(new Rectangle(600,600));
                initDisplayTreatmentHistoryStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                progressBar.setString("Retrieving Travel History...");
                progressBar.setValue(70);
                progressFrame.paintImmediately(new Rectangle(600,600));
                initDisplayTravelHistoryStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                progressBar.setString("Retrieving Examination Info...");
                progressBar.setValue(80);
                progressFrame.paintImmediately(new Rectangle(600,600));
                initDisplayExaminationStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                progressBar.setString("Retrieving Assessment Info...");
                progressBar.setValue(90);
                progressFrame.paintImmediately(new Rectangle(600,600));
                initDisplayAssesmentStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                progressBar.setString("Retrieving Treatment Plan Info...");
                progressBar.setValue(100);
                progressFrame.paintImmediately(new Rectangle(600,600));
                initDisplayTreatmentPlanStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                desktop.updateUI();
            }
        }
        catch (Exception exc)
        {
              JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }

    }

    //follow up patient complaints
    public void captureNewComplaints() 
    {

        if (stateMachineReset == true)
        {
           executeFollowUpMethod(FOLLOWUP_CAPTURE_COMPLAINT);
        } else
        {
        followUpMenuStates.setEventNumber(followUpMenuStates.new_complaint_menu_button_pressed_event);
           int stateresult = followUpMenuStates.newmenu_execute();
           ArrayList methodItems = followUpMenuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeFollowUpMethod(methodItem);
               }
           }
        }

    } 

    public void captureFollowUpAssesment() 
    {
        if (stateMachineReset == true)
        {
           executeFollowUpMethod(FOLLOWUP_CAPTURE_FINAL_DIAGNOSES);
        } else
        {
           followUpMenuStates.setEventNumber(followUpMenuStates.final_diagnoses_menu_button_pressed_event);
           int stateresult = followUpMenuStates.newmenu_execute();
           ArrayList methodItems = followUpMenuStates.getMethodItems();
           if (methodItems.size() != 0)
           {
               currentMenuItem = clickedMenuItem;
               currentMenuItem.setBackground(new Color(255,70,70));
               for (int loop = 0; loop < methodItems.size(); loop++)
               {
                   int methodItem = Integer.parseInt((String)methodItems.get(loop));
                   executeFollowUpMethod(methodItem);
               }
           }
        }

    } 

   public void displayLicenseAgreement()
   {
       try {
           agreementInternalFrame = new JInternalFrame("Mother Teres@ License Agreement",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
           agreementInternalFrame.setSize(new Dimension(FRAME_WIDTH - 350, FRAME_HEIGHT + 100));
           agreementInternalFrame.setLocation(FRAME_X,FRAME_Y);
           agreementInternalFrame.setFrameIcon(new ImageIcon(imagePath + "examination_sm.gif"));
           agreementInternalFrame.setVisible(true);
           JPanel agreementPanel = new JPanel();
           JEditorPane agreementPane = new JEditorPane();
           File agreementFile = new File(AGREEMENT_PATH);
           InputStream agreementInputStream = new FileInputStream(agreementFile);
           Document agreementDoc = new DefaultStyledDocument();
           agreementPane.setEditorKit(new RTFEditorKit());
           
           agreementPane.read(agreementInputStream,agreementPane);
           agreementPane.setEditable(false);
           agreementInternalFrame.getContentPane().add(agreementPane,BorderLayout.CENTER);
           desktop.add(agreementInternalFrame);
           agreementInternalFrame.moveToFront();
           agreementInternalFrame.setSelected(true);
           desktop.updateUI();
       }
       catch (Exception exc) {
           JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
       }
   }
    
    
    
    public void executeMethod(int inMethodItem)
    {
        this.setCursor(waitCursor);
        switch (inMethodItem)
        {
            case 0:
                //do nothing;
                break;
            case CAPTURE_BASIC:
                buildCaptureBasic();
                break;
            case DISPLAY_BASIC:
                desktop.remove(newPatientFrame);
                desktop.updateUI();
                try
                {
                    patientName = getPatientName(patientKey);
                    staticInfoFrame = new JInternalFrame("Medical information for: " + patientName,
                    true, //resizable
                    false, //closable
                    true, //maximizable
                    true); //iconifiable
                    staticInfoFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
                    staticInfoFrame.setLocation(FRAME_X,FRAME_Y);
                    staticInfoFrame.setFrameIcon(new ImageIcon(imagePath + "examination_sm.gif"));
                    staticInfoFrame.setVisible(true);
                    initDisplayBasicStaticInfoMVC();
                    staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                    objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                    desktop.add(staticInfoFrame);
                    //cursorField.setText("Active Patient: " + patientName);
                    patientLabelName.setText("<html> <h2><font color=\"olive\">Patient:-</font> <font color=\"blue\">" + patientName + "</font></h2> </html>");
                    cursorField.setEditable(false);
                    staticInfoFrame.moveToFront();
                    staticInfoFrame.setSelected(true);
                    //SwingUtilities.getRoot(desktop).repaint();
                    desktop.updateUI();
                }
                catch (Exception exc)
                {
                    JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
                }

                break;
            case CAPTURE_COMPLAINT:
                captureComplaintDetails();
                break;
            case DISPLAY_COMPLAINT:
                try
                {
                    initDisplayComplaintStaticInfoMVC();
                    objectContainer.remove((Object)"staticInfoFrame");
                    staticInfoFrame.getContentPane().removeAll();
                    staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                    objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                    //SwingUtilities.getRoot(staticInfoFrame).repaint();
                    desktop.updateUI();
                }
                catch (Exception exc)
                {
                    JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case CAPTURE_SYSTEMIC:
                captureSystemicDetails();
                break;
            case DISPLAY_SYSTEMIC:
                initDisplaySystemicStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                //SwingUtilities.getRoot(desktop).repaint();
                desktop.updateUI();
                break;
            case CAPTURE_MEDICAL_HISTORY:
                capturePreviousMedicalHistoryDetails();
                break;
            case DISPLAY_MEDICAL_HISTORY:
                try
                {
                    initDisplayMedicalHistoryStaticInfoMVC();
                    objectContainer.remove((Object)"staticInfoFrame");
                    staticInfoFrame.getContentPane().removeAll();
                    staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                    objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                    //SwingUtilities.getRoot(desktop).repaint();
                    desktop.updateUI();
                }
                catch (Exception exc)
                {
                    JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case CAPTURE_SURGICAL_HISTORY:
                capturePreviousSurgicalHistoryDetails();
                break;
            case DISPLAY_SURGICAL_HISTORY:
                try
                {
                    initDisplaySurgicalHistoryStaticInfoMVC();
                    objectContainer.remove((Object)"staticInfoFrame");
                    staticInfoFrame.getContentPane().removeAll();
                    staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                    objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                    //SwingUtilities.getRoot(desktop).repaint();
                    desktop.updateUI();
                }
                catch (Exception exc)
                {
                    JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case CAPTURE_FAMILY_HISTORY:
                captureFamilyHistoryDetails();
                break;
            case DISPLAY_FAMILY_HISTORY:
                initDisplayFamilyHistoryStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                //SwingUtilities.getRoot(desktop).repaint();
                desktop.updateUI();
                break;
            case CAPTURE_SOCIAL_HISTORY:
                captureSocialHistoryDetails();
                break;
            case DISPLAY_SOCIAL_HISTORY:
                initDisplaySocialHistoryStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                //SwingUtilities.getRoot(desktop).repaint();
                desktop.updateUI();
                break;
            case CAPTURE_OCCUPATIONAL_HISTORY:
                captureOccupationalHistoryDetails();
                break;
            case DISPLAY_OCCUPATIONAL_HISTORY:
                initDisplayOccupationalHistoryStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                //SwingUtilities.getRoot(desktop).repaint();
                desktop.updateUI();
                break;
            case CAPTURE_TREATMENT:
                captureTreatmentHistoryDetails();
                break;
            case DISPLAY_TREATMENT:
                initDisplayTreatmentHistoryStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                //SwingUtilities.getRoot(desktop).repaint();
                desktop.updateUI();
                break;
            case CAPTURE_ALLERGIES:
                captureAllergyHistoryDetails();
                break;
            case DISPLAY_ALLERGIES:
                initDisplayAllergyHistoryStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                //SwingUtilities.getRoot(desktop).repaint();
                desktop.updateUI();
                break;
            case CAPTURE_TRAVEL_HISTORY:
                captureTravelHistoryDetails();
                break;
            case DISPLAY_TRAVEL_HISTORY:
                initDisplayTravelHistoryStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                //SwingUtilities.getRoot(desktop).repaint();
                desktop.updateUI();
                break;
            case CAPTURE_EXAMINATION:
                captureList = null;
                captureExaminationDetails();
                break;
            case DISPLAY_EXAMINATION:
                if (examinationDone == true)
                {
                    initDisplayExaminationStaticInfoMVC();
                }
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                //SwingUtilities.getRoot(desktop).repaint();
                desktop.updateUI();
                break;
            case CAPTURE_ASSESMENT:
                captureAssesmentDetails();
                break;
            case DISPLAY_ASSESMENT:
                initDisplayAssesmentStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                //SwingUtilities.getRoot(desktop).repaint();
                desktop.updateUI();
                break;
            case CAPTURE_PLAN:
                captureTreatmentPlanDetails();
                break;
            case DISPLAY_PLAN:
                initDisplayTreatmentPlanStaticInfoMVC();
                objectContainer.remove((Object)"staticInfoFrame");
                staticInfoFrame.getContentPane().removeAll();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                //SwingUtilities.getRoot(desktop).repaint();
                desktop.updateUI();
                break;
            default:
                break;
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

//    public static  final int  FOLLOWUP_DISPLAY_BASIC = 1;
//    public static  final int  FOLLOWUP_CURRENT_STATUS = 2;
//    public static  final int  FOLLOWUP_CAPTURE_COMPLAINT = 3;
//    public static  final int  FOLLOWUP_DISPLAY_COMPLAINT = 4;
//    public static  final int  FOLLOWUP_CAPTURE_EXAMINATION = 5;
//    public static  final int  FOLLOWUP_DISPLAY_EXAMINATION = 6;
//    public static  final int  FOLLOWUP_CAPTURE_FINAL_DIAGNOSES = 7;
//    public static  final int  FOLLOWUP_DISPLAY_FINAL_DIAGNOSES = 8;
//    public static  final int  FOLLOWUP_CAPTURE_PLAN = 9;
//    public static  final int  FOLLOWUP_DISPLAY_PLAN = 10;
    
    
    public void executeFollowUpMethod(int inMethodItem)
    {
        this.setCursor(waitCursor);
        switch (inMethodItem)
        {
            case 0:
                //do nothing;
                break;
            case FOLLOWUP_CURRENT_STATUS:
                try
                {
                    progressFrame = new JInternalFrame("Retrieving Patient Data....",
                    false, //resizable
                    false, //closable
                    false, //maximizable
                    false); //iconifiable
                    progressFrame.setSize(new Dimension(400, 100));
                    progressFrame.setLocation(100,100);
                    progressFrame.setVisible(true);
                    progressBar = new JProgressBar(0,100);
                    progressBar.setStringPainted(true);
                    JPanel progressPanel = new JPanel();
                    progressPanel.setLayout(new BorderLayout());
                    JLabel progressHeadingLabel = new JLabel("<html><font color=\"blue\">Retrieving Patient Info....</font></html>");
                    progressPanel.add(progressHeadingLabel,BorderLayout.NORTH);
                    progressPanel.add(progressBar,BorderLayout.CENTER);
                    JScrollPane scrollPane = new JScrollPane(progressPanel);
                    progressFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
                    desktop.add(progressFrame);
                    progressFrame.setVisible(true);
                    progressFrame.moveToFront();
                    progressFrame.setSelected(true);
                    //desktop.repaint();
                    desktop.updateUI();
                    desktop.paintImmediately(new Rectangle(600,600));
                    methodDisplayPatientCurrentStatus();
                    desktop.remove(progressFrame);
                    
               }
                catch (Exception exc)
                {
                    JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case FOLLOWUP_CAPTURE_COMPLAINT:
                captureFollowUpComplaintDetails();
                break;
            case FOLLOWUP_DISPLAY_COMPLAINT:
                try
                {
                    initUpdateDisplayComplaintStaticInfoMVC();
                    objectContainer.remove((Object)"staticInfoFrame");
                    staticInfoFrame.getContentPane().removeAll();
                    staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                    objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                    //SwingUtilities.getRoot(desktop).repaint();
                    desktop.updateUI();
                }
                catch (Exception exc)
                {
                    JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case FOLLOWUP_CAPTURE_EXAMINATION:
                captureList = null;
                captureFollowUpExaminationDetails();
                break;
            case FOLLOWUP_DISPLAY_EXAMINATION:
                try
                {
                   if (examinationDone == true)
                   {
                      initUpdateDisplayExaminationStaticInfoMVC();
                   }
                    objectContainer.remove((Object)"staticInfoFrame");
                    staticInfoFrame.getContentPane().removeAll();
                    staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                    objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                    //SwingUtilities.getRoot(desktop).repaint();
                    desktop.updateUI();
                }
                catch (Exception exc)
                {
                    JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case FOLLOWUP_CAPTURE_FINAL_DIAGNOSES:
                captureFollowUpAssesmentDetails();
                //captureFollowUpComplaintDetails();
                break;
            case FOLLOWUP_DISPLAY_FINAL_DIAGNOSES:
                try
                {
                    initUpdateDisplayAssesmentStaticInfoMVC();
                    objectContainer.remove((Object)"staticInfoFrame");
                    staticInfoFrame.getContentPane().removeAll();
                    staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                    objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                    //SwingUtilities.getRoot(desktop).repaint();
                    desktop.updateUI();
                }
                catch (Exception exc)
                {
                    JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case FOLLOWUP_CAPTURE_PLAN:
                captureFollowUpTreatmentPlanDetails();
                break;
            case FOLLOWUP_DISPLAY_PLAN:
                try
                {
                    initUpdateDisplayTreatmentPlanStaticInfoMVC();
                    objectContainer.remove((Object)"staticInfoFrame");
                    staticInfoFrame.getContentPane().removeAll();
                    staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                    objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                    //SwingUtilities.getRoot(desktop).repaint();
                    desktop.updateUI();
                }
                catch (Exception exc)
                {
                    JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
                }
                break;
            default:
                break;
        }
        this.setCursor(Cursor.getDefaultCursor());
        
    }
    
    
    public void skipMenuItem(JButton skipButton, String skipNumber)
    {
        int firstItem = 0;
        int secondItem = 0;
        //skip number consists of 2 state numbers which must be split first
        StringTokenizer numberTokens = new StringTokenizer(skipNumber,",");	
        firstItem = Integer.parseInt((String)numberTokens.nextToken());
        secondItem = Integer.parseInt((String)numberTokens.nextToken());
        menuStates.setEventNumber(firstItem);
        int stateresult = menuStates.menu_execute();
        menuStates.setEventNumber(secondItem);
        stateresult = menuStates.menu_execute();
        skipButton.setEnabled(false);

        JMenuItem skipAssociatedMenuItem = (JMenuItem)skipButtonMap.get((Object)skipButton);
        
        ArrayList methodItems = menuStates.getMethodItems();
        if (methodItems.size() != 0) {
            for (arrayLoop = 0; arrayLoop < menuArrayList.size(); arrayLoop++) {
                JMenuItem tempMenuItem = (JMenuItem)menuArrayList.get(arrayLoop);
                if (skipAssociatedMenuItem == tempMenuItem) {
                    tempMenuItem.setBackground(new Color(60,0,130));
                    //tempMenuItem.setVisible(false);
                    
                    currentMenuItem = (JMenuItem)menuArrayList.get(arrayLoop + 1);
                    
                    if (currentMenuItem != null) {
                        currentMenuItem.setBackground(new Color(0,200,0));
                    }
                    break;
                }
            }
           
            //removed this to speed up skip menu items. looks ok still.
            /*
            for (int loop = 0; loop < methodItems.size(); loop++) {
                int methodItem = Integer.parseInt((String)methodItems.get(loop));
                executeMethod(methodItem);
            }
            */
            Set keySet = skipButtonMap.keySet();
            Iterator iter = keySet.iterator();
            while (iter.hasNext())
            {
               JButton theKey = (JButton)iter.next();
               JMenuItem theMenuItem = (JMenuItem)skipButtonMap.get((Object)theKey);
               if (currentMenuItem == theMenuItem)
               {
                   theKey.setEnabled(true);
                   break;
               }
            }

        }
        
    }
    
   public void actionPerformed(ActionEvent event)
    { 
        String methodNumber = null;
        String methodName = null;
        String skipButtonNumber = null;
        String systemicMethodName = null;
        String familyHistoryMethodName = null;
        String occupationHistoryMethodName = null;
        String examinationMethodName = null;
        String entMethodName = null;
        JMenuItem tempMenuItem = null;
        boolean DiagFrame = false;
        JInternalFrame tempObjectFrame = null;
        Class aClass = app.getClass();
        //Skipbutton check code
        if (skipButtonList != null)
        {
            Set skipButtonKeySet = skipButtonList.keySet();
            Iterator skipButtonIterator = skipButtonKeySet.iterator();
            while(skipButtonIterator.hasNext()) 
            {
                JButton theSkipButtonItem  = (JButton)skipButtonIterator.next();
                skipButtonNumber = (String)skipButtonList.get(theSkipButtonItem);
                if (event.getSource() == theSkipButtonItem) 
                {
                    skipMenuItem(theSkipButtonItem, skipButtonNumber);
                    //theSkipButtonItem.setVisible(false);
                   break;
                }
            }
        }
        //menubutton pressed find code
        try
        {
            Set methodKeySet = methodTable.keySet();
            Iterator methodIterator = methodKeySet.iterator();
            while(methodIterator.hasNext())
            {
                theMenuItem  = (JMenuItem)methodIterator.next();
                methodName = (String)methodTable.get(theMenuItem);
                if (event.getSource() == theMenuItem)
                {
                    Object parameters[] = null;
                    Method method = aClass.getMethod(methodName, null);
                    Object arguments[] = null;
                    clickedMenuItem = theMenuItem;
                    //find the associated skip button and disable if found
                    Set keySet = skipButtonMap.keySet();
                    Iterator iter = keySet.iterator();
                    while (iter.hasNext()) {
                        JButton theKey = (JButton)iter.next();
                        JMenuItem listMenuItem = (JMenuItem)skipButtonMap.get((Object)theKey);
                        if (theMenuItem == listMenuItem) {
                            theKey.setEnabled(false);
                            //theKey.setVisible(false);
                            //clickedMenuItem.setVisible(false);
                            break;
                        }
                    }
                    method.invoke(this, arguments);
                    break;
                }
            
            }
        }
        catch (NoSuchMethodException nsme)
        {
           JOptionPane.showMessageDialog(desktop, nsme.getMessage(), "Error: No Such Method ", JOptionPane.ERROR_MESSAGE);
        }
        catch (IllegalAccessException iae)
        {
           JOptionPane.showMessageDialog(desktop, iae.getMessage(), "Error: Illegal Access", JOptionPane.ERROR_MESSAGE);
        }
        catch (InvocationTargetException ite)
        {
           JOptionPane.showMessageDialog(desktop, ite.getTargetException().getMessage(), "Error: Invocation Target Exception", JOptionPane.ERROR_MESSAGE);
        }
        try
        {
            Set systemicMethodKeySet = systemicMethodTable.keySet();
            Iterator systemicMethodIterator = systemicMethodKeySet.iterator();
            while(systemicMethodIterator.hasNext())
            {
                systemicMenuItem  = (JMenuItem)systemicMethodIterator.next();
                systemicMethodName = (String)systemicMethodTable.get(systemicMenuItem);
                if (event.getSource() == systemicMenuItem)
                {
                    if (previousSystMenuItem != null)
                    {
                       previousSystMenuItem.setBackground(new Color(120,120,200));
                       previousSystMenuItem = systemicMenuItem;  
                    } else
                    {
                       previousSystMenuItem = systemicMenuItem;  
                    }
                    systemicMenuItem.setBackground(new Color(255,70,70));
                    Object parameters[] = null;
                    Method method = aClass.getMethod(systemicMethodName, null);
                    Object arguments[] = null;
                    systemicClickedMenuItem = systemicMenuItem;
                    method.invoke(this, arguments);
                    break;
                }
            
            }
        }
        catch (NoSuchMethodException nsme)
        {
           JOptionPane.showMessageDialog(desktop, nsme.getMessage(), "Error: No Such Method ", JOptionPane.ERROR_MESSAGE);
        }
        catch (IllegalAccessException iae)
        {
           JOptionPane.showMessageDialog(desktop, iae.getMessage(), "Error: Illegal Access", JOptionPane.ERROR_MESSAGE);
        }
        catch (InvocationTargetException ite)
        {
           JOptionPane.showMessageDialog(desktop, ite.getTargetException().getMessage(), "Error: Invocation Target Exception", JOptionPane.ERROR_MESSAGE);
        }
        try
        {
            Set familyHistoryMethodKeySet = familyHistoryMethodTable.keySet();
            Iterator familyHistoryMethodIterator = familyHistoryMethodKeySet.iterator();
            while(familyHistoryMethodIterator.hasNext())
            {
                familyHistoryMenuItem  = (JMenuItem)familyHistoryMethodIterator.next();
                familyHistoryMethodName = (String)familyHistoryMethodTable.get(familyHistoryMenuItem);
                if (event.getSource() == familyHistoryMenuItem)
                {
                    if (previousFamilyMenuItem != null)
                    {
                       previousFamilyMenuItem.setBackground(new Color(120,120,200));
                       previousFamilyMenuItem = familyHistoryMenuItem;  
                    } else
                    {
                       previousFamilyMenuItem = familyHistoryMenuItem;  
                    }
                    familyHistoryMenuItem.setBackground(new Color(255,70,70));
                    Object parameters[] = null;
                    Method method = aClass.getMethod(familyHistoryMethodName, null);
                    Object arguments[] = null;
                    familyHistoryClickedMenuItem = familyHistoryMenuItem;
                    method.invoke(this, arguments);
                    break;
                }
            
            }
        }
        catch (NoSuchMethodException nsme)
        {
           JOptionPane.showMessageDialog(desktop, nsme.getMessage(), "Error: No Such Method ", JOptionPane.ERROR_MESSAGE);
        }
        catch (IllegalAccessException iae)
        {
           JOptionPane.showMessageDialog(desktop, iae.getMessage(), "Error: Illegal Access", JOptionPane.ERROR_MESSAGE);
        }
        catch (InvocationTargetException ite)
        {
           JOptionPane.showMessageDialog(desktop, ite.getTargetException().getMessage(), "Error: Invocation Target Exception", JOptionPane.ERROR_MESSAGE);
        }

        try
        {
            Set examENTHistoryMethodKeySet = entMethodTable.keySet();
            Iterator examENTHistoryMethodIterator = examENTHistoryMethodKeySet.iterator();
            while(examENTHistoryMethodIterator.hasNext())
            {
                entMenuItem  = (JMenuItem)examENTHistoryMethodIterator.next();
                entMethodName = (String)entMethodTable.get(entMenuItem);
                if (event.getSource() == entMenuItem)
                {
                    if (subExamMenuItem != null)
                    {
                       subExamMenuItem.setBackground(new Color(120,120,200));
                       subExamMenuItem = entMenuItem;  
                    } else
                    {
                       subExamMenuItem = entMenuItem;  
                    }
                    entMenuItem.setBackground(new Color(255,70,70));
                    Object parameters[] = null;
                    Method method = aClass.getMethod(entMethodName, null);
                    Object arguments[] = null;
                    //familyHistoryClickedMenuItem = familyHistoryMenuItem;
                    method.invoke(this, arguments);
                    break;
                }
            
            }
        }
        catch (NoSuchMethodException nsme)
        {
           JOptionPane.showMessageDialog(desktop, nsme.getMessage(), "Error: No Such Method ", JOptionPane.ERROR_MESSAGE);
        }
        catch (IllegalAccessException iae)
        {
           JOptionPane.showMessageDialog(desktop, iae.getMessage(), "Error: Illegal Access", JOptionPane.ERROR_MESSAGE);
        }
        catch (InvocationTargetException ite)
        {
           JOptionPane.showMessageDialog(desktop, ite.getTargetException().getMessage(), "Error: Invocation Target Exception", JOptionPane.ERROR_MESSAGE);
        }
        
        try
        {
            Set occupationHistoryMethodKeySet = occupationHistoryMethodTable.keySet();
            Iterator occupationHistoryMethodIterator = occupationHistoryMethodKeySet.iterator();
            while(occupationHistoryMethodIterator.hasNext())
            {
                occupationHistoryMenuItem  = (JMenuItem)occupationHistoryMethodIterator.next();
                occupationHistoryMethodName = (String)occupationHistoryMethodTable.get(occupationHistoryMenuItem);
                if (event.getSource() == occupationHistoryMenuItem)
                {
                    if (previousOccupationHistMenuItem != null)
                    {
                       previousOccupationHistMenuItem.setBackground(new Color(120,120,200));
                       previousOccupationHistMenuItem = occupationHistoryMenuItem;  
                    } else
                    {
                       previousOccupationHistMenuItem = occupationHistoryMenuItem;  
                    }
                    occupationHistoryMenuItem.setBackground(new Color(255,70,70));
                    Object parameters[] = null;
                    Method method = aClass.getMethod(occupationHistoryMethodName, null);
                    Object arguments[] = null;
                    occupationHistoryClickedMenuItem = occupationHistoryMenuItem;
                    method.invoke(this, arguments);
                    break;
                }
            
            }
        }
        catch (NoSuchMethodException nsme)
        {
           JOptionPane.showMessageDialog(desktop, nsme.getMessage(), "Error: No Such Method ", JOptionPane.ERROR_MESSAGE);
        }
        catch (IllegalAccessException iae)
        {
           JOptionPane.showMessageDialog(desktop, iae.getMessage(), "Error: Illegal Access", JOptionPane.ERROR_MESSAGE);
        }
        catch (InvocationTargetException ite)
        {
           JOptionPane.showMessageDialog(desktop, ite.getTargetException().getMessage(), "Error: Invocation Target Exception", JOptionPane.ERROR_MESSAGE);
        }
        try
        {
            Set examinationMethodKeySet = examinationMethodTable.keySet();
            Iterator examinationMethodIterator = examinationMethodKeySet.iterator();
            while(examinationMethodIterator.hasNext())
            {
                examinationMenuItem  = (JMenuItem)examinationMethodIterator.next();
                examinationMethodName = (String)examinationMethodTable.get(examinationMenuItem);
                if (event.getSource() == examinationMenuItem)
                {
                    if (previousExamMenuItem != null)
                    {
                       previousExamMenuItem.setBackground(new Color(120,120,200));
                       previousExamMenuItem = examinationMenuItem;  
                    } else
                    {
                       previousExamMenuItem = examinationMenuItem;  
                    }
                    examinationMenuItem.setBackground(new Color(255,70,70));
                    Object parameters[] = null;
                    Method method = aClass.getMethod(examinationMethodName, null);
                    Object arguments[] = null;
                    examinationClickedMenuItem = examinationMenuItem;
                    method.invoke(this, arguments);
                    break;
                }
            
            }
        }
        catch (NoSuchMethodException nsme)
        {
           JOptionPane.showMessageDialog(desktop, nsme.getMessage(), "Error: No Such Method ", JOptionPane.ERROR_MESSAGE);
        }
        catch (IllegalAccessException iae)
        {
           JOptionPane.showMessageDialog(desktop, iae.getMessage(), "Error: Illegal Access", JOptionPane.ERROR_MESSAGE);
        }
        catch (InvocationTargetException ite)
        {
           JOptionPane.showMessageDialog(desktop, ite.getTargetException().getMessage(), "Error: Invocation Target Exception", JOptionPane.ERROR_MESSAGE);
        }
        try
        {
            Set followUpExaminationMethodKeySet = followUpExaminationMethodTable.keySet();
            Iterator followUpExaminationMethodIterator = followUpExaminationMethodKeySet.iterator();
            while(followUpExaminationMethodIterator.hasNext())
            {
                followUpExaminationMenuItem  = (JMenuItem)followUpExaminationMethodIterator.next();
                examinationMethodName = (String)followUpExaminationMethodTable.get(followUpExaminationMenuItem);
                if (event.getSource() == followUpExaminationMenuItem)
                {
                    if (previousExamMenuItem != null)
                    {
                       previousExamMenuItem.setBackground(new Color(120,120,200));
                       previousExamMenuItem = followUpExaminationMenuItem;  
                    } else
                    {
                       previousExamMenuItem = followUpExaminationMenuItem;  
                    }
                    followUpExaminationMenuItem.setBackground(new Color(255,70,70));
                    Object parameters[] = null;
                    Method method = aClass.getMethod(examinationMethodName, null);
                    Object arguments[] = null;
                    followUpExaminationClickedMenuItem = followUpExaminationMenuItem;
                    method.invoke(this, arguments);
                    break;
                }
            
            }
        }
        catch (NoSuchMethodException nsme)
        {
           JOptionPane.showMessageDialog(desktop, nsme.getMessage(), "Error: No Such Method ", JOptionPane.ERROR_MESSAGE);
        }
        catch (IllegalAccessException iae)
        {
           JOptionPane.showMessageDialog(desktop, iae.getMessage(), "Error: Illegal Access", JOptionPane.ERROR_MESSAGE);
        }
        catch (InvocationTargetException ite)
        {
           JOptionPane.showMessageDialog(desktop, ite.getTargetException().getMessage(), "Error: Invocation Target Exception", JOptionPane.ERROR_MESSAGE);
        }
        if (event.getSource() == diagExitButton)
        {
             JInternalFrame staticFrame = null;
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"DiseaseDiagFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 objectContainer.remove((Object)"DiseaseDiagFrame");
                 desktop.updateUI();
             }
        }     
        if (event.getSource() == rifeExitButton)
        {
             JInternalFrame staticFrame = null;
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"RifeGeneratorFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 objectContainer.remove((Object)"RifeGeneratorFrame");
                 desktop.updateUI();
             }
        }     
        if (event.getSource() == updateExitButton)
        {
             JInternalFrame staticFrame = null;
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"newPatientComplaintFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 staticFrame = null;
                 objectContainer.remove((Object)"newPatientComplaintFrame");
                 newComplaintPanel = null;
                 desktop.updateUI();
                 displayMethodNumber = FOLLOWUP_DISPLAY_COMPLAINT;
                 followUpMenuSelected = true;
                 
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"followUpPatientAssesmentFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 staticFrame = null;
                 objectContainer.remove((Object)"followUpPatientAssesmentFrame");
                 desktop.updateUI();
                 displayMethodNumber = FOLLOWUP_DISPLAY_FINAL_DIAGNOSES;
                 followUpMenuSelected = true;
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"systemicInternalFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 staticFrame = null;
                 objectContainer.remove((Object)"systemicInternalFrame");
                 removeSystemicPanels();
                 desktop.updateUI();
                 displayMethodNumber = DISPLAY_SYSTEMIC;
                 followUpMenuSelected = false;
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"newPreviousMedicalHistoryFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 staticFrame = null;
                 objectContainer.remove((Object)"newPreviousMedicalHistoryFrame");
                 desktop.updateUI();
                 displayMethodNumber = DISPLAY_MEDICAL_HISTORY;
                 followUpMenuSelected = false;
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"newPreviousSurgicalHistoryFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 staticFrame = null;
                 objectContainer.remove((Object)"newPreviousSurgicalHistoryFrame");
                 desktop.updateUI();
                 displayMethodNumber = DISPLAY_SURGICAL_HISTORY;
                 followUpMenuSelected = false;
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"familyHistoryInternalFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 staticFrame = null;
                 objectContainer.remove((Object)"familyHistoryInternalFrame");
                 removeFamilyHistoryPanels();
                 displayMethodNumber = DISPLAY_FAMILY_HISTORY;
                 followUpMenuSelected = false;
                 desktop.updateUI();
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"socialHistoryInternalFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 staticFrame = null;
                 objectContainer.remove((Object)"socialHistoryInternalFrame");
                 desktop.updateUI();
                 displayMethodNumber = DISPLAY_SOCIAL_HISTORY;
                 followUpMenuSelected = false;
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"occupationHistoryInternalFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 staticFrame = null;
                 objectContainer.remove((Object)"occupationHistoryInternalFrame");
                 removeOccupationHistoryPanels();
                 desktop.updateUI();
                 displayMethodNumber = DISPLAY_OCCUPATIONAL_HISTORY;
                 followUpMenuSelected = false;
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"treatmentHistoryInternalFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 staticFrame = null;
                 objectContainer.remove((Object)"treatmentHistoryInternalFrame");
                 displayMethodNumber = DISPLAY_TREATMENT;
                 followUpMenuSelected = false;
                 desktop.updateUI();
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"allergyHistoryInternalFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 staticFrame = null;
                 objectContainer.remove((Object)"allergyHistoryInternalFrame");
                 desktop.updateUI();
                 allergyHistoryInternalFrame = null;
                 displayMethodNumber = DISPLAY_ALLERGIES;
                 followUpMenuSelected = false;
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"travelHistoryInternalFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 staticFrame = null;
                 objectContainer.remove((Object)"travelHistoryInternalFrame");
                 travelHistoryInternalFrame = null;
                 displayMethodNumber = DISPLAY_TRAVEL_HISTORY;
                 followUpMenuSelected = false;
                 desktop.updateUI();
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"examinationInternalFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 displayMethodNumber = DISPLAY_EXAMINATION;
                 followUpMenuSelected = false;
                 if (captureList.size() > 0)
                 {
                    examinationDone = true;
                 } else
                 {
                    examinationDone = false;
                 }
                 if (examinationDone == true)
                 {
                     removeExaminationPanels();
                 }
                 objectContainer.remove((Object)"examinationInternalFrame");
                 examinationInternalFrame = null;
                 examinationPanel = null;
                 desktop.updateUI();
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"followUpExaminationInternalFrame")) != null)
             {
                 displayMethodNumber = FOLLOWUP_DISPLAY_EXAMINATION;
                 followUpMenuSelected = true;
                 desktop.remove(staticFrame);
                 if (captureList.size() > 0)
                 {
                    examinationDone = true;
                 } else
                 {
                    examinationDone = false;
                 }
                 if (examinationDone == true)
                 {
                     removeExaminationPanels();
                 }
                 objectContainer.remove((Object)"followUpExaminationInternalFrame");
                 desktop.updateUI();
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"newAssesmentInternalFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 staticFrame = null;
                 objectContainer.remove((Object)"newAssesmentInternalFrame");
                 desktop.updateUI();
                 displayMethodNumber = DISPLAY_ASSESMENT;
                 followUpMenuSelected = false;
             } else
             if ((staticFrame = (JInternalFrame)objectContainer.get((Object)"treatmentPlanInternalFrame")) != null)
             {
                 desktop.remove(staticFrame);
                 staticFrame = null;
                 objectContainer.remove((Object)"treatmentPlanInternalFrame");
                 desktop.updateUI();
                 if (stateMachineReset == false)
                 {
                    resetStateMachineAndMenus();
                 }
                 stateMachineReset = true;
                 displayMethodNumber = FOLLOWUP_DISPLAY_PLAN;
                 followUpMenuSelected = true;
             }
                 
             if (stateMachineReset == false)
             {
                 
             menuStates.setEventNumber(updateButtonEvent);
             int stateresult = menuStates.menu_execute();
             ArrayList methodItems = menuStates.getMethodItems();
             if (methodItems.size() != 0)
             {
                 for (arrayLoop = 0; arrayLoop < menuArrayList.size(); arrayLoop++)
                 {
                     tempMenuItem = (JMenuItem)menuArrayList.get(arrayLoop);
                     if (currentMenuItem == tempMenuItem)
                     {
                         currentMenuItem.setBackground(new Color(60,0,130));
                         tempMenuItem = (JMenuItem)menuArrayList.get(arrayLoop + 1);
                         if (tempMenuItem != null)
                         {
                             tempMenuItem.setBackground(new Color(0,200,0));
                         }
                         break;
                     }
                 }
                     for (int loop = 0; loop < methodItems.size(); loop++)
                  {
                      int methodItem = Integer.parseInt((String)methodItems.get(loop));
                      executeMethod(methodItem);
                  }
                 Set keySet = skipButtonMap.keySet();
                 Iterator iter = keySet.iterator();
                 while (iter.hasNext())
                 {
                    JButton theKey = (JButton)iter.next();
                    JMenuItem theMenuItem = (JMenuItem)skipButtonMap.get((Object)theKey);
                    if (tempMenuItem == theMenuItem)
                    {
                       theKey.setEnabled(true);
                       break;
                    }
                 }

             } else
             {
                 followUpMenuStates.setEventNumber(updateButtonEvent);
                 stateresult = followUpMenuStates.newmenu_execute();
                 methodItems = followUpMenuStates.getMethodItems();
                 if (methodItems.size() != 0)
                 {
                     for (arrayLoop = 0; arrayLoop < menuArrayList.size(); arrayLoop++)
                     {
                         tempMenuItem = (JMenuItem)menuArrayList.get(arrayLoop);
                         if (currentMenuItem == tempMenuItem)
                         {
                             currentMenuItem.setBackground(new Color(60,0,130));
                             tempMenuItem = (JMenuItem)menuArrayList.get(arrayLoop + 1);
                             if (tempMenuItem != null)
                             {
                                 tempMenuItem.setBackground(new Color(0,200,0));
                             }
                             break;
                         }
                     }
                     for (int loop = 0; loop < methodItems.size(); loop++)
                     {
                         int methodItem = Integer.parseInt((String)methodItems.get(loop));
                         executeFollowUpMethod(methodItem);
                     }
                 }
                 
             }
             } else
             {
                 if (followUpMenuSelected == true)
                 {
                    executeFollowUpMethod(displayMethodNumber);
                 } else
                 {
                    executeMethod(displayMethodNumber);
                 }
             }
        } else     
        if (event.getSource() == openNoteFile)
        {
            //test stub for fast test.
            captureTravelHistoryDetails();
            //getNoteFile();
        } else
        if (event.getSource() == languageTable)
        {
            initLangMVC();
            //pack();
        } else
        if (event.getSource() == allergyTypeTable)
        {
            initAllergyReactionTypeMVC();
        } else
        if (event.getSource() == appointmentStateTable)
        {
            initAppointmentStateTypeMVC();
        } else
        if (event.getSource() == bodyLocationTypeTable)
        {
            initBodyLocationTypeMVC();
        }  else
        if (event.getSource() == anatomicalLocationTable)
        {
            initAnatomicalLocationMVC();
        } else
        if (event.getSource() == diseaseTable)
        {
            initDiseaseMaintMVC();
        } else
        if (event.getSource() == surgeryTable)
        {
            initSurgeryMaintMVC();
        } else
        if (event.getSource() == physicianTable)
        {
            initPhysicianMaintMVC();
        } else
        if (event.getSource() == symptomTable)
        {
            initSymptomMaintMVC();
        } else
        if (event.getSource() == institutionTypeTable)
        {
            initInstitutionTypeMaintMVC();
        } else
        if (event.getSource() == testTable)
        {
            initTestMaintMVC();
        } else
        if (event.getSource() == dosageTable)
        {
            initDosageUnitTypeMVC();
        } else
        if (event.getSource() == employerTable)
        {
            initEmployerTypeMVC();
        } else
        if (event.getSource() == titleTable)
        {
            initTitleTypeMVC();
        } else
        if (event.getSource() == conditionTable)
        {
            initConditionTypeMVC();
        } else
        if (event.getSource() == displayCondition)
        {
            initDisplayConditionInfoMVC();
        } else
        if (event.getSource() == appetiteTable)
        {
            initAppetiteTypeMVC();
        } else
        if (event.getSource() == toleranceTable)
        {
            initToleranceTypeMVC();
        } else
        if (event.getSource() == categoryTable)
        {
            initCategoryTypeMVC();
        } else
        if (event.getSource() == foodSourceTable)
        {
            initFoodSourceTypeMVC();
        } else
        if (event.getSource() == bevarageSourceTable)
        {
            initBevarageSourceTypeMVC();
        } else
        if (event.getSource() == diagnosesCauseTable)
        {
            initDiagnosesCauseTypeMVC();
        } else
        if (event.getSource() == mapSourceTable)
        {
            initMapSourceCodeMVC();
        } else
        if (event.getSource() == travelMeansTable)
        {
            initMeansOfTravelMVC();
        } else
        if (event.getSource() == occupationTypeTable)
        {
            initOccupationTypeMVC();
        } else
        if (event.getSource() == patientTypeTable)
        {
            initPatientTypeMVC();
        } else
        if (event.getSource() == relationTable)
        {
            initRelationTypeMVC();
        } else
        if (event.getSource() == residenceTable)
        {
            initResidenceModeMVC();
        } else
        if (event.getSource() == specialistTypeTable)
        {
            initSpecialistTypeMVC();
        }  else
        if (event.getSource() == specialistTable)
        {
            initSpecialistMaintMVC();
        } else
        if (event.getSource() == timeUnitsTable)
        {
            initTimeUnitsTypeMVC();
        }  else
        if (event.getSource() == onsetUnitsTable)
        {
            initOnsetUnitsTypeMVC();
        } else
        if (event.getSource() == currentStatusTable)
        {
            initCurrentStatusTypeMVC();
        } else
        if (event.getSource() == medicalAidTable)
        {
            initMedicalAidTypeMVC();
        } else
        if (event.getSource() == about)
        {
            initAboutBox();
        }  else
        if (event.getSource() == launchAgreement)
        {
            displayLicenseAgreement();
        } else
        if (event.getSource() == newPatient)
        {
            createNewPatientMenu();
        }  else
        if (event.getSource() == basicInfo)
        {
            initBasicInfoMVC();
        }  else
        if (event.getSource() == vitalSigns)
        {
            initVitalInfoMVC();
        }  else
        if (event.getSource() == treatment)
        {
            initTreatmentInfoMVC();
        }  else
        if (event.getSource() == addTreatment)
        {
            initAddTreatmentInfoMVC();
        } else
        if (event.getSource() == addSymptoms)
        {
            initAddSymptomInfoMVC();
        }  else
        if ((event.getSource() == addVitalSigns) || (event.getSource() == vitalStatsButton))
        {
            initAddVitalSignInfoMVC();
        }  else
        if (event.getSource() == symptoms)
        {
            initSymptomInfoMVC();
        }  else
        if ((event.getSource() == addDiagnoses) || (event.getSource() == diagnosesButton))
        {
            initAddDiagnosesInfoMVC();
        }  else
        if (event.getSource() == diagnoses)
        {
            initDiagnosesInfoMVC();
        }  else
        if ((event.getSource() == addSurgery) || (event.getSource() == operationsButton))
        {
            initAddSurgeryInfoMVC();
        }  else
        if (event.getSource() == surgery)
        {
            initSurgeryInfoMVC();
        }  else
        if ((event.getSource() == addHabits) || (event.getSource() == habitsButton))
        {
            initAddHabitInfoMVC();
        }  else
        if (event.getSource() == habits)
        {
            initHabitInfoMVC();
        } else
        if (event.getSource() == addTests)
        {
            initAddTestInfoMVC();
        }  else
        if (event.getSource() == tests)
        {
            initTestInfoMVC();
        } else
        if (event.getSource() == addAllergies)
        {
            initAddAllergyInfoMVC();
        }  else
        if (event.getSource() == allergies)
        {
            initAllergyInfoMVC();
        }  else
        if (event.getSource() == addTravelHistory)
        {
            initAddTravelInfoMVC();
        }  else
        if (event.getSource() == travelHistory)
        {
            initTravelInfoMVC();
        }  else
        if (event.getSource() == addFamilyHistory)
        {
            initAddFamilyInfoMVC();
        } else
        if (event.getSource() == familyHistory)
        {
            initFamilyInfoMVC();
        } else
        if (event.getSource() == addImmunizationHistory)
        {
            initAddImmunizationInfoMVC();
        }  else
        if (event.getSource() == immunizationHistory)
        {
            initImmunizationInfoMVC();
        }  else
        if (event.getSource() == newAppointment)
        {
            initAddAppointmentInfoMVC();
        }  else
        if (event.getSource() == editAppointment)
        {
            initAppointmentInfoMVC();
        }  else
        if (event.getSource() == displayAppointment)
        {
            initDisplayAppointmentInfoMVC();
        }  else
        if (event.getSource() == diagnosticAid)
        {
            initDisplayDiseaseDiagInfoMVC();
        }   else
        if (event.getSource() == rifeGeneratorTool)
        {
            initRifeGeneratorToolMVC();
        } else
        if (event.getSource() == addInjury)
        {
            initAddInjuryInfoMVC();
        }  else
        if (event.getSource() == injury)
        {
            initInjuryInfoMVC();
        } else
        if (event.getSource() == addPoisoning)
        {
            initAddPoisoningInfoMVC();
        }  else
        if (event.getSource() == poisoning)
        {
            initPoisoningInfoMVC();
        } else
        if ((event.getSource() == addCondition) || (event.getSource() == conditionButton))
        {
            initAddConditionInfoMVC();
        }  else
        if (event.getSource() == condition)
        {
            initConditionInfoMVC();
        }  else
        if (event.getSource() == testReferrals)
        {
            initTestReferralInfoMVC();
        }  else
        if (event.getSource() == specialistReferrals)
        {
            initSpecialistReferralInfoMVC();
        }  else
        if (event.getSource() == occupationHistory)
        {
            initOccupationInfoMVC();
        }  else
        if (event.getSource() == prescriptionHistory)
        {
            initPrescriptionInfoMVC();
        }  else
        if (event.getSource() == addTestReferrals)
        {
            initAddTestReferralInfoMVC();
        }  else
        if (event.getSource() == addSpecialistReferrals)
        {
            initAddSpecialistReferralInfoMVC();
        }  else
        if (event.getSource() == addOccupationHistory)
        {
            initAddOccupationInfoMVC();
        }  else
        if (event.getSource() == addPrescriptionHistory)
        {
            initAddPrescriptionInfoMVC();
        }  else
        if (event.getSource() == testInstTable)
        {
            initNewTestInstInfoMVC();
        } else
        if (event.getSource() == displayBasic)
        {
            initDisplayBasicInfoMVC();
        } else
        if (event.getSource() == displayAllPatient)
        {
            initDisplayAllPatientInfoMVC();
        } else
        if (event.getSource() == displayVitalSigns)
        {
            initDisplayVitalSignInfoMVC();
        } else
        if (event.getSource() == displayInjury)
        {
            initDisplayInjuryInfoMVC();
        } else
        if (event.getSource() == displayPoisoning)
        {
            initDisplayPoisoningInfoMVC();
        } else
        if (event.getSource() == displaySymptoms)
        {
            initDisplaySymptomInfoMVC();
        } else
        if (event.getSource() == displayDiagnoses)
        {
            initDisplayDiagnosesInfoMVC();
        } else
        if (event.getSource() == displayTests)
        {
            initDisplayTestInfoMVC();
        } else
        if (event.getSource() == displayTreatment)
        {
            initDisplayTreatmentInfoMVC();
        } else
        if (event.getSource() == displaySurgery)
        {
            initDisplaySurgeryInfoMVC();
        } else
        if (event.getSource() == displayHabits)
        {
            initDisplayHabitInfoMVC();
        } else
        if (event.getSource() == displayFamily)
        {
            initDisplayFamilyInfoMVC();
        } else
        if (event.getSource() == displayAllergies)
        {
            initDisplayAllergyInfoMVC();
        } else
        if (event.getSource() == displayImmunization)
        {
            initDisplayImmunizationInfoMVC();
        } else
        if (event.getSource() == displayTravel)
        {
            initDisplayTravelInfoMVC();
        } else
        if (event.getSource() == displayTestReferrals)
        {
            initDisplayTestRefInfoMVC();
        } else
        if (event.getSource() == displaySpecialistReferrals)
        {
            initDisplaySpecialistRefInfoMVC();
        } else
        if (event.getSource() == displayOccupationHistory)
        {
            initDisplayOccupationHistoryInfoMVC();
        } else
        if (event.getSource() == displayPrescriptionHistory)
        {
            initDisplayPrescriptionHistoryInfoMVC();
        } else
        if (event.getSource() == newPatientButton)
        {
            createNewPatientMenu();
        } else
        if ((event.getSource() == cancelCaptureButton) || (event.getSource() == cancelPatient))
        {
          JOptionPane optionPane = new JOptionPane();
          int result = optionPane.showConfirmDialog(this,"Are you sure you want to cancel and delete the current data captured?","Cancel Capture Confirmation.",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
          if (result == JOptionPane.OK_OPTION) 
          {
            cancelCurrentOperation();
          }
        } else
        if ((event.getSource() == endCaptureButton) || (event.getSource() == endPatient))
        {
          JOptionPane optionPane = new JOptionPane();
          int result = optionPane.showConfirmDialog(this,"Are you sure you want to end the current capture of data?","End Capture Confirmation.",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
          if (result == JOptionPane.OK_OPTION) 
          {
            endCurrentOperation();
          }
           
        } else
        //Populate Tables Tablepop !!!
        if (event.getSource() == deletePatient)
        {
            //PopulateTables newTable = new PopulateTables(2);
        } else  
            
        if ((event.getSource() == followUpPatientButton) || (event.getSource() == followupPatient))
        {
            createFollowUpPatientMenu();
            JInternalFrame tempFrame = null;
            // remove newPatient frame if it exits.
            if ((tempFrame = (JInternalFrame)objectContainer.get((Object)"newPatientFrame")) != null)
            {
                tempFrame.removeAll();
                //desktop.getRootPane().getContentPane().remove(tempFrame);
                desktop.remove(tempFrame);
                objectContainer.remove((Object)"newPatientFrame");
                desktop.updateUI();
            }
            if (objectContainer != null)
            {
                Set objectKeySet = objectContainer.keySet();
                Iterator objectIterator = objectKeySet.iterator();
                while(objectIterator.hasNext())
                {
                    String objectItem  = (String)objectIterator.next();
                    tempObjectFrame = (JInternalFrame)objectContainer.get((Object)objectItem);
                    tempObjectFrame.removeAll();
                    desktop.remove(tempObjectFrame);
                    objectContainer.remove((Object)objectItem);
                    desktop.updateUI();
                 }
            }
            try
            {
                staticInfoFrame = new JInternalFrame("Patient Medical Information",
                true, //resizable
                false, //closable
                true, //maximizable
                true); //iconifiable
                //follow tag
                staticInfoFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
                staticInfoFrame.setLocation(FRAME_X,FRAME_Y);
                staticInfoFrame.setVisible(true);
                initDisplayBasicInfo();
                //initDisplayBasicStaticInfoMVC();
                staticInfoFrame.getContentPane().add((JSplitPane)staticInfoContainer.getInfoSplitPane(),BorderLayout.CENTER);
                objectContainer.put((Object)"staticInfoFrame",(Object)staticInfoFrame);
                desktop.add(staticInfoFrame);
                staticInfoFrame.moveToFront();
                staticInfoFrame.setSelected(true);
                desktop.updateUI();
            }
            catch (Exception exc)
            {
                JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
            }
            
        }  else
        if (event.getSource() == exit)
        {
            System.exit(0);
        }
        //SwingUtilities.getRoot(desktop).repaint();

    }
    

   public void resetStateMachineAndMenus()
   {
        //menuStates = new MenuStateMachineStubs();
        //followUpMenuStates = new NewMenuStateMachineStubs();
        
       if (currentMenu == FOLLOWUP_MENU)
       {
       
           for (int arrayLoop = 0; arrayLoop < menuArrayList.size(); arrayLoop++) {
               JMenuItem tempMenuItem = (JMenuItem)menuArrayList.get(arrayLoop);
               //skip first item
               if (arrayLoop != 0) {
                   tempMenuItem.setBackground(new Color(0,200,0));
               }
           }
       } else
       {
           for (int arrayLoop = 0; arrayLoop < menuArrayList.size(); arrayLoop++) 
           {
               JMenuItem tempMenuItem = (JMenuItem)menuArrayList.get(arrayLoop);
               tempMenuItem.setBackground(new Color(0,200,0));
           }
       } 
   }
   
    
   public void initDisplayHelp()
   {
       String helpSetFile = "MTHelp.hs";
       ClassLoader cl = InfoManager.class.getClassLoader();
       try
       {
           URL hsURL = HelpSet.findHelpSet(cl, helpSetFile);
           System.out.println("HelpSet URL = "  + hsURL);
           HelpSet hs = new HelpSet(null, hsURL);
           hb = hs.createHelpBroker();
       }
       catch (Exception e)
       {
           System.out.println("HelpSet " + e.getMessage() + " Not Found");
       }
       
   }
    
    public void initLangMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Language Type Table Editing",
            true, //resizable
            true, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new LanguageMVC("LANGUAGE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initCategoryTypeMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Body System Type Table Editing",
            true, //resizable
            true, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new CategoryTypeMVC("SYSTEM_CATEGORY_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void initFoodSourceTypeMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Food Source Type Table Editing",
            true, //resizable
            true, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new FoodSourceTypeMVC("FOOD_SOURCE_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void initBevarageSourceTypeMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Bevarage Source Type Table Editing",
            true, //resizable
            true, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new BevarageSourceTypeMVC("BEVARAGE_SOURCE_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void initDiagnosesCauseTypeMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Diagnoses Cause Type Table Editing",
            true, //resizable
            true, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new LanguageMVC("DIAGNOSES_CAUSE_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void initAllergyReactionTypeMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Allergy Reaction Type Table Editing",
                      true, //resizable
                      true, //closable
                      true, //maximizable
                      true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new AllergyReactionTypeMVC("ALLERGY_REACTION_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void initAppointmentStateTypeMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Appointment State Type Table Editing",
                      true, //resizable
                      true, //closable
                      true, //maximizable
                      true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new AppointmentStateTypeMVC("APPOINTMENT_STATE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initBodyLocationTypeMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Body Location Type Table Editing",
                      true, //resizable
                      true, //closable
                      true, //maximizable
                      true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new BodyLocationMVC("BODY_LOCATION",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initAnatomicalLocationMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Anatomical Location Type Table Editing",
                      true, //resizable
                      true, //closable
                      true, //maximizable
                      true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new TypeTableMVC("ANATOMICAL_LOCATION",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public void initDiseaseMaintMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Disease Table Maintenance",
                      true, //resizable
                      true, //closable
                      true, //maximizable
                      true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new NewDiseaseMVC(userName, passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initSurgeryMaintMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Surgery Table Maintenance",
                      true, //resizable
                      true, //closable
                      true, //maximizable
                      true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new NewSurgeryMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }
  
    public void initPhysicianMaintMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Physician Table Maintenance",
                      true, //resizable
                      true, //closable
                      true, //maximizable
                      true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new NewPhysicianMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initNewTestInstInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Test Facility Table Maintenance",
                      true, //resizable
                      true, //closable
                      true, //maximizable
                      true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new NewTestInstMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initSpecialistMaintMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Specialist Table Maintenance",
                      true, //resizable
                      true, //closable
                      true, //maximizable
                      true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new NewSpecialistMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initDosageUnitTypeMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Dosage Unit Type Table Editing ",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new DosageUnitTypeMVC("DOSAGE_UNIT_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
         }
    }

    public void initEmployerTypeMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Employer Type Table Editing ",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new EmployerTypeMVC(userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initTitleTypeMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Title Type Table Editing ",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new TitleTypeMVC("TITLE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initConditionTypeMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Condition Type Table Editing ",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new TitleTypeMVC("CONDITION_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initAppetiteTypeMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Appetite Type Table Editing ",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new TitleTypeMVC("APPETITE_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initToleranceTypeMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Exercise Tolerance Type Table Editing ",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new TitleTypeMVC("EXERCISE_TOLERANCE_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initInstitutionTypeMaintMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Test Institution Type Table Editing ",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new InstitutionTypeMVC("TEST_INSTITUTION_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initAboutBox()
    {
        try
        {
             new AboutDialog(this, "Test");
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(this.desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void initMapSourceCodeMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Map Source Type Table Editing ",
            true, //resizable
            true, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new MapSourceCodeMVC("MAP_SOURCE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initMeansOfTravelMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Means Of Travel Type Table Editing ",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new MeansOfTravelMVC("MEANS_OF_TRAVEL_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initMedicalAidTypeMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Medical Aid Table Maintenance",
            true, //resizable
            true, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new MedicalAidTypeMVC(userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initOccupationTypeMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Occupation Type Table Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new OccupationTypeMVC("OCCUPATION_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initPatientTypeMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Type Table Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new PatientTypeMVC("PATIENT_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initRelationTypeMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Relation Type Table Maintenance",
            true, //resizable
            true, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new RelationTypeMVC("RELATION_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initResidenceModeMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Residence Mode Type Table Maintenance",
            true, //resizable
            true, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new ResidenceModeMVC("RESIDENTIAL_MODE_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initSpecialistTypeMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Specialist Type Table Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new SpecialistTypeMVC("SPECIALIST_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initSymptomMaintMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Symptom Table Maintenance",
          true, //resizable
          true, //closable
          true, //maximizable
          true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new NewSymptomMVC(userName, passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initSymptomInfoMVC()
    {
       JInternalFrame internalFrame = new JInternalFrame("Symptom Table Maintenance",
          true, //resizable
          true, //closable
          true, //maximizable
          true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new SymptomInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initTestMaintMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Test Table Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new NewTestMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initBasicInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Personal Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new BasicInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initVitalInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Vital Sign Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new VitalInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initTreatmentInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Treatment Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new TreatmentInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddTreatmentInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Treatment Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddTreatmentInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddVitalSignInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Vital Sign Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddVitalSignInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddSymptomInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Symptom Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddSymptomInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initTimeUnitsTypeMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Time Units Type Table Maintenance",
            true, //resizable
            true, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new TimeUnitsTypeMVC("TIME_UNITS",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initOnsetUnitsTypeMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Onset Units Type Table Maintenance",
            true, //resizable
            true, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new TypeTableMVC("ONSET_UNITS",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initCurrentStatusTypeMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Current Status Type Table Maintenance",
            true, //resizable
            true, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(paneSize);
             internalFrame.setLocation(lastOffset, lastOffset);
             internalFrame.setVisible(true);
             internalFrame.getContentPane().add(new TypeTableMVC("CURRENT_STATUS_TYPE",userName,passWord));
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 
     
    public JInternalFrame initNewPatientMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("New Patient Data Capture",
            true, //resizable
            true, //closable
            true, //maximizable
            true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X ,FRAME_Y);
             internalFrame.setVisible(true);
             MTTableMVC1 newPatientPanel = new MTTableMVC1("NEWPATIENT", "SIMPLE",userName,passWord);
             patientKey = newPatientPanel.getKeyValue();
             JScrollPane scrollPane = new JScrollPane(newPatientPanel);
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
        return internalFrame;
    } 

    public void initDisplayBasicStaticInfoMVC()
    {
        try
        {
            staticInfoContainer.removeCategory("Personal Info");
            persInfoPanel = new DisplayBasicStaticInfoMVC(patientKey,userName,passWord);
            //persInfoPanel.typeDBAccess.closeConnection();
            //persInfoPanel.typeDBAccess = null;
            staticInfoContainer.addCategory("Personal Info", persInfoPanel);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayBasicInfo()
    {
        try
        {
            staticInfoContainer.removeCategory("Personal Info");
            persInfoPanel = new DisplayBasicStaticInfoMVC(userName,passWord);
            //persInfoPanel.typeDBAccess.closeConnection();
            //persInfoPanel.typeDBAccess = null;
            staticInfoContainer.addCategory("Personal Info", persInfoPanel);
            //   patientKey = persInfoPanel.getSelectedKeyValue();
            //   patientName = getPatientName(patientKey);
            //   cursorField.setText("Active Patient: " + patientName);
            //   cursorField.setEditable(false);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayComplaintStaticInfoMVC()
    {
        try
        {
            complaintInfoPanel = new DisplayComplaintStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            complaintInfoPanel.closeConnection();
            complaintInfoPanel.closeDBInstance();
            System.gc();
            //complaintInfoPanel.typeDBAccess = null;
            if (complaintInfoPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Current Complaints", complaintInfoPanel);
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initUpdateDisplayComplaintStaticInfoMVC()
    {
        try
        {
            staticInfoContainer.removeCategory("Current Complaints");
            complaintInfoPanel = new DisplayComplaintStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            complaintInfoPanel.closeConnection();
            complaintInfoPanel.closeDBInstance();
            System.gc();
            if (complaintInfoPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Current Complaints", complaintInfoPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayMedicalHistoryStaticInfoMVC()
    {
        try
        {
            DisplayMedicalHistoryStaticInfoMVC medicalHistoryInfoPanel = new DisplayMedicalHistoryStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            medicalHistoryInfoPanel.closeConnection();
            medicalHistoryInfoPanel.closeDBInstance();
            System.gc();
            if (medicalHistoryInfoPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Previous Medical History", medicalHistoryInfoPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayAssesmentStaticInfoMVC()
    {
        try
        {
            DisplayAssesmentStaticInfoMVC assesmentInfoPanel = new DisplayAssesmentStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            assesmentInfoPanel.closeConnection();
            assesmentInfoPanel.closeDBInstance();
            System.gc();
            if (assesmentInfoPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Patient Assesment", assesmentInfoPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayTreatmentPlanStaticInfoMVC()
    {
        try
        {
            DisplayTreatmentPlanStaticInfoMVC treatmentPlanInfoPanel = new DisplayTreatmentPlanStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            treatmentPlanInfoPanel.closeConnection();
            treatmentPlanInfoPanel.closeDBInstance();
            System.gc();
            if (treatmentPlanInfoPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Treatment Plan", treatmentPlanInfoPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initUpdateDisplayTreatmentPlanStaticInfoMVC()
    {
        try
        {
            staticInfoContainer.removeCategory("Treatment Plan");
            DisplayTreatmentPlanStaticInfoMVC treatmentPlanInfoPanel = new DisplayTreatmentPlanStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            treatmentPlanInfoPanel.closeConnection();
            treatmentPlanInfoPanel.closeDBInstance();
            System.gc();
            if (treatmentPlanInfoPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Treatment Plan", treatmentPlanInfoPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplaySurgicalHistoryStaticInfoMVC()
    {
        try
        {
            DisplaySurgicalHistoryStaticInfoMVC surgicalHistoryInfoPanel = new DisplaySurgicalHistoryStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            surgicalHistoryInfoPanel.closeConnection();
            surgicalHistoryInfoPanel.closeDBInstance();
            System.gc();
            if (surgicalHistoryInfoPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Previous Surgical History", surgicalHistoryInfoPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplaySystemicStaticInfoMVC()
    {
        try
        {
            DisplaySystemicStaticInfoMVC systemicInfoPanel = new DisplaySystemicStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            systemicInfoPanel.closeConnection();
            systemicInfoPanel.closeDBInstance();
            System.gc();
            if (systemicInfoPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Systemic Info", systemicInfoPanel);
            }    
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayFamilyHistoryStaticInfoMVC()
    {
        try
        {
            DisplayFamilyHistoryStaticInfoMVC familyHistoryPanel = new DisplayFamilyHistoryStaticInfoMVC(patientKey,dateSelectionArray, userName, passWord);
            familyHistoryPanel.closeConnection();
            familyHistoryPanel.closeDBInstance();
            System.gc();
            if (familyHistoryPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Family History", familyHistoryPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplaySocialHistoryStaticInfoMVC()
    {
        try
        {
            DisplaySocialHistoryStaticInfoMVC socialHistoryPanel = new DisplaySocialHistoryStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            socialHistoryPanel.closeConnection();
            socialHistoryPanel.closeDBInstance();
            System.gc();
            if (socialHistoryPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Social History", socialHistoryPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayOccupationalHistoryStaticInfoMVC()
    {
        try
        {
            DisplayOccupationHistoryStaticInfoMVC occupationHistoryPanel = new DisplayOccupationHistoryStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            occupationHistoryPanel.closeConnection();
            occupationHistoryPanel.closeDBInstance();
            System.gc();
            if (occupationHistoryPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Occcupation History", occupationHistoryPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayTreatmentHistoryStaticInfoMVC()
    {
        try
        {
            DisplayTreatmentHistoryStaticInfoMVC treatmentHistoryPanel = new DisplayTreatmentHistoryStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            treatmentHistoryPanel.closeConnection();
            treatmentHistoryPanel.closeDBInstance();
            System.gc();
            if (treatmentHistoryPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Treatment History", treatmentHistoryPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayAllergyHistoryStaticInfoMVC()
    {
        try
        {
            DisplayAllergyHistoryStaticInfoMVC allergyHistoryPanel = new DisplayAllergyHistoryStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            allergyHistoryPanel.closeConnection();
            allergyHistoryPanel.closeDBInstance();
            System.gc();
            if (allergyHistoryPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Allergy History", allergyHistoryPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayTravelHistoryStaticInfoMVC()
    {
        try
        {
            DisplayTravelHistoryStaticInfoMVC travelHistoryPanel = new DisplayTravelHistoryStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            travelHistoryPanel.closeConnection();
            travelHistoryPanel.closeDBInstance();
            System.gc();
            if (travelHistoryPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Travel History", travelHistoryPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayExaminationStaticInfoMVC()
    {
        try
        {
            DisplayExaminationStaticInfoMVC examinationInfoPanel = new DisplayExaminationStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            examinationInfoPanel.closeConnection();
            examinationInfoPanel.closeDBInstance();
            System.gc();
            if (examinationInfoPanel.isEmpty == false)
            {
                staticInfoContainer.addCategory("Examination Info", examinationInfoPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initUpdateDisplayExaminationStaticInfoMVC()
    {
        try
        {
            staticInfoContainer.removeCategory("Examination Info");
            DisplayExaminationStaticInfoMVC examinationInfoPanel = new DisplayExaminationStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            examinationInfoPanel.closeConnection();
            examinationInfoPanel.closeDBInstance();
            System.gc();
            if (examinationInfoPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Examination Info", examinationInfoPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initUpdateDisplayAssesmentStaticInfoMVC()
    {
        try
        {
            staticInfoContainer.removeCategory("Patient Assesment");
            DisplayAssesmentStaticInfoMVC assesmentInfoPanel = new DisplayAssesmentStaticInfoMVC(patientKey,dateSelectionArray,userName,passWord);
            assesmentInfoPanel.closeConnection();
            assesmentInfoPanel.closeDBInstance();
            System.gc();
            if (assesmentInfoPanel.isEmpty == false)
            {
               staticInfoContainer.addCategory("Patient Assesment", assesmentInfoPanel);
            }
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(desktop, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 


    public void initDiagnosesInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Diagnoses Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DiagnosesInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddDiagnosesInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Diagnoses Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             internalFrame.setFrameIcon(new ImageIcon(imagePath + "assessment_sm.gif"));
             JScrollPane scrollPane = new JScrollPane(new AddDiagnosesInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initSurgeryInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Surgery Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             internalFrame.setFrameIcon(new ImageIcon(imagePath + "surgicalhistory_sm.gif"));
             JScrollPane scrollPane = new JScrollPane(new SurgeryInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddSurgeryInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Surgery Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setFrameIcon(new ImageIcon(imagePath + "surgicalhistory_sm.gif"));
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddSurgeryInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initHabitInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Drug Habit Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DrugInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddHabitInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Drug Habit Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddDrugInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAllergyInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Allergy Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AllergyInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddAllergyInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Allergy Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddAllergyInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initTestReferralInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Test Referral Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new TestReferralInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddTestReferralInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Test Referral Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddTestReferralInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initSpecialistReferralInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Specialist Referral Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new SpecialistReferralInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddSpecialistReferralInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Specialist Referral Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddSpecialistReferralInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initOccupationInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Occupation Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new OccupationInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddOccupationInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Occupation Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddOccupationInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initPrescriptionInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Prescription Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new PrescriptionInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddPrescriptionInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Prescription Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddPrescriptionInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initTravelInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Travel Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new TravelInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddTravelInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Travel Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddTravelInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initFamilyInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Family Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new FamilyInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAppointmentInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Appointment Info Editing",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AppointmentInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddFamilyInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Family Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddFamilyInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initImmunizationInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Immunization Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new ImmunizationInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddImmunizationInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Immunization Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddImmunizationInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddAppointmentInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Appointment Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddAppointmentInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initInjuryInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Injury Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new InjuryInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddInjuryInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Injury Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddInjuryInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initPoisoningInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Poisoning Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new PoisoningInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddPoisoningInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Poisoning Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddPoisoningInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initConditionInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Condition Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new ConditionInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddConditionInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Condition Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddConditionInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayConditionInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Condition Info Display",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayConditionInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayBasicInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Personal Info Display",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayBasicInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayAllPatientInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Full Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayAllPatientInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initTestInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Test Info Maintenance",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new TestInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayVitalSignInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Vital Signs Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayVitalSignInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayInjuryInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Injury Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayInjuryInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayPoisoningInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Poisoning Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayPoisoningInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplaySymptomInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Symptom Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplaySymptomInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayDiagnosesInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Diagnoses Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayDiagnosesInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayTestInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Tests Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayTestInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayTreatmentInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Treatment Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayTreatmentInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayAppointmentInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Appointment Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayAppointmentInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void initDisplaySurgeryInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Surgery Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplaySurgeryInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayDiseaseDiagInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Diagnostic Aid",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(800, 480));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
                 JScrollPane scrollPane = new JScrollPane(new DisplayDiseaseDiagInfoMVC(patientKey,userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             diagExitButton = new JButton("Exit Diagnostic Aid", new ImageIcon(imagePath + "updateexit24.gif"));
             diagExitButton.addActionListener(this);
             internalFrame.getContentPane().add(diagExitButton,BorderLayout.SOUTH);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
             objectContainer.put((Object)"DiseaseDiagFrame",(Object)internalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 
    public void initRifeGeneratorToolMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Rife Generator",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             ImageIcon icon = new ImageIcon(imagePath + "rife_sm.gif");
             internalFrame.setFrameIcon(icon);
             //super(patientKey, "DISPLAYDIAGBYSYMPTOM",1,"SIMPLE",userName, passWord);
             //JScrollPane scrollPane = new JScrollPane(new RifeTablePanel());
             
             internalFrame.getContentPane().add(new RifeTablePanel(),BorderLayout.CENTER);
             rifeExitButton = new JButton("Exit Rife Generator", new ImageIcon(imagePath + "updateexit24.gif"));
             rifeExitButton.addActionListener(this);
             internalFrame.getContentPane().add(rifeExitButton,BorderLayout.SOUTH);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
             objectContainer.put((Object)"RifeGeneratorFrame",(Object)internalFrame);
             desktop.updateUI();
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayHabitInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Habits Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayHabitInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayFamilyInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Family Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayFamilyInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayImmunizationInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Immunization Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayImmunizationInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayAllergyInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Allergy Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayAllergyInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayTravelInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Travel History Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayTravelInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayTestRefInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient Test Referral Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayTestRefInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplaySpecialistRefInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Specialist Referral Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplaySpecialistRefInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayOccupationHistoryInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Occupation History Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayOccupationHistoryInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initDisplayPrescriptionHistoryInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Prescription History Report",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new DisplayPrescriptionHistoryInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void initAddTestInfoMVC()
    {
        JInternalFrame internalFrame = new JInternalFrame("Patient New Test Info Capture",
           true, //resizable
           true, //closable
           true, //maximizable
           true); //iconifiable
        try
        {
             internalFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
             internalFrame.setLocation(FRAME_X,FRAME_Y);
             internalFrame.setVisible(true);
             JScrollPane scrollPane = new JScrollPane(new AddTestInfoMVC(userName,passWord));
             internalFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
             desktop.add(internalFrame);
             internalFrame.moveToFront();
             internalFrame.setSelected(true);
        }
        catch (Exception exc)
        {
           JOptionPane.showMessageDialog(internalFrame, exc.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
        }
    } 

    public void getNoteFile()
    {
        File file = null;
        try
        {
            int returnOption = fileChooser.showOpenDialog(this);
            if (returnOption == JFileChooser.APPROVE_OPTION)
            {
                file = fileChooser.getSelectedFile();
                
                FileInputStream fis = new FileInputStream(file);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                
                PlainDocument note = new PlainDocument();
                JEditorPane noteEditorPane = new JEditorPane();
                noteEditorPane.read(in, note);
                statusField.setText("Opened " + file.getName());
                JInternalFrame internalFrame = new JInternalFrame(file.getName(),
                                                    true, //resizable
                                                    true, //closable
                                                    true, //maximizable
                                                    true); //iconifiable
                internalFrame.setSize(paneSize);
                internalFrame.setLocation(lastOffset, lastOffset);
                internalFrame.setVisible(true);
                internalFrame.getContentPane().add(new JScrollPane(noteEditorPane));
                desktop.add(internalFrame);
                internalFrame.moveToFront();
                internalFrame.setSelected(true);
                
                class MouseClickListener extends MouseAdapter
                {
                    public MouseClickListener(JInternalFrame internalFrame)
                    {
                        this.internalFrame = internalFrame;
                    }
                    
                    public void mouseReleased(MouseEvent event)
                    {
                        considerShowingPopupMenu(event);
                        internalFrame.moveToFront();
                    }
                    
                    private void considerShowingPopupMenu(MouseEvent event)
                    {
                        if (event.isPopupTrigger())
                        {
                            popupMenu.show(event.getComponent(), event.getX(), event.getY());
                        }
                    }
                    
                    private JInternalFrame internalFrame;
                }
                noteEditorPane.addMouseListener(new MouseClickListener(internalFrame));
                lastOffset += paneOffset;
                if (lastOffset > Math.min(screenSize.getWidth()*0.6, screenSize.getHeight()*0.6))
                {
                    lastOffset = 0;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    JTextField getCursField()
    {
        return cursorField;
    }
    

         private void swapElements(java.lang.String stringArray[], int i, int j)
        {
            java.lang.String temp;
            
            // Swap the elements
            temp = stringArray[i];
            stringArray[i] = stringArray[j];
            stringArray[j] = temp;
            
        }
        
        private void quickSort(java.lang.String stringArray[],
        int lo0,
        int hi0)
        {
            
            int lo = lo0;
            int hi = hi0;
            java.lang.String mid = null;
            
            
            // See if the high index is more than the low index
            if (hi0 > lo0)
            {
                // Arbitrarily establishing partition element as the midpoint of the array
                mid = stringArray[(lo0 + hi0) / 2];
                
                // Loop through the array until indices cross
                while (lo <= hi)
                {
                    // Find the first element that is greater than or equal to
                    // the partition element starting from the left Index.
                    while ((lo < hi0) &&
                    (stringArray[lo].compareTo(mid) < 0))
                        // Increment the lower index
                        ++lo;
                    
                    // Find an element that is smaller than or equal to
                    // the partition element starting from the right Index.
                    while ((hi > lo0) &&
                    (stringArray[hi].compareTo(mid) > 0))
                        // Decrement the high index
                        --hi;
                    
                    // If the indexes have not crossed, swap
                    if (lo <= hi)
                    {
                        // Swap the elements
                        swapElements(stringArray, lo, hi);
                        ++lo;
                        --hi;
                    }
                }
                
                // If the right index has not reached the left side of array
                // must now sort the left partition.
                if (lo0 < hi)
                    quickSort(stringArray, lo0, hi);
                
                // If the left index has not reached the right side of array
                // must now sort the right partition.
                if (lo < hi0)
                    quickSort(stringArray, lo, hi0 );
                
            }
        }
    
    
    //*********Non Static Inner Class**********************************

  protected class MTTableMVC1 extends JPanel{
   
   // The initial width and height of the frame
  private JScrollPane typePanel;
  public JScrollPane theScrollPane1;
  public  JButton updateButton;
  public  JButton addrowButton;
  public  JButton removeRowButton;
  public DBAccess typeDBAccess;
  public String[][] dataArray;
  private Object[][] tmpDataArray;
  private static final String SIMPLE_VIEW = "SIMPLE";
  private static final String OTHER_VIEW = "OTHER";
  private static final int tabsTop = JTabbedPane.TOP;
  private static final int tabsBottom = JTabbedPane.BOTTOM;
  private static final int tabsLeft = JTabbedPane.LEFT;
  private static final int tabsRight = JTabbedPane.RIGHT;
  private MTTableMVC1 MTTableMVCApp;
  public HashMap lookupTableDataCollection;
  public HashMap simpleTextCollection = new HashMap(10);
  private HashMap lookupTableData;
  private JButton openTextAreaButton;
  private Dimension textAreaSize = new Dimension(0,0);
  private boolean textFrame = false;
  private JScrollPane areaScrollPane;
  private JTextArea textArea;
  private JLayeredPane layeredPane;
  private JTextField textField;
  public  String fieldName = "";
  private String imagePath = "";
  private ArrayList fieldTextItems;
  private String docScanPath = "";
  private TwainScan twainScanner;
  private IniFile config = new IniFile();
  boolean iniFilePresent = false;
  private JButton addFileButton;
  private JButton scanDocButton;
  private String keyValue = null;
  private boolean updatedFlag = false;


  
  // the Model
  MTTableDataModel theDataModel;
  
  //View and controller combined
  DataView theDataView; //   
  
  // Another View
  //Add other views here if needed;   
  
  // the Controller
  // combined into view
    
    private  class CtoMAdaptor implements ActionListener 
    {
        MTTableDataModel model;
        DataView controller;
        
        public CtoMAdaptor(DataView c, MTTableDataModel m) throws MTException
        {
            model = m;
            controller = c;
            try 
            {
                controller.dataUpdateButton.addActionListener(this);
            }
            catch(Exception e)
            {
               throw new MTException(InfoManager.ADAPTER_ERROR, e.getMessage());
            }

         }
         public void actionPerformed(ActionEvent e) 
         {
            ArrayList fieldValueList = null;
            String[] fieldValueArray = null;
            String[] fieldComboValueArray = null;
            String comboValue = null;
            try 
            {
                if (e.getSource() == controller.dataUpdateButton)
                {
                       
                    
                    
                    Set keySet = controller.tablesTextList.keySet();
                    Iterator iter = keySet.iterator();
                    int lastCount = keySet.size() - 1;
                    while (iter.hasNext())
                    {
                       String tableName = (String)iter.next();
                       Set fieldSet = ((HashMap)controller.tablesTextList.get(tableName)).keySet();
                       HashMap fieldList = (HashMap)controller.tablesTextList.get(tableName);
                       Iterator fieldIter = fieldSet.iterator();
                       HashMap dbAccessCollection = (HashMap)model.getdbAccessCollection();
                       DBAccess dataInstance = (DBAccess)dbAccessCollection.get((Object)tableName);
                       while (fieldIter.hasNext())
                       {
                          String fieldName = (String)fieldIter.next();
                          String keyType = dataInstance.dataTable.getColumnKeyType(fieldName);
                          String fieldValue = "";
                          
                          if (keyType.equalsIgnoreCase("PRIMARY"))
                          {
                              fieldValue =  ((JTextField)fieldList.get((Object)fieldName)).getText();
                              int loop = 0;
                              //Code to strip off leading zeros
                              for (loop = 0; loop < fieldValue.length(); loop++)
                              {
                                  if (fieldValue.substring(loop, loop+1).equalsIgnoreCase("0") == false)
                                  {
                                      break;
                                  }
                              }
                              fieldValue = fieldValue.substring(loop);
                              HashMap wrapUpCollection = (HashMap)model.getdbWrapUpAccessCollection();
                              if (wrapUpCollection != null)
                              {
                                  Set wrapupKeySet = wrapUpCollection.keySet();
                                  Iterator wrapIter = wrapupKeySet.iterator();
                                  while (wrapIter.hasNext())
                                  {    
                                       String wrapTableName = (String)wrapIter.next();
                                       DBAccess wrapDataInstance = (DBAccess)wrapUpCollection.get((Object)wrapTableName);
                                       String[] columnSet = wrapDataInstance.dataTable.getColumnNames();
                                       for (int loop1 = 0; loop1 < columnSet.length; loop1++)
                                       {
                                           if (columnSet[loop1].equalsIgnoreCase(fieldName))
                                           {
                                              wrapDataInstance.dataTable.addElement(fieldName, fieldValue);
                                              wrapDataInstance.dataTable.addElement("DELETED", "0");
                                              break;
                                           }
                                       }
                                  }
                              }
                              
                          } else
                          if (keyType.equalsIgnoreCase("BOOLEAN_FIELD"))
                          {
                              String boolVal = "";
                              comboValue = (String)((JComboBox)fieldList.get((Object)fieldName)).getSelectedItem();
                              if (comboValue.equalsIgnoreCase("No"))
                              {
                                 boolVal = "0";
                              } else
                              {
                                 boolVal = "1";
                              }
                              fieldValue = boolVal;   
                          }  else
                          if (keyType.equalsIgnoreCase("TO_BY_BOOLEAN_FIELD"))
                          {
                              String boolVal = "";
                              comboValue = (String)((JComboBox)fieldList.get((Object)fieldName)).getSelectedItem();
                              if (comboValue.equalsIgnoreCase("To"))
                              {
                                 boolVal = "0";
                              } else
                              {
                                 boolVal = "1";
                              }
                              fieldValue = boolVal;   
                          } else
                          if ((keyType.equalsIgnoreCase("TEXT_AREA_FIELD")) || (keyType.equalsIgnoreCase("DOCUMENT_AREA_FIELD")))
                          {
                              fieldValue =  ((JTextArea)fieldList.get((Object)fieldName)).getText();
                          } else
                          if (keyType.equalsIgnoreCase("FOREIGN"))
                          {
                              lookupTableData = (HashMap)lookupTableDataCollection.get((Object)fieldName);
                              fieldValueList = (ArrayList)fieldList.get((Object)fieldName);
                              fieldComboValueArray = new String[fieldValueList.size()]; 
                              fieldValueArray = new String[fieldValueList.size()];
                              
                              for (int loop = 0; loop < fieldValueList.size(); loop++)
                              {    
                                  fieldValue = (String)((JComboBox)fieldValueList.get(loop)).getSelectedItem();
                                  fieldComboValueArray[loop] = fieldValue;
                              }
                              for (int loop = 0; loop < fieldComboValueArray.length; loop++)
                              {    
                                  comboValue = fieldComboValueArray[loop];
                                  Set lookupKeySet = lookupTableData.keySet();
                                  Iterator lookupIter = lookupKeySet.iterator();
                                  while (lookupIter.hasNext())
                                  {
                                      String theKey = (String)lookupIter.next();
                                      String theValue = (String)lookupTableData.get((Object)theKey);
                                      if (theValue.equalsIgnoreCase(comboValue))
                                      {
                                          //fieldValueArray[loop] = theKey;
                                          fieldValue = theKey;
                                          break;
                                      }
                                  }
                              }
                             /* lookupTableData = (HashMap)lookupTableDataCollection.get((Object)fieldName);
                             comboValue = (String)((JComboBox)fieldList.get((Object)fieldName)).getSelectedItem();
                             Set lookupKeySet = lookupTableData.keySet();
                             Iterator lookupIter = lookupKeySet.iterator();
                             while (lookupIter.hasNext())
                             {
                                String theKey = (String)lookupIter.next();
                                String theValue = (String)lookupTableData.get((Object)theKey);
                                if (theValue.equalsIgnoreCase(comboValue))
                                {
                                    fieldValue =  theKey;
                                    break;
                                }
                             } */ 
                          } else
                          {
                              fieldValue =  ((JTextField)fieldList.get((Object)fieldName)).getText();
                          }
                          dataInstance.dataTable.addElement(fieldName, fieldValue);
                       }
                       //OK Now lock the table for write
                      // String tableUnlock = "UNLOCK TABLES";
                      // dataInstance.updateUsingSQL(tableUnlock);
                       dataInstance.insertBulkData();

                       controller.dataUpdateButton.setEnabled(false);
                    }
                    HashMap wrapUpCollection = (HashMap)model.getdbWrapUpAccessCollection();
                    if (wrapUpCollection != null)
                    {
                       Set wrapupKeySet = wrapUpCollection.keySet();
                       Iterator wrapIter = wrapupKeySet.iterator();
                       while (wrapIter.hasNext())
                       {     
                            String wrapTableName = (String)wrapIter.next();
                            DBAccess wrapDataInstance = (DBAccess)wrapUpCollection.get((Object)wrapTableName);
                            wrapDataInstance.insertBulkData();
                            
                       }
                    }
                    //model.addData();
                    updatedFlag = true;
                    //new code
                    int buttonEvent =  model.getUpdateButtonEvent();
                    menuStates.setEventNumber(buttonEvent);
                    int stateresult = menuStates.menu_execute();
                    ArrayList methodItems = menuStates.getMethodItems();

                    if (methodItems.size() != 0)
                    {
                        for (arrayLoop = 0; arrayLoop < menuArrayList.size(); arrayLoop++)
                        {
                            JMenuItem tempMenuItem = (JMenuItem)menuArrayList.get(arrayLoop);
                            if (currentMenuItem == null)
                            {
                                currentMenuItem = (JMenuItem)menuArrayList.get(0);
                                //tempMenuItem = (JMenuItem)menuArrayList.get(0);
                                currentMenuItem.setBackground(new Color(0,200,0));
                                break;
                            } else
                            {
                                //if (currentMenuItem == tempMenuItem)
                                //{
                                    //tempMenuItem = (JMenuItem)menuArrayList.get(arrayLoop + 1);
                                    currentMenuItem = (JMenuItem)menuArrayList.get(0);
                                    currentMenuItem.setBackground(new Color(0,200,0));
                                    break;
                                //}
                            }
                        }
                        for (int loop = 0; loop < methodItems.size(); loop++)
                        {
                            int methodItem = Integer.parseInt((String)methodItems.get(loop));
                            executeMethod(methodItem);
                        }
                    }
                    //^^
                    //end new code
                    
                } else
                if (e.getSource() == addrowButton)
                {
                    //model.addRow();
                } else
                if (e.getSource() == removeRowButton)
                {
                    //model.removeRow();
                }
                 else
                if (e.getSource() == openTextAreaButton)
                {
                    //model.removeRow();
                }
                    
            }
            catch (MTException exc) 
            {
               JOptionPane.showMessageDialog(theDataView, exc.getErrorMessage(), exc.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex) 
            {
               JOptionPane.showMessageDialog(theDataView, ex.getMessage(), "Unspecified Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } 

    class MTTableDataModel extends MTBusinessModel 
    {
        public MTTableDataModel(String modelName,String userName, char[] password) throws MTException
        {
            super(modelName, InfoManager.OS_VERSION, userName, password);
        }
        
        public void updateData()
        {
             /*try
             {
                
             } catch (Exception exc) 
             {
                errorString = exc.getMessage();
             } */
        }           
    }
    
    public class DataView extends JPanel
    {
        private JTabbedPane tabbedPane;
        public JButton dataUpdateButton;
        private HashMap fieldTextList;
        public HashMap tablesTextList;
        public DataView(String viewType, int tabPlacement, MTTableDataModel _theModel, int _height, int  _width) throws MTException
        {
            if(viewType.equalsIgnoreCase(SIMPLE_VIEW))
            {
                try
                {
                    initSimpleDataView(tabPlacement, _theModel, _height, _width);
                }
                catch (MTException exc)
                {
                    throw exc;
                }
            } else
            if(viewType.equalsIgnoreCase(OTHER_VIEW))
            {
                //try
                //{
                    //initOtherDataView(tabPlacement, _theModel, _height, _width);
                //}
                //catch (MTException ex)
                //{
                    //throw ex;
                //}
            }
        }
        
        public void initSimpleDataView(int _tabPlacement, MTTableDataModel mtModel, int height, int width) throws MTException
        {
            try
            {
                Border raisedBevel, loweredBevel, compoundBorder;
                raisedBevel = BorderFactory.createRaisedBevelBorder();
                loweredBevel = BorderFactory.createLoweredBevelBorder();
                compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);

                tabbedPane = new JTabbedPane(_tabPlacement);
                JPanel controlPane = new JPanel(); 
                String theTableName = null;
                String theGroupLabel = null;
                theGroupLabel = mtModel.getGroupName();
                setLayout(new BorderLayout());
                JLabel mainHeaderLabel = new JLabel(theGroupLabel);
                mainHeaderLabel.setBorder(BorderFactory.createEtchedBorder());
                this.add(mainHeaderLabel,  BorderLayout.NORTH);
                tablesTextList = new HashMap(mtModel.getTableCount());
                for (int loop = 0; loop < mtModel.getTableCount(); loop++)
                {
                    theTableName = (String)mtModel.getTableNames().get(loop);
                    tabbedPane.addTab((String)mtModel.getTabSymbols().get((Object)mtModel.getTableNames().get(loop)), null, createPane(mtModel, theTableName, height, width));
                    tablesTextList.put((Object)theTableName,(Object)fieldTextList.clone()); 
                }
                this.add(tabbedPane,  BorderLayout.CENTER);

                dataUpdateButton = new JButton("Update");
                dataUpdateButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
                dataUpdateButton.setBackground(new Color(255,255,0));
                controlPane.setLayout(new BorderLayout());
                controlPane.setBorder(compoundBorder);
                controlPane.add(dataUpdateButton, BorderLayout.CENTER);
                this.add(controlPane,  BorderLayout.SOUTH);
            }
            catch (MTException e)
            {
                throw new MTException(e.getExceptionType(), e.getErrorMessage());
            }
            catch (Exception e)
            {
                throw new MTException(InfoManager.VIEW_BUILDER_ERROR, e.getMessage());
            }
            
        }
        
        
        public JPanel createPane(MTTableDataModel _Model, String _TableName, int _height, int _width) throws MTException
        {
            int rowCount  =  0;
            int colCount = 0;
            Border raisedBevel, loweredBevel, compoundBorder;

            HashMap dbAccessCollection = (HashMap)_Model.getdbAccessCollection();
            lookupTableDataCollection = (HashMap)_Model.getLookupTableDataCollection();
            DBAccess dataInstance = (DBAccess)dbAccessCollection.get((Object)_TableName);
            raisedBevel = BorderFactory.createRaisedBevelBorder();
            loweredBevel = BorderFactory.createLoweredBevelBorder();
            compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
            
            JPanel dataPanel = new JPanel();
            JPanel labelPanel = new JPanel();
            JPanel fieldPanel = new JPanel();
            JPanel southPanel = new JPanel();
            JPanel buttonPanel = new JPanel();
            JPanel headingPanel = new JPanel();
            
            rowCount = dataInstance.dataTable.getColumnCount();
            if (rowCount < 21)
            {
               rowCount = 21; 
            }
            dataPanel.setLayout(new BorderLayout());
            labelPanel.setLayout(new GridLayout(rowCount, 0));
            fieldPanel.setLayout(new GridLayout(rowCount, 0));
            buttonPanel.setLayout(new GridLayout(1,0,5,5));
            buttonPanel.setBorder(compoundBorder);
            southPanel.setLayout(new BorderLayout());
            JButton clearButton = new JButton("Clear");
            JButton searchButton = new JButton("Search");
            buttonPanel.add(clearButton);
            buttonPanel.add(searchButton);
            dataPanel.setBorder(BorderFactory.createEtchedBorder());
            String[] theColumnNames = dataInstance.dataTable.getColumnNames();
            String[] theColumnAliases = dataInstance.dataTable.getColumnAliases();
            String[] theColumnTypes = dataInstance.dataTable.getColumnKeyTypes();
            boolean[] theColumnLocks = dataInstance.dataTable.getColumnLocks();
            boolean[] theColumnDisplays = dataInstance.dataTable.getColumnDisplays();
            String thePanelHeading = (String)_Model.getTableAliases().get((Object)_TableName);
            JLabel panelHeadingLabel = new JLabel(thePanelHeading);
            panelHeadingLabel.setForeground(new Color(255,255,255));
            panelHeadingLabel.setPreferredSize(new Dimension(200,20));
            panelHeadingLabel.setFont(new Font("Serif",Font.BOLD,14));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setBackground(new Color(0,50,150));
            headingPanel.add(panelHeadingLabel);
            dataPanel.add(headingPanel, BorderLayout.NORTH);
            colCount = dataInstance.dataTable.getColumnCount();
            fieldTextList = new HashMap(colCount);
            MaskFormatter formatter;
            JFormattedTextField ftf;
            String selectedValue = "";

            try
            {
              for (int fieldIndex = 0; fieldIndex < colCount; fieldIndex++)
              {
                String theFieldLabel = theColumnAliases[fieldIndex] + ":";
                String theFieldName = theColumnNames[fieldIndex];
                String theFieldType = theColumnTypes[fieldIndex];
                String theFieldValue = dataInstance.dataTable.getDataAt(0, fieldIndex);
                int fieldLength = Integer.parseInt(dataInstance.dataTable.getColumnLength(theFieldName));
                boolean theFieldLock = theColumnLocks[fieldIndex];
                boolean theFieldDisplay = theColumnDisplays[fieldIndex];
                JLabel fieldLabel = new JLabel(theFieldLabel);
                fieldLabel.setPreferredSize(new Dimension(200,20));
                if (theFieldType.equalsIgnoreCase("PRIMARY"))
                {
                    String zeroPadding = "";
                    for (int loop = 0; loop < fieldLength - theFieldValue.length(); loop++)
                    {
                        zeroPadding = zeroPadding + "0";
                    }
                    theFieldValue = zeroPadding + theFieldValue;
                    keyValue = theFieldValue;
                    JTextField fieldText = new JTextField(theFieldValue,20);
                    if (theFieldLock)
                    {
                        fieldText.setEnabled(false);
                    } else
                    {
                        fieldText.setEnabled(true);;
                    }
                    fieldText.setPreferredSize(new Dimension(200,20));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(fieldText);
                        //fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldText);
                    }
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldText);
                } else
                if (theFieldType.equalsIgnoreCase("FOREIGN"))
                {
                    lookupTableData = (HashMap)lookupTableDataCollection.get((Object)theFieldName);
                    Set keySet = lookupTableData.keySet();
                    Iterator iter = keySet.iterator();
                    String[] comboBoxData = new String[lookupTableData.size()];
                    int comboBoxDataIndex = 0;
                    while (iter.hasNext())
                    {
                         String theKey = (String)iter.next();
                         String theValue = (String)lookupTableData.get((Object)theKey);
                         comboBoxData[comboBoxDataIndex] = theValue;
                         comboBoxDataIndex++;
                    }
                    quickSort(comboBoxData,0,comboBoxData.length - 1);
                    JComboBox cb = new JComboBox(comboBoxData);  
                    if (theFieldName.equalsIgnoreCase("LANGUAGE_CODE") == true)
                    {
                      String defValue = "English";
                      cb.setSelectedItem(defValue);  
                    } else
                    if (theFieldName.equalsIgnoreCase("GENDER_TYPE_CODE") == true)
                    {
                      String defValue = "Male";
                      cb.setSelectedItem(defValue);  
                    } else
                    if (theFieldName.equalsIgnoreCase("PATIENT_TYPE_CODE") == true)
                    {
                      String defValue = "Middle Income";
                      cb.setSelectedItem(defValue);  
                    } else
                    if (theFieldName.equalsIgnoreCase("MEDICAL_AID_CODE") == true)
                    {
                      String defValue = "Sanmed";
                      cb.setSelectedItem(defValue);  
                    } else
                    if (theFieldName.equalsIgnoreCase("TITLE_CODE") == true)
                    {
                      String defValue = "Mr";
                      cb.setSelectedItem(defValue);  
                    } else
                    if (theFieldName.equalsIgnoreCase("MEDICAL_AID_PRIMARY_MEMBER") == true)
                    {
                      String defValue = "Self";
                      cb.setSelectedItem(defValue);  
                    }
                    //cb.add(new JButton("A.Z"));
                    cb.setPreferredSize(new Dimension(200,20));
                    cb.setBackground(new Color(255,255,255));
                    //cb.setSelectedItem(selectedValue);
                    cb.addActionListener(new CBActionListener());
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(cb);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)cb);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)cb);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                        
                   /* while (iter.hasNext())
                    {
                         String theKey = (String)iter.next();
                         String theValue = (String)lookupTableData.get((Object)theKey);
                         comboBoxData[comboBoxDataIndex] = theValue;
                         comboBoxDataIndex++;
                    }  
                    JComboBox cb = new JComboBox(comboBoxData);  
                    cb.setPreferredSize(new Dimension(200,20));
                    cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(cb);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)cb);
                    }*/
                }  else
                if (theFieldType.equalsIgnoreCase("BOOLEAN_FIELD"))
                {
                    String[] comboBoxData = new String[2];
                    for (int comboBoxDataIndex = 0; comboBoxDataIndex < 2; comboBoxDataIndex++) 
                    {
                        String theValue = "";
                        if (comboBoxDataIndex == 0)
                        {
                            theValue = "No";
                        } else
                        {
                            theValue = "Yes";
                        }
                        if (theFieldValue.equalsIgnoreCase("0"))
                            selectedValue = "No"; 
                        else
                            selectedValue = "Yes";
                        comboBoxData[comboBoxDataIndex] = theValue;
                    }  
                    JComboBox cb = new JComboBox(comboBoxData);  
                    cb.setPreferredSize(new Dimension(200,20));
                    cb.setBackground(new Color(255,255,255));
                    cb.setSelectedItem(selectedValue);
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(cb);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)cb);
                    }
                } else
                if (theFieldType.equalsIgnoreCase("TO_BY_BOOLEAN_FIELD"))
                {
                    String[] comboBoxData = new String[2];
                    for (int comboBoxDataIndex = 0; comboBoxDataIndex < 2; comboBoxDataIndex++) 
                    {
                        String theValue = "";
                        if (comboBoxDataIndex == 0)
                        {
                            theValue = "To";
                        } else
                        {
                            theValue = "By";
                        }
                        selectedValue = "By";
                        comboBoxData[comboBoxDataIndex] = theValue;
                    }  
                    JComboBox cb = new JComboBox(comboBoxData);  
                    cb.setPreferredSize(new Dimension(200,20));
                    cb.setBackground(new Color(255,255,255));
                    cb.setSelectedItem(selectedValue);
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(cb);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)cb);
                    }
                } else
                if (theFieldType.equalsIgnoreCase("PHONE_FIELD"))
                {
                    formatter = new  MaskFormatter("(###) ###-####");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("CELL_FIELD"))
                {
                    formatter = new  MaskFormatter("(###) ###-####");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("AREA_FIELD"))
                {
                    formatter = new  MaskFormatter("####");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("ID_FIELD"))
                {
                    formatter = new  MaskFormatter("#############");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("BIRTH_DAY_FIELD"))
                {
                    formatter = new  MaskFormatter("##/##");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("TEXT_AREA_FIELD"))
                {
                    //textField = new JTextField((String)_TableName + "," + (String)theColumnNames[fieldIndex]);
                    //textField.addMouseListener(new TheMouseListener());
                    //textField.setBackground(new Color(200,200,200));
                    //textField.setForeground(new Color(200,200,200));
                    ImageIcon openIcon = new ImageIcon(imagePath + "Open16.gif");
                    openTextAreaButton = new JButton("Push to open",openIcon);
                    openTextAreaButton.setToolTipText((String)_TableName + "," + (String)theColumnNames[fieldIndex]);
                    openTextAreaButton.setPreferredSize(new Dimension(200,20));
                    openTextAreaButton.addMouseListener(new TheMouseListener());
                    //openTextAreaButton.addActionListener(this);
                    if (theFieldDisplay == true)
                    {
                        textArea = new JTextArea(80,25);
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(openTextAreaButton);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)textArea);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("DOCUMENT_AREA_FIELD"))
                {
                    ImageIcon openIcon = new ImageIcon(imagePath + "Open16.gif");
                    openTextAreaButton = new JButton("Push to open",openIcon);
                    //addFileButton = new JButton("Add");
                    openTextAreaButton.setToolTipText((String)_TableName + "," + (String)theColumnNames[fieldIndex]);
                    openTextAreaButton.setPreferredSize(new Dimension(200,20));
                    openTextAreaButton.addMouseListener(new TheMouseListener());
                    //openTextAreaButton.add(addFileButton);
                    if (theFieldDisplay == true)
                    {
                        textArea = new JTextArea(80,25);
                        textArea.setText(theFieldValue);
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(openTextAreaButton);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)textArea);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("INITIAL_FIELD"))
                {
                    //formatter = new  MaskFormatter("U U U");
                    //formatter.setPlaceholderCharacter('_');
                    //ftf = new JFormattedTextField(formatter);  
                    //ftf.setPreferredSize(new Dimension(200,20));
                    //cb.setBackground(new Color(255,255,255));
                    //if (theFieldDisplay == true)
                    //{
                    //    labelPanel.add(fieldLabel);
                    //    fieldPanel.add(ftf);
                    //    fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    //}
                    String mask = "";
                    for (int loop = 0; loop < fieldLength; loop++)
                    {
                        mask = mask + "*";
                    }
                    formatter = new  MaskFormatter(mask);
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    if (theFieldLock)
                    {
                        ftf.setEnabled(false);
                    } else
                    {
                        ftf.setEnabled(true);
                    }
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }

                } else
                if (theFieldType.equalsIgnoreCase("DATE_TIME_FIELD"))
                {
                    formatter = new  MaskFormatter("####-##-## ##:##:##");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("DATE_FIELD"))
                {
                    formatter = new  MaskFormatter("####-##-##");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                } else
                {
                    String mask = "";
                    for (int loop = 0; loop < fieldLength; loop++)
                    {
                        mask = mask + "*";
                    }
                    formatter = new  MaskFormatter(mask);
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    if (theFieldLock)
                    {
                        ftf.setEnabled(false);
                    } else
                    {
                        ftf.setEnabled(true);
                    }
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                } //end else
              } //end for
            } //end try
            catch (Exception ex) 
            {
                throw new MTException(InfoManager.PANE_CREATION_ERROR, ex.getMessage());
            }
            southPanel.add(buttonPanel, BorderLayout.EAST);
            dataPanel.add(labelPanel, BorderLayout.WEST);
            dataPanel.add(fieldPanel, BorderLayout.EAST);
            //add this later for buttons
            
            //dataPanel.add(southPanel, BorderLayout.SOUTH);
            //dataInstance.closeConnection();
            //dbAccessCollection.remove((Object)_TableName);
            //dataInstance = null;
            //System.gc();
            
            return dataPanel;
        }
        public JTabbedPane getTabbedPane()
        {
            return tabbedPane;
        }
    }
    
    class TheMouseListener extends MouseInputAdapter implements InternalFrameListener 
    {
        public void mouseReleased(MouseEvent mEvent)
        {
           if ( (SwingUtilities.isLeftMouseButton(mEvent)) && (textFrame == false))
           {
                   String cellReference = "";
                   String tableName = "";
                   String fieldName = "";
                   String theToken = "";
                   String fieldIndex = "";
                   cellReference = ((JButton)mEvent.getComponent()).getToolTipText();
                   //((JButton)mEvent.getComponent()).setVisible(false);
                   
                   StringTokenizer fieldTokens = new StringTokenizer(cellReference,",");	
                   int tokenCount = fieldTokens.countTokens();
                   for (int cellReferenceIndex = 0; cellReferenceIndex <  tokenCount; cellReferenceIndex++)
                   {
                        theToken = (String)fieldTokens.nextToken();
                        if (cellReferenceIndex == 0)
                        {
                            tableName = theToken;
                        } else
                        if (cellReferenceIndex == 1)
                        {
                            fieldName = theToken;
                        } else
                        {
                            fieldIndex = theToken;
                        }
                   }
                   String frameHeading = fieldName.replace('_',' ');
                   JInternalFrame textAreaFrame = new JInternalFrame(frameHeading,
                   true, //resizable
                   true, //closable
                   true, //maximizable
                   true); //iconifiable
                   textAreaFrame.setSize(new Dimension(300,300));
                   textAreaFrame.setLocation(200, 200);
                   textAreaFrame.setVisible(true);
       	           textAreaFrame.addInternalFrameListener(this);
                   HashMap fieldMap = (HashMap)(theDataView.tablesTextList.get((Object)tableName));
                   //ArrayList fieldArray = (ArrayList)fieldMap.get((Object)fieldName);
                   //textArea = (JTextArea)fieldArray.get(Integer.parseInt(fieldIndex));
                   textArea = (JTextArea)fieldMap.get((Object)fieldName);
                   areaScrollPane = new JScrollPane(textArea);
                   areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                   areaScrollPane.setPreferredSize(new Dimension(300,300));
                   textAreaFrame.getContentPane().add(areaScrollPane,BorderLayout.CENTER);
                   if ((frameHeading.indexOf("DOCS") > 0) || (frameHeading.indexOf("REFERENCE") > 0))
                   {
                       addFileButton = new JButton("Add File");
                       scanDocButton = new JButton("Scan Document");
                       JPanel buttonPane = new JPanel();
                       buttonPane.add(addFileButton);
                       if (InfoManager.OS_VERSION.equalsIgnoreCase("WINDOWS"))
                       {
                          buttonPane.add(scanDocButton);
                       }
                       addFileButton.addActionListener(new ActionListener()
                       {
                           public void actionPerformed(ActionEvent e)
                           {
                               JFileChooser fileChooser = new JFileChooser(docScanPath);
                               FileExtensionFilter filter = new FileExtensionFilter();
                               filter.add("gif");
                               filter.add("jpg");
                               filter.setDescription("GIF & JPG Images");
                               fileChooser.setFileFilter(filter);
                               int returnOption = fileChooser.showOpenDialog(theDataView);
                               if (returnOption == JFileChooser.APPROVE_OPTION)
                               {
                                   String fileName = fileChooser.getSelectedFile().getName();
                                   textArea.append("\n" + fileName);
                               }
                               textArea.repaint();
                           }
                       });
                        
                       if (InfoManager.OS_VERSION.equalsIgnoreCase("WINDOWS"))
                       {
                           scanDocButton.addActionListener(new ActionListener()
                           {
                               public void actionPerformed(ActionEvent e)
                               {
                                   String fileIndex = "";
                                   iniFilePresent = config.readIni(docScanPath + "docscan.ini");
                                   if (iniFilePresent)
                                   {
                                       JOptionPane optionPane = new JOptionPane();
                                       int result = optionPane.showConfirmDialog(theDataView,"Put document in scanner and press\nOK when ready to scan else press Cancel.","Scanner Confirmation.",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                                       if (result == JOptionPane.OK_OPTION)
                                       {
                                           config.setHeader("DOCSCANS");
                                           fileIndex = config.getItem("ScanFileIndex");
                                           int intFileIndex = Integer.parseInt(fileIndex);
                                           intFileIndex++;
                                           fileIndex = String.valueOf(intFileIndex);
                                           twainScanner = new TwainScan();
                                           config.changeItem("ScanFileIndex", fileIndex);
                                           config.writeIni(docScanPath + "docscan.ini");
                                           twainScanner.scan();
                                           twainScanner.save(docScanPath, "DocScan" + fileIndex + ".jpg");
                                           textArea.append("\n" + "DocScan" + fileIndex + ".jpg");
                                       }
                                       
                                   } else
                                   {
                                       JOptionPane optionPane = new JOptionPane();
                                       optionPane.showMessageDialog(theDataView,"Required docscan.ini file is missing!");
                                   }
                               }
                           });
                       }
                       textAreaFrame.getContentPane().add(buttonPane,BorderLayout.SOUTH);
                   }
                   add(textAreaFrame);
                   textAreaFrame.moveToFront();
                   textAreaFrame.requestFocus();
                   textFrame = true;
           }
        }
       
        public void internalFrameActivated(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
        public void internalFrameClosed(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
        public void internalFrameClosing(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
           textFrame = false;
        }
        
        public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
        public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
        public void internalFrameIconified(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
        public void internalFrameOpened(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
     }    

    class CBActionListener implements ActionListener
    {
       public void actionPerformed(ActionEvent e) 
       {
           boolean keyFound = false;
           String selectedKey = null;
           String theCollectionKey = null;
           int arrayIndex = 0;
           
           String selectedItem = (String)((JComboBox)e.getSource()).getSelectedItem();
           JComboBox sourceComboBox = (JComboBox)e.getSource(); 
           //find lookup key for this description
           Set lookupCollectionKeySet = lookupTableDataCollection.keySet();
           Iterator lookupCollectionIter = lookupCollectionKeySet.iterator();
           while (lookupCollectionIter.hasNext())
           {
               theCollectionKey = (String)lookupCollectionIter.next();
               HashMap lookupTableData = (HashMap)lookupTableDataCollection.get((Object)theCollectionKey);
               Set lookupKeySet = lookupTableData.keySet();
               Iterator lookupIter = lookupKeySet.iterator();
               while (lookupIter.hasNext())
               {
                   String theKey = (String)lookupIter.next();
                   String theValue = (String)lookupTableData.get((Object)theKey);
                   if (theValue.equalsIgnoreCase(selectedItem))
                   {
                       selectedKey = theKey;
                       keyFound = true;
                       
                       break;
                   }
               }
               if (keyFound == true) break;
           }
           //find location and index of combobox in dataview
           boolean fieldFound = false;
           String tableName = null;
           HashMap fieldList = null;
           Set tableKeySet = theDataView.tablesTextList.keySet();
           Iterator tableIter = tableKeySet.iterator();
           while (tableIter.hasNext())
           {
               tableName = (String)tableIter.next();
               fieldList = (HashMap)theDataView.tablesTextList.get(tableName);
               ArrayList fieldArrayList = null;
               fieldArrayList = (ArrayList)fieldList.get((Object)theCollectionKey);
               if (fieldArrayList != null)
               {
                   for (int arrayLoop = 0; arrayLoop < fieldArrayList.size(); arrayLoop++)
                   {
                      JComboBox cbBox = (JComboBox)fieldArrayList.get(arrayLoop);
                      if (cbBox.equals(sourceComboBox))
                      {
                          arrayIndex = arrayLoop;
                          fieldFound = true;
                          break;
                      }
                       
                   }
                   if (fieldFound == true) break;
               }
           }
           //check which fields are crosslinked and update them
           boolean crossKeyFound = false;
           String selectedValue = null;
           HashMap crossLinkCollection = (HashMap)theDataModel.getLookupTableCrossLinks();
           if (crossLinkCollection != null)
           {
               ArrayList crossLinkList = (ArrayList)crossLinkCollection.get(theCollectionKey);
               for (int loop = 0; loop < crossLinkList.size(); loop++) {
                   String crossLinkField = (String)crossLinkList.get(loop);
                   HashMap crossLookupTableData = (HashMap)lookupTableDataCollection.get((Object)crossLinkField);
                   
                   Set crossLookupTableKeySet = crossLookupTableData.keySet();
                   Iterator crossLookupIter = crossLookupTableKeySet.iterator();
                   while (crossLookupIter.hasNext()) {
                       String theKey = (String)crossLookupIter.next();
                       String theValue = (String)crossLookupTableData.get((Object)theKey);
                       if (theKey.equalsIgnoreCase(selectedKey)) {
                           selectedValue = theValue;
                           ArrayList fieldsArray = (ArrayList)fieldList.get((Object)crossLinkField);
                           JComboBox theCB = (JComboBox)fieldsArray.get(arrayIndex);
                           theCB.setSelectedItem(selectedValue);
                           theCB.transferFocus();
                           crossKeyFound = true;
                           break;
                       }
                   }//end while
               }//end for loop < crossLinkList.size()
           } //end if crossLinkCollection != null
       }// end actionperformed
   }// end class.

    public MTTableMVC1(String modelName, String viewType,String userName, char[] password) 
    {
        
        /** Creates new  Main Dialog Type Panel */
        MTTableMVCApp = this;
        initComponents(modelName, viewType, tabsTop, InfoManager.OS_VERSION, HEIGHT, WIDTH, userName, password);
        theScrollPane1 = typePanel;
    }

    public MTTableMVC1(String modelName, String viewType, int height, int width,String userName, char[] password) 
    {
        MTTableMVCApp = this;
        /** Creates new Main Dialog Type Panel */
        initComponents(modelName, viewType, tabsTop, InfoManager.OS_VERSION, height, width, userName, password);
        theScrollPane1 = typePanel;
    }

    public MTTableMVC1(String modelName, String viewType, int tabPlacement, int height, int width,String userName, char[] password) 
    {
        MTTableMVCApp = this;
        /** Creates new Main Dialog Type Panel */
        initComponents(modelName, viewType, tabPlacement, InfoManager.OS_VERSION, height, width, userName, password);
        theScrollPane1 = typePanel;
    }

    public void initComponents(String modelName, String viewType, int tabPlacement, String _theOS, int _height, int _width,String userName, char[] password)
    {
        try
        {
            if (InfoManager.OS_VERSION.equalsIgnoreCase("WINDOWS"))
            {
               imagePath = TARGET_VOLUME + "\\Mother Teresa\\images\\";
               docScanPath = InfoManager.WINDOWS_DOCUMENT_PATH;
            } else
            if (InfoManager.OS_VERSION.equalsIgnoreCase("UNIX"))
            {
               imagePath = "/usr/src/Mother Teresa/images/";
               docScanPath = InfoManager.UNIX_DOCUMENT_PATH;
            } else
            if (InfoManager.OS_VERSION.equalsIgnoreCase("WINXP"))
            {
               imagePath = TARGET_VOLUME + "\\Mother Teresa\\images\\";
               docScanPath = InfoManager.WINXP_DOCUMENT_PATH;
            }
            // Set Up DataModel by instantiating MTBusinessModel
            theDataModel = new MTTableDataModel(modelName, userName, password);
            theDataView = new DataView(viewType, tabPlacement, theDataModel, _height, _width);
            add(theDataView);
            CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
        }
        catch (MTException e)
        {
           JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }

     }
    
    public String getKeyValue()
    {
        return keyValue;
    }
    
    public boolean getUpdatedFlag()
    {
        return updatedFlag;
    }
   
}

//****************End of Inner Class*************************

  
  
//Start of Password inner class;
  
  public class PasswordPanel extends JPanel
                          implements ActionListener {

    private JInternalFrame controllingFrame; //needed for dialogs

    public PasswordPanel(JInternalFrame f) {
        //Use the default FlowLayout.
        controllingFrame = f;

        //Create everything.
        passwordField = new JPasswordField(10);
        passwordField.setEchoChar('#');
        passwordField.setActionCommand(OK);
        passwordField.addActionListener(this);

        JLabel label = new JLabel("Please enter your password: ");
        label.setLabelFor(passwordField);

        JComponent buttonPane = createButtonPanel();

        //Lay out everything.
        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        textPane.add(label);
        textPane.add(passwordField);
        Toolkit myToolkit = Toolkit.getDefaultToolkit();
        myToolkit.setLockingKeyState(KeyEvent.VK_CAPS_LOCK, false);
        add(textPane);
        add(buttonPane);
        resetFocus();
    }

    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0,1));
        JButton okButton = new JButton("OK");
        JButton helpButton = new JButton("Help");

        okButton.setActionCommand(OK);
        helpButton.setActionCommand(HELP);
        okButton.addActionListener(this);
        helpButton.addActionListener(this);

        p.add(okButton);
        p.add(helpButton);

        return p;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (OK.equals(cmd)) { //Process the password.
            char[] input = passwordField.getPassword();
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if (isPasswordCorrect(input)) {
                    //welcome all OK and activate buttons;
                    followUpPatientButton.setEnabled(true);
                    newPatientButton.setEnabled(true);
                    patientMenu.setEnabled(true);
                    toolsMenu.setEnabled(true);
                    //cursorField.setText("Login Successfull, Ready...");
                    desktop.remove(userLogonFrame);
                    this.setCursor(Cursor.getDefaultCursor());
                   desktop.updateUI();

                
            } else {
            this.setCursor(Cursor.getDefaultCursor());
                if (databaseDown == true)
                {
                   System.exit(0); 
                }
                logonTries++;
                if (logonTries == MAX_TRIES)
                {
                   System.exit(0);
                }
                JOptionPane.showMessageDialog(controllingFrame,
                    "Invalid password. (Try " + logonTries + " of " + MAX_TRIES +" left...)",
                    "Login Error Message",
                    JOptionPane.ERROR_MESSAGE);
            }
            //Zero out the possible password, for security.
            for (int i = 0; i < input.length; i++) {
                input[i] = 0;
            }

            passwordField.selectAll();
            resetFocus();
        } else { //The user has asked for help.
            JOptionPane.showMessageDialog(controllingFrame,
                "You have to type in the password assigned to\n"
              + "you by the systems administrator or person responsible\n"
              + "for your security requirements.\n"
              + "It is your responsibility to keep your password safe\n"
              + "If the security of your password has been breached\n"
              + "then a new password can be generated for you!");
        }
    }

    public String decrypCBC(String binHexMessage, byte[] dbKey, int binHexMessageSize)
    {
           BlowfishCBC bfcbc = new BlowfishCBC(dbKey, CBCIV_START);
           byte[] messbuf = new byte[binHexMessageSize];
           int buffSize = 50;
           
           int intResult = BinConverter.binHexToBytes(binHexMessage,messbuf,0,0,binHexMessageSize);
           bfcbc.setCBCIV(CBCIV_START);
           bfcbc.decrypt(messbuf);
           bfcbc.cleanUp();
           String result =  new String(messbuf).trim();
           return result;
    }

    private  boolean isPasswordCorrect(char[] input) {
        boolean isCorrect = false;
        try
        {
            DBAccess dbInstance = new DBAccess(HOST_NAME,DATABASE_NAME,"USER_LIST",dbUserName,dbPassWord,OS_VERSION);
            String theSqlQuery = "SELECT MAX(USER_CODE) FROM USER_LIST";
            String strMaxNumber = dbInstance.instSQLSingleItemFetch(theSqlQuery,  "MAX(USER_CODE)", "INT");
            String strNextNumber = String.valueOf(Integer.parseInt(strMaxNumber) + 1);
            String tmpString = String.copyValueOf(input);
            byte[] dbKey = tmpString.getBytes();
            tmpString = null;
            for (int loop = 1; loop < Integer.parseInt(strNextNumber); loop++)
            {
                //change to quad fetch to retrieve the user type code later if used.
                String sqlString = "SELECT USER_CODE , OWNER_NAME, USER_NAME FROM USER_LIST WHERE USER_CODE = " + loop;
                logonResultSet = dbInstance.instSQLTripleItemListFetch(sqlString, "USER_CODE", "INT", "OWNER_NAME", "MEDIUMTEXT","USER_NAME", "MEDIUMTEXT");
                if ((logonResultSet != null) && (logonResultSet.size() > 0))
                {
                  Set keySet = logonResultSet.keySet();
                    Iterator iter = keySet.iterator();
                    while (iter.hasNext())
                    {
                         String theKey = (String)iter.next();
                         ArrayList theKeyValues = (ArrayList)logonResultSet.get((Object)theKey);
                         String ownerName = (String)theKeyValues.get(0);
                         int fullOwnerNameSize = ownerName.length();
                         int encStringSize = fullOwnerNameSize >> 1;
                         String decryptOwnerString  = decrypCBC(ownerName, dbKey, encStringSize);
                         if (decryptOwnerString != null)
                         {
                             if (decryptOwnerString.equalsIgnoreCase(REGISTERED_USER) == true)
                             {
                                 //add username to textBox at Bottom.
                                 isCorrect = true;
                                 String tmpUserName = (String)theKeyValues.get(1);
                                 int fullUserNameSize = tmpUserName.length();
                                 int encUserNameSize = fullUserNameSize >> 1;
                                 String decryptUserNameString  = decrypCBC(tmpUserName, dbKey, encUserNameSize);
                                 if (decryptUserNameString != null)
                                 {
                                    MTUtils utils = new MTUtils();
                                    //utils.generateKeys(decryptUserNameString);
                                    userName = decryptUserNameString;
                                    userNameField.setText("Registered to-" + REGISTERED_USER + ", Logged In-" + decryptUserNameString);
                                    break;
                                 }
                                 
                             }
                         }
                    }  
                }
                if (isCorrect == true)
                {
                    break;
                }
                    
            }
              dbInstance.closeConnection();
              dbInstance = null;
              System.gc();

            return isCorrect;

        } 
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this,
                "No response from the database at " + HOST_NAME + ".\nPlease start the database or report the problem to the Systems Administrator.",
                    "Database server/connection down.",
                    JOptionPane.ERROR_MESSAGE);
            databaseDown = true;
            return false;
        }
    }

    //Must be called from the event-dispatching thread.
    public void resetFocus() {
        passwordField.requestFocusInWindow();
    }


}
//end of inner class;    


  public void clearStructures()
  {
    methodTable = new HashMap(5);
    systemicMethodTable = new HashMap(5);
    entMethodTable = new HashMap(5);
    familyHistoryMethodTable = new HashMap(5);
    examinationMethodTable = new HashMap(5);
    followUpExaminationMethodTable = new HashMap(5);
    occupationHistoryMethodTable = new HashMap(5);
    menuContainer = new HashMap(5);
    objectContainer = new HashMap(5);
    staticInfoContainer = new PatientInfoContainer(WINXP_IMAGE_PATH);
    systemicMenuArrayList = null;
    entMenuArrayList = null;
    familyHistoryMenuArrayList = null;
    examinationMenuArrayList = null;
    followUpExaminationMenuArrayList = null;

    occupationHistoryMenuArrayList = null;
    currentMenuItem = null; 
    clickedMenuItem = null;
    systemicClickedMenuItem = null;
    entClickedMenuItem = null;
    familyHistoryClickedMenuItem = null;
    occupationHistoryClickedMenuItem = null;
    examinationClickedMenuItem = null;
    followUpExaminationClickedMenuItem = null;
    theMenuItem = null;
    systemicMenuItem = null;
    entMenuItem = null;
    familyHistoryMenuItem = null;
    occupationHistoryMenuItem = null;
    examinationMenuItem = null;
    followUpExaminationMenuItem = null;
    updateButtonEvent = 0;
    arrayLoop = 0;
    staticInfoFrame = null;
    dateInfoFrame = null;
    systemicInternalFrame = null;
    agreementInternalFrame = null;
    captureExamEarNoseThroatFrame = null;
    captureExamUrogenitalFrame = null;
    familyHistoryInternalFrame = null;
    socialHistoryInternalFrame = null;
    occupationHistoryInternalFrame = null;
    treatmentHistoryInternalFrame = null;
    treatmentPlanInternalFrame = null;
    allergyHistoryInternalFrame = null;
    travelHistoryInternalFrame = null;
    examinationInternalFrame = null;
    followUpExaminationInternalFrame = null;
    newConstitutionalPanel = null; 
    newPalpitationsPanel = null; 
    newChestPainPanel = null; 
    newDyspneaPanel = null; 
    newClaudicationPanel = null; 
    newLegSwellingPanel = null; 
    newRespDyspneaPanel = null; 
    newRespChestPainPanel = null; 
    newRespChestTightnessPanel = null; 
    newRespCoughingPanel = null; 

    newAbdNauseaPanel = null; 
    newAbdVomitingPanel = null;
    newAbdDiarrheaPanel = null;
    newAbdConstipationPanel = null;
    newAbdominalPainPanel = null;
    newAbdTenesmusPanel = null;
    newAbdRectalProlapsePanel = null;
    newAbdHematemesisPanel = null;
    newAbdBloodPerRectumPanel = null;
    newAbdJaundicePanel = null;
    newMSJointPainsPanel = null;
    newMSJointSwellingsPanel = null;
    newMSMuclePainsPanel = null;
    newMSSpinalPainsPanel = null;
    newMSExtraArticularPanel = null;
    newMSJointDeformityPanel = null;
    newMSOtherPanel = null;    
    newCNSDizzySpellsPanel = null;    
    newCNSHeadachePanel = null;
    newCNSNeckStiffnessPanel = null;
    newCNSMotorDefecitPanel = null;
    newCNSSAndCNerveDefecitPanel = null;
    newCNSCerebellarDefecitPanel = null;
    newCNSGaitDefecitPanel = null;
    newCNSIncontinancePanel = null;
    newCNSSTMemoryLossPanel = null;
    newCNSLossOfConciousnessPanel = null;
    newCNSConvultionsPanel = null;
    newCNSHigherFunctionAbnormalitiesPanel = null;
    newENTSoreThroatPanel = null;
    newENTPostNasalDripPanel = null;    
    newENTDysphagiaPanel = null;    
    newENTHoarseVoicePanel = null;
    newENTThroatSwellingPanel = null;
    newENTSnoringPanel = null;
    newENTBlockedNosePanel = null;
    newENTSinusPainsPanel = null;
    newENTNoseBleedPanel = null;
    newENTSneezingPanel = null;
    newENTRhinorrhoeaPanel = null;
    newENTEarAchePanel = null;
    newENTEarDischargePanel = null;
    newENTDeafnessPanel = null;
    newENTVertigoPanel = null;
    newENTTinnitusPanel = null;
    newDermaRashPanel = null;
    newDermaUlcersPanel = null;
    newDermaVitiligoPanel = null;
    newDermaAllopeciaPanel = null;
    newDermaPetechiaePanel = null;
    newDermaPurperaPanel = null;
    newHemPetechiaePanel = null;
    newHemPurperaPanel = null;
    newEyesRedEyePanel = null;
    newEyesDischargePanel = null;
    newEyesPainPanel = null;
    newEyesDiplopiaPanel = null;
    newEyesLossOfVisionPanel = null;        
    newUGIncontinancePanel = null;
    newUGUrineRetentionPanel = null;
    newUGDysuriaPanel = null;
    newUGDischargePanel = null;
    newUGBleedingPanel = null;
    newEndoThyroidPanel = null;
    newENTEarPanel = null;
    newENTNosePanel = null;
    newENTParanasalPassagesPanel = null;
    newENTThroatPanel = null;
    newUROMalePanel = null;
    newUROFemalePanel = null;
    newUROUrinePanel = null;
    familyHistoryFatherPanel = null;
    familyHistoryMotherPanel = null;
    familyHistorySiblingsPanel = null;
    familyHistoryChildrenPanel = null;
    occupationHistoryCurrentPanel = null;
    occupationHistoryPreviousPanel = null;
    captureExamGeneralPanel = null;
    captureAllergyPanel = null;
    captureExamCardiovascularPanel = null;
    captureExamRespiratoryPanel = null;
    captureExamAbdomenPanel = null;
    captureExamMusculoskeletalPanel = null;
    captureExamCentralNervousSystemPanel = null;
    //captureExamEarNoseThroatPanel = null;
    captureExamDermatologicalPanel = null;
    captureExamHematologicalPanel = null;
    captureExamEyesPanel = null;
    captureExamUrogenitalPanel = null;
    captureExamEndocrinePanel = null;
    persInfoPanel = null;
    newComplaintPanel = null;
  }
  
  
  
    public static void main(String[] args)
    {
        new InfoManager();
    }
    
    private static TreeMap lookAndFeels = new TreeMap();
    static
    {
        lookAndFeels.put("Windows", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        //lookAndFeels.put("Mac", "com.sun.java.swing.plaf.mac.MacLookAndFeel");
        lookAndFeels.put("Motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        //lookAndFeels.put("Metal", "com.sun.java.swing.plaf.metal.MetalLookAndFeel");
        lookAndFeels.put("CrossPlatform", UIManager.getCrossPlatformLookAndFeelClassName());
        lookAndFeels.put("System", UIManager.getSystemLookAndFeelClassName());
        lookAndFeels.put("Kunststof", "com.incors.plaf.kunststoff.KunststoffLookAndFeel");
        lookAndFeels.put("GTK+", "org.gtk.java.swing.plaf.gtk.GtkLookAndFeel");
                       //lookAndFeels.put("Alloy (Incors)", "com.incors.plaf.alloy.AlloyLookAndFeel");
        lookAndFeels.put("CompiereLook", "org.compiere.plaf.CompiereLookAndFeel");
        lookAndFeels.put("3DLF", "swing.addon.plaf.threeD.ThreeDLookAndFeel");
        //lookAndFeels.put("NextStep", "nextlf.plaf.NextLookAndFeel");
        //lookAndFeels.put("TeknoLust", "com.teknolust.plaf.teknolust.TeknolustLookAndFeel");
        //lookAndFeels.put("Metouia", "net.sourceforge.mlf.metouia.MetouiaLookAndFeel");
        //lookAndFeels.put("Oyoaha", "com.oyoaha.swing.plaf.oyoaha.OyoahaLookAndFeel");
    }
}
