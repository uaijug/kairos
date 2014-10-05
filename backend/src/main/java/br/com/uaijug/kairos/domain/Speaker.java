package br.com.uaijug.kairos.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Speaker.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Speaker implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1125243061308441060L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	private Long id;

	@Size(min = 1, max = 255)
	@Column(name = "name")
	private String name;

	@Size(min = 1, max = 144)
	@Column(name = "description")
	private String description;

	@Size(max = 1000)
	@Column(name = "long_description")
	private String longDescription;

	@Size(max = 255)
	@Column(name = "photo")
	private String photo;

	@Size(max = 255)
	@Column(name = "thumb")
	private String thumb;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLongDescription() {
		return this.longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getThumb() {
		return this.thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}

		Speaker speaker = (Speaker) o;

		if (this.id != speaker.id) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (this.id ^ (this.id >>> 32));
	}

	@Override
	public String toString() {
		return "Speaker [id=" + this.id + ", name=" + this.name + ", description=" + this.description
				+ ", longDescription=" + this.longDescription + ", photo=" + this.photo + ", thumb=" + this.thumb + "]";
	}

	/**
	 * Return true if value is true or false if is false
	 * 
	 * @param value
	 *            the boolean value
	 * @return true if is true
	 */
	public boolean isTrue(boolean value) {
		if (value == true) {
			return true;
		} else {
			return false;
		}
	}
}
