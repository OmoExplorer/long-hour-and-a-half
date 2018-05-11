from random import randint

from day_state import DayState
from difficulty import Difficulty
from util import clamp, chance, difficulty_dependent


class Bladder:
    """Character's urinary bladder."""

    @property
    def maximal_urine(self):
        """Maximal urine volume in milliliters that this bladder can store."""
        return self.__maximal_urine

    @property
    def urine(self):
        """Current urine volume in milliliters."""
        return self.__urine

    @urine.setter
    def urine(self, value):
        self.__urine = clamp(value, 0, self.maximal_urine)
        self.__check_maximal_urine()

    @property
    def urine_decimal_ratio(self):
        """``urine / maximal_urine``"""
        return self.urine / self.maximal_urine

    @property
    def tummy_water(self):
        """Current volume of water in tummy."""
        return self.__tummy_water

    @tummy_water.setter
    def tummy_water(self, value):
        self.__tummy_water = max(0, value)

    def empty(self):
        """Empties this bladder and requires a corresponding character thought."""
        self.urine = 0
        self.__day.character.require_thought(
            'Ahhhh... Sweet relief...',
            'Oh yeah... I finally peed!',
            'Yeahhhh... I was waiting for this for long.',
            'Ahhhh... Finally...'
        )

    def __init__(self, day, character):
        values = {
            Difficulty.EASY: (1750, 5, 8),
            Difficulty.MEDIUM: (1500, 7, 10),
            Difficulty.HARD: (1200, 9, 12),
        }

        self.__maximal_urine = values[day.difficulty][0]
        if character.gender == 'Female':
            self.__maximal_urine *= 0.9
        self.__urine = randint(0, self.maximal_urine / 2)
        self.__urine_income = values[day.difficulty][1:]
        self.__tummy_water = 0
        self.sphincter = Sphincter(day, self)

        self.__day = day

    def tick(self):
        """Game element tick function."""
        self.__think_about_fullness()

        self.add_urine()
        self.sphincter.tick()

    def __think_about_fullness(self):
        urine_ratio = self.urine_decimal_ratio
        if urine_ratio < 0.2 and self.__day.state == DayState.LESSON:
            if not self.__day.teacher.testing:
                self.__day.character.require_thought(
                    self.__day.current_lesson() + ' is so boring!',
                    "How much time is it? I can't wait for the lesson to finish!",
                    'Bla-bla-bla... the teacher is so boring!'
                )
            else:
                self.__day.character.require_thought(
                    "What the hell is this test?",
                    'This test is ridiculous.',
                    'Hmm... Maybe A)... no, D)... I dunno.'
                )
        elif 0.2 < urine_ratio < 0.35 and self.__day.state == DayState.LESSON:
            if not self.__day.teacher.testing:
                self.__day.character.require_thought(
                    self.__day.current_lesson() + ' is so boring!',
                    "I've got to pee a bit, but... Not really much.",
                    "How much time is it? I can't wait for the lesson to finish!",
                )
            else:
                self.__day.character.require_thought(
                    "What the hell is this test?",
                    'This test is ridiculous.',
                    'Hmm... Maybe A)... no, D)... I dunno.'
                )
        elif 0.35 < urine_ratio < 0.5:
            if self.__day.state == DayState.LESSON:
                if not self.__day.teacher.testing:
                    self.__day.character.require_thought(
                        self.__day.current_lesson() + ' is so boring! Also I need to pee.',
                        "I've got to pee a bit, but... Not really much.",
                        "When this lesson will be over? I've got to pee.",
                    )
                else:
                    self.__day.character.require_thought(
                        "What the hell is this test? That slight peeing urges ain't helping.",
                        'This test is ridiculous. And I have to pee a bit.',
                        'Hmm... Maybe A)... no, D)... I dunno.'
                    )
            else:
                self.__day.character.require_thought(
                    'I need to pee slightly.',
                    'I need to pee. Maybe use a restroom?',
                    "I've got to pee a bit, but... Not really much.",
                    "I've got to pee.",
                )
        elif 0.5 < urine_ratio < 0.65:
            self.__day.character.require_thought(
                'Ugh... I need to pee quite badly.',
                "I've got to pee! I'm somehow able to deal with it... at least for now...",
                "Damn, I've got to pee. That's annoying!",
            )
        elif 0.65 < urine_ratio < 0.8:
            self.__day.character.require_thought(
                'Argh! I need to pee badly!',
                'I need to pee pretty badly.',
                "I've got to pee! And this is a problem now!",
                "Damn, I've got to pee really badly!",
            )
        elif 0.8 < urine_ratio < 1:
            self.__day.character.require_thought(
                "Damn! I've got to pee REALLY badly!!!",
                "I need to pee NOW!",
                "I need to pee right NOW!",
                "Need to pee, need to pee, need to pee...",
                "Uhh! I've gotta go very badly! I don't know whether I'm able to hold it!!",
            )

    def add_urine(self):
        """Adds some urine."""
        min_urine_income = self.__urine_income[0]
        max_urine_income = self.__urine_income[1]
        self.urine += randint(min_urine_income, max_urine_income)

        if self.tummy_water > 0:
            self.tummy_water -= 3
            self.urine += 3

    def __check_maximal_urine(self):
        if self.urine == self.maximal_urine:
            self.sphincter.power = 0


class Sphincter:
    """Bladder's sphincter."""

    @property
    def power(self):
        """Power of this sphincter."""
        return self.__power

    @power.setter
    def power(self, value):
        self.__power = clamp(value, 0, 100)

    def __init__(self, day, bladder):
        values = {
            Difficulty.EASY: (10, 2, 6),
            Difficulty.MEDIUM: (15, 6, 12),  # TODO
            Difficulty.HARD: (20, 10, 16),
        }

        self.__power = 100
        self.__leaking_level, self.__leak_volume_bounds = values[day.difficulty][0], values[day.difficulty][1:]
        self.incontinence = 1
        self.__day = day
        self.__bladder = bladder

    def tick(self):
        self.__decrease_power()
        self.__check_leak()

        self.__think_about_low_sphincter_power()

    def __decrease_power(self):
        if self.__bladder.urine_decimal_ratio < 0.2:
            self.power += difficulty_dependent(self.__day, 8, 6, 4)
        elif self.__bladder.urine_decimal_ratio > 0.5:
            self.power -= 1.8 * \
                          self.incontinence * \
                          self.__day.character.embarrassment * \
                          (self.__bladder.urine / self.__bladder.maximal_urine)

    def __check_leak(self):
        if self.power < self.__leaking_level:
            leak_chance = (self.__leaking_level - self.power) / self.__leaking_level * 100
            if chance(leak_chance):
                self.__leak()

    def __leak(self):
        volume = randint(*self.__leak_volume_bounds)
        self.__day.character.pee_into_wear(volume)

        self.__day.character.require_thought(
            "Oops! I'm peeing!",
            "No, no, NO! I'm peeing!!!",
            "Uh-oh... I have leaked...",
            "Oops! I have leaked!",
            "Damn, I can't help it, I'm leaking!!",
            "Oh no, I have leaked! I won't last much time!",
            "I barely can hold it... Ooooh, I feel wet...",
        )

    def __think_about_low_sphincter_power(self):
        if self.power < 10:
            self.__day.character.require_thought(
                "Oops... It's coming!",
                "It's gotta come out!!!",
                "Pee is coming!",
                "Ouch! I can't hold it!",
            )

# if __name__ == '__main__':
#     Bladder()
