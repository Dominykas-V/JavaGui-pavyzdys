
//Dominykas V. PRM20NKNS
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//------------------------------------------------------------------
class Sportininkas {
    private String vardas;
    private String pavarde;
    private double ugis;
    private double svoris;

    Sportininkas() {
    }

    Sportininkas(String v, String p, double u, double s) {
        this.vardas = v;
        this.pavarde = p;
        this.ugis = u;
        this.svoris = s;
    }

    // -----Setters

    // -----Getters
    public String getPilnasSportininkas() {
        return this.vardas + " " + this.pavarde + " " + this.ugis + " " + this.svoris;
    }

    public String getVardas() {
        return this.vardas;
    }

    public String getPavarde() {
        return this.pavarde;
    }

    public double getUgis() {
        return this.ugis;
    }

    public double getsvoris() {
        return this.svoris;
    }

}

// ----------------------------------------
class Komanda {
    static private ArrayList<Sportininkas> komanda = new ArrayList<>();

    public String surastiSportininka(String var, String pav) {
        for (int i = 0; i < komanda.size(); i++) {
            if (komanda.get(i).getVardas().equals(var) && komanda.get(i).getPavarde().equals(pav)) {
                return "Sportininkas rastas: " + komanda.get(i).getPilnasSportininkas() + ".";
            }
        }
        return "Sportininkas nerastas.";
    }

    // --
    public String surastiSportininka(String var, String pav, double ug) {
        for (int i = 0; i < komanda.size(); i++) {
            if (komanda.get(i).getVardas().equals(var) && komanda.get(i).getPavarde().equals(pav)
                    && (komanda.get(i).getUgis() == ug)) {
                return "Sportininkas rastas: " + komanda.get(i).getPilnasSportininkas() + ".";
            }
        }
        return "Sportininkas nerastas.";
    }

    // --
    public void pasalintiZaideja(int index) {
        komanda.remove(index);
    }

    // --
    public void sortPavarde() {
        komanda.sort((o1, o2) -> o1.getPavarde().compareTo(o2.getPavarde()));
    }

    // --
    public void sortUgis() {
        for (int i = 0; i < komanda.size(); i++) {
            for (int k = 0; k < komanda.size(); k++) {
                if (komanda.get(i).getUgis() > komanda.get(k).getUgis()) {
                    Collections.swap(komanda, i, k);

                }
            }
        }
    }

    // --
    public void SortPavardeUgis() {
        sortPavarde();
        for (int i = 0; i < komanda.size(); i++) {
            for (int k = i; k < komanda.size(); k++) {
                if (komanda.get(i).getPavarde().equals(komanda.get(k).getPavarde())
                        && komanda.get(i).getUgis() < komanda.get(k).getUgis()) {
                    Collections.swap(komanda, i, k);
                }
            }
        }
    }

    // --
    public void atvaizduotiSarasa() {
        if (getKiekNariu() > 0) {
            for (int i = 0; i < getKiekNariu(); i++) {
                System.out.println(i + " - " + getNari(i));
            }
        } else {
            System.out.println("Sarasas tuscias!");
        }
    }

    // -----Setters
    public void pridetiNari(Sportininkas sp) {
        komanda.add(sp);

    }

    // -----Getters
    public int getKiekNariu() {
        return komanda.size();
    }

    // --
    public Sportininkas getNari(int index) {
        return komanda.get(index);
    }

    // --
    public double getVidutinisUgis() {
        double vid = 0;
        for (int i = 0; i < komanda.size(); i++) {
            vid += komanda.get(i).getUgis();
        }
        return vid / komanda.size();
    }

}

// ---------------------------------------------------------------------------
class PagrindinisLangas extends JFrame {

    private Komanda kom = new Komanda();
    private DefaultTableModel model = new DefaultTableModel();

