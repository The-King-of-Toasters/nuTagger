/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOPackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author stephen
 */
public class DatabaseIO {

    /**
     * Cycles through a JTable's values and places them in a Tab Separated
     * Value File saved at the file sent by the caller.
     *
     * @param file
     * @param table
     * @param RowCount
     * @throws IOException
     */
    public static void ToTSV(File file, JTable table, int RowCount) throws IOException {
        String newLine = "\n";
        String newTab = "\t";
        try {
            TableModel model = table.getModel();
            try (FileWriter sheet = new FileWriter(file)) {
                for (int i = 0; i < model.getColumnCount(); i++) {
                    sheet.write(model.getColumnName(i) + newTab);
                }
                sheet.write(newLine);

                for (int i = 0; i <= (RowCount - 1); i++) {
                    for (int j = 0; j <= (model.getColumnCount() - 1); j++) {
                        sheet.write(model.getValueAt(i, j).toString() + newTab);
                    }
                    sheet.write(newLine);
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
