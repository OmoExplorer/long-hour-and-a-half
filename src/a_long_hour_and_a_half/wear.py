from .util import clamp


class Wear:
    def __init__(self, name, pressure, absorption, drying):
        self.name = name
        self.pressure = pressure
        self.absorption = absorption
        self.drying = drying

        self.maximal_dryness = self._dryness = absorption * 12.5

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
