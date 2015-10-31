#!/usr/bin/env bash

read horses
strengths=()
for (( i=0; i<horses; i++ )); do
    read strength
    strengths[i]=$strength
done

function printStrengths() {
    echo "Array items:" >&2
    array=("${!1}")
    for item in ${array[@]}
    do
        printf "%s\n" $item >&2
    done
}

function debug() {
    printf "%s: %s\n" $1 $2 >&2
}

sortedStrengths=( $( printf "%s\n" "${strengths[@]}" | sort -n ) )

smallestDelta="-1"
for i in `seq 1 $(( $horses - 1 ))`;
do
    previousIndex=$(( $i - 1 ))
    s1=${sortedStrengths[$previousIndex]}
    s2=${sortedStrengths[$i]}
    delta=$(( s2 - s1 ))
    if [ $smallestDelta -lt 0 ] || [ $delta -lt $smallestDelta ]
    then
        smallestDelta=$delta;
    fi
done

echo "$smallestDelta"