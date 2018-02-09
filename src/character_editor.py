import pickle
from tkinter import *
from tkinter import filedialog

from character import CharacterProfile
from game_metadata import GAME_NAME
from gender import Gender

_root = None
_name_var = StringVar(_root, 'Mrs. X')
_gender_var = StringVar(_root)
_incontinence_var = DoubleVar(_root)


def _save():
    file = filedialog.asksaveasfile('x', title='Save character', defaultextension='.lhhc',
                                    filetypes=((GAME_NAME + ' character file', '.lhhc'),))

    gender = Gender.FEMALE if _gender_var.get() == 'Female' else Gender.MALE

    character = CharacterProfile(_name_var.get(), gender, _incontinence_var.get())

    pickle.dump(character, file)


def _load():
    file = filedialog.askopenfile(title='Load character', defaultextension='.lhhc',
                                  filetypes=((GAME_NAME + ' character file', '.lhhc'),))

    character = pickle.load(file)

    _name_var.set(character.name)
    _gender_var.set(character.gender)
    _incontinence_var.set(character.incontinence)


def run():
    global _root

    global _name_var
    global _gender_var
    global _incontinence_var

    _root = Toplevel()
    _root.title('Character editor')

    Label(_root, text='Name').grid(row=1, column=1)
    Label(_root, text='Gender').grid(row=2, column=1)
    Label(_root, text='Incontinence').grid(row=3, column=1)

    Entry(_root).grid(row=1, column=2, variable=_name_var).grid(row=4, column=1)
    OptionMenu(_root, _gender_var, 'Female', 'Male').grid(row=5, column=1)
    Scale(_root, digits=_incontinence_var, from_=0.1, to=10, orient=HORIZONTAL).grid(row=6, column=1)

    Button(_root, text='Save', command=_save).grid(row=7, column=1)
    Button(_root, text='Load', command=_load).grid(row=7, column=2)

    _root.mainloop()
