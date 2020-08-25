package mbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

import dao.Database;
import dominios.Arquivo;
import dominios.Curso;
import metodos.Metodos;
import metodos.MetodosUteis;

@ManagedBean
@SessionScoped
public class CadastroCursoMBean {

private Curso curso;

	public CadastroCursoMBean() {
		curso = new Curso();	
	}
	
	public String iniciarEdicao(Curso c) {
		curso = c;	
		return "cadastroCurso.xhtml";
	}

	public String cadastrar() {
		boolean erro = false;
		
		if (curso.getNomeCurso() == null || curso.getNomeCurso().isEmpty()) {
			Metodos.addMensagem("Campo Nome Obrigatório!");
			erro = true;
		}
		
		if (curso.getCodigoCurso() == null || curso.getCodigoCurso().isEmpty()) {
			Metodos.addMensagem("Campo Código Obrigatório!");
			erro = true;
		}
		
		if (curso.getDescricao() == null || curso.getDescricao().isEmpty()) {
			Metodos.addMensagem("Campo Descrição Obrigatório!");
			erro = true;
		}
		
		if (Metodos.estaVazio(curso.getCargahoraria())) {
			Metodos.addMensagem("Campo Carga Horária Obrigatório!");
			erro = true;
		}
		

		if(erro) 
			return null;
		
		if(erro) 
			return null;
		
		if (!erro) {
			
			EntityManager em = 
					Database.getInstance().getEntityManager();
			
			try {
				em.getTransaction().begin();
				
				//Se o usuário anexou foto
				if (curso.getArquivoCurso() != null && 
						!MetodosUteis.estaVazia(
								curso.getArquivoCurso().getFileName())
						&& curso.getArquivoCurso().getSize() != -1){
				
					//Cria nova entidade arquivo
					Arquivo arq = new Arquivo();
					arq.setNome(curso.getArquivoCurso().getFileName());
					arq.setBytes(curso.getArquivoCurso().getContents());
					
					//Cadastrando a foto no banco
					em.persist(arq);
						
					curso.setIdFotoCurso(arq.getId());
				}
				
				if (curso.getIdCurso() == 0) {
					//Cadastro
					em.persist(curso);
					
					MetodosUteis.adicionarMensagem(
							"Curso cadastrado com sucesso!");
				} else {
					em.merge(curso); //Edição
					
					MetodosUteis.adicionarMensagem(
							"Curso editado com sucesso!");
				}
				
				//Confirmando transação
				em.getTransaction().commit();
				
				curso = new Curso();//resetando dados
			} catch (Exception e){
				e.printStackTrace();
				
				if (em.getTransaction().isActive())
					//Como ocorreu erro, 
					//a transação não será confirmada
					em.getTransaction().rollback();
			}
		}
		
		curso = new Curso();
		
		return null;
	}
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
}
