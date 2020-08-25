package dominios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.primefaces.model.UploadedFile;

@Entity
public class Curso {
	
	@Id
	@GeneratedValue
	@NotNull
	private int idCurso;
	
	@Column(nullable = false)
	private String nomeCurso;
	
	@Column(nullable = false)
	private String codigoCurso;
	
	@Column(nullable = false)
	private String descricao;
	
	@Column(nullable = false)
	private int cargahoraria;
	
	@Column(nullable = false)
	private boolean ativo = true;
	
	@Transient
	private UploadedFile arquivoCurso;
	
	@Column(name="id_foto")
	private Integer idFotoCurso;
	
	@Override	
	public int hashCode() {	
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeCurso == null) ? 0 : nomeCurso.hashCode());
		result = prime * result + idCurso;
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
		Curso other = (Curso) obj;
		if (nomeCurso == null) {
			if (other.nomeCurso != null)
				return false;
		} else if (!nomeCurso.equals(other.nomeCurso))
			return false;
		if (idCurso != other.idCurso)
			return false;
		return true;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public String getCodigoCurso() {
		return codigoCurso;
	}

	public void setCodigoCurso(String codigoCurso) {
		this.codigoCurso = codigoCurso;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getCargahoraria() {
		return cargahoraria;
	}

	public void setCargahoraria(int cargahoraria) {
		this.cargahoraria = cargahoraria;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public UploadedFile getArquivoCurso() {
		return arquivoCurso;
	}

	public void setArquivoCurso(UploadedFile arquivoCurso) {
		this.arquivoCurso = arquivoCurso;
	}

	public Integer getIdFotoCurso() {
		return idFotoCurso;
	}

	public void setIdFotoCurso(Integer idFotoCurso) {
		this.idFotoCurso = idFotoCurso;
	}
	
}
