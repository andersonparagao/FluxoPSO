package PsoGeracaoHidroeletrica;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import simulacao.SimulacaoOperacaoEnergeticaPSO;

public class ParticulaPSO {

	private double[][][] velocidade;
	private double[][][] posicao;
	private final double[][] posicaomin;
	private final double[][] posicaomax;
	private final double[][] velocidademax,velocidademin;
	private double pbest;
	private double[][][] vetorpbest;
	//private double[] valorAgua;
	
	public ParticulaPSO(int iteracao,int numUsinas,int numIntervalos,int i,double[] xminvazao,double[] xmaxvazao,double[] xminvolume,double[] xmaxvolume,SimulacaoOperacaoEnergeticaPSO simulacao){
		//a variavel volume vazao é porque as usinas terao como valores os volumes finais e vazaoes defluentes em sequencia
                int volumeVazao=numIntervalos*2;
                posicao=new double[iteracao][numUsinas][volumeVazao];
		velocidade=new double[iteracao][numUsinas][volumeVazao];
		vetorpbest=new double[iteracao][numUsinas][volumeVazao];
		posicaomin=new double[numUsinas][volumeVazao];
		posicaomax=new double[numUsinas][volumeVazao];
		//valorAgua=new double[dimensao];
		velocidademax=new double[numUsinas][volumeVazao];
		velocidademin=new double[numUsinas][volumeVazao];
                //foi separado devido aos limites de volume e vazão serem diferentes
                for(int j=0;j<numUsinas;j++){
                    for(int k=0;k<numIntervalos;k++){
                        posicaomin[j][k] = xminvolume[j];
                        posicaomax[j][k] = xmaxvolume[j];
                    }
		}
                for(int j=0;j<numUsinas;j++){
                    for(int k=numIntervalos;k<volumeVazao;k++){
                        posicaomin[j][k] = xminvazao[j];
                        posicaomax[j][k] = xmaxvazao[j];
                    }
		}
                for(int j=0;j<numUsinas;j++){
                    for(int k=0;k<numIntervalos;k++){
                        velocidademin[j][k] = (xminvolume[j] - xmaxvolume[j])/iteracao;
                        velocidademax[j][k] = (xmaxvolume[j] - xminvolume[j])/iteracao;
			}
                }
                
                for(int j=0;j<numUsinas;j++){
                    for(int k=numIntervalos;k<volumeVazao;k++){
                        velocidademin[j][k] = (xminvazao[j] - xmaxvazao[j])/iteracao;
                        velocidademax[j][k] = (xmaxvazao[j] - xminvazao[j])/iteracao;
			}
                }
                System.out.println("Volumes");
                for(int j = 0;j<numUsinas;j++){
                    System.out.println("Usinas "+(j+1));
                    for(int k = 0 ; k < numIntervalos;k++){
                        Random gerador= new Random();
                        double valor = (gerador.nextInt(10)+ 90 + gerador.nextDouble() )/100.0; 
                        //System.out.println(valor);
                        
                        posicao[0][j][k] = xmaxvolume[j]*valor;
                        System.out.println(posicao[0][j][k]);
                    }
//                    for(int k = numIntervalos ; k < volumeVazao;k++){
//                        //apenas a natural pq as usinas a montante defluem tudo que chegam nelas, portanto acaba sendo a natural
//                        //ajustar os valores da vazão defluente com relação ao volume
//                        posicao[0][j][k] = simulacao.getNos()[k-numIntervalos][j].getVazaoAfluenteNatural();   
//                    }

                    //inicializarVazoes(posicao[0], numIntervalos, numUsinas, simulacao);
                }
                double[][] volumeInicial= volumeInicial(posicao[0], numIntervalos, numUsinas);
                double[][] vazaoDefluenteAmontante = inicializarVazoes(volumeInicial, posicao[0], numIntervalos, numUsinas, simulacao);
                int teste = ResolucaoDeConflitos(volumeInicial, posicao[0], vazaoDefluenteAmontante, simulacao, numUsinas, numIntervalos);
		while(teste == 1){
                    //System.out.println("teste == 1");
                    volumeInicial= volumeInicial(posicao[0], numIntervalos, numUsinas);
                    vazaoDefluenteAmontante =  inicializarVazoes(volumeInicial, posicao[0], numIntervalos, numUsinas, simulacao);
                    teste = ResolucaoDeConflitos(volumeInicial, posicao[0], vazaoDefluenteAmontante, simulacao, numUsinas, numIntervalos);
                }
                System.out.println("volumes finais");
                for(int j=0;j<numUsinas;j++){
                    System.out.println("Usina "+ (j+1));
                    for(int k=0;k<numIntervalos;k++){
                        System.out.println(posicao[0][j][k]);
                    }
                }
//		for(int j=0;j<dimensao;j++){
//			//gerar a parte decimal
//			double decimal=geradorposicao.nextDouble();
//			//gerar
//			double neg=(geradorposicao.nextInt(2));
//			neg=(-1)*(neg*0.01)*(paramVolumesfinais[j]);
//			double pos=(geradorposicaopos.nextInt(3));
//			pos=(pos*0.01)*(paramVolumesfinais[j]);
//			posicao[0][j]=paramVolumesfinais[j] + pos+neg;
//			if(posicao[0][j]<posicaomin[j]){
//				posicao[0][j]=posicaomin[j];
//			}
//			else{
//				if(posicao[0][j]>posicaomax[j]){
//					posicao[0][j]=posicaomax[j];
//				}
//			}
//			//posicao[0][j]=geradorposicao.nextInt((int) (this.posicaomax[j]-this.posicaomin[j]-1))+this.posicaomin[j]+decimal;
//			decimal=geradorposicao.nextDouble();
//			//velocidade[0][j]=geradorposicao.nextInt((int) (velocidademax[j]-velocidademin[j]-1))+velocidademin[j]+decimal;
//			velocidade[0][j]=0;
//			
//		}
		
	}
        
