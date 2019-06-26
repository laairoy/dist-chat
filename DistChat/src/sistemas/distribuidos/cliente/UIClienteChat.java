/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.cliente;

import sistemas.distribuidos.distchat.ClienteListModel;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sistemas.distribuidos.distchat.BingoListModel;
import sistemas.distribuidos.distchat.DadosCliente;
import sistemas.distribuidos.distchat.MsgListModel;

/**
 *
 * @author laairoy
 */
public class UIClienteChat extends javax.swing.JFrame {

    private final String nome;
    private Cliente cliente;
    private int sorteado;
    private int[] cartela;
    /**
     * Creates new form ClienteChat
     *
     * @param nome
     *
     */
    @SuppressWarnings("unchecked")
    public UIClienteChat(String nome) {
        
        ClienteListModel list = ClienteListModel.init();
        MsgListModel listMsgModel = MsgListModel.init();
        BingoListModel bingoList = BingoListModel.init();
        
        initComponents();
        cbBroadcast.setSelected(true);
        getRootPane().setDefaultButton(btEnviar);

        this.nome = nome;

        listClientes.setModel(list);
        listClientes.setVisibleRowCount(10);
        
        listMsg.setModel(listMsgModel);
        
        listJogadores.setModel(bingoList);
        listJogadores.setVisibleRowCount(10);
        
        bSairBingo.setEnabled(false);
        b12.setEnabled(false);
        
        resetTela();
    }
    
