package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.model.entity.Product;
import com.globant.trainingnewgen.model.entity.ProductCategory;
import com.globant.trainingnewgen.repository.ProductRepository;
import com.globant.trainingnewgen.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
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
public class MenuPdf implements MenuService {

    private final ProductRepository productRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(MenuPdf.class);

    @Override
    public void generateDocument(ByteArrayOutputStream outputStream) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 20);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Restaurante XYZ");
                contentStream.endText();

                List<Product> products = productRepository.findByAvailable(Boolean.TRUE);
                Map<ProductCategory, List<Product>> categorizedProducts = products.stream()
                        .collect(Collectors.groupingBy(Product::getCategory));

                int yPosition = 650;

                for (Map.Entry<ProductCategory, List<Product>> entry : categorizedProducts.entrySet()) {
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 16);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText(entry.getKey().name());
                    contentStream.endText();
                    yPosition -= 20;

                    for (Product product : entry.getValue()) {
                        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                        contentStream.beginText();
                        contentStream.newLineAtOffset(120, yPosition);
                        contentStream.showText(product.getFantasyName() + " - $" + product.getPrice());
                        contentStream.endText();
                        yPosition -= 15;
                    }
                    yPosition -= 20;
                }
            }

            document.save(outputStream);
        } catch (IOException e) {
            LOGGER.error("Error writing menu to output stream", e);
        }
    }
}
