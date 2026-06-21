
package modelo;

public class Capacidad {
    
    private double voltajeNominal;
    private double voltajeMaximo;
    private double corrienteNominal;
    private double corrienteEmergencia;
    private double limiteTermico;
    private double longitudTotal;
    
    public Capacidad(double voltajeNominal, double voltajeMaximo, double corrienteNominal, double corrienteEmergencia, double limiteTermico, double longitudTotal){
        this.voltajeNominal=voltajeNominal;
        this.voltajeMaximo=voltajeMaximo;
        this.corrienteNominal=corrienteNominal;
        this.corrienteEmergencia=corrienteEmergencia;
        this.limiteTermico=limiteTermico;
        this.longitudTotal=longitudTotal;
    }
    
    public double calcularCapacidadMW(){
        
        double pf=0.95;
        
        double capacidad = (Math.sqrt(3)*this.voltajeNominal*this.corrienteNominal/1000)*pf;
        
        return Math.round(capacidad * 100.0) / 100.0;
    }
   
    //getters
    public double getVoltajeNominal(){ return voltajeNominal; }
    public double getVoltajeMaximo(){ return voltajeMaximo; }
    public double getCorrienteNominal(){ return corrienteNominal; }
    public double getCorrienteEmergencia(){ return corrienteEmergencia; }
    public double getLimiteTermico(){ return limiteTermico; }
    public double getLongitudTotal(){ return longitudTotal; }
    
    //setters
    public void setVoltajeNominal(double voltajeNominal){ this.voltajeNominal=voltajeNominal; }
    public void setVoltajeMaximo(double voltajeMaximo){ this.voltajeMaximo=voltajeNominal; }
    public void setCorrienteNominal(double corrienteNominal){ this.corrienteNominal=corrienteNominal; }
    public void setCorrienteEmergencia(double corrienteEmergencia){ this.corrienteEmergencia=corrienteEmergencia; }
    public void setLimiteTermico(double limiteTermico){ this.limiteTermico=limiteTermico; }
    public void setLongitudTotal(double longitudTotal){ this.longitudTotal=longitudTotal; }
    
}
