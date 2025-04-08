package model;

public class Paper {
    private int id;
    private String title;
    private String abstractText;
    private String content;
    private int year;
    private int authorId;
    private String authorName; // Added field for author name

    public Paper() {
        // Default constructor
    }

    public Paper(int id, String title, String abstractText, String content, int year, int authorId, String authorName) {
        this.id = id;
        this.title = title;
        this.abstractText = abstractText;
        this.content = content;
        this.year = year;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAbstract() { return abstractText; }
    public void setAbstract(String abstractText) { this.abstractText = abstractText; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
}