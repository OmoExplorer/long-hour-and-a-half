from random import random

from .enums import EASY, MEDIUM, HARD


def chance(percent):
    return random() < percent / 100


def clamp(n, smallest, largest):
    return max(smallest, min(n, largest))


def difficulty_dependent(day, easy, medium, hard):
    return {EASY: easy, MEDIUM: medium, HARD: hard}[day.difficulty]


def pass_(_):
    """Does nothing. Used for waiting action."""
    pass
