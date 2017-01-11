/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fluxoemredes;

import horizonte.IntervaloDeHorizonte;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import simulacao.CarregarSistema;
import simulacao.SimulacaoOperacaoEnergeticaPSO;

/**
 *
 * @author Wellington
 */
public class FluxoEmRede {

    int numUsinas;
    int numIntervalos;
    private CarregarSistema carregar = new CarregarSistema();
    private SimulacaoOperacaoEnergeticaPSO simulacaoHidroeletrica;
    double[][] rede;
    double[] volumeMin;
    double[] volumeMax;
    double[] vazaoMin;
    double[] vazaoMax;
    double[][] direcaoDeCaminhada;
    double[][] matrizPassosMaximos; //mudei o nome desta variável
    int[][] MIVB;
    ArrayList<Arco> arcosBasicos;
    ArrayList<Arco> superBasicos;
    ArrayList<Ciclo> ciclos;

    int[] usinasJusante;
    double passoMaximo;  //mudei o nome desta variável

    /**
     *
     * Construtor responsável por inicializar todas as variáveis do fluxo em
     * rede e da simulação para o cálculo da geração hidráulica.
     *
     * @param numUsinas quantidade de usinas do teste
     * @param numIntervalos número de intervalos do horizonte de planejamento
     * @param rede partícula do PSO com os volumes e as vazões defluências de
     * cada usina e cada horizonte de planejamento
     * @param Volumemin limite mínimo dos volumes de cada usina
     * @param Volumemax limite máximo dos volumes de cada usina
     * @param vazaoMin limite mínimo da vazão defluente de cada usina
     * @param vazaoMax limite máximo da vazão defluente de cada usina
     * @param demanda demanda do sistema em cada intervalo do horizonte de
     * planejamento
     *
     *
     *
     */
    public FluxoEmRede(int numUsinas, int numIntervalos, double[][] rede, double[] Volumemin, double[] Volumemax, double[] vazaoMin, double[] vazaoMax, double demanda) {
        this.numUsinas = numUsinas;
        this.numIntervalos = numIntervalos;
        this.rede = rede;
        this.matrizPassosMaximos = new double[this.rede.length][this.rede[0].length];
        this.volumeMin = Volumemin;
        this.volumeMax = Volumemax;
        this.vazaoMin = vazaoMin;
        this.vazaoMax = vazaoMax;
        this.arcosBasicos = new ArrayList();
        this.superBasicos = new ArrayList();
        this.ciclos = new ArrayList();
        this.MIVB = new int[numUsinas][numIntervalos];
        this.simulacaoHidroeletrica = new SimulacaoOperacaoEnergeticaPSO(numIntervalos, demanda);

        // tenta carregar os dados das usinas do teste
        try {
            carregar.UsinasMinas(simulacaoHidroeletrica);
            inicializaLimitesVolumeVazao(simulacaoHidroeletrica);
        } catch (IOException e) {
            // caso n carregue exibe uma msg e o erro
            System.out.println("erro ao carregar o sistema hidroeletrico\n" + e);
        }

        // identificar quem são as usinas a jusante de cada usina do caso teste
        usinasJusante = new int[numUsinas];
        for (int i = 0; i < numUsinas; i++) {
            //indice 0 porque estamos trabalhando com apenas uma usina a jusante)
            if (i < numUsinas - 1) {
                usinasJusante[i] = simulacaoHidroeletrica.getSistemaHidroeletrico().getUsinas().get(i).getUsinaAJusante().get(0).getCodigo();
            } else {
                // informando que chegou ao sumidouro
                usinasJusante[i] = i + 1;
            }
        }
    }

    /**
     *
     * Método que define os limites máximo dos volumes operativos e das vazões
     * defluentes de cada usina do horizonte de planejamento.
     *
     * @param simulacao variável que possui todos os dados do caso teste, como
     * usinas, volumes máximo e mínimos de cada usina, entre outros.
     *
     */
    private void inicializaLimitesVolumeVazao(SimulacaoOperacaoEnergeticaPSO simulacao) {
        for (int i = 0; i < numUsinas; i++) {
            volumeMax[i] = simulacao.getNos()[0][i].getLimiteMaximoVolume();
            volumeMin[i] = simulacao.getNos()[0][i].getLimiteMinimoVolume();
            vazaoMin[i] = simulacao.getNos()[0][i].getLimiteMinimoVazaoDefluente();
            vazaoMax[i] = simulacao.getNos()[0][i].getLimiteMaximoVazaoDefluente();
        }
    }

    /**
     *
     * Método utilizado para a detecção de ciclos decorrentes da inserção de um
     * arco superbásico na base. Após a inserção de um arco superbásico há a
     * formação de um ciclo. O método varre a lista de arcos superbásicos e
     * procura os ciclos formados por eles, add-os em uma lista de ciclos
     *
     */
    public void detectarCiclos() {
        ciclos.clear();
        for (int i = 0; i < superBasicos.size(); i++) {
            Ciclo ciclo = new Ciclo(superBasicos.get(i));
            ciclos.add(ciclo);
            ciclos.get(i).trilhaDestinoOrigem(MIVB, usinasJusante);
        }
    }

