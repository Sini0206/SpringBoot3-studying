package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Slf4j
@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    public ArrayList<Coffee> index() {
        return coffeeRepository.findAll();
    }

    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    public Coffee create(CoffeeDto dto) {
        Coffee coffee = dto.toEntity();
        if (coffee == null)
            return null;
        return coffeeRepository.save(coffee);
    }

    public Coffee update(Long id, CoffeeDto dto) {
        Coffee reOrder = dto.toEntity();
        log.info("id = {}, coffee = {}", id, reOrder.toString());

        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null || !id.equals(reOrder.getId())) {
            log.info("잘못된 요청! id: {}, coffee: {}", id, reOrder.toString());
            return null;
        }

        target.patch(reOrder);
        return coffeeRepository.save(target);
    }

    public Coffee delete(Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);

        if (target == null)
            return null;

        coffeeRepository.delete(target);
        return target;
    }
}
