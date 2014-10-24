package xbony2.afsu;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	public static String afsutexture;
	
	private static final String[] usableValues = {"default", "blue", "yellow", "black", "old"};
	
	public static void init(File file){
		Configuration config = new Configuration(file);
		
		config.load();
		{
			afsutexture = config.getString("AFSU Texture", "Textures", "default", "Changes the texture of the AFSU. Possible values: default, blue, yellow, black, old", usableValues);
		}
		config.save();
	}
}