    /**
     * Estratégia de partição de arcos baseada na Função de Produção Hidráulica.
     * Estratégia que sugere que o conjunto de arcos básicos seja constituído
     * pelos volumes, sempre que possível. Logo o conjunto de arcos básicos deve
     * ser o conjunto dos volumes armazenados no reservatório, e assim deve
     * permanecer até que um de seus limites de capacidade impeçam essa
     * partição. Assim, se um arco de volume (i,t) estiver "dentro" do intervalo
     * aberto de seus limites, este arco estará na base e MIVB (i,t) = 0. Caso
     * contrário, MIVB(i, t) = 1. Entretanto, se na(s) iteração(ões) seguinte(s)
     * a variável correspondente ao volume abandonar seu limite, a defluência
     * deixa automaticamente a base para a volta do volume.
     *
     *
     */
    public void ParticaoEPAFPH() {
        Arco arcobasico;
        arcosBasicos.clear();
        superBasicos.clear();

        for (int i = 0; i < MIVB.length; i++) {
            for (int j = 0; j < MIVB[0].length; j++) {
                /*  se o nó da rede estiver em um de seus limites de volume,
                    o valor correspondente ao nó na MIVB será 1, caso contrário seu valor será 0 */
                if ((rede[i][j] == volumeMin[i]) || (rede[i][j] == volumeMax[i])) {
                    MIVB[i][j] = 1;
                    arcobasico = new Arco(i, j, usinasJusante[i], j);
                    arcosBasicos.add(arcobasico);
                } else {
                    MIVB[i][j] = 0;
                    arcobasico = new Arco(i, j, i, (j + 1));
                    arcosBasicos.add(arcobasico);
                }
            }
        }
        imprimeMIBV();
        System.out.println();
        imprimeParticula();
        System.out.println();
        imprimeArcosBasicos();
        System.out.println();
    }

