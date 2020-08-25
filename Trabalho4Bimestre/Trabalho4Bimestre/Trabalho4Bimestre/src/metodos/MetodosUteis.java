package metodos;

import java.util.Collection;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dominios.Usuario;

public class MetodosUteis {

	public static boolean estaVazia(String s) {
		return s == null || s.isEmpty();
	}
	
	public static boolean Vazio(long l) {														
		if (l == 0) {
			return true;
		} else {
			return false;
		}
	}	
	
	public static void adicionarMensagem(String msg) {
		FacesMessage mensagem = new FacesMessage(msg);
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
	}
	
	/**
	 * Valida se um objeto é vazio. O seu funcionamento vai depender do tipo de objeto
	 * passado como parâmetro. Se o objeto for nulo, é vazio. Se for uma String, verifica
	 * se não é string vazia ou não é formada apenas por espaços. Se for uma coleção,
	 * verifica se a coleção está vazia, etc.
	 *
	 */
	public static final boolean isEmpty(Object o) {
		if (o == null)
			return true;
		if (o instanceof String)
			return isEmpty( (String) o);
		if (o instanceof Number) {
			Number i = (Number) o;
			return (i.intValue() == 0);
		}
		if (o instanceof Object[])
			return ((Object[]) o).length == 0;
		if (o instanceof int[])
			return ((int[]) o).length == 0;
		if (o instanceof Collection<?>)
			return ((Collection<?>) o).size() == 0;
		if (o instanceof Map<?, ?>)
			return ((Map<?, ?>) o).size() == 0;
		return false;
	}
	
	public static HttpSession getCurrentSession() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return req.getSession(true);
	}

	public static ExternalContext getExternalContext() {
		
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public static Usuario getUsuarioLogado() {
		return (Usuario) getCurrentSession().getAttribute("usuarioLogado");
	}
}
