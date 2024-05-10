package com.banca.digital.dtos;

import lombok.Data;

import java.util.List;

@Data
public class HistorialCuentaDTO {

    private String cuentaId;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<OperacionCuentaDTO> operacionesCuentaDTOS;
}
