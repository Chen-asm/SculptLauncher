#pragma once
struct BlockSource;
struct BlockPos;
struct Random;

struct Feature {
	bool manuallyPlaced;
	unsigned char updateNotify;
	

	Feature(bool);
	
	virtual ~Feature();
	//virtual bool place(BlockSource&, const BlockPos&, Random&) const;
};

