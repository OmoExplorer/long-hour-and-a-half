from enum import Enum, auto


class DayState(Enum):
    LESSON = auto()
    BREAK = auto()
    BREAK_PUNISHMENT = auto()
    END = auto()


class Difficulty(Enum):
    EASY = auto()
    MEDIUM = auto()
    HARD = auto()


class Gender(Enum):
    FEMALE = 'Female'
    MALE = 'Male'
