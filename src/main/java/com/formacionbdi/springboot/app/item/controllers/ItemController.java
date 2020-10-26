package com.formacionbdi.springboot.app.item.controllers;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;
import com.formacionbdi.springboot.app.item.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RefreshScope
@RestController
public class ItemController {

    private final Environment env;

    @Qualifier("serviceFeign") //  @Qualifier("serviceTemplate")
    private final ItemService itemService;

    @Value("${configuration.text}")
   private String texto;

    public ItemController(@Qualifier("serviceFeign") ItemService itemService, Environment env) {
        this.itemService = itemService;
        this.env = env;
    }

    @GetMapping("/listar")
    public List<Item> listar(){
        return itemService.findALl();
    }

    @HystrixCommand(fallbackMethod = "methodAlternative")
    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id,
                        @PathVariable Integer cantidad){
        return itemService.findById(id, cantidad);
    }

    public Item methodAlternative(Long id, Integer cantidad){
        Item item = new Item();
        Producto producto = new Producto();

        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Camara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);
        return  item;
    }

    @GetMapping("/get-config")
    public ResponseEntity<?> getConfig(@Value("${server.port}") String puerto) {
        log.info(texto);
        Map<String, String> json = new HashMap<>();
        json.put("texto", texto);
        json.put("puerto" , puerto);

        if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev"))
        json.put("autor.nombre", env.getProperty("configuration.autor.nombre"));
        json.put("autor.email", env.getProperty("configuration.autor.email"));

        return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
    }
}
