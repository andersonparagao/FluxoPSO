package horizonte;

import java.util.ArrayList;

public class HorizontePlanejamento {
	
	private ArrayList<IntervaloDeHorizonte> Intervalos=new ArrayList<IntervaloDeHorizonte>();
	private int numeroIntervalos;
	public ArrayList<IntervaloDeHorizonte> getIntervalos() {
		return Intervalos;
	}
	public void setIntervalos(ArrayList<IntervaloDeHorizonte> intervalos) {
		Intervalos = intervalos;
	}
	public int getNumeroIntervalos() {
		return numeroIntervalos;
	}
	public void setNumeroIntervalos(int numeroIntervalos) {
		this.numeroIntervalos = numeroIntervalos;
	}
	public double getTaxaJurosNominal() {
		return taxaJurosNominal;
	}
	public void setTaxaJurosNominal(double taxaJurosNominal) {
		this.taxaJurosNominal = taxaJurosNominal;
	}
	public double getPlanejamentoCustoGeracaoHidrotermica() {
		return PlanejamentoCustoGeracaoHidrotermica;
	}
	public void setPlanejamentoCustoGeracaoHidrotermica(double planejamentoCustoGeracaoHidrotermica) {
		PlanejamentoCustoGeracaoHidrotermica = planejamentoCustoGeracaoHidrotermica;
	}
	public int getMesInical() {
		return mesInical;
	}
	public void setMesInical(int mesInical) {
		this.mesInical = mesInical;
	}
	public int getDiscretizacao() {
		return discretizacao;
	}
	public void setDiscretizacao(int discretizacao) {
		this.discretizacao = discretizacao;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	private double taxaJurosNominal;
	private double PlanejamentoCustoGeracaoHidrotermica;
	private int mesInical;
	private int discretizacao;
	private int tipo;
}
