package com.lulan.shincolle.reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.unitclass.AttrsAdv;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/** HARD CODED VALUES
 *
 */
public class Values
{
    
    /** numbers */
    public static final class N
    {
        public static final float DIV_180_PI = 57.295779513F;  // 180 / PI
        public static final float DIV_PI_180 = 0.0174532925F;  // PI / 180
        
        public static final int BaseGrudge = 300;
        public static final int BaseLightAmmo = 30;
        public static final int BaseHeavyAmmo = 15;
        
        public static final int[] Pow2 = new int[] {1, 2, 4, 8, 16,  //2^0~24
                                                    32, 64, 128, 256, 512,
                                                    1024, 2048, 4096, 8192, 16384,
                                                    32768, 65536, 131072, 262144, 524288,
                                                    1048576, 2097152, 4194304, 8388608, 16777216};
    }
    
    
    /**SHIP ATTRIBUTES MAP
     * index by {@link ID.AttrsBase}
     */
    public static final Map<Integer, float[]> ShipAttrMap = Collections.unmodifiableMap(new HashMap<Integer, float[]>()
    {{
        //destroyer                                            HP    ATK  DEF     SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT     
        put((int)ID.ShipClass.DDI,                new float[] {20F,  3F,  0.05F,  1.0F, 0.5F,  6F,  0.3F,  0.25F, 0.11F, 0.5F,  1F,    0.4F});
        put((int)ID.ShipClass.DDRO,               new float[] {22F,  4F,  0.06F,  1.0F, 0.5F,  6F,  0.32F, 0.28F, 0.12F, 0.5F,  1F,    0.4F});
        put((int)ID.ShipClass.DDHA,               new float[] {24F,  3F,  0.07F,  1.0F, 0.5F,  6F,  0.34F, 0.25F, 0.13F, 0.5F,  1F,    0.4F});
        put((int)ID.ShipClass.DDNI,               new float[] {28F,  4F,  0.09F,  1.0F, 0.5F,  6F,  0.36F, 0.28F, 0.15F, 0.5F,  1F,    0.4F});
        //cruiser
//        put((int)ID.ShipClass.LightCruiserHO,     new float[] {33F,  6F,  0.11F,  1.0F, 0.45F, 8F,  0.38F, 0.3F,  0.17F, 0.53F, 0.9F,  0.5F});
//        put((int)ID.ShipClass.LightCruiserHE,     new float[] {36F,  9F,  0.13F,  1.0F, 0.45F, 8F,  0.4F,  0.35F, 0.18F, 0.53F, 0.9F,  0.5F});
//        put((int)ID.ShipClass.LightCruiserTO,     new float[] {39F,  8F,  0.15F,  1.0F, 0.45F, 8F,  0.42F, 0.32F, 0.19F, 0.53F, 0.9F,  0.5F});
//        put((int)ID.ShipClass.LightCruiserTSU,    new float[] {48F,  12F, 0.16F,  1.0F, 0.45F, 8F,  0.44F, 0.38F, 0.20F, 0.53F, 0.9F,  0.5F});
//        put((int)ID.ShipClass.TorpedoCruiserCHI,  new float[] {48F,  16F, 0.18F,  1.0F, 0.42F, 9F,  0.46F, 0.44F, 0.21F, 0.56F, 0.84F, 0.5F});
        put((int)ID.ShipClass.CARI,               new float[] {58F,  14F, 0.18F,  1.0F, 0.42F, 9F,  0.48F, 0.4F,  0.21F, 0.56F, 0.84F, 0.5F});
        put((int)ID.ShipClass.CANE,               new float[] {62F,  15F, 0.19F,  1.0F, 0.42F, 9F,  0.5F,  0.42F, 0.22F, 0.56F, 0.84F, 0.5F});
        //carrier                                              HP    ATK  DEF     SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//        put((int)ID.ShipClass.LightCarrierNU,     new float[] {65F,  20F, 0.20F,  0.7F, 0.32F, 14F, 0.52F, 0.45F, 0.22F, 0.5F,  0.64F, 0.6F});
        put((int)ID.ShipClass.CVWO,               new float[] {85F,  25F, 0.21F,  1.0F, 0.36F, 16F, 0.65f, 0.6F,  0.23F, 0.6F,  0.72F, 0.6F});
        //battleship
        put((int)ID.ShipClass.BBRU,               new float[] {95F,  30F, 0.30F,  1.0F, 0.32F, 12F, 0.85F, 0.65F, 0.27F, 0.63F, 0.66F, 0.5F});
        put((int)ID.ShipClass.BBTA,               new float[] {84F,  19F, 0.23F,  1.2F, 0.42F, 10F, 0.65F, 0.55F, 0.24F, 0.7F,  0.84F, 0.5F});
        put((int)ID.ShipClass.BBRE,               new float[] {120F, 27F, 0.25F,  1.1F, 0.36F, 12F, 0.8F,  0.65F, 0.25F, 0.63F, 0.72F, 0.5F});
        //transport
        put((int)ID.ShipClass.APWA,               new float[] {90F,  3F,  0.10F,  1.0F, 0.3F,  8F,  0.7F,  0.25F, 0.16F, 0.35F, 0.6F,  0.3F});
        //submarine
        put((int)ID.ShipClass.SSKA,               new float[] {40F,  28F, 0.09F,  0.8F, 0.3F,  5F,  0.35F, 0.67F, 0.14F, 0.7F,  0.6F,  0.3F});
        put((int)ID.ShipClass.SSYO,               new float[] {36F,  30F, 0.10F,  0.8F, 0.3F,  5F,  0.33F, 0.7F,  0.16F, 0.7F,  0.6F,  0.3F});
        put((int)ID.ShipClass.SSSO,               new float[] {34F,  38F, 0.12F,  0.8F, 0.28F, 5.5F,0.3F,  0.8F,  0.18F, 0.7F,  0.6F,  0.3F});
        //demon                                                HP    ATK  DEF     SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
        put((int)ID.ShipClass.IsolatedHime,       new float[] {225F, 13F, 0.34F,  0.9F, 0.22F, 24F, 1.3F,  0.4F,  0.29F, 0.6F,  0.44F, 0.8F});
//        put((int)ID.ShipClass.LightCruiserDemon,  new float[] {130F, 30F, 0.25F,  1.0F, 0.45F, 13F, 0.8F,  0.65F, 0.25F, 0.6F,  0.9F,  0.55F});
        //hime
//        put((int)ID.ShipClass.AirdefenseHime,     new float[] {120F, 30F, 0.35F,  1.0F, 0.5F,  12F, 0.8F,  0.7F,  0.3F,  0.6F,  1F,    0.5F});
        put((int)ID.ShipClass.AirfieldHime,       new float[] {240F, 16F, 0.32F,  1.0F, 0.3F,  26F, 1.2F,  0.45F, 0.28F, 0.6F,  0.6F,  0.8F});
//        put((int)ID.ShipClass.AnchorageHime,      new float[] {150F, 19F, 0.32F,  0.9F, 0.3F,  23F, 0.95F, 0.5F,  0.28F, 0.6F,  0.6F,  0.8F});
//        put((int)ID.ShipClass.ArmoredCarrierHime, new float[] {200F, 34F, 0.35F,  1.0F, 0.42F, 20F, 0.9F,  0.73F, 0.3F,  0.63F, 0.84F, 0.7F});
        put((int)ID.ShipClass.BBHime,             new float[] {220F, 42F, 0.40F,  1.0F, 0.4F,  16F, 1.0F,  0.8F,  0.32F, 0.73F, 0.8F,  0.6F});
        put((int)ID.ShipClass.CVHime,             new float[] {180F, 40F, 0.28F,  1.0F, 0.45F, 22F, 0.85F, 0.75F, 0.26F, 0.62F, 0.85F, 0.7F});
        put((int)ID.ShipClass.DDHime,             new float[] {90F,  22F, 0.20F,  1.0F, 0.52F, 12F, 0.55F, 0.5F,  0.22F, 0.6F,  1F,    0.5F});
        put((int)ID.ShipClass.HarbourHime,        new float[] {260F, 14F, 0.36F,  0.8F, 0.2F,  24F, 1.35F, 0.4F,  0.3F,  0.6F,  0.4F,  0.8F});
        put((int)ID.ShipClass.MidwayHime,         new float[] {350F, 22F, 0.45F,  0.8F, 0.25F, 30F, 1.5F,  0.5F,  0.34F, 0.6F,  0.4F,  0.8F});
        put((int)ID.ShipClass.NorthernHime,       new float[] {210F, 13F, 0.30F,  0.8F, 0.32F, 22F, 1.15F, 0.35F, 0.27F, 0.6F,  0.64F, 0.8F});
//        put((int)ID.ShipClass.SeaplaneHime,       new float[] {170F, 24F, 0.25F,  1.0F, 0.45F, 18F, 1F,    0.6F,  0.25F, 0.63F, 0.9F,  0.65F});
//        put((int)ID.ShipClass.SouthernHime,       new float[] {170F, 35F, 0.34F,  1.0F, 0.3F,  20F, 1F,    0.73F, 0.29F, 0.63F, 0.6F,  0.7F});
        put((int)ID.ShipClass.SSHime,             new float[] {75F,  45F, 0.15F,  1.0F, 0.3F,  7.5F,0.5F,  0.9F,  0.2F,  0.7F,  0.6F,  0.4F});
//        put((int)ID.ShipClass.LightCruiserHime,   new float[] {160F, 32F, 0.26F,  1.0F, 0.45F, 14F, 0.82F, 0.7F,  0.25F, 0.6F,  0.9F,  0.55F});
        put((int)ID.ShipClass.CAHime,             new float[] {180F, 35F, 0.32F,  1.0F, 0.45F, 14F, 0.85F, 0.77F, 0.29F, 0.65F, 0.9F,  0.6F});
//        put((int)ID.ShipClass.SupplyDepotHime,    new float[] {320F, 16F, 0.38F,  0.8F, 0.18F, 26F, 1.4F,  0.42F, 0.32F, 0.6F,  0.4F,  0.8F});
        put((int)ID.ShipClass.SSNH,               new float[] {55F,  34F, 0.10F,  1.0F, 0.4F,  5.5F,0.4F,  0.75F, 0.16F, 0.7F,  0.7F,  0.4F});
        //    
        //water demon                                          HP    ATK  DEF     SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//        put((int)ID.ShipClass.AnchorageWD,        new float[] {230F, 28F, 0.35F,  1.0F, 0.35F, 26F, 1.35F, 0.6F,  0.3F,  0.7F,  0.7F,  1F});
//        put((int)ID.ShipClass.BattleshipWD,       new float[] {280F, 50F, 0.45F,  1.0F, 0.42F, 21F, 1.1F,  1F,    0.34F, 0.85F, 0.84F, 0.7F});
        put((int)ID.ShipClass.CVWD,               new float[] {190F, 45F, 0.40F,  1.0F, 0.42F, 25F, 1F,    0.95F, 0.32F, 0.75F, 0.84F, 0.8F});
//        put((int)ID.ShipClass.HarbourWD,          new float[] {300F, 35F, 0.45F,  1.0F, 0.35F, 29F, 1.5F,  0.63F, 0.34F, 0.7F,  0.7F,  1F});
//        put((int)ID.ShipClass.DestroyerWD,        new float[] {150F, 23F, 0.25F,  1.0F, 0.55F, 15F, 0.9F,  0.8F,  0.3F,  0.7F,  1F,    0.6F});
        //hostile ship                                         
        //destroyer                                            HP    ATK  DEF     SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
        put((int)ID.ShipClass.DDAkatsuki,         new float[] {32F,  9F,  0.09F,  1.0F, 0.5F,  11F, 0.32f, 0.38F, 0.12F, 0.5F,  1F,    0.5F});
        put((int)ID.ShipClass.DDHibiki,           new float[] {40F,  7F,  0.11F,  1.0F, 0.5F,  10F, 0.38f, 0.36F, 0.14F, 0.5F,  1F,    0.48F});
        put((int)ID.ShipClass.DDIkazuchi,         new float[] {30F,  5F,  0.09F,  1.0F, 0.5F,  9F,  0.3f,  0.32F, 0.12F, 0.5F,  1F,    0.46F});
        put((int)ID.ShipClass.DDInazuma,          new float[] {30F,  5F,  0.09F,  1.0F, 0.5F,  9F,  0.3f,  0.32F, 0.12F, 0.5F,  1F,    0.46F});
        put((int)ID.ShipClass.DDShimakaze,        new float[] {38F,  11F, 0.12F,  1.0F, 0.6F,  9F,  0.35F, 0.4F,  0.16F, 0.55F, 1.2F,  0.46F});
        //cruiser
        put((int)ID.ShipClass.CLTenryuu,          new float[] {42F,  13F, 0.16F,  1.0F, 0.42F, 8F,  0.4F,  0.4F,  0.2F,  0.6F,  0.9F,  0.4F});
        put((int)ID.ShipClass.CLTatsuta,          new float[] {42F,  13F, 0.16F,  1.0F, 0.42F, 8F,  0.4F,  0.4F,  0.2F,  0.6F,  0.9F,  0.4F});
        put((int)ID.ShipClass.CAAtago,            new float[] {62F,  15F, 0.18F,  1.0F, 0.42F, 9F,  0.5F,  0.42F, 0.22F, 0.56F, 0.84F, 0.5F});
        put((int)ID.ShipClass.CATakao,            new float[] {62F,  15F, 0.18F,  1.0F, 0.42F, 9F,  0.5F,  0.42F, 0.22F, 0.56F, 0.84F, 0.5F});
        //battleship
        put((int)ID.ShipClass.BBNagato,           new float[] {135F, 40F, 0.26F,  1.0F, 0.32F, 14F, 0.85F, 0.8F,  0.25F, 0.63F, 0.64F, 0.6F});
        put((int)ID.ShipClass.BBYamato,           new float[] {150F, 55F, 0.36F,  1.0F, 0.3F,  20F, 1F,    1F,    0.3F,  0.7F,  0.6F,  0.7F});
        put((int)ID.ShipClass.BBKongou,           new float[] {90F,  28F, 0.36F,  1.0F, 0.42F, 12F, 0.7F,  0.6F,  0.24F, 0.6F,  0.84F, 0.55F});
        put((int)ID.ShipClass.BBHiei,             new float[] {90F,  28F, 0.36F,  1.0F, 0.42F, 12F, 0.7F,  0.6F,  0.24F, 0.6F,  0.84F, 0.55F});
        put((int)ID.ShipClass.BBHaruna,           new float[] {90F,  28F, 0.36F,  1.0F, 0.42F, 12F, 0.7F,  0.6F,  0.24F, 0.6F,  0.84F, 0.55F});
        put((int)ID.ShipClass.BBKirishima,        new float[] {90F,  28F, 0.36F,  1.0F, 0.42F, 12F, 0.7F,  0.6F,  0.24F, 0.6F,  0.84F, 0.55F});
        //submarine
        put((int)ID.ShipClass.SSU511,             new float[] {28F,  30F, 0.07F,  0.8F, 0.3F,  10F, 0.3F,  0.7F,  0.13F, 0.7F,  0.6F,  0.4F});
        put((int)ID.ShipClass.SSRo500,            new float[] {32F,  32F, 0.10F,  0.8F, 0.3F,  11F, 0.33F, 0.75F, 0.16F, 0.7F,  0.6F,  0.4F});
        //carrier
        put((int)ID.ShipClass.CVKaga,             new float[] {70F,  22F, 0.21F,  1.0F, 0.34F, 16F, 0.65f, 0.6F,  0.23F, 0.6F,  0.72F, 0.6F});
        put((int)ID.ShipClass.CVAkagi,            new float[] {75F,  22F, 0.20F,  1.0F, 0.32F, 16F, 0.65f, 0.6F,  0.23F, 0.6F,  0.72F, 0.6F});
        //support
        put((int)ID.ShipClass.APMamiya,           new float[] {90F,  3F,  0.10F,  1.0F, 0.3F,  8F,  0.7F,  0.25F, 0.16F, 0.35F, 0.6F,  0.3F});
    }});
    
