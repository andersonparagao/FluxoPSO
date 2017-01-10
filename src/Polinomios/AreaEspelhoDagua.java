package Polinomios;

public class AreaEspelhoDagua extends PolinomioAbs{
	
	public AreaEspelhoDagua(int grau,double coef[]) {
		super(grau,coef);
		coeficientePrimeiraDerivada();
		coeficienteIntegral();
	}

}

