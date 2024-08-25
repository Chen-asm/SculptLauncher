#pragma once

#include "world/level/block/Block.h"
#include "world/entity/player/Player.h"
#include "world/level/BlockPos.h"
class TntBlock : public Block {
public:
	TntBlock(std::string const&, int, std::string const&);
    //virtual bool use(Player&, const BlockPos&);
};
