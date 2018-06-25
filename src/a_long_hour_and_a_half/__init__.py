import atexit
import os

import colorama
from termcolor import colored

from .day import Day
from .enums import DayState, EASY, MEDIUM, HARD
from .util import chance, cls, pass_


# TODO: Refactor the whole file



def wait_few_minutes(day):
    while True:
        inp = input('How much minutes?\n'
                    '(Type "e" to wait until the lesson finish\n'
                    '"c" - until something goes bad\n'
                    '"b" - until a break) ').strip().lower()

        if not ((len(inp) == 1 and inp in 'ecb') or inp.isdigit()):
            continue

        if inp == 'e':
            to_wait = day.time_until_lesson_finish.raw_minutes + 1

            for _ in range(to_wait // 2 - 1):
                day.tick()
                if day.character.something_is_critical:
                    return

        if inp == 'c':
            while not day.character.something_is_critical:
                day.tick()

        if inp == 'b':
            while day.current_lesson().strip() != 'Break' and not day.character.something_is_critical:
                day.tick()

        if inp.isdigit():
            to_wait = int(inp)

            for _ in range(to_wait // 2 - 1):
                day.tick()
                if day.character.something_is_critical:
                    return

        return  # because we're in a endless loop


def create_bar(n, min_, max_, warning=False, critical=False):
    sum_sections = 20
    colored_sections = round(n / max_ * sum_sections)
    uncolored_sections = sum_sections - colored_sections
    bar = ''
    for i in range(colored_sections):
        bar += '█'
    for i in range(uncolored_sections):
        bar += '░'
    if critical:
        bar = colored(bar, 'red')
    elif warning:
        bar = colored(bar, 'yellow')
    return bar


def print_data(day):
    character = day.character
    bladder = character.bladder
    sphincter = character.sphincter

    print(character.name, ', ', character.gender, sep='')

    print(f"{day.character.name}'s thoughts\n", character.thoughts, '\n', sep='')

    print('Urine\t\t\t\t', bladder.urine, '/', bladder.maximal_urine, ' ml (',
          round(bladder.urine_decimal_ratio * 100), '%)\t',
          create_bar(bladder.urine_decimal_ratio, 0, 1,
                     0.6 <= bladder.urine_decimal_ratio < 0.8, bladder.is_fullness_critical), sep='')

    print('Tummy water\t\t\t', bladder.tummy_water, ' ml', sep='')

    print('Sphincter power\t\t\t', round(sphincter.power), '%\t\t\t',
          create_bar(sphincter.power, 0, 100,
                     20 <= sphincter.power < 40, sphincter.is_power_critical), sep='')

    print('Embarrassment\t\t\t', character.embarrassment, '%\t\t\t',
          create_bar(character.embarrassment, 0, 100), sep='')

    print('Thirst\t\t\t\t', round(character.thirst), '%\t\t\t', create_bar(character.thirst, 0, 100),
          colored('\tHot day: must drink more', 'yellow') if day.hot_day else '', sep='')

    print('Underwear\t\t\t', character.underwear, ', ', round(character.underwear.dryness), '/',
          round(character.underwear.maximal_dryness), ' ml\t',
          create_bar(character.underwear.dryness, 0, character.underwear.maximal_dryness,
                     character.underwear.dryness / character.underwear.maximal_dryness < 0.5,
                     character.underwear.is_dryness_critical), sep='')

    print('Outerwear\t\t\t', day.character.outerwear, ', ', round(day.character.outerwear.dryness), '/',
          round(day.character.outerwear.maximal_dryness), ' ml\t',
          create_bar(character.outerwear.dryness, 0, character.outerwear.maximal_dryness,
                     character.outerwear.dryness / character.outerwear.maximal_dryness < 0.5,
                     character.outerwear.is_dryness_critical), sep='')

    print('Time\t\t\t\t', day.time, f' ({day.time_until_lesson_finish} left)', sep='')

    print()

    print('Schedule\t\t\t',
          ',\n\t\t\t\t'.join(
              map(lambda x: x.name.strip() + ': \t' + str(x.timebounds[0]) + ' - ' + str(x.timebounds[1]),
                  day.schedule)), sep='')

    print()

    print('Lesson\t\t\t\t', day.current_lesson(), ' [Testing]' if day.teacher.testing else '', sep='')


def ask_to_go_out(day):
    if day.teacher.ask_toilet():
        day.character.bladder.empty()


def hold(day):
    day.character.bladder.sphincter.power += 10
    if chance(12):
        day.classmates.notice_holding()


def pee_in_wear(day):
    how_much = int(input('How much ml to pee? '))

    result_chances = {
        "can't pee": {
            EASY: 48,
            MEDIUM: 80,
            HARD: 120
        },
        'peeing more': {
            EASY: 48,
            MEDIUM: 80,
            HARD: 120
        },
        "can't stop": {
            EASY: 24,
            MEDIUM: 40,
            HARD: 60
        },
    }

    if chance(result_chances["can't stop"][day.difficulty] * day.character.bladder.urine_decimal_ratio):
        day.character.thinker.think_about_inability_to_stop_peeing()
        day.character.pee_into_wear(day.character.bladder.urine)
    elif chance(result_chances['peeing more'][day.difficulty] * day.character.bladder.urine_decimal_ratio):
        multiplier = {
            EASY: 1.2,
            MEDIUM: 1.4,
            HARD: 1.7,
        }
        day.character.pee_into_wear(how_much * multiplier[day.difficulty])
        day.character.thinker.think_about_peeing_more_than_intended()
    elif chance(result_chances["can't pee"][day.difficulty]):
        day.character.thinker.think_about_inability_to_start_peeing()
    else:
        day.character.pee_into_wear(how_much)


def go_to_toilet(day):
    day.toilet.use()


def drink(day):
    day.character.drink(day.character.thirst)


def prepare_actions(day):
    if day.state == DayState.LESSON:
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

    elif day.state == DayState.BREAK:
        return ('Wait 2 minutes', pass_), \
               ('Wait some time...', wait_few_minutes), \
               ('Go to toilet', go_to_toilet), \
               ('Hold pee', hold), \
               (f'Pee in the {day.character.underwear.name.lower()}', pee_in_wear), \
               ('Drink', drink)

    elif day.state == DayState.BREAK_PUNISHMENT:
        return ('Wait 2 minutes', pass_), \
               ('Wait some time...', wait_few_minutes), \
               ('Hold pee', hold), \
               (f'Pee in the {day.character.underwear.name.lower()}', pee_in_wear), \
               ('Drink', drink)

    else:
        return ()


def print_actions(actions):
    print()
    for n, action in enumerate(actions, 1):
        print(n, ': ', action[0], sep='')
    print()
    print('m: Main menu')
    print()


def main_menu(day):
    print('n: New game')
    print('s: Save')
    print('l: Load')
    print('r: Reset')
    print('q: Quit')
    ask_action(day)


def ask_action(day):
    actions = tuple(prepare_actions(day))
    print_actions(actions)

    action = input('> ')
    if action == 'm':
        main_menu(day)
    elif action == 'n':
        new_game()
    elif action == 's':
        save()
    elif action == 'l':
        load()
    elif action == 'r':
        main()
    elif action == 'q':
        exit()
    else:
        try:
            selected = int(action)
        except ValueError:
            selected = 0
        while (selected - 1) not in range(len(actions)):
            try:
                selected = int(input('> '))
            except ValueError:
                selected = 0

        actions[selected - 1][1](day)


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
    colorama.init()
    os.system('mode con: cols=110 lines=40')
    atexit.register(lambda: os.system('pause'))

    day = Day()
    day.tick()

    while day.state != DayState.END:
        cls()
        print_data(day)
        day.tick()
        ask_action(day)

