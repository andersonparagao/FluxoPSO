package usina;

import java.util.ArrayList;
import java.util.Vector;

import Polinomios.AreaEspelhoDagua;
import Polinomios.Jusante;
import Polinomios.Montante;
import casaDeMaquina.Conjuntos_unidades;
//import fuzzy.BaseRegras;
//import fuzzy.BaseRegras2;

public abstract class UsinaHidroeletrica {

    private double Xmor;
    //private double hl;
    private ArrayList<Conjuntos_unidades> conjuntos = new ArrayList<Conjuntos_unidades>();
    private ArrayList<UsinaHidroeletrica> usinaAJusante = new ArrayList<UsinaHidroeletrica>();
    private ArrayList<UsinaHidroeletrica> usinaImediatamenteAMontante = new ArrayList<UsinaHidroeletrica>();
//	private BaseRegras regras;
   // private BaseRegras2 regras;

//	public void RegrasEm() {
//		
//		double[] bEmb = new double[5];
//		double[] aEmb = new double[5];
//		bEmb[0] = -0.0234557374416864;
//		bEmb[1] = -0.134790499598902;
//		bEmb[2] = -0.118846624980057;
//		bEmb[3] = -0.149226809576278;
//		bEmb[4] = -0.253892775500441;
//		aEmb[0] = 0.644378874545644;
//		aEmb[1] = 1.13606623946794;
//		aEmb[2] = 1.08668443333148;
//		aEmb[3] = 1.12536496779299;
//		aEmb[4] = 1.25198966400944;
//		regras=new BaseRegras(aEmb, bEmb);
//	}
    public void RegrasItum() {

    }

//    public BaseRegras2 getRegras() {
//        return regras;
//
//    }
//
//    public void setRegras(BaseRegras2 regras) {
//        this.regras = regras;
//    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    private int codigo;
    //	private int[] usinasImediatamenteaMontante=new int[10];
//	private int[] usinasAJusante=new int[10];

//	public int[] getUsinasImediatamenteaMontante(){
//		return usinasImediatamenteaMontante;
//	}
    public ArrayList<UsinaHidroeletrica> getUsinaImediatamenteAMontante() {
        return usinaImediatamenteAMontante;
    }

    public void setUsinaImediatamenteAMontante(ArrayList<UsinaHidroeletrica> usinaImediatamenteAMontante) {
        this.usinaImediatamenteAMontante = usinaImediatamenteAMontante;
    }

//	public int[] getUsinaAJusante() {
//		return usinasAJusante;
//	}
    public void setUsinaAJusante(ArrayList<UsinaHidroeletrica> usinaImediatamenteAJusante) {
        this.usinaAJusante = usinaImediatamenteAJusante;
    }

    public ArrayList<UsinaHidroeletrica> getUsinaAJusante() {
        return usinaAJusante;
    }

//	public double getP_max() {
//		return p_max;
//	}
//
//	public void setP_max(double p_max) {
//		this.p_max = p_max;
//	}
    public ArrayList<Conjuntos_unidades> getConjuntos() {
        return conjuntos;
    }

    public void setConjuntos(ArrayList<Conjuntos_unidades> conjuntos) {
        this.conjuntos = conjuntos;
    }

//	public double getHl() {
//		return hl;
//	}
//
//	public void setHl(double hl) {
//		this.hl = hl;
//	}
//	public double getLimiteMaxVazaoTurbinada() {
//		return limiteMaxVazaoTurbinada;
//	}
//
//	public void setLimiteMaxVazaoTurbinada(double limiteMaxVazaoTurbinada) {
//		this.limiteMaxVazaoTurbinada = limiteMaxVazaoTurbinada;
//	}
//	private double q;
//	public double getQ() {
//		return q;
//	}
//
//	public void setQ(double q) {
//		this.q = q;
//	}
    //private double limiteMaxVazaoTurbinada;
    //private double Xmin;
    //private double Xutil;
    private double Xmax_max;
    private double Xseg;
    //private double Xev;
    //private double Ynat;
    //private double Yinc;
    private double Ce;
    //private double qef;
    private double K;
    //private double Xi;
    //private double fator_acoplamento;
    //private double gh_max;
    //private double gh_min;
    //private double u_min;
    //private double u_max;
    //private double pef;
    //private double q_max;
    //private double alfa;
    //private Vector[] coef_polimon = new Vector[5];
    private double cf;
    //private double p_max;
    //private double hef;
    private double fc_max;
    private double tax_if;
    private double tax_man;
    //private double X_final;
    //private double X_max;
    //private double u;
    private double V_max;
    private double V_min;
    //private double gh;
    //private double q_min;
    //private double fc;
    private double K_pc;
    //private double q_med;
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

//	public double getXmin() {
//		return Xmin;
//	}
//	
//	public void setXmin(double xmin) {
//		Xmin = xmin;
//	}
//	public double getXev() {
//		return Xev;
//	}
//
//	public void setXev(double xev) {
//		Xev = xev;
//	}
//	
//	public double getXutil() {
//		return Xutil;
//	}
//	
//	public void setXutil(double xutil) {
//		Xutil = xutil;
//	}
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

//	public double getYnat() {
//		return Ynat;
//	}
//	
//	public void setYnat(double ynat) {
//		Ynat = ynat;
//	}
//	
//	public double getYinc() {
//		return Yinc;
//	}
//	
//	public void setYinc(double yinc) {
//		Yinc = yinc;
//	}
//	
    public double getCe() {
        return Ce;
    }

