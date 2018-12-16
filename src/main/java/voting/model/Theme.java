package voting.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name= "themes")
public class Theme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "theme_id", nullable = false)
	public long themeId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "status")
	@JsonIgnoreProperties
	private String status;
	
	@JsonIgnoreProperties
	@OneToMany(targetEntity = Vote.class, mappedBy = "theme")
	private Set<Vote> votes;
	
	public Theme() {}
	
	public Theme(String name) {
		this.name=name;
		this.status="stopped";
	}
	
	public long getThemeId() {
		return themeId;
	}
	
	public void setThemeId(long id) {
		this.themeId=id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status=status;
	}
	
} 
