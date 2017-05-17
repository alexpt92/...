import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.regex.Pattern;
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

public class Maain {

	public static void main(String[] args) {
		try {
			// XMLReader erzeugen
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();

			// Pfad zur XML Datei
			FileReader reader = new FileReader(
					"/home/kiesant/Downloads/dblp.xml");
			InputSource inputSource = new InputSource(reader);

			// DTD kann optional übergeben werden
			inputSource.setSystemId("/home/kiesant/Downloads/dblp.dtd");

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
	}
}

class ConfigHandler extends DefaultHandler {
	// Map<String, Date> mapeins = new HashMap<String, Date>();
	Map<String, String> mapzwei = new HashMap<String, String>();
	String aktuellerStream;
	StringBuilder textContent = new StringBuilder();

	int counter;
	int titlecounter;
	private static long time;

	public boolean insideStream = false, insideTitle = false,
			insideProceeding = false;
	// private ArrayList<Person> allePersonen = new ArrayList<Person>();
	private String currentValue;

	// private Person person;

	// Aktuelle Zeichen die gelesen werden, werden in eine Zwischenvariable
	// gespeichert
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (insideTitle && insideStream && insideProceeding) {
			currentValue = new String(ch, start, length);
			textContent.append(ch, start, length);

			String[] parts = currentValue.split(",");
			// String month = parts[6]; // 004
			// String year = parts[7];
			// int jahr= Integer.parseInt(year);
			// mapzwei.put(aktuellerStream, currentValue);
		}
	}

	// Methode wird aufgerufen wenn der Parser zu einem Start-Tag kommt
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (qName.equals("proceedings")) {
			insideProceeding = true;

			if (atts.getValue("key") != null) {
				String key = atts.getValue("key");
				aktuellerStream = key;
				// System.out.print(aktuellerStream+" --> ");

			}

			if (Pattern.matches(("conf/.*/\\d\\d\\d\\d.*"), aktuellerStream)) {

				// System.out.print(aktuellerStream+" --> ");
				// System.out.println(textContent.toString());
				counter++;

				insideStream = true;

			}
		}

		if (qName == "title" && insideStream) {
			insideTitle = true;
			titlecounter++;

		}

		// Attribut id wird in einen Integer umgewandelt und dann zu der
		// jeweiligen Person gesetzt
		// person.setId(Integer.parseInt(atts.getValue("id")));
	}

	// Methode wird aufgerufen wenn der Parser zu einem End-Tag kommt
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (qName.equals("title") && insideStream && insideProceeding) {
			insideTitle = false;
			insideProceeding = false;
			String text = textContent.toString();

			if (aktuellerStream != null) {
				String map;
				
				if(text.contains("January")){
					map=text.substring(text.indexOf("January"));
					mapzwei.put(aktuellerStream, map);
				}else{
					
				if(text.contains("February")){
					map=text.substring(text.indexOf("February"));
					mapzwei.put(aktuellerStream, map);
				}else{
					
				if(text.contains("March")){
					map=text.substring(text.indexOf("March"));
					mapzwei.put(aktuellerStream, map);
				}else{
					
				if(text.contains("April")){
					map=text.substring(text.indexOf("April"));
					mapzwei.put(aktuellerStream, map);
				}else{
					
				if(text.contains("May")){
					map=text.substring(text.indexOf("May"));
					mapzwei.put(aktuellerStream, map);
				}else{
					
				if(text.contains("June")){
					map=text.substring(text.indexOf("June"));
					mapzwei.put(aktuellerStream, map);
				}else{
					
				if(text.contains("July")){
					map=text.substring(text.indexOf("July"));
					mapzwei.put(aktuellerStream, map);
				}else{
					
				if(text.contains("August")){
					map=text.substring(text.indexOf("August"));
					mapzwei.put(aktuellerStream, map);
				}else{
					
				if(text.contains("September")){
					map=text.substring(text.indexOf("September"));
					mapzwei.put(aktuellerStream, map);
				}else{
					
				if(text.contains("October")){
					map=text.substring(text.indexOf("October"));
					mapzwei.put(aktuellerStream, map);
				}else{
					
				if(text.contains("November")){
					map=text.substring(text.indexOf("November"));
					mapzwei.put(aktuellerStream, map);
				}else{
					
				if(text.contains("December")){
					map=text.substring(text.indexOf("December"));
					mapzwei.put(aktuellerStream, map);
				}}}}}}}}}}}}
				
				
				
				
			}
			//System.out.println(text);
			textContent.delete(0, textContent.length());
		}
		if (insideStream
				&& (qName.equals("article") || qName.equals("inproceedings")
						|| qName.equals("proceedings") || qName.equals("book")
						|| qName.equals("incollection")
						|| qName.equals("phdthesis") || qName
							.equals("masterthesis"))) {
			if (qName.equals("proceedings")) {
				insideProceeding = false;
			}
			// streamName = "";
			insideStream = false;

		}
	}

	public void endDocument() throws SAXException {

		System.out.println("Anzahl der Tags: " + counter);
		System.out.println("Anzahl der titelTags: " + titlecounter);
		System.out.println("end");

		System.out.println("Laufzeit: " + time + "ms");
	}

	public void endPrefixMapping(String prefix) throws SAXException {
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	public void setDocumentLocator(Locator locator) {
	}

	public void skippedEntity(String name) throws SAXException {
	}

	public void startDocument() throws SAXException {
		System.out.println("Document starts.");

		time = System.currentTimeMillis();
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
	}
}
