from .config import UI_CLASS
from .enums import StateMode
from .gamestate import GameState


class Game:
    def __init__(self):
        self.state = GameState(self)
        self.ui = UI_CLASS(self)

        self.mainloop()

    def mainloop(self):
        while self.state.mode != StateMode.END:
            self.ui.turn().do()
            self.state.tick()
