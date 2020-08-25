package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import dominios.Turma;
import metodos.MetodosUteis;

public class TurmaDAO extends DAOGenerico {

	public List<Turma> buscarTurma(String codigoTurma,
			String nomeTurma, String ordenarPor){
		EntityManager gerenciador = getEntityManager();
		
		String hql = "SELECT t FROM Turma t WHERE 1=1 ";
		hql += " AND t.ativo = true ";
		
		if (!MetodosUteis.estaVazia(codigoTurma)) {
			hql += " AND t.codigoTurma = :codigoTurma ";
		}
		if (!MetodosUteis.estaVazia(nomeTurma)) {
			hql += " AND upper(t.nomeTurma) like :nomeTurma ";
		}
		if (!MetodosUteis.estaVazia(ordenarPor)) {
			hql += " ORDER BY :ordenarPor ";
		}
		
		Query q = gerenciador.createQuery(hql);
		
		if (!MetodosUteis.estaVazia(codigoTurma)) {
			q.setParameter("codigoTurma", codigoTurma);
		}
		if (!MetodosUteis.estaVazia(nomeTurma)) {
			q.setParameter("nomeTurma", nomeTurma.toUpperCase());
		}
		if (!MetodosUteis.estaVazia(ordenarPor)) {
			q.setParameter("ordenarPor", ordenarPor);
		}
		
		try {
			List<Turma> resultado = q.getResultList();
			return resultado;
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
