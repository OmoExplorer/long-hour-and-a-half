from tkinter.messagebox import showerror
from traceback import format_exc

bar = '=' * 40

# noinspection PyBroadException
try:
    from a_long_hour_and_a_half import main

    main()
except Exception:
    with open('lhh_crash_info.txt', 'w+') as f:
        f.write(format_exc())

    crash_text = "A fatal error has occured.\n\n" + bar + '\n' + format_exc() + bar + '\n\n' + \
                 "Crash info was written to 'lhh_crash_info.txt' in the same" \
                 "folder where the game is.\n" \
                 "Please send this information to OmoExplorer at omorashi.org" \
                 "\nor create an issue on GitHub:\n" \
                 "https://github.com/javabird25/long-hour-and-a-half/issues/new" \
                 "\n\nI'm sorry for the inconvenience."

    showerror('A Long Hour and a Half: fatal error', crash_text)

    exit(1)
