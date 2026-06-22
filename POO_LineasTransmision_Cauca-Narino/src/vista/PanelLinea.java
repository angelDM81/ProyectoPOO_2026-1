
package vista;

import controlador.*;
import java.util.ArrayList;
import modelo.LineaTransmision;
import javax.swing.table.DefaultTableModel;

public class PanelLinea extends javax.swing.JPanel {

    private ControladorLinea controlador = new ControladorLinea();
    private ArrayList<LineaTransmision> lineasTranmision = new ArrayList<>();
    
    public PanelLinea() {
        initComponents();
        llenarTabla();
        
        //esta linea se encarga de inicializar el TableRowSorter en la tabla, al pasarle el DefaultTableModel se deja preparado para que el campo de texto de la GUI pueda aplicar los filtros dinamicos
        jTable2.setRowSorter(new javax.swing.table.TableRowSorter<>((javax.swing.table.DefaultTableModel) jTable2.getModel()));
        
    }
    
    //esta funcion devuelve la tabla jTable2 en donde estan los datos de las lineas de transmision, para que se pueda filtrar la busqueda por ID
    public javax.swing.JTable getJTable2(){
        return jTable2;
    }
    
    public void llenarTabla(){
        
        //se obtiene la lista de las lineas de transmision mediante el controlador
        lineasTranmision = controlador.obtenerLineaTransmision();
        
        //se castea el tipo de tabla, porque un Jtable es un componente unicamente visual, el que verdaderamente deja agregar, quitar y editar filas el es DefaultTableModel
        DefaultTableModel modeloTabla = (DefaultTableModel) jTable2.getModel();
        
        //borra todas las filas que existian previamente para evitar duplicados
        modeloTabla.setRowCount(0);
        
        for(LineaTransmision linea : lineasTranmision){
            
            String id =linea.getInformacionBasica().getID();
            String nombre = linea.getInformacionBasica().getNombre();
            String voltaje = linea.getCapacidad().getVoltajeNominal()+"Kv";
            String departamento = linea.getUbicacion().getDepartamento();
            
            //Se crea un vector que representa una fila de la tabla, donde los datos van en el orden exacto de las columnas
            Object[] fila = new Object[]{ id, nombre, voltaje, departamento };
            
            //agrega la fila recien creada a la tabla 
            modeloTabla.addRow(fila);
        }
  
        if(!lineasTranmision.isEmpty()){
        llenarEtiquetas(lineasTranmision.get(0));
        }
        
         
    }
    
