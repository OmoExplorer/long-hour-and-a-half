from logging import getLogger, basicConfig, DEBUG
from random import randint

from .util import chance

basicConfig(level=DEBUG)
_logger = getLogger('lhh.Leaker')


class Leaker:
    def __init__(self, state):
        self._state = state

    @property
    def _fullness_threshold(self):
        return self._state.character.bladder.urine * 0.7

    @property
    def leak_chance(self):
        bladder = self._state.character.bladder
        chn = (bladder.urine - self._fullness_threshold) / 2
        _logger.debug('chance: %f', chn)
        return chn

    @property
    def minimal_leak_volume(self):
        return max(1, self._state.character.bladder.urine ** 2 // 35000 - 23)

    @property
    def maximal_leak_volume(self):
        return max(0, self._state.character.bladder.urine ** 2 // 20000 - 32)

    def get_leak_volume(self):
        return randint(self.minimal_leak_volume, self.maximal_leak_volume)

    def tick(self):
        if chance(self.leak_chance):
            volume = self.get_leak_volume()
            _logger.debug('leaked %i ml', volume)
            self._state.character.pee_into_wear(volume)
            self._state.character.thinker.think_about_leaking()
