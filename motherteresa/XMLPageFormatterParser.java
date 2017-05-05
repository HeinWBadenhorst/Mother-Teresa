package motherteresa;

/*
 * DynamicMenuBuilder.java
 *
 * Created on 23 May 2003, 02:10
 */
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Attribute;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;
import java.io.*;
import java.net.*;
import java.util.*;
//import javax.xml.parsers.*;
//import org.w3c.dom.*;

/**
 *
 * @author  Hein
 */
public class XMLPageFormatterParser
{
    private String XMLPath = InfoManager.DISPLAY_XML_PATH;
    private  DOMBuilder builder = null;
    private  Document doc = null;
    private String pageName = null;
    private String pageVersion = null;
    private String pageOrientation = null;
    private int gridCount = 0;
    private boolean multiPage = false;

    private ArrayList pageItemList = new ArrayList(5);
    private HashMap pageItemToTypeMap = new HashMap(5);
    private HashMap staticTextItemMap = new HashMap(5);
    private HashMap textItemMap = new HashMap(5);
    private HashMap imageItemMap = new HashMap(5);
    private HashMap tableItemMap = new HashMap(5);
    private HashMap textLabelComboItemMap = new HashMap(5);
    private  ArrayList nameList = null;
    
    public XMLPageFormatterParser() 
    {
        int result = initDOMTree(XMLPath,"test.xml");
    }

    public XMLPageFormatterParser(String XMLDocPath, String XMLDocName)
    {
        int result = initDOMTree(XMLDocPath, XMLDocName);
    }
    
    public XMLPageFormatterParser(String XMLDocName)
    {
        int result = initDOMTree(this.XMLPath, XMLDocName);
    }

