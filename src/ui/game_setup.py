import pickle
from tkinter import *
from tkinter import filedialog

from character import Character
from game_metadata import GAME_NAME
from game_reset import reset_game
from wardrobe import underwear_models, outerwear_models, get_underwear_model, get_outerwear_model

_NO_BREAK_SPACE = ' '
_profile = None


def setup():
    root = Tk()
    root.title(GAME_NAME)
    root.resizable(False, True)

    Label(root, text='Character').grid(row=1, column=1)
    character_name_label = Label(root, text='<not selected>')
    character_name_label.grid(row=1, column=2)
    Button(root, text='Select...', command=lambda: _select_character(character_name_label.config)).grid(row=1, column=3)

    underwear_tree = _add_wear_tree(root, underwear_models, 1)
    outerwear_tree = _add_wear_tree(root, outerwear_models, 2)

    Label(root, text='Difficulty').grid(row=3, column=1)
    difficulty_var = StringVar(root, 'Medium')
    OptionMenu(root, difficulty_var, 'Easy', 'Medium', 'Hard').grid(row=3, column=2, columnspan=2)

    Button(root, text='Start',
           command=lambda: _start(
               root,
               difficulty_var.get(),
               get_underwear_model(underwear_tree.curselection()[0].replace(_NO_BREAK_SPACE, '')),
               get_outerwear_model(outerwear_tree.curselection()[0].replace(_NO_BREAK_SPACE, ''))
           )).grid(row=4, column=1, columnspan=3)

    root.mainloop()


def _add_wear_tree(root, wears, column):  # TODO: Add scroll
    box = Listbox(root,
                  listvariable=StringVar(root, ' '.join(map(lambda x: x.name.replace(' ', _NO_BREAK_SPACE), wears))))
    box.grid(row=2, column=column)
    return box


def _select_character(label_config):
    global _profile
    file = filedialog.askopenfile(title='Save wear', defaultextension='.lhhc',
                                  filetypes=((GAME_NAME + ' character', '.lhhc'),))
    _profile = pickle.load(file)
    _update_character_label(label_config)


def _update_character_label(label_config):
    if _profile is not None:
        label_config(text=_profile.name)
    else:
        label_config(text='<not selected>')


def _start(root, difficulty, underwear, outerwear):
    if _profile is None:
        return
    root.destroy()
    character = Character(_profile, underwear, outerwear)
    reset_game(character, difficulty)
