package print;

import java.io.FileWriter;
import java.io.PrintWriter;

import horizonte.HorizontePlanejamento;
import rede.NoHidroenergetico;

import java.io.IOException;
//import simulacao.SimulacaoOperacaoEnergetica;
import simulacao.SimulacaoOperacaoEnergeticaPSO;

public class GravarPSO {

    FileWriter arq;
    PrintWriter gravarArq;

    public void gravarValores(SimulacaoOperacaoEnergeticaPSO sistema, int intervalos, int numUsina) throws IOException {
        arq = new FileWriter("c:\\Users\\Wellington\\Desktop\\GeracaoHidraulicaPSO.txt");
        gravarArq = new PrintWriter(arq);
        gravarGeracaoHidraulica(sistema.getHorizontePlanejamento(), intervalos, numUsina);
        gravarGeracaoComplementar(sistema.getHorizontePlanejamento(), intervalos, numUsina);
//		gravarEnergiaArmazenadaNoSistema(sistema.getHorizontePlanejamento(),intervalos,numUsina);
        gravarVolumeInicial(sistema.getNos(), intervalos, numUsina);
        gravarVolumefinal(sistema.getNos(), intervalos, numUsina);
        gravarVolumeMedio(sistema.getNos(), intervalos, numUsina);
        gravarNivelMontante(sistema.getNos(), intervalos, numUsina);
        gravarNivelJusante(sistema.getNos(), intervalos, numUsina);
        gravarAlturaDeQuedaLiquida(sistema.getNos(), intervalos, numUsina);
        gravarVazaoIncremental(sistema.getNos(), intervalos, numUsina);
        gravarVazaoDefluente(sistema.getNos(), intervalos, numUsina);
        gravarEngolimentoMaximo(sistema.getNos(), intervalos, numUsina);
        gravarLimiteMaximoVazaoTurbinada(sistema.getNos(), intervalos, numUsina);
        gravarVazaoTurbinada(sistema.getNos(), intervalos, numUsina);
        gravarVazaoVertida(sistema.getNos(), intervalos, numUsina);
        gravarGeracaoHidraulicaIndividual(sistema.getNos(), intervalos, numUsina);
        gravarGeracaoHidraulicaMaximaContinua(sistema.getNos(), intervalos, numUsina);
        gravarPotenciaMaxima(sistema.getNos(), intervalos, numUsina);
//		gravarProdutividadeMedia(sistema.getNos(),intervalos,numUsina);
//		gravarProdutividadeMediaAcumulada(sistema.getNos(), intervalos, numUsina);
        arq.close();
    }

    public void gravarVolumeMedio(NoHidroenergetico[][] volumeMedio, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+------- volume Medio Usina Emborcao ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", volumeMedio[j][i].getVolumeMedio());
                gravarArq.println("");
            }

        }
    }
    
    public void gravarGeracaoComplementar(HorizontePlanejamento GeracaoComplementar, int intervalos, int numUsina) {

		gravarArq.printf("%n%n%n+------- geracao Complementar ------+%n");
		for (int j = 0; j < intervalos; j++) {
			gravarArq.printf("%.10f", GeracaoComplementar.getIntervalos().get(j).getGeracaoComplementar());
			gravarArq.println("");

		}
	}

    public void gravarVolumeInicial(NoHidroenergetico[][] volumeInicial, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+------- volume Inicial Emborca��o ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", volumeInicial[j][i].getVolumeInicial());
                gravarArq.println("");
            }

        }
    }

    public void gravarNivelMontante(NoHidroenergetico[][] volumeInicial, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+------- nivel montante Emborca��o ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", volumeInicial[j][i].getNivelMontante());
                gravarArq.println("");
            }
        }
    }

    public void gravarNivelJusante(NoHidroenergetico[][] volumeInicial, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+------- nivel jusante Emborca��o ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", volumeInicial[j][i].getNivelJusante());
                gravarArq.println("");
            }
        }
    }

    public void gravarVazaoDefluente(NoHidroenergetico[][] vazaoDefluente, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+------- vazao Defluente UsinaEmborcao ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", vazaoDefluente[j][i].getVazaoDefluente());
                gravarArq.println("");
            }

        }
    }

    public void gravarVolumefinal(NoHidroenergetico[][] volumeFinal, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+------- volume Final Usina Emborcao ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", volumeFinal[j][i].getVolumeFinal());
                gravarArq.println("");
            }
        }
    }

    public void gravarEngolimentoMaximo(NoHidroenergetico[][] EngolimentoMaximo, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+------- Engolimento Maximo Usina Emborcao ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", EngolimentoMaximo[j][i].getEngolimentoMaximo());
                gravarArq.println("");
            }
        }
    }

