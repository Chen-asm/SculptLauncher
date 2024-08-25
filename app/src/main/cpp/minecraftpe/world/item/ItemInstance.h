#pragma once
class CompoundTag;
class Item;
class Block;
class Player;
class ItemInstance {
public:
	short count, aux;
	CompoundTag* userData;
	bool valid;
	Item* item;
	Block* block;

	ItemInstance();
	ItemInstance(int, int, int);
	ItemInstance(const Item*, int);
	ItemInstance(const Item*, int, int);
	ItemInstance(const Block*, int);
	ItemInstance(const Block*, int, int);
	ItemInstance(const ItemInstance&);
	int getId() const;
	bool useOn(Player*, int, int, int, signed char, float, float, float);
	const ItemInstance& operator=(const ItemInstance&);
	
	bool isLiquidClipItem();
};
