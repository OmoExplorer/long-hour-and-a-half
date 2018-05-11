from random import sample

from .character import Character
from .classmates import Classmates
from .enums import DayState, Difficulty
from .game_results import win
from .teacher import Teacher
from .time import Time
from .toilet import Toilet
from .util import chance


def _generate_schedule():
    subjects = ('Math', 'PE', 'English', 'Spanish', 'Chemistry', 'Physics', 'Photography')

    picked_subjects = sample(subjects, 5)
    picked_subjects.insert(2, 'Break')
    picked_subjects.insert(5, 'Break ')
    times = (
        (Time(7, 38), Time(8, 45)),
        (Time(8, 45), Time(9, 35)),
        (Time(9, 35), Time(10, 50)),
        (Time(10, 50), Time(11, 40)),
        (Time(11, 40), Time(12, 5)),
        (Time(12, 5), Time(12, 55)),
        (Time(12, 55), Time(13, 45))
    )

    return [(x[0], x[1]) for x in zip(picked_subjects, times)]


class Day:
    def __init__(self):
        self.difficulty = Difficulty.MEDIUM
        self.schedule = _generate_schedule()
        self.time = Time(7, 40)
        self.character = Character(self)
        self.state = DayState.LESSON
        self.hot_day = chance(50)
        if self.hot_day:
            print('Day is hot!')
        self.teacher = Teacher(self)
        self.classmates = Classmates(self)
        self.toilet = Toilet(self)
        self._previous_lesson = self.current_lesson()

    def tick(self):
        self.character.tick()
        # self.teacher.tick()

        self.time += Time(0, 2)
        if self.current_lesson() != self._previous_lesson:
            self._previous_lesson = self.current_lesson()
            self.teacher = Teacher(self)
            if 'Break' in self.current_lesson():
                if self.character.stay_on_break:
                    self.state = DayState.BREAK_PUNISHMENT
                else:
                    self.state = DayState.BREAK
            elif self.current_lesson() == 'Day is over':
                win(self)
            else:
                self.state = DayState.LESSON
                self.character.stay_on_break = False

        if self.state == DayState.LESSON and chance(5) and not self.teacher.testing and self.current_lesson() != 'PE':
            self.teacher.ask_character()
        if self.state == DayState.LESSON and chance(5) and not self.teacher.testing and self.current_lesson() != 'PE':
            self.character.require_thought(
                'What? Teacher is giving us some test!',
                'Oops... Looks like we will write a test now.'
            )
            self.teacher.testing = True

    def current_lesson(self):  # FIXME: Buggy
        # noinspection PyShadowingNames
        # if self.time < self.schedule[0][1]:
        #     return self.schedule[0][0]

        for i in range(len(self.schedule)):
            beginning_time, end_time = self.schedule[i][1]
            if beginning_time <= self.time <= end_time:
                return self.schedule[i][0]

        return 'Day is over'

        # else:
        #     return 'Day is over'


if __name__ == '__main__':
    day = Day()
    print(day.schedule)
    while day.time < Time(13, 45):
        print(day.time, day.current_lesson())
        assert day.current_lesson() is not None
        day.time += Time(0, 1)
