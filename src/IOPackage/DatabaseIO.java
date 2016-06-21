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
package IOPackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author stephen
 */
public class DatabaseIO {

    /**
     * Cycles through a JTable's values and places them in a Comma Separated
     * Value File (.csv) saved at the file sent by the caller.
     *
     * @param file
     * @param jtable
     * @param RowCount
     * @throws IOException
     */
    public static void ToCSV(File file, JTable jtable, int RowCount) throws IOException {
        String NewLine = "\n";
        char NewComma = ',';
        try {
            TableModel model = jtable.getModel();
            try (FileWriter sheet = new FileWriter(file)) {
                for (int i = 0; i < model.getColumnCount(); i++) {
                    sheet.write(model.getColumnName(i) + NewComma);
                }
                sheet.write(NewLine);

                for (int i = 0; i <= (RowCount - 1); i++) {
                    for (int j = 0; j <= (model.getColumnCount() - 1); j++) {
                        sheet.write(model.getValueAt(i, j).toString() + NewComma);
                    }
                    sheet.write(NewLine);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(IOPackage.DatabaseIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
