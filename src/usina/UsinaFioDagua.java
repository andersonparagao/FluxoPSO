package usina;

public class UsinaFioDagua extends UsinaHidroeletrica{
 
	private double nivelMontante;
	public double getNivelMontante() {
		return nivelMontante;
	}
	public void setNivelMontante(double nivelMontante) {
		this.nivelMontante = nivelMontante;
	}
	public double getVolumeOperativo() {
		return volumeOperativo;
	}
	public void setVolumeOperativo(double volumeOperativo) {
		this.volumeOperativo = volumeOperativo;
	}
	private double volumeOperativo;
	
	public double volumeFinal(double x){
		return x;
	}
	
	public double vazaoDefluente(double Uafluente){
		return Uafluente;
	}
	@Override
	public double ProdutividadeMedia(double paramVolume, double paramVolumeMinimo) {
		double produtividade;
		produtividade=this.getK()*(nivelMontante - this.getCf_med() -(this.getK_pc()*(nivelMontante-this.getCf_med())));
		return produtividade;
	}
	
}
