import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@SuppressWarnings("deprecation")
class ConfigHandler extends DefaultHandler {
	
	String aktuellerStream;
	StringBuilder textContent = new StringBuilder();
	//Maps________________________________________________________________________________
	Map<String, Date> dblpEntry = new HashMap<String, Date>();
	Map<String, Conf> finalmap = new HashMap<String, Conf>();
	Map<String, Conf> outdated = new HashMap<String, Conf>();
	Map<String, Date> customdate = new HashMap<String, Date>();
	Map<String, Date> sonderfall = new HashMap<String, Date>();
	
	
	int ohneMonat = 0;
	int counter;
	int titlecounter;
	private static long time;
	//Parsing_____________________________________________________________________________
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

			//String[] parts = currentValue.split(",");
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
					finalmap.put(tmp, c);
				} else {if (!finalmap.containsKey(tmp)) {
					finalmap.put(tmp, c);
				}}
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
			Date zero = new Date(100,11,31);

			Pattern noDate = Pattern.compile("\\d\\d\\d\\d");
			Matcher regexMatcher = noDate.matcher(aktuellerStream);

			if (aktuellerStream != null) {
				if (text.contains("January")) {
					dblpEntry.put(aktuellerStream,toDate(text.substring(text.indexOf("January"))));
				}else {if (text.contains("Januar")) {
					dblpEntry.put(aktuellerStream, toDate(text.substring(text.indexOf("Januar"))));
				}else {if (text.contains("February")) {
					dblpEntry.put(aktuellerStream,toDate(text.substring(text.indexOf("February"))));
				}else {if (text.contains("Februar")) {
					dblpEntry.put(aktuellerStream, toDate(text.substring(text.indexOf("Februar"))));
				}else {if (text.contains("March")) {
					dblpEntry.put(aktuellerStream, toDate(text.substring(text.indexOf("March"))));
				}else {if (text.contains("März")) {
					dblpEntry.put(aktuellerStream, toDate(text.substring(text.indexOf("März"))));
				}else {if (text.contains("April")) {
					dblpEntry.put(aktuellerStream, toDate(text.substring(text.indexOf("April"))));
				} else {if (text.contains("May")) {
					dblpEntry.put(aktuellerStream, toDate(text.substring(text.indexOf("May"))));
				}else {if (text.contains("Mai")) {
					dblpEntry.put(aktuellerStream, toDate(text.substring(text.indexOf("Mai"))));
				}else {if (text.contains("June")) {
					dblpEntry.put(aktuellerStream,toDate(text.substring(text.indexOf("June"))));
				} else {if (text.contains("Juni")) {
					dblpEntry.put(aktuellerStream,toDate(text.substring(text.indexOf("Juni"))));
				} else {if (text.contains("July")) {
					dblpEntry.put(aktuellerStream,toDate(text.substring(text.indexOf("July"))));
				}else {if (text.contains("Juli")) {
					dblpEntry.put(aktuellerStream, toDate(text.substring(text.indexOf("Juli"))));
				}else {if (text.contains("August")) {
					dblpEntry.put(aktuellerStream,toDate(text.substring(text.indexOf("August"))));
				} else {if (text.contains("September")) {
					dblpEntry.put(aktuellerStream,toDate(text.substring(text.indexOf("September"))));
				} else {if (text.contains("October")) {
					dblpEntry.put(aktuellerStream,toDate(text.substring(text.indexOf("October"))));
				}else {if (text.contains("Oktober")) {
					dblpEntry.put(aktuellerStream, toDate(text.substring(text.indexOf("Oktober"))));
				}else {if (text.contains("November")) {
					dblpEntry.put(aktuellerStream,toDate(text.substring(text.indexOf("November"))));
				}else {if (text.contains("December")) {
					dblpEntry.put(aktuellerStream,toDate(text.substring(text.indexOf("December"))));
				}else {if (text.contains("Dezember")) {
					dblpEntry.put(aktuellerStream, toDate(text.substring(text.indexOf("Dezember"))));
				}
				else {
					ohneMonat++;

					dblpEntry.put(aktuellerStream,zero);
					sonderfall.put(aktuellerStream, zero);
				}}}}}}}}}}}}}}}}}}}}
			}textContent.delete(0, textContent.length());
		}
		
		if (insideStream&& (qName.equals("article") || qName.equals("inproceedings")|| qName.equals("proceedings") || 
				qName.equals("book")|| qName.equals("incollection")	|| qName.equals("phdthesis") || qName.equals("masterthesis"))) {
			if (qName.equals("proceedings")) {
				insideProceeding = false;
			}
			insideStream = false;
		}
	}

	public Date toDate(String s) {
		int tag = 15;
		int monat = 0;
		int jahr = 1900;
		Pattern year = Pattern.compile("\\d\\d\\d\\d");
		Pattern daypoint = Pattern.compile("'\\d\\p{Punct}\\d");
		Pattern days = Pattern.compile("\\d\\d");
		Pattern oneday = Pattern.compile("\\d");
		Matcher yearMatcher = year.matcher(s);
		Matcher dayPointMatcher = year.matcher(s);
		Matcher ohneYearMatcher = year.matcher(aktuellerStream);
		Matcher daysMatcher = days.matcher(s);
		Matcher onedayMatcher = oneday.matcher(s);
		
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
		if(dayPointMatcher.find()){
			tag = Integer.parseInt(s.substring(dayPointMatcher.start(),dayPointMatcher.start() + 1));
		}else{if (daysMatcher.find()) {
			tag = Integer.parseInt(s.substring(daysMatcher.start(),daysMatcher.start() + 2));
		}else{if (onedayMatcher.find()) {
			tag = Integer.parseInt(s.substring(onedayMatcher.start(),onedayMatcher.start() + 1));
		}}}

		// Berechnung des Jahres
		if (yearMatcher.find()) {// "/d/d/d/d" in "Title" suchen
			jahr = Integer.parseInt(s.substring(yearMatcher.start(),yearMatcher.start() + 4));
		} else {
			if(ohneYearMatcher.find()){//Streams ohne Jahr -> Jahr aus Key ziehen
				jahr = Integer.parseInt(aktuellerStream.substring(ohneYearMatcher.start(),ohneYearMatcher.start() + 4));
			}
		}
		//Datum zusammensetzten
		Date datum = new Date(jahr - 1900, monat, tag);

		return datum;
	}

	public void endDocument() throws SAXException {
		//TODO:Change Custom
		File f = new File("G:/Users/Christian/Dropbox/Universität/SS 2013/Programmierung II/Übungen/StudienProjekt/Customdate.txt");
		
		//organice Maps________________________________________________________________________
		Map<String, Date> dblpEntryFinal = new TreeMap<String, Date>(dblpEntry);
		Map<String, Conf> finalMapFinal = new TreeMap<String, Conf>(finalmap);
		
		if(f.exists()){
			try {
				readCustomDate();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			createCustomFinal(finalMapFinal);
		}
		
		//calculate Maps_____________________________________________________________________________
		yearFinal(dblpEntryFinal, finalMapFinal);
		datumFinal(dblpEntryFinal, finalMapFinal);
		//_____________________________________________________________________________________
		Map<String, Date> sonderfallFinal = new TreeMap<String, Date>(sonderfall);
		Map<String, Conf> outdatedFinal = new TreeMap<String, Conf>(outdated);
		Map<String, Date> customfinal = new TreeMap<String, Date>(customdate);
		
		//PrintMaps____________________________________________________________________________
		//Print dblpEntryFinal
		try {
			PrintStream ps;
			ps = new PrintStream(new File("MapPrint.txt"));

			for (Entry<String, Date> entry : dblpEntryFinal.entrySet()) {
				ps.println(entry.getKey() + " ;" + entry.getValue());

			}
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Print sonderfallFinal
		try {
			PrintStream ps;
			ps = new PrintStream(new File("noDate.txt"));

			for (Entry<String, Date> entry : sonderfallFinal.entrySet()) {
				ps.println(entry.getKey() + " ;" + entry.getValue());

			}
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Print customfinal
		try {
			PrintStream ps;
			ps = new PrintStream(new File("Customdate.txt"));
				
			for (Entry<String, Date> entry : customfinal.entrySet()) {
				ps.println(entry.getKey() + " ;" + entry.getValue());
			}
				ps.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
		}
		
		//Print outdatedfinal
		try {
			PrintStream ps;
			ps = new PrintStream(new File("Outdated.txt"));

			for (Entry<String, Conf> entry : outdatedFinal.entrySet()) {
				ps.println(entry.getKey() + " ;" + entry.getValue().getDate()
						+ " ;" + entry.getValue().getYear());
			}
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Print mapfinal
		try {
			PrintStream ps;
			ps = new PrintStream(new File("MapFinalPrint.txt"));
			Date now = new Date();
			String alert = "ALERT!!";
			for (Entry<String, Conf> entry : finalMapFinal.entrySet()) {
				if (entry.getValue().getDate().before(now)) {
					alert = "ALERT!!!";
				} else {
					alert = "NO";
				}
				ps.println(entry.getKey() + "; " + entry.getValue().getDate()
						+ "; " + entry.getValue().getYear() + " " + alert);
			}
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void createCustomFinal(Map<String, Conf> mapfinal) {
		for (Entry<String, Conf> entry : mapfinal.entrySet()) {
			customdate.put(entry.getKey(), entry.getValue().getCustomdate());
		}
	}
	
	public void readCustomDate() throws ParseException{
		BufferedReader bf;
		String line;
		
		try{
			//TODO:Change Custom
			bf=new BufferedReader(new FileReader("G:/Users/Christian/Dropbox/Universität/SS 2013/Programmierung II/Übungen/StudienProjekt/Customdate.txt"));
			while((line=bf.readLine())!=null){
				String[] a = line.split(" ;");
				DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
				Date date= format.parse(a[1]);
				customdate.put(a[0],date);
			}
			bf.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void datumFinal(Map<String, Date> map, Map<String, Conf> mapfinal) {
		System.out.println("Berechne Monate.");
		Map<String, Date> tmpmap = map;
	//	Map<String, Integer> monthcounter = new HashMap<String, Integer>();
			
		for (Entry<String, Conf> entry : mapfinal.entrySet()) {
			Date tmpDate = new Date();
			String finalmapkey = entry.getKey();
			int count =0;
		
			for (Entry<String, Date> entry1 : tmpmap.entrySet()) {
				if (entry1.getKey().contains(finalmapkey)) {
					if (!tmpDate.equals(entry1.getValue())) {
						count++;
						tmpDate = entry1.getValue();
					}		
				}else{
					if(count!=0){
						break;	
					}
				}
			}
			
			if(customdate.get(entry.getKey()).equals(new Date(100,11,24))){
				//Berechnung des nächsten Datums mit Jahresabstand 0.5
				if (entry.getValue().getYear() == 0.5) {
					switch (tmpDate.getMonth()) {
						case 0:
							mapfinal.get(entry.getKey()).setDate(tmpDate);
							mapfinal.get(entry.getKey()).getDate().setMonth(6);;
							break;
						case 1:
							mapfinal.get(entry.getKey()).setDate(tmpDate);
							mapfinal.get(entry.getKey()).getDate().setMonth(7);
							break;
						case 2:
							mapfinal.get(entry.getKey()).setDate(tmpDate);
							mapfinal.get(entry.getKey()).getDate().setMonth(8);
							break;
						case 3:
							mapfinal.get(entry.getKey()).setDate(tmpDate);
							mapfinal.get(entry.getKey()).getDate().setMonth(9);
							break;
						case 4:
							mapfinal.get(entry.getKey()).setDate(tmpDate);
							mapfinal.get(entry.getKey()).getDate().setMonth(10);
							break;
						case 5:
							mapfinal.get(entry.getKey()).setDate(tmpDate);
							mapfinal.get(entry.getKey()).getDate().setMonth(11);
							break;
						case 6:
							mapfinal.get(entry.getKey()).setDate(tmpDate);
							mapfinal.get(entry.getKey()).getDate().setMonth(0);
							mapfinal.get(entry.getKey()).getDate().setYear(entry.getValue().getDate().getYear() + 1);
							break;
						case 7:
							mapfinal.get(entry.getKey()).setDate(tmpDate);
							mapfinal.get(entry.getKey()).getDate().setMonth(1);
							mapfinal.get(entry.getKey()).getDate().setYear(entry.getValue().getDate().getYear() + 1);
							break;
						case 8:
							mapfinal.get(entry.getKey()).setDate(tmpDate);
							mapfinal.get(entry.getKey()).getDate().setMonth(2);
							mapfinal.get(entry.getKey()).getDate().setYear(entry.getValue().getDate().getYear() + 1);
							break;
						case 9:
							mapfinal.get(entry.getKey()).setDate(tmpDate);
							mapfinal.get(entry.getKey()).getDate().setMonth(3);
							mapfinal.get(entry.getKey()).getDate().setYear(entry.getValue().getDate().getYear() + 1);
							break;
						case 10:
							mapfinal.get(entry.getKey()).setDate(tmpDate);
							mapfinal.get(entry.getKey()).getDate().setMonth(4);
							mapfinal.get(entry.getKey()).getDate().setYear(entry.getValue().getDate().getYear() + 1);
							break;
						case 11:
							mapfinal.get(entry.getKey()).setDate(tmpDate);
							mapfinal.get(entry.getKey()).getDate().setMonth(5);
							mapfinal.get(entry.getKey()).getDate().setYear(entry.getValue().getDate().getYear() + 1);
							break;
					}
				} else {// berechnung des nächsten Datums der Konferenz
						mapfinal.get(entry.getKey()).getDate().setMonth(tmpDate.getMonth());
				}
			}else{
				mapfinal.get(entry.getKey()).setDate(customdate.get(entry.getKey()));
			}
		}
		System.out.println("Monate berechnet.");
	}

	public void yearFinal(Map<String, Date> map, Map<String, Conf> mapfinal) {
		System.out.println("Berechne Jahresabstand.");
		List<Date> year = new ArrayList<Date>();

		int tmp = 0;
		Map<String, Date> tmpmap = map;
		Map<String, Conf> tmpmapfinal = new TreeMap();
		tmpmapfinal.putAll(mapfinal);
		Date tmpDate = new Date();

		for (Entry<String, Conf> entry : tmpmapfinal.entrySet()) {
			String finalmapkey = entry.getKey();
			year.clear();
			tmp = 0;

			for (Entry<String, Date> entry1 : tmpmap.entrySet()) {
				if (entry1.getKey().contains(finalmapkey)) {	
					//wenn das Array leer ist
					if (tmp == 0) {
						year.add(entry1.getValue());
						tmp++;
					}else{if(tmpDate.getMonth()==entry1.getValue().getMonth()&&tmpDate.getYear()==entry1.getValue().getYear()){
						//nichts tun wenn letzter und jetziger entry gleichen Monat und gleiches Jahr enthält
					}else{if (entry1.getValue().equals(tmpDate)) {
						//nichts tun wenn Entry mehrfach vorkommt
					}else{if(year.contains(entry1.getValue().getYear())) {
						//nichts tun wenn ein entry zu dem Jahr schon enthalten ist.
					}else{
						year.add( entry1.getValue());
					}}}}
				} else {
					if (year.isEmpty()) {
					} else {
						break;
					}
				}
				tmpDate = entry1.getValue();
			}
			
			Date maximum =new Date(0,0,1900);

			Date now = new Date();

			for (int i = 0; i <= year.size() - 1; i++) {
				if (year.get(i).getYear() > maximum.getYear()) {
					maximum = year.get(i);
				}
			}

			if (((now.getYear() + 1900) - (maximum.getYear() + 1900)) > 3) { // +1900
				// 3 Jahre nicht mehr stattgefunden
				mapfinal.remove(entry.getKey());
				outdated.put(entry.getKey(), new Conf(tmpDate, -1));
			} else {
				int counter1 = 0;
				int counter2 = 0;
				int counter3 = 0;
				int counter05 = 0;
				int y = 1;

				if (year.size() == 1) {
					// Konferenz erst ein mal stattgefunden
					double sonderfall = -2;
					mapfinal.put(entry.getKey(), new Conf(new Date(),sonderfall));
				} else {if (year.size() < 6) {
					y = y + (6 - year.size());
				}
				
					for (int i = 1; y < 6; i++, y++) {
						if ((year.get(year.size() - (i)).getYear() - year.get(year.size()- (i + 1)).getYear()) == 1) {
							counter1++;
						} else {if ((year.get(year.size() - (i)).getYear() - year.get(year.size() - (i + 1)).getYear()) == 2) {
							counter2++;
						} else {if ((year.get(year.size() - (i)).getYear() - year.get(year.size() - (i + 1)).getYear()) == 3) {
							counter3++;
						} else {if ((year.get(year.size() - (i)).getYear() - year.get(year.size() - (i + 1)).getYear()) == 0) {
							counter05++;
						}}}}

					}
				
					if (counter1 > counter2 && counter1 > counter3 && counter1 > counter05 
							|| counter1 == counter2 && counter1 > counter3 && counter1 > counter05
							|| counter1 == counter3 && counter1 > counter05&& counter1 > counter2) {
							// Jahresabstand 1 -> Letztes Veranstaltungsjahr +1
							mapfinal.get(entry.getKey()).setDate(maximum);
							mapfinal.get(entry.getKey()).getDate().setYear(maximum.getYear() + 1);
							mapfinal.get(entry.getKey()).setYear(1);
					} else {if (counter2 > counter1 && counter2 > counter3&& counter2 > counter05 
							|| counter2 == counter3 && counter2 > counter05) {
							// Jahresabstand 2 -> Letztes Veranstaltungsjahr +2
							mapfinal.get(entry.getKey()).setDate(maximum);
							mapfinal.get(entry.getKey()).getDate().setYear(maximum.getYear() + 2);
							mapfinal.get(entry.getKey()).setYear(2);
					} else {if (counter3 > counter2 && counter3 > counter1&& counter3 > counter05) {
							// Jahresabstand 3 -> Letztes Veranstaltungsjahr +3
							mapfinal.get(entry.getKey()).setDate(maximum);
							mapfinal.get(entry.getKey()).getDate().setYear(maximum.getYear() + 3);
							mapfinal.get(entry.getKey()).setYear(3);
					} else {if (counter05 > counter1&& counter05 > counter2 && counter05 > counter3) {
							mapfinal.get(entry.getKey()).setDate(maximum);
							mapfinal.get(entry.getKey()).getDate().setYear(maximum.getYear());
							mapfinal.get(entry.getKey()).setYear(0.5);
					}else{
							mapfinal.put(entry.getKey(), new Conf(new Date(),-3));
					}}}}
				}
			}
		}
		System.out.println("Jahresabstand berechnet.");
	}
	public void endPrefixMapping(String prefix) throws SAXException {
	}
	public void ignorableWhitespace(char[] ch, int start, int length)throws SAXException {
	}
	public void processingInstruction(String target, String data)throws SAXException {
	}
	public void setDocumentLocator(Locator locator) {
	}
	public void skippedEntity(String name) throws SAXException {
	}
	public void startDocument() throws SAXException {
		System.out.println("Document starts.");

		time = System.currentTimeMillis();
	}
	public void startPrefixMapping(String prefix, String uri)throws SAXException {
	}
}