    /**HOSTILE SHIP ATTRIBUTES MAP
     * index by ID.AttrsBase
     */
    public static final Map<Integer, float[]> HostileShipAttrMap = Collections.unmodifiableMap(new HashMap<Integer, float[]>()
    {{
        //destroyer                                     HP     ATK    DEF    SPD    MOV    HIT
        put((int)ID.ShipClass.DDAkatsuki,  new float[] {0.35F, 0.35F, 0.35F, 1F,    1.1F,  0.7F});
        put((int)ID.ShipClass.DDHibiki,    new float[] {0.35F, 0.35F, 0.35F, 1F,    1.1F,  0.7F});
        put((int)ID.ShipClass.DDIkazuchi,  new float[] {0.35F, 0.35F, 0.35F, 1F,    1.1F,  0.7F});
        put((int)ID.ShipClass.DDInazuma,   new float[] {0.35F, 0.35F, 0.35F, 1F,    1.1F,  0.7F});
        put((int)ID.ShipClass.DDShimakaze, new float[] {0.4F,  0.5F,  0.4F,  1.2F,  1.2F,  0.75F});
        //cruiser
        put((int)ID.ShipClass.CLTenryuu,   new float[] {0.55F, 0.6F,  0.5F,  1F,    0.9F,  0.8F});
        put((int)ID.ShipClass.CLTatsuta,   new float[] {0.55F, 0.6F,  0.5F,  1F,    0.9F,  0.8F});
        put((int)ID.ShipClass.CAAtago,     new float[] {0.8F,  0.8F,  0.8F,  0.8F,  0.8F,  0.9F});
        put((int)ID.ShipClass.CATakao,     new float[] {0.8F,  0.8F,  0.8F,  0.8F,  0.8F,  0.9F});
        //battleship
        put((int)ID.ShipClass.BBNagato,    new float[] {1.1F,  1.1F,  1.1F,  1F,    0.8F,  1.05F});
        put((int)ID.ShipClass.BBYamato,    new float[] {1.2F,  1.2F,  1.2F,  1.2F,  0.8F,  1.1F});
        put((int)ID.ShipClass.BBKongou,    new float[] {1F,    1.05F, 1F,    1F,    1F,    1F});
        put((int)ID.ShipClass.BBHiei,      new float[] {1F,    1.05F, 1F,    1F,    1F,    1F});
        put((int)ID.ShipClass.BBHaruna,    new float[] {1F,    1.05F, 1F,    1F,    1F,    1F});
        put((int)ID.ShipClass.BBKirishima, new float[] {1F,    1.05F, 1F,    1F,    1F,    1F});
        //submarine                                            HP     ATK    DEF    SPD    MOV    HIT
        put((int)ID.ShipClass.SSU511,      new float[] {0.25F, 0.8F,  0.25F, 0.75F, 0.4F,  0.4F});
        put((int)ID.ShipClass.SSRo500,     new float[] {0.25F, 0.8F,  0.25F, 0.75F, 0.4F,  0.4F});
        //carrier
        put((int)ID.ShipClass.CVKaga,      new float[] {0.8F,  0.8F,  0.8F,  0.75F, 0.8F,  1.2F});
        put((int)ID.ShipClass.CVAkagi,     new float[] {0.8F,  0.8F,  0.8F,  0.75F, 0.8F,  1.2F});
        //support
        put((int)ID.ShipClass.APMamiya,    new float[] {0.35F, 0.35F, 0.35F, 1F,    0.8F,  0.7F});
    }});
    
