package br.com.k19.controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PollBean {

	private int placarTimeA ;
	private int placarTimeB ;

	public void update() {
		if (Math.random() > 0.5) {
			this.placarTimeA++;
		 } else {
			 this.placarTimeB++;
		 }
	 }

	public int getPlacarTimeA() {
		return placarTimeA;
	}

	public void setPlacarTimeA(int placarTimeA) {
		this.placarTimeA = placarTimeA;
	}

	public int getPlacarTimeB() {
		return placarTimeB;
	}

	public void setPlacarTimeB(int placarTimeB) {
		this.placarTimeB = placarTimeB;
	}
	
}