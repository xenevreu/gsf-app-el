package br.com.gsf.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ReinfControle.
 */
@Entity
@Table(name = "rif_controle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reinfcontrole")
public class ReinfControle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "dt_ini")
    private Instant dtIni;

    @Column(name = "dt_fim")
    private Instant dtFim;

    @Size(max = 1)
    @Column(name = "tp_ambiente", length = 1)
    private String tpAmbiente;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Empresa empresa;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ReinfStatusControle status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDtIni() {
        return dtIni;
    }

    public ReinfControle dtIni(Instant dtIni) {
        this.dtIni = dtIni;
        return this;
    }

    public void setDtIni(Instant dtIni) {
        this.dtIni = dtIni;
    }

    public Instant getDtFim() {
        return dtFim;
    }

    public ReinfControle dtFim(Instant dtFim) {
        this.dtFim = dtFim;
        return this;
    }

    public void setDtFim(Instant dtFim) {
        this.dtFim = dtFim;
    }

    public String getTpAmbiente() {
        return tpAmbiente;
    }

    public ReinfControle tpAmbiente(String tpAmbiente) {
        this.tpAmbiente = tpAmbiente;
        return this;
    }

    public void setTpAmbiente(String tpAmbiente) {
        this.tpAmbiente = tpAmbiente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public ReinfControle empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public ReinfStatusControle getStatus() {
        return status;
    }

    public ReinfControle status(ReinfStatusControle reinfStatusControle) {
        this.status = reinfStatusControle;
        return this;
    }

    public void setStatus(ReinfStatusControle reinfStatusControle) {
        this.status = reinfStatusControle;
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
        ReinfControle reinfControle = (ReinfControle) o;
        if (reinfControle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reinfControle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReinfControle{" +
            "id=" + getId() +
            ", dtIni='" + getDtIni() + "'" +
            ", dtFim='" + getDtFim() + "'" +
            ", tpAmbiente='" + getTpAmbiente() + "'" +
            "}";
    }
}
