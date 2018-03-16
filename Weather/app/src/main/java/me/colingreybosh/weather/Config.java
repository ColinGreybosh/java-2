package me.colingreybosh.weather;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class Config
{
    static String getProperty(String key, Context context) throws IOException
    {
        Properties p = new Properties();
        AssetManager assets = context.getAssets();
        InputStream is = assets.open("config.properties");
        p.load(is);
        return p.getProperty(key);
    }
}
