from .enums import EASY, MEDIUM, HARD
from .util import chance


class Teacher:
    def __init__(self, state):
        values = {
            EASY: (4, 35),
            MEDIUM: (3, 15),
            HARD: (2, 5)
        }

        self._ask_toilet_attempts, self._toilet_allow_chance = values[state.difficulty]
        self._state = state
        self.testing = False

    @property
    def upset(self):
        return self._ask_toilet_attempts < 1

    def ask_toilet(self):
        if self.testing:
            self._state.character.thinker.think_about_toilet_denial_during_test()
            return False

        allow = chance(self._toilet_allow_chance)

        if allow:
            self._state.character.thinker.think_about_toilet_approval()
        else:
            self._state.character.thinker.think_about_toilet_denial()

        if not allow or self.testing:
            self._ask_toilet_attempts -= 1
            if self._ask_toilet_attempts < 1:
                self.punish_character()

        return allow

    def punish_character(self):
        dice = chance(50)
        if dice and not self._state.character.stay_after_lessons:
            self._state.character.stay_after_lessons = True
            self._state.character.thinker.think_about_staying_after_classes()
        else:
            self._state.character.stay_on_break = True
            self._state.character.thinker.think_about_staying_on_next_break()

    def ask_character(self):
        self._state.character.thinker.think_about_teacher_question()
        self._state.character.block_holding(2)
