package com.globant.trainingnewgen.model.factory;

import com.globant.trainingnewgen.service.MenuService;
import com.globant.trainingnewgen.service.impl.MenuDocx;
import com.globant.trainingnewgen.service.impl.MenuPdf;
import com.globant.trainingnewgen.service.impl.MenuPlainText;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuFactory {

    private final MenuPlainText menuPlainText;
    private final MenuPdf menuPdf;
    private final MenuDocx menuDocx;

    public MenuService getMenuService(String contentType) {
        return switch (contentType) {
            case MediaType.TEXT_PLAIN_VALUE -> menuPlainText;
            case MediaType.APPLICATION_PDF_VALUE -> menuPdf;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> menuDocx;
            case null, default -> throw new IllegalArgumentException("Unsupported content type: " + contentType);
        };
    }
}
