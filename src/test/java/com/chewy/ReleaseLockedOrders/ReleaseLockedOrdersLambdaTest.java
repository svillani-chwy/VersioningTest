//package com.chewy.ReleaseLockedOrders;
//
//import com.amazonaws.services.lambda.runtime.Context;
//import com.chewy.ReleaseLockedOrders.configuration.AwsProperties;
//import com.chewy.ReleaseLockedOrders.configuration.CartProperties;
//import com.chewy.ReleaseLockedOrders.domain.Cart;
//import com.chewy.ReleaseLockedOrders.service.CartService;
//import com.chewy.ReleaseLockedOrders.util.OkHttpWrapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ReleaseLockedOrdersLambdaTest {
//
//    @Mock
//    private Context mockContext;
//
//    @Mock
//    private CartProperties mockCartProperties;
//
//    @Mock
//    private AwsProperties mockAwsProperties;
//
//    @Mock
//    private OkHttpWrapper mockOkHttpWrapper;
//
//    @Mock
//    private CartService mockCartService;
//
//    @InjectMocks
//    private ReleaseLockedOrdersLambda releaseLockedOrdersLambda;
//
//    private List<Cart> cartList;
//
//    private String apiKey;
//
//    private String apiToken;
//
//    private String cartStoreBasePath;
//
//    private String unlockCartsURL;
//
//    private String accountId;
//
//    private String editorAccountId;
//
//    private String cartId;
//
//    private Cart cart;
//
//    @BeforeEach
//    public void setUp() {
//
//        MockitoAnnotations.initMocks(this);
//
//        accountId = "111111";
//        editorAccountId = "222222";
//        cartId = "33333";
//        cart = new Cart();
//        cart.setId(cartId);
//        cart.setAccountId(accountId);
//        cart.setEditorAccountId(editorAccountId);
//        cartList = new ArrayList<>();
//        cartList.add(cart);
//        apiKey = "apiKey";
//        apiToken = "apiToken";
//        cartStoreBasePath = "http://www.cart.com";
//        unlockCartsURL = "/unlockCarts";
//    }
//
//    @Test
//    public void ReleaseLockedOrdersLambda_SuccessTest() throws Exception {
//        when(mockCartProperties.getApiKey()).thenReturn(apiKey);
//        when(mockAwsProperties.getApiToken()).thenReturn(apiToken);
//        when(mockCartProperties.getCartStoreBasePath()).thenReturn(cartStoreBasePath);
//        when(mockCartService.getLockedCarts()).thenReturn(cartList);
//        when(mockCartProperties.getUnlockCartsURL()).thenReturn(unlockCartsURL);
//        doNothing().when(mockOkHttpWrapper).patch(unlockCartsURL, cart, Void.class);
//        when(mockOkHttpWrapper.build(eq(cartStoreBasePath), any(Map.class))).thenReturn(mockOkHttpWrapper);
//
//        releaseLockedOrdersLambda.handleRequest(null, null, mockContext);
//
//        verify(mockCartProperties).getApiKey();
//        verify(mockAwsProperties).getApiToken();
//        verify(mockCartProperties).getCartStoreBasePath();
//        verify(mockCartService).getLockedCarts();
//        verify(mockCartProperties).getUnlockCartsURL();
//        verify(mockOkHttpWrapper).patch(unlockCartsURL, cart, Void.class);
//        verify(mockOkHttpWrapper).build(eq(cartStoreBasePath), any(Map.class));
//    }
//
//}
