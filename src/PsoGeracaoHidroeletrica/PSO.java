package PsoGeracaoHidroeletrica;

import fluxoemredes.Arco;
import java.util.ArrayList;
import java.util.List;

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
    private ParticulaPSO gBest;
    
    private List<Double> mediaAvaliacoes = new ArrayList<>();
    private List<Double> avaliacoesGBest = new ArrayList<>();

    public PSO(SimulacaoOperacaoEnergeticaPSO simulacaoHidroeletrica, double demanda, double[] vazaoMinima, double[] vazaoMaxima, double[] volumeMinimo, double[] volumeMaximo, int numeroParticulas, int numUsinas, int numIntervalos, double c1, double c2) {
        this.numUsinas = numUsinas;
        this.numIntervalos = numIntervalos;
        this.volumeMinimo = volumeMinimo;
        this.volumeMaximo = volumeMaximo;
        this.vazaoMinima = vazaoMinima;
        this.vazaoMaxima = vazaoMaxima;
        this.simulacaoHidroeletrica = simulacaoHidroeletrica;
        this.enxame = new ParticulaPSO[numeroParticulas];
        this.c1 = c1;
        this.c2 = c2;
        gBest = new ParticulaPSO(numUsinas, numIntervalos, vazaoMinima, vazaoMaxima, volumeMinimo, vazaoMaxima, simulacaoHidroeletrica);
        gBest.setAvaliacao(Double.MAX_VALUE);
    }

    public void AvaliarParticulas() {
        double soma = 0;
        for (int i = 0; i < enxame.length; i++) {
            enxame[i].AvaliarParticula();
            soma += enxame[i].getAvaliacao();
        }
        mediaAvaliacoes.add(soma/enxame.length);
    }
    

    public List atualizaVelocidade(int indiceParticula, List<Arco> arcosSuperBasicos, int tipoAtualizacaoVelocidade) {
        List<Double> direcaoCaminhadaArcosSuperBasicos = new ArrayList<>();

        direcaoCaminhadaArcosSuperBasicos = enxame[indiceParticula].atualizaVelocidade(indiceParticula, c1, c2, gBest.getPosicao(), arcosSuperBasicos, tipoAtualizacaoVelocidade);

        return direcaoCaminhadaArcosSuperBasicos;
    }

    public void inicializaParticulas() {
        for (int i = 0; i < enxame.length; i++) {
            ParticulaPSO particula = new ParticulaPSO(numUsinas, numIntervalos, vazaoMinima, vazaoMaxima, volumeMinimo, volumeMaximo, simulacaoHidroeletrica);
            particula.inicializaParticula();
            enxame[i] = particula;
        }
    }

    public void avaliaParticulas() {
        for (int indiceParticula = 0; indiceParticula < enxame.length; indiceParticula++) {
            enxame[indiceParticula].AvaliarParticula();
            System.out.println("Avaliação da Partícula " + indiceParticula + " = " + enxame[indiceParticula].getAvaliacao());
        }
    }



    public void ObterGbest() {
        int gbestPosicao = 0;
        for (int i = 1; i < enxame.length; i++) {
            if (enxame[i].getAvaliacao() < enxame[gbestPosicao].getAvaliacao()) {
                gbestPosicao = i;
            }
        }

        if (enxame[gbestPosicao].getAvaliacao() < gBest.getAvaliacao()) {
            ParticulaPSO novoGBest = new ParticulaPSO(numUsinas, numIntervalos, vazaoMinima, vazaoMaxima, volumeMinimo, vazaoMaxima, simulacaoHidroeletrica);
            novoGBest.setPosicao(enxame[gbestPosicao].getPosicao());
            novoGBest.setVelocidade(enxame[gbestPosicao].getVelocidade());
            novoGBest.setAvaliacao(enxame[gbestPosicao].getAvaliacao());
            gBest = novoGBest;
        }
        
        avaliacoesGBest.add(gBest.getAvaliacao());
    }
    
    public void ObterGbest_EPA_TEC2() {
        int gbestPosicao = 0;
        for (int i = 1; i < enxame.length; i++) {
            if (enxame[i].getAvaliacao() < enxame[gbestPosicao].getAvaliacao()) {
                gbestPosicao = i;
            }
        }

        if (enxame[gbestPosicao].getAvaliacao() < gBest.getAvaliacao()) {
            ParticulaPSO novoGBest = new ParticulaPSO(numUsinas, numIntervalos, vazaoMinima, vazaoMaxima, volumeMinimo, vazaoMaxima, simulacaoHidroeletrica);
            novoGBest.setPosicao(enxame[gbestPosicao].getPosicao());
            novoGBest.setVelocidade(enxame[gbestPosicao].getVelocidade());
            novoGBest.setAvaliacao(enxame[gbestPosicao].getAvaliacao());
            gBest = novoGBest;
        }
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

    public ParticulaPSO getgBest() {
        return gBest;
    }

    public void setgBest(ParticulaPSO gBest) {
        this.gBest = gBest;
    }

    public List<Double> getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(List<Double> mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public List<Double> getAvaliacoesGBest() {
        return avaliacoesGBest;
    }

    public void setAvaliacoesGBest(List<Double> avaliacoesGBest) {
        this.avaliacoesGBest = avaliacoesGBest;
    }
    
    public void imprimeMediaAvaliacoes(){
        System.out.println("Média das Avaliações:");
        for (int i = 0; i < mediaAvaliacoes.size(); i++) {
            System.out.println(String.format("%.10f", mediaAvaliacoes.get(i)));
        }
        System.out.println();
    }
    
    public void imprimeGBest(){
        System.out.println("GBest em cada Iteração:");
        for (int i = 0; i < avaliacoesGBest.size(); i++) {
            System.out.println(String.format("%.10f", avaliacoesGBest.get(i)));
        }
        System.out.println();
    }
}
