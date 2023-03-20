import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.BorderFactory;

public class myFrame extends JFrame implements ActionListener {
    ArrayList<String> id_DataBase = new ArrayList<>();
    ArrayList<String> quantity_DataBase = new ArrayList<>();
    ArrayList<String> confirmed_item = new ArrayList<>();
    ArrayList<String> transitions = new ArrayList<>();

    int counter = 1;
    JButton findButton = new JButton();
    JButton purchaseButton = new JButton();
    JButton viewButton = new JButton();
    JButton checkButton = new JButton();
    JButton startButton = new JButton();
    JButton exitButton = new JButton();
    JTextField id_filed = new JTextField();
    JTextField quantity_filed = new JTextField();
    JTextField details_filed = new JTextField();
    JTextField subtotal_filed = new JTextField();


    JLabel id_label = new JLabel();
    JLabel quantity_label = new JLabel();
    JLabel details_label = new JLabel();
    JLabel subtotal_label = new JLabel();

    String subTotal;
    String totalFinal = "0";

    String[][] db;

    ArrayList<String[]> dataBase = new ArrayList<String[]>();


    JPanel topPanel = new JPanel();
    JPanel bottomPanel = new JPanel();


    myFrame(String file) {

        this.setVisible(true);
        this.setResizable(false);
        this.setSize(1000, 600);
        this.setTitle("Nile Dot Cam - Spring 2023");
        this.setLayout(null);
        topPanel = topPanelMaker();
        bottomPanel = bottomPanelMaker();


        // adding buttons to bottom panel


        addButtons();
        purchaseButton.setEnabled(false);
        viewButton.setEnabled(false);
        checkButton.setEnabled(false);

        this.add(topPanel); // top Panel
        this.add(bottomPanel); // bottom Panel
        buildDB(file);
        findButton.addActionListener(this);
        purchaseButton.addActionListener(this);
        startButton.addActionListener(this);
        viewButton.addActionListener(this);
        exitButton.addActionListener(this);
        checkButton.addActionListener(this);


    }

    JPanel topPanelMaker() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1000, 300);
        panel.setBackground(new Color(189, 215, 214, 255));
        panel.add(textFiledMaker(id_filed, 650, 25));
        panel.add(textFiledMaker(quantity_filed, 650, 85));
        panel.add(textFiledMaker(details_filed, 650, 145));
        panel.add(textFiledMaker(subtotal_filed, 650, 205));


        id_label = textLabelMaker(270, 0, "Enter item ID for item #" + counter + ": ");
        quantity_label = textLabelMaker(270, 50, "Enter quantity for item #" + counter + ": ");
        details_label = textLabelMaker(270, 110, "Details for #" + counter + ": ");
        subtotal_label = textLabelMaker(270, 175, "Order subtotal for " + (counter - 1) + " item(s) #: ");

        panel.add(id_label);
        panel.add(quantity_label);
        panel.add(details_label);
        panel.add(subtotal_label);


        return panel;
    }

    JPanel bottomPanelMaker() {

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 300, 1000, 300);
        panel.setBackground(new Color(127, 157, 157, 255));


        return panel;
    }

    JTextField textFiledMaker(JTextField field, int x, int y) {

        field.setLayout(null);
        field.setPreferredSize(new Dimension(200, 50));
        field.setBackground(new Color(209, 227, 226));
        field.setBounds(x - 100, y, 400, 45);
        field.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 18));
