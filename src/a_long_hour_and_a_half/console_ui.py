import atexit
import os
from enum import Enum, auto

import colorama
from termcolor import colored

from .ui import UI

__all__ = 'BarStyle ConsoleUI'


def pause():
    os.system('pause')


def clear_console():
    os.system('cls' if os.name == 'nt' else 'clear')


BAR_LENGTH_IN_SECTIONS = 20


class BarStyle(Enum):
    NORMAL = auto()
    WARNING = auto()
    CRITICAL = auto()


def create_bar(n, min_, max_, status=BarStyle.NORMAL):
    sum_sections = BAR_LENGTH_IN_SECTIONS
    colored_sections = round(n / max_ * sum_sections)
    uncolored_sections = sum_sections - colored_sections
    bar = ''
    for i in range(colored_sections):
        bar += '█'
    for i in range(uncolored_sections):
        bar += '░'
    if status == BarStyle.WARNING:
        bar = colored(bar, 'yellow')
    elif status == BarStyle.CRITICAL:
        bar = colored(bar, 'red')
    return bar


class ConsoleUI(UI):
    def __init__(self, game_state):
        super().__init__(game_state)

        colorama.init()
        os.system('mode con: cols=110 lines=40')
        atexit.register(pause)

    def turn(self):
        clear_console()
        self.update_character_stats()
        self.print_actions()
        return self.get_action()

    def update_character_stats(self):
        stt = self._game.state

        character = stt.character
        bladder = character.bladder
        sphincter = character.sphincter

        print(character.name, ', ', character.gender, sep='')

        print(f"{character.name}'s thoughts\n", character.thoughts, '\n', sep='')

        print('Urine\t\t\t\t', bladder.urine, '/', bladder.maximal_urine, ' ml (',
              round(bladder.urine_decimal_ratio * 100), '%)\t',
              create_bar(bladder.urine_decimal_ratio, 0, 1, bladder.fullness_status), sep='')

        print('Tummy water\t\t\t', bladder.tummy_water, ' ml', sep='')

        print('Sphincter power\t\t\t', round(sphincter.power), '%\t\t\t',
              create_bar(sphincter.power, 0, 100, sphincter.is_power_critical), sep='')

        print('Embarrassment\t\t\t', character.embarrassment, '%\t\t\t',
              create_bar(character.embarrassment, 0, 100), sep='')

        print('Thirst\t\t\t\t', round(character.thirst), '%\t\t\t', create_bar(character.thirst, 0, 100),
              colored('\tHot day: must drink more', 'yellow') if stt.hot_day else '', sep='')

        print('Underwear\t\t\t', character.underwear, ', ', round(character.underwear.dryness), '/',
              round(character.underwear.maximal_dryness), ' ml\t',
              create_bar(character.underwear.dryness, 0, character.underwear.maximal_dryness,
                         character.underwear.dryness_status), sep='')

        print('Outerwear\t\t\t', character.outerwear, ', ', round(character.outerwear.dryness), '/',
              round(stt.character.outerwear.maximal_dryness), ' ml\t',
              create_bar(character.outerwear.dryness, 0, character.outerwear.maximal_dryness,
                         character.outerwear.dryness_status), sep='')

        print('Time\t\t\t\t', stt.time, f' ({stt.time_until_lesson_finish} left)', sep='')

        print()

        print('Schedule\t\t\t',
              ',\n\t\t\t\t'.join(
                  map(lambda x: x.name.strip() + ': \t' + str(x.timebounds[0]) + ' - ' + str(x.timebounds[1]),
                      stt.schedule)), sep='')

        print()

        print('Lesson\t\t\t\t', stt.current_lesson(), ' [Testing]' if stt.teacher.testing else '', sep='')

    def print_actions(self):
        print()
        for n, action in enumerate(self._game.state.actions, 1):
            print(n, ': ', action[0], sep='')
        print()
        print('m: Main menu')
        print()

    def get_action(self):
        actions = self._game.state.actions
        while True:
            inp = input('> ')
            if len(inp) == 1 and inp in 'mnslrq':
                self.show_main_menu(inp)
                continue
            try:
                inp = int(inp)
            except:
                continue

            if inp not in range(1, len(actions) + 1):
                continue

            return actions[inp - 1]

    def show_main_menu(self, inp):
        if inp == 'm':
            print('n: New game')
            print('s: Save')
            print('l: Load')
            print('r: Reset')
            print('q: Quit')
        elif inp == 'n':
            ...
        elif inp == 's':
            ...
        elif inp == 'l':
            ...
        elif inp == 'r':
            ...
        elif inp == 'q':
            atexit.unregister(pause)
            exit()

    input = input

    def show_message(self, text):
        if text.endswith('failed!'):
            quote, fail = text.split('\n')
            print('\n\n')
            print('"' + colored(quote, 'red', attrs=['bold']) + '"')
            print('\n\n')
            print(colored(fail, 'yellow', 'on_red', ['bold']))
        elif text.endswith('won!'):
            print(colored('You won!', 'blue', 'on_green', ['bold']))
        else:
            print('\n\n' + text + '\n\n')
