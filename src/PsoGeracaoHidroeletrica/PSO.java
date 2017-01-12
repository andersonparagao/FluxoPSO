package PsoGeracaoHidroeletrica;

import fluxoemredes.Arco;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import simulacao.CarregarSistema;
//import simulacao.SimulacaoOperacaoEnergetica;
import simulacao.SimulacaoOperacaoEnergeticaPSO;

public class PSO {

    private double c1 = 2;
    private double c2 = 2;
    private double r1;
    private double r2;
    private double[] xminvolume;
    private double[] xmaxvolume;
    private double[] xminvazao;
    private double[] xmaxvazao;
    private double[] volumesfinais_simulacao;
    private SimulacaoOperacaoEnergeticaPSO simulacaoHidroeletrica;
    private CarregarSistema carregar = new CarregarSistema();
    private ParticulaPSO[] particulas;
    private int numUsinas;
    private int numIntervalos;
    private int numeroParticulas;
    private double gbest;
    private double[][] vetorGbest;
    private int iteracoes;
    private int g;
    List<List<Arco>> arcosSuperBasicos; 

    public PSO(ParticulaPSO[] populacao, List<List<Arco>> arcosSuperBasicos, SimulacaoOperacaoEnergeticaPSO simulacaoHidroeletrica, double demanda, int numeroParticulas, int numUsinas, int Intervalos, int iteracoesFluxo, double c1, double c2) {
        this.numeroParticulas = numeroParticulas;
        this.numUsinas = numUsinas;
        this.numIntervalos = Intervalos;
        this.iteracoes = iteracoesFluxo;
        this.xminvolume = new double[numUsinas];
        this.xmaxvolume = new double[numUsinas];
        this.xminvazao = new double[numUsinas];
        this.xmaxvazao = new double[numUsinas];
        this.particulas = populacao;
        this.simulacaoHidroeletrica = simulacaoHidroeletrica;
        this.vetorGbest = new double[numUsinas][numIntervalos*2];
        this.c1 = c1;
        this.c2 = c2;
        this.arcosSuperBasicos = arcosSuperBasicos;
    }


    public void AvaliarParticulas() {
        for (int i = 0; i < numeroParticulas; i++) {
            particulas[i].AvaliarParticula(simulacaoHidroeletrica);
        }
    }

    public void AtualizarVelocidade() {
        Random geradorAleatorior1 = new Random();
        r1 = geradorAleatorior1.nextDouble();
        Random geradorAleatorior2 = new Random();
        r2 = geradorAleatorior2.nextDouble();

        for (int i = 0; i < numeroParticulas; i++) {
            particulas[i].AtualizarVelocidade(simulacaoHidroeletrica, c1, c2, r1, r2, vetorGbest, arcosSuperBasicos.get(i));
        }

    }

    public void AtualizarPosicao() {
        for (int i = 0; i < numeroParticulas; i++) {
            particulas[i].AtualizarPosicao();
        }
    }

    public void ObterGbest() {
        double gbestIteracao;
        gbestIteracao = particulas[0].getPbest();
        int gIteracao = 0;
        // obter o melhor da iteracao
        for (int i = 1; i < numeroParticulas; i++) {
            if (gbestIteracao > particulas[i].getPbest()) {
                gIteracao = i;
                gbestIteracao = particulas[i].getPbest();
            }
        }
        
        if(gbestIteracao < gbest){
            for (int i = 0; i < numUsinas; i++) {
               System.arraycopy(particulas[gIteracao].getPosicao()[i], 0, vetorGbest[i], 0, particulas[0].getPosicao()[0].length);
            }
            gbest = gbestIteracao;
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
    public List<double[][]> executar() {
        AvaliarParticulas();
        ObterGbest();
        AtualizarVelocidade();
        List<double[][]> velocidadesParticulas = new ArrayList<>();
        for (int i = 0; i < particulas.length; i++) {
            velocidadesParticulas.add(particulas[i].getVelocidade());
        }
        return velocidadesParticulas; 
    }
}


