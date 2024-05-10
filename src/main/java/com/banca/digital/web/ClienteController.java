package com.banca.digital.web;

import com.banca.digital.dtos.ClienteDTO;
import com.banca.digital.entities.Cliente;
import com.banca.digital.entities.CuentaBancaria;
import com.banca.digital.exceptions.ClienteNotFoundException;
import com.banca.digital.repositories.ClienteRepository;
import com.banca.digital.services.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;


    @GetMapping("/clientes")
    public List<ClienteDTO> listarClientes(){
        return cuentaBancariaService.listClientes();
    }

    @GetMapping("/clientes/{id}")
    public ClienteDTO listarDatosDelCliente(@PathVariable(name = "id") Long clienteId) throws ClienteNotFoundException {
        return cuentaBancariaService.getCliente(clienteId);
    }

    @PostMapping("/clientes")
    public ClienteDTO guardarCliente(@RequestBody ClienteDTO clienteDTO){
        return cuentaBancariaService.saveClienteDTO(clienteDTO);
    }

    @PutMapping("/clientes/{id}")
    public ClienteDTO actualizarCliente(@PathVariable(name = "id") Long clienteId, @RequestBody ClienteDTO clienteDTO) throws ClienteNotFoundException {
        clienteDTO.setId(clienteId);
        return cuentaBancariaService.updateCliente(clienteDTO);
    }

    @DeleteMapping("/clientes/{id}")
    public void eliminarCliente(@PathVariable Long id) throws ClienteNotFoundException {
        cuentaBancariaService.deleteCliente(id);
    }

    @GetMapping("/clientes/search")
    public List<ClienteDTO> listarClientesByNombre(@RequestParam(name = "keyword", defaultValue = "") String keyword){
        return cuentaBancariaService.searchClientes("%" + keyword + "%");
    }

}