//public void gravarEngolimentoefetivo(NoHidroenergetico[][] volumeInicial,int intervalos,int numUsina){
//	
//	
//	gravarArq.printf("%n%n%n+------- Engolimento efetivo Emborca��o %d ------+%n");
//for(int j=0;j<intervalos;j++){
//	gravarArq.printf("%.10f");
//	gravarArq.println("");
//}
//
//}
    public void gravarLimiteMaximoVazaoTurbinada(NoHidroenergetico[][] LimiteMaximoVazaoTurbinada, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+------- Limite Maximo Vazao Turbinada Usina Emborcao ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", LimiteMaximoVazaoTurbinada[j][i].getLimiteMaximoVazaoTurbinada());
                gravarArq.println("");
            }
        }
    }

    public void gravarVazaoTurbinada(NoHidroenergetico[][] VazaoTurbinada, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+-------  Vazao Turbinada Usina Emborcao ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", VazaoTurbinada[j][i].getVazaoTurbinada());
                gravarArq.println("");
            }
        }
    }

    public void gravarVazaoIncremental(NoHidroenergetico[][] AfluenciaIncremental, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+-------  Vazao Incremental Usina Emborca��o ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", AfluenciaIncremental[j][i].getVazaoAfluenteIncremental());
                gravarArq.println("");
            }
        }
    }

    public void gravarGeracaoHidraulica(HorizontePlanejamento GeracaoHidraulica, int intervalos, int numUsina) {

        gravarArq.printf("%n%n%n+------- geracao Hidraulica ------+%n");
        for (int j = 0; j < intervalos; j++) {
            gravarArq.printf("%.10f", GeracaoHidraulica.getIntervalos().get(j).getGeracaoHidraulica());
            gravarArq.println("");

        }
    }

//public void gravarEnergiaArmazenadaNoSistema(HorizontePlanejamento EnergiaArmazenadaNoSistema,int intervalos,int numUsina){
//	
//	
//	gravarArq.printf("%n%n%n+------- EnergiaArmazenadaNoSistema ------+%n");
//for(int j=0;j<intervalos;j++){
//	gravarArq.printf("%.10f",EnergiaArmazenadaNoSistema.getIntervalos().get(j).getEnergiaArmazenadaSistema());
//	gravarArq.println("");
//
//}
//}
    public void gravarGeracaoHidraulicaIndividual(NoHidroenergetico[][] GeracaoHidraulicaIndividual, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+------- geracao Hidraulica Usina Emborca��o ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", GeracaoHidraulicaIndividual[j][i].getGeracaoHidraulica());
                gravarArq.println("");
            }
        }
    }

    public void gravarGeracaoHidraulicaMaximaContinua(NoHidroenergetico[][] GeracaoHidraulicaMaximaContinua, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+------- geracao Hidraulica Maxima Continua Usina Emborca��o ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", GeracaoHidraulicaMaximaContinua[j][i].getGeracaoHIdraulicaMaximaContinua());
                gravarArq.println("");
            }
        }
    }

    public void gravarAlturaDeQuedaLiquida(NoHidroenergetico[][] AlturaDeQuedaLiquida, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+------- Altura De Queda Liquida Usina Emborca��o ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", AlturaDeQuedaLiquida[j][i].getAlturaQuedaLiquida());
                gravarArq.println("");
            }
        }
    }

    public void gravarVazaoVertida(NoHidroenergetico[][] VazaoVertida, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+------- Vazao Vertida Usina Emborca��o ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", VazaoVertida[j][i].getVazaoVertida());
                gravarArq.println("");
            }
        }
    }

    public void gravarPotenciaMaxima(NoHidroenergetico[][] PotenciaMaxima, int intervalos, int numUsina) {

        for (int i = 0; i < numUsina; i++) {
            gravarArq.printf("%n%n%n+------- Potencia Maxima Usina Emborca��o ------+%n");
            for (int j = 0; j < intervalos; j++) {
                gravarArq.printf("%.10f", PotenciaMaxima[j][i].getLimiteMaximoGeracaoHidraulica());
                gravarArq.println("");
            }
        }
    }

//public void gravarProdutividadeMedia(NoHidroenergetico[][] ProdutividadeMedia,int intervalos,int numUsina){
//	
//	for(int i=0;i<numUsina;i++){
//		gravarArq.printf("%n%n%n+------- Produtividade Media Individual Usina %d ------+%n",i);
//	for(int j=0;j<intervalos;j++){
//		gravarArq.printf("%.10f",ProdutividadeMedia[j][i].getProdutividadeMediaIndividual());
//		gravarArq.println("");
//	}
//	}
//}
//public void gravarProdutividadeMediaAcumulada(NoHidroenergetico[][] ProdutividadeMediaAcumulada,int intervalos,int numUsina){
//	
//	for(int i=0;i<numUsina;i++){
//		gravarArq.printf("%n%n%n+------- Produtividade Media Acumulada Usina %d ------+%n",i);
//	for(int j=0;j<intervalos;j++){
//		gravarArq.printf("%.10f",ProdutividadeMediaAcumulada[j][i].getProdutividadeMediaAcumulada());
//		gravarArq.println("");
//	}
//	}
//}
}
