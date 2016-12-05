package com.lulan.shincolle.worldgen;

import java.util.Random;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModBlocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

public class ShinColleWorldGen implements IWorldGenerator
{
	
	private WorldGenerator genPolymetal, genPolyGravel;
	
	
	//維度判定
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider)
	{
		//依照維度id呼叫不同生成方法
		switch (world.provider.getDimension())
		{
		case 0:		//一般世界
			generateSurface(world, random, chunkX*16, chunkZ*16);	//將chunk位置x16 轉成block位置
			generateSea(world, random, chunkX*16, chunkZ*16);
			break;
		case -1:	//地獄
		//	generateNether(world, random, chunkX*16, chunkZ*16);
			break;
		case 1:		//終界
		//	generateEnd(world, random, chunkX*16, chunkZ*16);
			break;
		default:	//其他維度
			generateSurface(world, random, chunkX*16, chunkZ*16);
			generateSea(world, random, chunkX*16, chunkZ*16);
			break;		
		}		
	}

	//隨機生成器
	//參數: 礦石,生成世界,隨機數,x起點,z起點,生成次數,最低高度,最高高度
	//生成次數:鐵/紅石~10 鑽石/金~2
	private void oreGenerator(WorldGenerator genOres, World world, Random rand, int blockX, int blockZ, int spawnNum, int minY, int maxY)
	{	
		//NYI: 依照生態系id生成不同數量的礦
		//以起點blockX,blockZ隨機加上0~15(即一個chunk範圍內)  生成高度則為minY~maxY之間
		//每個chunk執行spawnChance次生成動作
		int x,y,z = 0;
		int spawnN = spawnNum;
		
		Biome biome = world.getBiomeGenForCoords(new BlockPos(blockX, 0, blockZ));
		
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.OCEAN))
		{
			spawnN *= 3;
		}
		
		for (int i = 0; i < spawnN; i++)
		{
			x = blockX + rand.nextInt(16);
			z = blockZ + rand.nextInt(16);
			y = minY + rand.nextInt(maxY - minY);
			genOres.generate(world, rand, new BlockPos(x, y, z));
		}
	}

	//一般世界生成方法  每個chunk都會呼叫一次
	private void generateSurface(World world, Random rand, int x, int z)
	{
		//Polymetal生成: 生成大小4~8個block 每chunk生成次數10次 生成高度2~40
		genPolymetal = new WorldGenMinable(ModBlocks.BlockPolymetalOre.getDefaultState(), 4 + rand.nextInt(4));  //每個chunk會重新隨機一次生成礦物大小
		oreGenerator(genPolymetal, world, rand, x, z, ConfigHandler.polyOreBaseRate, 3, 50);
		
	}
	
	//海洋相關生態系: polymetallic gravel生成方法
	private void generateSea(World world, Random rand, int x, int z)
	{
		Biome biome = world.getBiomeGenForCoords(new BlockPos(x, 0, z));
		
		if(BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.OCEAN))
		{
			genPolyGravel = new WorldGenPolyGravel(2 + rand.nextInt(2));
			int posX, posY, posZ = 0;
			BlockPos pos;
			
			for (int i = 0; i < ConfigHandler.polyGravelBaseRate; i++)
			{
				//取得高度最高的實體方塊+1格(即水底+1)
				pos = world.getTopSolidOrLiquidBlock(new BlockPos(x + rand.nextInt(16), 1, z + rand.nextInt(16)));
				genPolyGravel.generate(world, rand, pos);
			}
		}
	}
	
	private void generateNether(World world, Random rand, int x, int z) {}
	
	private void generateEnd(World world, Random rand, int x, int z) {}
	
	
}
