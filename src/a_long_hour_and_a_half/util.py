from random import random

from .enums import EASY, MEDIUM, HARD


def chance(percent):
    return random() < percent / 100


def clamp(n, smallest, largest):
    return max(smallest, min(n, largest))


def difficulty_dependent(day, easy, medium, hard):
    if day.difficulty == Difficulty.EASY:
        return easy
    if day.difficulty == Difficulty.MEDIUM:
        return medium
    if day.difficulty == Difficulty.HARD:
        return hard
