package org.ahjk.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.ahjk.entity.LingYangUserInfo;
import org.ahjk.properties.LyProperties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.ahjk.constant.LyConstant.*;

public class LyUtils {

    private LyProperties lyProperties;

    public LyUtils(LyProperties lyProperties) {
        this.lyProperties = lyProperties;
    }

    /*public static void main(String[] args) throws Exception {
        getLyUserInfoByToken("1672804223000001-OPEN-22c8430d6cdf4b01bdad4f916e72ba48");
    }*/

    /**
     * 根据token获取羚羊用户信息
     *
     * @param token
     * @return
     */
    public LingYangUserInfo getLyUserInfoByToken(String token) throws Exception {
        /*LyProperties lyInsertConfig = new LyProperties();
        lyInsertConfig.setAesKey("jokdymyaaeuckald");
        lyInsertConfig.setAppKey("mmrjhywcnw");
        lyInsertConfig.setAppSecret("f8bd406ba02f38d39105eb3bc7e209aa20cb7c51");*/
        String signStr = String.format(SIGN_TEMPLATE
                , lyProperties.getAppSecret()
                , token
                , LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIME_TEMPLATE))
                , lyProperties.getAppKey()
                , lyProperties.getAppSecret()
        );
        String signMd5 = MD5(signStr);
        System.out.println("sign -->" + signMd5);
        HttpRequest post = HttpUtil.createPost(USER_INFO);
        post.header("channel", "app02", false);
        post.header("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIME_TEMPLATE)), false);
        post.header("appKey", lyProperties.getAppKey(), false);
        post.header("sign", signMd5, false);
        post.header("content-type", APPLICATION_JSON_VALUE, false);
        String encrypt;
        try {
            encrypt = aesEncrypt(String.format(ASE_TEMPLATE, token), lyProperties.getAesKey(), "utf-8");
            System.out.println("encrypt -->" + encrypt);
        } catch (Exception e) {
            throw e;
        }
        post.body(encrypt);
        HttpResponse execute = post.execute();
        String lingYangResultStr = execute.body();
        System.out.println("lingYangResultStr -->" + lingYangResultStr);
        String decrypt;
        try {
            decrypt = aesDecrypt(lingYangResultStr, lyProperties.getAesKey(), "utf-8");
            System.out.println("decrypt" + decrypt);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        JSONObject jsonObject = JSONObject.parseObject(decrypt);
        if (jsonObject.containsKey("code") && jsonObject.getInteger("code") == 0) {
            return JSONObject.parseObject(jsonObject.get("result").toString(), LingYangUserInfo.class);
        } else {
            throw new RuntimeException("数据解析异常！");
        }
    }

    /**
     * AES加密
     *
     * @param content
     * @param aesKey
     * @param charset
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content, String aesKey, String charset) throws Exception {
        if (StrUtil.isBlank(content)) {
            return content;
        }
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG); //AES/CBC/PKCS5Padding
            IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes()); //0000000000000000.getBytes()
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(aesKey.getBytes(), AES_ALG), iv); //AES
            byte[] encryptBytes = cipher.doFinal(content.getBytes(charset));
            return new String(Base64.encode(encryptBytes));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * AES解密
     *
     * @param content
     * @param key
     * @param charset
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String content, String key, String charset) throws Exception {
        if (StrUtil.isBlank(content)) {
            return content;
        }
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), AES_ALG), iv);
            byte[] cleanBytes = cipher.doFinal(Base64.decode(content.getBytes(Charset.forName("utf-8"))));
            return new String(cleanBytes, charset);
        } catch (Exception e) {
            throw e;
        }
    }

    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        byte[] btInput = s.getBytes();
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = null;
        try {
            mdInst = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        // 使用指定的字节更新摘要
        mdInst.update(btInput);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);

    }


}
