from enum import Enum, auto


class DayState(Enum):
    LESSON = auto()
    BREAK = auto()
    BREAK_PUNISHMENT = auto()
    END = auto()


EASY, MEDIUM, HARD = 'easy', 'medium', 'hard'

FEMALE, MALE = 'female', 'male'
