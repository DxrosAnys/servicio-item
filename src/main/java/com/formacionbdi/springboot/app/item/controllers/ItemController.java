package com.formacionbdi.springboot.app.item.controllers;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    @Qualifier("serviceFeign") //  @Qualifier("serviceTemplate")
    private final ItemService itemService;

    public ItemController(@Qualifier("serviceFeign") ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/listar")
    public List<Item> listar(){
        return itemService.findALl();
    }

    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id,
                        @PathVariable Integer cantidad){
        return itemService.findById(id, cantidad);
    }
}
