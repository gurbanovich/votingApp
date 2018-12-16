package voting.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "votes")
public class Vote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "vote_id", nullable = false)
	public long voteId;

	@Column(name = "no_item")
	private Integer noItem;

	@Column(name = "item")
	private String item;

	@Column(name = "number_of_votes")
	private long numberOfVotes;

	@Column(name = "stat_of_votes")
	private double statOfVotes;

	@ManyToOne
	@JoinColumn(name = "theme_id")
	private Theme theme;

	public Vote() {
	}

	public Vote(Integer noItem, String item) {
		this.noItem = noItem;
		this.item = item;
	}

	public Vote(Integer noItem, String item, long numberOfVotes) {
		this.noItem = noItem;
		this.item = item;
		this.numberOfVotes = numberOfVotes;
	}

	public Vote(Integer noItem, String item, long numberOfVotes, Theme theme) {
		this.noItem = noItem;
		this.item = item;
		this.numberOfVotes = numberOfVotes;
		this.theme = theme;
	}

	public Vote(Integer noItem, String item, long numberOfVotes, double statOfVotes, Theme theme) {
		this.noItem = noItem;
		this.item = item;
		this.numberOfVotes = numberOfVotes;
		this.statOfVotes = statOfVotes;
		this.theme = theme;
	}

	public long getVoteId() {
		return voteId;
	}

	public void setVoteId(long id) {
		this.voteId = id;
	}

	public Integer getNoItem() {
		return noItem;
	}

	public void setNoItem(Integer noItem) {
		this.noItem = noItem;
	}

	public String getItem() {
		return item;
	}

	public void setSItem(String item) {
		this.item = item;
	}

	public long getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(long numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

	public double getStatOfVotes() {
		return statOfVotes;
	}

	public void setStatOfVotes(double statOfVotes) {
		this.statOfVotes = statOfVotes;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

}
