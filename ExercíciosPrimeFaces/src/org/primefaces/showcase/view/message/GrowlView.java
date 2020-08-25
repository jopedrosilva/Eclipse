package org.primefaces.showcase.view.message;
 
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
@ManagedBean
public class GrowlView {
     
    private String message;
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
     
    public void saveMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
         
        context.addMessage(null, new FacesMessage("Sucesso!",  "Sua Mensagem: " + message) );
        context.addMessage(null, new FacesMessage("Mensagem Enviada!", "Ficamos Feliz por usar nossos Serviços!"));
    }
}