    PagrindinisLangas() {
        // MENU - duomenu suvedimas

        JMenuBar meniuEilute = new JMenuBar();
        Container cnt = getContentPane();
        cnt.setLayout(new BorderLayout());

        // Kaip suvesti duomenis
        JMenu kaipSuvestiDuomenis = new JMenu("Kaip suvesti duomenis");
        JMenuItem ivestiIsKlaveturos = new JMenuItem("Ivesti is klaveturos");
        kaipSuvestiDuomenis.add(ivestiIsKlaveturos);
        meniuEilute.add(kaipSuvestiDuomenis);
        ivestiIsKlaveturos.addActionListener(new AtidarymasIsKLaModalas());

        JMenuItem sukeltiIsFailo = new JMenuItem("Sukelti is failo");
        kaipSuvestiDuomenis.add(sukeltiIsFailo);
        meniuEilute.add(kaipSuvestiDuomenis);
        sukeltiIsFailo.addActionListener(new AtidarymasIsFailoModalas());

        // Rasti Pasalinti
        JMenu rastiPasalinti = new JMenu("Rasti/Pasalinti");
        JMenuItem rastiSportininka = new JMenuItem("Rasti sportininka");
        rastiPasalinti.add(rastiSportininka);
        meniuEilute.add(rastiPasalinti);
        rastiSportininka.addActionListener(new SurastiSportininkaModal());

        JMenuItem pasalintiSportininka = new JMenuItem("Pasalinti sportininka");
        rastiPasalinti.add(pasalintiSportininka);
        meniuEilute.add(rastiPasalinti);
        pasalintiSportininka.addActionListener(new PasalintiSportininkaModal());
        setJMenuBar(meniuEilute);

        // Main window ---
        JPanel langas = new JPanel();
        langas.setLayout(new FlowLayout());

        JButton ugioVidurkis = new JButton("Vidutinis komandos ugis");
        langas.add(ugioVidurkis);
        langas.add(Box.createHorizontalStrut(10));

        // Kaip rusiuoti
        JRadioButton pavarde = new JRadioButton("Pavarde", true);
        JRadioButton ugis = new JRadioButton("Ugis", false);
        JRadioButton pavardeIrUgis = new JRadioButton("Abu", false);
        ButtonGroup grupe = new ButtonGroup();
        JPanel radioLangas = new JPanel();
        radioLangas.setBorder(new TitledBorder("Rusevimo pasirinkimas"));
        grupe.add(pavarde);
        grupe.add(ugis);
        grupe.add(pavardeIrUgis);

        radioLangas.add(pavarde);
        radioLangas.add(ugis);
        radioLangas.add(pavardeIrUgis);

        langas.add(radioLangas);

        // Lentele
        JTable table = new JTable();

        Object[] columns = { "ID", "Vardas", "Pavarde", "Ugis", "Svoris" };
        // DefaultTableModel model = new DefaultTableModel();//
        model.setColumnIdentifiers(columns);

        table.setModel(model);
        table.setRowHeight(30);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 880, 200);

        langas.add(pane);

        // lenteles duomenys

        cnt.add(langas, BorderLayout.CENTER); // sudeda viska i langa

