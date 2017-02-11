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
        double[][] rede = new double[numeroUsinas][numeroIntervalos * 2];

        // definindo o Fluxo em Rede 
        FluxoEmRede fluxo = new FluxoEmRede(numeroUsinas, numeroIntervalos, rede, volumeMinimo, volumeMaximo, vazaoMinima, vazaoMaxima, demanda);

        // definindo o PSO
        int numeroParticulas = 150;
        int numeroIteracoes = 100;
        double c1 = 2;
        double c2 = 2;
        PSO pso = new PSO(simulacao, demanda, vazaoMinima, vazaoMaxima, volumeMinimo, volumeMaximo, numeroParticulas, numeroUsinas, numeroIntervalos, c1, c2);

        pso.inicializaParticulas();
        // critério de para
        double soma;
//        for (int iteracao = 0; iteracao < numeroIteracoes; iteracao++) {
//            soma = 0;
//            System.out.println("== ITERACAO " + iteracao + " ==");
//            for (int indiceParticula = 0; indiceParticula < pso.getEnxame().length; indiceParticula++) {
//                pso.getEnxame()[indiceParticula].AvaliarParticula();
//                soma = soma + pso.getEnxame()[indiceParticula].getAvaliacao();
//            }
//            mediaAvaliacoes.add(soma / numeroParticulas);
//            pso.ObterGbest2();
//            avalicaoGBest.add(pso.getgBest().getAvaliacao());
//            for (int indiceParticula = 0; indiceParticula < numeroParticulas; indiceParticula++) {
//                //EPA-TEC
//                if (iteracao < numeroIteracoes/2) {
//                    superBasicos = fluxo.executaFluxoEmRedeParte1EPA_TEC(pso.getEnxame()[indiceParticula]);
//                    direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].AtualizarVelocidadeWellington(indiceParticula, c1, c2, pso.getgBest().getPosicao(), superBasicos);
//                    matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula]);
//                    pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
//                } else {
//                    //EPA-TEU
//                    superBasicos = new ArrayList<>();
//                    direcaoCaminhadaSuperBasicos = new ArrayList<>();
//                    for (int i = 0; i < numeroUsinas; i++) {
//                        pso.getEnxame()[indiceParticula].AvaliarParticula();
//                        Arco arco = fluxo.executaFluxoEmRedeParte1EPA_TEU(i, pso.getEnxame()[indiceParticula]);
//                        superBasicos.add(arco);
//                        direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].AtualizarVelocidadeWellington(indiceParticula, 2, 2, pso.getgBest().getPosicao(), superBasicos);
//                        matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula]);
//                        pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
//                        superBasicos.clear();
//                    }
//                }
//            }
//        }

        for (int iteracao = 0; iteracao < numeroIteracoes; iteracao++) {
            soma = 0;
            System.out.println("== ITERACAO " + iteracao + " ==");
            for (int indiceParticula = 0; indiceParticula < pso.getEnxame().length; indiceParticula++) {
                pso.getEnxame()[indiceParticula].AvaliarParticula();
                soma = soma + pso.getEnxame()[indiceParticula].getAvaliacao();
            }
            mediaAvaliacoes.add(soma / numeroParticulas);
            pso.ObterGbest2();
            avalicaoGBest.add(pso.getgBest().getAvaliacao());
            for (int indiceParticula = 0; indiceParticula < numeroParticulas; indiceParticula++) {
                //EPA-TEC
                if (iteracao < 15) {
                    //System.out.println("EPA-TEC todos");
                        for (int i = 0; i < numeroIntervalos - 1; i++) {
                            for (int j = i + 1; j < numeroIntervalos; j++) {
                                superBasicos = fluxo.executaFluxoEmRedeParte1EPA_TEC_TODOS(pso.getEnxame()[indiceParticula]);
                                direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].AtualizarVelocidade(c1, c2, pso.getgBest().getPosicao(), superBasicos);
                                matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula]);
                                pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
                                pso.getEnxame()[indiceParticula].AvaliarParticula();
                                pso.ObterGbest2();
                            }
                        }
                }
