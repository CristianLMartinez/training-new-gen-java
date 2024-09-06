package com.globant.trainingnewgen.model.factory;

import com.globant.trainingnewgen.service.MenuService;
import com.globant.trainingnewgen.service.impl.DocxMenu;
import com.globant.trainingnewgen.service.impl.PdfMenu;
import com.globant.trainingnewgen.service.impl.PlainTextMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuFactory {

    private final PlainTextMenu menuPlainText;
    private final PdfMenu menuPdf;
    private final DocxMenu menuDocx;

    public MenuService getMenuService(String contentType) {
        return switch (contentType) {
            case MediaType.TEXT_PLAIN_VALUE -> menuPlainText;
            case MediaType.APPLICATION_PDF_VALUE -> menuPdf;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> menuDocx;
            case null, default -> throw new IllegalArgumentException("Unsupported content type: " + contentType);
        };
    }
}
