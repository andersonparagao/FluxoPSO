package PsoGeracaoHidroeletrica;

import fluxoemredes.Arco;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import simulacao.SimulacaoOperacaoEnergeticaPSO;

public class ParticulaPSO {

    private double[][] velocidade;
    private double[][] posicao;
    private final double[][] posicaoMin;
    private final double[][] posicaoMax;
    private final double[][] velocidadeMax;
    private final double[][] velocidadeMin;
    private double[][] vetorpBest;
    private double pBest;
    private double avaliacao;
    private SimulacaoOperacaoEnergeticaPSO simulacao;
    


    public ParticulaPSO(int numUsinas, int numIntervalos, double[] xminvazao, double[] xmaxvazao, double[] xminvolume, double[] xmaxvolume, SimulacaoOperacaoEnergeticaPSO simulacao) {
        //a variavel volume vazao é porque as usinas terao como valores os volumes finais e vazaoes defluentes em sequencia
        posicao = new double[numUsinas][numIntervalos * 2];
        velocidade = new double[numUsinas][numIntervalos * 2];
        vetorpBest = new double[numUsinas][numIntervalos * 2];
        posicaoMin = new double[numUsinas][numIntervalos * 2];
        posicaoMax = new double[numUsinas][numIntervalos * 2];
        velocidadeMax = new double[numUsinas][numIntervalos * 2];
        velocidadeMin = new double[numUsinas][numIntervalos * 2];
        pBest = Double.MAX_VALUE;
        this.simulacao = simulacao;

        //foi separado devido aos limites de volume e vazão serem diferentes
        for (int j = 0; j < numUsinas; j++) {
            for (int k = 0; k < numIntervalos; k++) {
                posicaoMin[j][k] = xminvolume[j];
                posicaoMax[j][k] = xmaxvolume[j];
            }
        }

        for (int j = 0; j < numUsinas; j++) {
            for (int k = numIntervalos; k < numIntervalos * 2; k++) {
                posicaoMin[j][k] = xminvazao[j];
                posicaoMax[j][k] = xmaxvazao[j];
            }
        }
        
        for (int j = 0; j < numUsinas; j++) {
            for (int k = 0; k < numIntervalos; k++) {
                // velocidades mínimas e máximas são iguais?
                velocidadeMin[j][k] = (xminvolume[j] - xmaxvolume[j]);
                velocidadeMax[j][k] = (xmaxvolume[j] - xminvolume[j]);
            }
        }

        for (int j = 0; j < numUsinas; j++) {
            for (int k = numIntervalos; k < numIntervalos * 2; k++) {
                // velocidades mínimas e máximas são iguais?
                velocidadeMin[j][k] = (xminvazao[j] - xmaxvazao[j]);
                velocidadeMax[j][k] = (xmaxvazao[j] - xminvazao[j]);
            }
        }
    }

    public double[][] volumeInicial(double[][] volumevazao, int numintervalos, int numUsinas) {
        double[][] volumeinicial = new double[numUsinas][numintervalos];
        //System.out.println("volume inicial");
        for (int i = 0; i < numUsinas; i++) {
            for (int j = 0; j < numintervalos; j++) {
                if (j == 0) {
                    volumeinicial[i][j] = posicaoMax[i][j];
                } else {
                    volumeinicial[i][j] = volumevazao[i][j - 1];
                }
            }
        }

        return volumeinicial;
    }

