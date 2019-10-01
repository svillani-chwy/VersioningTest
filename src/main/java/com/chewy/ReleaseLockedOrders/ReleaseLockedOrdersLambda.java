package com.chewy.ReleaseLockedOrders;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.chewy.ReleaseLockedOrders.configuration.AwsProperties;
import com.chewy.ReleaseLockedOrders.configuration.CartProperties;
import com.chewy.ReleaseLockedOrders.domain.Cart;
import com.chewy.ReleaseLockedOrders.service.CartService;
import com.chewy.ReleaseLockedOrders.util.OkHttpWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReleaseLockedOrdersLambda implements RequestStreamHandler {

    static final Logger logger = LogManager.getLogger(ReleaseLockedOrdersLambda.class);

    private OkHttpWrapper okHttpWrapper = new OkHttpWrapper(logger);

    private CartService cartService;

    private CartProperties cartProperties;

    private AwsProperties awsProperties;

    public ReleaseLockedOrdersLambda() {
        try {
            awsProperties = new AwsProperties();
            cartProperties = new CartProperties();
            cartService = new CartService(logger);
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public void handleRequest(final InputStream inputStream, final OutputStream outputStream, final Context context) throws IOException {
        logger.info("Starting releaseLockedOrders.");
        try {
            initializeOkHttpWrapper();
            List<Cart> lockedCarts = getLockedCarts();
            unlockCarts(lockedCarts);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("Finished releaseLockedOrders.");
    }



    private OkHttpWrapper initializeOkHttpWrapper() {
        Map<String, String> headers = new HashMap<>();
        headers.put(cartProperties.getApiKey(), awsProperties.getApiToken());
        return okHttpWrapper.build(cartProperties.getCartStoreBasePath(), headers);
    }

    private List<Cart> getLockedCarts() {
        try {
            return cartService.getLockedCarts();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void unlockCarts(List<Cart> lockedCarts) {
        List<String> unlockedCarts = new ArrayList<>();
        List<String> erroredCarts = new ArrayList<>();

        for (Cart lockedCart : lockedCarts) {
            try {
                okHttpWrapper.patch(cartProperties.getUnlockCartsURL(), lockedCart, Void.class);
                unlockedCarts.add(lockedCart.getId());
                logger.info("Successfully unlocked cartId: " + lockedCart.getId());
            } catch (Exception e) {
                erroredCarts.add(lockedCart.getId());
                logger.error("Error unlocking cartId: " + lockedCart.getId() + ". With error:  " + e.getMessage());
            }
        }
        logger.info("Successfully unlocked " + unlockedCarts.size());
        logger.info("Errored carts " + erroredCarts.size());
    }

}