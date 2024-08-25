#pragma once

class Color {
public:
	static const Color WHITE, GREY, BLACK, RED, GREEN, BLUE, YELLOW, PURPLE, CYAN, NIL, SHADE_DOWN, SHADE_UP, SHADE_NORTH_SOUTH, SHADE_WEST_EAST;
	
	float r, g, b, a;
	
	
	static Color fromHSB(float, float, float);
    Color(float r,float g,float b,float a)
	{
		this->r = r,this->g=g,this->b=b,this->a=a;
	}
	int toARGB() const;
	int toABGR() const;
};