    public List AtualizarVelocidadeWellington(int indiceParticula, double c1, double c2, double[][] gBest, List<Arco> arcosSuperBasicos) {
        //Random r = new Random();
        //double r1 = r.nextDouble();
        //double r2 = r.nextDouble();
        double avaliacaoAtual;
        int numUsinas = posicaoMin.length;
        int numIntervalos = posicao[0].length/2;
        List<Double> direcaoCaminhadaArcosSuperBasico = new ArrayList<>();
        double[][] posicaoAproximacao = new double[numUsinas][numIntervalos*2];
        
        for(int i = 0 ; i < arcosSuperBasicos.size(); i++){
            int linhaOrigem = arcosSuperBasicos.get(i).getOrigem()[0];
            int colunaOrigem = arcosSuperBasicos.get(i).getOrigem()[1];
            int linhaDestino = arcosSuperBasicos.get(i).getDestino()[0];
            int colunaDestino = arcosSuperBasicos.get(i).getDestino()[1];
            for(int j = 0;j < posicao.length;j++){
                for(int l = 0; l < posicao[0].length;l++){
                    posicaoAproximacao[j][l] = posicao[j][l];
                }
            }
         //   System.out.println("Arco SuperBásico = " + arcosSuperBasicos.get(i).toString());
            if(linhaOrigem != linhaDestino){
         //       System.out.println("defluencia");
                //System.out.println("defluencia antes: " + posicaoAproximacao[linhaOrigem][colunaOrigem + numIntervalos]);
                posicaoAproximacao[linhaOrigem][colunaOrigem + numIntervalos] = posicaoAproximacao[linhaOrigem][colunaOrigem + numIntervalos] + 1 ;
                //System.out.println("defluencia depois: " + posicaoAproximacao[linhaOrigem][colunaOrigem + numIntervalos]);
                simulacao.definirVolumesFinais(posicaoAproximacao, numUsinas, numIntervalos);
                simulacao.definirVazoesDefluentes(posicaoAproximacao, numUsinas, numIntervalos);
                double defluenciaposicao = posicao[linhaOrigem][colunaOrigem + numIntervalos] ;
                double defluenciaAproximacao  = posicaoAproximacao[linhaOrigem][colunaOrigem + numIntervalos];
                //System.out.println("defluencia antes: " + defluenciaposicao);
                //System.out.println("defluencia depois: " + defluenciaAproximacao);
                //System.out.println("avaliacao antes:  " + avaliacao);
                double aval = simulacao.simularOperacaoEnergeticaPSO(numIntervalos);
                simulacao.definirVolumesFinais(posicao, numUsinas, numIntervalos);
                simulacao.definirVazoesDefluentes(posicao, numUsinas, numIntervalos);
                avaliacaoAtual = simulacao.simularOperacaoEnergeticaPSO(numIntervalos);
                //System.out.println("avaliacao depois: " + aval);
                //System.out.println("avaliacao atual: " + avaliacaoAtual);
                //System.out.println("avaliacao depois: " + aval);
                if(indiceParticula == 0){
                    direcaoCaminhadaArcosSuperBasico.add(-1*(aval - avaliacaoAtual));
                } else {
                    Random r = new Random();
                    direcaoCaminhadaArcosSuperBasico.add(-1*(r.nextDouble())*(aval - avaliacaoAtual));
                }
                
                //direcaoCaminhadaArcosSuperBasico.add(-1*(simulacao.simularOperacaoEnergeticaPSO(numIntervalos) - avaliacao));
            }else{
                //posicaoAproximacao = posicao;
           //     System.out.println("volume");
                if(posicaoAproximacao[linhaOrigem][colunaOrigem]!=posicaoMax[linhaOrigem][0]){
                    posicaoAproximacao[linhaOrigem][colunaOrigem] = posicaoAproximacao[linhaOrigem][colunaOrigem] + 1;
                }else{
                    posicaoAproximacao[linhaOrigem][colunaOrigem] = posicaoAproximacao[linhaOrigem][colunaOrigem] - 1;
                }
                simulacao.definirVolumesFinais(posicaoAproximacao, numUsinas, numIntervalos);
                simulacao.definirVazoesDefluentes(posicaoAproximacao, numUsinas, numIntervalos);
                //System.out.println("avaliacao antes:  " + avaliacao);
                double aval = simulacao.simularOperacaoEnergeticaPSO(numIntervalos);
                simulacao.definirVolumesFinais(posicao, numUsinas, numIntervalos);
                simulacao.definirVazoesDefluentes(posicao, numUsinas, numIntervalos);
                avaliacaoAtual = simulacao.simularOperacaoEnergeticaPSO(numIntervalos);
                //System.out.println("avaliacao depois: " + aval);
                //System.out.println("avaliacao atual: " + avaliacaoAtual);
                //System.out.println("avaliacao depois: " + aval);
                if(indiceParticula == 0){
                    direcaoCaminhadaArcosSuperBasico.add(-1*(aval - avaliacaoAtual));
                } else {
                    Random r = new Random();
                    direcaoCaminhadaArcosSuperBasico.add(-1*(r.nextDouble())*(aval - avaliacaoAtual));
                }
                
            }
            //System.out.println("direcao caminhada: " + direcaoCaminhadaArcosSuperBasico.get(i));
        }
        
        return direcaoCaminhadaArcosSuperBasico;
    }
    
