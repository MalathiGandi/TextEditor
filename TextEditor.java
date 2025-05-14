import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor extends JFrame implements ActionListener {

    JTextArea textArea;
    JScrollPane scrollPane;
    JFileChooser fileChooser;
    String[] fontList = {"Arial", "Courier New", "Times New Roman", "Verdana", "Comic Sans MS"};

    public TextEditor() {
        // Frame setup
        setTitle("Simple Text Editor");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Text area setup
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        // File chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Documents (*.txt)", "txt"));

        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        addMenuItem(fileMenu, "New");
        addMenuItem(fileMenu, "Open");
        addMenuItem(fileMenu, "Save");
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Close");
        menuBar.add(fileMenu);

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        addMenuItem(editMenu, "Cut");
        addMenuItem(editMenu, "Copy");
        addMenuItem(editMenu, "Paste");
        menuBar.add(editMenu);

        // Font Menu
        JMenu fontMenu = new JMenu("Font");
        for (String font : fontList) {
            JMenuItem fontItem = new JMenuItem(font);
            fontItem.addActionListener(e -> textArea.setFont(new Font(font, Font.PLAIN, 16)));
            fontMenu.add(fontItem);
        }
        menuBar.add(fontMenu);

        setJMenuBar(menuBar);
        setVisible(true);
    }

    private void addMenuItem(JMenu menu, String name) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(this);
        menu.add(item);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New":
                textArea.setText("");
                break;

            case "Open":
                int openOption = fileChooser.showOpenDialog(this);
                if (openOption == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        textArea.read(reader, null);
                    } catch (IOException ex) {
                        showError("Error reading file");
                    }
                }
                break;

            case "Save":
                int saveOption = fileChooser.showSaveDialog(this);
                if (saveOption == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        textArea.write(writer);
                    } catch (IOException ex) {
                        showError("Error saving file");
                    }
                }
                break;

            case "Cut":
                textArea.cut();
                break;

            case "Copy":
                textArea.copy();
                break;

            case "Paste":
                textArea.paste();
                break;

            case "Close":
                System.exit(0);
                break;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextEditor::new);
    }
}
