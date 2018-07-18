package com.lulan.shincolle.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.lulan.shincolle.utility.LogHelper;

import net.minecraftforge.common.config.Configuration.UnicodeInputStreamReader;

/**
 * custom config for sounds, loot table ...etc.
 */
public abstract class BasicShipConfig
{

	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final String NEW_LINE;
	
    static
    {
        NEW_LINE = System.getProperty("line.separator");
    }
	
	protected String defaultEncoding = DEFAULT_ENCODING;
	protected File file;
	protected boolean changed = false;

	
	public BasicShipConfig() {}
	
	public BasicShipConfig(File file) throws Exception
	{
		this.file = file;
	}
	
	//load config file
	public void runConfig() throws Exception
	{
		try
		{
			load();
		}
		catch (Throwable e)
		{
			LogHelper.info("ERROR: load config file: " + this.file + " fail: "+ e);
			e.printStackTrace();
			
			//for exception, throw out
			if (e instanceof Exception) throw (Exception) e;
		}
	}
	
	protected void load() throws Throwable
	{
        BufferedReader buffer = null;
        UnicodeInputStreamReader input = null;
        
        try
        {
    		//check root dir
            if (this.file.getParentFile() != null)
            {
            	file.getParentFile().mkdirs();
            }

            //create new file
            if (!this.file.exists())
            {
                if (this.file.createNewFile())
                {
                	createDefault();
                }
                else
                {
                	throw new IOException("Create new config file fail");
                }
            }
            
            //read and parse file
            if (this.file.canRead())
            {
            	//init file reader
                input = new UnicodeInputStreamReader(new FileInputStream(file), defaultEncoding);
                defaultEncoding = input.getEncoding();
                buffer = new BufferedReader(input);
                
                String line;
                ArrayList<String> tempList = new ArrayList<String>();
                int lineNum = 0;
                
                //start read
                while (true)
                {
                	//read line
                    lineNum++;
                    line = buffer.readLine();
                    
                    //end of file, break
                    if (line == null)
                    {
                    	break;
                    }
                    else
                    {
                    	tempList.add(line);
                    }
                }//end read line
                
                //parse lines
                LogHelper.debug("DEBUG: load custom config lines: "+this.file+" "+tempList.size());
                parse(tempList);
                
            }//end can read
        }
        catch (Throwable e)
        {
        	throw e;
        }
        //close reader
        finally
        {
            if (buffer != null)
            {
                try
                {
                    buffer.close();
                }
                catch (IOException e) {}
            }
            if (input != null)
            {
                try
                {
                    input.close();
                }
                catch (IOException e) {}
            }
        }
	}
	
	/** create default file */
	protected void createDefault()
	{
		try
        {
            if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs();
            }

            if (!file.exists() && !file.createNewFile())
            {
                throw new IOException("Create default config file fail.");
            }

            if (file.canWrite())
            {
                FileOutputStream fos = new FileOutputStream(file);
                BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(fos, defaultEncoding));
                ArrayList<String> strDefault = getDefaultContent();
                
                if (strDefault != null && strDefault.size() > 0)
                {
                    for (String s : strDefault) buffer.write(s);
                }
                
                buffer.close();
                fos.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	}
	
	/** parse strings */
	abstract protected void parse(ArrayList<String> lines);
	
	/** strings of default content */
	abstract protected ArrayList<String> getDefaultContent();
	
	//check string is comment: first non whitespace char = '#'
	protected static boolean isCommentString(String str)
	{
		for (int i = 0; i < str.length(); ++i)
        {
			if (!Character.isWhitespace(str.charAt(i)))
            {
				if (str.charAt(i) == '#')
				{
					return true;
				}
				else
				{
					return false;
				}
            }
        }
		
		return false;
	}
	
	
}