    public List AtualizarVelocidade(double c1, double c2, double[][] gBest, List<Arco> arcosSuperBasicos) {
        Random r = new Random();
        double r1 = r.nextDouble();
        double r2 = r.nextDouble();
        
        int numUsinas = posicaoMin.length;
        int numIntervalos = posicao[0].length/2;
        List<Double> direcaoCaminhadaArcosSuperBasico = new ArrayList<>();
        
        for (int i = 0; i < arcosSuperBasicos.size(); i++) {
            int linhaOrigem = arcosSuperBasicos.get(i).getOrigem()[0];
            int colunaOrigem = arcosSuperBasicos.get(i).getOrigem()[1];
            int linhaDestino = arcosSuperBasicos.get(i).getDestino()[0];
            int colunaDestino = arcosSuperBasicos.get(i).getDestino()[1];

            // se o arco superbásico for um arco de volume
            if(linhaOrigem == linhaDestino){
                double direcao = (velocidade[linhaOrigem][colunaOrigem] + c1 * r1 * (vetorpBest[linhaOrigem][colunaOrigem] - posicao[linhaOrigem][colunaOrigem]) + c2 * r2 * (gBest[linhaOrigem][colunaOrigem] - posicao[linhaOrigem][colunaOrigem]));
                direcaoCaminhadaArcosSuperBasico.add(direcao);
            } else {
                double direcao = (velocidade[linhaOrigem][colunaOrigem + numIntervalos] + c1 * r1 * (vetorpBest[linhaOrigem][colunaOrigem + numIntervalos] - posicao[linhaOrigem][colunaOrigem + numIntervalos]) + c2 * r2 * (gBest[linhaOrigem][colunaOrigem + numIntervalos] - posicao[linhaOrigem][colunaOrigem + numIntervalos]));
                direcaoCaminhadaArcosSuperBasico.add(direcao);
            }
        }
        
        return direcaoCaminhadaArcosSuperBasico;
    }
    
    
    
    public List AtualizarVelocidadeFatorConstricao(double c1, double c2, double[][] gBest, List<Arco> arcosSuperBasicos) {
        Random r = new Random();
        double r1 = r.nextDouble();
        double r2 = r.nextDouble();
        
        double fi = c1 + c2;
        double denominador = (2 - fi - (Math.sqrt(Math.pow(fi, 2) - 4*fi)));
        double k = 2/denominador;
        
        
        
        int numUsinas = posicaoMin.length;
        int numIntervalos = posicao[0].length/2;
        List<Double> direcaoCaminhadaArcosSuperBasico = new ArrayList<>();
        
        for (int i = 0; i < arcosSuperBasicos.size(); i++) {
            int linhaOrigem = arcosSuperBasicos.get(i).getOrigem()[0];
            int colunaOrigem = arcosSuperBasicos.get(i).getOrigem()[1];
            int linhaDestino = arcosSuperBasicos.get(i).getDestino()[0];
            int colunaDestino = arcosSuperBasicos.get(i).getDestino()[1];

            // se o arco superbásico for um arco de volume
            if(linhaOrigem == linhaDestino){
                double direcao = -k*(velocidade[linhaOrigem][colunaOrigem] + c1 * r1 * (vetorpBest[linhaOrigem][colunaOrigem] - posicao[linhaOrigem][colunaOrigem]) + c2 * r2 * (gBest[linhaOrigem][colunaOrigem] - posicao[linhaOrigem][colunaOrigem]));
//                System.out.println("Com K = " + direcao);
//                System.out.println("Sem K = " + (velocidade[linhaOrigem][colunaOrigem] + c1 * r1 * (vetorpBest[linhaOrigem][colunaOrigem] - posicao[linhaOrigem][colunaOrigem]) + c2 * r2 * (gBest[linhaOrigem][colunaOrigem] - posicao[linhaOrigem][colunaOrigem])));
                direcaoCaminhadaArcosSuperBasico.add(direcao);
            } else {
                double direcao = -k*(velocidade[linhaOrigem][colunaOrigem + numIntervalos] + c1 * r1 * (vetorpBest[linhaOrigem][colunaOrigem + numIntervalos] - posicao[linhaOrigem][colunaOrigem + numIntervalos]) + c2 * r2 * (gBest[linhaOrigem][colunaOrigem + numIntervalos] - posicao[linhaOrigem][colunaOrigem + numIntervalos]));
//                System.out.println("com K " + direcao);
//                System.out.println("SEM K = " + (velocidade[linhaOrigem][colunaOrigem + numIntervalos] + c1 * r1 * (vetorpBest[linhaOrigem][colunaOrigem + numIntervalos] - posicao[linhaOrigem][colunaOrigem + numIntervalos]) + c2 * r2 * (gBest[linhaOrigem][colunaOrigem + numIntervalos] - posicao[linhaOrigem][colunaOrigem + numIntervalos])));
                direcaoCaminhadaArcosSuperBasico.add(direcao);
            }
        }
        
        return direcaoCaminhadaArcosSuperBasico;
    }
    
    
    public List AtualizarVelocidadeConstanteInercia(double c1, double c2, double[][] gBest, List<Arco> arcosSuperBasicos) {
        Random r = new Random();
        double r1 = r.nextDouble();
        double r2 = r.nextDouble();
        
        double contanteInercia = 0.75;
        
        int numUsinas = posicaoMin.length;
        int numIntervalos = posicao[0].length/2;
        List<Double> direcaoCaminhadaArcosSuperBasico = new ArrayList<>();
        
        for (int i = 0; i < arcosSuperBasicos.size(); i++) {
            int linhaOrigem = arcosSuperBasicos.get(i).getOrigem()[0];
            int colunaOrigem = arcosSuperBasicos.get(i).getOrigem()[1];
            int linhaDestino = arcosSuperBasicos.get(i).getDestino()[0];
            int colunaDestino = arcosSuperBasicos.get(i).getDestino()[1];

            // se o arco superbásico for um arco de volume
            if(linhaOrigem == linhaDestino){
                double direcao = (contanteInercia*velocidade[linhaOrigem][colunaOrigem] + c1 * r1 * (vetorpBest[linhaOrigem][colunaOrigem] - posicao[linhaOrigem][colunaOrigem]) + c2 * r2 * (gBest[linhaOrigem][colunaOrigem] - posicao[linhaOrigem][colunaOrigem]));
                direcaoCaminhadaArcosSuperBasico.add(direcao);
            } else {
                double direcao = (contanteInercia*velocidade[linhaOrigem][colunaOrigem + numIntervalos] + c1 * r1 * (vetorpBest[linhaOrigem][colunaOrigem + numIntervalos] - posicao[linhaOrigem][colunaOrigem + numIntervalos]) + c2 * r2 * (gBest[linhaOrigem][colunaOrigem + numIntervalos] - posicao[linhaOrigem][colunaOrigem + numIntervalos]));
                direcaoCaminhadaArcosSuperBasico.add(direcao);
            }
        }
        
        return direcaoCaminhadaArcosSuperBasico;
    }

