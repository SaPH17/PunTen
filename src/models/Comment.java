package models;

import java.util.Date;

public class Comment {
	private String author, content;
	private Date date;
	private final String SEPARATOR = "^";
	
	public Comment(String author, String content, Date date) {
		this.author = author;
		this.content = content;
		this.date = date;
	}
	
	public String getPrintFormat() {
		return content + SEPARATOR + author + SEPARATOR + date.getTime();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
