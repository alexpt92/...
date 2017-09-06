import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {

	public static void main(String[] args) {
		try {
			// XMLReader erzeugen
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();

			// ZipFile f = new
			// ZipFile("http://dblp.uni-trier.de/xml/dblp.xml.gz");

			// Pfad zur XML Datei
			FileReader reader = new FileReader("/home/mack/dblp.xml");
			InputSource inputSource = new InputSource(reader);

			// DTD kann optional übergeben werden
			inputSource.setSystemId("/home/mack/dblp.dtd");

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
		
		JFrame frame = new JFrame("Controlling");
		
		frame.setSize(500,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		//frame.getContentPane().add(BorderLayout.CENTER, panel);;
		
		
		JTextField upcoming=new JTextField("Upcoming:", 10);
		JTextField todo=new JTextField("TODO:", 10);
		JButton button = new JButton("Change Customdate");
		
		panel.setLayout(new BorderLayout());
		panel.add(upcoming, BorderLayout.CENTER);
		panel.add(todo, BorderLayout.SOUTH);
		panel.add(button, BorderLayout.EAST);
		frame.add(panel);
		frame.setVisible(true);
		
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String t="";
				BufferedWriter bw;
				String line;
				
				t ="conf/"+ JOptionPane.showInputDialog("Bitte Kürzel der Conference eingeben" )+"/";
				
				int d =Integer.parseInt(JOptionPane.showInputDialog("Bitte Tag eingeben (1-31)"));
				int m =Integer.parseInt(JOptionPane.showInputDialog("Bitte Monat eingeben (1-12)"));
				int y= Integer.parseInt(JOptionPane.showInputDialog("Bitte Jahr eingeben"));
				
				for (Entry<String, Date> entry : customdate.entrySet()) {
					if(entry.getKey().equals(t)){
						Date tmp = new Date(y-1900,m-1,d);
						entry.setValue(tmp);
						
					}
				}
			
				Map<String, Date> customfinal = new TreeMap<String, Date>(customdate);
				
				try {
					PrintStream ps;
					ps = new PrintStream(new File("Customdate.txt"));
						
					for (Entry<String, Date> entry : customfinal.entrySet()) {
						ps.println(entry.getKey() + " ;" + entry.getValue());
					}
						ps.close();
					} catch (FileNotFoundException h) {
						// TODO Auto-generated catch block
						h.printStackTrace();
				}
			}
			
			
			
			
		});
		
		
		
	}
}

