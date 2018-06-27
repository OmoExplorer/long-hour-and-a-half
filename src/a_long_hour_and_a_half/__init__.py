from .actions import wait_few_minutes, ask_to_go_out, hold, pee_in_wear, go_to_toilet, drink
from .console_ui import create_bar
from .enums import StateMode, EASY, MEDIUM, HARD
from .game import Game
from .gamestate import GameState
from .util import chance, pass_


# TODO: Refactor the whole file

def new_game():
    ...


def load_game():
    ...


def character_editor():
    ...


def main():
    # print('Welcome to A Long Hour and a Half!')
    # print('n: New game')
    # print('l: Load game')
    # print('c: Create character')

    # choice = input('> ').strip()
    # while len(choice) != 1 or choice not in 'nlc':
    #     choice = input('> ').strip()
    # if choice == 'n':
    #     new_game()
    # elif choice == 'l':
    #     load_game()
    # elif choice == 'c':
    #     character_editor()

    # day = GameState()
    # day.tick()
    #
    # while day.mode != StateMode.END:
    #     cls()
    #     print_data(day)
    #     day.tick()
    #     ask_action(day)
    Game()
