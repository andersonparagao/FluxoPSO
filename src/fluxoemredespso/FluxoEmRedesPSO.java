/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fluxoemredespso;

import PsoGeracaoHidroeletrica.PSO;
import fluxoemredes.Arco;
import fluxoemredes.FluxoEmRede;
import java.util.ArrayList;
import java.util.List;
import simulacao.CarregarSistema;
import simulacao.SimulacaoOperacaoEnergeticaPSO;

/**
 *
 * @author Wellington
 */
public class FluxoEmRedesPSO {

    List<Arco> superBasicos;
    List<Double> direcaoCaminhadaSuperBasicos;
    List<Double> avalicaoGBest = new ArrayList<>();
    List<Double> mediaAvaliacoes = new ArrayList<>();
    double[][] matrizFluxo;

    public static void executaOtimizacao(int[] tipoEPA, int tipoPassoOtimo, int tipoAtualizacaoVelocidade, PSO pso, FluxoEmRede fluxo, int numeroIteracoes) {

        switch (tipoEPA[0]) {
            case 1:
                pso.inicializaParticulas();
                for (int iteracao = 0; iteracao < numeroIteracoes; iteracao++) {
                    System.out.println("== ITERACAO " + iteracao + " ==");
                    pso.AvaliarParticulas();
                    pso.ObterGbest();

                    for (int indiceParticula = 0; indiceParticula < pso.getEnxame().length; indiceParticula++) {
                        List<Arco> superBasicos;
                        List<Double> direcaoCaminhadaSuperBasicos;
                        double[][] matrizFluxo;
                        //EPA-TEC
                        if (iteracao < numeroIteracoes / 2) {
                            superBasicos = fluxo.executaFluxoEmRedeParte1(pso.getEnxame()[indiceParticula]);
                            direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].atualizaVelocidade(indiceParticula, pso.getC1(), pso.getC2(), pso.getgBest().getPosicao(), superBasicos, tipoAtualizacaoVelocidade);
                            matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula], tipoPassoOtimo);
                            pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
                        } else {
                            //EPA-TEU
                            superBasicos = new ArrayList<>();
                            direcaoCaminhadaSuperBasicos = new ArrayList<>();
                            for (int i = 0; i < pso.getNumUsinas(); i++) {
                                pso.getEnxame()[indiceParticula].AvaliarParticula();
                                Arco arco = fluxo.executaFluxoEmRedeParte1EPA_TEU(i, pso.getEnxame()[indiceParticula]);
                                superBasicos.add(arco);
                                direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].atualizaVelocidade(indiceParticula, pso.getC1(), pso.getC2(), pso.getgBest().getPosicao(), superBasicos, tipoAtualizacaoVelocidade);
                                matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula], tipoPassoOtimo);
                                pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
                                superBasicos.clear();
                            }
                        }
                    }
                }
                break;

            case 2:
                System.out.println("EPA-TEC TODOS NO INICIO");
                pso.inicializaParticulas();
                for (int iteracao = 0; iteracao < numeroIteracoes; iteracao++) {
                    System.out.println("== ITERACAO " + iteracao + " ==");
                    pso.AvaliarParticulas();
                    pso.ObterGbest();

                    for (int indiceParticula = 0; indiceParticula < pso.getEnxame().length; indiceParticula++) {
                        List<Arco> superBasicos;
                        List<Double> direcaoCaminhadaSuperBasicos;
                        double[][] matrizFluxo;
                        if (iteracao < tipoEPA[1]) {
                            for (int i = 0; i < pso.getNumIntervalos() - 1; i++) {
                                for (int j = i + 1; j < pso.getNumIntervalos(); j++) {
                                    superBasicos = fluxo.executaFluxoEmRedeParte1EPA_TEC2(pso.getEnxame()[indiceParticula], i, j);
                                    direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].atualizaVelocidade(indiceParticula, pso.getC1(), pso.getC2(), pso.getgBest().getPosicao(), superBasicos, tipoAtualizacaoVelocidade);
                                    matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula], tipoPassoOtimo);
                                    pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
                                    pso.getEnxame()[indiceParticula].AvaliarParticula();
                                    pso.ObterGbest_EPA_TEC_Geral();
                                }
                            }
                        } else {
                            //EPA-TEC
                            if (iteracao < numeroIteracoes / 2) {
                                superBasicos = fluxo.executaFluxoEmRedeParte1(pso.getEnxame()[indiceParticula]);
                                direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].atualizaVelocidade(indiceParticula, pso.getC1(), pso.getC2(), pso.getgBest().getPosicao(), superBasicos, tipoAtualizacaoVelocidade);
                                matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula], tipoPassoOtimo);
                                pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
                            } else {
                                //EPA-TEU
                                superBasicos = new ArrayList<>();
                                direcaoCaminhadaSuperBasicos = new ArrayList<>();
                                for (int i = 0; i < pso.getNumUsinas(); i++) {
                                    pso.getEnxame()[indiceParticula].AvaliarParticula();
                                    Arco arco = fluxo.executaFluxoEmRedeParte1EPA_TEU(i, pso.getEnxame()[indiceParticula]);
                                    superBasicos.add(arco);
                                    direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].atualizaVelocidade(indiceParticula, pso.getC1(), pso.getC2(), pso.getgBest().getPosicao(), superBasicos, tipoAtualizacaoVelocidade);
                                    matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula], tipoPassoOtimo);
                                    pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
                                    superBasicos.clear();
                                }
                            }
                        }
                    }
                }
                break;
                
            case 3:
                System.out.println("EPA-TEC TODOS NO FINAL");
                pso.inicializaParticulas();
                for (int iteracao = 0; iteracao < numeroIteracoes; iteracao++) {
                    System.out.println("== ITERACAO " + iteracao + " ==");
                    pso.AvaliarParticulas();
                    pso.ObterGbest();

                    for (int indiceParticula = 0; indiceParticula < pso.getEnxame().length; indiceParticula++) {
                        List<Arco> superBasicos;
                        List<Double> direcaoCaminhadaSuperBasicos;
                        double[][] matrizFluxo;
                        if (iteracao > numeroIteracoes - tipoEPA[1]) {
                            for (int i = 0; i < pso.getNumIntervalos() - 1; i++) {
                                for (int j = i + 1; j < pso.getNumIntervalos(); j++) {
                                    superBasicos = fluxo.executaFluxoEmRedeParte1EPA_TEC2(pso.getEnxame()[indiceParticula], i, j);
                                    direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].atualizaVelocidade(indiceParticula, pso.getC1(), pso.getC2(), pso.getgBest().getPosicao(), superBasicos, tipoAtualizacaoVelocidade);
                                    matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula], tipoPassoOtimo);
                                    pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
                                    pso.getEnxame()[indiceParticula].AvaliarParticula();
                                    pso.ObterGbest_EPA_TEC_Geral();
                                }
                            }
                        } else {
                            //EPA-TEC
                            if (iteracao < numeroIteracoes / 2) {
                                superBasicos = fluxo.executaFluxoEmRedeParte1(pso.getEnxame()[indiceParticula]);
                                direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].atualizaVelocidade(indiceParticula, pso.getC1(), pso.getC2(), pso.getgBest().getPosicao(), superBasicos, tipoAtualizacaoVelocidade);
                                matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula], tipoPassoOtimo);
                                pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
                                superBasicos.clear();
                            } else {
                                //EPA-TEU
                                superBasicos = new ArrayList<>();
                                direcaoCaminhadaSuperBasicos = new ArrayList<>();
                                for (int i = 0; i < pso.getNumUsinas(); i++) {
                                    pso.getEnxame()[indiceParticula].AvaliarParticula();
                                    Arco arco = fluxo.executaFluxoEmRedeParte1EPA_TEU(i, pso.getEnxame()[indiceParticula]);
                                    superBasicos.add(arco);
                                    direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].atualizaVelocidade(indiceParticula, pso.getC1(), pso.getC2(), pso.getgBest().getPosicao(), superBasicos, tipoAtualizacaoVelocidade);
                                    matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula], tipoPassoOtimo);
                                    pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
                                    superBasicos.clear();
                                }
                            }
                        }
                    }
                }
                break;
                
            case 4:
                System.out.println("EPA-TEC WELLINGTON");
                pso.inicializaParticulas();
                for (int iteracao = 0; iteracao < numeroIteracoes; iteracao++) {
                    System.out.println("== ITERACAO " + iteracao + " ==");
                    pso.AvaliarParticulas();
                    pso.ObterGbest();

                    for (int indiceParticula = 0; indiceParticula < pso.getEnxame().length; indiceParticula++) {
                        List<Arco> superBasicos;
                        List<Double> direcaoCaminhadaSuperBasicos;
                        double[][] matrizFluxo;
                        if (iteracao < tipoEPA[1]) {
                            int[] intervalo = fluxo.InvervaloEsquerdaDireitaEPATEC3(pso.getEnxame()[indiceParticula],6);
//                            System.out.println(" intervalo esquerda: " + intervalo[0]);
//                            System.out.println(" intervalo direita: " + intervalo[1]);
                            for (int i = intervalo[0]; i < intervalo[1]; i++) {
                                for (int j = i + 1; j <= intervalo[1]; j++) { 
                                    superBasicos = fluxo.executaFluxoEmRedeParte1EPA_TEC3(pso.getEnxame()[indiceParticula],i,j);
                                    direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].atualizaVelocidade(indiceParticula, pso.getC1(), pso.getC2(), pso.getgBest().getPosicao(), superBasicos, tipoAtualizacaoVelocidade);
                                    matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula], tipoPassoOtimo);
                                    pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
                                    pso.getEnxame()[indiceParticula].AvaliarParticula();
                                    pso.ObterGbest_EPA_TEC_Geral();
                                }
                            }
                        } else {
                            for(int k = 0; k < pso.getNumIntervalos(); k = k+12){
                                for (int i = k; i < (k+12)-1; i++) {
                                    for (int j = i + 1; j < (k+12); j++) {
                                        superBasicos = fluxo.executaFluxoEmRedeParte1EPA_TEC2(pso.getEnxame()[indiceParticula], i, j);
                                        direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].atualizaVelocidade(indiceParticula, pso.getC1(), pso.getC2(), pso.getgBest().getPosicao(), superBasicos, tipoAtualizacaoVelocidade);
                                        matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula], tipoPassoOtimo);
                                        pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
                                        pso.getEnxame()[indiceParticula].AvaliarParticula();
                                        pso.ObterGbest_EPA_TEC_Geral();
                                    }
                                }
                            }
                        }
                    }
                }
                break;
        }
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        //Definindo o sistema para a simulação/otimização
//        int numeroIntervalos = 24;
//        int numeroUsinas = 3;
//        double demanda = 4500;
//
//        SimulacaoOperacaoEnergeticaPSO simulacao = new SimulacaoOperacaoEnergeticaPSO(numeroIntervalos, demanda);
//        double[] vazaoMinima = {77, 254, 408};
//        double[] vazaoMaxima = {1e20, 1e20, 1e20};
//        double[] volumeMinimo = {4669, 4573, 7000};
//        double[] volumeMaximo = {17190, 17027, 12540};
//
//        CarregarSistema carregar = new CarregarSistema();
//        try {
//            carregar.UsinasMinas(simulacao);
//        } catch (Exception e) {
//            System.out.println("Erro " + e);
//        }
//
//        double[][] rede = new double[numeroUsinas][numeroIntervalos * 2];
//
//        // definindo o Fluxo em Rede 
//        FluxoEmRede fluxo = new FluxoEmRede(numeroUsinas, numeroIntervalos, rede, volumeMinimo, volumeMaximo, vazaoMinima, vazaoMaxima, demanda);
//
//        // definindo o PSO
//        int numeroParticulas = 50;
//        int numeroIteracoes = 50;
//        double c1 = 2;
//        double c2 = 2;
//        PSO pso = new PSO(simulacao, demanda, vazaoMinima, vazaoMaxima, volumeMinimo, volumeMaximo, numeroParticulas, numeroUsinas, numeroIntervalos, c1, c2);
//
//        FluxoEmRedesPSO.executaOtimizacao(new int[]{1, 25}, 1, 1, pso, fluxo, numeroIteracoes);
//
//        
//        // exibindo a melhor Partícula
//        pso.imprimeResultadoFinal("50 part 50 iteracaoes 25 epa3 25 epa4 razao aurea constante de inércia 0.85 3");
//      
//    }
    
    
    public void ExecutaOtimizacaoAlgoritmo(int numeroParticulas, int numeroIteracoes, double c1_c2, int tipoEPA, int qtd1aEPA, int tipoPassoOtimo, int tipoVelocidade, String nomeArquivo) {
        //Definindo o sistema para a simulação/otimização
        int numeroIntervalos = 24;
        int numeroUsinas = 3;
        double demanda = 4500;

        SimulacaoOperacaoEnergeticaPSO simulacao = new SimulacaoOperacaoEnergeticaPSO(numeroIntervalos, demanda);
        double[] vazaoMinima = {77, 254, 408};
        double[] vazaoMaxima = {1e20, 1e20, 1e20};
        double[] volumeMinimo = {4669, 4573, 7000};
        double[] volumeMaximo = {17190, 17027, 12540};

        CarregarSistema carregar = new CarregarSistema();
        try {
            carregar.UsinasMinas(simulacao);
        } catch (Exception e) {
            System.out.println("Erro " + e);
        }

        double[][] rede = new double[numeroUsinas][numeroIntervalos * 2];

        // definindo o Fluxo em Rede 
        FluxoEmRede fluxo = new FluxoEmRede(numeroUsinas, numeroIntervalos, rede, volumeMinimo, volumeMaximo, vazaoMinima, vazaoMaxima, demanda);

        // definindo o PSO
        PSO pso = new PSO(simulacao, demanda, vazaoMinima, vazaoMaxima, volumeMinimo, volumeMaximo, numeroParticulas, numeroUsinas, numeroIntervalos, c1_c2, c1_c2);

        FluxoEmRedesPSO.executaOtimizacao(new int[]{tipoEPA, qtd1aEPA}, tipoPassoOtimo, tipoVelocidade, pso, fluxo, numeroIteracoes);

        
        // exibindo a melhor Partícula
        pso.imprimeResultadoFinal(nomeArquivo);
      
    }
}