    /**
     *
     * Método que implementa a Estratégia de Partição de Arcos - Transferência
     * de Energia entre Cascata. Permite a transferência de grandes blocos de
     * energia desde que os reservatórios de acumulação envolvidos tenham
     * capacidade para tal. Nesta estratégia, identificam-se os intervalos de
     * maior e menor custo marginal da operação, a fim de possibilitar a
     * transferência de um grande bloco de energia entre estes intervalos. Para
     * isso, declaram - se as defluências das usinas da cascata do intervalo
     * mais a direita como variáveis básicas, enquanto que, todos os arcos de
     * volume das usinas que não atingiram seus limites também devem pertencer
     * ao conjunto de variáveis básicas. Os arcos super básicos são os arcos de
     * defluência das usinas do intervalo mais à esquerda, a fim de fechar o
     * ciclo entre os dois intervalos.
     *
     */
//    public void ParticaoEPATEC() {
//        for (int i = 0; i < numIntervalos; i++) {
//            System.out.println("Período = " + i);
//            simulacaoHidroeletrica.definirVolumesFinais(rede, numUsinas, numIntervalos);
//            simulacaoHidroeletrica.definirVazoesDefluentes(rede, numUsinas, numIntervalos);
//            simulacaoHidroeletrica.simularOperacaoEnergeticaPSO(numIntervalos);
//            ArrayList<IntervaloDeHorizonte> IntervalosHorizonte = simulacaoHidroeletrica.getHorizontePlanejamento().getIntervalos();
//
//            superBasicos.clear();
//            int menor = i;
//            int maior = IntervaloDiferencaCustoMarginal(i, IntervalosHorizonte);
//            
//            System.out.println("\nPeríodo com o Maior Custo Marginal = " + maior + "\n");
//            Arco arcobasico;
//            
//            //===================== PARTE DO WELLINGTON QUE EU MODIFIQUEI
////            for (int usina = 0; usina < numUsinas; usina++) {
////                //colocando as defluencias do intervalo maior como variáveis básicas
////                if (rede[usina][maior + numIntervalos] != vazaoMin[usina]) {
////                    arcobasico = new Arco(usina, maior, usinasJusante[usina], maior);
////                    arcosBasicos.add(arcobasico);
////                    MIVB[usina][maior] = 1;
////                } else {
////                    arcobasico = new Arco(usina, maior, usina, (maior + 1));
////                    arcosBasicos.add(arcobasico);
////                    MIVB[usina][maior] = 0;
////                }
////                
////                //colocando as defluencias do intervalo menor como superbasicas
////                if ((rede[usina][menor] != volumeMax[usina]) && (rede[usina][menor] != volumeMin[usina])) {
////                    superBasicos.add(new Arco(usina, menor, usinasJusante[usina], menor));
////                } else {
////                    superBasicos.add(new Arco(usina, menor, usina, menor + 1));
////                }
////                
////                //colocando se possivel os volumes ao longo do intervalo menor e maior
////                for (int k = menor; k < maior; k++) {
////                    if (rede[usina][k] == volumeMax[usina] || rede[usina][k] == volumeMin[usina]) {
////                        //defluencia
////                        MIVB[usina][k] = 1;
////                        arcosBasicos.add(new Arco(usina, k, usinasJusante[usina], k));
////                    } else {
////                        //volume
////                        arcosBasicos.add(new Arco(usina, k, usina, k + 1));
////                        MIVB[usina][k] = 0;
////                    }
////
////                }
////            }
//
//            for (int usina = 0; usina < numUsinas; usina++) {
//                //colocando as defluencias do intervalo maior como variáveis básicas
//                if (rede[usina][maior + numIntervalos] != vazaoMin[usina]) {
//                    MIVB[usina][maior] = 1;
//                } else {
//                    MIVB[usina][maior] = 0;
//                }
//                
//                //colocando as defluencias do intervalo menor como superbasicas
//                if ((rede[usina][menor] != volumeMax[usina]) && (rede[usina][menor] != volumeMin[usina])) {
//                    superBasicos.add(new Arco(usina, menor, usinasJusante[usina], menor));
//                } else {
//                    superBasicos.add(new Arco(usina, menor, usina, menor + 1));
//                }
//                
//                //colocando se possivel os volumes ao longo do intervalo menor e maior
//                for (int k = menor; k < maior; k++) {
//                    if (rede[usina][k] == volumeMax[usina] || rede[usina][k] == volumeMin[usina]) {
//                        //defluencia
//                        MIVB[usina][k] = 1;
//                    } else {
//                        //volume
//                        MIVB[usina][k] = 0;
//                    }
//                }
//            }
//            
//            arcosBasicos.clear();
//            for (int j = 0; j < MIVB.length; j++) {
//                for (int k = 0; k < MIVB[0].length; k++) {
//                    if(MIVB[j][k] == 0){
//                        arcosBasicos.add(new Arco(j, k, j, k+1));
//                    } else {
//                        if(MIVB[j][k] == 1) {
//                            arcosBasicos.add(new Arco(j, k, j + 1, k));
//                        }
//                    }
//                }
//            }
//            
//            detectarCiclos();
//            
//            imprimeMIBV();
//            System.out.println();
//            imprimeParticula();
//            System.out.println();
//            imprimeArcosBasicos();
//            System.out.println();
//            imprimeArcosSuperBasicos();
//            System.out.println();
//            imprimeCiclos();
//            System.out.println();
//            
//            DirecaoDeCaminhadaArcosSuperBasicos();
//            imprimeDirecaoCaminhadaSuperBasicos();
//            System.out.println();
//            
//            System.out.println("Projeção");
//            ProjecaoDeCaminhadaArcosSuperBasicos();
//            imprimeDirecaoCaminhadaSuperBasicos();
//            System.out.println();
//            
//            DirecaoDeCaminhadaArcosBasicos();
//            imprimeDirecaoCaminhadaArcosBasicos();
//            System.out.println();
//            //PassoMaximoBasicoSuperBasico();
////            PassoMaximoDoCiclo();
////            Atualizar();
//            
//            System.out.println("=======================================================");
//        }
//    }
    public void ParticaoEPATEC() {
        int menor;
        int maior;

        simulacaoHidroeletrica.definirVolumesFinais(rede, numUsinas, numIntervalos);
        simulacaoHidroeletrica.definirVazoesDefluentes(rede, numUsinas, numIntervalos);
        simulacaoHidroeletrica.simularOperacaoEnergeticaPSO(numIntervalos);
        ArrayList<IntervaloDeHorizonte> IntervalosHorizonte = simulacaoHidroeletrica.getHorizontePlanejamento().getIntervalos();

        superBasicos.clear();
        menor = getMenorCustoMarginal(IntervalosHorizonte);
        maior = getMaiorCustoMarginal(IntervalosHorizonte);

        int direita = maior;
        int esquerda = menor;
        if (maior < menor) {
            direita = menor;
            esquerda = maior;
        }

        System.out.println("\nPeríodo com o Menor Custo Marginal = " + menor);
        System.out.println("Período com o Maior Custo Marginal = " + maior + "\n");
        Arco arcobasico;

        for (int usina = 0; usina < numUsinas; usina++) {
            //colocando as defluencias do intervalo maior como variáveis básicas
            if (rede[usina][direita + numIntervalos] != vazaoMin[usina]) {
                MIVB[usina][direita] = 1;
            } else {
                MIVB[usina][direita] = 0;
            }

            //colocando as defluencias do intervalo menor como superbasicas
            if ((rede[usina][esquerda] != volumeMax[usina]) && (rede[usina][esquerda] != volumeMin[usina])) {
                superBasicos.add(new Arco(usina, esquerda, usinasJusante[usina], esquerda));
            } else {
                superBasicos.add(new Arco(usina, esquerda, usina, esquerda + 1));
            }

            //colocando se possivel os volumes ao longo do intervalo menor e maior
            for (int k = esquerda; k < direita; k++) {
                if (rede[usina][k] == volumeMax[usina] || rede[usina][k] == volumeMin[usina]) {
                    //defluencia
                    MIVB[usina][k] = 1;
                } else {
                    //volume
                    MIVB[usina][k] = 0;
                }
            }
        }

        arcosBasicos.clear();
        for (int j = 0; j < MIVB.length; j++) {
            for (int k = 0; k < MIVB[0].length; k++) {
                if (MIVB[j][k] == 0) {
                    arcosBasicos.add(new Arco(j, k, j, k + 1));
                } else if (MIVB[j][k] == 1) {
                    arcosBasicos.add(new Arco(j, k, j + 1, k));
                }
            }
        }

        imprimeMIBV();
        System.out.println();
        imprimeParticula();
        System.out.println();
        imprimeArcosBasicos();
        System.out.println();
        imprimeArcosSuperBasicos();
        System.out.println();
        System.out.println("=======================================================");
    }

