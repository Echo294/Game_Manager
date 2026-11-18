import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class Main {
    private final GameManager manager = new GameManager();
    private JFrame frame;
    private final Color background = new Color(23,30,43);
    private final Color accent = new Color(52,77,117);
    private final Color text = new Color(230,237,247);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().start());
    }
    private void start() {
        frame = new JFrame("Game Vault - Video Game Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850,560);
        frame.setMinimumSize(new Dimension(680,450));
        frame.setLocationRelativeTo(null);
        showMenu();
        frame.setVisible(true);
    }
    private JButton button(String label) {
        JButton b = new JButton(label);
        b.setFont(new Font("SansSerif",Font.BOLD,16));
        b.setBackground(accent);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorder(BorderFactory.createEmptyBorder(12,18,12,18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
    private JPanel panel() {
        JPanel p = new JPanel();
        p.setBackground(background);
        p.setBorder(BorderFactory.createEmptyBorder(25,30,25,30));
        return p;
    }
    private JLabel heading(String value) {
        JLabel label = new JLabel(value, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif",Font.BOLD,30));
        label.setForeground(new Color(114,183,255));
        return label;
    }
    private void showMenu() {
        JPanel root = panel();
        root.setLayout(new GridBagLayout());
        JPanel center = new JPanel(new GridLayout(0,1,0,14));
        center.setBackground(background);
        center.setPreferredSize(new Dimension(370,390));
        center.add(heading("GAME VAULT"));
        JLabel subtitle = new JLabel("Video Game Management System",SwingConstants.CENTER);
        subtitle.setForeground(text);
        center.add(subtitle);
        JButton library = button("View Game Library"); library.addActionListener(e -> showLibrary()); center.add(library);
        JButton add = button("Add New Game"); add.addActionListener(e -> addGame()); center.add(add);
        JButton stats = button("Collection Statistics"); stats.addActionListener(e -> showStatistics()); center.add(stats);
        JButton exit = button("Exit Application"); exit.addActionListener(e -> frame.dispose()); center.add(exit);
        root.add(center);
        frame.setContentPane(root); frame.revalidate(); frame.repaint();
    }
    private void addGame() {
        JTextField title = new JTextField();
        JComboBox<String> genre = new JComboBox<>(new String[]{"Action","Adventure","RPG","Sandbox","Strategy","Simulation","Sports","Puzzle","Other"});
        JComboBox<String> platform = new JComboBox<>(new String[]{"PC","PS5","PS4","Xbox","Nintendo Switch","Nintendo DS","Other"});
        JCheckBox physical = new JCheckBox("Physical copy");
        JComboBox<String> condition = new JComboBox<>(new String[]{"New","Good","Fair","Poor"});
        condition.setEnabled(false);
        physical.addActionListener(e -> condition.setEnabled(physical.isSelected()));
        JPanel form = new JPanel(new GridLayout(0,2,10,12));
        form.add(new JLabel("Game title:")); form.add(title);
        form.add(new JLabel("Genre:")); form.add(genre);
        form.add(new JLabel("Platform:")); form.add(platform);
        form.add(physical); form.add(condition);
        while (JOptionPane.showConfirmDialog(frame,form,"Add New Game",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE)==JOptionPane.OK_OPTION) {
            if (title.getText().isBlank()) { JOptionPane.showMessageDialog(frame,"Enter a game title."); continue; }
            String t=title.getText().trim(), g=(String)genre.getSelectedItem(), p=(String)platform.getSelectedItem();
            try {
                manager.addGame(physical.isSelected() ? new PhysicalGame(t,g,p,(String)condition.getSelectedItem()) : new Game(t,g,p));
                JOptionPane.showMessageDialog(frame,"Game added successfully!");
                return;
            } catch (Exception ex) { JOptionPane.showMessageDialog(frame,ex.getMessage(),"Save error",JOptionPane.ERROR_MESSAGE); return; }
        }
    }
    private void showLibrary() {
        JPanel root=panel(); root.setLayout(new BorderLayout(12,14));
        root.add(heading("MY GAME LIBRARY"),BorderLayout.NORTH);
        JPanel middle = new JPanel(new BorderLayout(8,8));
        JTextField search=new JTextField();
        search.setToolTipText("Search by title, genre or platform");
        JPanel searchRow = new JPanel(new BorderLayout(8,0));
        searchRow.add(new JLabel("Search:"),BorderLayout.WEST); searchRow.add(search,BorderLayout.CENTER);
        middle.add(searchRow,BorderLayout.NORTH);
        DefaultTableModel model=new DefaultTableModel(new String[]{"Title","Genre","Platform","Condition"},0) {
            @Override public boolean isCellEditable(int r,int c) { return false; }
        };
        JTable table=new JTable(model); table.setRowHeight(28); table.setAutoCreateRowSorter(true);
        java.util.List<Game> shown=new ArrayList<>();
        Runnable refresh=()->{
            shown.clear(); model.setRowCount(0);
            String query=search.getText().toLowerCase(Locale.ROOT);
            for (Game g:manager.getGames()) {
                if (!(g.getTitle()+" "+g.getGenre()+" "+g.getPlatform()).toLowerCase(Locale.ROOT).contains(query)) continue;
                shown.add(g);
                model.addRow(new Object[]{g.getTitle(),g.getGenre(),g.getPlatform(),g instanceof PhysicalGame ? ((PhysicalGame)g).getCondition() : "Digital"});
            }
        };
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e){refresh.run();}
            public void removeUpdate(javax.swing.event.DocumentEvent e){refresh.run();}
            public void changedUpdate(javax.swing.event.DocumentEvent e){refresh.run();}
        });
        refresh.run(); middle.add(new JScrollPane(table),BorderLayout.CENTER); root.add(middle,BorderLayout.CENTER);
        JPanel actions=new JPanel(new FlowLayout(FlowLayout.CENTER,12,0)); actions.setBackground(background);
        JButton add=button("Add Game"); add.addActionListener(e->{addGame();refresh.run();});
        JButton remove=button("Delete Selected"); remove.addActionListener(e->{
            int row=table.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(frame,"Select a game first.");return;}
            int index=table.convertRowIndexToModel(row);
            if(JOptionPane.showConfirmDialog(frame,"Delete "+shown.get(index).getTitle()+"?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try {manager.removeGame(shown.get(index));refresh.run();} catch(Exception ex){JOptionPane.showMessageDialog(frame,ex.getMessage());}
            }
        });
        JButton back=button("Main Menu"); back.addActionListener(e->showMenu());
        actions.add(add);actions.add(remove);actions.add(back);root.add(actions,BorderLayout.SOUTH);
        frame.setContentPane(root);frame.revalidate();frame.repaint();
    }
    private void showStatistics() {
        int total=manager.getGames().size();
        long physical=manager.getGames().stream().filter(g->g instanceof PhysicalGame).count();
        long digital=total-physical;
        Set<String> platforms=new HashSet<>();
        for(Game g:manager.getGames()) platforms.add(g.getPlatform());
        JOptionPane.showMessageDialog(frame,"Total Games: "+total+"\nPhysical Games: "+physical+"\nDigital Games: "+digital+"\nPlatforms: "+platforms.size(),"Collection Statistics",JOptionPane.INFORMATION_MESSAGE);
    }
}
