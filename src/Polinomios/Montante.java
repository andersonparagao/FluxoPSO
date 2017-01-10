package Polinomios;


public class Montante extends PolinomioAbs{
	
	public Montante(int grau,double coef[]) {
		super(grau,coef);
		coeficientePrimeiraDerivada();
		coeficienteIntegral();
	}


	public double obterValorIntegral(double nivel) {
		double resul=0;
		for(int i = 0; i<=grau;i++){
			resul=resul+ coefIntegral[i]*(Math.pow(nivel, i+1));
		}
		return resul;
	}

}
