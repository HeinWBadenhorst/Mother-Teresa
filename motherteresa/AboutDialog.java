package motherteresa;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ccl.swing.*;
import ccl.util.*;

public class AboutDialog extends ExitJDialog
                         implements ActionListener
{
    private JButton _btnOK = null;
    private static final int SN_TITLE = 0;
    private static final int SN_AUTHOR = 1;
    private static final int SN_CHANGE = 2;
    private static final int SN_NUMBER = 3;

    private String[][] aasLabel = new String[SN_NUMBER][2];

    private JLabel  _lblName;
    private JLabel  _lblVersion;
    private JLabel  _lblAuthor;
    private JLabel  _lblRealAuthor;
    private JLabel  _lblDate;
    private JLabel  _lblRealDate;
    
    private static Font _pFont = new Font( "Dialog", Font.PLAIN, 12 );

    /**
     * Initialize swing label with special font size.
     */
    private JLabel _createNewLabel(String sCaption_) 
{
        Util.panicIf( sCaption_ == null );

        JLabel lblNew = new JLabel(sCaption_);
        lblNew.setFont( _pFont );
        
        return lblNew;
    }

    /**
     * Initialize swing labels with default values.
     */
    private void _initLabels() 
    {
        _lblName = _createNewLabel( "Mother Teres@ Practice Management System");
        _lblVersion = _createNewLabel("Version 1.0.1 (Beta)");
        _lblAuthor = _createNewLabel( "Company: Valkyrie Systems" );
        _lblRealAuthor = _createNewLabel( "Author: Hein Badenhorst" );
        _lblDate = _createNewLabel( "Build Date:" );
        _lblRealDate = _createNewLabel( "31 October 2010");
    }

    /**
     * @param   sRcsId_   RCS Header/Id string which is used
     *                    to extract the application name.
     */
    protected String _getName(String sRcsId_) 
    {
        Util.debug("AboutDialog: _getName: sRcsId_: " + sRcsId_);
        int index = sRcsId_.indexOf(".java");
        String sHead = sRcsId_.substring(0, index);
        index = sHead.lastIndexOf('/') + 1;
        String sProgramName = sHead.substring(index, sHead.length());
        if (!sProgramName.equals("Main")) 
        {
            return sProgramName;
        }
        index = sRcsId_.indexOf("/RCS/Main.java");
        if ( index == -1 )
        {
            index = sRcsId_.indexOf( "/Main.java" );
        }
        Util.panicIf(index == -1,
                     "Irgendwas stimmt mit der RCS �bergabe nicht in AboutDialog.");
        sHead = sRcsId_.substring(0, index);
        index = sHead.lastIndexOf('/') + 1;
        sProgramName = sHead.substring(index, sHead.length());
        sProgramName = Util.firstCharToUpperCase(sProgramName);
        Util.debug("AboutDialog: _getName: sProgramName: " + sProgramName);
        
        return sProgramName;
    }

    /**
     * @param   sRcsId_   RCS Header/Id string which is used
     *                    to extract program version 
     *                    information.
     */
    protected String _getVersion(String sRcsId_) 
    {
        int index = sRcsId_.indexOf(" ", 9) + 1;
        String sHead = sRcsId_.substring(index, sRcsId_.length());
        index = sHead.indexOf(' ');
        String sVersion = "Version " + sHead.substring(0, index);

        return sVersion;
    }

    /**
     * @param   sRcsHeader_   RCS Header string which is used
     *                        to extract latest date
     *                        information.
     */
    protected String _getDateAndTime( String sRcsHeader_ ) 
    {
        String sVersion = _getVersion(sRcsHeader_);
        Util.debug("AboutDialog: _getDateAndTime: sVersion: " + sVersion);
        Util.debug("AboutDialog: _getDateAndTime: sRcsHeader_: " +
                   sRcsHeader_);
        int index = sRcsHeader_.indexOf(sVersion.
                                        substring(8,    sVersion.length())
                                        ) + sVersion.length() - 7;
        Util.debug("AboutDialog: _getDateAndTime: index: " + index);
        String sHead = sRcsHeader_.substring(index, sRcsHeader_.length());
        index = sHead.indexOf(' ');
        String sDate = sHead.substring(0, index);

        // Time
        sHead = sHead.substring(index + 1, sHead.length());
        index = sHead.indexOf(' ');
        String sTime = sHead.substring(0, index);

        // Format Date
        int year = Util.atoi(sDate.substring(0, 4));
        int month = Util.atoi(sDate.substring(5, 7));
        int day = Util.atoi(sDate.substring(8, 10));
        /*MultiDate pMultiDate = new MultiDate(year, month, day,
          pLanguage_);
          sDate = pMultiDate.toLocaleDateString();*/
        sDate = "" + year + "-" + Util.paddWithZero( month, 2 ) + "-" +
               Util.paddWithZero( day, 2 );

        return sDate + "   " + sTime;
    }

    /**
     * When this dialog gets created, it is immediately shown
     * as well.
     */
    public AboutDialog( MainJFrame pMainFrame_ ) 
    {
        this( pMainFrame_, pMainFrame_.getInit() );
    }

    /**
     * When this dialog gets created, it is immediately shown
     * as well.
     */
    public AboutDialog( Frame frmParent_, String sInfoHeader_ ) 
    {
        this( frmParent_, null, sInfoHeader_ );
    }

    /**
     * When this dialog gets created, it is immediately shown
     * as well.
     */
    public AboutDialog( Frame frmParent_, Init pInit_ ) 
    {
        this( frmParent_,
              pInit_.getAuthor(),
              pInit_.getInfoHeader() );
    }

    /**
     * When this dialog gets created, it is immediately shown
     * as well.
     */
    public AboutDialog( Frame frmParent_, 
                        String sAuthor_,
                        String sRCSHeader_ ) 
    {
        super( frmParent_, "About", true );

        _initLabels();
        
        String sRcsId = sRCSHeader_.replace('\\', '/');
        
        if (sRcsId.startsWith("$Header: ")) 
        {
            _lblName.setText( _getName( sRcsId ) );
            _lblVersion.setText( _getVersion( sRcsId ) );
            _lblRealDate.setText( _getDateAndTime( sRcsId ) );
        }
        AutoGridBagLayout pAutoGridBagLayout = 
               new AutoGridBagLayout( 0 );
        
        getContentPane().setLayout(pAutoGridBagLayout);
        
        ((JPanel)getContentPane()).setBorder
               ( BorderFactory.createEmptyBorder( 23, 38, 17, 38 ) );

        int strut = 6;

        // Program name
        _lblName.setFont( new Font( "Dialog", Font.BOLD, 14 ) );
        pAutoGridBagLayout.setExpandHorizontal();
        pAutoGridBagLayout.setAnchor( AutoGridBagLayout.WEST );
        getContentPane().add( _lblName );

        pAutoGridBagLayout.setExpandNone();
        getContentPane().add( new JLabel( "             " ) );
        pAutoGridBagLayout.setExpandHorizontal();

        // Version
        getContentPane().add( _lblVersion );
        pAutoGridBagLayout.endLine();
        
        getContentPane().add( Box.createVerticalStrut( strut ) );
        pAutoGridBagLayout.endLine();

        // Autor
        getContentPane().add( _lblAuthor );
        pAutoGridBagLayout.skip();
        if ( !Util.isEmpty( sAuthor_ ) ) 
        {
            _lblRealAuthor.setText( sAuthor_ );
        }
        getContentPane().add( _lblRealAuthor );
        pAutoGridBagLayout.endLine();

        getContentPane().add( Box.createVerticalStrut( strut ) );
        pAutoGridBagLayout.endLine();

        // Datum
        getContentPane().add( _lblDate );
        pAutoGridBagLayout.skip();
        getContentPane().add(_lblRealDate);
        pAutoGridBagLayout.endLine();

        getContentPane().add( Box.createVerticalStrut
                              ( 3*strut + 1 ) );
        pAutoGridBagLayout.endLine();

        // OK Button
        _btnOK = new JButton("OK");
        _btnOK.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
        _btnOK.setPreferredSize
               ( new Dimension
                 ( 75, _btnOK.getPreferredSize().height ) );
        SwingUtil.setDefaultButton( this, _btnOK );
        _btnOK.addActionListener( this );

        pAutoGridBagLayout.setExtend( 3, 1 );
        pAutoGridBagLayout.setAnchor( AutoGridBagLayout.CENTER );
        getContentPane().add(_btnOK);
        SwingUtil.setInitialFocus( this, _btnOK );

        pack();
        setSize( getPreferredSize() );
        SwingUtil.centerComponent(this);
        show();
    }

    /**
     * Close about dialog when OK button is pressed.
     */
    public void actionPerformed(ActionEvent pActionEvent_) 
    {
        Object oSource = pActionEvent_.getSource();
        if (oSource instanceof JButton) 
        {
            dispose();
        }
    }
}
