from abc import ABC, abstractmethod

from action import Action


class UI(ABC):
    def __init__(self, gameplay):
        self.__gameplay = gameplay

    @abstractmethod
    def do_turn(self, text, actions) -> Action:
        pass
