package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@Slf4j
@RestController
public class CoffeeApiController {
    @Autowired
    CoffeeRepository coffeeRepository;
    @GetMapping("/api/coffees")
    public ArrayList<Coffee> index(){
        return coffeeRepository.findAll();
    }
    @GetMapping("/api/coffees/{id}")
    public Coffee show(@PathVariable Long id){
        return coffeeRepository.findById(id).orElse(null);
    }
    @PostMapping("/api/coffees")
    public ResponseEntity<Coffee> create(@RequestBody CoffeeDto dto){
        Coffee coffee = dto.toEntity();
        Coffee newCoffee = coffeeRepository.save(coffee);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCoffee);
    }
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeDto dto){
        Coffee reOrder = dto.toEntity();
        log.info("id = {}, coffee = {}", id, reOrder.toString());
        Coffee target = coffeeRepository.findById(id).orElse(null);

        if(target == null || id != reOrder.getId()) {
            log.info("잘못된 요청! id: {}, coffee: {}", id, reOrder.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        target.patch(reOrder);
        Coffee updated = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id){
        Coffee target = coffeeRepository.findById(id).orElse(null);

        if (target == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        coffeeRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