    /**
     *
     * Método que implementa a Estratégia de Partição de Arcos baseada na
     * Transferência de energia entre usinas. Ela propõe que se verifique a
     * curva de custo marginal da operação do sistema, declare como básica a
     * defluência que possibilite a maior transferência de energia de um
     * intervalo do planejamento para outro intervalo, de uma determinada usina.
     *
     */
//    public void ParticaoEPATEU() {
//        //arcosBasicos.clear();
//        for (int intervaloUsina = 0; intervaloUsina < numIntervalos; intervaloUsina++) {
//            for (int usina = 0; usina < numUsinas; usina++) {
//                simulacaoHidroeletrica.definirVolumesFinais(rede, numUsinas, numIntervalos);
//                simulacaoHidroeletrica.definirVazoesDefluentes(rede, numUsinas, numIntervalos);
//                simulacaoHidroeletrica.simularOperacaoEnergeticaPSO(numIntervalos);
//                ArrayList<IntervaloDeHorizonte> IntervalosHorizonte = simulacaoHidroeletrica.getHorizontePlanejamento().getIntervalos();
//                superBasicos.clear();
//                Arco arcobasico;
//                int menor = intervaloUsina;
//                int maior = IntervaloDiferencaCustoMarginal(intervaloUsina, IntervalosHorizonte);
//
//                // PARTE DO WELLINGTON QUE EU MODIFIQUEI
////                //colocando as defluências do intervalo maior como variáveis básicas
////                if (rede[usina][maior + numIntervalos] != vazaoMin[usina]) {
////                    arcobasico = new Arco(usina, maior, usinasJusante[usina], maior);
////                    arcosBasicos.add(arcobasico);
////                    MIVB[usina][maior] = 1;
////                } else {
////                    arcobasico = new Arco(usina, maior, usina, (maior + 1));
////                    arcosBasicos.add(arcobasico);
////                    MIVB[usina][maior] = 0;
////                }
////                
////                //colocando se possivel a defluência do intervalo menor como superbásicas
////                if ((rede[usina][menor] != volumeMax[usina]) && (rede[usina][menor] != volumeMin[usina])) {
////                    superBasicos.add(new Arco(usina, menor, usinasJusante[usina], menor));
////                } else {
////                    superBasicos.add(new Arco(usina, menor, usina, menor + 1));
////                }
////                
////                //colocando se possivel os volumes ao longo do intervalo menor e maior
////                for (int k = menor; k < maior; k++) {
////                    if (rede[usina][k] == volumeMax[usina] || rede[usina][k] == volumeMin[usina]) {
////                        //defluencia
////                        MIVB[usina][k] = 1;
////                        arcosBasicos.add(new Arco(usina, k, usinasJusante[usina], k));
////                    } else {
////                        //volume
////                        arcosBasicos.add(new Arco(usina, k, usina, k + 1));
////                        MIVB[usina][k] = 0;
////                    }
////                }
//
//                //colocando as defluências do intervalo maior como variáveis básicas
//                if (rede[usina][maior + numIntervalos] != vazaoMin[usina]) {
//                    MIVB[usina][maior] = 1;
//                } else {
//                    MIVB[usina][maior] = 0;
//                }
//                
//                //colocando se possivel a defluência do intervalo menor como superbásicas
//                if ((rede[usina][menor] != volumeMax[usina]) && (rede[usina][menor] != volumeMin[usina])) {
//                    superBasicos.add(new Arco(usina, menor, usinasJusante[usina], menor));
//                } else {
//                    superBasicos.add(new Arco(usina, menor, usina, menor + 1));
//                }
//                
//                //colocando se possivel os volumes ao longo do intervalo menor e maior
//                for (int k = menor; k < maior; k++) {
//                    if (rede[usina][k] == volumeMax[usina] || rede[usina][k] == volumeMin[usina]) {
//                        //defluencia
//                        MIVB[usina][k] = 1;
//                    } else {
//                        //volume
//                        MIVB[usina][k] = 0;
//                    }
//                }
//                
//                arcosBasicos.clear();
//                for (int j = 0; j < MIVB.length; j++) {
//                    for (int k = 0; k < MIVB[0].length; k++) {
//                        if(MIVB[j][k] == 0){
//                            arcosBasicos.add(new Arco(j, k, j, k+1));
//                        } else {
//                            if(MIVB[j][k] == 1) {
//                                arcosBasicos.add(new Arco(j, k, j + 1, k));
//                            }
//                        }
//                    }
//                }
//                
//                /* COLOQUEI COMO COMENTÁRIO
//                detectarCiclos();
//                DirecaoDeCaminhadaArcosSuperBasicos();
//                ProjecaoDeCaminhadaArcosSuperBasicos();
//                DirecaoDeCaminhadaArcosBasicos();
//                //PassoMaximoBasicoSuperBasico();
//                PassoMaximoDoCiclo();
//                Atualizar();
//                */
//                
//            imprimeMIBV();
//            System.out.println();
//            imprimeParticula();
//            System.out.println();
//            imprimeArcosBasicos();
//            System.out.println();
//            imprimeArcosSuperBasicos();
//            System.out.println("=======================================================");
//            }
//        }
//    }
    public void ParticaoEPATEU(int indiceUsina) {
        //arcosBasicos.clear();
        simulacaoHidroeletrica.definirVolumesFinais(rede, numUsinas, numIntervalos);
        simulacaoHidroeletrica.definirVazoesDefluentes(rede, numUsinas, numIntervalos);
        simulacaoHidroeletrica.simularOperacaoEnergeticaPSO(numIntervalos);
        ArrayList<IntervaloDeHorizonte> IntervalosHorizonte = simulacaoHidroeletrica.getHorizontePlanejamento().getIntervalos();
        superBasicos.clear();
        arcosBasicos.clear();

        int maior = getMaiorCustoMarginal(IntervalosHorizonte);
        int menor = getMenorCustoMarginal(IntervalosHorizonte);
        System.out.println("\nMaior = " + maior);
        System.out.println("Menor = " + menor + "\n");
        
        int direita = maior;
        int esquerda = menor;
        if (maior < menor) {
            direita = menor;
            esquerda = maior;
        }

        //colocando as defluencias do intervalo maior como variáveis básicas
        if (rede[indiceUsina][direita + numIntervalos] != vazaoMin[indiceUsina]) {
            MIVB[indiceUsina][direita] = 1;
        } else {
            MIVB[indiceUsina][direita] = 0;
        }

        //colocando as defluencias do intervalo menor como superbasicas
        if ((rede[indiceUsina][esquerda] != volumeMax[indiceUsina]) && (rede[indiceUsina][esquerda] != volumeMin[indiceUsina])) {
            superBasicos.add(new Arco(indiceUsina, esquerda, usinasJusante[indiceUsina], esquerda));
        } else {
            superBasicos.add(new Arco(indiceUsina, esquerda, indiceUsina, esquerda + 1));
        }

        //colocando se possivel os volumes ao longo do intervalo menor e maior
        for (int k = esquerda; k < direita; k++) {
            if (rede[indiceUsina][k] == volumeMax[indiceUsina] || rede[indiceUsina][k] == volumeMin[indiceUsina]) {
                //defluencia
                MIVB[indiceUsina][k] = 1;
            } else {
                //volume
                MIVB[indiceUsina][k] = 0;
            }
        }

        arcosBasicos.clear();
        for (int j = 0; j < MIVB.length; j++) {
            for (int k = 0; k < MIVB[0].length; k++) {
                if (MIVB[j][k] == 0) {
                    arcosBasicos.add(new Arco(j, k, j, k + 1));
                } else if (MIVB[j][k] == 1) {
                    arcosBasicos.add(new Arco(j, k, j + 1, k));
                }
            }
        }
    }

