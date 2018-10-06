package br.com.gsf.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ReinfStatusControle.
 */
@Entity
@Table(name = "rif_status_ctrl")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reinfstatuscontrole")
public class ReinfStatusControle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "cod_status", length = 30, nullable = false)
    private String codStatus;

    @Size(max = 200)
    @Column(name = "descr", length = 200)
    private String descr;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodStatus() {
        return codStatus;
    }

    public ReinfStatusControle codStatus(String codStatus) {
        this.codStatus = codStatus;
        return this;
    }

    public void setCodStatus(String codStatus) {
        this.codStatus = codStatus;
    }

    public String getDescr() {
        return descr;
    }

    public ReinfStatusControle descr(String descr) {
        this.descr = descr;
        return this;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReinfStatusControle reinfStatusControle = (ReinfStatusControle) o;
        if (reinfStatusControle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reinfStatusControle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReinfStatusControle{" +
            "id=" + getId() +
            ", codStatus='" + getCodStatus() + "'" +
            ", descr='" + getDescr() + "'" +
            "}";
    }
}
