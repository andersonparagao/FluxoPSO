package casaDeMaquina;

public class DescTurbina {
	private double alfaHlMaior=-1;
	private double alfaHlMenor;
	private double betaHlMaior=0;
	private double betaHlMenor;
	private int tipo;
	private double qmaq;
	public double getQmaq() {
		return qmaq;
	}

	public void setQmaq(double qmaq) {
		this.qmaq = qmaq;
	}


	private String nome;
	
	
	
	public DescTurbina(int tipo,String nome){
		this.nome=nome;
		this.tipo=tipo;
	}
	
	public DescTurbina() {
		
	}
	
	public double getAlfaHlMaior() {
		return alfaHlMaior;
	}


	public void setAlfaHlMaior(double alfaHlMaior) {
		this.alfaHlMaior = alfaHlMaior;
	}


	public double getAlfaHlMenor() {
		
		if(tipo==2){		//2-kaplan
			alfaHlMenor=0.2;
		}else{
			alfaHlMenor=0.5;
		}
		return alfaHlMenor;
	}


	public void setAlfaHlMenor(double alfaHlMenor) {
		this.alfaHlMenor = alfaHlMenor;
	}


	public double getBetaHlMaior() {
		return betaHlMaior;
	}


	public void setBetaHlMaior(double betaHlMaior) {
		this.betaHlMaior = betaHlMaior;
	}


	public double getBetaHlMenor() {
		if(tipo==2){
			betaHlMenor=1.2;
		}else{
			betaHlMenor=1.5;
		}
		return betaHlMenor;
	}


	public void setBetaHlMenor(double betaHlMenor) {
		this.betaHlMenor = betaHlMenor;
	}


	public int getTipo() {
		return tipo;
	}


	public void setTipo(int tipo) {
		this.tipo = tipo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}
	
public double Engolimento_max(double hl,double hef,double qef){
	double qmax;
		if(hl>=hef){
			qmax=qef*(Math.pow(hl/hef, getAlfaHlMaior()));	
		}
		else{
		qmax=qef*(Math.pow(hl/hef,getAlfaHlMenor()));
		}
		
		return qmax;
}

public double Potencia_max(double hl,double hef,double pef){
	double pmax;
		if(hl>=hef){
			pmax=pef*(Math.pow(hl/hef, getBetaHlMaior()));	
		}
		else{
		pmax=pef*(Math.pow(hl/hef,getBetaHlMenor()));
		}
		
		return pmax;
}

}
