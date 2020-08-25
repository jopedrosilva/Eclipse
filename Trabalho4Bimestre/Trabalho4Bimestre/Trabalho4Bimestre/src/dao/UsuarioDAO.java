package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import dominios.Usuario;
import metodos.MetodosUteis;

public class UsuarioDAO extends DAOGenerico {

	public List<Usuario> buscarUsuario(String email,
			String nome, String ordenarPor){
		EntityManager gerenciador = getEntityManager();
		
		String hql = "SELECT u FROM Usuario u WHERE 1=1 ";
		hql += " AND u.ativo = true ";
		
		if (!MetodosUteis.estaVazia(email)) {
			hql += " AND u.email = :email ";
		}
		if (!MetodosUteis.estaVazia(nome)) {
			hql += " AND upper(u.nomeUsuario) like :nome ";
		}
		if (!MetodosUteis.estaVazia(ordenarPor)) {
			hql += " ORDER BY :ordenarPor ";
		}
		
		Query q = gerenciador.createQuery(hql);
		
		if (!MetodosUteis.estaVazia(email)) {
			q.setParameter("email", email);
		}
		if (!MetodosUteis.estaVazia(nome)) {
			q.setParameter("nome", nome.toUpperCase());
		}
		if (!MetodosUteis.estaVazia(ordenarPor)) {
			q.setParameter("ordenarPor", ordenarPor);
		}
		
		try {
			List<Usuario> resultado = q.getResultList();
			return resultado;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Usuario findUsuarioByLoginSenha(String login, String senha) {
		EntityManager em = getEntityManager();
		
		String hql = "SELECT usuario";
		hql += " FROM Usuario usuario WHERE "
				+"usuario.email = :login and usuario.senha = :senha";
		
		Query q = em.createQuery(hql);
		q.setParameter("login", login);
		q.setParameter("senha", senha);
		
		try {
			Usuario usuario = (Usuario) q.getSingleResult();
			return usuario;
		} catch (NoResultException e) {
			return null;
		}
		
	}

}
