package br.com.k19.controle;
import java.util.Date;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class CalendarBean {
	private Date dataDeNascimento;

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}
}
