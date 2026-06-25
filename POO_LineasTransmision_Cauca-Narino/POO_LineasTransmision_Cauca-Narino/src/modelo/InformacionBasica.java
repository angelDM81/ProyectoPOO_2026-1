package modelo;

import java.util.ArrayList;

public class InformacionBasica {
    
    private String id;
    private String nombre;
    private String estado;
    private String operador;
    private String FPO;
    private String tipoDeConexion;
    private ArrayList<String> subestacionesRelacionadas = new ArrayList<>();
    private String tipoDeLinea;
    private String acuerdoDeConexionCompartida;
    
    public InformacionBasica(String id, String nombre, String operador, String estado, String FPO, String tipoDeConexion, ArrayList<String> subestacionesRelacionadas, String tipoDeLinea, String acuerdoDeConexionCompartida){
        this.id=id;
        this.nombre=nombre;
        this.estado=estado;
        this.operador=operador;
        this.FPO=FPO;
        this.tipoDeConexion=tipoDeConexion;
        this.subestacionesRelacionadas=subestacionesRelacionadas;
        this.tipoDeLinea=tipoDeLinea;
        this.acuerdoDeConexionCompartida=acuerdoDeConexionCompartida;
    }
    
    //getters
    public String getID(){ return id; }
    public String getNombre(){ return nombre; }
    public String getEstado(){ return estado; }
    public String getOperador(){ return operador; }
    public String getFPO(){ return FPO; }
    public String getTipoConexion(){ return tipoDeConexion; }
    public String getSubestacionesRelacionadas(){ 
        String subestaciones = String.join(" - ", this.subestacionesRelacionadas);
        return subestaciones;
    }
    public String getTipoDeLinea(){ return tipoDeLinea; }
    public String getAcuerdoDeConexionCompartida(){ return acuerdoDeConexionCompartida; }
    
    //setters
    public void setID(String id){ this.id=id; }
    public void setNombre(String nombre){ this.nombre=nombre; }
    public void setEstado(String estado){ this.estado=estado; }
    public void setOperador(String operador){ this.operador=operador; }
    public void setFPO(String FPO){ this.FPO=FPO; }
    public void setTipoConexion(String tipoConexion){ this.tipoDeConexion=tipoConexion; }
    public void setSubestacionesRelacionadas(String subestacion){ this.subestacionesRelacionadas.add(subestacion); }
    public void setTipoDeLinea(String tipoDeLinea){ this.tipoDeLinea=tipoDeLinea; }
    public void setAcuerdoDeConexionCompartida(String acuerdoDeConexionCompartida){ this.acuerdoDeConexionCompartida=acuerdoDeConexionCompartida; }
    
}
