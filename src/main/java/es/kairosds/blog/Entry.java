package es.kairosds.blog;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "Entry of the blog")
public class Entry {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ApiModelProperty(notes = "Does't show in the Blog")
	private String authorName;
	@ApiModelProperty(notes = "Appears in the Blog")
	private String authorNick;
	private String title;
	private String summary;
	private String text;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Comment> comments;

	public Entry() {
	}

	public Entry(String authorName, String authorNick, String title, String summary, String text) {
		super();
		this.authorName = authorName;
		this.authorNick = authorNick;
		this.title = title;
		this.summary = summary;
		this.text = text;
	}

	public String getAuthorName() {
		return authorName;
	}

	public String getAuthorNick() {
		return authorNick;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public long getId() {
		return id;
	}

	public String getSummary() {
		return summary;
	}

	public String getText() {
		return text;
	}

	public String getTitle() {
		return title;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setAuthorNick(String authorNick) {
		this.authorNick = authorNick;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTitle(String title) {
		this.title = title;
	};

}
