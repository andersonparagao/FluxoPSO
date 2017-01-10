package casaDeMaquina;

import java.util.ArrayList;

public class CatalogoTurb {
	private ArrayList<DescTurbina> turbinas;
	
	
	public CatalogoTurb(){
		turbinas=new ArrayList<DescTurbina>();
		
	}
	

	public ArrayList<DescTurbina> getTurbinas() {
		return turbinas;
	}

	public void setTurbinas(ArrayList<DescTurbina> turbinas) {
		this.turbinas = turbinas;
	}
	
	public void AdcTurbinas(DescTurbina turbina){
		turbinas.add(turbina);
	}
	
	public void RemoveTurbinas(String nome){
		for(int i=0;i<=turbinas.size();i++){
			if(turbinas.get(i).getNome().equals(nome)){
				turbinas.remove(i);
			}
		}
	}

	
}
