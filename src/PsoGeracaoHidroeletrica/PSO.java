package PsoGeracaoHidroeletrica;

import fluxoemredes.Arco;
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
    private double[] volumeMinimo;
    private double[] volumeMaximo;
    private double[] vazaoMinima;
    private double[] vazaoMaxima;
    private double[] volumesFinaisSimulacao;
    private SimulacaoOperacaoEnergeticaPSO simulacaoHidroeletrica;
    private ParticulaPSO[] enxame;
    private int numUsinas;
    private int numIntervalos;
    private double gbest;
    private double[][] vetorGbest;

    public PSO(SimulacaoOperacaoEnergeticaPSO simulacaoHidroeletrica, double demanda, double[] vazaoMinima, double[] vazaoMaxima, double[] volumeMinimo, double[] volumeMaximo, int numeroParticulas, int numUsinas, int numIntervalos, double c1, double c2) {
        this.numUsinas = numUsinas;
        this.numIntervalos = numIntervalos;
        this.volumeMinimo = volumeMinimo;
        this.volumeMaximo = volumeMaximo;
        this.vazaoMinima = vazaoMinima;
        this.vazaoMaxima = vazaoMaxima;
        this.simulacaoHidroeletrica = simulacaoHidroeletrica;
        this.vetorGbest = new double[numUsinas][numIntervalos*2];
        this.enxame = new ParticulaPSO[numeroParticulas];
        this.c1 = c1;
        this.c2 = c2;
    }


    public void AvaliarParticulas() {
        for (int i = 0; i < enxame.length; i++) {
            enxame[i].AvaliarParticula();
        }
    }

    public void AtualizarVelocidade(List<List<Arco>> arcosSuperBasicos) {
        Random geradorAleatorior1 = new Random();
        r1 = geradorAleatorior1.nextDouble();
        Random geradorAleatorior2 = new Random();
        r2 = geradorAleatorior2.nextDouble();

        for (int i = 0; i < enxame.length; i++) {
            enxame[i].AtualizarVelocidade(c1, c2, r1, r2, vetorGbest, arcosSuperBasicos.get(i));
        }
    }
    
    public void inicializaParticulas(int iteracaoFluxo){
        for (int i = 0; i < enxame.length; i++) {
            ParticulaPSO particula = new ParticulaPSO(numUsinas, numIntervalos, vazaoMinima, vazaoMaxima, volumeMinimo, volumeMaximo, simulacaoHidroeletrica, 1);
            particula.inicializaParticula();
            enxame[i] = particula;
        }
    }

    // não vamos usar agora, quem vai atualizar a posição é o Fluxo em Redes
//    public void AtualizarPosicao() {
//        for (int i = 0; i < numeroParticulas; i++) {
//            enxame[i].AtualizarPosicao();
//        }
//    }

    public void ObterGbest() {
        double gbestIteracao;
        gbestIteracao = enxame[0].getpBest();
        int gIteracao = 0;
        // obter o melhor da iteracao
        for (int i = 1; i < enxame.length; i++) {
            if (gbestIteracao > enxame[i].getpBest()) {
                gIteracao = i;
                gbestIteracao = enxame[i].getpBest();
            }
        }
        
        if(gbestIteracao < gbest){
            for (int i = 0; i < numUsinas; i++) {
               System.arraycopy(enxame[gIteracao].getPosicao()[i], 0, vetorGbest[i], 0, enxame[0].getPosicao()[0].length);
            }
            gbest = gbestIteracao;
        }
    }
//	public void InicializarPbestGbest(){
//		double fitnes = 0;
//
//		for(int i=0;i<numeroParticulas;i++){
//			double[][][] posicao=enxame.get(i).getX();
//			double x1=posicao[0][0][0];
//			double x2=posicao[0][];
//			//fitnes=(x1*x1) -(x1*x2)+(x2*x2)-(3*x2);
//			enxame.get(i).setPbest(fitnes);
//			enxame.get(i).getXx()[0][0]=posicao[0][0];
//			enxame.get(i).getXx()[0][1]=posicao[0][1];
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
//			enxame.get(i).setPbest(fitnes);
//		}
//	}
//	
    public List<double[][]> executar(List<List<Arco>> arcosSuperBasicos) {
        AvaliarParticulas();
        ObterGbest();
        AtualizarVelocidade(arcosSuperBasicos);
        List<double[][]> velocidadesParticulas = new ArrayList<>();
        for (int i = 0; i < enxame.length; i++) {
            velocidadesParticulas.add(enxame[i].getVelocidade());
        }
        return velocidadesParticulas; 
    }

    public double getC1() {
        return c1;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public double getC2() {
        return c2;
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }

    public double getR1() {
        return r1;
    }

    public void setR1(double r1) {
        this.r1 = r1;
    }

    public double getR2() {
        return r2;
    }

    public void setR2(double r2) {
        this.r2 = r2;
    }

    public double[] getVolumeMinimo() {
        return volumeMinimo;
    }

    public void setVolumeMinimo(double[] volumeMinimo) {
        this.volumeMinimo = volumeMinimo;
    }

    public double[] getVolumeMaximo() {
        return volumeMaximo;
    }

    public void setVolumeMaximo(double[] volumeMaximo) {
        this.volumeMaximo = volumeMaximo;
    }

    public double[] getVazaoMinima() {
        return vazaoMinima;
    }

    public void setVazaoMinima(double[] vazaoMinima) {
        this.vazaoMinima = vazaoMinima;
    }

    public double[] getVazaoMaxima() {
        return vazaoMaxima;
    }

    public void setVazaoMaxima(double[] vazaoMaxima) {
        this.vazaoMaxima = vazaoMaxima;
    }

    public double[] getVolumesFinaisSimulacao() {
        return volumesFinaisSimulacao;
    }

    public void setVolumesFinaisSimulacao(double[] volumesFinaisSimulacao) {
        this.volumesFinaisSimulacao = volumesFinaisSimulacao;
    }

    public SimulacaoOperacaoEnergeticaPSO getSimulacaoHidroeletrica() {
        return simulacaoHidroeletrica;
    }

    public void setSimulacaoHidroeletrica(SimulacaoOperacaoEnergeticaPSO simulacaoHidroeletrica) {
        this.simulacaoHidroeletrica = simulacaoHidroeletrica;
    }

    public ParticulaPSO[] getEnxame() {
        return enxame;
    }

    public void setEnxame(ParticulaPSO[] enxame) {
        this.enxame = enxame;
    }

    public int getNumUsinas() {
        return numUsinas;
    }

    public void setNumUsinas(int numUsinas) {
        this.numUsinas = numUsinas;
    }

    public int getNumIntervalos() {
        return numIntervalos;
    }

    public void setNumIntervalos(int numIntervalos) {
        this.numIntervalos = numIntervalos;
    }

    public double getGbest() {
        return gbest;
    }

    public void setGbest(double gbest) {
        this.gbest = gbest;
    }

    public double[][] getVetorGbest() {
        return vetorGbest;
    }

    public void setVetorGbest(double[][] vetorGbest) {
        this.vetorGbest = vetorGbest;
    }
    
}


