package mbean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import dao.Database;
import dao.UsuarioDAO;
import dominios.Usuario;

@ManagedBean
@ViewScoped	
public class ConsultaUsuarioMBean {

	private Usuario usuarioBusca;
	
	private boolean mostrarTodos;
	
	private List<Usuario> usuariosEncontrados;	
	
	public List<Usuario> getUsuariosEncontrados() {
		return usuariosEncontrados;
	}

	public void setUsuariosEncontrados(List<Usuario> usuariosEncontrados) {
		this.usuariosEncontrados = usuariosEncontrados;
	}

	@ManagedProperty(value="#{cadastroUsuarioMBean}")	
	private CadastroUsuarioMBean mBeanCadastro;
	
	public ConsultaUsuarioMBean () {
		usuarioBusca = new Usuario ();
	}
	
	public String buscar() {
		UsuarioDAO dao = new UsuarioDAO();
		
		String ordenarPor = "u.nome ASC";
		
		usuariosEncontrados = 
				dao.buscarUsuario(
						usuarioBusca.getEmail(), 
						usuarioBusca.getNomeUsuario(), 
						ordenarPor);
		
		return null;
	}
	
	public String remover(Usuario u) {	
		EntityManager gerenciador = 
				Database.getInstance().getEntityManager();
		UsuarioDAO dao = new UsuarioDAO();
		
		try {
			gerenciador.getTransaction().begin();
			
			if (u.isAtivo()) {
				dao.atualizarCampo(
						u.getClass(), u.getIdUsuario(), "ativo", false);
			}
			
			gerenciador.getTransaction().commit();
			
			//Forçando recarregamento do usuário pelo Hibernate
			dao.desanexarEntidade(u);
		} catch (Exception e) {
			e.printStackTrace();
			
			if (gerenciador.getTransaction().isActive()) {
				gerenciador.getTransaction().rollback();
			}
		}
		
		return buscar();
	}
	
	public CadastroUsuarioMBean getmBeanCadastro() {
		return mBeanCadastro;
	}

	public void setmBeanCadastro(CadastroUsuarioMBean mBeanCadastro) {
		this.mBeanCadastro = mBeanCadastro;
	}

	public boolean isMostrarTodos() {
		return mostrarTodos;
	}

	public void setMostrarTodos(boolean mostrarTodos) {
		this.mostrarTodos = mostrarTodos;
	}

	public Usuario getUsuarioBusca() {
		return usuarioBusca;
	}

	public void setUsuarioBusca(Usuario usuarioBusca) {
		this.usuarioBusca = usuarioBusca;
	}
	
}
