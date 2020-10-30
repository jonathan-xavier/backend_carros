package com.example.carros.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;
    //busca todos os carros
    public Iterable<Carro> getCarros(){
        return rep.findAll();
    }

    //busca o carro pelo id
    public Optional<Carro> getCarroById(Long id) {
        return rep.findById(id);
    }

    //busca o carro pelo tipo
    public List<Carro> getCarrosByTipo(String tipo) {
        return rep.findByTipo(tipo);
    }

        //cadastrar um novo carro
    public Carro insert(Carro carro) {
        return rep.save(carro);
    }

    //editar um carro
    public Carro update(Carro carro, Long id){
        Assert.notNull(id,"Não foi possível atualizar o registro");

        //busca o carro no banco de dados
        Optional<Carro> optional = getCarroById(id);
        if(optional.isPresent()){
            Carro db = optional.get();
            //copiar as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id: " + db.getId());

            //atualiza o carro
            rep.save(db);

            return db;

        }else{
             throw  new RuntimeException("Não foi possivel atualizar o registro");
        }
    }

        //funcao de deletar o carro
    public void delete(Long id) {

        Optional<Carro> carro = getCarroById(id);
        if(carro.isPresent()){
               rep.deleteById(id);
        }
    }

    //so um teste pra ver se funciona direito.
    public List<Carro> getCarrosFake(){
        List<Carro> carros = new ArrayList<>();

        carros.add(new Carro(1l,"Fusca"));
        carros.add(new Carro(2l,"Brasilia"));
        carros.add(new Carro(3l,"Chevette"));
        return carros;
    }

}