        public double[][] volumeInicial(double[][] volumevazao,int numintervalos,int numUsinas){
            double[][] volumeinicial=new double[numUsinas][numintervalos];
            //System.out.println("volume inicial");
            for(int i=0;i<numUsinas;i++){
                for(int j=0;j<numintervalos;j++){
                    if(j==0){
                        volumeinicial[i][j] = posicaomax[i][j];
                    }
                    else{
                        volumeinicial[i][j] = volumevazao[i][j-1];
                    }
                    //System.out.println(volumeinicial[i][j]);
                }    
            }
            return volumeinicial;
        }
        
        public double[][] inicializarVazoes(double[][] volumeinicial,double[][] volumevazao,int numintervalos,int numUsinas,SimulacaoOperacaoEnergeticaPSO simulacao){
           
            double[][] vazaoDefluenteAmontante = new double[numUsinas][numintervalos];
           
            for(int i=0;i<numUsinas;i++){
                for(int j=numintervalos;j<numintervalos*2;j++){
                    //double vazaoDefluenteAmontante=0;
                    if(i!=0){
                        vazaoDefluenteAmontante[i][j-numintervalos] = volumevazao[i-1][j];
                    }
                    double vazaoDefluente=(1000000.0/2628000.0)*((simulacao.getNos()[j-numintervalos][i].getVazaoAfluenteNatural() + vazaoDefluenteAmontante[i][j-numintervalos])*(2628000.0/1000000.0) +
					volumeinicial[i][j-numintervalos] - volumevazao[i][j-numintervalos]); //fator a direita esta tudo em volume e o fator a esquerda para transformar em vaz�o
			
                    BigDecimal bd = new BigDecimal(vazaoDefluente).setScale(11, RoundingMode.HALF_EVEN);
                    vazaoDefluente=bd.doubleValue();
                    volumevazao[i][j]=vazaoDefluente;
              
                }
            }    
            return vazaoDefluenteAmontante;
                
        }
        
