
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Kaushik and Johnny 28 and ProgJazz
 */
public class FlightList extends javax.swing.JFrame {
    
    public int selRow;
    public String flightNo, pass, globalDate;

    /**
     * Creates new form FlightList
     * @param objsearch
     * @throws java.io.IOException
     */
    
    SearchFlight objsearch;
    int fCheck = 0, f2check = 0;

    public FlightList(SearchFlight obj) throws IOException {
        initComponents();
        objsearch = obj;
        jButton1.setVisible(false);
        jButton2.setVisible(true);
        jTextField1.setEditable(false);
        jTextField2.setEditable(false);
        jButton2.setVisible(false);
        Icon img = new ImageIcon("Images\\project backgroundflight_plane_sky_color_line_61872_1024x768.jpg");   
        jLabel4.setIcon(img);
        DataManager dm=new DataManager();
        Flight flight[];
        flight = dm.readSilkAirFile();
        String[][] row = new String[20][10];
        String[] col = {"Departure","SpiceJet","Arrival","Intermediate","Via","Departure","SilkAir","Arrival" ,"Duration"};
        
        String source = objsearch.jComboBox1.getSelectedItem().toString();
        int spiceDay = check(Integer.parseInt(objsearch.jComboBox5.getSelectedItem().toString()));
        globalDate = objsearch.jComboBox5.getSelectedItem().toString();
        int silkDay = check(Integer.parseInt(objsearch.jComboBox5.getSelectedItem().toString()));  
        CombinedFlight cf = new CombinedFlight(source,spiceDay,silkDay);
        CombinedFlight[] cFlight = new CombinedFlight[cf.flightCount];
        cFlight = cf.combine(source,spiceDay,silkDay);
        
        int i;
        for(i=0;i<cf.flightCount;i++){
            row[i][0]=cFlight[i].getDeptSpice();
            row[i][1]=cFlight[i].getSpiceFlightNo();
            flightNo = row[i][1];
            row[i][2]=cFlight[i].getArrSpice();
            row[i][3]=cFlight[i].getIntermediate();
            row[i][4]=cFlight[i].getVia();
            row[i][5]=cFlight[i].getDeptSilk();
            row[i][6]=cFlight[i].getSilkFlightNo();
            row[i][7]=cFlight[i].getArrSilk();
            row[i][8]=cFlight[i].getDuration();
        }
        jTable1.setModel(new DefaultTableModel(row, col));
        int d= Integer.parseInt(objsearch.jComboBox5.getSelectedItem().toString());
        String th="th";
        int d1=d%10;
        if (d1==1)
            th="st";
        else if(d1==2)
            th="nd";
        else if(d1==3)
            th="rd";
        jLabel5.setText(objsearch.jComboBox2.getSelectedItem().toString()+" Passengers flying from "+source+" to Singapore on "+d+""+th+" Oct 2015");
        pass = objsearch.jComboBox2.getSelectedItem().toString();
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {  
                try {
                    selRow = jTable1.getSelectedRow();  
                    jTextField1.setText((String)jTable1.getModel().getValueAt(selRow, 1));
                    
                    if (jTextField1.getText().equals("")) {
                        jButton1.setVisible(false);
                        jButton2.setVisible(true);
                        f2check++;
                        if (f2check == 1) {
                            JOptionPane.showMessageDialog(null, "Please Select a Flight...");
                        }
                        jTextField2.setText((String)jTable1.getModel().getValueAt(selRow, 6));
                    }
                    else if (seatChecker(jTextField1.getText()) == -1) {
                        jButton1.setVisible(false);
                        jButton2.setVisible(true);
                        fCheck++;
                        if(fCheck == 1) {
                            JOptionPane.showMessageDialog(null, "Flight Cannot Be Booked...");
                        }
                        jTextField2.setText((String)jTable1.getModel().getValueAt(selRow, 6));
                    }
                    else {
                        jButton1.setVisible(true);
                        jButton2.setVisible(false);
                        jTextField2.setText((String)jTable1.getModel().getValueAt(selRow, 6));
                    }
                    
                }
                catch(Exception e) {
                    System.out.println("Troubled!!!");
                }
            }
        });
            jButton1.addMouseListener(new mouse(this));
    }
    public static int check(int dd) {
        Date date = (new GregorianCalendar(2015, 9, dd)).getTime();
        SimpleDateFormat f = new SimpleDateFormat("EEEE");
        String d = f.format(date);
        if(d.compareTo("Sunday") == 0)
            return 0;
        else if(d.compareTo("Monday") == 0)
            return 1;
        else if(d.compareTo("Tuesday") == 0)
            return 2;
        else if(d.compareTo("Wednesday") == 0)
            return 3;
        else if(d.compareTo("Thursday") == 0)
            return 4;
        else if(d.compareTo("Friday") == 0)
            return 5;
        else
            return 6;
    }
    
    FileReader fr;
    BufferedReader br;
    
    int seatChecker(String flightNo) {
        int sum = 0;
        String local, tokens, checkerToken; int seats, localSeatId, localDate;
        try {
            fr = new FileReader("Files/PassengerDatabase.csv");
            br = new BufferedReader(fr);
            if (br.readLine() != null) {
                fr = new FileReader("Files/PassengerDatabase.csv");
                br = new BufferedReader(fr);
                
                while ((local = br.readLine()) != null) {
                    StringTokenizer localTokenizer = new StringTokenizer(local, "|");
                    tokens = localTokenizer.nextToken();
                    localSeatId = Integer.parseInt(tokens);
                    tokens = localTokenizer.nextToken();
                    localDate = Integer.parseInt(tokens);
                    tokens = localTokenizer.nextToken();
                    checkerToken = localTokenizer.nextToken();
                    if (checkerToken.equals(flightNo) && localDate == Integer.parseInt(globalDate)) {
                        tokens = localTokenizer.nextToken();
                        seats = Integer.parseInt(tokens);
                        sum += seats;
                        if ((seats == 15) || (Integer.parseInt(pass) + seats > 15) && localDate == Integer.parseInt(globalDate)) {
                            return -1;
                        } } }
                if ((sum + Integer.parseInt(pass)) > 15) {
                    return -1;
                }
                } }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Passenger Database not Found...");
            JOptionPane.showMessageDialog(null, "Please load correct Database and try again...");
            System.exit(0);
        }
        return 0;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Departure", "SpiceJet", "Arrival", "Intermediate", "Via", "Departure", "SilkAir", "Arrival", "Duration"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(30, 140, 618, 140);

        jLabel1.setBackground(new java.awt.Color(102, 204, 255));
        jLabel1.setFont(new java.awt.Font("Sitka Text", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Search Result:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(41, 104, 110, 20);

        jButton1.setBackground(new java.awt.Color(51, 102, 255));
        jButton1.setFont(new java.awt.Font("Verdana", 0, 15)); // NOI18N
        jButton1.setText("Go to Booking");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(480, 370, 170, 40);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("SpiceJet:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(91, 321, 62, 25);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("Silk Air:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(91, 372, 62, 28);

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(jTextField1);
        jTextField1.setBounds(171, 322, 90, 31);

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField2);
        jTextField2.setBounds(171, 373, 90, 30);

        jLabel5.setFont(new java.awt.Font("Sitka Small", 0, 16)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().add(jLabel5);
        jLabel5.setBounds(60, 50, 540, 40);

        jLabel6.setFont(new java.awt.Font("Sitka Text", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Flight result for:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(40, 20, 129, 27);

        jButton2.setBackground(new java.awt.Color(153, 153, 153));
        jButton2.setFont(new java.awt.Font("Verdana", 0, 15)); // NOI18N
        jButton2.setText("Not available");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(480, 370, 170, 40);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Go back");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });
        getContentPane().add(jLabel7);
        jLabel7.setBounds(590, 20, 60, 30);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(jLabel4);
        jLabel4.setBounds(0, 0, 700, 440);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseEntered
        
    }//GEN-LAST:event_jButton1MouseEntered

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
     
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        objsearch.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        // TODO add your handling code here:
        jLabel7.setFont(jLabel7.getFont().deriveFont(Font.BOLD));
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        // TODO add your handling code here:
        jLabel7.setFont(jLabel7.getFont().deriveFont(Font.PLAIN));
    }//GEN-LAST:event_jLabel7MouseExited

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    public javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

}

