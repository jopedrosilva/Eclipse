package mbean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import dao.CursoDAO;
import dominios.Curso;

@ManagedBean
@RequestScoped
public class CursoMBean {
	
	public List<Curso> autocompleteCursosByNome(String query) {
		return new CursoDAO().buscarPelaColuna("nomeCurso", query, Curso.class);
	}
}
