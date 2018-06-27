from .config import UI_CLASS
from .enums import StateMode, MEDIUM
from .gamestate import GameState


class Game:
    def __init__(self):
        self.state = GameState(self, MEDIUM)
        self.ui = UI_CLASS(self)

    def mainloop(self):
        while self.state.mode != StateMode.END:
            self.ui.update_data()
            self.state.tick()
            self.ui.get_action().do(self.state)
