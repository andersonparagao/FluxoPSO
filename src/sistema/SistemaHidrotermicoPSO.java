package sistema;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import horizonte.HorizontePlanejamento;
import horizonte.IntervaloDeHorizonte;
import rede.NoHidroenergetico;
import usina.UsinaFioDagua;
import usina.UsinaHidroeletrica;

public class SistemaHidrotermicoPSO {
	private ArrayList<UsinaHidroeletrica> Usinas=new ArrayList<UsinaHidroeletrica>();
	private double tempo = 2628000;
	
	public ArrayList<UsinaHidroeletrica> getUsinas() {
		return Usinas;
	}

	public void setUsinas(ArrayList<UsinaHidroeletrica> usinas) {
		Usinas = usinas;
	}

	public void addUsina(UsinaHidroeletrica u){
		Usinas.add(u);
	}
	
	public void IniciarVolumes(double volumes[],NoHidroenergetico[] nosIntervaloAtual){
		for(int i=0;i<Usinas.size();i++){
			nosIntervaloAtual[i].setVolumeInicial(volumes[i]); // verificar se o intervalo e o 0 na classe de simula��o,ou seja sempre mandar os volumes certo para essa funcao
		}
	}
	
	public void AfluenciasNat(double Afluencias[],NoHidroenergetico[] nosIntervalorAtual){
		for(int i=0;i<Usinas.size();i++){
			nosIntervalorAtual[i].setVazaoAfluenteNatural(Afluencias[i]);
			
		}
	}	
	public void CalcularAfluenciaIncremental(NoHidroenergetico[] nosIntervaloAtual){
		
		
		for(int i=0;i<Usinas.size();i++){
		int tam=Usinas.get(i).getUsinaImediatamenteAMontante().size(); //quantas usinas a montante a usina em questao possui
		int[] indices=new int[tam];
		for(int k=0;k<tam;k++){
			//coloca os codigos das usinas a montante no vetor
		indices[k]=Usinas.get(i).getUsinaImediatamenteAMontante().get(k).getCodigo();	
		}
		double afluenciaNatural= nosIntervaloAtual[i].getVazaoAfluenteNatural();
		double somaAfluenciaDasUsinasAMontante=0;
		for(int j=0;j<tam;j++){
			somaAfluenciaDasUsinasAMontante=  somaAfluenciaDasUsinasAMontante + nosIntervaloAtual[indices[j]].getVazaoAfluenteNatural();
		}
		double vazaoIncremental=afluenciaNatural- somaAfluenciaDasUsinasAMontante;
		nosIntervaloAtual[i].setVazaoAfluenteIncremental(vazaoIncremental);
		}
		
	}
	
	
	
