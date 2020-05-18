#!/bin/zsh

mongoexport --db recflix --collection feedbacks --type csv -f userId,movieId,rating,createdAt --out ratings.csv
mongoexport --db recflix --collection userInteractions --type csv -f interactedBy,movieId,value,interactionTime --out userInteractions.csv