    public void setCe(double ce) {
        Ce = ce;
    }

//	public double getQef() {
//		return qef;
//	}
//	
//	public void setQef(double qef) {
//		this.qef = qef;
//	}
    public double getK() {
        return K;
    }

    public void setK(double k) {
        K = k;
    }
//	public double getXi() {
//		return Xi;
//	}
//	public void setXi(double xi) {
//		Xi = xi;
//	}
//	public double getFator_acoplamento() {
//		return fator_acoplamento;
//	}
//	public void setFator_acoplamento(double fator_acoplamento) {
//		this.fator_acoplamento = fator_acoplamento;
//	}
//	public double getGh_max() {
//		return gh_max;
//	}
//	public void setGh_max(double gh_max) {
//		this.gh_max = gh_max;
//	}
//	public double getGh_min() {
//		return gh_min;
//	}
//	public void setGh_min(double gh_min) {
//		this.gh_min = gh_min;
//	}
//	public double getU_min() {
//		return u_min;
//	}
//	public void setU_min(double u_min) {
//		this.u_min = u_min;
//	}
//	public double getU_max() {
//		return u_max;
//	}
//	public void setU_max(double u_max) {
//		this.u_max = u_max;
//	}
//	public double getPef() {
//		return pef;
//	}
//	public void setPef(double pef) {
//		this.pef = pef;
//	}
//	public double getQ_max() {
//		return q_max;
//	}
//	public void setQ_max(double q_max) {
//		this.q_max = q_max;
//	}
//	public double getAlfa() {
//		return alfa;
//	}
//	public void setAlfa(double alfa) {
//		this.alfa = alfa;
//	}
//	public double getGmaxCont() {
//		return GmaxCont;
//	}
//
//	public void setGmaxCont(double gmaxCont) {
//		GmaxCont = gmaxCont;
//	}

//	public Vector[] getCoef_polimon() {
//		return coef_polimon;
//	}
//	public void setCoef_polimon(Vector[] coef_polimon) {
//		this.coef_polimon = coef_polimon;
//	}
    public double getCf() {
        return cf;
    }

    public void setCf(double cf) {
        this.cf = cf;
    }

    public void setCaracteristicasOperativas(double FcMax, double taxman,
            double taxIf) {
        //this.K=k;
        this.tax_man = taxman;
        this.tax_if = taxIf;
        this.fc_max = FcMax;
        //this.K_pc=kpc;
        //this.cf_med=cfMed;
//	
//		
//		
//							IMPLEMENTAR 
//				
//		
//		
    }

    public double RegraParalela(double paramVolumeMinimo, double lambda, double paramVolumeMaximo) { //double para teste
        double volumeFinal;
        volumeFinal = paramVolumeMinimo + lambda * (paramVolumeMaximo - paramVolumeMinimo);
        return volumeFinal;
    }

    public double VolumeMedio(double paramVolumeFinal, double volumeInicial) {    //double para teste
        double volumeMedio = (paramVolumeFinal + volumeInicial) / 2;
        return volumeMedio;
    }

//	public double VolumeEvaporado(double coefmes){
//		
//		Xev=0.001*(coefmes)*(AreaEspelho.obterValorPolinomio(montante.obterValorPolinomio(Xi)));
//		return Xev;
//		
//	}
    public double obter_alturaquedabruta(double volume, double vazaodefluente) {
        return (montante.obterValorPolinomio(volume) - jusante.obterValorPolinomio(vazaodefluente));
    }

    public double obter_perda_carga_hidraulica(double u, double x, double produtibilidade_espe, int modelo) {
        double hp = 0;
        double constante = 0;

        switch (modelo) {
            case 1:
                hp = constante;

            case 2:
                hp = produtibilidade_espe * (obter_alturaquedabruta(x, u));

            case 3: ///falta complementar
        }

        return hp;

    }

    public double altura_queda_liquida(double x, double u) {
        double niveljus = jusante.obterValorPolinomio(u);
        double nivelmon = montante.obterValorPolinomio(x);
        double hl = nivelmon - niveljus - obter_perda_carga_hidraulica(u, x, K_pc, 2);
        return hl;

    }