	public double EnergiaArmazenadaNoSistema(NoHidroenergetico[] nosIntervaloAtual){
		double EAS=0;
		double produtividadeMediaAcumulada;
		double produtividadeMediaIndividual;
		double somatorio=0;
		for(int i=0;i<Usinas.size();i++){
			if(!((Usinas.get(i)) instanceof UsinaFioDagua)){
				//somente usinas a reservatorio que tem energia armazenada
			produtividadeMediaAcumulada=0;
			produtividadeMediaIndividual=0;
			double volume;
			if(nosIntervaloAtual[i].getVolumeMedio()==0){
				volume=nosIntervaloAtual[i].getVolumeInicial();
			}
			else{
				volume=nosIntervaloAtual[i].getVolumeMedio();
			}
			produtividadeMediaIndividual=Usinas.get(i).ProdutividadeMedia(volume,nosIntervaloAtual[i].getLimiteMinimoVolume());
			produtividadeMediaAcumulada=produtividadeMediaIndividual;
			int tam=Usinas.get(i).getUsinaAJusante().size();
			int[] indices=new int[tam];
			for(int k=0;k<tam;k++){
				indices[k]=Usinas.get(i).getUsinaAJusante().get(k).getCodigo();	
			}
			for(int j=0;j<tam;j++){
					if(nosIntervaloAtual[i].getVolumeMedio()==0){
						produtividadeMediaAcumulada=produtividadeMediaAcumulada + Usinas.get(indices[j])
						.ProdutividadeMedia(nosIntervaloAtual[indices[j]].getVolumeInicial(), 
						nosIntervaloAtual[indices[j]].getLimiteMinimoVolume());
					}
					else{
						produtividadeMediaAcumulada=produtividadeMediaAcumulada + Usinas.get(indices[j])
						.ProdutividadeMedia(nosIntervaloAtual[indices[j]].getVolumeMedio(), 
						nosIntervaloAtual[indices[j]].getLimiteMinimoVolume());
					
					}
			}
			nosIntervaloAtual[i].setProdutividadeMediaAcumulada(produtividadeMediaAcumulada);
			nosIntervaloAtual[i].setProdutividadeMediaIndividual(produtividadeMediaIndividual);
			produtividadeMediaAcumulada=produtividadeMediaAcumulada*(volume - nosIntervaloAtual[i].getLimiteMinimoVolume());
			somatorio= somatorio + produtividadeMediaAcumulada;
		}
		}
		EAS=somatorio*(1000000/tempo);
		return EAS;
	}
	public double EnergiaArmazenadaMaximaNoSistema(NoHidroenergetico[] nosIntervaloAtual){
		
		double EAMS=0;
		double produtividadeMediaAcumulada;
		double produtividadeMediaIndividual;
		double somatorio=0;
		for(int i=0;i<Usinas.size();i++){
			if(!((Usinas.get(i)) instanceof UsinaFioDagua)){
				//somente usinas a reservatorio que tem energia armazenada
			produtividadeMediaAcumulada=0;
			produtividadeMediaIndividual=0;
			produtividadeMediaIndividual=Usinas.get(i).ProdutividadeMedia(nosIntervaloAtual[i].getLimiteMaximoVolume(),nosIntervaloAtual[i].getLimiteMinimoVolume());
			produtividadeMediaAcumulada=produtividadeMediaIndividual;
			int tam=Usinas.get(i).getUsinaAJusante().size();
			int[] indices=new int[tam];
			for(int k=0;k<tam;k++){
					indices[k]=Usinas.get(i).getUsinaAJusante().get(k).getCodigo();	
			}
			for(int j=0;j<Usinas.get(i).getUsinaAJusante().size();j++){
					produtividadeMediaAcumulada=produtividadeMediaAcumulada + Usinas.get(indices[j])
					.ProdutividadeMedia(nosIntervaloAtual[indices[j]].getLimiteMaximoVolume(), 
					nosIntervaloAtual[indices[j]].getLimiteMinimoVolume());
				
			}
			nosIntervaloAtual[i].setProdutividadeMediaAcumulada(produtividadeMediaAcumulada);
			nosIntervaloAtual[i].setProdutividadeMediaIndividual(produtividadeMediaIndividual);
			produtividadeMediaAcumulada=produtividadeMediaAcumulada*(nosIntervaloAtual[i].getLimiteMaximoVolume() - nosIntervaloAtual[i].getLimiteMinimoVolume());
			somatorio= somatorio + produtividadeMediaAcumulada;
		}
			}
		EAMS=somatorio*(1000000/tempo);
		return EAMS;
	}
	
	public double ObterLambda(double EAS,double EASmax){
		double lambda=EAS/EASmax;
		return lambda;
	}
	
	public void volumefinal(double lambda, NoHidroenergetico[] NosIntervaloAtual){
		double volumefinal;
		for(int i=0;i<Usinas.size();i++){
			if(Usinas.get(i) instanceof UsinaFioDagua){
				NosIntervaloAtual[i].setVolumeFinal(NosIntervaloAtual[i].getVolumeInicial());
			}else{
			volumefinal=Usinas.get(i).RegraParalela(NosIntervaloAtual[i].getLimiteMinimoVolume(),lambda,NosIntervaloAtual[i].getLimiteMaximoVolume());
			NosIntervaloAtual[i].setVolumeFinal(volumefinal);
			}
			}
	}
	
	public void VolumeMedio(NoHidroenergetico[] nosHidroenergetico){
		double volumeMedio;
		for(int i=0;i<Usinas.size();i++){
			if(Usinas.get(i) instanceof UsinaFioDagua){
		       nosHidroenergetico[i].setVolumeMedio(nosHidroenergetico[i].getVolumeInicial());;
			}else{
			volumeMedio = Usinas.get(i).VolumeMedio(nosHidroenergetico[i].getVolumeFinal(),nosHidroenergetico[i].getVolumeInicial());
			nosHidroenergetico[i].setVolumeMedio(volumeMedio);
			}
			}
		
	}
	
