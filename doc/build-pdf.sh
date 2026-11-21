
#!/usr/bin/env bash
set -euo pipefail

source_file="mandelbrot-iteration-condition.tex"

cd -- "$(dirname -- "${BASH_SOURCE[0]}")"

if ! command -v pdflatex >/dev/null 2>&1; then
  echo "Error: pdflatex is not on PATH." >&2
  exit 1
fi

# Remove any stale cross-reference information.
rm -f -- "${source_file%.tex}.aux"

# Run twice: the first pass writes cross-references to the .aux file;
# the second pass reads them to resolve equation and other references.
pdflatex -interaction=nonstopmode -halt-on-error -file-line-error "$source_file"
pdflatex -interaction=nonstopmode -halt-on-error -file-line-error "$source_file"

echo "Created $(pwd)/${source_file%.tex}.pdf"
