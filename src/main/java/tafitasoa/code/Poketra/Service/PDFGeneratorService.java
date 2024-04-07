package tafitasoa.code.Poketra.Service;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.lowagie.text.pdf.PdfWriter;
import tafitasoa.code.Poketra.Models.Vente;


@Service
public class PDFGeneratorService {
    public void ventePDF(HttpServletResponse response, Vente vente) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // En-tête du document avec le nom de l'entreprise
        Font fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD);
        fontHeader.setSize(22);
        Paragraph headerParagraph = new Paragraph("Pok-Shop", fontHeader); // Nom de l'entreprise
        headerParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(headerParagraph);
        document.add(new Paragraph(" "));

        // Ajout des informations du client à gauche et date, NIF, statut à droite
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new float[]{1, 1}); // Ajustement des colonnes

        Font fontInfo = FontFactory.getFont(FontFactory.TIMES);
        fontInfo.setSize(12);

        // Ajout des informations du client à gauche
        PdfPCell clientInfoCell = new PdfPCell();
        clientInfoCell.setBorder(Rectangle.NO_BORDER);
        Paragraph clientInfoParagraph = new Paragraph();
        clientInfoParagraph.add(new Phrase("Nom: " + vente.getClient().getNom() + "\n", fontInfo));
        clientInfoParagraph.add(new Phrase("Genre: " + vente.getClient().getGenre() + "\n", fontInfo));
        clientInfoParagraph.add(new Phrase("Date de naissance: " + vente.getClient().getDate_naissance(), fontInfo));
        clientInfoCell.addElement(clientInfoParagraph);
        infoTable.addCell(clientInfoCell);

        // Ajout de la date, du NIF, et du statut à droite
        PdfPCell dateNifStatutCell = new PdfPCell();
        dateNifStatutCell.setBorder(Rectangle.NO_BORDER);
        Paragraph dateNifStatutParagraph = new Paragraph();
        dateNifStatutParagraph.add(new Phrase("Date: 08/02/2024\n", fontInfo)); // Ajoutez la vraie date ici
        dateNifStatutParagraph.add(new Phrase("NIF: 23-C83YG2/093\n", fontInfo)); // Ajoutez le vrai NIF ici
        dateNifStatutParagraph.add(new Phrase("Statut: 234/C7394-F433\n", fontInfo)); // Ajoutez le vrai statut ici
        dateNifStatutParagraph.add(new Phrase("Lieu: Andoharanofotsy", fontInfo)); // Ajoutez le vrai lieu ici
        dateNifStatutParagraph.setAlignment(Element.ALIGN_RIGHT);
        dateNifStatutCell.addElement(dateNifStatutParagraph);
        infoTable.addCell(dateNifStatutCell);

        // Ajout du tableau d'informations
        document.add(infoTable);
        document.add(new Paragraph(" "));

        // Contenu du document avec tableau des ventes
        PdfPTable table = createTable(vente);  // Appel à la méthode pour créer le tableau
        table.setWidthPercentage(100);

        // Ajout du tableau des ventes au document
        document.add(table);

        // Lettre de l'entreprise et lettre du client
        document.add(new Paragraph(" "));
        PdfPTable lettreTable = new PdfPTable(2);
        lettreTable.setWidthPercentage(100);
        // Lettre de l'entreprise avec une taille de police réduite
        Font fontLettre = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontLettre.setSize(12); // Taille de police réduite à 12
        PdfPCell entrepriseCell = new PdfPCell(new Phrase("Entreprise", fontLettre));
        entrepriseCell.setBorder(Rectangle.NO_BORDER);
        lettreTable.addCell(entrepriseCell);

        // Lettre du client avec une taille de police réduite
        PdfPCell clientCell = new PdfPCell(new Phrase("Client", fontLettre));
        clientCell.setBorder(Rectangle.NO_BORDER);
        clientCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        lettreTable.addCell(clientCell);

        document.add(lettreTable);
        document.close();
    }

    private PdfPTable createTable(Vente vente) {
        PdfPTable table = new PdfPTable(4); // Nombre de colonnes dans le tableau

        // Ajout des en-têtes de colonne
        Font fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD);
        fontHeader.setSize(14);

        PdfPCell cellHeader1 = new PdfPCell(new Phrase("Produit", fontHeader));
        PdfPCell cellHeader2 = new PdfPCell(new Phrase("Quantité", fontHeader));
        PdfPCell cellHeader3 = new PdfPCell(new Phrase("Prix Total", fontHeader));
        PdfPCell cellHeader4 = new PdfPCell(new Phrase("Date", fontHeader));

        table.addCell(cellHeader1);
        table.addCell(cellHeader2);
        table.addCell(cellHeader3);
        table.addCell(cellHeader4);

        // Ajout des données au tableau
        Font fontContent = FontFactory.getFont(FontFactory.TIMES);
        fontContent.setSize(12);
        table.addCell(new PdfPCell(new Phrase(vente.getProduit().getNom())));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(vente.getQuantite()))));
        double prix = vente.getQuantite() * 70000;
        table.addCell(new PdfPCell(new Phrase(String.valueOf(prix))));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(vente.getDate()))));
        return table;
    }
}
