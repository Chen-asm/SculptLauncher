//
// Created by 鸳汐 on 2024/8/29.
//

#ifndef SCULPTLAUNCHER_HOME_BRIDGE_H
#define SCULPTLAUNCHER_HOME_BRIDGE_H
#include <cstring>
#include <iostream>
#include <fstream>

struct ElfHeader {
    unsigned char e_ident[16]; // ELF 魔数和标识
    uint16_t e_type; // 文件类型
    uint16_t e_machine; // 硬件架构
    uint32_t e_version; // ELF 版本
};

const char* get_architecture(uint16_t);
std::string archInfo(const char* path);

#endif //SCULPTLAUNCHER_HOME_BRIDGE_H
