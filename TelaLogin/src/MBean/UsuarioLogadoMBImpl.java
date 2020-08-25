package MBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
  
import org.apache.log4j.Logger;

import model.User;
  
/**
 * Controla o LOGIN e LOGOUT do Usuário
 * */
@ManagedBean(name = "usuarioLogadoMB")
@SessionScoped
public class UsuarioLogadoMBImpl extends BasicMBImpl {
  
    private static final long serialVersionUID = 1L;
  
    private static Logger logger = Logger.getLogger(UsuarioLogadoMBImpl.class);
  
    @ManagedProperty(value = "#{userBO}")
    private UserBOImpl userBO;
  
    private String email;
    private String login;
    private String senha;
  
    /**
     * Retorna User logado
     * */
    public User getUser() {
       return (User) SessionContext.getInstance().getUsuarioLogado();
    }
  
    public String doLogin() {
       try {
           logger.info("Tentando logar com usuário " + login);
           User user = userBO.isUsuarioReadyToLogin(login, senha);
  
           if (user == null) {
             addErrorMessage("Login ou Senha errado, tente novamente !");
             FacesContext.getCurrentInstance().validationFailed();
             return "";
           }
  
           Usuario usuario = (Usuario) getUserBO().findByNamedQuery(Usuario.FIND_BY_ID,
                 new NamedParams("id", user.getId())).get(0);
           logger.info("Login efetuado com sucesso");
           SessionContext.getInstance().setAttribute("usuarioLogado", usuario);
           return "/index.xhtml?faces-redirect=true";
       } catch (BOException e) {
           addErrorMessage(e.getMessage());
           FacesContext.getCurrentInstance().validationFailed();
           e.printStackTrace();
           return "";
       }
  
    }
  
    public String doLogout() {
       logger.info("Fazendo logout com usuário "
               + SessionContext.getInstance().getUsuarioLogado().getLogin());
       SessionContext.getInstance().encerrarSessao();
       addInfoMessage("Logout realizado com sucesso !");
       return "/security/form_login.xhtml?faces-redirect=true";
    }
  
    public void solicitarNovaSenha() {
       try {
           getUserBO().gerarNovaSenha(login, email);
           addInfoMessage("Nova Senha enviada para o email " + email);
       } catch (BOException e) {
           addErrorMessage(e.getMessage());
           FacesContext.getCurrentInstance().validationFailed();
       }
    }
  
    public UserBOImpl getUserBO() {
       return userBO;
    }
  
    public void setUserBO(UserBOImpl userBO) {
       this.userBO = userBO;
    }
  
    public String getLogin() {
       return login;
    }
  
    public void setLogin(String login) {
       this.login = login;
    }
  
    public String getSenha() {
       return senha;
    }
  
    public void setSenha(String senha) {
       this.senha = senha;
    }
  
    public String getEmail() {
       return email;
    }
  
    public void setEmail(String email) {
       this.email = email;
    }
  
}