package org.skypro.skyshop.exception;

public class NoProductException extends IllegalArgumentException {
    public NoProductException() {
        super("Такого продукта нет");
    }
}
