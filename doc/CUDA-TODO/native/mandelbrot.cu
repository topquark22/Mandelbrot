// Compile to PTX: nvcc -ptx native/mandelbrot.cu -o native/mandelbrot.ptx
// Optionally pick an arch, e.g. -arch=compute_86 . PTX will JIT on load.

extern "C" __global__
void mandelbrotKernel(
  int width, int height,
  double xmin, double xmax, double ymin, double ymax,
  int maxIter,
  int *outIter // length = width*height
){
  int x = blockIdx.x * blockDim.x + threadIdx.x;
  int y = blockIdx.y * blockDim.y + threadIdx.y;
  if (x >= width || y >= height) return;

  // Map pixel → complex plane
  double cx = xmin + (x / (double)(width  - 1)) * (xmax - xmin);
  double cy = ymin + (y / (double)(height - 1)) * (ymax - ymin);

  // Optional cardioid / bulb short-circuits
  double q = (cx - 0.25)*(cx - 0.25) + cy*cy;
  if (q * (q + (cx - 0.25)) < 0.25*cy*cy) { outIter[y*width + x] = maxIter; return; }
  double dx = cx + 1.0; double r2 = dx*dx + cy*cy;
  if (r2 < 0.0625) { outIter[y*width + x] = maxIter; return; }

  // Iterate z <- z^2 + c
  double zx = 0.0, zy = 0.0;
  int iter = 0;
  while (zx*zx + zy*zy <= 4.0 && iter < maxIter) {
    double zx2 = zx*zx - zy*zy + cx;
    zy = 2.0*zx*zy + cy;
    zx = zx2;
    iter++;
  }
  outIter[y*width + x] = iter;
}