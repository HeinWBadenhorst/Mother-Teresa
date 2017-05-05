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
public class DynamicMenuBuilder
{
    private  DOMBuilder builder = null;
    private  Document doc = null;
    private  ArrayList nameList = null;
    private  ArrayList iconList = null;
    private  ArrayList typeList = null;
    private  ArrayList posList = null;
    private  ArrayList updateNumberList = null;
    private  boolean nameFound = false;
    private  boolean iconFound = false;
    private  boolean posFound = false;
    private  boolean typeFound = false;
    private boolean updateNumberFound = false;
    private String methodValue = null;
    private String updateNumberValue = null;
    private String XMLPath = InfoManager.DISPLAY_XML_PATH;
    
    /** Creates a new instance of DynamicMenuBuilder */
    public DynamicMenuBuilder() 
    {
        int result = initDOMTree(XMLPath,"test.xml");
    }

    public DynamicMenuBuilder(String XMLDocPath, String XMLDocName)
    {
        int result = initDOMTree(XMLDocPath, XMLDocName);
    }
    
    public DynamicMenuBuilder(String XMLDocName)
    {
        int result = initDOMTree(XMLDocName);
    }

    public int initDOMTree(String pathName, String fileName)
    {
        try 
        {
            builder = new DOMBuilder();
            doc = builder.build( new File(pathName + fileName));
        } catch (JDOMException e) 
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int initDOMTree(String fileName)
    {
        try 
        {
            builder = new DOMBuilder();
            doc = builder.build( new File(fileName));
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

    public String getMethodValue()
    {
        return methodValue;
    }

    public ArrayList getNameList()
    {
        return nameList;
    }

    public ArrayList getUpdateNumberList()
    {
        return updateNumberList;
    }

    public ArrayList getIconList()
    {
        return iconList;
    }

    public String getUpdateNumberValue()
    {
        return updateNumberValue;
    }

    public ArrayList getPosList()
    {
        return posList;
    }

    public ArrayList getTypeList()
    {
        return typeList;
    }

    public Element getRootElement()
    {
        return doc.getRootElement();
    }

    public ArrayList getMenuNames(Element currentElement)
    {
        nameList = new ArrayList(5);
        List content = currentElement.getContent();
        Iterator iterator = content.iterator();
        while(iterator.hasNext())
        {
            Object obj = iterator.next();
            if (obj instanceof Element)
            {
                Element childElement = (Element)obj;
                Attribute attribute = childElement.getAttribute("NAME");
                if (attribute != null)
                {
                     String value = attribute.getValue();
                     nameList.add((Object)value);
                }
            }
            
        }
        return nameList;
    }

    public ArrayList getMenuIcons(Element currentElement)
    {
        iconList = new ArrayList(5);
        List content = currentElement.getContent();
        Iterator iterator = content.iterator();
        while(iterator.hasNext())
        {
            Object obj = iterator.next();
            if (obj instanceof Element)
            {
                Element childElement = (Element)obj;
                Attribute attribute = childElement.getAttribute("ICON");
                if (attribute != null)
                {
                     String value = attribute.getValue();
                     iconList.add((Object)value);
                }
            }
            
        }
        return iconList;
    }

    public void getMenuNames(Element rootElement, String elementName)
    {
        String rootElementName = null;
        nameFound = false;
        List content = rootElement.getContent();
        String tmpElementName = rootElement.getName();
        if (tmpElementName.equalsIgnoreCase("MENUITEM") == true)
        {
            rootElementName = rootElement.getAttribute("NAME").getValue();
        }
        if ((rootElementName != null) && (rootElementName.equalsIgnoreCase(elementName) == true)) 
        {
            Iterator iterator = content.iterator();
            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                if (obj instanceof Element)
                {
                    Element childElement = (Element)obj;
                    Attribute attribute = childElement.getAttribute("NAME");
                    if (attribute != null)
                    {
                        String value = attribute.getValue();
                        nameList.add((Object)value);
                    }
                }
            }
            nameFound = true;
        } else
        {
            Iterator theIterator = content.iterator();
            while((theIterator.hasNext()) && (nameFound == false))
            {
                Object obj = theIterator.next();
                if (obj instanceof Element)
                {
                    Element childElement = (Element)obj;
                    nameList.clear();
                    getMenuNames(childElement, elementName); 
                }
            }

        }
    }

    public void getMenuIcons(Element rootElement, String elementName)
    {
        String rootElementName = null;
        iconFound = false;
        List content = rootElement.getContent();
        String tmpElementName = rootElement.getName();
        if (tmpElementName.equalsIgnoreCase("MENUITEM") == true)
        {
            rootElementName = rootElement.getAttribute("NAME").getValue();
        }
        if ((rootElementName != null) && (rootElementName.equalsIgnoreCase(elementName) == true)) 
        {
            Iterator iterator = content.iterator();
            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                if (obj instanceof Element)
                {
                    Element childElement = (Element)obj;
                    Attribute attribute = childElement.getAttribute("ICON");
                    if (attribute != null)
                    {
                        String value = attribute.getValue();
                        iconList.add((Object)value);
                    }
                }
            }
            iconFound = true;
        } else
        {
            Iterator theIterator = content.iterator();
            while((theIterator.hasNext()) && (iconFound == false))
            {
                Object obj = theIterator.next();
                if (obj instanceof Element)
                {
                    Element childElement = (Element)obj;
                    iconList.clear();
                    getMenuIcons(childElement, elementName); 
                }
            }

        }
    }
    
    public void getMethodName(Element rootElement, String elementName)
    {
        String rootElementName = null;
        nameFound = false;
        List content = rootElement.getContent();
        String tmpElementName = rootElement.getName();
        if (tmpElementName.equalsIgnoreCase("MENUITEM") == true)
        {
            rootElementName = rootElement.getAttribute("NAME").getValue();
        }
        if ((rootElementName != null) && (rootElementName.equalsIgnoreCase(elementName) == true)) 
        {
            
            Iterator iterator = content.iterator();
            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                Element tempElement = null;
                if (obj instanceof Element)
                {
                   tempElement = (Element)obj;
                }
                if ((obj instanceof Element) && (tempElement.getName().equalsIgnoreCase("METHODNAME") == true ))
                {
                    Attribute attribute = tempElement.getAttribute("VALUE");
                    if (attribute != null)
                    {
                        methodValue = attribute.getValue();
                        return;
                    }
                }
            }
            nameFound = true;
        } else
        {
            Iterator theIterator = content.iterator();
            while((theIterator.hasNext()) && (nameFound == false))
            {
                Object obj = theIterator.next();
                if (obj instanceof Element)
                {
                    Element childElement = (Element)obj;
                    getMethodName(childElement, elementName); 
                }
            }
        }
        return;
    }

