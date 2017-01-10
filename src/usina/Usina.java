package usina;

import java.util.ArrayList;
import java.util.Vector;

import Polinomios.AreaEspelhoDagua;
import Polinomios.Jusante;
import Polinomios.Montante;
import casaDeMaquina.Conjuntos_unidades;

public class Usina {
	private double Xmor;
	private double hl;
	private ArrayList<Conjuntos_unidades> conjuntos=new ArrayList<Conjuntos_unidades>();
	private ArrayList<Usina> usinaAJusante=new ArrayList<Usina>();
	private ArrayList<Usina> usinaImediatamenteAMontante=new ArrayList<Usina>();
	
	public ArrayList<Usina> getUsinaImediatamenteAMontante() {
		return usinaImediatamenteAMontante;
	}

	public void setUsinaImediatamenteAMontante(ArrayList<Usina> usinaImediatamenteAMontante) {
		this.usinaImediatamenteAMontante = usinaImediatamenteAMontante;
	}

	public ArrayList<Usina> getUsinaAJusante() {
		return usinaAJusante;
	}

	public void setUsinaAJusante(ArrayList<Usina> usinaImediatamenteAJusante) {
		this.usinaAJusante = usinaImediatamenteAJusante;
	}

	public double getP_max() {
		return p_max;
	}

	public void setP_max(double p_max) {
		this.p_max = p_max;
	}

	public ArrayList<Conjuntos_unidades> getConjuntos() {
		return conjuntos;
	}

	public void setConjuntos(ArrayList<Conjuntos_unidades> conjuntos) {
		this.conjuntos = conjuntos;
	}

	public double getHl() {
		return hl;
	}

	public void setHl(double hl) {
		this.hl = hl;
	}
	
	
	public double getLimiteMaxVazaoTurbinada() {
		return limiteMaxVazaoTurbinada;
	}

	public void setLimiteMaxVazaoTurbinada(double limiteMaxVazaoTurbinada) {
		this.limiteMaxVazaoTurbinada = limiteMaxVazaoTurbinada;
	}

	private double q;
	public double getQ() {
		return q;
	}

	public void setQ(double q) {
		this.q = q;
	}

	private double limiteMaxVazaoTurbinada;
	private double Xmin;
	private double Xutil;
	private double Xmax_max;
	private double Xseg;
	private double Xev;
	private double Ynat;
	private double Yinc;
	private double Ce;
	private double qef;
	private double K;
	private double Xi;
	private double fator_acoplamento;
	private double gh_max;
	private double gh_min;
	private double u_min;
	private double u_max;
	private double pef;
	private double q_max;
	private double alfa;
	private Vector[] coef_polimon = new Vector[5];
	private double cf;
	private double p_max;
	private double hef;
	private double fc_max;
	private double tax_if;
	private double tax_man;
	private double X_final;
	private double X_max;
	private double u;
	private double V_max;
	private double V_min;
	private double gh;
	private double q_min;
	private double fc;
	private double K_pc;
	private double q_med;
	private double cf_med;
	private Jusante jusante;
	
	public Jusante getJusante() {
		return jusante;
	}

	public void setJusante(Jusante jusante) {
		this.jusante = jusante;
	}

	public Montante getMontante() {
		return montante;
	}

	public void setMontante(Montante montante) {
		this.montante = montante;
	}
	private Montante montante;
	private AreaEspelhoDagua AreaEspelho;
	
	public AreaEspelhoDagua getAreaEspelho() {
		return AreaEspelho;
	}

	public void setAreaEspelho(AreaEspelhoDagua areaEspelho) {
		AreaEspelho = areaEspelho;
	}

	public double getXmor() {
		return Xmor;
	}
	
	public void setXmor(double xmor) {
		Xmor = xmor;
	}
	
	public double getXmin() {
		return Xmin;
	}
	
	public void setXmin(double xmin) {
		Xmin = xmin;
	}
	
	public double getXev() {
		return Xev;
	}

	public void setXev(double xev) {
		Xev = xev;
	}
	
	public double getXutil() {
		return Xutil;
	}
	
	public void setXutil(double xutil) {
		Xutil = xutil;
	}
	
	public double getXmax_max() {
		return Xmax_max;
	}
	
	public void setXmax_max(double xmax_max) {
		Xmax_max = xmax_max;
	}
	
	public double getXseg() {
		return Xseg;
	}
	
	public void setXseg(double xseg) {
		Xseg = xseg;
	}
	
	public double getYnat() {
		return Ynat;
	}
	
	public void setYnat(double ynat) {
		Ynat = ynat;
	}
	
	public double getYinc() {
		return Yinc;
	}
	
	public void setYinc(double yinc) {
		Yinc = yinc;
	}
	
	public double getCe() {
		return Ce;
	}
	