        public int ResolucaoDeConflitos(double[][] volumeinicial,double[][] volumevazao,double[][] defluenciaUsinasAmontante,SimulacaoOperacaoEnergeticaPSO simulacao,int numUsinas,int numIntervalos){
		
	int teste=0;
        int tamVolumevazao= numIntervalos*2;
	double tolerancia=0.0000000001;
		for(int i=0;i<numUsinas;i++){
                    for(int j=numIntervalos;j<tamVolumevazao;j++){
			//if(!(Usinas.get(i) instanceof UsinaFioDagua)){
			double limiteMinimoDefluente= simulacao.getNos()[0][i].getLimiteMinimoVazaoDefluente();
			double limiteMaximoDefluente=simulacao.getNos()[0][i].getLimiteMaximoVazaoDefluente();
			double vazaoDefluente=volumevazao[i][j];
			double volumeFinal;
//                        System.out.println("intervalo: "+(j-numIntervalos));
//                        System.out.println("Usina: "+(i+1));
//                        System.out.println("vazaoDefluente: "+volumevazao[i][j]);
//                        System.out.println("volume inicial: " +volumeinicial[i][j-numIntervalos]);
//                        System.out.println("volume final antes de alterar: "+volumevazao[i][j-numIntervalos]);
                        
                            
			//double volumeinicial= volumevazao;
                        double vazaoNaturalUsinasAmontante=0;
                        if(i!=0){
                            vazaoNaturalUsinasAmontante = simulacao.getNos()[j-numIntervalos][i-1].getVazaoAfluenteNatural();
                        }
                        
                        double vazaoIncremental=simulacao.getNos()[j-numIntervalos][i].getVazaoAfluenteNatural()- vazaoNaturalUsinasAmontante;
			//double volumeEvaporado=nosIntervaloAtual[i].getVolumeEvaporado();
                       // System.out.println("vazao incremental: "+vazaoIncremental);
			
			if((vazaoDefluente<limiteMinimoDefluente)||(vazaoDefluente>limiteMaximoDefluente)){
                            System.out.println("intervalo: "+(j-numIntervalos));
                            System.out.println("Usina: "+(i+1));
                            System.out.println("vazaoDefluente: "+volumevazao[i][j]);
                            System.out.println("volume inicial: " +volumeinicial[i][j-numIntervalos]);
                            System.out.println("volume final antes de alterar: "+volumevazao[i][j-numIntervalos]);
                            System.out.println("vazao incremental: "+vazaoIncremental);
				teste=1;
				if(vazaoDefluente<limiteMinimoDefluente){
					vazaoDefluente=limiteMinimoDefluente;
				}
				else{
					vazaoDefluente=limiteMaximoDefluente;
				}
                            System.out.println("vazao defluente: "+vazaoDefluente);    
				volumeFinal=volumeinicial[i][j-numIntervalos] + (vazaoIncremental +  defluenciaUsinasAmontante[i][j-numIntervalos] - vazaoDefluente)*(2628000.0/1000000);
				//nosIntervaloAtual[i].setVolumeFinal(volumeFinal);
                                volumevazao[i][j-numIntervalos]=volumeFinal;
                                System.out.println("volume final depois de alterar: "+volumeFinal);

				//VolumeMedio(nosIntervaloAtual);
				//double volumeMedio=Usinas.get(i).VolumeMedio(volumeFinal, volume);
				//nosIntervaloAtual[i].setVazaoDefluente(vazaoDefluente);
                                return teste;
                        }
		}
			//nosIntervaloAtual[i].setVolumeMedio(volumeMedio);
			//BalancoHidrico(nosIntervaloAtual);
//}
	}
		return teste;
	}
	
	public void AtualizarVelocidade(SimulacaoOperacaoEnergeticaPSO simulacao,int iteracao,double c1,double c2,double r1,double r2,double[][][] xxx){
            int numUsinas=posicaomin.length;
            int numIntervalos=posicao[0].length;
            for(int i=0;i<numUsinas;i++){
                for(int j=0;j <numIntervalos;j++){
                    velocidade[iteracao][i][j]= velocidade[iteracao-1][i][j] + c1*r1*(vetorpbest[iteracao-1][i][j] - posicao[iteracao-1][i][j])+
                    		c2*r2*(xxx[iteracao-1][i][j]-posicao[iteracao-1][i][j]);
                
		//verifica se passou dos limites
                    
                    if(velocidade[iteracao][i][j]>velocidademax[i][j]){
                        velocidade[iteracao][i][j]=velocidademax[i][j];
			
                        }
                    if(velocidade[iteracao][i][j] <velocidademin[i][j]){
                        velocidade[iteracao][i][j]=velocidademin[i][j];
                    }
	
		}
            }    
	}
	
	
	
