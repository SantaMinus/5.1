import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI {
	static JFrame frame = new JFrame("PZKS1");
	JPanel panel = new JPanel();
	static JTextArea text = new JTextArea();
	static JTextArea result = new JTextArea();
	static JButton button = new JButton("Submit");
	static JLabel label=new JLabel("Enter your string: ");
	
	static void paint(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button.setSize(300,50);

		addComps(frame);
		frame.pack();
		frame.setVisible(true);
		
		//listeners
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				PZKS1.validation();
			}
		});
	}
	
	private static void addComps(final Container pane) {
		final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(2,2));
         
        //Add buttons to experiment with Grid Layout
        panel.add(label);
        panel.add(text);
        panel.add(button);
        panel.add(result);
         
        //Process the Apply gaps button press
        pane.add(panel);
	}
}
