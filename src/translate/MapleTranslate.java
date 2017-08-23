package translate;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.*;

import static httpbuild.httpbuilder.md5;
import static httpbuild.httpbuilder.requestForHttp;

/**
 * OnlineTranslation
 * lijinning, 2017.08.23, Shanghai.
 */
public class MapleTranslate {
    private String appKey = "474b9e460e666d6f";
    private String query;
    private String salt;
    private String from;
    private String to;
    private String sign;
    Map<String, String> params = new HashMap<>();

    private String jsonString;

    public void buildJsonString() throws Exception {
        String salt = String.valueOf(System.currentTimeMillis());
        String sign = md5(appKey + query + salt+ "0z2OABXsH7bHb4lpDlBDtv7TZ4tEgXUB");

        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("sign", sign);
        params.put("salt", salt);
        params.put("appKey", appKey);

        jsonString = requestForHttp("https://openapi.youdao.com/api", params);
    }

    public String generateResult(){
        String res = "  ";
        JsonElement data = new JsonParser().parse(jsonString);
        JsonObject obj = data.getAsJsonObject();
        obj = obj.getAsJsonObject("basic");
        JsonArray expl = obj.getAsJsonArray("explains");
        for(int i = 0; i < expl.size(); i ++){
            res += expl.get(i).getAsString();
            if(i != expl.size()-1){
                res += "\n                 ";
            }

        }
        res += "";
        return res;
    }

    public String process(String _from, String _to, String _query) throws Exception {
        from = _from;
        to = _to;
        query = _query;
        buildJsonString();
        return generateResult();
    }

}
