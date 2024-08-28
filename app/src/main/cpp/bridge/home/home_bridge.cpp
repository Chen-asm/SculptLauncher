//
// Created by 鸳汐 on 2024/8/29.
//

#include "home_bridge.h"

// 输出架构信息
std::string archInfo(const char* path) {
    std::ifstream so_file(path, std::ios::binary);
    ElfHeader header;
    so_file.read(reinterpret_cast<char*>(&header), sizeof(header));

    if (std::memcmp(header.e_ident, "\x7f" "ELF", 4) != 0){
        return "unknown";
    }

    auto arch = get_architecture(header.e_machine);
    return arch;
}

// 架构映射
const char* get_architecture(uint16_t e_machine) {
    switch (e_machine) {
        case 0x03: return "x86";
        case 0x3E: return "x86-64";
        case 0x28: return "armeabi";
        case 0xB7: return "arm64-v8a";
            // 添加其他架构
        default: return "Unknown";
    }
}
