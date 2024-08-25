#pragma once
#include <vector>
#include <memory>
#include <string>
class BiomeDecorator;
class Random;
class BlockPos;
class BlockSource;
class LevelChunk;
#include "minecraftpe/world/entity/EntityType.h"
class MobSpawnerData;
class Color;
class Feature;
class Mob;
class Biome
{
public:
	enum class BiomeType : int
	{
          Beach,
          Desert,
          ExtremeHills,
          Flat,
          Forest,
          Hell,
          Ice,
          Jungle,
          Mesa,
          MushroomIsland,
          Ocean,
          Plain,
          River,
          Savanna,
          StoneBeach,
          Swamp,
          Taiga,
          TheEnd





	};
public:
     BiomeDecorator * decorator;//4
     std::vector<MobSpawnerData> hostileMobs;//8
     std::vector<MobSpawnerData> friendlyMobs;//20
     //int foliage_grassColor;//24
     //char fillers1[4];//28
     std::vector<MobSpawnerData> waterMobs;//32
	 char fillers2[12];//44
     std::string name;//56
     int oddColor;//64
     uint8_t surfaceBlock;//68
     uint8_t fillerBlock;//70
     char fillers3[4];//72
     float temperature;//76
     float downFall;//80
	 char fillers4[56];//84
     bool isRain;//140
     BiomeType biomeType;//144
	 int biomeId;//148
	 int idk;//152



public:
	static Biome *beaches;
	static Biome *birchForest;
	static Biome *birchForestHills;
	static Biome *birchForestHillsMutated;
	static Biome *birchForestMutated;
	static Biome *coldBeach;
	static Biome *deepOcean;
	static Biome *defaultTotalEnemyWeight;
	static Biome *defaultTotalFriendlyWeight;
	static Biome *desert;
	static Biome *desertHills;
	static Biome *desertMutated;
	static Biome *extremeHills;
	static Biome *extremeHillsMutated;
	static Biome *extremeHillsWithTrees;
	static Biome *extremeHillsWithTreesMutated;
	static Biome *forest;
	static Biome *forestHills;
	static Biome *forestMutated;
	static Biome *frozenOcean;
	static Biome *frozenRiver;
	static Biome *hell;
	static Biome *iceFlats;
	static Biome *iceFlatsMutated;
	static Biome *iceMountains;
	static Biome *jungle;
	static Biome *jungleEdge;
	static Biome *jungleEdgeMutated;
	static Biome *jungleHills;
	static Biome *jungleMutated;
	static Biome *mesa;
	static Biome *mesaClearRock;
	static Biome *mesaClearRockMutated;
	static Biome *mesaMutated;
	static Biome *mesaRock;
	static Biome *mesaRockMutated;
	static Biome *mushroomIsland;
	static Biome *mushroomIslandShore;
	static Biome *ocean;
	static Biome *plains;
	static Biome *plainsMutated;
	static Biome *redwoodTaiga;
	static Biome *redwoodTaigaHills;
	static Biome *redwoodTaigaHillsMutated;
	static Biome *redwoodTaigaMutated;
	static Biome *river;
	static Biome *roofedForest;
	static Biome *roofedForestMutated;
	static Biome *savanna;
	static Biome *savannaMutated;
	static Biome *savannaRock;
	static Biome *savannaRockMutated;
	static Biome *sky;
	static Biome *smallerExtremeHills;
	static Biome *stoneBeach;
	static Biome *swampland;
	static Biome *swamplandMutated;
	static Biome *taiga;
	static Biome *taigaCold;
	static Biome *taigaColdHills;
	static Biome *taigaColdMutated;
	static Biome *taigaHills;
	static Biome *taigaMutated;

public:
	static Biome *mBiomes;

public:
	Biome(int, Biome::BiomeType, std::unique_ptr<BiomeDecorator, std::default_delete<BiomeDecorator> >);
	virtual void setColor(int);
	virtual void setColor(int, bool);
	Biome* setName(std::string const&);
	Biome* setNoRain(void);
	virtual ~Biome();
	virtual Feature* getTreeFeature(Random *);
	virtual Feature* getGrassFeature(Random *);
	virtual float getTemperature();
	virtual float adjustScale(float);
	virtual float adjustDepth(float);
	virtual const Color getSkyColor(float);
	virtual std::vector<Mob*> getMobs(EntityType);
	virtual float getCreatureProbability();
	virtual int getFoliageColor();
	virtual void* getRandomFlowerTypeAndData(Random &, BlockPos const &);
	virtual void decorate(BlockSource *, Random &, BlockPos const &, bool, float);
	virtual void buildSurfaceAt(Random &, LevelChunk &, BlockPos const &, float);
	virtual int getGrassColor(BlockPos const &);
	virtual void refreshBiome(unsigned int);
	virtual void getTemperatureCategory() const;
	virtual bool isSame(Biome *);
	virtual bool isHumid();
	virtual Biome* createMutatedCopy(int);
	static void initBiomes();
	static const Biome& getBiome(int);
	static const Biome& getBiome(int, Biome*);

	static std::vector<Mob*> EMPTY_MOBLIST;

};
