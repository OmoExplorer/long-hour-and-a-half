from .day import Day
from .enums import DayState, Difficulty
from .util import chance


def wait(_):
    pass


def wait_few(day: Day):
    for _ in range(int(int(input('How much minutes?')) / 2) - 1):
        day.tick()


def create_bar(n, min_, max_):
    sum_sections = 20
    colored_sections = round(n / max_ * sum_sections)
    uncolored_sections = sum_sections - colored_sections
    bar = '   '
    for i in range(colored_sections):
        bar += '█'
    for i in range(uncolored_sections):
        bar += '░'
    return bar


def print_data(day):
    character = day.character
    bladder = character.bladder

    print(character.name)
    print(character.gender.value)
    print(f"{day.character.name}'s thoughts\n", character.thoughts, '\n', sep='')
    print('Urine\t\t\t\t', bladder.urine, '/', bladder.maximal_urine, ' ml (',
          round(bladder.urine_decimal_ratio * 100), '%)', create_bar(bladder.urine_decimal_ratio, 0, 1), sep='')
    print('Tummy water\t\t\t', bladder.tummy_water, ' ml', sep='')
    print('Sphincter power\t\t\t', round(bladder.sphincter.power), '%', create_bar(bladder.sphincter.power, 0, 100),
          sep='')
    print('Embarrassment\t\t\t', character.embarrassment, '%', create_bar(character.embarrassment, 0, 100), sep='')
    print('Thirst\t\t\t\t', round(character.thirst), '%', create_bar(character.thirst, 0, 100), sep='')
    print('Underwear\t\t\t', character.underwear, ', ', round(character.underwear.dryness), '/',
          round(character.underwear.maximal_dryness), ' ml',
          create_bar(character.underwear.dryness, 0, character.underwear.maximal_dryness), sep='')
    print('Outerwear\t\t\t', day.character.outerwear, ', ', round(day.character.outerwear.dryness), '/',
          round(day.character.outerwear.maximal_dryness), ' ml',
          create_bar(character.outerwear.dryness, 0, character.outerwear.maximal_dryness), sep='')
    print('Time\t\t\t\t', day.time, f' ({day.time_until_lesson_finish} left)', sep='')
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
        day.character.require_thought("Aaaaaah!!! I can't stop!",
                                      "Aaaaaah!!! I can't stop peeing!",
                                      "Aaaaaah!!! I can't stop the flow!",
                                      "Aaaaaah!!! I can't stop it!")
    elif chance(result_chances['peeing more'][day.difficulty] * day.character.bladder.urine_decimal_ratio):
        multiplier = {
            Difficulty.EASY: 1.2,
            Difficulty.MEDIUM: 1.4,
            Difficulty.HARD: 1.7,
        }
        day.character.pee_into_wear(how_much * multiplier[day.difficulty])
        day.character.require_thought("Damn! I peed a bit much than I was going to.",
                                      "Uhhh! I hardly stopped peeing!",
                                      "Uhhh! I hardly stopped the flow!",
                                      "Ohhh... It was hard to stop.")
    elif chance(result_chances["can't pee"][day.difficulty]):
        day.character.require_thought("I can't start peeing.",
                                      "I'm trying to pee, but I can't.",
                                      "I can't force myself to pee right here and now.")
    else:
        day.character.pee_into_wear(how_much)


def go_to_toilet(day: Day):
    day.toilet.use()


def drink(day: Day):
    day.character.drink(day.character.thirst)


def prepare_actions(day: Day):
    if day.state == DayState.LESSON:
        if day.current_lesson() == 'PE' or day.character.holding_blocked:
            actions = [
                ('Wait 2 minutes', wait),
                ('Wait some time...', wait_few),
                ('Ask to go out for toilet', ask_to_go_out)
                if not day.teacher.upset and not day.character.holding_blocked else None,
                (f'Pee in the {day.character.underwear.name.lower()}', pee_in_wear)
                if not day.character.holding_blocked else None,
                ('Drink', drink) if not day.character.holding_blocked else None
            ]
            return filter(lambda it: it is not None, actions)

        else:
            actions = [
                ('Wait 2 minutes', wait),
                ('Wait some time...', wait_few),
                ('Ask to go out for toilet',
                 ask_to_go_out) if not day.teacher.upset else None,
                ('Press on the crotch', press_on_crotch),
                ('Rub thighs', rub_thighs),
                ('Fidget', fidget),
                ('Lean forward', lean_forward),
                ('Cross the legs', cross_legs),
                (f'Pee in the {day.character.underwear.name.lower()}',
                 pee_in_wear),
                ('Drink', drink)
            ]
            return filter(lambda it: it is not None, actions)

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
    for n, action in enumerate(actions, 1):
        print(n, action[0])


def ask_action(day):
    actions = tuple(prepare_actions(day))
    print_actions(actions)

    try:
        selected = int(input())
    except ValueError:
        selected = 0
    while (selected - 1) not in range(len(actions)):
        try:
            selected = int(input())
        except ValueError:
            selected = 0

    actions[selected - 1][1](day)


def main():
    day = Day()
    day.tick()

    while day.state != DayState.END:
        print_data(day)
        day.tick()
        ask_action(day)


main()
