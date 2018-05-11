from random import random

from .enums import EASY, MEDIUM, HARD


def chance(percent):
    return random() < percent / 100


def clamp(n, smallest, largest):
    return max(smallest, min(n, largest))


def difficulty_dependent(day, easy, medium, hard):
    return {EASY: easy, MEDIUM: medium, HARD: hard}[day.difficulty]
    #
    # if day.difficulty == EASY:
    #     return easy
    # if day.difficulty == MEDIUM:
    #     return medium
    # if day.difficulty == HARD:
    #     return hard
