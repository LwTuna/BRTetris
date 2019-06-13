

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
/**
 * Log ui which creates itself on print method when it's not initilized
 * 
 * @author jonas
 *
 */
public class LogUI{

	
	
	private static List<String> messages = new ArrayList<String>();
	
	private static boolean initialized = false;
	
	private static JFrame frame;
	private static JTextArea textArea;
	/**
	 * Print message to ui
	 * @param message the message to print
	 */
	public static void print(String message) {
		
		
		
		if(frame == null) {
			initialize();
		}
		if(!frame.isVisible()) {
			show();
		}
		
		textArea.append(message+"\n");
		
		messages.add(message);
	}
	
	
	private static void show() {
		if(!initialized) {
			initialize();
		}
		frame.setVisible(true);
	}


	private static void initialize() {
		if(initialized) {
			return;
		}
		JPanel panel = new JPanel ();
		panel.setBorder ( new TitledBorder ( new EtchedBorder (), "Log" ) );


		textArea = new JTextArea (16,58 );
		textArea.setEditable ( false );
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    JScrollPane scroll = new JScrollPane ( textArea );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    
	    panel.add ( scroll );

	    frame = new JFrame ();
	    frame.add ( panel );
	    frame.pack ();
	    frame.setVisible ( true );
	}
	
}
