package org.example.service.model;

import java.util.Objects;

public class Page {
	private Integer id;
	private String title;
	private String content;

	public Page() {}

	public Page(Integer id, String content, String title) {
		this.id = id;
		this.content = content;
		this.title = title;
	}

	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	public String getContent() {return content;}
	public void setContent(String content) {this.content = content;}
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}


	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Page page = (Page) o;
		return Objects.equals(id, page.id) && Objects.equals(title, page.title) && Objects.equals(content, page.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, content);
	}

	@Override
	public String toString() {
		return "Page{" +
				"id=" + id +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
