import atexit
import os

import colorama
from termcolor import colored

from .console_ui import create_bar
from .actions import wait_few_minutes, ask_to_go_out, hold, pee_in_wear, go_to_toilet, drink
from .gamestate import GameState
from .enums import StateMode, EASY, MEDIUM, HARD
from .util import chance, pass_
from .game import Game


# TODO: Refactor the whole file


def prepare_actions(day):
    if day.state == StateMode.LESSON:
        holding_blocked = day.character.holding_blocked

        if day.current_lesson() == 'PE' or holding_blocked:
            actions = [
                ('Wait 2 minutes', pass_),
                ('Wait some time...', wait_few_minutes),
                ('Ask to go out for toilet', ask_to_go_out)
                if not day.teacher.upset and not holding_blocked else None,
                (f'Pee in the {day.character.underwear.name.lower()}', pee_in_wear) if not holding_blocked else None,
                ('Drink', drink) if not holding_blocked else None
            ]
            return filter(lambda it: it is not None, actions)

        else:
            actions = [
                ('Wait 2 minutes', pass_),
                ('Wait some time...', wait_few_minutes),
                ('Ask to go out for toilet', ask_to_go_out) if not day.teacher.upset else None,
                ('Hold pee', hold),
                (f'Pee in the {day.character.underwear.name.lower()}',
                 pee_in_wear),
                ('Drink', drink)
            ]
            return filter(lambda it: it is not None, actions)

    elif day.state == StateMode.BREAK:
        return ('Wait 2 minutes', pass_), \
               ('Wait some time...', wait_few_minutes), \
               ('Go to toilet', go_to_toilet), \
               ('Hold pee', hold), \
               (f'Pee in the {day.character.underwear.name.lower()}', pee_in_wear), \
               ('Drink', drink)

    elif day.state == StateMode.BREAK_PUNISHMENT:
        return ('Wait 2 minutes', pass_), \
               ('Wait some time...', wait_few_minutes), \
               ('Hold pee', hold), \
               (f'Pee in the {day.character.underwear.name.lower()}', pee_in_wear), \
               ('Drink', drink)

    else:
        return ()


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