    public double Engolimento_max(double x, double paramvazaodefluenteu) {
        double q;
        double engolimentoMaximoAtual;
        double delta_hl;
        double vazaodefluentedoAlgoritmo = paramvazaodefluenteu;
        double nivelmon = montante.obterValorPolinomio(x);
        double niveljus = jusante.obterValorPolinomio(paramvazaodefluenteu);// verificar direito no codigo
        int div = conjuntos.size();
        double[] hl_conj = new double[conjuntos.size()];
        double[] hl_conjnovo = new double[conjuntos.size()];
        double[] Qconj = new double[conjuntos.size()];
        double[] Qmaq = new double[conjuntos.size()];

        for (int i = 0; i < conjuntos.size(); i++) {
            Qconj[i] = paramvazaodefluenteu / div;
        }
        int j = 0;
        for (Conjuntos_unidades c : conjuntos) {
            Qmaq[j] = Qconj[j] / c.getNum_unidades();
            j++;
        }

        for (int i = 0; i < conjuntos.size(); i++) {
            hl_conj[i] = conjuntos.get(i).altura_liquida(nivelmon, niveljus, K_pc);
        }
        double engolimentoEfetivo = 0;
        for (int i = 0; i < conjuntos.size(); i++) {
            engolimentoEfetivo = engolimentoEfetivo + conjuntos.get(i).getQef() * conjuntos.get(i).getNum_unidades();
        }
        double engolimentoAnterior = engolimentoEfetivo;
        //engolimentoMaximoAtual=0;
        boolean convergencia = false;
        do {
            engolimentoMaximoAtual = 0;
            for (int i = 0; i < conjuntos.size(); i++) {
                hl_conj[i] = conjuntos.get(i).altura_liquida(nivelmon, niveljus, K_pc);
                //System.out.println("altura de queda = "+ hl_conj[i]);
            }

            for (int i = 0; i < conjuntos.size(); i++) {
                engolimentoMaximoAtual = engolimentoMaximoAtual + conjuntos.get(i).Engolimento_max(hl_conj[i]);
            }
            //System.out.println("engolimentomaximoatual = "+engolimentoMaximoAtual);
            if (engolimentoMaximoAtual > paramvazaodefluenteu) {
                vazaodefluentedoAlgoritmo = engolimentoMaximoAtual;
            }
            q = engolimentoMaximoAtual;
            niveljus = jusante.obterValorPolinomio(vazaodefluentedoAlgoritmo);

//			for(int i=0; i<conjuntos.size();i++){
//				hl_conjnovo[i]=conjuntos.get(i).altura_liquida(nivelmon, niveljus, K_pc);
//			}
//			for(int i=0; i<conjuntos.size();i++){
//				hl_conj[i]=hl_conjnovo[i];
//			}
            if (Math.abs(engolimentoMaximoAtual - engolimentoAnterior) < 1) {
                convergencia = true;
            } else {
                engolimentoAnterior = engolimentoMaximoAtual;

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
        } while (convergencia == false);
        return engolimentoMaximoAtual;
    }
//	

    public double P_max() {
        double potenciaMaxima = 0;
        //Engolimento_max(Xi, u);
        for (Conjuntos_unidades c : getConjuntos()) {
            potenciaMaxima = potenciaMaxima + c.P_max(c.getHl());
        }

        return potenciaMaxima;
    }

    public double GeracaoMaximaContinua(double potenciaMaxima, double paramVazaoDefluente, double paramVolumeMedio) {
//		double vazaoDefluente=paramVazaoDefluente;
        double GmaxCont;
        //Engolimento_max(paramVolumeMedio, vazaoDefluente);
        //double potenciaMaxima=P_max();
        GmaxCont = potenciaMaxima * this.fc_max * (1 - this.tax_man) * (1 - this.tax_if);
        return GmaxCont;

    }

    public double geracaoHidraulica(double paramvazaoTurbinada, double paramvolume, double paramvazaoDefluente) {
        double vazaoTurbinada = paramvazaoTurbinada;
        double volume = paramvolume;
        double vazaoDefluente = paramvazaoDefluente;
        double geracaoHidraulica;
        geracaoHidraulica = K * (montante.obterValorPolinomio(volume) - jusante.obterValorPolinomio(vazaoDefluente)
                - obter_perda_carga_hidraulica(vazaoDefluente, volume, K_pc, 2)) * vazaoTurbinada;

        return geracaoHidraulica;

    }

    public abstract double ProdutividadeMedia(double paramVolume, double paramVolumeMinimo);

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

    public double getK_pc() {
        return K_pc;
    }

    public void setK_pc(double k_pc) {
        K_pc = k_pc;
    }

    public double getCf_med() {
        return cf_med;
    }

    public void setCf_med(double cf_med) {
        this.cf_med = cf_med;
    }

}
