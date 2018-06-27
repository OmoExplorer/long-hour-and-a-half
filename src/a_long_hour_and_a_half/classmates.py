class Classmates:
    def __init__(self, state):
        self._state = state

    def notice_holding(self):
        self._state.character.embarrassment += 10
