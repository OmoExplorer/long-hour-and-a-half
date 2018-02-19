from game import Game
from gameplay import Gameplay
from ui.ui_selector import UI_CLASS
from world import World


def reset_game(character, difficulty):
    gameplay = Gameplay(character, difficulty, World())
    ui = UI_CLASS(gameplay)
    Game(gameplay, ui)
