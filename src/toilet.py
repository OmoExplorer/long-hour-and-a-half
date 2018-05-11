from random import randint

from util import difficulty_dependent, chance


class Toilet:
    def __init__(self, day):
        self.__day = day

        self.__queue_chance = difficulty_dependent(day, 10, 25, 50)
        self.__queue_duration_bounds = difficulty_dependent(day, (1, 5), (3, 10), (6, 30))

        self.__lock_chance = difficulty_dependent(day, 0, 1.5, 5)

        self.__locked = False
        self.__queue_duration = 0

    def use(self):
        if self.__queue_duration != 0:
            self.__day.character.require_thought(
                'There is a big queue for toilets.',
                'Damn! There is a big queue for toilets.',
                'There is a lot of people there. All cabins are occupied.',
                'All cabins are occupied. Damn, I have to wait.',
            )
        elif self.__locked:
            self.__day.character.require_thought(
                'No! Toilets are closed!',
                'Damn, damn, damn! Toilets are out of order!',
                'Shit! Toilets are locked for all day!',
                'Uh oh. Toilets are out of order for all day.',
                'There is a note on the door: "Sorry! Out of order". Are they kidding?',
                'Are they kidding?! "Out of order"!'
            )
        elif chance(self.__lock_chance):
            self.__locked = True
        elif chance(self.__queue_chance) and self.__queue_duration == 0:
            self.__queue_duration = randint(*self.__queue_duration_bounds)
            self.use()
        else:
            self.__day.character.bladder.empty()

    def tick(self):
        self.__queue_duration = max(0, self.__queue_duration - 2)
