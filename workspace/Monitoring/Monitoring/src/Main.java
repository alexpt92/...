import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;



public class Main {
	private static long time;

  public static void main(String[] args) {
	    try {
	        // XMLReader erzeugen
	        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
	        
	        // Pfad zur XML Datei
	        FileReader reader = new FileReader("Pfad XML");
	        InputSource inputSource = new InputSource(reader);

	        // DTD kann optional übergeben werden
	        inputSource.setSystemId("Pfad .dtd");

	        // PersonenContentHandler wird übergeben
	        xmlReader.setContentHandler(new ConfigHandler());

	        // Parsen wird gestartet
	        xmlReader.parse(inputSource);
	      } catch (FileNotFoundException e) {
	        e.printStackTrace();
	      } catch (IOException e) {
	        e.printStackTrace();
	      } catch (SAXException e) {
	        e.printStackTrace();
	      }
	    
	    
	     class ConfigHandler extends DefaultHandler {
	    	  
	    	  //private ArrayList<Person> allePersonen = new ArrayList<Person>();
	    	  private String currentValue;
	    	//  private Person person;

	    	  // Aktuelle Zeichen die gelesen werden, werden in eine Zwischenvariable
	    	  // gespeichert
	    	  public void characters(char[] ch, int start, int length)
	    	      throws SAXException {
	    	    currentValue = new String(ch, start, length);
	    	  }

	    	  // Methode wird aufgerufen wenn der Parser zu einem Start-Tag kommt
	    	  public void startElement(String uri, String localName, String qName,
	    	      Attributes atts) throws SAXException {
	    	    if (localName.equals("person")) {
	    	      // Neue Person erzeugen
	    	   //   person = new Person();

	    	      // Attribut id wird in einen Integer umgewandelt und dann zu der
	    	      // jeweiligen Person gesetzt
	    	    //  person.setId(Integer.parseInt(atts.getValue("id")));
	    	    }
	    	  }

	    	  // Methode wird aufgerufen wenn der Parser zu einem End-Tag kommt
	    	  public void endElement(String uri, String localName, String qName)
	    	      throws SAXException {

	    	    // Name setzen
	    	    if (localName.equals("name")) {
	    	  //    person.setName(currentValue);
	    	    }

	    	    // Vorname setzen
	    	    if (localName.equals("vorname")) {
	    	 //     person.setVorname(currentValue);
	    	    }

	    	    // Datum parsen und setzen
	    	    if (localName.equals("geburtsdatum")) {
	    	      SimpleDateFormat datumsformat = new SimpleDateFormat("dd.MM.yyyy");
	    	      try {
	    	        Date date = datumsformat.parse(currentValue);
	    	     //   person.setGeburtsdatum(date);
	    	      } catch (ParseException e) {
	    	        e.printStackTrace();
	    	      }
	    	    }

	    	    // Postleitzahl setzen
	    	    if (localName.equals("postleitzahl")) {
	    	    //  person.setPostleitzahl(currentValue);
	    	    }

	    	    // Ort setzen
	    	    if (localName.equals("ort")) {
	    	   //   person.setOrt(currentValue);
	    	    }

	    	    // Person in Personenliste abspeichern falls Person End-Tag erreicht
	    	    // wurde.
	    	    if (localName.equals("person")) {
	    	    //  allePersonen.add(person);
	    	    //  System.out.println(person);
	    	    }
	    	  }

	    	  public void endDocument() throws SAXException {}
	    	  public void endPrefixMapping(String prefix) throws SAXException {}
	    	  public void ignorableWhitespace(char[] ch, int start, int length)
	    	      throws SAXException {}
	    	  public void processingInstruction(String target, String data)
	    	      throws SAXException {}
	    	  public void setDocumentLocator(Locator locator) {  }
	    	  public void skippedEntity(String name) throws SAXException {}
	    	  
	    	  public void startDocument() throws SAXException {			
	    		  System.out.println("Document starts.");
	    		time = System.currentTimeMillis();}
	    	  
	    	  public void startPrefixMapping(String prefix, String uri)
	    	    throws SAXException {}
	    	} }
  }
