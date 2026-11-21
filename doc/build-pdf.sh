
#!/usr/bin/env bash
set -euo pipefail

cd -- "$(dirname -- "${BASH_SOURCE[0]}")"

if ! command -v pdflatex >/dev/null 2>&1; then
  echo "Error: pdflatex is not on PATH." >&2
  exit 1
fi

source_file=mandelbrot-iteration-condition.tex

pdflatex -interaction=nonstopmode -halt-on-error -file-line-error "$source_file"
pdflatex -interaction=nonstopmode -halt-on-error -file-line-error "$source_file"

echo "Created $(pwd)/${source_file%.tex}.pdf"
