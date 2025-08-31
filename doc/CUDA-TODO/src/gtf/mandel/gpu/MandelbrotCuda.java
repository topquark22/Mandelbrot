package gtf.mandel.gpu;

import jcuda.*;
import jcuda.driver.*;

import static jcuda.driver.JCudaDriver.*;

/** Launches the CUDA kernel and returns iteration counts (width*height). */
public final class MandelbrotCuda {
  static {
    JCudaDriver.setExceptionsEnabled(true);
    JCuda.setExceptionsEnabled(true);
    cuInit(0);
  }

  private final CUcontext ctx;
  private final CUmodule module;
  private final CUfunction func;

  public MandelbrotCuda(String ptxPath) {
    try {
      CUdevice dev = new CUdevice();
      cuDeviceGet(dev, 0);
      ctx = new CUcontext();
      cuCtxCreate(ctx, 0, dev);

      module = new CUmodule();
      cuModuleLoad(module, ptxPath);
      func = new CUfunction();
      cuModuleGetFunction(func, module, "mandelbrotKernel");
    } catch (CudaException e) {
      throw new RuntimeException("Failed to init JCuda / load PTX: " + ptxPath, e);
    }
  }

  /** Returns width*height iteration counts (inside == maxIter). */
  public int[] compute(int width, int height,
             double xmin, double xmax, double ymin, double ymax,
             int maxIter) {
    int n = width * height;
    int[] hostOut = new int[n];

    // Device buffer
    CUdeviceptr dOut = new CUdeviceptr();
    cuMemAlloc(dOut, (long) n * Integer.BYTES);

    // Kernel params (by value → device)
    Pointer kernelParams = Pointer.to(
      Pointer.to(new int[]{width}),
      Pointer.to(new int[]{height}),
      Pointer.to(new double[]{xmin}),
      Pointer.to(new double[]{xmax}),
      Pointer.to(new double[]{ymin}),
      Pointer.to(new double[]{ymax}),
      Pointer.to(new int[]{maxIter}),
      Pointer.to(dOut)
    );

    // Launch config
    int blockX = 16, blockY = 16;
    int gridX  = (width  + blockX - 1) / blockX;
    int gridY  = (height + blockY - 1) / blockY;

    cuLaunchKernel(func,
      gridX, gridY, 1,
      blockX, blockY, 1,
      0, null, kernelParams, null);
    cuCtxSynchronize();

    // Copy back
    cuMemcpyDtoH(Pointer.to(hostOut), dOut, (long) n * Integer.BYTES);
    cuMemFree(dOut);
    return hostOut;
  }

  public void close() {
    cuModuleUnload(module);
    cuCtxDestroy(ctx);
  }
}