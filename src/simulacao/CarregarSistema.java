package simulacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Polinomios.Jusante;
import Polinomios.Montante;
import casaDeMaquina.CatalogoTurb;
import casaDeMaquina.Conjuntos_unidades;
import casaDeMaquina.DescTurbina;
import horizonte.IntervaloDeHorizonte;
import usina.UsinaHidroeletrica;
import usina.UsinaReservatorio;

public class CarregarSistema {

    public void testarSimulacaoUsinasReservatorio(SimulacaoOperacaoEnergeticaPSO sistema) throws IOException {

        int numUsina = 3;
        int grau = 4;

        double[] coefmonta = new double[grau + 1];
        coefmonta[0] = 526.182;
        coefmonta[1] = 6.03170E-03;
        coefmonta[2] = -4.74559E-07;
        coefmonta[3] = 2.14093E-11;
        coefmonta[4] = -3.71519E-16;

        Montante poli = new Montante(grau, coefmonta);
        double[] coeficientesPolinomiosJusante = new double[grau + 1];

        coeficientesPolinomiosJusante[0] = 510.036;
        coeficientesPolinomiosJusante[1] = 1.92840E-03;
        coeficientesPolinomiosJusante[2] = -1.74093E-07;
        coeficientesPolinomiosJusante[3] = 1.21269E-11;
        coeficientesPolinomiosJusante[4] = -3.24194E-16;

        Jusante poliJusante = new Jusante(grau, coeficientesPolinomiosJusante);
        UsinaReservatorio TresMarias = new UsinaReservatorio();
        TresMarias.setCodigo(0);
        TresMarias.setMontante(poli);
        TresMarias.setJusante(poliJusante);

        Conjuntos_unidades conj = new Conjuntos_unidades(1, 6, 66, 154, 50.2);
        DescTurbina turb1 = new DescTurbina(2, "turb1");
        CatalogoTurb catalogo = new CatalogoTurb();
        catalogo.getTurbinas().add(turb1);
        conj.setTurbina(turb1);
        TresMarias.setK_pc(0.0108);
        TresMarias.setK(0.008564);
        TresMarias.setCaracteristicasOperativas(1, 0.08091, 0.02533);
        TresMarias.getConjuntos().add(conj);

        TresMarias.setCf_med(515.70);

        //--------------------------------------Carregar SOBRADINHO---------------------------------------
        coefmonta[0] = 374.178;
        coefmonta[1] = 1.39668E-03;
        coefmonta[2] = -5.35158E-08;
        coefmonta[3] = 1.15598E-12;
        coefmonta[4] = -9.54598E-18;

        Montante montanteSobradinho = new Montante(grau, coefmonta);
        double[] CoefJusanteSobradinho = new double[grau + 1];

        CoefJusanteSobradinho[0] = 358.932;
        CoefJusanteSobradinho[1] = 3.02017E-03;
        CoefJusanteSobradinho[2] = -7.68353E-07;
        CoefJusanteSobradinho[3] = 1.06153E-10;
        CoefJusanteSobradinho[4] = -5.42763E-15;
        Jusante JusanteSobradinho = new Jusante(grau, CoefJusanteSobradinho);

        UsinaReservatorio Sobradinho = new UsinaReservatorio();
        Sobradinho.setCodigo(1);
        Sobradinho.setMontante(montanteSobradinho);
        Sobradinho.setJusante(JusanteSobradinho);
        Conjuntos_unidades conjSobradinho = new Conjuntos_unidades(2, 6, 175, 713, 27.20);
        conjSobradinho.setTurbina(turb1);
        Sobradinho.setK_pc(0.0104);
        Sobradinho.setK(0.009025);
        Sobradinho.getConjuntos().add(conjSobradinho);
        Sobradinho.setCf_med(362.50);
        Sobradinho.setCaracteristicasOperativas(1, 0.08091, 0.02533);

        //-------------------------------------Carregar Itaparica-----------------------------------------
        coefmonta[0] = 275.812;
        coefmonta[1] = 6.76488E-03;
        coefmonta[2] = -8.86836E-07;
        coefmonta[3] = 7.06790E-11;
        coefmonta[4] = -2.23984E-15;

        Montante montanteItaparica = new Montante(grau, coefmonta);
        double[] CoefJusanteItaparica = new double[grau + 1];

        CoefJusanteItaparica[0] = 251.50;
        CoefJusanteItaparica[1] = 0.0;
        CoefJusanteItaparica[2] = 0.0;
        CoefJusanteItaparica[3] = 0.0;
        CoefJusanteItaparica[4] = 0.0;
        Jusante JusanteItaparica = new Jusante(grau, CoefJusanteItaparica);
        UsinaReservatorio Itaparica = new UsinaReservatorio();
        Itaparica.setCodigo(2);
        Itaparica.setMontante(montanteItaparica);
        Itaparica.setJusante(JusanteItaparica);
        Conjuntos_unidades conjItaparica = new Conjuntos_unidades(3, 10, 250, 551, 50.80);
        DescTurbina turb2 = new DescTurbina(1, "turb2");
        catalogo.getTurbinas().add(turb2);
        conjItaparica.setTurbina(turb2);
        Itaparica.setK_pc(0.0155);
        Itaparica.setK(0.008927);
        Itaparica.getConjuntos().add(conjItaparica);
        Itaparica.setCf_med(251.50);
        Itaparica.setCaracteristicasOperativas(1, 0.08091, 0.02533);
        // incluir Usinas no sistema

        UsinaReservatorio[] usinas = new UsinaReservatorio[3];
        usinas[0] = TresMarias;
        usinas[1] = Sobradinho;
        usinas[2] = Itaparica;
        sistema.definirSistemaHidroeletrico(usinas);
        TresMarias.getUsinaAJusante().add(Sobradinho);
        TresMarias.getUsinaAJusante().add(Itaparica);
        Sobradinho.getUsinaAJusante().add(Itaparica);

        Sobradinho.getUsinaImediatamenteAMontante().add(TresMarias);
        Itaparica.getUsinaImediatamenteAMontante().add(Sobradinho);

        // afluencias
        int numIntervalos = 60;
        double[][] afluencia = new double[numIntervalos][numUsina];

        // ---- tres marias
        //------- afluencia de 	TresMarias------
        try {
            FileReader arq = new FileReader("C:\\Users\\Wellington\\Documents\\Estudo-Projetos atual\\PSO\\TesteSimulacaoTSI\\2000-2005\\Três Marias.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            int i = 0;
            // l� a primeira linha // a vari�vel "linha" recebe o valor "null" quando o processo // de repeti��o atingir o final do arquivo texto 
            while (linha != null) {
                //System.out.printf("%s\n", linha); 
                //System.out.println("i = "+ i);
                afluencia[i][0] = Double.parseDouble(linha);
                //System.out.println(vetor[i]);
                i++;
                linha = lerArq.readLine(); // l� da segunda at� a �ltima linha
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        // ----- sobradinho
        try {
            FileReader arq = new FileReader("C:\\Users\\Wellington\\Documents\\Estudo-Projetos atual\\PSO\\TesteSimulacaoTSI\\2000-2005\\Sobradinho.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            int i = 0;
            // l� a primeira linha // a vari�vel "linha" recebe o valor "null" quando o processo // de repeti��o atingir o final do arquivo texto 
            while (linha != null) {
                //System.out.printf("%s\n", linha); 
                //System.out.println("i = "+ i);
                afluencia[i][1] = Double.parseDouble(linha);
                //System.out.println(vetor[i]);
                i++;
                linha = lerArq.readLine(); // l� da segunda at� a �ltima linha
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        // itaparica
        try {
            FileReader arq = new FileReader("C:\\Users\\Wellington\\Documents\\Estudo-Projetos atual\\PSO\\TesteSimulacaoTSI\\2000-2005\\Itaparica.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            int i = 0;
            // l� a primeira linha // a vari�vel "linha" recebe o valor "null" quando o processo // de repeti��o atingir o final do arquivo texto 
            while (linha != null) {
                //System.out.printf("%s\n", linha); 
                //System.out.println("i = "+ i);
                afluencia[i][2] = Double.parseDouble(linha);
                //System.out.println(vetor[i]);
                i++;
                linha = lerArq.readLine(); // l� da segunda at� a �ltima linha
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

//		for(int i=0;i<numIntervalos;i++){
//			
//			for(int j=0;j<numUsina;j++){
//				System.out.printf("Afluencia da usina "+ j+" do intervalo " );
//				afluencia[i][j] = ler.nextDouble();
//			}
//		}
//		
        IntervaloDeHorizonte[] intervalos = new IntervaloDeHorizonte[numIntervalos];

        for (int i = 0; i < numIntervalos; i++) {
            intervalos[i] = new IntervaloDeHorizonte();
        }
        sistema.definirHorizontePlanejamento(0.0, 0.0, 0, 0, 0, intervalos);
        sistema.definirVazoesAfluentesNaturais(afluencia, numUsina, numIntervalos);

        //--- definir volumes iniciais
        double[] volumes = new double[numUsina];

        volumes[0] = 19528;
        volumes[1] = 34116;
        volumes[2] = 10782;
//		
//				for(int j=0;j<numUsina;j++){
//					System.out.printf("volume inicial da usina  "+ j);
//					volumes[j] = ler.nextDouble();
//				}
//			
        sistema.definirVolumeInicial(volumes);

        // --- algumas restri��es operativas
        double[] restricoesVolume = new double[2];
        double[] restricoesDefluencia = new double[2];
        double limiteMaxGH;
        restricoesVolume[0] = 4250;
        restricoesVolume[1] = 19528;
        restricoesDefluencia[0] = 58;
        restricoesDefluencia[1] = 1E20;
        limiteMaxGH = 396;
        sistema.definirRestricoesOperativas(restricoesVolume, 0, restricoesDefluencia, limiteMaxGH);

        restricoesVolume[0] = 5447;
        restricoesVolume[1] = 34116;
        restricoesDefluencia[0] = 506;
        restricoesDefluencia[1] = 1E20;
        limiteMaxGH = 1050;
        sistema.definirRestricoesOperativas(restricoesVolume, 1, restricoesDefluencia, limiteMaxGH);

        restricoesVolume[0] = 7238;
        restricoesVolume[1] = 10782;
        restricoesDefluencia[0] = 501;
        restricoesDefluencia[1] = 1E20;
        limiteMaxGH = 2500;
        sistema.definirRestricoesOperativas(restricoesVolume, 2, restricoesDefluencia, limiteMaxGH);

        //-----Demanda
        //double[] demanda=new double[numIntervalos];
//		for(int j=0;j<numIntervalos;j++){
//			demanda[j]=1500;
//		}
//		FileWriter arq = new FileWriter("c:\\Users\\Wellington\\arquivo.txt");
//		PrintWriter gravarArq = new PrintWriter(arq);
        //	double gh=sistema.simularOperacaoEnergeticaPSO(demanda,numIntervalos);
//		arq.close();
//		 gh=sistema.simularOperacaoEnergetica(demanda,numIntervalos);
        //	Gravar print=new Gravar();
        //print.gravarValores(sistema, numIntervalos, numUsina);
        //System.out.println("geracao "+ gh);
    }

    public void UsinasMinas(SimulacaoOperacaoEnergeticaPSO sistema) throws IOException {
        int numUsina = 3;

        int grau = 4;
//						---- Emborca��o----
        double[] coefmonta = new double[grau + 1];

        double[] coeficientesPolinomioMontante = new double[grau + 1];
        coeficientesPolinomioMontante[0] = 568.09;
        coeficientesPolinomioMontante[1] = 1.4506E-02;
        coeficientesPolinomioMontante[2] = -1.2028E-06;
        coeficientesPolinomioMontante[3] = 5.8303E-11;
        coeficientesPolinomioMontante[4] = -1.1245E-15;

        Montante poli = new Montante(grau, coeficientesPolinomioMontante);
        double[] coeficientesPolinomiosJusante = new double[grau + 1];

        coeficientesPolinomiosJusante[0] = 519.78;
        coeficientesPolinomiosJusante[1] = 3.9966E-03;
        coeficientesPolinomiosJusante[2] = -1.0987E-06;
        coeficientesPolinomiosJusante[3] = 2.3438E-10;
        coeficientesPolinomiosJusante[4] = -1.7646E-14;

        Jusante poliJusante = new Jusante(grau, coeficientesPolinomiosJusante);
        UsinaReservatorio Emborcacao = new UsinaReservatorio();
        Emborcacao.setCodigo(0);
        Emborcacao.setMontante(poli);
        Emborcacao.setJusante(poliJusante);

        Conjuntos_unidades conj = new Conjuntos_unidades(1, 4, 298, 262, 130.30);
        DescTurbina turb1 = new DescTurbina(1, "turbTipo1");
        CatalogoTurb catalogo = new CatalogoTurb();
        catalogo.getTurbinas().add(turb1);
        conj.setTurbina(turb1);

        Emborcacao.setK_pc(0.0127);
        Emborcacao.setK(0.008731);
        Emborcacao.setCaracteristicasOperativas(1, 0.12122, 0.02917);
        Emborcacao.getConjuntos().add(conj);

        Emborcacao.setCf_med(521.9);

        //--------------------------------------Carregar Itumbiara---------------------------------------
        coeficientesPolinomioMontante[0] = 471.16;
        coeficientesPolinomioMontante[1] = 7.2805E-03;
        coeficientesPolinomioMontante[2] = -5.6098E-07;
        coeficientesPolinomioMontante[3] = 2.59776E-11;
        coeficientesPolinomioMontante[4] = -4.845359E-16;

        Montante montanteItumbiara = new Montante(grau, coeficientesPolinomioMontante);
        //double[]  coeficientesPolinomiosJusante=new double[grau+1];

        coeficientesPolinomiosJusante[0] = 433.0;
        coeficientesPolinomiosJusante[1] = 1.59584E-03;
        coeficientesPolinomiosJusante[2] = -8.177386E-08;
        coeficientesPolinomiosJusante[3] = 3.1735E-12;
        coeficientesPolinomiosJusante[4] = 0.0;
        Jusante JusanteItumbiara = new Jusante(grau, coeficientesPolinomiosJusante);

        UsinaReservatorio Itumbiara = new UsinaReservatorio();
        Itumbiara.setCodigo(1);
        Itumbiara.setMontante(montanteItumbiara);
        Itumbiara.setJusante(JusanteItumbiara);

        Conjuntos_unidades conjItumbiara = new Conjuntos_unidades(2, 6, 380, 537, 80.20);
        conjItumbiara.setTurbina(turb1);

        Itumbiara.setK_pc(0.0120);
        Itumbiara.setK(0.008829);
        Itumbiara.getConjuntos().add(conjItumbiara);
        Itumbiara.setCf_med(435.6);
        Itumbiara.setCaracteristicasOperativas(1, 0.12122, 0.02917);

        //-----------------------S�o Sim�o---------------------------------
        coeficientesPolinomioMontante[0] = 358.33;
        coeficientesPolinomioMontante[1] = 8.6173E-03;
        coeficientesPolinomioMontante[2] = -8.8427E-07;
        coeficientesPolinomioMontante[3] = 5.2933E-11;
        coeficientesPolinomioMontante[4] = -1.2420E-15;

        Montante montanteSaoSimao = new Montante(grau, coeficientesPolinomioMontante);
        //double[]  coeficientesPolinomiosJusante=new double[grau+1];

        coeficientesPolinomiosJusante[0] = 315.59;
        coeficientesPolinomiosJusante[1] = 2.3503E-03;
        coeficientesPolinomiosJusante[2] = -1.3803E-07;
        coeficientesPolinomiosJusante[3] = 5.2340E-12;
        coeficientesPolinomiosJusante[4] = -7.8594E-17;
        Jusante JusanteSaoSimao = new Jusante(grau, coeficientesPolinomiosJusante);

        UsinaReservatorio SaoSimao = new UsinaReservatorio();
        SaoSimao.setCodigo(2);
        SaoSimao.setMontante(montanteSaoSimao);
        SaoSimao.setJusante(JusanteSaoSimao);

        Conjuntos_unidades conjSaoSimao = new Conjuntos_unidades(4, 6, 280, 437, 70.90);
        conjSaoSimao.setTurbina(turb1);

        SaoSimao.setK_pc(0.0062);
        SaoSimao.setK(0.009025);
        SaoSimao.getConjuntos().add(conjSaoSimao);
        SaoSimao.setCf_med(328.1);
        SaoSimao.setCaracteristicasOperativas(1, 0.12122, 0.02917);

        //	-----------------------
        UsinaHidroeletrica[] usinas = new UsinaHidroeletrica[3];
        usinas[0] = Emborcacao;
        usinas[1] = Itumbiara;
        usinas[2] = SaoSimao;
        sistema.definirSistemaHidroeletrico(usinas);

        //definindo as usinas a jusantes de cada uma
        Emborcacao.getUsinaAJusante().add(Itumbiara);
        Emborcacao.getUsinaAJusante().add(SaoSimao);
        Itumbiara.getUsinaAJusante().add(SaoSimao);

        //definindo as usinas a montante de cada uma
        Itumbiara.getUsinaImediatamenteAMontante().add(Emborcacao);
        SaoSimao.getUsinaImediatamenteAMontante().add(Itumbiara);

        /*
		 * 
		 * Volumes iniciais == maximo ???
		 * 
		 * 
         */
        int numIntervalos = 12;
        double[][] afluencia = new double[numIntervalos][numUsina];
//Scanner ler = new Scanner(System.in);

        //------- afluencia de Emborcao------
        try {
            FileReader arq = new FileReader("Vazoes Afluentes\\Emborcação.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            int i = 0;
            // l� a primeira linha // a vari�vel "linha" recebe o valor "null" quando o processo // de repeti��o atingir o final do arquivo texto 
            while (i < numIntervalos) {
                //System.out.printf("%s\n", linha); 
                //System.out.println("i = "+ i);

                afluencia[i][0] = 0.8*Double.parseDouble(linha);
                //System.out.println(vetor[i]);
                i++;

                linha = lerArq.readLine(); // l� da segunda at� a �ltima linha
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        //------- afluencia de Itumbiara------
        try {
            FileReader arq = new FileReader("Vazoes Afluentes\\Itumbiara.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            int i = 0;
            // l� a primeira linha // a vari�vel "linha" recebe o valor "null" quando o processo // de repeti��o atingir o final do arquivo texto 
            while (i < numIntervalos) {
                //System.out.printf("%s\n", linha); 
                //System.out.println("i = "+ i);
                afluencia[i][1] = 0.8*Double.parseDouble(linha);
                //System.out.println(vetor[i]);
                i++;
//                                                if(i==3)
//                                                    break;
                linha = lerArq.readLine(); // l� da segunda at� a �ltima linha
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        //------- afluencia de S�o Sim�o------
        try {
            FileReader arq = new FileReader("Vazoes Afluentes\\São Simão.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            int i = 0;
            // l� a primeira linha // a vari�vel "linha" recebe o valor "null" quando o processo // de repeti��o atingir o final do arquivo texto 
            while (i < numIntervalos) {
                //System.out.printf("%s\n", linha); 
                //System.out.println("i = "+ i);
                afluencia[i][2] = 0.8*Double.parseDouble(linha);
                //System.out.println(vetor[i]);
                i++;
//                                                if(i==3)
//                                                    break;    
                linha = lerArq.readLine(); // l� da segunda at� a �ltima linha
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        //---- definindo as demandas
        double[] demanda = new double[numIntervalos];
        for (int i = 0; i < numIntervalos; i++) {
            demanda[i] = 2500;
        }

        IntervaloDeHorizonte[] intervalos = new IntervaloDeHorizonte[numIntervalos];

        for (int i = 0; i < numIntervalos; i++) {
            intervalos[i] = new IntervaloDeHorizonte();
        }
        sistema.definirHorizontePlanejamento(0.0, 0.0, 0, 0, 0, intervalos);
        sistema.definirVazoesAfluentesNaturais(afluencia, numUsina, numIntervalos);

        double[] restricoesVolume = new double[2];
        double[] restricoesDefluencia = new double[2];
        //double limiteMaxGH;

        restricoesVolume[0] = 4669;
        restricoesVolume[1] = 17190;
        restricoesDefluencia[0] = 77;
        restricoesDefluencia[1] = 1E20;
        double limiteMaxGH = 1192;
        sistema.definirRestricoesOperativas(restricoesVolume, 0, restricoesDefluencia, limiteMaxGH);

        restricoesVolume[0] = 4573;
        restricoesVolume[1] = 17027;
        restricoesDefluencia[0] = 254;
        restricoesDefluencia[1] = 1E20;
        limiteMaxGH = 2280;
        sistema.definirRestricoesOperativas(restricoesVolume, 1, restricoesDefluencia, limiteMaxGH);

        restricoesVolume[0] = 7000;
        restricoesVolume[1] = 12540;
        restricoesDefluencia[0] = 408;
        restricoesDefluencia[1] = 1E20;

        ///-------------------verificar esse limite maximo gera��o hidraulica e pq esta indo zero?
        limiteMaxGH = 1680;
        sistema.definirRestricoesOperativas(restricoesVolume, 2, restricoesDefluencia, limiteMaxGH);

        //--- definir volumes iniciais
        double[] volumes = new double[numUsina];

        volumes[0] = 17190;
        volumes[1] = 17027;
        volumes[2] = 12540;

        sistema.definirVolumeInicial(volumes);
    }
}
