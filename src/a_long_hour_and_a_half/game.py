from .config import UI_CLASS
from .gamestate import GameState


class Game:
    def __init__(self):
        self.state = GameState(self)
        self.ui = UI_CLASS(self)
