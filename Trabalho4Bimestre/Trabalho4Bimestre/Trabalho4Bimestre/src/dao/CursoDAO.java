package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import dominios.Curso;
import metodos.MetodosUteis;

public class CursoDAO extends DAOGenerico {

	public List<Curso> buscarCurso(String codigoCurso,
			String nomeCurso, String ordenarPor){
		EntityManager gerenciador = getEntityManager();
		
		String hql = "SELECT c FROM Curso c WHERE 1=1 ";
		hql += " AND c.ativo = true ";
		
		if (!MetodosUteis.estaVazia(codigoCurso)) {
			hql += " AND c.codigoCurso = :codigoCurso ";
		}
		if (!MetodosUteis.estaVazia(nomeCurso)) {
			hql += " AND upper(c.nomeCurso) like :nomeCurso ";
		}
		if (!MetodosUteis.estaVazia(ordenarPor)) {
			hql += " ORDER BY :ordenarPor ";
		}
		
		Query q = gerenciador.createQuery(hql);
		
		if (!MetodosUteis.estaVazia(codigoCurso)) {
			q.setParameter("codigoCurso", codigoCurso);
		}
		if (!MetodosUteis.estaVazia(nomeCurso)) {
			q.setParameter("nomeCurso", nomeCurso.toUpperCase());
		}
		if (!MetodosUteis.estaVazia(ordenarPor)) {
			q.setParameter("ordenarPor", ordenarPor);
		}
		
		try {
			List<Curso> resultado = q.getResultList();
			return resultado;
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
