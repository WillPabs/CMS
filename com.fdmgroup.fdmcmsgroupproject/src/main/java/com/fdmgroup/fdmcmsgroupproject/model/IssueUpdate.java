package com.fdmgroup.fdmcmsgroupproject.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * IssueUpdate:  POJO to model a IssueUpdate object to database
 * 
 * @version 1.0
 */
@Entity
@Table(name = "ISSUE_UPDATE")
@NamedQueries({@NamedQuery(name="findAllIssueUpdate", query="SELECT u FROM IssueUpdate u" )
	
})
public class IssueUpdate {

	@Id
	@Column(name="issueUpdate_id", nullable=false, length=4, unique=true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issueUpdate_id_gen")
	@SequenceGenerator(name = "issueUpdate_id_gen", sequenceName = "issueUpdate_id_seq", allocationSize = 1)
	private int issueUpdateId;

	@Column(name="submitted_by", length=4, nullable=false)
	private int author;
	
	@Column(name="update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	@Column(name="update_details", length=300, nullable=false)
	private String updateDetails;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="fk_Issue_ID", nullable=false)
	private Issue issue;
	
	public IssueUpdate() {
		super();
	}
	
	public IssueUpdate(int author, String updateDetails, Issue issue) {
		this.author=author;
		this.updateDetails=updateDetails;
		this.issue=issue;
	}
	
	public int getIssueId() {
		return issue.getIssueId();
	}

	public int getIssueUpdateId() {
		return issueUpdateId;
	}

	public void setIssueUpdateId(int issueId) {
		this.issueUpdateId = issueId;
	}
	
	public Issue getIssue() {
		return issue;
	}
	
	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdate() {
		return updateDetails;
	}

	public void setUpdate(String update) {
		this.updateDetails = update;
	}

	@Override
	public String toString() {
		return "IssueUpdate [issueUpdateId=" + issueUpdateId + ", author=" + author + ", updateDate=" + updateDate
				+ ", updateDetails=" + updateDetails + ", issueId=" + issue.getIssueId() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + author;
		result = prime * result + ((issue == null) ? 0 : issue.hashCode());
		result = prime * result + issueUpdateId;
		result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
		result = prime * result + ((updateDetails == null) ? 0 : updateDetails.hashCode());
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
		IssueUpdate other = (IssueUpdate) obj;
		if (author != other.author)
			return false;
		if (issue == null) {
			if (other.issue != null)
				return false;
		} else if (!issue.equals(other.issue))
			return false;
		if (issueUpdateId != other.issueUpdateId)
			return false;
		if (updateDate == null) {
			if (other.updateDate != null)
				return false;
		} else if (!updateDate.equals(other.updateDate))
			return false;
		if (updateDetails == null) {
			if (other.updateDetails != null)
				return false;
		} else if (!updateDetails.equals(other.updateDetails))
			return false;
		return true;
	} 

}
