package motherteresa;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;

//  A Simple DOM Application
public class BasicDOM {
	
	// Constructor
	public BasicDOM (String xmlFile) {
		
		//  Create a Xerces DOM Parser
		DOMParser parser = new DOMParser();

		//  Parse the Document		
		//  and traverse the DOM
		try {
			parser.parse(xmlFile);
			Document document = parser.getDocument();
			traverse (document);
		} catch (SAXException e) {
			System.err.println (e);
		} catch (IOException e) {
  			System.err.println (e);
		}
	}
	
	//  Traverse DOM Tree.  Print out Element Names
	private void traverse (Node node) {
		int type = node.getNodeType();
		if (type == Node.ELEMENT_NODE)
			System.out.println (node.getNodeName());
		NodeList children = node.getChildNodes();
		if (children != null) {
			for (int i=0; i< children.getLength(); i++) 
				traverse (children.item(i));	
		}
	}
	
	// Main Method
	public static void main (String[] args) {
		BasicDOM basicDOM = new  BasicDOM (InfoManager.TARGET_VOLUME + "\\Mother Teresa\\xml files\\systemic.xml");
	}
}
 