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
 * A Empresa.
 */
@Entity
@Table(name = "gsf_empresa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "empresa")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 120)
    @Column(name = "nome_empresa", length = 120, nullable = false)
    private String nomeEmpresa;

    @Column(name = "dt_inclusao")
    private Instant dtInclusao;

    @Size(max = 30)
    @Column(name = "usu_inclusao", length = 30)
    private String usuInclusao;

    @Column(name = "dt_ult_alteracao")
    private Instant dtUltAlteracao;

    @Column(name = "usu_ult_alteracao")
    private Instant usuUltAlteracao;

    @NotNull
    @Size(max = 15)
    @Column(name = "cnpj", length = 15, nullable = false)
    private String cnpj;

    @Size(max = 100)
    @Column(name = "sigla_la", length = 100)
    private String siglaLa;

    @Size(max = 1)
    @Column(name = "conteudo_codigo", length = 1)
    private String conteudoCodigo;

    @Column(name = "id_uf")
    private Long idUf;

    @Size(max = 100)
    @Column(name = "nome_fantasia", length = 100)
    private String nomeFantasia;

    @Column(name = "id_local_central")
    private Long idLocalCentral;

    @Size(max = 512)
    @Column(name = "ds_logo", length = 512)
    private String dsLogo;

    @Size(max = 512)
    @Column(name = "caminho_log", length = 512)
    private String caminhoLog;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Empresa empresaPai;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public Empresa nomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
        return this;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public Instant getDtInclusao() {
        return dtInclusao;
    }

    public Empresa dtInclusao(Instant dtInclusao) {
        this.dtInclusao = dtInclusao;
        return this;
    }

    public void setDtInclusao(Instant dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    public String getUsuInclusao() {
        return usuInclusao;
    }

    public Empresa usuInclusao(String usuInclusao) {
        this.usuInclusao = usuInclusao;
        return this;
    }

    public void setUsuInclusao(String usuInclusao) {
        this.usuInclusao = usuInclusao;
    }

    public Instant getDtUltAlteracao() {
        return dtUltAlteracao;
    }

    public Empresa dtUltAlteracao(Instant dtUltAlteracao) {
        this.dtUltAlteracao = dtUltAlteracao;
        return this;
    }

    public void setDtUltAlteracao(Instant dtUltAlteracao) {
        this.dtUltAlteracao = dtUltAlteracao;
    }

    public Instant getUsuUltAlteracao() {
        return usuUltAlteracao;
    }

    public Empresa usuUltAlteracao(Instant usuUltAlteracao) {
        this.usuUltAlteracao = usuUltAlteracao;
        return this;
    }

    public void setUsuUltAlteracao(Instant usuUltAlteracao) {
        this.usuUltAlteracao = usuUltAlteracao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Empresa cnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSiglaLa() {
        return siglaLa;
    }

    public Empresa siglaLa(String siglaLa) {
        this.siglaLa = siglaLa;
        return this;
    }

    public void setSiglaLa(String siglaLa) {
        this.siglaLa = siglaLa;
    }

    public String getConteudoCodigo() {
        return conteudoCodigo;
    }

    public Empresa conteudoCodigo(String conteudoCodigo) {
        this.conteudoCodigo = conteudoCodigo;
        return this;
    }

    public void setConteudoCodigo(String conteudoCodigo) {
        this.conteudoCodigo = conteudoCodigo;
    }

    public Long getIdUf() {
        return idUf;
    }

    public Empresa idUf(Long idUf) {
        this.idUf = idUf;
        return this;
    }

    public void setIdUf(Long idUf) {
        this.idUf = idUf;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public Empresa nomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
        return this;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public Long getIdLocalCentral() {
        return idLocalCentral;
    }

    public Empresa idLocalCentral(Long idLocalCentral) {
        this.idLocalCentral = idLocalCentral;
        return this;
    }

    public void setIdLocalCentral(Long idLocalCentral) {
        this.idLocalCentral = idLocalCentral;
    }

    public String getDsLogo() {
        return dsLogo;
    }

    public Empresa dsLogo(String dsLogo) {
        this.dsLogo = dsLogo;
        return this;
    }

    public void setDsLogo(String dsLogo) {
        this.dsLogo = dsLogo;
    }

    public String getCaminhoLog() {
        return caminhoLog;
    }

    public Empresa caminhoLog(String caminhoLog) {
        this.caminhoLog = caminhoLog;
        return this;
    }

    public void setCaminhoLog(String caminhoLog) {
        this.caminhoLog = caminhoLog;
    }

    public Empresa getEmpresaPai() {
        return empresaPai;
    }

    public Empresa empresaPai(Empresa empresa) {
        this.empresaPai = empresa;
        return this;
    }

    public void setEmpresaPai(Empresa empresa) {
        this.empresaPai = empresa;
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
        Empresa empresa = (Empresa) o;
        if (empresa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empresa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Empresa{" +
            "id=" + getId() +
            ", nomeEmpresa='" + getNomeEmpresa() + "'" +
            ", dtInclusao='" + getDtInclusao() + "'" +
            ", usuInclusao='" + getUsuInclusao() + "'" +
            ", dtUltAlteracao='" + getDtUltAlteracao() + "'" +
            ", usuUltAlteracao='" + getUsuUltAlteracao() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", siglaLa='" + getSiglaLa() + "'" +
            ", conteudoCodigo='" + getConteudoCodigo() + "'" +
            ", idUf=" + getIdUf() +
            ", nomeFantasia='" + getNomeFantasia() + "'" +
            ", idLocalCentral=" + getIdLocalCentral() +
            ", dsLogo='" + getDsLogo() + "'" +
            ", caminhoLog='" + getCaminhoLog() + "'" +
            "}";
    }
}
