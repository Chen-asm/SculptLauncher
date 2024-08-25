#pragma once
#include "minecraftpe/world/phys/Vec3.h"
struct BlockPos {
	int x, y, z;
	BlockPos(Vec3 const&);
	BlockPos(float,float,float);

};

