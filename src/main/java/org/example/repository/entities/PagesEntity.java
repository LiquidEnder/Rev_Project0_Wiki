package org.example.repository.entities;

import java.util.Objects;

public class PagesEntity {
	private Integer id;
	private String content;
	private String title;

	public PagesEntity() {}

	public PagesEntity(Integer id, String content, String title) {
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
	public String toString() {
		return "PagesEntity{" +
				"id=" + id +
				", content='" + content + '\'' +
				", title='" + title + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		PagesEntity that = (PagesEntity) o;
		return Objects.equals(id, that.id) && Objects.equals(content, that.content) && Objects.equals(title, that.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, content, title);
	}
}