        // --- acctionListiners rusevimas---
        ugioVidurkis.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PranesimoModalas("Vidutinis komandos ugis yra: " + kom.getVidutinisUgis());
            }
        });
        pavarde.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                kom.sortPavarde();
                perbraizytiLentele();
            }
        });
        ugis.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                kom.sortUgis();
                perbraizytiLentele();
            }
        });
        pavardeIrUgis.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                kom.SortPavardeUgis();
                perbraizytiLentele();
            }
        });
    }

    // --------------------------------------------------
    void pridetiPrieLenteles(int index) {
        Object[] row = new Object[5];
        row[0] = index + ".";
        row[1] = kom.getNari(index).getVardas();
        row[2] = kom.getNari(index).getPavarde();
        row[3] = kom.getNari(index).getUgis();
        row[4] = kom.getNari(index).getsvoris();
        model.addRow(row);
    }

    // -----
    void perbraizytiLentele() {
        Object[] row = new Object[5];
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        for (int i = 0; i < kom.getKiekNariu(); i++) {
            row[0] = i + ".";
            row[1] = kom.getNari(i).getVardas();
            row[2] = kom.getNari(i).getPavarde();
            row[3] = kom.getNari(i).getUgis();
            row[4] = kom.getNari(i).getsvoris();
            model.addRow(row);
        }
    }

    // -----
    public static boolean isNumeric(String string) {
        double value;
        if (string == null || string.equals("")) {
            return false;
        }
        try {
            value = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        try {
            value = Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    // -----
    void PranesimoModalas(String MSG) {
        JPanel pranesimas = new JPanel(new GridLayout(0, 1));
        pranesimas.add(new JLabel(MSG));
        JOptionPane.showConfirmDialog(null, pranesimas, "Pranesimas", JOptionPane.CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

    }

    // -----
    class AtidarymasIsKLaModalas implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel popUpas = new JPanel(new GridLayout(0, 1));

            JPanel ivedimai = new JPanel(new GridLayout(0, 2));
            ivedimai.add(new JLabel("Vardas: "));
            JTextField vardas = new JTextField();
            ivedimai.add(vardas);

            ivedimai.add(new JLabel("Pavarde: "));
            JTextField pavarde = new JTextField();
            ivedimai.add(pavarde);

            ivedimai.add(new JLabel("Ugis: "));
            JTextField ugis = new JTextField();
            ivedimai.add(ugis);

            ivedimai.add(new JLabel("Svoris: "));
            JTextField svoris = new JTextField();
            ivedimai.add(svoris);

            popUpas.add(ivedimai);
            int result = JOptionPane.showConfirmDialog(null, popUpas, "Iveskite Is Klaveturos",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION && isNumeric(ugis.getText()) && isNumeric(svoris.getText())) {
                kom.pridetiNari(new Sportininkas(vardas.getText(), pavarde.getText(),
                        Double.parseDouble(ugis.getText()), Double.parseDouble(svoris.getText())));
                pridetiPrieLenteles(kom.getKiekNariu() - 1);

                return;
            } else {
                PranesimoModalas("Blogai suvesti duomenys!");
            }
        }
    }

    // -----
    class AtidarymasIsFailoModalas implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                File fr = new File("Duomenys.txt");
                Scanner sc = new Scanner(fr);
                while (sc.hasNext()) {
                    String vardas = sc.next();
                    String pavarde = sc.next();
                    double ugis = sc.nextDouble();
                    double svoris = sc.nextDouble();
                    kom.pridetiNari(new Sportininkas(vardas, pavarde, ugis, svoris));
                    pridetiPrieLenteles(kom.getKiekNariu() - 1);

                }
                PranesimoModalas("Sarasas sukeltas is failo.");
                sc.close();
            } catch (FileNotFoundException er) {
                PranesimoModalas("Duomenu failas nerastas!");
                er.printStackTrace();
            }
        }
    }

    // -----
    class SurastiSportininkaModal implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JPanel popUpas = new JPanel(new GridLayout(0, 1));
            popUpas.add(new JLabel("Surasti sportininka. (ugis nebutinas)"));

            JPanel ivedimai = new JPanel(new GridLayout(0, 2));
            ivedimai.add(new JLabel("Vardas: "));
            JTextField vardas = new JTextField();
            ivedimai.add(vardas);

            ivedimai.add(new JLabel("Pavarde: "));
            JTextField pavarde = new JTextField();
            ivedimai.add(pavarde);

            ivedimai.add(new JLabel("Ugis*: "));
            JTextField ugis = new JTextField();
            ivedimai.add(ugis);

            popUpas.add(ivedimai);
            int result = JOptionPane.showConfirmDialog(null, popUpas, "Surasti sportininka",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                if (ugis.getText().equals(null) || ugis.getText().equals("")) {
                    PranesimoModalas(kom.surastiSportininka(vardas.getText(), pavarde.getText()));
                } else if (isNumeric(ugis.getText())) {
                    PranesimoModalas(kom.surastiSportininka(vardas.getText(), pavarde.getText(),
                            Double.parseDouble(ugis.getText())));
                } else {
                    PranesimoModalas("Klaida vedant duomenis!");
                }
            }
        }
    }

    // -----
    class PasalintiSportininkaModal implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JPanel popUpas = new JPanel(new GridLayout(0, 1));
            JPanel vaizdas = new JPanel(new GridLayout(0, 2));
            JTextField ID = new JTextField();
            JButton pasalintiVisus = new JButton("Pasalinti visus sportininkus");
            JLabel arba = new JLabel("-------Arba-------");
            arba.setHorizontalAlignment(JLabel.CENTER);
            popUpas.add(pasalintiVisus);
            popUpas.add(arba);
            vaizdas.add(new JLabel("Sportininko ID: "));
            vaizdas.add(ID);
            popUpas.add(vaizdas);

            pasalintiVisus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    while (kom.getKiekNariu() != 0) {
                        kom.pasalintiZaideja(0);
                    }
                    perbraizytiLentele();
                    PranesimoModalas("Visi zaidejai pasalinti!");

                }
            });
            int result = JOptionPane.showConfirmDialog(null, popUpas, "Pasalinti sportininka",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                if (isNumeric(ID.getText())) {
                    kom.pasalintiZaideja(Integer.parseInt(ID.getText()));
                    perbraizytiLentele();
                } else {
                    PranesimoModalas("Klaida vedant duomenis!");
                }
            }

        }
    }
}

// -----------------------------------
public class App {
    public static void main(String[] args) {
        PagrindinisLangas langas = new PagrindinisLangas();
        langas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        langas.setSize(500, 600);
        langas.setTitle("Sportininkai");
        langas.setVisible(true);
    }

}
// -----------------------------------