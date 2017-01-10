package casaDeMaquina;

public class Conjuntos_unidades {
	private DescTurbina turbina;
	private int id;
	private int num_unidades;
	private double pef;   
	private double hef; 
	private double qef;
	private double qconj;
	private double hl;
	private double hlnovo;
	 
	 public double getHlnovo() {
		return hlnovo;
	}

	public void setHlnovo(double hlnovo) {
		this.hlnovo = hlnovo;
	}

	public double getHl() {
		return hl;
	}

	public void setHl(double hl) {
		this.hl = hl;
	}

	public double getQconj() {
		return qconj;
	}

	public void setQconj(double qconj) {
		this.qconj = qconj;
	}

	public Conjuntos_unidades(int id,int num_unidades,double pef,double qef,double hef){
		 turbina=new DescTurbina();
		 this.num_unidades=num_unidades;
		 this.pef=pef;
		 this.qef=qef;
		 this.hef=hef;
		 this.id=id;
		 
	 }
	 
	 public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DescTurbina getTurbina() {
		return turbina;
	}

	public void setTurbina(DescTurbina turbina) {
		this.turbina = turbina;
	}

	public double getPef() {
		return pef;
	}

	public void setPef(double pef) {
		this.pef = pef;
	}

	public double getHef() {
		return hef;
	}

	public void setHef(double hef) {
		this.hef = hef;
	}

	public double getQef() {
		return qef;
	}

	public void setQef(double qef) {
		this.qef = qef;
	}
	

	public int getNum_unidades() {
		return num_unidades;
	}

	public void setNum_unidades(int num_unidades) {
		this.num_unidades = num_unidades;
	}

	
	
	public double Engolimento_max(double hl){
	double	qmax=num_unidades*turbina.Engolimento_max(hl, hef, qef);
		return qmax;
}
	
	public double P_max(double hl){
		double	pmax=turbina.Potencia_max(hl, hef, pef);
		return pmax*num_unidades;
	}
	
	public double altura_liquida(double hmon,double hjus, double kpc){
		hl= hmon - hjus - kpc*(hmon -hjus);
		//System.out.println("hl -> ="+ hl);
		return hl;
	}
//	public void altura_liquidanovo(double hmon,double hjus, double k){
//		hlnovo= hmon - hjus - k*(hmon -hjus);
//		System.out.println("hlnovo -> ="+ hlnovo);
//		
//	}
	
	

}
