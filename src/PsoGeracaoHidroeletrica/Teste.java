
package PsoGeracaoHidroeletrica;

import java.io.IOException;

//import Teste.TesteSimulacao;
//import simulacao.SimulacaoOperacaoEnergetica;


public class Teste {
	
	public static void main(String[] args) throws IOException {
		int numUsinas=3;
                int numIntervalos=60;
		int dimensao=numUsinas*numIntervalos;
		int iteracoes=1;
		int c1=2;
		int c2=2;
		int tamPopulacao=20;
		double demanda=1500;
		//TesteSimulacao simulacao= new TesteSimulacao();
		//recebe valores dos volumes finais de uma simula��o com a politica paralela 
		double[] paramvolumesfinais;
                double[] paramVolumesvazoesDefluentes;
             
		PSO pso;
		//double[] melhor=pso.executar();
		//pso.EnergiaArmazenadaMelhor();
		
//		for (int i = 0; i < dimensao; i++) {
//		System.out.println(" melhor " + i +" -> "+melhor[i]);	
//		}
            pso = new PSO(demanda,tamPopulacao,numUsinas,numIntervalos,iteracoes,c1,c2);
            double[][] retorno = pso.executar();
		for(int i=0;i<numUsinas;i++){
                    for(int j = 0;j<numIntervalos;j++)
                        System.out.println();
                }
	}

}
