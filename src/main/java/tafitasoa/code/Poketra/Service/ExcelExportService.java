package tafitasoa.code.Poketra.Service;

import jxl.write.WriteException;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Models.Vente;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
@Service
public class ExcelExportService {

    public void exportToExcel(List<Vente> dataList, String filePath) throws Exception{
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(filePath));
            WritableSheet sheet = workbook.createSheet("Data Sheet", 0);

            // En-tête de colonne
            String[] columns = {"Column1", "Column2", "Column3"};
            for (int i = 0; i < columns.length; i++) {
                Label label = new Label(i, 0, columns[i]);
                sheet.addCell(label);
            }

            // Données
            int rowNum = 1;
            for (Vente data : dataList) {
                sheet.addCell(new Label(0, rowNum, data.getProduit().getNom()));
                sheet.addCell(new jxl.write.Number(1, rowNum, data.getQuantite())); // Utilisation de Number pour les valeurs numériques
                sheet.addCell(new Label(2, rowNum, data.getDate().toString())); // Assurez-vous que data.getDate() retourne une chaîne

                rowNum++;
            }

            // Écrivez dans le fichier Excel
            workbook.write();
            workbook.close();

        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }
    }
}