//                } else {
//                    //System.out.println("EPA-TEC normal");
//                    if (iteracao < numeroIteracoes / 2) {
//                        superBasicos = fluxo.executaFluxoEmRedeParte1EPA_TEC(pso.getEnxame()[indiceParticula]);
//                        direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].AtualizarVelocidade(c1, c2, pso.getgBest().getPosicao(), superBasicos);
//                        matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula]);
//                        pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
//                    } else {
//                      //  System.out.println("EPA-TEU");
//                        //EPA-TEU
//                        superBasicos = new ArrayList<>();
//                        direcaoCaminhadaSuperBasicos = new ArrayList<>();
//                        for (int i = 0; i < numeroUsinas; i++) {
//                            pso.getEnxame()[indiceParticula].AvaliarParticula();
//                            Arco arco = fluxo.executaFluxoEmRedeParte1EPA_TEU(i, pso.getEnxame()[indiceParticula]);
//                            superBasicos.add(arco);
//                            direcaoCaminhadaSuperBasicos = pso.getEnxame()[indiceParticula].AtualizarVelocidade(c1, c2, pso.getgBest().getPosicao(), superBasicos);
//                            matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, pso.getEnxame()[indiceParticula]);
//                            pso.getEnxame()[indiceParticula].AtualizarPosicao(matrizFluxo);
//                            superBasicos.clear();
//                        }
//                    }
//                }
            }
        }

        // exibindo a melhor Partícula
        System.out.println("Avaliação GBest = " + pso.getgBest().getAvaliacao());
        pso.getgBest().imprimePosicaoFinal(volumeMaximo, volumeMinimo);
        pso.getgBest().AvaliarParticula2();
        System.out.println("Avaliação da Melhor Partícula = " + pso.getgBest().getAvaliacao());
        pso.getgBest().imprimeVolumes();
        //pso.getgBest().imprimePosicaoAG();

        System.out.println("Avalições das Melhores Partículas em cada Iteração");
        for (int i = 0; i < avalicaoGBest.size(); i++) {
            System.out.println(String.format("%.10f", avalicaoGBest.get(i)));
        }

        System.out.println("Média de Avaliação das Partículas em cada Iteração");
        for (int i = 0; i < avalicaoGBest.size(); i++) {
            System.out.println(String.format("%.10f", mediaAvaliacoes.get(i)));
        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        ParticulaPSO particula = new ParticulaPSO(numeroUsinas, numeroIntervalos, vazaoMinima, vazaoMaxima, volumeMinimo, volumeMaximo, simulacao);
//        particula.inicializaParticula();
//
//        for (int repetir = 0; repetir < 6; repetir++) {
//            System.out.println("INICIO");
//            particula.AvaliarParticula2();
//            List<Arco> arcosSuperBasicos = fluxo.executaFluxoEmRedeParte1EPA_TEC(particula);
//            particula.imprimeVelocidade();
//
//            System.out.println("Arcos SuperBásicos");
//            for (int i = 0; i < arcosSuperBasicos.size(); i++) {
//                if (i == 0) {
//                    System.out.print("[" + arcosSuperBasicos.get(i).toString() + "], [");
//                } else if (i != arcosSuperBasicos.size() - 1) {
//                    System.out.print(arcosSuperBasicos.get(i).toString() + "], [");
//                } else {
//                    System.out.print(arcosSuperBasicos.get(i).toString() + "]\n");
//                }
//            }
//
//            // partícula final de uma execução
//            double[][] gBest = {{17189.931352702086, 16723.65672725966, 16687.64803870616, 15669.13220043174, 15537.648274847412, 14215.882435978123, 12561.068955081506, 11867.29332479272, 13848.332760207197, 15577.393275930335, 17190.0, 17190.0},
//            {17009.7148170247, 16560.53022351509, 14724.279315800995, 13812.58530155172, 10724.18326624599, 9059.230030385315, 8712.51068832928, 13061.858205344111, 12302.76704922158, 13804.53834350605, 15206.790679704794, 17027.0},
//            {12079.973431732906, 12539.991909957309, 9562.492083195768, 8753.064949141117, 7000.188712096692, 8112.277329955309, 11869.982604245211, 7397.061030203891, 11452.088974388682, 11452.255342856673, 10802.797111921245, 12540.0}};
//
//            direcaoCaminhadaSuperBasicos = particula.AtualizarVelocidade(2, 2, gBest, arcosSuperBasicos);
//            System.out.println("\nDireção de Caminhada dos SuperBásicos");
//            for (int i = 0; i < direcaoCaminhadaSuperBasicos.size(); i++) {
//                if (i == 0) {
//                    System.out.print("[" + direcaoCaminhadaSuperBasicos.get(i) + ", ");
//                } else if (i != arcosSuperBasicos.size() - 1) {
//                    System.out.print(direcaoCaminhadaSuperBasicos.get(i) + ", ");
//                } else {
//                    System.out.print(direcaoCaminhadaSuperBasicos.get(i) + "]\n");
//                }
//
//            }
//            System.out.println();
//
//            matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, particula);
//            particula.AtualizarPosicao(matrizFluxo);
//            particula.AvaliarParticula2();
//            particula.imprimeVolumes();
//            System.out.println("==================================================\n\n");
//        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//        //EPA TEU FUNCIONANDO PERFEITAMENTE FUNCIONANDO FUNCIONANDO FUNCIONANDO
//        ParticulaPSO particula = new ParticulaPSO(numeroUsinas, numeroIntervalos, vazaoMinima, vazaoMaxima, volumeMinimo, volumeMaximo, simulacao);
//        particula.inicializaParticula();
//
//        double[][] gBest = {{17189.931352702086, 16723.65672725966, 16687.64803870616, 15669.13220043174, 15537.648274847412, 14215.882435978123, 12561.068955081506, 11867.29332479272, 13848.332760207197, 15577.393275930335, 17190.0, 17190.0, 399.0261214615001, 483.4256547178992, 258.7019362223, 583.5631045786007, 223.03193512229984, 708.9550397432005, 985.6854948152001, 919.9937710492001, 140.17981887750003, 246.06220839550016, 249.37491501679975, 623.0},
//                            {17009.7148170247, 16560.53022351509, 14724.279315800995, 13812.58530155172, 10724.18326624599, 9059.230030385315, 8712.51068832928, 13061.858205344111, 12302.76704922158, 13804.53834350605, 15206.790679704794, 17027.0, 1337.6034361589998, 1372.3482569006967, 1529.4275478849995, 1389.4786347600007, 1809.2229692435003, 1840.498886758003, 1908.6182733331011, 579.9909110263013, 2262.0272908413, 1620.6119450657004, 1617.7933550198995, 1350.3784930124998},
//                            {12079.973431732906, 12539.991909957309, 9562.492083195768, 8753.064949141117, 7000.188712096692, 8112.277329955309, 11869.982604245211, 7397.061030203891, 11452.088974388682, 11452.255342856673, 10802.797111921245, 12540.0, 2265.6515976398005, 1796.3031737445003, 3154.4183493261994, 2109.4798270871015, 2866.2230585022994, 1861.3297021690967, 1097.7456424489983, 3262.015864171304, 2047.0181798575, 3023.548637342998, 3282.923580464499, 1802.3423872493006}};
//
//        for (int h = 0; h < 40; h++) {
//            System.out.println("============ ITERACAO " + h + "================");
//            superBasicos = new ArrayList<>();
//            direcaoCaminhadaSuperBasicos = new ArrayList<>();
//            for (int i = 0; i < numeroUsinas; i++) {
//                particula.AvaliarParticula2();
//                Arco arco = fluxo.executaFluxoEmRedeParte1EPA_TEU(i, particula);
//                superBasicos.add(arco);
//                direcaoCaminhadaSuperBasicos = particula.AtualizarVelocidade(2, 2, gBest, superBasicos);
//                matrizFluxo = fluxo.executaFluxoEmRedeParte2(direcaoCaminhadaSuperBasicos, particula);
//                particula.AtualizarPosicao(matrizFluxo);
//                superBasicos.clear();
//            }
//        }
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
