from .console_ui import BarStyle
from .util import clamp


class Wear:
    def __init__(self, name, pressure, absorption, drying):
        self.name = name
        self.pressure = pressure
        self.absorption = absorption
        self.drying = drying

        self.maximal_dryness = self._dryness = absorption * 3.5

    def tick(self):
        self._dry()

    def _dry(self):
        self.dryness += self.drying

    @property
    def dryness(self):
        return self._dryness

    @dryness.setter
    def dryness(self, value):
        self._dryness = clamp(value, 0, self.maximal_dryness)

    def __str__(self):
        return self.name

    @property
    def is_dryness_critical(self):
        return self.dryness / self.maximal_dryness < 0.3

    @property
    def dryness_status(self):
        if self.dryness / self.maximal_dryness < 0.5:
            return BarStyle.WARNING
        if self.is_dryness_critical:
            return BarStyle.CRITICAL
        return BarStyle.NORMAL
