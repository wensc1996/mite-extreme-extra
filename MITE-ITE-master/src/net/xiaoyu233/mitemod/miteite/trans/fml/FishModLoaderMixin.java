package net.xiaoyu233.mitemod.miteite.trans.fml;


import net.xiaoyu233.fml.FishModLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

@Mixin(FishModLoader.class)
public class FishModLoaderMixin {

    @Overwrite
    public static String versionCheck() {
//        try {
//            URL url = new URL("https://raw.githubusercontent.com/XiaoYuOvO/FishModLoader/master/VERSION.txt");
//            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
//            SSLContext sc = SSLContext.getInstance("TLSv1.2");
//            sc.init((KeyManager[])null, (TrustManager[])null, new SecureRandom());
//            connection.setSSLSocketFactory(sc.getSocketFactory());
//            connection.setRequestMethod("GET");
//            connection.setDoInput(true);
//            connection.setInstanceFollowRedirects(false);
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:74.0) Gecko/20100101 Firefox/74.0");
//            connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
//            connection.setRequestProperty("Accept-Encoding", "text");
//            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
//            connection.setRequestProperty("Cache-Control", "max-age=0");
//            connection.setRequestProperty("Host", "raw.githubusercontent.com");
//            connection.setRequestProperty("Connection", "keep-alive");
//            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//            connection.connect();
//            return (new Scanner(connection.getInputStream(), String.valueOf(StandardCharsets.UTF_8))).nextLine();
//        } catch (IOException var3) {
//            var3.printStackTrace();
//        } catch (NoSuchAlgorithmException | KeyManagementException var4) {
//            var4.printStackTrace();
//        }
//
        return null;
    }
}
