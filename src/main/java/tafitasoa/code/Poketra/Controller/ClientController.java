package tafitasoa.code.Poketra.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tafitasoa.code.Poketra.Models.Client;
import tafitasoa.code.Poketra.Models.Taille;
import tafitasoa.code.Poketra.Service.ClientService;
import tafitasoa.code.Poketra.Service.EmployeService;
import tafitasoa.code.Poketra.Service.TailleService;

import java.sql.Timestamp;

@Controller
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    private final EmployeService employeService;
    public ClientController(ClientService clientService,EmployeService employeService){
        this.clientService = clientService;
        this.employeService = employeService;
    }
    @GetMapping("/list")
    public String getClientPage(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Client> clientPage = clientService.getAllTClients(pageable);
        model.addAttribute("clientPage", clientPage);
        return "client/list";
    }
    @PostMapping("/insert")
    public String insertNew(@RequestParam("nom") String nom,@RequestParam("genre_id") String genre_id,@RequestParam("dtn") String dtn) throws Exception{
        Client client = new Client();
        client.setNom(nom);
        client.setGenre(genre_id);
        Timestamp date_naissance = employeService.convertStringToTimestamp(dtn);
        client.setDate_naissance(date_naissance);
        this.clientService.create(client);
        return "redirect:/client/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        clientService.delete(id);
        return "redirect:/client/list";
    }
}
