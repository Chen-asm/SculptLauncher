#pragma once
class BlockSource;
class BlockPos;
class Random;
class Feature;
#include <memory>

class BiomeDecorator {
public:
	BiomeDecorator();
	virtual ~BiomeDecorator();
	void decorateDepthSpan(BlockSource*, Random&, const BlockPos&, int, std::unique_ptr<Feature, std::default_delete<Feature>>&, int, int);
	void decorateDepthAverage(BlockSource*, Random&, const BlockPos&, int, std::unique_ptr<Feature, std::default_delete<Feature>>&, int, int);
	virtual void decorateOres(BlockSource*, Random&, const BlockPos&);
};