	public void setCe(double ce) {
		Ce = ce;
	}
	
	public double getQef() {
		return qef;
	}
	
	public void setQef(double qef) {
		this.qef = qef;
	}
	public double getK() {
		return K;
	}
	public void setK(double k) {
		K = k;
	}
	public double getXi() {
		return Xi;
	}
	public void setXi(double xi) {
		Xi = xi;
	}
	public double getFator_acoplamento() {
		return fator_acoplamento;
	}
	public void setFator_acoplamento(double fator_acoplamento) {
		this.fator_acoplamento = fator_acoplamento;
	}
	public double getGh_max() {
		return gh_max;
	}
	public void setGh_max(double gh_max) {
		this.gh_max = gh_max;
	}
	public double getGh_min() {
		return gh_min;
	}
	public void setGh_min(double gh_min) {
		this.gh_min = gh_min;
	}
	public double getU_min() {
		return u_min;
	}
	public void setU_min(double u_min) {
		this.u_min = u_min;
	}
	public double getU_max() {
		return u_max;
	}
	public void setU_max(double u_max) {
		this.u_max = u_max;
	}
	public double getPef() {
		return pef;
	}
	public void setPef(double pef) {
		this.pef = pef;
	}
	public double getQ_max() {
		return q_max;
	}
	public void setQ_max(double q_max) {
		this.q_max = q_max;
	}
	public double getAlfa() {
		return alfa;
	}
	public void setAlfa(double alfa) {
		this.alfa = alfa;
	}
	public double getGmaxCont() {
		return GmaxCont;
	}

	public void setGmaxCont(double gmaxCont) {
		GmaxCont = gmaxCont;
	}

	public Vector[] getCoef_polimon() {
		return coef_polimon;
	}
	public void setCoef_polimon(Vector[] coef_polimon) {
		this.coef_polimon = coef_polimon;
	}
	public double getCf() {
		return cf;
	}
	public void setCf(double cf) {
		this.cf = cf;
	}
	
	public double getXmed() {
		return Xmed;
	}

	public void setXmed(double xmed) {
		Xmed = xmed;
	}

	public void InserirVolume(double x){
		Xi=x;
	}
	public void InserirAfluenciaNatural(double ynat){
		Ynat=ynat;
	}
	
	double Xmed;
	double GmaxCont;
	
	
	public void AfluenciaIncremental(){
		double ynatUsinaAMontante = 0;
		for(Usina ui: getUsinaImediatamenteAMontante()){
			ynatUsinaAMontante=ui.getYnat() + ynatUsinaAMontante;
		}
		Yinc=Ynat - ynatUsinaAMontante;
	}
	
	public double ObterLambda(double AES,double AESMAX){ // double para testar
		double lambda=AESMAX/AES;
		return lambda;
		
	}
	
	public double RegraParalela(double lambda){ //double para teste
		X_final=Xmin + lambda*(X_max - Xmin);
		return X_final;
	}
	
	public double VolumeMedio(){    //double para teste
		double Xmed=(X_final+ Xi)/2;
		return Xmed;
	}
	
	public double VolumeEvaporado(double coefmes){
		
		Xev=0.001*(coefmes)*(AreaEspelho.obterValorPolinomio(montante.obterValorPolinomio(Xi)));
		return Xev;
		
	}
	
	public double BalancoHidrico(double tempo){
		double uImediatamenteAMontante=0;
		for(Usina ui: getUsinaImediatamenteAMontante()){
			uImediatamenteAMontante=uImediatamenteAMontante+ ui.getU();
		}
		u=(1000000/tempo)*((Yinc+ uImediatamenteAMontante)*(tempo/1000000) + Xi - Xev - X_final); //fator a direita esta tudo em volume e o fator a esquerda para transformar em vaz�o
		return u;
		
	}
	
	public void ResolucaoDeConflitos(double tempo){
		if((u<u_min)||(u>u_max)){
			if(u<u_min){
			u=u_min;
			}
			else{
				u=u_max;
			}
			double uImediatamenteAMontante=0;
			for(Usina ui: getUsinaImediatamenteAMontante()){
				uImediatamenteAMontante=uImediatamenteAMontante+ ui.getU();
			}
			X_final=Xi- Xev + (Yinc +  uImediatamenteAMontante - u)*(tempo/1000000);
			VolumeMedio();
		}
	}
	