    public void resetTela(){
        bMarcar.setEnabled(false);
        bNaoMarcar.setEnabled(false);
        bBingo.setEnabled(false);
        lSorteio.setText("00");
    }
    
    
    public void initCliente(){
        try {
            this.cliente = Cliente.init();
        } catch (IOException ex) {
            Logger.getLogger(UIClienteChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void aguardarJogadores(){
    /*   for(int t = 30; t >= 0; t--) {
           try {
               TimeUnit.SECONDS.sleep(1);
               lTempo.setText("Aguarde " + t + " segundos");
               
           } catch (InterruptedException ex) {
               Logger.getLogger(UIClienteChat.class.getName()).log(Level.SEVERE, null, ex);
           }
       }*/
    }
    
    public void mostrarCartela(int[] cartela) {
        if (cartela.length == 25) {
            this.cartela = cartela;
            b0.setText(Integer.toString(cartela[0])); b1.setText(Integer.toString(cartela[1])); b2.setText(Integer.toString(cartela[2])); b3.setText(Integer.toString(cartela[3])); b4.setText(Integer.toString(cartela[4]));
            b5.setText(Integer.toString(cartela[5])); b6.setText(Integer.toString(cartela[6])); b7.setText(Integer.toString(cartela[7])); b8.setText(Integer.toString(cartela[8])); b9.setText(Integer.toString(cartela[9]));
            b10.setText(Integer.toString(cartela[10])); b11.setText(Integer.toString(cartela[11])); b12.setText(Integer.toString(cartela[12])); b13.setText(Integer.toString(cartela[13])); b14.setText(Integer.toString(cartela[14]));
            b15.setText(Integer.toString(cartela[15])); b16.setText(Integer.toString(cartela[16])); b17.setText(Integer.toString(cartela[17])); b18.setText(Integer.toString(cartela[18])); b19.setText(Integer.toString(cartela[19]));
            b20.setText(Integer.toString(cartela[20])); b21.setText(Integer.toString(cartela[21])); b22.setText(Integer.toString(cartela[22])); b23.setText(Integer.toString(cartela[23])); b24.setText(Integer.toString(cartela[24]));
        } else {
            JOptionPane.showMessageDialog(rootPane, "Erro no recebimento da cartela!");
            try {
                cliente.logoutBingo(nome);
                bSairBingo.setEnabled(false);
                bEntrarBingo.setEnabled(false);
            } catch (IOException ex) {
                Logger.getLogger(UIClienteChat.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    public void mostraSorteado(int sorteado){
        this.sorteado = sorteado;
        lSorteio.setText(Integer.toString(sorteado));
        bMarcar.setEnabled(true);
        bNaoMarcar.setEnabled(true);
        bBingo.setEnabled(true);
    }
    
    
    public void verificaNumCartela(){
      if (cartela != null) {
        if (cartela[0] == sorteado)
            b0.setEnabled(false);
        if (cartela[1] == sorteado)
            b1.setEnabled(false);
        if (cartela[2] == sorteado)
            b2.setEnabled(false);
        if (cartela[3] == sorteado)
            b3.setEnabled(false);
        if (cartela[4] == sorteado)
            b4.setEnabled(false);
        if (cartela[5] == sorteado)
            b5.setEnabled(false);
        if (cartela[6] == sorteado)
            b6.setEnabled(false);
        if (cartela[7] == sorteado)
            b7.setEnabled(false);
        if (cartela[8] == sorteado)
            b8.setEnabled(false);
        if (cartela[9] == sorteado)
            b9.setEnabled(false);
        if (cartela[10] == sorteado)
            b10.setEnabled(false);
        if (cartela[11] == sorteado)
            b11.setEnabled(false);
        if (cartela[12] == sorteado)
            b12.setEnabled(false);
        if (cartela[13] == sorteado)
            b13.setEnabled(false);
        if (cartela[14] == sorteado)
            b14.setEnabled(false);
        if (cartela[15] == sorteado)
            b15.setEnabled(false);
        if (cartela[16] == sorteado)
            b16.setEnabled(false);
        if (cartela[17] == sorteado)
            b17.setEnabled(false);
        if (cartela[18] == sorteado)
            b18.setEnabled(false);
        if (cartela[19] == sorteado)
            b19.setEnabled(false);
        if (cartela[20] == sorteado)
            b20.setEnabled(false);
        if (cartela[21] == sorteado)
            b21.setEnabled(false);
        if (cartela[22] == sorteado)
            b22.setEnabled(false);
        if (cartela[23] == sorteado)
            b23.setEnabled(false);
        if (cartela[24] == sorteado)
            b24.setEnabled(false);
      }
    }

    
    public void jogoIniciado(String msg) {
        JOptionPane.showMessageDialog(rootPane, msg);
        bEntrarBingo.setEnabled(true);
        bSairBingo.setEnabled(false);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        tfEnviar = new javax.swing.JTextField();
        btEnviar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listMsg = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listClientes = new javax.swing.JList<>();
        cbBroadcast = new javax.swing.JCheckBox();
        bEntrarBingo = new javax.swing.JButton();
        bLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lSorteio = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        b0 = new javax.swing.JButton();
        b1 = new javax.swing.JButton();
        b2 = new javax.swing.JButton();
        b3 = new javax.swing.JButton();
        b4 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        b5 = new javax.swing.JButton();
        b6 = new javax.swing.JButton();
        b7 = new javax.swing.JButton();
        b8 = new javax.swing.JButton();
        b9 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        b10 = new javax.swing.JButton();
        b11 = new javax.swing.JButton();
        b12 = new javax.swing.JButton();
        b13 = new javax.swing.JButton();
        b14 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        b15 = new javax.swing.JButton();
        b16 = new javax.swing.JButton();
        b17 = new javax.swing.JButton();
        b18 = new javax.swing.JButton();
        b19 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        b20 = new javax.swing.JButton();
        b21 = new javax.swing.JButton();
        b22 = new javax.swing.JButton();
        b23 = new javax.swing.JButton();
        b24 = new javax.swing.JButton();
        bMarcar = new javax.swing.JButton();
        bNaoMarcar = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listJogadores = new javax.swing.JList<>();
        bBingo = new javax.swing.JButton();
        lTempo = new javax.swing.JLabel();
        bSairBingo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chat", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24))); // NOI18N

        btEnviar.setText("Enviar");
        btEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEnviarActionPerformed(evt);
            }
        });
        btEnviar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btEnviarKeyPressed(evt);
            }
        });

        listMsg.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listMsg.setFocusable(false);
        listMsg.setVerifyInputWhenFocusTarget(false);
        listMsg.setVisibleRowCount(15);
        jScrollPane3.setViewportView(listMsg);

        jLabel1.setText("Mensagem:");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        listClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listClientes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listClientesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listClientes);

        cbBroadcast.setText("Broadcast");
        cbBroadcast.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cbBroadcastStateChanged(evt);
            }
        });
        cbBroadcast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBroadcastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(cbBroadcast)
                .addGap(0, 98, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbBroadcast)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btEnviar)
                            .addComponent(tfEnviar)
                            .addComponent(jLabel1)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bEntrarBingo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        bEntrarBingo.setText("Entrar Bingo");
        bEntrarBingo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bEntrarBingoMouseClicked(evt);
            }
        });
        bEntrarBingo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEntrarBingoActionPerformed(evt);
            }
        });

        bLogout.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        bLogout.setText("Logout Servidor");
        bLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLogoutActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bingo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24))); // NOI18N

        lSorteio.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lSorteio.setText("00");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Número sorteado:");

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel4.setText("B");

        b0.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b0.setText("0");

        b1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b1.setText("0");

        b2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b2.setText("0");

        b3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b3.setText("0");

        b4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b4.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(b0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(23, Short.MAX_VALUE))
            .addComponent(b1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b0, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel5.setText("I");

        b5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b5.setText("0");

        b6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b6.setText("0");

        b7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b7.setText("0");

        b8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b8.setText("0");

        b9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b9.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(b5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(33, Short.MAX_VALUE))
            .addComponent(b6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(b6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(b7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(b8, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(b9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setPreferredSize(new java.awt.Dimension(70, 445));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel6.setText("N");

        b10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b10.setText("0");

        b11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b11.setText("0");

        b12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b12.setText("0");

        b13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b13.setText("0");

        b14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b14.setText("0");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(b10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(19, Short.MAX_VALUE))
            .addComponent(b11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(b11, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(b12, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(b13, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(b14, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel8.setPreferredSize(new java.awt.Dimension(70, 445));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel7.setText("G");

        b15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b15.setText("0");

        b16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b16.setText("0");

        b17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b17.setText("0");

        b18.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b18.setText("0");

        b19.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b19.setText("0");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(b15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(20, Short.MAX_VALUE))
            .addComponent(b16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b15, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(b16, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(b17, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(b18, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(b19, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel9.setPreferredSize(new java.awt.Dimension(70, 445));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel8.setText("O");

        b20.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b20.setText("0");

        b21.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b21.setText("0");

        b22.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b22.setText("0");

        b23.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b23.setText("0");

        b24.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        b24.setText("0");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(b20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(19, Short.MAX_VALUE))
            .addComponent(b21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(b24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b20, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(b21, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(b22, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(b23, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(b24, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bMarcar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bMarcar.setText("Marcar");
        bMarcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMarcarActionPerformed(evt);
            }
        });

        bNaoMarcar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bNaoMarcar.setText("Não marcar");
        bNaoMarcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNaoMarcarActionPerformed(evt);
            }
        });

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de jogadores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18)), "Lista de Jogadores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        listJogadores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listJogadores.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listJogadoresValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listJogadores);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        bBingo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bBingo.setText("Bingo!");

        lTempo.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lTempo.setText("Tempo");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addComponent(lTempo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lSorteio)
                        .addGap(18, 18, 18)
                        .addComponent(bMarcar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bNaoMarcar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bBingo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lTempo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(bMarcar)
                                .addComponent(bNaoMarcar)
                                .addComponent(bBingo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(16, 16, 16)))
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(lSorteio)
                        .addGap(28, 28, 28)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bSairBingo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        bSairBingo.setText("Sair Bingo");
        bSairBingo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSairBingoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(bEntrarBingo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bSairBingo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bLogout, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)))
                .addGap(26, 26, 26)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(bEntrarBingo)
                        .addGap(18, 18, 18)
                        .addComponent(bSairBingo)
                        .addGap(18, 18, 18)
                        .addComponent(bLogout)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbBroadcastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBroadcastActionPerformed

    }//GEN-LAST:event_cbBroadcastActionPerformed

    private void btEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEnviarActionPerformed

        if (tfEnviar.getText().isEmpty() == false) {
            try {
                if (cbBroadcast.isSelected()) {
                    cliente.sendMsg(this.nome, tfEnviar.getText());
                } else if (listClientes.getSelectedIndex() >= 0) {
                    DadosCliente dados = ClienteListModel.init().getDadosCliente(listClientes.getSelectedIndex());
                    cliente.sendMsg(this.nome, tfEnviar.getText(), dados);
                }
            } catch (IOException ex) {
                Logger.getLogger(UIClienteChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        tfEnviar.setText("");
    }//GEN-LAST:event_btEnviarActionPerformed

    private void listClientesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listClientesValueChanged
        cbBroadcast.setSelected(false);
    }//GEN-LAST:event_listClientesValueChanged

    private void cbBroadcastStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cbBroadcastStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBroadcastStateChanged

    private void bLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLogoutActionPerformed

        try {
            cliente.logout(this.nome);

            this.dispose();
        } catch (IOException ex) {
            Logger.getLogger(UIClienteChat.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }//GEN-LAST:event_bLogoutActionPerformed

    private void bEntrarBingoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEntrarBingoActionPerformed
        // TODO add your handling code here:
            try {
                cliente.loginBingo(nome);
                bEntrarBingo.setEnabled(false);
                bSairBingo.setEnabled(true);
                
            } catch (IOException ex) {
                Logger.getLogger(UIClienteChat.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_bEntrarBingoActionPerformed

    private void bEntrarBingoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bEntrarBingoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_bEntrarBingoMouseClicked

    private void btEnviarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btEnviarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btEnviarKeyPressed

    private void bNaoMarcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNaoMarcarActionPerformed
        try {
               
                cliente.marca(nome, "falha", sorteado);
                bMarcar.setEnabled(false);
                bNaoMarcar.setEnabled(false);

        } catch (IOException ex) {
                Logger.getLogger(UIClienteChat.class.getName()).log(Level.SEVERE, null, ex);
        }  // TODO add your handling code here:
    }//GEN-LAST:event_bNaoMarcarActionPerformed

    private void listJogadoresValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listJogadoresValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_listJogadoresValueChanged

    private void bSairBingoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSairBingoActionPerformed
        try {
               
                cliente.logoutBingo(nome);
                bSairBingo.setEnabled(false);
                bEntrarBingo.setEnabled(true);
                resetTela();

        } catch (IOException ex) {
                Logger.getLogger(UIClienteChat.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_bSairBingoActionPerformed

    private void bMarcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMarcarActionPerformed
          
        try {
                cliente.marca(nome, "sucesso", sorteado);
                bMarcar.setEnabled(false);
                bNaoMarcar.setEnabled(false);

        } catch (IOException ex) {
                Logger.getLogger(UIClienteChat.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
        verificaNumCartela();


    }//GEN-LAST:event_bMarcarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b0;
    private javax.swing.JButton b1;
    private javax.swing.JButton b10;
    private javax.swing.JButton b11;
    private javax.swing.JButton b12;
    private javax.swing.JButton b13;
    private javax.swing.JButton b14;
    private javax.swing.JButton b15;
    private javax.swing.JButton b16;
    private javax.swing.JButton b17;
    private javax.swing.JButton b18;
    private javax.swing.JButton b19;
    private javax.swing.JButton b2;
    private javax.swing.JButton b20;
    private javax.swing.JButton b21;
    private javax.swing.JButton b22;
    private javax.swing.JButton b23;
    private javax.swing.JButton b24;
    private javax.swing.JButton b3;
    private javax.swing.JButton b4;
    private javax.swing.JButton b5;
    private javax.swing.JButton b6;
    private javax.swing.JButton b7;
    private javax.swing.JButton b8;
    private javax.swing.JButton b9;
    private javax.swing.JButton bBingo;
    private javax.swing.JButton bEntrarBingo;
    private javax.swing.JButton bLogout;
    private javax.swing.JButton bMarcar;
    private javax.swing.JButton bNaoMarcar;
    private javax.swing.JButton bSairBingo;
    private javax.swing.JButton btEnviar;
    private javax.swing.JCheckBox cbBroadcast;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lSorteio;
    private javax.swing.JLabel lTempo;
    private javax.swing.JList<String> listClientes;
    private javax.swing.JList<String> listJogadores;
    private javax.swing.JList<String> listMsg;
    private javax.swing.JTextField tfEnviar;
    // End of variables declaration//GEN-END:variables
}
