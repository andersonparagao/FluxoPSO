package rede;

public class NoHidroenergetico {
	
	private int Usina;
	private int IntervaloHorizontePlanejamento;
	private double	volumeInicial;
	private double	volumeFinal;
	private double	volumeMedio;
	private double	vazaoDefluente;
	private double	vazaoAfluenteIncremental;
	private double	vazaoAfluenteNatural;
	private double	vazaoAfluenteControlavel;
	private double	vazaoAfluente;
	private double	vazaoTurbinada;
	private double	vazaoVertida;
	private double	alturaQuedaLiquida;
	private double	engolimentoMaximo;
	private double	geracaoHidraulica;
	private double	geracaoHidraulicaMaxima;
	private double	geracaoHIdraulicaMaximaContinua;
	private double	produtividadeMediaIndividual;
	private double	produtividadeMediaAcumulada;
	private double	nivelMontante;
	private double	nivelJusante;
	private double	nivelPerdaCargaHidraulica;
	private double	areaEspelhoDeAgua;
	private double	limiteMinimoVazaoDefluente;
	private double	limiteMaximoVazaoDefluente;
	private double	limiteMinimoVolume;
	private double	limiteMaximoVoume;
	private double	valorDaAgua;
	private double	derivadaArcoVolume;
	private double	derivadaArcoVazaoDefluente;
	private double	direcaoCaminhadaArcoVazaoDefluente;
	private double	direcaoCaminhadaArcoVolume;
	private double	limiteTemporarioVolume;
	private double	limiteTemporarioVazaoDefluente;
	private double	limiteMinimoGeracaoHidraulica;
	private double	limiteMaximoGeracaoHidraulica;
	private double	limiteMinimoVazaoTurbinada;
	private double	limiteMaximoVazaoTurbinada;
	private double	volumeEvaporado;	
	
	
	public int getUsina() {
		return Usina;
	}
	public void setUsina(int usina) {
		Usina = usina;
	}
	public int getIntervaloHorizontePlanejamento() {
		return IntervaloHorizontePlanejamento;
	}
	public void setIntervaloHorizontePlanejamento(int intervaloHorizontePlanejamento) {
		IntervaloHorizontePlanejamento = intervaloHorizontePlanejamento;
	}
	public double getVolumeInicial() {
		return volumeInicial;
	}
	public void setVolumeInicial(double volumeInicial) {
		this.volumeInicial = volumeInicial;
	}
	public double getVolumeFinal() {
		return volumeFinal;
	}
	public void setVolumeFinal(double volumeFinal) {
		this.volumeFinal = volumeFinal;
	}
	public double getVolumeMedio() {
		return volumeMedio;
	}
	public void setVolumeMedio(double volumeMedio) {
		this.volumeMedio = volumeMedio;
	}
	public double getVazaoDefluente() {
		return vazaoDefluente;
	}
	public void setVazaoDefluente(double vazaoDefluente) {
		this.vazaoDefluente = vazaoDefluente;
	}
	public double getVazaoAfluenteIncremental() {
		return vazaoAfluenteIncremental;
	}
	public void setVazaoAfluenteIncremental(double vazaoAfluenteIncremental) {
		this.vazaoAfluenteIncremental = vazaoAfluenteIncremental;
	}
	public double getVazaoAfluenteNatural() {
		return vazaoAfluenteNatural;
	}
	public void setVazaoAfluenteNatural(double vazaoAfluenteNatural) {
		this.vazaoAfluenteNatural = vazaoAfluenteNatural;
	}
	public double getVazaoAfluenteControlavel() {
		return vazaoAfluenteControlavel;
	}
	public void setVazaoAfluenteControlavel(double vazaoAfluenteControlavel) {
		this.vazaoAfluenteControlavel = vazaoAfluenteControlavel;
	}
	public double getVazaoAfluente() {
		return vazaoAfluente;
	}
	public void setVazaoAfluente(double vazaoAfluente) {
		this.vazaoAfluente = vazaoAfluente;
	}
	public double getVazaoTurbinada() {
		return vazaoTurbinada;
	}
	public void setVazaoTurbinada(double vazaoTurbinada) {
		this.vazaoTurbinada = vazaoTurbinada;
	}
	public double getVazaoVertida() {
		return vazaoVertida;
	}
	public void setVazaoVertida(double vazaoVertida) {
		this.vazaoVertida = vazaoVertida;
	}
	public double getAlturaQuedaLiquida() {
		return alturaQuedaLiquida;
	}
	public void setAlturaQuedaLiquida(double alturaQuedaLiquida) {
		this.alturaQuedaLiquida = alturaQuedaLiquida;
	}
	public double getEngolimentoMaximo() {
		return engolimentoMaximo;
	}
	public void setEngolimentoMaximo(double engolimentoMaximo) {
		this.engolimentoMaximo = engolimentoMaximo;
	}
	public double getGeracaoHidraulica() {
		return geracaoHidraulica;
	}
	public void setGeracaoHidraulica(double geracaoHidraulica) {
		this.geracaoHidraulica = geracaoHidraulica;
	}
	public double getGeracaoHidraulicaMaxima() {
		return geracaoHidraulicaMaxima;
	}
	public void setGeracaoHidraulicaMaxima(double geracaoHidraulicaMaxima) {
		this.geracaoHidraulicaMaxima = geracaoHidraulicaMaxima;
	}
	public double getGeracaoHIdraulicaMaximaContinua() {
		return geracaoHIdraulicaMaximaContinua;
	}
	public void setGeracaoHIdraulicaMaximaContinua(double geracaoHIdraulicaMaximaContinua) {
		this.geracaoHIdraulicaMaximaContinua = geracaoHIdraulicaMaximaContinua;
	}
	public double getProdutividadeMediaIndividual() {
		return produtividadeMediaIndividual;
	}
	public void setProdutividadeMediaIndividual(double produtividadeMediaIndividual) {
		this.produtividadeMediaIndividual = produtividadeMediaIndividual;
	}
	public double getProdutividadeMediaAcumulada() {
		return produtividadeMediaAcumulada;
	}
	public void setProdutividadeMediaAcumulada(double produtividadeMediaAcumulada) {
		this.produtividadeMediaAcumulada = produtividadeMediaAcumulada;
	}
	public double getNivelMontante() {
		return nivelMontante;
	}
	public void setNivelMontante(double nivelMontante) {
		this.nivelMontante = nivelMontante;
	}
	public double getNivelJusante() {
		return nivelJusante;
	}
	public void setNivelJusante(double nivelJusante) {
		this.nivelJusante = nivelJusante;
	}
	public double getNivelPerdaCargaHidraulica() {
		return nivelPerdaCargaHidraulica;
	}
	public void setNivelPerdaCargaHidraulica(double nivelPerdaCargaHidraulica) {
		this.nivelPerdaCargaHidraulica = nivelPerdaCargaHidraulica;
	}
	public double getAreaEspelhoDeAgua() {
		return areaEspelhoDeAgua;
	}
	public void setAreaEspelhoDeAgua(double areaEspelhoDeAgua) {
		this.areaEspelhoDeAgua = areaEspelhoDeAgua;
	}
	public double getLimiteMinimoVazaoDefluente() {
		return limiteMinimoVazaoDefluente;
	}
	public void setLimiteMinimoVazaoDefluente(double limiteMinimoVazaoDefluente) {
		this.limiteMinimoVazaoDefluente = limiteMinimoVazaoDefluente;
	}
	public double getLimiteMaximoVazaoDefluente() {
		return limiteMaximoVazaoDefluente;
	}
	public void setLimiteMaximoVazaoDefluente(double limiteMaximoVazaoDefluente) {
		this.limiteMaximoVazaoDefluente = limiteMaximoVazaoDefluente;
	}
	public double getLimiteMinimoVolume() {
		return limiteMinimoVolume;
	}
	public void setLimiteMinimoVolume(double limiteMinimoVolume) {
		this.limiteMinimoVolume = limiteMinimoVolume;
	}
	public double getLimiteMaximoVolume() {
		return limiteMaximoVoume;
	}
	public void setLimiteMaximoVoume(double limiteMaximoVoume) {
		this.limiteMaximoVoume = limiteMaximoVoume;
	}
	public double getValorDaAgua() {
		return valorDaAgua;
	}
	public void setValorDaAgua(double valorDaAgua) {
		this.valorDaAgua = valorDaAgua;
	}
	public double getDerivadaArcoVolume() {
		return derivadaArcoVolume;
	}
	public void setDerivadaArcoVolume(double derivadaArcoVolume) {
		this.derivadaArcoVolume = derivadaArcoVolume;
	}
	public double getDerivadaArcoVazaoDefluente() {
		return derivadaArcoVazaoDefluente;
	}
	public void setDerivadaArcoVazaoDefluente(double derivadaArcoVazaoDefluente) {
		this.derivadaArcoVazaoDefluente = derivadaArcoVazaoDefluente;
	}
	public double getDirecaoCaminhadaArcoVazaoDefluente() {
		return direcaoCaminhadaArcoVazaoDefluente;
	}
	public void setDirecaoCaminhadaArcoVazaoDefluente(double direcaoCaminhadaArcoVazaoDefluente) {
		this.direcaoCaminhadaArcoVazaoDefluente = direcaoCaminhadaArcoVazaoDefluente;
	}
	public double getDirecaoCaminhadaArcoVolume() {
		return direcaoCaminhadaArcoVolume;
	}
	public void setDirecaoCaminhadaArcoVolume(double direcaoCaminhadaArcoVolume) {
		this.direcaoCaminhadaArcoVolume = direcaoCaminhadaArcoVolume;
	}
	public double getLimiteTemporarioVolume() {
		return limiteTemporarioVolume;
	}
	public void setLimiteTemporarioVolume(double limiteTemporarioVolume) {
		this.limiteTemporarioVolume = limiteTemporarioVolume;
	}
	public double getLimiteTemporarioVazaoDefluente() {
		return limiteTemporarioVazaoDefluente;
	}
	public void setLimiteTemporarioVazaoDefluente(double limiteTemporarioVazaoDefluente) {
		this.limiteTemporarioVazaoDefluente = limiteTemporarioVazaoDefluente;
	}
	public double getLimiteMinimoGeracaoHidraulica() {
		return limiteMinimoGeracaoHidraulica;
	}
	public void setLimiteMinimoGeracaoHidraulica(double limiteMinimoGeracaoHidraulica) {
		this.limiteMinimoGeracaoHidraulica = limiteMinimoGeracaoHidraulica;
	}
	public double getLimiteMaximoGeracaoHidraulica() {
		return limiteMaximoGeracaoHidraulica;
	}
	public void setLimiteMaximoGeracaoHidraulica(double limiteMaximoGeracaoHidraulica) {
		this.limiteMaximoGeracaoHidraulica = limiteMaximoGeracaoHidraulica;
	}
	public double getLimiteMinimoVazaoTurbinada() {
		return limiteMinimoVazaoTurbinada;
	}
	public void setLimiteMinimoVazaoTurbinada(double limiteMinimoVazaoTurbinada) {
		this.limiteMinimoVazaoTurbinada = limiteMinimoVazaoTurbinada;
	}
	public double getLimiteMaximoVazaoTurbinada() {
		return limiteMaximoVazaoTurbinada;
	}
	public void setLimiteMaximoVazaoTurbinada(double limiteMaximoVazaoTurbinada) {
		this.limiteMaximoVazaoTurbinada = limiteMaximoVazaoTurbinada;
	}
	public double getVolumeEvaporado() {
		return volumeEvaporado;
	}
	public void setVolumeEvaporado(double volumeEvaporado) {
		this.volumeEvaporado = volumeEvaporado;
	}
	
	
	
	
	
	

}
