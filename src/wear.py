class _WearModel:
    def __init__(self, name, in_game_name, pressure, absorption, drying):
        self.name = name
        self.in_game_name = in_game_name
        self.pressure = pressure
        self.absorption = absorption
        self.drying = drying


class UnderwearModel(_WearModel):
    pass


class OuterwearModel(_WearModel):
    pass


class _Wear:
    def __init__(self, model, color):
        self.model = model
        self.color = color


class Underwear(_Wear):
    pass


class Outerwear(_Wear):
    pass
