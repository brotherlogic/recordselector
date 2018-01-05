#!/bin/bash
grep System.out * -Rl | grep .java$ | grep -v _test.go
RESULT=$?
if [ $RESULT != 1 ]; then
    exit 1
fi
