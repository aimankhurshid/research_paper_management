import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResearchPaperManager {
    private Connection connection;

    public ResearchPaperManager(Connection connection) {
        this.connection = connection;
    }

    public void addAuthor(String name) throws SQLException {
        String sql = "INSERT INTO authors (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public void addPaper(String title, String abstractText, int year, int authorId) throws SQLException {
        String sql = "INSERT INTO papers (title, abstract, year, author_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, abstractText);
            pstmt.setInt(3, year);
            pstmt.setInt(4, authorId);
            pstmt.executeUpdate();
        }
    }
}