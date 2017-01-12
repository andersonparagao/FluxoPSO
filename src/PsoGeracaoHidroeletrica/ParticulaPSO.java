package PsoGeracaoHidroeletrica;

import fluxoemredes.Arco;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import simulacao.SimulacaoOperacaoEnergeticaPSO;

public class ParticulaPSO {

    private double[][] velocidade;
    private double[][] posicao;
    private final double[][] posicaomin;
    private final double[][] posicaomax;
    private final double[][] velocidademax;
    private final double[][] velocidademin;
    private double[][] vetorpbest;
    double pBest;


    public ParticulaPSO(int numUsinas, int numIntervalos, double[] xminvazao, double[] xmaxvazao, double[] xminvolume, double[] xmaxvolume, SimulacaoOperacaoEnergeticaPSO simulacao, int iteracaoFluxo) {
        //a variavel volume vazao é porque as usinas terao como valores os volumes finais e vazaoes defluentes em sequencia
        posicao = new double[numUsinas][numIntervalos * 2];
        velocidade = new double[numUsinas][numIntervalos * 2];
        vetorpbest = new double[numUsinas][numIntervalos * 2];
        posicaomin = new double[numUsinas][numIntervalos * 2];
        posicaomax = new double[numUsinas][numIntervalos * 2];
        velocidademax = new double[numUsinas][numIntervalos * 2];
        velocidademin = new double[numUsinas][numIntervalos * 2];
        pBest = Double.MAX_VALUE;
        //foi separado devido aos limites de volume e vazão serem diferentes
        for (int j = 0; j < numUsinas; j++) {
            for (int k = 0; k < numIntervalos; k++) {
                posicaomin[j][k] = xminvolume[j];
                posicaomax[j][k] = xmaxvolume[j];
            }
        }

        for (int j = 0; j < numUsinas; j++) {
            for (int k = numIntervalos; k < numIntervalos * 2; k++) {
                posicaomin[j][k] = xminvazao[j];
                posicaomax[j][k] = xmaxvazao[j];
            }
        }
        for (int j = 0; j < numUsinas; j++) {
            for (int k = 0; k < numIntervalos; k++) {
                velocidademin[j][k] = (xminvolume[j] - xmaxvolume[j]) / iteracaoFluxo;
                velocidademax[j][k] = (xmaxvolume[j] - xminvolume[j]) / iteracaoFluxo;
            }
        }

        for (int j = 0; j < numUsinas; j++) {
            for (int k = numIntervalos; k < numIntervalos * 2; k++) {
                velocidademin[j][k] = (xminvazao[j] - xmaxvazao[j]) / iteracaoFluxo;
                velocidademax[j][k] = (xmaxvazao[j] - xminvazao[j]) / iteracaoFluxo;
            }
        }
    }

    public double[][] volumeInicial(double[][] volumevazao, int numintervalos, int numUsinas) {
        double[][] volumeinicial = new double[numUsinas][numintervalos];
        //System.out.println("volume inicial");
        for (int i = 0; i < numUsinas; i++) {
            for (int j = 0; j < numintervalos; j++) {
                if (j == 0) {
                    volumeinicial[i][j] = posicaomax[i][j];
                } else {
                    volumeinicial[i][j] = volumevazao[i][j - 1];
                }
            }
        }

        return volumeinicial;
    }

    public void AtualizarVelocidade(SimulacaoOperacaoEnergeticaPSO simulacao, double c1, double c2, double r1, double r2, double[][] gBest, List<Arco> arcosSuperBasicos) {
        int numUsinas = posicaomin.length;
        int numIntervalos = posicao[0].length;
        
        for (int i = 0; i < arcosSuperBasicos.size(); i++) {
            int linhaOrigem = arcosSuperBasicos.get(i).getOrigem()[0];
            int colunaOrigem = arcosSuperBasicos.get(i).getOrigem()[1];
            int linhaDestino = arcosSuperBasicos.get(i).getDestino()[0];
            int colunaDestino = arcosSuperBasicos.get(i).getDestino()[1];
            
            // se o arco superbásico for um arco de volume
            if(linhaOrigem == linhaDestino){
                velocidade[linhaOrigem][colunaOrigem] = velocidade[linhaOrigem][colunaOrigem] + c1 * r1 * (vetorpbest[linhaOrigem][colunaOrigem] - posicao[linhaOrigem][colunaOrigem]) + c2 * r2 * (gBest[linhaOrigem][colunaOrigem] - posicao[linhaOrigem][colunaOrigem]);
            } else {
                velocidade[linhaOrigem][colunaOrigem + numIntervalos] = velocidade[linhaOrigem][colunaOrigem + numIntervalos] + c1 * r1 * (vetorpbest[linhaOrigem][colunaOrigem + numIntervalos] - posicao[linhaOrigem][colunaOrigem + numIntervalos]) + c2 * r2 * (gBest[linhaOrigem][colunaOrigem + numIntervalos] - posicao[linhaOrigem][colunaOrigem + numIntervalos]);
            }
        }
    }

    public void AtualizarPosicao() {
        int numUsinas = posicaomin.length;
        int numIntervalos = posicaomin[0].length;
        for (int i = 0; i < numUsinas; i++) {
            for (int j = 0; j < numIntervalos; j++) {
                posicao[i][j] = posicao[i][j] + velocidade[i][j];
                if (posicao[i][j] > posicaomax[i][j]) {
                    posicao[i][j] = posicaomax[i][j];
                }
                if (posicao[i][j] < posicaomin[i][j]) {
                    posicao[i][j] = posicaomin[i][j];
                }
            }
        }
    }

    public void AvaliarParticula(SimulacaoOperacaoEnergeticaPSO simulacao) {
        int numIntervalos = posicaomin[0].length / 2;
        int numUsinas = posicaomin.length;
        simulacao.definirVolumesFinais(posicao, numUsinas, numIntervalos);
        simulacao.definirVazoesDefluentes(posicao, numUsinas, numIntervalos);
        double fitness = simulacao.simularOperacaoEnergeticaPSO(numIntervalos);
        if (fitness < pBest) {
            pBest = fitness;
            for (int i = 0; i < posicaomin.length; i++) {
                System.arraycopy(posicao[i], 0, vetorpbest[i], 0, numIntervalos * 2);
            }
        }
    }

    public double[][] getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double[][] velocidade) {
        this.velocidade = velocidade;
    }

    public double[][] getPosicao() {
        return posicao;
    }

    public void setPosicao(double[][] posicao) {
        this.posicao = posicao;
    }

    public double getPbest() {
        return pBest;
    }

    public void setPbest(double pbest) {
        this.pBest = pbest;
    }

    public double[][] getVetorPBest() {
        return vetorpbest;
    }

    public void setVetorPBest(double[][] vetorPBest) {
        this.vetorpbest = vetorPBest;
    }
}
