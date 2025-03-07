package org.skypro.skyshop.model.service;

import org.skypro.skyshop.exception.NoProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;


@Service
@SessionScope
public class BasketService {
    private final Map<UUID, Integer> basket = new HashMap<>();
    private final StorageService storageService;


    public BasketService( StorageService storageService) {
        this.storageService = storageService;
    }

    public void addProduct(UUID id) {
        Optional<Product> product = storageService.getProductById(id);
        if (product.isEmpty()) throw new NoProductException();
        basket.merge(id, 1, Integer::sum);
    }

    public UserBasket getUserBasket() {
        ArrayList<BasketItem> basketItems = new ArrayList<>();

        for (Map.Entry<UUID, Integer> entry : basket.entrySet()) {
            UUID idProd = entry.getKey();
            int count = entry.getValue();

            Product product = storageService.getProductById(idProd)
                    .orElseThrow(NoProductException::new);

            basketItems.add(new BasketItem(product, count));
        }

        return new UserBasket(basketItems);
    }


}