	public double[] BalancoHidrico(NoHidroenergetico[] nosHidroenergetico){
		
		
		double[] defluenciaUsinasAmontante = new double[Usinas.size()];
		
		for(int i=0;i<Usinas.size();i++){
			int tam=Usinas.get(i).getUsinaImediatamenteAMontante().size();
			int[] indices=new int[tam];
			for(int k=0;k<tam;k++){
				indices[k]=Usinas.get(i).getUsinaImediatamenteAMontante().get(k).getCodigo();
			}
			for(int j=0;j<tam;j++){
				defluenciaUsinasAmontante[i]=defluenciaUsinasAmontante[i] + nosHidroenergetico[indices[j]].getVazaoDefluente();
				
			}
			if(Usinas.get(i) instanceof UsinaFioDagua){
				nosHidroenergetico[i].setVazaoDefluente(defluenciaUsinasAmontante[i]+ nosHidroenergetico[i].getVazaoAfluenteIncremental());
			}else{
			double vazaoDefluente=(1000000/tempo)*((nosHidroenergetico[i].getVazaoAfluenteIncremental() + defluenciaUsinasAmontante[i])*(tempo/1000000) +
					nosHidroenergetico[i].getVolumeInicial() - nosHidroenergetico[i].getVolumeEvaporado() - nosHidroenergetico[i].getVolumeFinal()); //fator a direita esta tudo em volume e o fator a esquerda para transformar em vaz�o
			
			BigDecimal bd = new BigDecimal(vazaoDefluente).setScale(11, RoundingMode.HALF_EVEN);
			vazaoDefluente=bd.doubleValue();
			nosHidroenergetico[i].setVazaoDefluente(vazaoDefluente);
			}
		}
		return defluenciaUsinasAmontante;
	}
	
	public int ResolucaoDeConflitos(double[] defluenciaUsinasAmontante,NoHidroenergetico[] nosIntervaloAtual){
		
	int teste=0;
	double tolerancia=0.0000000001;
		for(int i=0;i<Usinas.size();i++){
			if(!(Usinas.get(i) instanceof UsinaFioDagua)){
			double limiteMinimoDefluente=nosIntervaloAtual[i].getLimiteMinimoVazaoDefluente();
			double limiteMaximoDefluente=nosIntervaloAtual[i].getLimiteMaximoVazaoDefluente();
			double vazaoDefluente=nosIntervaloAtual[i].getVazaoDefluente();
			double volumeFinal=nosIntervaloAtual[i].getVolumeFinal();
			double volume=nosIntervaloAtual[i].getVolumeInicial();
			double vazaoIncremental=nosIntervaloAtual[i].getVazaoAfluenteIncremental();
			double volumeEvaporado=nosIntervaloAtual[i].getVolumeEvaporado();

			
			if((vazaoDefluente<limiteMinimoDefluente)||(vazaoDefluente>limiteMaximoDefluente)){
				teste=1;
//				if(vazaoDefluente<limiteMinimoDefluente){
//					vazaoDefluente=limiteMinimoDefluente;
//				}
//				else{
//					vazaoDefluente=limiteMaximoDefluente;
//				}
//			
//				volumeFinal=volume- volumeEvaporado + (vazaoIncremental +  defluenciaUsinasAmontante[i] - vazaoDefluente)*(tempo/1000000);
//				nosIntervaloAtual[i].setVolumeFinal(volumeFinal);
//				VolumeMedio(nosIntervaloAtual);
//				//double volumeMedio=Usinas.get(i).VolumeMedio(volumeFinal, volume);
//				nosIntervaloAtual[i].setVazaoDefluente(vazaoDefluente);
                        }
			//nosIntervaloAtual[i].setVolumeMedio(volumeMedio);
			//BalancoHidrico(nosIntervaloAtual);
                        }
                }
		return teste;
        }
	
	public void VazaoAfluente(NoHidroenergetico[] nosHidroenergetico,double[] defluenciaUsinasAmontante){
		double vazaoIncremental;
		double vazaoAfluente;
		for(int i=0;i<Usinas.size();i++){
			if(Usinas.get(i) instanceof UsinaFioDagua){
				nosHidroenergetico[i].setVazaoAfluente(nosHidroenergetico[i].getVazaoDefluente());
			}
			 vazaoIncremental=nosHidroenergetico[i].getVazaoAfluenteIncremental();
			 vazaoAfluente=vazaoIncremental + defluenciaUsinasAmontante[i];
			 nosHidroenergetico[i].setVazaoAfluente(vazaoAfluente);
			
		}
	}
	
