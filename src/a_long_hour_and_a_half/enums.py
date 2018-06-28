from enum import Enum, auto


class StateMode(Enum):
    LESSON = auto()
    BREAK = auto()
    BREAK_PUNISHMENT = auto()


EASY, MEDIUM, HARD = 'easy', 'medium', 'hard'

FEMALE, MALE = 'female', 'male'
