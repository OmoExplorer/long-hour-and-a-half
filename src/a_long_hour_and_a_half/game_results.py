from random import choice

from .day_state import DayState


def game_over(day):
    day.state = DayState.END

    print('\n\n')
    print('"' + choice((
        'I peed too much... Now everyone will see it...',
        'I... I can see a puddle beneath me...',
        f'Wh-what?... Classmates yell "Look! {day.character.name} is peeing '
        f'right in {"her" if day.character.gender == "Female" else "his"} {day.character.outerwear.name.lower()}!"...',
        "No... Nooo... I can't believe... Did I really wet myself completely just now?",
        f'Everyone is looking at me! They are whispering between each other... '
        f'"Look! {"she" if day.character.gender == "Female" else "he"} is wetting himself"...',
        "I CAN'T HOLD IT ANYMORE!"
    )) + '"')
    print('\n\n')
    print('You failed!')


def win(day):
    day.state = DayState.END
    print('You win!')