	public void geracaoHidraulicaMaximaContinua(NoHidroenergetico[] nosIntervaloAtual){
		double GmaxCont;
		double engolimentoMaximo;
		double potenciaMaxima;
		double nivelMontante;
		double nivelJusante;
		for(int i=0;i<Usinas.size();i++){
			GmaxCont=0;
			if(!(Usinas.get(i) instanceof UsinaFioDagua)){
			nivelMontante=Usinas.get(i).getMontante().obterValorPolinomio(nosIntervaloAtual[i].getVolumeMedio());
			nivelJusante=Usinas.get(i).getJusante().obterValorPolinomio(nosIntervaloAtual[i].getVazaoDefluente());
			nosIntervaloAtual[i].setNivelMontante(nivelMontante);
			nosIntervaloAtual[i].setNivelJusante(nivelJusante);
			}
			//System.out.println("antes engolimento");
			engolimentoMaximo=this.Usinas.get(i).Engolimento_max(nosIntervaloAtual[i].getVolumeMedio(), nosIntervaloAtual[i].getVazaoDefluente());
			//System.out.println("depois engolimento");
			potenciaMaxima=this.Usinas.get(i).P_max();
			GmaxCont=Usinas.get(i).GeracaoMaximaContinua(potenciaMaxima,nosIntervaloAtual[i].getVazaoDefluente(),nosIntervaloAtual[i].getVolumeMedio());
			nosIntervaloAtual[i].setEngolimentoMaximo(engolimentoMaximo);   
			nosIntervaloAtual[i].setLimiteMaximoGeracaoHidraulica(potenciaMaxima);    //----- verificar
			nosIntervaloAtual[i].setLimiteMinimoGeracaoHidraulica(0);
			nosIntervaloAtual[i].setGeracaoHIdraulicaMaximaContinua(GmaxCont);
			
		}
		
	}
	
	public void LimitesVazaoTurbinada(NoHidroenergetico[] nosIntervaloAtual){
		double ghmaxcont;
		double limiteMinGH;
		double limiteMaxGH = 0;
		double limiteMinOp;
		double volumeMedio;
		double ghmaxcontResultante;
		double ghminResultante;
		double ghmaxResultante;
		double limiteMinimoVazaoTurbinada;
		double limiteMaximoVazaoTurbinada;
		double ProIndividual;
		
		for(int i=0;i<Usinas.size();i++){
			
			
			
			ghmaxcont=nosIntervaloAtual[i].getGeracaoHIdraulicaMaximaContinua();
			limiteMinGH=nosIntervaloAtual[i].getLimiteMinimoGeracaoHidraulica();
			limiteMaxGH=nosIntervaloAtual[i].getLimiteMaximoGeracaoHidraulica();
			limiteMinOp=nosIntervaloAtual[i].getLimiteMinimoVolume();
			volumeMedio=nosIntervaloAtual[i].getVolumeMedio();
				//a fun��o de produtividade mudou- agora � abstrata
				ProIndividual=Usinas.get(i).ProdutividadeMedia(volumeMedio, limiteMinOp);
			
			ghmaxResultante=Math.min(ghmaxcont,limiteMaxGH);
			ghminResultante=Math.min(ghmaxResultante, limiteMinGH);
			limiteMinimoVazaoTurbinada=ghminResultante/ProIndividual;
			limiteMaximoVazaoTurbinada=ghmaxResultante/ProIndividual;
			nosIntervaloAtual[i].setProdutividadeMediaIndividual(ProIndividual);
			nosIntervaloAtual[i].setLimiteMaximoVazaoTurbinada(limiteMaximoVazaoTurbinada);
			nosIntervaloAtual[i].setLimiteMinimoVazaoTurbinada(limiteMinimoVazaoTurbinada);
			nosIntervaloAtual[i].setGeracaoHidraulicaMaxima(ghmaxResultante);
				
		}
		
	}
	
