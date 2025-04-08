package dao;

import model.Paper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public List<String> getPapersFromView() {
    List<String> results = new ArrayList<>();
    String sql = "SELECT * FROM paper_author_view";
    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            String line = "Title: " + rs.getString("paper_title") +
                          ", Author: " + rs.getString("author_name");
            results.add(line);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return results;
}
public class PaperDAO {

    // Method to search papers by partial match in title, abstract, or content
    public List<Paper> searchPapers(String query) throws SQLException {
        List<Paper> papers = new ArrayList<>();
        String sql = "SELECT * FROM papers WHERE title ILIKE ? OR abstract ILIKE ? OR content ILIKE ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            String searchQuery = "%" + query + "%";
            statement.setString(1, searchQuery);
            statement.setString(2, searchQuery);
            statement.setString(3, searchQuery);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Paper paper = new Paper(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("abstract"),
                        resultSet.getString("content"),
                        resultSet.getInt("year"),
                        resultSet.getInt("author_id"),
                        resultSet.getString("author_name")
                    );
                    papers.add(paper);
                }
            }
        }
        return papers;
    }

    // Method to add a new paper
    public void addPaper(Paper paper) throws SQLException {
        String sql = "INSERT INTO papers (title, abstract, content, year, author_id, author_name) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, paper.getTitle());
            statement.setString(2, paper.getAbstract());
            statement.setString(3, paper.getContent());
            statement.setInt(4, paper.getYear());
            statement.setInt(5, paper.getAuthorId());
            statement.setString(6, paper.getAuthorName());
            
            statement.executeUpdate();
        }
    }

    // Method to get papers by author name
    public List<Paper> getPapersByAuthorName(String authorName) throws SQLException {
        List<Paper> papers = new ArrayList<>();
        String sql = "SELECT * FROM papers WHERE author_name ILIKE ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + authorName + "%");
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Paper paper = new Paper(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("abstract"),
                        resultSet.getString("content"),
                        resultSet.getInt("year"),
                        resultSet.getInt("author_id"),
                        resultSet.getString("author_name")
                    );
                    papers.add(paper);
                }
            }
        }
        return papers;
    }
}