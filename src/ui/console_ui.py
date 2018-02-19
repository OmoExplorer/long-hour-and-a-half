from action import Action
from ui.game_ui import UI


class ConsoleUI(UI):
    def __print_characteristics(self):
        with self.__gameplay as g:
            print(g.character.name)
            print('Bladder fullness:', g.character.bladder.urine, 'ml')
            print('Water in tummy:', g.character.tummy_water, 'ml')
            print('Sphincter strength: ', g.character.bladder.sphincter.strength, '%', sep='')
            print('Clothes dryness:', g.character.dryness)
            print('Embarrassment:', g.character.embarrassment)
            print('Incontinence: ', g.character.bladder.sphincter.incontinence, 'x', sep='')
            print('Time:', g.world.time)
            print('Underwear:', g.character.underwear)
            print('Outerwear:', g.character.outerwear)

    def do_turn(self, text, actions) -> Action:
        print(text)
        print()
        self.__print_characteristics()