    public int initDOMTree(String pathName, String fileName)
    {
        try 
        {
            builder = new DOMBuilder();
            doc = builder.build( new File(pathName + fileName));
            buildPageItemLists();
        } catch (JDOMException e) 
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    public Document getDocument()
    {
        return doc;
    }

    public String getPageName()
    {
        return pageName;
    }

    public String getPageVersion()
    {
        return pageVersion;
    }
   
    public int getPageGridCount()
    {
        return gridCount;
    }

    public String getPageOrientation()
    {
        return pageOrientation;
    }
    
    public boolean isMultiPage()
    {
        return multiPage;
    }
    
    public ArrayList getPageItemList()
    {
        return pageItemList;
    }
    
    public HashMap getPageItemToTypeMap()
    {
        return pageItemToTypeMap;
    }

    public HashMap getStaticTextItemMap()
    {
        return staticTextItemMap;
    }

    public HashMap getTextItemMap()
    {
        return textItemMap;
    }

    public HashMap getTableItemMap()
    {
        return tableItemMap;
    }

    public HashMap getImageItemMap()
    {
        return imageItemMap;
    }

    public HashMap getTextLabelComboItemMap()
    {
        return textLabelComboItemMap;
    }

    
    public Element getRootElement()
    {
        return doc.getRootElement();
    }

    
    public void buildPageItemLists()
    {
        nameList = new ArrayList(5);
        Element theRootElement = getRootElement();
        List pageContent = theRootElement.getContent();
        pageName = theRootElement.getAttribute("NAME").getValue();
        pageVersion = theRootElement.getAttribute("VERSION").getValue();
        String multipage = theRootElement.getAttribute("MULTIPAGE").getValue();
        String gridColCount = theRootElement.getAttribute("GRIDCOUNT").getValue();
        pageOrientation = theRootElement.getAttribute("PAGEORIENTATION").getValue();
        String tableLink = null;
        gridCount = Integer.parseInt(gridColCount);
        if ( multipage.equalsIgnoreCase("Y") == true ) {
            multiPage = true;
        } else {
            multiPage = false;
        }

        Iterator contentIterator = pageContent.iterator();
        while(contentIterator.hasNext())
        {
            Object obj = contentIterator.next();
            if (obj instanceof Element)
            {
                String elementItemName = ((Element)obj).getName();
                if (elementItemName.equalsIgnoreCase("STATICTEXTITEM") == true)
                {
                     Element staticTextElement = (Element)obj;
                     String itemType = elementItemName;
                     String itemName = staticTextElement.getAttribute("ITEMNAME").getValue();
                     String itemValue = staticTextElement.getAttribute("ITEMVALUE").getValue();
                     String itemRowPos = staticTextElement.getAttribute("ROWPOS").getValue();
                     String itemColPos = staticTextElement.getAttribute("COLPOS").getValue();
                     String fillDown = staticTextElement.getAttribute("FILLDOWN").getValue();
                     String fillMaxRows = staticTextElement.getAttribute("FILLMAXROWS").getValue();
                     String editable = staticTextElement.getAttribute("EDITABLE").getValue();
                     String foreColour = staticTextElement.getAttribute("FORECOLOUR").getValue();
                     String backColour = staticTextElement.getAttribute("BACKCOLOUR").getValue();
                     String fontType = staticTextElement.getAttribute("FONTTYPE").getValue();
                     String fontSize = staticTextElement.getAttribute("FONTSIZE").getValue();
                     String fontStyle = staticTextElement.getAttribute("FONTSTYLE").getValue();
                     if (staticTextElement.getAttribute("TABLEITEMNAME") != null)
                     {    
                        tableLink = staticTextElement.getAttribute("TABLEITEMNAME").getValue();
                     } else
                     {
                        tableLink = "null";
                     }
                     HashMap staticTextItem = new HashMap(1);
                     staticTextItem.put((Object)"ITEMTYPE",(Object)itemType);
                     staticTextItem.put((Object)"ITEMVALUE",(Object)itemValue);
                     staticTextItem.put((Object)"ROWPOS",(Object)itemRowPos);
                     staticTextItem.put((Object)"COLPOS",(Object)itemColPos);
                     staticTextItem.put((Object)"FILLDOWN",(Object)fillDown);
                     staticTextItem.put((Object)"FILLMAXROWS",(Object)fillMaxRows);
                     staticTextItem.put((Object)"EDITABLE",(Object)editable);
                     staticTextItem.put((Object)"FORECOLOUR",(Object)foreColour);
                     staticTextItem.put((Object)"BACKCOLOUR",(Object)backColour);
                     staticTextItem.put((Object)"FONTTYPE",(Object)fontType);
                     staticTextItem.put((Object)"FONTSIZE",(Object)fontSize);
                     staticTextItem.put((Object)"FONTSTYLE",(Object)fontStyle);
                     if (tableLink != null)
                     {
                        staticTextItem.put((Object)"TABLELINK",(Object)tableLink);
                     } else
                     {
                        staticTextItem.put((Object)"TABLELINK",(Object)"null"); 
                     }
                     staticTextItemMap.put((Object)itemName, (Object)staticTextItem.clone());
                     pageItemToTypeMap.put((Object)itemName,(Object)"STATICTEXTITEM");
                     pageItemList.add((Object)itemName);
                } else
                if ((elementItemName.equalsIgnoreCase("TEXTITEM") == true) || (elementItemName.equalsIgnoreCase("LITERALTEXTITEM") == true))
                {
                     Element staticTextElement = (Element)obj;
                     String itemType = elementItemName;
                     String itemName = staticTextElement.getAttribute("ITEMNAME").getValue();
                     String itemRowPos = staticTextElement.getAttribute("ROWPOS").getValue();
                     String itemColPos = staticTextElement.getAttribute("COLPOS").getValue();
                     String fillDown = staticTextElement.getAttribute("FILLDOWN").getValue();
                     String fillMaxRows = staticTextElement.getAttribute("FILLMAXROWS").getValue();
                     String editable = staticTextElement.getAttribute("EDITABLE").getValue();
                     String foreColour = staticTextElement.getAttribute("FORECOLOUR").getValue();
                     String backColour = staticTextElement.getAttribute("BACKCOLOUR").getValue();
                     String fontType = staticTextElement.getAttribute("FONTTYPE").getValue();
                     String fontSize = staticTextElement.getAttribute("FONTSIZE").getValue();
                     String fontStyle = staticTextElement.getAttribute("FONTSTYLE").getValue();
                     
                     HashMap textItem = new HashMap(1);
                     textItem.put((Object)"ITEMTYPE",(Object)itemType);
                     textItem.put((Object)"ROWPOS",(Object)itemRowPos);
                     textItem.put((Object)"COLPOS",(Object)itemColPos);
                     textItem.put((Object)"FILLDOWN",(Object)fillDown);
                     textItem.put((Object)"FILLMAXROWS",(Object)fillMaxRows);
                     textItem.put((Object)"EDITABLE",(Object)editable);
                     textItem.put((Object)"FORECOLOUR",(Object)foreColour);
                     textItem.put((Object)"BACKCOLOUR",(Object)backColour);
                     textItem.put((Object)"FONTTYPE",(Object)fontType);
                     textItem.put((Object)"FONTSIZE",(Object)fontSize);
                     textItem.put((Object)"FONTSTYLE",(Object)fontStyle);
                     
                     textItemMap.put((Object)itemName, (Object)textItem.clone());
                     if (elementItemName.equalsIgnoreCase("TEXTITEM") == true)
                     {
                         pageItemToTypeMap.put((Object)itemName,(Object)"TEXTITEM");
                     } else
                     {
                        pageItemToTypeMap.put((Object)itemName,(Object)"LITERALTEXTITEM"); 
                     }
                     pageItemList.add((Object)itemName);
                    
                }  else
                if (elementItemName.equalsIgnoreCase("TEXTLABELCOMBOITEM") == true)
                {
                     Element staticTextElement = (Element)obj;
                     String itemType = elementItemName;
                     String itemName = staticTextElement.getAttribute("ITEMNAME").getValue();
                     String itemRowPos = staticTextElement.getAttribute("ROWPOS").getValue();
                     String itemColPos = staticTextElement.getAttribute("COLPOS").getValue();
                     String labelTextFlow = staticTextElement.getAttribute("LABELTEXTFLOW").getValue();
                     String labelFirst = staticTextElement.getAttribute("LABELFIRST").getValue();
                     String editable = staticTextElement.getAttribute("EDITABLE").getValue();
                     String foreColour = staticTextElement.getAttribute("FORECOLOUR").getValue();
                     String backColour = staticTextElement.getAttribute("BACKCOLOUR").getValue();
                     String labelForeColour = staticTextElement.getAttribute("LABELFORECOLOUR").getValue();
                     String labelBackColour = staticTextElement.getAttribute("LABELBACKCOLOUR").getValue();
                     String fontType = staticTextElement.getAttribute("FONTTYPE").getValue();
                     String fontSize = staticTextElement.getAttribute("FONTSIZE").getValue();
                     String fontStyle = staticTextElement.getAttribute("FONTSTYLE").getValue();
                     String labelFontType = staticTextElement.getAttribute("LABELFONTTYPE").getValue();
                     String labelFontSize = staticTextElement.getAttribute("LABELFONTSIZE").getValue();
                     String labelFontStyle = staticTextElement.getAttribute("LABELFONTSTYLE").getValue();
                     tableLink = staticTextElement.getAttribute("TABLEITEMNAME").getValue();
                     HashMap textItem = new HashMap(1);
                     textItem.put((Object)"ITEMTYPE",(Object)itemType);
                     textItem.put((Object)"ROWPOS",(Object)itemRowPos);
                     textItem.put((Object)"COLPOS",(Object)itemColPos);
                     textItem.put((Object)"LABELTEXTFLOW",(Object)labelTextFlow);
                     textItem.put((Object)"LABELFIRST",(Object)labelFirst);
                     textItem.put((Object)"EDITABLE",(Object)editable);
                     textItem.put((Object)"LABELFORECOLOUR",(Object)labelForeColour);
                     textItem.put((Object)"LABELBACKCOLOUR",(Object)labelBackColour);
                     textItem.put((Object)"FORECOLOUR",(Object)foreColour);
                     textItem.put((Object)"BACKCOLOUR",(Object)backColour);
                     textItem.put((Object)"LABELFONTTYPE",(Object)labelFontType);
                     textItem.put((Object)"LABELFONTSIZE",(Object)labelFontSize);
                     textItem.put((Object)"LABELFONTSTYLE",(Object)labelFontStyle);
                     textItem.put((Object)"FONTTYPE",(Object)fontType);
                     textItem.put((Object)"FONTSIZE",(Object)fontSize);
                     textItem.put((Object)"FONTSTYLE",(Object)fontStyle);
                     textItem.put((Object)"TABLELINK",(Object)tableLink);
                     textLabelComboItemMap.put((Object)itemName, (Object)textItem.clone());
                     pageItemToTypeMap.put((Object)itemName,(Object)"TEXTLABELCOMBOITEM"); 
                     pageItemList.add((Object)itemName);
                    
                } else
                if (elementItemName.equalsIgnoreCase("TABLEITEM") == true)
                {
                     Element tableElement = (Element)obj;
                     String itemType = elementItemName;
                     String itemName = tableElement.getAttribute("ITEMNAME").getValue();
                     String itemRowPos = tableElement.getAttribute("ROWPOS").getValue();
                     String itemColPos = tableElement.getAttribute("COLPOS").getValue();
                     String colCount = tableElement.getAttribute("COLCOUNT").getValue();
                     //GET COLUMN NAMES
                     String colNames = tableElement.getAttribute("COLNAMES").getValue();
                     StringTokenizer colNamesTokens = new StringTokenizer(colNames,",");
                     int colItemCount = colNamesTokens.countTokens();
                     ArrayList itemNameList = new ArrayList(colItemCount);
                     for (int colNameCount = 0; colNameCount < colItemCount; colNameCount++)
                     {
                         String colNameItem = (String)colNamesTokens.nextToken();
                         itemNameList.add((Object)colNameItem.toUpperCase());
                         
                     }
                     //GET COLUMN FOREGROUND COLOURS
                     String colForegroundItems = tableElement.getAttribute("COLFORECOLOURS").getValue();
                     StringTokenizer colForegroundTokens = new StringTokenizer(colForegroundItems,",");
                     int colForeItemCount = colForegroundTokens.countTokens();
                     ArrayList itemColForegroundList = new ArrayList(colForeItemCount);
                     for (int colForeCount = 0; colForeCount < colForeItemCount; colForeCount++)
                     {
                         String colForeItem = (String)colForegroundTokens.nextToken();
                         itemColForegroundList.add((Object)colForeItem.toUpperCase());
                     }
                     //GET COLUMN BACKGROUND COLOURS
                     String colBackgroundItems = tableElement.getAttribute("COLBACKCOLOURS").getValue();
                     StringTokenizer colBackgroundTokens = new StringTokenizer(colBackgroundItems,",");
                     int colBackItemCount = colBackgroundTokens.countTokens();
                     ArrayList itemColBackgroundList = new ArrayList(colBackItemCount);
                     for (int colBackCount = 0; colBackCount < colBackItemCount; colBackCount++)
                     {
                         String colBackItem = (String)colBackgroundTokens.nextToken();
                         itemColBackgroundList.add((Object)colBackItem.toUpperCase());
                     }
                     //GET HEADING FOREGROUND COLOURS
                     String colHeadingForegroundItems = tableElement.getAttribute("COLHEADINGFORECOLOURS").getValue();
                     StringTokenizer colHeadingForegroundTokens = new StringTokenizer(colHeadingForegroundItems,",");
                     int colHeadingForeItemCount = colHeadingForegroundTokens.countTokens();
                     ArrayList itemColHeadingForegroundList = new ArrayList(colHeadingForeItemCount);
                     for (int colHeadingForeCount = 0; colHeadingForeCount < colHeadingForeItemCount; colHeadingForeCount++)
                     {
                         String colHeadingForeItem = (String)colHeadingForegroundTokens.nextToken();
                         itemColHeadingForegroundList.add((Object)colHeadingForeItem.toUpperCase());
                     }
                     //GET HEADING BACKGROUND COLOURS
                     String colHeadingBackgroundItems = tableElement.getAttribute("COLHEADINGFORECOLOURS").getValue();
                     StringTokenizer colHeadingBackgroundTokens = new StringTokenizer(colHeadingBackgroundItems,",");
                     int colHeadingBackItemCount = colHeadingBackgroundTokens.countTokens();
                     ArrayList itemColHeadingBackgroundList = new ArrayList(colHeadingBackItemCount);
                     for (int colHeadingBackCount = 0; colHeadingBackCount < colHeadingBackItemCount; colHeadingBackCount++)
                     {
                         String colHeadingBackItem = (String)colHeadingBackgroundTokens.nextToken();
                         itemColHeadingBackgroundList.add((Object)colHeadingBackItem.toUpperCase());
                     }
                     String gridWidth = tableElement.getAttribute("GRIDWIDTH").getValue();
                     HashMap tableItem = new HashMap(1);
                     tableItem.put((Object)"ITEMTYPE",(Object)itemType);
                     tableItem.put((Object)"ROWPOS",(Object)itemRowPos);
                     tableItem.put((Object)"COLPOS",(Object)itemColPos);
                     tableItem.put((Object)"COLCOUNT",(Object)colCount);
                     tableItem.put((Object)"COLNAMELIST",(Object)itemNameList);
                     tableItem.put((Object)"COLFOREGROUNDLIST",(Object)itemColForegroundList);
                     tableItem.put((Object)"COLBACKGROUNDLIST",(Object)itemColBackgroundList);
                     tableItem.put((Object)"COLHEADINGFOREGROUNDLIST",(Object)itemColHeadingForegroundList);
                     tableItem.put((Object)"COLHEADINGBACKGROUNDLIST",(Object)itemColHeadingBackgroundList);
                     tableItem.put((Object)"GRIDWIDTH",(Object)gridWidth);
                     tableItemMap.put((Object)itemName, (Object)tableItem.clone());
                     pageItemToTypeMap.put((Object)itemName,(Object)"TABLEITEM");
                     pageItemList.add((Object)itemName);
                    
                } else
                if (elementItemName.equalsIgnoreCase("IMAGEITEM") == true)
                {
                     Element staticTextElement = (Element)obj;
                     String itemType = elementItemName;
                     String itemName = staticTextElement.getAttribute("ITEMNAME").getValue();
                     String itemRowPos = staticTextElement.getAttribute("ROWPOS").getValue();
                     String itemColPos = staticTextElement.getAttribute("COLPOS").getValue();
                     String maxWidth = staticTextElement.getAttribute("MAXWIDTH").getValue();
                     String maxHeight = staticTextElement.getAttribute("MAXHEIGHT").getValue();
                     String imageNegative = staticTextElement.getAttribute("IMAGENEGATIVE").getValue();
                     String imageChroma = staticTextElement.getAttribute("IMAGECHROMA").getValue();
                     String imageBorder = staticTextElement.getAttribute("IMAGEBORDER").getValue();
                     HashMap imageItem = new HashMap(1);
                     imageItem.put((Object)"ITEMTYPE",(Object)itemType);
                     imageItem.put((Object)"ROWPOS",(Object)itemRowPos);
                     imageItem.put((Object)"COLPOS",(Object)itemColPos);
                     imageItem.put((Object)"MAXWIDTH",(Object)maxWidth);
                     imageItem.put((Object)"MAXHEIGHT",(Object)maxHeight);
                     imageItem.put((Object)"IMAGENEGATIVE",(Object)imageNegative);
                     imageItem.put((Object)"IMAGECHROMA",(Object)imageChroma);
                     imageItem.put((Object)"IMAGEBORDER",(Object)imageBorder);
                     imageItemMap.put((Object)itemName, (Object)imageItem.clone());
                     pageItemToTypeMap.put((Object)itemName,(Object)"IMAGEITEM");
                     pageItemList.add((Object)itemName);
                }
            }
            
        }
    }
    
}
