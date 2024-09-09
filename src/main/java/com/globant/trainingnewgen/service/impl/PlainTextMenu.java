package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.model.entity.Product;
import com.globant.trainingnewgen.model.entity.ProductCategory;
import com.globant.trainingnewgen.repository.ProductRepository;
import com.globant.trainingnewgen.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlainTextMenu implements MenuService {

    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(PlainTextMenu.class);

    @Override
    public void generateDocument(ByteArrayOutputStream outputStream) {
        List<Product> products = productRepository.findByAvailable(Boolean.TRUE);

        Map<ProductCategory, List<Product>> categorizedProducts = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));

        StringBuilder menu = new StringBuilder("GRANDMA'S FOOD\n\n");

        for (Map.Entry<ProductCategory, List<Product>> entry : categorizedProducts.entrySet()) {
            menu.append(entry.getKey().name()).append("\n");
            for (Product product : entry.getValue()) {
                menu.append(product.getFantasyName()).append(" - $").append(product.getPrice()).append("\n");
            }
            menu.append("\n");
        }

        try {
            outputStream.write(menu.toString().getBytes());
        } catch (IOException e) {
           logger.error("Error writing menu to output stream", e);
        }
    }
}
