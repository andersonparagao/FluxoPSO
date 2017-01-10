package PsoGeracaoHidroeletrica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import simulacao.CarregarSistema;
//import simulacao.SimulacaoOperacaoEnergetica;
import simulacao.SimulacaoOperacaoEnergeticaPSO;

public class PSO {
	private double c1=2;
	private double c2=2;
	private double r1;
	private double r2;
	private double[] xminvolume;
	private double[] xmaxvolume;
        private double[] xminvazao;
        private double[] xmaxvazao;
	private double[] volumesfinais_simulacao;
	private SimulacaoOperacaoEnergeticaPSO simulacaoHidroeletrica;
	private CarregarSistema carregar=new CarregarSistema();
	private ArrayList<ParticulaPSO> particulas;
	private int numUsinas;
        private int numIntervalos;
	private int numeroParticulas;
	private double gbest;
	private double[][][] vetorGbest;
	private int iteracoes;
	private int g;
	public PSO(double demanda,int numeroParticulas,int numUsinas,int Intervalos,int iteracoes,double c1,double c2){
		this.numeroParticulas=numeroParticulas;
		this.numUsinas=numUsinas;
                this.numIntervalos = Intervalos;
		this.iteracoes=iteracoes;
		this.xminvolume=new double[numUsinas];
		this.xmaxvolume=new double[numUsinas];
                this.xminvazao= new double[numUsinas];
                this.xmaxvazao= new double[numUsinas];
		particulas=new ArrayList<>();
		vetorGbest=new double[iteracoes][numUsinas][numIntervalos];
		this.c1=c1;
		this.c2=c2;
		simulacaoHidroeletrica=new SimulacaoOperacaoEnergeticaPSO(Intervalos,demanda);
		try {
			carregar.UsinasMinas(simulacaoHidroeletrica);
                        inicializaLimitesVolumeVazao(simulacaoHidroeletrica);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("erro ao carregar o sistema hidroeletrico");
		}
	}
	
        public void inicializaLimitesVolumeVazao(SimulacaoOperacaoEnergeticaPSO simulacao){
            for(int i=0;i<numUsinas;i++){
                xmaxvolume[i] = simulacao.getNos()[0][i].getLimiteMaximoVolume();
                xminvolume[i] = simulacao.getNos()[0][i].getLimiteMinimoVolume();
                xminvazao[i] = simulacao.getNos()[0][i].getLimiteMinimoVazaoDefluente();
                xmaxvazao[i] = simulacao.getNos()[0][i].getLimiteMaximoVazaoDefluente();
                
            }
            
        }
        
	public void InicializarParticulas(){
		for(int i=0;i<numeroParticulas;i++){
			ParticulaPSO particula=new ParticulaPSO(iteracoes,numUsinas,numIntervalos,i,xminvazao,xmaxvazao,xminvolume,xmaxvolume,this.simulacaoHidroeletrica); 
			particulas.add(particula);
		}
	}
        public void definirVolumesFinais(){
            
        }
	
	public void AvaliarParticulas(int iteracao){
		for(int i=0;i<numeroParticulas;i++){
		
			particulas.get(i).AvaliarParticula(iteracao,simulacaoHidroeletrica);
		}
		
	}
	
	
	public void AtualizarVelocidade(int iteracao){
		          Random geradorAleatorior1 = new Random();
                          r1 = geradorAleatorior1.nextDouble();
                          Random geradorAleatorior2 = new Random();
                          r2 = geradorAleatorior2.nextDouble();
		
		for(int i=0;i<numeroParticulas;i++){
			particulas.get(i).AtualizarVelocidade(simulacaoHidroeletrica,iteracao, c1, c2, r1, r2, vetorGbest);
			
		}
		
	}
	
	
	public void AtualizarPosicao(int iteracao){
		
		for(int i=0;i<numeroParticulas;i++){
			particulas.get(i).AtualizarPosicao(iteracao);
			
		}
		
	}
	
