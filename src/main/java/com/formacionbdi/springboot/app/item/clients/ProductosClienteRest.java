package com.formacionbdi.springboot.app.item.clients;

import com.formacionbdi.springboot.app.item.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "servicios-productos")
public interface ProductosClienteRest {

    @GetMapping("/list")
    List<Producto> listar();

    @GetMapping("/list/{id}")
    Producto detalle(@PathVariable Long id);
}
