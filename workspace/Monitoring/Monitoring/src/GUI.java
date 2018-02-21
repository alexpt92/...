import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class GUI extends JPanel{
	public static void main(String[] args){
		JPanel panel = new JPanel();
		JTextField upcoming=new JTextField("Upcoming:", 10);
		JTextField todo=new JTextField("TODO:", 10);
		JButton button = new JButton("Change Customdate");
		
		panel.setLayout(new BorderLayout());
		panel.add(upcoming, BorderLayout.CENTER);
		panel.add(todo, BorderLayout.SOUTH);
		panel.add(button, BorderLayout.EAST);

		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//ConfigHandler.class.changeCustom();
				
			}
		});
		
	}
}