    private int IntervaloDiferencaCustoMarginal(int intervalo, ArrayList<IntervaloDeHorizonte> intervalos) {
        int indice = 0;
        double maiorDiferenca = Double.MIN_VALUE;
        double custoIntervaloAtual = intervalos.get(intervalo).getGeracaoComplementar();
        for (int i = intervalo + 1; i < numIntervalos; i++) {
            double diferencaiIntervaloAtual = Math.abs(intervalos.get(i).getGeracaoComplementar() - custoIntervaloAtual);
            if (diferencaiIntervaloAtual > maiorDiferenca) {
                indice = i;
                maiorDiferenca = diferencaiIntervaloAtual;
            }
        }
        return indice;
    }

    private int getMaiorCustoMarginal(ArrayList<IntervaloDeHorizonte> intervalos) {
        int indice = 0;
        for (int i = 1; i < intervalos.size(); i++) {
            if (intervalos.get(i).getGeracaoComplementar() > intervalos.get(indice).getGeracaoComplementar()) {
                indice = i;
            }
        }

        return indice;
    }

    private int getMenorCustoMarginal(ArrayList<IntervaloDeHorizonte> intervalos) {
        int indice = 0;
        for (int i = 1; i < intervalos.size(); i++) {
            /* Aqui estou usando a geração hidráulica, pois pode existir períodos em que a complementação térmica será nula.
               Caso isto ocorra, eu procuro o período com a maior geração hidráulica*/
            if (intervalos.get(i).getGeracaoHidraulica() > intervalos.get(indice).getGeracaoHidraulica()) {
                indice = i;
            }
        }

        return indice;
    }

    public void DirecaoDeCaminhadaArcosSuperBasicos() {
        direcaoDeCaminhada = new double[numUsinas][numIntervalos * 2];
        Random random = new Random();
        double numeroAleatorio;

        for (int i = 0; i < superBasicos.size(); i++) {
            numeroAleatorio = random.nextDouble();
            int indiceUsinaSB = superBasicos.get(i).getOrigem()[0];
            int indiceIntervaloSB = superBasicos.get(i).getOrigem()[1];
            if (numeroAleatorio < 0.5) {
                //se o arco superbasico for de volume
                if (MIVB[indiceUsinaSB][indiceIntervaloSB] == 1) {
                    direcaoDeCaminhada[indiceUsinaSB][indiceIntervaloSB] = -numeroAleatorio;
                } else {
                    direcaoDeCaminhada[indiceUsinaSB][indiceIntervaloSB + numIntervalos] = -numeroAleatorio * (1000000.0 / 2628000.0);
                }
            } else //se o arco superbasico for de volume
            if (MIVB[indiceUsinaSB][indiceIntervaloSB] == 1) {
                direcaoDeCaminhada[indiceUsinaSB][indiceIntervaloSB] = numeroAleatorio;
            } else {
                direcaoDeCaminhada[indiceUsinaSB][indiceIntervaloSB + numIntervalos] = numeroAleatorio * (1000000.0 / 2628000.0);
            }
        }
    }

