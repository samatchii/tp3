package com.mproduits.web.controller;

import com.mproduits.configurations.ApplicationPropertiesConfiguration;
import com.mproduits.dao.ProductDao;
import com.mproduits.model.Product;
import com.mproduits.web.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductDao productDao;

    @Autowired
    ApplicationPropertiesConfiguration appProperties;

    @GetMapping("/Produits")
    public List<Product> listeDesProduits() {
        System.out.println("****** ProductController listeDesProduits() ");
        List<Product> products = productDao.findAll();

        if (products.isEmpty())
            throw new ProductNotFoundException("Aucun produit n'est disponible à la vente");

        int limit = Math.min(appProperties.getLimitDeProduits(), products.size());
        return products.subList(0, limit);
    }

    @GetMapping("/Produits/{id}")
    public Optional<Product> recupererUnProduct(@PathVariable int id) {
        Optional<Product> product = productDao.findById(id);
        if (!product.isPresent())
            throw new ProductNotFoundException("Le produit " + id + " n'existe pas");
        return product;
    }
}