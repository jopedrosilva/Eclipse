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
			MetodosUteis.adicionarMensagem("Campo Matricula Obrigat�rio!");
			erro = true;
		}
		
		if (usuario.getNomeUsuario() == null || usuario.getNomeUsuario().isEmpty()) {
			MetodosUteis.adicionarMensagem("Campo Nome Obrigat�rio!");
			erro = true;
		}
		
		if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			MetodosUteis.adicionarMensagem("Campo Email Obrigat�rio!");
			erro = true;
		}
		
		if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
			MetodosUteis.adicionarMensagem("Campo Senha Obrigat�rio!");
			erro = true;
		}
		
		if (usuario.getTipo() == null || usuario.getTipo().isEmpty()) {
			MetodosUteis.adicionarMensagem("Campo Tipo Obrigat�rio!");
			erro = true;
		}
		
		if (usuario.getEmail().contains("@") || usuario.getEmail().contains(".")) {
			erro = false;
		} else {
			MetodosUteis.adicionarMensagem("Preencha o campo Email com um email v�lido! O email deve conter @ e .");
			erro = true;
		}
			
		
		if(erro) 
			return null;
			
		if (!erro) {
			
			EntityManager em = 
					Database.getInstance().getEntityManager();
			
			try {
				em.getTransaction().begin();
				
				//Se o usu�rio anexou foto
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
							"Usu�rio cadastrado com sucesso!");
				} else {
					em.merge(usuario); //Edi��o
					
					MetodosUteis.adicionarMensagem(
							"Usu�rio editado com sucesso!");
				}
				
				//Confirmando transa��o
				em.getTransaction().commit();
				
				usuario = new Usuario();//resetando dados
			} catch (Exception e){
				e.printStackTrace();
				
				if (em.getTransaction().isActive())
					//Como ocorreu erro, 
					//a transa��o n�o ser� confirmada
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
