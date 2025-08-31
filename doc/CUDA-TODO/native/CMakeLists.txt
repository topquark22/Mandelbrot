cmake_minimum_required(VERSION 3.18)
project(MandelbrotNative LANGUAGES CXX CUDA)

# Set output directory for compiled native lib
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/../target/native)

# Enable CUDA with C++14 support
set(CMAKE_CUDA_STANDARD 14)

# JNI headers (update paths if needed)
find_package(Java REQUIRED)
find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})

# Add shared library (you will implement these files)
add_library(mandelbrot SHARED mandelbrot.cu mandelbrot_jni.cpp)

# Optional: export symbols on Windows
if (WIN32)
  target_compile_definitions(mandelbrot PRIVATE -DJNIEXPORT)
endif()
