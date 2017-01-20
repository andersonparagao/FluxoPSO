/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fluxoemredespso;

import PsoGeracaoHidroeletrica.PSO;
import PsoGeracaoHidroeletrica.ParticulaPSO;
import fluxoemredes.Arco;
import fluxoemredes.FluxoEmRede;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import simulacao.CarregarSistema;
import simulacao.SimulacaoOperacaoEnergeticaPSO;

/**
 *
 * @author Wellington
 */
public class FluxoEmRedesPSO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
        
        //Definindo o sistema para a simulação/otimização
        int numeroIntervalos = 12;
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
        
        List<Arco> superBasicos;
        List<Double> direcaoCaminhadaSuperBasicos;
        List<Double> avalicaoGBest = new ArrayList<>();
        List<Double> mediaAvaliacoes = new ArrayList<>();
        double[][] matrizFluxo;
        double[][] rede = new double[numeroUsinas][numeroIntervalos*2];
        
        // definindo o Fluxo em Rede 
        FluxoEmRede fluxo = new FluxoEmRede(numeroUsinas, numeroIntervalos, rede, volumeMinimo, volumeMaximo, vazaoMinima, vazaoMaxima, demanda);

        // definindo o PSO
        int numeroParticulas = 50;
        int numeroIteracoes = 1000;
        double c1 = 2;
        double c2 = 2;
        PSO pso = new PSO(simulacao, demanda, vazaoMinima, vazaoMaxima, volumeMinimo, volumeMaximo, numeroParticulas, numeroUsinas, numeroIntervalos, c1, c2);
        
        pso.inicializaParticulas();       
        // critério de para
        double soma;
        for (int iteracao = 0; iteracao < numeroIteracoes; iteracao++) {
            soma = 0;
            System.out.println("== ITERACAO " + iteracao + " ==");
            for (int indiceParticula = 0; indiceParticula < pso.getEnxame().length; indiceParticula++) {
                pso.getEnxame()[indiceParticula].AvaliarParticula();
                soma = soma + pso.getEnxame()[indiceParticula].getAvaliacao();
            }
            mediaAvaliacoes.add(soma/numeroParticulas);
            pso.ObterGbest();
            avalicaoGBest.add(pso.getgBest().getAvaliacao());
            for (int indiceParticula = 0; indiceParticula < numeroParticulas; indiceParticula++) {
                //EPA-TEC
                if(iteracao < numeroIteracoes/2){
                    superBasicos = fluxo.executaFluxoEmRedeParte1EPA_TEC(pso.getEnxame()[indiceParticula]);
                    direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].AtualizarVelocidade(c1, c2, pso.getgBest().getPosicao(), superBasicos);
                    matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula]);
                    pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
                } else {
                    //EPA-TEU
                    for (int i = 0; i < numeroUsinas; i++) {
                        superBasicos = fluxo.executaFluxoEmRedeParte1EPA_TEU(i, pso.getEnxame()[indiceParticula]);
                        direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].AtualizarVelocidade(c1, c2, pso.getgBest().getPosicao(), superBasicos);
                        matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula]);
                        pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
                    }
                }
            }
        }
        // exibindo a melhor Partícula
        System.out.println("Avaliação GBest = " + pso.getgBest().getAvaliacao());
        pso.getgBest().imprimePosicaoFinal();
        pso.getgBest().AvaliarParticula2();
        System.out.println("Avaliação da Melhor Partícula = " + pso.getgBest().getAvaliacao());
        pso.getgBest().imprimePosicaoFinal();
        pso.getgBest().imprimePosicaoAG();
        
        
        System.out.println("Avalições das Melhores Partículas em cada Iteração");
        for (int i = 0; i < avalicaoGBest.size(); i++) {
            System.out.println(String.format("%.10f", avalicaoGBest.get(i)));
        }
        
        System.out.println("Média de Avaliação das Partículas em cada Iteração");
        for (int i = 0; i < avalicaoGBest.size(); i++) {
            System.out.println(String.format("%.10f", mediaAvaliacoes.get(i)));
            
        }
        
        
        

//        ParticulaPSO particula = new ParticulaPSO(numeroUsinas, numeroIntervalos, vazaoMinima, vazaoMaxima, volumeMinimo, volumeMaximo, simulacao);
//        particula.inicializaParticula();
//        System.out.println("============== imprimindo posição ====================");
//        particula.imprimePosicao();
//        particula.AvaliarParticula();
//        Random r = new Random();
//        double r1 = r.nextDouble();
//        double r2 = r.nextDouble();
//        System.out.println("============ velocidade ===============");
//        particula.imprimeVelocidade();
//        
//        double[][] gBest = {{17190, 16190, 16000, 15290, 14150, 14440, 14000, 14900, 15900, 16500, 17000, 16000, 399.0000000000, 686.5175038052, 317.2983257230, 466.1674277017, 606.7899543379, 95.6499238965, 523.4277016743, 313.5342465753, 513.4824961948, 675.6894977169, 672.7412480974, 550.7016742770},
//                            {17027, 16900, 15027, 14027, 13527, 13000, 13590, 14227, 15190, 15527, 16227, 16000, 932.0000000000, 1072.3257229833, 1529.7092846271, 1035.5175038052, 774.2587519026, 904.5327245053, 922.4946727549, 1728.6103500761, 2360.5616438356, 2721.7656012177, 2498.6377473364, 1738.5859969559},
//                            {12540, 12000, 11900, 11200, 10700, 10000, 10500, 11000, 11300, 11800, 12000, 11000, 753.0000000000, 1828.4794520548, 1347.0517503805, 1333.3622526636, 1164.2587519026, 1414.3622526636, 1575.7412480974, 2760.7412480974, 3940.8447488585, 4062.7412480974, 4106.8964992390, 2950.5205479452}};
//             
//        List<Arco> arcosSuperBasico = new ArrayList<>();
//        arcosSuperBasico.add(new Arco(0, 1, 1, 1));
//        arcosSuperBasico.add(new Arco(2, 1, 3, 1));
//        
//        
//        List<Double> direcaoCaminhada = particula.atualizaVelocidade(0.2, 0.2, r1, r2, gBest, arcosSuperBasico);
//        System.out.println("============= nova velocidade ==============");
//        particula.imprimeVelocidade();
//        
//        for (int i = 0; i < direcaoCaminhada.size(); i++) {
//            System.out.println(direcaoCaminhada.get(i));
//            
//        }
    }
}
