from tkinter import *
from tkinter.font import Font

import game_manager
from game_metadata import GAME_NAME, GAME_DESCRIPTION
from ui import wear_editor, character_editor


def new_game():
    root.destroy()
    game_manager.new_game()


def load_game():
    root.destroy()
    game_manager.load_game()


def run_character_editor():
    root.destroy()
    character_editor.run()


def run_wear_editor():
    root.destroy()
    wear_editor.run()


def show_about():
    about = Toplevel(root)
    about.title('About ' + GAME_NAME)
    Label(about, text=GAME_DESCRIPTION).pack(fill=BOTH)
    about.resizable(False, False)
    about.mainloop()


root = Tk()
root.resizable(False, False)
root.title(GAME_NAME)

# noinspection SpellCheckingInspection
welcome_label_font = Font(family='Tahoma', size=18)
welcome_label = Label(root, text=GAME_NAME, font=welcome_label_font)
welcome_label.grid(row=1, column=1)

Button(root, text='New game', command=new_game).grid(row=2, column=1)
Button(root, text='Load game', command=load_game).grid(row=3, column=1)
Button(root, text='Character editor', command=run_character_editor).grid(row=4, column=1)
Button(root, text='Wear editor', command=run_wear_editor).grid(row=5, column=1)
Button(root, text='About', command=show_about).grid(row=6, column=1)
Button(root, text='Quit', command=exit).grid(row=7, column=1)

root.mainloop()
