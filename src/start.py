from sys import exc_info
from tkinter.messagebox import showerror
from traceback import format_exc

bar = '=' * 40

try:
    from a_long_hour_and_a_half import main

    main()
except Exception:
    class_, message, tb = exc_info()

    class_name = class_.__name__
    showerror('A Long Hour and a Half: fatal error', "A fatal error has occured.\n\n" + bar + '\n'
                                                     + format_exc() + bar + '\n\n'
                                                     "Press Ctrl + C to copy this message.\n"
                                                     "Please send this information to OmoExplorer @ omorashi.org"
                                                     "\nor create an issue on GitHub:\n"
                                                     ".\n\nI'm sorry for the inconvenience.")  # TODO
