package mbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

import dao.Database;
import dominios.Arquivo;
import dominios.Turma;
import metodos.Metodos;
import metodos.MetodosUteis;;

@ManagedBean
@SessionScoped
public class CadastroTurmaMBean {

private Turma turma;
	
	public CadastroTurmaMBean() {
		turma = new Turma();	
	}
	
	public String iniciarEdicao(Turma t) {
		turma = t;	
		return "cadastroTurma.xhtml";
	}

	public String cadastrar() {
		boolean erro = false;
		
		if (turma.getNomeTurma() == null || turma.getNomeTurma().isEmpty()) {
			MetodosUteis.adicionarMensagem("Campo Nome Obrigatório!");
			erro = true;
		}
		
		if (turma.getCodigoTurma() == null || turma.getCodigoTurma().isEmpty()) {
			MetodosUteis.adicionarMensagem("Campo Código Obrigatório!");
			erro = true;
		}
		
		if (Metodos.estaVazio(turma.getQtdvagas())) {
			MetodosUteis.adicionarMensagem("Campo Quantidade de Vagas Obrigatório!");
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
				if (turma.getArquivoTurma() != null && 
						!MetodosUteis.estaVazia(
								turma.getArquivoTurma().getFileName())
						&& turma.getArquivoTurma().getSize() != -1){
				
					//Cria nova entidade arquivo
					Arquivo arq = new Arquivo();
					arq.setNome(turma.getArquivoTurma().getFileName());
					arq.setBytes(turma.getArquivoTurma().getContents());
					
					//Cadastrando a foto no banco
					em.persist(arq);
						
					turma.setIdFotoTurma(arq.getId());
				}
				
				if (turma.getIdTurma() == 0) {
					//Cadastro
					em.persist(turma);
					
					MetodosUteis.adicionarMensagem(
							"Turma cadastrada com sucesso!");
				} else {
					em.merge(turma); //Edição
					
					MetodosUteis.adicionarMensagem(
							"Turma editada com sucesso!");
				}
				
				//Confirmando transação
				em.getTransaction().commit();
				
				turma = new Turma();//resetando dados
			} catch (Exception e){
				e.printStackTrace();
				
				if (em.getTransaction().isActive())
					//Como ocorreu erro, 
					//a transação não será confirmada
					em.getTransaction().rollback();
			}
		}
		
		turma = new Turma();
		
		return null;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
}
