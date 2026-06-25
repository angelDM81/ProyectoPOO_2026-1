
package vista;

import controlador.ControladorSubestacion;
import controlador.ControladorLinea;

public class GUI extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GUI.class.getName());
    private ControladorLinea contraladorLinea = new ControladorLinea();
    private ControladorSubestacion contraladorSubE = new ControladorSubestacion();
    private PanelLinea panelLinea = new PanelLinea();
    private PanelSubEstacion panelSubE = new PanelSubEstacion();
    private String idSelec;
    
    //aqui se usa tableRowSorter que es un motor de java swing que se encarga de ordenar y filtrar de manera visual las filas de una tabla, sin modificar los datos originales
    private javax.swing.table.TableRowSorter sorter = (javax.swing.table.TableRowSorter) panelLinea.getJTable2().getRowSorter();

    public GUI() {
        initComponents();
        jPanel1.setVisible(false);
        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        jPanel11.setVisible(false);
        jButton1.setVisible(false);
    }
    
    private void aplicarFiltros() {
    
  
        //DECLARAMOS SORTER FUERA DEL CONDICIONAL PARA ASIGNARLE UN VALOR SEGUN LA TABLA ABIERTA
        javax.swing.table.TableRowSorter<?> sorter = null;
        //usamos el if else if para asignarle un valor al sorter
        if(panelLinea.getJTable2().isShowing()){
            sorter = (javax.swing.table.TableRowSorter) panelLinea.getJTable2().getRowSorter();
        }
        else if(panelSubE.getJTable1().isShowing()){
            sorter = (javax.swing.table.TableRowSorter) panelSubE.getJTable1().getRowSorter(); 
        }
        
        if(sorter != null){
        
            //Se crea un arraylist encargado de almacenar todos los filtros individuales que se encuentren activos
            //El RowFilter<Object, Object> indica que los filtros trabajarán con cualquier tipo de modelo de tabla
            java.util.List<javax.swing.RowFilter<Object, Object>> listaFiltros = new java.util.ArrayList<>();

            //textoBusqueda recolecta lo que el usuario escribe y elimina los espacios en blanco a los lados
            String textoBusqueda = jTextField1.getText().trim();

            //Este if es para que cuando el text field este vacio, no haya filtros y se muestren todas las filas
            if(!textoBusqueda.isEmpty()){
        
                //Al ser ids alfanumericas largos, la busqueda es directa desde el inicio
                //(?i) ignora mayúsculas/minúsculas
                //^ asegura que la coincidencia empiece desde el primer carácter de la celda
                //Pattern.quote() convierte el texto ingresado en un literal seguro, evitando errores si el usuario teclea símbolos especiales por accidente
                String regexId = "(?i)^" + java.util.regex.Pattern.quote(textoBusqueda);
        
                // Esta línea aplica el filtro de búsqueda establecido en regexId únicamente a la columna de las id (índice 0)
                listaFiltros.add(javax.swing.RowFilter.regexFilter(regexId, 0));
            }

            
            //Este if evalúa si el usuario seleccionó una opción valida dentro del combobox.
            //El indice 0 corresponde al texto descriptivo Voltaje Nominal, por lo que si el índice es mayor a 0 
            //significa que el usuario eligio un voltaje real como 115-110kV o 230-220kV y se debe proceder con el filtro
            if (jComboBox4.getSelectedIndex() > 0) {
                
                //Extrae el objeto seleccionado actualmente en el combobox y lo convierte en una cadena de texto string para poder manipularlo
                String seleccion = jComboBox4.getSelectedItem().toString();
                
                //El metodo replaceAll aplica una expresion regular (regex) para limpiar el texto seleccionado
                //La expresion [^0-9\\-] sirve para reemplazar cualquier caracter que no sea un numero por "", ya que la expresion ^0-9 significa que va a buscar todo lo que no sea un numero
                //y el \\- sirve para indicar que tambien se quiere conservar el guion y no se desea reemplazar, se ponen las \\ por delante, debido a que el - indica un rango, entonces para que no lo inteprete como rango 
                //si no como un caracter que se quiere guardar se ponen las // por delante
                //En pocas palabras busca cualquier carácter que NO sea un número, ni un guion como las letras kV y lo reemplaza por nada ("")
                String soloNumeros = seleccion.replaceAll("[^0-9\\-]", "");
                
                //Se le aplica a la cadena anterior, para separar los dos valores de voltaje de la Jboombox que estan separados por -, y guardarlos en un arreglo
                String[] partes = soloNumeros.split("-");
                
                //String.join une los elementos del arreglo anterior poniendo entre ambos el caracter "|", que en expresiones regulares significa "O" logico
                //Al envolverlo entre parentesis se crea una expresion regex que le dira al motor de busqueda que por ejemplo: Encuentre el numero 115 O el número 110
                String regexVoltaje = "(" + String.join("|", partes) + ")";
            
                //Esta linea crea un filtro basado en la expresion regular generada regexVoltaje y lo programa para que busque unicamente en la columna con indice 2 de la tabla, que es donde estan los voltajes
                //Por ultimo, este filtro se añade a la listaFiltros para que sea tomado en cuenta
                listaFiltros.add(javax.swing.RowFilter.regexFilter(regexVoltaje, 2));
            }
            
            //Este if evalua si el usuario seleccionó una opción valida dentro del combobox
            //El indice 0 corresponde al texto descriptivo Seleccionar Departamento, por lo que si el índice es mayor a 0 
            //significa que el usuario eligio un departamento como Nariño o Huila y se debe proceder con el filtro
            if (jComboBox1.getSelectedIndex() > 0) { 
    
                //Se extrae el nombre del departamento seleccionado
                String deptoSeleccionado = jComboBox1.getSelectedItem().toString();
                
                //El metodo replaceAll aplica una regla para modificar la cadena de texto del departamento seleccionado
                //La expresión [ñÑ] busca cualquier coincidencia de la letra ñ (ya sea minúscula o mayúscula)
                //El segundo parametro "." reemplaza esa ñ por un punto físico en la cadena de la expresión regular, esto es porque en regex, el punto es un comodin universal que representa cualquier carácter
                //Se hace esto como una estrategia ante errores de codificación, por si el archivo CSV cargo la palabra "Nariño" con un carácter corrupto o extraño en la tabla, al buscar con el comodin "Nari.o"
                //el motor ignorará el defecto del carácter central, validara la fila como correcta y evitará que la tabla se quede en blanco.
                String deptoRegexTolerante = deptoSeleccionado.replaceAll("[ñÑ]", ".");
                
                //Se crea la regla expresión regular
                //El (?i) sirve para ignorar mayúsculas y minusculas por seguridad
                //El ^ asegura que coincida exactamente desde el inicio del texto en la celda
                String regexDepto = "(?i)^" + deptoRegexTolerante;
    
                //Se crea el filtro y se aplica a la columna de Departamentos
                //Finalmente, se suma a la lista de filtros acumulados
                if(panelLinea.getJTable2().isShowing()){
                    listaFiltros.add(javax.swing.RowFilter.regexFilter(regexDepto, 3));
                }
                else{
                    listaFiltros.add(javax.swing.RowFilter.regexFilter(regexDepto, 2));
                }
            }

            if (listaFiltros.isEmpty()) {
                // Si la lista está vacía, no hay texto ni combobox seleccionado, se limpia la tabla de cualquier condicion que tenga y se mostraran todas las lineas
                sorter.setRowFilter(null);
            } else {
                
                //Si la lista contiene uno o mas filtros, el método "andFilter" toma la lista completa de filtros y los unifica bajo un AND o &&
                //Esto obliga a la tabla a mostrar únicamente las filas que cumplan con todas las condiciones activas al mismo tiempo
                sorter.setRowFilter(javax.swing.RowFilter.andFilter(listaFiltros));
            }
            
            if(panelLinea.getJTable2().isShowing()){
                jLabel6.setText(panelLinea.getJTable2().getRowCount()+"");
            }
            else if(panelSubE.getJTable1().isShowing()){
                jLabel6.setText(panelSubE.getJTable1().getRowCount()+""); 
            }
            
            jLabel10.setText(String.format("%.2f KM", contraladorLinea.calcularLongitudTotal(panelLinea.getJTable2())));
            jLabel8.setText(String.format("%.2f MW", contraladorLinea.calcularCapacidadTotal(panelLinea.getJTable2())));
        }
    }
    
    public void activarPanelLineas(){
        panelLinea.llenarTabla();
        jPanel11.setVisible(true);
        jPanel1.setVisible(true);
        jLabel7.setVisible(true);
        jLabel8.setVisible(true);
        jLabel9.setVisible(true);
        jLabel10.setVisible(true);
        jPanel2.setVisible(true); 
        jPanel3.setVisible(true);
        jComboBox4.setVisible(true);
        //el removeAll lo que hace es que borra cualquier panel que estuviera abierto antes de presionar el boton
        panelContenedor.removeAll();
        
        //esta linea lo que hace es agregar el panel panelLinea y se le indica a java que el panel ocupe todo el espacio disponible del centro sin dejar bordes
        panelContenedor.add(panelLinea, java.awt.BorderLayout.CENTER);
    
        //revalidate le dice a java que recalcule los tamaños y las estructuras de los componentes para que no haya problemas
        panelContenedor.revalidate();
        //repaint le dice a java que borre todos los pixeles viejos y pinte los nuevos pixeles del panel panelLinea
        panelContenedor.repaint();
        sorter.setRowFilter(null);
        jLabel4.setText("Lineas de Transmision");
        jLabel5.setText("Total de Lineas");
        jLabel6.setText(contraladorLinea.obtenerNumeroDeLineas()+"");
        jLabel7.setText("Capacidad Total");
        jLabel8.setText(String.format("%.2f MW", contraladorLinea.calcularCapacidadTotal(panelLinea.getJTable2())));
        jLabel9.setText("Longitud total");
        jLabel10.setText(String.format("%.2f KM", contraladorLinea.calcularLongitudTotal(panelLinea.getJTable2())));
    

    }
    
    public void activarPanelSubestaciones(){
        //actualiza lod datos
        panelSubE.llenarTabla();
         //borra los paneles donde estaba anteriormente la carga total y longitud recorrida
        jLabel7.setVisible(false);
        jLabel8.setVisible(false);
        jLabel9.setVisible(false);
        jLabel10.setVisible(false);
        jPanel2.setVisible(false); 
        jPanel3.setVisible(false);
        jPanel1.setVisible(true);
        jPanel11.setVisible(true);
        //borro el combobox de voltaje nominal
        jComboBox4.setVisible(false);
        jLabel4.setText("SubEstaciones");
        jLabel5.setText("Total de SubEstaciones");
        jLabel6.setText(contraladorSubE.obtenerNumeroDeSubestaciones()+"");
        sorter.setRowFilter(null);

        panelContenedor.removeAll();
        
        panelContenedor.add(panelSubE, java.awt.BorderLayout.CENTER);
    
        panelContenedor.revalidate();
        panelContenedor.repaint();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        panelContenedor = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel9.setBackground(new java.awt.Color(184, 250, 53));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel13.setText("Lineas de Transmision ");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel14.setText("Sistema Electrico Nacional - Colombia - Subarea Cauca-Nariño");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Gestion");

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setText("Lineas");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton6.setText("Subestaciones");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Registrar");

        jButton11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton11.setText("Lineas");
        jButton11.addActionListener(this::jButton11ActionPerformed);

        jButton12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton12.setText("Subestaciones");
        jButton12.addActionListener(this::jButton12ActionPerformed);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Eliminar");

        jButton13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton13.setText("Lineas");
        jButton13.addActionListener(this::jButton13ActionPerformed);

        jButton14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton14.setText("Subestaciones");
        jButton14.addActionListener(this::jButton14ActionPerformed);

        jButton1.setText("CONFIRMAR");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(jButton1)
                .addContainerGap(159, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel12.setLayout(new java.awt.GridLayout(1, 3, 25, 0));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("jLabel1");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        jPanel12.add(jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("jLabel7");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("jLabel8");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        jPanel12.add(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("jLabel9");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("jLabel10");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(12, 12, 12)
                .addComponent(jLabel10)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel12.add(jPanel3);

        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel14.setLayout(new java.awt.GridLayout(1, 4, 25, 0));

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Buscar por ID                     🔍");
        jPanel14.add(jLabel24);

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField1.addActionListener(this::jTextField1ActionPerformed);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel14.add(jTextField1);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Voltaje Nominal", "115-110kV", "138kV", "230-220kV" }));
        jComboBox4.setToolTipText("");
        jComboBox4.setAutoscrolls(true);
        jComboBox4.addActionListener(this::jComboBox4ActionPerformed);
        jPanel14.add(jComboBox4);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Departamento", "Cauca", "Valle del Cauca", "Nariño", "Huila" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);
        jPanel14.add(jComboBox1);

        panelContenedor.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelContenedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelContenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        getContentPane().add(jPanel8, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       activarPanelLineas();
       ///oculta confirmar
       jButton1.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        jButton1.setVisible(false);
        
        RegistroLinea ventanaFormulario = new RegistroLinea(this, true);

        ventanaFormulario.setLocationRelativeTo(this);

        ventanaFormulario.setVisible(true);
        
        
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        
        aplicarFiltros();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        //oculta confirmar
        jButton1.setVisible(false);
        //muestra en pantalla la tabla de datos de subestaciones
        activarPanelSubestaciones();
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        
       aplicarFiltros();
        
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        aplicarFiltros();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        //activa el panel de lineas para seleccionar la de borrar
        activarPanelLineas();
        jButton1.setVisible(true);
       
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if(panelLinea.getJTable2().isShowing()){
        //este es el boton confirmar asi q haria falta desasctivar el boton a menos que le de a eliminar lineas, ya q esta activo todo el tiempo, se me olvido
        //creamos el objeto para luego eliminarlo del arraylist
        int filaSeleccionada = panelLinea.getJTable2().getSelectedRow();
    
        // Si no hay ninguna fila seleccionada, devolvemos 
        if (filaSeleccionada == -1) {
            return;
        }

        // de la vista al índice real del modelo para no pasar el dato equivocado.
        int filaModelo = panelLinea.getJTable2().convertRowIndexToModel(filaSeleccionada);
    
        //con esto hallamos la id de la linea seleccionada
        idSelec = panelLinea.getJTable2().getModel().getValueAt(filaModelo, 0).toString();
        
        //se pasa la id de la linea de transmision que se quiere borrar al metodo que se encarga de eliminar la linea
        contraladorLinea.borrarLinea(idSelec);
       
        //usado el llenar tabla para actualizar la tabla en tiempo real
        panelLinea.llenarTabla();
        //usados para actualizar los valores de longitud y capacidad en tiempo real
        activarPanelLineas();
        }
        
        else if(panelSubE.getJTable1().isShowing()){
            int filaSeleccionada = panelSubE.getJTable1().getSelectedRow();
        // Si no hay ninguna fila seleccionada, devolvemos 
        if (filaSeleccionada == -1) {
            return;
        }
                // de la vista al índice real del modelo para no pasar el dato equivocado.
        int filaModelo = panelSubE.getJTable1().convertRowIndexToModel(filaSeleccionada);
        //con esto hallamos la id de la linea seleccionada
        idSelec = panelSubE.getJTable1().getModel().getValueAt(filaModelo, 0).toString();
                //se pasa la id de la linea de transmision que se quiere borrar al metodo que se encarga de eliminar la linea
        contraladorSubE.eliminarSubestacion(idSelec);
       
        //usado el llenar tabla para actualizar la tabla en tiempo real
        panelSubE.llenarTabla();
        activarPanelSubestaciones();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        activarPanelSubestaciones();
        jButton1.setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        jButton1.setVisible(false);
        
        RegistroSubestacion ventanaFormulario2 = new RegistroSubestacion(this, true);

        ventanaFormulario2.setLocationRelativeTo(this);

        ventanaFormulario2.setVisible(true);
        
//boton crear sub
    }//GEN-LAST:event_jButton14ActionPerformed

        
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new GUI().setVisible(true));
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel panelContenedor;
    // End of variables declaration//GEN-END:variables
}
