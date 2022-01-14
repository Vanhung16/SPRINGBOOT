package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.models.ResponseObject;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {
   //DI = dependency injection
    @Autowired
    private ProductRepository repository;

    @GetMapping("/getAllProducts")
    //this request is: http://localhost:8080/api/v1/Products/getAllProducts
    List<Product> getAllProducts(){
        return repository.findAll();
    }
    @GetMapping("/{id}")
    //let's return an ogject with: data, message, status
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<Product> foundProduct = repository.findById(id);
        return foundProduct.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Query product succesfully", foundProduct)
            ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("false", "Cannot find product with id = "+id, "")
                );

//        if(foundProduct.isPresent()){
//            return ResponseEntity.status(HttpStatus.OK).body(
//              new ResponseObject("OK", "Query product succesfully", foundProduct)
//            );
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//              new ResponseObject("false", "Cannot find product with id = "+id, "")
//            );
//        }
    }
    //insert data
    //postman: Raw, JSON
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct){
        return ResponseEntity.status(HttpStatus.OK).body(
          new ResponseObject("OK","insert successfully", repository.save(newProduct))
        );

    }

}