    public void ProjecaoDeCaminhadaArcosSuperBasicos() {
        for (int i = 0; i < superBasicos.size(); i++) {
            int indiceUsina = superBasicos.get(i).getOrigem()[0];
            int indiceIntervalo = superBasicos.get(i).getOrigem()[1];
            //caso o arco superbasico sera de volume
            if (MIVB[indiceUsina][indiceIntervalo] == 1) {
                if ((rede[indiceUsina][indiceIntervalo] == volumeMax[indiceUsina]) && (direcaoDeCaminhada[indiceUsina][indiceIntervalo] > 0)) {
                    direcaoDeCaminhada[indiceUsina][indiceIntervalo] = 0;
                } else if ((rede[indiceUsina][indiceIntervalo] == volumeMin[indiceUsina]) && (direcaoDeCaminhada[indiceUsina][indiceIntervalo] < 0)) {
                    direcaoDeCaminhada[indiceUsina][indiceIntervalo] = 0;
                }
            } else if ((rede[indiceUsina][numIntervalos + indiceIntervalo] == vazaoMax[indiceUsina]) && (direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo] > 0)) {
                direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo] = 0;
            } else if ((rede[indiceUsina][numIntervalos + indiceIntervalo] == vazaoMin[indiceUsina]) && (direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo] < 0)) {
                direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo] = 0;
            }
        }
    }

    public void DirecaoDeCaminhadaArcosBasicos() {
        double fatorConversaoVAVO = 1000000.0 / 2628000.0;
        double direcaoSuperBasico;
        for (int i = 0; i < arcosBasicos.size(); i++) {
            int indiceUsina = arcosBasicos.get(i).getOrigem()[0];
            int indiceIntervalo = arcosBasicos.get(i).getOrigem()[1];
            for (int j = 0; j < ciclos.size(); j++) {
                int SinalDeConcordanciaDoArcoSB = 1;
                boolean pertence = false;

                //caso em que o arco basico esta na trilha de destino
                if (VerificarTrilhaDeDestino(arcosBasicos.get(i), ciclos.get(j))) {
                    pertence = true;
                } else if (VerificarTrilhaDeOrigem(arcosBasicos.get(i), ciclos.get(j))) {
                    pertence = true;
                    SinalDeConcordanciaDoArcoSB = -1;
                }

                if (pertence) {
                    int indiceUsinaSB = ciclos.get(j).getSuperbasico().getOrigem()[0];
                    int indiceIntervaloSB = ciclos.get(j).getSuperbasico().getOrigem()[1];
                    //verificando se o arco basico é de defluencia
                    if (MIVB[indiceUsina][indiceIntervalo] == 1) {
                        //verificando se o arco superbasico é de montante
                        if (MIVB[indiceUsinaSB][indiceIntervaloSB] == 1) {
                            //direcaoSuperBasico = (SinalDeConcordanciaDoArcoSB * direcaoDeCaminhada[indiceUsinaSB][indiceIntervaloSB]*fatorConversaoVAVO); 
                            direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo] = direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo] + (SinalDeConcordanciaDoArcoSB * direcaoDeCaminhada[indiceUsinaSB][indiceIntervaloSB]);
                        } else {
                            direcaoSuperBasico = (SinalDeConcordanciaDoArcoSB * direcaoDeCaminhada[indiceUsinaSB][indiceIntervaloSB + numIntervalos]);
                            direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo] = direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo] + direcaoSuperBasico;
                        }
                    } else //verificando se o arco superbasico é de montante
                    if (MIVB[indiceUsinaSB][indiceIntervaloSB] == 1) {
                        direcaoDeCaminhada[indiceUsina][indiceIntervalo] = direcaoDeCaminhada[indiceUsina][indiceIntervalo] + SinalDeConcordanciaDoArcoSB * direcaoDeCaminhada[indiceUsinaSB][indiceIntervaloSB];
                    } else {
                        direcaoSuperBasico = (SinalDeConcordanciaDoArcoSB * direcaoDeCaminhada[indiceUsinaSB][numIntervalos + indiceIntervaloSB]);
                        direcaoDeCaminhada[indiceUsina][indiceIntervalo] = direcaoDeCaminhada[indiceUsina][indiceIntervalo] + direcaoSuperBasico;
                    }
                }

            }
        }
    }

    
    public void PassoMaximoDoCiclo() {
        matrizPassosMaximos = new double[numUsinas][numIntervalos * 2];
        PassoMaximoBasicoSuperBasico();
        double menor = Double.MAX_VALUE;
        for (int i = 0; i < matrizPassosMaximos.length; i++) {
            for (int j = 0; j < matrizPassosMaximos[0].length; j++) {
                if (matrizPassosMaximos[i][j] < menor && matrizPassosMaximos[i][j] != 0) {
                    menor = matrizPassosMaximos[i][j];
                }
            }
        }
        passoMaximo = menor;
    }

    private void PassoMaximoBasicoSuperBasico() {
        //calculando o passo maximo dos arcos superbasicos
        for (int i = 0; i < superBasicos.size(); i++) {
            int indiceUsina = superBasicos.get(i).getOrigem()[0];
            int indiceIntervalo = superBasicos.get(i).getOrigem()[1];
            //caso o superbasico seja volume
            if (MIVB[indiceUsina][indiceIntervalo] == 1) {
                if (direcaoDeCaminhada[indiceUsina][indiceIntervalo] < 0) {
                    matrizPassosMaximos[indiceUsina][indiceIntervalo] = (volumeMin[indiceUsina] - rede[indiceUsina][indiceIntervalo]) / direcaoDeCaminhada[indiceUsina][indiceIntervalo];
                } else if (direcaoDeCaminhada[indiceUsina][indiceIntervalo] > 0) {
                    matrizPassosMaximos[indiceUsina][indiceIntervalo] = (volumeMax[indiceUsina] - rede[indiceUsina][indiceIntervalo]) / direcaoDeCaminhada[indiceUsina][indiceIntervalo];
                }
            } else if (direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo] < 0) {
                matrizPassosMaximos[indiceUsina][numIntervalos + indiceIntervalo] = (vazaoMin[indiceUsina] - rede[indiceUsina][numIntervalos + indiceIntervalo]) * (1000000.0 / 2628000.0) / direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo];
            } else if (direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo] > 0) {
                matrizPassosMaximos[indiceUsina][numIntervalos + indiceIntervalo] = (vazaoMax[indiceUsina] - rede[indiceUsina][numIntervalos + indiceIntervalo]) * (1000000.0 / 2628000.0) / direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo];
            }
        }

        for (int i = 0; i < ciclos.size(); i++) {
            for (int j = 0; j < ciclos.get(i).getArestasbasicas().size(); j++) {
                Arco arcobasico = ciclos.get(i).getArestasbasicas().get(j);
                int indiceUsina = arcobasico.getOrigem()[0];
                int indiceIntervalo = arcobasico.getOrigem()[1];
                //caso em que o arco basico é defluencia
                if (MIVB[indiceUsina][indiceIntervalo] == 1) {
                    //verificando se o passo maximo do arco basico ja foi calculado
                    if (matrizPassosMaximos[indiceUsina][numIntervalos + indiceIntervalo] == 0) {
                        if (direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo] < 0) {
                            matrizPassosMaximos[indiceUsina][numIntervalos + indiceIntervalo] = (vazaoMin[indiceUsina] - rede[indiceUsina][numIntervalos + indiceIntervalo]) * (1000000.0 / 2628000.0) / direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo];
                        } else if (direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo] > 0) {
                            matrizPassosMaximos[indiceUsina][numIntervalos + indiceIntervalo] = (vazaoMax[indiceUsina] - rede[indiceUsina][numIntervalos + indiceIntervalo]) * (1000000.0 / 2628000.0) / direcaoDeCaminhada[indiceUsina][numIntervalos + indiceIntervalo];

                        }
                    }
                } else//caso em que o arco basico é montante
                //verificando se o passo maximo do arco basico ja foi calculado
                 if (matrizPassosMaximos[indiceUsina][indiceIntervalo] == 0) {
                        if (direcaoDeCaminhada[indiceUsina][indiceIntervalo] < 0) {
                            matrizPassosMaximos[indiceUsina][indiceIntervalo] = (volumeMin[indiceUsina] - rede[indiceUsina][indiceIntervalo])  / direcaoDeCaminhada[indiceUsina][indiceIntervalo];
                        } else if (direcaoDeCaminhada[indiceUsina][indiceIntervalo] > 0) {
                            matrizPassosMaximos[indiceUsina][indiceIntervalo] = (volumeMax[indiceUsina] - rede[indiceUsina][indiceIntervalo])  / direcaoDeCaminhada[indiceUsina][indiceIntervalo];

                        }
                    }
            }
        }
    }

    private boolean VerificarTrilhaDeOrigem(Arco arco, Ciclo ciclo) {
        for (int i = 0; i < ciclo.getTrilhaorigem().size(); i++) {
            Arco arcobasico = ciclo.getTrilhaorigem().get(i);
            if ((arco.getOrigem()[0] == arcobasico.getOrigem()[0]) && (arco.getOrigem()[1] == arcobasico.getOrigem()[1])
                    && (arco.getDestino()[0] == arcobasico.getDestino()[0]) && (arco.getDestino()[1] == arcobasico.getDestino()[1])) {
                return true;
            }
        }
        return false;
    }

    private boolean VerificarTrilhaDeDestino(Arco arco, Ciclo ciclo) {
        for (int i = 0; i < ciclo.getTrilhadestino().size(); i++) {
            Arco arcobasico = ciclo.getTrilhadestino().get(i);
            if ((arco.getOrigem()[0] == arcobasico.getOrigem()[0]) && (arco.getOrigem()[1] == arcobasico.getOrigem()[1])
                    && (arco.getDestino()[0] == arcobasico.getDestino()[0]) && (arco.getDestino()[1] == arcobasico.getDestino()[1])) {
                return true;
            }
        }
        return false;
    }

    
    public void AtualizarRede() {
        for (int i = 0; i < rede.length; i++) {
            // atualização dos volumes
            for (int j = 0; j < numIntervalos; j++) {
                rede[i][j] = rede[i][j] + direcaoDeCaminhada[i][j] * passoMaximo;
            }
            // atualização das defluências
            for (int j = numIntervalos; j < rede[0].length; j++) {
                rede[i][j] = rede[i][j] + direcaoDeCaminhada[i][j] * passoMaximo * (2628000.0 / 1000000.0);

            }
        }
    }

    
    public void imprimeMIBV() {
        System.out.println("Matriz Indicadora de Variáveis Básicas");
        for (int i = 0; i < MIVB.length; i++) {
            if (i != 0) {
                System.out.println();
            }
            for (int j = 0; j < MIVB[0].length; j++) {
                if (j == MIVB[0].length - 1) {
                    System.out.print(MIVB[i][j]);
                } else {
                    System.out.print(MIVB[i][j] + ", ");
                }
            }
        }
        System.out.println();
    }

    public void imprimeParticula() {
        System.out.println("Partícula");
        for (int i = 0; i < rede.length; i++) {
            if (i != 0) {
                System.out.println();
            }
            for (int j = 0; j < rede[0].length; j++) {
                if (j == rede[0].length - 1) {
                    System.out.print(rede[i][j]);
                } else {
                    System.out.print(rede[i][j] + ", ");
                }
            }
        }
        System.out.println();
    }

    public void imprimeArcosBasicos() {
        System.out.println("Arcos Básicos");
        for (int i = 0; i < arcosBasicos.size(); i++) {
            System.out.println(arcosBasicos.get(i).toString());
        }
        System.out.println();
    }

    public void imprimeArcosSuperBasicos() {
        System.out.println("Arcos Super Básicos");
        for (int i = 0; i < superBasicos.size(); i++) {
            System.out.println(superBasicos.get(i).toString());
        }
        System.out.println();
    }

    public void imprimeCiclos() {
        System.out.println("IMPRESSÃO DOS CICLOS");
        for (int i = 0; i < ciclos.size(); i++) {
            System.out.println("Ciclo " + i);
            System.out.println("Trilha de Origem do ciclo " + i);
            for (int j = 0; j < ciclos.get(i).getTrilhaorigem().size(); j++) {
                System.out.println(ciclos.get(i).getTrilhaorigem().get(j).toString());
            }
            System.out.println();
            System.out.println("Trilha de Destino do ciclo " + i);
            for (int j = 0; j < ciclos.get(i).getTrilhadestino().size(); j++) {
                System.out.println(ciclos.get(i).getTrilhadestino().get(j).toString());
            }
            System.out.println();
        }
    }

    public void imprimeDirecaoCaminhadaSuperBasicos() {
        System.out.println("Direcao de Caminhada dos Arcos SuperBásicos");
        for (int i = 0; i < superBasicos.size(); i++) {
            int indiceUsinaSB = superBasicos.get(i).getOrigem()[0];
            int indiceIntervaloSB = superBasicos.get(i).getOrigem()[1];
            if (MIVB[indiceUsinaSB][indiceIntervaloSB] == 1) {
                System.out.println(superBasicos.get(i).toString() + " Direção do Arco Super Básico = " + direcaoDeCaminhada[indiceUsinaSB][indiceIntervaloSB]);

            } else {
                System.out.println(superBasicos.get(i).toString() + " Direção do Arco Super Básico = " + direcaoDeCaminhada[indiceUsinaSB][indiceIntervaloSB + numIntervalos]);
            }
        }
        System.out.println();
    }

    public void imprimeDirecaoCaminhadaArcosBasicos() {
        for (int i = 0; i < direcaoDeCaminhada.length; i++) {
            if (i != 0) {
                System.out.println();
            }
            for (int j = 0; j < direcaoDeCaminhada[0].length; j++) {
                if (j != direcaoDeCaminhada[0].length - 1) {
                    System.out.print(direcaoDeCaminhada[i][j] + ", ");
                } else {
                    System.out.print(direcaoDeCaminhada[i][j]);
                }
            }
        }
        System.out.println();
    }

    public void imprimeMatrizPassosMaximos() {
        for (int i = 0; i < matrizPassosMaximos.length; i++) {
            if (i != 0) {
                System.out.println();
            }
            for (int j = 0; j < matrizPassosMaximos[0].length; j++) {
                if (j != matrizPassosMaximos[0].length - 1) {
                    System.out.print(matrizPassosMaximos[i][j] + ", ");
                } else {
                    System.out.print(matrizPassosMaximos[i][j]);
                }
            }
        }
        System.out.println();
    }

    public ArrayList<Ciclo> getCiclos() {
        return ciclos;
    }

    public void setCiclos(ArrayList<Ciclo> ciclos) {
        this.ciclos = ciclos;
    }

}