	public double VazaoAfluente(){
		double YImediatamenteAMontante=0;
		for(Usina ui: getUsinaImediatamenteAMontante()){
			YImediatamenteAMontante=YImediatamenteAMontante+ ui.getU();
		}
		double vazaoafluente=Yinc+ YImediatamenteAMontante;
		return vazaoafluente;
	}
	
	
	
	
	public double obter_alturaquedabruta(double volume,double vazaodefluente){
		return (montante.obterValorPolinomio(volume)- jusante.obterValorPolinomio(vazaodefluente));
	}
	
	
	public double obter_perda_carga_hidraulica(double u,double x,double produtibilidade_espe,int modelo){
		double hp = 0;
		double constante = 0;
		
		switch(modelo){
		case 1: hp=constante;
		
		case 2: hp = produtibilidade_espe*(obter_alturaquedabruta(x,u));
		
		case 3: ///falta complementar
		}
		
		
		
		
		return hp;
		
		
		
	}
	
	public double altura_queda_liquida(double x,double u){
		double niveljus=jusante.obterValorPolinomio(u);
		double nivelmon=montante.obterValorPolinomio(x);
		double hl= nivelmon - niveljus - obter_perda_carga_hidraulica( u, x, K_pc, 2);
		return hl;
		
	
	}
	
	
	
	public double Engolimento_max(double x,double paramvazaodefluenteu) {
		double q;
		double delta_hl ;
		double vazaodefluentedoAlgoritmo=paramvazaodefluenteu;
		double nivelmon=montante.obterValorPolinomio(x);
		double niveljus=jusante.obterValorPolinomio(paramvazaodefluenteu);// verificar direito no codigo
		int div=conjuntos.size();
		double[] hl_conj=new double[conjuntos.size()];
		double[] hl_conjnovo=new double[conjuntos.size()];
		double[] Qconj=new double[conjuntos.size()];
		double[] Qmaq=new double[conjuntos.size()];
		
		for(int i=0; i<conjuntos.size();i++){
			Qconj[i]=paramvazaodefluenteu/div;
		}
		int j=0;
		for(Conjuntos_unidades c : conjuntos){
			Qmaq[j]=Qconj[j]/c.getNum_unidades();
			j++;
		}
		
		
		for(int i=0; i<conjuntos.size();i++){
			hl_conj[i]=conjuntos.get(i).altura_liquida(nivelmon, niveljus,K_pc);
		}
		double engolimentoEfetivo=0;
		for(int i=0;i<conjuntos.size();i++){
			engolimentoEfetivo=engolimentoEfetivo+ conjuntos.get(i).getQef()*conjuntos.get(i).getNum_unidades(); 
		}
		double engolimentoAnterior=engolimentoEfetivo;
		double engolimentoMaximoAtual=0;
		boolean convergencia=false;
		do{
			for(int i=0; i<conjuntos.size();i++){
				hl_conj[i]=conjuntos.get(i).altura_liquida(nivelmon, niveljus,K_pc);
			}
			
			for(int i=0; i<conjuntos.size();i++){
				engolimentoMaximoAtual=conjuntos.get(i).Engolimento_max(hl_conj[i]);
			}
			if(engolimentoMaximoAtual>paramvazaodefluenteu){
				vazaodefluentedoAlgoritmo=engolimentoMaximoAtual;
			}
			q=engolimentoMaximoAtual;
			niveljus=jusante.obterValorPolinomio(vazaodefluentedoAlgoritmo);
			
//			for(int i=0; i<conjuntos.size();i++){
//				hl_conjnovo[i]=conjuntos.get(i).altura_liquida(nivelmon, niveljus, K_pc);
//			}
//			for(int i=0; i<conjuntos.size();i++){
//				hl_conj[i]=hl_conjnovo[i];
//			}
			if(Math.abs(engolimentoMaximoAtual-engolimentoAnterior)<1){
				convergencia=true;
			}
			else{
				engolimentoAnterior=engolimentoMaximoAtual;
				
			}
			
//			delta_hl=-1;
//			
//			for(int i=0; i<conjuntos.size();i++){
//				double   hl_hlnovo=Math.abs( ((hl_conjnovo[i]- hl_conj[i])/hl_conj[i]));
//				if(delta_hl<hl_hlnovo){
//					delta_hl=hl_hlnovo;
//				}
//			}
//			
//			for(int i=0; i<conjuntos.size();i++){
//				hl_conj[i]=hl_conjnovo[i];
//			}
//		
		} while(convergencia==false);
		
		return engolimentoMaximoAtual;
	}
//	
	
	public double P_max() {
		p_max=0;
	    //Engolimento_max(Xi, u);
		for(Conjuntos_unidades c: getConjuntos()){
			p_max=p_max + c.P_max(c.getHl());
		}
		
		
		return p_max;
	}
	
	
	public double GeracaoMaximaContinua(double fcmax,double tman,double tif){
		Engolimento_max(Xmed, u);
		P_max();
		GmaxCont= p_max*fcmax*(1-tman)*(1-tif);
		return GmaxCont;
		
	}   
	
