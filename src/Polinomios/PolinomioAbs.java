package Polinomios;

public abstract class PolinomioAbs {
	
protected	 double[] coef;
protected	 double[] coefDerivada;
protected	 double[] coefIntegral;
protected	 int grau;
	
	public double[] getCoef() {
		return coef;
	}


	public double[] getCoefDerivada() {
		return coefDerivada;
	}


	public void setCoefDerivada(double[] coefDerivada) {
		this.coefDerivada = coefDerivada;
	}


	public double[] getCoefIntegral() {
		return coefIntegral;
	}


	public void setCoefIntegral(double[] coefIntegral) {
		this.coefIntegral = coefIntegral;
	}


	public int getGrau() {
		return grau;
	}


	public void setGrau(int grau) {
		this.grau = grau;
	}


	public void setCoef(double[] coef) {
		this.coef = coef;
	}

	public PolinomioAbs(int grau,double coef[]){
		this.grau=grau;
		this.coef=new double[grau+1];
		for(int i=0;i<=grau;i++){
			this.coef[i]=coef[i];
		}
	}

	public void coeficientePrimeiraDerivada(){
		coefDerivada=new double[grau];
		for(int i=0;i<grau;i++){
			coefDerivada[i]=coef[i+1]*(i+1);
		}
	}
	public void coeficienteIntegral(){
		coefIntegral=new double[grau+1];
		for(int i=grau;i>=0;i--){
			if(i!=0)
				coefIntegral[i]=coef[i]/(i+1);
		else{
			coefIntegral[i]=coef[i];
			
		}
		}
	}
	
		


	public double obterValorPolinomio(double nivel){
		double resul=0;
		for(int i =0; i<=grau;i++){
			resul=resul+ coef[i]*(Math.pow(nivel, i));
		}
		return resul;
	}
	public double obterDerivadaPolinomio(double nivel){
		double resul=0;
		for(int i =0; i<grau;i++){
			resul=resul+ coefDerivada[i]*(Math.pow(nivel, i));
		}
		
		return resul;
	}

	

}
