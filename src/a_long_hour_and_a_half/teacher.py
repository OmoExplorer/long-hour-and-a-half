from .enums import Difficulty
from .util import chance


class Teacher:
    def __init__(self, day):
        values = {
            Difficulty.EASY: (4, 35),
            Difficulty.MEDIUM: (3, 15),
            Difficulty.HARD: (2, 5)
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
            self._day.character.require_thought('Teacher has denied to go out.',
                                                "I'm not allowed to go out.")
        elif self.testing:
            self._day.character.require_thought("Teacher has denied to go out, because we're writing a test now.",
                                                "I'm not allowed to go out during a test.")
        else:
            self._day.character.require_thought('Yeah! I was allowed to go out!',
                                                'Yes! I can go out!')

        if not allow or self.testing:
            self._ask_toilet_attempts -= 1
            if self._ask_toilet_attempts < 1:
                self.punish_character()

        return allow

    def punish_character(self):
        dice = chance(50)
        if dice and not self._day.character.stay_after_lessons:
            self._day.character.stay_after_lessons = True
            self._day.character.require_thought('Oh no... I was told to stay after lessons!',
                                                'Damn! I will have to stay after lessons!')
        else:
            self._day.character.stay_on_break = True
            self._day.character.require_thought('Oh no... I was told to stay on the next break!',
                                                'Damn! I will have to stay on the next break!')

    def ask_character(self):
        self._day.character.require_thought("Oh! Teacher asked me to answer a question. I don't know what to answer.")
        self._day.character.block_holding(2)
