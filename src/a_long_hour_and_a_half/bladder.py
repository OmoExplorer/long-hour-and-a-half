from random import randint

from .console_ui import BarStyle
from .enums import EASY, MEDIUM, HARD, FEMALE
from .util import clamp, chance, difficulty_dependent


class Bladder:
    """Character's urinary bladder."""

    def __init__(self, day, gender):
        values = {
            EASY: (1400, 2, 4),
            MEDIUM: (1200, 4, 6),
            HARD: (1000, 5, 7),
        }

        self.maximal_urine, *self._urine_income_bounds = values[day.difficulty]

        if gender == FEMALE:
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
        self._day.character.sphincter.die_if_bladder_is_too_full()

    @property
    def urine_decimal_ratio(self):
        """``urine / maximal_urine``"""
        return self.urine / self.maximal_urine

    @property
    def tummy_water(self):
        """Current volume of water in tummy in milliliters."""
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
        self._day.character.thinker.think_about_bladder_fullness()

        self._add_urine()

    def _add_urine(self):
        """Adds some urine."""
        self.urine += randint(*self._urine_income_bounds)

        if self.tummy_water > 0:
            self.tummy_water -= 3
            self.urine += 3

    @property
    def is_fullness_critical(self):
        return self.urine_decimal_ratio > 0.8

    @property
    def fullness_status(self):
        if 0.6 <= self.urine_decimal_ratio < 0.8:
            return BarStyle.WARNING
        if self.is_fullness_critical:
            return BarStyle.CRITICAL
        return BarStyle.NORMAL


class Sphincter:
    """Bladder's sphincter."""

    def __init__(self, day):
        values = {
            EASY: (10, 2, 6),
            MEDIUM: (15, 6, 12),  # TODO
            HARD: (20, 10, 16),
        }

        self._power = 100
        self._leaking_level, *self._leak_volume_bounds = values[day.difficulty]
        self.incontinence = 1
        self._day = day

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
        bladder = self._day.character.bladder
        if bladder.urine_decimal_ratio < 0.2:
            self.power += difficulty_dependent(self._day, 8, 6, 4)
        elif bladder.urine_decimal_ratio > 0.5:
            self.power -= 1.8 \
                          * self.incontinence \
                          * self._day.character.embarrassment \
                          * (bladder.urine / bladder.maximal_urine)

    def _check_leak(self):
        if self.power < self._leaking_level:
            leak_chance = (self._leaking_level - self.power) / self._leaking_level * 100
            if chance(leak_chance):
                self._leak()

    def _leak(self):
        volume = randint(*self._leak_volume_bounds)
        self._day.character.pee_into_wear(volume)

        self._day.character.thinker.think_about_leaking()

    def die_if_bladder_is_too_full(self):
        bladder = self._day.character.bladder
        if bladder.urine >= bladder.maximal_urine:
            self._day.character.sphincter.power = 0

    @property
    def is_power_critical(self):
        return self.power < 20

    @property
    def power_status(self):
        if 20 <= self.power < 40:
            return BarStyle.WARNING
        if self.is_power_critical:
            return BarStyle.CRITICAL
        return BarStyle.NORMAL
