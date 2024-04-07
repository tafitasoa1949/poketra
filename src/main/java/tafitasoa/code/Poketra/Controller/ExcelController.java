package tafitasoa.code.Poketra.Controller;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tafitasoa.code.Poketra.Models.Vente;
import tafitasoa.code.Poketra.Service.ExcelExportService;
import tafitasoa.code.Poketra.Service.VenteService;

import java.io.FileOutputStream;
import java.util.List;

@Controller
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    private ExcelExportService excelExportService;
    private final VenteService venteService;

    public ExcelController(VenteService venteService) {
        this.venteService = venteService;
    }

    @GetMapping("/index")
    public void exportToExcel() throws Exception{
        List<Vente> dataList = venteService.getList();
        try {
            String filePath = "D:/huhu.xlsx";
            excelExportService.exportToExcel(dataList, filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
