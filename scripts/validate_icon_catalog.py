#!/usr/bin/env python3
"""Validate demo icon catalog references and drawable resources."""

from __future__ import annotations

import pathlib
import re
import sys

ROOT = pathlib.Path(__file__).resolve().parents[1]
SYMBOLS_FILE = ROOT / "composeApp/src/commonMain/kotlin/com/bernaferrari/diagonalwipeicon/demo/MaterialSymbolIcons.kt"
CATALOG_FILE = ROOT / "composeApp/src/commonMain/kotlin/com/bernaferrari/diagonalwipeicon/demo/MaterialWipeIconCatalog.kt"
DRAWABLE_DIR = ROOT / "composeApp/src/commonMain/composeResources/drawable"

SYMBOL_DECL_RE = re.compile(
    r"^\s*val\s+([A-Za-z0-9_]+)\s*=\s*DrawableWipeIcon\(Res\.drawable\.([A-Za-z0-9_]+)\)\s*$"
)
CATALOG_PAIR_RE = re.compile(
    r"MaterialWipeIconPair\(\s*\"([^\"]+)\"\s*,\s*MaterialSymbolIcons\.([A-Za-z0-9_]+)\s*,\s*MaterialSymbolIcons\.([A-Za-z0-9_]+)"
)


def parse_symbols() -> dict[str, str]:
    mapping: dict[str, str] = {}
    for line in SYMBOLS_FILE.read_text(encoding="utf-8").splitlines():
        m = SYMBOL_DECL_RE.match(line)
        if not m:
            continue
        symbol_name = m.group(1)
        drawable_name = m.group(2)
        mapping[symbol_name] = drawable_name
    return mapping


def parse_catalog_pairs() -> list[tuple[str, str, str]]:
    content = CATALOG_FILE.read_text(encoding="utf-8")
    return CATALOG_PAIR_RE.findall(content)


def drawable_exists(drawable_name: str) -> bool:
    return (DRAWABLE_DIR / f"{drawable_name}.xml").exists()


def main() -> int:
    symbols = parse_symbols()
    pairs = parse_catalog_pairs()

    errors: list[str] = []

    if not symbols:
        errors.append("No MaterialSymbolIcons entries parsed.")
    if not pairs:
        errors.append("No MaterialWipeIconPair entries parsed from catalog.")

    seen_labels: set[str] = set()

    for label, enabled_symbol, disabled_symbol in pairs:
        if label in seen_labels:
            errors.append(f"Duplicate catalog label: {label}")
        seen_labels.add(label)

        if enabled_symbol not in symbols:
            errors.append(f"Unknown enabled symbol '{enabled_symbol}' for '{label}'")
        if disabled_symbol not in symbols:
            errors.append(f"Unknown disabled symbol '{disabled_symbol}' for '{label}'")

        if enabled_symbol == disabled_symbol:
            errors.append(f"Enabled/disabled symbol is identical for '{label}': {enabled_symbol}")

        if enabled_symbol in symbols and not drawable_exists(symbols[enabled_symbol]):
            errors.append(
                f"Missing drawable file for enabled symbol '{enabled_symbol}': {symbols[enabled_symbol]}.xml"
            )
        if disabled_symbol in symbols and not drawable_exists(symbols[disabled_symbol]):
            errors.append(
                f"Missing drawable file for disabled symbol '{disabled_symbol}': {symbols[disabled_symbol]}.xml"
            )

    if errors:
        print("ICON CATALOG VALIDATION FAILED")
        for err in errors:
            print(f"- {err}")
        return 1

    print(
        f"OK: validated {len(pairs)} catalog pairs against {len(symbols)} MaterialSymbolIcons entries and drawable files."
    )
    return 0


if __name__ == "__main__":
    sys.exit(main())
