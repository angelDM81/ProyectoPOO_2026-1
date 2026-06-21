package controlador;
import java.util.ArrayList;
import modelo.Subestacion;
import modelo.SistemaElectrico;
public class ContraladorSubestacion {
        private SistemaElectrico sistemaElectrico = new SistemaElectrico();
    
    public ArrayList<Subestacion> obtenerSubestaciones(){
        return sistemaElectrico.getSubestacionesActivas();
    }
        public int obtenerNumeroDeSubestaciones(){
        return sistemaElectrico.getSubestacionesActivas().size();
    }
    
}