	public double geracaoHidraulica(double vazaoTurbinada,double volume,double vazaoDefluente){
		
		double hl=altura_queda_liquida(volume, vazaoDefluente);
		//System.out.println("hl1 -> " + hl);
		//System.out.println("nivel montante -> " +montante.obterValorPolinomio(volume) );
		//System.out.println("nivel Jusante -> " + jusante.obterValorPolinomio(vazaoDefluente));
	
		gh=K*(montante.obterValorPolinomio(volume)- jusante.obterValorPolinomio(vazaoDefluente) 
				- obter_perda_carga_hidraulica(vazaoDefluente, volume, K_pc, 2))*vazaoTurbinada;
		
		return gh;
		
	}
	
	
	public double NivelMedioMontante(double X){
		double Hmedmon=0;
		if(X==Xmin){
			Hmedmon=montante.obterValorPolinomio(X);
		}else{
		//double integralxi=montante.obterValorIntegral(Xi);
		//double integralxmin=montante.obterValorIntegral(Xmin);
		double valorIntegral=montante.obterValorIntegral(X)- montante.obterValorIntegral(X);
		Hmedmon=(1/(X - Xmin))*valorIntegral;
		}
		
		return Hmedmon;
		
		
	}
	
	
	public double PerdaCargaHidraulicaMedia(double X){
		double HP_med=0;
		
		HP_med=K_pc*(NivelMedioMontante(X) - cf_med);
		//System.out.println("HP_med -> " +HP_med);
		
		return HP_med;
	}
	
	public double AlturaQuedaLiquidaMedia(double X){
		double HL_med=0;
		HL_med=NivelMedioMontante(X) - cf_med - PerdaCargaHidraulicaMedia(X);
		
		return HL_med;
	}
	
	
	public double ProdutividadeMedia(double X){
		double produtividadeMedia = 0;
		if(Xi>X){
			Xi=X;
		}
		produtividadeMedia=K*(AlturaQuedaLiquidaMedia(X));
		
		return produtividadeMedia;
	}
	
	public double ProdutividadeMediaAcumulada(double X){
		double PMacum=0;
		PMacum=ProdutividadeMedia(X);
		
		for(Usina Ui: getUsinaAJusante()){
			PMacum= Ui.ProdutividadeMedia(X)+ PMacum; 
		}
		
		return PMacum;
		
	}
	
	
	
	public double EnergiaArmazenadaNoSistema(){
		double EAS = 0;
		if(Xi>X_max){
			Xi=X_max;
		}
		EAS=ProdutividadeMediaAcumulada(Xi);
				EAS=EAS*(Xi - Xmin);
		return EAS;
	}
	
	public double EnergiaArmazenadaMaximaNoSistema(){
		double EAS = 0;
		if(Xi>X_max){
			Xi=X_max;
		}
		EAS=ProdutividadeMediaAcumulada(X_max);
				EAS=EAS*(X_max - Xmin);
		return EAS;
	}
	
	
	
//	public void setP_max() {
//		this.p_max = p_max;
//	}
	public double getHef() {
		return hef;
	}
	public void setHef(double hef) {
		this.hef = hef;
	}
	public double getFc_max() {
		return fc_max;
	}
	public void setFc_max(double fc_max) {
		this.fc_max = fc_max;
	}
	public double getTax_if() {
		return tax_if;
	}
	public void setTax_if(double tax_if) {
		this.tax_if = tax_if;
	}
	public double getTax_man() {
		return tax_man;
	}
	public void setTax_man(double tax_man) {
		this.tax_man = tax_man;
	}
	public double getX_final() {
		return X_final;
	}
	public void setX_final(double x_final) {
		X_final = x_final;
	}
	public double getX_max() {
		return X_max;
	}
	public void setX_max(double x_max) {
		X_max = x_max;
	}
	public double getU() {
		return u;
	}
	public void setU(double u) {
		this.u = u;
	}
	public double getV_max() {
		return V_max;
	}
	public void setV_max(double v_max) {
		V_max = v_max;
	}
	public double getV_min() {
		return V_min;
	}
	public void setV_min(double v_min) {
		V_min = v_min;
	}
	public double getGh() {
		return gh;
	}
	public void setGh(double gh) {
		this.gh = gh;
	}
	public double getQ_min() {
		return q_min;
	}
	public void setQ_min(double q_min) {
		this.q_min = q_min;
	}
	public double getFc() {
		return fc;
	}
	public void setFc(double fc) {
		this.fc = fc;
	}
	public double getK_pc() {
		return K_pc;
	}
	public void setK_pc(double k_pc) {
		K_pc = k_pc;
	}
	public double getQ_med() {
		return q_med;
	}
	public void setQ_med(double q_med) {
		this.q_med = q_med;
	}
	public double getCf_med() {
		return cf_med;
	}
	public void setCf_med(double cf_med) {
		this.cf_med = cf_med;
	}
	
	
}
