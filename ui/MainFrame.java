package ui;

import dao.PaperDAO;
import model.Paper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class MainFrame extends JFrame {
    private JTextField keywordField;
    private JTextArea outputArea;
    private JTextField partialSearchField;
    private JButton partialSearchButton;
    private JTextField titleField;
    private JTextField abstractField;
    private JTextField contentField; // Added field for content
    private JTextField yearField;
    private JTextField authorIdField;
    private JTextField authorNameField; // Added field for author name
    private JButton addPaperButton;
    private JButton searchByAuthorButton; // Added button for searching by author

    public MainFrame() {
        setTitle("Research Paper Management System");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2)); // Updated grid layout
        JButton viewButton = new JButton("Show View");
        viewButton.addActionListener(new ShowViewButtonListener());
        panel.add(viewButton);
        inputPanel.add(new JLabel("Keywords:"));
        keywordField = new JTextField();
        inputPanel.add(keywordField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        inputPanel.add(searchButton);

        inputPanel.add(new JLabel("Partial Search:"));
        partialSearchField = new JTextField();
        inputPanel.add(partialSearchField);

        partialSearchButton = new JButton("Partial Search");
        partialSearchButton.addActionListener(new PartialSearchButtonListener());
        inputPanel.add(partialSearchButton);

        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Abstract:"));
        abstractField = new JTextField();
        inputPanel.add(abstractField);

        inputPanel.add(new JLabel("Content:")); // Added label for content
        contentField = new JTextField();
        inputPanel.add(contentField);

        inputPanel.add(new JLabel("Year:"));
        yearField = new JTextField();
        inputPanel.add(yearField);

        inputPanel.add(new JLabel("Author ID:"));
        authorIdField = new JTextField();
        inputPanel.add(authorIdField);

        inputPanel.add(new JLabel("Author Name:")); // Added label for author name
        authorNameField = new JTextField();
        inputPanel.add(authorNameField);

        addPaperButton = new JButton("Add Paper");
        addPaperButton.addActionListener(new AddPaperButtonListener());
        inputPanel.add(addPaperButton);

        searchByAuthorButton = new JButton("Search by Author");
        searchByAuthorButton.addActionListener(new SearchByAuthorButtonListener());
        inputPanel.add(searchByAuthorButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String keywords = keywordField.getText();
            PaperDAO paperDAO = new PaperDAO();
            try {
                java.util.List<Paper> papers = paperDAO.searchPapers(keywords);
                outputArea.setText("");
                if (papers.isEmpty()) {
                    outputArea.setText("No papers found with those keywords.\n");
                } else {
                    for (Paper paper : papers) {
                        outputArea.append("ID: " + paper.getId() + "\nTitle: " + paper.getTitle() +
                                "\nAbstract: " + paper.getAbstract() + "\nContent: " + paper.getContent() +
                                "\nYear: " + paper.getYear() + "\nAuthor ID: " + paper.getAuthorId() +
                                "\nAuthor Name: " + paper.getAuthorName() + "\n\n");
                    }
                }
            } catch (SQLException ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    private class PartialSearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String query = partialSearchField.getText();
            PaperDAO paperDAO = new PaperDAO();
            try {
                java.util.List<Paper> papers = paperDAO.searchPapers(query);
                outputArea.setText("");
                if (papers.isEmpty()) {
                    outputArea.setText("No results found for the partial search.\n");
                } else {
                    outputArea.append("Papers:\n");
                    for (Paper paper : papers) {
                        outputArea.append("ID: " + paper.getId() + "\nTitle: " + paper.getTitle() +
                                "\nAbstract: " + paper.getAbstract() + "\nContent: " + paper.getContent() +
                                "\nYear: " + paper.getYear() + "\nAuthor ID: " + paper.getAuthorId() +
                                "\nAuthor Name: " + paper.getAuthorName() + "\n\n");
                    }
                }
            } catch (SQLException ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    private class AddPaperButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText();
            String abstractText = abstractField.getText();
            String content = contentField.getText(); // Added content
            int year = Integer.parseInt(yearField.getText());
            int authorId = Integer.parseInt(authorIdField.getText());
            String authorName = authorNameField.getText(); // Added author name

            Paper paper = new Paper(0, title, abstractText, content, year, authorId, authorName);
            PaperDAO paperDAO = new PaperDAO();
            try {
                paperDAO.addPaper(paper);
                outputArea.setText("Paper added successfully.\n");
            } catch (SQLException ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    private class SearchByAuthorButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String authorName = authorNameField.getText();
            PaperDAO paperDAO = new PaperDAO();
            try {
                java.util.List<Paper> papers = paperDAO.getPapersByAuthorName(authorName);
                outputArea.setText("");
                if (papers.isEmpty()) {
                    outputArea.setText("No papers found for author: " + authorName + "\n");
                } else {
                    for (Paper paper : papers) {
                        outputArea.append("ID: " + paper.getId() + "\nTitle: " + paper.getTitle() +
                                "\nAbstract: " + paper.getAbstract() + "\nContent: " + paper.getContent() +
                                "\nYear: " + paper.getYear() + "\nAuthor ID: " + paper.getAuthorId() +
                                "\nAuthor Name: " + paper.getAuthorName() + "\n\n");
                    }
                }
            } catch (SQLException ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
    private class ShowViewButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        PaperDAO dao = new PaperDAO();
        List<String> viewData = dao.getPapersFromView();
        StringBuilder message = new StringBuilder("Papers (via View):\n");
        for (String line : viewData) {
            message.append(line).append("\n");
        }
        JOptionPane.showMessageDialog(null, message.toString());
    }
}
}