    /** SHIP LEASH HEIGHT
     * 
     *  map <ship id(short), data(float[])>
     *  data: 0:Width, 1:Ride, 2:RideSit, 3:Sit, 4:Stand
     */
    public static final Map<Integer, float[]> ShipLeashHeight = Collections.unmodifiableMap(new HashMap<Integer, float[]>()
    {{
        //destroyer
        put((int)ID.ShipClass.DDI,            new float[] {0.8F, 0.8F, 0.8F, 0.8F, 0.8F});
        put((int)ID.ShipClass.DDRO,           new float[] {1.2F, 0.8F, 0.8F, 0.8F, 0.7F});
        put((int)ID.ShipClass.DDHA,           new float[] {1.5F, 0.8F, 0.8F, 0.8F, 0.8F});
        put((int)ID.ShipClass.DDNI,           new float[] {0.5F, 0.9F, 0.9F, 0.9F, 0.9F});
        //cruiser
//        put((int)ID.Ship.LightCruiserHO,      new float[] {});
//        put((int)ID.Ship.LightCruiserHE,      new float[] {});
//        put((int)ID.Ship.LightCruiserTO,      new float[] {});
//        put((int)ID.Ship.LightCruiserTSU,     new float[] {});
//        put((int)ID.Ship.TorpedoCruiserCHI,   new float[] {});
        put((int)ID.ShipClass.CARI,           new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.25F});
        put((int)ID.ShipClass.CANE,           new float[] {0.5F, 1.1F, 1.1F, 1.1F, 0.85F});
        //carrier
//        put((int)ID.Ship.LightCarrierNU,        new float[] {});
        put((int)ID.ShipClass.CVWO,           new float[] {0.1F, 0.2F, 0.2F, 0.2F, 0.15F});
        //battleship
        put((int)ID.ShipClass.BBRU,           new float[] {0.1F, 0.85F, 0.85F, 0.85F, 0.15F});
        put((int)ID.ShipClass.BBTA,           new float[] {0.1F, 0.85F, 0.85F, 0.85F, 0.15F});
        put((int)ID.ShipClass.BBRE,           new float[] {0.1F, 0.45F, 0.45F, 0.65F, 0.45F});
        //transport
        put((int)ID.ShipClass.APWA,           new float[] {0.2F, 0.8F, 0.8F, 0.8F, 0.3F});
        //submarine
        put((int)ID.ShipClass.SSKA,           new float[] {0.2F, 0.7F, 0.8F, 0.8F, 0.2F});
        put((int)ID.ShipClass.SSYO,           new float[] {0.2F, 0.7F, 0.8F, 0.8F, 0.2F});
        put((int)ID.ShipClass.SSSO,           new float[] {0.2F, 0.7F, 0.8F, 0.8F, 0.2F});
        //hime
        put((int)ID.ShipClass.CVHime,         new float[] {0.1F, 0.95F, 0.85F, 0.9F, 0.15F});
        put((int)ID.ShipClass.AirfieldHime,   new float[] {0.1F, 1.2F, 1.5F, 0.7F, 0.2F});
//        put((int)ID.Ship.ArmoredCarrierHime,  new float[] {});
//        put((int)ID.Ship.AnchorageHime,       new float[] {});
//        put((int)ID.Ship.LightCruiserDemon,   new float[] {});
//        put((int)ID.Ship.AirdefenseHime,      new float[] {});
        put((int)ID.ShipClass.BBHime,         new float[] {0.1F, 0.3F, 0.8F, 0.7F, 0.1F});
        put((int)ID.ShipClass.DDHime,         new float[] {0.1F, 0.85F, 0.85F, 0.85F, 0.35F});
        put((int)ID.ShipClass.HarbourHime,    new float[] {0F, 1.5F, 2.1F, 1.1F, 0.15F});
        put((int)ID.ShipClass.IsolatedHime,   new float[] {0.1F, 0.85F, 0.85F, 0.85F, 0.35F});
        put((int)ID.ShipClass.MidwayHime,     new float[] {0F, 1.5F, 2.1F, 1.1F, 0.15F});
        put((int)ID.ShipClass.NorthernHime,   new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.5F});
//        put((int)ID.Ship.SouthernHime,        new float[] {});
//        put((int)ID.Ship.SeaplaneHime,        new float[] {});
        put((int)ID.ShipClass.CVWD,           new float[] {0.1F, 0.95F, 0.85F, 0.9F, 0.15F});
//        put((int)ID.Ship.BattleshipWD,        new float[] {});
//        put((int)ID.Ship.AnchorageWD,         new float[] {});
//        put((int)ID.Ship.HarbourWD,           new float[] {});
//        put((int)ID.Ship.DestroyerWD,         new float[] {});
//        put((int)ID.Ship.LightCruiserHime,    new float[] {});
        put((int)ID.ShipClass.CAHime,         new float[] {0.5F, 1.1F, 1.1F, 1.1F, 0.85F});
        put((int)ID.ShipClass.SSHime,         new float[] {0F, 1.5F, 2.1F, 1.1F, 0.15F});
//        put((int)ID.Ship.SupplyDepotHime,     new float[] {});
        put((int)ID.ShipClass.SSNH,           new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.5F});
        //hostile ship
        //DD
        put((int)ID.ShipClass.DDAkatsuki,     new float[] {0.1F, 0.8F, 0.8F, 0.8F, 0.3F});
        put((int)ID.ShipClass.DDHibiki,       new float[] {0.1F, 0.8F, 0.8F, 0.8F, 0.3F});
        put((int)ID.ShipClass.DDIkazuchi,     new float[] {0.1F, 0.8F, 0.8F, 0.8F, 0.3F});
        put((int)ID.ShipClass.DDInazuma,      new float[] {0.1F, 0.8F, 0.8F, 0.8F, 0.3F});
        put((int)ID.ShipClass.DDShimakaze,    new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.4F});
        //CL
        put((int)ID.ShipClass.CLTenryuu,      new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.25F});
        put((int)ID.ShipClass.CLTatsuta,      new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.25F});
        put((int)ID.ShipClass.CAAtago,        new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.2F});
        put((int)ID.ShipClass.CATakao,        new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.2F});
        //BB
        put((int)ID.ShipClass.BBNagato,       new float[] {0.1F, 0.95F, 0.95F, 0.95F, 0.1F});
        put((int)ID.ShipClass.BBYamato,       new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.15F});
        put((int)ID.ShipClass.BBKongou,       new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
        put((int)ID.ShipClass.BBHiei,         new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
        put((int)ID.ShipClass.BBHaruna,       new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
        put((int)ID.ShipClass.BBKirishima,    new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
        //SS
        put((int)ID.ShipClass.SSU511,         new float[] {0.1F, 1F, 1F, 1F, 0.35F});
        put((int)ID.ShipClass.SSRo500,        new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.35F});
        //CV
        put((int)ID.ShipClass.CVKaga,         new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
        put((int)ID.ShipClass.CVAkagi,        new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
        //AP
        put((int)ID.ShipClass.APMamiya,       new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
    }});
    
    
    /** SHIP NAME ICON MAP
     * 
     *  map <ship id(short), int[]: 0:file&line id, 1:icon X, 2:icon Y>
     *  
     *  font: MS Mincho, 12
     *  
     *  file & line id = file * 100 + line, ex: file 5 line 9 = 509
     *    
     *  icon size:
     *    file 0:
     *      line 1~3: 11x59
     *      line 4:   11x71
     *    fil1 1:
     *      line 1~5: 11x39
     *      line 6:   11x61
     */
    public static final Map<Integer, int[]> ShipNameIconMap = Collections.unmodifiableMap(new HashMap<Integer, int[]>()
    {{
        //file 0
        //line 1
        put((int)ID.ShipClass.DDI,               new int[] {1, 0, 0});
        put((int)ID.ShipClass.DDRO,              new int[] {1, 11, 0});
        put((int)ID.ShipClass.DDHA,              new int[] {1, 22, 0});
        put((int)ID.ShipClass.DDNI,              new int[] {1, 33, 0});
        put((int)ID.ShipClass.CLHO,              new int[] {1, 44, 0});
        put((int)ID.ShipClass.CLHE,              new int[] {1, 55, 0});
        put((int)ID.ShipClass.CLTO,              new int[] {1, 66, 0});
        put((int)ID.ShipClass.CLTSU,             new int[] {1, 77, 0});
        put((int)ID.ShipClass.CLTCHI,            new int[] {1, 88, 0});
        put((int)ID.ShipClass.CARI,              new int[] {1, 99, 0});
        put((int)ID.ShipClass.CANE,              new int[] {1, 110, 0});
        put((int)ID.ShipClass.CVLNU,             new int[] {1, 121, 0});
        put((int)ID.ShipClass.CVWO,              new int[] {1, 132, 0});
        put((int)ID.ShipClass.BBRU,              new int[] {1, 143, 0});
        put((int)ID.ShipClass.BBTA,              new int[] {1, 154, 0});
        put((int)ID.ShipClass.BBRE,              new int[] {1, 165, 0});
        put((int)ID.ShipClass.APWA,              new int[] {1, 176, 0});
        put((int)ID.ShipClass.SSKA,              new int[] {1, 187, 0});
        put((int)ID.ShipClass.SSYO,              new int[] {1, 198, 0});
        put((int)ID.ShipClass.SSSO,              new int[] {1, 209, 0});
        put((int)ID.ShipClass.DDNA,              new int[] {1, 220, 0});
//        put((int)ID.ShipClass,new int[] {0, 231, 0});
//        put((int)ID.ShipClass,new int[] {0, 242, 0});
        //line 2
        put((int)ID.ShipClass.CVHime,            new int[] {2, 0, 59});
        put((int)ID.ShipClass.AirfieldHime,      new int[] {2, 11, 59});
        put((int)ID.ShipClass.ArmoredCVHime,     new int[] {2, 22, 59});
        put((int)ID.ShipClass.AnchorageHime,     new int[] {2, 33, 59});
        put((int)ID.ShipClass.CLDemon,           new int[] {2, 44, 59});
        put((int)ID.ShipClass.AirdefenseHime,    new int[] {2, 55, 59});
        put((int)ID.ShipClass.BBHime,            new int[] {2, 66, 59});
        put((int)ID.ShipClass.DDHime,            new int[] {2, 77, 59});
        put((int)ID.ShipClass.HarbourHime,         new int[] {2, 88, 59});
        put((int)ID.ShipClass.IsolatedHime,          new int[] {2, 99, 59});
        put((int)ID.ShipClass.MidwayHime,         new int[] {2, 110, 59});
        put((int)ID.ShipClass.NorthernHime,         new int[] {2, 121, 59});
        put((int)ID.ShipClass.SouthernHime,         new int[] {2, 132, 59});
        put((int)ID.ShipClass.STHime,            new int[] {2, 143, 59});
        put((int)ID.ShipClass.CVWD,              new int[] {2, 154, 59});
        put((int)ID.ShipClass.BBWD,              new int[] {2, 165, 59});
        put((int)ID.ShipClass.AnchorageWD,         new int[] {2, 176, 59});
        put((int)ID.ShipClass.HarbourWD,         new int[] {2, 187, 59});
        put((int)ID.ShipClass.DDWD,              new int[] {2, 198, 59});
        put((int)ID.ShipClass.CLHime,            new int[] {2, 209, 59});
        put((int)ID.ShipClass.CAHime,            new int[] {2, 220, 59});
        put((int)ID.ShipClass.SSHime,            new int[] {2, 231, 59});
        put((int)ID.ShipClass.SupplyDepotHime,     new int[] {2, 242, 59});
        //line 3
        put((int)ID.ShipClass.DDAH,              new int[] {3, 0, 118});
        put((int)ID.ShipClass.STWH,              new int[] {3, 11, 118});
        put((int)ID.ShipClass.NorthernWH,        new int[] {3, 22, 118});
        put((int)ID.ShipClass.JellyfishHime,     new int[] {3, 33, 118});
        put((int)ID.ShipClass.EscortHime,        new int[] {3, 44, 118});
        put((int)ID.ShipClass.EuropeanHime,      new int[] {3, 55, 118});
        put((int)ID.ShipClass.CentralHime,       new int[] {3, 66, 118});
        put((int)ID.ShipClass.SSNH,              new int[] {3, 77, 118});
        put((int)ID.ShipClass.FrenchHime,        new int[] {3, 88, 118});
        put((int)ID.ShipClass.NightStraitHime,   new int[] {3, 99, 118});
        put((int)ID.ShipClass.EntombedAAHime,    new int[] {3, 110, 118});
        put((int)ID.ShipClass.NorthlandHime,     new int[] {3, 121, 118});
        put((int)ID.ShipClass.SupplyDepotSH,     new int[] {3, 132, 118});
        put((int)ID.ShipClass.SSSH,              new int[] {3, 143, 118});
        put((int)ID.ShipClass.BBSH,              new int[] {3, 154, 118});
        put((int)ID.ShipClass.CASH,              new int[] {3, 165, 118});
        put((int)ID.ShipClass.CVSH,              new int[] {3, 176, 118});
        put((int)ID.ShipClass.HarbourSH,         new int[] {3, 187, 118});
//        put((int)ID.ShipClass,new int[] {3, 198, 118});
//        put((int)ID.ShipClass,new int[] {3, 209, 118});
//        put((int)ID.ShipClass,new int[] {3, 220, 118});
//        put((int)ID.ShipClass,new int[] {3, 231, 118});
//        put((int)ID.ShipClass,new int[] {3, 242, 118});
        //line 4
        put((int)ID.ShipClass.LycorisHime,       new int[] {4, 0, 177});
        put((int)ID.ShipClass.TwinHime,          new int[] {4, 11, 177});
//        put((int)ID.ShipClass,new int[] {4, 22, 177});
//        put((int)ID.ShipClass,new int[] {4, 33, 177});
//        put((int)ID.ShipClass,new int[] {4, 44, 177});
//        put((int)ID.ShipClass,new int[] {4, 55, 177});
//        put((int)ID.ShipClass,new int[] {4, 66, 177});
//        put((int)ID.ShipClass,new int[] {4, 77, 177});
//        put((int)ID.ShipClass,new int[] {4, 88, 177});
//        put((int)ID.ShipClass,new int[] {4, 99, 177});
//        put((int)ID.ShipClass,new int[] {4, 110, 177});
//        put((int)ID.ShipClass,new int[] {4, 121, 177});
//        put((int)ID.ShipClass,new int[] {4, 132, 177});
//        put((int)ID.ShipClass,new int[] {4, 143, 177});
//        put((int)ID.ShipClass,new int[] {4, 154, 177});
//        put((int)ID.ShipClass,new int[] {4, 165, 177});
//        put((int)ID.ShipClass,new int[] {4, 176, 177});
//        put((int)ID.ShipClass,new int[] {4, 187, 177});
//        put((int)ID.ShipClass,new int[] {4, 198, 177});
//        put((int)ID.ShipClass,new int[] {4, 209, 177});
//        put((int)ID.ShipClass,new int[] {4, 220, 177});
//        put((int)ID.ShipClass,new int[] {4, 231, 177});
//        put((int)ID.ShipClass,new int[] {4, 242, 177});
        
        //file 1
        //line 1
        put((int)ID.ShipClass.DDShimakaze,       new int[] {101, 0, 0});
        put((int)ID.ShipClass.BBNagato,          new int[] {101, 11, 0});
        put((int)ID.ShipClass.BBYamato,          new int[] {101, 22, 0});
        put((int)ID.ShipClass.CVKaga,            new int[] {101, 33, 0});
        put((int)ID.ShipClass.CVAkagi,           new int[] {101, 44, 0});
        put((int)ID.ShipClass.DDAkatsuki,        new int[] {101, 55, 0});
        put((int)ID.ShipClass.DDHibiki,          new int[] {101, 66, 0});
        put((int)ID.ShipClass.DDIkazuchi,        new int[] {101, 77, 0});
        put((int)ID.ShipClass.DDInazuma,         new int[] {101, 88, 0});
        put((int)ID.ShipClass.Raiden,            new int[] {101, 99, 0});
        put((int)ID.ShipClass.CLTenryuu,         new int[] {101, 110, 0});
        put((int)ID.ShipClass.CLTatsuta,         new int[] {101, 121, 0});
        put((int)ID.ShipClass.CAAtago,           new int[] {101, 132, 0});
        put((int)ID.ShipClass.CATakao,           new int[] {101, 143, 0});
        put((int)ID.ShipClass.BBKongou,          new int[] {101, 154, 0});
        put((int)ID.ShipClass.BBHiei,            new int[] {101, 165, 0});
        put((int)ID.ShipClass.BBHaruna,          new int[] {101, 176, 0});
        put((int)ID.ShipClass.BBKirishima,       new int[] {101, 187, 0});
        put((int)ID.ShipClass.SSU511,            new int[] {101, 198, 0});
        put((int)ID.ShipClass.SSRo500,           new int[] {101, 209, 0});
        put((int)ID.ShipClass.APMamiya,          new int[] {101, 220, 0});
//        put((int)ID.ShipClass,new int[] {101, 231, 0});
//        put((int)ID.ShipClass,new int[] {101, 242, 0});
        //line 2
//        put((int)ID.ShipClass,new int[] {102, 0, 39});
//        put((int)ID.ShipClass,new int[] {102, 11, 39});
//        put((int)ID.ShipClass,new int[] {102, 22, 39});
//        put((int)ID.ShipClass,new int[] {102, 33, 39});
//        put((int)ID.ShipClass,new int[] {102, 44, 39});
//        put((int)ID.ShipClass,new int[] {102, 55, 39});
//        put((int)ID.ShipClass,new int[] {102, 66, 39});
//        put((int)ID.ShipClass,new int[] {102, 77, 39});
//        put((int)ID.ShipClass,new int[] {102, 88, 39});
//        put((int)ID.ShipClass,new int[] {102, 99, 39});
//        put((int)ID.ShipClass,new int[] {102, 110, 39});
//        put((int)ID.ShipClass,new int[] {102, 121, 39});
//        put((int)ID.ShipClass,new int[] {102, 132, 39});
//        put((int)ID.ShipClass,new int[] {102, 143, 39});
//        put((int)ID.ShipClass,new int[] {102, 154, 39});
//        put((int)ID.ShipClass,new int[] {102, 165, 39});
//        put((int)ID.ShipClass,new int[] {102, 176, 39});
//        put((int)ID.ShipClass,new int[] {102, 187, 39});
//        put((int)ID.ShipClass,new int[] {102, 198, 39});
//        put((int)ID.ShipClass,new int[] {102, 209, 39});
//        put((int)ID.ShipClass,new int[] {102, 220, 39});
//        put((int)ID.ShipClass,new int[] {102, 231, 39});
//        put((int)ID.ShipClass,new int[] {102, 242, 39});
        //line 3
//        put((int)ID.ShipClass,new int[] {103, 0, 78});
//        put((int)ID.ShipClass,new int[] {103, 11, 78});
//        put((int)ID.ShipClass,new int[] {103, 22, 78});
//        put((int)ID.ShipClass,new int[] {103, 33, 78});
//        put((int)ID.ShipClass,new int[] {103, 44, 78});
//        put((int)ID.ShipClass,new int[] {103, 55, 78});
//        put((int)ID.ShipClass,new int[] {103, 66, 78});
//        put((int)ID.ShipClass,new int[] {103, 77, 78});
//        put((int)ID.ShipClass,new int[] {103, 88, 78});
//        put((int)ID.ShipClass,new int[] {103, 99, 78});
//        put((int)ID.ShipClass,new int[] {103, 110, 78});
//        put((int)ID.ShipClass,new int[] {103, 121, 78});
//        put((int)ID.ShipClass,new int[] {103, 132, 78});
//        put((int)ID.ShipClass,new int[] {103, 143, 78});
//        put((int)ID.ShipClass,new int[] {103, 154, 78});
//        put((int)ID.ShipClass,new int[] {103, 165, 78});
//        put((int)ID.ShipClass,new int[] {103, 176, 78});
//        put((int)ID.ShipClass,new int[] {103, 187, 78});
//        put((int)ID.ShipClass,new int[] {103, 198, 78});
//        put((int)ID.ShipClass,new int[] {103, 209, 78});
//        put((int)ID.ShipClass,new int[] {103, 220, 78});
//        put((int)ID.ShipClass,new int[] {103, 231, 78});
//        put((int)ID.ShipClass,new int[] {103, 242, 78});
        //line 4
//        put((int)ID.ShipClass,new int[] {104, 0, 117});
//        put((int)ID.ShipClass,new int[] {104, 11, 117});
//        put((int)ID.ShipClass,new int[] {104, 22, 117});
//        put((int)ID.ShipClass,new int[] {104, 33, 117});
//        put((int)ID.ShipClass,new int[] {104, 44, 117});
//        put((int)ID.ShipClass,new int[] {104, 55, 117});
//        put((int)ID.ShipClass,new int[] {104, 66, 117});
//        put((int)ID.ShipClass,new int[] {104, 77, 117});
//        put((int)ID.ShipClass,new int[] {104, 88, 117});
//        put((int)ID.ShipClass,new int[] {104, 99, 117});
//        put((int)ID.ShipClass,new int[] {104, 110, 117});
//        put((int)ID.ShipClass,new int[] {104, 121, 117});
//        put((int)ID.ShipClass,new int[] {104, 132, 117});
//        put((int)ID.ShipClass,new int[] {104, 143, 117});
//        put((int)ID.ShipClass,new int[] {104, 154, 117});
//        put((int)ID.ShipClass,new int[] {104, 165, 117});
//        put((int)ID.ShipClass,new int[] {104, 176, 117});
//        put((int)ID.ShipClass,new int[] {104, 187, 117});
//        put((int)ID.ShipClass,new int[] {104, 198, 117});
//        put((int)ID.ShipClass,new int[] {104, 209, 117});
//        put((int)ID.ShipClass,new int[] {104, 220, 117});
//        put((int)ID.ShipClass,new int[] {104, 231, 117});
//        put((int)ID.ShipClass,new int[] {104, 242, 117});
        //line 5
//        put((int)ID.ShipClass,new int[] {105, 0, 156});
//        put((int)ID.ShipClass,new int[] {105, 11, 156});
//        put((int)ID.ShipClass,new int[] {105, 22, 156});
//        put((int)ID.ShipClass,new int[] {105, 33, 156});
//        put((int)ID.ShipClass,new int[] {105, 44, 156});
//        put((int)ID.ShipClass,new int[] {105, 55, 156});
//        put((int)ID.ShipClass,new int[] {105, 66, 156});
//        put((int)ID.ShipClass,new int[] {105, 77, 156});
//        put((int)ID.ShipClass,new int[] {105, 88, 156});
//        put((int)ID.ShipClass,new int[] {105, 99, 156});
//        put((int)ID.ShipClass,new int[] {105, 110, 156});
//        put((int)ID.ShipClass,new int[] {105, 121, 156});
//        put((int)ID.ShipClass,new int[] {105, 132, 156});
//        put((int)ID.ShipClass,new int[] {105, 143, 156});
//        put((int)ID.ShipClass,new int[] {105, 154, 156});
//        put((int)ID.ShipClass,new int[] {105, 165, 156});
//        put((int)ID.ShipClass,new int[] {105, 176, 156});
//        put((int)ID.ShipClass,new int[] {105, 187, 156});
//        put((int)ID.ShipClass,new int[] {105, 198, 156});
//        put((int)ID.ShipClass,new int[] {105, 209, 156});
//        put((int)ID.ShipClass,new int[] {105, 220, 156});
//        put((int)ID.ShipClass,new int[] {105, 231, 156});
//        put((int)ID.ShipClass,new int[] {105, 242, 156});
        //line 6
//        put((int)ID.ShipClass,new int[] {106, 0, 195});
//        put((int)ID.ShipClass,new int[] {106, 11, 195});
//        put((int)ID.ShipClass,new int[] {106, 22, 195});
//        put((int)ID.ShipClass,new int[] {106, 33, 195});
//        put((int)ID.ShipClass,new int[] {106, 44, 195});
//        put((int)ID.ShipClass,new int[] {106, 55, 195});
//        put((int)ID.ShipClass,new int[] {106, 66, 195});
//        put((int)ID.ShipClass,new int[] {106, 77, 195});
//        put((int)ID.ShipClass,new int[] {106, 88, 195});
//        put((int)ID.ShipClass,new int[] {106, 99, 195});
//        put((int)ID.ShipClass,new int[] {106, 110, 195});
//        put((int)ID.ShipClass,new int[] {106, 121, 195});
//        put((int)ID.ShipClass,new int[] {106, 132, 195});
//        put((int)ID.ShipClass,new int[] {106, 143, 195});
//        put((int)ID.ShipClass,new int[] {106, 154, 195});
//        put((int)ID.ShipClass,new int[] {106, 165, 195});
//        put((int)ID.ShipClass,new int[] {106, 176, 195});
//        put((int)ID.ShipClass,new int[] {106, 187, 195});
//        put((int)ID.ShipClass,new int[] {106, 198, 195});
//        put((int)ID.ShipClass,new int[] {106, 209, 195});
//        put((int)ID.ShipClass,new int[] {106, 220, 195});
//        put((int)ID.ShipClass,new int[] {106, 231, 195});
//        put((int)ID.ShipClass,new int[] {106, 242, 195});
    }});
    
    /**SHIP LIST for guidebook
     * 
     * index by page number
     */
    public static final List<Integer> ShipBookList = Collections.unmodifiableList(new ArrayList<Integer>()
    {{
        add((int)ID.ShipClass.DDI);
        add((int)ID.ShipClass.DDRO);
        add((int)ID.ShipClass.DDHA);
        add((int)ID.ShipClass.DDNI);
        add((int)ID.ShipClass.CARI);
        add((int)ID.ShipClass.CANE);
        add((int)ID.ShipClass.CVWO);
        add((int)ID.ShipClass.BBTA);
        add((int)ID.ShipClass.BBRE);
        add((int)ID.ShipClass.AirfieldHime);
        add((int)ID.ShipClass.BBHime);
        add((int)ID.ShipClass.HarbourHime);
        add((int)ID.ShipClass.NorthernHime);
        add((int)ID.ShipClass.CVWD);
        add((int)ID.ShipClass.APWA);
        add((int)ID.ShipClass.SSKA);
        add((int)ID.ShipClass.SSYO);
        add((int)ID.ShipClass.SSSO);
        add((int)ID.ShipClass.CVHime);
        add((int)ID.ShipClass.BBRU);
        add((int)ID.ShipClass.DDHime);
        add((int)ID.ShipClass.CAHime);
        add((int)ID.ShipClass.IsolatedHime);
        add((int)ID.ShipClass.MidwayHime);
        add((int)ID.ShipClass.SSHime);
        add((int)ID.ShipClass.SSNH);
    }});
    
    /**ENEMY LIST for guidebook
     * 
     * index by page number
     */
    public static final List<Integer> EnemyBookList = Collections.unmodifiableList(new ArrayList<Integer>()
    {{
        add((int)ID.ShipClass.DDShimakaze);
        add((int)ID.ShipClass.BBNagato);
        add((int)ID.ShipClass.SSU511);
        add((int)ID.ShipClass.SSRo500);
        add((int)ID.ShipClass.BBYamato);
        add((int)ID.ShipClass.CVKaga);
        add((int)ID.ShipClass.CVAkagi);
        add((int)ID.ShipClass.DDAkatsuki);
        add((int)ID.ShipClass.DDHibiki);
        add((int)ID.ShipClass.DDIkazuchi);
        add((int)ID.ShipClass.DDInazuma);
        add((int)ID.ShipClass.CLTenryuu);
        add((int)ID.ShipClass.CLTatsuta);
        add((int)ID.ShipClass.CAAtago);
        add((int)ID.ShipClass.CATakao);
        add((int)ID.ShipClass.BBKongou);
        add((int)ID.ShipClass.BBHiei);
        add((int)ID.ShipClass.BBHaruna);
        add((int)ID.ShipClass.BBKirishima);
        add((int)ID.ShipClass.APMamiya);
    }});
    
    
    /** SHIP TYPE ICON MAP
     * 
     *  map <ship id(short), int[]: 0:icon X, 1:icon Y>
     *  
     *  font: MS Mincho
     *  size: 12
     */
    public static final Map<Byte, int[]> ShipTypeIconMap = Collections.unmodifiableMap(new HashMap<Byte, int[]>()
    {{
        put(ID.ShipIconType.TRANSPORT,            new int[] {12, 74});
        put(ID.ShipIconType.DESTROYER,            new int[] {41, 0});
        put(ID.ShipIconType.LIGHT_CRUISER,        new int[] {41, 29});
        put(ID.ShipIconType.HEAVY_CRUISER,        new int[] {41, 58});
        put(ID.ShipIconType.TORPEDO_CRUISER,    new int[] {41, 87});
        put(ID.ShipIconType.BATTLESHIP,            new int[] {70, 0});
        put(ID.ShipIconType.STANDARD_CARRIER,    new int[] {70, 29});
        put(ID.ShipIconType.LIGHT_CARRIER,        new int[] {70, 58});
        put(ID.ShipIconType.HIME,                new int[] {70, 87});
        put(ID.ShipIconType.SUBMARINE,            new int[] {99, 0});
//        put(ID.ShipType.,    new int[] {99, 29});  //empty for now
        put(ID.ShipIconType.DEMON,                new int[] {99, 58});
//        put(ID.ShipType.,    new int[] {99, 87});  //empty for now
    }});
    
    
    /**damage modifier array [damage type id][target type id]
     * index by ID.ShipDmgType
     * CARRIER  AVIATION  BATTLESHIP  CRUISER  DESTROYER   SUBMARINE  AIRPLANE
     */
    public static final float[][] ModDmgDay =         //for day battle
        {{0.5F,  0.5F,  0.5F,  0.5F,  1F,    0F,    0.75F},
         {1F,    1F,    1F,    1.25F, 1.5F,  0.5F,  0.75F},
         {1.25F, 1.25F, 1F,    1.5F,  2F,    0F,    0.5F},
         {1F,    1F,    1F,    1F,    1.25F, 1.25F, 1F},
         {0.5F,  0.5F,  0.5F,  0.5F,  1F,    2F,    1.5F},
         {1.5F,  1.5F,  1.25F, 1.25F, 1.5F,  1.5F,  0F},
         {1.5F,  1.5F,  1.5F,  1.75F, 2F,    0.5F,  2F}
        };
    public static final float[][] ModDmgNight =     //for night battle
        {{0.25F, 0.25F, 0.25F, 0.25F, 0.5F,  0F,    0.5F},
         {0.75F, 0.75F, 0.75F, 1F,    1F,    0.25F, 0.5F},
         {1F,    1F,    0.75F, 1.25F, 1.75F, 0F,    0.25F},
         {0.75F, 0.75F, 0.75F, 0.75F, 1F,    1.25F, 0.75F},
         {0.5F,  0.5F,  0.5F,  0.5F,  0.75F, 1.75F, 1.25F},
         {1.5F,  1.5F,  1.25F, 1.25F, 1.5F,  1.5F,  0F},
         {0.5F,  0.5F,  0.5F,  0.75F, 0.75F, 0.25F, 1F}
        };
    
    
    /**
     * equip main attributes, index by {@link ID.Attrs}
     * 
     * EquipID = EquipType + EquipSubID * 100
     * 
     * note: 新增裝備要記得在LargeRecipe新增回收價格
     */
    public static final Map<Integer, float[]> EquipAttrsMain = Collections.unmodifiableMap(new HashMap<Integer, float[]>()
    {{
        //single cannon                                                                             HP   AL   AH   AAL  AAH  DEF   SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Dodge XP    GRUD  AMMO  HPRES KB
        put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_SINGLE_5 * 100,     new float[]{0F,  2F,  0F,  0F,  0F,  0F,   0.1F, -0.01F, 1F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.1F, 0F,   0F});
        put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_SINGLE_6 * 100,     new float[]{0F,  3F,  0F,  0F,  0F,  0F,   0.1F, -0.02F, 1F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.1F, 0F,   0F});
        put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_CG_5 * 100,         new float[]{30F, 4F,  0F,  0F,  0F,  0.04F,0.1F, -0.04F, 1F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.1F, 0F,   0F});
        //twin cannon                            
        put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_5 * 100,    new float[]{0F,  4F,  0F,  0F,  0F,  0F,   0.2F, -0.06F, 1F,   0F,    0.05F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
        put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_6 * 100,    new float[]{0F,  5F,  0F,  0F,  0F,  0F,   0.4F, -0.08F, 1F,   0F,    0.05F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
        put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_8 * 100,    new float[]{0F,  4F,  0F,  0F,  0F,  0F,   0.2F, -0.10F, 3F,   0F,    0.05F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
        put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_5DP * 100,  new float[]{0F,  5F,  0F,  0F,  0F,  0F,   0.25F,-0.12F, 1F,   0F,    0.06F, 0F,    0F,    20F, 0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
        put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_125 * 100,  new float[]{0F,  7F,  0F,  0F,  0F,  0F,   0.2F, -0.15F, 1.5F, 0F,    0.06F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
        put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_14 * 100,   new float[]{0F,  9F,  0F,  0F,  0F,  0F,   0.2F, -0.18F, 2F,   0F,    0.07F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
        put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_16 * 100,   new float[]{0F,  12F, 0F,  0F,  0F,  0F,   0.2F, -0.24F, 2F,   0F,    0.08F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
        put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_20 * 100,   new float[]{0F,  15F, 0F,  0F,  0F,  0F,   0.2F, -0.30F, 2.5F, 0F,    0.10F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
        //triple cannon
        put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_TRI_8 * 100,        new float[]{0F,  9F,  0F,  0F,  0F,  0F,   0.4F, -0.25F, 1.5F, 0F,    0.06F, 0.06F, 0F,    0F,  0F,  0F,   0F,   0F,   0.3F, 0F,   0F});
        put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_TRI_12 * 100,       new float[]{0F,  12F, 0F,  0F,  0F,  0F,   0.4F, -0.28F, 1.5F, 0F,    0.07F, 0.07F, 0F,    0F,  0F,  0F,   0F,   0F,   0.3F, 0F,   0F});
        put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_TRI_16 * 100,       new float[]{0F,  15F, 0F,  0F,  0F,  0F,   0.4F, -0.32F, 2F,   0F,    0.08F, 0.08F, 0F,    0F,  0F,  0F,   0F,   0F,   0.3F, 0F,   0F});
        put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_FG_15 * 100,        new float[]{140F,6F,  0F,  0F,  0F,  0.06F,0.2F, -0.32F, 1.5F, 0F,    0.04F, 0.04F, 0F,    0F,  0F,  0F,   0F,   0F,   0.3F, 0F,   0F});
        put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_QUAD_15 * 100,      new float[]{0F,  18F, 0F,  0F,  0F,  0F,   0.6F, -0.4F,  2F,   0F,    0.1F,  0.1F,  0F,    0F,  0F,  0F,   0F,   0F,   0.4F, 0F,   0F});
        //machine gun
        put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_HA_3 * 100,                  new float[]{0F,  1F,  0F,  0F,  0F,  0F,   0F,   -0.02F, 0F,   0F,    0F,    0F,    0F,    8F,  0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_HA_5 * 100,                  new float[]{0F,  2F,  0F,  0F,  0F,  0F,   0F,   -0.02F, 0F,   0F,    0F,    0F,    0F,    12F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_SINGLE_12 * 100,             new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   -0.03F, 0F,   0F,    0F,    0F,    0F,    18F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_SINGLE_20 * 100,          new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   -0.04F, 0F,   0F,    0F,    0F,    0F,    25F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_TWIN_40 * 100,            new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   -0.05F, 0F,   0.01F, 0F,    0F,    0F,    35F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_QUAD_40 * 100,            new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   -0.08F, 0F,   0.02F, 0F,    0F,    0F,    48F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_TWIN_4_CIC * 100,         new float[]{0F,  3F,  0F,  0F,  0F,  0F,   0F,   -0.10F, 2F,   0.04F, 0F,    0F,    0.06F, 65F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
        //torpedo                                                                                   HP   LA   HA   LAA  HAA  DEF   SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Dodge XP    GRUD  AMMO  HPRES KB      
        put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_21MK1 * 100,      new float[]{0F,  0F,  6F,  0F,  0F,  0F,   0F,   -0.08F, 0F,   0.03F, 0F,    0F,    0F,    0F,  8F,  0F,   0F,   0F,   0.1F, 0F,   0F});
        put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_21MK2 * 100,      new float[]{0F,  0F,  12F, 0F,  0F,  0F,   0F,   -0.12F, 0.5F, 0.05F, 0F,    0F,    0F,    0F,  12F, 0F,   0F,   0F,   0.1F, 0F,   0F});
        put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_22MK1 * 100,      new float[]{0F,  0F,  18F, 0F,  0F,  0F,   0.1F, -0.16F, 0.5F, 0.07F, 0F,    0F,    0F,    0F,  18F, 0F,   0F,   0F,   0.15F,0F,   0F});
        put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_CUTTLEFISH * 100, new float[]{0F,  0F,  25F, 0F,  0F,  0F,   0.1F, -0.20F, 1F,   0.09F, 0F,    0F,    0F,    0F,  24F, 0F,   0F,   0F,   0.2F, 0F,   0F});
        put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_HIGHSPEED * 100,  new float[]{0F,  0F,  32F, 0F,  0F,  0F,   0.14F,-0.25F, 1.3F, 0.11F, 0F,    0F,    0F,    0F,  32F, 0F,   0F,   0F,   0.25F,0F,   0F});
        put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_HIGHSPEED2 * 100, new float[]{0F,  0F,  40F, 0F,  0F,  0F,   0.18F,-0.30F, 1.6F, 0.15F, 0F,    0F,    0.06F, 0F,  45F, 0F,   0F,   0F,   0.3F, 0F,   0F});
        put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_AMB * 100,        new float[]{0F,  0F,  48F, 0F,  0F,  0F,   0.2F, -0.35F, 2F,   0.2F,  0F,    0F,    0.06F, 0F,  60F, 0F,   0F,   0F,   0.35F,0F,   0F});
        //Torpedo aircraft
        put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK1 * 100,          new float[]{0F,  0F,  0F,  2F,  14F, 0F,   0F,   -0.10F, 2F,   0F,    0F,    0F,    0F,    3F,  12F, 0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK2 * 100,          new float[]{0F,  0F,  0F,  5F,  20F, 0F,   0F,   -0.14F, 2F,   0F,    0F,    0F,    0F,    6F,  18F, 0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK3 * 100,          new float[]{0F,  0F,  0F,  7F,  28F, 0F,   0F,   -0.18F, 2.5F, 0F,    0F,    0F,    0F,    9F,  24F, 0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_T_HI + (int)ID.EquipSubID.AIRCRAFT_TAVENGER * 100,      new float[]{0F,  0F,  0F,  5F,  38F, 0F,   0.05F,-0.25F, 2.5F, 0F,    0F,    0F,    0F,    15F, 30F, 0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_T_HI + (int)ID.EquipSubID.AIRCRAFT_TAVENGERK * 100,     new float[]{0F,  0F,  0F,  8F,  50F, 0F,   0.08F,-0.30F, 3F,   0F,    0F,    0F,    0F,    20F, 45F, 0F,   0F,   0F,   0F,   0F,   0F});
        //Fighter aircraft
        put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK1 * 100,          new float[]{0F,  0F,  0F,  6F,  0F,  0F,   0F,   -0.10F, 4F,   0F,    0F,    0F,    0F,    20F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK2 * 100,          new float[]{0F,  0F,  0F,  8F,  0F,  0F,   0F,   -0.14F, 4F,   0F,    0F,    0F,    0F,    35F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK3 * 100,          new float[]{0F,  0F,  0F,  10F, 0F,  0F,   0.05F,-0.18F, 4.5F, 0F,    0F,    0F,    0F,    50F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FFLYFISH * 100,      new float[]{0F,  0F,  0F,  12F, 0F,  0F,   0.05F,-0.22F, 4.5F, 0F,    0F,    0F,    0.01F, 65F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCAT * 100,      new float[]{0F,  0F,  0F,  16F, 0F,  0F,   0.08F,-0.24F, 5F,   0F,    0F,    0F,    0.02F, 80F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCATB * 100,     new float[]{0F,  0F,  0F,  8F,  24F, 0F,   0.08F,-0.30F, 5F,   0.1F,  0F,    0F,    0.04F, 50F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCATK * 100,     new float[]{0F,  0F,  0F,  20F, 0F,  0F,   0.12F,-0.28F, 5F,   0F,    0F,    0F,    0.04F, 100F,0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FBC * 100,         new float[]{0F,  0F,  0F,  24F, 0F,  0F,   0.12F,-0.32F, 5.5F, 0F,    0F,    0F,    0.04F, 120F,0F,  0F,   0F,   0F,   0F,   0F,   0F});
        //Bomber aircraft                                                                           HP   LA   HA   LAA  HAA  DEF   SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Dodge XP    GRUD  AMMO  HPRES KB      
        put((int)ID.EquipType.AIR_B_LO + (int)ID.EquipSubID.AIRCRAFT_BMK1 * 100,          new float[]{0F,  0F,  0F,  2F,  8F,  0F,   0F,   -0.14F, 1.5F, 0.04F, 0F,    0F,    0F,    2F,  2F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_B_LO + (int)ID.EquipSubID.AIRCRAFT_BMK2 * 100,          new float[]{0F,  0F,  0F,  5F,  10F, 0F,   0F,   -0.18F, 2F,   0.05F, 0F,    0F,    0F,    3F,  4F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BFLYFISH * 100,      new float[]{0F,  0F,  0F,  7F,  12F, 0F,   0F,   -0.22F, 2F,   0.06F, 0F,    0F,    0F,    4F,  8F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BHELL * 100,         new float[]{0F,  0F,  0F,  5F,  14F, 0F,   0F,   -0.25F, 2.5F, 0.08F, 0F,    0F,    0F,    6F,  12F, 0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BHELLK * 100,        new float[]{0F,  0F,  0F,  7F,  16F, 0F,   0.05F,-0.30F, 2.5F, 0.12F, 0F,    0F,    0F,    8F,  16F, 0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BLAND * 100,        new float[]{0F,  0F,  0F,  8F,  18F, 0F,   0.06F,-0.35F, 3F,   0.14F, 0F,    0F,    0F,    10F, 20F, 0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BLANDA * 100,        new float[]{0F,  0F,  0F,  9F,  20F, 0F,   0.08F,-0.38F, 3.5F, 0.16F, 0F,    0F,    0F,    12F, 24F, 0F,   0F,   0F,   0F,   0F,   0F});
        //Recon aircraft
        put((int)ID.EquipType.AIR_R_LO + (int)ID.EquipSubID.AIRCRAFT_R * 100,             new float[]{0F,  1F,  1F,  1F,  1F,  0.03F,0.02F,-0.10F, 3.5F, 0.03F, 0F,    0F,    0.10F, 8F,  4F,  0.08F,0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AIR_R_HI + (int)ID.EquipSubID.AIRCRAFT_RFLYFISH * 100,      new float[]{0F,  2F,  2F,  2F,  2F,  0.06F,0.04F,-0.16F, 5.5F, 0.05F, 0F,    0F,    0.15F, 12F, 8F,  0.15F,0F,   0F,   0F,   0F,   0F});
        //radar
        put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_AIRMK1 * 100,           new float[]{0F,  2F,  0F,  3F,  0F,  0.03F,0F,   -0.08F, 1F,   0F,    0F,    0F,    0.05F, 12F, 0F,  0.02F,0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_AIRMK2 * 100,           new float[]{0F,  3F,  0F,  4F,  0F,  0.06F,0F,   -0.10F, 2F,   0.02F, 0F,    0F,    0.10F, 18F, 0F,  0.02F,0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_AIRABYSS * 100,         new float[]{0F,  4F,  0F,  6F,  0F,  0.10F,0F,   -0.12F, 2F,   0.03F, 0F,    0F,    0.15F, 24F, 0F,  0.04F,0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SURMK1 * 100,           new float[]{0F,  2F,  3F,  3F,  4F,  0.03F,0F,   -0.08F, 1F,   0F,    0F,    0F,    0.05F, 0F,  0F,  0.02F,0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SURMK2 * 100,           new float[]{0F,  3F,  4F,  4F,  5F,  0.06F,0F,   -0.10F, 2F,   0.02F, 0F,    0F,    0.10F, 0F,  0F,  0.02F,0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_SURABYSS * 100,         new float[]{0F,  3F,  5F,  6F,  8F,  0.10F,0F,   -0.12F, 2F,   0.03F, 0F,    0F,    0.15F, 0F,  0F,  0.04F,0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SONAR * 100,            new float[]{0F,  0F,  6F,  0F,  6F,  0.03F,0F,   -0.06F, 1F,   0.02F, 0F,    0F,    0.05F, 0F,  24F, 0.03F,0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_SONARMK2 * 100,         new float[]{0F,  0F,  9F,  0F,  9F,  0.06F,0F,   -0.08F, 1F,   0.04F, 0F,    0F,    0.10F, 0F,  32F, 0.04F,0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_FCSCIC * 100,           new float[]{0F,  4F,  6F,  8F,  10F, 0.12F,0F,   -0.12F, 3F,   0.06F, 0F,    0F,    0.18F, 24F, 24F, 0.08F,0F,   0F,   0F,   0F,   0F});
        //turbine                                                                                   HP   LA   HA   LAA  HAA  DEF   SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Dodge XP    GRUD  AMMO  HPRES KB      
        put((int)ID.EquipType.TURBINE_LO + (int)ID.EquipSubID.TURBINE * 100,            new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   0.12F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.06F,0F,   0.2F, 0F,   0F,   0F});     
        put((int)ID.EquipType.TURBINE_LO + (int)ID.EquipSubID.TURBINE_IMP * 100,        new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   0.16F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.08F,0F,   0.3F, 0F,   0F,   0F});
        put((int)ID.EquipType.TURBINE_HI + (int)ID.EquipSubID.TURBINE_ENH * 100,        new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   0.20F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.10F,0F,   0.4F, 0F,   0F,   0F});
        put((int)ID.EquipType.TURBINE_HI + (int)ID.EquipSubID.TURBINE_GE * 100,         new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   0.24F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.12F,0F,   0.5F, 0F,   0F,   0F});
        put((int)ID.EquipType.TURBINE_HI + (int)ID.EquipSubID.TURBINE_GENEW * 100,      new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   0.28F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.14F,0F,   0.6F, 0F,   0F,   0F});
        //armor                                                                                                                                                                                                       
        put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR * 100,                  new float[]{80F, 0F,  0F,  0F,  0F,  0.12F,0F,   -0.16F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0.1F});
        put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR_ATBS * 100,              new float[]{180F,0F,  0F,  0F,  0F,  0.02F,0F,   -0.12F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.04F,0F,   0F,   0F,   0.1F, 0.1F});
        put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR_ATBM * 100,              new float[]{260F,0F,  0F,  0F,  0F,  0.03F,0F,   -0.14F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.06F,0F,   0F,   0F,   0.15F,0.12F});
        put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR_ATBA * 100,              new float[]{100F,0F,  0F,  0F,  0F,  0.16F,0F,   -0.22F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0.1F, 0.2F});
        put((int)ID.EquipType.ARMOR_HI + (int)ID.EquipSubID.ARMOR_ATBL * 100,              new float[]{340F,0F,  0F,  0F,  0F,  0.04F,0F,   -0.18F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.08F,0F,   0F,   0F,   0.3F, 0.15F});
        put((int)ID.EquipType.ARMOR_HI + (int)ID.EquipSubID.ARMOR_ENH * 100,              new float[]{100F,0F,  0F,  0F,  0F,  0.20F,0F,   -0.28F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0.25F});
        put((int)ID.EquipType.ARMOR_HI + (int)ID.EquipSubID.ARMOR_APB * 100,              new float[]{120F,0F,  0F,  0F,  0F,  0.24F,0F,   -0.35F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0.3F});
        //catapult                                                                                                                                                                                                    
        put((int)ID.EquipType.CATAPULT_LO + (int)ID.EquipSubID.CATAPULT_F * 100,        new float[]{0,   0F,  0F,  0F,  0F,  0F,   0.4F, -0.15F, 2F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0.08F});
        put((int)ID.EquipType.CATAPULT_LO + (int)ID.EquipSubID.CATAPULT_H * 100,        new float[]{0,   0F,  0F,  0F,  0F,  0F,   0.8F, -0.22F, 4F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0.1F});
        put((int)ID.EquipType.CATAPULT_HI + (int)ID.EquipSubID.CATAPULT_C * 100,        new float[]{0,   0F,  0F,  0F,  0F,  0F,   1.2F, -0.30F, 6F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0.15F});
        put((int)ID.EquipType.CATAPULT_HI + (int)ID.EquipSubID.CATAPULT_E * 100,        new float[]{100F,0F,  0F,  0F,  0F,  0.1F, 2.2F, -0.40F, 8F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0.25F,0F,   0.5F, 0.2F});
        //drum                                                                                                                                                                                                        
        put((int)ID.EquipType.DRUM_LO + (int)ID.EquipSubID.DRUM * 100,                    new float[]{0,   0F,  0F,  0F,  0F,  0F,   0F,   -0.25F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0.5F, 0F,   0F,   0.15F,0F});
        put((int)ID.EquipType.DRUM_LO + (int)ID.EquipSubID.DRUM_F * 100,                new float[]{0,   0F,  0F,  0F,  0F,  0F,   0F,   -0.25F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0.5F, 0F,   0F,   0.15F,0F});
        put((int)ID.EquipType.DRUM_LO + (int)ID.EquipSubID.DRUM_E * 100,                new float[]{0,   0F,  0F,  0F,  0F,  0F,   0F,   -0.25F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0.5F, 0F,   0F,   0.15F,0F});
        //compass                                                                                                                                                                            
        put((int)ID.EquipType.COMPASS_LO + (int)ID.EquipSubID.COMPASS * 100,            new float[]{0,   0F,  0F,  0F,  0F,  0F,   0F,   -0.01F, 2F,   0F,    0F,    0F,    0.05F, 0F,  0F,  0F,   0.5F, 0F,   0F,   0F,   0F});
        //flare                                                                                                                                                                              
        put((int)ID.EquipType.FLARE_LO + (int)ID.EquipSubID.FLARE * 100,                new float[]{0,   1F,  0F,  2F,  0F,  0F,   0F,   -0.02F, 2F,   0.04F, 0F,    0F,    0.05F, 0F,  5F,  0F,   0F,   0F,   0F,   0F,   0F}); 
        //searchlight                                                                               HP   LA   HA   LAA  HAA  DEF   SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Dodge XP    GRUD  AMMO  HPRES KB      
        put((int)ID.EquipType.SEARCHLIGHT_LO + (int)ID.EquipSubID.SEARCHLIGHT * 100,    new float[]{0,   0F,  0F,  0F,  0F,  -0.06F,0F,  -0.02F, 2F,   0.06F, 0F,    0F,    0.1F,  0F,  10F, -0.10F,0F,  0F,   0F,   0F,   0F});
        //ammo
        put((int)ID.EquipType.AMMO_LO + (int)ID.EquipSubID.AMMO_T91 * 100,              new float[]{0F,  6F,  0F,  6F,  0F,  0F,   0F,   -0.02F, 2F,   0.03F, 0.02F, 0.02F, 0.02F, 0F,  0F,  0F,   0F,   0F,   0.1F, 0F,   0F});
        put((int)ID.EquipType.AMMO_LO + (int)ID.EquipSubID.AMMO_AA * 100,                new float[]{0F,  2F,  0F,  2F,  0F,  0F,   0F,   -0.02F, 4F,   0F,    0F,    0F,    0.06F, 20F, 0F,  0F,   0F,   0F,   0.1F, 0F,   0F});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_T1 * 100,                  new float[]{0F,  8F,  0F,  8F,  0F,  0F,   0F,   -0.04F, 2F,   0.06F, 0.04F, 0.04F, 0.04F, 0F,  0F,  0F,   0F,   0F,   0.15F,0F,   0F});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_T3 * 100,                new float[]{0F,  4F,  0F,  4F,  0F,  0F,   0F,   -0.04F, 4F,   0F,    0F,    0F,    0.1F,  60F, 0F,  0F,   0F,   0F,   0.15F,0F,   0F});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_DU * 100,                  new float[]{0F,  12F, 0F,  12F, 0F,  0F,   0F,   -0.06F, 3F,   0.09F, 0.06F, 0.06F, 0.06F, 0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_G * 100,                new float[]{0F,  0F,  8F,  0F,  8F,  0F,   -0.2F,-0.06F, -2F,  0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_AG * 100,                  new float[]{0F,  2F,  0F,  2F,  0F,  0F,   -0.1F,-0.02F, 2F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_E * 100,                new float[]{0F,  0F,  0F,  0F,  0F,  0F,   -0.2F,-0.01F, -2F,  0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0F});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_C * 100,                new float[]{0F,  0F,  16F, 0F,  16F, 0F,   -0.3F,-0.01F, -2F,  0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0F});
    }});
    
    /**
     * equip misc attributes, index by {@link ID.EquipMisc}
     * 
     * EquipID = EquipType + EquipSubID * 100
     * 
     * equip type:
     *        0:unused
     *     1:cannon, torpedo
     *     2:aircraft-R, engine, armor, radar, ammo
     *     3:aircraft-T/F/B
     */
    public static final Map<Integer, int[]> EquipAttrsMisc = Collections.unmodifiableMap(new HashMap<Integer, int[]>()
    {{
        //single cannon                                                                           Type Rare Type/Mean                     MatsType, EnchType
        put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_SINGLE_5 * 100,     new int[]{1,   ID.EquipType.CANNON_SI,      1000, 128,  2,  1});
        put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_SINGLE_6 * 100,     new int[]{1,   ID.EquipType.CANNON_SI,      3000, 128,  2,  1});
        put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_CG_5 * 100,         new int[]{1,   ID.EquipType.CANNON_SI,      4000, 128,  2,  1});
        //twin cannon                            
        put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_5 * 100,    new int[]{1,   ID.EquipType.CANNON_TW_LO,   1000, 320,  2,  1});
        put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_6 * 100,    new int[]{1,   ID.EquipType.CANNON_TW_LO,   2000, 320,  2,  1});
        put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_8 * 100,    new int[]{1,   ID.EquipType.CANNON_TW_LO,   3000, 320,  2,  1});
        put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_5DP * 100,  new int[]{1,   ID.EquipType.CANNON_TW_LO,   3200, 320,  2,  1});
        put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_125 * 100,  new int[]{1,   ID.EquipType.CANNON_TW_LO,   4000, 320,  2,  1});
        put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_14 * 100,   new int[]{1,   ID.EquipType.CANNON_TW_HI,   1000, 1600, 2,  1});
        put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_16 * 100,   new int[]{1,   ID.EquipType.CANNON_TW_HI,   2400, 1600, 2,  1});
        put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_20 * 100,   new int[]{1,   ID.EquipType.CANNON_TW_HI,   4000, 1600, 2,  1});
        //triple cannon
        put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_TRI_8 * 100,        new int[]{1,   ID.EquipType.CANNON_TR,      1000, 4400, 2,  1});
        put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_TRI_12 * 100,       new int[]{1,   ID.EquipType.CANNON_TR,      2200, 4400, 2,  1});
        put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_TRI_16 * 100,       new int[]{1,   ID.EquipType.CANNON_TR,      3500, 4400, 2,  1});
        put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_FG_15 * 100,        new int[]{1,   ID.EquipType.CANNON_TR,      2500, 4400, 2,  1});
        put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_QUAD_15 * 100,      new int[]{1,   ID.EquipType.CANNON_TR,      4000, 4400, 2,  1});
        //machine gun
        put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_HA_3 * 100,                  new int[]{2,   ID.EquipType.GUN_LO,         1000, 100,  2,  1});
        put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_HA_5 * 100,                  new int[]{2,   ID.EquipType.GUN_LO,         2000, 100,  2,  1});
        put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_SINGLE_12 * 100,             new int[]{2,   ID.EquipType.GUN_LO,         3200, 100,  2,  1});
        put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_SINGLE_20 * 100,          new int[]{2,   ID.EquipType.GUN_LO,         4000, 100,  2,  1});
        put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_TWIN_40 * 100,            new int[]{2,   ID.EquipType.GUN_HI,         1000, 800,  2,  1});
        put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_QUAD_40 * 100,            new int[]{2,   ID.EquipType.GUN_HI,         2400, 800,  2,  1});
        put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_TWIN_4_CIC * 100,         new int[]{2,   ID.EquipType.GUN_HI,         4000, 800,  2,  1});
        //torpedo                                                                                 Type Rare Type/Mean                     MatsType, EnchType
        put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_21MK1 * 100,      new int[]{1,   ID.EquipType.TORPEDO_LO,     1000, 160,  2,  1});
        put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_21MK2 * 100,      new int[]{1,   ID.EquipType.TORPEDO_LO,     2400, 160,  2,  1});
        put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_22MK1 * 100,      new int[]{1,   ID.EquipType.TORPEDO_LO,     4000, 160,  2,  1});
        put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_CUTTLEFISH * 100, new int[]{1,   ID.EquipType.TORPEDO_HI,     1000, 1200, 2,  1});
        put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_HIGHSPEED * 100,  new int[]{1,   ID.EquipType.TORPEDO_HI,     2500, 1200, 2,  1});
        put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_HIGHSPEED2 * 100, new int[]{1,   ID.EquipType.TORPEDO_HI,     3600, 1200, 2,  1});
        put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_AMB * 100,        new int[]{1,   ID.EquipType.TORPEDO_HI,     4000, 1200, 2,  1});
        //Torpedo aircraft
        put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK1 * 100,          new int[]{3,   ID.EquipType.AIR_T_LO,       1000, 2400, 3,  1});
        put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK2 * 100,          new int[]{3,   ID.EquipType.AIR_T_LO,       2400, 2400, 3,  1});
        put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK3 * 100,          new int[]{3,   ID.EquipType.AIR_T_LO,       4000, 2400, 3,  1});
        put((int)ID.EquipType.AIR_T_HI + (int)ID.EquipSubID.AIRCRAFT_TAVENGER * 100,      new int[]{3,   ID.EquipType.AIR_T_HI,       1000, 3800, 3,  1});
        put((int)ID.EquipType.AIR_T_HI + (int)ID.EquipSubID.AIRCRAFT_TAVENGERK * 100,     new int[]{3,   ID.EquipType.AIR_T_HI,       4000, 3800, 3,  1});
        //Fighter aircraft
        put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK1 * 100,          new int[]{3,   ID.EquipType.AIR_F_LO,       1000, 2400, 3,  1});
        put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK2 * 100,          new int[]{3,   ID.EquipType.AIR_F_LO,       2400, 2400, 3,  1});
        put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK3 * 100,          new int[]{3,   ID.EquipType.AIR_F_LO,       4000, 2400, 3,  1});
        put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FFLYFISH * 100,      new int[]{3,   ID.EquipType.AIR_F_HI,       1000, 3800, 3,  1});
        put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCAT * 100,      new int[]{3,   ID.EquipType.AIR_F_HI,       2400, 3800, 3,  1});
        put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCATB * 100,     new int[]{3,   ID.EquipType.AIR_F_HI,       2900, 3800, 3,  1});
        put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCATK * 100,     new int[]{3,   ID.EquipType.AIR_F_HI,       3400, 3800, 3,  1});
        put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FBC * 100,         new int[]{3,   ID.EquipType.AIR_F_HI,       4000, 3800, 3,  1});
        //Bomber aircraft                                                                         Type Rare Type/Mean                     MatsType, EnchType
        put((int)ID.EquipType.AIR_B_LO + (int)ID.EquipSubID.AIRCRAFT_BMK1 * 100,          new int[]{3,   ID.EquipType.AIR_B_LO,       1000, 2400, 3,  1});
        put((int)ID.EquipType.AIR_B_LO + (int)ID.EquipSubID.AIRCRAFT_BMK2 * 100,          new int[]{3,   ID.EquipType.AIR_B_LO,       4000, 2400, 3,  1});
        put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BFLYFISH * 100,      new int[]{3,   ID.EquipType.AIR_B_HI,       1000, 3800, 3,  1});
        put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BHELL * 100,         new int[]{3,   ID.EquipType.AIR_B_HI,       2400, 3800, 3,  1});
        put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BHELLK * 100,        new int[]{3,   ID.EquipType.AIR_B_HI,       3200, 3800, 3,  1});
        put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BLAND * 100,        new int[]{3,   ID.EquipType.AIR_B_HI,       3500, 3800, 3,  1});
        put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BLANDA * 100,        new int[]{3,   ID.EquipType.AIR_B_HI,       4000, 3800, 3,  1});
        //Recon aircraft
        put((int)ID.EquipType.AIR_R_LO + (int)ID.EquipSubID.AIRCRAFT_R * 100,             new int[]{2,   ID.EquipType.AIR_R_LO,       200,  256,  3,  1});
        put((int)ID.EquipType.AIR_R_HI + (int)ID.EquipSubID.AIRCRAFT_RFLYFISH * 100,      new int[]{2,   ID.EquipType.AIR_R_HI,       200,  1000, 3,  1});
        //radar
        put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_AIRMK1 * 100,           new int[]{2,   ID.EquipType.RADAR_LO,       1000, 200,  0,  3});
        put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_AIRMK2 * 100,           new int[]{2,   ID.EquipType.RADAR_LO,       1800, 200,  0,  3});
        put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_AIRABYSS * 100,         new int[]{2,   ID.EquipType.RADAR_HI,       1000, 2000, 0,  3});
        put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SURMK1 * 100,           new int[]{2,   ID.EquipType.RADAR_LO,       2600, 200,  0,  3});
        put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SURMK2 * 100,           new int[]{2,   ID.EquipType.RADAR_LO,       3400, 200,  0,  3});
        put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_SURABYSS * 100,         new int[]{2,   ID.EquipType.RADAR_HI,       2000, 2000, 0,  3});    
        put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SONAR * 100,            new int[]{2,   ID.EquipType.RADAR_LO,       4000, 200,  0,  3});
        put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_SONARMK2 * 100,         new int[]{2,   ID.EquipType.RADAR_HI,       3200, 2000, 0,  3});
        put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_FCSCIC * 100,           new int[]{2,   ID.EquipType.RADAR_HI,       4000, 2000, 0,  3});
        //turbine                                                                                 Type Rare Type/Mean                     MatsType, EnchType
        put((int)ID.EquipType.TURBINE_LO + (int)ID.EquipSubID.TURBINE * 100,            new int[]{2,   ID.EquipType.TURBINE_LO,     1000, 1400, 0,  3});
        put((int)ID.EquipType.TURBINE_LO + (int)ID.EquipSubID.TURBINE_IMP * 100,        new int[]{2,   ID.EquipType.TURBINE_LO,     4000, 1400, 0,  3});
        put((int)ID.EquipType.TURBINE_HI + (int)ID.EquipSubID.TURBINE_ENH * 100,        new int[]{2,   ID.EquipType.TURBINE_HI,     2000, 3200, 0,  3});
        put((int)ID.EquipType.TURBINE_HI + (int)ID.EquipSubID.TURBINE_GE * 100,         new int[]{2,   ID.EquipType.TURBINE_HI,     3000, 3200, 0,  3});
        put((int)ID.EquipType.TURBINE_HI + (int)ID.EquipSubID.TURBINE_GENEW * 100,      new int[]{2,   ID.EquipType.TURBINE_HI,     4400, 3200, 0,  3});
        //armor
        put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR * 100,                  new int[]{2,   ID.EquipType.ARMOR_LO,       1000, 80,   1,  2});
        put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR_ATBS * 100,              new int[]{2,   ID.EquipType.ARMOR_LO,       2000, 80,   1,  2});
        put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR_ATBM * 100,              new int[]{2,   ID.EquipType.ARMOR_LO,       3000, 80,   1,  2});
        put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR_ATBA * 100,              new int[]{2,   ID.EquipType.ARMOR_LO,       4000, 80,   1,  2});
        put((int)ID.EquipType.ARMOR_HI + (int)ID.EquipSubID.ARMOR_ATBL * 100,              new int[]{2,   ID.EquipType.ARMOR_HI,       1000, 500,  1,  2});
        put((int)ID.EquipType.ARMOR_HI + (int)ID.EquipSubID.ARMOR_ENH * 100,              new int[]{2,   ID.EquipType.ARMOR_HI,       2000, 500,  1,  2});
        put((int)ID.EquipType.ARMOR_HI + (int)ID.EquipSubID.ARMOR_APB * 100,              new int[]{2,   ID.EquipType.ARMOR_HI,       4000, 500,  1,  2});
        //catapult
        put((int)ID.EquipType.CATAPULT_LO + (int)ID.EquipSubID.CATAPULT_F * 100,        new int[]{3,   ID.EquipType.CATAPULT_LO,    1000, 2800, 3,  3});
        put((int)ID.EquipType.CATAPULT_LO + (int)ID.EquipSubID.CATAPULT_H * 100,        new int[]{3,   ID.EquipType.CATAPULT_LO,    2000, 2800, 3,  3});
        put((int)ID.EquipType.CATAPULT_HI + (int)ID.EquipSubID.CATAPULT_C * 100,        new int[]{3,   ID.EquipType.CATAPULT_HI,    3200, 5000, 3,  3});
        put((int)ID.EquipType.CATAPULT_HI + (int)ID.EquipSubID.CATAPULT_E * 100,        new int[]{3,   ID.EquipType.CATAPULT_HI,    4400, 5000, 3,  3});
        //drum
        put((int)ID.EquipType.DRUM_LO + (int)ID.EquipSubID.DRUM * 100,                    new int[]{2,   ID.EquipType.DRUM_LO,          2000, 120,  1,  3});
        put((int)ID.EquipType.DRUM_LO + (int)ID.EquipSubID.DRUM_F * 100,                new int[]{2,   ID.EquipType.DRUM_LO,          2500, 120,  1,  3});
        put((int)ID.EquipType.DRUM_LO + (int)ID.EquipSubID.DRUM_E * 100,                new int[]{2,   ID.EquipType.DRUM_LO,          2500, 120,  1,  3});
        //compass
        put((int)ID.EquipType.COMPASS_LO + (int)ID.EquipSubID.COMPASS * 100,            new int[]{2,   ID.EquipType.COMPASS_LO,     2000, 90,   0,  3});
        //flare
        put((int)ID.EquipType.FLARE_LO + (int)ID.EquipSubID.FLARE * 100,                new int[]{1,   ID.EquipType.FLARE_LO,       2000, 80,   2,  3});
        //searchlight                                                                             Type Rare Type/Mean                     MatsType, EnchType
        put((int)ID.EquipType.SEARCHLIGHT_LO + (int)ID.EquipSubID.SEARCHLIGHT * 100,    new int[]{2,   ID.EquipType.SEARCHLIGHT_LO, 2000, 80,   0,  3});
        //ammo
        put((int)ID.EquipType.AMMO_LO + (int)ID.EquipSubID.AMMO_T91 * 100,              new int[]{2,   ID.EquipType.AMMO_LO,        2000, 120,  2,  1});
        put((int)ID.EquipType.AMMO_LO + (int)ID.EquipSubID.AMMO_AA * 100,                new int[]{2,   ID.EquipType.AMMO_LO,        2000, 120,  2,  1});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_T1 * 100,                  new int[]{2,   ID.EquipType.AMMO_HI,        1000, 1000, 2,  1});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_T3 * 100,                new int[]{2,   ID.EquipType.AMMO_HI,        2000, 1000, 2,  1});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_DU * 100,                  new int[]{2,   ID.EquipType.AMMO_HI,        4500, 1000, 2,  1});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_G * 100,                new int[]{2,   ID.EquipType.AMMO_HI,        3000, 1000, 2,  1});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_AG * 100,                  new int[]{2,   ID.EquipType.AMMO_HI,        3000, 1000, 2,  1});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_E * 100,                new int[]{2,   ID.EquipType.AMMO_HI,        3500, 1000, 2,  1});
        put((int)ID.EquipType.AMMO_HI + (int)ID.EquipSubID.AMMO_C * 100,                new int[]{2,   ID.EquipType.AMMO_HI,        4000, 1000, 2,  1});
    }});

    
    /** FORMATION BUFFS MAP
     * 
     *  index: {@link ID.Attrs}
     *  put id: formatID * 10 + slotID
     *  
     *  ex: formation 2, slot 4 = 24
     *  
     *  multiplication:
     *    ATK, DEF, SPD, CRI, DHIT, THIT, MISS, AA, ASM 
     *  addition:
     *    HP, MOV, HIT, DODGE, XP, GRUDGE, AMMO, HPRES, KB
     *  no effect by default:
     *    HP, XP, AMMO
     */
    public static final Map<Integer, float[]> FormationAttrs = Collections.unmodifiableMap(new HashMap<Integer, float[]>()
    {{
        //Line Ahead          HP, ATK_L  ATK_H  ATK_AL ATK_AH DEF    SPD,   MOV    HIT, CRI    DHIT   THIT   MISS   AA     ASM    DODGE   XP   GRU    AMMO HPRES KB
        put(10,  new float[] {0F, 2F,    2F,    1.2F,  1.2F,  0.3F,  1.3F,  0.08F, 4F,  1.75F, 1.75F, 1.75F, 1.25F, 0.5F,  0.4F,  0.1F,   0F,  0.2F,  0F,  0F,   0F});
        put(11,  new float[] {0F, 1.75F, 1.75F, 1.2F,  1.2F,  0.4F,  1.3F,  0.08F, 4F,  1.55F, 1.55F, 1.55F, 1.2F,  0.5F,  0.4F,  0.1F,   0F,  0.2F,  0F,  0F,   0F});
        put(12,  new float[] {0F, 1.55F, 1.55F, 1.15F, 1.15F, 0.5F,  1.2F,  0.08F, 3F,  1.4F,  1.4F,  1.4F,  1.2F,  0.5F,  0.4F,  0.1F,   0F,  0.2F,  0F,  0F,   0F});
        put(13,  new float[] {0F, 1.4F,  1.4F,  1.15F, 1.15F, 0.6F,  1.2F,  0.08F, 3F,  1.3F,  1.3F,  1.3F,  1.15F, 0.5F,  0.4F,  0.1F,   0F,  0.2F,  0F,  0F,   0F});
        put(14,  new float[] {0F, 1.3F,  1.3F,  1.1F,  1.1F,  0.7F,  1.1F,  0.08F, 2F,  1.2F,  1.2F,  1.2F,  1.15F, 0.5F,  0.4F,  0.1F,   0F,  0.2F,  0F,  0F,   0F});
        put(15,  new float[] {0F, 1.25F, 1.25F, 1.1F,  1.1F,  0.8F,  1.1F,  0.08F, 2F,  1.1F,  1.1F,  1.1F,  1.1F,  0.5F,  0.4F,  0.1F,   0F,  0.2F,  0F,  0F,   0F});
        //Double Line
        put(20,  new float[] {0F, 1.4F,  1.4F,  1.1F,  1.1F,  0.9F,  1.08F, 0F,    2F,  1.15F, 1.15F, 1.15F, 1.55F, 1.2F,  1F,    -0.15F, 0F,  0.1F,  0F,  0.3F, 0.05F});
        put(21,  new float[] {0F, 1.4F,  1.4F,  1.1F,  1.1F,  0.9F,  1.08F, 0F,    2F,  1.15F, 1.15F, 1.15F, 1.55F, 1.2F,  1F,    -0.15F, 0F,  0.1F,  0F,  0.3F, 0.05F});
        put(22,  new float[] {0F, 1.5F,  1.5F,  1.15F, 1.15F, 0.75F, 1.15F, 0F,    3F,  1.3F,  1.3F,  1.3F,  1.75F, 1.1F,  1F,    -0.05F, 0F,  0.1F,  0F,  0.1F, 0.05F});
        put(23,  new float[] {0F, 1.5F,  1.5F,  1.15F, 1.15F, 0.75F, 1.15F, 0F,    3F,  1.3F,  1.3F,  1.3F,  1.75F, 1.1F,  1F,    -0.05F, 0F,  0.1F,  0F,  0.1F, 0.05F});
        put(24,  new float[] {0F, 1.3F,  1.3F,  1.05F, 1.05F, 1F,    1F,    0F,    1F,  1.1F,  1.1F,  1.1F,  1.35F, 1.1F,  1F,    -0.05F, 0F,  0.1F,  0F,  0.1F, 0.05F});
        put(25,  new float[] {0F, 1.3F,  1.3F,  1.05F, 1.05F, 1F,    1F,    0F,    1F,  1.1F,  1.1F,  1.1F,  1.35F, 1.1F,  1F,    -0.05F, 0F,  0.1F,  0F,  0.1F, 0.05F});
        //Diamond
        put(30,  new float[] {0F, 0.6F,  0.3F,  2F,    2F,    1.5F,  1F,    -0.1F, 4F,  1.1F,  1F,    1F,    1F,    2F,    1F,    -0.5F,  0F,  0F,    0F,  0.5F, 0.1F});
        put(31,  new float[] {0F, 1F,    0.65F, 1.2F,  1.2F,  1.25F, 1F,    -0.1F, 1F,  1.1F,  1.1F,  1.1F,  1F,    1.75F, 1.3F,  -0.3F,  0F,  0F,    0F,  0.3F, 0.1F});
        put(32,  new float[] {0F, 1F,    0.65F, 1.2F,  1.2F,  1.25F, 1F,    -0.1F, 1F,  1.1F,  1.1F,  1.1F,  1F,    1.75F, 1.3F,  -0.3F,  0F,  0F,    0F,  0.3F, 0.1F});
        put(33,  new float[] {0F, 1F,    0.65F, 1.2F,  1.2F,  1.25F, 1F,    -0.1F, 1F,  1.1F,  1.1F,  1.1F,  1F,    1.75F, 1.3F,  -0.3F,  0F,  0F,    0F,  0.3F, 0.1F});
        put(34,  new float[] {0F, 1F,    0.65F, 1.2F,  1.2F,  1.25F, 1F,    -0.1F, 1F,  1.1F,  1.1F,  1.1F,  1F,    1.75F, 1.3F,  -0.3F,  0F,  0F,    0F,  0.3F, 0.1F});
        put(35,  new float[] {0F, 0.6F,  0.3F,  2F,    2F,    1.5F,  1F,    -0.1F, 4F,  1.1F,  1F,    1F,    1F,    2F,    1F,    -0.5F,  0F,  0F,    0F,  0.5F, 0.1F});
        //Echelon             HP, ATK_L  ATK_H  ATK_AL ATK_AH DEF    SPD,   MOV    HIT, CRI    DHIT   THIT   MISS   AA     ASM    DODGE   XP   GRU    AMMO HPRES KB
        put(40,  new float[] {0F, 1.2F,  1.2F,  1F,    1F,    0.75F, 1F,    0.18F, 2F,  1.25F, 1.25F, 1.25F, 0.65F, 0.3F,  0.8F,  0.25F,  0F,  0.25F, 0F,  0F,   0F});
        put(41,  new float[] {0F, 1.1F,  1.1F,  1F,    1F,    0.85F, 1F,    0.18F, 2F,  1.2F,  1.2F,  1.2F,  0.7F,  0.3F,  0.8F,  0.25F,  0F,  0.25F, 0F,  0F,   0F});
        put(42,  new float[] {0F, 1F,    1F,    1F,    1F,    0.95F, 1F,    0.18F, 2F,  1.15F, 1.15F, 1.15F, 0.75F, 0.3F,  0.8F,  0.25F,  0F,  0.25F, 0F,  0F,   0F});
        put(43,  new float[] {0F, 1F,    1F,    1F,    1F,    1.05F, 1F,    0.18F, 1F,  1.1F,  1.1F,  1.1F,  0.8F,  0.3F,  0.8F,  0.25F,  0F,  0.25F, 0F,  0F,   0F});
        put(44,  new float[] {0F, 0.9F,  0.9F,  1F,    1F,    1.15F, 1F,    0.18F, 1F,  1.05F, 1.05F, 1.05F, 0.85F, 0.3F,  0.8F,  0.25F,  0F,  0.25F, 0F,  0F,   0F});
        put(45,  new float[] {0F, 0.8F,  0.8F,  1F,    1F,    1.25F, 1F,    0.18F, 1F,  1F,    1F,    1F,    0.9F,  0.3F,  0.8F,  0.25F,  0F,  0.25F, 0F,  0F,   0F});
        //Line Abreast
        put(50,  new float[] {0F, 0.9F,  0.9F,  0.9F,  0.9F,  1.35F, 0.8F,  0.05F, -2F, 1.15F, 1F,    1F,    1F,    1F,    1.75F, -0.15F, 0F,  0F,    0F,  0.1F, 0F});
        put(51,  new float[] {0F, 0.9F,  0.9F,  0.9F,  0.9F,  1.35F, 0.8F,  0.05F, -2F, 1.15F, 1F,    1F,    1F,    1F,    1.75F, -0.15F, 0F,  0F,    0F,  0.1F, 0F});
        put(52,  new float[] {0F, 0.9F,  0.9F,  0.9F,  0.9F,  1.35F, 0.8F,  0.05F, -2F, 1.15F, 1F,    1F,    1F,    1F,    1.75F, -0.15F, 0F,  0F,    0F,  0.1F, 0F});
        put(53,  new float[] {0F, 0.9F,  0.9F,  0.9F,  0.9F,  1.35F, 0.8F,  0.05F, -2F, 1.15F, 1F,    1F,    1F,    1F,    1.75F, -0.15F, 0F,  0F,    0F,  0.1F, 0F});
        put(54,  new float[] {0F, 1F,    1F,    1F,    1F,    1.2F,  0.9F,  0.05F, -1F, 1.15F, 1F,    1F,    1F,    1F,    1.75F, -0.1F,  0F,  0F,    0F,  0F,   0F});
        put(55,  new float[] {0F, 1F,    1F,    1F,    1F,    1.2F,  0.9F,  0.05F, -1F, 1.15F, 1F,    1F,    1F,    1F,    1.75F, -0.1F,  0F,  0F,    0F,  0F,   0F});
        //no buff
        put(0,   AttrsAdv.getResetFormationValue());
        put(1,   AttrsAdv.getResetFormationValue());
        put(2,   AttrsAdv.getResetFormationValue());
        put(3,   AttrsAdv.getResetFormationValue());
        put(4,   AttrsAdv.getResetFormationValue());
        put(5,   AttrsAdv.getResetFormationValue());
    }});
    
    
    /**
     * morale buff, index by {@link ID.Attrs}
     * 
     * key: -1:normal, 0:Excited, 1:Happy, 2:Tired, 3:Exhausted
     * 
     * multiplication:
     *   ATK, SPD, CRI, DHIT, THIT, MISS, AA, ASM 
     * addition:
     *   HP, DEF, MOV, HIT, DODGE, XP, GRUDGE, AMMO, HPRES, KB
     * ignore:
     *   HP
     */
    public static final Map<Integer, float[]> MoraleAttrs = Collections.unmodifiableMap(new HashMap<Integer, float[]>()
    {{
        //                 HP,  ATK_L  ATK_H  ATK_AL ATK_AH DEF   SPD,  MOV    HIT,  CRI   DHIT  THIT  MISS  AA    ASM   DODGE  XP     GRU    AMMO   HPRES   KB
        put(0, new float[]{0F,  1.25F, 1.25F, 1.25F, 1.25F, 0.2F, 1.4F, 0.15F, 4F,   1.2F, 1.2F, 1.2F, 1.5F, 1.5F, 1.5F, 0.25F, 0.5F,  0.5F,  0.5F,  0.5F,   0.25F});
        put(1, new float[]{0F,  1.1F,  1.1F,  1.1F,  1.1F,  0.1F, 1.2F, 0.08F, 2F,   1.1F, 1.1F, 1.1F, 1.25F,1.25F,1.25F,0.12F, 0.25F, 0.25F, 0.25F, 0.25F,  0.15F});
        put(2, new float[]{0F,  0.9F,  0.9F,  0.9F,  0.9F,  -0.1F,0.8F, -0.08F,-2F,  0.9F, 0.9F, 0.9F, 0.75F,0.75F,0.75F,-0.12F,-0.25F,-0.25F,-0.25F,-0.25F, -0.1F});
        put(3, new float[]{0F,  0.75F, 0.75F, 0.75F, 0.75F, -0.2F,0.6F, -0.15F,-4F,  0.8F, 0.8F, 0.8F, 0.5F, 0.5F, 0.5F, -0.25F,-0.5F, -0.5F, -0.5F, -0.5F,  -0.2F});
        put(-1, AttrsAdv.getResetMoraleValue());
    }});
    
    
    /** ITEMSTACK MAP FOR ICON
     *  
     */
    public static final Map<Short, ItemStack> ItemIconMap = Collections.unmodifiableMap(new HashMap<Short, ItemStack>()
    {{
        put(ID.Icon.IronIG,     new ItemStack(Items.IRON_INGOT));
        put(ID.Icon.Grudge,     new ItemStack(ModItems.Grudge));
        put(ID.Icon.GrudgeB,    new ItemStack(ModBlocks.BlockGrudge));
        put(ID.Icon.GrudgeBH,   new ItemStack(ModBlocks.BlockGrudgeHeavy));
        put(ID.Icon.AbyssIG,    new ItemStack(ModItems.AbyssMetal, 1, 0));
        put(ID.Icon.AbyssB,     new ItemStack(ModBlocks.BlockAbyssium));
        put(ID.Icon.PolymIG,    new ItemStack(ModItems.AbyssMetal, 1, 1));
        put(ID.Icon.PolymOre,   new ItemStack(ModBlocks.BlockPolymetalOre));
        put(ID.Icon.PolymB,     new ItemStack(ModBlocks.BlockPolymetal));
        put(ID.Icon.PolymBG,    new ItemStack(ModBlocks.BlockPolymetalGravel));
        put(ID.Icon.Gunpowder,  new ItemStack(Items.GUNPOWDER));
        put(ID.Icon.Blazepowder,new ItemStack(Items.BLAZE_POWDER));
        put(ID.Icon.AmmoL,      new ItemStack(ModItems.Ammo, 1, 0));
        put(ID.Icon.AmmoLC,     new ItemStack(ModItems.Ammo, 1, 1));
        put(ID.Icon.AmmoH,      new ItemStack(ModItems.Ammo, 1, 2));
        put(ID.Icon.AmmoHC,     new ItemStack(ModItems.Ammo, 1, 3));
        put(ID.Icon.RpBucket,   new ItemStack(ModItems.BucketRepair));
        put(ID.Icon.LaBucket,   new ItemStack(Items.LAVA_BUCKET));
        put(ID.Icon.NStar,      new ItemStack(Items.NETHER_STAR));
        put(ID.Icon.Ring,       new ItemStack(ModItems.MarriageRing));
        put(ID.Icon.Paper,      new ItemStack(Items.PAPER));
        put(ID.Icon.OwnPaper,   new ItemStack(ModItems.OwnerPaper));
        put(ID.Icon.Stick,      new ItemStack(Items.STICK));
        put(ID.Icon.KHammer,    new ItemStack(ModItems.KaitaiHammer));
        put(ID.Icon.ModTool,    new ItemStack(ModItems.ModernKit));
        put(ID.Icon.SpawnEgg0,  new ItemStack(ModItems.ShipSpawnEgg, 1, 0));
        put(ID.Icon.SpawnEgg1,  new ItemStack(ModItems.ShipSpawnEgg, 1, 1));
        put(ID.Icon.SpawnEgg2,  new ItemStack(ModItems.ShipSpawnEgg, 1, 2));
        put(ID.Icon.InstantMat, new ItemStack(ModItems.InstantConMat));
        put(ID.Icon.DiamondB,   new ItemStack(Blocks.DIAMOND_BLOCK));
        put(ID.Icon.RpGod,      new ItemStack(ModItems.RepairGoddess));
        put(ID.Icon.Pointer,    new ItemStack(ModItems.PointerItem));
        put(ID.Icon.ModelZF,    new ItemStack(ModItems.ToyAirplane));
        put(ID.Icon.Desk,       new ItemStack(ModBlocks.BlockDesk));
        put(ID.Icon.DeskBook,   new ItemStack(ModItems.DeskItemBook));
        put(ID.Icon.DeskRadar,  new ItemStack(ModItems.DeskItemRadar));
        put(ID.Icon.WriteBook,  new ItemStack(Items.WRITABLE_BOOK));
        put(ID.Icon.Compass,    new ItemStack(Items.COMPASS));
        put(ID.Icon.ObsidianB,  new ItemStack(Blocks.OBSIDIAN));
        put(ID.Icon.WoolB,      new ItemStack(Blocks.WOOL));
        put(ID.Icon.SmallSY,    new ItemStack(ModBlocks.BlockSmallShipyard));
        put(ID.Icon.Wrench,     new ItemStack(ModItems.TargetWrench));
        put(ID.Icon.VolCore,    new ItemStack(ModBlocks.BlockVolCore));
        put(ID.Icon.VolBlock,   new ItemStack(ModBlocks.BlockVolBlock));
        put(ID.Icon.Frame,      new ItemStack(ModBlocks.BlockFrame));
        put(ID.Icon.Waypoint,   new ItemStack(ModBlocks.BlockWaypoint));
        put(ID.Icon.Crane,      new ItemStack(ModBlocks.BlockCrane));
        put(ID.Icon.Piston,     new ItemStack(Blocks.PISTON));
        put(ID.Icon.TrainBook,  new ItemStack(ModItems.TrainingBook));
        put(ID.Icon.MagmaB,     new ItemStack(Blocks.MAGMA));
        put(ID.Icon.Tank0,      new ItemStack(ModItems.ShipTank, 1, 0));
        put(ID.Icon.Tank1,      new ItemStack(ModItems.ShipTank, 1, 1));
        put(ID.Icon.Tank2,      new ItemStack(ModItems.ShipTank, 1, 2));
        put(ID.Icon.Tank3,      new ItemStack(ModItems.ShipTank, 1, 3));
        put(ID.Icon.Cauldron,   new ItemStack(Items.CAULDRON));
        put(ID.Icon.Lapis,      new ItemStack(Items.DYE, 1, 4));
        put(ID.Icon.RecipePaper,new ItemStack(ModItems.RecipePaper));
        put(ID.Icon.OPTool,     new ItemStack(ModItems.OPTool));
        put(ID.Icon.AmmoEnchant,new ItemStack(ModItems.EquipAmmo, 1, 7));
        put(ID.Icon.Potions,    new ItemStack(Items.POTIONITEM));
        put(ID.Icon.GrudgeXP,   new ItemStack(ModItems.Grudge, 1, 1));
        put(ID.Icon.GrudgeXPB,  new ItemStack(ModBlocks.BlockGrudgeXP));
        put(ID.Icon.XPBot,      new ItemStack(Items.EXPERIENCE_BOTTLE));
    }});
    
    
    /** BOOK CONTENT MAP
     *  map<int, List<int[]>> = <ChapPage#, Content List<Content Array>>
     *  
     *  ChapPage#: ex: 1020 = chap 1, page 20
     *                 2018 = chap 2, page 18
     *                 
     *  Content List: list of content in int array               
     *  
     *  Content Array: 0:c.type  1:page pos  2:posX  3:posY  4...N:parms
     *    c.type: 0:text  1:picture  2:item icon
     *    page pos: 0:left  1:right
     *    posX: if picture: x pixels offset
     *    posY: if picture: y pixels offset
     *    parms: 
     *      for picture: 4:picID  5:picU  6:picV  7:sizeX  8:sizeY
     *      for item icon: 4:iconID
     *    
     */
    public static final Map<Integer, List> BookList = Collections.unmodifiableMap(new HashMap<Integer, List>()
    {{
        //chap 0: introduction
        //page 0
        put(0, Arrays.asList(   new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0}));
        //page 1
        put(1, Arrays.asList(   new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0}));
        
        //chap 1: resources
        //page 0: grudge
        put(1000, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, 76, 0, 0, 0, 100, 56},
                                new int[] {2, 0, 13, -3, ID.Icon.Grudge},
                                new int[] {2, 0, 43, -3, ID.Icon.GrudgeB},
                                new int[] {2, 0, 73, -3, ID.Icon.GrudgeBH}));
        //page 1: abyssium
        put(1001, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3, 17,  ID.Icon.Grudge},
                                new int[] {2, 0, 23, 17, ID.Icon.IronIG},
                                new int[] {2, 0, 81, 17, ID.Icon.AbyssIG}));
        //page 2: polymetal
        put(1002, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 0, 56, 100, 56},
                                new int[] {1, 1, 0, 73, 0, 0, 112, 100, 59},
                                new int[] {2, 0, 5, 52,  ID.Icon.PolymOre},
                                new int[] {2, 0, 30, 52, ID.Icon.PolymIG},
                                new int[] {2, 0, 55, 52, ID.Icon.PolymB},
                                new int[] {2, 0, 80, 52, ID.Icon.PolymBG}));
        //page 3: ammo
        put(1003, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.IronIG},
                                new int[] {2, 0, 23, -3, ID.Icon.IronIG},
                                new int[] {2, 0, 43, -3, ID.Icon.IronIG},
                                new int[] {2, 0, 3,  17, ID.Icon.IronIG},
                                new int[] {2, 0, 23, 17, ID.Icon.Grudge},
                                new int[] {2, 0, 43, 17, ID.Icon.IronIG},
                                new int[] {2, 0, 3,  37, ID.Icon.IronIG},
                                new int[] {2, 0, 23, 37, ID.Icon.Gunpowder},
                                new int[] {2, 0, 43, 37, ID.Icon.IronIG},
                                new int[] {2, 0, 81, 17, ID.Icon.AmmoL},
                                new int[] {2, 1, 3, 110, ID.Icon.AmmoL},
                                new int[] {2, 1, 28, 110,ID.Icon.AmmoLC},
                                new int[] {2, 1, 53, 110,ID.Icon.AmmoH},
                                new int[] {2, 1, 78, 110,ID.Icon.AmmoHC}));
        //page 4: repair bucket
        put(1004, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  17, ID.Icon.LaBucket},
                                new int[] {2, 0, 23, 17, ID.Icon.Grudge},
                                new int[] {2, 0, 81, 17, ID.Icon.RpBucket}));
        //page 5: ring
        put(1005, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 23, -3, ID.Icon.NStar},
                                new int[] {2, 0, 3,  17, ID.Icon.AbyssIG},
                                new int[] {2, 0, 43, 17, ID.Icon.AbyssIG},
                                new int[] {2, 0, 23, 37, ID.Icon.AbyssIG},
                                new int[] {2, 0, 81, 17, ID.Icon.Ring}));
        //page 6: ownership paper
        put(1006, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  17, ID.Icon.Paper},
                                new int[] {2, 0, 23, 17, ID.Icon.Grudge},
                                new int[] {2, 0, 81, 17, ID.Icon.OwnPaper}));
        //page 7: hammer
        put(1007, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.AbyssIG},
                                new int[] {2, 0, 23, -3, ID.Icon.AbyssIG},
                                new int[] {2, 0, 43, -3, ID.Icon.AbyssIG},
                                new int[] {2, 0, 3,  17, ID.Icon.AbyssIG},
                                new int[] {2, 0, 23, 17, ID.Icon.AbyssIG},
                                new int[] {2, 0, 43, 17, ID.Icon.AbyssIG},
                                new int[] {2, 0, 23, 37, ID.Icon.Stick},
                                new int[] {2, 0, 81, 17, ID.Icon.KHammer}));
        //page 8: modernization toolkit
        put(1008, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.KHammer},
                                new int[] {2, 0, 23, -3, ID.Icon.Wrench},
                                new int[] {2, 0, 43, -3, ID.Icon.GrudgeXPB},
                                new int[] {2, 0, 3,  17, ID.Icon.GrudgeXPB},
                                new int[] {2, 0, 23, 17, ID.Icon.GrudgeXPB},
                                new int[] {2, 0, 43, 17, ID.Icon.GrudgeXPB},
                                new int[] {2, 0, 81, 17, ID.Icon.ModTool}));
        //page 9: instant mats
        put(1009, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  17, ID.Icon.KHammer},
                                new int[] {2, 0, 23, 17, ID.Icon.SpawnEgg0},
                                new int[] {2, 0, 81, 17, ID.Icon.InstantMat}));
        //page 10: abyssal goddess
        put(1010, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.GrudgeB},
                                new int[] {2, 0, 23, -3, ID.Icon.GrudgeBH},
                                new int[] {2, 0, 43, -3, ID.Icon.GrudgeB},
                                new int[] {2, 0, 3,  17, ID.Icon.GrudgeBH},
                                new int[] {2, 0, 23, 17, ID.Icon.DiamondB},
                                new int[] {2, 0, 43, 17, ID.Icon.GrudgeBH},
                                new int[] {2, 0, 3,  37, ID.Icon.GrudgeB},
                                new int[] {2, 0, 23, 37, ID.Icon.GrudgeBH},
                                new int[] {2, 0, 43, 37, ID.Icon.GrudgeB},
                                new int[] {2, 0, 81, 17, ID.Icon.RpGod}));
        //page 11: pointer
        put(1011, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  37, ID.Icon.PolymIG},
                                new int[] {2, 0, 23, 17, ID.Icon.PolymIG},
                                new int[] {2, 0, 43, -3, ID.Icon.GrudgeB},
                                new int[] {2, 0, 81, 17, ID.Icon.Pointer}));
        //page 12: pointer
        put(1012, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0}));
        //page 13: pointer
        put(1013, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0}));
        //page 14: model zero fighter
        put(1014, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 23, -3, ID.Icon.PolymIG},
                                new int[] {2, 0, 3,  17, ID.Icon.PolymIG},
                                new int[] {2, 0, 23, 17, ID.Icon.PolymIG},
                                new int[] {2, 0, 43, 17, ID.Icon.PolymIG},
                                new int[] {2, 0, 23, 37, ID.Icon.PolymIG},
                                new int[] {2, 0, 81, 17, ID.Icon.ModelZF}));
        //page 15: desk item book
        put(1015, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.Grudge},
                                new int[] {2, 0, 23, -3, ID.Icon.Grudge},
                                new int[] {2, 0, 43, -3, ID.Icon.Grudge},
                                new int[] {2, 0, 3,  17, ID.Icon.Grudge},
                                new int[] {2, 0, 23, 17, ID.Icon.WriteBook},
                                new int[] {2, 0, 43, 17, ID.Icon.Grudge},
                                new int[] {2, 0, 3,  37, ID.Icon.Grudge},
                                new int[] {2, 0, 23, 37, ID.Icon.Grudge},
                                new int[] {2, 0, 43, 37, ID.Icon.Grudge},
                                new int[] {2, 0, 81, 17, ID.Icon.DeskBook}));
        //page 16: desk item radar
        put(1016, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.Grudge},
                                new int[] {2, 0, 23, -3, ID.Icon.Grudge},
                                new int[] {2, 0, 43, -3, ID.Icon.Grudge},
                                new int[] {2, 0, 3,  17, ID.Icon.Grudge},
                                new int[] {2, 0, 23, 17, ID.Icon.Compass},
                                new int[] {2, 0, 43, 17, ID.Icon.Grudge},
                                new int[] {2, 0, 3,  37, ID.Icon.Grudge},
                                new int[] {2, 0, 23, 37, ID.Icon.Grudge},
                                new int[] {2, 0, 43, 37, ID.Icon.Grudge},
                                new int[] {2, 0, 81, 17, ID.Icon.DeskRadar}));
        //page 17: desk
        put(1017, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.DeskRadar},
                                new int[] {2, 0, 23, -3, ID.Icon.DeskBook},
                                new int[] {2, 0, 43, -3, ID.Icon.WoolB},
                                new int[] {2, 0, 3,  17, ID.Icon.ObsidianB},
                                new int[] {2, 0, 23, 17, ID.Icon.ObsidianB},
                                new int[] {2, 0, 43, 17, ID.Icon.ObsidianB},
                                new int[] {2, 0, 3,  37, ID.Icon.ObsidianB},
                                new int[] {2, 0, 43, 37, ID.Icon.ObsidianB},
                                new int[] {2, 0, 81, 17, ID.Icon.Desk}));
        //page 18: wrench
        put(1018, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.AbyssIG},
                                new int[] {2, 0, 43, -3, ID.Icon.AbyssIG},
                                new int[] {2, 0, 3,  17, ID.Icon.AbyssIG},
                                new int[] {2, 0, 23, 17, ID.Icon.AbyssIG},
                                new int[] {2, 0, 43, 17, ID.Icon.AbyssIG},
                                new int[] {2, 0, 23, 37, ID.Icon.AbyssIG},
                                new int[] {2, 0, 81, 17, ID.Icon.Wrench}));
        //page 19: volcano block
        put(1019, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.ObsidianB},
                                new int[] {2, 0, 23, -3, ID.Icon.MagmaB},
                                new int[] {2, 0, 43, -3, ID.Icon.ObsidianB},
                                new int[] {2, 0, 3,  17, ID.Icon.MagmaB},
                                new int[] {2, 0, 23, 17, ID.Icon.GrudgeB},
                                new int[] {2, 0, 43, 17, ID.Icon.MagmaB},
                                new int[] {2, 0, 3,  37, ID.Icon.ObsidianB},
                                new int[] {2, 0, 23, 37, ID.Icon.MagmaB},
                                new int[] {2, 0, 43, 37, ID.Icon.ObsidianB},
                                new int[] {2, 0, 81, 17, ID.Icon.VolBlock}));
        //page 20: volcano core
        put(1020, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.ObsidianB},
                                new int[] {2, 0, 23, -3, ID.Icon.VolBlock},
                                new int[] {2, 0, 43, -3, ID.Icon.ObsidianB},
                                new int[] {2, 0, 3,  17, ID.Icon.VolBlock},
                                new int[] {2, 0, 23, 17, ID.Icon.GrudgeBH},
                                new int[] {2, 0, 43, 17, ID.Icon.VolBlock},
                                new int[] {2, 0, 3,  37, ID.Icon.ObsidianB},
                                new int[] {2, 0, 23, 37, ID.Icon.VolBlock},
                                new int[] {2, 0, 43, 37, ID.Icon.ObsidianB},
                                new int[] {2, 0, 81, 17, ID.Icon.VolCore}));
        //page 21: frame
        put(1021, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.AbyssIG},
                                new int[] {2, 0, 43, -3, ID.Icon.AbyssIG},
                                new int[] {2, 0, 23, 17, ID.Icon.ObsidianB},
                                new int[] {2, 0, 3,  37, ID.Icon.AbyssIG},
                                new int[] {2, 0, 43, 37, ID.Icon.AbyssIG},
                                new int[] {2, 0, 81, 17, ID.Icon.Frame}));
        //page 22: waypoint
        put(1022, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 23, 17, ID.Icon.Grudge},
                                new int[] {2, 0, 23, 37, ID.Icon.Stick},
                                new int[] {2, 0, 81, 17, ID.Icon.Waypoint}));
        //page 23: crane
        put(1023, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.AbyssIG},
                                new int[] {2, 0, 23, -3, ID.Icon.AbyssIG},
                                new int[] {2, 0, 43, -3, ID.Icon.AbyssIG},
                                new int[] {2, 0, 3,  17, ID.Icon.AbyssIG},
                                new int[] {2, 0, 23, 17, ID.Icon.GrudgeB},
                                new int[] {2, 0, 43, 17, ID.Icon.AbyssIG},
                                new int[] {2, 0, 3,  37, ID.Icon.AbyssIG},
                                new int[] {2, 0, 23, 37, ID.Icon.Piston},
                                new int[] {2, 0, 43, 37, ID.Icon.AbyssIG},
                                new int[] {2, 0, 81, 17, ID.Icon.Crane}));
        //page 24: training book
        put(1024, Arrays.asList(new int[] {3, 4, 1},
                                new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.KHammer},
                                new int[] {2, 0, 23, -3, ID.Icon.ModTool},
                                new int[] {2, 0, 43, -3, ID.Icon.WriteBook},
                                new int[] {2, 0, 3,  17, ID.Icon.GrudgeXPB},
                                new int[] {2, 0, 23, 17, ID.Icon.GrudgeXPB},
                                new int[] {2, 0, 43, 17, ID.Icon.GrudgeXPB},
                                new int[] {2, 0, 3,  37, ID.Icon.GrudgeXPB},
                                new int[] {2, 0, 81, 17, ID.Icon.TrainBook},
                                new int[] {1, 2, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 2, 3,  -3, ID.Icon.GrudgeXP},
                                new int[] {2, 2, 23, -3, ID.Icon.GrudgeXP},
                                new int[] {2, 2, 43, -3, ID.Icon.GrudgeXP},
                                new int[] {2, 2, 3,  17, ID.Icon.GrudgeXP},
                                new int[] {2, 2, 23, 17, ID.Icon.GrudgeXP},
                                new int[] {2, 2, 43, 17, ID.Icon.GrudgeXP},
                                new int[] {2, 2, 3,  37, ID.Icon.GrudgeXP},
                                new int[] {2, 2, 23, 37, ID.Icon.GrudgeXP},
                                new int[] {2, 2, 43, 37, ID.Icon.GrudgeXP},
                                new int[] {2, 2, 81, 17, ID.Icon.GrudgeXPB},
                                new int[] {1, 4, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 4, 3,  -3, ID.Icon.XPBot},
                                new int[] {2, 4, 23, -3, ID.Icon.XPBot},
                                new int[] {2, 4, 43, -3, ID.Icon.XPBot},
                                new int[] {2, 4, 3,  17, ID.Icon.XPBot},
                                new int[] {2, 4, 23, 17, ID.Icon.Grudge},
                                new int[] {2, 4, 43, 17, ID.Icon.XPBot},
                                new int[] {2, 4, 3,  37, ID.Icon.XPBot},
                                new int[] {2, 4, 23, 37, ID.Icon.XPBot},
                                new int[] {2, 4, 43, 37, ID.Icon.XPBot},
                                new int[] {2, 4, 81, 17, ID.Icon.GrudgeXP}));
        
        //page 25: ship tank
        put(1025, Arrays.asList(new int[] {3, 6, 1},
                                new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.PolymIG},
                                new int[] {2, 0, 23, -3, ID.Icon.Cauldron},
                                new int[] {2, 0, 43, -3, ID.Icon.PolymIG},
                                new int[] {2, 0, 3,  17, ID.Icon.PolymIG},
                                new int[] {2, 0, 23, 17, ID.Icon.Cauldron},
                                new int[] {2, 0, 43, 17, ID.Icon.PolymIG},
                                new int[] {2, 0, 3,  37, ID.Icon.PolymIG},
                                new int[] {2, 0, 23, 37, ID.Icon.Cauldron},
                                new int[] {2, 0, 43, 37, ID.Icon.PolymIG},
                                new int[] {2, 0, 81, 17, ID.Icon.Tank0},
                                new int[] {1, 2, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 2, 3,  -3, ID.Icon.ObsidianB},
                                new int[] {2, 2, 23, -3, ID.Icon.Tank0},
                                new int[] {2, 2, 43, -3, ID.Icon.ObsidianB},
                                new int[] {2, 2, 3,  17, ID.Icon.ObsidianB},
                                new int[] {2, 2, 23, 17, ID.Icon.Tank0},
                                new int[] {2, 2, 43, 17, ID.Icon.ObsidianB},
                                new int[] {2, 2, 3,  37, ID.Icon.ObsidianB},
                                new int[] {2, 2, 23, 37, ID.Icon.Tank0},
                                new int[] {2, 2, 43, 37, ID.Icon.ObsidianB},
                                new int[] {2, 2, 81, 17, ID.Icon.Tank1},
                                new int[] {1, 4, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 4, 3,  -3, ID.Icon.AbyssB},
                                new int[] {2, 4, 23, -3, ID.Icon.Tank1},
                                new int[] {2, 4, 43, -3, ID.Icon.AbyssB},
                                new int[] {2, 4, 3,  17, ID.Icon.AbyssB},
                                new int[] {2, 4, 23, 17, ID.Icon.Tank1},
                                new int[] {2, 4, 43, 17, ID.Icon.AbyssB},
                                new int[] {2, 4, 3,  37, ID.Icon.AbyssB},
                                new int[] {2, 4, 23, 37, ID.Icon.Tank1},
                                new int[] {2, 4, 43, 37, ID.Icon.AbyssB},
                                new int[] {2, 4, 81, 17, ID.Icon.Tank2},
                                new int[] {1, 6, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 6, 3,  -3, ID.Icon.GrudgeBH},
                                new int[] {2, 6, 23, -3, ID.Icon.Tank2},
                                new int[] {2, 6, 43, -3, ID.Icon.GrudgeBH},
                                new int[] {2, 6, 3,  17, ID.Icon.GrudgeBH},
                                new int[] {2, 6, 23, 17, ID.Icon.Tank2},
                                new int[] {2, 6, 43, 17, ID.Icon.GrudgeBH},
                                new int[] {2, 6, 3,  37, ID.Icon.GrudgeBH},
                                new int[] {2, 6, 23, 37, ID.Icon.Tank2},
                                new int[] {2, 6, 43, 37, ID.Icon.GrudgeBH},
                                new int[] {2, 6, 81, 17, ID.Icon.Tank3}));
        //page 26: recipe paper
        put(1026, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  17, ID.Icon.Grudge},
                                new int[] {2, 0, 23, 17, ID.Icon.Paper},
                                new int[] {2, 0, 43, 17, ID.Icon.Lapis},
                                new int[] {2, 0, 81, 17, ID.Icon.RecipePaper}));
        //page 27: OP Tool
        put(1027, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {2, 0, 43, 17, ID.Icon.OPTool}));
        //page 28: Enchant Shell
        put(1028, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {2, 0, 3,  -3, ID.Icon.Potions},
                                new int[] {2, 0, 23, -3, ID.Icon.Potions},
                                new int[] {2, 0, 43, -3, ID.Icon.Potions},
                                new int[] {2, 0, 3,  17, ID.Icon.Potions},
                                new int[] {2, 0, 23, 17, ID.Icon.AmmoEnchant},
                                new int[] {2, 0, 43, 17, ID.Icon.Potions},
                                new int[] {2, 0, 3,  37, ID.Icon.Potions},
                                new int[] {2, 0, 23, 37, ID.Icon.Potions},
                                new int[] {2, 0, 43, 37, ID.Icon.Potions},
                                new int[] {2, 0, 81, 17, ID.Icon.AmmoEnchant}));
                
        //chap 2: construction
        //page 0: vent
        put(2000, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
                                new int[] {1, 1, 0, -6, 0, 100, 134, 100, 46},
                                new int[] {2, 0, 3,  -3, ID.Icon.Grudge},
                                new int[] {2, 0, 23, -3, ID.Icon.LaBucket},
                                new int[] {2, 0, 43, -3, ID.Icon.Grudge},
                                new int[] {2, 0, 3,  17, ID.Icon.LaBucket},
                                new int[] {2, 0, 23, 17, ID.Icon.ObsidianB},
                                new int[] {2, 0, 43, 17, ID.Icon.LaBucket},
                                new int[] {2, 0, 3,  37, ID.Icon.ObsidianB},
                                new int[] {2, 0, 23, 37, ID.Icon.ObsidianB},
                                new int[] {2, 0, 43, 37, ID.Icon.ObsidianB},
                                new int[] {2, 0, 81, 17, ID.Icon.SmallSY}));
        //page 1: vent
        put(2001, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 25, -12, 0, 0, 230, 50, 26}));
        //page 2: vent
        put(2002, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 25, -12, 0, 50, 230, 50, 26}));
        //page 3: vortex
        put(2003, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, 60, 0, 100, 180, 100, 65},
                                new int[] {1, 1, -7, -18, 0, 200, 0, 38, 38},
                                new int[] {1, 1, 31, -18, 0, 200, 38, 38, 38},
                                new int[] {1, 1, 69, -18, 0, 200, 76, 38, 38}));
        //page 4: vortex
        put(2004, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0}));
        
        //chap 3:
        //page 13: morale
        put(3013, Arrays.asList(new int[] {0, 0, 0, 0},
                                new int[] {0, 1, 0, 0},
                                new int[] {1, 0, 0, 120, 0, 100, 245, 100, 11}));
        
    }});
    
    
}