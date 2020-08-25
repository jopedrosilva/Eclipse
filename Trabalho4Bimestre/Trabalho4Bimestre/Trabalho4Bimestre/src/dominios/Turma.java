package dominios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.primefaces.model.UploadedFile;

@Entity
public class Turma {
	
	@Id
	@GeneratedValue
	@NotNull
	private int idTurma;
	
	@Column(nullable = false)
	private String nomeTurma;
	
	@Column(nullable = false)
	private String codigoTurma;
	
	@Column(nullable = false)
	private int qtdvagas;
	
	@Column(nullable = false)
	private boolean ativo = true;
	
	@Transient
	private UploadedFile arquivoTurma;
	
	@Column(name="id_foto")
	private Integer idFotoTurma;
	
	@ManyToOne
	@JoinColumn(name="idcurso")
	private Curso curso;
	
	@Override	
	public int hashCode() {	
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeTurma == null) ? 0 : nomeTurma.hashCode());
		result = prime * result + idTurma;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turma other = (Turma) obj;
		if (nomeTurma == null) {
			if (other.nomeTurma != null)
				return false;
		} else if (!nomeTurma.equals(other.nomeTurma))
			return false;
		if (idTurma != other.idTurma)
			return false;
		return true;
	}


	public String getNomeTurma() {
		return nomeTurma;
	}

	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
	}

	public int getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}

	public String getCodigoTurma() {
		return codigoTurma;
	}

	public void setCodigoTurma(String codigoTurma) {
		this.codigoTurma = codigoTurma;
	}

	public int getQtdvagas() {
		return qtdvagas;
	}

	public void setQtdvagas(int qtdvagas) {
		this.qtdvagas = qtdvagas;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public UploadedFile getArquivoTurma() {
		return arquivoTurma;
	}

	public void setArquivoTurma(UploadedFile arquivoTurma) {
		this.arquivoTurma = arquivoTurma;
	}

	public Integer getIdFotoTurma() {
		return idFotoTurma;
	}

	public void setIdFotoTurma(Integer idFotoTurma) {
		this.idFotoTurma = idFotoTurma;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
}
