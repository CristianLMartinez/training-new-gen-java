package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.model.entity.Product;
import com.globant.trainingnewgen.model.entity.ProductCategory;
import com.globant.trainingnewgen.repository.ProductRepository;
import com.globant.trainingnewgen.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
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
public class MenuDocx implements MenuService {

    private final ProductRepository productRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(MenuPdf.class);

    @Override
    public void generateDocument(ByteArrayOutputStream outputStream) {
        LOGGER.debug("Generating DOCX menu");
        try (XWPFDocument document = new XWPFDocument()) {
            XWPFParagraph title = document.createParagraph();
            XWPFRun run = title.createRun();
            run.setText("Restaurante XYZ");
            run.setBold(true);
            run.setFontSize(20);

            List<Product> products = productRepository.findByAvailable(Boolean.TRUE);
            Map<ProductCategory, List<Product>> categorizedProducts = products.stream()
                    .collect(Collectors.groupingBy(Product::getCategory));

            for (Map.Entry<ProductCategory, List<Product>> entry : categorizedProducts.entrySet()) {
                XWPFParagraph categoryTitle = document.createParagraph();
                XWPFRun categoryRun = categoryTitle.createRun();
                categoryRun.setText(entry.getKey().name());
                categoryRun.setBold(true);
                categoryRun.setFontSize(16);

                for (Product product : entry.getValue()) {
                    XWPFParagraph productParagraph = document.createParagraph();
                    XWPFRun productRun = productParagraph.createRun();
                    productRun.setText(product.getFantasyName() + " - $" + product.getPrice());
                    productRun.setFontSize(12);
                }
            }

            document.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
