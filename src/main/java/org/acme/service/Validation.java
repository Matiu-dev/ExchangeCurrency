package org.acme.service;

import jakarta.ws.rs.InternalServerErrorException;
import org.acme.model.InputData;

public class Validation {
    public void inputDataValidation(InputData inputData) {

        if (inputData.getAmount() == null || inputData.getAmount().isEmpty()) {
            throw new InternalServerErrorException("Amount jest nullem.");
        }

        if (inputData.getMyCurrency() == null || inputData.getMyCurrency().isEmpty()) {
            throw new InternalServerErrorException("MyCurrency jest nullem.");
        }

        if (inputData.getTargetCurrency() == null || inputData.getTargetCurrency().isEmpty()) {
            throw new InternalServerErrorException("TargetCurrency jest nullem.");
        }

        //sprawdzenie poprwawności typow danych

        try {
            double v = Double.parseDouble(inputData.getAmount());
        } catch (NumberFormatException e) {
            throw new InternalServerErrorException("Amount musi być intem albo doublem.");
        }
    }
}
