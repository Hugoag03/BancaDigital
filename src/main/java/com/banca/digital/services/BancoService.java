package com.banca.digital.services;

import com.banca.digital.entities.CuentaActual;
import com.banca.digital.entities.CuentaAhorro;
import com.banca.digital.entities.CuentaBancaria;
import com.banca.digital.repositories.CuentaBancariaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BancoService {

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    public void consultar() {
        CuentaBancaria cuentaBancariaBBDD = cuentaBancariaRepository.findById("232d8e32-9a43-4de6-838f-d58d31938821").orElse(null);

        if (cuentaBancariaBBDD != null) {
            System.out.println("*******************************");
            System.out.println("ID: " + cuentaBancariaBBDD.getId());
            System.out.println("Balance de la cuenta: " + cuentaBancariaBBDD.getBalance());
            System.out.println("Estado: " + cuentaBancariaBBDD.getEstadoCuenta());
            System.out.println("Fecha de creación: " + cuentaBancariaBBDD.getFechaCreacion());
            System.out.println("Cliente: " + cuentaBancariaBBDD.getCliente().getNombre());
            System.out.println("Nombre de la clase: " + cuentaBancariaBBDD.getClass().getSimpleName());

            if (cuentaBancariaBBDD instanceof CuentaActual) {
                System.out.println("Sobregiro: " + ((CuentaActual) cuentaBancariaBBDD).getSobregiro());
            } else if (cuentaBancariaBBDD instanceof CuentaAhorro) {
                System.out.println("Tasa de interés: " + ((CuentaAhorro) cuentaBancariaBBDD).getTasaDeInteres());
            }

            cuentaBancariaBBDD.getOperacionesCuenta().forEach(operacionCuenta -> {
                System.out.println("-------------------------------------------");
                System.out.println("Tipo de operación: " + operacionCuenta.getTipoOperacion());
                System.out.println("Fecha de operación: " + operacionCuenta.getFechaOperacion());
                System.out.println("Monto: " + operacionCuenta.getMonto());
            });
        }
    }
}
