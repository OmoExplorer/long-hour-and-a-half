from util import clamp


class Wear:
    @property
    def dryness(self):
        return self.__dryness

    @dryness.setter
    def dryness(self, value):
        self.__dryness = clamp(value, 0, self.maximal_dryness)

    def __init__(self, name, pressure, absorption, drying):
        self.name = name
        self.pressure = pressure
        self.absorption = absorption
        self.drying = drying

        self.maximal_dryness = self.__dryness = absorption * 12.5

    def tick(self):
        self.__dry()

    def __dry(self):
        self.dryness += self.drying

    def __str__(self):
        return self.name
