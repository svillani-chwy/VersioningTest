package com.chewy.ReleaseLockedOrders.util;

import com.chewy.ReleaseLockedOrders.domain.Cart;
import com.google.common.io.BaseEncoding;
import okhttp3.*;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;

public class OkHttpWrapper {

    private String REQUEST_ID = "X-Request-ID";

    private String CALLER = "caller";

    private String ACCOUNT = "account";

    private OkHttpClient client = new OkHttpClient();

    private Request.Builder builder;

    private String baseURL;

    private Logger logger;

    public OkHttpWrapper(Logger logger){
        this.logger = logger;
    }

    public OkHttpWrapper build(String baseUrl, Map<String, String> headers) {
        this.baseURL = baseUrl;
        builder = new Request.Builder();

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        return this;
    }

    private void execute() throws Exception {
        Request request = builder.build();

        logger.info("With headers: " + request.headers().toString());
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.code() != 200) {
            throw new Exception(response.code() + " error. " + response.message());
        }
    }

    public void patch(String path, Object object, Class<?> classType) throws Exception {
        resetHeaders();
        builder.addHeader(CALLER, ((Cart) object).getEditorAccountId());
        builder.addHeader(ACCOUNT, ((Cart) object).getAccountId());
        builder.addHeader(REQUEST_ID, "BAT-" + generateId());
        builder.url(baseURL + path);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "[{}]");
        builder.patch(body);
        logger.info("Sending cartId: " + ((Cart) object).getId() + " to the /unlock endpoint in the cartService");
        execute();
    }

    private void resetHeaders() {
        builder.removeHeader(CALLER);
        builder.removeHeader(ACCOUNT);
        builder.removeHeader(REQUEST_ID);
    }

    public String generateId() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return BaseEncoding.base32().omitPadding().encode(bb.array());
    }

}