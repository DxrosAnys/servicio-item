package com.formacionbdi.springboot.app.item.clients;

import com.formacionbdi.springboot.app.item.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "servicio-productos")
public interface ProductosClienteRest {

    @GetMapping("/list")
    public List<Producto> listar();

    @GetMapping("/list/{id}")
    public Producto detalle(@PathVariable Long id);
}
