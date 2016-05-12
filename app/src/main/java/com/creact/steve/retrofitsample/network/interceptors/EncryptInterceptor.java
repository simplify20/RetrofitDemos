package com.creact.steve.retrofitsample.network.interceptors;

import android.text.TextUtils;

import com.creact.steve.retrofitsample.network.util.Cookies;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016/5/8.
 */
public class EncryptInterceptor extends BaseInterceptor {
    private Map<String,Object> mExtras;
    private MediaType mMediaType;
    private Map<String, String> mFactors;


    public void setMediaType(MediaType mediaType) {
        this.mMediaType = mediaType;
    }

    @Override
    public Request manipulateRequest(Request request) {
        String url  = getUrl(request);
        String userId = Cookies.getUserId();
        String postBody = getRequestBody(request, mCharset);
        //encrypt
        mFactors = new HashMap<>();
        mFactors.put("path",url);
        mFactors.put("userid", userId);

        HashMap<String,Object> encryptRet = encrypt(postBody, mFactors);
        JSONObject finalObject = new JSONObject();
        int policyNumber = 0;
        if (encryptRet != null) {
            policyNumber = (int) encryptRet.get("number");
        }
        //0标示不加密
        if (policyNumber != 0){
            try {
                String encrypt = new String((byte[]) encryptRet.get("encrypt"), "UTF-8");
                finalObject.put("var", encrypt);
                finalObject.put("tmp", encryptRet.get("random"));
                RequestBody newRequestBody = RequestBody.create(mMediaType,finalObject.toString());
                request.newBuilder().method(request.method(),newRequestBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mExtras = encryptRet;

        return request;
    }

    /**
     * mock encrypt algorithm
     * @param postBody
     * @param factors
     * @return
     */
    private HashMap<String, Object> encrypt(String postBody, Map<String, String> factors) {
        return new HashMap<>();
    }

    @Override
    public Response manipulateResponse(Response response) {
        try {
            String respnseBody =  getResponseBody(response);
            String contentType = response.header("Content-Type");
            if("application/json".equals(contentType)){
                JSONObject jsonObject = new JSONObject(respnseBody);
                Integer policyNumber = (Integer) mExtras.get("number");
                String randomFactor = jsonObject.optString("tmp");
                String data = jsonObject.optString("var");
                String overload = jsonObject.optString("overload");

                if ("-1".equals(overload)
                        || (0 != policyNumber && TextUtils.isEmpty(data))
                        ) {
                    refreshAlgorithm(policyNumber);
                }
                if (policyNumber == 0) {
                    //说明目前加密策略是不加密
                    return response;
                }

                byte[] rawData = decrypt(policyNumber,data,mFactors,randomFactor);
                jsonObject = new JSONObject(new String(rawData,mCharset.name()));
                ResponseBody newResponseBody = ResponseBody.create(mMediaType,jsonObject.toString());
                response.newBuilder().body(newResponseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private void refreshAlgorithm(Integer policyNumber) {
        //// TODO: call refresh,2016/5/12
    }

    private byte[] decrypt(Integer policyNumber, String data, Map<String, String> mFactors, String randomFactor) {
        //// TODO: decrypt,2016/5/12
        return new byte[0];
    }


}
