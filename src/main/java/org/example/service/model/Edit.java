package org.example.service.model;

import java.time.Instant;
import java.util.Objects;
import java.sql.Timestamp;

public class Edit {
	private Integer id;
	private Integer AccountID;
	private Integer PageID;
	private String Content;
	private Instant Timestamp;

	public Edit() {}

	public Edit(Integer id, Integer accountID, Integer pageID, String content, Instant timestamp) {
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
		return "Edit{" +
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
		Edit edit = (Edit) o;
		return Objects.equals(id, edit.id) && Objects.equals(AccountID, edit.AccountID) && Objects.equals(PageID, edit.PageID) && Objects.equals(Content, edit.Content) && Objects.equals(Timestamp, edit.Timestamp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, AccountID, PageID, Content, Timestamp);
	}
}