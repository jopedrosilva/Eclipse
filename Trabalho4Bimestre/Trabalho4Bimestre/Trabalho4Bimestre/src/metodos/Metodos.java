package metodos;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Metodos {

	public static boolean estaVazia(String s) {														
		if (s == null || s.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}	
	
	public static boolean estaVazio(int i) {														
		if (i == 0) {
			return true;
		} else {
			return false;
		}
	}	
	
	public static boolean Vazio(long l) {														
		if (l == 0) {
			return true;
		} else {
			return false;
		}
	}	
	
	public static void addMensagem(String msg) {	
		FacesMessage mensagem = new FacesMessage(msg);
		FacesContext.getCurrentInstance().addMessage(
				null, mensagem);
	}
	
}
