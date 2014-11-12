package br.com.uaijug.kairos.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import br.com.uaijug.kairos.domain.util.CustomLocalDateSerializer;
import br.com.uaijug.kairos.validation.AssertMethodAsTrue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;

/**
 * A Event.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name="T_EVENT")
@AssertMethodAsTrue(value="isValidDateRange")
public class Event implements Serializable {

	private static final long serialVersionUID = -7153531826933994787L;

	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name="id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "as_will_be")
    private String asWillBe;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToMany
    @JoinTable(name="T_EVENT_SPEAKER",
    joinColumns=  @JoinColumn( name = "speaker_id"),
    inverseJoinColumns= @JoinColumn(name = "event_id") )

    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Speaker> speakers = new HashSet<>();

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

    public String getAsWillBe() {
        return this.asWillBe;
    }

    public void setAsWillBe(String asWillBe) {
        this.asWillBe = asWillBe;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Speaker> getSpeakers() {
        return this.speakers;
    }

    public void setSpeakers(Set<Speaker> speakers) {
        this.speakers = speakers;
    }

    public boolean isValidDateRange() {
    	return this.startDate.isBefore(this.endDate) || this.startDate.isEqual(this.endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Event event = (Event) o;

        if (this.id != null ? !this.id.equals(event.id) : event.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (this.id ^ (this.id >>> 32));
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + this.id +
                ", name='" + this.name + "'" +
                ", description='" + this.description + "'" +
                ", asWillBe='" + this.asWillBe + "'" +
                ", startDate='" + this.startDate + "'" +
                ", endDate='" + this.endDate + "'" +
                '}';
    }
}
