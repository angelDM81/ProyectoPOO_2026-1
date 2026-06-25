
package modelo;

public class LineaTransmision {
    
    private InformacionBasica informacionBasica;
    private Capacidad capacidad;
    private Ubicacion ubicacion;
    
    public LineaTransmision(InformacionBasica informacionBasica, Capacidad capacidad, Ubicacion ubicacion){
        this.informacionBasica=informacionBasica;
        this.capacidad=capacidad;
        this.ubicacion=ubicacion;
    }
    
    public String toCSV(){
        
         //el metodo String.join lo que hace es agrupar las subestaciones que estan guardados en el arraylist de subestacionesRelacionadas en una sola linea String, y a cada operador lo separa un -
        String subestacionesRelacionadas = String.join(" - ", informacionBasica.getSubestacionesRelacionadas());
        
        //se concatenan toda la informacion de la linea de transmision en un solo string que va a ser retornando, siendo que cada valor esta separado por un ;
        String linea = informacionBasica.getID()+";"+informacionBasica.getNombre()+";"+informacionBasica.getOperador()+";"+informacionBasica.getEstado()+";"+informacionBasica.getFPO()+";"+informacionBasica.getTipoConexion()+";"+
                           subestacionesRelacionadas+";"+informacionBasica.getTipoDeLinea()+";"+informacionBasica.getAcuerdoDeConexionCompartida()+";"+capacidad.getVoltajeNominal()+";"+capacidad.getVoltajeMaximo()+";"+
                           capacidad.getCorrienteNominal()+";"+capacidad.getCorrienteEmergencia()+";"+capacidad.getLimiteTermico()+";"+capacidad.getLongitudTotal()+";"+ubicacion.getDepartamento()+";"+
                           ubicacion.getMunicipio()+";"+ubicacion.getSubAreaOperativa();
        
        return linea;
    }
    
    //getters
    public Ubicacion getUbicacion(){ return ubicacion; }
    public Capacidad getCapacidad(){ return capacidad; }
    public InformacionBasica getInformacionBasica(){ return informacionBasica; }
   
}