    public void llenarEtiquetas(LineaTransmision linea){
        
        jlabelid.setText("ID: "+linea.getInformacionBasica().getID());
        jlabelnombre.setText("Nombre: "+linea.getInformacionBasica().getNombre());
        jlabeloperador.setText("Operador: "+linea.getInformacionBasica().getOperador());
        jlabelestado.setText("Estado: "+linea.getInformacionBasica().getEstado());
        jlabelfpo.setText("Fecha de Puesta en Operacion: "+linea.getInformacionBasica().getFPO()); 
        jlabelconexion.setText("Tipo de uso/conexion: "+linea.getInformacionBasica().getTipoConexion());
        jlabelsubestaciones.setText("Subestaciones relacionadas: "+linea.getInformacionBasica().getSubestacionesRelacionadas());
        jlabeltipolinea.setText("Tipo de linea: "+linea.getInformacionBasica().getTipoDeLinea());
        jlabelacuerdo.setText("Hace parte de un acuero de conexion compartida?: "+linea.getInformacionBasica().getAcuerdoDeConexionCompartida());
        
        jlabelvoltajenominal.setText("Voltaje nominal: "+String.valueOf(linea.getCapacidad().getVoltajeNominal()) + " kV");
        jlabelvoltajemaximo.setText("Voltaje maximo: "+String.valueOf(linea.getCapacidad().getVoltajeMaximo()) + " kV");
        jlabelcorrientenominal.setText("Corriente nominal: "+String.valueOf(linea.getCapacidad().getCorrienteNominal()) + " A");
        jlabellimiteemergencia.setText("Limite de emergencia: "+String.valueOf(linea.getCapacidad().getCorrienteEmergencia()) + " A");
        jlabellimitetermico.setText("Limite termico: "+String.valueOf(linea.getCapacidad().getLimiteTermico()) + " A");
        jlabellongitud.setText("Longitud total: "+String.valueOf(linea.getCapacidad().getLongitudTotal()) + " km");
        
        jlabeldepartamento.setText("Departamento: "+linea.getUbicacion().getDepartamento());
        jlabelmunicipio.setText("Ubicacion: "+ linea.getUbicacion().getMunicipio());
        //se hace uso del metodo replace para quitar la palabra SubArea que sale en el csv
        String subarea = linea.getUbicacion().getSubAreaOperativa().replace("SubArea", "");
        jlabelsubarea.setText("Subarea: "+subarea);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jlabelid = new javax.swing.JLabel();
        jlabelnombre = new javax.swing.JLabel();
        jlabeloperador = new javax.swing.JLabel();
        jlabelestado = new javax.swing.JLabel();
        jlabelfpo = new javax.swing.JLabel();
        jlabelconexion = new javax.swing.JLabel();
        jlabelsubestaciones = new javax.swing.JLabel();
        jlabeltipolinea = new javax.swing.JLabel();
        jlabelacuerdo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jlabelvoltajenominal = new javax.swing.JLabel();
        jlabelvoltajemaximo = new javax.swing.JLabel();
        jlabelcorrientenominal = new javax.swing.JLabel();
        jlabellimiteemergencia = new javax.swing.JLabel();
        jlabellimitetermico = new javax.swing.JLabel();
        jlabellongitud = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jlabeldepartamento = new javax.swing.JLabel();
        jlabelmunicipio = new javax.swing.JLabel();
        jlabelsubarea = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setResizeWeight(0.6);
        jSplitPane1.setLastDividerLocation(500);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(0, 0));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Voltaje nominal", "Departamento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
        }

        jSplitPane1.setLeftComponent(jScrollPane2);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel1.setText("Informacion Basica");

        jlabelid.setText("ID ");

        jlabelnombre.setText("Nombre");

        jlabeloperador.setText("Operador");

        jlabelestado.setText("Estado");

        jlabelfpo.setText("Fecha de Puesta en Operacion");

        jlabelconexion.setText("Tipo de uso/conexion");

        jlabelsubestaciones.setText("Subestaciones relacionadas");

        jlabeltipolinea.setText("Tipo de linea");

        jlabelacuerdo.setText("Hace parte de un acuero de conexion compartida?");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel3.setText("Informacion Tecnica");

        jlabelvoltajenominal.setText("Voltaje nominal");

        jlabelvoltajemaximo.setText("Voltaje maximo");

        jlabelcorrientenominal.setText("Corriente nominal");

        jlabellimiteemergencia.setText("Limite emergencia");

        jlabellimitetermico.setText("Limite termico");

        jlabellongitud.setText("Longitud");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel4.setText("Ubicacion");

        jlabeldepartamento.setText("Departamento");

        jlabelmunicipio.setText("Municipio");

        jlabelsubarea.setText("Subarea");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jlabelsubarea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabelmunicipio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlabelid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabelnombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabeloperador, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabelestado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabelfpo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabelconexion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabelsubestaciones, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabeltipolinea, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabelacuerdo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlabelvoltajenominal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabelvoltajemaximo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabelcorrientenominal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabellimiteemergencia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabellimitetermico, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabellongitud, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlabeldepartamento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 251, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelnombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabeloperador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelestado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelfpo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelconexion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelsubestaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabeltipolinea)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelacuerdo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelvoltajenominal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelvoltajemaximo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelcorrientenominal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabellimiteemergencia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabellimitetermico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabellongitud)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlabeldepartamento)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelmunicipio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabelsubarea)
                .addGap(0, 68, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1357, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        
        //filaSeleccionada recolecta el numero de fila en el que se hizo click, para asi poder buscar la linea de transmision y mostrar sus datos en los labels
        int filaSeleccionada = jTable2.getSelectedRow();
    
        //se valida que el numero de fila sea valido, por si el usuario da click en algun otro lugar y se activa el evento
        if(filaSeleccionada != -1){
            
            //esta linea de codigo lo que hace es que como a veces el indice visual no va a coincidir con el indice del arraylist debido a la busqueda por id
            //entonces esta linea que hace es traducir ese indice visual al verdadero indice que esta en el arraylist
            int filaReal = jTable2.convertRowIndexToModel(filaSeleccionada);
        
            //se busca en el array lineasTransmision la linea seleccionada por medio de su posicion (ya que tanto en el array como en la tabla tienen el mismo indice)
            LineaTransmision linea = lineasTranmision.get(filaReal);
        
            llenarEtiquetas(linea);
        }
    }//GEN-LAST:event_jTable2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel jlabelacuerdo;
    private javax.swing.JLabel jlabelconexion;
    private javax.swing.JLabel jlabelcorrientenominal;
    private javax.swing.JLabel jlabeldepartamento;
    private javax.swing.JLabel jlabelestado;
    private javax.swing.JLabel jlabelfpo;
    private javax.swing.JLabel jlabelid;
    private javax.swing.JLabel jlabellimiteemergencia;
    private javax.swing.JLabel jlabellimitetermico;
    private javax.swing.JLabel jlabellongitud;
    private javax.swing.JLabel jlabelmunicipio;
    private javax.swing.JLabel jlabelnombre;
    private javax.swing.JLabel jlabeloperador;
    private javax.swing.JLabel jlabelsubarea;
    private javax.swing.JLabel jlabelsubestaciones;
    private javax.swing.JLabel jlabeltipolinea;
    private javax.swing.JLabel jlabelvoltajemaximo;
    private javax.swing.JLabel jlabelvoltajenominal;
    // End of variables declaration//GEN-END:variables
}
