package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import components.PunCard;
import javafx.scene.layout.Pane;

public abstract class Pun {
	private String author, title;
	private Date date;
	private List<Comment> comments;
	protected String type;
	
	public abstract Pane getContentLayout();
	public abstract String getUniqueStringFormat();

	public Pun(String author, String title, Date date) {
		this.author = author;
		this.title = title;
		this.date = date;
		comments = new ArrayList<Comment>();
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
