package mbean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import dao.UsuarioDAO;
import dominios.Curso;

@FacesConverter("cursoConverter")
public class CursoConverter implements Converter {
	
	public String getAsString(FacesContext context, UIComponent component, Object value)
			throws ConverterException {
		if (value != null) {
			Curso c = (Curso) value;
			return String.valueOf(c.getIdCurso());	
		} else {
			return null;
		}
	}
	
	public Object getAsObject(FacesContext context, UIComponent component, String value)
			throws ConverterException {
		if (value != null && value.trim().length() > 0) {
			try {
				int idUsuario = Integer.parseInt(value);
				return new UsuarioDAO().encontrarPeloID(idUsuario, Curso.class);
			} catch(NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Erro ao recuperar o usuário.", null));
			}
		} else {
			return null;
		}
	}
}
