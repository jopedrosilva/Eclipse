package mbean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import dao.TurmaDAO;
import dao.Database;
import dominios.Turma;

@ManagedBean
@ViewScoped
public class ConsultaTurmaMBean {

	private Turma turmaBusca;
	
	private boolean mostrarTodas;
	
	private List<Turma> turmasEncontradas;	
	
	@ManagedProperty(value="#{cadastroTurmaMBean}")	
	private CadastroTurmaMBean mBeanCadastro;
	
	public ConsultaTurmaMBean () {
		turmaBusca = new Turma ();
	}

	public String buscar() {
		TurmaDAO dao = new TurmaDAO();
		
		String ordenarPor = "t.nomeTurma ASC";
		
		turmasEncontradas = 
				dao.buscarTurma(
						turmaBusca.getCodigoTurma(), 
						turmaBusca.getNomeTurma(), 
						ordenarPor);
		
		return null;
	}
	
	public String remover(Turma t) {	
		EntityManager gerenciador = 
				Database.getInstance().getEntityManager();
		TurmaDAO dao = new TurmaDAO();
		
		try {
			gerenciador.getTransaction().begin();
			
			if (t.isAtivo()) {
				dao.atualizarCampo(
						t.getClass(), t.getIdTurma(), "ativo", false);
			}
			
			gerenciador.getTransaction().commit();
			
			//Forçando recarregamento do usuário pelo Hibernate
			dao.desanexarEntidade(t);
		} catch (Exception e) {
			e.printStackTrace();
			
			if (gerenciador.getTransaction().isActive()) {
				gerenciador.getTransaction().rollback();
			}
		}
		
		return buscar();
	}

	public Turma getTurmaBusca() {
		return turmaBusca;
	}

	public void setTurmaBusca(Turma turmaBusca) {
		this.turmaBusca = turmaBusca;
	}

	public boolean isMostrarTodas() {
		return mostrarTodas;
	}

	public void setMostrarTodas(boolean mostrarTodas) {
		this.mostrarTodas = mostrarTodas;
	}

	public CadastroTurmaMBean getmBeanCadastro() {
		return mBeanCadastro;
	}

	public void setmBeanCadastro(CadastroTurmaMBean mBeanCadastro) {
		this.mBeanCadastro = mBeanCadastro;
	}

	public List<Turma> getTurmasEncontradas() {
		return turmasEncontradas;
	}

	public void setTurmasEncontradas(List<Turma> turmasEncontradas) {
		this.turmasEncontradas = turmasEncontradas;
	}
	
	
	
}
