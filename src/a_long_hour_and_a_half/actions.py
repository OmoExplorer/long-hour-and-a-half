from collections import namedtuple

from .enums import EASY, MEDIUM, HARD
from .util import chance

Action = namedtuple('Action', 'desc do')


def do_wait_few_minutes(state):
    ui = state.game.ui
    while True:
        inp = ui.input('How much minutes?\n'
                       '(Type "e" to wait until the lesson finish\n'
                       '"c" - until something goes bad\n'
                       '"b" - until a break) ').strip().lower()

        if not ((len(inp) == 1 and inp in 'ecb') or inp.isdigit()):
            continue

        if inp == 'e':
            to_wait = state.time_until_lesson_finish.raw_minutes + 1

            for _ in range(to_wait // 2 - 1):
                state.tick()
                if state.character.something_is_critical:
                    return

        if inp == 'c':
            while not state.character.something_is_critical:
                state.tick()

        if inp == 'b':
            while state.current_lesson().strip() != 'Break' and not state.character.something_is_critical:
                state.tick()

        if inp.isdigit():
            to_wait = int(inp)

            for _ in range(to_wait // 2 - 1):
                state.tick()
                if state.character.something_is_critical:
                    return

        return  # because we're in a endless loop


wait_few_minutes = Action('Wait few minutes', do_wait_few_minutes)


def do_ask_to_go_out(state):
    if state.teacher.ask_toilet():
        state.character.bladder.empty()


ask_to_go_out = Action('Ask to go out', do_ask_to_go_out)


def do_hold(state):
    state.character.bladder.sphincter.power += 10
    if chance(12):
        state.classmates.notice_holding()


hold = Action('Hold it', do_hold)


def do_pee_in_wear(state):
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

    if chance(result_chances["can't stop"][state.difficulty] * state.character.bladder.urine_decimal_ratio):
        state.character.thinker.think_about_inability_to_stop_peeing()
        state.character.pee_into_wear(state.character.bladder.urine)
    elif chance(result_chances['peeing more'][state.difficulty] * state.character.bladder.urine_decimal_ratio):
        multiplier = {
            EASY: 1.2,
            MEDIUM: 1.4,
            HARD: 1.7,
        }
        state.character.pee_into_wear(how_much * multiplier[state.difficulty])
        state.character.thinker.think_about_peeing_more_than_intended()
    elif chance(result_chances["can't pee"][state.difficulty]):
        state.character.thinker.think_about_inability_to_start_peeing()
    else:
        state.character.pee_into_wear(how_much)


pee_in_wear = Action('Pee right where you are', do_pee_in_wear)

go_to_toilet = Action('Use toilet', lambda state: state.toilet.use())

drink = Action('Drink', lambda state: state.character.drink(state.character.thirst))
