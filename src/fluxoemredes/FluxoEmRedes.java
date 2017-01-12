/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fluxoemredes;

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

        int[][] velocidade = new int[numUsinas][numIntervalos * 2];
        double[] volumeMin = new double[numUsinas];
        double[] volumeMax = new double[numUsinas];
        double[] vazaoMin = new double[numUsinas];
        double[] vazaoMax = new double[numUsinas];
        double demanda = 4500;

        FluxoEmRede fluxoEmRedes = new FluxoEmRede(numUsinas, numIntervalos, rede2, volumeMin, volumeMax, vazaoMin, vazaoMax, demanda);
        for (int i = 0; i < 1000; i++) {
            if (i < 500) {
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

//        MVB[0][0] = 1;
//        MVB[0][1] = 0;
//        MVB[0][2] = 0;
//        MVB[0][3] = 1;
//        MVB[1][0] = 1;     
//        MVB[1][1] = 0;      
//        MVB[1][2] = 0;
//        MVB[1][3] = 1;
//        MVB[2][0] = 1;       
//        MVB[2][1] = 0;        
//        MVB[2][2] = 0;
//        MVB[2][3] = 1;
//        MVB[3][0] = 1;       
//        MVB[3][1] = 0;        
//        MVB[3][2] = 0;
//        MVB[3][3] = 1;
//        ArrayList<Arco> superbasicos = new ArrayList();
//        Arco superbasico1 = new Arco(0,1,1,1);
//        Arco superbasico2 = new Arco(1,1,2,1);
//        Arco superbasico3 = new Arco(2,1,3,1);
//        Arco superbasico4 = new Arco(3,1,4,1);
//        
//        superbasicos.add(superbasico1);
//        superbasicos.add(superbasico2);
//        superbasicos.add(superbasico3);
//        superbasicos.add(superbasico4);
//        ArrayList<Ciclo> ciclo=new ArrayList();
//        ArrayList<Arco> arcosBasicos = new ArrayList();
//        for(int i=0;i<superbasicos.size();i++){
//            Ciclo ciclo1 = new Ciclo(superbasicos.get(i));
//            ciclo.add(ciclo1);
//            if(i==3){
//                System.out.println("");
//            }
////            ciclo.get(i).trilhaDestino(superbasico3, MVB, rede, numIntervalos);
////            ciclo.get(i).trilhaDestino(superbasicos.get(i), MVB,rede,numIntervalos,arcosBasicos);
////            ciclo.get(i).trilhaOrigem(superbasicos.get(i), MVB,rede,numIntervalos,arcosBasicos);
//            ciclo.get(i).trilhaDestinoOrigem(MVB, rede);
//        }
//        for (Ciclo ciclos : ciclo) {
//            System.out.println("Arco Superbasico");
//            System.out.println(" origem usina : " + ciclos.getSuperbasico().getOrigem()[0]);
//            System.out.println(" origem intervalo :" + ciclos.getSuperbasico().getOrigem()[1]);
//            System.out.println(" destino usina : " + ciclos.getSuperbasico().getDestino()[0]);
//            System.out.println(" destino intervalo :" + ciclos.getSuperbasico().getDestino()[1]);
//            
//            System.out.println("\n\nArcos Basico\n\n");
//            for(int i=0;i<ciclos.getArestasbasicas().size();i++){
//                if(!(ciclos.isRepetido(arcosBasicos, ciclos.getArestasbasicas().get(i)))){
//                    arcosBasicos.add(ciclos.getArestasbasicas().get(i));
//                }else{
//                    System.out.println("\n\n\n\nREPETIDO\n\n\n");
//                }
//                System.out.println(" origem usina : " + ciclos.getArestasbasicas().get(i).getOrigem()[0]);
//                System.out.println(" origem intervalo :" + ciclos.getArestasbasicas().get(i).getOrigem()[1]);
//                System.out.println(" destino usina : " + ciclos.getArestasbasicas().get(i).getDestino()[0]);
//                System.out.println(" destino intervalo :" + ciclos.getArestasbasicas().get(i).getDestino()[1]);
//                System.out.println("\n\n");
//            }
//        }
//        System.out.println("");
//        //calcular variaveis basicas
//        int aux=0;
//        for(int i=0;i<MVB.length;i++){
//            for(int j=0;j<MVB[0].length;j++){
//                if(MVB[i][j]==0){
//                    Arco arco = new Arco(i,j,i,(j+1));
//                    arcosBasicos.add(arco);
//                    for(int k=0;k<ciclo.size();k++){
//                        for(int l=0;l<ciclo.get(k).getArestasbasicas().size();l++){
//                            Arco arco2 = ciclo.get(k).getArestasbasicas().get(l);
//                            if((arco2.getOrigem()[0]==arco.getOrigem()[0])&&(arco2.getOrigem()[1]==arco.getOrigem()[1])&&(arco2.getDestino()[0]==
//                                    arco.getDestino()[0])&&(arco2.getDestino()[1]==arco.getDestino()[1])){
//                                System.out.println("\n\nENTROU\n\n");
//                            
//                            arcosBasicos.get(aux).getSuperbasico().add(ciclo.get(k).getSuperbasico());
//                            }
//                        }
//                    }
//                }else{
//                    Arco arco = new Arco(i,j,(i+1),j);
//                    arcosBasicos.add(arco);
//                    for(int k=0;k<ciclo.size();k++){
//                        for(int l=0;l<ciclo.get(k).getArestasbasicas().size();l++){
//                            Arco arco2 = ciclo.get(k).getArestasbasicas().get(l);
//                            if((arco2.getOrigem()[0]==arco.getOrigem()[0])&&(arco2.getOrigem()[1]==arco.getOrigem()[1])&&(arco2.getDestino()[0]==
//                                    arco.getDestino()[0])&&(arco2.getDestino()[1]==arco.getDestino()[1])){
//                                System.out.println("\n\nENTROU\n\n");
//                            
//                            arcosBasicos.get(aux).getSuperbasico().add(ciclo.get(k).getSuperbasico());
//                            }
//                        }
//                       
//                    }
//                }
//             aux++;   
//            }
//        }
//            
//           for(int i=0;i<arcosBasicos.size();i++){
//            System.out.println("Aresta basicas");
//                System.out.print(" origem usina : " + arcosBasicos.get(i).getOrigem()[0]);
//                System.out.println(" origem intervalo : "+ arcosBasicos.get(i).getOrigem()[1]);
//                System.out.print("destino usina : " + arcosBasicos.get(i).getDestino()[0]);
//                System.out.println(" destino intervalo : "+ arcosBasicos.get(i).getDestino()[1]);
//                System.out.println(" Arcos superbasicos desse arco basico");
//                for(int j=0;j<arcosBasicos.get(i).getSuperbasico().size();j++){
//                    System.out.println("origem usina arco super basico :" +arcosBasicos.get(i).getSuperbasico().get(j).getOrigem()[0]);
//                    System.out.println("origem intervalo arco super basico :" +arcosBasicos.get(i).getSuperbasico().get(j).getOrigem()[1]);
//                    System.out.println("destino usina arco super basico :" +arcosBasicos.get(i).getSuperbasico().get(j).getDestino()[0]);
//                    System.out.println("destino intervalo arco super basico :" +arcosBasicos.get(i).getSuperbasico().get(j).getDestino()[1]);
//
//                }
//            
//            
//        }
//        for(int i=0;i<numUsinas;i++){
//            for(int j=0;j<numIntervalos;j++){
//                MVB[i][j] = 1;
//            }
//        }
        // TODO code application logic here
    }

}