    // quem vai atualizar vai ser o Fluxo em Rede
    public void AtualizarPosicao(double[][] matrizFluxo) {
        int numUsinas = posicao.length;
        int numIntervalos = posicao[0].length;
        for (int i = 0; i < numUsinas; i++) {
            for (int j = 0; j < numIntervalos; j++) {
                if(matrizFluxo[i][j] != 0){
                    velocidade[i][j] = matrizFluxo[i][j];
                    posicao[i][j] = posicao[i][j] + velocidade[i][j];
                    if (posicao[i][j] > posicaoMax[i][j]) {
                        posicao[i][j] = posicaoMax[i][j];
                    }
                    if (posicao[i][j] < posicaoMin[i][j]) {
                        posicao[i][j] = posicaoMin[i][j];
                    }
                } 
            }
        }
    }

    
    
    public void AvaliarParticula() {
        int numIntervalos = posicaoMin[0].length/2;
        int numUsinas = posicaoMin.length;
        simulacao.definirVolumesFinais(posicao, numUsinas, numIntervalos);
        simulacao.definirVazoesDefluentes(posicao, numUsinas, numIntervalos);
        avaliacao = simulacao.simularOperacaoEnergeticaPSO(numIntervalos);
        if (avaliacao < pBest) {
            pBest = avaliacao;
            for (int i = 0; i < posicaoMin.length; i++) {
                System.arraycopy(posicao[i], 0, vetorpBest[i], 0, numIntervalos * 2);
            }
        }
    }
    
    
    public void AvaliarParticula2() {
        int numIntervalos = posicaoMin[0].length/2;
        int numUsinas = posicaoMin.length;
        simulacao.definirVolumesFinais(posicao, numUsinas, numIntervalos);
        simulacao.definirVazoesDefluentes(posicao, numUsinas, numIntervalos);
        avaliacao = simulacao.simularOperacaoEnergeticaPSOFinal(numIntervalos);
        if (avaliacao < pBest) {
            pBest = avaliacao;
            for (int i = 0; i < posicaoMin.length; i++) {
                System.arraycopy(posicao[i], 0, vetorpBest[i], 0, numIntervalos * 2);
            }
        }
    }

    
    /**
     * Método que inicializa as partículas com a solução a fio d'água;
     * Também irá inicializar a velocidade
     **/
    public void inicializaParticula(){
        Random r = new Random();
        for (int i = 0; i < posicao.length; i++) {
            for (int j = 0; j < posicao[0].length/2; j++) {
                posicao[i][j] = posicaoMax[i][j];
            }
        }
        
        for (int i = 0; i < posicao.length; i++) {
            for (int j = posicao[0].length/2; j < posicao[0].length; j++){
                posicao[i][j] = simulacao.getNos()[j - posicao[0].length/2][i].getVazaoAfluenteNatural();
            }
        }
        
        velocidade = calculaVelocidadesIniciais(posicao);
    }
    
    
    /**
     *
     * Método para calcular as velocidades iniciais das Partículas. Gera-se um
     * valor aleatório de 0 a 5% do volume máximo da usina para cada mês do
     * período de planejamento, que corresponde a quanto o volume da usina será
     * esvaziado no primeiro mês. Posteriormente, calcula-se o valor do quando
     * aumentará o valor de cada uma das vazões defluentes, dado que a usina
     * esvaziou o seu reservatório.
     *
     * @param posicao matriz com os volumes e as vazões defluentes de cada
     * usina do caso teste (posições das particulas)
     * 
     * @return o valor das velocidades iniciais das partículas.
     *
     */
    public double[][] calculaVelocidadesIniciais(double[][] posicao) {
        // para a conversão dos volumes (em hm3) para vazão (em m3/s)
        double fatorConversao = 1000000.0 / 2628000.0;
        // vetor que irá guardar as velocidades iniciais das usinas
        double[][] velocidadesIniciais = new double[posicao.length][posicao[0].length];
        // vetor que irá guardar os números aleatórios gerados para diminuir o volume das usinas (valor gerado entre 0 e 5%)
        double[] numerosAleatorios = new double[posicao[0].length / 2];
        Random r = new Random();

        // for para gerar os números aleatórios de 0 a 5% (valor que será retirado do volume)
        for (int i = 0; i < numerosAleatorios.length; i++) {
            numerosAleatorios[i] = (0.05) * r.nextDouble();
        }

        // ordenação crescente os números aleatórios gerados
        double menor = 0;
        for (int i = 0; i < numerosAleatorios.length; i++) {
            for (int j = 0; j < numerosAleatorios.length; j++) {
                if (numerosAleatorios[i] < numerosAleatorios[j]) {
                    menor = numerosAleatorios[i];
                    numerosAleatorios[i] = numerosAleatorios[j];
                    numerosAleatorios[j] = menor;
                }
            }
        }

        // definir o quanto vai ser defluido em cada usina
        for (int i = 0; i < posicao.length; i++) {
            for (int j = 0; j < posicao[0].length/2; j++) {
                BigDecimal bd = new BigDecimal(numerosAleatorios[j]).setScale(11, RoundingMode.HALF_EVEN);
                numerosAleatorios[j] = bd.doubleValue();
                velocidadesIniciais[i][j] = -numerosAleatorios[j] * posicao[i][j];
            }
        }

        // cálculo das velocidades iniciais das partículas
        for (int i = 0; i < velocidadesIniciais.length; i++) {
            for (int j = velocidadesIniciais[0].length/2; j < velocidadesIniciais[0].length; j++) {
               // se eu estou na primeira usina e estou consideranco os VOLUMES (não há jusante)
                if (i == 0 && j == posicao[0].length/2) {
                    velocidadesIniciais[i][j] = ((-1) * velocidadesIniciais[i][j - (posicao[0].length/2)] * fatorConversao);
                } else {
                    // se eu não estou na primeira usina, então tenho que considerar (somar) as defluências das usinas a montante
                    if (j == (posicao[0].length/2)) {
                        velocidadesIniciais[i][j] = ((-1) * velocidadesIniciais[i][j - (posicao[0].length/2)] * fatorConversao) + velocidadesIniciais[i - 1][j];
                    }
                }

                // se eu estou na primeira usina e não estou no primeiro mês
                if (i == 0 && j > (posicao[0].length/2)) {
                    // volume que retirei da usina no intervalo atual - volume que retirei da usina no intervalo anterior
                    velocidadesIniciais[i][j] = ((-1) * velocidadesIniciais[i][j - (posicao[0].length/2)] - (-1) * velocidadesIniciais[i][j - (posicao[0].length/2) - 1]) * fatorConversao;
                }

                if (i != 0 && j > (posicao[0].length/2)) {
                    velocidadesIniciais[i][j] = ((-1) * velocidadesIniciais[i][j - (posicao[0].length/2)] - (-1) * velocidadesIniciais[i][j - (posicao[0].length/2) - 1]) * fatorConversao + velocidadesIniciais[i - 1][j];
                }
            }
        }

        return velocidadesIniciais;
    }
    
    
    public void imprimePosicao(){
        System.out.println("POSICAO");
        for (int i = 0; i < posicao.length; i++) {
            if(i != 0){
                System.out.println();
            }
            for (int j = 0; j < posicao[0].length; j++) {
                if(j == posicao[0].length - 1){
                    System.out.print(posicao[i][j]);
                } else {
                    System.out.print(posicao[i][j] + ", ");
                }
            }
        }
        System.out.println("\n");
    }
    
