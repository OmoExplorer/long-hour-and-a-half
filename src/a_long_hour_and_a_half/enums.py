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


EASY, MEDIUM, HARD = Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD


class Gender(Enum):
    FEMALE = 'Female'
    MALE = 'Male'


FEMALE, MALE = Gender.FEMALE, Gender.MALE
