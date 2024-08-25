#pragma once

namespace mce {
	struct MaterialPtr;
	struct TexturePtr;
	struct Mesh {
		char filler[80];
		
		
		void render(mce::MaterialPtr const&, unsigned int, unsigned int) const;
		void render(mce::MaterialPtr const&, mce::TexturePtr const&, unsigned int, unsigned int) const;
		void render(mce::MaterialPtr const&, mce::TexturePtr const&, mce::TexturePtr const&, unsigned int, unsigned int) const;
		void render(mce::MaterialPtr const&, mce::TexturePtr const&, mce::TexturePtr const&, mce::TexturePtr const&, unsigned int, unsigned int) const;
		Mesh();
	};
};
