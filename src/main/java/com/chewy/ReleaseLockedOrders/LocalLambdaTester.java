package com.chewy.ReleaseLockedOrders;

public class LocalLambdaTester {
    public static void main(String[] args) throws Exception {
        ReleaseLockedOrdersLambda r = new ReleaseLockedOrdersLambda();
        r.handleRequest(null, null, null);
    }
}
