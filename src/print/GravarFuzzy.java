package print;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GravarFuzzy {
	FileWriter arq;
	PrintWriter gravarArq ;
	public void gravarVolumes(double[][] volumes) throws IOException{
		arq= new FileWriter("c:\\Users\\Wellington\\Desktop\\Dados teste Simulacao hidreletrica\\fuzzy.txt");
		gravarArq= new PrintWriter(arq);
		gravarEmborcacao(volumes[0]);
		gravarItumbiara(volumes[1]);
		gravarSaoSimao(volumes[2]);
		
		arq.close();
	}
	
	public void gravarEmborcacao(double[] volumes){
		double volumeOperativo=0;
		gravarArq.printf("%n%n%n+------- Volume Operativo Emborcacao ------+%n");

		for(int i=0;i<=1000;i++){
//			volumeOperativo= volumes[i]*(17190-4669)+4669;
//			
			gravarArq.printf("%.10f",volumes[i]);
			gravarArq.println("");
		}
	}
	
	public void gravarItumbiara(double[] volumes){
		double volumeOperativo=0;
		gravarArq.printf("%n%n%n+------- Volume Operativo Itumbiara ------+%n");

		for(int i=0;i<=1000;i++){
			//volumeOperativo= volumes[i]*(17027-4573)+4573;
			
			gravarArq.printf("%.10f",volumes[i]);
			gravarArq.println("");
		}
	}
	
	public void gravarSaoSimao(double[] volumes){
		double volumeOperativo=0;
		gravarArq.printf("%n%n%n+------- Volume Operativo S�o Sim�o ------+%n");

		for(int i=0;i<=1000;i++){
			//volumeOperativo= volumes[i]*(12540-7000)+7000;
			
			gravarArq.printf("%.10f",volumes[i]);
			gravarArq.println("");
		}
	}
}
