#pragma once
#include "Player.h"

class ServerPlayer : public Player {
public:
	bool hasResource(int);
    void changeDimension(DimensionId);
	void push(Vec3 const&);
    void knockback(Entity*, int, float, float);
    void hurtArmor(int);
};