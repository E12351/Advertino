/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dinuka_08966
 */
@Component
public class CommanUtil {

    public String getServerTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public String getServerDateDataBase() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public String findNodeJsonStr(String parentnode, String findNode) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(parentnode);
        JsonNode node = root.path(findNode);
        return node.toString();
    }

    public String getStringFromInputStream(InputStream inputStream) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

}
