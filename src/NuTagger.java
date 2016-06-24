/* 
 * The MIT License
 *
 * Copyright 2016 Stephen Gregoratto.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
import IOPackage.TagIO;
import com.opencsv.CSVReader;
import entagged.audioformats.exceptions.CannotReadException;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * <h1> Main GUI File </h1>
 *
 * @author Stephen Gregoratto
 * @version Eternal Alpha
 * @since 2016
 */
public class NuTagger extends javax.swing.JFrame {

    public JFileChooser FC;
    public FileNameExtensionFilter SONGFILTER;
    public FileNameExtensionFilter CSVFILTER;
    public int ROWCOUNTER;
    public String OS;
    public int maxWidth; 
    public int minWidth;
    public int preWidth;

    /**
     * Creates the main form, initialises public JFileChooser, sets public
     * FileFilter for song/CSV files, initialises counter for filled rows.
     */
    public NuTagger() {
        this.FC = new JFileChooser();
        this.SONGFILTER = new FileNameExtensionFilter("Song Files"
                + "(mp3, ogg, flac, wav, wma, ape)", "mp3", "ogg", "flac", "ape", "wav", "wma");
        this.CSVFILTER = new FileNameExtensionFilter("Comma Seperated File (.csv)", "csv");
        FC.addChoosableFileFilter(SONGFILTER);
        this.ROWCOUNTER = 0;
        OS = System.getProperty("os.name").toLowerCase();
        initComponents();
        SetIcon();

        IncrementNumbers();
        /* Allows the selection of multiple files */
        FC.setMultiSelectionEnabled(true);
        /* HACK - Storing widths for # column for DB import (it messes up widths) */
        this.maxWidth = MusicTable.getColumnModel().getColumn(0).getMaxWidth();
        this.minWidth = MusicTable.getColumnModel().getColumn(0).getMinWidth();
        this.preWidth = MusicTable.getColumnModel().getColumn(0).getPreferredWidth();
        
        MusicTable.getSelectionModel().addListSelectionListener(new MyListSelectionListener());
    }

    /**
     * Cycles through the first column's rows and numbers them.
     */
    private void IncrementNumbers() {
        for (int i = 1; i <= MusicTable.getRowCount(); i++) {
            MusicTable.setValueAt(i, (i - 1), 0);
        }
    }

