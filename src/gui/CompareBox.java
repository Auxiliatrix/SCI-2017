package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import objects.TeamData;
 
@SuppressWarnings("serial")
public class CompareBox extends JPanel {
    protected JTextArea textArea;
    private final static String newline = "\n";
 
    public CompareBox(TeamData one, TeamData two) {
        super(new GridBagLayout());
 
 
        textArea = new JTextArea(24, 60);
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
        
        String p = one.toComparison(two);
        write(p);
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
    private static void createAndShowGUI(String name, TeamData one, TeamData two) {
        //Create and set up the window.
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(new CompareBox(one, two));
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void generate(String name, TeamData one, TeamData two) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(name, one, two);
            }
        });
    }
}