    public ArrayList getMenuTypes(Element currentElement)
    {
        typeList = new ArrayList(5);
        List content = currentElement.getContent();
        Iterator iterator = content.iterator();
        while(iterator.hasNext())
        {
            Object obj = iterator.next();
            if (obj instanceof Element)
            {
                Element childElement = (Element)obj;
                Attribute attribute = childElement.getAttribute("TYPE");
                if (attribute != null)
                {
                     String value = attribute.getValue();
                     typeList.add((Object)value);
                }
            }
            
        }
        return typeList;
    }

    public void getMenuTypes(Element rootElement, String elementName)
    {
        String rootElementName = null;
        typeFound = false;
        List content = rootElement.getContent();
        String tmpElementName = rootElement.getName();
        if (tmpElementName.equalsIgnoreCase("MENUITEM") == true)
        {
            rootElementName = rootElement.getAttribute("NAME").getValue();
        }
        if ((rootElementName != null) && (rootElementName.equalsIgnoreCase(elementName) == true)) 
        {
            Iterator iterator = content.iterator();
            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                if (obj instanceof Element)
                {
                    Element childElement = (Element)obj;
                    Attribute attribute = childElement.getAttribute("TYPE");
                    if (attribute != null)
                    {
                        String value = attribute.getValue();
                        typeList.add((Object)value);
                    }
                }
            }
            typeFound = true;
        } else
        {
            Iterator theIterator = content.iterator();
            while((theIterator.hasNext()) && (typeFound == false))
            {
                Object obj = theIterator.next();
                if (obj instanceof Element)
                {
                    Element childElement = (Element)obj;
                    typeList.clear();
                    getMenuTypes(childElement, elementName); 
                }
            }

        }
    }

    public ArrayList getMenuPos(Element currentElement)
    {
        posList = new ArrayList(5);
        List content = currentElement.getContent();
        Iterator iterator = content.iterator();
        while(iterator.hasNext())
        {
            Object obj = iterator.next();
            if (obj instanceof Element)
            {
                Element childElement = (Element)obj;
                Attribute attribute = childElement.getAttribute("MENUPOS");
                if (attribute != null)
                {
                     String value = attribute.getValue();
                     posList.add((Object)value);
                }
            }
            
        }
        return posList;
    }

    public void getMenuPos(Element rootElement, String elementName)
    {
        String rootElementName = null;
        posFound = false;
        List content = rootElement.getContent();
        String tmpElementName = rootElement.getName();
        if (tmpElementName.equalsIgnoreCase("MENUITEM") == true)
        {
            rootElementName = rootElement.getAttribute("NAME").getValue();
        }
        if ((rootElementName != null) && (rootElementName.equalsIgnoreCase(elementName) == true)) 
        {
            Iterator iterator = content.iterator();
            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                if (obj instanceof Element)
                {
                    Element childElement = (Element)obj;
                    Attribute attribute = childElement.getAttribute("MENUPOS");
                    if (attribute != null)
                    {
                        String value = attribute.getValue();
                        posList.add((Object)value);
                    }
                }
            }
            posFound = true;
        } else
        {
            Iterator theIterator = content.iterator();
            while((theIterator.hasNext()) && (posFound == false))
            {
                Object obj = theIterator.next();
                if (obj instanceof Element)
                {
                    Element childElement = (Element)obj;
                    posList.clear();
                    getMenuPos(childElement, elementName); 
                }
            }

        }
    }

    public ArrayList getUpdateNumber(Element currentElement)
    {
        updateNumberList = new ArrayList(5);
        List content = currentElement.getContent();
        Iterator iterator = content.iterator();
        while(iterator.hasNext())
        {
            Object obj = iterator.next();
            if (obj instanceof Element)
            {
                Element childElement = (Element)obj;
                Attribute attribute = childElement.getAttribute("UPDATENUMBER");
                if (attribute != null)
                {
                     updateNumberValue = attribute.getValue();
                     updateNumberList.add((Object)updateNumberValue);
                }
            }
            
        }
        return updateNumberList;
    }

    public void getUpdateNumber(Element rootElement, String elementName)
    {
        String rootElementName = null;
        updateNumberFound = false;
        List content = rootElement.getContent();
        String tmpElementName = rootElement.getName();
        if (tmpElementName.equalsIgnoreCase("MENUITEM") == true)
        {
            rootElementName = rootElement.getAttribute("NAME").getValue();
        }
        if ((rootElementName != null) && (rootElementName.equalsIgnoreCase(elementName) == true)) 
        {
            Iterator iterator = content.iterator();
            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                if (obj instanceof Element)
                {
                    Element childElement = (Element)obj;
                    Attribute attribute = childElement.getAttribute("UPDATENUMBER");
                    if (attribute != null)
                    {
                        updateNumberValue = attribute.getValue();
                        updateNumberList.add((Object)updateNumberValue);
                    }
                }
            }
            updateNumberFound = true;
        } else
        {
            Iterator theIterator = content.iterator();
            while((theIterator.hasNext()) && (updateNumberFound == false))
            {
                Object obj = theIterator.next();
                if (obj instanceof Element)
                {
                    Element childElement = (Element)obj;
                    updateNumberList.clear();
                    getUpdateNumber(childElement, elementName); 
                }
            }

        }
    }
    
    public ArrayList getMenuAttribs(Element currentElement)
    {
        ArrayList attribList = new ArrayList(5);
        nameList = new ArrayList(5);
        iconList = new ArrayList(5);
        posList = new ArrayList(5); 
        typeList = new ArrayList(5); 
        updateNumberList = new ArrayList(5); 

        nameList = getMenuNames(currentElement);
        iconList = getMenuIcons(currentElement);
        typeList = getMenuTypes(currentElement);
        posList = getMenuPos(currentElement);
        updateNumberList = getUpdateNumber(currentElement);
        
        
        for (int loop = 0; loop < nameList.size(); loop++)
        {
            ArrayList tempList = new ArrayList(5);
            tempList.add((Object)nameList.get(loop));
            tempList.add((Object)iconList.get(loop));
            tempList.add((Object)typeList.get(loop));
            tempList.add((Object)posList.get(loop));
            tempList.add((Object)updateNumberList.get(loop));
            attribList.add((Object)tempList);
        }
        nameList = new ArrayList(5);
        iconList = new ArrayList(5);
        posList = new ArrayList(5); 
        typeList = new ArrayList(5);
        updateNumberList = new ArrayList(5); 
        return attribList;
    }

    public ArrayList getMenuAttribs(String currentElementName)
    {
        ArrayList attribList = new ArrayList(5);
        ArrayList myNameList = new ArrayList(5); 
        ArrayList myIconList = new ArrayList(5); 
        ArrayList myPosList = new ArrayList(5); 
        ArrayList myTypeList = new ArrayList(5); 
        ArrayList myUpdateNumberList = new ArrayList(5); 
        getMenuNames(getRootElement(), currentElementName);
        myNameList = getNameList();
        getMenuIcons(getRootElement(), currentElementName);
        myIconList = getIconList();
        getMenuTypes(getRootElement(), currentElementName);
        myTypeList = getTypeList();
        getMenuPos(getRootElement(), currentElementName);
        myPosList = getPosList();
        getUpdateNumber(getRootElement(), currentElementName);
        myUpdateNumberList = getUpdateNumberList();
        
        for (int loop = 0; loop < nameList.size(); loop++)
        {
            ArrayList tempList = new ArrayList(5);
            tempList.add((Object)myNameList.get(loop));
            tempList.add((Object)myIconList.get(loop));
            tempList.add((Object)myTypeList.get(loop));
            tempList.add((Object)myPosList.get(loop));
            tempList.add((Object)myUpdateNumberList.get(loop));
            attribList.add((Object)tempList);
        }
        myNameList = new ArrayList(5); 
        myIconList = new ArrayList(5); 
        myPosList = new ArrayList(5);
        myUpdateNumberList = new ArrayList(5);
        myTypeList = new ArrayList(5); 
        return attribList;
    }

    
}