    public void imprimePosicaoFinal(double[] volumesMaximos, double[] volumesMinimos){
        System.out.println("POSICAO");
        for (int i = 0; i < posicao.length; i++) {
            System.out.println();
            for (int j = 0; j < posicao[0].length/2; j++) {
                System.out.println(String.format("%.10f", ((posicao[i][j] - volumesMinimos[i])/(volumesMaximos[i] - volumesMinimos[i]))));
            }
        }
        System.out.println("\n");
    }
    
    
    public void imprimePosicaoFinalNormalizada(){
        System.out.println("POSICAO NORMALIZADA");
        for (int i = 0; i < posicao.length; i++) {
            System.out.println();
            for (int j = 0; j < posicao[0].length; j++) {
                System.out.println(String.format("%.10f", ((velocidadeMax[i][j] - posicao[i][j])/(velocidadeMax[i][j] - velocidadeMin[i][j]))));
            }
        }
        System.out.println("\n");
    }
    
    public void imprimeVolumes() {
        System.out.println("Partícula");
        for (int i = 0; i < posicao.length; i++) {
            if (i != 0) {
                System.out.print("\n{");
            } else {
                System.out.print("{{");
            }

            for (int j = 0; j < posicao[0].length / 2; j++) {
                if (j == posicao[0].length / 2 - 1 && i != posicao.length - 1) {
                    System.out.print(posicao[i][j] + "},");
                } else if (j == posicao[0].length / 2 - 1 && i == posicao.length - 1) {
                    System.out.print(posicao[i][j] + "}}");
                } else {
                    System.out.print(posicao[i][j] + ", ");
                }
            }
        }

        System.out.print("\n\nDefluências");
        for (int i = 0; i < posicao.length; i++) {
            System.out.println();
            for (int j = posicao[0].length / 2; j < posicao[0].length; j++) {
                System.out.println(posicao[i][j]);
            }
        }
        System.out.println();
    }
    
    
    
    
    public void imprimeVelocidade(){
        System.out.println("\nVELOCIDADE");
        for (int i = 0; i < velocidade.length; i++) {
            if(i != 0){
                System.out.println();
            }
            for (int j = 0; j < velocidade[0].length; j++) {
                if(j == velocidade[0].length - 1){
                    System.out.print(velocidade[i][j]);
                } else {
                    System.out.print(velocidade[i][j] + ", ");
                }
            }
        }
        System.out.println("\n");
    }
    
    public void imprimePosicaoPBest(){
        for (int i = 0; i < vetorpBest.length; i++) {
            if(i != 0){
                System.out.println();
            }
            for (int j = 0; j < vetorpBest[0].length; j++) {
                if(j == vetorpBest[0].length - 1){
                    System.out.print(vetorpBest[i][j]);
                } else {
                    System.out.print(vetorpBest[i][j] + ", ");
                }
            }
        }
        System.out.println();
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

    public double[][] getVetorpBest() {
        return vetorpBest;
    }

    public void setVetorpBest(double[][] vetorpBest) {
        this.vetorpBest = vetorpBest;
    }

    public double getpBest() {
        return pBest;
    }

    public void setpBest(double pBest) {
        this.pBest = pBest;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }
    
}
