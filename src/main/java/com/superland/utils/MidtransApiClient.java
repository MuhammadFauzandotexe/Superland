package com.superland.utils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MidtransApiClient {

    public static String getStatus(String orderId) throws Exception {
        String responseBody = getResponseBody(orderId);
        if (responseBody.startsWith("{\"status_code\":\"404\"")){
            return "Not-Found";
        }
        for (String data:
                responseBody.replace("{","")
                        .replace("}","")
                        .replace("\"","")
                        .split(",")
        )
        {
            if(data.startsWith("transaction_status:expire")){
                return "Expire";
            }
            else if(data.startsWith("transaction_status:settlement")){
                return "SETTLEMENT";
            }
            else if(data.startsWith("transaction_status:pending")){
                return "Pending";
            }
        }
        return "";
    }
    public static String getResponseBody(String orderId) throws Exception{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(String.format("https://api.sandbox.midtrans.com/v2/%s/status",orderId))
                .get()
                .addHeader("accept", "application/json")
                .addHeader("authorization", "Basic U0ItTWlkLXNlcnZlci1aVm5IZmV0eTZxM1FTRkZaWkoxYnhKcVM6")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
