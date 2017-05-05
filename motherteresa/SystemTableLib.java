package motherteresa;

import java.util.*;
import java.util.Collection;

/**
 * <p>
 * 
 * @author Hein Badenhorst 
 * </p>
 */
public class SystemTableLib {

  ///////////////////////////////////////
  // attributes

    private ArrayList columnData;
    private ArrayList columnList;
    private ArrayList filterList;
    private ArrayList tableFields;
    private ArrayList tableNames;
    private ArrayList tableDefArray;
    private ArrayList tokenList;

    private HashMap tableData;
    private HashMap tableDef;

    private IniFile config = new IniFile();

    private int itemCount = 0;
    private int tableDefIndex = 0;
    private int tableNameIndex = 0;
    private int tokenCount = 0;
    private int tableDefArrayIndex = 0;
    private int columnDataIndex = 0;

    private String tableName = "";
    private String fieldDetailString = "";
    private String theToken = "";
    private String theFieldName = "";

    private boolean iniFilePresent = false;
    
    public  SystemTableLib(final java.lang.String systemName, final String _myOS) 
    {
        if (systemName.equalsIgnoreCase("medibase"))
        {
            /*    
            Field Attribute List 
            Position 1 = Field Name
            Position 2 = Field Alias
            Position 3 = Field Key Type ie. Primary, Foreign, FIELD
            Position 4 = Field Data Type ie. INT, VARCHAR
            Position 5 = Field Length
            Position 6 = Field Active Flag  
            Position 7 = Field Lock Flag
            Position 8 = Field Display Flag 
            Position 9 = Field Highlight Flag 
            Position 10 = Label Display Flag 
            */    

            tableDef = new HashMap();
            tableFields = new ArrayList(20);
            tableNames = new ArrayList(50);

            tableFields.clear();
            tableFields.add((Object)"VITAL_SIGN_CODE,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"VITAL_SIGN_DATE,Date of Vital Signs,DATE_TIME_FIELD,DATE,10,1,0,1,0,1");
            tableFields.add((Object)"BLOOD_PRESSURE,Blood Pressure,BP_FIELD,VARCHAR,7,1,0,1,0,1");
            tableFields.add((Object)"HEART_RATE,Heart Rate,FIELD,VARCHAR,3,1,0,1,0,1");
            tableFields.add((Object)"TEMPERATURE,Temperature,FIELD,VARCHAR,3,1,0,1,0,1");
            tableFields.add((Object)"RESPIRATORY_RATE,Respiratory Rate,FIELD,VARCHAR,2,1,0,1,0,1");
            tableDef.put((Object)"PATIENT_VITAL_SIGNS",(Object)tableFields.clone());
            tableNames.add((Object)"PATIENT_VITAL_SIGNS");
            tableFields.clear();
            tableFields.add((Object)"LANGUAGE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LANGUAGE_DESCRIPTION,Language Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LANGUAGE",(Object)tableFields.clone());
            tableNames.add((Object)"LANGUAGE");
            tableFields.clear();
            tableFields.add((Object)"EMPLOYER_CODE,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"EMPLOYER_NAME,Employer Name,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"EMPLOYER_ADDRESS_1,Employer Address 1,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"EMPLOYER_ADDRESS_2,Employer Address 2,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"EMPLOYER_ADDRESS_3,Employer Address 3,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"EMPLOYER_AREA_CODE,Employer Area Code,AREA_FIELD,VARCHAR,6,1,0,1,0,1");
            tableFields.add((Object)"EMPLOYER_PHONE_NUMBER,Employer Phone Number,PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"EMPLOYER_FAX_NUMBER,Employer Fax Number,PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"EMPLOYER_E_MAIL_ADDRESS,Employer E-Mail Address,FIELD,VARCHAR,40,1,0,1,0,1");
            tableDef.put((Object)"EMPLOYER",(Object)tableFields.clone());
            tableNames.add((Object)"EMPLOYER");
            tableFields.clear();
            tableFields.add((Object)"SYMPTOM_SEQUENCE_NUMBER,Code,PRIMARY,INT,11,1,1,1,0,0");
            tableFields.add((Object)"SYMPTOM_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"SYMPTOM_NAME,Symptom Name,FIELD,MEDIUMTEXT,40,1,0,1,0,1");
            tableFields.add((Object)"SYMPTOM_DESCRIPTION,Symptom Description,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"SYSTEM_CATEGORY_MAP,System Categories,FOREIGN_MAP,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"RISKS,Symptom Risks,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"NORMAL_VALUES,Symptom Normal Values,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"DATA_SOURCE_CODE,Data Source,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYMPTOMS",(Object)tableFields.clone());
            tableNames.add((Object)"SYMPTOMS");
            tableFields.clear();
            tableFields.add((Object)"DISEASE_SEQUENCE_NUMBER,Code,PRIMARY,INT,11,1,1,1,0,0");
            tableFields.add((Object)"DISEASE_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"DISEASE_NAME,Disease Name,FIELD,VARCHAR,40,1,0,1,0,1");
            tableFields.add((Object)"DISEASE_DESCRIPTION,Disease Description,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"SYSTEM_CATEGORY_CODE,System Category,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PREVENTION,Disease Prevention,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"SYMPTOMS,Disease Symptoms,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"TESTS,Disease Tests,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT,Disease Treatment,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"PROGNOSIS,Disease Prognosis,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"COMPLICATIONS,Disease Complications,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"DATA_SOURCE_CODE,Data Source,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"DISEASES",(Object)tableFields.clone());
            tableNames.add((Object)"DISEASES");
            tableFields.clear();
            tableFields.add((Object)"SURGERY_SEQUENCE_NUMBER,Code,PRIMARY,INT,11,1,1,1,0,0");
            tableFields.add((Object)"SURGERY_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"SURGERY_NAME,Surgery Name,FIELD,MEDIUMTEXT,40,1,0,1,0,1");
            tableFields.add((Object)"SURGERY_ALTERNATE_NAMES,Surgery Alternate Names,FIELD,MEDIUMTEXT,80,1,0,1,0,1");
            tableFields.add((Object)"SURGERY_DESCRIPTION,Surgery Description,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"SYSTEM_CATEGORY_CODE,System Category,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"INDICATIONS,Surgery Indications,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"EXPECTATIONS,Surgery Expectations,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"CONVALESCENCE,Surgery Convalescence,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"RISKS,Surgery Risks,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"DATA_SOURCE_CODE,Data Source,FOREIGN,INT,11,1,0,1,0,1");

            tableDef.put((Object)"SURGERY",(Object)tableFields.clone());
            tableNames.add((Object)"SURGERY");
            tableFields.clear();
            tableFields.add((Object)"TEST_SEQUENCE_NUMBER,Code,PRIMARY,INT,11,1,1,1,0,0");
            tableFields.add((Object)"TEST_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"TEST_NAME,Test Name,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"TEST_DESCRIPTION,Test Description,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"RISKS,Test Risks,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"NORMAL_VALUES,Test Normal Values,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ABNORMAL_VALUES,Test Abnormal Values,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"SPECIAL_CONSIDERATIONS,Test Considerations,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"TESTS",(Object)tableFields.clone());
            tableNames.add((Object)"TESTS");
            tableFields.clear();
            tableFields.add((Object)"HEALTH_RISK_INDEX,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RISK_DESCRIPTION,Health Risk,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RISK_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RISK_TYPE");
            tableFields.clear();
            tableFields.add((Object)"RELATION_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RELATION_DESCRIPTION,Relation Description,FIELD,VARCHAR,50,1,0,1,0,1");
            tableDef.put((Object)"RELATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RELATION_TYPE");
            tableFields.clear();
            tableFields.add((Object)"PATIENT_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PATIENT_TYPE_DESCRIPTION,Patient Type Description,FIELD,VARCHAR,50,1,0,1,0,1");
            tableDef.put((Object)"PATIENT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PATIENT_TYPE");
            tableFields.clear();
            tableFields.add((Object)"MEDICAL_AID_CODE,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"MEDICAL_AID_NAME,Medical Aid,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"MA_ADDRESS_1,Address 1,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"MA_ADDRESS_2,Address 2,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"MA_ADDRESS_3,Address 3,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"MA_AREA_CODE,Area Code,AREA_FIELD,VARCHAR,6,1,0,1,0,1");
            tableFields.add((Object)"MA_TELEPHONE_NUMBER,Phone Number,PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"MA_FAX_NUMBER,Fax Number,PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"MA_QLINK_NUMBER,Q-Link Number,PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"MA_E_MAIL_ADDRESS,E-Mail Address,FIELD,VARCHAR,40,1,0,1,0,1");
            tableDef.put((Object)"MEDICAL_AID_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"MEDICAL_AID_TYPE");
            tableFields.clear();
            tableFields.add((Object)"SPECIALIST_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SPECIALIST_DESCRIPTION,Specialist Type,FIELD,VARCHAR,20,1,0,1,0,1");
            tableDef.put((Object)"SPECIALIST_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"SPECIALIST_TYPE");
            tableFields.clear();
            tableFields.add((Object)"LIST_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SPECIALIST_DESCRIPTION,Specialist Type,FIELD,VARCHAR,20,1,0,1,0,1");
            tableDef.put((Object)"SPECIALIST_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"SPECIALIST_TYPE");
            tableFields.clear();
            tableFields.add((Object)"FOOD_SOURCE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"FOOD_SOURCE_DESCRIPTION,Food Source,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"FOOD_SOURCE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"FOOD_SOURCE_TYPE");
            tableFields.clear();
            tableFields.add((Object)"BEVARAGE_SOURCE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"BEVARAGE_SOURCE_DESCRIPTION,Beverage Source,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"BEVARAGE_SOURCE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"BEVARAGE_SOURCE_TYPE");
            tableFields.clear();
            tableFields.add((Object)"STATE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"STATE_DESCRIPTION,Appointment State,FIELD,VARCHAR,20,1,0,1,0,1");
            tableDef.put((Object)"APPOINTMENT_STATE",(Object)tableFields.clone());
            tableNames.add((Object)"APPOINTMENT_STATE");
            tableFields.clear();
            tableFields.add((Object)"PHYSICIAN_INTERNAL_REF_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"INITIALS,Initials,FIELD,VARCHAR,6,1,0,1,0,1");
            tableFields.add((Object)"NAME,Name,FIELD,VARCHAR,20,1,0,1,0,1");
            tableFields.add((Object)"SURNAME,Surname,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ADDRESS_1,Address 1,FIELD,VARCHAR,50,1,0,1,0,1");
            tableFields.add((Object)"ADDRESS_2,Address 2,FIELD,VARCHAR,50,1,0,1,0,1");
            tableFields.add((Object)"ADDRESS_3,Address 3,FIELD,VARCHAR,50,1,0,1,0,1");
            tableFields.add((Object)"AREA_CODE,Area Code,AREA_FIELD,VARCHAR,6,1,0,1,0,1");
            tableFields.add((Object)"PO_BOX_NUMBER,P.O. Box Number,FIELD,VARCHAR,20,1,0,1,0,1");
            tableFields.add((Object)"PO_ADDRESS_1,Postal Address 1,FIELD,VARCHAR,50,1,0,1,0,1");
            tableFields.add((Object)"PO_ADDRESS_2,Postal Address 1,FIELD,VARCHAR,50,1,0,1,0,1");
            tableFields.add((Object)"TEL_NUMBER,Telephone Number,PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"FAX_NUMBER,Fax Number,PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"PAGER_NUMBER,Pager Number,FIELD,VARCHAR,20,1,0,1,0,1");
            tableFields.add((Object)"CELL_NUMBER,Cell Number,PHONE_FIELD,VARCHAR,12,1,0,1,0,1");
            tableFields.add((Object)"E_MAIL_ADDRESS,E-Mail Address,FIELD,VARCHAR,40,1,0,1,0,1");
            tableDef.put((Object)"PHYSICIAN",(Object)tableFields.clone());
            tableNames.add((Object)"PHYSICIAN");
            tableFields.clear();
            tableFields.add((Object)"BODY_LOCATION_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LOCATION_DESCRIPTION,Body Location,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"BODY_LOCATION",(Object)tableFields.clone());
            tableNames.add((Object)"BODY_LOCATION");
            tableFields.clear();
            tableFields.add((Object)"OCCUPATION_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"OCCUPATION_DESCRIPTION,Occupation Description,FIELD,VARCHAR,40,1,0,1,0,1");
            tableFields.add((Object)"HEALTH_RISK_INDEX,Risk Index,FIELD,INTEGER,11,1,0,1,0,1");
            tableDef.put((Object)"OCCUPATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"OCCUPATION_TYPE");
            tableFields.clear();
            tableFields.add((Object)"TIME_UNITS_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TIME_UNITS_DESCRIPTION,Time Units,FIELD,VARCHAR,20,1,0,1,0,1");
            tableFields.add((Object)"TIME_UNITS_HOUR_FACTOR,Time Units Per Hour,FIELD,FLOAT,11,1,0,1,0,1");
            tableDef.put((Object)"TIME_UNITS",(Object)tableFields.clone());
            tableNames.add((Object)"TIME_UNITS");
            tableFields.clear();
            tableFields.add((Object)"DOSAGE_UNIT_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"DOSAGE_DESCRIPTION,Dosage Description,FIELD,VARCHAR,20,1,0,1,0,1");
            tableDef.put((Object)"DOSAGE_UNIT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"DOSAGE_UNIT_TYPE");
            tableFields.clear();
            tableFields.add((Object)"RESIDENTIAL_MODE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RESIDENTIAL_MODE_DESCRIPTION,Residential Mode,FIELD,VARCHAR,20,1,0,1,0,1");
            tableDef.put((Object)"RESIDENTIAL_MODE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RESIDENTIAL_MODE_TYPE");
            tableFields.clear();
            tableFields.add((Object)"ALLERGY_TYPICAL_REACTION_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"REACTION_DESCRIPTION,Allergic Reaction Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"ALLERGY_REACTION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"ALLERGY_REACTION_TYPE");
            tableFields.clear();
            tableFields.add((Object)"MEANS_OF_TRAVEL_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"MEANS_OF_TRAVEL_DESCRIPTION,Means Of Travel,FIELD,VARCHAR,20,1,0,1,0,1");
            tableDef.put((Object)"MEANS_OF_TRAVEL_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"MEANS_OF_TRAVEL_TYPE");
            tableFields.clear();
            tableFields.add((Object)"TITLE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TITLE_DESCRIPTION,Title Description,FIELD,VARCHAR,15,1,0,1,0,1");
            tableDef.put((Object)"TITLE",(Object)tableFields.clone());
            tableNames.add((Object)"TITLE");
            tableFields.clear();
            tableFields.add((Object)"INSTITUTION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"INSTITUTION_DESCRIPTION,Institution Description,FIELD,VARCHAR,20,1,0,1,0,1");
            tableDef.put((Object)"TEST_INSTITUTION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"TEST_INSTITUTION_TYPE");
            tableFields.clear();
            tableFields.add((Object)"MAP_SOURCE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"DESCRIPTION,Map Source,FIELD,VARCHAR,20,1,0,1,0,1");
            tableDef.put((Object)"MAP_SOURCE",(Object)tableFields.clone());
            tableNames.add((Object)"MAP_SOURCE");
            tableFields.clear();
            tableFields.add((Object)"EXERCISE_TOLERANCE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXERCISE_TOLERANCE_DESCRIPTION,Exercise Tolerance,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"EXERCISE_TOLERANCE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"EXERCISE_TOLERANCE_TYPE");
            tableFields.clear();
            tableFields.add((Object)"APPETITE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"APPETITE_DESCRIPTION,Appetite Condition,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"APPETITE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"APPETITE_TYPE");
            tableFields.clear();
            tableFields.add((Object)"CONDITION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"CONDITION_DESCRIPTION,Patient Condition,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"CONDITION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"CONDITION_TYPE");
            tableFields.clear();
            tableFields.add((Object)"DIAGNOSES_MAIN_CAUSE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"DIAGNOSES_CAUSE_DESCRIPTION,Diagnoses Cause,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"DIAGNOSES_CAUSE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"DIAGNOSES_CAUSE_TYPE");
            tableFields.clear();
            tableFields.add((Object)"POISONING_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"POISON_NAME,Poison Name,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"POISON_DATE,Poison Date,DATE_FIELD,DATE,10,1,0,1,0,1");
            tableFields.add((Object)"POISONING_ICD_CODE,ICD Code,FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"POISONING_DETAILS,Poisoning Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"POISONING_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"POISONING_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"CONDITION_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"CONDITION_TYPE_CODE,Patient Condition,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"CONDITION_DATE,Condition Date,DATE_TIME_FIELD,DATE,10,1,0,1,0,1");
            tableFields.add((Object)"EXERCISE_TOLERANCE_TYPE_CODE,Exercise Tolerance,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"APPETITE_TYPE_CODE,Appetite,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"CONDITION_DETAILS,Condition Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"CONDITION_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"CONDITION_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"SPECIALIST_INTERNAL_REF_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"SPECIALIST_CODE,Speciality,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"INITIALS,Initials,FIELD,VARCHAR,6,1,0,1,0,1");
            tableFields.add((Object)"NAME,Name,FIELD,VARCHAR,20,1,0,1,0,1");
            tableFields.add((Object)"SURNAME,Surname,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ADDRESS_1,Address 1,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ADDRESS_2,Address 2,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ADDRESS_3,Address 3,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"AREA_CODE,Area Code,AREA_FIELD,VARCHAR,6,1,0,1,0,1");
            tableFields.add((Object)"PO_ADDRESS_1,P.O. Box Number,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"PO_ADDRESS_2,Postal Address 1,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"PO_ADDRESS_3,Postal Address 2,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"PO_AREA_CODE,Postal Area Code,AREA_FIELD,VARCHAR,6,1,0,1,0,1");
            tableFields.add((Object)"TEL_NUMBER,Telephone Number,PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"FAX_NUMBER,Fax Number,PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"PAGER_NUMBER,Pager Number,FIELD,VARCHAR,20,1,0,1,0,1");
            tableFields.add((Object)"CELL_NUMBER,Cell Number,PHONE_FIELD,VARCHAR,12,1,0,1,0,1");
            tableFields.add((Object)"SPEC_E_MAIL_ADDRESS,E-Mail Address,FIELD,VARCHAR,40,1,0,1,0,1");
            tableDef.put((Object)"SPECIALIST",(Object)tableFields.clone());
            tableNames.add((Object)"SPECIALIST");
            tableFields.clear();
            tableFields.add((Object)"TRAVEL_SEQ_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"RESIDENTIAL_MODE_CODE,Residential Mode,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"MEANS_OF_TRAVEL_CODE,Means of Travel,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TRAVEL_DATE,Departure Date,DATE_FIELD,DATE,10,1,0,1,0,1");
            tableFields.add((Object)"TRAVEL_DESTINATION,Travel Destination,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"TRAVEL_OBJECTIVE,Travel Objective,FIELD,VARCHAR,30,1,0,1,0,1");
            //tableFields.add((Object)"MODE_OF_RESIDENCE,Mode Of Residence,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ARRIVAL_DATE,Arrival Date,DATE_FIELD,DATE,10,1,0,1,0,1");
            tableFields.add((Object)"DEPARTURE_DATE,Return Date,DATE_FIELD,DATE,10,1,0,1,0,1");
            tableFields.add((Object)"PROPHYLACTIC_DETAILS,Prophylactic Details,FIELD,TEXT,200,1,0,1,0,1");
            tableFields.add((Object)"FOOD_SOURCE_CODE,Food Source,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"BEVARAGE_SOURCE_CODE,Beverage Source,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"TRAVEL_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"TRAVEL_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"ACCOUNT_NUMBER,Account Number,PRIMARY,INT,11,1,1,1,0,0");
            tableFields.add((Object)"LANGUAGE_CODE,Language,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"GENDER_TYPE_CODE,Gender,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PATIENT_TYPE_CODE,Patient Type,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"MEDICAL_AID_CODE,Medical Aid,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TITLE_CODE,Title,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"INITIALS,Initials,INITIAL_FIELD,VARCHAR,6,1,0,1,0,1");
            tableFields.add((Object)"FIRST_NAME,First Name,FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"SURNAME,Surname,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"IDENTITY_NUMBER,Identity Number,ID_FIELD,VARCHAR,13,1,0,1,0,1");
            tableFields.add((Object)"BIRTH_DAY,Birth Day,BIRTH_DAY_FIELD,VARCHAR,5,1,0,1,0,1");
            tableFields.add((Object)"MEDICAL_AID_NUMBER,Medical Aid Number,FIELD,VARCHAR,20,1,0,1,0,1");
            tableFields.add((Object)"MEDICAL_AID_PRIMARY_MEMBER,Medical Aid Primary Member,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILE_NUMBER,File Reference Number,FIELD,VARCHAR,10,1,0,1,0,1");
            tableFields.add((Object)"HOME_PHONE_NUMBER,Phone Number(Home),PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"CELL_PHONE_NUMBER,Phone Number(Cell),PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"WORK_PHONE_NUMBER,Phone Number(Work),PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"E_MAIL_ADDRESS,E-Mail Address,FIELD,VARCHAR,40,1,0,1,0,1");
            tableFields.add((Object)"DEBTORS_ACCOUNT_NUMBER,Debtors Account Number,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"STREET_ADDRESS_1,Street Addres 1,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"STREET_ADDRESS_2,Street Addres 2,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"STREET_ADDRESS_3,Street Addres 3,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"AREA_CODE,Area Code,AREA_FIELD,VARCHAR,6,1,0,1,0,1");
            tableFields.add((Object)"POSTAL_ADDRESS_1,Postal Addres 1,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"POSTAL_ADDRESS_2,Postal Addres 2,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"POSTAL_ADDRESS_3,Postal Addres 3,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"AREA_CODE_PA,PO Area Code,AREA_FIELD,VARCHAR,6,1,0,1,0,1");
            tableDef.put((Object)"PATIENT_PHYSICAL",(Object)tableFields.clone());
            tableNames.add((Object)"PATIENT_PHYSICAL");
            tableFields.clear();
            tableFields.add((Object)"HISTORY_SEQ_NUMBER,Sequence Number,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HIS_DATE_TIME,Time Stamp,DATE_TIME_FIELD,DATETIME,19,1,0,1,0,1");
            tableFields.add((Object)"VITAL_SIGN_CODE,Vital Sign Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"COMPLAINT_NUMBER,Symptom Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_CARDIOVASCULAR_NUMBER,Cardiovascular Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_ABDOMINAL_NUMBER,Abdominal Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_RESPIRATORY_NUMBER,Respiratory Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_MUSCULOSKELETAL_NUMBER,Musculoskeletal Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_CNS_NUMBER,CNS Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_ENT_NUMBER,ENT Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_DERMATOLOGICAL_NUMBER,Dermatological Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_HEMATOLOGICAL_NUMBER,Hematological Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_EYES_NUMBER,Eyes Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_UROGENITAL_NUMBER,Urogenital Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_THYROID_NUMBER,Thyroid Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_ADRENAL_NUMBER,Adrenal Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_PITUITARY_NUMBER,Pituitary Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_PARATHYROID_NUMBER,Parathyroid Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_PANCREAS_NUMBER,Pancreas Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEMIC_GONADS_NUMBER,Gonads Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PREVIOUS_MEDICAL_HISTORY_NUMBER,PMH Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PREVIOUS_SURGICAL_HISTORY_NUMBER,PSH Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"FAMILY_HISTORY_NUMBER,Family History Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SOCIAL_HISTORY_NUMBER,Social History Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_CARDIOVASCULAR_NUMBER,Examination Cardivascular Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_GENERAL_NUMBER,Examination General Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_RESPIRATORY_NUMBER,Examination Respiratory Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_ABDOMINAL_NUMBER,Examination Abdominal Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_MUSCULOSKELETAL_NUM,Examination Musculoskeletal Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_CNS_NUMBER,Examination CNS Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_ENT_EAR_NUMBER,Examination ENT Ear Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_ENT_NOSE_NUMBER,Examination ENT Nose Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_ENT_PARANASAL_SINUSES_NUMBER,Examination ENT PNS Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_ENT_THROAT_NUMBER,Examination ENT Throat Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_DERMATOLOGICAL_NUMBER,Examination Dermatological Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_HEMATOLOGICAL_NUMBER,Examination Hematolocical Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_EYES_NUMBER,Examination Eyes Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_UROGENITAL_MALE_NUMBER,Examination Urogenital Male Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_UROGENITAL_FEMALE_NUMBER,Examination Urogenital Female Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_UROGENITAL_URINE_NUMBER,Examination Urogenital Urine Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAMINATION_ENDOCRINE_NUMBER,Examination Endocrine Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"MEDICAL_ASSESMENT_NUMBER,Assesment Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"CURRENT_OCCUPATION_NUMBER,Current Occupation Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PREVIOUS_OCCUPATION_NUMBER,Previous Occupation Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"CONSTITUTIONAL_SYMPTOM_NUMBER,Constitutional Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PATIENT_ALLERGIES_NUMBER,Allergies Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PATIENT_TRAVEL_HISTORY_NUMBER,Travel History Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"DIAGNOSES_NUMBER,Diagnoses Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TEST_CODE,Test Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PRESCRIPTION_NUMBER,Prescription Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SURGERY_NUMBER,Surgery Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TREATMENT_NUMBER,Treatment Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PLAN_TREATMENT_NUMBER,Plan Treatment Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PATIENT_TREATMENT_NUMBER,Treatment Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"INJURY_NUMBER,Injury Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SPECIALIST_REFERRAL_NUMBER,Specialist Referral Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TEST_REFERRAL_NUMBER,Test Referral Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"DRUG_NUMBER,Drug Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"ALLERGY_HISTORY_CODE,Allergy Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"IMMUNIZATION_NUMBER,Immunization Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"POISONING_NUMBER,Poisoning Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"OCCUPATION_NUMBER,Occupation,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TRAVEL_SEQ_NUMBER,Travel Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"CONDITION_NUMBER,Condition Code,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"ACCOUNT_NUMBER,Account Number,FOREIGN,INT,11,1,1,0,0,1");
            tableFields.add((Object)"DELETED,Deleted,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"PATIENT_MEDICAL_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"PATIENT_MEDICAL_HISTORY");

            tableFields.clear();
            tableFields.add((Object)"INSTITUTION_SEQ_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"INSTITUTION_TYPE_CODE,Facility Type,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"NAME,Institution Name,FIELD,VARCHAR,20,1,0,1,0,1");
            tableFields.add((Object)"ADDRESS_1,Address 1,FIELD,VARCHAR,50,1,0,1,0,1");
            tableFields.add((Object)"ADDRESS_2,Address 2,FIELD,VARCHAR,50,1,0,1,0,1");
            tableFields.add((Object)"ADDRESS_3,Address 3,FIELD,VARCHAR,50,1,0,1,0,1");
            tableFields.add((Object)"AREA_CODE,Area Code,AREA_FIELD,VARCHAR,6,1,0,1,0,1");
            tableFields.add((Object)"PO_BOX_NUMBER,P.O. Box Number,FIELD,VARCHAR,50,1,0,1,0,1");
            tableFields.add((Object)"PO_ADDRESS_1,Postal Address 1,FIELD,VARCHAR,50,1,0,1,0,1");
            tableFields.add((Object)"PO_ADDRESS_2,Postal Address 2,FIELD,VARCHAR,50,1,0,1,0,1");
            tableFields.add((Object)"PO_AREA_CODE,Postal Area Code,AREA_FIELD,VARCHAR,6,1,0,1,0,1");
            tableFields.add((Object)"TEL_NUMBER,Telephone Number,PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"FAX_NUMBER,Fax Number,PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            //tableFields.add((Object)"PAGER_NUMBER,Pager Number,FIELD,VARCHAR,20,1,0,1,0,1");
            tableFields.add((Object)"CELL_NUMBER,Cell Number,PHONE_FIELD,VARCHAR,15,1,0,1,0,1");
            tableFields.add((Object)"E_MAIL_ADDRESS,E-Mail Address,FIELD,VARCHAR,40,1,0,1,0,1");
            tableDef.put((Object)"TEST_INSTITUTION",(Object)tableFields.clone());
            tableNames.add((Object)"TEST_INSTITUTION");
            tableFields.clear();
            tableFields.add((Object)"DIAGNOSES_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"DIAGNOSES_DATE_TIME,Diagnoses Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"DISEASE_SEQUENCE_NUMBER,Diagnoses,FOREIGN,INT,30,1,0,1,0,1");
            tableFields.add((Object)"DISEASE_ICD_CODE,ICD Code,FOREIGN,INT,9,1,0,1,0,1");
            tableFields.add((Object)"DIAGNOSES_MAIN_CAUSE_CODE,Main Cause,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DIAGNOSES_DETAILS,Diagnoses Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"DIAGNOSES_FILE_REFERENCE,Document Scans,DOCUMENT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"DIAGNOSES_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"DIAGNOSES_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"TEST_CODE,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"TEST_DATE_TIME,Test Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"TEST_NAME,Test,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"TEST_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"TEST_DETAILS,Test Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"TEST_FILE_REFERENCE,Document Scans,DOCUMENT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"TEST_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"TEST_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"PRESCRIPTION_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"PRESCRIPTION_DATE_TIME,Prescription Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"PRESCRIPTION_NAME,Prescription,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"PRESCRIPTION_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"PRESCRIPTION_DETAILS,Prescription Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"PRESCRIPTION_DOCS,Document Scans,DOCUMENT_AREA_FIELD,TEXT,500,1,0,1,0,1");
            tableDef.put((Object)"PRESCRIPTION_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"PRESCRIPTION_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"SURGERY_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"SURGERY_DATE_TIME,Surgery Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"SURGERY_NAME,Surgery,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"SURGERY_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"SURGERY_DETAILS,Surgery Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"SURGERY_DOCS,Document Scans,DOCUMENT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"SURGERY_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"SURGERY_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"INJURY_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"INJURY_DATE_TIME,Injury Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"INJURY_NAME,Injury,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"INJURY_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"INJURY_AT_WORK,On Duty Injury,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"INJURY_DETAILS,Injury Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"INJURY_DOCS,Document Scans,DOCUMENT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"INJURY_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"INJURY_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"TREATMENT_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"TREATMENT_DATE_TIME,Treatment Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_NAME,Treatment,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"DOSAGE_UNIT_CODE,Dosage,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_DETAILS,Treatment Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_DOCS,Document Scans,DOCUMENT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_SCHEDULE,Treatment Schedule,FIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_START,Treatment Start Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_END,Treatment End Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"REPEATS_PER_DAY,Daily Repeats,FIELD,INT,11,1,0,1,0,1");
            tableDef.put((Object)"TREATMENT_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"TREATMENT_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"SPECIALIST_REFERRAL_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"REFERRAL_DATE_TIME,Referral Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"SPECIALIST_INTERNAL_REF_NUMBER,Specialist,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"REFERRAL_DETAILS,Referral Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"REFERRAL_DOCS,Document Scans,DOCUMENT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"REFERRAL_TO_FROM,Referred To/By,TO_BY_BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"SPECIALIST_REFERRAL_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"SPECIALIST_REFERRAL_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"TEST_REFERRAL_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"REFERRAL_DATE_TIME,Referral Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"INSTITUTION_SEQ_NUMBER,Institution,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TEST_REFERRAL_DETAILS,Test Referral Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"TEST_REFERRAL_DOCS,Document Scans,DOCUMENT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"TEST_REFERRAL_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"TEST_REFERRAL_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"DRUG_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"DRUG_NAME,Name,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"DRUG_DATE_TIME,Report Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"DOSAGE_UNIT_CODE,Dosage Unit,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DOSAGE,Dosage Amount,FIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TIME_UNITS_CODE,Frequency,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DRUG_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"DRUG_DESCRIPTION,Usage Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"COMMENCEMENT_DATE,Start Date,DATE_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"TERMINATION_DATE,End Date,DATE_FIELD,DATETIME,20,1,0,1,0,1");
            tableDef.put((Object)"HABITUAL_DRUG_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"HABITUAL_DRUG_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"ALLERGY_HISTORY_CODE,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"ALLERGY_DATE_TIME,Allergy Discovery Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"ALLERGY_NAME,Allergy,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ALLERGY_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"ALLERGY_DETAILS,Allergy Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ALLERGY_TYPICAL_REACTION_CODE,Typical Reaction,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ALLERGY_ATYPICAL_DESCRIPTION,Atypical Description,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"ALLERGY_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"ALLERGY_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"IMMUNIZATION_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"IMMUNIZATION_DATE_TIME,Immunization Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"IMMUNIZATION_NAME,Immunization,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"IMMUNIZATION_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"IMMUNIZATION_DESCRIPTION,Immunization Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"IMMUNIZATION_DOCS,Document Scans,DOCUMENT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"IMMUNIZATION_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"IMMUNIZATION_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"SYMPTOM_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYMPTOM_SEQUENCE_NUMBER,Symptom Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"SYMPTOM_SYNONYM_NUMBER,Symptom Map,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYMPTOM_SYNONYMS",(Object)tableFields.clone());
            tableNames.add((Object)"SYMPTOM_SYNONYMS");
            tableFields.clear();
            tableFields.add((Object)"DISEASE_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"DISEASE_SEQUENCE_NUMBER,Disease Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DISEASE_SYNONYM_NUMBER,Disease Map,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"DISEASE_SYNONYMS",(Object)tableFields.clone());
            tableNames.add((Object)"DISEASE_SYNONYMS");
            tableFields.clear();
            tableFields.add((Object)"TREATMENT_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TREATMENT_SEQUENCE_NUMBER,Treatment Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_SYNONYM_NUMBER,Treatment Map,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"TREATMENT_SYNONYMS",(Object)tableFields.clone());
            tableNames.add((Object)"TREATMENT_SYNONYMS");
            tableFields.clear();
            tableFields.add((Object)"SURGERY_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SURGERY_SEQUENCE_NUMBER,Surgery Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"SURGERY_SYNONYM_NUMBER,Surgery Map,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SURGERY_SYNONYMS",(Object)tableFields.clone());
            tableNames.add((Object)"SURGERY_SYNONYMS");
            tableFields.clear();
            tableFields.add((Object)"SEQUENCE_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"DISEASE_SEQUENCE_NUMBER,Disease Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"SYMPTOM_SEQUENCE_NUMBER,Symptom Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"MAP_SOURCE_CODE,Map Source,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYDISMAP",(Object)tableFields.clone());
            tableNames.add((Object)"SYDISMAP");
            tableFields.clear();
            tableFields.add((Object)"TEST_SYNONYM_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TEST_SEQUENCE_NUMBER,Test Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TEST_SYNONYM_NAME,Test Name,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"TEST_SYNONYMS",(Object)tableFields.clone());
            tableNames.add((Object)"TEST_SYNONYMS");
            tableFields.clear();
            tableFields.add((Object)"SEQUENCE_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TEST_SEQUENCE_NUMBER,Test Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DISEASE_SEQUENCE_NUMBER,Disease Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"MAP_SOURCE_CODE,Map Source,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"DISTESMAP",(Object)tableFields.clone());
            tableNames.add((Object)"DISTESMAP");
            tableFields.clear();
            tableFields.add((Object)"SEQUENCE_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TEST_SEQUENCE_NUMBER,Test Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DISEASE_SEQUENCE_NUMBER,Disease Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"MAP_SOURCE_CODE,Map Source,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"DISTESMAP",(Object)tableFields.clone());
            tableNames.add((Object)"DISTESMAP");
            tableFields.clear();
            tableFields.add((Object)"SEQUENCE_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYMPTOM_SEQUENCE_NUMBER,Symptom Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TEST_SEQUENCE_NUMBER,Test Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"MAP_SOURCE_CODE,Map Source,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYMPTOM_TO_TEST_MAP",(Object)tableFields.clone());
            tableNames.add((Object)"SYMPTOM_TO_TEST_MAP");
            tableFields.clear();
            tableFields.add((Object)"SYMPTOM_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"SYMPTOM_START_DATE_TIME,Symptom Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            tableFields.add((Object)"SYMPTOM_NAME,Symptom,FIELD,MEDIUMTEXT,40,1,0,1,0,1");
            tableFields.add((Object)"BODY_LOCATION_CODE,Body Location,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"SYMPTOM_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"SYMPTOM_DETAILS,Symptom Details,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"SYMPTOM_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"SYMPTOM_HISTORY");
            tableFields.clear();
            tableFields.add((Object)"RELATION_SEQ_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"RELATION_CODE,Relation Type,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ACCOUNT_NUMBER,Patient Code,FIELD,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RELATION_ACCOUNT_NUMBER,Relation Name,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DELETED,Deleted,BOOLEAN_FIELD,BINARY,4,1,0,0,0,1");
            tableDef.put((Object)"PATIENT_RELATIONS",(Object)tableFields.clone());
            tableNames.add((Object)"PATIENT_RELATIONS");
            tableFields.clear();
            tableFields.add((Object)"APPOINTMENT_REF_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"PATIENT_ACCOUNT_NUMBER,Patient Account Number,FIELD,INT,11,1,1,0,0,1");
            tableFields.add((Object)"APPOINTMENT_STATE_CODE,Appointment State,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PHYSICIAN_INTERNAL_REF_NUMBER,Physician,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"APPOINTMENT_DATE,Appointment Date,DATE_TIME_FIELD,DATETIME,20,1,0,1,0,1");
            //tableFields.add((Object)"APPOINTMENT_NEXT,Next Appointment,FOREIGN,INT,11,1,0,1,0,1");
            //tableFields.add((Object)"APPOINTMENT_PREVIOUS,Previous Appointment,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"APPOINTMENT_DETAILS,Appointment Details,FIELD,VARCHAR,200,1,0,1,0,1");
            tableDef.put((Object)"APPOINTMENTS",(Object)tableFields.clone());
            tableNames.add((Object)"APPOINTMENTS");
            tableFields.clear();
            tableFields.add((Object)"OCCUPATION_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"OCCUPATION_CODE,Occupation,FOREIGN,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"EMPLOYER_CODE,Employer,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"COMMENCEMENT_DATE,Commencement Date,DATE_FIELD,DATE,20,1,0,1,0,1");
            tableFields.add((Object)"TERMINATION_DATE,Termination Date,DATE_FIELD,DATE,20,1,0,1,0,1");
            tableFields.add((Object)"POSITION_AT_TERMINATION,Position,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"OCCUPATION_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"OCCUPATION_HISTORY");
            tableFields.clear();
            //~
            tableFields.add((Object)"DIAGNOSES_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"SYSTEM_CATEGORY_CODE,Body System,GROUPKEY,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DISEASE_SEQUENCE_NUMBER,Diagnoses,GROUP,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"DISEASE_ICD_CODE,ICD Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DURATION,Durarion,FIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TIME_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Association,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ONSET,Onset,FIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"PATIENT_DIAGNOSES",(Object)tableFields.clone());
            tableNames.add((Object)"PATIENT_DIAGNOSES");

            tableFields.clear();
            tableFields.add((Object)"COMPLAINT_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"SYSTEM_CATEGORY_CODE,Body System,GROUPKEY,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"SYMPTOM_SEQUENCE_NUMBER,Complaint,GROUP,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"OTHER_SYMPTOM,Foreign Complaint,GROUPDEPENDANT,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"ONSET_UNITS,Onset,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"FILLER3,Ago,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Association,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_CODE,Anatomical Location,FOREIGN,INT,11,1,0,1,0,1");
            //tableFields.add((Object)"ANATOMICAL_LOCATION,Anatomical Location,XMLTREE,MEDIUMTEXT,200,1,0,1,0,1");
            tableDef.put((Object)"PATIENT_COMPLAINTS",(Object)tableFields.clone());
            tableNames.add((Object)"PATIENT_COMPLAINTS");

            tableFields.clear();
            tableFields.add((Object)"ANATOMICAL_LOCATION_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LOCATION_DESCRIPTION,Location Description,FIELD,MEDIUMTEXT,50,1,0,1,0,1");
            tableDef.put((Object)"ANATOMICAL_LOCATION",(Object)tableFields.clone());
            tableNames.add((Object)"ANATOMICAL_LOCATION");

            tableFields.clear();
            tableFields.add((Object)"CURRENT_STATUS_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"CURRENT_STATUS_DESCRIPTION,Current Status Description,FIELD,VARCHAR,50,1,0,1,0,1");
            tableDef.put((Object)"CURRENT_STATUS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"CURRENT_STATUS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"SYSTEM_CATEGORY_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTEM_CATEGORY_DESCRIPTION,Category Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"SYSTEM_CATEGORY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEM_CATEGORY_TYPE");

            tableFields.clear();
            tableFields.add((Object)"SYSTEM_CATEGORY_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SURGERY_CATEGORY_DESCRIPTION,Category Description,FIELD,VARCHAR,50,1,0,1,0,1");
            tableDef.put((Object)"SURGERY_CATEGORY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"SURGERY_CATEGORY_TYPE");

            tableFields.clear();
            tableFields.add((Object)"ONSET_UNITS_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"ONSET_UNITS_DESCRIPTION,Onset Units,FIELD,VARCHAR,20,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_HOUR_FACTOR,Onset Units Per Hour,FIELD,FLOAT,11,1,0,1,0,1");
            tableDef.put((Object)"ONSET_UNITS",(Object)tableFields.clone());
            tableNames.add((Object)"ONSET_UNITS");
            
            tableFields.clear();
            tableFields.add((Object)"CONSTITUTIONAL_SYMPTOM_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"LOST_WEIGHT,Lost Weight,BOOLEAN_GROUP,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"WEIGHT_AMOUNT,Weight Lost (Kg),BOOLEAN_GROUPDEPENDANT,INT,11,1,1,1,0,1");
            tableFields.add((Object)"LOST_APPETITE,Lost Appetite,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"NIGHTSWEATS,Night Sweats,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"FILLER3,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER4,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FEVER,Fever,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"CONSTITUTIONAL_SYMPTOMS",(Object)tableFields.clone());
            tableNames.add((Object)"CONSTITUTIONAL_SYMPTOMS");
            
            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_CARDIOVASCULAR_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"CARDIOVASCULAR_SYMPTOM_CODE,Symptom,FIELD_FOREIGN,INT,11,1,1,0,0,0,0");
            tableFields.add((Object)"ONSET_UNITS,Onset,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"FILLER1,Ago,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Association,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_CODE,Anatomical Location,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_CARDIOVASCULAR",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_CARDIOVASCULAR");

            tableFields.clear();
            tableFields.add((Object)"CARDIOVASCULAR_SYMPTOM_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"CARDIOVASCULAR_SYMPTOM_DESCRIPTION,Symptom Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_ENABLED,Anatomy Enabled,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"CARDIOVASCULAR_SYMPTOM",(Object)tableFields.clone());
            tableNames.add((Object)"CARDIOVASCULAR_SYMPTOM");
            
            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_RESPIRATORY_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"RESPIRATORY_SYMPTOM_CODE,Symptom,FIELD_FOREIGN,INT,11,1,1,0,0,0,0");
            tableFields.add((Object)"ONSET_UNITS,Onset,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"FILLER1,Ago,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Association,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_CODE,Anatomical Location,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_RESPIRATORY",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_RESPIRATORY");

            tableFields.clear();
            tableFields.add((Object)"RESPIRATORY_SYMPTOM_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RESPIRATORY_SYMPTOM_DESCRIPTION,Symptom Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_ENABLED,Anatomy Enabled,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"RESPIRATORY_SYMPTOM",(Object)tableFields.clone());
            tableNames.add((Object)"RESPIRATORY_SYMPTOM");
            
            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_ABDOMINAL_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"ABDOMINAL_SYMPTOM_CODE,Symptom,FIELD_FOREIGN,INT,11,1,1,0,0,0,0");
            tableFields.add((Object)"ONSET_UNITS,Onset,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"FILLER1,Ago,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Association,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_CODE,Anatomical Location,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_ABDOMINAL",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_ABDOMINAL");

            tableFields.clear();
            tableFields.add((Object)"ABDOMINAL_SYMPTOM_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"ABDOMINAL_SYMPTOM_DESCRIPTION,Symptom Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_ENABLED,Anatomy Enabled,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"ABDOMINAL_SYMPTOM",(Object)tableFields.clone());
            tableNames.add((Object)"ABDOMINAL_SYMPTOM");

            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_MUSCULOSKELETAL_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"MUSCULOSKELETAL_SYMPTOM_CODE,Symptom,FIELD_FOREIGN,INT,11,1,1,0,0,0,0");
            tableFields.add((Object)"ONSET_UNITS,Onset,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"FILLER1,Ago,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Association,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_CODE,Anatomical Location,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_MUSCULOSKELETAL",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_MUSCULOSKELETAL");

            tableFields.clear();
            tableFields.add((Object)"MUSCULOSKELETAL_SYMPTOM_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"MUSCULOSKELETAL_SYMPTOM_DESCRIPTION,Symptom Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_ENABLED,Anatomy Enabled,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"MUSCULOSKELETAL_SYMPTOM",(Object)tableFields.clone());
            tableNames.add((Object)"MUSCULOSKELETAL_SYMPTOM");
            
            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_CNS_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"CNS_SYMPTOM_CODE,Symptom,FIELD_FOREIGN,INT,11,1,1,0,0,0,0");
            tableFields.add((Object)"ONSET_UNITS,Onset,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"FILLER1,Ago,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Association,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_CODE,Anatomical Location,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_CNS",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_CNS");

            tableFields.clear();
            tableFields.add((Object)"CNS_SYMPTOM_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"CNS_SYMPTOM_DESCRIPTION,Symptom Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_ENABLED,Anatomy Enabled,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"CNS_SYMPTOM",(Object)tableFields.clone());
            tableNames.add((Object)"CNS_SYMPTOM");

            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_ENT_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"ENT_SYMPTOM_CODE,Symptom,FIELD_FOREIGN,INT,11,1,1,0,0,0,0");
            tableFields.add((Object)"ONSET_UNITS,Onset,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"FILLER1,Ago,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Association,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_CODE,Anatomical Location,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_ENT",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_ENT");

            tableFields.clear();
            tableFields.add((Object)"ENT_SYMPTOM_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"ENT_SYMPTOM_DESCRIPTION,Symptom Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_ENABLED,Anatomy Enabled,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"ENT_SYMPTOM",(Object)tableFields.clone());
            tableNames.add((Object)"ENT_SYMPTOM");

            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_DERMATOLOGICAL_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"DERMATOLOGICAL_SYMPTOM_CODE,Symptom,FIELD_FOREIGN,INT,11,1,1,0,0,0,0");
            tableFields.add((Object)"ONSET_UNITS,Onset,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"FILLER1,Ago,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Association,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_CODE,Anatomical Location,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_DERMATOLOGICAL",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_DERMATOLOGICAL");

            tableFields.clear();
            tableFields.add((Object)"DERMATOLOGICAL_SYMPTOM_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"DERMATOLOGICAL_SYMPTOM_DESCRIPTION,Symptom Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_ENABLED,Anatomy Enabled,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"DERMATOLOGICAL_SYMPTOM",(Object)tableFields.clone());
            tableNames.add((Object)"DERMATOLOGICAL_SYMPTOM");

            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_HEMATOLOGICAL_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"HEMATOLOGICAL_SYMPTOM_CODE,Symptom,FIELD_FOREIGN,INT,11,1,1,0,0,0,0");
            tableFields.add((Object)"ONSET_UNITS,Onset,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"FILLER1,Ago,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Association,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_CODE,Anatomical Location,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_HEMATOLOGICAL",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_HEMATOLOGICAL");

            tableFields.clear();
            tableFields.add((Object)"HEMATOLOGICAL_SYMPTOM_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HEMATOLOGICAL_SYMPTOM_DESCRIPTION,Symptom Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_ENABLED,Anatomy Enabled,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"HEMATOLOGICAL_SYMPTOM",(Object)tableFields.clone());
            tableNames.add((Object)"HEMATOLOGICAL_SYMPTOM");

            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_EYES_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"EYES_SYMPTOM_CODE,Symptom,FIELD_FOREIGN,INT,11,1,1,0,0,0,0");
            tableFields.add((Object)"ONSET_UNITS,Onset,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"FILLER1,Ago,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Association,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_CODE,Anatomical Location,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_EYES",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_EYES");

            tableFields.clear();
            tableFields.add((Object)"EYES_SYMPTOM_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EYES_SYMPTOM_DESCRIPTION,Symptom Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_ENABLED,Anatomy Enabled,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"EYES_SYMPTOM",(Object)tableFields.clone());
            tableNames.add((Object)"EYES_SYMPTOM");

            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_UROGENITAL_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"UROGENITAL_SYMPTOM_CODE,Symptom,FIELD_FOREIGN,INT,11,1,1,0,0,0,0");
            tableFields.add((Object)"ONSET_UNITS,Onset,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"FILLER1,Ago,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Association,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_CODE,Anatomical Location,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_UROGENITAL",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_UROGENITAL");

            tableFields.clear();
            tableFields.add((Object)"UROGENITAL_SYMPTOM_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"UROGENITAL_SYMPTOM_DESCRIPTION,Symptom Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableFields.add((Object)"ANATOMICAL_LOCATION_ENABLED,Anatomy Enabled,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"UROGENITAL_SYMPTOM",(Object)tableFields.clone());
            tableNames.add((Object)"UROGENITAL_SYMPTOM");

            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_THYROID_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"WEIGHT_TYPE_CODE,Weight Variance,FOREIGN,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"TEMPERATURE_PREFERENCE_CODE,Temperature Preference,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"SWEATING_EXCESSIVE,Sweating Excessive,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"TREMORS,Tremors,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"EMOTIONABILITY,Emotionability,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_THYROID",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_THYROID");

            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_ADRENAL_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"WEIGHT_TYPE_CODE,Weight Variance,FOREIGN,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"BLOOD_PRESSURE_TYPE_CODE,Blood Pressure,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DIAPHORESIS,Diaphoresis,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"TREMORS,Tremors,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"EMOTIONABILITY,Emotionability,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"ASTHENIA,Asthenia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"MYALGIA,Myalgia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"GASTRALGIA,Gastralgia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"ARTHRALGIA,Arthralgia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"POLYURIA,Polyuria,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"HYPERPIGMENTATION,Hyperpigmentation,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"DEHYDRATION,Dehydration,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"TACHYCARDIA,Tachycardia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"PYREXIA,Pyrexia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_ADRENAL",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_ADRENAL");

            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_PITUITARY_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"WEIGHT_TYPE_CODE,Weight Variance,FOREIGN,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"MEGALOMANUS,Megalomanus,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"MEGALOPODIA,Megalopodia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"SKINGROWTH,Skin Growth,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"JAWPROMINENT,Jaw Prominent,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"MACROPROSOPIA,Macroprosopia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"PACHYDERMIA,Pachydermia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"DYSPHONIA,Dysphonia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"HEADACHE,Headache,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"ARTHRALGIA,Arthralgia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"MYOMALACIA,Myomalacia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"CARPALTUNNEL,Carpal Tunnel Syndrome,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"HYPERPIGMENTATION,Hyperpigmentation,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"HYPOPIGMENTATION,Hypopigmentaion,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"DIAPHORESIS,Diaphoresis,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"MYASTHENIA,Myasthenia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"HIRSUITISMFEMALE,Hirsuitism Female,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"XERODERMIA,Xerodermia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"AGRAMMATOLOGIA,Agrammatologia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"HYPERCRYAESTHESIA,Hypercryaesthesia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"ANOREXIA,Anorexia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"HYPOTRICHOSIS,Hypotrichosis,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"AMIBLYOPIA,Amiblyopia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"GASTRALGIA,Gastralgia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"SEBACEOUSLARGE,Enlarged Sebaceous Glands,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_PITUITARY",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_PITUITARY");

            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_PARATHYROID_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"LETHARGY,Lethargy,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"NEPHROLITH,Nephrolith,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"NAUSEA,Nausea,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"COPROSTASIA,Coprostasia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"MYALGIA,Myalgia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"OSTEOCLASIA,Osteoclasia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"OSTEOPOROSIS,Osteoporosis,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"RICKETS,Rickets,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_PARATHYROID",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_PARATHYROID");
            
            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_PANCREAS_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"APPETITE_TYPE_CODE,Appetite Variance,FOREIGN,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"ICTERUS,Icterus,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"GASTRALGIA,Gastralgia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"STOOLSCLAYCOLORED,Stools Clay Coloured,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"STEATORRHOEA,Steatorrhoea,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"NAUSEA,Nausea,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"WEIGHTLOSS,Weight Loss,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"FACIALFLUSHING,Facial Flushing,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"HEADACHE,Headache,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"DIARRHEA,Diarrhea,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"FATIGUE,Fatigue,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"DYSPEPSIA,Dyspepsia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"BLURREDVISION,Blurred Vision,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"PALLOR,Pallor,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"DEPRESSION,Depression,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"BEHAVIOURCHANGES,Behaviour Changes,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"BILIRUBINABSENT,Bilirubin Absent,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"URINEDARK,Urine Dark,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"PRURITIS,Pruritis,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"STOMATITIS,Stomatitis,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"GLOSSITIS,Glossitis,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"FLATULENCEEXCESSIVE,Flatulence Excessive,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"ECZEMA,Eczema,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"SINGULTUS,Singultus,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"POLYDIPSIA,Polydipsia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"POLYURIA,Polyuria,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"ERECTILEDYS,Erectile Dysfunction,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"HAEMATEMESIS,Haematemesis,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"FREQUENTINFECT,Frequent Infections,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"SLOWHEAL,Slow Healing,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_PANCREAS",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_PANCREAS");

            tableFields.clear();
            tableFields.add((Object)"SYSTEMIC_GONADS_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"CRYPTORCHISM,Cryptorchism,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"DECREASEDBODYHAIR,Decreased Body Hair,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"GYNAECOMASTIA,Gynomastia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"INFERTILITY,Infertility,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"MICROSOMIA,Microsomia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"MENOLIPSIS,Menolipsis,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"HYPOMASTIA,Hypomastia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"AMYOTROPHIC,Amyotrophic,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"LIBIDODECREASED,Libido Decreased,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"HOTFLUSHES,Hot Flushes,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"HEADACHES,Headaches,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"AMAUROSIS,Amaurosis,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableDef.put((Object)"SYSTEMIC_GONADS",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTEMIC_GONADS");

            tableFields.clear();
            tableFields.add((Object)"WEIGHT_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"WEIGHT_TYPE_DESCRIPTION,Weight Type Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"WEIGHT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"WEIGHT_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"BLOOD_PRESSURE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"BLOOD_PRESSURE_TYPE_DESCRIPTION,Blood Pressure Type Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"BLOOD_PRESSURE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"BLOOD_PRESSURE_TYPE");

            tableFields.clear();
            tableFields.add((Object)"TREATMENT_CATEGORY_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TREATMENT_CATEGORY_DESCRIPTION,Treatment Category Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"TREATMENT_CATEGORY",(Object)tableFields.clone());
            tableNames.add((Object)"TREATMENT_CATEGORY");

            tableFields.clear();
            tableFields.add((Object)"TEMPERATURE_PREFERENCE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TEMPERATURE_PREFERENCE_DESCRIPTION,Temperature Preference Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"TEMPERATURE_PREFERENCE",(Object)tableFields.clone());
            tableNames.add((Object)"TEMPERATURE_PREFERENCE");

            tableFields.clear();
            tableFields.add((Object)"PREVIOUS_MEDICAL_HISTORY_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"DISEASE_SYSTEM_CATEGORY_CODE,Body System,GROUPKEY,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"DISEASE_SEQUENCE_NUMBER,Diagnoses,GROUP,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"OTHER_DISEASE,Foreign Diagnoses,GROUPDEPENDANT,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"ONSET_UNITS,Onset,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"FILLER3,Ago,FILLER_FIELD,FILLER,11,1,0,1,1,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"PREVIOUS_MEDICAL_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"PREVIOUS_MEDICAL_HISTORY");
            
            tableFields.clear();
            tableFields.add((Object)"CURRENT_STATUS_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"CURRENT_STATUS_DESCRIPTION,Current Status Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"CURRENT_STATUS_PMH",(Object)tableFields.clone());
            tableNames.add((Object)"CURRENT_STATUS_PMH");

            tableFields.clear();
            tableFields.add((Object)"PREVIOUS_SURGICAL_HISTORY_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"SYSTEM_CATEGORY_CODE,Surgery Category,GROUPKEY,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"SURGERY_SEQUENCE_NUMBER,Surgery,GROUP,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"OTHER_SURGERY,Foreign Surgery,GROUPDEPENDANT,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"ONSET_UNITS,Onset,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ONSET_UNITS_CODE,Units Of Time,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"FILLER3,Ago,FILLER_FIELD,FILLER,11,1,0,1,1,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"PREVIOUS_SURGICAL_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"PREVIOUS_SURGICAL_HISTORY");

            tableFields.clear();
            tableFields.add((Object)"FAMILY_HISTORY_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"FAMILY_TYPE_CODE,Family Relation,FIELD_FOREIGN,INT,11,1,1,0,0,0");
            tableFields.add((Object)"ALIVE,Alive,BOOLEAN_GROUP,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"AGE_AT_DEATH,Age At Death,BOOLEAN_GROUPDEPENDANT,INT,11,1,1,1,0,1");
            tableFields.add((Object)"SYSTEM_CATEGORY_CODE,Body System,GROUPKEY,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"DISEASE_SEQUENCE_NUMBER,Disease,GROUP,INT,11,1,0,1,0,1");
            tableDef.put((Object)"FAMILY_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"FAMILY_HISTORY");

            tableFields.clear();
            tableFields.add((Object)"FAMILY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"FAMILY_TYPE_DESCRIPTION,Family Type Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"FAMILY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"FAMILY_TYPE");

            tableFields.clear();
            tableFields.add((Object)"TABLE_KEY,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"ACCOUNT_NUMBER,Max Account Number,FIELD,INT,11,1,0,1,0,1");
            tableDef.put((Object)"MAX_INDEXES",(Object)tableFields.clone());
            tableNames.add((Object)"MAX_INDEXES");

            tableFields.clear();
            tableFields.add((Object)"HABITUAL_DRUG_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HABITUAL_DRUG_DESCRIPTION,Habitual Drug Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HABITUAL_DRUG",(Object)tableFields.clone());
            tableNames.add((Object)"HABITUAL_DRUG");

            tableFields.clear();
            tableFields.add((Object)"SOCIAL_HISTORY_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"GEOGRAPHICAL_REGION_CODE,Region,GROUPKEY,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"PLACE_LIST_CODE,Hometown,GROUP,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"FILLER3,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER4,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_SMOKER,Current Smoker,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"FILLER5,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER6,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"SMOKING,Smoking,BOOLEAN_GROUP,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"PACK_YEARS,Pack Years,BOOLEAN_GROUPDEPENDANT,INT,11,1,1,1,0,1");
            tableFields.add((Object)"SOCIAL_DRINKER,Social Drinker,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"FILLER9,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER10,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"DRINKING,Drinking,BOOLEAN_GROUP,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"DRINK_UNITS,Drink Units Per Week,BOOLEAN_GROUPDEPENDANT,INT,11,1,1,1,0,1");
            tableFields.add((Object)"HABITUAL_DRUG_CODE,Habitual Drugs,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"SOCIAL_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"SOCIAL_HISTORY");

            tableFields.clear();
            tableFields.add((Object)"HOMETOWN_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HOMETOWN_DESCRIPTION,Hometown Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HOMETOWN_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HOMETOWN_TYPE");

            tableFields.clear();
            tableFields.add((Object)"CURRENT_OCCUPATION_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"CURRENT_OCCUPATION_NAME,Current Occupation,FIELD,VARCHAR,40,1,0,1,0,1");
            tableDef.put((Object)"CURRENT_OCCUPATION",(Object)tableFields.clone());
            tableNames.add((Object)"CURRENT_OCCUPATION");
            
            tableFields.clear();
            tableFields.add((Object)"PREVIOUS_OCCUPATION_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"PREVIOUS_OCCUPATION_NAME,Previous Occupation,FIELD,VARCHAR,40,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"PREVIOUS_OCCUPATION_DATE,Date,SPINYEARFIELD,INT,11,1,0,1,0,1");
            tableDef.put((Object)"PREVIOUS_OCCUPATION",(Object)tableFields.clone());
            tableNames.add((Object)"PREVIOUS_OCCUPATION");
           
            tableFields.clear();
            tableFields.add((Object)"PATIENT_TREATMENT_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"TREATMENT_CATEGORY_CODE,Treatment Category,GROUPKEY,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_SEQUENCE_NUMBER,Treatment Name,GROUP,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"OTHER_TREATMENT,Foreign Treatment,GROUPDEPENDANT,VARCHAR,40,1,1,1,0,1");
            //tableFields.add((Object)"FILLER3,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            //tableFields.add((Object)"FILLER4,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"DOSAGE,Dosage,SPINDOSAGE,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DOSAGE_UNIT_CODE,Dosage Units,FOREIGN,INT,11,1,0,1,0,0");
            //tableFields.add((Object)"FILLER3,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            //tableFields.add((Object)"FREQUENCY,Frequency,SPININTFIELD,INT,11,1,0,1,0,0");
            tableFields.add((Object)"TIME_UNITS_CODE,Time Schedule,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"DATE_STARTED,Date Started,SPINLITMONTHYEAR,DATE,11,1,0,1,0,1");
            tableDef.put((Object)"PATIENT_TREATMENT",(Object)tableFields.clone());
            tableNames.add((Object)"PATIENT_TREATMENT");

            tableFields.clear();
            tableFields.add((Object)"TREATMENT_SEQUENCE_NUMBER,Code,PRIMARY,INT,11,1,1,1,0,0");
            tableFields.add((Object)"TREATMENT_CATEGORY_CODE,Treatment Category,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_NAME,Treatment Name,FIELD,MEDIUMTEXT,40,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_DESCRIPTION,Treatment Description,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"RISKS,Treatment Risks,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"PROGNOSIS,Treatment Prognosis,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"COMPLICATIONS,Treatment Complications,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"DATA_SOURCE_CODE,Data Source,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"TREATMENT",(Object)tableFields.clone());
            tableNames.add((Object)"TREATMENT");

            tableFields.clear();
            tableFields.add((Object)"ALLERGY_SEQUENCE_NUMBER,Code,PRIMARY,INT,11,1,1,1,0,0");
            tableFields.add((Object)"ALLERGY_CATEGORY_CODE,Allergy Category,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ALLERGY_ICD_CODE,ICD Code,ICD_FIELD,VARCHAR,9,1,0,1,0,1");
            tableFields.add((Object)"ALLERGY_NAME,Allergy Name,FIELD,MEDIUMTEXT,40,1,0,1,0,1");
            tableFields.add((Object)"ALLERGY_DESCRIPTION,Treatment Description,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"RISKS,Allergy Risks,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"PROGNOSIS,Allergy Prognosis,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"COMPLICATIONS,Allergy Complications,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"ALLERGY",(Object)tableFields.clone());
            tableNames.add((Object)"ALLERGY");

            tableFields.clear();
            tableFields.add((Object)"ALLERGY_CATEGORY_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"ALLERGY_CATEGORY_DESCRIPTION,Allergy Category Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"ALLERGY_CATEGORY",(Object)tableFields.clone());
            tableNames.add((Object)"ALLERGY_CATEGORY");

            tableFields.clear();
            tableFields.add((Object)"PATIENT_ALLERGIES_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"TREATMENT_CATEGORY_CODE,Medicine Category,GROUPKEY,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_SEQUENCE_NUMBER,Medicine Name,GROUP,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"FILLER3,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER4,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FOOD_ALLERGY_SEQUENCE_NUMBER,Food Allergy,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER5,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER6,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"ENVIRONMENT_ALLERGY_SEQUENCE_NUMBER,Environment Allergy,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER7,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER8,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"OTHER_ALLERGY,Other Allergy,FIELD,VARCHAR,40,1,0,1,0,1");
            tableDef.put((Object)"PATIENT_ALLERGIES",(Object)tableFields.clone());
            tableNames.add((Object)"PATIENT_ALLERGIES");
            
            tableFields.clear();
            tableFields.add((Object)"GEOGRAPHICAL_REGION_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"GEOGRAPHICAL_REGION_DESCRIPTION,Geographical Region Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"GEOGRAPHICAL_REGION",(Object)tableFields.clone());
            tableNames.add((Object)"GEOGRAPHICAL_REGION");

            tableFields.clear();
            tableFields.add((Object)"PLACE_LIST_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"GEOGRAPHICAL_REGION_CODE,Geographical Region,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PLACE_NAME,Place Name,FIELD,MEDIUMTEXT,40,1,0,1,0,1");
            tableDef.put((Object)"PLACE_LIST",(Object)tableFields.clone());
            tableNames.add((Object)"PLACE_LIST");

            tableFields.clear();
            tableFields.add((Object)"INTERNATIONAL_REGION_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"INTERNATIONAL_REGION_DESCRIPTION,International Region Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"INTERNATIONAL_REGION",(Object)tableFields.clone());
            tableNames.add((Object)"INTERNATIONAL_REGION");

            tableFields.clear();
            tableFields.add((Object)"COUNTRY_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"INTERNATIONAL_REGION_CODE,International Region,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"COUNTRY_NAME,Country Name,FIELD,MEDIUMTEXT,40,1,0,1,0,1");
            tableDef.put((Object)"COUNTRY_LIST",(Object)tableFields.clone());
            tableNames.add((Object)"COUNTRY_LIST");

            tableFields.clear();
            tableFields.add((Object)"PATIENT_TRAVEL_HISTORY_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"INTERNATIONAL_REGION_CODE,World Region,GROUPKEY,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"COUNTRY_CODE,Country Name,GROUP,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"FILLER3,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER4,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"STAY_DURATION,Duration Of Stay (Days),SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER5,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER6,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"DATE_LEFT,Date Left,SPIN_DATE_FIELD,DATE,20,1,0,1,0,1");
            tableDef.put((Object)"PATIENT_TRAVEL_HISTORY",(Object)tableFields.clone());
            tableNames.add((Object)"PATIENT_TRAVEL_HISTORY");

            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_GENERAL_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"JAUNDICE,Jaundice,BOOLEAN_FIELD,BINARY,4,1,0,1,1,1");
            tableFields.add((Object)"ANEMIA,Anemia,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"CYNOSIS,Cynosis,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"CLUBBING,Clubbing,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"EDEMA,Edema,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"LYMPH_ADENOPATHY,Lymph Adenopathy,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"HYDRATION_TYPE_CODE,Hydration,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ACIDOSIS,Acidosis,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"TEMPERATURE,Temperature,SPINTEMP,FLOAT,11,1,0,1,0,1");
            tableDef.put((Object)"EXAMINATION_GENERAL",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_GENERAL");
            
            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_CARDIOVASCULAR_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"PULSE_RATE,Pulse Rate,SPINPULSE,INT,11,1,0,1,1,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"PULSE_CHARACTER_TYPE_CODE,Pulse Character,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER3,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER4,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"BLOOD_PRESSURE_SYSTOLIC,Blood Pressure (Sys),SPINBPS,INT,7,1,0,1,0,1");
            tableFields.add((Object)"BLOOD_PRESSURE_DIASTOLIC,Blood Pressure (Dia),SPINBPD,INT,7,1,0,1,0,0");
            tableFields.add((Object)"FILLER5,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"JUGULAR_PULSE_TYPE_CODE,Jugular Venous Pulse,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER6,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER7,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"HEART_SOUNDS_S1_TYPE_CODE,Heart Sounds S1,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"HEART_SOUNDS_S2_TYPE_CODE,Heart Sounds S2,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"HEART_SOUNDS_S3_TYPE_CODE,Heart Sounds S3,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"HEART_SOUNDS_S4_TYPE_CODE,Heart Sounds S4,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"SYSTOLIC_MURMURS_TYPE_CODE,Systolic Murmurs,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"SYSTOLIC_LOCATION_TYPE_CODE,Systolic Murmurs Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"DIASTOLIC_MURMURS_TYPE_CODE,Diastolic Murmurs,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DIASTOLIC_LOCATION_TYPE_CODE,Diastolic Murmurs Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"APEX_BEAT_TYPE_CODE,Apex Beat,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_VENTRICULAR_HEAVE_TYPE_CODE,Right Ventricular Heave,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PERIPHERAL_PULSES_PRESENT,Peripheral Pulses All Present,BOOLEAN_FIELD,BINARY,4,1,0,1,0,1");
            tableFields.add((Object)"PERIPHERAL_PULSES_LOCATION_TYPE_CODE,Pulse Location,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"EXAMINATION_CARDIOVASCULAR",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_CARDIOVASCULAR");

            tableFields.clear();
            tableFields.add((Object)"HYDRATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HYDRATION_TYPE_DESCRIPTION,Hydration Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HYDRATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HYDRATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"PULSE_CHARACTER_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PULSE_CHARACTER_TYPE_DESCRIPTION,Pulse Character Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PULSE_CHARACTER_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PULSE_CHARACTER_TYPE");

            tableFields.clear();
            tableFields.add((Object)"JUGULAR_PULSE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"JUGULAR_PULSE_TYPE_DESCRIPTION,Jugular Pulse Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"JUGULAR_PULSE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"JUGULAR_PULSE_TYPE");

            tableFields.clear();
            tableFields.add((Object)"HEART_SOUNDS_S1_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HEART_SOUNDS_S1_TYPE_DESCRIPTION,Heart Sounds S1 Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HEART_SOUNDS_S1_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HEART_SOUNDS_S1_TYPE");

            tableFields.clear();
            tableFields.add((Object)"HEART_SOUNDS_S2_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HEART_SOUNDS_S2_TYPE_DESCRIPTION,Heart Sounds S2 Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HEART_SOUNDS_S2_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HEART_SOUNDS_S2_TYPE");

            tableFields.clear();
            tableFields.add((Object)"HEART_SOUNDS_S3_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HEART_SOUNDS_S3_TYPE_DESCRIPTION,Heart Sounds S3 Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HEART_SOUNDS_S3_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HEART_SOUNDS_S3_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"HEART_SOUNDS_S4_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HEART_SOUNDS_S4_TYPE_DESCRIPTION,Heart Sounds S4 Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HEART_SOUNDS_S4_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HEART_SOUNDS_S4_TYPE");

            tableFields.clear();
            tableFields.add((Object)"SYSTOLIC_MURMURS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTOLIC_MURMURS_TYPE_DESCRIPTION,Systolic Murmurs Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"SYSTOLIC_MURMURS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTOLIC_MURMURS_TYPE");

            tableFields.clear();
            tableFields.add((Object)"SYSTOLIC_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SYSTOLIC_LOCATION_TYPE_DESCRIPTION,Systolic Murmurs Location Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"SYSTOLIC_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"SYSTOLIC_LOCATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"DIASTOLIC_MURMURS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"DIASTOLIC_MURMURS_TYPE_DESCRIPTION,Diastolic Murmurs Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"DIASTOLIC_MURMURS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"DIASTOLIC_MURMURS_TYPE");

            tableFields.clear();
            tableFields.add((Object)"DIASTOLIC_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"DIASTOLIC_LOCATION_TYPE_DESCRIPTION,Diastolic Murmurs Location Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"DIASTOLIC_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"DIASTOLIC_LOCATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"APEX_BEAT_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"APEX_BEAT_TYPE_DESCRIPTION,Apex Beat Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"APEX_BEAT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"APEX_BEAT_TYPE");

            tableFields.clear();
            tableFields.add((Object)"RIGHT_VENTRICULAR_HEAVE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_VENTRICULAR_HEAVE_DESCRIPTION,Right Ventricular Heave Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_VENTRICULAR_HEAVE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_VENTRICULAR_HEAVE_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"PERIPHERAL_PULSES_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PERIPHERAL_PULSES_LOCATION_TYPE_DESCRIPTION,Peripheral Pulses Location Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PERIPHERAL_PULSES_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PERIPHERAL_PULSES_LOCATION_TYPE");

            
            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_RESPIRATORY_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"BREATH_RATE,Breath Rate,SPINBREATH,INT,11,1,0,1,1,1");
            tableFields.add((Object)"RESPIRATORY_DISTRESS_TYPE_CODE,Respiratory Distress,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TRACHEAL_POSITION_TYPE_CODE,Tracheal Posistion,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"BREATH_SOUNDS_TYPE_CODE,Breath Sounds,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"BREATH_SOUNDS_LOCATION_TYPE_CODE,Breath Sounds Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"WHEAZE_TYPE_CODE,Type of Wheaze,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"CREPETATIOUS_TYPE_CODE,Crepetatious Type,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PERCUSSION_TYPE_CODE,Percussion,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PERCUSSION_LOCATION_TYPE_CODE,Percussion Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"HYPER_INFLATION_TYPE_CODE,Hyper Inflation,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"NOTES,Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"EXAMINATION_RESPIRATORY",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_RESPIRATORY");

            tableFields.clear();
            tableFields.add((Object)"RESPIRATORY_DISTRESS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RESPIRATORY_DISTRESS_TYPE_DESCRIPTION,Respiratory Distress Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RESPIRATORY_DISTRESS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RESPIRATORY_DISTRESS_TYPE");

            tableFields.clear();
            tableFields.add((Object)"TRACHEAL_POSITION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TRACHEAL_POSITION_TYPE_DESCRIPTION,Tracheal Position Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"TRACHEAL_POSITION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"TRACHEAL_POSITION_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"BREATH_SOUNDS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"BREATH_SOUNDS_TYPE_DESCRIPTION,Breath Sounds Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"BREATH_SOUNDS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"BREATH_SOUNDS_TYPE");

            tableFields.clear();
            tableFields.add((Object)"BREATH_SOUNDS_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"BREATH_SOUNDS_LOCATION_TYPE_DESCRIPTION,Breath Sounds Location Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"BREATH_SOUNDS_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"BREATH_SOUNDS_LOCATION_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"WHEAZE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"WHEAZE_TYPE_DESCRIPTION,Wheaze Type Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"WHEAZE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"WHEAZE_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"CREPETATIOUS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"CREPETATIOUS_TYPE_DESCRIPTION,Crepetatious Type Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"CREPETATIOUS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"CREPETATIOUS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"PERCUSSION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PERCUSSION_TYPE_DESCRIPTION,Percussion Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PERCUSSION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PERCUSSION_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"PERCUSSION_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PERCUSSION_LOCATION_TYPE_DESCRIPTION,Percussion Location Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PERCUSSION_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PERCUSSION_LOCATION_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"HYPER_INFLATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HYPER_INFLATION_TYPE_DESCRIPTION,Hyper Inflation Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HYPER_INFLATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HYPER_INFLATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_ABDOMINAL_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"PALPITATION_ABDOMINAL_TYPE_CODE,Abdominal Palpitation,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"PALPITATION_REBOUND_TENDERNESS_TYPE_CODE,Rebound Tenderness,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER3,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER4,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"SPLENOMAGALY_TYPE_CODE,Splenomegaly,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"HEPATOMEGALY_TYPE_CODE,Hepatomegaly,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"MASSES_PALPABLE_TYPE_CODE,Masses Palpable,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"MASSES_PALPABLE_LOCATION_TYPE_CODE,Masses Palpable Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"OSCULTATION_BELL_SOUNDS_TYPE_CODE,Oscultation Bell Sounds,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RECTAL_EXAMINATION_TYPE_CODE,Rectal Examination,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"NOTES,Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"EXAMINATION_ABDOMINAL",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_ABDOMINAL");

            tableFields.clear();
            tableFields.add((Object)"PALPITATION_ABDOMINAL_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PALPITATION_ABDOMINAL_TYPE_DESCRIPTION,Palpitations Abdominal Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PALPITATION_ABDOMINAL_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PALPITATION_ABDOMINAL_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"PALPITATION_REBOUND_TENDERNESS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PALPITATION_REBOUND_TENDERNESS_TYPE_DESCRIPTION,Rebound Tenderness Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PALPITATION_REBOUND_TENDERNESS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PALPITATION_REBOUND_TENDERNESS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"SPLENOMAGALY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SPLENOMAGALY_TYPE_DESCRIPTION,Splenomegaly Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"SPLENOMAGALY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"SPLENOMAGALY_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"HEPATOMEGALY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HEPATOMEGALY_TYPE_DESCRIPTION,Hepatomegaly Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HEPATOMEGALY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HEPATOMEGALY_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"MASSES_PALPABLE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"MASSES_PALPABLE_TYPE_DESCRIPTION,Masses Palpable Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"MASSES_PALPABLE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"MASSES_PALPABLE_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"MASSES_PALPABLE_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"MASSES_PALPABLE_LOCATION_TYPE_DESCRIPTION,Masses Palpable Location Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"MASSES_PALPABLE_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"MASSES_PALPABLE_LOCATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"OSCULTATION_BELL_SOUNDS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"OSCULTATION_BELL_SOUNDS_TYPE_DESCRIPTION,Bell Sounds Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"OSCULTATION_BELL_SOUNDS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"OSCULTATION_BELL_SOUNDS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RECTAL_EXAMINATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RECTAL_EXAMINATION_TYPE_DESCRIPTION,Rectal Examination Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RECTAL_EXAMINATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RECTAL_EXAMINATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_MUSCULOSKELETAL_NUM,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"MOVEMENT_ABNORMALITY_TYPE_CODE,Movement Abnormality,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"MOVEMENT_ABNORMALITY_LOCATION_TYPE_CODE,Movement Abnormality Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"CUPSULAR_PATTERN_TYPE_CODE,Cupsular Pattern,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"JOINT_SWELLING_TYPE_CODE,Joint Swelling,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"JOINT_SWELLING_LOCATION_TYPE_CODE,Joint Swelling Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"SUBCUTANEOUS_NODULES_TYPE_CODE,Subcutaneous Nodules,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"SUBCUTANEOUS_NODULES_LOCATION_TYPE_CODE,Subcutaneous Nodules Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"MUSCLE_WASTING_TYPE_CODE,Muscle Wasting,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"MUSCLE_WASTING_LOCATION_TYPE_CODE,Muscle Wasting Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"NOTES,Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"EXAMINATION_MUSCULOSKELETAL",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_MUSCULOSKELETAL");

            tableFields.clear();
            tableFields.add((Object)"MOVEMENT_ABNORMALITY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"MOVEMENT_ABNORMALITY_TYPE_DESCRIPTION,Movement Abnormality Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"MOVEMENT_ABNORMALITY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"MOVEMENT_ABNORMALITY_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"MOVEMENT_ABNORMALITY_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"MOVEMENT_ABNORMALITY_LOCATION_TYPE_DESCRIPTION,Movement Abnormality Location,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"MOVEMENT_ABNORMALITY_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"MOVEMENT_ABNORMALITY_LOCATION_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"CUPSULAR_PATTERN_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"CUPSULAR_PATTERN_TYPE_DESCRIPTION,Cupsular Pattern Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"CUPSULAR_PATTERN_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"CUPSULAR_PATTERN_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"JOINT_SWELLING_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"JOINT_SWELLING_TYPE_DESCRIPTION,Joint Swelling Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"JOINT_SWELLING_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"JOINT_SWELLING_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"JOINT_SWELLING_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"JOINT_SWELLING_LOCATION_TYPE_DESCRIPTION,Joint Swelling Location Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"JOINT_SWELLING_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"JOINT_SWELLING_LOCATION_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"SUBCUTANEOUS_NODULES_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SUBCUTANEOUS_NODULES_TYPE_DESCRIPTION,Subcutaneous Nodules Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"SUBCUTANEOUS_NODULES_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"SUBCUTANEOUS_NODULES_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"SUBCUTANEOUS_NODULES_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SUBCUTANEOUS_NODULES_LOCATION_TYPE_DESCRIPTION,Subcutaneous Nodules Location Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"SUBCUTANEOUS_NODULES_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"SUBCUTANEOUS_NODULES_LOCATION_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"MUSCLE_WASTING_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"MUSCLE_WASTING_TYPE_DESCRIPTION,Muscle Wasting Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"MUSCLE_WASTING_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"MUSCLE_WASTING_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"MUSCLE_WASTING_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"MUSCLE_WASTING_LOCATION_TYPE_DESCRIPTION,Muscle Wasting Location Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"MUSCLE_WASTING_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"MUSCLE_WASTING_LOCATION_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_CNS_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"LEVEL_OF_CONSCIOUSNESS_TYPE_CODE,Level Of Conciousness,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"GCS_EYE_OPENING_TYPE_CODE,GCS Eye Opening,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"GCS_VERBAL_RESPONSE_TYPE_CODE,GCS Verbal Response,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"GCS_BEST_MOTOR_RESPONSE_TYPE_CODE,GCS Motor Response,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"CNE_EYE_MOVEMENT_TYPE_CODE,CNE Eye Movement,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"GAZE_PALSY_TYPE_CODE,Gaze Palsy,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"VISUAL_FIELD_DEFECT_TYPE_CODE,Visual Field Defect,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"THIRD_FOURTH_SIXTH_NERVE_TYPE_CODE,3/4/6 Nerve Deficit,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PUPIL_SIZE_TYPE_CODE,Pupil Size,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PUPIL_MOBILITY_TYPE_CODE,Pupil Mobility,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FIFTH_NERVE_SENSORY_DEFICIT_TYPE_CODE,5th Nerve Sensory Defecit,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FIFTH_NERVE_MOTOR_DEFICIT_TYPE_CODE,5th Nerve Motor Deficit,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"SEVENTH_NERVE_SENSORY_DEFICIT_TYPE_CODE,7th Nerve Sensory Deficit,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"SEVENTH_NERVE_MOTOR_DEFICIT_TYPE_CODE,7Th Nerve Motor Deficit,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"EIGHTH_NERVE_HEARING_DEFICIT_TYPE_CODE,8th Nerve Hearing Deficit,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"NINTH_TENTH_ELEVENTH_NERVE_BULBAR_PALSY_TYPE_CODE,9/10/11th Nerve Bulbar Palsy,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TWELTH_NERVE_TONGUE_DEFICIT_TYPE_CODE,12th Nerve Tongue Deficit,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"UPPER_LIMB_MUSCLE_MOTOR_TYPE_CODE,Upper Limb Muscles Motor Deficit,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"UPPER_LIMB_MUSCLE_MOTOR_LOCATION_TYPE_CODE,Upper Limb Muscles Motor Deficit Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"UPPER_LIMB_SENSATION_TYPE_CODE,Upper Limb Muscles Sensation Deficit,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"UPPER_LIMB_SENSATION_LOCATION_TYPE_CODE,Upper Limb Muscles Sensation Deficit Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"UPPER_LIMB_REFLEX_TYPE_CODE,Upper Limb Muscles Reflex,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"UPPER_LIMB_REFLEX_LOCATION_TYPE_CODE,Upper Limb Muscles Reflex Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"UPPER_LIMB_TONE_TYPE_CODE,Upper Limb Muscles Tone,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"UPPER_LIMB_TONE_LOCATION_TYPE_CODE,Upper Limb Muscles Tone Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"LOWER_LIMB_MUSCLE_MOTOR_TYPE_CODE,Lower Limb Muscles Motor Deficit,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LOWER_LIMB_MUSCLE_MOTOR_LOCATION_TYPE_CODE,Lower Limb Muscles Motor Deficit Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"LOWER_LIMB_SENSATION_TYPE_CODE,Lower Limb Muscles Sensation Deficit,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LOWER_LIMB_SENSATION_LOCATION_TYPE_CODE,Lower Limb Muscles Sensation Deficit Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"LOWER_LIMB_REFLEX_TYPE_CODE,Lower Limb Muscles Reflex,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LOWER_LIMB_REFLEX_LOCATION_TYPE_CODE,Lower Limb Muscles Reflex Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"LOWER_LIMB_TONE_TYPE_CODE,Lower Limb Muscles Tone,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LOWER_LIMB_TONE_LOCATION_TYPE_CODE,Lower Limb Muscles Tone Deficit Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"BABINSKY_REFLEX_TYPE_CODE,Babinsky Relex,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ANAXOMILIA_LOCATION_TYPE_CODE,Anatomical Location,FOREIGN,INT,11,1,1,1,0,1");
            tableFields.add((Object)"GAIT,Gait,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableFields.add((Object)"INVOLUNTARY_MOVE_TYPE_CODE,Involuntary Moves,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"NOTES,Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"EXAMINATION_CENTRAL_NERVOUS_SYSTEM",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_CENTRAL_NERVOUS_SYSTEM");
            
            tableFields.clear();
            tableFields.add((Object)"LEVEL_OF_CONSCIOUSNESS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEVEL_OF_CONSCIOUSNESS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEVEL_OF_CONSCIOUSNESS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEVEL_OF_CONSCIOUSNESS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"GCS_EYE_OPENING_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"GCS_EYE_OPENING_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"GCS_EYE_OPENING_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"GCS_EYE_OPENING_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"GCS_VERBAL_RESPONSE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"GCS_VERBAL_RESPONSE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"GCS_VERBAL_RESPONSE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"GCS_VERBAL_RESPONSE_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"GCS_BEST_MOTOR_RESPONSE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"GCS_BEST_MOTOR_RESPONSE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"GCS_BEST_MOTOR_RESPONSE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"GCS_BEST_MOTOR_RESPONSE_TYPE");

            tableFields.clear();
            tableFields.add((Object)"CNE_EYE_MOVEMENT_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"CNE_EYE_MOVEMENT_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"CNE_EYE_MOVEMENT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"CNE_EYE_MOVEMENT_TYPE");

            tableFields.clear();
            tableFields.add((Object)"GAZE_PALSY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"GAZE_PALSY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"GAZE_PALSY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"GAZE_PALSY_TYPE");

            tableFields.clear();
            tableFields.add((Object)"VISUAL_FIELD_DEFECT_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"VISUAL_FIELD_DEFECT_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"VISUAL_FIELD_DEFECT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"VISUAL_FIELD_DEFECT_TYPE");

            tableFields.clear();
            tableFields.add((Object)"THIRD_FOURTH_SIXTH_NERVE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"THIRD_FOURTH_SIXTH_NERVE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"THIRD_FOURTH_SIXTH_NERVE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"THIRD_FOURTH_SIXTH_NERVE_TYPE");

            tableFields.clear();
            tableFields.add((Object)"PUPIL_SIZE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PUPIL_SIZE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PUPIL_SIZE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PUPIL_SIZE_TYPE");

            tableFields.clear();
            tableFields.add((Object)"PUPIL_MOBILITY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PUPIL_MOBILITY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PUPIL_MOBILITY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PUPIL_MOBILITY_TYPE");

            tableFields.clear();
            tableFields.add((Object)"FIFTH_NERVE_SENSORY_DEFICIT_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"FIFTH_NERVE_SENSORY_DEFICIT_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"FIFTH_NERVE_SENSORY_DEFICIT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"FIFTH_NERVE_SENSORY_DEFICIT_TYPE");

            tableFields.clear();
            tableFields.add((Object)"FIFTH_NERVE_MOTOR_DEFICIT_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"FIFTH_NERVE_MOTOR_DEFICIT_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"FIFTH_NERVE_MOTOR_DEFICIT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"FIFTH_NERVE_MOTOR_DEFICIT_TYPE");

            tableFields.clear();
            tableFields.add((Object)"SEVENTH_NERVE_SENSORY_DEFICIT_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SEVENTH_NERVE_SENSORY_DEFICIT_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"SEVENTH_NERVE_SENSORY_DEFICIT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"SEVENTH_NERVE_SENSORY_DEFICIT_TYPE");

            tableFields.clear();
            tableFields.add((Object)"SEVENTH_NERVE_MOTOR_DEFICIT_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SEVENTH_NERVE_MOTOR_DEFICIT_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"SEVENTH_NERVE_MOTOR_DEFICIT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"SEVENTH_NERVE_MOTOR_DEFICIT_TYPE");

            tableFields.clear();
            tableFields.add((Object)"EIGHTH_NERVE_HEARING_DEFICIT_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EIGHTH_NERVE_HEARING_DEFICIT_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"EIGHTH_NERVE_HEARING_DEFICIT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"EIGHTH_NERVE_HEARING_DEFICIT_TYPE");

            tableFields.clear();
            tableFields.add((Object)"NINTH_TENTH_ELEVENTH_NERVE_BULBAR_PALSY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"NINTH_TENTH_ELEVENTH_NERVE_BULBAR_PALSY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"NINTH_TENTH_ELEVENTH_NERVE_BULBAR_PALSY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"NINTH_TENTH_ELEVENTH_NERVE_BULBAR_PALSY_TYPE");

            tableFields.clear();
            tableFields.add((Object)"TWELTH_NERVE_TONGUE_DEFICIT_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TWELTH_NERVE_TONGUE_DEFICIT_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"TWELTH_NERVE_TONGUE_DEFICIT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"TWELTH_NERVE_TONGUE_DEFICIT_TYPE");

            tableFields.clear();
            tableFields.add((Object)"UPPER_LIMB_MUSCLE_MOTOR_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"UPPER_LIMB_MUSCLE_MOTOR_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"UPPER_LIMB_MUSCLE_MOTOR_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"UPPER_LIMB_MUSCLE_MOTOR_TYPE");

            tableFields.clear();
            tableFields.add((Object)"UPPER_LIMB_MUSCLE_MOTOR_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"UPPER_LIMB_MUSCLE_MOTOR_LOCATION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"UPPER_LIMB_MUSCLE_MOTOR_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"UPPER_LIMB_MUSCLE_MOTOR_LOCATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"UPPER_LIMB_SENSATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"UPPER_LIMB_SENSATION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"UPPER_LIMB_SENSATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"UPPER_LIMB_SENSATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"UPPER_LIMB_SENSATION_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"UPPER_LIMB_SENSATION_LOCATION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"UPPER_LIMB_SENSATION_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"UPPER_LIMB_SENSATION_LOCATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"UPPER_LIMB_REFLEX_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"UPPER_LIMB_REFLEX_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"UPPER_LIMB_REFLEX_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"UPPER_LIMB_REFLEX_TYPE");

            tableFields.clear();
            tableFields.add((Object)"UPPER_LIMB_REFLEX_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"UPPER_LIMB_REFLEX_LOCATION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"UPPER_LIMB_REFLEX_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"UPPER_LIMB_REFLEX_LOCATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"UPPER_LIMB_TONE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"UPPER_LIMB_TONE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"UPPER_LIMB_TONE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"UPPER_LIMB_TONE_TYPE");

            tableFields.clear();
            tableFields.add((Object)"UPPER_LIMB_TONE_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"UPPER_LIMB_TONE_LOCATION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"UPPER_LIMB_TONE_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"UPPER_LIMB_TONE_LOCATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"LOWER_LIMB_MUSCLE_MOTOR_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LOWER_LIMB_MUSCLE_MOTOR_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LOWER_LIMB_MUSCLE_MOTOR_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LOWER_LIMB_MUSCLE_MOTOR_TYPE");

            tableFields.clear();
            tableFields.add((Object)"LOWER_LIMB_MUSCLE_MOTOR_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LOWER_LIMB_MUSCLE_MOTOR_LOCATION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LOWER_LIMB_MUSCLE_MOTOR_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LOWER_LIMB_MUSCLE_MOTOR_LOCATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"LOWER_LIMB_SENSATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LOWER_LIMB_SENSATION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LOWER_LIMB_SENSATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LOWER_LIMB_SENSATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"LOWER_LIMB_SENSATION_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LOWER_LIMB_SENSATION_LOCATION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LOWER_LIMB_SENSATION_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LOWER_LIMB_SENSATION_LOCATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"LOWER_LIMB_REFLEX_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LOWER_LIMB_REFLEX_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LOWER_LIMB_REFLEX_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LOWER_LIMB_REFLEX_TYPE");

            tableFields.clear();
            tableFields.add((Object)"LOWER_LIMB_REFLEX_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LOWER_LIMB_REFLEX_LOCATION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LOWER_LIMB_REFLEX_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LOWER_LIMB_REFLEX_LOCATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"LOWER_LIMB_TONE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LOWER_LIMB_TONE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LOWER_LIMB_TONE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LOWER_LIMB_TONE_TYPE");

            tableFields.clear();
            tableFields.add((Object)"LOWER_LIMB_TONE_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LOWER_LIMB_TONE_LOCATION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LOWER_LIMB_TONE_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LOWER_LIMB_TONE_LOCATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"BABINSKY_REFLEX_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"BABINSKY_REFLEX_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"BABINSKY_REFLEX_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"BABINSKY_REFLEX_TYPE");

            tableFields.clear();
            tableFields.add((Object)"ANAXOMILIA_LOCATION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"ANAXOMILIA_LOCATION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"ANAXOMILIA_LOCATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"ANAXOMILIA_LOCATION_TYPE");

            tableFields.clear();
            tableFields.add((Object)"INVOLUNTARY_MOVE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"INVOLUNTARY_MOVE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"INVOLUNTARY_MOVE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"INVOLUNTARY_MOVE_TYPE");

            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_ENT_EAR_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"LEFT_EXTERNAL_AUDITORY_MEATUS_TYPE_CODE,External Auditory Meatus,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_EXTERNAL_AUDITORY_MEATUS_TYPE_CODE,External Auditory Meatus,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_OTHER_EXTERNAL_AUDITORY_MEATUS,Other Auditory Meatus,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"RIGHT_OTHER_EXTERNAL_AUDITORY_MEATUS,Other Auditory Meatus,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"LEFT_TYMPANIC_MEMBRANE_TYPE_CODE,Tympanic Membrane,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_TYMPANIC_MEMBRANE_TYPE_CODE,Tympanic Membrane,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_OTHER_TYMPANIC_MEMBRANE,Tympanic Membrane Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"RIGHT_OTHER_TYMPANIC_MEMBRANE,Tympanic Membrane Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"LEFT_HEARING_LOSS_PRESENT_TYPE_CODE,Hearing Loss Present,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_HEARING_LOSS_PRESENT_TYPE_CODE,Hearing Loss Present,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RINNE_TEST_TYPE_CODE,External Auditory Meatus,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"WEBER_TEST_TYPE_CODE,External Auditory Meatus,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"NYSTAGMUS_TYPE_CODE,Nystagmus,FOREIGN,INT,11,1,0,1,0,1");
            tableDef.put((Object)"EXAMINATION_ENT_EAR",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_ENT_EAR");

            tableFields.clear();
            tableFields.add((Object)"LEFT_EXTERNAL_AUDITORY_MEATUS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_EXTERNAL_AUDITORY_MEATUS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_EXTERNAL_AUDITORY_MEATUS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_EXTERNAL_AUDITORY_MEATUS_TYPE");

            tableFields.clear();
            tableFields.add((Object)"RIGHT_EXTERNAL_AUDITORY_MEATUS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_EXTERNAL_AUDITORY_MEATUS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_EXTERNAL_AUDITORY_MEATUS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_EXTERNAL_AUDITORY_MEATUS_TYPE");

            tableFields.clear();
            tableFields.add((Object)"LEFT_TYMPANIC_MEMBRANE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_TYMPANIC_MEMBRANE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_TYMPANIC_MEMBRANE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_TYMPANIC_MEMBRANE_TYPE");

            tableFields.clear();
            tableFields.add((Object)"RIGHT_TYMPANIC_MEMBRANE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_TYMPANIC_MEMBRANE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_TYMPANIC_MEMBRANE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_TYMPANIC_MEMBRANE_TYPE");

            tableFields.clear();
            tableFields.add((Object)"LEFT_HEARING_LOSS_PRESENT_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_HEARING_LOSS_PRESENT_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_HEARING_LOSS_PRESENT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_HEARING_LOSS_PRESENT_TYPE");

            tableFields.clear();
            tableFields.add((Object)"RIGHT_HEARING_LOSS_PRESENT_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_HEARING_LOSS_PRESENT_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_HEARING_LOSS_PRESENT_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_HEARING_LOSS_PRESENT_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RINNE_TEST_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RINNE_TEST_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RINNE_TEST_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RINNE_TEST_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"WEBER_TEST_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"WEBER_TEST_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"WEBER_TEST_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"WEBER_TEST_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"NYSTAGMUS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"NYSTAGMUS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"NYSTAGMUS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"NYSTAGMUS_TYPE");

            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_ENT_NOSE_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"LEFT_NASAL_PASSAGES_TYPE_CODE,Left Nasal Passages,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_NASAL_PASSAGES_TYPE_CODE,Right Nasal Passages,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_NASAL_PASSAGES_NOTES,Left Nasal Passage Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"RIGHT_NASAL_PASSAGES_NOTES,Left Nasal Passage Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableDef.put((Object)"EXAMINATION_ENT_NOSE",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_ENT_NOSE");

            tableFields.clear();
            tableFields.add((Object)"RIGHT_NASAL_PASSAGES_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_NASAL_PASSAGES_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_NASAL_PASSAGES_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_NASAL_PASSAGES_TYPE");

            tableFields.clear();
            tableFields.add((Object)"LEFT_NASAL_PASSAGES_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_NASAL_PASSAGES_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_NASAL_PASSAGES_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_NASAL_PASSAGES_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_ENT_PARANASAL_SINUSES_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"LEFT_FRONTAL_SINUSES_TYPE_CODE,Left Frontal Sinuses,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_FRONTAL_SINUSES_TYPE_CODE,Right Frontal Sinuses,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_OTHER_FRONTAL_SINUSES,Left Frontal Sinuses Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"RIGHT_OTHER_FRONTAL_SINUSES,Right Frontal Sinuses Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"LEFT_MAXILLARY_SINUSES_TYPE_CODE,Left Maxillary Sinuses,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_MAXILLARY_SINUSES_TYPE_CODE,Right Maxillary Sinuses,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_OTHER_MAXILLARY_SINUSES,Left Maxillary Sinuses Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"RIGHT_OTHER_MAXILLARY_SINUSES,Right Maxillary Sinuses Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableDef.put((Object)"EXAMINATION_ENT_PARANASAL_SINUSES",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_ENT_PARANASAL_SINUSES");
            
            
            tableFields.clear();
            tableFields.add((Object)"LEFT_FRONTAL_SINUSES_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_FRONTAL_SINUSES_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_FRONTAL_SINUSES_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_FRONTAL_SINUSES_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"LEFT_MAXILLARY_SINUSES_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_MAXILLARY_SINUSES_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_MAXILLARY_SINUSES_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_MAXILLARY_SINUSES_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_MAXILLARY_SINUSES_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_MAXILLARY_SINUSES_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_MAXILLARY_SINUSES_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_MAXILLARY_SINUSES_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_FRONTAL_SINUSES_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_FRONTAL_SINUSES_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_FRONTAL_SINUSES_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_FRONTAL_SINUSES_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_ENT_THROAT_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"POST_NASAL_DRIP_TYPE_CODE,Post Nasal Drip,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"PHARYNGITIS_TYPE_CODE,Pharyngitis Present,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PHARYNGITIS_OTHER,Pharyngitis Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"TONSILS_TYPE_CODE,Tonsils,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TONSILS_OTHER,Tonsils Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"ORAL_LESIONS_TYPE_CODE,Oral Lesions Present,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ORAL_LESIONS_NOTES,Oral Lesions Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableDef.put((Object)"EXAMINATION_ENT_THROAT",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_ENT_THROAT");

            tableFields.clear();
            tableFields.add((Object)"POST_NASAL_DRIP_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"POST_NASAL_DRIP_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"POST_NASAL_DRIP_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"POST_NASAL_DRIP_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"PHARYNGITIS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PHARYNGITIS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PHARYNGITIS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PHARYNGITIS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"TONSILS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"TONSILS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"TONSILS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"TONSILS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"ORAL_LESIONS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"ORAL_LESIONS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"ORAL_LESIONS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"ORAL_LESIONS_TYPE");

            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_DERMATOLOGICAL_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"PIGMENTATION_TYPE_CODE,Skin Pigmentation,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PIGMENTATION_LOCATION,Pigmentation Location,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"PIGMENTATION_OTHER,Pigmentation Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"HEMORRHAGE_TYPE_CODE,Skin Hemorrhage Present,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"HEMORRHAGE_LOCATION,Hemorrhage Location,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"SKIN_LESIONS_TYPE_CODE,Skin Lesions Present,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"SKIN_LESIONS_LOCATION,Lesions Location,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"HAIR_TYPE_CODE,Hair,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"HAIR_OTHER,Hair Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"NAILS_TYPE_CODE,Nails,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"NAILS_OTHER,Nails Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableDef.put((Object)"EXAMINATION_DERMATOLOGICAL",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_DERMATOLOGICAL");

            tableFields.clear();
            tableFields.add((Object)"PIGMENTATION_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PIGMENTATION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PIGMENTATION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PIGMENTATION_TYPE");
                      
            tableFields.clear();
            tableFields.add((Object)"HEMORRHAGE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HEMORRHAGE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HEMORRHAGE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HEMORRHAGE_TYPE");
                      
            tableFields.clear();
            tableFields.add((Object)"SKIN_LESIONS_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SKIN_LESIONS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"SKIN_LESIONS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"SKIN_LESIONS_TYPE");
                      
            tableFields.clear();
            tableFields.add((Object)"HAIR_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HAIR_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HAIR_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HAIR_TYPE");
                      
            tableFields.clear();
            tableFields.add((Object)"NAILS_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"NAILS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"NAILS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"NAILS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_HEMATOLOGICAL_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"ACTIVE_BLEEDING_TYPE_CODE,Active Bleeding,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ACTIVE_BLEEDING_TEXT,Active Bleeding Text,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"IRON_DEFICIENCY_QUERY_TYPE_CODE,Iron Deficiency Query,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"VITAMIN_PACID_DEFICIENCY_TYPE_CODE,B12/Pholic Acid Deficiency,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER3,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER4,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"HEMOLYTIC_ANAEMIA_TYPE_CODE,Hemolytic Anaemia,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"HEMOLYTIC_ANAEMIA_TEXT,Hemolytic Anaemia Text,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"LYMPH_ADENOPATHY_TYPE_CODE,Lymph Adenopathy,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LYMPH_ADENOPATHY_TEXT,Lymph Adenopathy Text,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"HEPATOMEGALLY_TYPE_CODE,Hepatomegally,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"HEPATOMEGALLY_TEXT,Hepatomegally Text,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"SPLENOMEGALLY_TYPE_CODE,Splenomegally,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"SPLENOMEGALLY_TEXT,Splenomegally Text,FIELD,VARCHAR,40,1,1,1,0,1");
            tableDef.put((Object)"EXAMINATION_HEMATOLOGICAL",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_HEMATOLOGICAL");
            
            
            tableFields.clear();
            tableFields.add((Object)"ACTIVE_BLEEDING_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"ACTIVE_BLEEDING_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"ACTIVE_BLEEDING_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"ACTIVE_BLEEDING_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"IRON_DEFICIENCY_QUERY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"IRON_DEFICIENCY_QUERY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"IRON_DEFICIENCY_QUERY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"IRON_DEFICIENCY_QUERY_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"VITAMIN_PACID_DEFICIENCY_TYPE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"VITAMIN_PACID_DEFICIENCY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"VITAMIN_PACID_DEFICIENCY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"VITAMIN_PACID_DEFICIENCY_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"HEMOLYTIC_ANAEMIA_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HEMOLYTIC_ANAEMIA_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HEMOLYTIC_ANAEMIA_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HEMOLYTIC_ANAEMIA_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"LYMPH_ADENOPATHY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LYMPH_ADENOPATHY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LYMPH_ADENOPATHY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LYMPH_ADENOPATHY_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"HEPATOMEGALLY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HEPATOMEGALLY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HEPATOMEGALLY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HEPATOMEGALLY_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"SPLENOMEGALLY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"SPLENOMEGALLY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"SPLENOMEGALLY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"SPLENOMEGALLY_TYPE");
            
  
            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_EYES_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"FILLER1,<html><h3><font color=\"blue\">Left Eye:</font></h3></html>,FILLER_FIELD,FILLER,11,1,0,1,1,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER3,<html><h3><font color=\"blue\">Right Eye:</font></h3></html>,FILLER_FIELD,FILLER,11,1,0,1,1,1");
            tableFields.add((Object)"FILLER4,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_VISUAL_ACCUITY_TYPE_CODE,Visual Accuity,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_VISUAL_ACCUITY_TYPE_CODE,Visual Accuity,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_COLOUR_SENSE_TYPE_CODE,Colour Sense,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_COLOUR_SENSE_TYPE_CODE,Colour Sense,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_COLOUR_SENSE_TEXT,Colour Sense Text,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"RIGHT_COLOUR_SENSE_TEXT,Colour Sense Text,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"LEFT_VISUAL_FIELDS_TYPE_CODE,Visual Fields,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_VISUAL_FIELDS_TYPE_CODE,Visual Fields,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_VISUAL_FIELDS_TEXT,Visual Fields Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"RIGHT_VISUAL_FIELDS_TEXT,Visual Fields Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"LEFT_PTOSIS_TYPE_CODE,Ptosis,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_PTOSIS_TYPE_CODE,Ptosis,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_LID_RETRACTION_TYPE_CODE,Lid Retraction,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_LID_RETRACTION_TYPE_CODE,Lid Retraction,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_CONJUNCTIVITUS_TYPE_CODE,Conjunctivitus,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_CONJUNCTIVITUS_TYPE_CODE,Conjunctivitus,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_CORNEA_TYPE_CODE,Cornea,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_CORNEA_TYPE_CODE,Cornea,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_CORNEA_TEXT,Cornea Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"RIGHT_CORNEA_TEXT,Cornea Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"LEFT_IRIS_TYPE_CODE,Iris,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_IRIS_TYPE_CODE,Iris,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_IRIS_TEXT,Iris Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"RIGHT_IRIS_TEXT,Iris Other,FIELD,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"LEFT_PUPIL_TYPE_CODE,Pupil,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_PUPIL_TYPE_CODE,Pupil,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_FUNDUSCOPY_TYPE_CODE,Funduscopy,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_FUNDUSCOPY_TYPE_CODE,Funduscopy,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_FUNDUSCOPY_TEXT,Funduscopy Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"RIGHT_FUNDUSCOPY_TEXT,Funduscopy Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableDef.put((Object)"EXAMINATION_EYES",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_EYES");

            tableFields.clear();
            tableFields.add((Object)"LEFT_VISUAL_ACCUITY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_VISUAL_ACCUITY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_VISUAL_ACCUITY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_VISUAL_ACCUITY_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_VISUAL_ACCUITY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_VISUAL_ACCUITY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_VISUAL_ACCUITY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_VISUAL_ACCUITY_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"LEFT_COLOUR_SENSE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_COLOUR_SENSE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_COLOUR_SENSE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_COLOUR_SENSE_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_COLOUR_SENSE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_COLOUR_SENSE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_COLOUR_SENSE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_COLOUR_SENSE_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"LEFT_VISUAL_FIELDS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_VISUAL_FIELDS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_VISUAL_FIELDS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_VISUAL_FIELDS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_VISUAL_FIELDS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_VISUAL_FIELDS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_VISUAL_FIELDS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_VISUAL_FIELDS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"LEFT_PTOSIS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_PTOSIS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_PTOSIS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_PTOSIS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_PTOSIS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_PTOSIS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_PTOSIS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_PTOSIS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"LEFT_LID_RETRACTION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_LID_RETRACTION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_LID_RETRACTION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_LID_RETRACTION_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_LID_RETRACTION_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_LID_RETRACTION_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_LID_RETRACTION_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_LID_RETRACTION_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"LEFT_CONJUNCTIVITUS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_CONJUNCTIVITUS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_CONJUNCTIVITUS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_CONJUNCTIVITUS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_CONJUNCTIVITUS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_CONJUNCTIVITUS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_CONJUNCTIVITUS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_CONJUNCTIVITUS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"LEFT_CORNEA_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_CORNEA_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_CORNEA_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_CORNEA_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_CORNEA_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_CORNEA_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_CORNEA_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_CORNEA_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"LEFT_IRIS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_IRIS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_IRIS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_IRIS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_IRIS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_IRIS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_IRIS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_IRIS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"LEFT_PUPIL_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_PUPIL_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_PUPIL_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_PUPIL_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_PUPIL_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_PUPIL_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_PUPIL_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_PUPIL_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"LEFT_FUNDUSCOPY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_FUNDUSCOPY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_FUNDUSCOPY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_FUNDUSCOPY_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_FUNDUSCOPY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_FUNDUSCOPY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_FUNDUSCOPY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_FUNDUSCOPY_TYPE");

            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_UROGENITAL_MALE_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"LEFT_TESTICAL_TYPE_CODE,Left Testical,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_TESTICAL_TYPE_CODE,Right Testical,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_TESTICAL_TEXT,Left Testical Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"RIGHT_TESTICAL_TEXT,Right Testical Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"PENIS_TYPE_CODE,Penis,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PENIS_TEXT,Penis Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"PROSTATE_TYPE_CODE,Prostate,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PROSTATE_TEXT,Prostate Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableDef.put((Object)"EXAMINATION_UROGENITAL_MALE",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_UROGENITAL_MALE");

            tableFields.clear();
            tableFields.add((Object)"LEFT_TESTICAL_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_TESTICAL_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_TESTICAL_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_TESTICAL_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_TESTICAL_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_TESTICAL_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_TESTICAL_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_TESTICAL_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"PENIS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PENIS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PENIS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PENIS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"PROSTATE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PROSTATE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PROSTATE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PROSTATE_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_UROGENITAL_FEMALE_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"CERVIX_TYPE_CODE,Cervix,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"CERVIX_TEXT,Cervix Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"UTERUS_TYPE_CODE,Uterus,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"UTERUS_TEXT,Uterus Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"ADNEXAE_TYPE_CODE,Adnexae,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ADNEXAE_TEXT,Adnexae Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"VAGINA_TYPE_CODE,Vagina,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"VAGINA_TEXT,Vagina Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"INTROITUS_TYPE_CODE,Introitus,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"INTROITUS_TEXT,Introitus Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"PAP_SMEAR_TYPE_CODE,Pap Smear Done,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PAP_SMEAR_TEXT,Pap Smear Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"LEFT_BREAST_TYPE_CODE,Left Breast,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEFT_BREAST_TEXT,Left Breast Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"RIGHT_BREAST_TYPE_CODE,Right Breast,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"RIGHT_BREAST_TEXT,Right Breast Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableDef.put((Object)"EXAMINATION_UROGENITAL_FEMALE",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_UROGENITAL_FEMALE");
            
            tableFields.clear();
            tableFields.add((Object)"CERVIX_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"CERVIX_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"CERVIX_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"CERVIX_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"UTERUS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"UTERUS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"UTERUS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"UTERUS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"ADNEXAE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"ADNEXAE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"ADNEXAE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"ADNEXAE_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"VAGINA_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"VAGINA_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"VAGINA_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"VAGINA_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"INTROITUS_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"INTROITUS_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"INTROITUS_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"INTROITUS_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"PAP_SMEAR_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PAP_SMEAR_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PAP_SMEAR_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PAP_SMEAR_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"LEFT_BREAST_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEFT_BREAST_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEFT_BREAST_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEFT_BREAST_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"RIGHT_BREAST_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"RIGHT_BREAST_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"RIGHT_BREAST_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"RIGHT_BREAST_TYPE");

            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_UROGENITAL_URINE_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"FILLER1,<html><h3><font color=\"blue\">Urine Dipsticks Check:</font></h3></html>,FILLER_FIELD,FILLER,11,1,0,1,1,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER3,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER4,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"PROTEIN_TYPE_CODE,Protein Present,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"PROTEIN_TEXT,Protien Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"GLUCOSE_TYPE_CODE,Glucose Present,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"GLUCOSE_TEXT,Glucose Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"LEUKOSYTES_TYPE_CODE,Leukosytes Present,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"LEUKOSYTES_TEXT,Leukosytes Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"NITRITES_TYPE_CODE,Nitrites Present,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"NITRITES_TEXT,Nitrites Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"BILLIRUBIN_TYPE_CODE,Billirubin Present,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"BILLIRUBIN_TEXT,Billirubin Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"KETONES_TYPE_CODE,Ketones Present,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"KETONES_TEXT,Ketones Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"BLOOD_TYPE_CODE,Blood Present,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"BLOOD_TEXT,Blood Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"FILLER5,________________________________,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER6,_____________________________,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER7,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER8,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER9,<html> <h3><font color=\"blue\">Microscopy Check:</font></h3> <html>,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER10,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER11,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER12,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"MICROSCOPY_TYPE_CODE,Microscopy,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"MICROSCOPY_TEXT,Microscopy Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableDef.put((Object)"EXAMINATION_UROGENITAL_URINE",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_UROGENITAL_URINE");

            tableFields.clear();
            tableFields.add((Object)"PROTEIN_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"PROTEIN_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"PROTEIN_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"PROTEIN_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"GLUCOSE_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"GLUCOSE_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"GLUCOSE_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"GLUCOSE_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"LEUKOSYTES_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"LEUKOSYTES_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"LEUKOSYTES_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"LEUKOSYTES_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"NITRITES_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"NITRITES_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"NITRITES_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"NITRITES_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"BILLIRUBIN_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"BILLIRUBIN_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"BILLIRUBIN_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"BILLIRUBIN_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"KETONES_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"KETONES_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"KETONES_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"KETONES_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"BLOOD_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"BLOOD_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"BLOOD_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"BLOOD_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"MICROSCOPY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"MICROSCOPY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"MICROSCOPY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"MICROSCOPY_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"EXAMINATION_ENDOCRINE_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"THYROID_GLAND_TYPE_CODE,Thyroid Gland,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"HYPERTHYROIDISM_TYPE_CODE,Features Of Hyperthyroidism,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"HYPERTHYROIDISM_TEXT,Hyperthyroidism Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"HYPOTHYROIDISM_TYPE_CODE,Features Of Hypothyroidism,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"HYPOTHYROIDISM_TEXT,Hypothyroidism Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableFields.add((Object)"HORMONE_ABNORMALITY_TYPE_CODE,Hormone Abnormality,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"HORMONE_ABNORMALITY_TEXT,Hormone Abnormality Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,1,1,0,1");
            tableDef.put((Object)"EXAMINATION_ENDOCRINE",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_ENDOCRINE");
          
           
            tableFields.clear();
            tableFields.add((Object)"THYROID_GLAND_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"THYROID_GLAND_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"THYROID_GLAND_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"THYROID_GLAND_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"HYPERTHYROIDISM_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HYPERTHYROIDISM_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HYPERTHYROIDISM_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HYPERTHYROIDISM_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"HYPOTHYROIDISM_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HYPOTHYROIDISM_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HYPOTHYROIDISM_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HYPOTHYROIDISM_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"HORMONE_ABNORMALITY_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"HORMONE_ABNORMALITY_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"HORMONE_ABNORMALITY_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"HORMONE_ABNORMALITY_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"GENDER_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"GENDER_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"GENDER_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"GENDER_TYPE");

            tableFields.clear();
            tableFields.add((Object)"MEDICAL_ASSESMENT_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"ASSESMENT_SYSTEM_CATEGORY_CODE,Body System,GROUPKEY,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"DISEASE_SEQUENCE_NUMBER,Final Diagnoses,GROUP,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"OTHER_DISEASE,Unlisted Diagnoses,GROUPDEPENDANT,VARCHAR,40,1,1,1,0,1");
            tableFields.add((Object)"DIAGNOSES_MAIN_CAUSE_CODE,Main Cause,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DISEASE_ICD_CODE,ICD Code,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"CURRENT_STATUS_CODE,Current Status,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"ASSOCIATION,Notes,TEXT_AREA_FIELD,MEDIUMTEXT,500,1,0,1,0,1");
            tableDef.put((Object)"MEDICAL_ASSESMENT",(Object)tableFields.clone());
            tableNames.add((Object)"MEDICAL_ASSESMENT");
            
            tableFields.clear();
            tableFields.add((Object)"PLAN_TREATMENT_NUMBER,Code,PRIMARY,INT,11,1,1,0,0,0");
            tableFields.add((Object)"TREATMENT_CATEGORY_CODE,Treatment Category,GROUPKEY,INT,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER1,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"FILLER2,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"TREATMENT_SEQUENCE_NUMBER,Treatment Name,GROUP,INT,11,1,0,1,1,1,1");
            tableFields.add((Object)"OTHER_TREATMENT,Foreign Treatment,GROUPDEPENDANT,VARCHAR,40,1,1,1,0,1");
            //tableFields.add((Object)"FILLER3,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            //tableFields.add((Object)"FILLER4,Filler,FILLER_FIELD,FILLER,11,1,0,1,0,1");
            tableFields.add((Object)"DOSAGE,Dosage,SPINDOSAGE,INT,11,1,0,1,0,1");
            tableFields.add((Object)"DOSAGE_UNIT_CODE,Dosage Units,FOREIGN,INT,11,1,0,1,0,0");
            //tableFields.add((Object)"FREQUENCY,Frequency,SPININTFIELD,INT,11,1,0,1,0,1");
            tableFields.add((Object)"TIME_UNITS_CODE,Time Schedule,FOREIGN,INT,11,1,0,1,0,0");
            tableFields.add((Object)"DATE_START,Start Date,SPINLITMONTHYEAR,DATE,11,1,0,1,0,1");
            tableDef.put((Object)"PLAN_TREATMENT",(Object)tableFields.clone());
            tableNames.add((Object)"PLAN_TREATMENT");

            tableFields.clear();
            tableFields.add((Object)"TEST_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"CRYP_FIELD,Crypto Field,FIELD,VARCHAR,30,1,0,1,0,1");
            tableDef.put((Object)"ENCRYPT_TEST",(Object)tableFields.clone());
            tableNames.add((Object)"ENCRYPT_TEST");

            tableFields.clear();
            tableFields.add((Object)"EXAM_SEQ_NUM,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAM_SUB_TYPE_CODE,Table Type,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"EXAM_SUB_TYPE_SEQ_NUM,Sub Type Sequence,SUBSEQ,INT,11,1,0,1,0,1");
            tableFields.add((Object)"EXAM_SUB_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,50,1,0,1,0,1");
            tableDef.put((Object)"EXAMINATION_TYPES",(Object)tableFields.clone());
            tableNames.add((Object)"EXAMINATION_TYPES");
            
            tableFields.clear();
            tableFields.add((Object)"EXAM_SUB_TYPE_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"EXAM_TYPE_DESCRIPTION,Description,FIELD,VARCHAR,50,1,0,1,0,1");
            tableDef.put((Object)"EXAM_TYPE",(Object)tableFields.clone());
            tableNames.add((Object)"EXAM_TYPE");
            
            tableFields.clear();
            tableFields.add((Object)"USER_CODE,Code,PRIMARY,INT,11,1,1,0,0,1");
            tableFields.add((Object)"USER_TYPE,User Type,FOREIGN,INT,11,1,0,1,0,1");
            tableFields.add((Object)"OWNER_NAME,Owner Name,FIELD,MEDIUMTEXT,30,1,0,1,0,1");
            tableFields.add((Object)"USER_NAME,User Name,FIELD,MEDIUMTEXT,30,1,0,1,0,1");
            tableDef.put((Object)"USER_LIST",(Object)tableFields.clone());
            tableNames.add((Object)"USER_LIST");
 
            if (_myOS.equalsIgnoreCase("WINDOWS"))
            {
               iniFilePresent = config.readIni(InfoManager.WINDOWS_INI_PATH);
            } else
            if (_myOS.equalsIgnoreCase("UNIX"))
            {
               iniFilePresent = config.readIni(InfoManager.UNIX_INI_PATH);
            } else
            if (_myOS.equalsIgnoreCase("WINXP"))
            {
               iniFilePresent = config.readIni(InfoManager.WINXP_INI_PATH);
            }
            columnData = new ArrayList(20);
            columnList = new ArrayList(20);
            tokenList = new ArrayList(20);
            tableDefArray = new ArrayList(20);
            tableData = new HashMap();
            for (tableNameIndex = 0; tableNameIndex < tableNames.size(); tableNameIndex++)
            {
                tableName = (String)tableNames.get(tableNameIndex);
                tableDefArray = (ArrayList)tableDef.get((Object)tableName);
                for (tableDefArrayIndex = 0; tableDefArrayIndex < tableDefArray.size(); tableDefArrayIndex++)
                {
                    fieldDetailString = (String)tableDefArray.get(tableDefArrayIndex);
                    StringTokenizer fieldTokens = new StringTokenizer(fieldDetailString,",");	
                    tokenCount = fieldTokens.countTokens();
                    columnData.clear();
                    for (columnDataIndex = 0; columnDataIndex <  tokenCount; columnDataIndex++)
                    {
                        theToken = (String)fieldTokens.nextToken();
                        if (columnDataIndex == 0)
                        {
                            theFieldName = theToken;
                        }
                        if ((columnDataIndex != 5) || (iniFilePresent == false))
                        {    
                           columnData.add(columnDataIndex,(Object)theToken);
                        } else
                        {
                            config.setHeader(tableName.toUpperCase());
                            itemCount = (int)config.nodes[config.cursor].numItems();
                            filterList = new ArrayList(itemCount);
                            for (int loop = 0; loop < itemCount; loop ++)
                            {
                                filterList.add((Object)config.nodes[config.cursor].getItemName(loop));
                            }
                            if (filterList.contains((Object)theFieldName))
                            {    
                               columnData.add(columnDataIndex,(Object)"0");
                            } else   
                            {
                                columnData.add(columnDataIndex,(Object)"1");
                            }
                        }    
                    } //end columnData
                    columnList.add(tableDefArrayIndex,(Object)columnData.clone());
                }
                tableData.put((Object)tableName,columnList.clone());
                columnList.clear();
            }
        }
    }
    
    public ArrayList getTableDetails(String tableName)
    {
        return (ArrayList)tableData.get(tableName);
    }
     

} // end systemTableLib



