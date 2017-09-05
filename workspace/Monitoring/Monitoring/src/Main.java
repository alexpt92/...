import java.io.BufferedReader;
import java.io.File;
//import static java.lang.Math.toIntExact;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipFile;

import javax.jws.soap.InitParam;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

	public static void main(String[] args) {
		try {
			// XMLReader erzeugen
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();

			// ZipFile f = new
			// ZipFile("http://dblp.uni-trier.de/xml/dblp.xml.gz");

			// Pfad zur XML Datei
			FileReader reader = new FileReader("/home/kiesant/Downloads/dblp(1).xml");
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
		
		/*JFrame frame = new JFrame("Controlling");
		frame.setVisible(true);
		frame.setSize(500,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GUI gui = new GUI();
		frame.add(gui);
		*/
		
		
	}
}

