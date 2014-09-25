package br.com.uaijug.kairos.domain;

import java.io.Serializable;

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
	private Long id;

	@Size(min = 1, max = 255)
	private String name;

	@Size(min = 1, max = 144)
	private String description;

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
		return "Speaker [id=" + this.id + ", name=" + this.name
				+ ", description=" + this.description + "]";
	}

}
