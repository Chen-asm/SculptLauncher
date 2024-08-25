#pragma once
class Block;
struct BlockPos;
class BlockEntity;
class Level;
class Material;
#include "../../CommonTypes.h"
#include "minecraftpe/world/level/dimension/Dimension.h"
#include "minecraftpe/world/level/gen/ChunkSource.h"

class BlockSource {
public:
    BlockSource(Level&, Dimension&, ChunkSource&, bool, bool);
	Level* getLevel() const;
	Block* getBlock(const BlockPos&);
	FullBlock getBlockID(const BlockPos&);
	DataID getData(const BlockPos&);
	Material& getMaterial(const BlockPos&);
	void setBlock(BlockPos const&, BlockID, int);
	void setBlock(int, int, int, BlockID, int);
	bool setBlockAndData(const BlockPos&, FullBlock, int);
	void removeBlock(int, int, int);
	bool isEmptyBlock(int, int, int);
	bool isEmptyBlock(BlockPos const&);
	BlockEntity* getBlockEntity(const BlockPos&);
   
};
