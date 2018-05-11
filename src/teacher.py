from difficulty import Difficulty
from util import chance


class Teacher:
    @property
    def upset(self):
        return self.__ask_toilet_attempts < 1

    def __init__(self, day):
        values = {
            Difficulty.EASY: (4, 35),
            Difficulty.MEDIUM: (3, 15),
            Difficulty.HARD: (2, 5)
        }

        self.__ask_toilet_attempts, self.__toilet_allow_chance = values[day.difficulty]
        self.__day = day
        self.testing = False

    def ask_toilet(self):
        allow = chance(self.__toilet_allow_chance)

        if not allow:
            self.__day.character.require_thought(
                'Teacher has denied to go out.',
                "I'm not allowed to go out."
            )
        elif self.testing:
            self.__day.character.require_thought(
                "Teacher has denied to go out, because we're writing a test now.",
                "I'm not allowed to go out during a test."
            )
        else:
            self.__day.character.require_thought(
                'Yeah! I was allowed to go out!',
                'Yes! I can go out!'
            )

        if not allow or self.testing:
            self.__ask_toilet_attempts -= 1
            if self.__ask_toilet_attempts < 1:
                self.punish_character()

        return allow

    def punish_character(self):
        dice = chance(50)
        if dice and not self.__day.character.stay_after_lessons:
            self.__day.character.stay_after_lessons = True
            self.__day.character.require_thought(
                'Oh no... I was told to stay after lessons!',
                'Damn! I will have to stay after lessons!'
            )
        else:
            self.__day.character.stay_on_break = True
            self.__day.character.require_thought(
                'Oh no... I was told to stay on the next break!',
                'Damn! I will have to stay on the next break!'
            )

    def ask_character(self):
        self.__day.character.require_thought(
            "Oh! Teacher asked me to answer a question. I don't know what to answer."
        )
        self.__day.character.block_holding(2)
