#!/bin/bash
# Author: Gaby Roch
#
#   extract branch stable in temporary directory and
#   push it in production server
#
# Local dependencies: 
#   git
#   rsync
# Remote dependencies:
#   rsync



set -e

HOST=amt-pianorgue
BRANCH=stable

if [ ! -z "$1" ]; then
  if [ "$1" = "--help" -o "$1" = "-h" ]; then
    echo "USAGE: "
    echo " $0 [branch]"
    echo "   Copie la branche <branch> vers le serveur de production"
    echo ""
    echo " S'il n'y a pas de branche définie, la branche stable est prise"
  else 
    BRANCH="$1"
  fi
fi


TEMPDIR=$(mktemp -d)

# Extrait la branche dans le répertoire temporaire (sans répertoire .git)
git clone -b "$BRANCH" .git "$TEMPDIR"
rm -fr "$TEMPDIR/.git"

## TODO Éxecute la suite de test

# Synchronise avec le serveur
rsync --delete -av "$TEMPDIR/" amt-pianorgue:"$BRANCH"

# Clean
rm -vfr "$TEMPDIR"


