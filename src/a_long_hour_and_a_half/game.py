from .day import Day
from .day_state import DayState
from .difficulty import Difficulty
from .util import chance


def wait(_):
    pass


def wait_few(day: Day):
    for _ in range(int(int(input('How much minutes?')) / 2) - 1):
        day.tick()


def print_data(day: Day):
    print(day.character.name)
    print(day.character.gender)
    print(f"{day.character.name}'s thoughts\n", day.character.thoughts, '\n', sep='')
    print('Urine\t\t\t\t', day.character.bladder.urine, '/', day.character.bladder.maximal_urine, ' ml (',
          round(day.character.bladder.urine_decimal_ratio * 100), '%)', sep='')
    print('Tummy water\t\t\t', day.character.bladder.tummy_water, ' ml', sep='')
    print('Sphincter power\t\t', round(day.character.bladder.sphincter.power), '%', sep='')
    print('Embarrassment\t\t', day.character.embarrassment, '%', sep='')
    print('Thirst\t\t\t\t', round(day.character.thirst), '%', sep='')
    print('Underwear\t\t\t', day.character.underwear, ', ', round(day.character.underwear.dryness), '/',
          round(day.character.underwear.maximal_dryness), ' ml', sep='')
    print('Outerwear\t\t\t', day.character.outerwear, ', ', round(day.character.outerwear.dryness), '/',
          round(day.character.outerwear.maximal_dryness), ' ml', sep='')
    print('Time\t\t\t\t', day.time, sep='')
    print('Schedule\t\t\t',
          ',\n\t\t\t\t\t'.join(map(lambda x: x[0].strip() + ': ' + str(x[1][0]) + '-' + str(x[1][1]), day.schedule)),
          sep='')
    print('Lesson\t\t\t\t', day.current_lesson(), ' [Testing]' if day.teacher.testing else '', sep='')


def ask_to_go_out(day: Day):
    if day.teacher.ask_toilet():
        day.character.bladder.empty()


def hold(day, power_increase, notice_chance):
    day.character.bladder.sphincter.power += power_increase
    if chance(notice_chance):
        day.classmates.notice_holding()


def press_on_crotch(day: Day):
    hold(day, 13, 15)


def rub_thighs(day: Day):
    hold(day, 1.5, 3)


def fidget(day: Day):
    hold(day, 3, 1.5)


def lean_forward(day: Day):
    hold(day, 4, 4)


def cross_legs(day: Day):
    hold(day, 15, 10)


def pee_in_wear(day: Day):
    how_much = int(input('How much to pee?'))

    result_chances = {
        "can't pee": {
            Difficulty.EASY: 48,
            Difficulty.MEDIUM: 80,
            Difficulty.HARD: 120
        },
        'peeing more': {
            Difficulty.EASY: 48,
            Difficulty.MEDIUM: 80,
            Difficulty.HARD: 120
        },
        "can't stop": {
            Difficulty.EASY: 24,
            Difficulty.MEDIUM: 40,
            Difficulty.HARD: 60
        },
    }

    if chance(result_chances["can't stop"][day.difficulty] * day.character.bladder.urine_decimal_ratio):
        day.character.pee_into_wear(day.character.bladder.urine)
        day.character.require_thought(
            "Aaaaaah!!! I can't stop!",
            "Aaaaaah!!! I can't stop peeing!",
            "Aaaaaah!!! I can't stop the flow!",
            "Aaaaaah!!! I can't stop it!",
        )
    elif chance(result_chances['peeing more'][day.difficulty] * day.character.bladder.urine_decimal_ratio):
        multiplier = {
            Difficulty.EASY: 1.2,
            Difficulty.MEDIUM: 1.4,
            Difficulty.HARD: 1.7,
        }
        day.character.pee_into_wear(how_much * multiplier[day.difficulty])
        day.character.require_thought(
            "Damn! I peed a bit much than I was going to.",
            "Uhhh! I hardly stopped peeing!",
            "Uhhh! I hardly stopped the flow!",
            "Ohhh... It was hard to stop.",
        )
    elif chance(result_chances["can't pee"][day.difficulty]):
        day.character.require_thought(
            "I can't start peeing.",
            "I'm trying to pee, but I can't.",
            "I can't force myself to pee right here and now.",
        )
    else:
        day.character.pee_into_wear(how_much)


def go_to_toilet(day: Day):
    day.toilet.use()


def drink(day: Day):
    day.character.drink(day.character.thirst)


def prepare_actions(day: Day):
    if day.state == DayState.LESSON:
        if day.current_lesson() == 'PE' or day.character.holding_blocked:
            return tuple(filter(lambda it: it is not None, (
                ('Wait 2 minutes', wait),
                ('Wait some time...', wait_few),
                ('Ask to go out for toilet', ask_to_go_out)
                if not day.teacher.upset and not day.character.holding_blocked else None,
                (f'Pee in the {day.character.underwear.name.lower()}', pee_in_wear)
                if not day.character.holding_blocked else None,
                ('Drink', drink) if not day.character.holding_blocked else None
            )))
        else:
            return tuple(filter(lambda it: it is not None, (
                ('Wait 2 minutes', wait),
                ('Wait some time...', wait_few),
                ('Ask to go out for toilet', ask_to_go_out) if not day.teacher.upset else None,
                ('Press on the crotch', press_on_crotch),
                ('Rub thighs', rub_thighs),
                ('Fidget', fidget),
                ('Lean forward', lean_forward),
                ('Cross the legs', cross_legs),
                (f'Pee in the {day.character.underwear.name.lower()}', pee_in_wear),
                ('Drink', drink))))
    elif day.state == DayState.BREAK:
        return ('Go to toilet', go_to_toilet), \
               ('Wait 2 minutes', wait), \
               ('Wait some time...', wait_few), \
               ('Press on the crotch', press_on_crotch), \
               ('Rub thighs', rub_thighs), \
               ('Fidget', fidget), \
               ('Lean forward', lean_forward), \
               ('Cross the legs', cross_legs), \
               (f'Pee in the {day.character.underwear.name.lower()}', pee_in_wear), \
               ('Drink', drink)
    elif day.state == DayState.BREAK_PUNISHMENT:
        return ('Wait 2 minutes', wait), \
               ('Wait some time...', wait_few), \
               ('Press on the crotch', press_on_crotch), \
               ('Rub thighs', rub_thighs), \
               ('Fidget', fidget), \
               ('Cross the legs', cross_legs), \
               (f'Pee in the {day.character.underwear.name.lower()}', pee_in_wear), \
               ('Drink', drink)
    else:
        return ()


def print_actions(actions):
    print()
    n = 1
    for action in actions:
        print(n, action[0])
        n += 1


def ask_action(day):
    actions = prepare_actions(day)
    print_actions(actions)

    selected = int(input())
    while (selected - 1) not in range(len(actions)):
        selected = int(input())

    actions[selected - 1][1](day)


def main():
    day = Day()
    day.tick()

    while day.state != DayState.END:
        print_data(day)
        day.tick()
        ask_action(day)


main()
