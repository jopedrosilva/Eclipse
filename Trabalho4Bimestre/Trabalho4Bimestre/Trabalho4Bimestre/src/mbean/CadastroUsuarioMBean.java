package mbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

import dao.Database;
import dominios.Arquivo;
import dominios.Usuario;
import metodos.MetodosUteis;
import metodos.CriptografiaUtils;

@ManagedBean
@SessionScoped
public class CadastroUsuarioMBean {

private Usuario usuario;
	
	public CadastroUsuarioMBean() {
		usuario = new Usuario();
	}
	
	public String iniciarEdicaoUsuario(Usuario u) {
		usuario = u;
		return "cadastroUsuario.xhtml";
	}
	
	public List<String> getTipos(){
		List<String> tipos = new ArrayList<String>();
		tipos.add("Aluno");
		tipos.add("Professor");
		return tipos;
	}
	
	public String cadastrar() {
		boolean erro = false;
		
		if (MetodosUteis.Vazio(usuario.getMatricula())) {
			MetodosUteis.adicionarMensagem("Campo Matricula Obrigatório!");
			erro = true;
		}
		
		if (usuario.getNomeUsuario() == null || usuario.getNomeUsuario().isEmpty()) {
			MetodosUteis.adicionarMensagem("Campo Nome Obrigatório!");
			erro = true;
		}
		
		if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			MetodosUteis.adicionarMensagem("Campo Email Obrigatório!");
			erro = true;
		}
		
		if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
			MetodosUteis.adicionarMensagem("Campo Senha Obrigatório!");
			erro = true;
		}
		
		if (usuario.getTipo() == null || usuario.getTipo().isEmpty()) {
			MetodosUteis.adicionarMensagem("Campo Tipo Obrigatório!");
			erro = true;
		}
		
		if (usuario.getEmail().contains("@") || usuario.getEmail().contains(".")) {
			erro = false;
		} else {
			MetodosUteis.adicionarMensagem("Preencha o campo Email com um email válido! O email deve conter @ e .");
			erro = true;
		}
			
		
		if(erro) 
			return null;
			
		if (!erro) {
			
			EntityManager em = 
					Database.getInstance().getEntityManager();
			
			try {
				em.getTransaction().begin();
				
				//Se o usuário anexou foto
				if (usuario.getArquivo() != null && 
						!MetodosUteis.estaVazia(
								usuario.getArquivo().getFileName())
						&& usuario.getArquivo().getSize() != -1){
				
					//Cria nova entidade arquivo
					Arquivo arq = new Arquivo();
					arq.setNome(usuario.getArquivo().getFileName());
					arq.setBytes(usuario.getArquivo().getContents());
					
					//Cadastrando a foto no banco
					em.persist(arq);
						
					usuario.setIdFoto(arq.getId());
				}
				
				String senhaCriptografada = 
						CriptografiaUtils.criptografarMD5(
								usuario.getSenha());
				usuario.setSenha(senhaCriptografada);
				
				if (usuario.getIdUsuario() == 0) {
					//Cadastro
					em.persist(usuario);
					
					MetodosUteis.adicionarMensagem(
							"Usuário cadastrado com sucesso!");
				} else {
					em.merge(usuario); //Edição
					
					MetodosUteis.adicionarMensagem(
							"Usuário editado com sucesso!");
				}
				
				//Confirmando transação
				em.getTransaction().commit();
				
				usuario = new Usuario();//resetando dados
			} catch (Exception e){
				e.printStackTrace();
				
				if (em.getTransaction().isActive())
					//Como ocorreu erro, 
					//a transação não será confirmada
					em.getTransaction().rollback();
			}
		}
		
		usuario = new Usuario();
		
		return null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
