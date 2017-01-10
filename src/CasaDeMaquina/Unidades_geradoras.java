package casaDeMaquina;

public class Unidades_geradoras {
	private String tipo_turbina;
	private double Beta;
	private double alfa;
	
	public Unidades_geradoras(String tipo){
		this.tipo_turbina=tipo;
	}
	
	public double engolimento_max(double hl,double qef,double hef){
		double qmax = 0;
		if(hl>=hef){
			alfa=-1;
		}
		else if(tipo_turbina.equals("Francis")|| tipo_turbina.equals("Pelton")){
			alfa=0.5;
		}else if(tipo_turbina.equals("Kaplan")){
			alfa=0.2;
		}
		qmax=qef*(Math.pow(hl/hef, alfa));
		return qmax;
	}
	
	
	public double Potencia_max(double hl,double pef,double hef){
		double pmax=0;
		if(hl>=hef){
			Beta=0;
		}
		else if(tipo_turbina.equals("Francis")|| tipo_turbina.equals("Pelton")){
			Beta=1.5;
		}else if(tipo_turbina.equals("Kaplan")){
			Beta=1.2;
		}
		pmax=pef*(Math.pow(hl/hef, Beta));
		return pmax;
	}
	
	
	public String getTipo_usina() {
		return tipo_turbina;
	}
	public void setTipo_usina(String tipo_usina) {
		this.tipo_turbina = tipo_usina;
	}
	

}
