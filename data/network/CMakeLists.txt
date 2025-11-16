cmake_minimum_required(VERSION 3.10.2)
project("securekeys")

add_library(
        keys-lib
        SHARED
        src/main/cpp/keys-lib.cpp
)

find_library(
        log-lib
        log
)

target_link_libraries(
        keys-lib
        ${log-lib}
)