package br.com.gsf.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ReinfEvento.
 */
@Entity
@Table(name = "rif_evento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reinfevento")
public class ReinfEvento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 30)
    @Column(name = "cod_evento", length = 30)
    private String codEvento;

    @Size(max = 200)
    @Column(name = "desc_evento", length = 200)
    private String descEvento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodEvento() {
        return codEvento;
    }

    public ReinfEvento codEvento(String codEvento) {
        this.codEvento = codEvento;
        return this;
    }

    public void setCodEvento(String codEvento) {
        this.codEvento = codEvento;
    }

    public String getDescEvento() {
        return descEvento;
    }

    public ReinfEvento descEvento(String descEvento) {
        this.descEvento = descEvento;
        return this;
    }

    public void setDescEvento(String descEvento) {
        this.descEvento = descEvento;
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
        ReinfEvento reinfEvento = (ReinfEvento) o;
        if (reinfEvento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reinfEvento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReinfEvento{" +
            "id=" + getId() +
            ", codEvento='" + getCodEvento() + "'" +
            ", descEvento='" + getDescEvento() + "'" +
            "}";
    }
}