	public void AtualizarPosicao(int iteracao){
            int numUsinas=posicaomin.length;
            int numIntervalos=posicaomin[0].length;
            for(int i=0;i<numUsinas;i++){		
		for(int j=0;j<numIntervalos;j++){
                    posicao[iteracao][i][j]=posicao[iteracao-1][i][j]+ velocidade[iteracao][i][j];
                    if(posicao[iteracao][i][j]>posicaomax[i][j]){
			posicao[iteracao][i][j]=posicaomax[i][j];
                    }
                    if(posicao[iteracao][i][j]<posicaomin[i][j]){
			posicao[iteracao][i][j]=posicaomin[i][j];
                    }
		}
            }
	}
	
	
	public void AvaliarParticula(int iteracao,SimulacaoOperacaoEnergeticaPSO simulacao){
                int numIntervalos = posicaomin[0].length/2;
                int numUsinas= posicaomin.length;
		double fitness;
		simulacao.definirVolumesFinais(posicao[iteracao],numUsinas, numIntervalos);
                simulacao.definirVazoesDefluentes(posicao[iteracao],numUsinas, numIntervalos);
		fitness=simulacao.simularOperacaoEnergeticaPSO(numIntervalos);
		//simulacao.determinaValorAgua(this.valorAgua);
		//fitnes=(posicao[iteracao][0])*(posicao[iteracao][0]) -(posicao[iteracao][0])*(posicao[iteracao][1]) +(posicao[iteracao][1])*(posicao[iteracao][1]) -3*(posicao[iteracao][1]);
		if(iteracao==0){
			pbest=fitness;
			for (int i = 0; i < posicaomin.length; i++) {
                            System.arraycopy(posicao[iteracao][i], 0, vetorpbest[iteracao][i], 0, numIntervalos*2);    
			}
		}else{
			if(fitness<pbest){
			pbest=fitness;
			for (int i = 0; i < posicaomin.length; i++) {
                            System.arraycopy(posicao[iteracao][i], 0, vetorpbest[iteracao][i], 0, numIntervalos*2);    
			}
			}else{
				for (int i = 0; i < posicaomin.length; i++) {
                                    System.arraycopy(vetorpbest[iteracao-1][i], 0, vetorpbest[iteracao][i], 0, numIntervalos*2);    
				}
			}
		}
		
	}
	
	
//	public double[] getValorAgua() {
//		return valorAgua;
//	}
//
//	public void setValorAgua(double[] valorAgua) {
//		this.valorAgua = valorAgua;
//	}

	public double[][][] getV() {
		return velocidade;
	}


	public void setV(double[][][] v) {
		this.velocidade = v;
	}


	public double[][][] getX() {
		return posicao;
	}


	public void setX(double[][][] x) {
		this.posicao = x;
	}

	public double getPbest() {
		return pbest;
	}

	public void setPbest(double pbest) {
		this.pbest = pbest;
	}

	public double[][][] getXx() {
		return vetorpbest;
	}

	public void setXx(double[][][] xx) {
		this.vetorpbest = xx;
	}

    public double[][][] getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double[][][] velocidade) {
        this.velocidade = velocidade;
    }

    public double[][][] getPosicao() {
        return posicao;
    }

    public void setPosicao(double[][][] posicao) {
        this.posicao = posicao;
    }

    public double[][][] getVetorpbest() {
        return vetorpbest;
    }

    public void setVetorpbest(double[][][] vetorpbest) {
        this.vetorpbest = vetorpbest;
    }


	


	
	
	

}
