package tafitasoa.code.Poketra.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tafitasoa.code.Poketra.Models.Client;
import tafitasoa.code.Poketra.Models.Vente;
import tafitasoa.code.Poketra.Service.ClientService;
import tafitasoa.code.Poketra.Service.PDFGeneratorService;
import tafitasoa.code.Poketra.Service.VenteService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequestMapping("/facturation")
public class FacturationController {
    @Autowired
    PDFGeneratorService pdfGeneratorService;
    private final VenteService venteService;
    private final ClientService clientService;

    public FacturationController(VenteService venteService, ClientService clientService) {
        this.venteService = venteService;
        this.clientService = clientService;
    }
    @GetMapping("vente/{id}")
    public void facturerVente(@PathVariable String id,HttpServletResponse response)throws Exception{
        Vente vente = venteService.getById(id);
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Vente_Client_"+vente.getClient().getNom()+"_Ref"+vente.getId()+".pdf";
        response.setHeader(headerKey, headerValue);

        pdfGeneratorService.ventePDF(response,vente);
    }
}