//        field.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        field.setVisible(true);


        return field;
    }

    JLabel textLabelMaker(int x, int y, String text) {

        JLabel content = new JLabel();
        content.setLayout(null);
        content.setBounds(x - 150, y, 400, 100);
        content.setText(text);
        content.setFont(new Font("Italic", Font.LAYOUT_LEFT_TO_RIGHT, 25));
        content.setForeground(new Color(44, 44, 49));
        content.setVisible(true);

        return content;
    }

    JButton buttonDes(JButton button, String text, int x, int y) {
        button.setVisible(true);
        button.setLayout(null);
        button.setBounds(x, y, 300, 40);
        // button.setSize(new Dimension(200, 40));
        button.setText(text);
        button.setFocusable(false);
        button.setBackground(new Color(64, 79, 79));
        button.setFont(new Font("Italic", Font.LAYOUT_LEFT_TO_RIGHT, 18));
        button.setForeground(Color.black);

        return button;


    }

    void addButton(JButton button) {
        bottomPanel.add((button));
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == findButton) {
            if (id_filed.getText().isEmpty() || quantity_filed.getText().isEmpty()) {
                if (id_filed.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Please Enter item ID!",
                            "  Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Please Enter item Quantity!",
                            "  Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                id_DataBase.add(id_filed.getText());
                quantity_DataBase.add((quantity_filed.getText()));
                // throwPane();
                // System.out.println(id_DataBase.get(counter-1));
//            System.out.println(id_DataBase);
//            System.out.println(quantity_DataBase);
                throwPane();

            }


        }
        if (e.getSource() == purchaseButton) {

            JOptionPane.showConfirmDialog(null,
                    "Item #" + (counter - 1) + " accepted. Added to your cart.",
                    "  Nile Dot Com - Item Confirmed", JOptionPane.PLAIN_MESSAGE);

            try {
                generateOutputFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            updateLabel();
            findButton.setEnabled(true);
            purchaseButton.setEnabled(false);
            viewButton.setEnabled(true);
            checkButton.setEnabled(true);
            // generateOutputFile();
        }

        if (e.getSource() == startButton) {
            try {
                clearTheFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            clearEverything();

        }

        if (e.getSource() == viewButton) {

            ImageIcon iconView = new ImageIcon("src/view.png");
            Image image = iconView.getImage(); // transform it
            Image newImg = image.getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            iconView = new ImageIcon(newImg);  // transform it back


            StringBuilder sb = new StringBuilder();
            int i = 1;
            for (String s : confirmed_item) {
                sb.append(i);
                sb.append(". ");
                sb.append(s);
                sb.append("\n");
                i++;
            }

            JOptionPane.showMessageDialog(null,
                    sb.toString(),
                    "  Nile Dot Com - Current Shopping Cart Status", JOptionPane.INFORMATION_MESSAGE, iconView);

        }


        if (e.getSource() == exitButton) {

            try {
                clearTheFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }

        if (e.getSource() == checkButton) {


            ImageIcon icon = new ImageIcon("src/invo.png");
            Image image = icon.getImage(); // transform it
            Image newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(newImg);  // transform it back


            JOptionPane.showMessageDialog(null,
                    generateInvoice(),
                    "  Nile Dot Com - FINAL INVOICE", JOptionPane.INFORMATION_MESSAGE, icon);
            id_filed.setEnabled(false);
            quantity_filed.setEnabled(false);

        }


    }

    void buildDB(String file) {
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                dataBase.add(line.split(","));
                //  System.out.println(dataBase[3]);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        db = new String[dataBase.size()][0];
        dataBase.toArray(db);

    }


    void throwPane() {
        int res = (search(0, id_DataBase.get(counter - 1)));
        // System.out.println(res);
        //System.out.println(db[res][2]);

        if (res >= 0) {

            if (db[res][2].replaceAll("\\s", "").equals("true")) {
                itemFound(res);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Sorry... that item is out of stock, please try another item",
                        "  Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
                id_filed.setText("");
                quantity_filed.setText("");
                id_DataBase.remove(counter - 1);
                quantity_DataBase.remove(counter - 1);

            }

        } else {
            JOptionPane.showMessageDialog(null,
                    "Item ID " + id_DataBase.get(counter - 1) + " not in file",
                    "  Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
            id_filed.setText("");
            quantity_filed.setText("");
            id_DataBase.remove(counter - 1);
            quantity_DataBase.remove(counter - 1);
        }


    }

    int search(int index_start, String item) {
//        System.out.println(db.length);

        for (int i = 0; i < db.length; i++) {
            if (item.equals(db[i][index_start])) {
                return i;
            }

        }
        return -9999;
    }

    String discount(int count) {

        int item = Integer.parseInt(quantity_DataBase.get(count));

        if (item >= 0 && item <= 4) {
            return "0";
        } else if (item >= 5 && item <= 9) {
            return "10";
        } else if (item >= 10 && item <= 14) {
            return "15";
        } else {
            return "20";
        }
    }

    String applyDiscount(int count, int res) {

        DecimalFormat twoDForm = new DecimalFormat("#.##");

        float dis = Float.parseFloat(discount(count)) / 100;
        float total = (Float.parseFloat(db[res][3]) - (Float.parseFloat(db[res][3]) * dis)) * Float.parseFloat(quantity_DataBase.get(count));
        total = Float.valueOf(twoDForm.format(total));
        return Float.toString(total);

    }

    void updateLabel() {
        id_label.setText("Enter item ID for item #" + counter + ": ");
        quantity_label.setText("Enter quantity for item #" + counter + ": ");
        details_label.setText("Details for #" + (counter - 1) + ": ");
        subtotal_label.setText("Order subtotal for " + (counter - 1) + " item(s) #: ");

        findButton.setText("Find Item #" + counter);
        purchaseButton.setText("Purchase Item #" + counter);
        id_filed.setText("");
        quantity_filed.setText("");
        subtotal_filed.setText(calcTotal(subTotal, totalFinal));


    }


    void addButtons() {
        int x = 0;
        int y = 0;
        addButton(buttonDes(findButton, "Find Item #" + counter, x + 200, y + 30));
        addButton(buttonDes(purchaseButton, "Purchase Item #" + counter, x + 550, y + 30));
        addButton(buttonDes(viewButton, "View Current Order", x + 200, y + 90));
        addButton(buttonDes(checkButton, "Complete Order - Check Out ", x + 550, y + 90));
        addButton(buttonDes(startButton, "Start New Order", x + 200, y + 150));
        addButton(buttonDes(exitButton, "Exit (Close App)", x + 550, y + 150));


    }

    void itemFound(int res) {
        subTotal = applyDiscount(counter - 1, res);


        String details = db[res][0] + " " + db[res][1] + " $" + db[res][3] +
                " " + quantity_DataBase.get(counter - 1) + " " + discount(counter - 1) + "%" +
                " $" + subTotal;
        confirmed_item.add(details);
        details_filed.setText(confirmed_item.get(counter - 1));
        details_filed.setFont(new Font("Italic", Font.LAYOUT_LEFT_TO_RIGHT, 10));
        counter++;
        purchaseButton.setEnabled(true);
        findButton.setEnabled(false);

    }

    String calcTotal(String priceWithDiscount, String prevTotal) {
        String res = "";
        DecimalFormat twoDForm = new DecimalFormat("#.##");

        //float quantity= Float.parseFloat(quantity_DataBase.get(counter-2));
        float price = Float.parseFloat(priceWithDiscount);
        //float tot=Float.parseFloat(totalFinal);


        float total = Float.parseFloat(totalFinal) + (price);
//        System.out.println(tot+"     "+total+"     "+quantity);
        totalFinal = Float.toString(total);
        total = Float.valueOf(twoDForm.format(total));

        return "$" + Float.toString(total);

    }

    void clearEverything() {


        id_DataBase.removeAll(id_DataBase);
        quantity_DataBase.removeAll(quantity_DataBase);
        confirmed_item.removeAll(confirmed_item);
        subTotal = "";
        totalFinal = "0";
        counter = 1;


        id_label.setText("Enter item ID for item #" + counter + ": ");
        quantity_label.setText("Enter quantity for item #" + counter + ": ");
        details_label.setText("Details for #" + (counter) + ": ");
        subtotal_label.setText("Order subtotal for " + (counter - 1) + " item(s) #: ");

        findButton.setText("Find Item #" + counter);
        purchaseButton.setText("Purchase Item #" + counter);
        id_filed.setText("");
        quantity_filed.setText("");
        details_filed.setText("");
        subtotal_filed.setText("");
        id_filed.setEnabled(true);
        quantity_filed.setEnabled(true);

//        System.out.println(id_DataBase);
//        System.out.println(quantity_DataBase);
//        System.out.println(confirmed_item);
//


    }

    String generateTime() {
        SimpleDateFormat formatDate = new SimpleDateFormat(
                "dd/MM/yyyy  HH:mm:ss a z");

        Date date = new Date();
        formatDate.setTimeZone(TimeZone.getTimeZone("EST"));

        return "Date: " + formatDate.format(date).toString();

    }

    String generateInvoice() {

        DecimalFormat twoDForm = new DecimalFormat("#.##");

        StringBuilder st = new StringBuilder();
        int i = 1;
        Float taxAmount = Float.parseFloat(totalFinal) * (0.06F);
        Float totalWithTax = Float.parseFloat(totalFinal) + taxAmount;
        Float totFin = Float.valueOf(twoDForm.format(Float.parseFloat(totalFinal)));

        st.append(generateTime());
        st.append("\n\n");
        st.append("Number of line items: " + quantity_DataBase.size());
        st.append("\n\n");
        st.append("Item# / ID / Title / Price / Qty / Disc% / Subtotal:");
        st.append("\n\n");

        for (String s : confirmed_item) {
            st.append(i);
            st.append(". ");
            st.append(s);
            st.append("\n");
            i++;
        }

        st.append("\n\n");
        st.append("Order subtotal: $" + totFin + "\n\n");
        st.append("Tax Rate: 6%\n\n");
        st.append("Tax Amount: $" + Float.valueOf(twoDForm.format(taxAmount)));
        st.append("\n\nORDER TOTAL: $" + Float.valueOf(twoDForm.format(totalWithTax)));
        st.append("\n\nThanks for shopping at Nile Dot Com! ");


        return st.toString();

    }

    void generateOutputFile() throws IOException {

        BufferedWriter out = null;

        FileWriter fstream = null; //true tells to append data.
        try {
            fstream = new FileWriter("transactions.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out = new BufferedWriter(fstream);


        StringBuilder sd = new StringBuilder();


        String trans = generatIds() + ", " + confirmed_item.get(confirmed_item.size() - 1).replaceAll("  ", " ") +
                ", " + quantity_DataBase.get(quantity_DataBase.size() - 1) + ", " + discount(counter - 2) + ", " +
                "$" + subTotal + ", " + generateTime();

        transitions.add(trans);

        out.write(transitions.get(transitions.size() - 1) + "\n");
        out.close();

    }


    String generatIds() {
        SimpleDateFormat formatDate = new SimpleDateFormat(
                "ddMMyyyyHHmm");

        Date date = new Date();
        formatDate.setTimeZone(TimeZone.getTimeZone("EST"));

        return formatDate.format(date).toString();

    }


    public static void clearTheFile() throws IOException {
        FileWriter fwOb = new FileWriter("transactions.txt", false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }

}
