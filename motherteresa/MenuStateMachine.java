package motherteresa;
//  Object used by state
//  Generated by LIBERO 2.30 on  2 Oct, 2003,  9:44.
//  Schema file used: lrschema.jav.

import java.awt.*;
import java.applet.*;
import java.net.*;
import java.util.*;
import java.io.*;


abstract public class MenuStateMachine
{
    //- Variables used by dialog interpreter --------------------------------

    private static int
        _LR_event,                  //  Event for state transition
        _LR_state,                  //  Current dialog state
        _LR_savest,                 //  Saved dialog state
        _LR_index;                  //  Index of methods function

    public static int
        the_next_event,             //  Next event from module
        the_exception_event;        //  Exception event from module

    private static boolean
        exception_raised;           //  TRUE if exception raised


    //- Symbolic constants and event numbers --------------------------------

    private static int
        _LR_STOP            = 0xFFFF,
        _LR_NULL_EVENT      = -2;
    public static int
        _LR_STATE_after_init = 0,
        _LR_STATE_patient_basic_capture = 1,
        _LR_STATE_current_complaint_enabled = 2,
        _LR_STATE_current_complaint_capture = 3,
        _LR_STATE_systemic_enquiry_enabled = 4,
        _LR_STATE_systemic_enquiry_capture = 5,
        _LR_STATE_previous_medical_history_enabled = 6,
        _LR_STATE_previous_medical_history_capture = 7,
        _LR_STATE_previous_surgical_history_enabled = 8,
        _LR_STATE_previous_surgical_history_capture = 9,
        _LR_STATE_family_history_enabled = 10,
        _LR_STATE_family_history_capture = 11,
        _LR_STATE_social_history_enabled = 12,
        _LR_STATE_social_history_capture = 13,
        _LR_STATE_occupational_history_enabled = 14,
        _LR_STATE_occupational_history_capture = 15,
        _LR_STATE_treatment_enabled = 16,
        _LR_STATE_treatment_capture = 17,
        _LR_STATE_allergies_enabled = 18,
        _LR_STATE_allergies_capture = 19,
        _LR_STATE_travel_history_enabled = 20,
        _LR_STATE_travel_history_capture = 21,
        _LR_STATE_examination_enabled = 22,
        _LR_STATE_examination_capture = 23,
        _LR_STATE_assesment_enabled = 24,
        _LR_STATE_assesment_capture = 25,
        _LR_STATE_final_diagnoses_enabled = 26,
        _LR_STATE_final_diagnoses_capture = 27,
        _LR_STATE_plan_enabled = 28,
        _LR_STATE_plan_capture = 29,
        allergies_menu_button_pressed_event = 0,
        allergies_update_button_pressed_event = 1,
        assesment_menu_button_pressed_event = 2,
        assesment_update_button_pressed_event = 3,
        basic_update_button_pressed_event = 4,
        current_complaint_menu_button_pressed_event = 5,
        current_complaint_update_button_pressed_event = 6,
        examination_menu_button_pressed_event = 7,
        examination_update_button_pressed_event = 8,
        family_history_menu_button_pressed_event = 9,
        family_history_update_button_pressed_event = 10,
        final_diagnoses_menu_button_pressed_event = 11,
        final_diagnoses_update_button_pressed_event = 12,
        new_patient_menu_button_pressed_event = 13,
        occupational_history_menu_button_pressed_event = 14,
        occupational_history_update_button_pressed_event = 15,
        plan_menu_button_pressed_event = 16,
        plan_update_button_pressed_event = 17,
        previous_medical_history_menu_button_pressed_event = 18,
        previous_medical_history_update_button_pressed_event = 19,
        previous_surgical_history_menu_button_pressed_event = 20,
        previous_surgical_history_update_button_pressed_event = 21,
        social_history_menu_button_pressed_event = 22,
        social_history_update_button_pressed_event = 23,
        systemic_enquiry_menu_button_pressed_event = 24,
        systemic_enquiry_update_button_pressed_event = 25,
        travel_history_menu_button_pressed_event = 26,
        travel_history_update_button_pressed_event = 27,
        treatment_menu_button_pressed_event = 28,
        treatment_update_button_pressed_event = 29,
        terminate_event     = -1;

    //- Static areas --------------------------------------------------------

