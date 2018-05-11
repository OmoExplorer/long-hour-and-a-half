from random import randint

from .util import difficulty_dependent, chance


class Toilet:
    def __init__(self, day):
        self._day = day

        self._queue_chance = difficulty_dependent(day, 10, 25, 50)
        self._queue_duration_bounds = difficulty_dependent(day, (1, 5), (3, 10), (6, 30))

        self._lock_chance = difficulty_dependent(day, 0, 1.5, 5)

        self._locked = False
        self._queue_duration = 0

    def use(self):
        if self._queue_duration != 0:
            self._day.character.require_thought(
                'There is a big queue for toilets.',
                'Damn! There is a big queue for toilets.',
                'There is a lot of people there. All cabins are occupied.',
                'All cabins are occupied. Damn, I have to wait.',
            )
        elif self._locked:
            self._day.character.require_thought(
                'No! Toilets are closed!',
                'Damn, damn, damn! Toilets are out of order!',
                'Shit! Toilets are locked for all day!',
                'Uh oh. Toilets are out of order for all day.',
                'There is a note on the door: "Sorry! Out of order". Are they kidding?',
                'Are they kidding?! "Out of order"!'
            )
        elif chance(self._lock_chance):
            self._locked = True
        elif chance(self._queue_chance) and self._queue_duration == 0:
            self._queue_duration = randint(*self._queue_duration_bounds)
            self.use()
        else:
            self._day.character.bladder.empty()

    def tick(self):
        self._queue_duration = max(0, self._queue_duration - 2)
