from .enums import EASY, MEDIUM, HARD
from .util import chance


class Teacher:
    def __init__(self, day):
        values = {
            EASY: (4, 35),
            MEDIUM: (3, 15),
            HARD: (2, 5)
        }

        self._ask_toilet_attempts, self._toilet_allow_chance = values[day.difficulty]
        self._day = day
        self.testing = False

    @property
    def upset(self):
        return self._ask_toilet_attempts < 1

    def ask_toilet(self):
        allow = chance(self._toilet_allow_chance)

        if not allow:
            self._day.character.thinker.think_about_toilet_denial()
        elif self.testing:
            self._day.character.thinker.think_about_toilet_denial_during_test()
        else:
            self._day.character.thinker.think_about_toilet_approval()

        if not allow or self.testing:
            self._ask_toilet_attempts -= 1
            if self._ask_toilet_attempts < 1:
                self.punish_character()

        return allow

    def punish_character(self):
        dice = chance(50)
        if dice and not self._day.character.stay_after_lessons:
            self._day.character.stay_after_lessons = True
            self._day.character.thinker.think_about_staying_after_classes()
        else:
            self._day.character.stay_on_break = True
            self._day.character.thinker.think_about_staying_on_next_break()

    def ask_character(self):
        self._day.character.thinker.think_about_teacher_question()
        self._day.character.block_holding(2)
