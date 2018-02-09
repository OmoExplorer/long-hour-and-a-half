import pickle
from tkinter import *
from tkinter import filedialog

from game_metadata import GAME_NAME
from wear import *

_root = None
_name = None
_game_name = None
_pressure = None
_absorption = None
_drying = None
_type_var = None


def _save():
    file = filedialog.asksaveasfile('x', title='Save wear', defaultextension='.lhhw',
                                    filetypes=((GAME_NAME + ' wear file', '.lhhw'),))

    if _type_var.get() == 'Underwear':
        wear = Underwear(_name.get(), _game_name.get(), _pressure.get(), _absorption.get(), _drying.get())
    else:
        wear = Outerwear(_name.get(), _game_name.get(), _pressure.get(), _absorption.get(), _drying.get())

    pickle.dump(wear, file)


def _load():
    file = filedialog.askopenfile(title='Load wear', defaultextension='.lhhw',
                                  filetypes=((GAME_NAME + ' wear file', '.lhhw'),))

    wear = pickle.load(file)

    # TODO


def run():
    global _root

    global _name
    global _game_name
    global _pressure
    global _absorption
    global _drying
    global _type_var

    _root = Toplevel()
    _root.title('Wear editor')

    Label(_root, text='Name').grid(row=1, column=1)
    Label(_root, text='Game name').grid(row=2, column=1)
    Label(_root, text='Pressure').grid(row=3, column=1)
    Label(_root, text='Absorption').grid(row=4, column=1)
    Label(_root, text='Drying').grid(row=5, column=1)
    Label(_root, text='Type').grid(row=6, column=1)

    _name = Entry(_root).grid(row=1, column=2)
    _game_name = Entry(_root).grid(row=2, column=2)
    _pressure = Spinbox(_root, from_=0, increment=0.1).grid(row=3, column=2)
    _absorption = Spinbox(_root, from_=0, increment=0.1).grid(row=4, column=2)
    _drying = Spinbox(_root, from_=0, increment=0.1).grid(row=5, column=2)
    _type_var = StringVar(_root)
    OptionMenu(_root, _type_var, 'Underwear', 'Outerwear')

    Button(_root, text='Save', command=_save).grid(row=7, column=1)
    Button(_root, text='Load', command=_load).grid(row=7, column=2)

    _root.mainloop()
