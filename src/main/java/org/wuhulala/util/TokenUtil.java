package org.wuhulala.util;

import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.wuhulala.util.ThreeDes.*;


/**
 * Token 生成-解析 器
 *
 * @author Wuhulala
 * @version 1.0
 * @updateTime 2017/1/29
 */
public class TokenUtil {

    /**
     * 生成Token
     *
     * @return Token字符串
     */
     public static String generateToken(Long data, String remoteAddr,String requestURI ,Long expireTime){
         Token token = new Token(data,expireTime,remoteAddr,requestURI);
         System.out.println(token.generateBytes());
         return ThreeDes.bytesToHexString(encryptMode(keyBytes, token.generateBytes().getBytes()));
     }

    /**
     * 解析Token
     *
     * @return 存入token的数据
     */
     public static Token parseToken(String token){
         String result = new String(decryptMode(keyBytes, hexStringToBytes(token)));
         String results[] = result.split("&&");
         if(results.length == 4) {
             return new Token(Long.valueOf(results[0]),Long.valueOf(results[1]),results[2],results[3]);
         }else{
             return null;
         }
     }

    public static void main(String[] args){

        String token = TokenUtil.generateToken(112312323L,"127.0.0.1","/asd/asd", DateUtils.addDays(new Date(),10).getTime());
        System.out.println(token);
        //7B49774273DE686F5DAA2D8199A649FB320778B0A6C2794C1787E39735DD57DAAE0BD2A1F423EC17
        //7B49774273DE686FB3414EA69454F86770F43B2598396E501787E39735DD57DAAE0BD2A1F423EC17
        //0F107D3F19DF0708163054153A44EE2A08B0F93C71A1BD3ED4A8B6149CCB35E66E6E082B00296C9C87C15B306CF0C9E2
        Token token1 = TokenUtil.parseToken(token);
        System.out.println(token1.getUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(token1.getExpireTime()));
        System.out.println(token1);
        String data = "123&&1486816932995&&127.0.0.1&&/asd/asd";
        System.out.println( ThreeDes.bytesToHexString(encryptMode(keyBytes, data.getBytes())));
    }
}
