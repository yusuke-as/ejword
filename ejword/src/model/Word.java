package model;

import java.io.Serializable;

public class Word implements Serializable{
	private int id;
	private String title;
	private String body;
	public Word() {
	}
	public Word(String title, String body) {
		this.title = title;
		this.body = body;
	}
	public Word(int id, String title, String body) {
		this(title,body);
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

}
