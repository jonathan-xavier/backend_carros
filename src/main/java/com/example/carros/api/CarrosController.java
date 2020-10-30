package com.example.carros.api;

import com.example.carros.models.Carro;
import com.example.carros.models.CarroService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
    @Autowired
    private CarroService service;

    //busca todos os carros
    @GetMapping
    public Iterable<Carro> get()
    {
        return service.getCarros();

    }
    //buscar o carro pelo id
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id)
    {
        Optional<Carro> carro = service.getCarroById(id);
        return  carro
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        /*if(carro.isPresent()){
                return ResponseEntity.ok(carro.get());
        }else{
            return ResponseEntity.notFound().build();
        }*/
    }
    //busca os carros pelo tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getCarroByTipo(@PathVariable("tipo") String tipo){
            List<Carro> carros = service.getCarrosByTipo(tipo);
            return carros.isEmpty() ?
                    ResponseEntity.noContent().build() :
                    ResponseEntity.ok(carros);
    }

    //salvar um carro
    @PostMapping
    public String post(@RequestBody Carro carro){
       Carro c =  service.insert(carro);
       return "Carro salvo com sucesso: " + c.getId();
    }
    //editar um carro
    @PutMapping("/{id}")
    public String put(@PathVariable("id") Long id, @RequestBody Carro carro){
        Carro c = service.update(carro, id);
        return "Carro atualizado com sucesso! " + carro.getId();

    }

    //deletar carro
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        service.delete(id);
        return "Carro deletado com sucesso";
    }

}
