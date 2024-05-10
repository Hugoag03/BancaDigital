package com.banca.digital;

import com.banca.digital.dtos.ClienteDTO;
import com.banca.digital.dtos.CuentaActualDTO;
import com.banca.digital.dtos.CuentaAhorroDTO;
import com.banca.digital.dtos.CuentaBancariaDTO;
import com.banca.digital.entities.*;
import com.banca.digital.enums.EstadoCuenta;
import com.banca.digital.enums.TipoOperacion;
import com.banca.digital.exceptions.BalanceInsuficienteException;
import com.banca.digital.exceptions.ClienteNotFoundException;
import com.banca.digital.exceptions.CuentaBancariaNotFoundException;
import com.banca.digital.repositories.ClienteRepository;
import com.banca.digital.repositories.CuentaBancariaRepository;
import com.banca.digital.repositories.OperacionCuentaRepository;
import com.banca.digital.services.BancoService;
import com.banca.digital.services.CuentaBancariaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiBancaDigitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiBancaDigitalApplication.class, args);
    }

    //@Bean
    CommandLineRunner commandLineRunner(BancoService bancoService){
        return args -> {
            bancoService.consultar();
        };
    }

    //Datos de prueba
    //@Bean
    CommandLineRunner start(CuentaBancariaService cuentaBancariaService) {
        return args -> {
            Stream.of("Christian", "Julen", "Biaggio", "Lanudo").forEach(nombre -> {
                ClienteDTO cliente = new ClienteDTO();
                cliente.setNombre(nombre);
                cliente.setEmail(nombre + "@gmail.com");
                cuentaBancariaService.saveClienteDTO(cliente);

            });
                cuentaBancariaService.listClientes().forEach(cliente -> {
                    try{
                        cuentaBancariaService.saveCuentaBancariaActual(Math.random() * 90000, 9000, cliente.getId());
                        cuentaBancariaService.saveCuentaBancariaAhorro(120000, 5.5, cliente.getId());

                        List<CuentaBancariaDTO> cuentasBancarias = cuentaBancariaService.listarCuentasBancarias();

                        for(CuentaBancariaDTO cuentaBancaria : cuentasBancarias){
                            for (int i = 0; i < 10; i++){
                                String cuentaId;

                                if(cuentaBancaria instanceof CuentaAhorroDTO){
                                    cuentaId = ((CuentaAhorroDTO) cuentaBancaria).getId();
                                }else{
                                    cuentaId = ((CuentaActualDTO) cuentaBancaria).getId();
                                }
                                cuentaBancariaService.credit(cuentaId, 10000+Math.random()*120000, "Crédito");
                                cuentaBancariaService.debit(cuentaId, 10000+Math.random()*9000, "Débito");
                            }
                        }
                    }catch (ClienteNotFoundException | CuentaBancariaNotFoundException | BalanceInsuficienteException e){
                        e.printStackTrace();
                    }

            });

            /*
            //Le asignamos cuentas bancarias
            clienteRepository.findAll().forEach(cliente -> {
                CuentaActual cuentaActual = new CuentaActual();
                cuentaActual.setId(UUID.randomUUID().toString());
                cuentaActual.setBalance(Math.random() * 90000);
                cuentaActual.setFechaCreacion(new Date());
                cuentaActual.setEstadoCuenta(EstadoCuenta.CREADA);
                cuentaActual.setCliente(cliente);
                cuentaActual.setSobregiro(9000);
                cuentaBancariaRepository.save(cuentaActual);

                CuentaAhorro cuentaAhorro = new CuentaAhorro();
                cuentaAhorro.setId(UUID.randomUUID().toString());
                cuentaAhorro.setBalance(Math.random() * 90000);
                cuentaAhorro.setFechaCreacion(new Date());
                cuentaAhorro.setEstadoCuenta(EstadoCuenta.CREADA);
                cuentaAhorro.setCliente(cliente);
                cuentaAhorro.setTasaDeInteres(5.5);
                cuentaBancariaRepository.save(cuentaAhorro);
            });

            //Agregamos las operaciones
            cuentaBancariaRepository.findAll().forEach(cuentaBancaria -> {
                for (int i = 0; i < 10; i++) {
                    OperacionCuenta operacionCuenta = new OperacionCuenta();
                    operacionCuenta.setFechaOperacion(new Date());
                    operacionCuenta.setMonto(Math.random() * 12000);
                    operacionCuenta.setTipoOperacion(Math.random() > 0.5 ? TipoOperacion.DEBITO : TipoOperacion.CREDITO);
                    operacionCuenta.setCuentaBancaria(cuentaBancaria);
                    operacionCuentaRepository.save(operacionCuenta);
                }
            });
            */
        };
    }
}
