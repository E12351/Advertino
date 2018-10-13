/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.schedule;

import dlg.lk.bms.util.CommanUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dinuka_08966
 */
@Component
public class TokenScheduler {

    @Value("${encode.key}")
    private String encodedKey;

    @Value("${refresh.token}")
    private String refreshKey;

    @Value("${dev.username}")
    private String userName;

    @Value("${dev.password}")
    private String password;

    @Value("${x.key.url}")
    private String xKeyUrl;

    @Value("${refresh.token.url}")
    private String refreshTokenUrl;

    public static String ACCESS_KEY;
    public static String X_KEY;
    public static String USER_ID;

    @Autowired
    private CommanUtil commanUtil;

//    @Scheduled(fixedDelay = 60 * 60 * 47)
//    public void accesstokenUpdater() {
//        try {
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.set("Content-Type", "application/json");
//            httpHeaders.set("IotMife-Token", encodedKey);
//            httpHeaders.set("IotMife-RefreshToken", refreshKey);
//
//            HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
//
//            RestTemplate restTemplate = new RestTemplate();
//            String response = restTemplate.postForObject(refreshTokenUrl, httpEntity, String.class);
//
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode root = mapper.readTree(response);
//            JsonNode accessToken = root.path("access_token");
//            JsonNode refreshToken = root.path("refresh_token");
//            refreshKey = refreshToken.toString();
//            ACCESS_KEY = accessToken.toString();
//            xKeyUserIdUpdater();
//        } catch (IOException ex) {
//            Logger.getLogger(TokenScheduler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    @Value("${accessToken}")
    private String accesstoken;
    @Value("${add.scence.url}")
    private String addSceneUrl;

    //@Scheduled(fixedDelay = 60 * 60 * 47)
    public void accesstoken() {
        ACCESS_KEY = accesstoken;
        xKeyUserIdUpdater();
    }

   

    public void xKeyUserIdUpdater() {
        try {
            URL url = new URL(xKeyUrl);
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("user_name", userName);
            params.put("password", password);

            StringBuilder postData = new StringBuilder();
            postData.append("{");

            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 1) {
                    postData.append(',');
                }
                postData.append("\"" + param.getKey() + "\"");
                postData.append(':');
                postData.append("\"" + param.getValue() + "\"");
            }
            postData.append("}");
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("IotMife-AccessToken", ACCESS_KEY);
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            String stringFromInputStream = commanUtil.getStringFromInputStream(conn.getInputStream());
            X_KEY = commanUtil.findNodeJsonStr(stringFromInputStream, "token");
            String data = commanUtil.findNodeJsonStr(stringFromInputStream, "data");
            USER_ID = commanUtil.findNodeJsonStr(data, "user_id");

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TokenScheduler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException | ProtocolException ex) {
            Logger.getLogger(TokenScheduler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TokenScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
