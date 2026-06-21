
package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import modelo.Capacidad;
import modelo.InformacionBasica;
import modelo.LineaTransmision;
import modelo.SistemaElectrico;
import modelo.Subestacion;
import modelo.Ubicacion;

public class Lectura {
    
    private int numeroDeLinea=0;
    private int numeroDeSubestacion=0;
    private SistemaElectrico sistemaElectrico = new SistemaElectrico();
    
    public void lecturaLineasDeTransmision() {

        File in = new File("data\\dataLineas.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(in))){
            
            String s;

            while ((s = br.readLine()) != null) {
                
                //sirve para validar que cuando haya lineas en blanco el programa no se explote
                if (s.trim().isEmpty()) continue;

                String token[] = s.split(";");
                String subestacionRelacionada=token[5]; //se define un string que va a guardar las subestaciones relacionadas a la linea de transmision, para despues splitear cada subestacion y guardarla por individual en un arraylist
                String token2[] = subestacionRelacionada.split("-"); //se define el token2, para hacer el split a las subestaciones mediante el caracter de separacion "-"
                ArrayList<String> subestacionesRelacionadas = new ArrayList<>(); //se crea un arraylist que es el que va a guardar a ambas subestaciones
                subestacionesRelacionadas.add(token2[0].trim()); //se agregan la primera linea de transmision al arraylist de subestaciones, y trim limpia los espacios en blanco que puede haber
                subestacionesRelacionadas.add(token2[1].trim()); //se agregan la segunda linea de transmision al arraylist de subestaciones, y trim limpia los espacios en blanco que puede haber
                
                Ubicacion ubicacion = new Ubicacion(token[14], token[15], token[16]);
                InformacionBasica informacionBasica = new InformacionBasica("LT-"+(++numeroDeLinea),token[0], token[1], token[2], token[3], token[4], subestacionesRelacionadas, token[6], token[7]);
                Capacidad capacidad = new Capacidad(Double.parseDouble(token[8].replace(".", "").replace(",", ".")), Double.parseDouble(token[9].replace(".", "").replace(",", ".")), Double.parseDouble(token[10].replace(".", "").replace(",", ".")), Double.parseDouble(token[11].replace(".", "").replace(",", ".")), Double.parseDouble(token[12].replace(".", "").replace(",", ".")), Double.parseDouble(token[13].replace(".", "").replace(",", ".")));
                LineaTransmision lineaTransmision = new LineaTransmision(informacionBasica, capacidad, ubicacion);
                sistemaElectrico.agregarLineasActivas(lineaTransmision);
                
            }
                
        }
        catch(Exception e){
            System.out.println("Error en la lectura del archivo csv: "+e.getMessage()); //getMessage() es un metodo que extrae un mensaje corto explicando la causa directa del error
            e.printStackTrace(); //muestra una lista de archivos y lineas de codigo organizados de manera inversa, que me ayudan a identificar en donde se rompio el programa, mas exactamente en la linea 1
        }
    }
    
    public void lecturaSubestaciones(){
        
        File in = new File("data\\dataSubestaciones.csv");
        
        try(BufferedReader br = new BufferedReader(new FileReader(in))){
            
            String s;
            
            while((s=br.readLine())!=null){
                
                //sirve para validar que cuando haya lineas en blanco el programa no se explote
                if (s.trim().isEmpty()) continue;
               
                String token[] = s.split(";");
                String voltajeNominal=token[1]; //se define un String que guarda los voltajes nominales que tiene la subestacion porque a veces son mas de 1
                String operador=token[2]; //se define otro string que guarda los operadores de la subestacion, porque tambien pueden ser mas de 1
                String token2[] = voltajeNominal.split("-"); //se define el token2 que separa los voltajes en caso de que haya mas de 1
                String token3[] = operador.split("-"); //se define el token 3 que separa los operadores en caso de que haya mas de 1
                
                ArrayList<Double> voltajesNominales = new ArrayList<>(); //se crea un arraylist auxiliar para guardar los voltajes que hay y enviarlo por parametro al constructor
                ArrayList<String> operadores = new ArrayList<>(); //se crea otro arraylist auxiliar para guardar los operadores que hay y enviarlo por parametro al constructor
                
                //este ciclo lo que hace es que va a guardar la cantidad de voltajes nominales que hay en el token2 en el arraylist de voltajes nominales, se parsea a double y se limpia los espacios con trim
                for(int i=0; i<token2.length; i++){
                    voltajesNominales.add(Double.parseDouble(token2[i].trim()));
                }
                
                //este ciclo lo que hace es que va a guardar la cantidad de operadores que hay en el token2 en el arraylist de operadores y se limpia los espacios con trim
                for(int i=0; i<token3.length; i++){
                    operadores.add(token3[i].trim());
                }
                
                Ubicacion ubicacion = new Ubicacion(token[3], token[4], token[5]);
                Subestacion subestacion = new Subestacion("Sub-"+(++numeroDeSubestacion), token[0], voltajesNominales, operadores, ubicacion, 0, 0);
                sistemaElectrico.agregarSubestacionesActivas(subestacion);        
                
            }
        }
        catch(Exception e){
            System.out.println("Error en la lectura del archivo csv: "+e.getMessage()); //getMessage() es un metodo que extrae un mensaje corto explicando la causa directa del error
            e.printStackTrace(); //muestra una lista de archivos y lineas de codigo organizados de manera inversa, que me ayudan a identificar en donde se rompio el programa, mas exactamente en la linea 1
        }
    }
}
