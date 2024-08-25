#pragma once
#include <functional>

class PacketSender;
class ItemInstance;
class Player;
class Level;
class SoundPlayer;
class Vibration;
class BlockPos;
class Vec3;
class GameMode
{
    public:
    GameMode(PacketSender&, Level&, SoundPlayer&, Vibration&);
    virtual void _destroyBlockInternal(Player&, BlockPos, signed char);
    virtual void _creativeDestroyBlock(Player&, BlockPos, signed char);
    virtual void continueDestroyBlock(Player&, BlockPos, signed char);
    virtual void startDestroyBlock(Player&, BlockPos, signed char);
    virtual void useItem(Player&, ItemInstance&);
    virtual void useItemOn(Player&, ItemInstance*, BlockPos const&, signed char, Vec3 const&);
    




};