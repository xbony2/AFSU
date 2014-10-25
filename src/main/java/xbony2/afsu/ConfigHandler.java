package xbony2.afsu;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	public static String afsutexture;
	public static String afbtexture;
	
	private static final String[] usableValuesAFSU = {"default", "blue", "yellow", "black", "old"};
	private static final String[] usableValuesAFB = {"default"};
	
	public static void init(File file){
		Configuration config = new Configuration(file);
		
		config.load();
		{
			afsutexture = config.getString("AFSU Texture", "Textures", "default", "Changes the texture of the AFSU. Possible values: default, blue, yellow, black, old", usableValuesAFSU);
			afbtexture = config.getString("AFB Texture", "Textures", "default", "Changes the texture of the AFB. Possible values: default", usableValuesAFB);
		}
		config.save();
	}
}
