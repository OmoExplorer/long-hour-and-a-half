class Classmates:
    def __init__(self, day):
        self._day = day

    def notice_holding(self):
        self._day.character.embarrassment += 10