    public void SetIcon() {
        if (OS.contains("windows")) {
            setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
        } else {
            /* Default case for *NIX */
            setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("iconwhite.png")));
        }
    }

    public class MyListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int row = MusicTable.getSelectedRow();
            if (MusicTable.getSelectedRow() > -1         // Checks if a row is selected
                && MusicTable.getValueAt(row, 3) != null // Check if there is a song in the row
                && MusicTable.getSelectedRowCount() == 1 // Stops importing of more than one song
                && !e.getValueIsAdjusting()) {           // Loads only when the event is adjusted (false)

                String path = (String) MusicTable.getValueAt(row, 3); // Gets file path
                ImportTags(new File(path));

                /* Clears all values in the cloned row */
                for (int i = 1; i < 4; i++) {
                    MusicTable.setValueAt(null, (ROWCOUNTER), i);
                }
            }
        }
    }

    /**
     *
     * @param song
     */
    public void ImportTags(File song) {
        String[] SongInfo;
        try {
            SongInfo = TagIO.GetTagsInFile(song);
            MusicTable.setValueAt(SongInfo[1], ROWCOUNTER, 1); // Loads Album Name in table
            MusicTable.setValueAt(SongInfo[0], ROWCOUNTER, 2); // Same for its title
            MusicTable.setValueAt(song.getAbsolutePath(), ROWCOUNTER, 3); // Full file path

            /* Loads song data in the quick edit section */
            SongTextField.setText(SongInfo[0]);
            AlbumTextField.setText(SongInfo[1]);
            ArtistTextField.setText(SongInfo[2]);
            YearTextField.setText(SongInfo[3]);
            GenreTextField.setText(SongInfo[4]);
            CommentTextField.setText(SongInfo[5]);
                    

            System.out.println("Rowcounter (Import Tags):" + ROWCOUNTER);

        } catch (CannotReadException ex) {
            JOptionPane.showMessageDialog(rootPane, "A valid music file has"
                    + " not been selected", "Input Error", ERROR_MESSAGE);
            Logger.getLogger(IOPackage.TagIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TableScrollPane = new javax.swing.JScrollPane();
        MusicTable = new javax.swing.JTable();
        SongTextField = new javax.swing.JTextField();
        AlbumTextField = new javax.swing.JTextField();
        ArtistTextField = new javax.swing.JTextField();
        YearTextField = new javax.swing.JTextField();
        GenreTextField = new javax.swing.JTextField();
        CommentTextField = new javax.swing.JTextField();
        MainMenuBar = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        OpenListItem = new javax.swing.JMenuItem();
        CSVLoadMenuItem = new javax.swing.JMenuItem();
        PlayListItem = new javax.swing.JMenuItem();
        EditMenu = new javax.swing.JMenu();
        ClearMenuItems = new javax.swing.JMenuItem();
        SaveSelectedListItem = new javax.swing.JMenuItem();
        CSVSaveMenuItem = new javax.swing.JMenuItem();
        HelpMenu = new javax.swing.JMenu();
        OnlineDocsItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        AboutNutaggerItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("nuTagger");
        setFont(new java.awt.Font("Cantarell", 0, 12)); // NOI18N
        setLocationByPlatform(true);

        TableScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        MusicTable.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        MusicTable.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        MusicTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "Album", "Title", "Location"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        MusicTable.setToolTipText("This is where imported music is stored. You can select songs to edit their tags.");
        MusicTable.setDoubleBuffered(true);
        MusicTable.setDragEnabled(true);
        MusicTable.setEditingColumn(1);
        MusicTable.setEditingRow(1);
        MusicTable.setGridColor(new java.awt.Color(200, 200, 200));
        MusicTable.getTableHeader().setReorderingAllowed(false);
        TableScrollPane.setViewportView(MusicTable);
        if (MusicTable.getColumnModel().getColumnCount() > 0) {
            MusicTable.getColumnModel().getColumn(0).setMinWidth(15);
            MusicTable.getColumnModel().getColumn(0).setPreferredWidth(20);
            MusicTable.getColumnModel().getColumn(0).setMaxWidth(20);
            MusicTable.getColumnModel().getColumn(1).setMinWidth(50);
            MusicTable.getColumnModel().getColumn(1).setPreferredWidth(70);
            MusicTable.getColumnModel().getColumn(2).setMinWidth(30);
            MusicTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        }

        SongNameLabel.setFont(SongTextField.getFont());
        SongNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        SongNameLabel.setText("Title:");

        AlbumLabel.setFont(SongTextField.getFont());
        AlbumLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        AlbumLabel.setText("Album:");

        ArtistLabel.setFont(SongTextField.getFont());
        ArtistLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ArtistLabel.setText("Artist:");

        YearLable.setFont(SongTextField.getFont());
        YearLable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        YearLable.setText("Year:");

        GenreLable.setFont(SongTextField.getFont());
        GenreLable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        GenreLable.setText("Genre:");

        SongTextField.setToolTipText("This is where the song's title is located. (e.g \"The Man-Machine\" ) You can edit its value and save it using Ctrl+S");
        SongTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SongTextFieldActionPerformed(evt);
            }
        });

        AlbumTextField.setToolTipText("This is where the song's album name is located (e.g \"Animals\" ). You can edit its value and save it using Ctrl+S");
        AlbumTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlbumTextFieldActionPerformed(evt);
            }
        });

        ArtistTextField.setToolTipText("This is where the song's artist is located (e.g \"David Bowie\" ). You can edit its value and save it using Ctrl+S");
        ArtistTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArtistTextFieldActionPerformed(evt);
            }
        });

        YearTextField.setToolTipText("This is the year the song was made. You can edit its value and save it using Ctrl+S");

        GenreTextField.setToolTipText("This is the song's genre. This is the year the song was made (e.g Classical, Techno ). You can edit its value and save it using Ctrl+S");

        CommentLabel.setFont(SongTextField.getFont());
        CommentLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        CommentLabel.setText("Comment:");

        CommentTextField.setToolTipText("This is where the song's individual comment is located (e.g \"Best song of my childhood!\" ). You can edit its value and save it using Ctrl+S");
        CommentTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CommentTextFieldActionPerformed(evt);
            }
        });

        MainMenuBar.setFont(MainMenuBar.getFont().deriveFont(MainMenuBar.getFont().getSize()+3f));

        FileMenu.setText("File");
        FileMenu.setToolTipText("Options to load/play songs and load previous Dataabases.");

        OpenListItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        OpenListItem.setText("Open Files");
        OpenListItem.setToolTipText("Open music files and load them into nuTagger.");
        OpenListItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenListItemActionPerformed(evt);
            }
        });
        FileMenu.add(OpenListItem);

        CSVLoadMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        CSVLoadMenuItem.setText("Load Database");
        CSVLoadMenuItem.setToolTipText("Load a database that has been previously saved.");
        CSVLoadMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CSVLoadMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(CSVLoadMenuItem);

        PlayListItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        PlayListItem.setText("Play Song");
        PlayListItem.setToolTipText("Play a selected song usig your default music player.");
        PlayListItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayListItemActionPerformed(evt);
            }
        });
        FileMenu.add(PlayListItem);
        PlayListItem.getAccessibleContext().setAccessibleDescription("");

        MainMenuBar.add(FileMenu);

        EditMenu.setText("Edit");
        EditMenu.setToolTipText("Options to save music/database and clear the database of records.");

        ClearMenuItems.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, java.awt.event.InputEvent.ALT_MASK));
        ClearMenuItems.setText("Clear All Values");
        ClearMenuItems.setToolTipText("Cleas all songs from the database. Does NOT delete the songs themselves.");
        ClearMenuItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearMenuItemsActionPerformed(evt);
            }
        });
        EditMenu.add(ClearMenuItems);

        SaveSelectedListItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        SaveSelectedListItem.setText("Save Selected");
        SaveSelectedListItem.setToolTipText("Save the ");
        SaveSelectedListItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveSelectedListItemActionPerformed(evt);
            }
        });
        EditMenu.add(SaveSelectedListItem);

        CSVSaveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        CSVSaveMenuItem.setText("Save Music to Database");
        CSVSaveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CSVSaveMenuItemActionPerformed(evt);
            }
        });
        EditMenu.add(CSVSaveMenuItem);

        MainMenuBar.add(EditMenu);

        HelpMenu.setText("Help");
        HelpMenu.setToolTipText("Options for help in using nuTagger.");

        OnlineDocsItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        OnlineDocsItem.setText("Online Help");
        OnlineDocsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnlineDocsItemActionPerformed(evt);
            }
        });
        HelpMenu.add(OnlineDocsItem);
        HelpMenu.add(jSeparator1);

        AboutNutaggerItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        AboutNutaggerItem.setText("About nuTagger");
        AboutNutaggerItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutNutaggerItemActionPerformed(evt);
            }
        });
        HelpMenu.add(AboutNutaggerItem);

        MainMenuBar.add(HelpMenu);

        setJMenuBar(MainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SongNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(AlbumLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ArtistLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(YearLable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(YearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(GenreLable)
                                        .addGap(18, 18, 18)
                                        .addComponent(GenreTextField))
                                    .addComponent(AlbumTextField)
                                    .addComponent(SongTextField)
                                    .addComponent(ArtistTextField)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(CommentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CommentTextField)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(TableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SongTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SongNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AlbumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AlbumLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ArtistTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ArtistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CommentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CommentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(YearLable)
                        .addComponent(YearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(GenreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(GenreLable)))
                .addGap(12, 12, 12))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void OpenListItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenListItemActionPerformed
        FC.setFileFilter(SONGFILTER); // Sets filter for proper song import
        DefaultTableModel model = (DefaultTableModel) MusicTable.getModel(); // Used to add rows

        if (FC.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File[] SelectedSongs = FC.getSelectedFiles();
            for (int i = 0; i <= (SelectedSongs.length - 1); i++) {
                ImportTags(SelectedSongs[i]);
                model.addRow(new Object[]{}); // New blank row
                ROWCOUNTER++;
            }
        }
        FC.resetChoosableFileFilters(); // Resets filter for CSV export

        IncrementNumbers();
    }//GEN-LAST:event_OpenListItemActionPerformed

    private void SongTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SongTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SongTextFieldActionPerformed

    private void ArtistTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArtistTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ArtistTextFieldActionPerformed

    private void AlbumTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlbumTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlbumTextFieldActionPerformed

    private void OnlineDocsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnlineDocsItemActionPerformed
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/The-King-of-Toasters/nuTagger/wiki/nuTagger-Wiki"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(NuTagger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_OnlineDocsItemActionPerformed

    private void AboutNutaggerItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutNutaggerItemActionPerformed
        JFrame aboutFrame = new AboutJFrame();
        aboutFrame.setVisible(true);
    }//GEN-LAST:event_AboutNutaggerItemActionPerformed

    private void CSVSaveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CSVSaveMenuItemActionPerformed
        /* Sets file save to csv files only */
        FC.setFileFilter(CSVFILTER);

        if (FC.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            /* Gets file name ("a.csv") */
            String filename = FC.getSelectedFile().getName();
            /* Gets file path (e.g. "/mnt/player/Bob\ Marley") */
            String path = FC.getSelectedFile().getParentFile().getPath();

            int FilenameLength = filename.length();
            String FileExtension = null, file = null;

            /* Stores the extension at the end as a substring of the filename */
            if (FilenameLength > 4) {
                FileExtension = filename.substring(FilenameLength - 4, FilenameLength);
            }

            /* Joins path and name for CSV save location */
            if (FileExtension.equals(".csv")) {
                if (OS.contains("windows")) {
                    /* Special case for Win NT */
                    file = path + "\\" + filename;
                } else {
                    /* Default case for *NIX */
                    file = path + "//" + filename;
                }
            } else if (OS.contains("windows")) {
                file = path + "\\" + filename + ".csv";
            } else {
                file = path + "//" + filename + ".csv";
            }

            try {
                IOPackage.DatabaseIO.ToCSV(new File(file), MusicTable, ROWCOUNTER);
            } catch (IOException ex) {
                Logger.getLogger(NuTagger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FC.resetChoosableFileFilters(); // Resets Filter for Music Import
    }//GEN-LAST:event_CSVSaveMenuItemActionPerformed

    private void SaveSelectedListItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveSelectedListItemActionPerformed
        /* Checks if a song is actually selected using pathname data */
        if (MusicTable.getValueAt(MusicTable.getSelectedRow(), 3) != null
            && MusicTable.getSelectedRowCount() == 1) {
            String[] NewData = new String[6];
            /* Storing data in array as set out in GetTags...() */
            NewData[0] = SongTextField.getText();
            NewData[1] = AlbumTextField.getText();
            NewData[2] = ArtistTextField.getText();
            NewData[3] = YearTextField.getText();
            NewData[4] = GenreTextField.getText();
            NewData[5] = CommentTextField.getText();

            /* New file from the song's path in the JTable */
            File PathAtRow = new File(MusicTable.getValueAt(MusicTable.getSelectedRow(), 3).toString());
            try {
                IOPackage.TagIO.WriteNewTagsToFile(NewData, PathAtRow);
                /* Only on successfull write will the data is copied to the table */
                MusicTable.setValueAt(NewData[1], MusicTable.getSelectedRow(), 1);
                MusicTable.setValueAt(NewData[0], MusicTable.getSelectedRow(), 2);
            } catch (CannotReadException ex) {
                Logger.getLogger(NuTagger.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "You have not selected a "
                    + "music file to save", "Selection Error", ERROR_MESSAGE);
        }
    }//GEN-LAST:event_SaveSelectedListItemActionPerformed

    private void ClearMenuItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearMenuItemsActionPerformed
        /* Cycles through rows to remove their contents */
        for (int i = 0; i <= (ROWCOUNTER - 1); i++) {
            for (int j = 1; j <= MusicTable.getColumnCount() - 1; j++) {
                MusicTable.setValueAt(null, i, j);
            }
        }

        /* Removes song data in the quick edit section. */
        SongTextField.setText(null);
        AlbumTextField.setText(null);
        ArtistTextField.setText(null);
        YearTextField.setText(null);
        GenreTextField.setText(null);
        CommentTextField.setText(null);

        ROWCOUNTER = 0;
    }//GEN-LAST:event_ClearMenuItemsActionPerformed

    private void CSVLoadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CSVLoadMenuItemActionPerformed
        try {
            FC.setFileFilter(CSVFILTER);
            if (FC.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                ClearMenuItemsActionPerformed(evt);
                File oldDB = FC.getSelectedFile();
                /* Calls a new CSV Reader from OpenCSV using the file */
                CSVReader reader = new CSVReader(new FileReader(oldDB), ',', '"', '|');
                
                List myEntries = reader.readAll();
                /* Used to manipluate the table later on */ 
                DefaultTableModel model = (DefaultTableModel) MusicTable.getModel();
                model.setColumnCount(5); // HACK - DB Import need extra column to function

                int rowcount = myEntries.size();
                for (int i = 1; i < rowcount; i++) {
                    int columnnumber = 0;
                    /* The first row (i=0) is used for column titles, so it is skiped */
                        for (String thiscellvalue : (String[]) myEntries.get(i)) {
                            model.setValueAt(thiscellvalue, (i - 1), columnnumber); // Rows start form 0
                            columnnumber++;
                        }
                }

                model.setColumnCount(4);

                IncrementNumbers();
                MusicTable.getColumnModel().getColumn(0).setPreferredWidth(preWidth);
                MusicTable.getColumnModel().getColumn(0).setMinWidth(minWidth);
                MusicTable.getColumnModel().getColumn(0).setMaxWidth(maxWidth);
                MusicTable.removeRowSelectionInterval(myEntries.size() - 1, myEntries.size());

                ROWCOUNTER = myEntries.size() - 1;

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NuTagger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NuTagger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_CSVLoadMenuItemActionPerformed

    private void PlayListItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayListItemActionPerformed
        int row = MusicTable.getSelectedRow();
        if (MusicTable.getSelectedRow() > -1             // Checks if a row is selected
            && MusicTable.getValueAt(row, 3) != null     // Check if there is a song in the row
            && MusicTable.getSelectedRowCount() == 1) {  // Stops playing of more than one song

            File songToPlay = new File((String) MusicTable.getValueAt(row, 3));
            try {
                Desktop.getDesktop().open(songToPlay);
            } catch (IOException ex) {
                Logger.getLogger(NuTagger.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
                JOptionPane.showMessageDialog(rootPane, "A valid music file has"
                    + " not been selected", "Input Error", ERROR_MESSAGE);
        }
    }//GEN-LAST:event_PlayListItemActionPerformed

    private void CommentTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CommentTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CommentTextFieldActionPerformed
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Setup menu bars at the top for Mac OS X */
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.menu.about.name", "ImageRotator");
        /* Set Look and Feel based on OS Default (Windows, Aqua, GTK) or Metal fallback */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(NuTagger.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new NuTagger().setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutNutaggerItem;
    private static final javax.swing.JLabel AlbumLabel = new javax.swing.JLabel();
    private javax.swing.JTextField AlbumTextField;
    private static final javax.swing.JLabel ArtistLabel = new javax.swing.JLabel();
    private javax.swing.JTextField ArtistTextField;
    private javax.swing.JMenuItem CSVLoadMenuItem;
    private javax.swing.JMenuItem CSVSaveMenuItem;
    private javax.swing.JMenuItem ClearMenuItems;
    private static final javax.swing.JLabel CommentLabel = new javax.swing.JLabel();
    private javax.swing.JTextField CommentTextField;
    private javax.swing.JMenu EditMenu;
    public static javax.swing.JMenu FileMenu;
    private static final javax.swing.JLabel GenreLable = new javax.swing.JLabel();
    private javax.swing.JTextField GenreTextField;
    private javax.swing.JMenu HelpMenu;
    private javax.swing.JMenuBar MainMenuBar;
    private javax.swing.JTable MusicTable;
    private javax.swing.JMenuItem OnlineDocsItem;
    private javax.swing.JMenuItem OpenListItem;
    public javax.swing.JMenuItem PlayListItem;
    private javax.swing.JMenuItem SaveSelectedListItem;
    private static final javax.swing.JLabel SongNameLabel = new javax.swing.JLabel();
    private javax.swing.JTextField SongTextField;
    private javax.swing.JScrollPane TableScrollPane;
    private static final javax.swing.JLabel YearLable = new javax.swing.JLabel();
    private javax.swing.JTextField YearTextField;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
