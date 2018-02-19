from tkinter import *

import game_manager
from action import Action
from game_metadata import GAME_NAME
from ui.game_ui import UI


class TkUI(UI):
    class __TkVars:
        pass

    def __init__(self, gameplay):
        super().__init__(gameplay)

        self.__tk_root = root = Toplevel()
        self.__setup_vars(root)
        del self.__setup_vars
        self.__construct(root)
        del self.__construct

    def __setup_vars(self, root):
        self.__tk_vars = tk_vars = TkUI.__TkVars()
        tk_vars.text = StringVar(root)
        tk_vars.bladder_fullness = IntVar(root)
        tk_vars.embarrassment = IntVar(root)
        tk_vars.tummy_water = IntVar(root)
        tk_vars.sphincter_power = IntVar(root)
        tk_vars.dryness = IntVar(root)

    def __construct(self, root):
        tk_vars = self.__tk_vars

        root.title(GAME_NAME)

        Label(root, variable=tk_vars.text).grid(row=1, column=1)
        Label(root, variable=tk_vars.bladder_fullness).grid(row=2, column=1)
        Label(root, variable=tk_vars.embarrassment).grid(row=3, column=1)
        Label(root, variable=tk_vars.tummy_water).grid(row=4, column=1)
        Label(root, variable=tk_vars.sphincter_power).grid(row=5, column=1)
        Label(root, variable=tk_vars.dryness).grid(row=6, column=1)
        Button(root, text='New game', command=game_manager.new_game).grid(row=7, column=1)
        Button(root, text='Reset',
               command=lambda: game_manager.reset_game(self.__gameplay.character, self.__gameplay.difficulty)) \
            .grid(row=7, column=2)
        Button(root, text='Save', command=lambda: game_manager.save_game(self.__gameplay)).grid(row=7, column=3)

        def load():
            self.__gameplay = game_manager.load_game()

        Button(root, text='Load', command=load).grid(row=7, column=4)
        Button(root, text='Quit', command=exit).grid(row=7, column=5)

    def do_turn(self, text, actions) -> Action:
        self.__update(self.__gameplay, text, actions)

    def __update(self, gameplay, text, actions):
        tk_vars = self.__tk_vars
        tk_vars.text = text
        tk_vars.bladder_status = gameplay.character.bladder.urine
        tk_vars.embarrassment = gameplay.character.embarrassment
        tk_vars.tummy_water = gameplay.character.tummy_water
        tk_vars.sphincter_power = gameplay.character.bladder.sphincter.power
        tk_vars.dryness = gameplay.character.dryness
