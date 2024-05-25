package com.courseproject.LMS.controllers;

import com.courseproject.LMS.models.Client;
import com.courseproject.LMS.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/customers")
    public String getClients(Model model){
        List<Client> clientList = clientService.getClients();
        model.addAttribute("customers", clientList);
        return "customers";
    }
    @PostMapping("/customers/addNew")
    public String addNew(Client client){
        clientService.save(client);
        return "redirect:/customers";
    }
    @RequestMapping("/customers/findById")
    @ResponseBody
    public Optional<Client> findById(int id){
        return clientService.findById(id);
    }

    @RequestMapping(value = "/customers/update", method = RequestMethod.POST)
    public String update(@RequestParam int id, Client client){
        client.setClientId(id);
        clientService.save(client);
        return "redirect:/customers";
    }
    @RequestMapping(value = "/customers/delete", method = RequestMethod.POST)
    public String delete(@RequestParam int id) {
        clientService.deleteById(id);
        return "redirect:/customers";
    }
}

