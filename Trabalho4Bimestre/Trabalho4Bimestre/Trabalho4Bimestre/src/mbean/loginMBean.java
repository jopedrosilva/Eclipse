package mbean;


import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextFactory;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.UsuarioDAO;
import dominios.Usuario;
import metodos.CriptografiaUtils;
import metodos.MetodosUteis;



@ManagedBean

public class loginMBean {
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Usuario usuario = new Usuario();
     
    public String autenticar() {
           
    	if (!validarLogin()) {
			return null;
		}
		try {
			UsuarioDAO dao = new UsuarioDAO();
			usuario = dao.findUsuarioByLoginSenha((usuario.getEmail()),
					CriptografiaUtils.criptografarMD5(usuario.getSenha()));
			
			if (!MetodosUteis.isEmpty(usuario)) {
				if (!usuario.isAtivo()) {
					MetodosUteis.adicionarMensagem("Esse usuario foi desabilitado ou simplismente não existe");
					return null;
				}
				
			} else {
				this.usuario = new Usuario();
				MetodosUteis.adicionarMensagem("Email/Senha incorretos.");
				return null;
			}
			MetodosUteis.getCurrentSession().setAttribute("usuarioLogado", usuario);
			return "/inicio1.xhtml";
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
           
           
    }
    
    public String logoff () {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest req = (HttpServletRequest) ec.getRequest ();
		HttpSession sessao = req.getSession(true);
		sessao.invalidate();
		return "/login.xhtml";
	}
   
    
    public Usuario getUsuario() {
 	   return usuario;
    }

	public boolean validarLogin() {

		boolean validou = true;
		
		if (usuario.getEmail() == null || usuario.getEmail().isEmpty() ) {
			MetodosUteis.adicionarMensagem("Digite seu Email!");
			validou = false;
		}
		if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
			MetodosUteis.adicionarMensagem("Digite sua senha!");
			validou = false;
		}
		return validou;
	}
	
}
