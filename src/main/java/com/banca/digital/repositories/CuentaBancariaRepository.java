package com.banca.digital.repositories;

import com.banca.digital.dtos.OperacionCuentaDTO;
import com.banca.digital.entities.CuentaBancaria;
import com.banca.digital.entities.OperacionCuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, String> {


}
