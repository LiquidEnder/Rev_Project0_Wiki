package org.example.repository.entities;

import java.util.Objects;
import java.time.Instant;

public class EditsEntity {
	private Integer id;
	private Integer AccountID;
	private Integer PageID;
	private String Content;
	private Instant Timestamp;

	public EditsEntity() {}

	public EditsEntity(Integer id, Integer accountID, Integer pageID, String content, Instant timestamp) {
		this.id = id;
		AccountID = accountID;
		PageID = pageID;
		Content = content;
		Timestamp = timestamp;
	}

	public Integer getId() {return id;}

	public void setId(Integer id) {this.id = id;}

	public Integer getAccountID() {return AccountID;}

	public void setAccountID(Integer accountID) {AccountID = accountID;}

	public Integer getPageID() {return PageID;}

	public void setPageID(Integer pageID) {PageID = pageID;}

	public String getContent() {return Content;}

	public void setContent(String content) {Content = content;}

	public Instant getTimestamp() {return Timestamp;}

	public void setTimestamp(Instant timestamp) {Timestamp = timestamp;}

	@Override
	public String toString() {
		return "EditsEntity{" +
				"id=" + id +
				", AccountID=" + AccountID +
				", PageID=" + PageID +
				", Content='" + Content + '\'' +
				", Timestamp=" + Timestamp +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		EditsEntity that = (EditsEntity) o;
		return Objects.equals(id, that.id) && Objects.equals(AccountID, that.AccountID) && Objects.equals(PageID, that.PageID) && Objects.equals(Content, that.Content) && Objects.equals(Timestamp, that.Timestamp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, AccountID, PageID, Content, Timestamp);
	}
}
