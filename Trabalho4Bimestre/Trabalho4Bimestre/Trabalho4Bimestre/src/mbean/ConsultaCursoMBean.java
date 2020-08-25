package mbean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import dao.Database;
import dao.CursoDAO;
import dominios.Curso;

@ManagedBean
@ViewScoped
public class ConsultaCursoMBean {

private Curso cursoBusca;
	
	private boolean mostrarTodos;
	
	private List<Curso> cursosEncontrados;	
	
	@ManagedProperty(value="#{cadastroCursoMBean}")	
	private CadastroCursoMBean mBeanCadastro;
	
	public ConsultaCursoMBean () {
		cursoBusca = new Curso ();
	}

	public String buscar() {
		CursoDAO dao = new CursoDAO();
		
		String ordenarPor = "c.nomeCurso ASC";
		
		cursosEncontrados = 
				dao.buscarCurso(
						cursoBusca.getCodigoCurso(), 
						cursoBusca.getNomeCurso(), 
						ordenarPor);
		
		return null;
	}
	
	public String remover(Curso c) {	
		EntityManager gerenciador = 
				Database.getInstance().getEntityManager();
		CursoDAO dao = new CursoDAO();
		
		try {
			gerenciador.getTransaction().begin();
			
			if (c.isAtivo()) {
				dao.atualizarCampo(
						c.getClass(), c.getIdCurso(), "ativo", false);
			}
			
			gerenciador.getTransaction().commit();
			
			//Forçando recarregamento do usuário pelo Hibernate
			dao.desanexarEntidade(c);
		} catch (Exception e) {
			e.printStackTrace();
			
			if (gerenciador.getTransaction().isActive()) {
				gerenciador.getTransaction().rollback();
			}
		}
		
		return buscar();
	}
	
	public Curso getCursoBusca() {
		return cursoBusca;
	}

	public void setCursoBusca(Curso cursoBusca) {
		this.cursoBusca = cursoBusca;
	}

	public boolean isMostrarTodos() {
		return mostrarTodos;
	}

	public void setMostrarTodos(boolean mostrarTodos) {
		this.mostrarTodos = mostrarTodos;
	}

	public List<Curso> getCursosEncontrados() {
		return cursosEncontrados;
	}

	public void setCursosEncontrados(List<Curso> cursosEncontrados) {
		this.cursosEncontrados = cursosEncontrados;
	}

	public CadastroCursoMBean getmBeanCadastro() {
		return mBeanCadastro;
	}

	public void setmBeanCadastro(CadastroCursoMBean mBeanCadastro) {
		this.mBeanCadastro = mBeanCadastro;
	}
	
	
	
}
