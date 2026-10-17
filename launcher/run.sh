#!/usr/bin/env bash
set -e
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
JAR="$ROOT/dist/fate-algorithm-war.jar"
RUNTIME="$ROOT/runtime"

find_java() {
  if [ -x "$RUNTIME/bin/java" ]; then echo "$RUNTIME/bin/java"; return; fi
  if command -v java >/dev/null 2>&1; then command -v java; return; fi
  echo ""
}

JAVA_BIN="$(find_java)"
JAVA_MAJOR=0
if [ -n "$JAVA_BIN" ]; then
  JAVA_MAJOR="$($JAVA_BIN -version 2>&1 | sed -n '1s/.*version "\([0-9]*\).*/\1/p')"
fi

if [ -z "$JAVA_BIN" ] || [ "$JAVA_MAJOR" -lt 21 ]; then
  command -v curl >/dev/null 2>&1 || { echo "Necesitás curl para descargar Java 21."; exit 1; }
  mkdir -p "$RUNTIME"
  OS="linux"; ARCH="x64"
  case "$(uname -s)" in
    Darwin) OS="mac";;
  esac
  case "$(uname -m)" in
    aarch64|arm64) ARCH="aarch64";;
  esac
  URL="https://api.adoptium.net/v3/binary/latest/21/ga/$OS/$ARCH/jre/hotspot/normal/eclipse"
  TMP="$(mktemp -d)"
  curl -fL "$URL" -o "$TMP/java-runtime"
  if [ "$OS" = "mac" ]; then
    tar -xzf "$TMP/java-runtime" -C "$TMP"
  else
    tar -xzf "$TMP/java-runtime" -C "$TMP"
  fi
  FOUND="$(find "$TMP" -type f -path '*/bin/java' | head -1)"
  [ -n "$FOUND" ] || { echo "No se pudo encontrar el ejecutable Java descargado."; exit 1; }
  SRCROOT="$(cd "$(dirname "$FOUND")/.." && pwd)"
  cp -R "$SRCROOT/." "$RUNTIME/"
  JAVA_BIN="$RUNTIME/bin/java"
fi

exec "$JAVA_BIN" -jar "$JAR"
