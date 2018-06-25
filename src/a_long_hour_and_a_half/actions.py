from a_long_hour_and_a_half import chance, EASY, MEDIUM, HARD


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