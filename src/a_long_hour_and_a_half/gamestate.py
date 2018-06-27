from collections import namedtuple
from random import sample

from .actions import *
from .character import Character
from .classmates import Classmates
from .enums import StateMode, MEDIUM
from .game_end import win
from .teacher import Teacher
from .time import Time
from .toilet import Toilet
from .util import chance

_Lesson = namedtuple('_Lesson', 'name timebounds')


def _generate_schedule():
    subjects = ['Math', 'PE', 'English', 'Spanish', 'Chemistry', 'Physics', 'Photography']

    picked_subjects = sample(subjects, 5)
    picked_subjects.insert(2, 'Break')
    picked_subjects.insert(5, 'Break ')  # With a space at the end to distinguish with the first break
    times = [(Time(7, 38), Time(8, 45)),
             (Time(8, 45), Time(9, 35)),
             (Time(9, 35), Time(10, 50)),
             (Time(10, 50), Time(11, 40)),
             (Time(11, 40), Time(12, 5)),
             (Time(12, 5), Time(12, 55)),
             (Time(12, 55), Time(13, 45))]

    return [_Lesson(*x) for x in zip(picked_subjects, times)]


class GameState:
    def __init__(self, game):
        self.game = game
        self.difficulty = MEDIUM
        self.schedule = _generate_schedule()
        self.time = Time(7, 40)
        self.character = Character(self)
        self.mode = StateMode.LESSON
        self.hot_day = chance(50)
        self.teacher = Teacher(self)
        self.classmates = Classmates(self)
        self.toilet = Toilet(self)
        self._previous_lesson = self.current_lesson()

        self.actions = self.update_actions()

    def tick(self):
        self.character.tick()
        # self.teacher.tick()

        self.time += Time(0, 2)
        if self.current_lesson() != self._previous_lesson:
            self._previous_lesson = self.current_lesson()
            self.teacher = Teacher(self)
            self.toilet = Toilet(self)
            if 'Break' in self.current_lesson():
                if self.character.stay_on_break:
                    self.mode = StateMode.BREAK_PUNISHMENT
                else:
                    self.mode = StateMode.BREAK
            elif self.current_lesson() == 'Day is over':
                win(self)
            else:
                self.mode = StateMode.LESSON
                self.character.stay_on_break = False

        def event_chance(chn):
            return self.mode == StateMode.LESSON \
                   and chance(chn) \
                   and not self.teacher.testing \
                   and self.current_lesson() != 'PE'

        if event_chance(5):
            self.teacher.ask_character()
        if event_chance(2):
            self.character.thinker.think_about_test()
            self.teacher.testing = True

        self.actions = self.update_actions()

    def update_actions(self):
        if self.mode == StateMode.LESSON:
            holding_blocked = self.character.holding_blocked

            if self.current_lesson() == 'PE' or holding_blocked:
                actions = [
                    wait_2_minutes, wait_few_minutes,
                    ask_to_go_out if not self.teacher.upset and not holding_blocked else None,
                    pee_in_wear if not holding_blocked else None,
                    drink if not holding_blocked else None
                ]
                return list(filter(lambda it: it is not None, actions))

            else:
                actions = [
                    wait_2_minutes, wait_few_minutes,
                    ask_to_go_out if not self.teacher.upset else None,
                    hold, pee_in_wear, drink
                ]
                return list(filter(lambda it: it is not None, actions))

        elif self.mode == StateMode.BREAK:
            return [wait_2_minutes, wait_few_minutes, go_to_toilet, hold, pee_in_wear, drink]

        elif self.mode == StateMode.BREAK_PUNISHMENT:
            return [wait_2_minutes, wait_few_minutes, hold, pee_in_wear, drink]

        else:
            return []

    def current_lesson(self):
        for i in range(len(self.schedule)):
            beginning_time, end_time = self.schedule[i][1]
            if beginning_time <= self.time <= end_time:
                return self.schedule[i][0]

        return 'Day is over'

    @property
    def time_until_lesson_finish(self):
        try:
            return next(filter(lambda l: l.name == self.current_lesson(), self.schedule)).timebounds[1] - self.time
        except StopIteration:
            return Time(0, 0)
