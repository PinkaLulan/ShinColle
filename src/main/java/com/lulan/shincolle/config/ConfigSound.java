package com.lulan.shincolle.config;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import com.lulan.shincolle.reference.Enums.SoundType;

public class ConfigSound extends BasicShipConfig
{
    
    /**
     * sound rate map by shipID (shipID, rate map)
     * rate map (Enums.SoundType, rate)
     */
    public static HashMap<Integer, EnumMap<SoundType, Float>> SOUNDRATE;

    
    public ConfigSound() {}
    
    public ConfigSound(File file) throws Exception
    {
        super(file);
        
        this.SOUNDRATE = new HashMap<Integer, EnumMap<SoundType, Float>>();
    }

    @Override
    protected void parse(ArrayList<String> lines)
    {
        //null check
        if (lines != null && lines.size() > 0)
        {
            //check lines
            for (String str : lines)
            {
                //check comment
                if (this.isCommentString(str))
                {
                    continue;    //go to next line
                }
                //parse loot entry
                else
                {
                    str = str.replaceAll("\\s", "");    //remove all whitespace
                    String strs[] = str.split(",");        //split strins by ','
                    int[] ints = new int[10];
                    
                    //check length
                    if (strs != null && strs.length == 10)
                    {
                        //get int value
                        try
                        {
                            for (int i = 0; i < 10; i++)
                            {
                                ints[i] = Integer.parseInt(strs[i]);
                            }
                            
                            //conv int rate (0 ~ 100) to float rate (0.0 ~ 1.0)
                            float[] rate = new float[9];
                            
                            for (int i = 0; i < 9; i++)
                            {
                                rate[i] = (float)ints[i + 1] * 0.01F;
                            }
                            
                            //add rate to map
                            this.SOUNDRATE.put(ints[0], rate);
                        }
                        catch (Exception e)
                        {
                            //set item entry fail, discard this string
                            continue;    //go to next line
                        }
                    }//end parse string
                }//end not comment
            }//end for all lines
        }//end lines != null        
    }

    @Override
    protected ArrayList<String> getDefaultContent()
    {
        ArrayList<String> strs = new ArrayList<String>();
        
        strs.add("# Custom Sound Rate"+NEW_LINE);
        strs.add("#"+NEW_LINE);
        strs.add("# format: ship_ID, idle, attack, hurt, dead, marry, knockback, item, feed, timekeep"+NEW_LINE);
        strs.add("#"+NEW_LINE);
        strs.add("# for each value: 70 = 70% play custom sound and 30% play general sound"+NEW_LINE);
        strs.add("#"+NEW_LINE);
        strs.add("# for any non-zero number, you must add a corresponding entry in sounds.json"+NEW_LINE);
        strs.add("# see CustomSoundReadme.txt in mod jar file"+NEW_LINE);
        strs.add("#"+NEW_LINE);
        strs.add(""+NEW_LINE);
        strs.add("54,25,0,25,0,50,0,50,0,0"+NEW_LINE);
        strs.add("56,50,50,50,100,0,0,50,0,0"+NEW_LINE);
        strs.add("60,25,50,0,0,0,0,0,0,0"+NEW_LINE);
        strs.add("62,0,35,0,0,0,0,0,0,0"+NEW_LINE);
        
        return strs;
    }
    
    
}