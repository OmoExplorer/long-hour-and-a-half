class _Wear:
    def __init__(self, name, in_game_name, pressure, absorption, drying):
        self.name = name
        self.in_game_name = in_game_name
        self.pressure = pressure
        self.absorption = absorption
        self.drying = drying


class Underwear(_Wear):
    def __init__(self, name, in_game_name, pressure, absorption, drying):
        super().__init__(name, in_game_name, pressure, absorption, drying)


class Outerwear(_Wear):
    def __init__(self, name, in_game_name, pressure, absorption, drying):
        super().__init__(name, in_game_name, pressure, absorption, drying)
