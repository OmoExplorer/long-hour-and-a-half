from random import randint

from .console_ui import BarStyle
from .enums import EASY, MEDIUM, HARD, FEMALE
from .util import clamp


class Bladder:
    """Character's urinary bladder."""

    def __init__(self, state, gender):
        values = {
            EASY: (1400, 2, 4),
            MEDIUM: (1200, 4, 6),
            HARD: (1000, 5, 7),
        }

        self.maximal_urine, *self._urine_income_bounds = values[state.difficulty]

        if gender == FEMALE:
            self.maximal_urine *= 0.9

        self._urine = randint(0, self.maximal_urine / 2)
        self._tummy_water = 0

        self._state = state

    @property
    def urine(self):
        """Current urine volume in milliliters."""
        return self._urine

    @urine.setter
    def urine(self, value):
        self._urine = clamp(value, 0, self.maximal_urine)
        self._state.character.sphincter.die_if_bladder_is_too_full()

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
        self._state.character.thinker.think_about_peeing()

    def tick(self):
        """Game element tick function."""
        self._state.character.thinker.think_about_bladder_fullness()

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