    private static int _LR_nextst [][] = {
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,12,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,14,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,16,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,17,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,18 },
        { 19,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,20,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,22,0,0 },
        { 0,0,0,0,0,0,0,23,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,24,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,25,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,26,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,27,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,28,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,29,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,29,0,0,0,0,0,0,0,0,0,0,0,0 }
    };

    private static int _LR_action [][] = {
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,12,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,14,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,16,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,17,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,18 },
        { 19,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,20,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,22,0,0 },
        { 0,0,0,0,0,0,0,23,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,24,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,25,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,26,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,27,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,28,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,29,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,30,0,0,0,0,0,0,0,0,0,0,0,0 }
    };

    private static int _LR_vector [][] = {
        {0},
        {10,_LR_STOP},
        {11,_LR_STOP},
        {4,_LR_STOP},
        {5,_LR_STOP},
        {18,_LR_STOP},
        {19,_LR_STOP},
        {13,_LR_STOP},
        {14,_LR_STOP},
        {15,_LR_STOP},
        {16,_LR_STOP},
        {23,_LR_STOP},
        {7,_LR_STOP},
        {27,_LR_STOP},
        {17,_LR_STOP},
        {25,_LR_STOP},
        {9,_LR_STOP},
        {29,_LR_STOP},
        {21,_LR_STOP},
        {0,_LR_STOP},
        {2,_LR_STOP},
        {28,_LR_STOP},
        {20,_LR_STOP},
        {22,_LR_STOP},
        {6,_LR_STOP},
        {1,_LR_STOP},
        {3,_LR_STOP},
        {24,_LR_STOP},
        {8,_LR_STOP},
        {26,_LR_STOP},
        {12,_LR_STOP}
    };

    abstract public void initialise_the_program ();
    abstract public void get_external_event ();
    abstract public void allergies_capture_display ();
    abstract public void assesment_capture_display ();
    abstract public void display_allergies_detail ();
    abstract public void display_assesment_detail ();
    abstract public void display_current_complaint_capture ();
    abstract public void display_current_complaint_detail ();
    abstract public void display_examination_detail ();
    abstract public void display_family_history_detail ();
    abstract public void display_final_diagnoses_detail ();
    abstract public void display_occupational_history_detail ();
    abstract public void display_patient_basic_capture ();
    abstract public void display_patient_basic_detail ();
    abstract public void display_plan_detail ();
    abstract public void display_previous_medical_history_capture ();
    abstract public void display_previous_medical_history_detail ();
    abstract public void display_previous_surgical_history_capture ();
    abstract public void display_previous_surgical_history_detail ();
    abstract public void display_social_history_detail ();
    abstract public void display_systemic_enquiry_capture ();
    abstract public void display_systemic_enquiry_detail ();
    abstract public void display_travel_history_detail ();
    abstract public void display_treatment_detail ();
    abstract public void examination_capture_display ();
    abstract public void family_history_capture_display ();
    abstract public void final_diagnoses_capture_display ();
    abstract public void occupational_history_capture_display ();
    abstract public void plan_capture_display ();
    abstract public void social_history_capture_display ();
    abstract public void travel_history_capture_display ();
    abstract public void treatment_capture_display ();

    //- Dialog interpreter starts here --------------------------------------

    public void init()
    {
        _LR_state = 0;                  //  First state is always zero
    }

    public void setEvent(int eventNumber)
    {
        the_next_event = eventNumber;                  
    }    
    
    public int execute ()
    {
        int
            feedback = 0,
            index,
            next_module;

        //_LR_state = 0;                  //  First state is always zero
        //initialise_the_program ();
        while (the_next_event != terminate_event)
          {
            _LR_event = the_next_event;
            if (_LR_event >= 30 || _LR_event < 0)
              {
                String buffer;
                buffer  = "State " + _LR_state + " - event " + _LR_event;
                buffer += " is out of range";
                System.out.println (buffer);
                break;
              }
            _LR_savest = _LR_state;
            _LR_index  = _LR_action [_LR_state][_LR_event];
            if (_LR_index == 0)
              {
                String buffer;
                buffer  = "State " + _LR_state + " - event " + _LR_event;
                buffer += " is not accepted";
                //System.out.println (buffer);
                break;
              }
            the_next_event          = terminate_event;
            the_exception_event     = _LR_NULL_EVENT;
            exception_raised        = false;
            next_module             = 0;

            for (;;)
              {
                index = _LR_vector [_LR_index][next_module];
                if ((index == _LR_STOP)
                || (exception_raised))
                break;
                switch (index)
                  {
                    case 0: allergies_capture_display (); break;
                    case 1: assesment_capture_display (); break;
                    case 2: display_allergies_detail (); break;
                    case 3: display_assesment_detail (); break;
                    case 4: display_current_complaint_capture (); break;
                    case 5: display_current_complaint_detail (); break;
                    case 6: display_examination_detail (); break;
                    case 7: display_family_history_detail (); break;
                    case 8: display_final_diagnoses_detail (); break;
                    case 9: display_occupational_history_detail (); break;
                    case 10: display_patient_basic_capture (); break;
                    case 11: display_patient_basic_detail (); break;
                    case 12: display_plan_detail (); break;
                    case 13: display_previous_medical_history_capture (); break;
                    case 14: display_previous_medical_history_detail (); break;
                    case 15: display_previous_surgical_history_capture (); break;
                    case 16: display_previous_surgical_history_detail (); break;
                    case 17: display_social_history_detail (); break;
                    case 18: display_systemic_enquiry_capture (); break;
                    case 19: display_systemic_enquiry_detail (); break;
                    case 20: display_travel_history_detail (); break;
                    case 21: display_treatment_detail (); break;
                    case 22: examination_capture_display (); break;
                    case 23: family_history_capture_display (); break;
                    case 24: final_diagnoses_capture_display (); break;
                    case 25: occupational_history_capture_display (); break;
                    case 26: plan_capture_display (); break;
                    case 27: social_history_capture_display (); break;
                    case 28: travel_history_capture_display (); break;
                    case 29: treatment_capture_display (); break;
                  }
                  next_module++;
              }
            if (exception_raised)
              {
                if (the_exception_event != _LR_NULL_EVENT)
                    _LR_event = the_exception_event;
                the_next_event = _LR_event;
              }
            else
                _LR_state = _LR_nextst [_LR_state][_LR_event];

            /*if (the_next_event == _LR_NULL_EVENT)
              {
                get_external_event ();
                if (the_next_event == _LR_NULL_EVENT)
                  {
                    String buffer;
                    buffer  = "No event set after event " + _LR_event;
                    buffer += " in state " + _LR_state;
                    System.out.println (buffer);
                    break;
                  }
              }*/
          }
        return (feedback);
    }

    //- Standard dialog routines --------------------------------------------
    public void raise_exception (int event)
    {
        exception_raised = true;
        if (event >= 0)
            the_exception_event = event;
    }

}