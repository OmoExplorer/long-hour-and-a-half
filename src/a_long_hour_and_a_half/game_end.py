from random import choice

from .enums import FEMALE


def game_over(day):
    quotes = [
        'I peed too much... Now everyone will see it...',
        'I... I can see a puddle beneath me...',
        f'Wh-what?... Classmates yell "Look! {day.character.name} is peeing '
        f'right in {"her" if day.character.gender == FEMALE else "his"} '
        f'{day.character.outerwear.name.lower()}!"...',
        "No... Nooo... I can't believe... Did I really wet myself completely just now?",
        f'Everyone is looking at me! They are whispering between each other... '
        f'"Look! {"She" if day.character.gender == FEMALE else "He"} is wetting himself"...',
        "I CAN'T HOLD IT ANYMORE!"
    ]

    # print('\n\n')
    # print('"' + colored(choice(quotes), 'red', attrs=['bold']) + '"')
    # print('\n\n')
    # print(colored('You failed!', 'yellow', 'on_red', ['bold']))
    day.game.ui.show_message(choice(quotes) + '\nYou failed!')
    exit()


def win(day):
    # print(colored('You win!', 'blue', 'on_green', ['bold']))
    day.game.ui.show_message('You won!')
    exit()
