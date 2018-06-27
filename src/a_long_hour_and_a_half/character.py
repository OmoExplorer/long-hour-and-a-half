from .bladder import Bladder
from .sphincter import Sphincter
from .enums import FEMALE, EASY, MEDIUM, HARD
from .game_end import game_over
from .thinker import Thinker
from .util import clamp
from .wear import Wear


class Character:
    _THINK_DELAY = 5

    def __init__(self, state):
        self._state = state

        self.name = 'Jane'
        self.gender = FEMALE
        self.bladder = Bladder(state, self.gender)
        self.sphincter = Sphincter(state)
        self.thinker = Thinker(state)

        self.stay_after_lessons = False
        self.stay_on_break = False
        self._embarrassment = 1

        if state.difficulty == EASY:
            self._embarrassment_decay = 3
            self._thirst_increase = 1
        elif state.difficulty == MEDIUM:
            self._embarrassment_decay = 3
            self._thirst_increase = 1.5
        elif state.difficulty == HARD:
            self._embarrassment_decay = 3
            self._thirst_increase = 2

        self.underwear = Wear('Panties', 2, 4, 1)
        self.outerwear = Wear('Jeans', 7, 12, 1.2)
        self._thirst = 0
        self._holding_block_duration = 0

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

    def tick(self):
        self.thinker.tick()
        self.bladder.tick()
        self.sphincter.tick()
        self.underwear.tick()
        self.outerwear.tick()

        self.embarrassment -= self._embarrassment_decay
        self.thirst += self._thirst_increase

        self._holding_block_duration = max(0, self._holding_block_duration - 1)

        if self.embarrassment > 5:
            self.thinker.think_about_embarrassment()
        if self.thirst > 75:
            self.thinker.think_about_thirst()

    def pee_into_wear(self, how_much: int):
        self.bladder.urine -= how_much
        if self.underwear.dryness > how_much:
            self.underwear.dryness -= how_much
        else:
            how_much -= self.underwear.dryness
            self.underwear.dryness = 0
            self.outerwear.dryness -= how_much

        if self.outerwear.dryness == 0:
            game_over(self._state)

    def drink(self, how_much_percent):
        self.thirst -= how_much_percent
        self.bladder.tummy_water += how_much_percent * 3

    def block_holding(self, turns):
        self._holding_block_duration += turns

    @property
    def something_is_critical(self):
        return self.bladder.is_fullness_critical \
               or self.sphincter.is_power_critical \
               or self.underwear.is_dryness_critical \
               or self.outerwear.is_dryness_critical