	public void VazaoTurbinada(NoHidroenergetico[] nosIntervaloAtual){
		double u;
		double qmax;
		double q;
		for(int i=0;i<Usinas.size();i++){
			u=nosIntervaloAtual[i].getVazaoDefluente();
			qmax=Math.min(nosIntervaloAtual[i].getLimiteMaximoVazaoTurbinada(), nosIntervaloAtual[i].getEngolimentoMaximo());
		
			if(u>qmax){
				q=qmax;
				if(nosIntervaloAtual[i].getLimiteMaximoVazaoTurbinada()<nosIntervaloAtual[i].getEngolimentoMaximo()){
					//System.out.println(" limite maior intervalo "+qmax);
				}
				nosIntervaloAtual[i].setVazaoVertida(u-qmax);
			}
			else{
				q=u;
				
				nosIntervaloAtual[i].setVazaoVertida(0);
			}
			nosIntervaloAtual[i].setVazaoTurbinada(q);
			
		}
	}
	
	
        public double[] DefluenciasUsinasAMontante(NoHidroenergetico[] nosIntervaloAtual){
            double[] defluenciaUsinasAmontante = new double[Usinas.size()];
            for(int i=0;i<Usinas.size();i++){
                if(i==0)
                    defluenciaUsinasAmontante[i]=0;
                else
                    defluenciaUsinasAmontante[i]=nosIntervaloAtual[i-1].getVazaoDefluente();
            }
            return defluenciaUsinasAmontante;
        }
                
	public double GeracaoHidraulica(NoHidroenergetico[] nosIntervaloAtual){
		
//		volumefinal(lambda, nosIntervaloAtual); 
		VolumeMedio(nosIntervaloAtual);			
		double[] defluenciaUsinasAmontante = DefluenciasUsinasAMontante(nosIntervaloAtual); 
//		defluenciaUsinasAmontante=BalancoHidrico(nosIntervaloAtual); 
//		//System.out.println("antes resolucao");
//		double teste=ResolucaoDeConflitos(defluenciaUsinasAmontante, nosIntervaloAtual);
//		if(teste==1){
//			return 0;
//			}
		//System.out.println("depois resolucao");
                // fazer uma função que calcula as defluencias da usinas a montante
		VazaoAfluente(nosIntervaloAtual, defluenciaUsinasAmontante);
		geracaoHidraulicaMaximaContinua(nosIntervaloAtual);
		LimitesVazaoTurbinada(nosIntervaloAtual); 
		VazaoTurbinada(nosIntervaloAtual);
		double geracaoHidraulica;
		double geracaoHidraulicaSistema = 0;
		double alturaQuedaLiquida;
		for(int i=0;i<Usinas.size();i++){
			
			alturaQuedaLiquida=Usinas.get(i).altura_queda_liquida(nosIntervaloAtual[i].getVolumeMedio(), nosIntervaloAtual[i].getVazaoDefluente());
			geracaoHidraulica=alturaQuedaLiquida*nosIntervaloAtual[i].getVazaoTurbinada()*Usinas.get(i).getK();
			nosIntervaloAtual[i].setAlturaQuedaLiquida(alturaQuedaLiquida);
			nosIntervaloAtual[i].setGeracaoHidraulica(geracaoHidraulica);
			geracaoHidraulicaSistema=geracaoHidraulica + geracaoHidraulicaSistema;
		}
		return geracaoHidraulicaSistema;
		 
	}
	
	public double AjusteLambda(double alfa,double paramlambda,double fatorDeCorrecao){
		double lambda=paramlambda;
		lambda=lambda + alfa*fatorDeCorrecao;
		if(lambda>1){
			lambda=1;
		}
		if(lambda<0){
			lambda=0;
		}
		return lambda;
		
	}
	
	public double Executar(int intervalo,double Demanda, NoHidroenergetico[] nosIntervaloAtual,
                               NoHidroenergetico[] nosIntervaloanterior,IntervaloDeHorizonte intervaloHorizonte){
		CalcularAfluenciaIncremental(nosIntervaloAtual);
		//o primeiro volume � iniciado como parametro
		if(intervalo!=0){
			double[] volumes=new double[Usinas.size()];
			for(int j=0;j<Usinas.size();j++){
				volumes[j]=nosIntervaloanterior[j].getVolumeFinal();
			}
			IniciarVolumes(volumes, nosIntervaloAtual);
		}
		
		double geracaoHidraulicaSistema=GeracaoHidraulica(nosIntervaloAtual);
                double geracaoComplementar;
		geracaoComplementar = Demanda - geracaoHidraulicaSistema;
		if(geracaoComplementar<0)
			geracaoComplementar=0;
		intervaloHorizonte.setGeracaoComplementar(geracaoComplementar);
		
		intervaloHorizonte.setEnergiaArmazenadaSistema(EnergiaArmazenadaNoSistema(nosIntervaloAtual));
		return geracaoHidraulicaSistema;
	}
}

