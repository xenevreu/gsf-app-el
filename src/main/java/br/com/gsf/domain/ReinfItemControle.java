package br.com.gsf.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ReinfItemControle.
 */
@Entity
@Table(name = "rif_item_cntr")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reinfitemcontrole")
public class ReinfItemControle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ReinfEvento evento;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ReinfStatusControle status;

    @ManyToOne
    @JsonIgnoreProperties("itens")
    private ReinfControle controle;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReinfEvento getEvento() {
        return evento;
    }

    public ReinfItemControle evento(ReinfEvento reinfEvento) {
        this.evento = reinfEvento;
        return this;
    }

    public void setEvento(ReinfEvento reinfEvento) {
        this.evento = reinfEvento;
    }

    public ReinfStatusControle getStatus() {
        return status;
    }

    public ReinfItemControle status(ReinfStatusControle reinfStatusControle) {
        this.status = reinfStatusControle;
        return this;
    }

    public void setStatus(ReinfStatusControle reinfStatusControle) {
        this.status = reinfStatusControle;
    }

    public ReinfControle getControle() {
        return controle;
    }

    public ReinfItemControle controle(ReinfControle reinfControle) {
        this.controle = reinfControle;
        return this;
    }

    public void setControle(ReinfControle reinfControle) {
        this.controle = reinfControle;
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
        ReinfItemControle reinfItemControle = (ReinfItemControle) o;
        if (reinfItemControle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reinfItemControle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReinfItemControle{" +
            "id=" + getId() +
            "}";
    }
}
