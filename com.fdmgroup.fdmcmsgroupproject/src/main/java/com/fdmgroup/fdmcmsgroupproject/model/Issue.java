package com.fdmgroup.fdmcmsgroupproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Issue: POJO to model a Issue object to database
 *
 * @version 1.0
 */
@Entity
@Table
@NamedQueries({ @NamedQuery(name = "findByDept", query = "SELECT i FROM Issue i WHERE i.assignedToDeptId = :depId ORDER BY i.issueId DESC"),
		@NamedQuery(name = "findUnassigned", query = "SELECT i FROM Issue i WHERE i.assignedToDeptId = 1 ORDER BY i.issueId DESC"),
		@NamedQuery(name = "findAllIssues", query = "SELECT i FROM Issue i ORDER BY i.issueId DESC"),
		@NamedQuery(name = "findIssuesByUser", query = "SELECT i FROM Issue i WHERE i.author = :userId")
		})

public class Issue implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "issue_id", length = 4, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_id_gen")
	@SequenceGenerator(name = "issue_id_gen", sequenceName = "issue_id_seq", allocationSize = 1)
	private int issueId;

	@Column(name = "title", length = 30, nullable = false)
	private String title;

	@Column(name = "issue_desc", length = 300, nullable = false)
	private String issueDesc;

	@Column(name = "admin_comment", length = 300)
	private String adminComment;

	@Column(name = "assigned_to_dept_id", length = 4)
	private int assignedToDeptId;

	@Column(name = "author", length = 4, nullable = false)
	private int author;

	@Column(name = "status", length = 30)
	private String status;

	@Column(name = "priority", length = 30)
	private String priority;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_submitted")
	private Date dateSubmitted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_resolved")
	private Date dateResolved;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="issue", cascade = CascadeType.ALL)
	private List<IssueUpdate> updates;
	
	public Issue(String title, String issueDesc, int assignedToDeptId, int author) {
		this();
		this.title=title;
		this.issueDesc=issueDesc;
		this.assignedToDeptId=assignedToDeptId;
		this.author=author;
		this.updates = new ArrayList<>();
	}
	
	public Issue() {
		super();
		this.updates = new ArrayList<>();
	}

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIssueDesc() {
		return issueDesc;
	}

	public void setIssueDesc(String issueDesc) {
		this.issueDesc = issueDesc;
	}

	public String getAdminComment() {
		return adminComment;
	}

	public void setAdminComment(String adminComment) {
		this.adminComment = adminComment;
	}

	public int getAssignedToDeptId() {
		return assignedToDeptId;
	}

	public void setAssignedToDeptId(int assignedToDeptId) {
		this.assignedToDeptId = assignedToDeptId;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public Date getDateResolved() {
		return dateResolved;
	}

	public void setDateResolved(Date dateResolved) {
		this.dateResolved = dateResolved;
	}

	public List<IssueUpdate> getUpdates() {
		return updates;
	}

	public void setUpdates(List<IssueUpdate> updates) {
		this.updates = updates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + author;
		result = prime * result + ((issueDesc == null) ? 0 : issueDesc.hashCode());
		result = prime * result + issueId;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Issue other = (Issue) obj;
		if (author != other.author)
			return false;
		if (issueDesc == null) {
			if (other.issueDesc != null)
				return false;
		} else if (!issueDesc.equals(other.issueDesc))
			return false;
		if (issueId != other.issueId)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Issue [issueId=" + issueId + ", title=" + title + ", issueDesc=" + issueDesc + ", adminComment="
				+ adminComment + ", assignedToDeptId=" + assignedToDeptId + ", author=" + author + ", status=" + status
				+ ", priority=" + priority + ", dateSubmitted=" + dateSubmitted + ", dateResolved=" + dateResolved
				+ ", updates=" + updates + "]";
	}
	
}
