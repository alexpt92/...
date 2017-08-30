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
			FileReader reader = new FileReader("H:/SelfWorkspace/dblp.xml");
			InputSource inputSource = new InputSource(reader);

			// DTD kann optional übergeben werden
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
	}
}

class ConfigHandler extends DefaultHandler {
	// Map<String, Date> mapeins = new HashMap<String, Date>();
	Map<String, Date> mapzwei = new HashMap<String, Date>();
	String aktuellerStream;
	StringBuilder textContent = new StringBuilder();
	Map<String, Conf> finalmap = new HashMap<String, Conf>();
	Map<String, Conf> outdated = new HashMap<String, Conf>();
	Map<String, Date> customdate = new HashMap<String, Date>();

	int ohneMonat = 0;
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

			if (Pattern.matches(("conf/.*"), aktuellerStream)) {

				// System.out.print(aktuellerStream+" --> ");
				// System.out.println(textContent.toString());
				counter++;

				String[] tmpa;
				tmpa = aktuellerStream.split("/");
				String tmp = tmpa[0] + "/" + tmpa[1] + "/";

				Conf c = new Conf(new Date(0, 0, 0), 0);

				if (finalmap.isEmpty()) {
					finalmap.put(tmp, c);// TODO: einheitsdatum zum vergleich
											// später ob customdate da ist
				} else {

					if (!finalmap.containsKey(tmp)) {
						finalmap.put(tmp, c);
					}

				}

				insideStream = true;

			}
		}

		if (qName == "title" && insideStream) {
			insideTitle = true;
			titlecounter++;

		}

	}

	// Methode wird aufgerufen wenn der Parser zu einem End-Tag kommt
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (qName.equals("title") && insideStream && insideProceeding) {
			insideTitle = false;
			insideProceeding = false;
			String text = textContent.toString();
			Date zero = new Date(0, 0, 0);

			Pattern noDate = Pattern.compile("\\d\\d\\d\\d");
			Matcher regexMatcher = noDate.matcher(aktuellerStream);

			if (aktuellerStream != null) {
				if (text.contains("January")) {
					// String map = text.substring(text.indexOf("January"));
					mapzwei.put(aktuellerStream,toDate(text.substring(text.indexOf("January"))));
				} else {if (text.contains("February")) {
					// map = text.substring(text.indexOf("February"));
					mapzwei.put(aktuellerStream,toDate(text.substring(text.indexOf("February"))));
				} else {if (text.contains("March")) {
					// map = text.substring(text.indexOf("March"));
					mapzwei.put(aktuellerStream, toDate(text.substring(text.indexOf("March"))));
				} else {if (text.contains("April")) {
					// map = text.substring(text.indexOf("April"));
					mapzwei.put(aktuellerStream, toDate(text.substring(text.indexOf("April"))));
				} else {if (text.contains("May")) {
					// map = text.substring(text.indexOf("May"));
					mapzwei.put(aktuellerStream, toDate(text.substring(text.indexOf("May"))));
				} else {if (text.contains("June")) {
					// map =text.substring(text.indexOf("June"));
					mapzwei.put(aktuellerStream,toDate(text.substring(text.indexOf("June"))));
				} else {if (text.contains("Juni")) {
					// map = text.substring(text.indexOf("Juni"));
					mapzwei.put(aktuellerStream,toDate(text.substring(text.indexOf("Juni"))));
				} else {if (text.contains("July")) {
					// map = text.substring(text.indexOf("July"));
					mapzwei.put(aktuellerStream,toDate(text.substring(text.indexOf("July"))));
				} else {if (text.contains("August")) {
					// map = text.substring(text.indexOf("August"));
					mapzwei.put(aktuellerStream,toDate(text.substring(text.indexOf("August"))));
				} else {if (text.contains("September")) {
					// map = text.substring(text.indexOf("September"));
					mapzwei.put(aktuellerStream,toDate(text.substring(text.indexOf("September"))));
				} else {if (text.contains("October")) {
					// map = text.substring(text.indexOf("October"));
					mapzwei.put(aktuellerStream,toDate(text.substring(text.indexOf("October"))));
				} else {if (text.contains("November")) {
					// map = text.substring(text.indexOf("November"));
					mapzwei.put(aktuellerStream,toDate(text.substring(text.indexOf("November"))));
				} else {if (text.contains("December")) {
					// map = text.substring(text.indexOf("December"));
					mapzwei.put(aktuellerStream,toDate(text.substring(text.indexOf("December"))));
				} else {
					ohneMonat++;

					if (regexMatcher.find()) {
						int jahr = Integer.parseInt(
								aktuellerStream.substring(regexMatcher.start(),regexMatcher.start() + 4));
						Date datum = new Date(jahr - 1900,0,15);
						mapzwei.put(aktuellerStream,datum);
					} else {
						mapzwei.put(aktuellerStream,zero);
					}

				}}}}}}}}}}}}}
			}textContent.delete(0, textContent.length());
		}
		
		if (insideStream&& (qName.equals("article") || qName.equals("inproceedings")
						|| qName.equals("proceedings") || qName.equals("book")
						|| qName.equals("incollection")	|| qName.equals("phdthesis") 
						|| qName.equals("masterthesis"))) {
			if (qName.equals("proceedings")) {
				insideProceeding = false;
			}
			// streamName = "";
			insideStream = false;
		}
	}

	public Date toDate(String s) {

		int tag = 15;
		int monat = 0;
		int jahr = 1900;
		Pattern regex = Pattern.compile("\\d\\d\\d\\d");
		Pattern days = Pattern.compile("\\d\\d");
		Pattern oneday = Pattern.compile("\\d");
		Matcher regexMatcher = regex.matcher(s);
		Matcher daysMatcher = days.matcher(s);
		Matcher onedayMatcher = oneday.matcher(s);
		String m;

		// Berechnung des Monats
		if (s.contains("January")|| s.contains("Januar")) {
			monat = 0;
		} else {if (s.contains("February")|| s.contains("Februar")) {
			monat = 1;
		} else {if (s.contains("March")|| s.contains("März")) {
			monat = 2;
		} else {if (s.contains("April")) {
			monat = 3;
		} else {if (s.contains("May")|| s.contains("Mai")) {
			monat = 4;
		} else {if (s.contains("June") || s.contains("Juni")) {
			monat = 5;
		} else {if (s.contains("July")|| s.contains("Juli")) {
			monat = 6;
		} else {if (s.contains("August")) {
			monat = 7;
		} else {if (s.contains("September")) {
			monat = 8;
		} else {if (s.contains("October")|| s.contains("Oktober")) {
			monat = 9;
		} else {if (s.contains("November")) {
			monat = 10;
		} else {if (s.contains("December")|| s.contains("Dezember")) {
			monat = 11;
		}}}}}}}}}}}}
		
		// Berechnung des Tages
		// vorher gucken ob wirklich zahlen des Tages drinstehen
		if (daysMatcher.find()) {
			tag = Integer.parseInt(s.substring(daysMatcher.start(),
					daysMatcher.start() + 2));
		}else{if (onedayMatcher.find()) {
				tag = Integer.parseInt(s.substring(onedayMatcher.start(),
						onedayMatcher.start() + 1));
			}

		}

		//
		// Berechnung des Jahres
		if (regexMatcher.find()) {
			jahr = Integer.parseInt(s.substring(regexMatcher.start(),
					regexMatcher.start() + 4));
		} else {
			jahr = Integer.parseInt(aktuellerStream.substring(
					aktuellerStream.lastIndexOf("/") + 1,
					aktuellerStream.lastIndexOf("/") + 3)) + 1900;
		}

		Date datum = new Date(jahr - 1900, monat, tag);

		return datum;
	}

	public void endDocument() throws SAXException {

		Map<String, Date> map = new TreeMap<String, Date>(mapzwei);
		Map<String, Conf> mapfinal = new TreeMap<String, Conf>(finalmap);

		yearFinal(map, mapfinal);
		datumFinal(map, mapfinal);
		createCustomFinal(mapfinal);

		Map<String, Date> customfinal = new TreeMap<String, Date>(customdate);
		Map<String, Conf> outdatedfinal = new TreeMap<String, Conf>(outdated);

		try {
			PrintStream ps;
			ps = new PrintStream(new File("MapPrint.txt"));

			for (Entry<String, Date> entry : map.entrySet()) {
				ps.println(entry.getKey() + " ;" + entry.getValue());

			}
			ps.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File Customdate = new File("Customdate.txt");
		if (!Customdate.exists()) {
			try {
				PrintStream ps;
				ps = new PrintStream(new File("Customdate.txt"));

				for (Entry<String, Date> entry : customfinal.entrySet()) {
					ps.println(entry.getKey() + " ;" + entry.getValue());
				}
				ps.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
		}

		try {
			PrintStream ps;
			ps = new PrintStream(new File("Outdated.txt"));

			for (Entry<String, Conf> entry : outdatedfinal.entrySet()) {
				ps.println(entry.getKey() + " ;" + entry.getValue().getDate()
						+ " ;" + entry.getValue().getYear());

			}
			ps.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			PrintStream ps;
			ps = new PrintStream(new File("MapFinalPrint.txt"));
			Date now = new Date();
			String alert = "ALERT!!";
			for (Entry<String, Conf> entry : mapfinal.entrySet()) {// datumFinal,
				if (entry.getValue().getDate().before(now)) {
					alert = "ALERT!!!";
				} else {
					alert = "NO";
				} // yearFinal
				ps.println(entry.getKey() + "; " + entry.getValue().getDate()
						+ "; " + entry.getValue().getYear() + " " + alert);

			}
			ps.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("File printed.");
		System.out.println("Anzahl der Tags: " + counter);
		System.out.println("Anzahl der titelTags: " + titlecounter);
		System.out.println(mapzwei.size());
		System.out.println("Laufzeit: " + time + "ms");
		System.out.println("Titel ohne Monat" + ohneMonat);

	}

	public void createCustomFinal(Map<String, Conf> mapfinal) {
		for (Entry<String, Conf> entry : mapfinal.entrySet()) {
			customdate.put(entry.getKey(), entry.getValue().getCustomdate());
		}
	}

	public void datumFinal(Map<String, Date> map, Map<String, Conf> mapfinal) {
		System.out.println("Berechne Monate.");
		Map<String, Date> tmpmap = map;
		Map<String, Integer> monthcounter = new HashMap<String, Integer>();

		for (Entry<String, Conf> entry : mapfinal.entrySet()) {
			Date tmpDate = new Date();
			String finalmapkey = entry.getKey();

			monthcounter.clear();
			monthcounter.put("January", 0);
			monthcounter.put("February", 0);
			monthcounter.put("March", 0);
			monthcounter.put("April", 0);
			monthcounter.put("May", 0);
			monthcounter.put("June", 0);
			monthcounter.put("July", 0);
			monthcounter.put("August", 0);
			monthcounter.put("September", 0);
			monthcounter.put("October", 0);
			monthcounter.put("November", 0);
			monthcounter.put("December", 0);

			int quartal1 = 0;
			int quartal2 = 0;
			int quartal3 = 0;
			int quartal4 = 0;

			for (Entry<String, Date> entry1 : tmpmap.entrySet()) {
				if (entry1.getKey().contains(finalmapkey)) {
					if (!tmpDate.equals(entry1.getValue())) {
						switch (entry1.getValue().getMonth()) {
						case 0:
							monthcounter.put("January",monthcounter.get("January") + 1);
							quartal1++;
							break;
						case 1:
							monthcounter.put("February",monthcounter.get("February") + 1);
							quartal1++;
							break;
						case 2:
							monthcounter.put("March",monthcounter.get("March") + 1);
							quartal1++;
							break;
						case 3:
							monthcounter.put("April",monthcounter.get("April") + 1);
							quartal2++;
							break;
						case 4:
							monthcounter.put("May", monthcounter.get("May") + 1);
							quartal2++;
							break;
						case 5:
							monthcounter.put("June",monthcounter.get("June") + 1);
							quartal2++;
							break;
						case 6:
							monthcounter.put("July",monthcounter.get("July") + 1);
							quartal3++;
							break;
						case 7:
							monthcounter.put("August",monthcounter.get("August") + 1);
							quartal3++;
							break;
						case 8:
							monthcounter.put("September",monthcounter.get("September") + 1);
							quartal3++;
							break;
						case 9:
							monthcounter.put("October",monthcounter.get("October") + 1);
							quartal4++;
							break;
						case 10:
							monthcounter.put("November",monthcounter.get("November") + 1);
							quartal4++;
							break;
						case 11:
							monthcounter.put("December",monthcounter.get("December") + 1);
							quartal4++;
							break;
						}
					} else {
						if (monthcounter.get("January") > 0
								|| monthcounter.get("February") > 0
								|| monthcounter.get("March") > 0
								|| monthcounter.get("April") > 0
								|| monthcounter.get("May") > 0
								|| monthcounter.get("June") > 0
								|| monthcounter.get("July") > 0
								|| monthcounter.get("August") > 0
								|| monthcounter.get("September") > 0
								|| monthcounter.get("October") > 0
								|| monthcounter.get("November") > 0
								|| monthcounter.get("December") > 0) {
							break;
						}
					}
					tmpDate = entry1.getValue();
				}

			}
			if (entry.getValue().getYear() == 0.5) {
				// TODO: berechnung des nächsten Datums mit Jahresabstand 0.5
				switch (entry.getValue().getDate().getMonth()) {
					case 0:
						mapfinal.get(entry.getKey()).getDate().setMonth(6);
						break;
					case 1:
						mapfinal.get(entry.getKey()).getDate().setMonth(7);
						break;
					case 2:
						mapfinal.get(entry.getKey()).getDate().setMonth(8);
						break;
					case 3:
						mapfinal.get(entry.getKey()).getDate().setMonth(9);
						break;
					case 4:
						mapfinal.get(entry.getKey()).getDate().setMonth(10);
						break;
					case 5:
						mapfinal.get(entry.getKey()).getDate().setMonth(11);
						break;
					case 6:
						mapfinal.get(entry.getKey()).getDate().setMonth(0);
						mapfinal.get(entry.getKey()).getDate()
								.setYear(entry.getValue().getDate().getYear() + 1);
						break;
					case 7:
						mapfinal.get(entry.getKey()).getDate().setMonth(1);
						mapfinal.get(entry.getKey()).getDate().setYear(entry.getValue().getDate().getYear() + 1);
						break;
					case 8:
						mapfinal.get(entry.getKey()).getDate().setMonth(2);
						mapfinal.get(entry.getKey()).getDate().setYear(entry.getValue().getDate().getYear() + 1);
						break;
					case 9:
						mapfinal.get(entry.getKey()).getDate().setMonth(3);
						mapfinal.get(entry.getKey()).getDate().setYear(entry.getValue().getDate().getYear() + 1);
						break;
					case 10:
						mapfinal.get(entry.getKey()).getDate().setMonth(4);
						mapfinal.get(entry.getKey()).getDate().setYear(entry.getValue().getDate().getYear() + 1);
						break;
					case 11:
						mapfinal.get(entry.getKey()).getDate().setMonth(5);
						mapfinal.get(entry.getKey()).getDate().setYear(entry.getValue().getDate().getYear() + 1);
						break;
				}
			} else {// berechnung des nächsten Datums der Konferenz
				mapfinal.get(entry.getKey()).getDate().setMonth(tmpDate.getMonth());
			}
		}

		System.out.println("Monate berechnet.");
	}

	/*
	 * int tmp = 0; Map<String, Date> tmpmap = map; for (Entry<String, Conf>
	 * entry : mapfinal.entrySet()) { String finalmapkey = entry.getKey(); //
	 * erstellen eines Arrays für einen Eintrag in mapfinal for (Entry<String,
	 * Date> entry1 : tmpmap.entrySet()) { if
	 * (entry1.getKey().contains(finalmapkey)) { d[tmp] = entry1.getValue();
	 * tmp++; } else { break; } } }
	 */

	public void yearFinal(Map<String, Date> map, Map<String, Conf> mapfinal) {
		System.out.println("Berechne Jahresabstand.");
		List<Long> year = new ArrayList<Long>();

		// Long[] y = new Long[100];

		int tmp = 0;
		Map<String, Date> tmpmap = map;
		Map<String, Date> tmpmap2 = tmpmap;
		Date tmpDate = new Date();

		for (Entry<String, Conf> entry : mapfinal.entrySet()) {
			String finalmapkey = entry.getKey();
			year.clear();
			tmp = 0;
			// tmpmap=tmpmap2;

			for (Entry<String, Date> entry1 : tmpmap.entrySet()) {
				if (entry1.getKey().contains(finalmapkey)) {

					// TO-DO: Hier passiert nichts
					if (tmp == 0) {
						year.add((long) entry1.getValue().getYear());

					} else {
						if (entry1.getValue().equals(tmpDate)) {
						} else {
							if (year.contains(entry1.getValue().getYear())) {
							} else {
								year.add((long) entry1.getValue().getYear());
							}
						}
						tmp++;
					}
				} else {
					if (year.isEmpty()) {
					} else {
						break;
					}

				}
				// tmpmap2.remove(entry1.getKey());
				tmpDate = entry1.getValue();

			}

			double maximum = 0;

			Date now = new Date();

			for (int i = 0; i <= year.size() - 1; i++) {
				if (year.get(i) > maximum) {
					maximum = year.get(i);
				}
			}

			if (((now.getYear() + 1900) - (maximum + 1900)) > 3) { // +1900
				// 3 Jahre nicht mehr stattgefunden

				mapfinal.put(entry.getKey(), new Conf(new Date(), -1));// !!
				outdated.put(entry.getKey(), new Conf(new Date(), -1));
			} else {
				int counter1 = 0;
				int counter2 = 0;
				int counter3 = 0;
				int counter05 = 0;
				int y = 1;

				if (year.size() == 1) {
					// Konferenz erst ein mal stattgefunden, evtl Ausgabe mit
					// Benachrichtigung
					double sonderfall = -2;
					mapfinal.put(entry.getKey(), new Conf(new Date(),
							sonderfall));
				} else {

					if (year.size() < 6) {
						y = y + (6 - year.size());
					}
					for (int i = 1; y < 6; i++, y++) {
						// System.out.println(year.size() - (i));
						// System.out.println(year.size() - (i + 1));
						if ((year.get(year.size() - (i)) - year.get(year.size()
								- (i + 1))) == 1) {
							counter1++;

						} else {
							if ((year.get(year.size() - (i)) - year.get(year
									.size() - (i + 1))) == 2) {
								counter2++;
							} else {
								if ((year.get(year.size() - (i)) - year
										.get(year.size() - (i + 1))) == 3) {
									counter3++;
								} else {
									if ((year.get(year.size() - (i)) - year
											.get(year.size() - (i + 1))) == 0) {

										counter05++;
									}
								}
							}
						}

					}
					if (counter1 > counter2 && counter1 > counter3
							&& counter1 > counter05 || counter1 == counter2
							&& counter1 > counter3 && counter1 > counter05
							|| counter1 == counter3 && counter1 > counter05
							&& counter1 > counter2) {
						// Jahresabstand 1 -> Letztes Veranstaltungsjahr +1
						mapfinal.get(entry.getKey()).getDate().setYear((int) (year.get(year.size() - 1) + 1));
						mapfinal.get(entry.getKey()).setYear(1);
					} else {
						if (counter2 > counter1 && counter2 > counter3
								&& counter2 > counter05 || counter2 == counter3
								&& counter2 > counter05) {
							// Jahresabstand 2 -> Letztes Veranstaltungsjahr +2
							mapfinal.get(entry.getKey()).getDate().setYear(/* toIntExact */(int) (year.get(year.size() - 1) + 2));
							mapfinal.get(entry.getKey()).setYear(2);
						} else {
							if (counter3 > counter2 && counter3 > counter1&& counter3 > counter05) {
								// Jahresabstand 3 -> Letztes Veranstaltungsjahr
								// +3
								mapfinal.get(entry.getKey()).getDate().setYear((int) (year.get(year.size() - 1) + 3));
								mapfinal.get(entry.getKey()).setYear(3);
							} else {
								if (counter05 > counter1&& counter05 > counter2
										&& counter05 > counter3|| counter05 == counter1
										|| counter05 == counter2|| counter05 == counter3) {
									mapfinal.get(entry.getKey()).setYear(0.5);
									// TODO: Jahresabstand 0.5 -> + 6 Monate???
								}
							}
						}
					}
				}
			}
		}
		System.out.println("Jahresabstand berechnet.");
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
