from random import randint

from .console_ui import BarStyle
from .enums import EASY, MEDIUM, HARD
from .toilet import chance
from .util import clamp, difficulty_dependent


class Sphincter:
    """Bladder's sphincter."""

    def __init__(self, state):
        values = {
            EASY: (10, 2, 6),
            MEDIUM: (15, 6, 12),  # TODO
            HARD: (20, 10, 16),
        }

        self._power = 100
        self._leaking_level, *self._leak_volume_bounds = values[state.difficulty]
        self.incontinence = 1
        self._state = state

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

        self._state.character.thinker.think_about_low_sphincter_power()

    def _decrease_power(self):
        bladder = self._state.character.bladder
        if bladder.urine_decimal_ratio < 0.2:
            self.power += difficulty_dependent(self._state, 8, 6, 4)
        elif bladder.urine_decimal_ratio > 0.5:
            self.power -= 1.8 \
                          * self.incontinence \
                          * self._state.character.embarrassment \
                          * (bladder.urine / bladder.maximal_urine)

    def _check_leak(self):
        if self.power < self._leaking_level:
            leak_chance = (self._leaking_level - self.power) / self._leaking_level * 100
            if chance(leak_chance):
                self._leak()

    def _leak(self):
        volume = randint(*self._leak_volume_bounds)
        self._state.character.pee_into_wear(volume)

        self._state.character.thinker.think_about_leaking()

    def die_if_bladder_is_too_full(self):
        bladder = self._state.character.bladder
        if bladder.urine >= bladder.maximal_urine:
            self._state.character.sphincter.power = 0

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
