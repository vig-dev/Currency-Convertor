package jdbcproj2;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.image.BufferedImage;
import java.io.File;

class convertor extends Frame implements ActionListener, ItemListener, KeyListener {

    Label l1, l2, l3, l4, l5, l6, l7, l8;
    List list1, list2;
    Button b1, b2;
    TextField t1;
    Connection con = null;
    Statement st;
    ResultSet rt;
    BufferedImage bi;
    float x, y, total;
    String cur1, cur2;

    convertor() {
        try {
            File f = new File("C:\\waah.jpg");
            bi = javax.imageio.ImageIO.read(f);
        } catch (Exception e) {
            System.out.println(e);
        }

        setSize(1000, 800);
        setResizable(false);
        setVisible(true);
        setTitle("Currency Convertor");

        list1 = new List();
        list2 = new List();
        String url = "jdbc:sqlserver://localhost:1433;databaseName=project;integratedSecurity=true";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url);
            st = con.createStatement();
            rt = st.executeQuery("select * from Conversion");
            while (rt.next()) {
                list1.add(rt.getString(2));
                list2.add(rt.getString(2));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        l1 = new Label("From");
        l2 = new Label("To");
        l3 = new Label("Amount");
        l4 = new Label("       ");
        t1 = new TextField();
        b2 = new Button("Exit application");
        l5 = new Label("Currency 1");
        l6 = new Label("Currency 2");
        l7 = new Label("   ");
        l8 = new Label("   ");
        //setLayout(null);
        //setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        setLocationRelativeTo(null);
        l1.setBounds(30, 80, 75, 30);
        l1.setFont(new Font("Courier New", Font.PLAIN, 25));
        l1.setBackground(new Color(224, 224, 224, 0));
        //l1.setForeground(Color.WHITE);
        add(l1);

        l5.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        l5.setBounds(160, 80, 120, 30);
        l5.setBackground(new Color(220, 220, 220, 0));
        add(l5);

        list1.setFont(new Font("TimesRoman", Font.BOLD, 30));
        list1.setBounds(160, 150, 250, 200);
        add(list1);

        l2.setBounds(500, 80, 40, 30);
        l2.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        l2.setBackground(Color.lightGray);
        l2.setBackground(new Color(204, 204, 204, 0));
        add(l2);

        l6.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        l6.setBounds(600, 80, 130, 30);
        l6.setBackground(new Color(198, 198, 198, 0));
        add(l6);

        list2.setFont(new Font("TimesRoman", Font.BOLD, 30));
        list2.setBounds(600, 150, 250, 200);
        add(list2);

        l3.setBounds(30, 400, 110, 30);
        l3.isLightweight();
        l3.setFont(new Font("Courier New", Font.PLAIN, 25));
        l3.setBackground(new Color(234, 234, 234, 0));
        add(l3);

        t1.setBounds(160, 400, 250, 40);
        t1.setFont(new Font("Courier New", Font.BOLD, 30));
        add(t1);

        l7.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        l7.setBounds(450, 400, 80, 40);
        l7.setBackground(new Color(224, 224, 224, 0));
        add(l7);

        l4.setBounds(600, 400, 250, 40);
        l4.setFont(new Font("Courier New", Font.PLAIN, 25));
        l4.setText("Conversion output.");
        l4.setForeground(new Color(222, 222, 222, 0));
        l4.setBackground(new Color(97, 120, 91));
        add(l4);

        l8.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        l8.setBounds(900, 400, 80, 40);
        l8.setBackground(new Color(210, 210, 210, 0));
        add(l8);

        add(b2);
        b2.setBounds(600, 525, 250, 40);
        b2.setFont(new Font("Courier New", Font.PLAIN, 25));

        
        b2.addActionListener(this);
        t1.addKeyListener(this);
        list1.addItemListener(this);
        list2.addItemListener(this);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
        setLayout(null);
    }

    public void itemStateChanged(ItemEvent ie) {
        cur1 = list1.getSelectedItem();
        cur2 = list2.getSelectedItem();
        String url = "jdbc:sqlserver://localhost:1433;databaseName=project;integratedSecurity=true";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url);
            st = con.createStatement();
            String SQL = "select rate from Conversion where currency='" + cur1 + "'";
            rt = st.executeQuery(SQL);
            while (rt.next()) {
                x = rt.getFloat(1);
            }
            st = con.createStatement();
            String SQL2 = "select rate from Conversion where currency='" + cur2 + "'";
            rt = st.executeQuery(SQL2);
            while (rt.next()) {
                y = rt.getFloat(1);
            }
            con.close();

            try {
                int amount = Integer.parseInt(t1.getText());
                float total = (x / y) * amount;
                l4.setText(String.valueOf(total));
                l7.setText(cur1);
                l8.setText(cur2);
            } catch (Exception m) {
                if (t1.getText().isEmpty()) {
                    l4.setText("Conversion output.");
                    l7.setText("   ");
                    l8.setText("   ");
                } else {
                    l4.setText("Invalid input.");
                }
            }
        } catch (Exception m) {
            System.out.println(m.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            try {
                int amount = Integer.parseInt(t1.getText());
                float total = (x / y) * amount;
                l4.setText(String.valueOf(total));
                l7.setText(cur1);
                l8.setText(cur2);
            } catch (Exception m) {

                l4.setText("Invalid input.");
            }

        } else if (ae.getSource() == b2) {
            System.exit(0);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        l4.setText("calculating...");
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            int amount = Integer.parseInt(t1.getText());
            float total = (x / y) * amount;
            l4.setText(String.valueOf(total));
            l7.setText(cur1);
            l8.setText(cur2);
        } catch (Exception m) {
            if (t1.getText().isEmpty()) {
                l4.setText("Conversion output.");
                l7.setText("   ");
                l8.setText("   ");
            } else {
                l4.setText("Invalid input.");
            }

        }
    }
}

public class JDBCproj2 {

    public static void main(String[] args) {
        convertor object = new convertor();
    }

}
