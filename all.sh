#!/usr/bin/env bash

set -e

IGNORE=""
while getopts "i:" opt; do
  case "$opt" in
    i) IGNORE="$OPTARG" ;;
    *) echo "Usage: $0 [-i submodule_name] <command>"; exit 1 ;;
  esac
done
shift $((OPTIND - 1))

if [ $# -eq 0 ]; then
  echo "Error: no command provided"
  exit 1
fi

CMD="$*"

git submodule foreach --recursive '
  if [ -n "'"$IGNORE"'" ] && \
     { [ "$name" = "'"$IGNORE"'" ] || [[ "$path" == *"'"$IGNORE"'"* ]]; }; then
    echo "Skipping submodule: $name"
    exit 0
  fi

  echo "Running in $name"
  '"$CMD"
