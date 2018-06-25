from random import randint

from .enums import EASY, MEDIUM, HARD, FEMALE
from .util import clamp, chance, difficulty_dependent


class Bladder:
    """Character's urinary bladder."""

    def __init__(self, day, character):
        values = {
            EASY: (1400, 2, 4),
            MEDIUM: (1200, 4, 6),
            HARD: (1000, 5, 7),
        }

        self.maximal_urine, *self._urine_income_bounds = values[day.difficulty]

        if character.gender == FEMALE:
            self.maximal_urine *= 0.9

        self._urine = randint(0, self.maximal_urine / 2)
        self._tummy_water = 0

        self._day = day

    @property
    def urine(self):
        """Current urine volume in milliliters."""
        return self._urine

    @urine.setter
    def urine(self, value):
        self._urine = clamp(value, 0, self.maximal_urine)
        self._check_maximal_urine()

    @property
    def urine_decimal_ratio(self):
        """``urine / maximal_urine``"""
        return self.urine / self.maximal_urine

    @property
    def tummy_water(self):
        """Current volume of water in tummy."""
        return self._tummy_water

    @tummy_water.setter
    def tummy_water(self, value):
        self._tummy_water = max(0, value)

    def empty(self):
        """Empties this bladder and requires a corresponding character thought."""
        self.urine = 0
        self._day.character.thinker.think_about_peeing()

    def tick(self):
        """Game element tick function."""
        self._day.character.thinker.think_about_fullness()

        self._add_urine()

    def _add_urine(self):
        """Adds some urine."""
        self.urine += randint(*self._urine_income_bounds)

        if self.tummy_water > 0:
            self.tummy_water -= 3
            self.urine += 3

    def _check_maximal_urine(self):
        if self.urine == self.maximal_urine:
            self._day.character.sphincter.power = 0


class Sphincter:
    """Bladder's sphincter."""

    def __init__(self, day, bladder):
        values = {
            EASY: (10, 2, 6),
            MEDIUM: (15, 6, 12),  # TODO
            HARD: (20, 10, 16),
        }

        self._power = 100
        self._leaking_level, *self._leak_volume_bounds = values[day.difficulty]
        self.incontinence = 1
        self._day = day
        self._bladder = bladder

    @property
    def power(self):
        """Power of this sphincter."""
        return self._power

    @power.setter
    def power(self, value):
        self._power = clamp(value, 0, 100)

    def tick(self):
        self._decrease_power()
        self._check_leak()

        self._day.character.thinker.think_about_low_sphincter_power()

    def _decrease_power(self):
        if self._bladder.urine_decimal_ratio < 0.2:
            self.power += difficulty_dependent(self._day, 8, 6, 4)
        elif self._bladder.urine_decimal_ratio > 0.5:
            self.power -= 1.8 \
                          * self.incontinence \
                          * self._day.character.embarrassment \
                          * (self._bladder.urine / self._bladder.maximal_urine)

    def _check_leak(self):
        if self.power < self._leaking_level:
            leak_chance = (self._leaking_level - self.power) / self._leaking_level * 100
            if chance(leak_chance):
                self._leak()

    def _leak(self):
        volume = randint(*self._leak_volume_bounds)
        self._day.character.pee_into_wear(volume)

        self._day.character.thinker.think_about_leaking()

