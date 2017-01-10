package usina;

public  class UsinaReservatorio extends UsinaHidroeletrica{
	public double ProdutividadeMedia(double paramVolume,double paramVolumeMinimo){
		double volume=paramVolume;
		double produtividadeMedia = 0;
//		if(volume>X){
//			volume=X;
//		}
		produtividadeMedia=this.getK()*(AlturaQuedaLiquidaMedia(volume,paramVolumeMinimo));
		
		return produtividadeMedia;
	}
	
	public double NivelMedioMontante(double X,double paramVolumeMinimo){
		double Hmedmon=0;
		if(X==paramVolumeMinimo){
			Hmedmon=this.getMontante().obterValorPolinomio(X);
		}else{
			double valorIntegral=this.getMontante().obterValorIntegral(X)- this.getMontante().obterValorIntegral(paramVolumeMinimo);
			Hmedmon=(1/(X - paramVolumeMinimo))*valorIntegral;
		}
		
		return Hmedmon;
		
		
	}
	
	
	public double PerdaCargaHidraulicaMedia(double X,double paramVolumeMinimo){
		double HP_med=0;
		
		HP_med=this.getK_pc()*(NivelMedioMontante(X,paramVolumeMinimo) - this.getCf_med());
		//System.out.println("HP_med -> " +HP_med);
		
		return HP_med;
	}
	
	public double AlturaQuedaLiquidaMedia(double X,double paramVolumeMinimo){
		double HL_med=0;
		HL_med=NivelMedioMontante(X,paramVolumeMinimo) - this.getCf_med() - PerdaCargaHidraulicaMedia(X,paramVolumeMinimo);
		
		return HL_med;
	}
	
//	private double volumeMinimoOperativo;
//	private double volumeMaximoOperativo;
//	public double getVolumeMinimoOperativo() {
//		return volumeMinimoOperativo;
//	}
//	public void setVolumeMinimoOperativo(double volumeMinimoOperativo) {
//		this.volumeMinimoOperativo = volumeMinimoOperativo;
//	}
//	public double getVolumeMaximoOperativo() {
//		return volumeMaximoOperativo;
//	}
//	public void setVolumeMaximoOperativo(double volumeMaximoOperativo) {
//		this.volumeMaximoOperativo = volumeMaximoOperativo;
//	}
//	public double getVolumeutil() {
//		return volumeutil;
//	}
//	public void setVolumeutil(double volumeutil) {
//		this.volumeutil = volumeutil;
//	}
//	private double volumeutil;

}
