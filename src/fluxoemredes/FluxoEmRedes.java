/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fluxoemredes;

import PsoGeracaoHidroeletrica.ParticulaPSO;

/**
 *
 * @author Wellington
 */
public class FluxoEmRedes {

    public Ciclo procurarCiclo(int[][] MVB, Arco superbasico) {
        Ciclo ciclo = null;
        return ciclo;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numUsinas = 3;
        int numIntervalos = 12;

        // volumes com mil hm3 a menos e vazões defluentes normais
        double[][] rede1 = {{16190, 16190, 16190, 16190, 16190, 16190, 16190, 16190, 16190, 16190, 16190, 16190, 399, 306, 245, 196, 173, 206, 356, 656, 894, 904, 863, 623},
                            {16027, 16027, 16027, 16027, 16027, 16027, 16027, 16027, 16027, 16027, 16027, 16027, 1331, 1024, 817, 655, 584, 704, 1147, 1971, 2727, 2850, 2765, 2043},
                            {11540, 11540, 11540, 11540, 11540, 11540, 11540, 11540, 11540, 11540, 11540, 11540, 2084, 1623, 1309, 1067, 974, 1148, 1766, 2951, 4055, 4253, 4183, 3156}};

        // volumes máximos e vazões defluentes normais
        double[][] rede2 = {{17190, 17190, 17190, 17190, 17190, 17190, 17190, 17190, 17190, 17190, 17190, 17190, 399, 306, 245, 196, 173, 206, 356, 656, 894, 904, 863, 623},
                            {17027, 17027, 17027, 17027, 17027, 17027, 17027, 17027, 17027, 17027, 17027, 17027, 1331, 1024, 817, 655, 584, 704, 1147, 1971, 2727, 2850, 2765, 2043},
                            {12540, 12540, 12540, 12540, 12540, 12540, 12540, 12540, 12540, 12540, 12540, 12540, 2084, 1623, 1309, 1067, 974, 1148, 1766, 2951, 4055, 4253, 4183, 3156}};

        // volumes com variações e vazões defluentes normais
        double[][] rede3 = {{17190, 16190, 16000, 15290, 14150, 14440, 14000, 14900, 15900, 16500, 17000, 17190, 399.0000000000, 686.5175038052, 317.2983257230, 466.1674277017, 606.7899543379, 95.6499238965, 523.4277016743, 313.5342465753, 513.4824961948, 675.6894977169, 672.7412480974, 550.7016742770},
                            {17027, 16900, 15027, 14027, 13527, 13000, 13590, 14227, 15190, 15527, 16227, 17027, 932.0000000000, 1072.3257229833, 1529.7092846271, 1035.5175038052, 774.2587519026, 904.5327245053, 922.4946727549, 1728.6103500761, 2360.5616438356, 2721.7656012177, 2498.6377473364, 1738.5859969559},
                            {12540, 12000, 11900, 11200, 10700, 10000, 10500, 11000, 11300, 11800, 12000, 12540, 753.0000000000, 1828.4794520548, 1347.0517503805, 1333.3622526636, 1164.2587519026, 1414.3622526636, 1575.7412480974, 2760.7412480974, 3940.8447488585, 4062.7412480974, 4106.8964992390, 2950.5205479452}};


        double[] volumeMin = new double[numUsinas];
        double[] volumeMax = new double[numUsinas];
        double[] vazaoMin = new double[numUsinas];
        double[] vazaoMax = new double[numUsinas];
        double demanda = 4500;

        FluxoEmRede fluxoEmRedes = new FluxoEmRede(numUsinas, numIntervalos, rede2, volumeMin, volumeMax, vazaoMin, vazaoMax, demanda);
        for (int i = 0; i < 100; i++) {
            if (i < 50) {
                System.out.println("======================== FPH ========================");
                fluxoEmRedes.ParticaoEPAFPH();
                fluxoEmRedes.imprimeMIBV();
                fluxoEmRedes.imprimeArcosSuperBasicos();
                fluxoEmRedes.imprimeArcosBasicos();
                System.out.println("====================== EPA-TEC ======================"); //53
                fluxoEmRedes.ParticaoEPATEC();
                System.out.println("============== IDENTIFICAÇÃO DE CICLOS ===============");
                fluxoEmRedes.detectarCiclos();
                System.out.println("quantidade de ciclos = " + fluxoEmRedes.getCiclos().size());
                fluxoEmRedes.imprimeCiclos();
                System.out.println("========= DIREÇÃO DE CAMINHADA SUPER BÁSICOS ==========");
                fluxoEmRedes.DirecaoDeCaminhadaArcosSuperBasicos();
                fluxoEmRedes.imprimeDirecaoCaminhadaSuperBasicos();
                System.out.println("========= PROJEÇÃ0 DE CAMINHADA SUPER BÁSICOS =========");
                fluxoEmRedes.ProjecaoDeCaminhadaArcosSuperBasicos();
                fluxoEmRedes.imprimeDirecaoCaminhadaSuperBasicos();
                System.out.println("============= MIVB ================");
                fluxoEmRedes.imprimeMIBV();
                System.out.println("======= DIREÇÃO DE CAMINHADA DOS ARCOS BÁSICOS ========");
                fluxoEmRedes.DirecaoDeCaminhadaArcosBasicos();
                fluxoEmRedes.imprimeDirecaoCaminhadaArcosBasicos();
                System.out.println("================= PASSO MÁXIMO =====================");
                fluxoEmRedes.PassoMaximoDoCiclo();
                fluxoEmRedes.imprimeMatrizPassosMaximos();
                System.out.println(fluxoEmRedes.passoMaximo);
                System.out.println("================== PARTÍCULA ===================");
                fluxoEmRedes.imprimeParticula();
                System.out.println("=========== ATUALIZAÇÃO DA REDE ================");
                fluxoEmRedes.AtualizarRede();
                System.out.println("================== PARTÍCULA ===================");
                fluxoEmRedes.imprimeParticula();
                System.out.println("******************************************************");
            } else {
                System.out.println("i : " + (i + 1));
                for (int j = 0; j < fluxoEmRedes.numUsinas; j++) {
                    System.out.println("USINA " + j);
                    System.out.println("======================== FPH ========================");
                    fluxoEmRedes.ParticaoEPAFPH();
                    System.out.println("====================== EPA-TEU ======================");
                    fluxoEmRedes.ParticaoEPATEU(j);
                    fluxoEmRedes.imprimeMIBV();
                    fluxoEmRedes.imprimeArcosSuperBasicos();
                    fluxoEmRedes.imprimeArcosBasicos();
                    System.out.println("============== IDENTIFICAÇÃO DE CICLOS ===============");
                    fluxoEmRedes.detectarCiclos();
                    System.out.println("quantidade de ciclos = " + fluxoEmRedes.getCiclos().size());
                    fluxoEmRedes.imprimeCiclos();
                    System.out.println("========= DIREÇÃO DE CAMINHADA SUPER BÁSICOS ==========");
                    fluxoEmRedes.DirecaoDeCaminhadaArcosSuperBasicos();
                    fluxoEmRedes.imprimeDirecaoCaminhadaSuperBasicos();
                    System.out.println("========= PROJEÇÃ0 DE CAMINHADA SUPER BÁSICOS =========");
                    fluxoEmRedes.ProjecaoDeCaminhadaArcosSuperBasicos();
                    fluxoEmRedes.imprimeDirecaoCaminhadaSuperBasicos();
                    System.out.println("============= MIVB ================");
                    fluxoEmRedes.imprimeMIBV();
                    System.out.println("======= DIREÇÃO DE CAMINHADA DOS ARCOS BÁSICOS ========");
                    fluxoEmRedes.DirecaoDeCaminhadaArcosBasicos();
                    fluxoEmRedes.imprimeDirecaoCaminhadaArcosBasicos();
                    System.out.println("================= PASSO MÁXIMO =====================");
                    fluxoEmRedes.PassoMaximoDoCiclo();
                    fluxoEmRedes.imprimeMatrizPassosMaximos();
                    System.out.println(fluxoEmRedes.passoMaximo);
                    System.out.println("================== PARTÍCULA ===================");
                    fluxoEmRedes.imprimeParticula();
                    System.out.println("=========== ATUALIZAÇÃO DA REDE ================");
                    fluxoEmRedes.AtualizarRede();
                    System.out.println("================== PARTÍCULA ===================");
                    fluxoEmRedes.imprimeParticula();
                    System.out.println("******************************************************");
                }

                System.out.println();
            }
        }
    }

}
