from random import choice

from .bladder import Bladder
from .enums import Difficulty
from .game_results import game_over
from .util import clamp
from .wear import Wear


class Character:
    _THINK_DELAY = 5

    @property
    def embarrassment(self):
        return self._embarrassment

    @embarrassment.setter
    def embarrassment(self, value):
        self._embarrassment = clamp(value, 1, 100)

    @property
    def thirst(self):
        return self._thirst

    @thirst.setter
    def thirst(self, value):
        self._thirst = clamp(value, 0, 100)
        if self.thirst == 100:
            self.drink(100)

    @property
    def holding_blocked(self):
        return self._holding_block_duration != 0

    def __init__(self, day):
        self._day = day

        self.name = 'Jane'
        self.gender = 'Female'
        self.bladder = Bladder(day, self)
        self.stay_after_lessons = False
        self.stay_on_break = False
        self._embarrassment = 1
        self.thoughts = ''

        if day.difficulty == Difficulty.EASY:
            self._embarrassment_decay = 3
            self._thirst_increase = 1
        elif day.difficulty == Difficulty.MEDIUM:
            self._embarrassment_decay = 3
            self._thirst_increase = 1.5
        elif day.difficulty == Difficulty.HARD:
            self._embarrassment_decay = 3
            self._thirst_increase = 2

        self.underwear = Wear('Panties', 2, 4, 1)
        self.outerwear = Wear('Jeans', 7, 12, 1.2)
        self._thirst = 0
        self._holding_block_duration = 0

    def tick(self):
        self.thoughts = ''

        self.bladder.tick()
        self.underwear.tick()
        self.outerwear.tick()

        self.embarrassment -= self._embarrassment_decay
        self.thirst += self._thirst_increase

        self._holding_block_duration = max(0, self._holding_block_duration - 1)

        self._think_about_embarrassment()
        self._think_about_thirst()

    def pee_into_wear(self, how_much: int):
        self.bladder.urine -= how_much
        if self.underwear.dryness > how_much:
            self.underwear.dryness -= how_much
        else:
            how_much -= self.underwear.dryness
            self.underwear.dryness = 0
            self.outerwear.dryness -= how_much

        if self.outerwear.dryness == 0:
            game_over(self._day)

    def drink(self, how_much_percent: int):
        self.thirst -= how_much_percent
        self.bladder.tummy_water += how_much_percent * 3

    def block_holding(self, turns):
        self._holding_block_duration += turns

    def require_thought(self, *thoughts):
        self.thoughts += '\n' + choice(thoughts)

    def _think_about_embarrassment(self):
        if self.embarrassment > 5:
            self.require_thought(
                "Oh... I'm so embarrassed...",
                'I feel embarrassed...',
                "Classmates know that I have to pee. That's very bad."
            )

    def _think_about_thirst(self):
        if self.thirst > 75:
            self.require_thought(
                "I'm thirsty.",
                "I'm so thirsty...",
                "I want to drink.",
                "I need to drink."
            )
