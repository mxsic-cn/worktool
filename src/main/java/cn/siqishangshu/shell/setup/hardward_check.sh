#!/bin/bash

set -e

mount | grep /data || exit 1

mount | grep /preserve || exit 2

true


