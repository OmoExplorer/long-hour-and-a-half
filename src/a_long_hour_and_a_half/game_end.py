from random import choice

from termcolor import colored

from .enums import StateMode


def game_over(day):
    day.state = StateMode.END

    quotes = [
        'I peed too much... Now everyone will see it...',
        'I... I can see a puddle beneath me...',
        f'Wh-what?... Classmates yell "Look! {day.character.name} is peeing '
        f'right in {"her" if day.character.gender == "Female" else "his"} '
        f'{day.character.outerwear.name.lower()}!"...',
        "No... Nooo... I can't believe... Did I really wet myself completely just now?",
        f'Everyone is looking at me! They are whispering between each other... '
        f'"Look! {"she" if day.character.gender == "Female" else "he"} is wetting himself"...',
        "I CAN'T HOLD IT ANYMORE!"
    ]

    print('\n\n')
    print('"' + colored(choice(quotes), 'red', attrs=['bold']) + '"')
    print('\n\n')
    print(colored('You failed!', 'yellow', 'on_red', ['bold']))


def win(day):
    day.state = StateMode.END
    print(colored('You win!', 'blue', 'on_green', ['bold']))
