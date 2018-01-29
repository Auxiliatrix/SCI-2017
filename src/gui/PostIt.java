package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
 
@SuppressWarnings("serial")
public class PostIt extends JPanel {
    protected JTextArea textArea;
    private final static String newline = "\n";
 
    public PostIt(ArrayList<String> lines) {
        super(new GridBagLayout());
 
 
        textArea = new JTextArea(25, 30);
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Courier New",Font.BOLD,36));

        JScrollPane scrollPane = new JScrollPane(textArea);
 
        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
 
        c.fill = GridBagConstraints.HORIZONTAL;
 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
        
        for( String l : lines )
        	this.write(l);
    }
 
    public PostIt(String line) {
        super(new GridBagLayout());
 
 
        textArea = new JTextArea(25, 30);
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Courier New",Font.BOLD,36));

        JScrollPane scrollPane = new JScrollPane(textArea);
 
        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
 
        c.fill = GridBagConstraints.HORIZONTAL;
 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
        
        this.write(line);
    }
    
    private void write(String text) {
        textArea.append(text + newline);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI(String name, ArrayList<String> lines) {
        //Create and set up the window.
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(new PostIt(lines));
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    private static void createAndShowGUI(String name, String line) {
        //Create and set up the window.
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(new PostIt(line));
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void generate(String name, ArrayList<String> lines) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(name, lines);
            }
        });
    }
    public static void generate(String name, String line) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(name, line);
            }
        });
    }
}