	public void ObterGbest(int iteracao){
		double gbestIteracao;
		gbestIteracao=particulas.get(0).getPbest();
		int gIteracao=0;
		// obter o melhor da iteracao
		for(int i=1;i<numeroParticulas;i++){
			if(gbestIteracao>particulas.get(i).getPbest()){
				gIteracao=i;
				gbestIteracao=particulas.get(i).getPbest();
			}
		}
		//se o melhor da iteracao for melhor que o gbest
		if((gbest>gbestIteracao)||(iteracao==0)){
			gbest=gbestIteracao;
			g=gIteracao;
			for(int i=0;i<numUsinas;i++){
                            //verificar se assim funciona
                            //vetorGbest[iteracao][i] = particulas.get(g).getX()[iteracao][i];
                            //outra maneira de fazer
                            System.arraycopy(particulas.get(g).getX()[iteracao][i], 0, vetorGbest[iteracao][i], 0, numIntervalos);
//                            for(int j=0;j<numIntervalos;j++){
//				vetorGbest[iteracao][i][j]=particulas.get(g).getX()[iteracao][i][j];
//                            }    
			}
		}
		//se algum da atual iteracao nao for melhor e pq ate o momento o melhor tbm e o melhor da iteracao anterior
		else{
			for(int i=0;i<numUsinas;i++){
                            System.arraycopy(vetorGbest[iteracao-1][i], 0, vetorGbest[iteracao][i], 0, numIntervalos);    
			}
		}
	}
	
	
//	public void InicializarPbestGbest(){
//		double fitnes = 0;
//
//		for(int i=0;i<numeroParticulas;i++){
//			double[][][] posicao=particulas.get(i).getX();
//			double x1=posicao[0][0][0];
//			double x2=posicao[0][];
//			//fitnes=(x1*x1) -(x1*x2)+(x2*x2)-(3*x2);
//			particulas.get(i).setPbest(fitnes);
//			particulas.get(i).getXx()[0][0]=posicao[0][0];
//			particulas.get(i).getXx()[0][1]=posicao[0][1];
//			if(i==0){
//				gbest=fitnes;
//				g=i;
//				for(int j=0;j<dimensao;j++){
//					vetorGbest[0][i]=posicao[0][i];
//				}
//
//			}else{
//				if(fitnes<gbest){
//					g=i;
//					gbest=fitnes;
//					vetorGbest[0][0]=posicao[0][0];
//					vetorGbest[0][1]=posicao[0][1];
//				}
//			}
//			particulas.get(i).setPbest(fitnes);
//		}
//	}
//	
	
	
	public double[][] executar(){
		InicializarParticulas();
		AvaliarParticulas(0);
		ObterGbest(0);
		for(int i=1;i<iteracoes;i++){
			
			AtualizarVelocidade(i);
			AtualizarPosicao(i);
			AvaliarParticulas(i);
			ObterGbest(i);
			
		}
		//System.out.println("melhor : "+gbest);
		imprimirParticula(0);
		return vetorGbest[iteracoes-1];
	}
        
        public void imprimirParticula(int indiceparticula){
//            for(int i=0;i<numUsinas;i++){
//                System.out.println("");
//                System.out.println("Usina " + (i+1));
//                for(int j=0;j<numIntervalos;j++){
//                    System.out.print(particulas.get(indiceparticula).getPosicao()[0][i][j]+", ");
//                }
//                
//            }
//            for(int i=0;i<numUsinas;i++){
//                System.out.println("");
//                System.out.println("Usina " + (i+1));
//                for(int j=numIntervalos;j<numIntervalos*2;j++){
//                    System.out.print(particulas.get(indiceparticula).getPosicao()[0][i][j]+", ");
//                }
//                
//            }
            
        }
	
//	public void EnergiaArmazenadaMelhor(){
//		simulacaoHidroeletrica.definirVolumesFinais(particulas.get(g).getX()[99],3, 60);
//		simulacaoHidroeletrica.simularOperacaoEnergeticaPSO(60);
//		//simulacaoHidroeletrica.determinaValorAgua(particulas.get(g).getValorAgua());
//		//simulacaoHidroeletrica.definirVolumesFinais(particulas.get(g).getX()[99], 3,60);
//		System.out.println("energia melhor-> "+simulacaoHidroeletrica.EnergiaArmazenada());
//	}
	

}
