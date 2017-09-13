import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipFile;

import javax.jws.soap.InitParam;

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

	public static void main(String[] args) throws ParseException {
		//
		try {
			// XMLReader erzeugen
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();

			// ZipFile f = new
			// ZipFile("http://dblp.uni-trier.de/xml/dblp.xml.gz");

			// Pfad zur XML Datei
			// TODO:Change Custom
			FileReader reader = new FileReader("H:/SelfWorkspace/dblp.xml");
			InputSource inputSource = new InputSource(reader);

			// DTD kann optional übergeben werden
			// TODO:Change Custom
			inputSource.setSystemId("H:/SelfWorkspace/dblp.dtd");

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

		// Maps____________________________________________________________
		Map<String, Date> alertMap = new HashMap<String, Date>();
		Map<String, Date> upcomingMap = new HashMap<String, Date>();

		BufferedReader bf;
		String line;

		// Read from MapFinalPrint.txt to alertMap || upcomingMap
		try {
			// TODO:Change Custom
			bf = new BufferedReader(
					new FileReader(
							"G:/Users/Christian/Dropbox/Universität/SS 2013/Programmierung II/Übungen/StudienProjekt/MapFinalPrint.txt"));
			while ((line = bf.readLine()) != null) {
				String[] a = line.split(";");
				Date now = new Date();
				now.setDate(now.getDate() + 3);
				if (a[2].contains("ALERT")) {
					DateFormat format = new SimpleDateFormat(
							" EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
					Date date = format.parse(a[1]);
					alertMap.put(a[0], date);
				} else {
					DateFormat format = new SimpleDateFormat(
							" EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
					Date date = format.parse(a[1]);
					if (date.before(now) && date.after(new Date())) {
						upcomingMap.put(a[0], date);
					}
				}
			}
			bf.close();
		} catch (IOException e) {

		}

		// Organize Maps
		Map<String, Date> finalAlert = new TreeMap<String, Date>(alertMap);
		Map<String, Date> finalUp = new TreeMap<String, Date>(upcomingMap);

		// Print finalAlert
		try {
			PrintStream ps;
			ps = new PrintStream(new File("Alert.txt"));

			for (Entry<String, Date> entry : finalAlert.entrySet()) {
				ps.println(entry.getKey() + " ;" + entry.getValue());
			}
			ps.close();
		} catch (FileNotFoundException h) {
			h.printStackTrace();
		}
		
		// Print finalUp
		try {
			PrintStream ps;
			ps = new PrintStream(new File("Upcoming.txt"));

			for (Entry<String, Date> entry : finalUp.entrySet()) {
				ps.println(entry.getKey() + " ;" + entry.getValue());
			}
			ps.close();
		} catch (FileNotFoundException h) {
			h.printStackTrace();
		}

		// GUI______________________________________________________________
		JFrame frame = new JFrame("Controlling");

		frame.setSize(500, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		// frame.getContentPane().add(BorderLayout.CENTER, panel);;

		JTextField upcoming = new JTextField("Upcoming:", 10);
		JTextField todo = new JTextField("TODO:", 10);
		JButton button = new JButton("Change Customdate");

		panel.setLayout(new BorderLayout());
		panel.add(upcoming, BorderLayout.CENTER);
		panel.add(todo, BorderLayout.SOUTH);
		panel.add(button, BorderLayout.EAST);
		frame.add(panel);
		frame.setVisible(true);

		button.addActionListener(new ActionListener() {
			Map<String, Date> customdate = new HashMap<String, Date>();

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					readCustomDate();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

				String t = "conf/"+ JOptionPane.showInputDialog("Bitte Kürzel der Conference eingeben")+ "/";

				int d = Integer.parseInt(JOptionPane.showInputDialog("Bitte Tag eingeben (1-31)"));
				int m = Integer.parseInt(JOptionPane.showInputDialog("Bitte Monat eingeben (1-12)"));
				int y = Integer.parseInt(JOptionPane.showInputDialog("Bitte Jahr eingeben"));

				for (Entry<String, Date> entry : customdate.entrySet()) {
					if (entry.getKey().equals(t)) {
						Date tmp = new Date(y - 1900, m - 1, d);
						entry.setValue(tmp);

					}
				}
				//organize Map
				Map<String, Date> customfinal = new TreeMap<String, Date>(customdate);

				try {
					PrintStream ps;
					ps = new PrintStream(new File("Customdate.txt"));

					for (Entry<String, Date> entry : customfinal.entrySet()) {
						ps.println(entry.getKey() + " ;" + entry.getValue());
					}
					ps.close();
				} catch (FileNotFoundException h) {
					h.printStackTrace();
				}
			}
			
			//read 
			public void readCustomDate() throws ParseException {
				BufferedReader bf;
				String line;

				try {
					// TODO:Change Custom
					bf = new BufferedReader(new FileReader("G:/Users/Christian/Dropbox/Universität/SS 2013/Programmierung II/Übungen/StudienProjekt/Customdate.txt"));
					
					while ((line = bf.readLine()) != null) {
						String[] a = line.split(" ;");
						DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
						Date date = format.parse(a[1]);
						customdate.put(a[0], date);
					}
					bf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
